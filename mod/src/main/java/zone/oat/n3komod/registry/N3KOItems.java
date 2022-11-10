package zone.oat.n3komod.registry;

import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.content.item.DrywallChunk;
import zone.oat.n3komod.content.item.block.BigBlockItem;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOItems implements ItemRegistryContainer {
  public static final BlockItem GIANT_COBBLESTONE = new BigBlockItem(N3KOBlocks.GIANT_COBBLESTONE, new FabricItemSettings().group(N3KOMod.ITEM_GROUP));

  public static final Item WET_DRYWALL = new Item(new FabricItemSettings().group(N3KOMod.ITEM_GROUP));
  public static final Item DRYWALL_CHUNK = new DrywallChunk(new FabricItemSettings().group(N3KOMod.ITEM_GROUP).food(
    new FoodComponent.Builder()
      .hunger(2)
      .saturationModifier(0.3f)
      .alwaysEdible()
      .build()
  ));
}
