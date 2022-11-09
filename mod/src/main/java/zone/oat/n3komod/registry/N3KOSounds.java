package zone.oat.n3komod.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KOSounds {
  public static final Identifier SQUEAK = new ModIdentifier("squeak");
  public static SoundEvent SQUEAK_EVENT = new SoundEvent(SQUEAK);
  public static final SoundEvent SILENCE = new SoundEvent(new ModIdentifier("silence"));

  public static void init() {
    Registry.register(Registry.SOUND_EVENT, SQUEAK, SQUEAK_EVENT);
  }
}
