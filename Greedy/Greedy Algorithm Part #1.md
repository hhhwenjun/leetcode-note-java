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

## Two City Scheduling(Medium #1029)

**Question**: A company is planning to interview `2n` people. Given the array `costs` where `costs[i] = [aCosti, bCosti]`, the cost of flying the `ith` person to city `a` is `aCosti`, and the cost of flying the `ith` person to city `b` is `bCosti`.

Return *the minimum cost to fly every person to a city* such that exactly `n` people arrive in each city.

**Example 1:**

```
Input: costs = [[10,20],[30,200],[400,50],[30,20]]
Output: 110
Explanation: 
The first person goes to city A for a cost of 10.
The second person goes to city A for a cost of 30.
The third person goes to city B for a cost of 50.
The fourth person goes to city B for a cost of 20.

The total minimum cost is 10 + 30 + 50 + 20 = 110 to have half the people interviewing in each city.
```

**Example 2:**

```
Input: costs = [[259,770],[448,54],[926,667],[184,139],[840,118],[577,469]]
Output: 1859
```

**Example 3:**

```
Input: costs = [[515,563],[451,713],[537,709],[343,819],[855,779],[457,60],[650,359],[631,42]]
Output: 3086
```

**Constraints:**

-   `2 * n == costs.length`
-   `2 <= costs.length <= 100`
-   `costs.length` is even.
-   `1 <= aCosti, bCosti <= 1000`

### Standard Solution

#### Solution #1 Greedy

*   Greedy problems usually look like "Find a minimum number of *something* to do *something*" or "Find a maximum number of *something* to fit in *some conditions*", and typically propose an unsorted input.
*   The idea of the greedy algorithm is to pick the *locally* optimal move at each step, that will lead to the *globally* optimal solution.

*   The standard solution has $\mathcal{O}(N \log N)$ time complexity and consists of two parts:
    -   Figure out how to sort the input data ($\mathcal{O}(N \log N)$ time). That could be done directly by a sorting or indirectly by a heap usage. Typically sort is better than the heap usage because of gain in space.
    -   Parse the sorted input to have a solution ($\mathcal{O}(N)$ time).
*   Sort the persons in the ascending order by `price_A - price_B` parameter, which indicates the company's additional costs(Based on the two group differences).
*   The first n persons choose group A, the last n persons choose group B. 

```java
public int twoCitySchedCost(int[][] costs) {
    // Sort by a gain which company has 
    // by sending a person to city A and not to city B
    Arrays.sort(costs, new Comparator<int[]>() {
      @Override
      public int compare(int[] o1, int[] o2) {
        return o1[0] - o1[1] - (o2[0] - o2[1]);
      }
    });

    int total = 0;
    int n = costs.length / 2;
    // To optimize the company expenses,
    // send the first n persons to the city A
    // and the others to the city B
    for (int i = 0; i < n; ++i) total += costs[i][0] + costs[i + n][1];
    return total;
}
```

-   Time complexity: $\mathcal{O}(N \log N)$ because of sorting of input data.
-   Space complexity: $\mathcal{O}(1)$ since it's a constant space solution.
