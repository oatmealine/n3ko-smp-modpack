package zone.oat.n3komod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import zone.oat.n3komod.N3KOMod;

@Mixin(OreConfiguredFeatures.class)
public class AdjustAncientDebrisSpawnRatesMixin {
  @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/OreFeatureConfig;<init>(Lnet/minecraft/structure/rule/RuleTest;Lnet/minecraft/block/BlockState;IF)V"))
  private static void injected(Args args) {
    BlockState state = args.get(1);
    int size = args.get(2);

    if (state.getBlock().equals(Blocks.ANCIENT_DEBRIS)) {
      N3KOMod.LOGGER.info("now at {}", size + 3);
      args.set(2, size + 3);
    }
  }
}
