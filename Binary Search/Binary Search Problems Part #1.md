# Binary Search Problems Part #1

## Closest Binary Search Tree Value(Easy #270)

**Question**: Given the `root` of a binary search tree and a `target` value, return *the value in the BST that is closest to the* `target` 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/03/12/closest1-1-tree.jpg)

```
Input: root = [4,2,5,1,3], target = 3.714286
Output: 4
```

**Example 2:**

```
Input: root = [1], target = 4.428571
Output: 1
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 104]`.
-   `0 <= Node.val <= 109`
-   `-109 <= target <= 109`

### My Solution

*   In order recursively find the distance between the value and the target
*   Compare the distance and record the root value

```java
class Solution {
    int value = 0;
    public int closestValue(TreeNode root, double target) {
        double distance = Double.POSITIVE_INFINITY;
        findValue(root, target, distance);
        return value;
    }
    // recursively find the value near target
    public double findValue(TreeNode root, double target, double distance){
        if (root == null){
            return distance;
        }
        distance = findValue(root.left, target, distance);
        if ((double)Math.abs(target - root.val) < distance){
            distance = (double)Math.abs(target - root.val);
            value = root.val;
        }
        distance = findValue(root.right, target, distance);
        return distance;
    }
}
```

### Standard Solution

#### Solution #1 Recursive Inorder + Linear Search

*   Recursively use the list to record all the value
*   Create a customized comparator for comparison

```java
public void inorder(TreeNode root, List<Integer> nums){
    if (root == null) return;
    inorder(root.left, nums);
    nums.add(root.val);
    inorder(root.right, nums);
}
public int closestValue(TreeNode root, double target){
    List<Integer> nums = new ArrayList();
    inorder(root, nums); // put all values to the list
    return Collections.min(nums, new Comparator<Integer>(){
        @Override
        public int compare(Integer o1, Integer o2){
            return Math.abs(o1 - target) < Math.abs(o2 - target) ? -1 : 1;
        }
    });
}
```

-   Time complexity: $\mathcal{O}(N)$ because to build in order traversal and then to perform linear search takes linear time.
-   Space complexity: $\mathcal{O}(N)$ to keep in order traversal.

#### Solution #2 Binary Search, O(H) time

*   Use the properties of the binary search tree

<img src="https://leetcode.com/problems/closest-binary-search-tree-value/Figures/270/binary.png" alt="pic" style="zoom:50%;" />

```java
public int closestValue(TreeNode root, double target){
    int val, closest = root.val;
    while (root != null){
        val = root.val;
        closest = Math.abs(val - target) < Math.abs(closest - target) ? val : closest;
        root = target < root.val ? root.left : root.right;
    }
    return closest;
}
```

-   Time complexity: $\mathcal{O}(H)$ since here one goes from root down to a leaf.
-   Space complexity: $\mathcal{O}(1)$.

## Search in a Sorted Array of Unknown Size(Medium #702)

**Question**: This is an ***interactive problem***.

You have a sorted array of **unique** elements and an **unknown size**. You do not have an access to the array but you can use the `ArrayReader` interface to access it. You can call `ArrayReader.get(i)` that:

-   returns the value at the `ith` index (**0-indexed**) of the secret array (i.e., `secret[i]`), or
-   returns `231 - 1` if the `i` is out of the boundary of the array.

You are also given an integer `target`.

Return the index `k` of the hidden array where `secret[k] == target` or return `-1` otherwise.

You must write an algorithm with `O(log n)` runtime complexity.

**Example 1:**

```
Input: secret = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in secret and its index is 4.
```

**Example 2:**

```
Input: secret = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in secret so return -1.
```

**Constraints:**

-   `1 <= secret.length <= 104`
-   `-104 <= secret[i], target <= 104`
-   `secret` is sorted in a strictly increasing order.

### My Solution

*   Binary search method

```java
public int search(ArrayReader reader, int target) {
    int max = (int)Math.pow(10, 4);
    int low = 0;
    while (low < max){
        int mid = low + (max - low) / 2;
        int value = reader.get(mid);
        if (value > target){
            max = mid - 1;
        }
        else if (value < target){
            low = mid + 1;
        }
        else {
            return mid;
        }
    }
    return reader.get(low) == target ? low : -1;
}
```

### Standard Solution

#### Solution #1 Binary Search + Bit Operation

