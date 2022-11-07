package zone.oat.n3komod.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.content.item.DrywallChunk;
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
  public static BlockItem BUTTON_BLACK = createBlockItem(N3KOBlocks.BUTTON_BLACK, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_BLUE = createBlockItem(N3KOBlocks.BUTTON_BLUE, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_BROWN = createBlockItem(N3KOBlocks.BUTTON_BROWN, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_CYAN = createBlockItem(N3KOBlocks.BUTTON_CYAN, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_GREEN = createBlockItem(N3KOBlocks.BUTTON_GREEN, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_GRAY = createBlockItem(N3KOBlocks.BUTTON_GRAY, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_LIGHT_BLUE = createBlockItem(N3KOBlocks.BUTTON_LIGHT_BLUE, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_LIGHT_GRAY = createBlockItem(N3KOBlocks.BUTTON_LIGHT_GRAY, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_LIME = createBlockItem(N3KOBlocks.BUTTON_LIME, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_MAGENTA = createBlockItem(N3KOBlocks.BUTTON_MAGENTA, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_ORANGE = createBlockItem(N3KOBlocks.BUTTON_ORANGE, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_PINK = createBlockItem(N3KOBlocks.BUTTON_PINK, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_PURPLE = createBlockItem(N3KOBlocks.BUTTON_PURPLE, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_RED = createBlockItem(N3KOBlocks.BUTTON_RED, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_WHITE = createBlockItem(N3KOBlocks.BUTTON_WHITE, N3KOMod.ITEM_GROUP);
  public static BlockItem BUTTON_YELLOW = createBlockItem(N3KOBlocks.BUTTON_YELLOW, N3KOMod.ITEM_GROUP);
  public static BlockItem DRYWALL_BLOCK = createBlockItem(N3KOBlocks.DRYWALL_BLOCK, N3KOMod.ITEM_GROUP);

  public static Item WET_DRYWALL = new Item(new FabricItemSettings().group(N3KOMod.ITEM_GROUP));
  public static Item DRYWALL_CHUNK = new DrywallChunk(new FabricItemSettings().group(N3KOMod.ITEM_GROUP).food(
    new FoodComponent.Builder()
      .hunger(2)
      .saturationModifier(0.3f)
      .alwaysEdible()
      .build()
  ));

  public static void init() {
    register("n3ko_block", N3KO_BLOCK);
    register("she_plush", SHE_PLUSH);
    register("plush_base", PLUSH_BASE);
    register("she_block", SHE_BLOCK);
    register("thread_ground", THREAD_GROUND);
    register("button_black", BUTTON_BLACK);
    register("button_blue", BUTTON_BLUE);
    register("button_brown", BUTTON_BROWN);
    register("button_cyan", BUTTON_CYAN);
    register("button_green", BUTTON_GREEN);
    register("button_gray", BUTTON_GRAY);
    register("button_light_blue", BUTTON_LIGHT_BLUE);
    register("button_light_gray", BUTTON_LIGHT_GRAY);
    register("button_lime", BUTTON_LIME);
    register("button_magenta", BUTTON_MAGENTA);
    register("button_orange", BUTTON_ORANGE);
    register("button_pink", BUTTON_PINK);
    register("button_purple", BUTTON_PURPLE);
    register("button_red", BUTTON_RED);
    register("button_white", BUTTON_WHITE);
    register("button_yellow", BUTTON_YELLOW);
    register("drywall_block", DRYWALL_BLOCK);

    register("wet_drywall", WET_DRYWALL);
    register("drywall_chunk", DRYWALL_CHUNK);
  }
}
