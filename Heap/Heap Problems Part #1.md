# Heap Problems Part #1

## Kth Largest Element in an Array(Medium #215)

**Question**: Given an integer array `nums` and an integer `k`, return *the* `kth` *largest element in the array*.

Note that it is the `kth` largest element in the sorted order, not the `kth` distinct element.

**Example 1:**

```
Input: nums = [3,2,1,5,6,4], k = 2
Output: 5
```

**Example 2:**

```
Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
Output: 4
```

**Constraints:**

-   `1 <= k <= nums.length <= 104`
-   `-104 <= nums[i] <= 104`

### My Solution

```java
public int findKthLargest(int[] nums, int k) {
    // lambda expression
    PriorityQueue<Integer> heap = new PriorityQueue<>((x, y) -> Integer.compare(y, x));
    for (int num : nums){
        heap.add(num);
    }
    for (int i = 0; i < k - 1; i++){
        heap.poll();
    }
    return heap.peek();
}
```

### Standard Solution

#### Solution #1 Heap

*   Also use lambda expression for comparator
*   A similar idea to my solution

```java
public int findKthLargest(int[] nums, int k){
    // init heal the smallest element first
    PriorityQueue<Integer> heap = 
        new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
    // keep k largest elements in the heap
    for (int n : nums){
        heap.add(n);
        if (heap.size() > k){
            heap.poll();
        }
    }
    return heap.poll();
}
```

*   Time complexity: $\mathcal{O}(N \log k)$.
*   Space complexity: $\mathcal{O}(k)$ to store the heap elements.

## Top K Frequent Elements(Medium #347)

**Question**: Given an integer array `nums` and an integer `k`, return *the* `k` *most frequent elements*. You may return the answer in **any order**.

**Example 1:**

```
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
```

**Example 2:**

```
Input: nums = [1], k = 1
Output: [1] 
```

**Constraints:**

-   `1 <= nums.length <= 105`
-   `k` is in the range `[1, the number of unique elements in the array]`.
-   It is **guaranteed** that the answer is **unique**.

**Follow up:** Your algorithm's time complexity must be better than `O(n log n)`, where n is the array's size.

### My Solution

```java
// first attempt: Hash Map
class Solution {
    public List<Map.Entry<Integer, Integer>> sortByValue(HashMap<Integer, Integer> hm){
        List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer,Integer>>(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>(){
            public int compare(Map.Entry<Integer,Integer> o1,
                              Map.Entry<Integer,Integer> o2){
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        return list;
    }
    
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> countMap = new HashMap<>();
        for(int num : nums){
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        List<Map.Entry<Integer, Integer>> sortedMap = sortByValue(countMap);
        Collections.reverse(sortedMap);
        int[] intArry = new int[k];
        int i = 0;
        for (Map.Entry<Integer, Integer> countPair : sortedMap){
            Integer countVal = countPair.getKey();
            intArry[i] = Integer.valueOf(countVal);
            i++;
            if (i == k) break;
        }
        return intArry;
    }
}
```

```java
// second attempt: heap
 public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    for(int num: nums){
        count.put(num, count.getOrDefault(num, 0) + 1);
    }
    PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> count.get(n1) - count.get(n2));
    for(Map.Entry<Integer, Integer> set : count.entrySet()){
        heap.add(set.getKey());
        if (heap.size() > k){
            heap.poll();
        }
    }
    int[] res = new int[k];
    for(int i = 0; i < k; i++){
        res[i] = heap.poll();
    }
    return res;
}
```

*   The heap method is more time and space costly, but easier to implement and read.

```java
// third attempt
public int[] topKFrequent(int[] nums, int k) {
    // create hashmap to count the frequency and then use max-heap to return the entryset
    Map<Integer, Integer> map = new HashMap<>();
    int[] res = new int[k];
    for (int num : nums){
        map.put(num, map.getOrDefault(num, 0) + 1);
    }
    PriorityQueue<Map.Entry<Integer, Integer>> maxheap = 
        new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
    for (Map.Entry<Integer, Integer> countSet : map.entrySet()){
        maxheap.add(countSet);
    }
    for (int i = 0; i < k; i++){
        res[i] = maxheap.poll().getKey();
    }
    return res;
}
```