```java
class Solution {
  public int search(ArrayReader reader, int target) {
    if (reader.get(0) == target) return 0;
    // search boundaries
    int left = 0, right = 1;
    while (reader.get(right) < target) {
      left = right;
      right <<= 1;
    }
    // binary search
    int pivot, num;
    while (left <= right) {
      pivot = left + ((right - left) >> 1);
      num = reader.get(pivot);

      if (num == target) return pivot;
      if (num > target) right = pivot - 1;
      else left = pivot + 1;
    }
    // there is no target element
    return -1;
  }
}
```

-   Time complexity: $\mathcal{O}(\log T)$, where T is an index of the target value.

    There are two operations here: to define search boundaries and to perform a binary search.

    Let's first find the number of steps k to set up the boundaries. In the first step, the boundaries are $2^0 .. 2^{0 + 1}$, on the second step $2^1 .. 2^{1 + 1}$, etc. When everything is done, the boundaries are $2^k .. 2^{k + 1}$ and $2^k < T \le 2^{k + 1}$. That means one needs $k = \log T$ steps to set up the boundaries, that means $\mathcal{O}(\log T)$ time complexity.

    Now let's discuss the complexity of the binary search. There are $2^{k + 1} - 2^k = 2^k$ elements in the boundaries, i.e. $2^{\log T} = T2$ elements. Binary search has logarithmic complexity, which results in $\mathcal{O}(\log T)$ time complexity.

-   Space complexity: $\mathcal{O}(1)$ since it's a constant space solution.

## Valid Perfect Square(Easy #367)

**Question**: Given a **positive** integer *num*, write a function that returns True if *num* is a perfect square else False.

**Follow up:** **Do not** use any built-in library function such as `sqrt`.

**Example 1:**

```
Input: num = 16
Output: true
```

**Example 2:**

```
Input: num = 14
Output: false
```

**Constraints:**

-   `1 <= num <= 2^31 - 1`

### My Solution

*   Use binary search to find mid, check if the square larger or smaller than the target

```java
public boolean isPerfectSquare(int num) {
    if (num == 0 || num == 1){
        return true;
    }
    if (num < 0){
        return false;
    }
    int low = 1, high = num / 2;
    while (low < high){
        int mid = low + (high - low) / 2;
        if ((long)mid * mid < num){
            low = mid + 1;
        }
        else if ((long)mid * mid > num){
            high = mid - 1;
        }
        else {
            return true;
        }
    }
    return low * low == num ? true : false;
}
```

### Standard Solution

#### Solution #1 Binary Search

*   Almost the same as my solution

```java
 public boolean isPerfectSquare(int num) {
    if (num < 2) {
      return true;
    }
    long left = 2, right = num / 2, x, guessSquared;
    while (left <= right) {
      x = left + (right - left) / 2;
      guessSquared = x * x;
      if (guessSquared == num) {
        return true;
      }
      if (guessSquared > num) {
        right = x - 1;
      } else {
        left = x + 1;
      }
    }
    return false;
 }
```

*   Time complexity: $\mathcal{O}(\log N)$.
*   Space complexity: $\mathcal{O}(1)$.

## Find Smallest Letter Greater Than Target (Easy #744)

**Question**: Given a characters array `letters` that is sorted in **non-decreasing** order and a character `target`, return *the smallest character in the array that is larger than* `target`.

**Note** that the letters wrap around.

-   For example, if `target == 'z'` and `letters == ['a', 'b']`, the answer is `'a'`.

**Example 1:**

```
Input: letters = ["c","f","j"], target = "a"
Output: "c"
```

**Example 2:**

```
Input: letters = ["c","f","j"], target = "c"
Output: "f"
```

**Example 3:**

```
Input: letters = ["c","f","j"], target = "d"
Output: "f"
```

**Constraints:**

-   `2 <= letters.length <= 104`
-   `letters[i]` is a lowercase English letter.
-   `letters` are sorted in **non-decreasing** order.
-   `letters` contain at least two different characters.
-   `target` is a lowercase English letter.

### My Solution

*   Calculate the relative distance

```java
public char nextGreatestLetter(char[] letters, char target) {
    int distance = 0;
    int minDistance = Integer.MAX_VALUE;
    char res = 'a';
    for (char letter : letters){
        if (letter >= target){
            distance = letter - target;
        }
        else {
            distance = 'z' - target + letter - 'a' + 1;
        }
        if (distance > 0 && distance < minDistance){
            minDistance = distance;
            res = letter;
        }
    }
    return res;
}
```

### Standard Solution

#### Solution #1 Linear Scan

