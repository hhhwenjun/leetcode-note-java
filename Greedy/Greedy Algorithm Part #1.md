# Greedy Algorithm Part #1

## Remove Covered Intervals(Medium #1288)

**Question**: Given an array `intervals` where `intervals[i] = [li, ri]` represent the interval `[li, ri)`, remove all intervals that are covered by another interval in the list.

The interval `[a, b)` is covered by the interval `[c, d)` if and only if `c <= a` and `b <= d`.

Return *the number of remaining intervals*.

**Example 1:**

```
Input: intervals = [[1,4],[3,6],[2,8]]
Output: 2
Explanation: Interval [3,6] is covered by [2,8], therefore it is removed.
```

**Example 2:**

```
Input: intervals = [[1,4],[2,3]]
Output: 1
```

**Constraints:**

-   `1 <= intervals.length <= 1000`
-   `intervals[i].length == 2`
-   `0 <= li <= ri <= 105`
-   All the given intervals are **unique**.

### Standard Solution

#### Solution #1 Greedy Algorithm

*   The typical greedy solution has $\mathcal{O}(N \log N)$ time complexity and consists of two steps:

    -   Figure out how to sort the input data. That would take $\mathcal{O}(N \log N)$ time, and could be done directly by sorting or indirectly by using the heap data structure. Usually sorting is better than heap usage because of gain in space.
    -   Parse the sorted input in $\mathcal{O}(N)$ time to construct a solution.

    <img src="https://leetcode.com/problems/remove-covered-intervals/Figures/1288/sort.png" alt="traversal" style="zoom:50%;" />

*   Let us consider two subsequent intervals after sorting. Since sorting ensures that `start1 < start2`, it's sufficient to compare the end boundaries:
    -   If `end1 < end2`, the intervals won't completely cover one another, though they have some overlapping.
    -   If `end1 >= end2`, interval 2 is covered by interval 1.
*   Edge case: **How to treat intervals that share start point**
    *   If two intervals share the same start point, one has to put the longer interval in front.

<img src="https://leetcode.com/problems/remove-covered-intervals/Figures/1288/share.png" alt="traversal" style="zoom:50%;" />

```java
public int removeCoveredIntervals(int[][] intervals) {
    Arrays.sort(intervals, new Comparator<int[]>(){
        @Override
        public int compare(int[] o1, int[] o2){
            // sort by start point
            // if two intervals share same start point,
            // put the longer one to be first
            return o1[0] == o2[0] ? o2[1] - o1[1] : o1[0] - o2[0];
        }
    });
    int count = 0;
    int end, prev_end = 0;
    for (int[] curr : intervals){
        end = curr[1];
        // if current interval is not covered
        // by the previous one
        if (prev_end < end){
            ++count;
            prev_end = end;
        }
    }
    return count;
}
```

*   Time complexity: $\mathcal{O}(N \log N)$ since the sorting dominates the complexity of the algorithm.
*   Space complexity: $\mathcal{O}(N)$ or $\mathcal{O}(\log{N})$
    -   The space complexity of the sorting algorithm depends on the implementation of each programming language.
    -   For instance, the `sorted()` function in Python is implemented with the [Timsort](https://en.wikipedia.org/wiki/Timsort) algorithm whose space complexity is $\mathcal{O}(N)$
    -   In Java, the [Arrays. sort()](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#sort-byte:A-) is implemented as a variant of quicksort algorithm whose space complexity is $\mathcal{O}(\log{N})$.

