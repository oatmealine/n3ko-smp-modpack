package zone.oat.n3komod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zone.oat.n3komod.client.N3KOModClient;
import zone.oat.n3komod.registry.N3KODimensions;

@Mixin(WorldRenderer.class)
public class SkyboxRenderMixin {
  @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"), cancellable = true)
  private void renderDimensionSkybox(MatrixStack matrices, Matrix4f matrix4f, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
    boolean cancel = !N3KODimensions.isInThread();

    runnable.run();
    if (cancel) {
      ci.cancel();
    } else {
      N3KOModClient.threadSkyRenderer.render();
    }
  }
}
