package zone.oat.n3komod.mixin.sound;

import net.minecraft.client.sound.Source;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Source.class)
public interface SourceAccessorMixin {
  @Invoker("create")
  static Source invokeCreate() {
    throw new AssertionError();
  }

  @Accessor
  int getPointer();
}
