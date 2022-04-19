# Binary Search Tutorials

## Binary Search

*   Definition: Binary Search is one of the most fundamental and useful algorithms in Computer Science. It describes the process of **searching for a specific value** in an **ordered collection**.
*   Key point
    *   Target - the value that you are searching for
    *   Index - the current location that you are searching
    *   Left, Right - the indices from which we use to maintain our search Space
    *   Mid - the index that we use to apply a condition to determine if we should search left or right

## Method

*   Search Space: Binary Search operates on a contiguous sequence with a specified left and right index.
*   Binary Search maintains the left, right, and middle indices of the search space and compares the search target or applies the search condition to the middle value of the collection
*   **Always sort the collection before applying binary search.**
*   Binary Search can take many alternate forms and might not always be as straightforward as searching for a specific value.

*   **Template 1**
    *   ***Pre-processing*** - Sort if the collection is unsorted.
    *   ***Binary Search*** - Using a loop or recursion to divide search space in half after each comparison.
    *   ***Post-processing*** - Determine viable candidates in the remaining space.
    *   Initial Condition:` left = 0, right = length-1`
    *   Termination: `left > right`
    *   Searching Left: `right = mid-1`
    *   Searching Right: `left = mid+1`

```java
int binarySearch(int[] nums, int target){
  if(nums == null || nums.length == 0)
    return -1;

  int left = 0, right = nums.length - 1;
  while(left <= right){
    // Prevent (left + right) overflow
    int mid = left + (right - left) / 2;
    if(nums[mid] == target){ return mid; }
    else if(nums[mid] < target) { left = mid + 1; }
    else { right = mid - 1; }
  }

  // End Condition: left > right
  return -1;
}
```

*   **Template 2**
    *   Search Condition needs to access the element's immediate right neighbor
    *   Use the element's right neighbor to determine if the condition is met and decide whether to go left or right
    *   Initial Condition: `left = 0, right = length`
    *   Termination: `left == right`
    *   Searching Left: `right = mid`
    *   Searching Right: `left = mid+1`

```java
int binarySearch(int[] nums, int target){
  if(nums == null || nums.length == 0)
    return -1;

  int left = 0, right = nums.length;
  while(left < right){
    // Prevent (left + right) overflow
    int mid = left + (right - left) / 2;
    if(nums[mid] == target){ return mid; }
    else if(nums[mid] < target) { left = mid + 1; }
    else { right = mid; }
  }

  // Post-processing:
  // End Condition: left == right
  if(left != nums.length && nums[left] == target) return left;
  return -1;
}
```

*   **Template 3**
    *   Initial Condition:` left = 0, right = length-1`
    *   Termination: `left + 1 == right`
    *   Searching Left: `right = mid`
    *   Searching Right: `left = mid`

```java
int binarySearch(int[] nums, int target) {
    if (nums == null || nums.length == 0)
        return -1;

    int left = 0, right = nums.length - 1;
    while (left + 1 < right){
        // Prevent (left + right) overflow
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid;
        } else {
            right = mid;
        }
    }

    // Post-processing:
    // End Condition: left + 1 == right
    if(nums[left] == target) return left;
    if(nums[right] == target) return right;
    return -1;
}
```

## Sqrt(x) (Easy #69)

**Question**: Given a non-negative integer `x`, compute and return *the square root of* `x`.

Since the return type is an integer, the decimal digits are **truncated**, and only **the integer part** of the result is returned.

**Note:** You are not allowed to use any built-in exponent function or operators, such as `pow(x, 0.5)` or `x ** 0.5`.

**Example 1:**

```
Input: x = 4
Output: 2
```

**Example 2:**

```
Input: x = 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since the decimal part is truncated, 2 is returned.
```

**Constraints:**

-   `0 <= x <= 231 - 1`

### My Solution

*   Just be careful of the range of value, use long to cast the integer.
*   `low <= high` is the condition
*   Find the middle value `mid = low + (high - low) / 2`
*   Change the condition `high = mid - 1` and `low = mid + 1`

