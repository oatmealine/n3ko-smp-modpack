package zone.oat.n3komod.content.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

// largely copypasted from AbstractButtonBlock
public class ButtonBlock extends Block {
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<WallMountLocation> FACE = Properties.WALL_MOUNT_LOCATION;

    public static final int PRESS_TICKS = 60;

    public ButtonBlock(Settings settings) {
        super(settings);

        this.setDefaultState(
            this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, Boolean.FALSE).with(FACE, WallMountLocation.WALL)
        );
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(POWERED)) {
            return ActionResult.CONSUME;
        } else {
            this.powerOn(state, world, pos);
            this.playClickSound(player, world, pos, true);
            world.emitGameEvent(player, GameEvent.BLOCK_PRESS, pos);
            return ActionResult.success(world.isClient);
        }
    }

    public void powerOn(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_ALL);
        this.updateNeighbors(state, world, pos);
        world.createAndScheduleBlockTick(pos, this, PRESS_TICKS);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            if (state.get(POWERED)) {
                this.updateNeighbors(state, world, pos);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) && getDirection(state) == direction ? 15 : 0;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    private void updateNeighbors(BlockState state, World world, BlockPos pos) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(getDirection(state).getOpposite()), this);
    }

    protected void playClickSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, boolean powered) {
        //world.playSound(powered ? player : null, pos, this.getClickSound(powered), SoundCategory.BLOCKS, 0.3F, powered ? 0.6F : 0.5F);
    }

    /*
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }
    */

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAt(world, pos, getDirection(state).getOpposite());
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        for(Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState;
            if (direction.getAxis() == Direction.Axis.Y) {
                blockState = this.getDefaultState()
                        .with(FACE, direction == Direction.UP ? WallMountLocation.CEILING : WallMountLocation.FLOOR)
                        .with(FACING, ctx.getPlayerFacing());
            } else {
                blockState = this.getDefaultState().with(FACE, WallMountLocation.WALL).with(FACING, direction.getOpposite());
            }

            if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                return blockState;
            }
        }

        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        return getDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos)
                ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED)) {
            world.setBlockState(pos, state.with(POWERED, Boolean.FALSE), Block.NOTIFY_ALL);
            this.updateNeighbors(state, world, pos);
            this.playClickSound(null, world, pos, false);
            world.emitGameEvent(GameEvent.BLOCK_UNPRESS, pos);
        }
    }

    protected static Direction getDirection(BlockState state) {
        switch(state.get(FACE)) {
            case CEILING:
                return Direction.DOWN;
            case FLOOR:
                return Direction.UP;
            default:
                return state.get(FACING);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, FACE);
    }
}
