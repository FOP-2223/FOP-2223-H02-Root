package h02.h4;

import fopbot.*;
import h02.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static h02.h3.H3Utils.convertRobotArrayToString;
import static h02.h3.H3Utils.convertStringToRobotArrayWithCoordinates;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static h02.Utils.*;

@TestForSubmission("h02")
public class LetRobotsMarchTest {

    private static final String PATH_TO_CSV = "/h4/RobotArrays.csv";

    private static final ArrayList<Transition.RobotAction> unexpectedActions = new ArrayList<>();

    private static final Main main = new Main();

    @BeforeAll
    static void setup() {
        World.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        World.setDelay(0);
        unexpectedActions.addAll(
            Arrays.stream(Transition.RobotAction.values())
                .filter(e -> !e.equals(Transition.RobotAction.MOVE) &&
                    !e.equals(Transition.RobotAction.PUT_COIN) &&
                    !e.equals(Transition.RobotAction.NONE)
                )
                .toList()
        );
    }

    @Test
    void testUseOfMethods() {
        Main mainSpy = Mockito.spy(Main.class);
        Robot[] robots = new Robot[]{
            new Robot(0, 0, Direction.RIGHT, 10000),
            new Robot(1, 1, Direction.RIGHT, 10000),
            new Robot(2, 2, Direction.RIGHT, 10000),
        };

        var context = contextBuilder()
            .add("Robot-Array", convertRobotArrayToString(robots))
            .build();

        mainSpy.letRobotsMarch(robots);

        call(
            () -> Mockito.verify(mainSpy, Mockito.atLeastOnce()).numberOfNullRobots(Mockito.any()),
            context,
            r -> String.format(
                "Expected method %s to call method %s at least once!",
                "letRobotsMarch",
                "numberOfNullRobots"
            )
        );

        call(
            () -> Mockito.verify(mainSpy, Mockito.atLeastOnce()).generateThreeDistinctRandomIndices(Mockito.anyInt()),
            context,
            r -> String.format(
                "Expected method %s to call method %s at least once!",
                "letRobotsMarch",
                "generateThreeDistinctRandomIndices"
            )
        );

        call(
            () -> Mockito.verify(mainSpy, Mockito.atLeastOnce()).generateThreeDistinctRandomIndices(Mockito.anyInt()),
            context,
            r -> String.format(
                "Expected method %s to call method %s at least once!",
                "letRobotsMarch",
                "generateThreeDistinctRandomIndices"
            )
        );

        call(
            () -> Mockito.verify(mainSpy, Mockito.atLeastOnce()).sortArray(Mockito.any()),
            context,
            r -> String.format(
                "Expected method %s to call method %s at least once!",
                "letRobotsMarch",
                "sortArray"
            )
        );

        call(
            () -> Mockito.verify(mainSpy, Mockito.atLeastOnce()).reduceRobotArray(Mockito.any(), Mockito.anyInt()),
            context,
            r -> String.format(
                "Expected method %s to call method %s at least once!",
                "letRobotsMarch",
                "reduceRobotArray"
            )
        );
    }

    @Test
    void testNullArray() {
        World.reset();
        Robot[] array = {null, null, null};
        main.letRobotsMarch(array);
        List<RobotTrace> listOfTraces = World.getGlobalWorld().getTraces();

        var context = contextBuilder()
            .add("Array", Arrays.toString(array))
            .build();

        for (RobotTrace trace : listOfTraces) {
            assertNull(
                trace,
                context,
                r -> String.format(
                    "Expected no traces after using array containing only null, but found the following trace(s):\n%s",
                    listOfTraces
                )
            );
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void checkForExceptions(String arrayAsString) {

        var context = contextBuilder()
            .add("Array", arrayAsString)
            .build();

        World.reset();
        call(
            () -> main.letRobotsMarch(convertStringToRobotArrayWithCoordinates(arrayAsString)),
            context,
            r -> "Expected letAllRobotsGo to not throw an exception!"
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void checkActions(String arrayAsString) {

        var context = contextBuilder()
            .add("Array", arrayAsString)
            .build();

        Robot[] robots = convertStringToRobotArrayWithCoordinates(arrayAsString);
        main.letRobotsMarch(robots);
        for (RobotTrace trace : World.getGlobalWorld().getTraces()) {
            for (Transition transition : trace.getTransitions()) {
                assertFalse(
                    unexpectedActions.contains(transition.action),
                    context,
                    r -> String.format(
                        "Expected all robots to only ever move or put a coin, but a robot executes action %s in transition %s",
                        transition.action,
                        transition
                    )
                );
            }
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testAllRobotsReachEnd(String arrayAsString) {

        var context = contextBuilder()
            .add("Array", arrayAsString)
            .build();

        World.reset();
        Robot[] robots = convertStringToRobotArrayWithCoordinates(arrayAsString);
        Robot[] robotsCopy = Arrays.copyOf(robots, robots.length);
        main.letRobotsMarch(robots);

        for (Robot roby : robotsCopy) {
            if (roby == null)
                continue;

            int initialX = World
                .getGlobalWorld()
                .getTrace(roby)
                .getTransitions()
                .get(0)
                .robot
                .getX();

            assertEquals(
                WORLD_WIDTH - 1,
                roby.getX(),
                context,
                r -> String.format(
                    "Expected each robot to reach the end of the world but robot %s which started at %d stayed at %d!",
                    roby,
                    initialX,
                    roby.getX()
                )
            );
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testAllRobotsPutCoins(String arrayAsString) {

        var context = contextBuilder()
            .add("Array", arrayAsString)
            .build();

        Robot[] robots = convertStringToRobotArrayWithCoordinates(arrayAsString);
        Robot[] robotsCopy = Arrays.copyOf(robots, robots.length);

        main.letRobotsMarch(robots);

        for (Robot roby : robotsCopy) {
            if (roby == null)
                continue;

            List<Transition> transitionsOfRobot = World
                .getGlobalWorld()
                .getTrace(roby)
                .getTransitions();

            int initialX = transitionsOfRobot
                .get(0)
                .robot
                .getX();

            int expectedNumberOfPutCoinCalls = WORLD_WIDTH - initialX;
            int actualNumberOfPutCoinCalls = (int) transitionsOfRobot
                .stream()
                .filter(
                    e -> e.action.equals(Transition.RobotAction.PUT_COIN)
                ).count();

            int expectedNumberOfCoins = 10000 - expectedNumberOfPutCoinCalls;
            int actualNumberOfCoins = roby.getNumberOfCoins();

            assertEquals(
                expectedNumberOfPutCoinCalls,
                actualNumberOfPutCoinCalls,
                context,
                r -> String.format(
                    "Expected robot %s to call method \"putCoin()\" %d times but the robot only called it %d times!",
                    roby,
                    expectedNumberOfPutCoinCalls,
                    actualNumberOfPutCoinCalls
                )
            );

            assertEquals(
                expectedNumberOfCoins,
                roby.getNumberOfCoins(),
                context,
                r -> String.format(
                    "Expected each robot to place one coin before movement but robot %s which started at X=%d has %d more coins than it should have!",
                    roby,
                    initialX,
                    (actualNumberOfCoins - expectedNumberOfCoins)
                )
            );
        }
    }
}
