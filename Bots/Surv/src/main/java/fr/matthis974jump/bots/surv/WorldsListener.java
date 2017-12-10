package fr.matthis974jump.bots.surv;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

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
        ArrayList<Advertissements> ldw = new ArrayList<Advertissements>();
        String msg = e.getMessage().getContent().toLowerCase();
        for (String denied:
             main.getConfig().getList()) {

            if (msg.contains(denied)) {
                Advertissements ad = new Advertissements();
                ad.setWhy("Envoi de  : "+denied);
                ad.setBy(null);
                ad.setDate(Calendar.getInstance().getTime());
                ldw.add(ad);
            }

        }
        if(e.getMessage().mentionsEveryone() && !e.getGuild().getMember(e.getAuthor()).hasPermission(Permission.ADMINISTRATOR)) {
            Advertissements edv = new Advertissements();
            edv.setWhy("Mention @everyone");
            edv.setDate(Calendar.getInstance().getTime());
            ldw.add(edv);
        }

        if(e.getMessage().getContent().contains("discord.gg/")) {
            Advertissements edv = new Advertissements();
            edv.setWhy("Invitation autre Serveur");
            edv.setDate(Calendar.getInstance().getTime());
            ldw.add(edv);
        }
        if(ldw.size() > 0) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("SANCTION - VOCABULAIRE");
            builder.setTitle("SANCTION - VOCABULAIRE");
            for (Advertissements ad : ldw) {
                builder.addField("Mot ou terme interdit : ",ad.getWhy(),true);

                    CompletableFuture<String> completableFuture
                            = CompletableFuture.supplyAsync(() -> {
                        try {
                            main.getMysql().addAdvertissementToMember(e.getMember(),ad,e.getMessage());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        return "";
                    });
            }
            builder.setDescription("**("+ldw.size()+" avertissements ajoutés)**");
            CompletableFuture<String> completableFuture
                    = CompletableFuture.supplyAsync(() -> {
                        e.getMessage().delete().complete();
                e.getTextChannel().sendMessage(builder.build()).complete().delete().completeAfter(10L,TimeUnit.SECONDS); return ";";
                    });

        }

    }


    }