### Standard Solution

#### Solution #1 Heap

```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // O(1) time
        if (k == nums.length) {
            return nums;
        }
        
        // 1. build hash map : character and how often it appears
        // O(N) time
        Map<Integer, Integer> count = new HashMap();
        for (int n: nums) {
          count.put(n, count.getOrDefault(n, 0) + 1);
        }

        // init heap 'the less frequent element first'
        Queue<Integer> heap = new PriorityQueue<>(
            (n1, n2) -> count.get(n1) - count.get(n2));

        // 2. keep k top frequent elements in the heap
        // O(N log k) < O(N log N) time
        for (int n: count.keySet()) {
          heap.add(n);
          if (heap.size() > k) heap.poll();    
        }

        // 3. build an output array
        // O(k log k) time
        int[] top = new int[k];
        for(int i = k - 1; i >= 0; --i) {
            top[i] = heap.poll();
        }
        return top;
    }
}
```

*   Similar idea to my solution
*   Time complexity: $\mathcal{O}(N \log k)$ if k < N and $\mathcal{O}(N)$ in the particular case of N = k. That ensures time complexity is better than $\mathcal{O}(N \log N)$.
*   Space complexity: $\mathcal{O}(N + k)$ to store the hash map with not more N elements and a heap with k elements.

## Last Stone Weight(Easy #1046)

**Question**: You are given an array of integers `stones` where `stones[i]` is the weight of the `ith` stone.

We are playing a game with the stones. On each turn, we choose the **heaviest two stones** and smash them together. Suppose the heaviest two stones have weights `x` and `y` with `x <= y`. The result of this smash is:

-   If `x == y`, both stones are destroyed, and
-   If `x != y`, the stone of weight `x` is destroyed, and the stone of weight `y` has new weight `y - x`.

At the end of the game, there is **at most one** stone left.

Return *the smallest possible weight of the left stone*. If there are no stones left, return `0`.

**Example 1:**

```
Input: stones = [2,7,4,1,8,1]
Output: 1
Explanation: 
We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of the last stone.
```

**Example 2:**

```
Input: stones = [1]
Output: 1
```

**Constraints:**

-   `1 <= stones.length <= 30`
-   `1 <= stones[i] <= 1000`

### My Solution

*   Build up max heap
*   **Important Tips** while using the lambda expression
    *   Max heap: `PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> n2 - n1);`
    *   Min heap: `PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> n1 - n2);`

```java
public int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> n2 - n1);
    for (int stone : stones){
        heap.add(stone);
    }
    while(heap.size() > 1){
        int stone1 = heap.poll();
        int stone2 = heap.poll();
        int rest = Math.abs(stone1 - stone2);
        heap.add(rest);
    }
    return heap.peek();
}
```

### Standard Solution

#### Solution #1 Heap Simulation

*   Similar idea to my solution

```java
public int lastStoneWeight(int[] stones) {
    // Insert all the stones into a Max-Heap.
    // use comparator, reverse order to indicate a max heap
    PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
    for (int stone: stones) {
        heap.add(stone);
    }
    // While there is more than one stone left, we need to remove the two largest
    // and smash them together. If there is a resulting stone, we need to put into
    // the heap.
    while (heap.size() > 1) {
        int stone1 = heap.remove();
        int stone2 = heap.remove();
        if (stone1 != stone2) {
            heap.add(stone1 - stone2);
        }
    }
    // Check whether or not there is a stone left to return.
    return heap.isEmpty() ? 0 : heap.remove();
}
```

