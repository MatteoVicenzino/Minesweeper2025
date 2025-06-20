package ms;

import ms.CLI.CLIHandler;
import ms.commands.CommandParser;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        CLIHandler cliHandler = new CLIHandler(parser);
        cliHandler.start();
    }
}