*   The question asks for the letter, not distance, don't need to calculate the distance
*   Just loop through the letters and find the one that meets the requirement

```java
class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        for (char c: letters)
            if (c > target) return c;
        return letters[0];
    }
}
```

-   Time Complexity: $O(N)$, where N is the length of `letters`. We scan every element of the array.
-   Space Complexity: $O(1)$, as we maintain only pointers.

#### Solution #2 Binary Search

*   Binary search method to check if the letter is larger than the target

```java
public char nextGreatestLetter(char[] letters, char target) {
    int lo = 0, hi = letters.length;
    while (lo < hi) {
        int mi = lo + (hi - lo) / 2;
        if (letters[mi] <= target) lo = mi + 1;
        else hi = mi;
    }
    return letters[lo % letters.length];
}
```

-   Time Complexity: $O(\log N)$, where N is the length of `letters`. We peek only at $\log N$ elements in the array.
-   Space Complexity: $O(1)$, as we maintain only pointers.

## Find Minimum in Rotated Sorted Array II (Hard #154)

**Question**: Suppose an array of length `n` sorted in ascending order is **rotated** between `1` and `n` times. For example, the array `nums = [0,1,4,4,5,6,7]` might become:

-   `[4,5,6,7,0,1,4]` if it was rotated `4` times.
-   `[0,1,4,4,5,6,7]` if it was rotated `7` times.

Notice that **rotating** an array `[a[0], a[1], a[2], ..., a[n-1]]` 1 time results in the array `[a[n-1], a[0], a[1], a[2], ..., a[n-2]]`.

Given the sorted rotated array `nums` that may contain **duplicates**, return *the minimum element of this array*.

You must decrease the overall operation steps as much as possible.

**Example 1:**

```
Input: nums = [1,3,5]
Output: 1
```

**Example 2:**

```
Input: nums = [2,2,2,0,1]
Output: 0
```

**Constraints:**

-   `n == nums.length`
-   `1 <= n <= 5000`
-   `-5000 <= nums[i] <= 5000`
-   `nums` is sorted and rotated between `1` and `n` times.

### Standard Solution

#### Solution #1 Variant of Binary Search

