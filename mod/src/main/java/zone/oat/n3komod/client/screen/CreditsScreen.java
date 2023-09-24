package zone.oat.n3komod.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.terraformersmc.modmenu.ModMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import zone.oat.n3komod.client.render.N3KOShaders;
import zone.oat.n3komod.util.ModIdentifier;

import static zone.oat.n3komod.client.render.util.DrawableHelperRebourne.fillShader;

@Environment(EnvType.CLIENT)
public class CreditsScreen extends Screen {
  private static final Text[] LINES = {
    Text.translatable("menu.n3ko.credits.0"),
    Text.translatable("menu.n3ko.credits.1"),
    Text.of(""),
    Text.translatable("menu.n3ko.credits.2", SharedConstants.getGameVersion().getName(), ModMenu.getDisplayedModCount()),
    Text.of(""),
    Text.of(""),
    Text.of("")
  };
  private static final int LINE_HEIGHT = 12;
  private static final Identifier GITHUB_ICON_TEXTURE = new ModIdentifier("textures/gui/credits/github.png");
  private static final Identifier DISCORD_ICON_TEXTURE = new ModIdentifier("textures/gui/credits/discord.png");

  protected final Screen parent;

  private float t = 0;

  public CreditsScreen(Screen parent) {
    super(Text.of(""));
    this.parent = parent;
  }

  private void openLink(String url) {
    final var client = MinecraftClient.getInstance();
    var screen = client.currentScreen;
    client.setScreen(new ConfirmLinkScreen(confirmed -> {
      if (confirmed) Util.getOperatingSystem().open(url);
      client.setScreen(screen);
    }, url, true));
  }

  @Override
  protected void init() {
    int y = this.height/2 + 20;

    int buttonSize = 20;
    int buttonPadding = 2;

    TexturedButtonWidget[] buttons = {
      this.addDrawableChild(new TexturedButtonWidget(
        0,
        0,
        buttonSize,
        buttonSize,
        0,
        0,
        buttonSize,
        GITHUB_ICON_TEXTURE,
        32,
        64,
        button -> openLink("https://github.com/oatmealine/n3ko-smp-modpack")
      )),
      this.addDrawableChild(new TexturedButtonWidget(
        0,
        0,
        buttonSize,
        buttonSize,
        0,
        0,
        buttonSize,
        DISCORD_ICON_TEXTURE,
        32,
        64,
        button -> openLink("https://discord.gg/f3UbY4x2d8")
      ))
    };

    int columnsWidth = (buttonSize + buttonPadding) * (buttons.length - 1);
    for (int i = 0; i < buttons.length; i++) {
      int x = (int) (Math.floor(this.width / 2f - columnsWidth / 2f) + i * (buttonSize + buttonPadding));
      buttons[i].setPosition(x - buttonSize/2, y - buttonSize/2);
    }

    this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), (button) -> {
      this.close();
    })
      .dimensions(this.width / 2 - 50, y + 12, 100, 20)
      .build()
    );
  }

  @Override
  public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
    MatrixStack matrices = ctx.getMatrices();

    super.renderBackground(ctx);

    t += delta;

    //SlideshowBackground.render(matrices, delta, this.width, this.height);
    ctx.fillGradient(0, 0, this.width, this.height, 0x2a000000, 0x9b000000, 0);

    matrices.push();
    matrices.translate(this.width/2f, this.height/2f, 0f);

    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.enableBlend();
    RenderSystem.defaultBlendFunc();

    ShaderProgram shader = N3KOShaders.threadSky.getShader();
    shader.gameTime.set(t);
    fillShader(ctx, -120, -70, 120, 70, 0xFFFFFF, N3KOShaders.threadSky::getShader);

    RenderSystem.enableCull();
    RenderSystem.enableDepthTest();

    matrices.pop();

    super.render(ctx, mouseX, mouseY, delta);

    int centerX = this.width / 2;
    int centerY = this.height / 2;

    float linesHeight = LINE_HEIGHT * LINES.length;

    for (int i = 0; i < LINES.length; i++) {
      Text line = LINES[i];
      int y = (int) (centerY - linesHeight / 2f) + i * LINE_HEIGHT;
      ctx.drawCenteredTextWithShadow(this.textRenderer, line.asOrderedText(), centerX, y, i == 0 ? 0x05FEB3 : 0xFFFFFF);
    }
  }

  @Override
  public void close() {
    this.client.setScreen(this.parent);
  }
}
