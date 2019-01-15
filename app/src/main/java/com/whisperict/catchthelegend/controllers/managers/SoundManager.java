package com.whisperict.catchthelegend.controllers.managers;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * The SoundManager class manages the sound of the application.
 */
public class SoundManager implements MediaPlayer.OnCompletionListener {

    /**
     * The mediaPlayer attribute regulates the sound of the application.
     */
    private MediaPlayer mediaPlayer = new MediaPlayer();

    /**
     * Countains all sounds that will be played.
     */
    private ArrayList<MediaPlayer> mediaPlayers = new ArrayList<>();

    private MediaPlayer constantPlayer = new MediaPlayer();

    /**
     * Keeps track of which sound is played in the list of mediaplayers.
     */
    private int index = 0;

    private static SoundManager instance;

    private SoundManager() {

    }

    /**
     * Playss a audio file.
     * @param sound is the sound that will be played.
     */
    public void playSound(Sound sound) {
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(sound.getContext(), sound.getId());
        mediaPlayer.start();
    }

    /**
     * Plays the whole list of mediaplayers.
     */
    public void playPlayList(){
        new Thread(() -> {
            for(index = 0; index < mediaPlayers.size();){
                if(!mediaPlayer.isPlaying()){
                    MediaPlayer mediaPlayer = mediaPlayers.get(index);
                    mediaPlayer.start();
                    index++;
                }
            }
        }).start();
    }

    public void playConstant(Sound sound){
        constantPlayer = MediaPlayer.create(sound.getContext(), sound.getId());
        constantPlayer.setLooping(true);
        constantPlayer.setVolume(1,1);
        constantPlayer.start();
    }

    /**
     * Adds a sound to the playlist
     * @param sound
     */
    public void addToPlayList(Sound sound) {
        mediaPlayers.add(MediaPlayer.create(sound.getContext(), sound.getId()));
    }

    /**
     * Adds all sounds to the playlist.
     * @param sounds
     */
    public void addAllToPlayList(Sound... sounds){
        for(Sound sound : sounds){
            mediaPlayers.add(MediaPlayer.create(sound.getContext(), sound.getId()));
        }
    }

    /**
     * Stops all sounds in the playlist.
     */
    public void stopSounds(){
        index = Integer.MAX_VALUE;
        mediaPlayers.clear();
        mediaPlayer.stop();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public ArrayList<MediaPlayer> getMediaPlayers() {
        return mediaPlayers;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);
    }

    public static SoundManager getInstance(){
        if(instance == null){
            instance = new SoundManager();
        }
        return instance;
    }

    public MediaPlayer getConstantPlayer() {
        return constantPlayer;
    }

    public void setConstantPlayer(MediaPlayer constantPlayer) {
        this.constantPlayer = constantPlayer;
    }
}
