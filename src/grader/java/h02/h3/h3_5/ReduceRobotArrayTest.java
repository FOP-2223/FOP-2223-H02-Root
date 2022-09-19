package h02.h3.h3_5;

import fopbot.Robot;
import fopbot.World;
import h02.Main;
import h02.h3.H3Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static h02.Utils.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReduceRobotArrayTest {

    private static final String PATH_TO_CSV = "/h3/h3_5/RobotArrays.csv";

    private static final Main main = new Main();

    @BeforeAll
    static void setup() {
        World.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        World.setDelay(0);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testSize(String arrayAsString, int numberRobots) {
        Robot[] robots = H3Utils.convertStringToRobotArrayWithCoordinates(arrayAsString);
        int numberNulls = WORLD_WIDTH * WORLD_HEIGHT - numberRobots;
        int expectedSize = robots.length - numberNulls;
        int actualSize = main.reduceRobotArray(robots, numberNulls).length;

        assertEquals(
            expectedSize,
            actualSize,
            "Expected array " + Arrays.toString(robots) + " to have " + expectedSize +
                " number of components after method call, but it has " + actualSize
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testRobotsAfterResize(String arrayAsString, int numberRobots) {
        Robot[] robots = H3Utils.convertStringToRobotArrayWithCoordinates(arrayAsString);
        List<Robot> robotsBefore = Arrays.stream(robots).filter(Objects::nonNull).toList();
        int numberNulls = WORLD_WIDTH * WORLD_HEIGHT - numberRobots;
        Robot[] resize = main.reduceRobotArray(robots, numberNulls);
        List<Robot> robotsAfter = Arrays.stream(resize).toList();

        assertEquals(
            robotsBefore,
            robotsAfter,
            "Expected robots in returned array to have exactly the same order as before!"
        );
    }

}
