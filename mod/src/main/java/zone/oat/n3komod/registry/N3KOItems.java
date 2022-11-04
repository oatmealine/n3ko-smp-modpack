package zone.oat.n3komod.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOItems {
  private static BlockItem createBlockItem(Block block, ItemGroup group) {
    return new BlockItem(block, new FabricItemSettings().group(group));
  }

  private static Item register(String id, Item item) {
    return Registry.register(Registry.ITEM, new ModIdentifier(id), item);
  }

  public static BlockItem N3KO_BLOCK = createBlockItem(N3KOBlocks.N3KO_BLOCK, N3KOMod.ITEM_GROUP);
  public static BlockItem SHE_PLUSH = createBlockItem(N3KOBlocks.SHE_PLUSH, N3KOMod.ITEM_GROUP);
  public static BlockItem PLUSH_BASE = createBlockItem(N3KOBlocks.PLUSH_BASE, N3KOMod.ITEM_GROUP);
  public static BlockItem SHE_BLOCK = createBlockItem(N3KOBlocks.SHE_BLOCK, N3KOMod.ITEM_GROUP);
  public static BlockItem THREAD_GROUND = createBlockItem(N3KOBlocks.THREAD_GROUND, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_RED = createBlockItem(N3KOBlocks.BUTTON_RED, N3KOMod.ITEM_GROUP);

  public static void init() {
    register("n3ko_block", N3KO_BLOCK);
    register("she_plush", SHE_PLUSH);
    register("plush_base", PLUSH_BASE);
    register("she_block", SHE_BLOCK);
    register("thread_ground", THREAD_GROUND);
    register("button_red", BUTTON_RED);
  }
}
