package h02.h3.h3_2;

import h02.Main;
import h02.Utils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
public
class GenerateThreeDistinctRandomIndicesTest {

    static final int BOUND = 3;
    static final int NUMBER_OF_SAMPLES = 100;
    static final int EXPECTED_LENGTH = 3;

    static final ArrayList<int[]> arraySamples = new ArrayList<>();
    static int[] comparator;

    @BeforeAll
    static void setup() {
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            arraySamples.add(Main.generateThreeDistinctRandomIndices(BOUND));
        }
        comparator = Main.generateThreeDistinctRandomIndices(BOUND);
    }

    @Test
    void testLength() {
        int actualLength;
        String arrayAsString;
        for (int[] sample : arraySamples) {
            actualLength = sample.length;
            arrayAsString = Arrays.toString(sample);
            assertEquals(
                EXPECTED_LENGTH,
                actualLength,
                Utils.getGeneralInfo("Generated array: " + arrayAsString) +
                    "Expected each array to contain 3 elements but the generated array contains " + actualLength + " elements!"
            );
        }
    }

    @Test
    void testDissimilarityOfElements() {
        String arrayAsString;
        for (int[] sample : arraySamples) {
            arrayAsString = Arrays.toString(sample);
            assertNotEquals(
                sample[0],
                sample[1],
                Utils.getGeneralInfo("Generated array: " + arrayAsString) +
                    "Expected each array to contain distinct elements but in the generated array the elements at index 0 and 1 are equal!"
            );

            assertNotEquals(
                sample[0],
                sample[2],
                Utils.getGeneralInfo("Generated array: " + arrayAsString) +
                    "Expected each array to contain distinct elements but in the generated array the elements at index 0 and 2 are equal!"
            );

            assertNotEquals(
                sample[1],
                sample[2],
                Utils.getGeneralInfo("Generated array: " + arrayAsString) +
                    "Expected each array to contain distinct elements but in the generated array the elements at index 1 and 2 are equal!"
            );
        }
    }

    @Test
    void testBounds() {
        String arrayAsString;
        for (int[] sample : arraySamples) {
            arrayAsString = Arrays.toString(sample);
            for (int j = 0; j < sample.length; j++) {
                assertTrue(
                    sample[j] < BOUND,
                    Utils.getGeneralInfo("Generated array: " + arrayAsString) +
                        "Bound was " + BOUND + " but element at index " + j + " in the generated array is larger!"
                );
                assertTrue(
                    sample[j] >= 0,
                    Utils.getGeneralInfo("Generated array: " + arrayAsString) +
                        "Expected each element to be greater than 0 but element at index " + j + " in the generated array is smaller!"
                );
            }
        }
    }

    @Test
    void testDissimilarityOfArrays() {
        int equals = 0;
        for (int[] sample : arraySamples) {
            if (Arrays.equals(comparator, sample))
                equals++;
            comparator = sample;
        }
        assertNotEquals(
            100,
            equals,
            Utils.getGeneralInfo("Generated array was always: " + Arrays.toString(arraySamples.get(0))) +
                "Method was supposed to randomly generate arrays but all of the samples were the same!"
        );
    }
}
