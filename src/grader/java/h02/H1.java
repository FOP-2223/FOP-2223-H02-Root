package h02;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
class H1 {

    int width, height;

    boolean[][] worldSizePattern, randomPattern;

    List<FieldEntity> allRobots;

    @BeforeEach
    void initialize() {
        width = ThreadLocalRandom.current().nextInt(2, 11);
        height = ThreadLocalRandom.current().nextInt(2, 11);

        randomPattern = getRandomRobotPattern(
            ThreadLocalRandom.current().nextInt(1, 21),
            ThreadLocalRandom.current().nextInt(1, 21)
        );
        worldSizePattern = getWorldSizeRobotPattern(
            randomPattern,
            width,
            height
        );

        World.setSize(width, height);

        Main.initializeRobotsPattern(randomPattern, width, height);

        allRobots = World.getGlobalWorld().getAllFieldEntities();
    }

    @Test
    void testNumberOfRobots() {
        int expectedNumberOfRobots = 0;
        int actualNumberOfRobots = allRobots.size();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (worldSizePattern[y][x]) {
                    expectedNumberOfRobots++;
                }
            }
        }

        assertEquals(
            expectedNumberOfRobots,
            actualNumberOfRobots,
            getGeneralInfo() +
                "Expected " + expectedNumberOfRobots + " robots in the world but there were actually " + actualNumberOfRobots + "."
        );
    }

    @Test
    void testRobotCoordinates() {
        boolean[][] actualPattern = new boolean[height][width];

        for (FieldEntity robot : allRobots) {
            if (robot instanceof Robot) {
                actualPattern[robot.getY()][robot.getX()] = true;
            }
        }

        assertArrayEquals(
            worldSizePattern,
            actualPattern,
            getGeneralInfo() +
                "Expected the robots to be arranged like this:" +
                booleanArrayToString(worldSizePattern) +
                "\nBut they are arranged like this:" +
                booleanArrayToString(actualPattern)
        );
    }

    @Test
    void testRobotCoins() {
        int expectedCoins;
        int actualCoins;

        for (FieldEntity robot : allRobots) {
            if (robot instanceof Robot) {
                expectedCoins = width - robot.getX();
                actualCoins = ((Robot) robot).getNumberOfCoins();
                assertEquals(
                    expectedCoins,
                    actualCoins,
                    getGeneralInfo() +
                        "Expected robot " + robot + " to have " + expectedCoins + " coins but it has " + actualCoins + "."
                );
            }
        }
    }

    @Test
    void testRobotDirections() {
        Direction expectedDirection = Direction.RIGHT;
        Direction actualDirection;

        for (FieldEntity robot : allRobots) {
            if (robot instanceof Robot) {
                actualDirection = ((Robot) robot).getDirection();
                assertEquals(
                    expectedDirection,
                    actualDirection,
                    getGeneralInfo() +
                        "Expected robot " + robot + " to face " + expectedDirection + " but it actually faces " + actualDirection + "."
                );
            }
        }
    }

    private String getGeneralInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Size of the Fopbot-World: ");
        builder.append(World.getWidth() + "x" + World.getHeight());
        builder.append(", size of the pattern: ");
        builder.append(randomPattern[0].length + "x" + randomPattern.length);
        builder.append("\nTest failed because:\n");
        return builder.toString();
    }

    private boolean[][] getRandomRobotPattern(int width, int height) {
        boolean[][] pattern = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pattern[y][x] = ThreadLocalRandom.current().nextBoolean();
            }
        }

        return pattern;
    }

    private boolean[][] getWorldSizeRobotPattern(boolean[][] randomPattern, int width, int height) {
        boolean[][] pattern = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y < randomPattern.length && x < randomPattern[0].length)
                    pattern[y][x] = randomPattern[y][x];
            }
        }

        return pattern;
    }

    private String booleanArrayToString(boolean[][] pattern) {
        StringBuilder builder = new StringBuilder("\n");
        for (int i = 0; i < pattern.length; i++) {
            builder.append("[");
            for (int j = 0; j < pattern[i].length; j++) {
                builder.append(pattern[i][j] ? "T" : "F");
                if (j + 1 != pattern[i].length)
                    builder.append(", ");
            }
            builder.append("]\n");
        }
        return builder.toString();
    }

}
