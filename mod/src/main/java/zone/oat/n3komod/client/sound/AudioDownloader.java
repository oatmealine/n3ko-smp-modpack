package zone.oat.n3komod.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.util.Util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Environment(EnvType.CLIENT)
public class AudioDownloader {
    static AudioDownloader instance = new AudioDownloader();
    public static AudioDownloader getInstance() {
        return instance;
    }

    private Map<String, byte[]> cache = Collections.synchronizedMap(new WeakHashMap<>());

    // Create a service for downloading the audio
    private final ExecutorService service = newFixedThreadPool(4);
    private final Object mutex = new Object();

    private static final HttpClient CLIENT = HttpClient.newBuilder().build();

    private HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "N3KO SMP Modpack (https://github.com/oatmealine/n3ko-smp-modpack)")
                .build();
    }

    private void download(String url, FinishAction action) {
        N3KOMod.LOGGER.info("Downloading sound file: {}", url);

        service.submit(() -> {
            try {
                HttpRequest req = buildRequest(url);

                HttpResponse<byte[]> response = CLIENT.send(req, HttpResponse.BodyHandlers.ofByteArray());

                if (!Util.isOK(response.statusCode()))
                    throw new RuntimeException("Received a non-2xx status code: " + response.statusCode());

                synchronized (mutex) {
                    byte[] data = response.body();
                    cache.put(url, data);
                    action.onFinish(data);
                }
            } catch (Exception e) {
                N3KOMod.LOGGER.error("Error downloading audio", e);
            }
        });
    }

    public interface FinishAction {
        void onFinish(byte @Nullable [] data);
    }

    // is there a reason for this?
    /*@Nullable
    public byte[] getData(String url) {
        synchronized (mutex) {
            byte[] data = cache.get(url);
            if (data == null) {
                download(url, (d)->{});
                return null;
            }

            return data;
        }
    }*/

    public void getData(String url, FinishAction action) {
        synchronized (mutex) {
            byte[] data = cache.get(url);
            if (data == null) {
                try {
                    download(url, action);
                } catch (Exception e) {
                    N3KOMod.LOGGER.error("Error downloading", e);
                    action.onFinish(null);
                }
            } else {
                action.onFinish(data);
            }
        }
    }

    public void clear() {
        cache.clear();
    }
    public void remove(String url) {
        cache.remove(url);
    }
}
