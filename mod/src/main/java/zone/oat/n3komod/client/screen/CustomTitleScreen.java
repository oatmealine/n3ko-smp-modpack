package zone.oat.n3komod.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.terraformersmc.modmenu.gui.ModsScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import zone.oat.n3komod.util.ModIdentifier;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class CustomTitleScreen extends Screen {
  public static final Text VERSION_TEXT = new TranslatableText("menu.n3ko.version", FabricLoader.getInstance().getModContainer("n3komod").get().getMetadata().getVersion().getFriendlyString());
  private static final Identifier ACCESSIBILITY_ICON_TEXTURE = new Identifier("textures/gui/accessibility.png");
  private final boolean isMinceraft;
  @Nullable
  private String splashText;
  private static final Identifier MINECRAFT_TITLE_TEXTURE = new Identifier("textures/gui/title/minecraft.png");
  private static final Identifier EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");

  private PressableTextWidget version;

  private class SmallPressableTextWidget extends PressableTextWidget {
    public SmallPressableTextWidget(int x, int y, int width, int height, Text text, PressAction onPress, TextRenderer textRenderer) {
      super(x, y, width, height, text, onPress, textRenderer);
    }

    public boolean shouldDownscale() {
      return this.width > CustomTitleScreen.this.width * 0.6f;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      matrices.push();
      if (shouldDownscale()) {
        matrices.translate(CustomTitleScreen.this.width, 0d, 0d);
        matrices.scale(0.5f, 0.5f, 1.0f);
        matrices.translate(-CustomTitleScreen.this.width, 0d, 0d);
      }

      int padding = 3;

      RenderSystem.disableBlend();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.fill(matrices, version.x - padding, version.y - padding, version.x + version.getWidth() + padding * 2, version.y + version.getHeight() + padding * 2, 0xFF111111);

      super.renderButton(matrices, mouseX, mouseY, delta);

      matrices.pop();
    }
  }

  private static CompletableFuture<Void> loadBackgroundTexturesAsync(TextureManager textureManager, Executor executor) {
    return SlideshowBackground.loadBackgroundTexturesAsync(textureManager, executor);
  }

  public CustomTitleScreen() {
    super(new TranslatableText("narrator.screen.title"));
    this.isMinceraft = (double)new Random().nextFloat() < 1.0E-4;
  }

  public static CompletableFuture<Void> loadTexturesAsync(TextureManager textureManager, Executor executor) {
    return CompletableFuture.allOf(
      textureManager.loadTextureAsync(MINECRAFT_TITLE_TEXTURE, executor),
      textureManager.loadTextureAsync(EDITION_TITLE_TEXTURE, executor),
      loadBackgroundTexturesAsync(textureManager, executor)
    );
  }

  @Override
  public boolean shouldPause() {
    return false;
  }

  @Override
  public boolean shouldCloseOnEsc() {
    return false;
  }

  private int getXPadding() {
    return this.width / 22;
  }

  private int getLogoY() {
    return this.height / 12;
  }

  @Override
  protected void init() {
    if (this.splashText == null) {
      this.splashText = this.client.getSplashTextLoader().get();
    }

    int versionWidth = this.textRenderer.getWidth(VERSION_TEXT) - 1;
    int versionX = this.width - versionWidth;
    int buttonsX = getXPadding();
    int buttonsY = getLogoY() * 2 + 50;

    int buttonHeight = 12;
    int buttonSpacing = buttonHeight + 1;
    int buttonSectionSpacing = 7;

    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY,
      130,
      buttonHeight,
      new TranslatableText("menu.n3ko.singleplayer"),
      button -> this.client.setScreen(new SelectWorldScreen(this)),
      this.textRenderer
    ));

    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY + buttonSpacing * 1,
      130,
      buttonHeight,
      new TranslatableText("menu.n3ko.multiplayer"),
      button -> {
        this.client.setScreen(new MultiplayerScreen(this));
      },
      this.textRenderer
    ));

    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY + buttonSpacing * 2 + buttonSectionSpacing,
      98,
      buttonHeight,
      new TranslatableText("menu.n3ko.modmenu"),
      button -> this.client.setScreen(new ModsScreen(this)),
      textRenderer
    ));
    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY + buttonSpacing * 3 + buttonSectionSpacing,
      98,
      buttonHeight,
      new TranslatableText("menu.n3ko.dupes").formatted(Formatting.GRAY),
      button -> {},
      textRenderer
    )).active = false;
    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY + buttonSpacing * 4 + buttonSectionSpacing,
      98,
      buttonHeight,
      new TranslatableText("menu.n3ko.demos").formatted(Formatting.GRAY),
      button -> {},
      textRenderer
    )).active = false;
    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY + buttonSpacing * 5 + buttonSectionSpacing,
      98,
      buttonHeight,
      new TranslatableText("menu.n3ko.saves").formatted(Formatting.GRAY),
      button -> {},
      textRenderer
    )).active = false;

    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY + buttonSpacing * 6 + buttonSectionSpacing * 2,
      98,
      buttonHeight,
      new TranslatableText("menu.n3ko.options"),
      button -> this.client.setScreen(new OptionsScreen(this, this.client.options)),
      textRenderer
    ));

    this.addDrawableChild(new PressableTextWidget(
      buttonsX,
      buttonsY + buttonSpacing * 7 + buttonSectionSpacing * 3,
      98,
      buttonHeight,
      new TranslatableText("menu.n3ko.quit"),
      button -> this.client.scheduleStop(),
      textRenderer
    ));

    this.addDrawableChild(
      new TexturedButtonWidget(
        this.width - 22,
        this.height - 22,
        20,
        20,
        0,
        106,
        20,
        ButtonWidget.WIDGETS_TEXTURE,
        256,
        256,
        button -> this.client.setScreen(new LanguageOptionsScreen(this, this.client.options, this.client.getLanguageManager())),
        new TranslatableText("narrator.button.language")
      )
    );
    this.addDrawableChild(new TexturedButtonWidget(
      this.width - 22 - 22,
      this.height - 22,
      20,
      20,
      0,
      0,
      20,
      ACCESSIBILITY_ICON_TEXTURE,
      32,
      64,
      button -> this.client.setScreen(new AccessibilityOptionsScreen(this, this.client.options)),
      new TranslatableText("narrator.button.accessibility")
    ));

    version = this.addDrawableChild(
      new SmallPressableTextWidget(
        versionX - 8, 6, versionWidth, 7, VERSION_TEXT, button -> this.client.setScreen(new CreditsScreen(this)), this.textRenderer
      )
    );
    version.setAlpha(0.8f);
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    int logoX = getXPadding();
    int logoY = getLogoY();

    SlideshowBackground.render(matrices, delta, this.width, this.height);

    RenderSystem.disableBlend();
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

    int gradientWidth = (int)(this.width * 0.7);

    matrices.push();
    matrices.translate(gradientWidth/2f, this.height/2f, 0);
    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-90f));
    DrawableHelper.fillGradient(matrices, -(int)Math.floor(this.height/2f), -(int)Math.floor(gradientWidth/2f) - 1, (int)Math.ceil(this.height/2f), (int)Math.ceil(gradientWidth/2f), 0x7b000000, 0x00000000, 0);
    matrices.pop();

    DrawableHelper.fill(matrices, 0, this.height - 24, this.width, this.height, 0x70111111);

    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, MINECRAFT_TITLE_TEXTURE);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    if (this.isMinceraft) {
      this.drawWithOutline(logoX, logoY, (x, y) -> {
        this.drawTexture(matrices, x + 0, y, 0, 0, 99, 44);
        this.drawTexture(matrices, x + 99, y, 129, 0, 27, 44);
        this.drawTexture(matrices, x + 99 + 26, y, 126, 0, 3, 44);
        this.drawTexture(matrices, x + 99 + 26 + 3, y, 99, 0, 26, 44);
        this.drawTexture(matrices, x + 155, y, 0, 45, 155, 44);
      });
    } else {
      this.drawWithOutline(logoX, logoY, (x, y) -> {
        this.drawTexture(matrices, x + 0, y, 0, 0, 155, 44);
        this.drawTexture(matrices, x + 155, y, 0, 45, 155, 44);
      });
    }

    RenderSystem.setShaderTexture(0, EDITION_TITLE_TEXTURE);
    drawTexture(matrices, logoX + 88, logoY + 37, 0.0F, 0.0F, 98, 14, 128, 16);

    if (this.splashText != null) {
      matrices.push();
      matrices.translate(260d, logoY + 40.0, 0.0);
      matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-20.0F));
      float h = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 1000.0F * (float) (Math.PI * 2)) * 0.1F);
      h = h * 100.0F / (float)(this.textRenderer.getWidth(this.splashText) + 32);
      matrices.scale(h, h, h);
      drawCenteredText(matrices, this.textRenderer, this.splashText, 0, -8, 16776960);
      matrices.pop();
    }

    super.render(matrices, mouseX, mouseY, delta);
  }
}
