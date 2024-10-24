package io.github.nozyx12.opentoast.modifier;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A ToastModifier implementation that plays a sound when the toast notification is displayed.
 * The sound is played from a byte array representing the audio data.
 */
public class SoundToastModifier implements ToastModifier {
    private final byte[] displaySoundData;

    /**
     * Constructs a SoundToastModifier with a specified sound file input stream.
     *
     * @param displaySoundFileInputStream An InputStream for the sound file to be played.
     */
    public SoundToastModifier(InputStream displaySoundFileInputStream) {
        this.displaySoundData = this.readAllBytes(displaySoundFileInputStream);
    }

    /**
     * Called when the toast notification is displayed.
     * This method plays the associated sound.
     *
     * @param panel The JPanel representing the content of the toast notification.
     */
    @Override
    public void onDisplay(JPanel panel) {
        this.playSound(this.displaySoundData);
    }

    /**
     * Called when the toast notification is closing.
     * This implementation does not perform any actions upon closing.
     *
     * @param panel The JPanel representing the content of the toast notification.
     */
    @Override
    public void onClose(JPanel panel) {}

    /**
     * Plays the specified sound data.
     *
     * @param soundData The byte array containing the audio data to be played.
     */
    private void playSound(byte[] soundData) {
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(soundData))) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) clip.close();
            });

            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all bytes from the given InputStream into a byte array.
     *
     * @param inputStream The InputStream to read from.
     * @return A byte array containing all the bytes read from the input stream.
     */
    private byte[] readAllBytes(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }
}
