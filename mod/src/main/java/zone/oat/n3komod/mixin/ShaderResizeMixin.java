package zone.oat.n3komod.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zone.oat.n3komod.client.N3KOModClient;

@Mixin(GameRenderer.class)
public class ShaderResizeMixin {
  @Inject(method = "onResized", at = @At("RETURN"))
  protected void injected(int width, int height, CallbackInfo ci) {
    N3KOModClient.threadSkyRenderer.updateSize(width, height);
  }
}