```java
public int mySqrt(int x) {
    if(x < 2){
        return x;
    }
    int low = 2, high = x / 2;
    while(low <= high){
        int mid = low + (high - low) / 2;
        if ((long)mid * mid > x){
            high = mid - 1;
        }
        else {
            low = mid + 1;
        }
    }
    return high;
}
```

### Standard Solution

#### Solution #1 Binary Search

*   Similar solution
*   Time complexity: $\mathcal{O}(\log N)$
*   Space complexity: $O(1)$

#### Solution #2 Pocket Calculator Algorithm

*   $\sqrt x=e^{\frac{1}{2}\log x}$

```java
public int mySqrt(int x){
    if (x < 2) return x;
    
    int left = (int)Math.pow(Math.E, 0.5 * Math.log(x));
    int right = left + 1;
    return (long)right * right > x ? left : right;
}
```

*   Time and space complexity are both $O(1)$.

## Guess Number Higher or Lower(Easy #374)

**Question**: We are playing the Guess Game. The game is as follows:

I pick a number from `1` to `n`. You have to guess which number I picked.

Every time you guess wrong, I will tell you whether the number I picked is higher or lower than your guess.

You call a pre-defined API `int guess(int num)`, which returns three possible results:

-   `-1`: Your guess is higher than the number I picked (i.e. `num > pick`).
-   `1`: Your guess is lower than the number I picked (i.e. `num < pick`).
-   `0`: your guess is equal to the number I picked (i.e. `num == pick`).

Return *the number that I picked*.

**Example 1:**

```
Input: n = 10, pick = 6
Output: 6
```

**Example 2:**

```
Input: n = 1, pick = 1
Output: 1
```

**Example 3:**

```
Input: n = 2, pick = 1
Output: 1
```

**Constraints:**

-   `1 <= n <= 231 - 1`
-   `1 <= pick <= n`

### My Solution

```java
// binary search solution
public int guessNumber(int n) {
    int low = 0, high = n;
    while(low <= high){
        int mid = low + (high - low)/2;
        if (guess(mid) == -1){
            high = mid - 1;
        }
        else if (guess(mid) == 1){
            low = mid + 1;
        }
        else {
            return mid;
        }
    }
    return 0;
}
```

### Standard Solution

#### Solution #1 Brute Force

```java
public int guessNumber(int n) {
    for (int i = 1; i < n; i++)
        if (guess(i) == 0)
            return i;
    return n;
}
```

-   Time complexity: $O(n)$. We scan all the numbers from 1 to n.
-   Space complexity: $O(1)$. No extra space is used.

#### Solution #2 Binary Search

*   Same as my solution
*   Time complexity: $O\big(\log_2 n\big)$. Binary Search is used.
*   Space complexity: $O(1)$. No extra space is used.

## Search in Rotated Sorted Array(Medium #33)

**Question**: There is an integer array `nums` sorted in ascending order (with **distinct** values).

Prior to being passed to your function, `nums` is **possibly rotated** at an unknown pivot index `k` (`1 <= k < nums.length`) such that the resulting array is `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]` (**0-indexed**). For example, `[0,1,2,4,5,6,7]` might be rotated at pivot index `3` and become `[4,5,6,7,0,1,2]`.

Given the array `nums` **after** the possible rotation and an integer `target`, return *the index of* `target` *if it is in* `nums`*, or* `-1` *if it is not in* `nums`.

You must write an algorithm with `O(log n)` runtime complexity.

**Example 1:**

```
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
```

**Example 2:**

```
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
```

**Example 3:**

```
Input: nums = [1], target = 0
Output: -1
```

**Constraints:**

-   `1 <= nums.length <= 5000`
-   `-104 <= nums[i] <= 104`
-   All values of `nums` are **unique**.
-   `nums` is an ascending array that is possibly rotated.
-   `-104 <= target <= 104`

### My Solution

