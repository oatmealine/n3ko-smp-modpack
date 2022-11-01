package zone.oat.n3komod.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOItems {
  private static BlockItem createBlockItem(Block block, ItemGroup group) {
    return new BlockItem(block, new FabricItemSettings().group(group));
  }

  public static BlockItem N3KO_BLOCK = createBlockItem(N3KOBlocks.N3KO_BLOCK, N3KOMod.ITEM_GROUP);
  public static BlockItem SHE_PLUSH = createBlockItem(N3KOBlocks.SHE_PLUSH, N3KOMod.ITEM_GROUP);
  public static BlockItem PLUSH_BASE = createBlockItem(N3KOBlocks.PLUSH_BASE, N3KOMod.ITEM_GROUP);
  public static BlockItem SHE_BLOCK = createBlockItem(N3KOBlocks.SHE_BLOCK, N3KOMod.ITEM_GROUP);
  public static BlockItem THREAD_GROUND = createBlockItem(N3KOBlocks.THREAD_GROUND, N3KOMod.ITEM_GROUP);

  public static void init() {
    Registry.register(Registry.ITEM, new ModIdentifier("n3ko_block"), N3KO_BLOCK);
    Registry.register(Registry.ITEM, new ModIdentifier("she_plush"), SHE_PLUSH);
    Registry.register(Registry.ITEM, new ModIdentifier("plush_base"), PLUSH_BASE);
    Registry.register(Registry.ITEM, new ModIdentifier("she_block"), SHE_BLOCK);
    Registry.register(Registry.ITEM, new ModIdentifier("thread_ground"), THREAD_GROUND);
  }
}
