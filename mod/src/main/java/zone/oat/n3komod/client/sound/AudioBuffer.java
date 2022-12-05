package zone.oat.n3komod.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.Source;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.mixin.sound.SourceAccessorMixin;
import zone.oat.n3komod.util.Util;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

@Environment(EnvType.CLIENT)
public class AudioBuffer {
    private static final HttpClient CLIENT = HttpClient.newBuilder().build();
    private int buffer;

    public AudioBuffer(String url) {
        buffer = AL10.alGenBuffers();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("User-Agent", "N3KO SMP Modpack (https://github.com/oatmealine/n3ko-smp-modpack)")
            .build();
        load(request);
    }

    public void close() {
        if (buffer != 0) {
            AL10.alDeleteBuffers(buffer);
            buffer = 0;
        }
    }

    private int formatOf(int channels) {
        boolean stereo = channels > 1;

        if (stereo) {
            N3KOMod.LOGGER.warn("Stereo audio detected, attenuation is not supported!");
            return AL10.AL_FORMAT_STEREO16;
        }

        return AL10.AL_FORMAT_MONO16;
    }

    private void load(HttpRequest request) {
        N3KOMod.LOGGER.debug("Loading sound file: {}", request.uri());

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer channelCount = stack.mallocInt(1);
            IntBuffer sampleRate = stack.mallocInt(1);

            HttpResponse<byte[]> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (!Util.isOK(response.statusCode())) throw new Exception("Received a non-2xx status code: " + response.statusCode());

            byte[] bytes = response.body();
            ByteBuffer dataBuffer = stack.malloc(bytes.length);
            dataBuffer.put(0, bytes);

            ShortBuffer data = STBVorbis.stb_vorbis_decode_memory(dataBuffer, channelCount, sampleRate);

            if (data == null) {
                N3KOMod.LOGGER.warn("Failed to decode sound file due to an unknown error!!");
                return;
            }

            try {
                AL10.alBufferData(buffer, formatOf(channelCount.get()), data, sampleRate.get());
            } catch (Exception e) {
                buffer = 0;
                N3KOMod.LOGGER.warn("Failed to upload sound file", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            buffer = 0;
            N3KOMod.LOGGER.warn("(Potentially temporary) Error while downloading sound file", e);
        }
    }

    public int getHandle() {
        return buffer;
    }

    public Source play(Vec3d pos) {
        // THIS IS DERANGED
        // please don't do what i'm doing, if at all possible
        // thanks
        Source source = SourceAccessorMixin.invokeCreate();
        assert source != null;
        AL10.alSourcei(((SourceAccessorMixin) source).getPointer(), 4105, this.buffer);
        source.setPosition(pos);
        source.setAttenuation(16f);
        source.setRelative(false);
        source.play();

        return source;
    }

}
