package zone.oat.n3komod;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class N3KOBlockEntities {
  public static BlockEntityType<PlushBlockEntity> PLUSH_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(
    PlushBlockEntity::new,
    N3KOBlocks.SHE_PLUSH,
    N3KOBlocks.PLUSH_BASE
  ).build();

  public static void init() {
    Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(N3KOMod.NAMESPACE, "plush_block_entity"), PLUSH_BLOCK_ENTITY);
  }
}
