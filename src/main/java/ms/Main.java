package ms;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        Game game = new Game(Difficulty.EASY);
        CLIHandler cliHandler = new CLIHandler(parser);

        cliHandler.start();
    }
}