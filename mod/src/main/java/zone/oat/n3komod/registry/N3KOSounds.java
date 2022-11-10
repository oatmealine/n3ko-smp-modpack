package zone.oat.n3komod.registry;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOSounds implements AutoRegistryContainer<SoundEvent> {
  public static final SoundEvent SQUEAK = new SoundEvent(new ModIdentifier("squeak"));
  public static final SoundEvent SILENCE = new SoundEvent(new ModIdentifier("silence"));

  @Override
  public Registry<SoundEvent> getRegistry() {
    return Registry.SOUND_EVENT;
  }

  @Override
  public Class<SoundEvent> getTargetFieldType() {
    return SoundEvent.class;
  }
}
