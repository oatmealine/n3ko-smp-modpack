package zone.oat.n3komod.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import zone.oat.n3komod.N3KOBlockEntities;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.PlushBlockEntity;

public class N3KOPackets {
  public static Identifier SQUISH_PLUSH = new Identifier(N3KOMod.NAMESPACE, "squish_plush");

  public static void init() {
    ClientPlayNetworking.registerGlobalReceiver(SQUISH_PLUSH, (client, handler, buf, responseSender) -> {
      BlockPos target = buf.readBlockPos();
      client.execute(() -> {
        // Everything in this lambda is run on the render thread
        //ClientBlockHighlighting.highlightBlock(client, target);
        N3KOMod.LOGGER.info("hewwo");

        BlockEntity plush = MinecraftClient.getInstance().world.getBlockEntity(target);
        if (plush instanceof PlushBlockEntity) {
          ((PlushBlockEntity) plush).onUse();
        }
      });
    });
  }
}
