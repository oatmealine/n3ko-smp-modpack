package zone.oat.n3komod.registry;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOSounds implements AutoRegistryContainer<SoundEvent> {
  public static final SoundEvent SQUEAK = SoundEvent.of(new ModIdentifier("squeak"));
  public static final SoundEvent SILENCE = SoundEvent.of(new ModIdentifier("silence"));

  @Override
  public Registry<SoundEvent> getRegistry() {
    return Registries.SOUND_EVENT;
  }

  @Override
  public Class<SoundEvent> getTargetFieldType() {
    return SoundEvent.class;
  }
}
