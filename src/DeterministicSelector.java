public class DeterministicSelector {
    private int maxRecursionDepth = 0;
    private long comparisonCount = 0;

    public int select(int[] arr, int k) {
        if (arr == null || k < 0 || k >= arr.length) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        maxRecursionDepth = 0;
        comparisonCount = 0;

        int[] workArr = arr.clone();
        int index = selectIndex(workArr, 0, workArr.length - 1, k, 0);
        return workArr[index];
    }

    private int selectIndex(int[] arr, int low, int high, int k, int depth) {
        if (depth > maxRecursionDepth) maxRecursionDepth = depth;
        if (low == high) return low;

        int pivotIndex = medianOfMediansIndex(arr, low, high, depth + 1);
        pivotIndex = partition(arr, low, high, pivotIndex);

        if (k == pivotIndex) {
            return k;
        } else if (k < pivotIndex) {
            return selectIndex(arr, low, pivotIndex - 1, k, depth + 1);
        } else {
            return selectIndex(arr, pivotIndex + 1, high, k, depth + 1);
        }
    }

    private int medianOfMediansIndex(int[] arr, int low, int high, int depth) {
        int numElements = high - low + 1;
        if (numElements <= 5) {
            insertionSort(arr, low, high);
            return low + numElements / 2;
        }

        int numOfGroups = (numElements + 4) / 5;
        for (int i = 0; i < numOfGroups; i++) {
            int subLow = low + i * 5;
            int subHigh = Math.min(subLow + 4, high);
            insertionSort(arr, subLow, subHigh);
            int medianIndex = subLow + (subHigh - subLow) / 2;
            swap(arr, low + i, medianIndex);
        }

        return selectIndex(arr, low, low + numOfGroups - 1, low + numOfGroups / 2, depth + 1);
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

    private void insertionSort(int[] arr, int low, int high) {
        int i = low + 1;
        while (i <= high) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= low) {
                comparisonCount++;
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
            i++;
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int getMaxRecursionDepth() { return maxRecursionDepth; }
    public long getComparisonCount() { return comparisonCount; }
}