*   Compare the pivot element to the element pointed by the upper bound pointer
*   We use the upper bound of the search scope as the reference for the comparison with the pivot element, while in the classical binary search the reference would be the desired value.
*   When the result of the comparison is equal (*i.e.* Case #3), we further move the upper bound, while in the classical binary search normally we would return the value immediately.

```java
public int findMin(int[] nums) {
    int low = 0, high = nums.length - 1;
    while (low < high){
        int mid = low + (high - low) / 2;
        if (nums[mid] > nums[high]){
            low = mid + 1;
        }
        else if (nums[mid] < nums[high]) {
            high = mid;
        }
        // change the edge value since it is valid
        else {
            high--;
        }
    }
    return nums[low];
}
```

-   Time complexity: on average $\mathcal{O}(\log_{2}{N})$ where N is the length of the array, since in general, it is a binary search algorithm. However, in the worst case where the array contains identical elements (*i.e.* case #3 `nums[pivot]==nums[high]`), the algorithm would deteriorate to iterating each element, as a result, the time complexity becomes $\mathcal{O}(N)$.
-   Space complexity: $\mathcal{O}(1)$, it's a constant space solution.

## Median of Two Sorted Array(Hard #4)

**Question**: Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return **the median** of the two sorted arrays.

The overall run time complexity should be `O(log (m+n))`.

**Example 1:**

```
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.
```

**Example 2:**

```
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
```

**Constraints:**

-   `nums1.length == m`
-   `nums2.length == n`
-   `0 <= m <= 1000`
-   `0 <= n <= 1000`
-   `1 <= m + n <= 2000`
-   `-106 <= nums1[i], nums2[i] <= 106`

### My Solution

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int length = nums1.length + nums2.length;
    int[] nums = new int[length];
    for (int i = 0; i < nums1.length; i++){
        nums[i] = nums1[i];
    }
    for (int i = nums1.length; i < length; i++){
        nums[i] = nums2[i - nums1.length];
    }
    Arrays.sort(nums);
    double median = 0.0;
    // find the median of the aggregated array
    if (length % 2 == 1){
        median = nums[length/ 2];
    }
    else {
        median = ((double)nums[(length - 1)/ 2] + (double)nums[(length - 1) / 2 + 1]) / 2;
    }
    return median;
}
```

### Other Solution

*   Binary search and compare two value

```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int index1 = 0;
    int index2 = 0;
    int med1 = 0;
    int med2 = 0;
    for (int i=0; i<=(nums1.length+nums2.length)/2; i++) {
        med1 = med2;
        if (index1 == nums1.length) {
            med2 = nums2[index2];
            index2++;
        } else if (index2 == nums2.length) {
            med2 = nums1[index1];
            index1++;
        } else if (nums1[index1] < nums2[index2] ) {
            med2 = nums1[index1];
            index1++;
        }  else {
            med2 = nums2[index2];
            index2++;
        }
    }

    // the median is the average of two numbers
    if ((nums1.length+nums2.length)%2 == 0) {
        return (float)(med1+med2)/2;
    }
    return med2;
}
```

## Find k-th Smallest Pair Distance (Hard #719)

**Question**: The **distance of a pair** of integers `a` and `b` is defined as the absolute difference between `a` and `b`.

Given an integer array `nums` and an integer `k`, return *the* `kth` *smallest **distance among all the pairs*** `nums[i]` *and* `nums[j]` *where* `0 <= i < j < nums.length`.

**Example 1:**

```
Input: nums = [1,3,1], k = 1
Output: 0
Explanation: Here are all the pairs:
(1,3) -> 2
(1,1) -> 0
(3,1) -> 2
Then the 1st smallest distance pair is (1,1), and its distance is 0.
```

**Example 2:**

```
Input: nums = [1,1,1], k = 2
Output: 0
```

**Example 3:**

```
Input: nums = [1,6,1], k = 3
Output: 5
```

**Constraints:**

-   `n == nums.length`
-   `2 <= n <= 104`
-   `0 <= nums[i] <= 106`
-   `1 <= k <= n * (n - 1) / 2`

### Standard Solution

#### Solution #1 Binary Search

*   Find low distance, and high distance, use binary search

```java
public int smallestDistancePair(int[] nums, int k) {
    // sort the array and then do the binary search
    Arrays.sort(nums);

    int lo = 0;
    int hi = nums[nums.length - 1] - nums[0];
    while (lo < hi) {
        int mi = (lo + hi) / 2;
        int count = 0, left = 0;
        for (int right = 0; right < nums.length; right++){
            // find out how many count of pairs with distance <= mi
            while (nums[right] - nums[left] > mi) left++;
            count += right - left;
        }
        // count = number of pairs with distance <= mi
        if (count >= k) hi = mi;
        else lo = mi + 1;
    }
    return lo;
}
```

-   Time Complexity: $O(N \log{W} + N \log{N})$, where N is the length of `nums`, and W*W* is equal to `nums[nums.length - 1] - nums[0]`. The $\log W$ factor comes from our binary search, and we do $O(N)$ work inside our call to `possible` (or to calculate `count` in Java). The final $O(N\log N)$ factor comes from sorting.
-   Space Complexity: $O(1)$. No additional space is used except for integer variables.

## Split Array Largest Sum (Hard #410)

**Question**: Given an array `nums` which consists of non-negative integers and an integer `m`, you can split the array into `m` non-empty continuous subarrays.

Write an algorithm to minimize the largest sum among these `m` subarrays.

**Example 1:**

```
Input: nums = [7,2,5,10,8], m = 2
Output: 18
Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7,2,5] and [10,8],
where the largest sum among the two subarrays is only 18.
```

**Example 2:**

```
Input: nums = [1,2,3,4,5], m = 2
Output: 9
```

**Example 3:**

```
Input: nums = [1,4,4], m = 3
Output: 4 
```

**Constraints:**

-   `1 <= nums.length <= 1000`
-   `0 <= nums[i] <= 106`
-   `1 <= m <= min(50, nums.length)`

### Standard Solution

#### Solution #1 Binary Search

*   Not only use binary search on array index but also use it for value search
*   Check if the target value converge

```java
private int minimumSubarraysRequired(int[] nums, int maxSumAllowed) {
    int currentSum = 0;
    int splitsRequired = 0;
    for (int element : nums) {
        // Add element only if the sum doesn't exceed maxSumAllowed
        if (currentSum + element <= maxSumAllowed) {
            currentSum += element;
        } else {
            // If the element addition makes sum more than maxSumAllowed
            // Increment the splits required and reset sum
            currentSum = element;
            splitsRequired++;
        }
    }
    // Return the number of subarrays, which is the number of splits + 1
    return splitsRequired + 1;
}

