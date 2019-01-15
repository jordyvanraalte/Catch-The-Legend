package com.whisperict.catchthelegend.managers;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * The SoundManager class manages the sound of the application.
 */
public class SoundManager {

    /**
     * The mediaPlayer attribute regulates the sound of the application.
     */
    private static MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * Countains all sounds that will be played.
     */
    private static ArrayList<MediaPlayer> mediaPlayers = new ArrayList<>();

    /**
     * Keeps track of which sound is played in the list of mediaplayers.
     */
    private static int index = 0;

    private SoundManager() {

    }

    /**
     * Playss a audio file.
     * @param sound is the sound that will be played.
     */
    public static void playSound(Sound sound) {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(sound.getContext(), sound.getId());
        mediaPlayer.start();
    }

    /**
     * Plays the whole list of mediaplayers.
     */
    public static void playPlayList(){
        new Thread(() -> {
            for(index = 0; index < mediaPlayers.size();){
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer = mediaPlayers.get(index);
                    mediaPlayer.start();
                    index++;
                }
            }
        }).start();
    }

    /**
     * Adds a sound to the playlist
     * @param sound
     */
    public static void addToPlayList(Sound sound) {
        mediaPlayers.add(MediaPlayer.create(sound.getContext(), sound.getId()));
    }

    /**
     * Adds all sounds to the playlist.
     * @param sounds
     */
    public static void addAllToPlayList(Sound... sounds){
        for(Sound sound : sounds){
            mediaPlayers.add(MediaPlayer.create(sound.getContext(), sound.getId()));
        }
    }

    /**
     * Stops all sounds in the playlist.
     */
    public static void stopSounds(){
        index = Integer.MAX_VALUE;
        mediaPlayers.clear();
        mediaPlayer.stop();
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static ArrayList<MediaPlayer> getMediaPlayers() {
        return mediaPlayers;
    }
}
