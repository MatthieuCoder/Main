

import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.commandclient.examples.PingCommand;
import com.jagrosh.jdautilities.commandclient.examples.ShutdownCommand;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import fr.matthis974jump.checker.CheckerServer;
import fr.matthis974jump.checker.CheckerUtils;
import fr.matthis974jump.configbot.ConfigBot;
import fr.matthis974jump.configbot.FileManager;
import fr.matthis974jump.configbot.GsonManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Runnable {
    private  JDA jda;
    public Main(String s, boolean isFolder) {
        File file = new File(s);

        try {
            FileManager.createfile(file);
            GsonManager gm = new GsonManager();
            gm.initGson();
            ConfigBot cb = gm.deserialize(FileManager.loadfile(file));
            System.out.println("Loading bot with "+s);

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
            client.setPrefix("!!check "+file.getName().replace(".json","").replaceFirst("bot","")+" ");

            // adds commands
            client.addCommands(


                    // command to check bot latency
                    new PingCommand(),
                    new ShutdownCommand()

            );

            jda = new JDABuilder(AccountType.BOT).setToken(cb.getToken()).setGame(Game.of(cb.getGame()))
                    .addEventListener(waiter)
                    .addEventListener(client.build())
                    .buildBlocking();
            for(Guild g : jda.getGuilds()){
                g.getController().setNickname(g.getMember(jda.getSelfUser()),cb.getName()).complete();
            }
            jda.getPresence().setGame(Game.of(cb.getGame()));
            CheckerUtils.viderMessage(jda.getTextChannelById(cb.getIdSallon()).getMessageById(cb.getIdMessage()).complete());
            Thread ct = new Thread(new CheckerServer(jda.getTextChannelById(cb.getIdSallon()).getMessageById(cb.getIdMessage()).complete(),cb));
            ct.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Main[] bots = new Main[]{new Main("bot1.json", true)};
        List<Thread> threads = new ArrayList<Thread>();
        for (Main m:
                bots) {
            threads.add(new Thread(m,"bot-"));
        }
        for (Thread t:
                threads) {
            t.start();
        }
        final Scanner sc = new Scanner(System.in);
        while(!sc.next().equalsIgnoreCase("exit")){ sc.nextLine(); }

        for (Main m:
                bots) {
            m.shutdown();
        }
        for (Thread t:
                threads){
            t.interrupt();
        }
        System.out.println("Stopped ... Good bye");
        System.exit(0);
    }

    private void shutdown() {
        jda.shutdown();
    }

    public void run() {

    }
}