```java
// binary search solution, but not efficient
public int search(int[] nums, int target) {
    if (nums.length == 1){
        return nums[0] == target ? 0 : -1;
    }
    // find the pivot index k
    int pivot = 0;
    for(int i = 0; i < nums.length - 1; i++){
        if (nums[i + 1] < nums[i]){
            pivot = nums.length - i - 1;
            break;
        }
    }
    Arrays.sort(nums);
    // use binary search to find the value(sorted array)
    int low = 0, high = nums.length - 1;
    int res = -1;
    while(low <= high){
        int mid = low + (high - low) / 2;
        if (nums[mid] > target){
            high = mid - 1;
        }
        else if (nums[mid] < target){
            low = mid + 1;
        }
        else {
            if (pivot > 0){
                if (mid < pivot){
                    res = nums.length - pivot + mid;
                }
                else {
                    res = mid - pivot;
                }
            }
            else {
                res = mid;
            }
            break;
        }
    }
    return res;
}
```

### Standard Solution

#### Solution #1 Binary Search

*   Similar to my solution, but more efficient
*   **Algorithm**: 
    *   Find a rotation index `rotation_index`, *i.e.* index of the smallest element in the array. Binary search works just perfect here.
    *   `rotation_index` splits array in two parts. Compare `nums[0]` and `target` to identify in which part one has to look for `target`.
    *   Perform a binary search in the chosen part of the array.

```java
class Solution {
  int [] nums;
  int target;

  public int find_rotate_index(int left, int right) {
    if (nums[left] < nums[right])
      return 0;

    while (left <= right) {
      int pivot = (left + right) / 2;
      if (nums[pivot] > nums[pivot + 1])
        return pivot + 1;
      else {
        if (nums[pivot] < nums[left])
          right = pivot - 1;
        else
          left = pivot + 1;
      }
    }
    return 0;
  }

  public int search(int left, int right) {
    /*
    Binary search
    */
    while (left <= right) {
      int pivot = (left + right) / 2;
      if (nums[pivot] == target)
        return pivot;
      else {
        if (target < nums[pivot])
          right = pivot - 1;
        else
          left = pivot + 1;
      }
    }
    return -1;
  }
  public int search(int[] nums, int target) {
    this.nums = nums;
    this.target = target;
    int n = nums.length;
    if (n == 1)
      return this.nums[0] == target ? 0 : -1;
    int rotate_index = find_rotate_index(0, n - 1);
    // if target is the smallest element
    if (nums[rotate_index] == target)
      return rotate_index;
    // if array is not rotated, search in the entire array
    if (rotate_index == 0)
      return search(0, n - 1);
    if (target < nums[0])
      // search in the right side
      return search(rotate_index, n - 1);
    // search in the left side
    return search(0, rotate_index);
  }
}
```

-   Time complexity: $\mathcal{O}(\log{N})$.
-   Space complexity: $\mathcal{O}(1)$.

#### Solution #2 One-pass Binary Search

*   Separate it to multiple conditions

```java
  public int search(int[] nums, int target) {
    int start = 0, end = nums.length - 1;
    while (start <= end) {
      int mid = start + (end - start) / 2;
      if (nums[mid] == target) return mid;
      else if (nums[mid] >= nums[start]) {
        if (target >= nums[start] && target < nums[mid]) end = mid - 1;
        else start = mid + 1;
      }
      else {
        if (target <= nums[end] && target > nums[mid]) start = mid + 1;
        else end = mid - 1;
      }
    }
    return -1;
  }
```

*   Complexity is the same

## First Bad Version(Easy #278)

**Question**: You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.

Suppose you have `n` versions `[1, 2, ..., n]` and you want to find out the first bad one, which causes all the following ones to be bad.

You are given an API `bool isBadVersion(version)` which returns whether `version` is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.

**Example 1:**

```
Input: n = 5, bad = 4
Output: 4
Explanation:
call isBadVersion(3) -> false
call isBadVersion(5) -> true
call isBadVersion(4) -> true
Then 4 is the first bad version.
```

**Example 2:**

```
Input: n = 1, bad = 1
Output: 1
```

**Constraints:**

-   `1 <= bad <= n <= 231 - 1`

### My Solution

```java
public int firstBadVersion(int n) {
    int low = 1, high = n;
    while(low < high){
        int mid = low + (high - low) / 2;
        boolean isBad = isBadVersion(mid);
        if (!isBad){
            low = mid + 1;
        }
        else {
            high = mid;
        }
    }
    if (isBadVersion(low)){
        return low;
    }
    return -1;
}
```

