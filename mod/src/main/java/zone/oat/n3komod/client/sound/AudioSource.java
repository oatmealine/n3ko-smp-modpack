package zone.oat.n3komod.client.sound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.apache.commons.lang3.NotImplementedException;
import org.lwjgl.openal.AL10;

public class AudioSource {

    private boolean attenuation;
    private float x, y, z;
    private int source;
    private boolean playing;

    AudioSource() {
        source = AL10.alGenSources();
        attenuation = false;

        // the distance at which only half of the volume is heard
        AL10.alSourcef(source, AL10.AL_REFERENCE_DISTANCE, 5);
    }

    void close() {
        if (source != 0) {
            AL10.alDeleteSources(source);
            source = 0;
        }
    }

    /**
     * Set the audio buffer from which to play the sound
     */
    public void setBuffer(AudioBuffer buffer) {
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer.getHandle());
    }

    /**
     * Set the pitch of this source
     */
    public AudioSource setPitch(float pitch) {
        AL10.alSourcef(source, AL10.AL_PITCH, pitch);
        return this;
    }

    /**
     * Set the volume of this source
     */
    public AudioSource setVolume(float volume) {
        AL10.alSourcef(source, AL10.AL_GAIN, volume);
        return this;
    }

    /**
     * Enable or disable looping for this source
     */
    public AudioSource setLoop(boolean loop) {
        AL10.alSourcei(source, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
        return this;
    }

    /**
     * Enable or disable attenuation for this source
     */
    public void setAttenuation(boolean attenuation) {
        this.attenuation = attenuation;
    }

    /**
     * Positions from which the audio should be played
     */
    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Check if the audio is being played
     */
    public boolean isPlaying() {
        return AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    /**
     * Start playing
     */
    public void play() {
        AL10.alSourcePlay(source);
        positionUpdate();
    }

    /**
     * Stop playing
     */
    public void stop() {
        AL10.alSourceStop(source);
    }

    public boolean tick() {
        positionUpdate();

        if (!isPlaying()) {
            close();
            return false;
        }

        return true;
    }

    private void positionUpdate() {
        // minecraft handles this for you, silly!!!!
        /*
        if (!attenuation) return;
        World world = MinecraftClient.getInstance().world;
        if (world != null && world.isClient()) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            //AL10.alSource3f(source, AL10.AL_POSITION, (float) player.getX() + x, (float) player.getY() + y, (float) player.getZ() + z);
        }
        */
    }

}