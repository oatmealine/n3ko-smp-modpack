package zone.oat.n3komod.content.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.registry.N3KOBlockEntities;

public class ButtonBlockEntity extends BlockEntity {
    public static final float MIN_PITCH = 0.2f;
    public static final float MAX_PITCH = 5.0f;
    public static final float MIN_VOLUME = 0.0f;
    public static final float MAX_VOLUME = 2.0f;

    public String url = "";
    public String label = "";
    public float pitch = 1.0f;
    public float volume = 1.0f;

    public ButtonBlockEntity(BlockPos pos, BlockState state) {
        super(N3KOBlockEntities.BUTTON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putString("url", url);
        nbt.putString("label", label);
        nbt.putFloat("pitch", pitch);
        nbt.putFloat("volume", volume);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        url = nbt.getString("url");
        label = nbt.getString("label");
        pitch = nbt.getFloat("pitch");
        volume = nbt.getFloat("volume");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
