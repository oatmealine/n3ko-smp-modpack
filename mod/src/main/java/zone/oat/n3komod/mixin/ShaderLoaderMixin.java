package zone.oat.n3komod.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
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
  private boolean n3ko$addedToList = true;
  private ResourceManager n3ko$manager;

  @Redirect(method = "loadShaders", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  <E> boolean injectIntoList(List instance, E e) throws IOException {
    if (!n3ko$addedToList) {
      List<ShaderProvider> shaders = N3KOShaders.getShaders();
      for (ShaderProvider s : shaders) {
        instance.add(Pair.of(
          s.createShader(n3ko$manager),
          (Consumer<Shader>) shader -> s.setShader(shader)
        ));
      }
      n3ko$addedToList = true;
    }
    return instance.add(e);
  }

  @Inject(method = "reload", at = @At(value = "HEAD"))
  void injected(ResourceManager manager, CallbackInfo ci) {
    this.n3ko$addedToList = false;
    this.n3ko$manager = manager;
  }
}
