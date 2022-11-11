package zone.oat.n3komod.registry;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.content.blockentity.ButtonBlockEntity;
import zone.oat.n3komod.content.blockentity.PlushBlockEntity;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOBlockEntities implements AutoRegistryContainer<BlockEntityType<?>> {
  public static BlockEntityType<PlushBlockEntity> PLUSH_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(
    PlushBlockEntity::new,
    N3KOBlocks.SHE_PLUSH,
    N3KOBlocks.NOEL_PLUSH,
    N3KOBlocks.ZEPH_PLUSH,
    N3KOBlocks.LEZ_PLUSH,
    N3KOBlocks.JILLO_PLUSH
  ).build();

  public static BlockEntityType<ButtonBlockEntity> BUTTON_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(
    ButtonBlockEntity::new,
    N3KOBlocks.BUTTON_BLACK,
    N3KOBlocks.BUTTON_BLUE,
    N3KOBlocks.BUTTON_BROWN,
    N3KOBlocks.BUTTON_CYAN,
    N3KOBlocks.BUTTON_GREEN,
    N3KOBlocks.BUTTON_GRAY,
    N3KOBlocks.BUTTON_LIGHT_BLUE,
    N3KOBlocks.BUTTON_LIGHT_GRAY,
    N3KOBlocks.BUTTON_LIME,
    N3KOBlocks.BUTTON_MAGENTA,
    N3KOBlocks.BUTTON_ORANGE,
    N3KOBlocks.BUTTON_PINK,
    N3KOBlocks.BUTTON_PURPLE,
    N3KOBlocks.BUTTON_RED,
    N3KOBlocks.BUTTON_WHITE,
    N3KOBlocks.BUTTON_YELLOW
  ).build();

  @Override
  public Registry<BlockEntityType<?>> getRegistry() {
    return Registry.BLOCK_ENTITY_TYPE;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<BlockEntityType<?>> getTargetFieldType() {
    return (Class<BlockEntityType<?>>) (Object) BlockEntityType.class;
  }
}
