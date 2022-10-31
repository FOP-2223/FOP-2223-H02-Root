package h02.h1.h1_1;

import h02.Main;
import h02.h1.H1Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.Utils.WORLD_WIDTH;
import static h02.Utils.WORLD_HEIGHT;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission("h02")
public class CountRobotsInPatternTest {

    private static final String PATH_TO_CSV = "/h1/h1_1/FittingPatterns.csv";

    private static final String PATH_TO_CSV_2 = "/h1/h1_1/UnfittingPatterns.csv";

    private static final Main main = new Main();

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testFittingPattern(String patternAsString, int expected) {
        testCounting(patternAsString, expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV_2)
    void testUnFittingPattern(String patternAsString, int expected) {
        testCounting(patternAsString, expected);
    }

    private void testCounting(String patternAsString, int expected) {
        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        int actual = main.countRobotsInPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        var context = contextBuilder()
            .add("World width", WORLD_WIDTH)
            .add("World height", WORLD_HEIGHT)
            .add("Pattern", patternAsString)
            .add("Number of robots", expected)
            .build();

        assertEquals(
            expected,
            actual,
            context,
            r -> String.format("Expected method to return %d but it actually returned %d.", expected, actual)
        );
    }

}
