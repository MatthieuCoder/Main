package fr.matthis974jump.bots.surv.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class infoCommand extends Command {
    private final EventWaiter waiter;
    public infoCommand(EventWaiter waiter)
    {
        this.waiter = waiter;
        this.name = "hello";
        this.aliases = new String[]{"hi"};
        this.help = "says hello and waits for a response";
    }

    @Override
    protected void execute(CommandEvent event)
    {
        // ask what the user's name is
        event.reply("Bonjour quel est ton nom ?");

        // wait for a response
        waiter.waitForEvent(MessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel
                e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()),
                // respond, inserting the name they listed into the response
                e -> e.getChannel().sendMessage("Bonjour, `"+e.getMessage().getRawContent()+"`! Je suis `"+e.getJDA().getSelfUser().getName()+"`!").queue(),
                // if the user takes more than a minute, time out
                10, TimeUnit.SECONDS, () -> event.reply("Désolé mais tu as été trop long"));
    }

}
