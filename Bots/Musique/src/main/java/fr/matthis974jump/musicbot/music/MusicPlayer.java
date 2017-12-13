package fr.matthis974jump.musicbot.music;


import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicPlayer {

    private final AudioPlayer audioPlayer;
    private final AudioListener listener;
    private final Guild guild;
    private final TextChannel tc;
    public MusicPlayer(AudioPlayer audioPlayer, Guild guild, TextChannel tc){
        this.audioPlayer = audioPlayer;
        this.guild = guild;
        this.tc = tc;
        listener = new AudioListener(this);
        audioPlayer.addListener(listener);
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public Guild getGuild() {
        return guild;
    }

    public AudioListener getListener() {
        return listener;
    }

    public AudioHandler getAudioHandler(){
        return new AudioHandler(audioPlayer);
    }

    public synchronized void playTrack(AudioTrack track){
        listener.queue(track);
    }

    public synchronized void skipTrack(){
        listener.nextTrack();
    }

    public TextChannel getTextChannel() {
        return tc;
    }
}