package fr.matthis974jump.musicbot.music;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicManager {

    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<String, MusicPlayer>();

    public MusicManager(){
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
    }

    public synchronized MusicPlayer getPlayer(Guild guild){
        if(!players.containsKey(guild.getId())) players.put(guild.getId(), new MusicPlayer(manager.createPlayer(), guild, guild.getDefaultChannel()));
        return players.get(guild.getId());
    }

    public void loadTrack(final TextChannel channel, final String source){
        final MusicPlayer player = getPlayer(channel.getGuild());

        channel.getGuild().getAudioManager().setSendingHandler(player.getAudioHandler());

        manager.loadItemOrdered(player, source, new AudioLoadResultHandler(){

            public void trackLoaded(AudioTrack track) {
                player.playTrack(track);
            }

            public void playlistLoaded(AudioPlaylist playlist) {
                StringBuilder builder = new StringBuilder();
                builder.append("Ajout de la playlist **").append(playlist.getName()).append("**\n");

                for(int i = 0; i < playlist.getTracks().size() && i < 5; i++){
                    AudioTrack track = playlist.getTracks().get(i);
                    builder.append("\n  **->** ").append(track.getInfo().title);
                    player.playTrack(track);
                }

                channel.sendMessage(builder.toString()).queue();
            }


            public void noMatches() {

            }

            public void loadFailed(FriendlyException exception) {
            }
        });
    }

    public void skipTrack(Guild g) {
        players.get(g.getId()).skipTrack();
    }
}