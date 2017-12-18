package fr.matthis974jump.configbot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonManager {

    public Gson gson;

    public void initGson(){
        gson = newGsonInstance();
    }

    public static Gson newGsonInstance(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
    }

    public String serialize(ConfigBot c){
        return gson.toJson(c);
    }

    public ConfigBot deserialize(String t){
        return gson.fromJson(t,ConfigBot.class);
    }
}
