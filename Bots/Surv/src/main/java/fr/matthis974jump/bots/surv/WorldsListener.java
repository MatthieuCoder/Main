package fr.matthis974jump.bots.surv;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WorldsListener implements EventListener{
    private final Main main;

    public WorldsListener(Main main) {
        this.main = main;
    }

    public void onEvent(Event event) {
        if(event instanceof MessageReceivedEvent) onGuildMessage(((MessageReceivedEvent)event));
        System.out.println(event.getClass().getSimpleName());
    }

    private void onGuildMessage(MessageReceivedEvent e) {
        ArrayList<String> ldw = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        System.out.printf(e.getMessage().getContent());
        for (String denied:
             main.getConfig().getList()) {
            if (e.getMessage().getContent().contains(denied)) {
                ldw.add(denied);
                sb.append(" ").append(denied);
            }
        }
        if(e.getMessage().mentionsEveryone()) {ldw.add("EVERYONE"); sb.append("Mention everyone");}
        if(e.getMessage().getContent().contains("discord.gg")) {ldw.add("INVITATION AUTRE SERVEUR"); sb.append("Invitation autre serveur");}
        if(ldw.size() > 0){
            e.getMessage().delete().complete();
            e.getMessage().getTextChannel().sendMessage(new EmbedBuilder().addField("ADVERTISSEMENT : MESSAGE IN CHAT",e.getAuthor().getName()+" got an advertissement :"+sb.toString(),false).setThumbnail(e.getAuthor().getAvatarUrl()).build()).complete().delete().completeAfter(10, TimeUnit.SECONDS);
        }
    }
}
