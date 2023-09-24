package zone.oat.n3komod.registry;

import io.wispforest.owo.registration.annotations.AssignedName;
import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.content.item.DrywallChunk;
import zone.oat.n3komod.util.ModIdentifier;

import java.lang.reflect.Field;

public class N3KOItems implements ItemRegistryContainer {
  public static final Item WET_DRYWALL = new Item(new FabricItemSettings());
  public static final Item DRYWALL_CHUNK = new DrywallChunk(new FabricItemSettings().food(
    new FoodComponent.Builder()
      .hunger(2)
      .saturationModifier(0.3f)
      .alwaysEdible()
      .build()
  ));

  @Override
  public void postProcessField(String namespace, Item value, String identifier, Field field) {
    ItemGroupEvents.modifyEntriesEvent(N3KOMod.ITEM_GROUP_REGISTRY_KEY).register(content -> {
      content.add(value);
    });
  }
}
