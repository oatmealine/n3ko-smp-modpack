package zone.oat.n3komod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.JsonEffectGlShader;
import net.minecraft.client.gl.PostProcessShader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostProcessShader.class)
public class PostProcessShaderMixin {
  @Final
  @Shadow
  private JsonEffectGlShader program;

  @Inject(method = "render", at = @At("HEAD"))
  protected void onRender(float time, CallbackInfo ci) {
    GlUniform uniform = this.program.getUniformByName("GameTime");
    if (uniform != null) uniform.set(((float) MinecraftClient.getInstance().world.getTime()) / 50.0f);
  }
}
