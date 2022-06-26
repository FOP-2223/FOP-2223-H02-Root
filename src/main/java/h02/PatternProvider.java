package h02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PatternProvider {

    private final boolean[][] pattern;

    public PatternProvider(String filename) {
        String pathToFile = new File("").getAbsolutePath().concat("/" + filename);

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            String[] patternAsStrings = sb.toString().replaceAll(" ", "").split("\n");

            this.pattern = stringsToPattern(patternAsStrings);
        } catch (IOException e) {
            throw new RuntimeException("Could not read lines!");
        }
    }

    private boolean[][] stringsToPattern(String[] patternAsStrings) {
        boolean[][] pattern = new boolean[patternAsStrings.length][patternAsStrings[0].length()];

        for (int i = patternAsStrings.length - 1; i >= 0; i--) {
            char[] chars = patternAsStrings[i].toCharArray();
            for (int j = 0; j < chars.length; j++)
                pattern[patternAsStrings.length - 1 - i][j] = chars[j] == '1';
        }

        return pattern;
    }

    public boolean[][] getPattern() {
        return pattern;
    }

}