-   Time complexity: $O(N \, \log \, N)$

    Converting an array into a Heap takes $O(N)$ time (it isn't actually sorting; it's putting them into an order that allows us to get the maximums, each in $O(\log \, N)$.

    Like before, the main loop iterates up to $N - 1$ times. This time, however, it's doing up to three $O(\log \, N)$ operation each time; two removes, and an optional add. Like always, the three are ignored constantly. This means that we're doing $N \cdot O(\log \, N) = O(N \, \log \, N)$ operations.

-   Space complexity: $O(N)$ or $O(\log \, N)$.

    In Python, converting a list to a heap is done in place, requiring $O(1)$ Auxiliary space, giving a total space complexity of $O(1)$. Modifying the input has its pros and cons; it saves space, but it means that other functions can't use the same array.

    In Java though, it's $O(N)$ to create the `PriorityQueue`.

    We could reduce the space complexity to $O(1)$ by implementing our own iterative heapfiy, if needed

#### Solution #2 Bucket Sort

*   In this method, we create an array with maximum value to store the counts
*   Then from the end to the start, to reduce the stones

```java
class Solution {
    
    public int lastStoneWeight(int[] stones) {
        
        // Set up the bucket array.
        int maxWeight = stones[0];
        for (int stone: stones) {
            maxWeight = Math.max(maxWeight, stone);
        }
        int[] buckets = new int[maxWeight + 1];

        // Bucket sort.
        for (int weight : stones) {
            buckets[weight]++;
        }

        // Scan through the buckets.
        int biggestWeight = 0;
        int currentWeight = maxWeight;
        while (currentWeight > 0) {
            if (buckets[currentWeight] == 0) {
                currentWeight--;
            } else if (biggestWeight == 0) {
                buckets[currentWeight] %= 2;
                if (buckets[currentWeight] == 1) {
                    biggestWeight = currentWeight;
                }
                currentWeight--;
            } else {
                buckets[currentWeight]--;
                if (biggestWeight - currentWeight <= currentWeight) {
                    buckets[biggestWeight - currentWeight]++;
                    biggestWeight = 0;
                } else {
                    biggestWeight -= currentWeight;
                }
            }
        }
        return biggestWeight;
    }
}
```

*   Time complexity: $O(N + W)$
*   Space complexity: $O(W)$

## Kth Smallest Element in a Sorted Matrix(Medium #378)

**Question**: Given an `n x n` `matrix` where each of the rows and columns is sorted in ascending order, return *the* `kth` *smallest element in the matrix*.

Note that it is the `kth` smallest element **in the sorted order**, not the `kth` **distinct** element.

You must find a solution with a memory complexity better than `O(n2)`.

**Example 1:**

```
Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
Output: 13
Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13
```

**Example 2:**

```
Input: matrix = [[-5]], k = 1
Output: -5
```

**Constraints:**

-   `n == matrix.length == matrix[i].length`
-   `1 <= n <= 300`
-   `-109 <= matrix[i][j] <= 109`
-   All the rows and columns of `matrix` are **guaranteed** to be sorted in **non-decreasing order**.
-   `1 <= k <= n2`

**Follow up:**

-   Could you solve the problem with a constant memory (i.e., `O(1)` memory complexity)?
-   Could you solve the problem in `O(n)` time complexity? The solution may be too advanced for an interview but you may find reading [this paper](http://www.cse.yorku.ca/~andy/pubs/X+Y.pdf) fun.

### My Solution

*   Create a min-heap and put all elements to min heap in loop
*   Then pop the element out of heap one by one until meet the number requirements.

```java
public int kthSmallest(int[][] matrix, int k) {
    // create a min heap and put all the element to the min heap
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    for (int[] row : matrix){
        for (int num : row){
            heap.add(num);
        }
    }
    // pop the number out of the heap
    for (int i = 0; i < k - 1; i++){
        heap.poll();
    }
    return heap.peek();
}
```

### Standard Solution

#### Solution #1 Min Heap

*   More complicated than my solution but avoid nested for loop
*   Create a min-heap tree, with nodes containing data, row and column number
*   Each node contains a triplet of information
*   First, input the first element of each row to heap, then input the rest of the row by column
*   Control the size of the heap

```java
class MyHeapNode {

  int row;
  int column;
  int value;
  public MyHeapNode(int v, int r, int c) {
    this.value = v;
    this.row = r;
    this.column = c;
  }
  public int getValue() {
    return this.value;
  }
  public int getRow() {
    return this.row;
  }
  public int getColumn() {
    return this.column;
  }
}
class MyHeapComparator implements Comparator<MyHeapNode> {
  public int compare(MyHeapNode x, MyHeapNode y) {
    return x.value - y.value;
  }
}
class Solution {
  public int kthSmallest(int[][] matrix, int k) {
    int N = matrix.length;
    PriorityQueue<MyHeapNode> minHeap =
        new PriorityQueue<MyHeapNode>(Math.min(N, k), new MyHeapComparator());
    // Preparing our min-heap
    for (int r = 0; r < Math.min(N, k); r++) {
      // We add triplets of information for each cell
      minHeap.offer(new MyHeapNode(matrix[r][0], r, 0));
    }
    MyHeapNode element = minHeap.peek();
    while (k-- > 0) {
      // Extract-Min
      element = minHeap.poll();
      int r = element.getRow(), c = element.getColumn();
      // If we have any new elements in the current row, add them
      if (c < N - 1) {
        minHeap.offer(new MyHeapNode(matrix[r][c + 1], r, c + 1));
      }
    }
    return element.getValue();
  }
}
```

-   Time Complexity: $\text{let X=} \text{min}(K, N); X + K \log(X)$
    -   Well, the heap construction takes $O(X)$ time.
    -   After that, we perform K iterations and each iteration has two operations. We extract the minimum element from a heap containing X elements. Then we add a new element to this heap. Both the operations will take $O(\log(X))$ time.
    -   Thus, the total time complexity for this algorithm comes down to be $O(X + K\log(X))$ where X is $\text{min}(K, N)$.
-   Space Complexity: $O(X)$ which is occupied by the heap.

## Meeting Rooms II(Medium #253)

**Question**: Given an array of meeting time intervals `intervals` where `intervals[i] = [starti, endi]`, return *the minimum number of conference rooms required*.

**Example 1:**

```
Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2
```

**Example 2:**

```
Input: intervals = [[7,10],[2,4]]
Output: 1
```

**Constraints:**

-   `1 <= intervals.length <= 104`
-   `0 <= starti < endi <= 106`

### Standard Solution

#### Solution #1 Priority Queue

*   First sort the input array to arrange it with the meeting start time
*   Based on the start time, create rooms for meetings, and store each room's end time in a min-heap
*   In the min-heap, each time we pop the room that ends first. If the current meeting information starts before the room's end time, we need to create a new room for the meeting.
*   **Algorithm**: 
    *   Sort the given meetings by their `start time`.
    *   Initialize a new `min-heap` and add the first meeting's ending time to the heap. We simply need to keep track of the ending times as that tells us when a meeting room will get free.
    *   For every meeting room check if the minimum element of the heap i.e. the room at the top of the heap is free or not.
    *   If the room is free, then we extract the topmost element and add it back with the ending time of the current meeting we are processing.
    *   If not, then we allocate a new room and add it to the heap.
    *   After processing all the meetings, the size of the heap will tell us the number of rooms allocated. This will be the minimum number of rooms needed to accommodate all the meetings.

```java
public int minMeetingRooms(int[][] intervals){
    // check for the base case. If there are no intervals, return 0
    if (intervals.length == 0){
        return 0;
    }
    
    // min heap
    PriorityQueue<Integer> allocator = 
        new PriorityQueue<Integer>(intervals.length,new Comparator<Integer>(){
            public int compare(Integer a, Integer b){
                return a - b;
            }
        });
    // sort the intervals by start time
    Arrays.sort(intervals, new Comparator<int[]>(){
        public int compare(final int[] a, final int[] b){
            return a[0] - b[0];
        }
    });
    // add the first meeting
    allocator.add(intervals[0][1]);
    
    // iterate over remaining intervals
    for (int i = 1; i < intervals.length; i++){
        // if the room due to free up the earliest is free, assign that room to this meeting
        if (intervals[i][0] >= allocator.peek()){
            allocator.poll();
        }
        // if a new room is to be assigned, then also add to the heap
        // if an old room is allocated, then also we have to add to the heap with updated end time
        allocator.add(intervals[i][1]);
    }
    return allocator.size();
}
```

*   Time Complexity: $O(N\log N)$
    -   There are two major portions that take up time here. One is `sorting` the array that takes $O(N\log N)$ considering that the array consists of N elements.
    -   Then we have the `min-heap`. In the worst case, all N meetings will collide with each other. In any case, we, have N*N* add operations on the heap. In the worst case, we will have N*N* extract-min operations as well. Overall complexity being $(NlogN)$ since the extract-min operation on a heap takes $O(\log N)$.
*   Space Complexity: $O(N)$ because we construct the `min-heap` and that can contain N elements in the worst case as described above in the time complexity section. Hence, the space complexity is $O(N)$.

## K Closest Points to Origin(Medium #973)

**Question**: Given an array of `points` where `points[i] = [xi, yi]` represents a point on the **X-Y** plane and an integer `k`, return the `k` closest points to the origin `(0, 0)`.

The distance between two points on the **X-Y** plane is the Euclidean distance (i.e., `√(x1 - x2)2 + (y1 - y2)2`).

You may return the answer in **any order**. The answer is **guaranteed** to be **unique** (except for the order that it is in).

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/03/03/closestplane1.jpg" alt="img" style="zoom:33%;" />

```
Input: points = [[1,3],[-2,2]], k = 1
Output: [[-2,2]]
Explanation:
The distance between (1, 3) and the origin is sqrt(10).
The distance between (-2, 2) and the origin is sqrt(8).
Since sqrt(8) < sqrt(10), (-2, 2) is closer to the origin.
We only want the closest k = 1 points from the origin, so the answer is just [[-2,2]].
```

**Example 2:**

```
Input: points = [[3,3],[5,-1],[-2,4]], k = 2
Output: [[3,3],[-2,4]]
Explanation: The answer [[-2,4],[3,3]] would also be accepted.
```

**Constraints:**

-   `1 <= k <= points.length <= 104`
-   `-104 < xi, yi < 104`

### My Solution

```java
public int[][] kClosest(int[][] points, int k) {
    // use min heap to store the value of points, and hashmap 
    PriorityQueue<int[]> heap = new PriorityQueue<>(new Comparator<int[]>(){
        public int compare(int[] a, int[] b){
            return a[0]*a[0] + a[1]*a[1] - (b[0]*b[0] + b[1]*b[1]);
        }
    });
    for(int[] point : points){
        heap.add(point);
    }
    int[][] res = new int[k][2];
    int[] singlePoint;
    for(int i = 0; i < k; i++){
        singlePoint = heap.poll();
        res[i] = singlePoint;
    }
    return res;
}
```

### Standard Solution

#### Solution #1 Sort with Custom Comparator

*   Sort with a custom comparator, similar to my solution but without a heap

```java
public int[][] kClosest(int[][] points, int k){
    // sort the array with a custom lambda comparator function
    Arrays.sort(points, (a, b) -> squaredDistance(a) - squaredDistance(b));
    
    // return the first k elements of the sorted array
    return Arrays.copyOf(points, k);
}
private int squaredDistance(int[] point){
    // calculate and return the squared euclidean distance
    return point[0]*point[0] + point[1]*point[1];
}
```

*   Time complexity: $O(N \cdot \log N)$ for the sorting of `points`.

*   Space complexity: $O(\log N)$ to $O(N)$ for the extra space required by the sorting process.

#### Solution #2 Max Heap

*   Create a new structure to store the distance and point index(similar to a hashmap but not a map).
*   Create a max heap to store the distance data
*   Each time poll the topmost point with the farthest distance, and input the new pointer with a shorter distance
*   Finally, poll all the data from the heap and use the point index to find the point.

```java
public int[][] kClosest(int[][] points, int k){
    // use a lambda comparator to sort the PQ by the farthest distance
    Queue<int[]> maxPQ = new PriorityQueue<>((a, b) -> b[0] - a[0]);
    for (int i = 0; i < points.length; i++){
        int[] entry = {squaredDistance(points[i]), i};
        if (maxPQ.size() < k){
            // fill the max PQ up to k points
            maxPQ.add(entry);
        }
        else if (entry[0] < maxPQ.peek()[0]){
            // if the max PQ is full and a closer point is found, discard the farthest point
            maxPQ.poll();
            maxPQ.add(entry);
        }
    }
    // return all points stored in max PQ
    int[][] ansert = new int[k][2];
    for (int i = 0; i < k; i++){
        int entryIndex = maxPQ.poll()[1];
        answer[i] = points[entryIndex];
    }
    return answer;
}

 private int squaredDistance(int[] point) {
    // Calculate and return the squared Euclidean distance
    return point[0] * point[0] + point[1] * point[1];
}
```

-   Time complexity: $O(N \cdot \log k)$

    Adding to/removing from the heap (or priority queue) only takes $O(\log k)$ the time when the size of the heap is capped at k elements.

-   Space complexity: $O(k)$

    The heap (or priority queue) will contain at most k elements.

## Minimum Cost to Connet Sticks(Medium #1167)

**Question**: You have some number of sticks with positive integer lengths. These lengths are given as an array `sticks`, where `sticks[i]` is the length of the `ith` stick.

You can connect any two sticks of lengths `x` and `y` into one stick by paying a cost of `x + y`. You must connect all the sticks until there is only one stick remaining.

Return *the minimum cost of connecting all the given sticks into one stick in this way*.

**Example 1:**

```
Input: sticks = [2,4,3]
Output: 14
Explanation: You start with sticks = [2,4,3].
1. Combine sticks 2 and 3 for a cost of 2 + 3 = 5. Now you have sticks = [5,4].
2. Combine sticks 5 and 4 for a cost of 5 + 4 = 9. Now you have sticks = [9].
There is only one stick left, so you are done. The total cost is 5 + 9 = 14.
```

**Example 2:**

```
Input: sticks = [1,8,3,5]
Output: 30
Explanation: You start with sticks = [1,8,3,5].
1. Combine sticks 1 and 3 for a cost of 1 + 3 = 4. Now you have sticks = [4,8,5].
2. Combine sticks 4 and 5 for a cost of 4 + 5 = 9. Now you have sticks = [9,8].
3. Combine sticks 9 and 8 for a cost of 9 + 8 = 17. Now you have sticks = [17].
There is only one stick left, so you are done. The total cost is 4 + 9 + 17 = 30.
```

**Example 3:**

```
Input: sticks = [5]
Output: 0
Explanation: There is only one stick, so you don't need to do anything. The total cost is 0.
```

**Constraints:**

-   `1 <= sticks.length <= 104`
-   `1 <= sticks[i] <= 104`

### My Solution

```java
public int connectSticks(int[] sticks) {
    // create a min heap
    PriorityQueue<Integer> heap = new PriorityQueue<>((n1, n2) -> n1 - n2);

    // add to the heap
    for(int stick : sticks){
        heap.add(stick);
    }
    int cost = 0;
    while(heap.size() > 1){
        int stick1 = heap.poll();
        int stick2 = heap.poll();
        int newStick = stick1 + stick2;
        cost += stick1 + stick2;
        heap.add(newStick);
    }
    return cost;
}
```

*   Same as the given standard solution
*   Time complexity: $O(N\log{N})$, where N is the length of the input array. Let's break it down:
    -   Step 1) Adding N elements to the priority queue will be $O(N\log{N})$.
    -   Step 2) We remove two of the smallest elements and then add one element to the priority queue until we are left with one element. Since each such operation will reduce one element from the priority queue, we will perform N-1 such operations. Now, we know that both `add` and `remove` operations take $O(\log{N})$ in the priority queue, therefore, the complexity of this step will be $O(N\log{N})$.
