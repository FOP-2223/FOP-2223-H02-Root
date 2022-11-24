package h02;

import h02.h1.h1_1.CountRobotsInPatternTest;
import h02.h1.h1_2.InitializeRobotsPatternTest;
import h02.h3.h3_1.NumberOfNullRobotsTest;
import h02.h3.h3_2.GenerateThreeDistinctRandomIndicesTest;
import h02.h3.h3_3.SortArrayTest;
import h02.h3.h3_4.SwapRobotsTest;
import h02.h3.h3_5.ReduceRobotArrayTest;
import h02.h4.LetRobotsMarchTest;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

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

    private static final BiFunction<String, Callable<Method>, Criterion> TWO_POINTS_CRITERION = (s, methodCallable) ->
        Criterion.builder()
            .shortDescription(s)
            .maxPoints(2)
            .grader(Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(methodCallable))
                .pointsFailedMin()
                .pointsPassedMax()
                .build())
            .build();

    private static final Criterion CRITERION_H1_1 = Criterion
        .builder()
        .shortDescription("H1.1 | Zählen von \\<samp\\>true\\</samp\\> in einem Array von Array von \\<samp\\>boolean\\</samp\\>")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Methode zählt die Anzahl der Roboter für ein gegebenes Muster, welches in die Welt passt, korrekt.",
