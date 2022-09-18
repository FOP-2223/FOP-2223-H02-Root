package h02.h1.h1_1;

import h02.Main;
import h02.Utils;
import h02.h1.H1Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.Utils.WORLD_WIDTH;
import static h02.Utils.WORLD_HEIGHT;
import static h02.h1.H1Utils.convertArrayOfArrayOfBooleanToString;
import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
public class CountRobotsInPatternTest {

    private static final String PATH_TO_CSV = "/h1/h1_1/FittingPatterns.csv";

    private static final String PATH_TO_CSV_2 = "/h1/h1_1/UnfittingPatterns.csv";

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testFittingPattern(String patternAsString, int expected) {
        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        int actual = Main.countRobotsInPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        assertEquals(
            expected,
            actual,
            Utils.getGeneralInfo("Pattern:\n" + convertArrayOfArrayOfBooleanToString(pattern)) +
                "Expected method to return " + expected + " but it actually returned " + actual + "."
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV_2)
    void testUnFittingPattern(String patternAsString, int expected) {
        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        int actual = Main.countRobotsInPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        assertEquals(
            expected,
            actual,
            Utils.getGeneralInfo("Pattern:\n" + convertArrayOfArrayOfBooleanToString(pattern)) +
                "Expected method to return " + expected + " but it actually returned " + actual + "."
        );
    }

}