*   Space complexity: $O(N)$ since we will store N elements in our priority queue.

## Furthest Building You Can Reach(Medium #1642)

**Question**: You are given an integer array `heights` representing the heights of buildings, some `bricks`, and some `ladders`.

You start your journey from building `0` and move to the next building by possibly using bricks or ladders.

While moving from building `i` to building `i+1` (**0-indexed**),

-   If the current building's height is **greater than or equal** to the next building's height, you do **not** need a ladder or bricks.
-   If the current building's height is **less than** the next building's height, you can either use **one ladder** or `(h[i+1] - h[i])` **bricks**.

*Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.*

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/10/27/q4.gif" alt="img" style="zoom:33%;" />

```
Input: heights = [4,2,7,6,9,14,12], bricks = 5, ladders = 1
Output: 4
Explanation: Starting at building 0, you can follow these steps:
- Go to building 1 without using ladders nor bricks since 4 >= 2.
- Go to building 2 using 5 bricks. You must use either bricks or ladders because 2 < 7.
- Go to building 3 without using ladders nor bricks since 7 >= 6.
- Go to building 4 using your only ladder. You must use either bricks or ladders because 6 < 9.
It is impossible to go beyond building 4 because you do not have any more bricks or ladders.
```

**Example 2:**

```
Input: heights = [4,12,2,7,3,18,20,3,19], bricks = 10, ladders = 2
Output: 7
```

