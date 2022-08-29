package h02;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static final int WORLD_WIDTH = 4;

    public static final int WORLD_HEIGHT = 4;

    public static void patternForLetAllRobotsGoProvider() {
        for (int k = 0; k < 10; k++) {
            double chance = ThreadLocalRandom.current().nextDouble(1);
            for (int i = 0; i < WORLD_HEIGHT; i++) {
                for (int j = 0; j < WORLD_WIDTH; j++) {
                    System.out.print(ThreadLocalRandom.current().nextDouble(1) < chance ? "1" : "0");
                }
                if (i + 1 != WORLD_HEIGHT)
                    System.out.print("/");
            }
            System.out.println();
        }
    }

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

    public static String getGeneralInfo(String information) {
        StringBuilder builder = new StringBuilder("General Information:\n");
        builder.append("World size: " + WORLD_WIDTH + "x" + WORLD_HEIGHT + "\n");
        builder.append(information);
        builder.append("\nTest failed because:\n");
        return builder.toString();
    }

    public static String robotArrayToString(Robot[] allRobots) {
        StringBuilder builder = new StringBuilder("\n");
        builder.append("[");
        for (int i = 0; i < allRobots.length; i++) {
            builder.append(allRobots[i]);
            if (i + 1 != allRobots.length)
                builder.append(", ");
        }
        builder.append("]\n");
        return builder.toString();
    }


}
