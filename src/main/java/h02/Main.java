package h02;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    // Delay between each action in FopBot-World (world), for example:
    // Waits 1000ms between each .move() call
    public static final int DELAY = 1000;

    // Generates random int between 4 (inclusive) and 10 (exclusive)
    public static int getRandomWorldSize() {
        return 4 + ThreadLocalRandom.current().nextInt(6);
    }

    // Name of file for patterns
    public static final String FILENAME = "ExamplePattern.txt";

    public static void main(String[] args) {
        int numberOfColumns = getRandomWorldSize();
        int numberOfRows = getRandomWorldSize();
        World.setSize(numberOfColumns, numberOfRows);
        World.setDelay(DELAY);
        World.setVisible(true);
        System.out.println("Size of world: " + numberOfColumns + "x" + numberOfRows);

        PatternProvider patternProvider;
        try {
            patternProvider = new PatternProvider(FILENAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean[][] testPattern = patternProvider.getPattern();

        Robot[] allRobots = initializeRobotsPattern(testPattern, numberOfColumns, numberOfRows);
        letAllRobotsGo(allRobots);
    }

    /**
     * Initialize allRobots array for given pattern and world size.
     *
     * @param pattern           The pattern for the robots.
     * @param numberOfColumns   Number of columns in world.
     * @param numberOfRows      Number of rows in world.
     * @return                  Correctly initialized allRobots array.
     */
    public static Robot[] initializeRobotsPattern(boolean[][] pattern, int numberOfColumns, int numberOfRows) {
        // Number of robots that need to be stored in the array allRobots
        int numberOfRobots = 0;

        // Find number of robots to instantiate allRobots-array:
        // Loop through rows of the world. Satisfies condition (a)
        for (int x = 0; x < numberOfRows; x++) {

            // Loop through columns of the world. Satisfies condition (b)
            for (int y = 0; y < numberOfColumns; y++) {

                // Condition (c)
                boolean c = y < pattern.length;

                // Condition (d), only satisfiable if (c) is met
                boolean d = c && x < pattern[y].length;

                // Condition (e), only satisfiable if (c) and (d) are met
                boolean e = c && d && pattern[y][x];

                // Iff all five conditions are met, increase number of robots by one
                if(c && d && e) {
                    numberOfRobots++;
                }
            }
        }

        // Initialize allRobots-array with the number of robots
        Robot[] allRobots = new Robot[numberOfRobots];

        // Index for the robots
        int indexForRobot = 0;

        // Fill the allRobots-array with robots:
        // Loop through rows of the world. Satisfies condition (a)
        for (int y = 0; y < numberOfRows; y++) {

            // Loop through columns of the world. Satisfies condition (b)
            for (int x = 0; x < numberOfColumns; x++) {

                // Condition (c)
                boolean c = y < pattern.length;

                // Condition (d), only satisfiable if (c) is met
                boolean d = c && x < pattern[y].length;

                // Condition (e), only satisfiable if (c) and (d) are met
                boolean e = c && d && pattern[y][x];

                // Iff all five conditions are met, create a new Robot with coordinates x, y,
                // direction = RIGHT and numberOfCoins = numberOfColumns - x
                if(c && d && e) {
                    allRobots[indexForRobot++] = new Robot(x, y, Direction.RIGHT, numberOfColumns - x);
                }
            }
        }

        // Return array containing the robots
        return allRobots;
    }

    /**
     * Lets all robots in the given array walk to the right while also putting down coins.
     * If robots leave the world they are set to null.
     * After the steps are made, if more than three robots exist, three of them change their index.
     * If more than 2 components of the array are null, the array is reduced by the amount of null components.
     *
     * @param allRobots   Array containing all the robots.
     */
    public static void letAllRobotsGo(Robot[] allRobots) {
        // Main loop ("Hauptschleife")
        // Only continues if there are non-null elements in the allRobots array
        while(numberOfNullRobots(allRobots) != allRobots.length) {

            // Loop through allRobots-array
            for (int i = 0; i < allRobots.length; i++) {

                // Check whether robot exists at index i
                if(allRobots[i] != null) {

                    // Put coin
                    allRobots[i].putCoin();

                    // Check whether robot would leave world
                    if(canMoveForwards(allRobots[i])) {

                        // If not, make robot move
                        allRobots[i].move();
                    } else {

                        // If yes, set robot to null
                        allRobots[i] = null;
                    }


                }
            }

            // Check whether we have at least 3 components
            if (allRobots.length >= 3) {

                // Get random indices i, j and k
                int[] indices = generateThreeDistinctRandomIndices(allRobots.length);

                // Sort indices-array
                sortArray(indices);

                // Swap the robots
                swapRobots(indices, allRobots);
            }

            // Get number of null elements in allRobots
            int l = numberOfNullRobots(allRobots);

            // If number is equal or greater than 3, create new array
            if (l >= 3) {

                // Initialize new array with updated length
                Robot[] tmp = new Robot[allRobots.length - l];

                // Initialize index for looping through array
                int index = 0;

                // Loop through existing array
                for (Robot roby : allRobots) {

                    // Add robot to new array, if robot is not null
                    if(roby != null) {
                        tmp[index++] = roby;
                    }
                }

                // Assign allRobots to new array
                allRobots = tmp;
            }
        }
    }

    /**
     * Returns how many of the components of the given robot-array are null.
     *
     * @param allRobots   The robot-array.
     * @return            Number of null elements in the array.
     */
    public static int numberOfNullRobots(Robot[] allRobots) {
        // Initialize counter for null elements
        int counter = 0;

        // Loop through allRobots array
        for (Robot roby : allRobots)

            // If array contains null element, increase counter
            if (roby == null)
                counter++;

        // Return counter
        return counter;
    }

    /**
     * Checks whether given robot can move to the right without leaving the world.
     *
     * @param roby    The robot.
     * @return        True, if the robot can move to the right.
     */
    public static boolean canMoveForwards(Robot roby) {
        // Check movement to the right
        if (roby.getDirection() == Direction.RIGHT && roby.getX() < World.getWidth() - 1) {
            return true;
        }

        // Check movement to the left
        if (roby.getDirection() == Direction.LEFT && roby.getX() > 0) {
            return true;
        }

        // Check movement upwards
        if (roby.getDirection() == Direction.UP && roby.getY() < World.getHeight() - 1) {
            return true;
        }

        // Check movement downwards, else return true
        return roby.getDirection() == Direction.DOWN && roby.getY() > 0;
    }

    /**
     * Creates an array containing three (pseudo-) random int values from 0 (inclusive) to given parameter (exclusive).
     *
     * @param bound   The upper bound for the int values.
     * @return        The array.
     */
    public static int[] generateThreeDistinctRandomIndices(int bound) {
        // Initialize i, j and k
        int i = 0, j = 0,  k = 0;

        // Create three distinct, random Integer-values
        while (i == j || i == k || k == j) {
            i = ThreadLocalRandom.current().nextInt(bound);
            j = ThreadLocalRandom.current().nextInt(bound);
            k = ThreadLocalRandom.current().nextInt(bound);
        }

        // Return array containing the random values
        return new int[]{i, j, k};
    }

    /**
     * Sorts the given 3 valued array from lowest to highest.
     *
     * @param array   The array to be sorted.
     */
    public static void sortArray(int[] array) {
        // Swap elements at index 0 and 1, if element at index 0 is bigger
        if (array[0] > array[1]) {
            int tmp = array[0];
            array[0] = array[1];
            array[1] = tmp;
        }

        // Swap elements at index 0 and 2, if element at index 0 is bigger
        if (array[0] > array[2]) {
            int tmp = array[0];
            array[0] = array[2];
            array[2] = tmp;
        }

        // Swap elements at index 1 and 2, if element at index 1 is bigger
        if (array[1] > array[2]) {
            int tmp = array[1];
            array[1] = array[2];
            array[2] = tmp;
        }
    }

    /**
     * Swaps three robots in given robot array.
     * Robot at index i will later be at index j.
     * Robot at index j will later be at index k.
     * Robot at index k will later be at index i.
     *
     * @param indices       Array containing indices i, j and k.
     * @param allRobots     Array containing the robots.
     */
    public static void swapRobots(int[] indices, Robot[] allRobots) {
        // Assign indices
        int i = indices[0];
        int j = indices[1];
        int k = indices[2];

        // Create temporary robot
        Robot tmp = allRobots[k];

        // Assign robot j to k
        allRobots[k] = allRobots[j];

        // Assign robot i to j
        allRobots[j] = allRobots[i];

        // Assign robot k to i
        allRobots[i] = tmp;
    }
}
