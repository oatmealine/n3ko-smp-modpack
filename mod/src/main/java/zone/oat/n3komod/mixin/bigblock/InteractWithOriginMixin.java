package zone.oat.n3komod.mixin.bigblock;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import zone.oat.n3komod.content.block.BigBlock;

@Mixin(ClientPlayerInteractionManager.class)
public class InteractWithOriginMixin {

  @Shadow @Final
  private MinecraftClient client;

  @ModifyVariable(at=@At("HEAD"), method={
          "attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
          "updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"
  }, argsOnly=true, ordinal=0)
  public BlockPos modifyAttackPos(BlockPos pos) {
    if (client.world != null) {
      BlockState bs = client.world.getBlockState(pos);
      if (bs.getBlock() instanceof BigBlock) {
        BigBlock b = (BigBlock)bs.getBlock();
        BlockPos origin = pos.add(-bs.get(b.xProp), -bs.get(b.yProp), -bs.get(b.zProp));
        return origin;
      }
    }
    return pos;
  }

}