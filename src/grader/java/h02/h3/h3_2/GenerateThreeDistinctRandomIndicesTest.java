package h02.h3.h3_2;

import h02.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.ArrayList;
import java.util.Arrays;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission("h02")
public
class GenerateThreeDistinctRandomIndicesTest {

    static final int BOUND = 3;
    static final int NUMBER_OF_SAMPLES = 100;
    static final int EXPECTED_LENGTH = 3;

    static final ArrayList<int[]> arraySamples = new ArrayList<>();
    static int[] comparator;

    private static final Main main = new Main();

    @BeforeAll
    static void setup() {
        for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
            arraySamples.add(main.generateThreeDistinctRandomIndices(BOUND));
        }
        comparator = main.generateThreeDistinctRandomIndices(BOUND);
    }

    @Test
    void testLength() {
        String arrayAsString;
        for (int[] sample : arraySamples) {
            int actualLength = sample.length;
            arrayAsString = Arrays.toString(sample);

            var context = contextBuilder()
                .add("Generated array", arrayAsString)
                .build();

            assertEquals(
                EXPECTED_LENGTH,
                actualLength,
                context,
                r -> String.format("Expected each array to contain 3 elements but the generated array contains %d elements!", actualLength)
            );
        }
    }

    @Test
    void testDissimilarityOfElements() {
        String arrayAsString;
        for (int[] sample : arraySamples) {
            arrayAsString = Arrays.toString(sample);

            var context = contextBuilder()
                .add("Generated array", arrayAsString)
                .build();

            assertNotEquals(
                sample[0],
                sample[1],
                context,
                r -> "Expected each array to contain distinct elements but in the generated array the elements at index 0 and 1 are equal!"
            );

            assertNotEquals(
                sample[0],
                sample[2],
                context,
                r -> "Expected each array to contain distinct elements but in the generated array the elements at index 0 and 2 are equal!"
            );

            assertNotEquals(
                sample[1],
                sample[2],
                context,
                r -> "Expected each array to contain distinct elements but in the generated array the elements at index 1 and 2 are equal!"
            );
        }
    }

    @Test
    void testBounds() {
        String arrayAsString;
        for (int[] sample : arraySamples) {
            arrayAsString = Arrays.toString(sample);

            var context = contextBuilder()
                .add("Generated array", arrayAsString)
                .build();

            for (int j = 0; j < sample.length; j++) {
                int finalJ = j;
                assertTrue(
                    sample[j] < BOUND,
                    context,
                    r -> String.format("Bound was %d but the element at index %d in the generated array is larger!", BOUND, finalJ)
                );
                assertTrue(
                    sample[j] >= 0,
                    context,
                    r -> String.format("Expected each element to be greater than 0 but element at index %d in the generated array is smaller!", finalJ)
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

        var context = contextBuilder()
            .add("All generated arrays", Arrays.toString(arraySamples.get(0)))
            .build();

        assertNotEquals(
            100,
            equals,
            context,
            r -> "Method was supposed to randomly generate arrays but all of the samples were the same!"
        );
    }
}
