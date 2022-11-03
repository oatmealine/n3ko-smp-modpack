package zone.oat.n3komod.client.render;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.Uniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.util.ModIdentifier;

import java.io.IOException;

public class ThreadSkyRenderHandler implements DimensionRenderingRegistry.SkyRenderer {
  private VertexBuffer vertexBuffer;


  public static void buildSphere(BufferBuilder builder, int stack, int slice, Matrix4f matrix4f) {
    builder.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_TEXTURE_COLOR);

    // setting this as high as possible to prevent view bobbing from affecting the skybox
    float radius = 600f;

    float r0, r1, alpha0, alpha1, x0, x1, y0, y1, z0, z1, beta;
    float stackStep = (float) (Math.PI / stack);
    float sliceStep = (float) (Math.PI / slice);
    for (int i = 0; i < stack; ++i) {
      alpha0 = (float) (-Math.PI / 2 + i * stackStep);
      alpha1 = alpha0 + stackStep;
      r0 = (float) Math.cos(alpha0) * radius;
      r1 = (float) Math.cos(alpha1) * radius;

      y0 = (float) Math.sin(alpha0) * radius;
      y1 = (float) Math.sin(alpha1) * radius;

      for (int j = 0; j < (slice << 1); ++j) {
        beta = j * sliceStep;
        x0 = (float) (r0 * Math.cos(beta));
        x1 = (float) (r1 * Math.cos(beta));

        z0 = (float) (-r0 * Math.sin(beta));
        z1 = (float) (-r1 * Math.sin(beta));

        builder.vertex(matrix4f, x0, y0, z0)
                .texture((float) i / (stack - 1), (float) j / (slice - 1))
                .color(1f, 1f, 1f, 1f)
                .next();
        builder.vertex(matrix4f, x1, y1, z1)
                .texture((float) (i + 1) / (stack - 1), (float) j / (slice - 1))
                .color(1f, 1f, 1f, 1f)
                .next();
      }
    }
  }

  public static void buildCube(BufferBuilder builder, Matrix4f matrix4f) {
    builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

    builder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -100.0f, -100.0f, -99.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, -100.0f, -99.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -100.0f, 100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -100.0f, 100.0f, 101.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, 100.0f, -100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, -100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, 100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 100.0f, 100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -100.0f, 100.0f, -100.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -100.0f, 100.0f, 100.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -100.0f, -100.0f, 100.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -100.0f, -100.0f, -100.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
  }

  public void renderSphere(MatrixStack matrices, Matrix4f projection, int stack, int slice, Camera camera) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();

    Matrix4f position = matrices.peek().getPositionMatrix();

    //Quaternion rotation = camera.getRotation();
    //Matrix4f rot = new Matrix4f(rotation);
    buildSphere(builder, stack, slice, position);
    //buildCube(builder, position); // strictly testing-only; the cube looks awful for a skybox

    /*
    if (vertexBuffer == null) {
      vertexBuffer = new VertexBuffer();
    }
    vertexBuffer.bind();
    builder.end();
    vertexBuffer.upload(builder);
    */

    matrices.push();

    //RenderSystem.setShader(GameRenderer::getPositionColorShader);

    //vertexBuffer.setShader(matrices.peek().getPositionMatrix(), projection, this.threadShader);
    //vertexBuffer.setShader(matrices.peek().getPositionMatrix(), projection, GameRenderer.getPositionTexColorShader());
    //vertexBuffer.drawElements();
    //vertexBuffer.drawVertices();
    tessellator.draw();

    matrices.pop();

    //VertexBuffer.unbind();
  }

  private static final String SHADER_NAME = "fbm-perlin";
  private static final Identifier UV_TEST_TEX = new ModIdentifier("textures/environment/uvtest.png");

  @Nullable
  private Shader threadShader;
  private Shader getThreadShader() {
    return this.threadShader;
  }

  public Shader createShader(ResourceManager resource) throws IOException {
    N3KOMod.LOGGER.info("i have been asked to create a \"Shader\" by the mystical Mixin forces. let's hope this goes well...");
    return new Shader(resource, SHADER_NAME, VertexFormats.POSITION_TEXTURE_COLOR);
  }
  public void setShader(Shader shader) {
    this.threadShader = shader;
    N3KOMod.LOGGER.info(":DDDD POGGER????? shader has been set: " + shader.toString() + "   <--- ((proof)");
  }

  @Override
  public void render(WorldRenderContext context) {
    render(context.matrixStack(), context.projectionMatrix(), context.camera());
  }
  public void render(MatrixStack matrices, Matrix4f projection, Camera camera) {
    if (this.threadShader != null) {
      RenderSystem.setShader(this::getThreadShader);
      RenderSystem.setShaderTexture(0, UV_TEST_TEX);

      Uniform uniform = this.threadShader.getUniformOrDefault("GameTime");
      if (uniform != null) uniform.set(((float) MinecraftClient.getInstance().world.getTime()) / 50.0f);

      renderSphere(matrices, projection, 18, 36, camera);

      RenderSystem.enableTexture();
    }
  }
}