**Example 3:**

```
Input: heights = [14,3,19,3], bricks = 17, ladders = 0
Output: 3
```

**Constraints:**

-   `1 <= heights.length <= 105`
-   `1 <= heights[i] <= 106`
-   `0 <= bricks <= 109`
-   `0 <= ladders <= heights.length`

### Standard Solution

#### Solution #1 Min Heap

*   Each time we assume to use ladders as a priority when the number of ladders is sufficient and put the number of steps of the ladder into a min-heap.
*   Then after using all the ladders, we start to use bricks to take the place of the ladders
    *   When the size of the heap is larger than the ladder number, we poll the topmost one to use bricks instead
    *   In this way, each time we find the shortest height and use bricks to save the ladder for a longer height
    *   Then minus the brick number until it is below 0, return the current index of array

```java
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // create a min-heap to store the ladder steps
        PriorityQueue<Integer> ladderSteps = new PriorityQueue<>((n1, n2) -> n1 - n2);
        
        // loop through the heights of the building, allocates the bricks and ladders
        for(int i = 0; i < heights.length - 1; i++){
            if (heights[i] > heights[i + 1]){
                continue;
            }
            // requires bricks and ladders for the climb
            ladderSteps.add(heights[i + 1] - heights[i]);
            
            // check if exceed the given ladder number
            if (ladderSteps.size() <= ladders){
                continue;
            }
            
            // otherwise, try to poll the topmost(min) to use bricks instead
            int changeToBricks = ladderSteps.poll();
            bricks -= changeToBricks;
            if (bricks < 0){
                return i;
            }
        }
        return heights.length - 1;
    }
```

