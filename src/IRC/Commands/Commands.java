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
    }

    public List<Command> getCommands() {
        return commands;
    }
}
