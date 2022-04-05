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

*   **Template**
    *   ***Pre-processing*** - Sort if the collection is unsorted.
    *   ***Binary Search*** - Using a loop or recursion to divide search space in half after each comparison.
    *   ***Post-processing*** - Determine viable candidates in the remaining space.

```java
public int search(int[] nums, int target) {
    int low = 0, high = nums.length - 1;
    while(low <= high){
        int mid = low + (high - low) / 2;
        if (nums[mid] == target){
            return mid;
        }
        else if (nums[mid] > target){
            high = mid - 1;
        }
        else {
            low = mid + 1;
        }
    }
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