package IRC.Commands;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private List<Command> commands = new ArrayList<Command>();

    public Commands() {
        commands.add(new Command("NOT FOUND"));
        commands.add(new Command("NICK"));
        commands.add(new Command("USER"));
    }

    public List<Command> getCommands() {
        return commands;
    }
}
