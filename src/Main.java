import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Assignment 1: Divide-and-Conquer Algorithm Analysis");

        System.out.println("\nVerifying algorithm correctness on demo data:");

        int[] testArr = {12, 11, 13, 5, 6, 7, 3, 1};
        System.out.println("Original array: " + Arrays.toString(testArr));

        // MergeSort
        MergeSorter mergeSorter = new MergeSorter();
        int[] mArr = testArr.clone();
        mergeSorter.sort(mArr);
        System.out.println("-> MergeSort:       " + Arrays.toString(mArr)
                + " (Recursion Depth: " + mergeSorter.getMaxRecursionDepth() + ")");

        // QuickSorter
        QuickSorter quickSorter = new QuickSorter();
        int[] qArr = testArr.clone();
        quickSorter.sort(qArr);
        System.out.println("-> QuickSort:       " + Arrays.toString(qArr)
                + " (Recursion Depth: " + quickSorter.getMaxRecursionDepth() + ")");

        // Deterministic Select
        DeterministicSelector selector = new DeterministicSelector();
        int k = 3;
        int kthElement = selector.select(testArr, k);
        System.out.println("-> Det. Select (k=" + k + "): element is " + kthElement);

        // Closest Pair of Points
        ClosestPairSolver cpSolver = new ClosestPairSolver();
        List<Point> points = new ArrayList<>();
        points.add(new Point(0.0, 0.0));
        points.add(new Point(1.0, 1.0));
        points.add(new Point(1.1, 1.1));
        points.add(new Point(5.0, 5.0));
        ClosestPairSolver.Pair closestPair = cpSolver.findClosestPair(points);
        System.out.println("-> Closest Pair:    minimum distance " + closestPair.distance +
                " between " + closestPair.p1 + " and " + closestPair.p2);

        // Saving results to CSV
        System.out.println("\nRunning experiments for different input sizes (Small, Medium, Large)...");
        Experiment.runAllExperiments();

        System.out.println("\nAll tasks completed successfully! results.csv is ready");
    }
}