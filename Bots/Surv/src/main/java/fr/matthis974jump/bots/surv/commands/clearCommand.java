package fr.matthis974jump.bots.surv.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class clearCommand extends Command {
    private final EventWaiter waiter;
    public clearCommand(EventWaiter waiter)
    {
        this.waiter = waiter;
        this.name = "clear";
        this.aliases = new String[]{"delete","effacer"};
        this.help = "says hello and waits for a response";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Combien de lignes a supprimer ?");
        // wait for a response
        waiter.waitForEvent(MessageReceivedEvent.class,
                // make sure it's by the same user, and in the same channel
                e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()),
                // respond, inserting the name they listed into the response
                    e -> {
                        int n = Integer.getInteger(e.getMessage().getContent());
                        n = n*10;
                        for (int i = 0;i != n;i++){
                            e.getTextChannel().sendMessage("\n\n .").queue();
                        }


                    },
                // if the user takes more than a minute, time out
                10, TimeUnit.SECONDS, () -> event.reply("Désolé mais tu as été trop long"));
    }
}
