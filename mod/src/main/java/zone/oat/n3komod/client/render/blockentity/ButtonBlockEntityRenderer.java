package zone.oat.n3komod.client.render.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.content.block.ButtonBlock;
import zone.oat.n3komod.content.blockentity.ButtonBlockEntity;
import zone.oat.n3komod.content.blockentity.PlushBlockEntity;

public class ButtonBlockEntityRenderer implements BlockEntityRenderer<ButtonBlockEntity> {
  private final TextRenderer textRenderer;

  public ButtonBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    this.textRenderer = ctx.getTextRenderer();
  }

  @Override
  public void render(ButtonBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    if (blockEntity.label.equals("")) return;

    BlockState blockState = blockEntity.getCachedState();
    OrderedText text = Text.of(blockEntity.label).asOrderedText();

    matrices.push();

    matrices.translate(0.5, 0.5, 0.5);
    float h = -(blockState.get(ButtonBlock.FACING)).asRotation();
    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(h));
    if (blockState.get(ButtonBlock.FACE).equals(WallMountLocation.FLOOR)) {
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));
      matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));
    }
    matrices.translate(0.0, 0.0, -0.162);
    if (blockState.get(ButtonBlock.POWERED)) {
      matrices.translate(0.0, 0.0, -0.06);
    }
    matrices.translate(0.0, 0.0F, 0.046666667F);
    matrices.scale(0.010416667F, -0.010416667F, 0.010416667F);

    float width = this.textRenderer.getWidth(text);
    float height = this.textRenderer.fontHeight;
    float size = 30f / width;
    matrices.translate(0.0f, -(height * size) / 3, 0.0f);
    matrices.scale(size, size, size);
    this.textRenderer.drawWithOutline(text, -width / 2, 0, 0xFFFFFF, 0x111111, matrices.peek().getPositionMatrix(), vertexConsumers, light);

    matrices.pop();
  }
}
