package org.textin.util;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-03-12 12:09
 */

import ws.schild.jave.EncoderException;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;

import java.io.*;
import java.util.Arrays;
import java.util.List;
public class ToWavUtil {
    public static byte[] convertToWAVAndToBytes(byte[] input) throws IOException, EncoderException {
        File tmpFile = File.createTempFile("temp", ".wav");
        try (FileOutputStream fos = new FileOutputStream(tmpFile)) {
            fos.write(input);
        }
        File wavFile = File.createTempFile("temp", ".wav");
        AudioAttributes audioAttrs = new AudioAttributes();
        audioAttrs.setCodec("pcm_s16le");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audioAttrs);
        Encoder encoder = new Encoder();
        List<MultimediaObject> source = Arrays.asList(new MultimediaObject(tmpFile));
        encoder.encode(source, wavFile, attrs);

        // Convert WAV to byte array
        try (FileInputStream fis = new FileInputStream(wavFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        }
    }
}
