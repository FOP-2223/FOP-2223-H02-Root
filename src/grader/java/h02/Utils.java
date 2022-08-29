package h02;

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

    public static String getGeneralInfo(String information) {
        StringBuilder builder = new StringBuilder("General Information:\n");
        builder.append("World size: " + WORLD_WIDTH + "x" + WORLD_HEIGHT + "\n");
        builder.append(information);
        builder.append("\nTest failed because:\n");
        return builder.toString();
    }
}
