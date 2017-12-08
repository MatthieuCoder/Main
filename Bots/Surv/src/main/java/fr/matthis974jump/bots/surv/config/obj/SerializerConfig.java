package fr.matthis974jump.bots.surv.config.obj;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializerConfig {

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

    public String serialize(Config c){
        return gson.toJson(c);
    }

    public Config deserialize(String t){
        return gson.fromJson(t,Config.class);
    }
}
