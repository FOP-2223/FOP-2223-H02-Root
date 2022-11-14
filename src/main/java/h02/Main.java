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
        // Get number of columns from method
        int numberOfColumns = getRandomWorldSize();

        // Get number of rows from method
        int numberOfRows = getRandomWorldSize();

        // Initialize World with specified number of columns and rows
        World.setSize(numberOfColumns, numberOfRows);

        // Set the internal delay of the world
        World.setDelay(DELAY);

        // Set the world visible
        World.setVisible(true);

        // Print out size of the world to the command line
        System.out.println("Size of world: " + numberOfColumns + "x" + numberOfRows);

        // Initialize new Main-object to call methods
        Main main = new Main();

        // Initialize a pattern provider for the .txt-file in resources
        PatternProvider patternProvider;
        try {
            patternProvider = new PatternProvider(FILENAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the pattern from the .txt file
        boolean[][] testPattern = patternProvider.getPattern();

        // Call initializeRobotsPattern
        Robot[] allRobots = main.initializeRobotsPattern(testPattern, numberOfColumns, numberOfRows);

        // Call letRobotsMarch with initialized Pattern
        main.letRobotsMarch(allRobots);
    }

    /**
     * Counts the number of robots in a pattern, given a specified world size.
     *
     * @param pattern           The pattern for the robots.
     * @param numberOfColumns   Number of columns in the world.
     * @param numberOfRows      Number of rows in the world.
     * @return                  Number of robots in the world.
     */
    public int countRobotsInPattern(boolean[][] pattern, int numberOfColumns, int numberOfRows) {
        // Number of robots that are present in the pattern and also satisfy conditions (a) - (e)
        int numberOfRobots = 0;

        // Loop through the columns of the world. Satisfies condition (a)
        for (int x = 0; x < numberOfColumns; x++) {

            // Loop through the rows of the world. Satisfies condition (b)
            for (int y = 0; y < numberOfRows; y++) {

                // Condition (c)
                boolean c = x < pattern.length;

                // Condition (d), only satisfiable if (c) is met
                boolean d = c && y < pattern[x].length;

                // Condition (e), only satisfiable if (c) AND (d) are met
                boolean e = c && d && pattern[x][y];

                // Iff (if and only if) all five conditions are met, increase number of robots
                if (c && d && e) {
                    numberOfRobots++;
                }

                // Note that conditions can be summarized in one if-statement, as can be seen in the following method
            }
        }

        // Return the number of robots
        return numberOfRobots;
    }

    /**
     * Initialize allRobots array for given pattern and world size.
     *
     * @param pattern           The pattern for the robots.
     * @param numberOfColumns   Number of columns in world.
     * @param numberOfRows      Number of rows in world.
     * @return                  Correctly initialized allRobots array.
     */
    public Robot[] initializeRobotsPattern(boolean[][] pattern, int numberOfColumns, int numberOfRows) {
        // Initialize Robot-array with a call of countRobotsInPattern
        Robot[] allRobots = new Robot[countRobotsInPattern(pattern, numberOfColumns, numberOfRows)];

        // Index for the robots since we can not use x or y of the for-loops for our allRobots-array
        int indexForRobot = 0;

        // Loop through the columns of the world. Satisfies condition (a)
        for (int x = 0; x < numberOfColumns; x++) {

            // Loop through the rows of the world. Satisfies condition (b)
            for (int y = 0; y < numberOfRows; y++) {

                // Satisfy conditions c to e in one if-statement
                if (x < pattern.length && y < pattern[x].length && pattern[x][y]) {

                    // Initialize Robot, note that indexForRobot++ increases indexForRobot AFTER this line is executed
                    allRobots[indexForRobot++] = new Robot(x, y, Direction.RIGHT, numberOfColumns - x);
                }
            }
        }


        // Do you see a drawback of using arrays?


        // Return the array containing the Robots
        return allRobots;
    }

    /**
     * Returns how many of the components of the given robot-array are null.
     *
     * @param allRobots   The Robot-array.
     * @return            True, if array contains robot.
     */
    public int numberOfNullRobots(Robot[] allRobots) {
        // Initialize counter for null elements
        int counter = 0;

        // Loop through allRobots array, can also be done using a classic fori loop
        for (Robot roby : allRobots) {

            // If array contains null element, increase counter
            if (roby == null) {
                counter++;
            }
        }

        // Return counter
        return counter;
    }

    /**
     * Creates an array containing three (pseudo-) random int values from 0 (inclusive) to given parameter (exclusive).
     *
     * @param bound   The upper bound for the int values.
     * @return        The array.
     */
    public int[] generateThreeDistinctRandomIndices(int bound) {
        // Initialize i, j and k
        int i = 0, j = 0,  k = 0;

        // Create three distinct, random Integer-values
        // Using a while-loop guarantees that each index is distinct
        // Do you have a better idea for solving this problem?
        while (i == j || i == k || k == j) {
            i = ThreadLocalRandom.current().nextInt(bound);
            j = ThreadLocalRandom.current().nextInt(bound);
            k = ThreadLocalRandom.current().nextInt(bound);
        }

        // Return array containing the random values, using the faster array creation method explained in H1.2
        return new int[]{i, j, k};
    }

    /**
     * Sorts the given 3 valued array from lowest to highest.
     *
     * @param array   The array to be sorted.
     */
    public void sortArray(int[] array) {
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
    public void swapRobots(int[] indices, Robot[] allRobots) {
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

        // This way you only have to create one temporary Robot-object, meaning less memory used
    }

    /**
     * Reduces the given robot array by the set amount and only keeps non-null components.
     *
     * @param robots    The array to be reduced.
     * @param reduceBy  The number of indices that are reduced.
     * @return          The reduced array.
     */
    public Robot[] reduceRobotArray(Robot[] robots, int reduceBy) {
        // Create new Robot-array with less space
        Robot[] newArray = new Robot[robots.length - reduceBy];

        // Create index for new Robot-array, same reason as in H1.1 (countRobotsInPattern and H1.2 (initializeRobotsPattern)
        int index = 0;

        // Loop through old array to find non-null Robot-objects using an advanced for-loop
        for (Robot robot : robots) {

            // Check whether component is non-null
            if (robot != null) {

                // Iff it is non-null, add it to the new array
                // Again, note that index++ increases index afterwards
                newArray[index++] = robot;
            }
        }

        // Return the new arrrrrrrrrrrrrrrrrrrrray, hurray
        return newArray;
    }

    /**
     * Lets all robots in the given array walk to the right while also putting down coins.
     * If robots leave the world they are set to null.
     * After the steps are made, if more than three robots exist, three of them change their index.
     * If 3 or more components of the array are null, the array is reduced by the amount of null components.
     *
     * @param allRobots   Array containing all the robots.
     */
    public void letRobotsMarch(Robot[] allRobots) {
        // Main loop ("Hauptschleife")
        // Call numberOfNullRobots to ensure there are non-null elements in the array
        while(numberOfNullRobots(allRobots) != allRobots.length) {

            // Loop through allRobots-array
            for (int i = 0; i < allRobots.length; i++) {

                // Check whether robot exists at index i
                if(allRobots[i] != null) {

                    // Put coin
                    allRobots[i].putCoin();

                    // Check whether robot would leave world
                    if((allRobots[i].getX() + 1) != World.getWidth()) {

                        // If not, make robot move
                        allRobots[i].move();
                    } else {

                        // If yes, set robot to null
                        allRobots[i] = null;
                    }
                }
            }

            // Check whether we have at least 3 components
            // That is important since all methods only work with three or more components
            if (allRobots.length >= 3) {

                // Call generateThreeDistinctRandomIndices to get random indices i, j and k
                int[] indices = generateThreeDistinctRandomIndices(allRobots.length);

                // Call sortArray to sort the newly generated indices-array
                sortArray(indices);

                // Call swapRobots to swap the robots in allRobots
                swapRobots(indices, allRobots);
            }

            // Call numberOfNullRobots to get number of null components in allRobots
            int l = numberOfNullRobots(allRobots);

            // If number is equal or greater than 3, reduce the array
            if (l >= 3) {

                // Call reduceRobotArray to reduce the array by l
                allRobots = reduceRobotArray(allRobots, l);

                // Again, do you see a drawback using arrays?
            }

            // This way all the previously implemented methods are called
        }
    }
}
