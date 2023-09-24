package zone.oat.n3komod.content.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PadBlock extends Block {
  public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

  public PadBlock(Settings settings) {
    super(settings);

    this.setDefaultState(
      this.stateManager.getDefaultState().with(FACING, Direction.NORTH)
    );
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
  }

  private VoxelShape getShape(BlockState state) {
    Direction dir = state.get(FACING);

    Vec3d p = new Vec3d(1.0/16D, 0.0/16D, 0.5/16D);
    Vec3d s = new Vec3d(14.0/16D, 2.0/16D, 15.0/16D);

    return switch (dir) {
      case NORTH -> VoxelShapes.cuboid(
        p.x, p.y, p.z,
        p.x + s.x, p.y + s.y, p.z + s.z
      );
      case WEST, EAST -> VoxelShapes.cuboid(
        p.z, p.y, p.x,
        p.z + s.z, p.y + s.y, p.x + s.x
      );
      case SOUTH -> VoxelShapes.cuboid(
        p.x, p.y, 1.0 - (p.z + s.z),
        p.x + s.x, p.y + s.y, p.z + s.z
      );
      default -> VoxelShapes.fullCube();
    };
  }

  public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return this.getShape(state);
  }

  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return this.getShape(state);
  }

  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }
}
