package zone.oat.n3komod.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.content.blockentity.ButtonBlockEntity;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOC2SPackets {
    public static Identifier BUTTON_SETTINGS = new ModIdentifier("button_settings");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(BUTTON_SETTINGS, (server, player, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            String url = buf.readString();
            String label = buf.readString();
            N3KOMod.LOGGER.info("got packet: {}", url);
            server.execute(() -> {
                BlockEntity entity = MinecraftClient.getInstance().world.getBlockEntity(target);
                if (entity instanceof ButtonBlockEntity button) {
                    N3KOMod.LOGGER.info("setting url to {}", url);
                    button.setURL(url);
                    button.setLabel(label);
                    BlockState state = player.world.getBlockState(target);
                    player.world.updateListeners(target, state, state, Block.NOTIFY_ALL);
                }
            });
        });
    }
}
