package IRC.Replies;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Replies {

    private Map<Integer, Reply> replies = new HashMap<Integer, Reply>();
    private String host;
    private String time;

    public Replies(String host, String time) throws UnknownHostException {
        this.host = host;
        this.time = time;
        setCodes();
    }

    private void setCodes() {
        replies.put(001, new Reply(001, host + " RPL_WELCOME", ":Welcome to the Internet Relay Network!<parameter1>@<parameter2>"));
        replies.put(999, new Reply(999, host + " RPL_NICKCHANGE", ":<parameter1> Is now <parameter2>!"));
        replies.put(002, new Reply(002, host + " RPL_YOURHOST", ":Your host is <parameter1>"));
        replies.put(003, new Reply(003, host + " RPL_CREATED", ":This Server was created at " + time ));
        replies.put(004, new Reply(004, host + " RPL_MYINFO", "Max ao mtov."));
    }

    public String getMessage(Integer code, String para1, String para2, String para3) {
        if (para1 == null) para1 = "";
        if (para2 == null) para2 = "";
        if (para3 == null) para3 = "";

        Reply rep = replies.get(code);
        return rep.getReply() + " " + rep.getMessage().replace("<parameter1>", para1)
                                                        .replace("<parameter2>", para2)
                                                        .replace("<parameter3>", para3);
    }
}
