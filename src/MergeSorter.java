import java.util.Arrays;

public class MergeSorter {
    private static final int INSERTION_SORT_THRESHOLD = 10;

    private int maxRecursionDepth = 0;
    private long comparisonCount = 0;
    private long arrayAccessCount = 0;

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        maxRecursionDepth = 0;
        comparisonCount = 0;
        arrayAccessCount = 0;

        int[] auxiliary = new int[arr.length];
        sort(arr, auxiliary, 0, arr.length - 1, 0);
    }

    private void sort(int[] arr, int[] aux, int low, int high, int depth) {
        if (depth > maxRecursionDepth) {
            maxRecursionDepth = depth;
        }

        if (high <= low + INSERTION_SORT_THRESHOLD) {
            insertionSort(arr, low, high);
            return;
        }

        int mid = low + (high - low) / 2;
        sort(arr, aux, low, mid, depth + 1);
        sort(arr, aux, mid + 1, high, depth + 1);

        comparisonCount++;
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }

        merge(arr, aux, low, mid, high);
    }

    private void merge(int[] arr, int[] aux, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {
            aux[k] = arr[k];
            arrayAccessCount += 2;
        }

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                arr[k] = aux[j++];
                arrayAccessCount += 2;
            } else if (j > high) {
                arr[k] = aux[i++];
                arrayAccessCount += 2;
            } else {
                comparisonCount++;
                if (aux[j] < aux[i]) {
                    arr[k] = aux[j++];
                } else {
                    arr[k] = aux[i++];
                }
                arrayAccessCount += 3;
            }
        }
    }

    private void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= low) {
                comparisonCount++;
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                    arrayAccessCount += 2;
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
            arrayAccessCount += 2;
        }
    }

    public int getMaxRecursionDepth() { return maxRecursionDepth; }
    public long getComparisonCount() { return comparisonCount; }
    public long getArrayAccessCount() { return arrayAccessCount; }
}