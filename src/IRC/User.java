package IRC;

import java.io.IOException;

public class User {
    private String nick;
    private String fullname;
    private String address;
    private boolean register;
    private Client client;
    private boolean operator = false;

    public User(String nick, String fullname, String address, Client client, boolean register) {
        this.nick = nick;
        this.fullname = fullname;
        this.address = address;
        this.client = client;
        this.register = register;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        User temp = (User) obj;
        return temp.getAddress().equals(this.address);
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public boolean isOperator() {
        return operator;
    }

    public void setOperator(boolean operator) {
        this.operator = operator;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void sendMessage(String message) throws IOException {
        client.tell(message, null);
    }
}
