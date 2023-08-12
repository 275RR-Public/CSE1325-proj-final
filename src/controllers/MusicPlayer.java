//https://www.geeksforgeeks.org/play-audio-file-using-java/#

package controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import utils.Constants;

public class MusicPlayer {
    
    private Clip clip;
    private Long current_frame = 0L;
    private String status;
    private List<URL> urls = new ArrayList<>();
    private URL current_song;
    private float current_volume;

    private final String res = Constants.BGM_RES;

    public MusicPlayer(int song, float volume) {
        List<Path> paths = LoadSave.getResourcePaths(res);
        for(var path : paths) {
            String filename = path.getFileName().toString();
            urls.add(LoadSave.getContext().getResource(res + filename));
        }
        current_song = urls.get(song);
        current_volume = volume;
        loadMusic(current_song);
        play();
    }

    private void loadMusic(URL song) {
        try (AudioInputStream audio_stream = AudioSystem.getAudioInputStream(song)) {
            AudioFormat format = audio_stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.open(audio_stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException e) {
            System.out.println("playMusic: Couldn't read or find file.");
        } catch (UnsupportedAudioFileException e) {
            System.out.println("playMusic: Can't play file type.");
        } catch (LineUnavailableException e) {
            System.out.println("playMusic: Line unavailable.");
        }
    }

    private void play() {
        setVolume(current_volume); //[0-1]
        clip.start();
        status = "play";
    }

    private void stop() {
        current_frame = 0L;
        clip.stop();
        clip.close();
    }
      
    public void pause() {
        if (status.equals("paused")) return;
        current_frame = clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }
      
    public void resumeAudio() {
        if (status.equals("play")) return;
        clip.close();
        loadMusic(current_song);
        clip.setMicrosecondPosition(current_frame);
        play();
    }

    public void next() {
        stop();
        int index = urls.indexOf(current_song);
        int length = urls.size();
        if((index + 1) == length) {
            current_song = urls.get(0);
        }
        else {
            current_song = urls.get(index + 1);
        }
        loadMusic(current_song);
        play();
    }

    public void prev() {
        stop();
        int index = urls.indexOf(current_song);
        int length = urls.size();
        if(index == 0) {
            current_song = urls.get(length - 1);
        }
        else {
            current_song = urls.get(index - 1);
        }
        loadMusic(current_song);
        play();
    }
    
    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        gainControl.setValue(20f * (float) Math.log10(volume));
        current_volume = volume;
    }
}
