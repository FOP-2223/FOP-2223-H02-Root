package h02.h3.h3_4;
import fopbot.Robot;
import fopbot.World;
import h02.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.h3.H3Utils.*;
import static h02.Utils.WORLD_WIDTH;
import static h02.Utils.WORLD_HEIGHT;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
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

        var context = contextBuilder()
            .add("Robot-Array before swapping", robotArrayAsString)
            .add("Index i", i)
            .add("Index j", j)
            .add("Index k", k)
            .add("Robot-Array after swapping", convertRobotArrayToString(array))
            .build();

        assertSame(
            reference[i],
            array[j],
            context,
            r -> String.format("Expected Robot from index i = %d to now be at index j = %d!", i, j)
        );

        assertSame(
            reference[j],
            array[k],
            context,
            r -> String.format("Expected Robot from index j = %d to now be at index k = %d!", j, k)
        );

        assertSame(
            reference[k],
            array[i],
            context,
            r -> String.format("Expected Robot from index k = %d to now be at index i = %d!", k, i)
        );
    }
}
