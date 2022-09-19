package h02;

import h02.h1.h1_2.InitializeRobotsPatternTest;
import h02.h3.H3Utils;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static final int WORLD_WIDTH = 4;

    public static final int WORLD_HEIGHT = 4;

    public static final String DIR = System.getProperty("user.dir").replaceAll("/build/run", "");

    public static final File MAIN_FILE = new File(DIR + "/src/main/java/h02/Main.java");

    public static void main(String[] args)  {
        providePattern(true, 1000);
    }

    public static String getMainAsString() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(MAIN_FILE));

            String line;

            while ((line = br.readLine()) != null)
                sb.append(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    public static int getNumberOfMethodInvokes(String method) {
        String[] strings = getMainAsString().split("\n");
        int numberMethodInvokes = 0;

        for (int i = 0; i < strings.length; i++) {
            if (strings[i].contains(getFullMethodDeclaration(method))) {
                continue;
            }
            if (strings[i].contains(method + "(")) {
                numberMethodInvokes++;
            }
        }
        return numberMethodInvokes;
    }

    public static String getGeneralInfo(String information) {
        StringBuilder builder = new StringBuilder("General Information:\n");
        builder.append("World size: " + WORLD_WIDTH + "x" + WORLD_HEIGHT + "\n");
        builder.append(information);
        builder.append("\nTest failed because:\n");
        return builder.toString();
    }

    public static String getFullMethodDeclaration(String method) {
        switch (method) {
            case "countRobotsInPattern" -> {
                return "public static int countRobotsInPattern(boolean[][] pattern, int numberOfColumns, int numberOfRows)";
            }
            case "numberOfNullRobots" -> {
                return "public static int numberOfNullRobots(Robot[] allRobots)";
            }
            case "generateThreeDistinctRandomIndices" -> {
                return "public static int[] generateThreeDistinctRandomIndices(int bound)";
            }
            case "sortArray" -> {
                return "public static void sortArray(int[] array)";
            }
            case "swapRobots" -> {
                return "public static void swapRobots(int[] indices, Robot[] allRobots)";
            }
            case "reduceRobotArray" -> {
                return "public static Robot[] reduceRobotArray(Robot[] robots, int reduceBy)";
            }
            default -> {
                return method;
            }
        }
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