### Standard Solution

#### Solution #1 Binary Search

*   Same as my solution
*   `left` and `right` indicator meet and it must be the first bad version.

```java
public int firstBadVersion(int n) {
    int left = 1;
    int right = n;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (isBadVersion(mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}
```

-   Time complexity: $O(\log n)$. The search space is halved each time, so the time complexity is $O(\log n)$.
-   Space complexity: $O(1)$.

## Find Peak Element(Medium #162)

**Question**: A peak element is an element that is strictly greater than its neighbors.

Given an integer array `nums`, find a peak element, and return its index. If the array contains multiple peaks, return the index to **any of the peaks**.

You may imagine that `nums[-1] = nums[n] = -∞`.

You must write an algorithm that runs in `O(log n)` time.

**Example 1:**

```
Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.
```

**Example 2:**

```
Input: nums = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.
```

**Constraints:**

-   `1 <= nums.length <= 1000`
-   `-231 <= nums[i] <= 231 - 1`
-   `nums[i] != nums[i + 1]` for all valid `i`.

### My Solution

*   Use a hashmap to store the value in array and the index
*   Output the last one's index in the sorted array

```java
public int findPeakElement(int[] nums) {
    // first use hashmap to store the index of the integer
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++){
        if(!map.containsKey(nums[i])){
            map.put(nums[i], i);
        }
    }
    Arrays.sort(nums);
    return map.get(nums[nums.length - 1]);
}
```

### Standard Solution

*   From the description, we can assume there is one peak in the data
*   Moreover, every other values would be smaller than the peak

#### Solution #1 Recursive Binary Search

*   Recursively find the middle pivot, and check if this value larger than than `pivot + 1`
*   If so, we search the subarray before pivot, otherwise, search the subarray after the pivot
*   Search subarray until `left` and `right` converge

```java
public int findPeakElement(int[] nums) {
    return search(nums, 0, nums.length - 1);
}
public int search(int[] nums, int l, int r) {
    if (l == r)
        return l;
    int mid = (l + r) / 2;
    if (nums[mid] > nums[mid + 1])
        return search(nums, l, mid);
    return search(nums, mid + 1, r);
}
```

-   Time complexity: $O\big(log_2(n)\big)$. We reduce the search space in half at every step. Thus, the total search space will be consumed in $log_2(n)$ steps. Here, n refers to the size of nums array.
-   Space complexity: $O\big(log_2(n)\big)$. We reduce the search space in half at every step. Thus, the total search space will be consumed in $log_2(n)$ steps. Thus, the depth of recursion tree will go upto $log_2(n)$.

#### Solution #2 Iterative Binary Search

*   Similar idea to above solution, but in while loop

```java
public int findPeakElement(int[] nums){
    int l = 0, r = nums.length - 1;
    while(l < r){
        int mid = (l + r) / 2;
        if (nums[mid] > nums[mid + 1]){
            r = mid;
        }
        else {
            l = mid + 1;
        }
    }
    return l;
}
```

*   Time complexity: $O\big(log_2(n)\big)$. We reduce the search space in half at every step. Thus, the total search space will be consumed in $log_2(n)$ steps. Here, n*n* refers to the size of nums array.
*   Space complexity: $O(1)$. Constant extra space is used.

## Find Minimum in Rotated Sorted Array(Medium #153)

**Question**: Suppose an array of length `n` sorted in ascending order is **rotated** between `1` and `n` times. For example, the array `nums = [0,1,2,4,5,6,7]` might become:

-   `[4,5,6,7,0,1,2]` if it was rotated `4` times.
-   `[0,1,2,4,5,6,7]` if it was rotated `7` times.

Notice that **rotating** an array `[a[0], a[1], a[2], ..., a[n-1]]` 1 time results in the array `[a[n-1], a[0], a[1], a[2], ..., a[n-2]]`.

Given the sorted rotated array `nums` of **unique** elements, return *the minimum element of this array*.

You must write an algorithm that runs in `O(log n) time.`

