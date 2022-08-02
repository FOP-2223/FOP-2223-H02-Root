package h02;

import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h02")
class H3_1 {

    @Test
    void testNumberOfNullRobots() {
        World.setSize(1, 1);
        int expectedNumberOfNullRobots;
        int actualNumberOfNullRobots;

        double[] chances = new double[]{0.0, 0.5, 1.0};

        for (int i = 0; i < 3; i++) {
            RobotArrayProvider provider = new RobotArrayProvider(chances[i % 3]);

            expectedNumberOfNullRobots = provider.numberOfNullElements;
            actualNumberOfNullRobots = Main.numberOfNullRobots(provider.robots);

            assertEquals(
                expectedNumberOfNullRobots,
                actualNumberOfNullRobots,
                provider.getGeneralInfo() +
                    "Expected number of null elements in array: " + expectedNumberOfNullRobots +
                    ", actual number of null elements in array: " + actualNumberOfNullRobots
            );
        }
    }


    private static class RobotArrayProvider {
        public Robot[] robots;
        public int numberOfNullElements = 0;

        RobotArrayProvider(double chance) {
            int numberOfRobots = ThreadLocalRandom.current().nextInt(256);

            numberOfNullElements = 0;

            robots = new Robot[numberOfRobots];

            for (int i = 0; i < robots.length; i++) {
                if (ThreadLocalRandom.current().nextDouble(1.0) < chance) {
                    numberOfNullElements++;
                    robots[i] = null;
                } else {
                    robots[i] = new Robot(0, 0);
                }
            }
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

        String getGeneralInfo() {
            StringBuilder builder = new StringBuilder("General Information:\n");
            builder.append("Size of array: " + robots.length);
            builder.append(", array: " + arrayToString());
            builder.append("\nTest failed because:\n");
            return builder.toString();
        }
    }
}
