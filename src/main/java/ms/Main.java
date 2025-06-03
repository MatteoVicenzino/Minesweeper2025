package ms;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        cliHandler.start();
    }
}