**Example 1:**

```
Input: nums = [3,4,5,1,2]
Output: 1
Explanation: The original array was [1,2,3,4,5] rotated 3 times.
```

**Example 2:**

```
Input: nums = [4,5,6,7,0,1,2]
Output: 0
Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4 times.
```

**Example 3:**

```
Input: nums = [11,13,15,17]
Output: 11
Explanation: The original array was [11,13,15,17] and it was rotated 4 times. 
```

**Constraints:**

-   `n == nums.length`
-   `1 <= n <= 5000`
-   `-5000 <= nums[i] <= 5000`
-   All the integers of `nums` are **unique**.
-   `nums` is sorted and rotated between `1` and `n` times.

### My Solution

*   Binary search with template that ending with `left == right`
*   Compare the mid point with the edge point

```java
public int findMin(int[] nums) {
    int left = 0, right = nums.length - 1;
    while(left < right){
        int pivot = left + (right - left) / 2;
        // check if current pivot has larger value than end of subarray
        // meaning there is rotation
        if (nums[pivot] > nums[right]){
            left = pivot + 1;
        }
        else {
            right = pivot;
        }
    }
    return nums[left];
}
```

### Standard Solution

#### Solution #1 Binary Search

*   Similar idea with my solution
*   Time Complexity: Same as Binary Search $O(\log N)$
*   Space Complexity: $O(1)$

## Find First And Last Position of Element in Sorted Array(Medium #34)

**Question**: Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value.

If `target` is not found in the array, return `[-1, -1]`.

You must write an algorithm with `O(log n)` runtime complexity.

**Example 1:**

```
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
```

**Example 2:**

```
Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
```

**Example 3:**

```
Input: nums = [], target = 0
Output: [-1,-1]
```

**Constraints:**

-   `0 <= nums.length <= 105`
-   `-109 <= nums[i] <= 109`
-   `nums` is a non-decreasing array.
-   `-109 <= target <= 109`

### My Solution

```java
public int[] searchRange(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    int targetLow = -1, targetHigh = -1;
    boolean lowFound = false, highFound = false;
    while (low <= high){
        if (nums[low] == target && !lowFound){
            targetLow = low;
            lowFound = true;
        }
        if (nums[high] == target && !highFound){
            targetHigh = high;
            highFound = true;
        }
        if (!lowFound){
            low++;
        }
        if (!highFound){
            high--;
        }
        if (highFound && lowFound){
            break;
        }
    }
    return new int[]{targetLow, targetHigh};
}
```

### Standard Solution

