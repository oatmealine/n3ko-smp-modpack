package zone.oat.n3komod.client.render.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import zone.oat.n3komod.util.ModIdentifier;

import java.util.function.Supplier;

public abstract class DrawableHelperRebourne {
  private static final Identifier UV_TEST_TEX = new ModIdentifier("textures/environment/uvtest.png");

  public static void fillShader(DrawContext ctx, int x1, int y1, int x2, int y2, int color, Supplier<ShaderProgram> shaderSupplier) {
    fillShader(ctx.getMatrices().peek().getPositionMatrix(), x1, y1, x2, y2, color, shaderSupplier);
  }

  private static void fillShader(Matrix4f matrix, int x1, int y1, int x2, int y2, int color, Supplier<ShaderProgram> shaderSupplier) {
    if (x1 < x2) {
      int i = x1;
      x1 = x2;
      x2 = i;
    }

    if (y1 < y2) {
      int i = y1;
      y1 = y2;
      y2 = i;
    }

    float f = (float)(color >> 24 & 0xFF) / 255.0F;
    float g = (float)(color >> 16 & 0xFF) / 255.0F;
    float h = (float)(color >> 8 & 0xFF) / 255.0F;
    float j = (float)(color & 0xFF) / 255.0F;
    BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
    RenderSystem.setShader(shaderSupplier);
    RenderSystem.setShaderTexture(0, UV_TEST_TEX);
    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
    bufferBuilder.vertex(matrix, (float)x1, (float)y2, 0.0F).texture(0.0f, 1.0f).color(g, h, j, f).next();
    bufferBuilder.vertex(matrix, (float)x2, (float)y2, 0.0F).texture(1.0f, 1.0f).color(g, h, j, f).next();
    bufferBuilder.vertex(matrix, (float)x2, (float)y1, 0.0F).texture(1.0f, 0.0f).color(g, h, j, f).next();
    bufferBuilder.vertex(matrix, (float)x1, (float)y1, 0.0F).texture(0.0f, 0.0f).color(g, h, j, f).next();
    BufferRenderer.draw(bufferBuilder.end());
    //RenderSystem.enableTexture();
    RenderSystem.disableBlend();
  }
}
