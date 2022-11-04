package zone.oat.n3komod.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import zone.oat.n3komod.content.block.ButtonBlock;
import zone.oat.n3komod.util.BlockWithTooltip;
import zone.oat.n3komod.content.block.PlushBlock;
import zone.oat.n3komod.util.ModIdentifier;

import java.util.List;

public class N3KOBlocks {
  private static Block register(String id, Block block) {
    return Registry.register(Registry.BLOCK, new ModIdentifier(id), block);
  }

  public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) { return false; }
  public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) { return false; }

  public static Block N3KO_BLOCK = new BlockWithTooltip(FabricBlockSettings.of(Material.METAL).strength(2.0f), List.of("block.n3ko.n3ko_block.tooltip"));

  public static Block SHE_PLUSH = new PlushBlock();
  public static Block PLUSH_BASE = new PlushBlock();

  public static Block SHE_BLOCK = new BlockWithTooltip(FabricBlockSettings.of(Material.METAL).strength(0.5f), List.of("block.n3ko.she_block.tooltip"));

  public static Block THREAD_GROUND = new Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning(N3KOBlocks::never));

  public static Block BUTTON_RED = new ButtonBlock(FabricBlockSettings.of(Material.STONE).strength(2f).noCollision());

  public static void init() {
    register("n3ko_block", N3KO_BLOCK);

    register("she_plush", SHE_PLUSH);
    register("plush_base", PLUSH_BASE);

    register("she_block", SHE_BLOCK);

    register("thread_ground", THREAD_GROUND);

    register("button_red", BUTTON_RED);
  }
}
