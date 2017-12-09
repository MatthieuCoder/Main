package fr.matthis974jump.bots.surv;

// MATTHIS974JUMP CODE //
// ConnectCredidentials 09/12/2017 08:52 //
// by claud //
public class ConnectCredidentials {
    private String baseurl,database,user,password,host,port;

    public ConnectCredidentials(String baseurl, String database, String user, String password, String host, String port) {
        this.baseurl = baseurl;
        this.database = database;
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public String getURL() {
        return new StringBuilder(baseurl).append(host).append("/").append(database).toString();
    }

    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }
}
