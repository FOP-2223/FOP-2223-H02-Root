package h02.h3.h3_3;

import h02.Main;
import h02.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static h02.h3.H3Utils.convertStringToIntArray;

@TestForSubmission("h02")
public class SortArrayTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/h3/arraysToSort.csv")
    void testSorting(String arrayAsString) {
        int[] actual = convertStringToIntArray(arrayAsString);
        int[] expected = actual.clone();
        Main.sortArray(actual);
        Arrays.sort(expected);
        assertArrayEquals(
            expected,
            actual,
            Utils.getGeneralInfo("Your sorted array: " + Arrays.toString(actual)) +
                "Expected the array to be sorted!"
        );
    }
}
