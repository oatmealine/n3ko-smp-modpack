package zone.oat.n3komod.registry;

import io.wispforest.owo.registration.reflect.BlockRegistryContainer;
import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.content.block.ButtonBlock;
import zone.oat.n3komod.content.block.GiantsBlock;
import zone.oat.n3komod.content.block.PadBlock;
import zone.oat.n3komod.content.block.PlushBlock;
import zone.oat.n3komod.util.ModIdentifier;

import java.util.List;

public class N3KOBlocks implements BlockRegistryContainer {
  public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityType<?> entityType) { return false; }
  public static boolean never(BlockState blockState, BlockView blockView, BlockPos blockPos) { return false; }

  public static final Block N3KO_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(2.0f));

  public static final Block SHE_PLUSH = new PlushBlock();
  public static final Block NOEL_PLUSH = new PlushBlock();
  public static final Block ZEPH_PLUSH = new PlushBlock();
  public static final Block LEZ_PLUSH = new PlushBlock();
  public static final Block JILLO_PLUSH = new PlushBlock();
  public static final Block ULLU_PLUSH = new PlushBlock();
  public static final Block JAM_PLUSH = new PlushBlock();

  public static final Block SHE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(0.5f));

  public static final Block THREAD_GROUND = new Block(FabricBlockSettings.of(Material.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning(N3KOBlocks::never));

  private static Block createButton(MapColor color) {
    return new ButtonBlock(FabricBlockSettings.of(Material.STONE).strength(0.2f).mapColor(color));
  }

  public static final Block BUTTON_BLACK = createButton(MapColor.BLACK);
  public static final Block BUTTON_BLUE = createButton(MapColor.BLUE);
  public static final Block BUTTON_BROWN = createButton(MapColor.BROWN);
  public static final Block BUTTON_CYAN = createButton(MapColor.CYAN);
  public static final Block BUTTON_GREEN = createButton(MapColor.GREEN);
  public static final Block BUTTON_GRAY = createButton(MapColor.GRAY);
  public static final Block BUTTON_LIGHT_BLUE = createButton(MapColor.LIGHT_BLUE);
  public static final Block BUTTON_LIGHT_GRAY = createButton(MapColor.LIGHT_GRAY);
  public static final Block BUTTON_LIME = createButton(MapColor.LIME);
  public static final Block BUTTON_MAGENTA = createButton(MapColor.MAGENTA);
  public static final Block BUTTON_ORANGE = createButton(MapColor.ORANGE);
  public static final Block BUTTON_PINK = createButton(MapColor.PINK);
  public static final Block BUTTON_PURPLE = createButton(MapColor.PURPLE);
  public static final Block BUTTON_RED = createButton(MapColor.RED);
  public static final Block BUTTON_WHITE = createButton(MapColor.WHITE);
  public static final Block BUTTON_YELLOW = createButton(MapColor.YELLOW);
  public static final Block[] BUTTONS = {
    BUTTON_BLACK, BUTTON_BLUE, BUTTON_BROWN, BUTTON_CYAN, BUTTON_GREEN, BUTTON_GRAY, BUTTON_LIGHT_BLUE, BUTTON_LIGHT_GRAY, BUTTON_LIME, BUTTON_MAGENTA, BUTTON_ORANGE, BUTTON_PINK, BUTTON_PURPLE, BUTTON_RED, BUTTON_WHITE, BUTTON_YELLOW
  };

  public static final Block DRYWALL_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE).strength(0.4f, 1.8f));
  public static final Block SMOOTH_DRYWALL_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE).strength(0.4f, 1.8f));

  public static final Block PAD = new PadBlock(FabricBlockSettings.of(Material.METAL).strength(0.8f, 1.8f));

  @BlockRegistryContainer.NoBlockItem
  public static final GiantsBlock GIANT_COBBLESTONE = new GiantsBlock(FabricBlockSettings.copyOf(Blocks.COBBLESTONE));

  @Override
  public BlockItem createBlockItem(Block block, String identifier) {
    return new BlockItem(block, new FabricItemSettings().group(N3KOMod.ITEM_GROUP));
  }
}
