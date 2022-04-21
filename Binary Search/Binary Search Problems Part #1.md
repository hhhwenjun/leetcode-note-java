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

