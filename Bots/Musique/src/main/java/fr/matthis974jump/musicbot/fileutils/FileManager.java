package fr.matthis974jump.musicbot.fileutils;


import java.io.*;

public class FileManager {

    public static void createfile(File f) throws IOException {
        if(!f.exists()){
            f.createNewFile();
            GsonManager m = new GsonManager();
            m.initGson();
            savefile(f,m.serialize(new ConfigBot("TOKEN HERE","Please Config the bot","Plesae Config","/null")));
        }
    }
    public static void savefile(File f, String cont) throws IOException{
        if(!f.exists()) createfile(f);
        FileWriter fw = new FileWriter(f);
        fw.write(cont);
        fw.flush();
        fw.close();

    }

    public static String loadfile(File f) throws IOException{
        if(!f.exists()) return "";
        BufferedReader bf = new BufferedReader(new FileReader(f));
        StringBuilder sb = new StringBuilder();

        String line;
        while((line = bf.readLine()) != null){
            sb.append(line);
        }
        bf.close();
        return sb.toString();
    }
}