#### Solution #1 Binary Search

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {      
        int firstOccurrence = this.findBound(nums, target, true);        
        if (firstOccurrence == -1) {
            return new int[]{-1, -1};
        }      
        int lastOccurrence = this.findBound(nums, target, false);       
        return new int[]{firstOccurrence, lastOccurrence};
    }
    private int findBound(int[] nums, int target, boolean isFirst) {
        int N = nums.length;
        int begin = 0, end = N - 1;       
        while (begin <= end) {     
            int mid = (begin + end) / 2;         
            if (nums[mid] == target) {         
                if (isFirst) {           
                    // This means we found our lower bound.
                    if (mid == begin || nums[mid - 1] != target) {
                        return mid;
                    }            
                    // Search on the left side for the bound.
                    end = mid - 1;                
                } else {           
                    // This means we found our upper bound.
                    if (mid == end || nums[mid + 1] != target) {
                        return mid;
                    }        
                    // Search on the right side for the bound.
                    begin = mid + 1;
                }         
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                begin = mid + 1;
            }
        }
        return -1;
    }
}
```

-   Time Complexity: $O(\text{log} N)$ considering there are N elements in the array. This is because binary search takes logarithmic time to scan an array of N elements. Why? Because at each step we discard half of the array we are scanning and hence, we're done after a logarithmic number of steps. We simply perform a binary search twice in this case.
-   Space Complexity: $O(1)$ since we only use space for a few variables and our result array, all of which require constant space.

## Find K Closest Elements(Medium #658)

**Question**: Given a **sorted** integer array `arr`, two integers `k` and `x`, return the `k` closest integers to `x` in the array. The result should also be sorted in ascending order.

An integer `a` is closer to `x` than an integer `b` if:

-   `|a - x| < |b - x|`, or
-   `|a - x| == |b - x|` and `a < b`

**Example 1:**

```
Input: arr = [1,2,3,4,5], k = 4, x = 3
Output: [1,2,3,4]
```

**Example 2:**

```
Input: arr = [1,2,3,4,5], k = 4, x = -1
Output: [1,2,3,4]
```

**Constraints:**

-   `1 <= k <= arr.length`
-   `1 <= arr.length <= 104`
-   `arr` is sorted in **ascending** order.
-   `-104 <= arr[i], x <= 104`

### My Solution

*   Create a comparator to sort the values

```java
public List<Integer> findClosestElements(int[] arr, int k, int x) {
    List<Integer> res = new ArrayList<>();
    for (int digit : arr){
        res.add(digit);
    }
    Collections.sort(res, new Comparator<Integer>(){
        @Override
        public int compare(Integer n1, Integer n2){ // have to use integer instead of int
            return Math.abs(x - n1) - Math.abs(x - n2);
        }
    });
    res = res.subList(0, k);
    Collections.sort(res);
    return res;
}
```

### Standard Solution

#### Solution #1 Sort with Custom Comparator

*   Similar to my solution
*   A more simplified version

```java
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // Convert from array to list first to make use of Collections.sort()
        List<Integer> sortedArr = new ArrayList<Integer>();
        for (int num: arr) {
            sortedArr.add(num);
        }
        // Sort using custom comparator
        Collections.sort(sortedArr, (num1, num2) -> Math.abs(num1 - x) - Math.abs(num2 - x));
        // Only take k elements
        sortedArr = sortedArr.subList(0, k);
        // Sort again to have output in ascending order
        Collections.sort(sortedArr);
        return sortedArr;
    }
}
```

-   Time complexity: $O(N \cdot \log(N) + k \cdot \log(k))$

    To build `sortedArr`, we need to sort every element in the array by a new criteria: `x - num`. This costs $O(N \cdot \log(N))$. Then, we have to sort `sortedArr` again to get the output in ascending order. This costs $O(k \cdot \log(k))$ time since `sortedArr.length` is only `k`.

-   Space complexity: $O(N)$

    Before we slice `sortedArr` to contain only `k` elements, it contains every element from `arr`, which requires $O(N)$ extra space. Note that we can use less space if we sort the input in place.

#### Solution #2 Binary Search

*   Binary search to find the closest element
*   Find the target location in the array
*   Create window size with the target center, and expand the window size until reach k

```java
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList<Integer>();        
        // Base case
        if (arr.length == k) {
            for (int i = 0; i < k; i++) {
                result.add(arr[i]);
            }
            
            return result;
        }      
        // Binary search to find the closest element
        int left = 0;
        int right = arr.length;
        int mid = 0;
        while (left < right) {
            mid = (left + right) / 2;
            if (arr[mid] >= x) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        // Initialize our sliding window's bounds
        left -= 1;
        right = left + 1;
        
        // While the window size is less than k
        while (right - left - 1 < k) {
            // Be careful to not go out of bounds
            if (left == -1) {
                right += 1;
                continue;
            }         
            // Expand the window towards the side with the closer number
            // Be careful to not go out of bounds with the pointers
            if (right == arr.length || Math.abs(arr[left] - x) <= Math.abs(arr[right] - x)) {
                left -= 1;
            } else {
                right += 1;
            }
        } 
        // Build and return the window
        for (int i = left + 1; i < right; i++) {
            result.add(arr[i]);
        }    
        return result;
    }
}
```

-   Time complexity: $O(\log(N) + k)$.

    The initial binary search to find where we should start our window costs $O(\log(N))$. Our sliding window initially starts with size 0 and we expand it one by one until it is of size `k`, thus it costs $O(k)$ to expand the window.

-   Space complexity: $O(1)$

    We only use integer variables `left` and `right` that are $O(1)$ regardless of input size. Space used for the output is not counted towards the space complexity.