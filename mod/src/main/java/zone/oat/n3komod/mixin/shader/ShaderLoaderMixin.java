package zone.oat.n3komod.mixin.shader;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zone.oat.n3komod.client.render.N3KOShaders;
import zone.oat.n3komod.client.render.ShaderProvider;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class ShaderLoaderMixin {
  private boolean n3ko$addedToList = false;

  @Redirect(method = "loadPrograms", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  <E> boolean injectIntoList(List instance, E e) throws IOException {
    ResourceManager manager = ((GameRenderer)(Object)this).getClient().getResourceManager();

    if (!n3ko$addedToList) {
      List<ShaderProvider> shaders = N3KOShaders.getShaders();
      for (ShaderProvider s : shaders) {
        instance.add(Pair.of(
          s.createShader(manager),
          (Consumer<ShaderProgram>) shader -> s.setShader(shader)
        ));
      }
      n3ko$addedToList = true;
    }
    return instance.add(e);
  }

  @Inject(method = "clearPrograms", at = @At(value = "HEAD"))
  void injected(CallbackInfo ci) {
    this.n3ko$addedToList = false;
  }
}
