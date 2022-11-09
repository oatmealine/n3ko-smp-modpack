package zone.oat.n3komod.client.render;

import net.minecraft.client.render.VertexFormats;
import zone.oat.n3komod.N3KOMod;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class N3KOShaders {
  public static ShaderProvider threadSky = new ShaderProvider("fbm-perlin", VertexFormats.POSITION_TEXTURE_COLOR);

  public static List<ShaderProvider> getShaders() {
    return Arrays.stream(N3KOShaders.class.getDeclaredFields())
      .map(field -> {
        try {
          if (field.get(N3KOShaders.class) instanceof ShaderProvider provider) {
            return provider;
          } else {
            return null;
          }
        } catch (IllegalAccessException e) {
          return null;
        }
      })
      .filter(Objects::nonNull)
      .toList();
  }
}
