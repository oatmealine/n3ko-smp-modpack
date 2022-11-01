package zone.oat.n3komod.client.render;


import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.Shader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.util.ModIdentifier;

import java.io.IOException;

public class ThreadSkyRenderHandler implements DimensionRenderingRegistry.SkyRenderer {

  @Nullable
  private ShaderEffect fbmPerlinShader;

  @Nullable
  private Framebuffer skyFramebuffer;

  private final MinecraftClient mc = MinecraftClient.getInstance();

  public void makeSkyShader() {
    if (this.fbmPerlinShader != null) {
      this.fbmPerlinShader.close();
    }
    Identifier identifier = new Identifier("shaders/post/fbm-perlin.json");
    try {
      this.fbmPerlinShader = new ShaderEffect(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), identifier);
      this.fbmPerlinShader.setupDimensions(this.mc.getWindow().getFramebufferWidth(), this.mc.getWindow().getFramebufferHeight());
      this.skyFramebuffer = this.fbmPerlinShader.getSecondaryTarget("final");
    } catch (IOException err) {
      N3KOMod.LOGGER.warn("Failed to load shader: {}", identifier, err);
      this.fbmPerlinShader = null;
      this.skyFramebuffer = null;
    } catch (JsonSyntaxException err) {
      N3KOMod.LOGGER.warn("Failed to parse shader: {}", identifier, err);
      this.fbmPerlinShader = null;
      this.skyFramebuffer = null;
    }

  }

  @Override
  public void render(WorldRenderContext context) {
    this.fbmPerlinShader.render(context.tickDelta());
    this.mc.getFramebuffer().beginWrite(false);
  }
}