package zone.oat.n3komod.content.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zone.oat.n3komod.registry.N3KOBlockEntities;

public class PlushBlockEntity extends BlockEntity {
  public static final int SQUISH_DURATION = 8;

  public int squishTicks;
  public boolean isSquished;

  public PlushBlockEntity(BlockPos pos, BlockState state) {
    super(N3KOBlockEntities.PLUSH_BLOCK_ENTITY, pos, state);
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
