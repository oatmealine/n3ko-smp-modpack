package zone.oat.n3komod.client.render.util;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class CubeRenderer {
  public static void buildCube(BufferBuilder builder, Matrix4f matrix4f) {
    builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

    builder.vertex(matrix4f, -1.0f, -1.0f, -1.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -1.0f, -1.0f, 1.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, -1.0f, 1.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, -1.0f, -1.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -1.0f, 1.0f, -1.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -1.0f, -1.0f, -1.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, -1.0f, -1.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, 1.0f, -1.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -1.0f, -1.0f, 1.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -1.0f, 1.0f, 1.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, 1.0f, 1.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, -1.0f, 1.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -1.0f, 1.0f, 1.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -1.0f, 1.0f, -1.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, 1.0f, -1.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, 1.0f, 1.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, 1.0f, -1.0f, -1.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, -1.0f, 1.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, 1.0f, 1.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, 1.0f, 1.0f, -1.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();

    builder.vertex(matrix4f, -1.0f, 1.0f, -1.0f).texture(0.0F, 0.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -1.0f, 1.0f, 1.0f).texture(0.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -1.0f, -1.0f, 1.0f).texture(1.0F, 1.0F).color(255, 255, 255, 255).next();
    builder.vertex(matrix4f, -1.0f, -1.0f, -1.0f).texture(1.0F, 0.0F).color(255, 255, 255, 255).next();
  }

  public static void renderCube(MatrixStack matrices) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder builder = tessellator.getBuffer();

    Matrix4f position = matrices.peek().getPositionMatrix();

    buildCube(builder, position);

    //matrices.push();
    tessellator.draw();
    //matrices.pop();
  }
}
