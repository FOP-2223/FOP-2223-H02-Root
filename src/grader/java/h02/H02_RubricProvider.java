package h02;

import h02.h1.InitializeRobotsPatternTest;
import h02.h3.h3_1.NumberOfNullRobotsTest;
import h02.h3.h3_2.GenerateThreeDistinctRandomIndicesTest;
import h02.h3.h3_3.SortArrayTest;
import h02.h3.h3_4.SwapRobotsTest;
import org.sourcegrade.jagr.api.rubric.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

@RubricForSubmission("h02")
public class H02_RubricProvider implements RubricProvider {

    private static final BiFunction<String, Callable<Method>, Criterion> DEFAULT_CRITERION = (s, methodCallable) ->
        Criterion.builder()
            .shortDescription(s)
            .grader(Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(methodCallable))
                .pointsFailedMin()
                .pointsPassedMax()
                .build())
            .build();

    private static final Criterion CRITERION_H3_1 = Criterion
        .builder()
        .shortDescription("H3.1: Arraykomponenten gleich null")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Number of elements equal to null is correctly calculated.",
                () -> NumberOfNullRobotsTest.class.getDeclaredMethod("testNumberOfNullRobots", String.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3_2 = Criterion
        .builder()
        .shortDescription("H3.2: Drei (pseudo-)zufällige int-Werte")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "All constructed arrays contain exactly 3 elements.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testLength")
            ),
            DEFAULT_CRITERION.apply(
                "All generated elements are different.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testDissimilarityOfElements")
            ),
            DEFAULT_CRITERION.apply(
                "All generated elements are larger or equal to 0 and smaller than given bound.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testBounds")
            ),
            DEFAULT_CRITERION.apply(
                "All generated Arrays are not always the same.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testDissimilarityOfArrays")
            )
        )
        .build();

    private static final Criterion CRITERION_H3_3 = Criterion
        .builder()
        .shortDescription("H3.3: Sortierung eines 3-elementigen int-Arrays")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "All arrays are sorted correctly.",
                () -> SortArrayTest.class.getDeclaredMethod("testSorting", String.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3_4 = Criterion
        .builder()
        .shortDescription("H3.4: Vertauschen von Robotern")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Swapping of robots works correctly.",
                () -> SwapRobotsTest.class.getDeclaredMethod("testSwapRobots")
            )
        )
        .build();

    private static final Criterion CRITERION_H1 = Criterion
        .builder()
        .shortDescription("H1: Initialisierungen vor der Hauptschleife")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "The number of initialized robots is correct.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testNumberOfRobotsWithFittingPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "All robots are spawned at the correct coordinates.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testCoordinatesWithFittingPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "All robots have the correct amount of coins.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testCoinsWithFittingPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "All robots face to the right.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testDirectionsWithFittingPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "The method can handle patterns that do not fit the world size.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testNotFittingPatterns", String.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3 = Criterion
        .builder()
        .shortDescription("H3: Die Hauptschleife")
        .addChildCriteria(CRITERION_H3_1, CRITERION_H3_2, CRITERION_H3_3, CRITERION_H3_4)
        .build();

    private static final Rubric RUBRIC = Rubric.builder()
        .title("H02: ")
        .addChildCriteria(CRITERION_H1, CRITERION_H3)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
