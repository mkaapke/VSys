package IRC.Errors;

import java.util.HashMap;
import java.util.Map;

public class Errors {

    private Map<Integer, Error> errors = new HashMap<>();
    private String host;

    public Errors(String host) {
        this.host = host;
        setCodes();
    }

    private void setCodes() {
        errors.put(433, new Error(433, host + " ERR_NICKNAMEINUSE", "* <nick> :Nickname is already in use."));
    }

    public String getMessage(Integer code, String nick, String user, String host, String command) {
        if (nick == null) nick = "";
        Error ret = errors.get(code);
        return ret.getMessage().replace("<nick>", nick);
    }
}

