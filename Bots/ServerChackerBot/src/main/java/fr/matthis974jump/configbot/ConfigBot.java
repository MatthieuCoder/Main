package fr.matthis974jump.configbot;

import java.util.HashSet;
import java.util.Set;

public class ConfigBot {
    private String token;
    private String name;
    private String game;
    private String idMessage;
    private String idSallon;
    private Set<HostToCheck> hosts = new HashSet<HostToCheck>();
    public ConfigBot(String token,String game,String name,Set<HostToCheck> host) {
        this.token = token;
        this.name = name;
        this.game = game;
        this.hosts = host;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getGame() {
        return game;
    }

    public String getIdMessage() {
        return idMessage;
    }

    public String getIdSallon() {
        return idSallon;
    }

    public Set <HostToCheck> getHosts() {
        return hosts;
    }
}
