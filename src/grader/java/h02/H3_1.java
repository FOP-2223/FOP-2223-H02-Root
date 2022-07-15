package h02;

import fopbot.Robot;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h02")
class H3_1 {

    String message = "Number of null elements in robot array: ";

    @Test
    void testNumberOfNullRobots() {
        int expected;
        int actual;

        for (int i = 0; i < 1000; i++) {
            RobotArrayProvider provider = new RobotArrayProvider();

            expected = provider.numberOfNullElements;
            actual = Main.numberOfNullRobots(provider.robots);

            assertEquals(message + expected, message + actual);
        }
    }


    private static class RobotArrayProvider {
        public Robot[] robots;
        public int numberOfNullElements = 0;

        RobotArrayProvider() {
            int numberOfRobots = ThreadLocalRandom.current().nextInt(256);

            double chance = ThreadLocalRandom.current().nextDouble(1.0);

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
    }
}
