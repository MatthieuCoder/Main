package fr.matthis974jump.musicbot.music.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.matthis974jump.musicbot.music.MusicManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SkipCommand extends Command {
    private final EventWaiter waiter;
    private final MusicManager music;

    public SkipCommand(EventWaiter waiter,MusicManager mm)
    {
        this.music = mm;
        this.waiter = waiter;
        this.name = "skip";
        this.aliases = new String[]{"passer"};
        this.help = "says hello and waits for a response";
    }
    @Override protected void execute(CommandEvent event) { music.skipTrack(event.getGuild()); event.getMessage().addReaction(":ok:").queue();}}


