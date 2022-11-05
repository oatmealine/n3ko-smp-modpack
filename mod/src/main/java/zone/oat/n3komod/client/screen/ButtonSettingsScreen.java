package zone.oat.n3komod.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.client.sound.AudioBuffer;
import zone.oat.n3komod.client.sound.AudioSource;
import zone.oat.n3komod.content.blockentity.ButtonBlockEntity;
import zone.oat.n3komod.networking.N3KOC2SPackets;

import java.util.regex.Pattern;

public class ButtonSettingsScreen extends Screen {
    private final BlockPos pos;
    @Nullable
    private final ButtonBlockEntity blockEntity;
    private TextFieldWidget urlField;
    private TextFieldWidget labelField;
    private ButtonWidget confirmButton;
    private ButtonWidget previewButton;
    @Nullable
    private AudioSource previewSource;
    @Nullable
    private AudioBuffer previewBuffer;
    private String previewBufferURL;

    private static final Text PREVIEW_TEXT = new TranslatableText("gui.n3ko.text.preview");
    private static final Text PREVIEW_STOP_TEXT = new TranslatableText("gui.n3ko.text.preview_stop");
    private static final Text PREVIEW_ERROR_TEXT = new TranslatableText("gui.n3ko.text.preview_error");

    private static final Pattern URL_REGEX = Pattern.compile("^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\.ogg$");

    public ButtonSettingsScreen(BlockPos pos) {
        super(Text.of(""));
        this.pos = pos;

        World world = MinecraftClient.getInstance().world;
        if (world == null || !world.isClient) throw new RuntimeException("why is this running on the server???");
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ButtonBlockEntity) {
            blockEntity = (ButtonBlockEntity) be;
        } else {
            blockEntity = null;
        }
    }

    public static void open(BlockPos pos) {
        MinecraftClient.getInstance().setScreen(new ButtonSettingsScreen(pos));
    }

    private boolean isURLValid() {
        String url = this.urlField.getText();
        return URL_REGEX.matcher(url).matches();
    }

    private void updateURLField() {
        if (isURLValid()) {
            this.confirmButton.active = true;
            this.previewButton.active = true;
            this.urlField.setEditableColor(0xFFFFFF);
        } else {
            this.confirmButton.active = false;
            this.previewButton.active = false;
            this.urlField.setEditableColor(0xFF0000);
        }
    }

    @Override
    protected void init() {
        int x = this.width / 2;
        int y = this.height / 2 - 40;

        // this is undoubtedly a bad idea that Will Not work with translations.
        // alas, i, Jill "oatmealine" Monoids, am not a maker of good decisions or ideas.
        // so i will proceed to do it anyways in hopes that the many obvious issues this
        // has are never run into, and i get away with the crimes i've committed.
        int xoffset = -26;

        urlField = addDrawableChild(new TextFieldWidget(textRenderer, x + xoffset - 100, y, 200, 20, Text.of("https://oat.zone/f/hi.ogg")));
        urlField.setMaxLength(128);
        if (blockEntity != null && blockEntity.getURL() != null) this.urlField.setText(blockEntity.getURL());
        urlField.setTextFieldFocused(true);
        urlField.setChangedListener(url -> {
            updateURLField();
        });

        previewButton = addDrawableChild(new ButtonWidget(x + 105 + xoffset, y, 50, 20, PREVIEW_TEXT, (button) -> {
            if (previewSource != null && previewSource.isPlaying()) {
                previewSource.stop();
            } else {
                String url = this.urlField.getText();
                if (!URL_REGEX.matcher(url).matches()) return;
                if (!url.equals(previewBufferURL)) {
                    previewBuffer = new AudioBuffer(url);
                    previewBufferURL = url;
                }
                if (previewBuffer.getHandle() == 0) {
                    previewBuffer.close();
                    previewBuffer = null;
                    previewButton.setMessage(PREVIEW_ERROR_TEXT);
                } else {
                    previewSource = previewBuffer.play();
                    previewButton.setMessage(PREVIEW_STOP_TEXT);
                }
            }
        }));

        labelField = addDrawableChild(new TextFieldWidget(textRenderer, x - 55, y + 38, 110, 20, Text.of("nut")));
        labelField.setMaxLength(16);
        if (blockEntity != null && blockEntity.getLabel() != null) this.labelField.setText(blockEntity.getLabel());

        confirmButton = addDrawableChild(new ButtonWidget(x - 50, y + 64, 100, 20, new TranslatableText("gui.done"), (button) -> {
            // cleanup
            if (previewSource != null && previewSource.isPlaying()) {
                previewSource.stop();
                previewSource = null;
            }
            if (previewBuffer != null) {
                previewBuffer.close();
                previewBuffer = null;
            }

            if (isURLValid()) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(this.pos);
                buf.writeString(this.urlField.getText());
                buf.writeString(this.labelField.getText());
                ClientPlayNetworking.send(N3KOC2SPackets.BUTTON_SETTINGS, buf);
            }
            this.close();
        }));

        updateURLField();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        int x = this.width / 2;
        int y = this.height / 2 - 40;
        drawCenteredTextWithShadow(matrices, textRenderer, new TranslatableText("gui.n3ko.text.button_settings").asOrderedText(), x, y - 24, 0xFFFFFF);
        drawCenteredTextWithShadow(matrices, textRenderer, new TranslatableText("gui.n3ko.text.format").asOrderedText(), x, y - 12, 0xFF8888);
        drawCenteredTextWithShadow(matrices, textRenderer, new TranslatableText("gui.n3ko.text.button_name").asOrderedText(), x, y + 26, 0xFFFFFF);
        drawCenteredTextWithShadow(matrices, textRenderer, new TranslatableText("gui.n3ko.text.notes.0").asOrderedText(), x, y + 90, 0x666666);
        drawCenteredTextWithShadow(matrices, textRenderer, new TranslatableText("gui.n3ko.text.notes.1").asOrderedText(), x, y + 102, 0x666666);
    }

    @Override
    public void tick() {
        if (previewSource != null) {
            boolean active = previewSource.tick();
            if (!active) {
                previewSource = null;
                previewButton.setMessage(PREVIEW_TEXT);
            }
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
