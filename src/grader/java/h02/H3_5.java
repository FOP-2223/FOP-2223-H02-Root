package h02;

import fopbot.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@TestForSubmission("h02")
class H3_5 {

    @BeforeEach
    void resetWorld() {
        World.reset();
    }

    @Test
    void testNullArray() {
        Robot[] robots = new Robot[]{null, null, null};
        Main.letAllRobotsGo(robots);
        List<RobotTrace> listOfTraces = World.getGlobalWorld().getTraces();
        boolean onlyContainsNull = true;

        for (RobotTrace trace : listOfTraces) {
            if (trace != null) {
                onlyContainsNull = false;
                break;
            }
        }

        assertTrue(
            onlyContainsNull,
            "Expected no traces after using array containing only null, but found the following trace(s):\n" +
                listOfTraces
        );
    }

    @Test
    void testExceptions() {
        int width = ThreadLocalRandom.current().nextInt(3, 12);
        int height = ThreadLocalRandom.current().nextInt(3, 12);

        World.setSize(width, height);
        World.setDelay(0);

        Robot[] robots = Utils.getRandomRobotArray(1, width, height);

        assertDoesNotThrow(
            () -> Main.letAllRobotsGo(robots),
            "Expected letAllRobotsGo to not throw an exception!"
        );
    }

    @Test
    void checkDirections() {
        int width = ThreadLocalRandom.current().nextInt(3, 12);
        int height = ThreadLocalRandom.current().nextInt(3, 12);
        Transition.RobotAction unexpectedAction = Transition.RobotAction.TURN_LEFT;

        World.setSize(width, height);
        World.setDelay(0);

        Robot[] robots = Utils.getRandomRobotArray(1, width, height);

        Main.letAllRobotsGo(robots);

        for (RobotTrace trace : World.getGlobalWorld().getTraces()) {
            for (Transition transition : trace.getTransitions()) {
                assertNotEquals(
                    unexpectedAction,
                    transition.action,
                    "Expected all robots to never change their direction, but a robot changes direction in " +
                        transition
                );
            }
        }
    }

    @Test
    void testAllRobotsReachEnd() {
        int width = ThreadLocalRandom.current().nextInt(3, 12);
        int height = ThreadLocalRandom.current().nextInt(3, 12);
        int expectedNumberOfCoins, actualNumberOfCoins;
        String generalInfo = Utils.getGeneralInfo("World size: " + World.getWidth() + "x" + World.getHeight());

        World.setSize(width, height);
        World.setDelay(0);

        Robot[] robots = Utils.getRandomRobotArray(1, width, height);
        Robot[] robotsCopy = Arrays.copyOf(robots, robots.length);

        Main.letAllRobotsGo(robots);

        for (Robot roby : robotsCopy) {
            int initialX = World
                .getGlobalWorld()
                .getTrace(roby)
                .getTransitions()
                .get(0)
                .robot
                .getX();

            assertEquals(
                width - 1,
                roby.getX(),
                generalInfo +
                    "Expected each robot to reach the end of the world but " +
                    roby + " which started at " + initialX +
                    " stayed at " +
                    roby.getX() + "!"
            );

            expectedNumberOfCoins = 10000 - (width - initialX);
            actualNumberOfCoins = roby.getNumberOfCoins();

            assertEquals(
                expectedNumberOfCoins,
                roby.getNumberOfCoins(),
                generalInfo +
                    "Expected each robot to place one coin before movement but " +
                    roby + " which started at X=" + initialX +
                    " has " + (actualNumberOfCoins - expectedNumberOfCoins) + " more coins than it should have!"
            );
        }
    }

    @Test
    void testResizeOfArray() {
        Robot[] robots = new Robot[3];
        Robot[] robotsCopy = new Robot[3];
        for (int i = 0; i < robots.length; i++) {
            robots[i] = new Robot(i, i, Direction.RIGHT, robots.length - i);
            robotsCopy[i] = new Robot(i, i, Direction.RIGHT, robots.length - i);
        }
        World.setSize(robots.length, robots.length);

        Main.letAllRobotsGo(robots);

        String robotsAsString = Utils.robotArrayToString(robots);
        String robotsCopyAsString = Utils.robotArrayToString(robotsCopy);

        assertArrayEquals(
            new Robot[]{null, null, null},
            robots,
            "Expected array " +
                robotsCopyAsString +
                " to only contain null after letAllRobotsGo but it actually looks like " +
                robotsAsString
        );
    }

    @Test
    void checkRobotActions() {
        int width = ThreadLocalRandom.current().nextInt(3, 20);
        int height = ThreadLocalRandom.current().nextInt(3, 20);

        World.setSize(width, height);
        World.setDelay(0);

        Robot[] robots = Utils.getRandomRobotArray(1, width, height);

        Main.letAllRobotsGo(robots);

        Transition.RobotAction actualAction;

        for (RobotTrace trace : World.getGlobalWorld().getTraces()) {
            for (Transition transition : trace.getTransitions()) {
                actualAction = transition.action;

                assertNotEquals(
                    Transition.RobotAction.PICK_COIN,
                    actualAction,
                    "Expected all robots to only move and put coins, but a robot picks a coin in " +
                        transition
                );

                assertNotEquals(
                    Transition.RobotAction.SET_X,
                    actualAction,
                    "Expected all robots to only move and put coins, but a robot's x coordinate is changed in " +
                        transition
                );

                assertNotEquals(
                    Transition.RobotAction.SET_Y,
                    actualAction,
                    "Expected all robots to only move and put coins, but a robot's y coordinate is changed in " +
                        transition
                );

                assertNotEquals(
                    Transition.RobotAction.TURN_OFF,
                    actualAction,
                    "Expected all robots to only move and put coins, but a robot is turned off in " +
                        transition
                );
            }
        }
    }

}
