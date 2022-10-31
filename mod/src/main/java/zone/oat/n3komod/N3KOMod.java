package zone.oat.n3komod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class N3KOMod implements ModInitializer {
	public static String NAMESPACE = "n3ko";

	public static final Logger LOGGER = LoggerFactory.getLogger("n3ko");

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
					new Identifier(NAMESPACE, "general"),
					() -> new ItemStack(N3KOBlocks.N3KO_BLOCK));

	@Override
	public void onInitialize() {
		N3KOBlocks.init();
		N3KOItems.init();
		N3KOBlockEntities.init();
		N3KOSounds.init();
	}
}