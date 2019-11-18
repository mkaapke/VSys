package IRC;

public class User {
    private String nick;
    private String username;
    private String address;
    private Client client;

    public User(String nick, String username, String address, Client client) {
        this.nick = nick;
        this.username = username;
        this.address = address;
        this.client = client;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
