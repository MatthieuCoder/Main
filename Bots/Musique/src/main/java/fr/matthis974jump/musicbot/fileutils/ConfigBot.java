package fr.matthis974jump.musicbot.fileutils;

import java.util.HashSet;
import java.util.Set;

public class ConfigBot {
    private String token;
    private String name;
    private String game;
    private String musicpath;
    private String idchannel;
    public ConfigBot(String token,String game,String name,String musicpath) {
        this.token = token;
        this.name = name;
        this.game = game;
        this.musicpath = musicpath;
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

    public String getMusicpath() {
        return musicpath;
    }

    public String getIdchannel() {
        return idchannel;
    }
}
