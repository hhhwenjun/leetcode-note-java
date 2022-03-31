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

### My Problem

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

### Standard Solution

#### Solution #1 Heap

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

    Adding to/removing from the heap (or priority queue) only takes $O(\log k)$ time when the size of the heap is capped at k elements.

-   Space complexity: $O(k)$

    The heap (or priority queue) will contain at most k elements.