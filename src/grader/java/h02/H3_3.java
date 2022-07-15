package h02;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
class H3_3 {

    int[] arrayWithDistinctRandomIndices;

    final int expectedLength = 3;

    @Test
    void testLength() {
        int actualLength;
        for (int i = 0; i < 1000; i++) {
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(3);
            actualLength = arrayWithDistinctRandomIndices.length;
            String array = Utils.arrayToString(arrayWithDistinctRandomIndices);
            assertEquals(expectedLength,
                actualLength,
                "Expected each array to contain 3 elements, but the array " + array + " contains " + actualLength + " elements!");
        }
    }

    @Test
    void testDissimilarityOfElements() {
        int bound;
        for (int i = 0; i < 1000; i++) {
            bound = ThreadLocalRandom.current().nextInt(2,256);
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(bound);
            String array = Utils.arrayToString(arrayWithDistinctRandomIndices);
            assertNotEquals(arrayWithDistinctRandomIndices[0],
                arrayWithDistinctRandomIndices[1],
                "Expected each array to contain distinct elements, but in the array " + array + " the elements at index 0 and 1 are equal!");

            assertNotEquals(arrayWithDistinctRandomIndices[0],
                arrayWithDistinctRandomIndices[2],
                "Expected each array to contain distinct elements, but in the array " + array + " the elements at index 0 and 2 are equal!");

            assertNotEquals(arrayWithDistinctRandomIndices[1],
                arrayWithDistinctRandomIndices[2],
                "Expected each array to contain distinct elements, but in the array " + array + " the elements at index 1 and 2 are equal!");
        }
    }

    @Test
    void testBounds() {
        int bound;
        for (int i = 0; i < 1000; i++) {
            bound = ThreadLocalRandom.current().nextInt(2,256);
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(bound);
            String array = Utils.arrayToString(arrayWithDistinctRandomIndices);
            for (int j = 0; j < arrayWithDistinctRandomIndices.length; j++) {
                assertTrue(arrayWithDistinctRandomIndices[j] < bound,
                    "Bound was " + bound + ", but element at index " + j + " in array " + array + " is larger!");
                assertTrue(arrayWithDistinctRandomIndices[j] >= 0,
                    "Expected each element to be greater than 0, but element at index " + j + " in array " + array + " is smaller!");
            }
        }
    }

    @Test
    void testDissimilarityOfArrays() {
        int[] comparator = Main.generateThreeDistinctRandomIndices(3);
        int equals = 0;
        for (int i = 0; i < 10000; i++) {
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(3)
        }
    }
}
