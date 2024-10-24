package io.github.nozyx12.opentoast.modifier;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundToastModifier implements ToastModifier {
    private final byte[] displaySoundData;

    public SoundToastModifier(InputStream displaySoundFileInputStream) {
        this.displaySoundData = this.readAllBytes(displaySoundFileInputStream);
    }

    @Override
    public void onDisplay(JPanel panel) {
        this.playSound(this.displaySoundData);
    }

    @Override
    public void onClose(JPanel panel) {}

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

    private byte[] readAllBytes(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;

        try {
            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) buffer.write(data, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }
}
