package ms.view;

/**
 * Provides ANSI color codes for console output.
 * Contains constants for colored text and backgrounds.
 */
public final class AnsiColors {

    public static final String RESET = "\033[0m";

    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String CYAN_BOLD = "\033[1;36m";

    public static final String BLUE_BRIGHT = "\033[0;94m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String PURPLE_BRIGHT = "\033[0;95m";
    public static final String YELLOW_BRIGHT = "\033[0;93m";

    public static final String WHITE_BACKGROUND = "\033[47m";
}