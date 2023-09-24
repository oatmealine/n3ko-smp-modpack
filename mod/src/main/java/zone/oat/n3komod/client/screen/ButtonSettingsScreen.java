package zone.oat.n3komod.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.sound.Source;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.client.sound.AudioBuffer;
import zone.oat.n3komod.content.blockentity.ButtonBlockEntity;
import zone.oat.n3komod.networking.N3KOC2SPackets;

import java.util.regex.Pattern;

import static zone.oat.n3komod.content.blockentity.ButtonBlockEntity.*;

@Environment(EnvType.CLIENT)
public class ButtonSettingsScreen extends Screen {

    class SettingsSlider extends SliderWidget {
        private final float min;
        private final float max;
        private final String translationKey;

        public SettingsSlider(int x, int y, int width, float value, float min, float max, String translationKey) {
            super(x, y, width, 20, Text.empty(), 0.0);
            this.min = min;
            this.max = max;
            this.value = ((MathHelper.clamp(value, min, max) - min) / (max - min));
            this.translationKey = translationKey;
            this.updateMessage();
        }

        public float getValue() {
            return (float)MathHelper.lerp(MathHelper.clamp(this.value, 0.0, 1.0), this.min, this.max);
        }

        public void setValue(float value) {
            this.value = ((MathHelper.clamp(value, min, max) - min) / (max - min));
            this.updateMessage();
        }

        @Override
        public void applyValue() {

        }

        @Override
        protected void updateMessage() {
            this.setMessage(Text.translatable(translationKey, Math.round(this.getValue() * 100f) / 100f));
        }

        @Override
        public void onClick(double mouseX, double mouseY) {
        }

        @Override
        public void onRelease(double mouseX, double mouseY) {
        }
    }

    private final BlockPos pos;
    @Nullable
    private final ButtonBlockEntity blockEntity;
    private TextFieldWidget urlField;
    private TextFieldWidget labelField;
    private SettingsSlider pitch;
    private SettingsSlider volume;
    private ButtonWidget confirmButton;
    private ButtonWidget previewButton;
    @Nullable
    private Source previewSource;
    @Nullable
    private AudioBuffer previewBuffer;
    private String previewBufferURL;

    private static final Text PREVIEW_TEXT = Text.translatable("gui.n3ko.text.preview");
    private static final Text PREVIEW_STOP_TEXT = Text.translatable("gui.n3ko.text.preview_stop");
    private static final Text PREVIEW_ERROR_TEXT = Text.translatable("gui.n3ko.text.preview_error");
    private static final Text PREVIEW_WAIT_TEXT = Text.translatable("gui.n3ko.text.preview_wait");

    private static final Pattern URL_REGEX = Pattern.compile("^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]\\.ogg([?#][-a-zA-Z0-9+&@#/%=~_|]*)?$");

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

    private void sendPacket() {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(this.pos);
        buf.writeString(this.urlField.getText());
        buf.writeString(this.labelField.getText());
        buf.writeFloat(this.pitch.getValue());
        buf.writeFloat(this.volume.getValue());
        ClientPlayNetworking.send(N3KOC2SPackets.BUTTON_SETTINGS, buf);
    }

    @Override
    protected void init() {
        int x = this.width / 2;
        int y = this.height / 2 - 50;

        // this is undoubtedly a bad idea that Will Not work with translations.
        // alas, i, Jill "oatmealine" Monoids, am not a maker of good decisions or ideas.
        // so i will proceed to do it anyways in hopes that the many obvious issues this
        // has are never run into, and i get away with the crimes i've committed.
        int urlOffset = -26;

        urlField = addDrawableChild(new TextFieldWidget(textRenderer, x + urlOffset - 100, y, 200, 20, Text.of("https://oat.zone/f/hi.ogg")));
        urlField.setMaxLength(128);
        if (blockEntity != null && blockEntity.url != null) this.urlField.setText(blockEntity.url);
        urlField.setFocused(true);
        urlField.setChangedListener(url -> {
            updateURLField();
        });

        previewButton = addDrawableChild(ButtonWidget.builder(PREVIEW_TEXT, (button) -> {
            if (previewSource != null && previewSource.isPlaying()) {
                previewSource.stop();
                previewSource.close();
            } else {
                String url = this.urlField.getText();
                if (!URL_REGEX.matcher(url).matches()) return;
                if (!url.equals(previewBufferURL) || this.previewBuffer == null) {
                    previewBufferURL = url;
                    previewBuffer = new AudioBuffer(url);
                    previewButton.setMessage(PREVIEW_WAIT_TEXT);
                }

                previewBuffer.playOnceLoaded((source) -> {
                    if (source == null) {
                        previewBuffer.close();
                        previewBuffer = null;
                        previewButton.setMessage(PREVIEW_ERROR_TEXT);
                    } else {
                        previewSource = source;
                        PlayerEntity player = MinecraftClient.getInstance().player;
                        previewSource = previewBuffer.play(player.getPos());
                        previewSource.setPitch(pitch.getValue());
                        previewSource.setVolume(volume.getValue());
                        previewButton.setMessage(PREVIEW_STOP_TEXT);
                    }
                });
            }
        })
                .dimensions(x + 105 + urlOffset, y, 50, 20)
                .build()
        );

        labelField = addDrawableChild(new TextFieldWidget(textRenderer, x - 55, y + 38, 110, 20, Text.of("nut")));
        labelField.setMaxLength(16);
        if (blockEntity != null && blockEntity.label != null) this.labelField.setText(blockEntity.label);

        confirmButton = addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), (button) -> {
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
                    sendPacket();
                }
                this.close();
            }).dimensions(x - 50, y + 86, 100, 20).build()
        );

        int slidersOffset = -21;

        pitch = addDrawableChild(new SettingsSlider(x - 91 + slidersOffset, y + 64, 90, 1f, MIN_PITCH, MAX_PITCH, "gui.n3ko.text.pitch"));
        if (blockEntity != null) this.pitch.setValue(blockEntity.pitch);
        volume = addDrawableChild(new SettingsSlider(x + 1 + slidersOffset, y + 64, 90, 1f, MIN_VOLUME, MAX_VOLUME, "gui.n3ko.text.volume"));
        if (blockEntity != null) this.volume.setValue(blockEntity.volume);
        addDrawableChild(ButtonWidget.builder(Text.translatable("controls.reset"), (button) -> {
            volume.setValue(1.0f);
            pitch.setValue(1.0f);
        })
            .dimensions(x + 93 + slidersOffset, y + 64, 40, 20)
            .build()
        );

        updateURLField();
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        int x = this.width / 2;
        int y = this.height / 2 - 50;
        ctx.drawCenteredTextWithShadow(textRenderer, Text.translatable("gui.n3ko.text.button_settings").asOrderedText(), x, y - 24, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.translatable("gui.n3ko.text.format").asOrderedText(), x, y - 12, 0xFF8888);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.translatable("gui.n3ko.text.button_name").asOrderedText(), x, y + 26, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.translatable("gui.n3ko.text.notes.0").asOrderedText(), x, y + 110, 0x666666);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.translatable("gui.n3ko.text.notes.1").asOrderedText(), x, y + 122, 0x666666);
    }

    @Override
    public void tick() {
        if (previewSource != null) {
            previewSource.tick();
            if (!previewSource.isPlaying()) {
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
