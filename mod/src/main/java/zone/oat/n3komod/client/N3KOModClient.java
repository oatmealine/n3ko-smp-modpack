package zone.oat.n3komod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.render.RenderLayer;
import zone.oat.n3komod.client.render.N3KOShaders;
import zone.oat.n3komod.client.render.ThreadSkyRenderHandler;
import zone.oat.n3komod.client.render.blockentity.ButtonBlockEntityRenderer;
import zone.oat.n3komod.networking.N3KOS2CPackets;
import zone.oat.n3komod.registry.N3KOBlockEntities;
import zone.oat.n3komod.registry.N3KOBlocks;
import zone.oat.n3komod.client.render.blockentity.PlushBlockEntityRenderer;
import zone.oat.n3komod.registry.N3KODimensions;

public class N3KOModClient implements ClientModInitializer {
  public static final ThreadSkyRenderHandler threadSkyRenderer = new ThreadSkyRenderHandler();

  @Override
  public void onInitializeClient() {
    BlockEntityRendererRegistry.register(N3KOBlockEntities.PLUSH_BLOCK_ENTITY, PlushBlockEntityRenderer::new);
    BlockEntityRendererRegistry.register(N3KOBlockEntities.BUTTON_BLOCK_ENTITY, ButtonBlockEntityRenderer::new);
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.SHE_PLUSH, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.NOEL_PLUSH, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.ZEPH_PLUSH, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.LEZ_PLUSH, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.JILLO_PLUSH, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.PAD, RenderLayer.getTranslucent());

    for (int i = 0; i < N3KOBlocks.BUTTONS.length; i++) {
      BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.BUTTONS[i], RenderLayer.getCutout());
    }

    N3KOS2CPackets.init();

    DimensionRenderingRegistry.registerSkyRenderer(N3KODimensions.THREAD_REGISTRY_KEY, threadSkyRenderer);
    //ClientSpriteRegistryCallback.EVENT.register((atlasTexture, registry) -> MinecraftClient.getInstance().execute(threadSkyRenderer::makeSkyShader));

    N3KOShaders.getShaders();
  }
}
