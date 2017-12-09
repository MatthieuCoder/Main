package fr.matthis974jump.bots.surv;

import com.google.gson.Gson;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

// MATTHIS974JUMP CODE //
// bdd 09/12/2017 08:48 //
// by claud //
public class bdd {
    private Connection mc;
    private String url,user,password;

    public bdd(ConnectCredidentials cs) {
        this.url = cs.getURL();
        this.user = cs.getPassword();
        this.password = cs.getPassword();
    }

    public void connect() throws SQLException{
        this.mc = DriverManager.getConnection(url,user,password);
    }
    public boolean inConnected(){
        return (mc == null);
    }
    public void disconnect() throws SQLException {
        if(inConnected()) {
            mc.close();
            mc = null;
        }
    }
    public void addAdvertissementToMember(Member m, Advertissements a, Message msg) throws SQLException{
        PreparedStatement ps = mc.prepareStatement("INSERT INTO `adv` (`id`, `user`, `guild`, `for`, `date`, `salon_id`) VALUES (NULL, '"+m.getUser().getId()+"', '"+m.getGuild().getId()+"', '"+a.getWhy()+"', '"+new Date(a.getDate().getTime())+"', '"+msg.getTextChannel().getId()+"');");
        ps.executeUpdate();
    }
}
