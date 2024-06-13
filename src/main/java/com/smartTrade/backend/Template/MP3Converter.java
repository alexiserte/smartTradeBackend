package com.smartTrade.backend.Template;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class MP3Converter extends Converter<AudioInputStream> {


    @Override
    public AudioInputStream convertToFile(String source) {

        try {
            File audioFile = new File(source);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            return audioInputStream;
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String transformFileAndConvertToBase64(AudioInputStream audioInputStream) {
        // Leer el contenido del archivo y convertirlo a base64
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, baos);
            byte[] audioBytes = baos.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(audioBytes);
            return base64String;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToBase64(AudioInputStream audioInputStream) {
        // Este método se puede utilizar directamente si solo se necesita convertir el AudioInputStream a base64
        return transformFileAndConvertToBase64(audioInputStream);
    }
}
