package zone.oat.n3komod.client.render;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.Uniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.client.render.util.SphereRenderer;
import zone.oat.n3komod.util.ModIdentifier;

import java.io.IOException;

public class ThreadSkyRenderHandler implements DimensionRenderingRegistry.SkyRenderer {
  private static final Identifier UV_TEST_TEX = new ModIdentifier("textures/environment/uvtest.png");

  @Override
  public void render(WorldRenderContext context) {
    render(context.matrixStack(), context.projectionMatrix(), context.camera());
  }
  public void render(MatrixStack matrices, Matrix4f projection, Camera camera) {
    ShaderProgram shader = N3KOShaders.threadSky.getShader();
    if (shader != null) {
      RenderSystem.setShader(N3KOShaders.threadSky::getShader);
      RenderSystem.setShaderTexture(0, UV_TEST_TEX);

      shader.gameTime.set((float) MinecraftClient.getInstance().world.getTime() / 50.0f);
      //Uniform uniform = shader.getUniformOrDefault("GameTime");
      //if (uniform != null) uniform.set(((float) MinecraftClient.getInstance().world.getTime()) / 50.0f);

      SphereRenderer.renderSphere(matrices, projection, 18, 36, camera);

      //RenderSystem.enableTexture();
    }
  }
}