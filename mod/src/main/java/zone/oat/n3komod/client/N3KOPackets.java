package zone.oat.n3komod.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import zone.oat.n3komod.content.blockentity.PlushBlockEntity;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOPackets {
  public static Identifier SQUISH_PLUSH = new ModIdentifier("squish_plush");

  public static void init() {
    ClientPlayNetworking.registerGlobalReceiver(SQUISH_PLUSH, (client, handler, buf, responseSender) -> {
      BlockPos target = buf.readBlockPos();
      client.execute(() -> {
        BlockEntity plush = MinecraftClient.getInstance().world.getBlockEntity(target);
        if (plush instanceof PlushBlockEntity) {
          ((PlushBlockEntity) plush).onUse();
        }
      });
    });
  }
}
