package h02.h1;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h02.Main;
import h02.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.h1.H1Utils.convertArrayOfArrayOfBooleanToString;
import static org.junit.jupiter.api.Assertions.*;
import static h02.Utils.WORLD_WIDTH;
import static h02.Utils.WORLD_HEIGHT;


@TestForSubmission("h02")
public class InitializeRobotsPatternTest {

    private static final String PATH_TO_CSV = "/h1/fittingPatterns.csv";
    private static final String PATH_TO_CSV_2 = "/h1/unfittingPatterns.csv";

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


    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV_2)
    void testNotFittingPatterns(String patternAsString) {
        boolean[][] notFittingPattern = H1Utils.convertStringToPattern(patternAsString);
        testNumberOfRobots(notFittingPattern);
        doesNotThrowException(notFittingPattern);
        testCoins(notFittingPattern);
        testDirections(notFittingPattern);
        testCoordinates(notFittingPattern);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testNumberOfRobotsWithFittingPattern(String patternAsString) {
        boolean[][] fittingPattern = H1Utils.convertStringToPattern(patternAsString);
        testNumberOfRobots(fittingPattern);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testCoinsWithFittingPattern(String patternAsString) {
        testCoins(H1Utils.convertStringToPattern(patternAsString));
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testDirectionsWithFittingPattern(String patternAsString) {
        testDirections(H1Utils.convertStringToPattern(patternAsString));
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testCoordinatesWithFittingPattern(String patternAsString) {
        testCoordinates(H1Utils.convertStringToPattern(patternAsString));
    }

    private void doesNotThrowException(boolean[][] pattern) {
        assertDoesNotThrow(
            () -> Main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT),
            Utils.getGeneralInfo("Pattern:\n" + convertArrayOfArrayOfBooleanToString(pattern)) +
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
            Utils.getGeneralInfo("Pattern:\n" + convertArrayOfArrayOfBooleanToString(pattern)) +
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
                    Utils.getGeneralInfo("Pattern:\n" + convertArrayOfArrayOfBooleanToString(pattern)) +
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
                    Utils.getGeneralInfo("Pattern:\n" + convertArrayOfArrayOfBooleanToString(pattern)) +
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
            Utils.getGeneralInfo("Pattern:\n" + convertArrayOfArrayOfBooleanToString(pattern)) +
                "Expected the robots to be arranged like this:" +
                convertArrayOfArrayOfBooleanToString(worldSizePattern) +
                "\nBut they are arranged like this:" +
                convertArrayOfArrayOfBooleanToString(actualPattern)
        );
    }

}
