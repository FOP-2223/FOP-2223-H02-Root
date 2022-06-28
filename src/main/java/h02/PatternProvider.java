package h02;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PatternProvider {

    private final boolean[][] pattern;

    public PatternProvider(String filename) throws IOException {
        final @Nullable InputStream patternStream = getClass().getResourceAsStream("/" + filename);
        if (patternStream == null) {
            throw new IOException("Could not find file " + filename + " in src/main/resources");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(patternStream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }

        String[] patternAsStrings = sb.toString().replaceAll(" ", "").split("\n");

        this.pattern = stringsToPattern(patternAsStrings);
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
