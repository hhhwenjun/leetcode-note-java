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