package IRC.Commands;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private List<Command> commands = new ArrayList<Command>();


    public Commands() {
        commands.add(new Command("NOT FOUND", 1, true));
        commands.add(new Command("QUIT", 2, false));
        commands.add(new Command("NICK", 2, true));
        commands.add(new Command("USER", 5, false));
        commands.add(new Command("PRIVMSG", 3, false));
        commands.add(new Command("NOTICE", 3, false));
        commands.add(new Command("PING", 1, true));
        commands.add(new Command("PONG", 1, true));
        commands.add(new Command("WHOIS", 2, true));
        commands.add((new Command("JOIN", 2, true)));
        commands.add((new Command("PART", 2, true)));
        commands.add((new Command("PART", 3, false)));
        commands.add((new Command("TOPIC", 2, true)));
        commands.add((new Command("TOPIC", 3, false)));
    }

    public List<Command> getCommands() {
        return commands;
    }
}
