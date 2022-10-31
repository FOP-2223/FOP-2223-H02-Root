package h02.h3.h3_1;

import fopbot.Robot;
import fopbot.World;
import h02.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.Utils.WORLD_WIDTH;
import static h02.Utils.WORLD_HEIGHT;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission("h02")
public class NumberOfNullRobotsTest {

    private static final String PATH_TO_CSV = "/h3/h3_1/RobotArrays.csv";

    private static final Main main = new Main();

    RobotArrayProvider provider = new RobotArrayProvider("");

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
    void testNumberOfNullRobots(String robotArray) {

        var context = contextBuilder()
            .add("World width", WORLD_WIDTH)
            .add("World height", WORLD_HEIGHT)
            .add("Robot-Array", robotArray)
            .build();

        provider.reassign(robotArray);

        int expectedNumberOfNullRobots = provider.numberOfNullElements;
        int actualNumberOfNullRobots = main.numberOfNullRobots(provider.robots);

        assertEquals(
            expectedNumberOfNullRobots,
            actualNumberOfNullRobots,
            context,
            r -> String.format("Expected method to count %d null elements in array but it actually counted %d.", expectedNumberOfNullRobots, actualNumberOfNullRobots)
        );
    }

    private static class RobotArrayProvider {
        public Robot[] robots;
        public int numberOfNullElements;

        RobotArrayProvider(String robotsAsString) {
            reassign(robotsAsString);
        }

        void reassign(String robotsAsString) {
            numberOfNullElements = 0;

            robots = new Robot[robotsAsString.length()];

            for (int i = 0; i < robots.length; i++) {
                if (robotsAsString.charAt(i) == '0') {
                    numberOfNullElements++;
                    robots[i] = null;
                } else {
                    robots[i] = new Robot(0, 0);
                }
            }
        }
    }

}
