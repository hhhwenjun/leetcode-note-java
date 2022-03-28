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