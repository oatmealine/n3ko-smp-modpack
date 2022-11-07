package zone.oat.n3komod.client.render.util;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public class SphereRenderer {
  private static VertexBuffer vertexBuffer;

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

  public static void renderSphere(MatrixStack matrices, Matrix4f projection, int stack, int slice, Camera camera) {
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
}
