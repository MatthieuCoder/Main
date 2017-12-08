package fr.matthis974jump.bots.surv.config.obj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Config {
    private String token;
    private Set<String> list = new HashSet<String>();
    private String name;
    private String game;
    public Config(String token,String game) {
        this.token = token;
        this.list = list;
        this.name = name;
        this.game = game;
    }

    public String getToken() {
        return token;
    }

    public Set<String> getList() {
        return list;
    }

    public String getName() {
        return name;
    }

    public String getGame() {
        return game;
    }
}
