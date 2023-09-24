package zone.oat.n3komod;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.dimension.DimensionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zone.oat.n3komod.client.screen.CreditsScreen;
import zone.oat.n3komod.networking.N3KOC2SPackets;
import zone.oat.n3komod.registry.*;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOMod implements ModInitializer {
	public static String NAMESPACE = "n3ko";

	public static final Logger LOGGER = LoggerFactory.getLogger("n3ko");

	//public static final OwoItemGroup ITEM_GROUP = new N3KOItemGroup(new ModIdentifier("general"));
	public static final OwoItemGroup ITEM_GROUP = OwoItemGroup
			.builder(new ModIdentifier("general"), () -> Icon.of(N3KOBlocks.N3KO_BLOCK))
			.customTexture(new ModIdentifier("textures/gui/group.png"))
			.build();
	public static final RegistryKey<ItemGroup> ITEM_GROUP_REGISTRY_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, new ModIdentifier("general"));

	@Environment(EnvType.CLIENT)
	private void openButton() {
		MinecraftClient client = MinecraftClient.getInstance();
		client.setScreen(new CreditsScreen(client.currentScreen));
	}

	@Override
	public void onInitialize() {
		FieldRegistrationHandler.register(N3KOBlocks.class, NAMESPACE, false);
		FieldRegistrationHandler.register(N3KOItems.class, NAMESPACE, false);
		FieldRegistrationHandler.register(N3KOBlockEntities.class, NAMESPACE, false);
		FieldRegistrationHandler.register(N3KOSounds.class, NAMESPACE, false);
		N3KOCommands.init();
		N3KOC2SPackets.init();
		ITEM_GROUP.initialize();
		ITEM_GROUP.addButton(new ItemGroupButton(ITEM_GROUP, Icon.of(N3KOBlocks.N3KO_BLOCK), "credits", this::openButton));
	}
}