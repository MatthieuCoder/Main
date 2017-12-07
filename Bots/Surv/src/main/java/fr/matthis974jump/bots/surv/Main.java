package fr.matthis974jump.bots.surv;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter implements Runnable {
    private final String token;
    private final String channel;
    private final String path;

    public Main(String token, String channel, String pathtomusic) throws Exception {
        this.token = token;
        this.channel = channel;
        this.path = pathtomusic;
        connect();
    }

    public static void main(String[] args){
        try {
            new Thread(new Main("","",""),"Main").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void connect() throws Exception{
        // Note: It is important to register your ReadyListener before building
        JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(this)
                .buildBlocking();
    }
    public void run() {

    }

    @Override
    public void onMessageReceived(ReadyEvent e)
    {
        System.out.println("Ready");
    }
}
