package IRC.Replies;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Replies {

    private Map<Integer, Reply> replies = new HashMap<Integer, Reply>();
    private String host;

    public Replies(String host) throws UnknownHostException {
        this.host = host;
        setCodes();
    }

    private void setCodes() {
        replies.put(001, new Reply(001, host + " RPL_WELCOME", "Welcome to the Internet Relay Network" + "\n" + "<parameter1>!<parameter2>@<parameter3>"));
    }

    public String getMessage(Integer code, String para1, String para2, String para3) {
        if (para1 == null) para1 = "";
        if (para2 == null) para2 = "";
        if (para3 == null) para3 = "";

        Reply rep = replies.get(code);
        return rep.getMessage().replace("<parameter1>", para1)
                                .replace("<parameter2>", para2)
                                .replace("<parameter3>", para3);
    }
}