*   Let N be the length of the `heights` array. Let L be the number of ladders available. We're mostly going to focus on analyzing in terms of N; however, it is also interesting to look at how the number of ladders available impacts the time and space complexity.

*   Time complexity: $O(N \log N)$ or $O(N \log L)$

    Inserting or removing an item from a heap incurs a cost of $O(\log x)$, where x is the number of items currently in the heap. In the worst case, we know that there will be an N - 1 climb in the heap, thus giving a time complexity of $O(\log N)$for each insertion and removal, and we're doing up to N of each of these two operations. This gives a total time complexity of $O(N \log N)$.

    In practice, though, the heap will never contain more than L + 1 climbs at a time—when it gets to this size, we immediately remove a climb from it. So, the heap operations are actually $O(\log L)$. We are still performing up to N of each of them, though, so this gives a total time complexity of $O(N \log L)$.

*   Space complexity: $O(N)$ or $O(L)$.

    As we determined above, the heap can contain up to $O(L)$ numbers at a time. In the worst case, $L = N$, so we get $O(N)$.

## Find Median from Data Stream(Hard #295)

**Question**: The **median** is the middle value in an ordered integer list. If the size of the list is even, there is no middle value and the median is the mean of the two middle values.

-   For example, for `arr = [2,3,4]`, the median is `3`.
-   For example, for `arr = [2,3]`, the median is `(2 + 3) / 2 = 2.5`.

