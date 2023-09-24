package zone.oat.n3komod.registry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import zone.oat.n3komod.util.ModIdentifier;

public class N3KODimensions {
  public static final Identifier THREAD = new ModIdentifier("thread");
  public static final RegistryKey<World> THREAD_REGISTRY_KEY = RegistryKey.of(RegistryKeys.WORLD, THREAD);

  public static boolean isInThread() {
      return MinecraftClient.getInstance().world.getDimensionEntry().matchesId(THREAD);
  }
}
