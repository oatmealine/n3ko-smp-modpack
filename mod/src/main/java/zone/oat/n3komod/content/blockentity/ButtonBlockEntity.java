package zone.oat.n3komod.content.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import zone.oat.n3komod.registry.N3KOBlockEntities;

public class ButtonBlockEntity extends BlockEntity {
    private String url = "";
    private String label = "";

    public ButtonBlockEntity(BlockPos pos, BlockState state) {
        super(N3KOBlockEntities.BUTTON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putString("url", url);
        nbt.putString("label", label);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        url = nbt.getString("url");
        label = nbt.getString("label");
    }

    public void setLabel(String label) {
        this.label = label;
        this.markDirty();
    }
    public String getLabel() {
        return this.label;
    }

    public void setURL(String url) {
        this.url = url;
        this.markDirty();
    }
    public String getURL() {
        return this.url;
    }
}
