package zone.oat.n3komod.client.render.block_entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.PlushBlock;
import zone.oat.n3komod.PlushBlockEntity;
import zone.oat.n3komod.client.N3KOModClient;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class PlushBlockEntityRenderer implements BlockEntityRenderer<PlushBlockEntity> {
  public PlushBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

  @Override
  public void render(PlushBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    matrices.push();

    BlockPos pos = blockEntity.getPos();
    BlockState state = blockEntity.getCachedState();

    double off = -0.5; // middle point, so-to-speak

    float t = blockEntity.getWorld().getTime() + tickDelta;

    matrices.translate(-off, 0.0, -off);
    //matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
    matrices.multiply(state.get(PlushBlock.FACING).getRotationQuaternion());
    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

    if (blockEntity.isSquished) {
      float squishAmt = 1.0f - (float)blockEntity.squishTicks / PlushBlockEntity.SQUISH_DURATION;
      float squishAnimX = (float)Math.sin(t) * 0.6f;
      float squishAnimY = (float)Math.cos(t) * 0.6f;
      matrices.scale(1.0f + squishAnimX * squishAmt, 1.0f, 1.0f + squishAnimY * squishAmt);
    }

    matrices.translate(off, 0.0, off);

    MinecraftClient client = MinecraftClient.getInstance();
    BlockRenderManager rm = client.getBlockRenderManager();
    BlockModelRenderer mr = rm.getModelRenderer();
    BakedModel model = rm.getModel(state);
    Random random = client.world.getRandom();
    mr.render(blockEntity.getWorld(), model, state, pos, matrices, vertexConsumers.getBuffer(RenderLayers.getBlockLayer(state)), false, random, state.getRenderingSeed(pos), overlay);

    matrices.pop();
  }
}
