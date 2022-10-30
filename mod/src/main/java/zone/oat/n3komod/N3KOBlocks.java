package zone.oat.n3komod;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.util.List;

public class N3KOBlocks {
  public static Block N3KO_BLOCK = new BlockWithTooltip(FabricBlockSettings.of(Material.METAL).strength(2.0f), List.of("block.n3ko.n3ko_block.tooltip"));

  public static void init() {
    Registry.register(Registry.BLOCK, new Identifier(N3KOMod.NAMESPACE, "n3ko_block"), N3KO_BLOCK);
  }
}
