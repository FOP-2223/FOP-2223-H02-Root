package h02.h3.h3_4;
import fopbot.Robot;
import fopbot.World;
import h02.Main;
import h02.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.*;
import static h02.h3.H3Utils.*;
import static h02.Utils.WORLD_WIDTH;
import static h02.Utils.WORLD_HEIGHT;

@TestForSubmission("h02")
public class SwapRobotsTest {

    private static final String PATH_TO_CSV = "/h3/h3_4/RobotArrays.csv";

    private static final Main main = new Main();

    @BeforeAll
    static void setup() {
        World.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        World.setDelay(0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testSwapping(String robotArrayAsString, int i, int j, int k) {
        Robot[] array = convertStringToRobotArray(robotArrayAsString);
        Robot[] reference = array.clone();

        main.swapRobots(new int[]{i, j, k}, array);

        String generalInformation = Utils.getGeneralInfo(
            "Array before: " + convertRobotArrayToString(reference) +
                "\nArray afterwards: " + convertRobotArrayToString(array)
        );

        assertSame(
            reference[i],
            array[j],
            generalInformation +
                "Expected Robot from index i = " + i + " to now be at index j = " + j + "!"
        );

        assertSame(
            reference[j],
            array[k],
            generalInformation +
                "Expected Robot from index j = " + j + " to now be at index k = " + k + "!"
        );

        assertSame(
            reference[k],
            array[i],
            generalInformation +
                "Expected Robot from index k = " + k + " to now be at index i = " + i + "!"
        );

    }
}
