package h02;

import h02.h1.h1_1.CountRobotsInPatternTest;
import h02.h1.h1_2.InitializeRobotsPatternTest;
import h02.h3.h3_1.NumberOfNullRobotsTest;
import h02.h3.h3_2.GenerateThreeDistinctRandomIndicesTest;
import h02.h3.h3_3.SortArrayTest;
import h02.h3.h3_4.SwapRobotsTest;
import h02.h3.h3_5.ReduceRobotArrayTest;
import h02.h4.LetRobotsMarchTest;
import org.sourcegrade.jagr.api.rubric.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;

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

    private static final Criterion CRITERION_H1_1 = Criterion
        .builder()
        .shortDescription("H1.1: Zählen von [[[true]]] in einem Array von Array von [[[boolean]]]")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Method correctly counts the number of robots for a pattern with a size fitting the world.",
                () -> CountRobotsInPatternTest.class.getDeclaredMethod("testFittingPattern", String.class, int.class)
            ),
            DEFAULT_CRITERION.apply(
                "Method correctly counts the number of robots for a pattern with a size not fitting the world.",
                () -> CountRobotsInPatternTest.class.getDeclaredMethod("testUnFittingPattern", String.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H1_2 = Criterion
        .builder()
        .shortDescription("H1.2: Erstellen eines [[[Robot]]]-Arrays mittels eines Patterns")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Method uses [[[countRobotsInPattern]]] at least once.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testInvocationsOfCountOfRobotsInPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Method initializes a correct number of robots",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testNumberOfRobots", boolean[][].class, int.class)
            ),
            DEFAULT_CRITERION.apply(
                "Method initializes robots at the correct coordinates",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testCoordinates", boolean[][].class)
            ),
            DEFAULT_CRITERION.apply(
                "Method initializes robots with the correct number of coins",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testCoins", boolean[][].class)
            ),
            DEFAULT_CRITERION.apply(
                "Method initializes robots with the correct direction.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testDirections", boolean[][].class)
            ),
            DEFAULT_CRITERION.apply(
                "Method does correct initialization with a pattern that does not fit the world.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testNotFittingPatterns", String.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H1 = Criterion
        .builder()
        .shortDescription("H1: Erstellen eines [[[Robot]]]-Arrays")
        .addChildCriteria(CRITERION_H1_1, CRITERION_H1_2)
        .build();

    private static final Criterion CRITERION_H3_1 = Criterion
        .builder()
        .shortDescription("H3.1: Arraykomponenten gleich [[[null]]]")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Number of elements equal to [[[null]]] is correctly counted.",
                () -> NumberOfNullRobotsTest.class.getDeclaredMethod("testNumberOfNullRobots", String.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3_2 = Criterion
        .builder()
        .shortDescription("H3.2: Drei (pseudo-)zufällige [[[int]]]-Werte")
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
                "All generated arrays are not always the same.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testDissimilarityOfArrays")
            )
        )
        .build();

    private static final Criterion CRITERION_H3_3 = Criterion
        .builder()
        .shortDescription("H3.3: Sortierung eines 3-elementigen [[[int]]]-Arrays")
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
                () -> SwapRobotsTest.class.getDeclaredMethod("testSwapping", String.class, int.class, int.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3_5 = Criterion
        .builder()
        .shortDescription("H3.5: Reduzieren eines Arrays")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Method correctly reduces the array.",
                () -> ReduceRobotArrayTest.class.getDeclaredMethod("testSize", String.class, int.class)
            ),
            DEFAULT_CRITERION.apply(
                "Method keeps the robots in the same order.",
                () -> ReduceRobotArrayTest.class.getDeclaredMethod("testRobotsAfterResize", String.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3 = Criterion
        .builder()
        .shortDescription("H3: Hilfsmethoden für die Hauptschleife")
        .addChildCriteria(CRITERION_H3_1, CRITERION_H3_2, CRITERION_H3_3, CRITERION_H3_4, CRITERION_H3_5)
        .build();

    private static final Criterion CRITERION_H4 = Criterion
        .builder()
        .shortDescription("H3.5: Die Hauptschleife")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Method calls other methods implemented in H3.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testUseOfMethods")
            ),
            DEFAULT_CRITERION.apply(
                "Method correctly works with an array containing only [[[null]]].",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testNullArray")
            ),
            DEFAULT_CRITERION.apply(
                "Method does not throw any exceptions.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("checkForExceptions", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Robots do not perform actions other than move or put a coin.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("checkActions", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "All robots move to the end.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testAllRobotsReachEnd", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "All robots put the correct amount of coins.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testAllRobotsPutCoins", String.class)
            )
        )
        .build();

    private static final Rubric RUBRIC = Rubric.builder()
        .title("H02 - Let them march")
        .addChildCriteria(CRITERION_H1, CRITERION_H3, CRITERION_H4)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
