package h02.h1.h1_2;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.World;
import h02.Main;
import h02.h1.H1Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.Utils.*;
import static h02.h1.H1Utils.convertArrayOfArrayOfBooleanToString;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;


@TestForSubmission
public class InitializeRobotsPatternTest {

    private static final String PATH_TO_CSV = "/h1/h1_2/FittingPatterns.csv";
    private static final String PATH_TO_CSV_2 = "/h1/h1_2/UnfittingPatterns.csv";

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
    void testInvocationsOfCountOfRobotsInPattern(String patternAsString) {
        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        Main mainSpy = Mockito.spy(Main.class);
        mainSpy.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        call(
            () -> Mockito.verify(mainSpy, Mockito.atLeastOnce()).countRobotsInPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT),
            contextBuilder().add("Pattern", patternAsString).build(),
            r -> String.format(
                "Expected method %s to call method %s at least once!",
                "initializeRobotsPattern",
                "countRobotsInPattern"
            )
        );
    }


    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV_2)
    void testNotFittingPatterns(String patternAsString, int expected) {
        testNumberOfRobots(patternAsString, expected);
        doesNotThrowException(patternAsString);
        testCoins(patternAsString);
        testDirections(patternAsString);
        testCoordinates(patternAsString);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testNumberOfRobotsWithFittingPattern(String patternAsString, int expected) {
        testNumberOfRobots(patternAsString, expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testCoinsWithFittingPattern(String patternAsString) {
        testCoins(patternAsString);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testDirectionsWithFittingPattern(String patternAsString) {
        testDirections(patternAsString);
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testCoordinatesWithFittingPattern(String patternAsString) {
        testCoordinates(patternAsString);
    }

    private void doesNotThrowException(String patternAsString) {

        var context = contextBuilder()
            .add("World width", WORLD_WIDTH)
            .add("World height", WORLD_HEIGHT)
            .add("Pattern", patternAsString)
            .build();

        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);

        call(
            () -> main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT),
            context,
            r -> "The method \"initializeRobotsArray\" threw an Exception when processing the pattern!"
        );
    }

    private void testNumberOfRobots(String patternAsString, int expected) {

        var context = contextBuilder()
            .add("World width", WORLD_WIDTH)
            .add("World height", WORLD_HEIGHT)
            .add("Pattern", patternAsString)
            .build();

        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        int actualNumberOfRobots = World.getGlobalWorld().getAllFieldEntities().size();

        assertEquals(
            expected,
            actualNumberOfRobots,
            context,
            r -> String.format("Expected %d robots in the world but there were actually %d.", expected, actualNumberOfRobots)
        );
    }

    private void testCoins(String patternAsString) {

        var context = contextBuilder()
            .add("World width", WORLD_WIDTH)
            .add("World height", WORLD_HEIGHT)
            .add("Pattern", patternAsString)
            .build();

        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        for (FieldEntity robot : World.getGlobalWorld().getAllFieldEntities()) {
            if (robot instanceof Robot) {
                int expectedCoins = WORLD_WIDTH - robot.getX();
                int actualCoins = ((Robot) robot).getNumberOfCoins();

                assertEquals(
                    expectedCoins,
                    actualCoins,
                    context,
                    r -> String.format("Expected robot %s to have %d coins but it has %d.", robot, expectedCoins, actualCoins)
                );
            }
        }
    }

    private void testDirections(String patternAsString) {

        var context = contextBuilder()
            .add("World width", WORLD_WIDTH)
            .add("World height", WORLD_HEIGHT)
            .add("Pattern", patternAsString)
            .build();

        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        Direction expectedDirection = Direction.RIGHT;

        for (FieldEntity robot : World.getGlobalWorld().getAllFieldEntities()) {
            if (robot instanceof Robot) {
                Direction actualDirection = ((Robot) robot).getDirection();

                assertEquals(
                    expectedDirection,
                    actualDirection,
                    context,
                    r -> String.format("Expected robot %s to face %s but it actually faces %s.", robot, expectedDirection, actualDirection)
                );
            }
        }
    }

    private void testCoordinates(String patternAsString) {

        var context = contextBuilder()
            .add("World width", WORLD_WIDTH)
            .add("World height", WORLD_HEIGHT)
            .add("Pattern", patternAsString)
            .build();

        boolean[][] pattern = H1Utils.convertStringToPattern(patternAsString);
        main.initializeRobotsPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        boolean[][] actualPattern = new boolean[WORLD_HEIGHT][WORLD_WIDTH];
        boolean[][] worldSizePattern = H1Utils.getWorldSizeRobotPattern(pattern, WORLD_WIDTH, WORLD_HEIGHT);

        for (FieldEntity robot : World.getGlobalWorld().getAllFieldEntities()) {
            if (robot instanceof Robot) {
                actualPattern[robot.getX()][robot.getY()] = true;
            }
        }

        for (int i = 0; i < worldSizePattern.length; i++) {
            for (int j = 0; j < worldSizePattern[0].length; j++) {
                assertEquals(
                    worldSizePattern[i][j],
                    actualPattern[i][j],
                    context,
                    r -> String.format("Expected the robots to be arranged like %s \nBut they are arranged like %s", convertArrayOfArrayOfBooleanToString(worldSizePattern), convertArrayOfArrayOfBooleanToString(actualPattern))
                );
            }
        }
    }

}
