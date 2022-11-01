package zone.oat.n3komod.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KODimensions {
  public static final Identifier THREAD = new ModIdentifier("thread");
  public static final RegistryKey<World> THREAD_REGISTRY_KEY = RegistryKey.of(Registry.WORLD_KEY, THREAD);
}
