package h02.h1;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h02.Main;
import h02.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.h1.H1Utils.booleanArrayToString;
import static org.junit.jupiter.api.Assertions.*;


@TestForSubmission("h02")
public class InitializeRobotsPatternTest {

    private static final int WORLD_WIDTH = 4;
    private static final int WORLD_HEIGHT = 4;

    @BeforeAll
    static void setup() {
        World.setSize(WORLD_WIDTH, WORLD_HEIGHT);
    }

    @BeforeEach
    void reset() {
        World.reset();
    }


    /*

    TODO: Test for use of for-loops

     */


    // Method works with unfit pattern
    @ParameterizedTest
    @CsvFileSource(resources = {"/h1/biggerPattern.csv", "/h1/smallerPattern.csv", "/h1/unfittingPattern.csv"})
    void testNotFittingPatterns(String patternAsString) {
        boolean[][] notFittingPattern = H1Utils.stringToPatternConverter(patternAsString);
        testNumberOfRobots(notFittingPattern);
        doesNotThrowException(notFittingPattern);
        testCoins(notFittingPattern);
        testDirections(notFittingPattern);
        testCoordinates(notFittingPattern);
    }

    // Number of robots after method call is correct
    @ParameterizedTest
    @CsvFileSource(resources = "/h1/fittingPattern.csv")
    void testNumberOfRobotsWithFittingPattern(String patternAsString) {
        boolean[][] fittingPattern = H1Utils.stringToPatternConverter(patternAsString);
        testNumberOfRobots(fittingPattern);
    }

    // All coins are correctly set
    @ParameterizedTest
    @CsvFileSource(resources = "/h1/fittingPattern.csv")
    void testCoinsWithFittingPattern(String patternAsString) {
        testCoins(H1Utils.stringToPatternConverter(patternAsString));
    }

    // All robots face right after method call
    @ParameterizedTest
    @CsvFileSource(resources = "/h1/fittingPattern.csv")
    void testDirectionsWithFittingPattern(String patternAsString) {
        testDirections(H1Utils.stringToPatternConverter(patternAsString));
    }

    // All robots are at the correct coordinates
    @ParameterizedTest
    @CsvFileSource(resources = "/h1/fittingPattern.csv")
    void testCoordinatesWithFittingPattern(String patternAsString) {
        testCoordinates(H1Utils.stringToPatternConverter(patternAsString));
    }

    private void doesNotThrowException(boolean[][] pattern) {
        assertDoesNotThrow(
            () -> Main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT),
            Utils.getGeneralInfo("Pattern:\n" + booleanArrayToString(pattern)) +
                "The method \"initializeRobotsArray\" threw an Exception when processing the pattern above!"
        );
    }

    private void testNumberOfRobots(boolean[][] pattern) {
        Main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        int expectedNumberOfRobots = 0;
        int actualNumberOfRobots = World.getGlobalWorld().getAllFieldEntities().size();

        boolean[][] worldSizePattern = H1Utils.getWorldSizeRobotPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        for (int y = 0; y < WORLD_HEIGHT; y++) {
            for (int x = 0; x < WORLD_WIDTH; x++) {
                if (worldSizePattern[y][x]) {
                    expectedNumberOfRobots++;
                }
            }
        }

        assertEquals(
            expectedNumberOfRobots,
            actualNumberOfRobots,
            Utils.getGeneralInfo("Pattern:\n" + booleanArrayToString(pattern)) +
                "Expected " + expectedNumberOfRobots + " robots in the world but there were actually " + actualNumberOfRobots + "."
        );
    }

    private void testCoins(boolean[][] pattern) {
        Main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        int expectedCoins;
        int actualCoins;

        for (FieldEntity robot : World.getGlobalWorld().getAllFieldEntities()) {
            if (robot instanceof Robot) {
                expectedCoins = WORLD_WIDTH - robot.getX();
                actualCoins = ((Robot) robot).getNumberOfCoins();
                assertEquals(
                    expectedCoins,
                    actualCoins,
                    Utils.getGeneralInfo("Pattern:\n" + booleanArrayToString(pattern)) +
                        "Expected robot " + robot + " to have " + expectedCoins + " coins but it has " + actualCoins + "."
                );
            }
        }
    }

    private void testDirections(boolean[][] pattern) {
        Main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        Direction expectedDirection = Direction.RIGHT;
        Direction actualDirection;

        for (FieldEntity robot : World.getGlobalWorld().getAllFieldEntities()) {
            if (robot instanceof Robot) {
                actualDirection = ((Robot) robot).getDirection();
                assertEquals(
                    expectedDirection,
                    actualDirection,
                    Utils.getGeneralInfo("Pattern:\n" + booleanArrayToString(pattern)) +
                        "Expected robot " + robot + " to face " + expectedDirection + " but it actually faces " + actualDirection + "."
                );
            }
        }
    }

    private void testCoordinates(boolean[][] pattern) {
        Main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        boolean[][] actualPattern = new boolean[WORLD_HEIGHT][WORLD_WIDTH];
        boolean[][] worldSizePattern = H1Utils.getWorldSizeRobotPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        for (FieldEntity robot : World.getGlobalWorld().getAllFieldEntities()) {
            if (robot instanceof Robot) {
                actualPattern[robot.getY()][robot.getX()] = true;
            }
        }

        assertArrayEquals(
            worldSizePattern,
            actualPattern,
            Utils.getGeneralInfo("Pattern:\n" + booleanArrayToString(pattern)) +
                "Expected the robots to be arranged like this:" +
                booleanArrayToString(worldSizePattern) +
                "\nBut they are arranged like this:" +
                booleanArrayToString(actualPattern)
        );
    }

}
