package h02;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
class H3_4 {

    String generalInformation;

    @Test
    void testSwapRobots() {
        int[] indices;
        Robot[] robots, reference;

        int width = 4, height = 4, x, y, i, j, k;
        World.setSize(width, height);

        for (int iter = 0; iter < 20; iter++) {
            robots = Utils.getRandomRobotArray(3, width, height);

            reference = robots.clone();

            do {
                indices = ThreadLocalRandom.current().ints(3, 0, robots.length).distinct().toArray();
            } while (indices.length != 3);

            Arrays.sort(indices);

            Main.swapRobots(indices, robots);

            i = indices[0];
            j = indices[1];
            k = indices[2];

            generalInformation =
                Utils.getGeneralInfo(
                    "Array before: " + Utils.robotArrayToString(reference) +
                        "\nArray afterwards: " + Utils.robotArrayToString(robots)
                );

            assertSame(
                reference[i],
                robots[j],
                generalInformation +
                    "Expected Robot from index i = " + i + " to now be at index j = " + j + ", but it is at index " +
                    getIndexOfRobot(robots, reference[i]) + "!"
            );

            assertSame(
                reference[j],
                robots[k],
                generalInformation +
                    "Expected Robot from index j = " + j + " to now be at index k = " + k + ", but it is at index " +
                    getIndexOfRobot(robots, reference[j]) + "!"
            );

            assertSame(
                reference[k],
                robots[i],
                generalInformation +
                    "Expected Robot from index k = " + k + " to now be at index i = " + i + ", but it is at index" +
                    getIndexOfRobot(robots, reference[k]) + "!"
            );
        }
    }

    private int getIndexOfRobot(Robot[] robots, Robot robot) {
        for (int i = 0; i < robots.length; i++) {
            if (robots[i].equals(robot))
                return i;
        }
        return -1;
    }
}
