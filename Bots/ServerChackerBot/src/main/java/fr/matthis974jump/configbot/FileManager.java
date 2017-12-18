package fr.matthis974jump.configbot;


import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileManager {

    public static void createfile(File f) throws IOException {
        if(!f.exists()){
            f.createNewFile();
            GsonManager m = new GsonManager();
            m.initGson();
            Set<HostToCheck> hostsbase = new HashSet <HostToCheck>();
            hostsbase.add(new HostToCheck("google.com",80,true,"Google"));
            savefile(f,m.serialize(new ConfigBot("TOKEN HERE","Please Config the bot","Checkeur de serveurs",hostsbase)));
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