Implement the MedianFinder class:

-   `MedianFinder()` initializes the `MedianFinder` object.
-   `void addNum(int num)` adds the integer `num` from the data stream to the data structure.
-   `double findMedian()` returns the median of all elements so far. Answers within `10-5` of the actual answer will be accepted. 

**Example 1:**

```
Input
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output
[null, null, null, 1.5, null, 2.0]

Explanation
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0
```

**Constraints:**

-   `-105 <= num <= 105`
-   There will be at least one element in the data structure before calling `findMedian`.
-   At most `5 * 104` calls will be made to `addNum` and `findMedian`.

**Follow up:**

-   If all integer numbers from the stream are in the range `[0, 100]`, how would you optimize your solution?
-   If `99%` of all integer numbers from the stream are in the range `[0, 100]`, how would you optimize your solution?

### My Solution

```java
// first attempt, exceed limit time
class MedianFinder {
    private PriorityQueue<Integer> heap;
    private List<Integer> storage;
    
    public MedianFinder() {
        heap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
    }
    
    public void addNum(int num) {
        heap.add(num);
    }
    
    public double findMedian() {
        storage = new ArrayList<>();
        int size = heap.size();
        boolean even = false;
        boolean complete = false;
        // check if the median exists
        if (size % 2 == 0){
            even = true;
        }
        // while we haven't find out the median
        int i = 0;
        double res = 0;
        while(!complete){
            int element = heap.poll();
            storage.add(element);
            i++;
            if(even && i >= size / 2){
                int lastElement = heap.poll();
                res = ((double)element + (double)lastElement)/2;
                storage.add(lastElement);
                complete = true;
            }
            else if (!even && i > size / 2){
                res = element;
                complete = true;
            }
        }
        while(storage.size() > 0){
            heap.add(storage.remove(0));
        }
        return res;
    }
}
```

