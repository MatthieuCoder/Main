package fr.matthis974jump.bots.surv.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import fr.matthis974jump.bots.surv.Advertissements;
import fr.matthis974jump.bots.surv.Main;
import net.dv8tion.jda.core.EmbedBuilder;

import java.sql.SQLException;

public class getadvrCommand extends Command {

    private final EventWaiter waiter;
    private final Main main;

    public getadvrCommand(EventWaiter waiter, Main m)
    {
        this.main = m;
        this.waiter = waiter;
        this.name = "advertissements";
        this.aliases = new String[]{"info","casier"};
        this.help = "says hello and waits for a response";
    }
    @Override
    protected void execute(CommandEvent commandEvent) {
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("*Liste des faits et reports :  @"+commandEvent.getMessage().getMentionedUsers().get(0).getName()+"*");
        try {
            Advertissements[] advr = main.getMysql().getAdvr(commandEvent.getMessage().getMentionedUsers().get(0),commandEvent.getGuild());
            if(advr.length == 0){
                builder.addField("*Rien :)*","",true);
            }else{
                for(Advertissements advertissements : advr){
                    builder.addField(":arrow_forward: "+advertissements.getWhy(),advertissements.getDate().toString(),false);
                    if(builder.build().getLength()  > 3000){
                        commandEvent.reply(builder.build());
                        builder = new EmbedBuilder();
                    }
                }

            }

            commandEvent.reply(builder.build());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
