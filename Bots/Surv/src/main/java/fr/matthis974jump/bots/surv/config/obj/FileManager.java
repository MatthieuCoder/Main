package fr.matthis974jump.bots.surv.config.obj;

import java.io.*;

public class FileManager {

    public static void createfile(File f) throws IOException{
        if(!f.exists()){
            f.createNewFile();

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
