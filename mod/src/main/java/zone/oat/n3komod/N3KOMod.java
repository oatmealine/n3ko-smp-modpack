package zone.oat.n3komod;

import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.dimension.DimensionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zone.oat.n3komod.networking.N3KOC2SPackets;
import zone.oat.n3komod.registry.*;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOMod implements ModInitializer {
	public static String NAMESPACE = "n3ko";

	public static final Logger LOGGER = LoggerFactory.getLogger("n3ko");

	public static final OwoItemGroup ITEM_GROUP = new N3KOItemGroup(new ModIdentifier("general"));

	@Override
	public void onInitialize() {
		FieldRegistrationHandler.register(N3KOBlocks.class, NAMESPACE, false);
		FieldRegistrationHandler.register(N3KOItems.class, NAMESPACE, false);
		FieldRegistrationHandler.register(N3KOBlockEntities.class, NAMESPACE, false);
		FieldRegistrationHandler.register(N3KOSounds.class, NAMESPACE, false);
		N3KOCommands.init();
		N3KOC2SPackets.init();
		ITEM_GROUP.initialize();
	}
}