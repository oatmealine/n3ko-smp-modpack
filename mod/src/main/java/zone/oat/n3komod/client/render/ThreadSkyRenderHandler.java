package zone.oat.n3komod.client.render;


import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.N3KOMod;

import java.io.IOException;
import java.util.function.Supplier;

public class ThreadSkyRenderHandler implements DimensionRenderingRegistry.SkyRenderer {

  @Nullable
  private ShaderEffect fbmPerlinShader;

  private int oldWidth;
  private int oldHeight;

  private VertexBuffer vertexBuffer;

  private final MinecraftClient mc = MinecraftClient.getInstance();

  public static void buildSphere(BufferBuilder builder, int stack, int slice, float radius, Vec3f center, float r, float g, float b, float alpha) {
    float r0, r1, alpha0, alpha1, x0, x1, y0, y1, z0, z1, beta;
    float stackStep = (float) (Math.PI / stack);
    float sliceStep = (float) (Math.PI / slice);
    for (int i = 0; i < stack; ++i) {
      alpha0 = (float) (-Math.PI / 2 + i * stackStep);
      alpha1 = alpha0 + stackStep;
      r0 = (float) (radius * Math.cos(alpha0));
      r1 = (float) (radius * Math.cos(alpha1));

      y0 = (float) (radius * Math.sin(alpha0));
      y1 = (float) (radius * Math.sin(alpha1));

      for (int j = 0; j < (slice << 1); ++j) {
        beta = j * sliceStep;
        x0 = (float) (r0 * Math.cos(beta));
        x1 = (float) (r1 * Math.cos(beta));

        z0 = (float) (-r0 * Math.sin(beta));
        z1 = (float) (-r1 * Math.sin(beta));

        builder.vertex(x0 + center.getX(), y0 + center.getY(), z0 + center.getZ())
                .color(r, g, b, alpha)
                .next();
        builder.vertex(x1 + center.getX(), y1 + center.getY(), z1 + center.getZ())
                .color(r, g, b, alpha)
                .next();
      }
    }
  }

  public void renderSphere(MatrixStack matrices, Matrix4f projection, int stack, int slice, float radius, Vec3f center, float r, float g, float b, float alpha) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();
    builder.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

    buildSphere(builder, stack, slice, radius, center, r, g, b, alpha);

    if (vertexBuffer == null) {
      vertexBuffer = new VertexBuffer();
    }
    vertexBuffer.bind();
    builder.end();
    vertexBuffer.upload(builder);

    RenderSystem.enableDepthTest();


    matrices.push();
    RenderSystem.lineWidth(1f);

    RenderSystem.setShader(GameRenderer::getPositionColorShader);

    vertexBuffer.setShader(matrices.peek().getPositionMatrix(), projection, GameRenderer.getPositionColorShader());

    matrices.pop();

    VertexBuffer.unbind();
  }

  public class ThreadShaderSupplier implements Supplier<Shader> {
    private Shader shader;

    @Override
    public Shader get() {
      return shader;
    }

    public void set(Shader shader) {
      this.shader = shader;
    }
  }

  ThreadShaderSupplier supplier = new ThreadShaderSupplier();

  public void makeSkyShader() {
    if (this.fbmPerlinShader != null) {
      this.fbmPerlinShader.close();
    }
    Identifier identifier = new Identifier("shaders/post/fbm-perlin.json");
    try {
      oldWidth = 0;
      oldHeight = 0;
      this.fbmPerlinShader = new ShaderEffect(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), identifier);
      updateSize();
    } catch (IOException err) {
      N3KOMod.LOGGER.warn("Failed to load shader: {}", identifier, err);
      this.fbmPerlinShader = null;
    } catch (JsonSyntaxException err) {
      N3KOMod.LOGGER.warn("Failed to parse shader: {}", identifier, err);
      this.fbmPerlinShader = null;
    }
  }

  public void updateSize() {
    Window window = this.mc.getWindow();
    updateSize(window.getFramebufferWidth(), window.getFramebufferHeight());
  }
  public void updateSize(int width, int height) {
    if (this.fbmPerlinShader == null) return;

    if (width != oldWidth || height != oldHeight) {
      this.fbmPerlinShader.setupDimensions(width, height);
      oldWidth = width;
      oldHeight = height;
    }
  }

  @Override
  public void render(WorldRenderContext context) {
    render(context.matrixStack(), context.projectionMatrix());
  }
  public void render(MatrixStack matrices, Matrix4f projection) {
    //RenderSystem.setShader();
    RenderSystem.setShader(GameRenderer::getPositionColorShader);
    renderSphere(matrices, projection, 10, 10, 200f, new Vec3f(0.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f, 1.0f);
    //this.fbmPerlinShader.render(context.tickDelta());
    //this.mc.getFramebuffer().beginWrite(false);
  }
}