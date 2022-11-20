package h02.h3.h3_5;

import fopbot.Robot;
import fopbot.World;
import h02.Main;
import h02.h3.H3Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static h02.Utils.*;
import static h02.h3.H3Utils.*;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ReduceRobotArrayTest {

    private static final String PATH_TO_CSV = "/h3/h3_5/RobotArrays.csv";

    private static final Main main = new Main();

    @BeforeAll
    static void setup() {
        World.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        World.setDelay(0);
    }

    @BeforeEach
    void reset() {
        World.reset();
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testSize(String arrayAsString, int numberRobots) {
        Robot[] robots = H3Utils.convertStringToRobotArrayWithCoordinates(arrayAsString);
        int numberNulls = WORLD_WIDTH * WORLD_HEIGHT - numberRobots;
        int expectedSize = robots.length - numberNulls;

        call(
            () -> main.reduceRobotArray(robots, numberNulls),
            contextBuilder().add("Method", "reduceRobotArray()").build(),
            r -> "Call resulted in an error"
        );

        Robot[] resize = main.reduceRobotArray(robots, numberNulls);
        int actualSize = resize.length;

        var context = contextBuilder()
            .add("Robot-Array before", arrayAsString)
            .add("Number of robots", numberRobots)
            .add("Robot-Array after", convertRobotArrayToString(resize))
            .build();

        assertEquals(
            expectedSize,
            actualSize,
            context,
            r -> String.format("Expected array %s to have %d number of components after method call but it has %d!", Arrays.toString(robots), expectedSize, actualSize)
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testRobotsAfterResize(String arrayAsString, int numberRobots) {
        Robot[] robots = H3Utils.convertStringToRobotArrayWithCoordinates(arrayAsString);
        List<Robot> robotsBefore = Arrays.stream(robots).filter(Objects::nonNull).toList();
        int numberNulls = WORLD_WIDTH * WORLD_HEIGHT - numberRobots;

        call(
            () -> main.reduceRobotArray(robots, numberNulls),
            contextBuilder().add("Method", "reduceRobotArray()").build(),
            r -> "Call resulted in an error"
        );

        Robot[] resize = main.reduceRobotArray(robots, numberNulls);
        List<Robot> robotsAfter = Arrays.stream(resize).toList();

        var context = contextBuilder()
            .add("Robot-Array before", convertRobotArrayToString(robots))
            .add("Number of robots", numberRobots)
            .add("Robot-Array after", convertRobotArrayToString(resize))
            .build();

        assertEquals(
            robotsBefore,
            robotsAfter,
            context,
            r -> "Expected robots in returned array to have exactly the same order as before!"
        );
    }

}
