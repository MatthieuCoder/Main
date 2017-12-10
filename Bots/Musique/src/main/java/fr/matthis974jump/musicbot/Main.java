package fr.matthis974jump.musicbot;

import fr.matthis974jump.musicbot.fileutils.ConfigBot;
import fr.matthis974jump.musicbot.fileutils.FileManager;
import fr.matthis974jump.musicbot.fileutils.GsonManager;
import fr.matthis974jump.musicbot.music.MusicManager;
import fr.matthis974jump.musicbot.music.Utils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Runnable {
    private  JDA jda;
    private MusicManager manager = new MusicManager();
    public Main(String s) {
        File file = new File(s);

        try {
            FileManager.createfile(file);
            GsonManager gm = new GsonManager();
            gm.initGson();
            ConfigBot cb = gm.deserialize(FileManager.loadfile(file));
            System.out.println("Loading bot with "+s);

            jda = new JDABuilder(AccountType.BOT).setToken(cb.getToken()).setGame(Game.of(cb.getGame())).buildBlocking();
            for(Guild g : jda.getGuilds()){
                g.getController().setNickname(g.getMember(jda.getSelfUser()),cb.getName()).complete();
            }
            jda.getGuildById("374892234597859330").getAudioManager().openAudioConnection(jda.getVoiceChannelById(cb.getIdchannel()));
            Utils.loadFolder(new File(cb.getMusicpath()),manager,jda.getTextChannelById("389301847564877834"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Main[] bots = new Main[]{new Main("bot1.json"),new Main("bot2.json"),new Main("bot3.json")};
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

    }

    public void run() {

    }
}
