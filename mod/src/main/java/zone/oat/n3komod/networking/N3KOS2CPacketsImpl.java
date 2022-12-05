package zone.oat.n3komod.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import zone.oat.n3komod.content.block.ButtonBlock;
import zone.oat.n3komod.content.blockentity.PlushBlockEntity;

@Environment(EnvType.CLIENT)
public class N3KOS2CPacketsImpl {
  public static void squishPlushHandler(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
    BlockPos target = buf.readBlockPos();
    client.execute(() -> {
      BlockEntity plush = handler.getWorld().getBlockEntity(target);
      if (plush instanceof PlushBlockEntity) {
        ((PlushBlockEntity) plush).onUse();
      }
    });
  }

  public static void pressButtonHandler(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
    BlockPos target = buf.readBlockPos();
    client.execute(() -> {
      ButtonBlock.playSound(handler.getWorld(), target);
    });
  }
}
