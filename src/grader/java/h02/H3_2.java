package h02;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
class H3_2 {

    int[] arrayWithDistinctRandomIndices;

    String arrayAsString;

    final int EXPECTED_LENGTH = 3;

    @Test
    void testLength() {
        int actualLength;
        for (int i = 0; i < 1000; i++) {
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(3);
            actualLength = arrayWithDistinctRandomIndices.length;
            arrayAsString = Arrays.toString(arrayWithDistinctRandomIndices);
            assertEquals(
                EXPECTED_LENGTH,
                actualLength,
                Utils.getGeneralInfo("Generated array: " + Arrays.toString(arrayWithDistinctRandomIndices)) +
                    "Expected each array to contain 3 elements but the generated array contains " + actualLength + " elements!"
            );
        }
    }

    @Test
    void testDissimilarityOfElements() {
        int bound;
        for (int i = 0; i < 100; i++) {
            bound = ThreadLocalRandom.current().nextInt(2,256);
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(bound);
            arrayAsString = Arrays.toString(arrayWithDistinctRandomIndices);
            assertNotEquals(
                arrayWithDistinctRandomIndices[0],
                arrayWithDistinctRandomIndices[1],
                Utils.getGeneralInfo("Generated array: " + Arrays.toString(arrayWithDistinctRandomIndices)) +
                    "Expected each array to contain distinct elements but in the generated array the elements at index 0 and 1 are equal!"
            );

            assertNotEquals(
                arrayWithDistinctRandomIndices[0],
                arrayWithDistinctRandomIndices[2],
                Utils.getGeneralInfo("Generated array: " + Arrays.toString(arrayWithDistinctRandomIndices)) +
                    "Expected each array to contain distinct elements but in the generated array the elements at index 0 and 2 are equal!"
            );

            assertNotEquals(
                arrayWithDistinctRandomIndices[1],
                arrayWithDistinctRandomIndices[2],
                Utils.getGeneralInfo("Generated array: " + Arrays.toString(arrayWithDistinctRandomIndices)) +
                    "Expected each array to contain distinct elements but in the generated array the elements at index 1 and 2 are equal!"
            );
        }
    }

    @Test
    void testBounds() {
        int bound;
        for (int i = 0; i < 100; i++) {
            bound = ThreadLocalRandom.current().nextInt(2,256);
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(bound);
            arrayAsString = Arrays.toString(arrayWithDistinctRandomIndices);
            for (int j = 0; j < arrayWithDistinctRandomIndices.length; j++) {
                assertTrue(
                    arrayWithDistinctRandomIndices[j] < bound,
                    Utils.getGeneralInfo("Generated array: " + Arrays.toString(arrayWithDistinctRandomIndices)) +
                        "Bound was " + bound + " but element at index " + j + " in the generated array is larger!"
                );
                assertTrue(
                    arrayWithDistinctRandomIndices[j] >= 0,
                    Utils.getGeneralInfo("Generated array: " + Arrays.toString(arrayWithDistinctRandomIndices)) +
                        "Expected each element to be greater than 0 but element at index " + j + " in the generated array is smaller!"
                );
            }
        }
    }

    @Test
    void testDissimilarityOfArrays() {
        int[] comparator = Main.generateThreeDistinctRandomIndices(3);
        int equals = 0;
        for (int i = 0; i < 10000; i++) {
            arrayWithDistinctRandomIndices = Main.generateThreeDistinctRandomIndices(3);
            if (comparator.equals(arrayWithDistinctRandomIndices))
                equals++;
            comparator = arrayWithDistinctRandomIndices;
        }
        assertNotEquals(
            10000,
            equals,
            Utils.getGeneralInfo("Generated array was always: " + Arrays.toString(arrayWithDistinctRandomIndices)) +
                "Method was supposed to randomly generate arrays but all of the samples were the same!"
        );
    }
}
