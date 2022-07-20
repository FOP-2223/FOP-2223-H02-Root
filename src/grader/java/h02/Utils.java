package h02;

import fopbot.Direction;
import fopbot.Robot;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static Robot[] getRandomRobotArray(int minimalArrayLength, int width, int height) {
        Robot[] robots = new Robot[ThreadLocalRandom.current().nextInt(minimalArrayLength, width * height)];
        int x, y;
        for (int r = 0; r < robots.length; r++) {
            x = ThreadLocalRandom.current().nextInt(width);
            y = ThreadLocalRandom.current().nextInt(height);
            robots[r] = new Robot(x, y, Direction.RIGHT, 10000);
        }

        return robots;
    }
}