//                "Method correctly counts the number of robots for a pattern with a size fitting the world.",
                () -> CountRobotsInPatternTest.class.getDeclaredMethod("testFittingPattern", String.class, int.class)
            ),
            DEFAULT_CRITERION.apply(
                "Methode zählt die Anzahl der Roboter für ein gegebenes Muster, welches nicht in die Welt passt, korrekt.",
//                "Method correctly counts the number of robots for a pattern with a size not fitting the world.",
                () -> CountRobotsInPatternTest.class.getDeclaredMethod("testUnFittingPattern", String.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H1_2 = Criterion
        .builder()
        .shortDescription("H1.2 | Erstellen eines \\<samp\\>Robot\\</samp\\>-Arrays mittels eines Patterns")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Methode benutzt \\<samp\\>countRobotsInPattern\\</samp\\> mindestens einmal.",
//                "Method uses \\<samp\\>countRobotsInPattern\\</samp\\> at least once.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testInvocationsOfCountOfRobotsInPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Methode initialisiert die korrekte Anzahl von Robotern.",
//                "Method initializes a correct number of robots",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testNumberOfRobotsWithFittingPattern", String.class, int.class)
            ),
            DEFAULT_CRITERION.apply(
                "Methode initialisiert die Roboter mit den korrekten Koordinaten.",
//                "Method initializes robots at the correct coordinates",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testCoordinatesWithFittingPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Methode initialisiert die Roboter mit der korrekten Anzahl an Münzen.",
//                "Method initializes robots with the correct number of coins",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testCoinsWithFittingPattern", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Methode initialisiert die Roboter mit der korrekten Ausrichtung.",
//                "Method initializes robots with the correct direction.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testDirectionsWithFittingPattern", String.class)
            ),
            TWO_POINTS_CRITERION.apply(
                "Methode initialisiert die Roboter korrekt, wenn das Muster nicht in die Welt passt.",
//                "Method does correct initialization with a pattern that does not fit the world.",
                () -> InitializeRobotsPatternTest.class.getDeclaredMethod("testNotFittingPatterns", String.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H1 = Criterion
        .builder()
        .shortDescription("H1 | Erstellen eines \\<samp\\>Robot\\</samp\\>-Arrays")
        .addChildCriteria(CRITERION_H1_1, CRITERION_H1_2)
        .build();

    private static final Criterion CRITERION_H3_1 = Criterion
        .builder()
        .shortDescription("H3.1 | Arraykomponenten gleich \\<samp\\>null\\</samp\\>")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Die Anzahl der Komponenten mit Wert \\<samp\\>null\\</samp\\> wird korrekt gezählt.",
//                "Number of elements equal to \\<samp\\>null\\</samp\\> is correctly counted.",
                () -> NumberOfNullRobotsTest.class.getDeclaredMethod("testNumberOfNullRobots", String.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3_2 = Criterion
        .builder()
        .shortDescription("H3.2 | Drei (pseudo-)zufällige \\<samp\\>int\\</samp\\>-Werte")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Alle erzeugten Arrays haben exakt die Länge 3.",
//                "All constructed arrays contain exactly 3 elements.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testLength")
            ),
            DEFAULT_CRITERION.apply(
                "Die erzeugten Zahlen in den Arrays sind unterschiedlich.",
//                "All generated elements are different.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testDissimilarityOfElements")
            ),
            DEFAULT_CRITERION.apply(
                "Alle Komponenten der Arrays sind größer oder gleich 0 und kleiner als die gegebene obere Schranke.",
//                "All generated elements are larger or equal to 0 and smaller than given bound.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testBounds")
            ),
            DEFAULT_CRITERION.apply(
                "Die Rückgaben unterscheiden sich; es wird nicht jedes Mal das gleiche Objekt zurückgegeben.",
//                "All generated arrays are not always the same.",
                () -> GenerateThreeDistinctRandomIndicesTest.class.getDeclaredMethod("testDissimilarityOfArrays")
            )
        )
        .build();

    private static final Criterion CRITERION_H3_3 = Criterion
        .builder()
        .shortDescription("H3.3 | Sortierung eines 3-elementigen \\<samp\\>int\\</samp\\>-Arrays")
        .maxPoints(2)
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Alle Arrays werden korrekt sortiert.",
//                "All arrays are sorted correctly.",
                () -> SortArrayTest.class.getDeclaredMethod("testSorting", String.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3_4 = Criterion
        .builder()
        .shortDescription("H3.4 | Vertauschen von Robotern")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Das Vertauschen der Roboter funktioniert korrekt.",
//                "Swapping of robots works correctly.",
                () -> SwapRobotsTest.class.getDeclaredMethod("testSwapping", String.class, int.class, int.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3_5 = Criterion
        .builder()
        .shortDescription("H3.5 | Reduzieren eines Arrays")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Die Methode reduziert die Arrays korrekt.",
//                "Method correctly reduces the array.",
                () -> ReduceRobotArrayTest.class.getDeclaredMethod("testSize", String.class, int.class)
            ),
            DEFAULT_CRITERION.apply(
                "Die Methode behält die Reihenfolge der Roboter bei.",
//                "Method keeps the robots in the same order.",
                () -> ReduceRobotArrayTest.class.getDeclaredMethod("testRobotsAfterResize", String.class, int.class)
            )
        )
        .build();

    private static final Criterion CRITERION_H3 = Criterion
        .builder()
        .shortDescription("H3 | Hilfsmethoden für die Hauptschleife")
        .addChildCriteria(CRITERION_H3_1, CRITERION_H3_2, CRITERION_H3_3, CRITERION_H3_4, CRITERION_H3_5)
        .build();

    private static final Criterion CRITERION_H4 = Criterion
        .builder()
        .shortDescription("H4 | Die Hauptschleife")
        .addChildCriteria(
            DEFAULT_CRITERION.apply(
                "Die Methode ruft die anderen in H3 implementierten Methoden auf.",
//                "Method calls other methods implemented in H3.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testUseOfMethods")
            ),
            DEFAULT_CRITERION.apply(
                "Die Methode funktioniert korrekt, auch dann, wenn alle Arraykomponenten \\<samp\\>null\\</samp\\> sind.",
//                "Method correctly works with an array containing only \\<samp\\>null\\</samp\\>.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testNullArray")
            ),
            DEFAULT_CRITERION.apply(
                "Die Methode wirft keine Exceptions.",
//                "Method does not throw any exceptions.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("checkForExceptions", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Die Roboter führen keine weitere Aktion aus, als sich zu bewegen und Münzen abzulegen.",
//                "Robots do not perform actions other than move or put a coin.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("checkActions", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Alle Roboter bewegen sich zum Ende.",
//                "All robots move to the end.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testAllRobotsReachEnd", String.class)
            ),
            DEFAULT_CRITERION.apply(
                "Alle Roboter legen die korrekte Anzahl von Münzen ab.",
//                "All robots put the correct amount of coins.",
                () -> LetRobotsMarchTest.class.getDeclaredMethod("testAllRobotsPutCoins", String.class)
            )
        )
        .build();

    private static final Rubric RUBRIC = Rubric.builder()
        .title("H02 | Let them march")
        .addChildCriteria(CRITERION_H1, CRITERION_H3, CRITERION_H4)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
