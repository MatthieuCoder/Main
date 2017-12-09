package fr.matthis974jump.bots.surv;

import fr.matthis974jump.bots.surv.config.obj.Config;
import fr.matthis974jump.bots.surv.config.obj.FileManager;
import fr.matthis974jump.bots.surv.config.obj.SerializerConfig;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main implements Runnable , EventListener{
    private final String token;
    private final bdd bdd;
    private JDA jda;
    private final Scanner s = new Scanner(System.in);
    private static Logger  l = Logger.getLogger("MainBot");
    private boolean inRunning;
    private Config config;
    public Main() throws Exception {
        File conf = new File("config.json");
        SerializerConfig confs = new SerializerConfig();
        confs.initGson();
        config = confs.deserialize(FileManager.loadfile(conf));
        this.token = config.getToken();
        this.bdd = new bdd(new ConnectCredidentials("jdbc:myql://","discord","root","root","localhost","3306"));
        bdd.connect();
        connect();

    }

    public static void main(String[] args){
        try {
            new Thread(new Main(),"Main").start();
        } catch (Exception e) {
            Main.l.log(Level.OFF,"Exception detected",e);
        }
    }
    private void connect() throws Exception{
        jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(this,new WorldsListener(this))
                .buildBlocking();

        this.inRunning = true;
        for (Guild g:
             jda.getGuilds()) {
            g.getController().setNickname(g.getMember(jda.getSelfUser()),config.getName()).complete();
        }
        jda.getPresence().setGame(Game.of(config.getGame()));

    }
    public void run() {
        while(inRunning){
            if(s.hasNext()) if(s.nextLine().equalsIgnoreCase("exit")) inRunning = false;
        }
        jda.shutdown();
        s.close();
        System.out.println("OFF --- !");
        System.exit(0);
    }

    public void onEvent(Event event) {
        if(event instanceof ReadyEvent) l.info("The System is Ready ! :)");
    }

    public Config getConfig() {
        return config;
    }
}
