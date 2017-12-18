package fr.matthis974jump.checker;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

// MATTHIS974JUMP CODE //
// CheckerUtils 15/12/2017 21:28 //
// by claud //
public class CheckerUtils {
    public static void viderMessage(Message m){
        m.editMessage(new EmbedBuilder().setTitle("**Loading ...**").setColor(Color.RED).build()).queue();
    }

    public static boolean checkServer(String ip,int port) {
        Socket socket;

        try {

            socket = new Socket(ip,port);
            if(socket.isConnected()) return true;
            socket.close();

        }catch (UnknownHostException e) {

            return false;
        }catch (IOException e) {
            return false;
        }
        return false;
    }
    }

