package fr.matthis974jump.musicbot.music;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.io.File;

public class Utils {
    public static void loadFolder(File folder, MusicManager am, TextChannel tc){
        for (File cont : folder.listFiles()){
            if(cont.getName().endsWith(".mp3")){
                am.loadTrack(tc,cont.getAbsolutePath());
            }
        }
    }
}
