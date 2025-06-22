package ms;

import ms.commands.CommandParser;
import ms.view.CLIHandler;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        CLIHandler cliHandler = new CLIHandler(parser);
        cliHandler.run();
    }
}