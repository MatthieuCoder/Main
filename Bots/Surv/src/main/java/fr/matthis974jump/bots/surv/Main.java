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
import java.util.logging.Logger;

public class Main implements Runnable , EventListener{
    private final String token;
    private JDA jda;
    private final Scanner s = new Scanner(System.in);
    private static Logger  l = Logger.getLogger("MainBot");
    private boolean inRunning;

    public Main(String token) throws Exception {
        this.token = token;
        connect();
    }

    public static void main(String[] args){
        try {
            new Thread(new Main("MzgwMjUwNzIzNDE1NDkwNTYz.DQsgWQ.pMxSgywLn2qZgY4mFyIzt2G2aQs"),"Main").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void connect() throws Exception{
        // Note: It is important to register your ReadyListener before building

        File conf = new File("config.json");
        SerializerConfig confs = new SerializerConfig();
        confs.initGson();
        Config config = confs.deserialize(FileManager.loadfile(conf));

        jda = new JDABuilder(AccountType.BOT)
                .setToken(config.getToken())
                .addEventListener(this)
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
        System.out.println("ByeBye");
        System.exit(0);
    }

    public void onEvent(Event event) {
        if(event instanceof ReadyEvent) l.info("The System is Ready ! :)");
    }
}
