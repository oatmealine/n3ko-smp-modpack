package zone.oat.n3komod.content.block;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import zone.oat.n3komod.registry.N3KOBlockEntities;
import zone.oat.n3komod.registry.N3KOBlocks;
import zone.oat.n3komod.registry.N3KOSounds;
import zone.oat.n3komod.content.blockentity.PlushBlockEntity;
import zone.oat.n3komod.networking.N3KOS2CPackets;

public class PlushBlock extends BlockWithEntity {
  //public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
  public static final IntProperty ROTATION = Properties.ROTATION;

  public PlushBlock() {
    super(FabricBlockSettings.create()
      .sounds(BlockSoundGroup.WOOL)
      .nonOpaque()
      .allowsSpawning(N3KOBlocks::never)
      .solidBlock(N3KOBlocks::never)
      .suffocates(N3KOBlocks::never)
      .blockVision(N3KOBlocks::never)
      .pistonBehavior(PistonBehavior.DESTROY)
      .breakInstantly()
    );
    this.setDefaultState(
      this.stateManager.getDefaultState().with(ROTATION, 0)
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
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(ROTATION);
  }

  @Override
  public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
    return false;
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return this.getDefaultState().with(ROTATION, MathHelper.floor((double) ((180.0F + ctx.getPlayerYaw()) * 16.0F / 360.0F) + 0.5) & 15);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
    // grab from blockbench
    Vec3d p = new Vec3d(5.0/16D, 0.0/16D, 5.0/16D);
    Vec3d s = new Vec3d(6.0/16D, 12.0/16D, 6.0/16D);

    Vec3d min = p;
    Vec3d max = new Vec3d(p.x + s.x, p.y + s.y, p.z + s.z);

    return VoxelShapes.cuboid(
      min.x, min.y, min.z,
      max.x, max.y, max.z
    );
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
    return checkType(type, N3KOBlockEntities.PLUSH_BLOCK_ENTITY, PlushBlockEntity::tick);
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
    BlockEntity be = world.getBlockEntity(pos);
    if (be instanceof PlushBlockEntity) {
      if (world.isClient()) return ActionResult.SUCCESS;

      if (!player.getWorld().isClient) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        for (ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld) world, pos)) {
          ServerPlayNetworking.send(serverPlayer, N3KOS2CPackets.SQUISH_PLUSH, buf);
        }
      }

      world.playSound(
        null,
        pos,
        N3KOSounds.SQUEAK,
        SoundCategory.BLOCKS,
        0.7f,
        0.9f + world.getRandom().nextFloat() * 0.2f
      );

      return ActionResult.SUCCESS;
    }
    return ActionResult.PASS;
  }
}