package fr.matthis974jump.configbot;

// MATTHIS974JUMP CODE //
// HostToCheck 15/12/2017 21:09 //
// by claud //
public class HostToCheck {
    private String adress;
    private int port;
    private boolean enabled;
    private String name;
    public HostToCheck(String adress, int port, boolean enabled,String name) {
        this.adress = adress;
        this.port = port;
        this.enabled = enabled;
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public int getPort() {
        return port;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }
}
