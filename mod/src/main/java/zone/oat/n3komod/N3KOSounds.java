package zone.oat.n3komod;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class N3KOSounds {
  public static final Identifier SQUEAK = new Identifier(N3KOMod.NAMESPACE, "squeak");
  public static SoundEvent SQUEAK_EVENT = new SoundEvent(SQUEAK);

  public static void init() {
    Registry.register(Registry.SOUND_EVENT, SQUEAK, SQUEAK_EVENT);
  }
}