public int splitArray(int[] nums, int m) {
    // Find the sum of all elements and the maximum element
    int sum = 0;
    int maxElement = Integer.MIN_VALUE;
    for (int element : nums) {
        sum += element;
        maxElement = Math.max(maxElement, element);
    }
    // Define the left and right boundary of binary search
    int left = maxElement;
    int right = sum;
    int minimumLargestSplitSum = 0;
    while (left <= right) {
        // Find the mid value
        int maxSumAllowed = left + (right - left) / 2;
        // Find the minimum splits. If splitsRequired is less than
        // or equal to m move towards left i.e., smaller values
        if (minimumSubarraysRequired(nums, maxSumAllowed) <= m) {
            right = maxSumAllowed - 1;
            minimumLargestSplitSum = maxSumAllowed;
        } else {
            // Move towards right if splitsRequired is more than m
            left = maxSumAllowed + 1;
        }
    }
    return minimumLargestSplitSum;
}
```

-   Time complexity: $O(N \cdot \log(S))$, where N is the length of the array and S is the sum of integers in the array.

    The total number of iterations in the binary search is $\log(S)$, and for each such iteration, we call `minimumSubarraysRequired` which takes $O(N)$ time. Hence, the time complexity is equal to $O(N \cdot \log(S))$.

-   Space complexity: $O(1)$

    We do not use any data structures that require more than constant extra space.

## 3Sum Closest (Medium #16)

**Question**: Given an integer array `nums` of length `n` and an integer `target`, find three integers in `nums` such that the sum is closest to `target`.

Return *the sum of the three integers*.

You may assume that each input would have exactly one solution.

**Example 1:**

```
Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
```

**Example 2:**

```
Input: nums = [0,0,0], target = 1
Output: 0
```

**Constraints:**

-   `3 <= nums.length <= 1000`
-   `-1000 <= nums[i] <= 1000`
-   `-104 <= target <= 104`

### Standard Solution

#### Solution #1 Two Pointers

*   Use for loop and three-pointers, calculate the sum
*   Compare the sum with the target, if smaller, move the low pointer, if higher, move the high pointer

```java
public int threeSumClosest(int[] nums, int target) {
    int diff = Integer.MAX_VALUE;
    int sz = nums.length;
    Arrays.sort(nums);
    for (int i = 0; i < sz && diff != 0; i++){
        int lo = i + 1;
        int hi = sz - 1;
        while (lo < hi){
            int sum = nums[i] + nums[lo] + nums[hi];
            if (Math.abs(target - sum) < Math.abs(diff)){
                diff = target - sum;
            }
            if (sum < target){
                lo++;
            }
            else {
                hi--;
            }
        }
    }
    return target - diff;
}
```

-   Time Complexity: $\mathcal{O}(n^2)$. We have outer and inner loops, each going through n elements.

    Sorting the array takes $\mathcal{O}(n\log{n})$,  so overall complexity is $\mathcal{O}(n\log{n} + n^2)$. This is asymptotically equivalent to $\mathcal{O}(n^2)$.

-   Space Complexity: from $\mathcal{O}(\log{n})$ to $\mathcal{O}(n)$, depending on the implementation of the sorting algorithm.

#### Solution #2 Binary Search

```java
public int threeSumClosest(int[] nums, int target) {
    int diff = Integer.MAX_VALUE;
    int sz = nums.length;
    Arrays.sort(nums);
    for (int i = 0; i < sz && diff != 0; ++i) {
        for (int j = i + 1; j < sz - 1; ++j) {
            int complement = target - nums[i] - nums[j];
            var idx = Arrays.binarySearch(nums, j + 1, sz - 1, complement);
            int hi = idx >= 0 ? idx : ~idx, lo = hi - 1;
            if (hi < sz && Math.abs(complement - nums[hi]) < Math.abs(diff))
                diff = complement - nums[hi];
            if (lo > j && Math.abs(complement - nums[lo]) < Math.abs(diff))
                diff = complement - nums[lo];
        }
    }
    return target - diff;
}
```

-   Time Complexity: $\mathcal{O}(n^2\log{n})$. Binary search takes $\mathcal{O}(\log{n})$, and we do it n times in the inner loop. Since we are going through n elements in the outer loop, the overall complexity is $\mathcal{O}(n^2\log{n})$.
-   Space Complexity: from $\mathcal{O}(\log{n})$ to $\mathcal{O}(n)$, depending on the implementation of the sorting algorithm.