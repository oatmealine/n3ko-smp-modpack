package zone.oat.n3komod.client.render;


import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.*;
import net.minecraft.client.util.Window;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.util.ModIdentifier;

import java.io.IOException;

public class ThreadSkyRenderHandler implements DimensionRenderingRegistry.SkyRenderer {

  @Nullable
  private ShaderEffect fbmPerlinShader;

  private int oldWidth;
  private int oldHeight;

  private final MinecraftClient mc = MinecraftClient.getInstance();

  public static void drawSphereFaces(Tessellator tessellator, BufferBuilder builder,
                                     float cx, float cy, float cz,
                                     float r, int subd,
                                     float red, float grn, float blu, float alpha)
  {

    float step = (float)Math.PI / (subd/2);
    int num_steps180 = (int)(Math.PI / step)+1;
    int num_steps360 = (int)(2*Math.PI / step);
    for (int i = 0; i <= num_steps360; i++)
    {
      float theta = i * step;
      float thetaprime = theta+step;
      builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);  // quad strip to quads
      float xb = 0;
      float zb = 0;
      float xbp = 0;
      float zbp = 0;
      float yp = r;
      for (int j = 0; j <= num_steps180; j++)
      {
        float phi = j * step;
        float x = r * (float)Math.sin(phi) * (float)Math.cos(theta);
        float z = r * (float)Math.sin(phi) * (float)Math.sin(theta);
        float y = r * (float)Math.cos(phi);
        float xp = r * (float)Math.sin(phi) * (float)Math.cos(thetaprime);
        float zp = r * (float)Math.sin(phi) * (float)Math.sin(thetaprime);
        builder.vertex(xb + cx, yp + cy, zb + cz).color(red, grn, blu, alpha).next();
        builder.vertex(xbp + cx, yp + cy, zbp + cz).color(red, grn, blu, alpha).next();
        builder.vertex(xp + cx, y + cy, zp + cz).color(red, grn, blu, alpha).next();
        builder.vertex(x + cx, y + cy, z + cz).color(red, grn, blu, alpha).next();
        xb = x;
        zb = z;
        xbp = xp;
        zbp = zp;
        yp = y;
      }
      tessellator.draw();
    }
  }

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
    render();
  }
  public void render() {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();

    drawSphereFaces(tessellator, builder, 0, 0, 0, 5, 5, 1, 1, 1, 0.6f);

    //this.fbmPerlinShader.render(context.tickDelta());
    //this.mc.getFramebuffer().beginWrite(false);
  }
}