package fr.matthis974jump.musicbot.music.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.matthis974jump.musicbot.music.MusicManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.List;

// MATTHIS974JUMP CODE //
// InfoCommand 13/12/2017 11:19 //
// by claud //
public class InfoCommand extends Command {
    private final EventWaiter waiter;
    private final MusicManager music;

    public InfoCommand(EventWaiter waiter,MusicManager mm)
    {
        this.music = mm;
        this.waiter = waiter;
        this.name = "title";
        this.aliases = new String[]{"chanson","encours"};
        this.help = "says hello and waits for a response";
    }
    @Override
    protected void execute(CommandEvent commandEvent) {
        String title = music.getPlayer(commandEvent.getGuild()).getAudioPlayer().getPlayingTrack().getInfo().title;
        String author = music.getPlayer(commandEvent.getGuild()).getAudioPlayer().getPlayingTrack().getInfo().author;

        EmbedBuilder b = new EmbedBuilder();

        b.setTitle("Current title :")
                .addField(title,author,true);
        commandEvent.getTextChannel().sendMessage(b.build());
    }
}
