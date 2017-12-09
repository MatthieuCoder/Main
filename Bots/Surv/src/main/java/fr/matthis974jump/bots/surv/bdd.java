package fr.matthis974jump.bots.surv;

import com.google.gson.Gson;
import net.dv8tion.jda.core.entities.Member;

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
    public Advertissements[] getAdvertissements(Member m) throws SQLException{
        PreparedStatement ps = mc.prepareStatement("SELET * FROM 'users_adrvs' WHERE 'user'='"+m.getUser().getId()+"'");
        ResultSet rs = ps.executeQuery();
        ArrayList<Advertissements> adv = new ArrayList <Advertissements>();
        Advertissements drv;
        while(rs.next()){
            Advertissements d = new Advertissements();
            d.setBy(new Gson().fromJson(rs.getString("by"),Member.class));
            d.setDate(rs.getDate("date"));
            d.setId(rs.getInt("id"));
            d.setWhy(rs.getString("why"));
        }
        return ((Advertissements[])adv.toArray());
    }
}
