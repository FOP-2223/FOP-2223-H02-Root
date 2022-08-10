package h02;

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
                () -> H3_1.class.getDeclaredMethod("testNumberOfNullRobots")
            )
        )
        .build();

    private static final Criterion CRITERION_H3_2 = Criterion
        .builder()
        .shortDescription("H3.2: Drei (pseudo-)zufällige int-Werte")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "All constructed arrays contain exactly 3 elements.",
                () -> H3_2.class.getDeclaredMethod("testLength")
            ),
            DEFAULT_CRITERION.apply(
                "All generated elements are different.",
                () -> H3_2.class.getDeclaredMethod("testDissimilarityOfElements")
            ),
            DEFAULT_CRITERION.apply(
                "All generated elements are larger or equal to 0 and smaller than given bound.",
                () -> H3_2.class.getDeclaredMethod("testBounds")
            ),
            DEFAULT_CRITERION.apply(
                "All generated Arrays are not always the same.",
                () -> H3_2.class.getDeclaredMethod("testDissimilarityOfArrays")
            )
        )
        .build();

    private static final Criterion CRITERION_H3_3 = Criterion
        .builder()
        .shortDescription("H3.3: Sortierung eines 3-elementigen int-Arrays")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "All arrays are sorted correctly.",
                () -> H3_3.class.getDeclaredMethod("testSortArray")
            )
        )
        .build();

    private static final Criterion CRITERION_H3_4 = Criterion
        .builder()
        .shortDescription("H3.4: Vertauschen von Robotern")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Swapping of robots works correctly.",
                () -> H3_4.class.getDeclaredMethod("testSwapRobots")
            )
        )
        .build();

    private static final Criterion CRITERION_H1 = Criterion
        .builder()
        .shortDescription("H1: Initialisierungen vor der Hauptschleife")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "The number of initialized robots is correct.",
                () -> H1.class.getDeclaredMethod("testNumberOfRobots")
            ),
            DEFAULT_CRITERION.apply(
                "All robots are spawned at the correct coordinates.",
                () -> H1.class.getDeclaredMethod("testRobotCoordinates")
            ),
            DEFAULT_CRITERION.apply(
                "All robots have the correct amount of coins.",
                () -> H1.class.getDeclaredMethod("testRobotCoins")
            ),
            DEFAULT_CRITERION.apply(
                "All robots face to the right.",
                () -> H1.class.getDeclaredMethod("testRobotDirections")
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
