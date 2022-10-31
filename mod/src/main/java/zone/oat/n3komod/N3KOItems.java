package zone.oat.n3komod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class N3KOItems {
  private static BlockItem createBlockItem(Block block, ItemGroup group) {
    return new BlockItem(block, new FabricItemSettings().group(group));
  }

  public static BlockItem N3KO_BLOCK = createBlockItem(N3KOBlocks.N3KO_BLOCK, N3KOMod.ITEM_GROUP);
  public static BlockItem SHE_PLUSH = createBlockItem(N3KOBlocks.SHE_PLUSH, N3KOMod.ITEM_GROUP);
  public static BlockItem PLUSH_BASE = createBlockItem(N3KOBlocks.PLUSH_BASE, N3KOMod.ITEM_GROUP);

  public static void init() {
    Registry.register(Registry.ITEM, new Identifier(N3KOMod.NAMESPACE, "n3ko_block"), N3KO_BLOCK);
    Registry.register(Registry.ITEM, new Identifier(N3KOMod.NAMESPACE, "she_plush"), SHE_PLUSH);
    Registry.register(Registry.ITEM, new Identifier(N3KOMod.NAMESPACE, "plush_base"), PLUSH_BASE);
  }
}
