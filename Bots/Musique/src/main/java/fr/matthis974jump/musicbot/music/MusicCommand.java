package fr.matthis974jump.musicbot.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class MusicCommand extends Command {
    private final EventWaiter waiter;
    private final MusicManager music;

    public MusicCommand(EventWaiter waiter,MusicManager mm)
    {
        this.music = mm;
        this.waiter = waiter;
        this.name = "skip";
        this.aliases = new String[]{"passer"};
        this.help = "says hello and waits for a response";
    }

    @Override
    protected void execute(CommandEvent event)
    {
            music.skipTrack(event.getGuild());

    }
}
