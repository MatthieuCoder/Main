package fr.matthis974jump.bots.surv;

import com.google.gson.Gson;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.awt.event.AdjustmentEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// MATTHIS974JUMP CODE //
// bdd 09/12/2017 08:48 //
// by claud //
public class bdd {
    private final Main m;
    private Connection mc;
    private String url,user,password;

    public bdd(ConnectCredidentials cs,Main m) {
        this.m = m;
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

    public Advertissements[] getAdvr(User u, Guild g) throws SQLException {
        PreparedStatement ps = mc.prepareStatement("SELECT * FROM `adv` WHERE `user`=\""+u.getId()+"\" AND `guild`=\""+g.getId()+"\"");

        ResultSet rs = ps.executeQuery();

        List<Advertissements> a = new ArrayList<>();

        while (rs.next()){
            Advertissements d = new Advertissements();
            d.setDate(rs.getDate("date"));
            d.setWhy(rs.getString("for"));
            a.add(d);
        }
        return a.toArray(new Advertissements[]{});
    }
}
