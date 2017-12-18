package fr.matthis974jump.musicbot.music;

import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.EmbedBuilder;

public class AudioListener extends AudioEventAdapter{

    private final BlockingQueue<AudioTrack> tracks = new LinkedBlockingQueue<>();
    private final MusicPlayer player;

    public AudioListener(MusicPlayer player){
        this.player = player;
    }

    public BlockingQueue<AudioTrack> getTracks() {
        return tracks;
    }

    public int getTrackSize(){
        return tracks.size();
    }

    public void nextTrack(){
        if(tracks.isEmpty()){
            if(player.getGuild().getAudioManager().getConnectedChannel() != null)
                player.getGuild().getAudioManager().closeAudioConnection();
            return;
        }
        player.getAudioPlayer().startTrack(tracks.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
            this.player.getTextChannel().sendMessage(new EmbedBuilder()
            .setColor(Color.BLUE)
            .setTitle("Changement de piste")
            .setAuthor(this.player.getGuild().getSelfMember().getNickname(),this.player.getGuild().getInvites().complete().get(0).getURL(),this.player.getGuild().getSelfMember().getUser().getAvatarUrl())
            .addField("**:track_next: Next track : **","**"+track.getInfo().author+" ->** "+track.getInfo().title,true)
                    .build()).queue();
            this.tracks.add(track);

        }
    }

    public void queue(AudioTrack track) {
        if (!player.getAudioPlayer().startTrack(track, true)) tracks.offer(track);
    }

}