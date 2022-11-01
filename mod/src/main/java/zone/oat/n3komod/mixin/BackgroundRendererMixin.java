package zone.oat.n3komod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.registry.N3KODimensions;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
  @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CubicSampler;sampleColor(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/CubicSampler$RgbFetcher;)Lnet/minecraft/util/math/Vec3d;"))
  private static Vec3d injected(Vec3d pos, CubicSampler.RgbFetcher rgbFetcher) {
    boolean cancel = !MinecraftClient.getInstance().world.method_40134().matchesId(N3KODimensions.THREAD);
    if (!cancel) {
      return new Vec3d(1, 1, 1);
    } else {
      return CubicSampler.sampleColor(pos, rgbFetcher);
    }
  }
}
