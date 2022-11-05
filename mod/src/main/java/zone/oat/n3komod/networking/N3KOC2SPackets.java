package zone.oat.n3komod.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.content.blockentity.ButtonBlockEntity;
import zone.oat.n3komod.util.ModIdentifier;

import static zone.oat.n3komod.content.blockentity.ButtonBlockEntity.*;

public class N3KOC2SPackets {
    public static Identifier BUTTON_SETTINGS = new ModIdentifier("button_settings");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(BUTTON_SETTINGS, (server, player, handler, buf, responseSender) -> {
            BlockPos target = buf.readBlockPos();
            String url = buf.readString();
            String label = buf.readString();
            float pitch = buf.readFloat();
            float volume = buf.readFloat();
            server.execute(() -> {
                BlockEntity entity = player.world.getBlockEntity(target);
                if (entity instanceof ButtonBlockEntity button) {
                    button.url = url;
                    button.label = label;
                    button.pitch = MathHelper.clamp(pitch, MIN_PITCH, MAX_PITCH);
                    button.volume = MathHelper.clamp(volume, MIN_VOLUME, MAX_VOLUME);
                    button.markDirty();
                    BlockState state = player.world.getBlockState(target);
                    player.world.updateListeners(target, state, state, Block.NOTIFY_ALL);
                }
            });
        });
    }
}
