package h02.h1;

public class H1Utils {

    public static boolean[][] stringToPatternConverter(String patternAsString) {
        String[] subStrings = patternAsString.split("/");
        int height = subStrings.length;
        int width = subStrings[0].length();
        boolean[][] thePattern = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                thePattern[i][j] = subStrings[i].charAt(j) == '1';
            }
        }

        return thePattern;
    }

    public static String booleanArrayToString(boolean[][] pattern) {
        StringBuilder builder = new StringBuilder("\n");

        for (int i = 0; i < pattern.length; i++) {
            builder.append("[");
            for (int j = 0; j < pattern[i].length; j++) {
                builder.append(pattern[i][j] ? "T" : "F");
                if (j + 1 != pattern[i].length)
                    builder.append(", ");
            }
            builder.append("]\n");
        }

        return builder.toString();
    }

    public static boolean[][] getWorldSizeRobotPattern(boolean[][] randomPattern, int width, int height) {
        boolean[][] pattern = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y < randomPattern.length && x < randomPattern[0].length)
                    pattern[y][x] = randomPattern[y][x];
            }
        }

        return pattern;
    }

}
