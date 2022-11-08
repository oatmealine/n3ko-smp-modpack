package zone.oat.n3komod.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
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
  public static Block NOEL_PLUSH = new PlushBlock();

  public static Block SHE_BLOCK = new BlockWithTooltip(FabricBlockSettings.of(Material.METAL).strength(0.5f), List.of("block.n3ko.she_block.tooltip"));

  public static Block THREAD_GROUND = new Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning(N3KOBlocks::never));

  private static Block createButton(MapColor color) {
    return new ButtonBlock(FabricBlockSettings.of(Material.STONE).strength(2f).mapColor(color));
  }

  public static Block BUTTON_BLACK = createButton(MapColor.BLACK);
  public static Block BUTTON_BLUE = createButton(MapColor.BLUE);
  public static Block BUTTON_BROWN = createButton(MapColor.BROWN);
  public static Block BUTTON_CYAN = createButton(MapColor.CYAN);
  public static Block BUTTON_GREEN = createButton(MapColor.GREEN);
  public static Block BUTTON_GRAY = createButton(MapColor.GRAY);
  public static Block BUTTON_LIGHT_BLUE = createButton(MapColor.LIGHT_BLUE);
  public static Block BUTTON_LIGHT_GRAY = createButton(MapColor.LIGHT_GRAY);
  public static Block BUTTON_LIME = createButton(MapColor.LIME);
  public static Block BUTTON_MAGENTA = createButton(MapColor.MAGENTA);
  public static Block BUTTON_ORANGE = createButton(MapColor.ORANGE);
  public static Block BUTTON_PINK = createButton(MapColor.PINK);
  public static Block BUTTON_PURPLE = createButton(MapColor.PURPLE);
  public static Block BUTTON_RED = createButton(MapColor.RED);
  public static Block BUTTON_WHITE = createButton(MapColor.WHITE);
  public static Block BUTTON_YELLOW = createButton(MapColor.YELLOW);
  public static Block[] BUTTONS = {
    BUTTON_BLACK, BUTTON_BLUE, BUTTON_BROWN, BUTTON_CYAN, BUTTON_GREEN, BUTTON_GRAY, BUTTON_LIGHT_BLUE, BUTTON_LIGHT_GRAY, BUTTON_LIME, BUTTON_MAGENTA, BUTTON_ORANGE, BUTTON_PINK, BUTTON_PURPLE, BUTTON_RED, BUTTON_WHITE, BUTTON_YELLOW
  };

  public static Block DRYWALL_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).strength(0.4f, 1.8f));

  public static void init() {
    register("n3ko_block", N3KO_BLOCK);

    register("she_plush", SHE_PLUSH);
    register("noel_plush", NOEL_PLUSH);

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
  }
}
