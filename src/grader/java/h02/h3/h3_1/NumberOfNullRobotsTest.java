package h02.h3.h3_1;

import fopbot.Robot;
import fopbot.World;
import h02.Main;
import h02.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.Utils.WORLD_WIDTH;
import static h02.Utils.WORLD_HEIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h02")
public class NumberOfNullRobotsTest {

    private static final String PATH_TO_CSV = "/h3/robotArrays.csv";

    @BeforeAll
    static void setup() {
        World.setSize(WORLD_WIDTH, WORLD_HEIGHT);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testNumberOfNullRobots(String robotArray) {
        RobotArrayProvider provider = new RobotArrayProvider(robotArray);

        int expectedNumberOfNullRobots = provider.numberOfNullElements;
        int actualNumberOfNullRobots = Main.numberOfNullRobots(provider.robots);

        assertEquals(
            expectedNumberOfNullRobots,
            actualNumberOfNullRobots,
            Utils.getGeneralInfo(provider.information) +
                "Expected number of null elements in array: " + expectedNumberOfNullRobots +
                ", actual number of null elements in array: " + actualNumberOfNullRobots
        );
    }

    private static class RobotArrayProvider {
        public Robot[] robots;
        public int numberOfNullElements = 0;

        public String information;

        RobotArrayProvider(String robotsAsString) {
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

            information =
                "Size of array: " + robots.length +
                    ", array: " + arrayToString();
        }

        String arrayToString() {
            StringBuilder builder = new StringBuilder("[");
            for (int i = 0; i < robots.length; i++) {
                builder.append(
                    robots[i] != null ? "robot" : "null"
                );
                if (i + 1 != robots.length) {
                    builder.append(", ");
                }
            }
            builder.append("]");
            return builder.toString();
        }
    }

}
