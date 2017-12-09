package fr.matthis974jump.bots.surv;

import net.dv8tion.jda.core.entities.Member;

import java.util.Date;

// MATTHIS974JUMP CODE //
// Advertissements 09/12/2017 09:08 //
// by claud //
public class Advertissements {
    public Member by;
    public String why;
    public Date date;
    public int id;

    public void setBy(Member by) {
        this.by = by;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getBy() {
        return by;
    }

    public String getWhy() {
        return why;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }
}