```java
// second attempt, faster than 86%, less than 92%
class MedianFinder {
	// store value > median
    private PriorityQueue<Integer> minheap;
	// store value <= median
    private PriorityQueue<Integer> maxheap;
    
    public MedianFinder() {
        minheap = new PriorityQueue<Integer>((n1, n2) -> n1 - n2);
        maxheap = new PriorityQueue<Integer>((n1, n2) -> n2 - n1);
    }
    
    public void addNum(int num) {
        maxheap.add(num);
        minheap.add(maxheap.poll());
        if (maxheap.size() < minheap.size()){
            maxheap.add(minheap.poll());
        }
    }
    
    public double findMedian() {
        return maxheap.size() > minheap.size() ? maxheap.peek() : ((double)maxheap.peek() + minheap.peek()) * 0.5;
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
```

### Standard Solution

*   Various solutions are applicable, such as insertion sort, two pointers, two heaps
*   Only present two heap method

#### Solution #1 Two heaps

*   Same as my solution number 2
*   We would create min-heap and max-heap at the same time
    *   Min-heap: the value larger than the median
    *   Max-heap: the value smaller or equal to the median
*   In this way for adding a number:
    *   We add it to the max-heap first, let the heap rearrange the data
    *   Poll the topmost(maximum) number of the max-heap, move it to the min-heap
    *   Poll max-heap elements and balancing the size of two heaps
*   Find the median:
    *   If two heap has different size, poll the topmost element of the max-heap
    *   Otherwise, poll the topmost elements of two heaps and calculate the mean
*   Time complexity: $O(5 \cdot \log n) + O(1) \approx O(\log n)$
    -   At worst, there are three heap insertions and two heap deletions from the top. Each of these takes about $O(\log n)$ time.
    -   Finding the median takes constant $O(1)$ time since the tops of heaps are directly accessible.
*   Space complexity: $O(n)$ linear space to hold input in containers.