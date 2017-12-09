package fr.matthis974jump.bots.surv;

import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.commandclient.examples.PingCommand;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import fr.matthis974jump.bots.surv.commands.clearCommand;
import fr.matthis974jump.bots.surv.commands.getadvrCommand;
import fr.matthis974jump.bots.surv.commands.infoCommand;
import fr.matthis974jump.bots.surv.config.obj.Config;
import fr.matthis974jump.bots.surv.config.obj.FileManager;
import fr.matthis974jump.bots.surv.config.obj.SerializerConfig;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.io.File;
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
        FileManager.createfile(conf);
        SerializerConfig confs = new SerializerConfig();
        confs.initGson();
        config = confs.deserialize(FileManager.loadfile(conf));
        this.token = config.getToken();
        this.bdd = new bdd(new ConnectCredidentials("jdbc:mysql://","discord","root","root","localhost","3306"),this);
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
// define an eventwaiter, dont forget to add this to the JDABuilder!
        EventWaiter waiter = new EventWaiter();

        // define a command client
        CommandClientBuilder client = new CommandClientBuilder();

        // The default is "Type !!help" (or whatver prefix you set)
        client.useDefaultGame();

        // sets the owner of the bot
        client.setOwnerId("314354049023737857");

        // sets emojis used throughout the bot on successes, warnings, and failures
        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");

        // sets the bot prefix
        client.setPrefix("!!");

        // adds commands
        client.addCommands(


                // command to check bot latency
                new PingCommand(),

                new infoCommand(waiter),

                new clearCommand(waiter),

                new getadvrCommand(waiter,this)
        );

        jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(this,new WorldsListener(this))
                .addEventListener(waiter)
                .addEventListener(client.build())
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

        if(event instanceof ShutdownEvent) {
            inRunning = false;
        }
    }

    public Config getConfig() {
        return config;
    }

    public bdd getMysql() {
        return bdd;
    }

    public JDA getJDA() {
        return jda;
    }
}
