import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class AlgorithmTest {
    public static void main(String[] args) {
        System.out.println("Starting Automated Tests");

        testSortingAlgorithms();
        testDeterministicSelector();
        testClosestPair();

        System.out.println("\n All tests passed successfully!");
    }

    private static void testSortingAlgorithms() {
        System.out.println("\n[Testing Sorting Algorithms: MergeSort & QuickSort]");
        int[][] testCases = {
                {},
                {42},
                {5, 2, 9, 1, 5, 6, 3},
                {1, 2, 3, 4, 5},
                {5, 4, 3, 2, 1},
                {7, 7, 7, 7, 7}
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] original = testCases[i];

            int[] expected = original.clone();
            Arrays.sort(expected);

            //MergeSort
            int[] mArr = original.clone();
            new MergeSorter().sort(mArr);
            if (!Arrays.equals(mArr, expected)) {
                throw new AssertionError("MergeSort failed on test case index: " + i);
            }

            //QuickSort
            int[] qArr = original.clone();
            new QuickSorter().sort(qArr);
            if (!Arrays.equals(qArr, expected)) {
                throw new AssertionError("QuickSort failed on test case index: " + i);
            }
        }
        System.out.println("-> MergeSort and QuickSort passed all edge cases and reference checks!");
    }

    private static void testDeterministicSelector() {
        System.out.println("\n[Testing Deterministic Selector (100 random tests)]");
        Random rand = new Random(42);
        DeterministicSelector selector = new DeterministicSelector();

        for (int test = 0; test < 100; test++) {
            int size = 50 + rand.nextInt(200);
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = rand.nextInt(1000);
            }

            int k = rand.nextInt(size);

            int[] expectedArr = arr.clone();
            Arrays.sort(expectedArr);
            int expectedVal = expectedArr[k];

            int actualVal = selector.select(arr, k);
            if (actualVal != expectedVal) {
                throw new AssertionError(String.format("DeterministicSelect failed at test %d: expected %d, got %d for k=%d",
                        test, expectedVal, actualVal, k));
            }
        }
        System.out.println("-> Deterministic Select passed 100 random tests against Arrays.sort()!");
    }

    private static void testClosestPair() {
        System.out.println("\n[Testing Closest Pair of Points against Brute-Force]");
        Random rand = new Random(42);
        int n = 300;

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000));
        }

        ClosestPairSolver solver = new ClosestPairSolver();
        ClosestPairSolver.Pair dcPair = solver.findClosestPair(points);

        double bfMinDist = Double.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double dist = points.get(i).distanceTo(points.get(j));
                if (dist < bfMinDist) {
                    bfMinDist = dist;
                }
            }
        }
        if (Math.abs(dcPair.distance - bfMinDist) > 1e-5) {
            throw new AssertionError(String.format("ClosestPair mismatch: D&C=%.4f, BruteForce=%.4f",
                    dcPair.distance, bfMinDist));
        }
        System.out.println("-> Closest Pair result matches brute-force solution (Distance: " + dcPair.distance + ")");
    }
}