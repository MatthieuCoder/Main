package fr.matthis974jump.musicbot.music.commands;

// MATTHIS974JUMP CODE //
// QueueCommand 13/12/2017 11:19 //
// by claud //

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.matthis974jump.musicbot.music.MusicManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QueueCommand extends Command {
    private final EventWaiter waiter;
    private final MusicManager music;

    public QueueCommand(EventWaiter waiter,MusicManager mm)
    {
        this.music = mm;
        this.waiter = waiter;
        this.name = "queue";
        this.aliases = new String[]{"liste","ajouer"};
        this.help = "says hello and waits for a response";
    }
    @Override
    protected void execute(CommandEvent commandEvent) {
        List<MessageEmbed> arl = new ArrayList<>();
        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.BLUE);
        int i = 1;
        emb.appendDescription(music.getPlayer(commandEvent.getGuild()).getListener().getTrackSize()+" chansons dans la queue");
        for(AudioTrack t : music.getPlayer(commandEvent.getGuild()).getListener().getTracks()){
            int totalSecs = (int) t.getDuration();
            int seconds;
            int minutes;
            int hours;
            hours = totalSecs / 3600;
            minutes = (totalSecs % 3600) / 60;
            seconds = totalSecs % 60;

            String timeString = String.format("%02d Heure(s),%02d Minute(s):%02d Seconde(s)", hours, minutes, seconds);
            emb.addField(":musical_note:  **->** "+t.getInfo().title,"Queue : "+i+", "+t.getInfo().title +" **>**"+t.getInfo().author+" \n Duration : "+timeString,false);
            i++;
            if(emb.build().getLength()> 2000){
                arl.add(emb.build());
                emb = new EmbedBuilder();
                emb.setColor(Color.BLUE);
            }
            if(i > 10){
               break;
            }
        }
        for (MessageEmbed e : arl){
            commandEvent.getTextChannel().sendMessage(e).queue();
        }
    }
}

