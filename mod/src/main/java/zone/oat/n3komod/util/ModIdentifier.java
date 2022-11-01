package zone.oat.n3komod.util;

import net.minecraft.util.Identifier;
import zone.oat.n3komod.N3KOMod;

public class ModIdentifier extends Identifier {
  public ModIdentifier(String path) {
    super(N3KOMod.NAMESPACE, path);
  }
}
