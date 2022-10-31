package zone.oat.n3komod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import zone.oat.n3komod.N3KOBlockEntities;
import zone.oat.n3komod.N3KOBlocks;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.client.render.block_entity.PlushBlockEntityRenderer;

import java.util.List;

public class N3KOModClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    BlockEntityRendererRegistry.register(N3KOBlockEntities.SHE_PLUSH_BLOCK_ENTITY, PlushBlockEntityRenderer::new);
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.SHE_PLUSH, RenderLayer.getTranslucent());

    N3KOPackets.init();
  }

  public static BakedModel getModel(BakedModelManager dispatcher, Identifier id) {
    return BakedModelManagerHelper.getModel(dispatcher, id);
  }

  public static void renderBlock(Identifier texture, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    MinecraftClient client = MinecraftClient.getInstance();
    BakedModelManager manager = client.getBakedModelManager();
    BakedModel model = getModel(manager, texture);

    VertexConsumer vertexConsumer1 = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE));
    List<BakedQuad> quads1 = model.getQuads(null, null, client.world.random);
    MatrixStack.Entry entry1 = matrices.peek();

    for (BakedQuad quad : quads1) {
      vertexConsumer1.quad(entry1, quad, 1, 1, 1, light, overlay);
    }
  }
}
