package h02;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static final int WORLD_WIDTH = 4;

    public static final int WORLD_HEIGHT = 4;

    public static void main(String[] args) {
        providePattern(false, 1000);
    }

    public static String getGeneralInfo(String information) {
        StringBuilder builder = new StringBuilder("General Information:\n");
        builder.append("World size: " + WORLD_WIDTH + "x" + WORLD_HEIGHT + "\n");
        builder.append(information);
        builder.append("\nTest failed because:\n");
        return builder.toString();
    }

    public static void providePattern(boolean fitting, int lines) {
        for (int k = 0; k < lines; k++) {
            int numberIsOne = 0;
            int columns = fitting ? WORLD_WIDTH : ThreadLocalRandom.current().nextInt(1,10);
            int rows = fitting ? WORLD_HEIGHT : ThreadLocalRandom.current().nextInt(1, 10);
            double chance = ThreadLocalRandom.current().nextDouble(1);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    String number = ThreadLocalRandom.current().nextDouble(1) < chance ? "1" : "0";
                    if (number.equals("1") && i < WORLD_HEIGHT && j < WORLD_WIDTH)
                        numberIsOne++;
                    System.out.print(number);
                }
                if (i + 1 != rows)
                    System.out.print("/");
            }
            System.out.println(", " + numberIsOne);
        }
    }
}
