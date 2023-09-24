package zone.oat.n3komod.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.Source;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.mixin.sound.SourceAccessorMixin;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayDeque;
import java.util.Queue;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Environment(EnvType.CLIENT)
public class AudioBuffer {
    private int buffer;
    private boolean isLoaded = false;

    public interface SourceAction {
        void onLoad(@Nullable Source source);
    }
    private Queue<SourceAction> playQueue = new ArrayDeque<>();

    public AudioBuffer(String url) {
        buffer = 0;
        download(url);
    }

    public void close() {
        if (buffer != 0) {
            AL10.alDeleteBuffers(buffer);
            buffer = 0;
        }
        isLoaded = false;
    }

    private int formatOf(int channels) {
        boolean stereo = channels > 1;

        if (stereo) {
            N3KOMod.LOGGER.warn("Stereo audio detected, attenuation is not supported!");
            return AL10.AL_FORMAT_STEREO16;
        }

        return AL10.AL_FORMAT_MONO16;
    }

    private void onLoaded() {
        isLoaded = true;
        while (!playQueue.isEmpty()) {
            N3KOMod.LOGGER.info("lois... i'm iterating... fuck");
            SourceAction action = playQueue.remove();
            action.onLoad(play());
        }
    }
    private void loadFailed() {
        N3KOMod.LOGGER.warn("Load has failed!");
        while (!playQueue.isEmpty()) {
            SourceAction action = playQueue.remove();
            action.onLoad(null);
        }
        close();
    }

    private boolean decodeAndLoad(byte[] bytes) {
        buffer = AL10.alGenBuffers();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer channelCount = stack.mallocInt(1);
            IntBuffer sampleRate = stack.mallocInt(1);

            ByteBuffer dataBuffer = stack.malloc(bytes.length);
            dataBuffer.put(0, bytes);

            ShortBuffer data = STBVorbis.stb_vorbis_decode_memory(dataBuffer, channelCount, sampleRate);

            if (data == null) {
                N3KOMod.LOGGER.warn("Failed to decode sound file due to an unknown error!!");
                return false;
            }

            try {
                AL10.alBufferData(buffer, formatOf(channelCount.get()), data, sampleRate.get());
                return true;
            } catch (Exception e) {
                buffer = 0;
                N3KOMod.LOGGER.warn("Failed to upload sound file", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            buffer = 0;
            N3KOMod.LOGGER.warn("(Potentially temporary) Error while downloading sound file", e);
        }

        return false;
    }

    private void download(String url) {
        AudioDownloader.getInstance().getData(url, (data) -> {
            if (data == null) {
                loadFailed();
            } else {
                boolean success = decodeAndLoad(data);
                if (success) {
                    onLoaded();
                } else {
                    loadFailed();
                }
            }
        });
    }

    public int getHandle() {
        return buffer;
    }

    public Source play() {
        if (!isLoaded) return null;

        // THIS IS DERANGED
        // please don't do what i'm doing, if at all possible
        // thanks
        Source source = SourceAccessorMixin.invokeCreate();
        assert source != null;
        AL10.alSourcei(((SourceAccessorMixin) source).getPointer(), 4105, this.buffer);
        source.setAttenuation(16f);
        source.setRelative(false);
        source.play();

        return source;
    }
    public Source play(Vec3d pos) {
        Source source = play();
        if (source == null) return null;
        source.setPosition(pos);
        return source;
    }
    public Source play(Vec3d pos, float volume, float pitch) {
        Source source = play();
        if (source == null) return null;
        source.setPosition(pos);
        source.setVolume(volume);
        source.setPitch(pitch);
        return source;
    }


    public void playOnceLoaded(SourceAction action) {
        if (isLoaded) {
            action.onLoad(play());
        } else if (buffer == 0) {
            action.onLoad(null);
        } else {
            playQueue.add(action);
        }
    }
    public void playOnceLoaded(SourceAction action, Vec3d pos) {
        playOnceLoaded((source) -> {
            if (source != null) source.setPosition(pos);
            action.onLoad(source);
        });
    }
    public void playOnceLoaded(SourceAction action, Vec3d pos, float volume, float pitch) {
        playOnceLoaded((source) -> {
            if (source != null) {
                source.setPosition(pos);
                source.setVolume(volume);
                source.setPitch(pitch);
            }
            action.onLoad(source);
        });
    }
}
