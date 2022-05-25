package display.panels;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MajorFrame extends JFrame {

    public static AudioInputStream pieUp;
    public static AudioInputStream pieDown;
    public static AudioInputStream bgm;

    public MajorFrame(String text){
        super(text);
    }

    public void decode(){
        try {
            String path0 = "pic"+File.separator+"pie1.wav";
            String path1 = "pic"+File.separator+"xia1.wav";
            String path2 = "pic"+File.separator+"piee3.wav";
            pieDown = AudioSystem.getAudioInputStream(new File(path0));
            bgm = AudioSystem.getAudioInputStream(new File(path1));
            pieUp = AudioSystem.getAudioInputStream(new File(path2));
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
