package h02;

import fopbot.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@TestForSubmission("h02")
class H3_5 {

    @BeforeEach
    void resetWorld() {
        World.reset();
        List<RobotTrace> listOfTraces = World.getGlobalWorld().getTraces();
        if (listOfTraces.isEmpty())
            System.out.println("Yeah");
    }

    @Test
    void testNullArray() {
        Robot[] robots = new Robot[]{null};
        Main.letAllRobotsGo(robots);

    }

    @Test
    void test() {
        World.setSize(4, 4);
        Robot roby = new Robot(1, 1);
        //roby.move();
        roby = null;
        if (World.getGlobalWorld().getTrace(roby) == null)
            System.out.println("roby is null");

        List<RobotTrace> listOfTraces = World.getGlobalWorld().getTraces();
        System.out.println(listOfTraces.get(0).toString());
        if (listOfTraces.get(0).getTransitions().get(0).robot.getDirection() == Direction.UP)
            System.out.println("Hella good");
    }

    @Test
    void testLetAllRobotsGo() {
        // TODO: Test no change in trace with array containing only null
    }

    @Test
    void testDirections() {
        Robot[] robots;
        int width, height;
        for (int i = 0; i < 1000; i++) {
            width = ThreadLocalRandom.current().nextInt(3, 12);
            height = ThreadLocalRandom.current().nextInt(3, 12);

            World.setSize(width, height);
            World.setDelay(0);

            robots = Utils.getRandomRobotArray(1, width, height);

            Main.letAllRobotsGo(robots);

            for (RobotTrace trace : World.getGlobalWorld().getTraces()) {
                for (Transition t : trace.getTransitions()) {

                    Direction expected = Direction.RIGHT;
                    Direction actual = t.robot.getDirection();

                    assertEquals(
                        expected,
                        actual,
                        "Expected the robots to never change their direction, but robot " + t.robot.toString() +
                            " has direction " + t.robot.getDirection().toString() + " instead of direction " +
                            expected.toString() + "!"
                    );
                }
            }

            World.reset();
        }
    }

}
