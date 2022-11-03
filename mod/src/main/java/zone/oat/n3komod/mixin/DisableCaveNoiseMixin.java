package zone.oat.n3komod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.BiomeEffectSoundPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.registry.N3KODimensions;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(BiomeEffectSoundPlayer.class)
public class DisableCaveNoiseMixin {
  // dumb. hacky. stupid. i don't care
  @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
  private void injected(CallbackInfo ci) {
    boolean cancel = !N3KODimensions.isInThread();
    if (cancel) ci.cancel();
  }
}
