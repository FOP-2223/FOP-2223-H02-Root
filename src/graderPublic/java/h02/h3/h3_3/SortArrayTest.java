package h02.h3.h3_3;

import h02.Main;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.*;

import static h02.h3.H3Utils.convertStringToIntArray;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class SortArrayTest {

    private static final String PATH_TO_CSV = "/h3/h3_3/ArraysToSort.csv";

    private static final Main main = new Main();

    @ParameterizedTest
    @CsvFileSource(resources = PATH_TO_CSV)
    void testSorting(String arrayAsString) {
        int[] actual = convertStringToIntArray(arrayAsString);
        int[] expected = actual.clone();
        main.sortArray(actual);
        Arrays.sort(expected);

        var context = contextBuilder()
            .add("Sorted array", Arrays.toString(expected))
            .add("Your result", Arrays.toString(actual))
            .build();

        for (int i = 0; i < expected.length; i++) {
            int finalI = i;
            assertEquals(
                expected[i],
                actual[i],
                context,
                r -> String.format("Expected the array to be sorted but the element at index %d is wrong!", finalI)
            );
        }
    }
}
