package IRC.Replies;

import java.util.HashMap;
import java.util.Map;

public class Replies {

    private Map<Integer, Reply> replies = new HashMap<Integer, Reply>();
    private String host;
    private String time;

    public Replies(String host, String time) {
        this.host = host;
        this.time = time;
        setCodes();
    }

    private void setCodes() {
        replies.put(001, new Reply(001, host + " RPL_WELCOME", ":Welcome to the Internet Relay Network!<parameter1>@<parameter2>"));
        replies.put(999, new Reply(999, host + " RPL_NICKCHANGE", ":<parameter1> Is now <parameter2>!"));
        replies.put(002, new Reply(002, host + " RPL_YOURHOST", ":Your host is <parameter3>"));
        replies.put(003, new Reply(003, host + " RPL_CREATED", ":This Server was created at " + time ));
        replies.put(004, new Reply(004, host + " RPL_MYINFO", "Max ao mtov."));
        replies.put(311, new Reply(311, host + " RPL_WHOISUSER", "<parameter1> <parameter1>@<parameter2> <parameter2> * :<parameter3>"));
        replies.put(312, new Reply(312, host + " RPL_WHOISSERVER", "<parameter1> <parameter2> :<parameter3>"));
        replies.put(318, new Reply(318, host + " RPL_ENDOFWHOIS", "<parameter1> :End of WHOIS list"));
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
