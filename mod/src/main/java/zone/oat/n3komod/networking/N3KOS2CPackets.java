package zone.oat.n3komod.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOS2CPackets {
  public static Identifier SQUISH_PLUSH = new ModIdentifier("squish_plush");
  public static Identifier PRESS_BUTTON = new ModIdentifier("press_button");

  @Environment(EnvType.CLIENT)
  public static void init() {
    ClientPlayNetworking.registerGlobalReceiver(SQUISH_PLUSH, N3KOS2CPacketsImpl::squishPlushHandler);
    ClientPlayNetworking.registerGlobalReceiver(PRESS_BUTTON, N3KOS2CPacketsImpl::pressButtonHandler);
  }
}
