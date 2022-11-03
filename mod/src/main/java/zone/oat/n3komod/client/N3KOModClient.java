package zone.oat.n3komod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import zone.oat.n3komod.client.render.ThreadSkyRenderHandler;
import zone.oat.n3komod.registry.N3KOBlockEntities;
import zone.oat.n3komod.registry.N3KOBlocks;
import zone.oat.n3komod.client.render.blockentity.PlushBlockEntityRenderer;
import zone.oat.n3komod.registry.N3KODimensions;

public class N3KOModClient implements ClientModInitializer {
  public static final ThreadSkyRenderHandler threadSkyRenderer = new ThreadSkyRenderHandler();
  public static final Vec3d threadFogColor = new Vec3d(0.929f, 0.8509f, 0.929f);

  @Override
  public void onInitializeClient() {
    BlockEntityRendererRegistry.register(N3KOBlockEntities.PLUSH_BLOCK_ENTITY, PlushBlockEntityRenderer::new);
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.SHE_PLUSH, RenderLayer.getTranslucent());
    BlockRenderLayerMap.INSTANCE.putBlock(N3KOBlocks.PLUSH_BASE, RenderLayer.getTranslucent());

    N3KOPackets.init();

    DimensionRenderingRegistry.registerSkyRenderer(N3KODimensions.THREAD_REGISTRY_KEY, threadSkyRenderer);
    //ClientSpriteRegistryCallback.EVENT.register((atlasTexture, registry) -> MinecraftClient.getInstance().execute(threadSkyRenderer::makeSkyShader));
  }
}
