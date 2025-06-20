package ms;

import ms.CLI.CLIHandler;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        CLIHandler cliHandler = new CLIHandler(parser);
        cliHandler.start();
    }
}