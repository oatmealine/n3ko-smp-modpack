package zone.oat.n3komod;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

import java.util.List;

public class N3KOBlocks {
  public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) { return false; }
  public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) { return false; }

  public static Block N3KO_BLOCK = new BlockWithTooltip(FabricBlockSettings.of(Material.METAL).strength(2.0f), List.of("block.n3ko.n3ko_block.tooltip"));

  public static Block SHE_PLUSH = new PlushBlock();
  public static Block PLUSH_BASE = new PlushBlock();

  public static void init() {
    Registry.register(Registry.BLOCK, new Identifier(N3KOMod.NAMESPACE, "n3ko_block"), N3KO_BLOCK);

    Registry.register(Registry.BLOCK, new Identifier(N3KOMod.NAMESPACE, "she_plush"), SHE_PLUSH);
    Registry.register(Registry.BLOCK, new Identifier(N3KOMod.NAMESPACE, "plush_base"), PLUSH_BASE);
  }
}
