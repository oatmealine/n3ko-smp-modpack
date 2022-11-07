package zone.oat.n3komod.client.render;

import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceManager;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ShaderProvider {
  // minecraft/shaders/core/{SHADER_NAME}.json
  protected final String shaderName;
  protected final VertexFormat vertexFormat;

  public ShaderProvider(String name, VertexFormat format) {
    this.shaderName = name;
    this.vertexFormat = format;
  }

  @Nullable
  private Shader shader;
  @Nullable
  public Shader getShader() { return this.shader; }
  public void setShader(Shader shader) { this.shader = shader; }

  public Shader createShader(ResourceManager resource) throws IOException {
    return new Shader(resource, shaderName, vertexFormat);
  }
}
