package org.textin;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class M4AToWavConverter {
    public static void main(String[] args) throws Exception {
        String inputPath = "src/main/resources/m4a/3.m4a";
        String outputPath = "src/main/resources/m4a/3.wav";

        AudioAttributes audioAttrs = new AudioAttributes();
        audioAttrs.setCodec("pcm_s16le");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setAudioAttributes(audioAttrs);

        Encoder encoder = new Encoder();
        List<MultimediaObject> source = Arrays.asList(new MultimediaObject(new File(inputPath)));
        encoder.encode(source, new File(outputPath), attrs);
    }
}
