package zone.oat.n3komod;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import zone.oat.n3komod.client.screen.CreditsScreen;
import zone.oat.n3komod.registry.N3KOBlocks;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOItemGroup extends OwoItemGroup {
  public N3KOItemGroup(Identifier id) {
    super(id);
  }

  @Environment(EnvType.CLIENT)
  private void openButton() {
    MinecraftClient client = MinecraftClient.getInstance();
    client.setScreen(new CreditsScreen(client.currentScreen));
  }

  @Override
  public void setup() {
    addButton(new ItemGroupButton(Icon.of(N3KOBlocks.N3KO_BLOCK), "credits", this::openButton));
    setNoScrollbar();
    setCustomTexture(new ModIdentifier("textures/gui/group.png"));
  }

  @Override
  public ItemStack createIcon() {
    return new ItemStack(N3KOBlocks.N3KO_BLOCK);
  }
}
