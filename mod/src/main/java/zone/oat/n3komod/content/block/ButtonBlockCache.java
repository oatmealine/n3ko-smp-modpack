package zone.oat.n3komod.content.block;

import zone.oat.n3komod.client.sound.AudioBuffer;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class ButtonBlockCache {
    private Map<String, AudioBuffer> cache = Collections.synchronizedMap(new WeakHashMap<>());

    private AudioBuffer getBufferInternal(String url) {
        // TODO: PLEASE please please PLEASE figure out how to make this async/threaded
        AudioBuffer buf = new AudioBuffer(url);
        return buf;
    }

    public AudioBuffer getBuffer(String url) {
        AudioBuffer cached = cache.get(url);
        if (cached == null) {
            cached = getBufferInternal(url);
            cache.put(url, cached);
        }
        return cached;
    }

    public void clear() {
        cache.clear();
    }
}
