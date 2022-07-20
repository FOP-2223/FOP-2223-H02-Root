package h02;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h02")
class H3_3 {

    @Test
    void testSortArray() {
        int[] actual, expected;
        for (int i = 0; i < 10000; i++) {
            do {
                actual = ThreadLocalRandom.current().ints(3).distinct().toArray();
            } while (actual.length != 3);

            expected = actual.clone();

            Arrays.sort(expected);

            Main.sortArray(actual);

            assertArrayEquals(
                expected,
                actual,
                "Expected array " + Arrays.toString(actual) + " to be sorted!"
            );
        }
    }
}
