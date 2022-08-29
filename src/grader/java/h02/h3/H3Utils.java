package h02.h3;

import fopbot.Robot;

public class H3Utils {

    public static Robot[] convertStringToRobotArray(String arrayAsString) {
        Robot[] robots = new Robot[arrayAsString.length()];
        for (int i = 0; i < robots.length; i++) {
            if (arrayAsString.charAt(i) == '0') {
                robots[i] = null;
            } else {
                robots[i] = new Robot(0, 0);
            }
        }
        return robots;
    }

    public static String convertRobotArrayToString(Robot[] allRobots) {
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

    public static int[] convertStringToIntArray(String arrayAsString) {
        String[] split = arrayAsString.split("/");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        return array;
    }

}
