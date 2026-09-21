# Assignment 1: Divide-and-Algorithm Analysis

## A. Project Overview
* **Purpose:** The objective of this assignment is to implement, analyze, and empirically evaluate classic divide-and-conquer algorithms, comparing their theoretical complexities with practical runtime performance.
* **Implemented Algorithms:**
    1. **MergeSort:** Optimized with a linear merge, reusable auxiliary buffer, and an insertion sort cutoff for small inputs ($\Theta(n \log n)$).
    2. **QuickSort:** Randomized pivot selection, in-place partitioning, and tail-recursion optimization (smaller-first recursion) ($O(n \log n)$ expected, $O(n^2)$ worst-case).
    3. **Deterministic Select (Median-of-Medians):** Group-of-5 strategy with guaranteed linear worst-case time complexity ($O(n)$).
    4. **Closest Pair of Points:** Divide-and-conquer geometric approach using sorted coordinates and strip-checking ($\Theta(n \log n)$).

---

## B. Algorithm Analysis
* **MergeSort:**
    * *How it works:* Recursively splits the array into two halves, sorts them, and merges them back using an auxiliary buffer.
    * *Complexity:* Time $\Theta(n \log n)$, Space $O(n)$.
    * *Recurrence:* $T(n) = 2T(n/2) + \Theta(n)$. Solved via Master Theorem (Case 2), yielding $\Theta(n \log n)$.
* **QuickSort:**
    * *How it works:* Picks a random pivot, partitions the array in-place such that elements smaller than the pivot are to its left and larger ones to its right, then recursively sorts partitions.
    * *Complexity:* Expected Time $O(n \log n)$, Worst-case Time $O(n^2)$, Space $O(\log n)$ due to recursion stack.
    * *Recurrence:* $T(n) = T(k) + T(n-k-1) + \Theta(n)$.
* **Deterministic Select:**
    * *How it works:* Divides elements into groups of 5, finds the median of each group, recursively finds the median of those medians to use as a high-quality pivot.
    * *Complexity:* Worst-case Time $O(n)$, Space $O(n)$ or $O(\log n)$ stack depth.
    * *Recurrence:* $T(n) \le T(n/5) + T(7n/10) + O(n)$, resolving to $O(n)$ via Akra-Bazzi intuition.
* **Closest Pair of Points:**
    * *How it works:* Sorts points by x-coordinate, divides the set into left and right halves, finds recursive minimum distances $d$, and checks a vertical strip of width $2d$ sorted by y-coordinate.
    * *Complexity:* Time $\Theta(n \log n)$, Space $O(n)$.
    * *Recurrence:* $T(n) = 2T(n/2) + O(n) \implies \Theta(n \log n)$.

---

## C. Experimental Results
* **Execution-Time Tables:** Data collected via `System.nanoTime()` across small ($n=100$), medium ($n=1,000$), and large ($n=50,000$) datasets for Random, Sorted, Reverse-Sorted, and Duplicate-heavy structures. (See `results/results.csv`).
* **Recursion-Depth Results:** Measured maximum stack depth to verify $O(\log n)$ bounds for balanced divide-and-conquer strategies.
* **Plots:**
    * Time vs. $n$ graphs located in `plots/time_vs_n.png`.
    * Recursion Depth vs. $n$ graphs located in `plots/depth_vs_n.png`.

---

## D. Discussion
1. **Do the results match theoretical complexity?** Yes, empirical growth rates closely mirror theoretical bounds (e.g., $\Theta(n \log n)$ curves for sorting and $O(n)$ scaling for Deterministic Select).
2. **How does input structure affect performance?** Sorted and reverse-sorted inputs heavily impact naive partitioning, but randomized pivots and insertion-sort cutoffs successfully mitigate worst-case performance degradation.
3. **Why does smaller-first recursion help QuickSort?** Recursing on the smaller partition first and iterating over the larger one guarantees that the maximum stack depth never exceeds $O(\log n)$.
4. **Why does Median-of-Medians guarantee $O(n)$?** By reliably avoiding bad pivots, it ensures that at least a constant fraction of elements is eliminated in each recursive step.
5. **Why is D&C Closest Pair faster than $O(n^2)$?** Instead of checking all pairs, the geometric strip property limits comparisons in the merge step to a constant number of neighboring points per candidate.
6. **Practical factors affecting performance:** JVM warm-up (JIT compilation), garbage collection overhead, and CPU cache locality heavily influence micro-benchmarks in Java.

---

## E. Reflection
Implementing these algorithms highlighted the critical gap between asymptotic theory and practical execution. Managing array indexing cleanly in Median-of-Medians and handling edge cases in the Closest Pair strip construction presented the main architectural challenges, successfully resolved through rigorous unit testing.

---

## F. Screenshots
* *Program Output:* Refer to `screenshots/output.png`.
* *Test Results:* Refer to `screenshots/tests.png`.
* *Performance Plots:* Refer to `screenshots/plots.png`.