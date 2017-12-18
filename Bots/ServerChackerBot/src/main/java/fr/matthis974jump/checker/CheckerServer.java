package fr.matthis974jump.checker;

import fr.matthis974jump.configbot.ConfigBot;
import fr.matthis974jump.configbot.HostToCheck;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.util.Timer;
import java.util.TimerTask;

// MATTHIS974JUMP CODE //
// CheckerServer 15/12/2017 21:34 //
// by claud //
public class CheckerServer extends TimerTask {
    private final Message msg;
    private ConfigBot cb;
    /**
     * The action to be performed by this timer task.
     */
    public void run() {
        EmbedBuilder emb = new EmbedBuilder();
        emb.setTitle("Etat des Serveurs");
        int totalservs = cb.getHosts().size();
        int onlineservs = 0;
        int oflineservs = 0;
        for (HostToCheck h : cb.getHosts()){
            String mot;
            if(CheckerUtils.checkServer(h.getAdress(),h.getPort())){
                mot = "**:white_check_mark:  Online**";
                onlineservs++;
            }else{
                mot = "**:x: Offline**";
                oflineservs++;
            }
            emb.addField(h.getName(),mot,false);
        }
        emb.setDescription("_** Nombre de serveurs en ligne :"+onlineservs
        +"\n Nombre de serveurs hors ligne :"+oflineservs+
        "\n Nombre total de serveurs vérifiés :"+totalservs+""+
        "\n Pourcentage de serveurs en ligne :"+Integer.toString((100*onlineservs)/totalservs)+"% **_");
        msg.editMessage(emb.build()).queue();
    }

    public CheckerServer(Message msg, ConfigBot cb){
        this.cb = cb;
        this.msg = msg;
        Timer t  = new Timer();
        t.schedule(this,20);
    }
}
