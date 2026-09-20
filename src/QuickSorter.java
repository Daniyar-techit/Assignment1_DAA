import java.util.Random;

public class QuickSorter {
    private final Random random = new Random();

    private int maxRecursionDepth = 0;
    private long comparisonCount = 0;
    private long swapCount = 0;

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        maxRecursionDepth = 0;
        comparisonCount = 0;
        swapCount = 0;

        quickSortIterativeTail(arr, 0, arr.length - 1, 0);
    }

    private void quickSortIterativeTail(int[] arr, int low, int high, int depth) {
        if (depth > maxRecursionDepth) {
            maxRecursionDepth = depth;
        }

        while (low < high) {
            int pivotIndex = low + random.nextInt(high - low + 1);
            int p = partition(arr, low, high, pivotIndex);


            if (p - low < high - p) {
                quickSortIterativeTail(arr, low, p - 1, depth + 1);
                low = p + 1;
            } else {
                quickSortIterativeTail(arr, p + 1, high, depth + 1);
                high = p - 1;
            }
        }
    }

    private int partition(int[] arr, int low, int high, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, high);

        int storeIndex = low;
        for (int i = low; i < high; i++) {
            comparisonCount++;
            if (arr[i] < pivotValue) {
                swap(arr, i, storeIndex);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, high);
        return storeIndex;
    }

    private void swap(int[] arr, int i, int j) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            swapCount++;
        }
    }

    public int getMaxRecursionDepth() { return maxRecursionDepth; }
    public long getComparisonCount() { return comparisonCount; }
    public long getSwapCount() { return swapCount; }
}