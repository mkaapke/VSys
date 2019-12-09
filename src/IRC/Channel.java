package IRC;

import java.util.List;

public class Channel {
    private String name;
    private String topic;
    private List<User> users;

    public Channel(String name, String topic, List<User> users) {
        this.name = name;
        this.topic = topic;
        this.users = users;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
