package zone.oat.n3komod;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PlushBlockEntity extends BlockEntity {
  public static final int SQUISH_DURATION = 8;

  public int squishTicks;
  public boolean isSquished;

  public PlushBlockEntity(BlockPos pos, BlockState state) {
    super(N3KOBlockEntities.SHE_PLUSH_BLOCK_ENTITY, pos, state);
  }

  public static void tick(World world, BlockPos pos, BlockState state, PlushBlockEntity blockEntity) {
    if (blockEntity.isSquished) {
      blockEntity.squishTicks++;
    }

    if (blockEntity.squishTicks >= PlushBlockEntity.SQUISH_DURATION) {
      blockEntity.isSquished = false;
      blockEntity.squishTicks = 0;
    }
  }

  public void onUse() {
    this.isSquished = true;
    this.squishTicks = 0;
  }
}
