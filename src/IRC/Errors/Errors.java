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
        errors.put(433, new Error(433, host + " ERR_NICKNAMEINUSE", "* <nick> :<nick> is already in use."));
        errors.put(431, new Error(431, host + " ERR_NONICKNAMEGIVEN", "* <nick> :No nickname given."));
        errors.put(462, new Error(462, host + " ERR_ALREADYREGISTRED", "<nick> :You are already in."));
        errors.put(130, new Error(130, host + " ERR_NEEDMOREPARAMS", "<command> :Not enough parameters or to many parameter"));
        errors.put(451, new Error(451, host + " ERR_NOTREGISTERED", ":You have not registered" ));
        errors.put(411, new Error(411, host + " ERR_NORECIPIENT", "<nick> :No recipient found."));
        errors.put(412, new Error(412, host + " ERR_NOTEXTTOSEND", "<nick> :No text to send found."));
        errors.put(401, new Error(401, host + " ERR_NOSUCHNICK", "<nick>: User not found"));
        errors.put(421, new Error(421, host + " ERR_UNKNOWNCOMMAND", "<command> :Unknown command"));

    }

    public String getMessage(Integer code, String nick, String user, String host, String command) {
        if (nick == null) nick = "";
        if (user == null) user = "";
        if (host == null) host = "";
        if (command == null) command = "";
        Error ret = errors.get(code);
        return ret.getError() + " " + ret.getMessage().replace("<nick>", nick)
                .replace("<user>", user)
                .replace("<host>", host)
                .replace("<command>", command);
    }
}

