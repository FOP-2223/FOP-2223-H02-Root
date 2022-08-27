package h02.h3;

import fopbot.Robot;

public class H3Utils {

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

}
