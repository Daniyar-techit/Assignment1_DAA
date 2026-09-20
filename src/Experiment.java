import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Experiment {
    private static final String CSV_FILE_PATH = "results/results.csv";

    public static void runAllExperiments() {
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            resultsDir.mkdir();
        }

        int[] sizes = {100, 500, 1000, 5000, 10000, 50000};
        String[] inputTypes = {"Random", "Sorted", "ReverseSorted", "Duplicates"};

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            writer.append("Algorithm,InputType,Size,TimeNs,RecursionDepth,Comparisons\n");

            for (int size : sizes) {
                for (String type : inputTypes) {
                    int[] baseArray = generateArray(size, type);

                    // MergeSort
                    int[] arr1 = baseArray.clone();
                    MergeSorter mergeSorter = new MergeSorter();
                    long startMerge = System.nanoTime();
                    mergeSorter.sort(arr1);
                    long timeMerge = System.nanoTime() - startMerge;
                    writer.append(String.format("MergeSort,%s,%d,%d,%d,%d\n",
                            type, size, timeMerge, mergeSorter.getMaxRecursionDepth(), mergeSorter.getComparisonCount()));

                    //QuickSort
                    int[] arr2 = baseArray.clone();
                    QuickSorter quickSorter = new QuickSorter();
                    long startQuick = System.nanoTime();
                    quickSorter.sort(arr2);
                    long timeQuick = System.nanoTime() - startQuick;
                    writer.append(String.format("QuickSort,%s,%d,%d,%d,%d\n",
                            type, size, timeQuick, quickSorter.getMaxRecursionDepth(), quickSorter.getComparisonCount()));

                    //Deterministic Select
                    int[] arr3 = baseArray.clone();
                    DeterministicSelector selector = new DeterministicSelector();
                    long startSelect = System.nanoTime();
                    selector.select(arr3, size / 2);
                    long timeSelect = System.nanoTime() - startSelect;
                    writer.append(String.format("DeterministicSelect,%s,%d,%d,%d,%d\n",
                            type, size, timeSelect, selector.getMaxRecursionDepth(), selector.getComparisonCount()));
                }

                //Closest Pair of Points
                if (size <= 10000) { // Limit size for faster execution of ClosestPair
                    List<Point> points = generatePoints(size);
                    ClosestPairSolver cpSolver = new ClosestPairSolver();
                    long startCP = System.nanoTime();
                    cpSolver.findClosestPair(points);
                    long timeCP = System.nanoTime() - startCP;
                    writer.append(String.format("ClosestPair,Random,%d,%d,%d,%d\n",
                            size, timeCP, cpSolver.getMaxRecursionDepth(), cpSolver.getComparisonCount()));
                }
            }

            System.out.println("Experiments completed successfully! Results saved to " + CSV_FILE_PATH);

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    private static int[] generateArray(int size, String type) {
        Random rand = new Random(42); // Fixed seed for reproducibility
        int[] arr = new int[size];

        switch (type) {
            case "Random":
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(100000);
                break;
            case "Sorted":
                for (int i = 0; i < size; i++) arr[i] = i;
                break;
            case "ReverseSorted":
                for (int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "Duplicates":
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt(10); // heavy duplicates
                break;
        }
        return arr;
    }

    private static List<Point> generatePoints(int size) {
        Random rand = new Random(42);
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            points.add(new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000));
        }
        return points;
    }

    public static void main(String[] args) {
        runAllExperiments();
    }
}