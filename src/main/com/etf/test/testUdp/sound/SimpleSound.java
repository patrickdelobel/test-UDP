package com.etf.test.testUdp.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Created by patrick on 28/04/17.
 */
public class SimpleSound {
    public static float SAMPLE_RATE = 8000f;

    public static void tone(int hz, int msecs, double vol) {
        try {
            byte[] buf = new byte[1];
            AudioFormat af =
                    new AudioFormat(
                            SAMPLE_RATE, // sampleRate
                            8,           // sampleSizeInBits
                            1,           // channels
                            true,        // signed
                            false);      // bigEndian
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);
            sdl.start();
            for (int i = 0; i < msecs * 8; i++) {
                double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127.0 * vol);
                sdl.write(buf, 0, 1);
            }
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException e) {
            //TODO log some error
        }
    }
}
