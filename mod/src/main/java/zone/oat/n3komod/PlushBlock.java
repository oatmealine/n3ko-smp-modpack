package zone.oat.n3komod;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import zone.oat.n3komod.client.N3KOPackets;

public class PlushBlock extends BlockWithEntity {
  public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

  public PlushBlock() {
    super(FabricBlockSettings
      .of(Material.WOOL)
      .sounds(BlockSoundGroup.WOOL)
      .nonOpaque()
      .allowsSpawning(N3KOBlocks::never)
      .solidBlock(N3KOBlocks::never)
      .suffocates(N3KOBlocks::never)
      .blockVision(N3KOBlocks::never)
    );
    this.setDefaultState(
      this.stateManager.getDefaultState().with(FACING, Direction.NORTH)
    );
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.INVISIBLE;
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new PlushBlockEntity(pos, state);
  }

  @Override
  public PistonBehavior getPistonBehavior(BlockState state) {
    return PistonBehavior.DESTROY;
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }

  @Override
  public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
    return false;
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
    Direction dir = state.get(FACING);

    Vector3d p = new Vector3d(5.0/16D, 0.0/16D, 5.25/16D);
    Vector3d s = new Vector3d(6.0/16D, 12.0/16D, 6.0/16D);

    switch(dir) {
      case NORTH:
        return VoxelShapes.cuboid(
          5/16D, 0, 5.25/16D,
          11/16D, 12/16D, 11.25/16D
        );
      case WEST:
        return VoxelShapes.cuboid(
          5.25/16D, 0, 5/16D,
          11.25/16D, 12/16D, 11/16D
        );
      case EAST:
        return VoxelShapes.cuboid(
          4.75/16D, 0, 5/16D,
          10.75/16D, 12/16D, 11/16D
        );
      case SOUTH:
        return VoxelShapes.cuboid(
          5/16D, 0, 4.75/16D,
          11/16D, 12/16D, 10.75/16D
        );
      default:
        return VoxelShapes.fullCube();
    }
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return checkType(type, N3KOBlockEntities.PLUSH_BLOCK_ENTITY, (world1, pos, state1, be) -> PlushBlockEntity.tick(world1, pos, state1, be));
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    BlockEntity be = world.getBlockEntity(pos);
    if (be instanceof PlushBlockEntity) {
      if (world.isClient()) return ActionResult.SUCCESS;

      PacketByteBuf buf = PacketByteBufs.create();
      buf.writeBlockPos(pos);
      for (ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld) world, pos)) {
        ServerPlayNetworking.send(serverPlayer, N3KOPackets.SQUISH_PLUSH, buf);
      }

      world.playSound(
        null,
        pos,
        N3KOSounds.SQUEAK_EVENT,
        SoundCategory.BLOCKS,
        0.7f,
        0.9f + world.getRandom().nextFloat() * 0.2f
      );

      return ActionResult.SUCCESS;
    }
    return ActionResult.PASS;
  }
}