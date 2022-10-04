# Recursion Tutorial

## Introduction

*   Recursion is an approach to solving problems using a function that calls itself a subroutine.

*   The trick is that each time a recursive function calls itself, it reduces the given problem into subproblems. 

*   A recursive function should have the following properties so that it does not result in an infinite loop:

    1.  A simple `base case` (or cases) — a terminating scenario that does not use recursion to produce an answer.
    2.  A set of rules, also known as a `recurrence relation,` reduces all other cases towards the base case.

*   **Example**: Print a string in reverse order.

    ```java
    private static void printReverse(char [] str) {
      helper(0, str);
    }
    
    private static void helper(int index, char [] str) {
      if (str == null || index >= str.length) {
        return;
      }
      helper(index + 1, str);
      System.out.print(str[index]);
    }
    ```

*   **Example**:

    ```
    Given a linked list, swap every two adjacent nodes and return its head.
    
    e.g.  for a list 1-> 2 -> 3 -> 4, one should return the head of list as 2 -> 1 -> 4 -> 3.
    ```

    *   First, we swap the first two nodes in the list, *i.e.* `head` and `head. next`;
    *   Then, we call the function self as `swap(head.next.next)` to swap the rest of the list following the first two nodes.
    *   Finally, we attach the returned head of the sub-list in step (2) with the two nodes swapped in step (1) to form a new linked list.

## Swap Nodes in Pairs(Medium #24)

**Question**: Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/03/swap_ex1.jpg)

```
Input: head = [1,2,3,4]
Output: [2,1,4,3]
```

**Example 2:**

```
Input: head = []
Output: []
```

**Example 3:**

```
Input: head = [1]
Output: [1]
```

**Constraints:**

-   The number of nodes in the list is in the range `[0, 100]`.
-   `0 <= Node.val <= 100`

### My Solution

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }
    ListNode oldHead = head;
    head = head.next;
    oldHead.next = head.next;
    head.next = oldHead;
    oldHead.next = swapPairs(head.next.next);
    return head;
}
```

### Standard Solution

#### Solution #1 Recursive Approach

*   Similar to my solution
*   In every function call we take out two nodes that would be swapped and the remaining nodes are passed to the next recursive call.
*   Each time swap two nodes and similar to sliding window, similar to backtracking
*   Return the head of the two nodes

```java
public ListNode swapPairs(ListNode head) {
    // If the list has no node or has only one node left.
    if ((head == null) || (head.next == null)) {
        return head;
    }
    // Nodes to be swapped
    ListNode firstNode = head;
    ListNode secondNode = head.next;
    // Swapping
    firstNode.next  = swapPairs(secondNode.next);
    secondNode.next = firstNode;
    // Now the head is the second node
    return secondNode;
}
```

*   Time Complexity: $O(N)$ where N is the size of the linked list.
*   Space Complexity: $O(N)$ stack space utilized for recursion.

#### Solution #2 Iterative Approach

*   Swap the nodes on the go

*   After swapping a pair of nodes, say `A` and `B`, we need to link node `B` to the node that was right before `A`. 

    <img src="https://leetcode.com/problems/swap-nodes-in-pairs/Figures/24/24_Swap_Nodes_7.png" alt="img" style="zoom:50%;" />

<img src="https://leetcode.com/problems/swap-nodes-in-pairs/Figures/24/24_Swap_Nodes_8.png" alt="img" style="zoom:50%;" />

```java
public ListNode swapPairs(ListNode head){
    // Dummy node acts as the prevNode for the head node
    // of the list and hence stores pointer to the head node
    ListNode dummy = new ListNode(-1);
    dummy.next = head;
    
    ListNode prevNode = dummy;
    while ((head != null) && (head.next != null)){
        // Nodes to be swapped
        ListNode firstNode = head;
        ListNode secondNode = head.next;
        
        // swapping
        prevNode.next = secondNode;
        firstNode.next = secondNode.next;
        secondNode.next = firstNode;
        
        // Reinitializing the head and prevNode for next swap
        prevNode = firstNode;
        head = firstNode.next;//jump
    }
    return dummy.next;
}
```

*   Time Complexity : $O(N)$ where N is the size of the linked list.
*   Space Complexity : $O(1)$.

## Reverse Linked List(Easy #206)

**Question**: Given the `head` of a singly linked list, reverse the list, and return *the reversed list*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/rev1ex1.jpg)

```
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/02/19/rev1ex2.jpg)

```
Input: head = [1,2]
Output: [2,1]
```

**Example 3:**

```
Input: head = []
Output: []
```

**Constraints:**

-   The number of nodes in the list is the range `[0, 5000]`.
-   `-5000 <= Node.val <= 5000`

### Standard Solution

#### Solution #1 Recursion

*   The key of recursion is to work backwards
*   Assume the rest of the list had already been reversed, how to reverse the front part
*   Avoid linking the list as a cycle.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public ListNode reverseList(ListNode head) {
    // base case
    if (head == null || head.next == null){
        return head;
    }
    ListNode prev = reverseList(head.next); // go to the end
    // reconstruct the head and next relationship
    head.next.next = head;
    head.next = null;
    return prev;
}
```

*   Time complexity : $O(n)$. Assume that n*n* is the list's length, the time complexity is $O(n)$.
*   Space complexity : $O(n)$. The extra space comes from implicit stack space due to recursion. The recursion could go up to n levels deep.

#### Solution #2 Iteration

*   Store its previous element beforehand
*   Need another pointer to store the next node before changing the reference

```java
public ListNode reverseList(ListNode head){
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null){
        ListNode nextTemp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = nextTemp;
    }
    return prev;
}
```

*   Time complexity : $O(n)$. Assume that n is the list's length, the time complexity is $O(n)$.
*   Space complexity : $O(1)$.

## Search in a Binary Search Tree(Easy #700)

**Question**: You are given the `root` of a binary search tree (BST) and an integer `val`.

Find the node in the BST that the node's value equals `val` and return the subtree rooted with that node. If such a node does not exist, return `null`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/12/tree1.jpg)

```
Input: root = [4,2,7,1,3], val = 2
Output: [2,1,3]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/01/12/tree2.jpg)

```
Input: root = [4,2,7,1,3], val = 5
Output: []
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 5000]`.
-   `1 <= Node.val <= 107`
-   `root` is a binary search tree.
-   `1 <= val <= 107`

### My Solution

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
public TreeNode searchBST(TreeNode root, int val){
    if (root == null) return null;
    if (root.val == val) {
        return root;
    }
    return root.val < val ? searchBST(root.right, val) : searchBST(root.left, val);
}
```

### Standard Solution

*   A binary Search Tree is a binary tree where the key in each node
    -   is greater than any key stored in the left sub-tree,
    -   and less than any key stored in the right sub-tree.

#### Solution #1 Recursion

*   If the tree is empty `root == null` or the value to find is here `val == root.val` - return root.
*   If `val < root.val` - go to search into the left subtree `searchBST(root.left, val)`.
*   If `val > root.val` - go to search into the right subtree `searchBST(root.right, val)`.
*   Return `root`.

```java
public TreeNode searchBST(TreeNode root, int val) {
    if (root == null || val == root.val) return root;
    return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
  }
```

*   Time complexity : $\mathcal{O}(H)$, where H*H* is a tree height. That results in $\mathcal{O}(\log N)$ in the average case, and $\mathcal{O}(N)$ in the worst case.
*   Space complexity : $\mathcal{O}(H)$ to keep the recursion stack, i.e. $\mathcal{O}(\log N)$ in the average case, and $\mathcal{O}(N)$ in the worst case.

#### Solution #2 Iteration

*   The concept is the same as the recursion method

```java
public TreeNode searchBST(TreeNode root, int val){
    while(root != null && val != root.val){
        root = val < root.val ? root.left : root.right;
    }
    return root;
}
```

*   Time complexity is the same as recursion, but the space complexity is $O(1)$

## Duplicate Calculation in Recursion

*   Duplicate calculations problem that could happen with recursion

*   **Memoization**:

    *   To eliminate the duplicate calculation in the above case, as many of you would have figured out, one of the ideas would be to **store** the intermediate results in the cache so that we could reuse them later without re-calculation.

    *   Memoization is an optimization technique used primarily to **speed up** computer programs by **storing** the results of expensive function calls and returning the cached result when the same inputs occur again.

    *   **Example of Fibonacci number**

        ```java
        import java.util.HashMap;
        public class Main {
        
          HashMap<Integer, Integer> cache = new HashMap<Integer, Integer>();
          private int fib(int N) {
            if (cache.containsKey(N)) {
              return cache.get(N);
            }
            int result;
            if (N < 2) {
              result = N;
            } else {
              result = fib(N-1) + fib(N-2);
            }
            // keep the result in cache.
            cache.put(N, result);
            return result;
          }
        }
        ```

## Fibonacci Number(Easy #509)

**Question**: The **Fibonacci numbers**, commonly denoted `F(n)` form a sequence, called the **Fibonacci sequence**, such that each number is the sum of the two preceding ones, starting from `0` and `1`. That is,

```
F(0) = 0, F(1) = 1
F(n) = F(n - 1) + F(n - 2), for n > 1.
```

Given `n`, calculate `F(n)`.

**Example 1:**

```
Input: n = 2
Output: 1
Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
```

**Example 2:**

```
Input: n = 3
Output: 2
Explanation: F(3) = F(2) + F(1) = 1 + 1 = 2.
```

**Example 3:**

```
Input: n = 4
Output: 3
Explanation: F(4) = F(3) + F(2) = 2 + 1 = 3.
```

**Constraints:**

-   `0 <= n <= 30`

### My Solution

```java
Map<Integer, Integer> fibMap = new HashMap<>();
public int fib(int n) {
    int result = 0;
    if (fibMap.containsKey(n)) return fibMap.get(n);
    if (n < 2){
        result = n;
    }
    else {
        result = fib(n - 1) + fib(n - 2);
    }
    fibMap.put(n, result);
    return result;
}
```

*   If only directly use recursion, the time complexity is $O(2^N)$, space complexity is $O(N)$. The running time is extremely slow.
*   We can use a hashmap to store the values to avoid duplicate calculations.

### Standard Solution

#### Solution #1 Top-Down Approach using Memoization

*   Similar to my solution
*   Store all the pre-computed answers, then return the answer for N

```java
class Solution {
    // Creating a hash map with 0 -> 0 and 1 -> 1 pairs
    private Map<Integer, Integer> cache = new HashMap<>(Map.of(0, 0, 1, 1));

    public int fib(int N) {
        if (cache.containsKey(N)) {
            return cache.get(N);
        }
        cache.put(N, fib(N - 1) + fib(N - 2));
        return cache.get(N);
    }
}
```

*   Time complexity: $O(N)$. Each number, starting at 2 up to and including `N`, is visited, computed, and then stored for $O(1)$ access later on.
*   Space complexity: $O(N)$. The size of the stack in memory is proportional to `N`. Also, the memoization hash table is used, which occupies $O(N)$ space.

#### Solution #2 Iterative Bottom-up Approach

*   Use two pointers to take the N - 1 and N - 2 values, continuously change two pointers
*   Use for loop to calculate the result

```java
public int fib(int N){
    if (N <= 1){
        return N;
    }
    int current = 0;
    int prev1 = 0;
    int prev2 = 0;
    
    for (int i = 2; i <= N; i++){
        current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
    return current;
}
```

*   Time complexity: $O(N)$. Each value from `2` to `N` is computed once. Thus, the time it takes to find the answer is directly proportional to `N` where `N` is the Fibonacci Number we are looking to compute.

*   Space complexity: $O(1)$. This requires 1 unit of space for the integer `N` and 3 units of space to store the computed values (`current`, `prev1`, and `prev2`) for every loop iteration. The amount of space used is independent of N, so this approach uses a constant amount of space.

## Climbing Stairs(Easy #70)

**Question**: You are climbing a staircase. It takes `n` steps to reach the top.

Each time you can either climb `1` or `2` steps. In how many distinct ways can you climb to the top?

**Example 1:**

```
Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
```

**Example 2:**

```
Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
```

**Constraints:**

-   `1 <= n <= 45`

### My Solution

```java
// dynamic programming
public int climbStairs(int n) {
    // a later step has combinations of the previous possible steps sum
    if (n == 1) return n;
    int[] map = new int[n + 1];
    map[1] = 1; // 1 step has 1 method
    map[2] = 2; // 2 steps has 2 methods
    for (int i = 3; i <= n; i++){ // each step has sum of previous steps
        map[i] = map[i - 1] + map[i - 2];
    }
    return map[n];
}
```

*   In fact, it is almost the same as the fibonacci number question

### Standard Soltuion

#### Solution #1 Dynamic Programming

*   Same as my solution
*   Time complexity : $O(n)$. Single loop upto n*n*.
*   Space complexity : $O(n)$. dp array of size n is used.

#### Solution #2 Fibonacci Number

*   We can summarize the above solution and improve it to a iterative bottom-up approach

```java
public int climbStairs(int n){
    if (n == 1){
        return 1;
    }
    int first = 1;
    int second = 2;
    for (int i = 3; i <= n; i++){
        int third = first + second;
        first = second;
        second = third;
    }
    return second;
}
```

*   Time complexity : $O(n)$. Single loop upto n is required to calculate $n^{th}$ fibonacci number.
*   Space complexity : $O(1)$. Constant space is used.

## K-diff Pairs in an Array(Medium #532)

**Question**: Given an array of integers `nums` and an integer `k`, return *the number of **unique** k-diff pairs in the array*.

A **k-diff** pair is an integer pair `(nums[i], nums[j])`, where the following are true:

-   `0 <= i < j < nums.length`
-   `|nums[i] - nums[j]| == k`

**Notice** that `|val|` denotes the absolute value of `val`.

**Example 1:**

```
Input: nums = [3,1,4,1,5], k = 2
Output: 2
Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
Although we have two 1s in the input, we should only return the number of unique pairs.
```

**Example 2:**

```
Input: nums = [1,2,3,4,5], k = 1
Output: 4
Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
```

**Example 3:**

```
Input: nums = [1,3,1,5,4], k = 0
Output: 1
Explanation: There is one 0-diff pair in the array, (1, 1). 
```

**Constraints:**

-   `1 <= nums.length <= 104`
-   `-107 <= nums[i] <= 107`
-   `0 <= k <= 107`

### My Solution

*   When encouter array problem that has an order, sort it first.
*   A brute force method with two pointers

```java
public int findPairs(int[] nums, int k) {
    Arrays.sort(nums);
    int current = 0;
    int prev = Integer.MAX_VALUE;
    int pair = 0;
    for(int i = 0; i < nums.length; i++){
        current = nums[i];
        if (current == prev) continue; // if same value, continue
        for (int j = i + 1; j < nums.length; j++){ // loop to find the pair
            if (nums[j] - nums[i] == k){
                pair++; // find a pair
                prev = current;
                break;
            }
            else if (nums[j] - nums[i] > k){
                prev = current; // out of range, move to next number and record it as prev
                break;
            }
        }
    }
    return pair;
}
```

### Standard Solution

#### Solution #1 Brute Force(not recommend)

*   Similar to my solution but it should be slower since my solution will break if out of range
*   Actually no need to use absolute value since it is sorted

```java
public int findPairs(int[] nums, int k){
    Arrays.sort(nums);
    int result = 0;
    for (int i = 0; i < nums.length; i++){
        if (i > 0 && nums[i] == nums[i - 1]){ // i don't think we need to check i
            continue;
        }
        for (int j = i + 1; j < nums.length; j++){
            if (j > i + 1 && nums[j] == nums[j - 1]){
                continue;
            }
            if (Math.abs(nums[j] - nums[i]) == k){
                result++;
            }
        }
    }
    return result;
}
```

*   Time complexity : $O(N^2)$ where N is the size of `nums`. The time complexity for sorting is $O(N \log N)$ while the time complexity for going through ever pair in the `nums` is $O(N^2)$. Therefore, the final time complexity is $O(N \log N) + O(N^2) \approx O(N^2)$.
*   Space complexity : $O(N)$ where N is the size of `nums`. This space complexity is incurred by the sorting algorithm. Space complexity is bound to change depending on the sorting algorithm you use. There is no additional space required for the part with two `for` loops, apart from a single variable `result`. Therefore, the final space complexity is $O(N) + O(1) \approx O(N)$.

#### Solution #2 Two Pointers

*   Rather than checking for every possible pair, we can have two pointers to point the left number and right number that should be checked in a sorted array.
*   Take the difference between the numbers which left and right pointers point.
    *   If it is less than k, we increment the right pointer.
        -   If left and right pointers are pointing to the same number, we increment the right pointer too.
    *   If it is greater than `k`, we increment the left pointer.
    *   If it is exactly `k`, we have found our pair, we increment our placeholder `result` and increment left pointer.

```java
public int findPairs(int[] nums, int k){
    Arrays.sort(nums);
    int left = 0, right = 1;
    int result = 0;
    
    while(left < nums.length && right < nums.length){
        if (left == right || nums[right] - nums[left] < k){
            // continue to move on
            right++;
        } else if (nums[right] - nums[left] > k){
            // exceed the range, move left pointer
            left++;
        } else {
            // find the target
            left++;
            result++;
            while (left < nums.length && nums[left] == nums[left - 1]){
                left++; // loop until meet a new number
            }
        }
    }
    return result;
}
```

*   Time complexity : $O(N \log N)$ where N is the size of `nums`. The time complexity for sorting is $O(N \log N)$ while the time complexity for going through `nums` is $O(N)$. One might mistakenly think that it should be $O(N^2)$ since there is another `while` loop inside the first `while` loop. The `while` loop inside is just incrementing the pointer to skip numbers which are the same as the previous number. The animation should explain this behavior clearer. Therefore, the final time complexity is $O(N \log N) + O(N) \approx O(N \log N)$.
*   Space complexity : O(N) where N is the size of `nums`. Similar to approach 1, this space complexity is incurred by the sorting algorithm. Space complexity is bound to change depending on the sorting algorithm you use. There is no additional space required for the part where two pointers are being incremented, apart from a single variable `result`. Therefore, the final space complexity is $O(N) + O(1) \approx O(N)$.

#### Solution #3 HashMap

*   Removes the sort algorithm but use hashmap to store the counts
*   Compare the key and add to the pairs
*   If k = 0, then each pair should count

```java
public int findPairs(int[] nums, int k){
    int result = 0;
    HashMap<Integer, Integer> counter = new HashMap<>();
    for (int n : nums){
        counter.put(n, counter.getOrDefault(n, 0) + 1);
    }
    for (Map.Entry<Integer, Integer> entry : counter.entrySet()){
        int x = entry.getKey();
        int val = entry.getValue();
        if (k > 0 && counter.containsKey(x + k)){
            result++;
        } else if (k == 0 && val > 1){
            result++;
        }
    }
    return result;
}
```

*   Time complexity : $O(N)$.
    -   It takes $O(N)$ to create an initial frequency hash map and another $O(N)$ to traverse the keys of that hash map. One thing to note about is the hash key lookup. The time complexity for hash key lookup is $O(1)$ but if there are hash key collisions, the time complexity will become $O(N)$. However those cases are rare and thus, the amortized time complexity is $O(2N) \approx O(N)$.
*   Space complexity : $O(N)$
    -   We keep a table to count the frequency of each unique number in the input. In the worst case, all numbers are unique in the array. As a result, the maximum size of our table would be $O(N)$.

## Pow(x, n)(Medium #50)

**Question**: Implement [pow(x, n)](http://www.cplusplus.com/reference/valarray/pow/), which calculates `x` raised to the power `n` (i.e., `xn`).

**Example 1:**

```
Input: x = 2.00000, n = 10
Output: 1024.00000
```

**Example 2:**

```
Input: x = 2.10000, n = 3
Output: 9.26100
```

**Example 3:**

```
Input: x = 2.00000, n = -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25 
```

**Constraints:**

-   `-100.0 < x < 100.0`
-   `-231 <= n <= 231-1`
-   `-104 <= xn <= 104`

### My Solution

```java
public double myPow(double x, int n){
    if (n == 0){
        return 1;
    }
    if (n == 1){
        return x;
    }
    if (n > 0){
        return myPow(x, n - 1); // this works in theory, but encounter stackoverflow exception
    } else {
        return 1 / myPow(x, (-n) - 1);
    }
}
```

### Standard Solution

*   In this case, recursion is more space complicated since it might cause stack overflow

#### Solution #1 Brute Force

*   If $n < 0$, we can substitute $x$, $n$ with $\dfrac{1}{x}$, $-n$ to make sure $n \ge 0$. This restriction can simplify our further discussion.

```java
public double myPow(double x, int n) {
    long N = n;
    if (N < 0) {
        x = 1 / x;
        N = -N;
    }
    double ans = 1;
    for (long i = 0; i < N; i++)
        ans = ans * x;
    return ans;
}
```

*   Time complexity : $O(n)$. We will multiply `x` for `n` times.
*   Space complexity : $O(1)$. We only need one variable to store the final product of `x`.

#### Solution #2 Fast Power Algorithm Recursive

*   Using recursion, but split the n into half, it could help release half of the calculation.
*   Then we would need to consider cases for n is even or odd

```java
private double fastPow(double x, long n){
    if (n == 0){
        return 1.0;
    }
    double half = fastPow(x, n / 2);
    // split into half and half, reduce calculations
    if (n % 2 == 0){
        return half * half;
    } else {
        return half * half * x;
    }
}
public double myPow(double x, int n){
    long N = n;
    // handle the negative situation
    if (N < 0){
        x = 1 / x;
        N = -N;
    }
    return fastPow(x, N);
}
```

*   Time complexity : $O(\log n)$. Each time we apply the formula $(x ^ n) ^ 2 = x ^ {2 * n}$, n is reduced by half. Thus we need at most $O(\log n)$ computations to get the result.
*   Space complexity : $O(\log n)$. For each computation, we need to store the result of $x ^ {n / 2}$. We need to do the computation for $O(\log n)$ times, so the space complexity is $O(\log n)$.

## Super Pow(Medium #372)

**Question**: Your task is to calculate  $a^b$  mod `1337`  where `a` is a positive integer and `b` is an extremely large positive integer given in the form of an array.

**Example 1:**

```
Input: a = 2, b = [3]
Output: 8
```

**Example 2:**

```
Input: a = 2, b = [1,0]
Output: 1024
```

**Example 3:**

```
Input: a = 1, b = [4,3,3,8,5,2]
Output: 1
```

**Constraints:**

-   `1 <= a <= 231 - 1`
-   `1 <= b.length <= 2000`
-   `0 <= b[i] <= 9`
-   `b` does not contain leading zeros.

### My Solution

```java
public int superPow(int a, int[] b) {
    int ans = 1;
    int length = b.length;
    a %= 1337;
    for (int i = 0; i < length; i++){
        ans = (pow(ans,10)) * (pow(a,b[i])) % 1337; // previous ^ 10 * next exp
    }
    return ans;
}

public int pow(int a, int b){ // a and b are both positive
    if (b == 0) return 1;
    if (b == 1) return a;
    int half = pow(a, b / 2);
    int res = (half * half) % 1337; // be carefull here for exceeding limit
    if (b % 2 == 1) {
        res = (res * a) % 1337;
    }
    return res;
}
```

## Merge Two Sorted Lists(Easy #21)

**Question**: You are given the heads of two sorted linked lists `list1` and `list2`.

Merge the two lists in a one **sorted** list. The list should be made by splicing together the nodes of the first two lists.

Return *the head of the merged linked list*. 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/03/merge_ex1.jpg)

```
Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]
```

**Example 2:**

```
Input: list1 = [], list2 = []
Output: []
```

**Example 3:**

```
Input: list1 = [], list2 = [0]
Output: [0] 
```

**Constraints:**

-   The number of nodes in both lists is in the range `[0, 50]`.
-   `-100 <= Node.val <= 100`
-   Both `list1` and `list2` are sorted in **non-decreasing** order.

### My Solution

```java
// tail recursion
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    if (list1 == null) return list2;
    if (list2 == null) return list1;
    if (list1.val > list2.val){
        list2.next = mergeTwoLists(list1, list2.next);
        return list2;
    } else {
        list1.next = mergeTwoLists(list1.next, list2);
        return list1;
    }
}
```

### Standard Solution

#### Solution #1 Recursion

*   If either is initially null, no merge to perform.

*   If there is no merge to perform, simply return the null list.

*   Same as my solution part.

*   Time complexity : $O(n + m)$

    Because each recursive call increments the pointer to `l1` or `l2` by one (approaching the dangling `null` at the end of each list), there will be exactly one call to `mergeTwoLists` per element in each list. Therefore, the time complexity is linear in the combined size of the lists.

*   Space complexity : $O(n + m)$

    The first call to `mergeTwoLists` does not return until the ends of both `l1` and `l2` have been reached, so $n + m$ stack frames consume $O(n + m)$ space.

#### Solution #2 Iteration

*   Create a false prehead and give it a random value
*   Use while loop to continuously compare two list nodes value

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2){
    // maintain an unchanging refernce to node ahead of the return node.
    ListNode prehead = new ListNode(-1);
    
    ListNode prev = prehead;
    while(l1 != null && l2 != null){
        if (l1.val <= l2.val){
            prev.next = l1;
            l1 = l1.next;
        } else {
            prev.next = l2;
            l2 = l2.next;
        }
        prev = prev.next;
        
        // merge the rest of the nodes to the end
        prev.next = l1 == null ? l2 : l1;
        return prehead.next;
    }
}
```

*   Time complexity : $O(n + m)$

    Because exactly one of `l1` and `l2` is incremented on each loop iteration, the `while` loop runs for a number of iterations equal to the sum of the lengths of the two lists. All other work is constant, so the overall complexity is linear.

*   Space complexity : $O(1)$

    The iterative approach only allocates a few pointers, so it has a constant overall memory footprint.

## K-th Symbol in Grammar(Medium #779)

**Question**: We build a table of `n` rows (**1-indexed**). We start by writing `0` in the `1st` row. Now in every subsequent row, we look at the previous row and replace each occurrence of `0` with `01`, and each occurrence of `1` with `10`.

-   For example, for `n = 3`, the `1st` row is `0`, the `2nd` row is `01`, and the `3rd` row is `0110`.

Given two integer `n` and `k`, return the `kth` (**1-indexed**) symbol in the `nth` row of a table of `n` rows.

**Example 1:**

```
Input: n = 1, k = 1
Output: 0
Explanation: row 1: 0
```

**Example 2:**

```
Input: n = 2, k = 1
Output: 0
Explanation: 
row 1: 0
row 2: 01
```

**Example 3:**

```
Input: n = 2, k = 2
Output: 1
Explanation: 
row 1: 0
row 2: 01 
```

**Constraints:**

-   `1 <= n <= 30`
-   `1 <= k <= 2n - 1`

### My Solution

*   We can find a pattern that each row has two parts:
    *   First-half : the copy of last row
    *   Second-half: the complement of last row

```
1th 0
2nd 01
3rd 0110
4th 01101001
5th 0110100110010110
```

*   So we can conclude the following

```java
 public int kthGrammar(int n, int k) {
    // so the int array would be length of 2 ^ n
    int length = (int)Math.pow(2, n);
    int[] data = new int[length];
    data[0] = 0;
    data[1] = 1;
    int round = 2;
    int innerLength = 2;
    int complement = 0;
    for(int i = 2; i < length; i++){
        if (i == innerLength){
            innerLength *= 2;
        }
        complement = i - (innerLength / 2);
        data[i] = 1 - data[complement];
    }
    return data[k - 1];
}
// but it exceed the memory limit though it works
```

### Standard Solution

#### Solution #1 Recursion

```java
public int kthGrammar(int n, int k) {
    if(n==1){
        return 0;
    }
    return Integer.parseInt(kthGrammarMain(n,k));
}
public static String kthGrammarMain(int n, long k) {
    if(n==2){
        if(k%2==0){
            return "1";
        }else{
            return "0";
        }
    }

    String prevStr=kthGrammarMain(n-1,Math.round(((double)k)/2));
    if(prevStr.equals("0")){
        if(k%2==0){
            return "1";
        }else{
            return "0";
        }
    }else {
        if(k%2==0){
            return "0";
        }else{
            return "1";
        }
    }
}
```

*   We can find that the element is the complement of element in half round before
*   We use string so it could reduce space

## Unique Binary Search Trees II(Medium #95)

**Question**: Given an integer `n`, return *all the structurally unique **BST'**s (binary search trees), which has exactly* `n` *nodes of unique values from* `1` *to* `n`. Return the answer in **any order**.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/18/uniquebstn3.jpg)

```
Input: n = 3
Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]
```

**Example 2:**

```
Input: n = 1
Output: [[1]]
```

**Constraints:**

-   `1 <= n <= 8`

### Standard Solution

#### Solution #1 Recursion

*   Let's pick up number $i$ out of the sequence $1 ..n$ and use it as the root of the current tree. 
*   Then there are $i - 1$ elements available for the construction of the left subtree and $n - i$ elements available for the right subtree. As we already discussed that results in $G(i - 1)$ different left subtrees and $G(n - i)$ different right subtrees, where G is a Catalan number.

<img src="https://leetcode.com/problems/unique-binary-search-trees-ii/Figures/96_BST.png" alt="BST" style="zoom:50%;" />

*   Now let's repeat the step above for the sequence `1 ... i - 1` to construct all left subtrees, and then for the sequence `i + 1 ... n` to construct all right subtrees.
*   This way we have a root `i` and two lists for the possible left and right subtrees. The final step is to loop over both lists to link left and right subtrees to the root.

```java
public LinkedList<TreeNode> generate_trees(int start, int end){
    LinkedList<TreeNode> all_trees = new LinkedList<TreeNode>();
    if (start > end){
        all_trees.add(null);
        return all_trees;
    }
    
    // pick up a root
    for (int i = start; i <= end; i++){
        // all possible left subtrees if i is chosen to be a root
        LinkedList<TreeNode> left_trees = generate_trees(start, i - 1);
        
        // all possible right subtrees if i is chosen to be a root
        LinkedList<TreeNode> right_trees = generate_trees(i + 1, end);
        
        // connect left and right trees to the root i
        for (TreeNode l : left_trees){
            for (TreeNode r : right_trees){
                TreeNode current_tree = new TreeNode(i);
                current_tree.left = l;
                current_tree.right = r;
                all_trees.add(current_tree);
            }
        }
    }
    return all_trees;
}

public List<TreeNode> generateTrees(int n){
    if (n == 0){
        return new LinkedList<TreeNode>();
    }
    return generate_trees(1, n);
}
```

-   Time complexity : The main computations are to construct all possible trees with a given root, that is actually Catalan number $G_n$ as was discussed above. This is done `n` times, that results in time complexity $n G_n$. Catalan numbers grow as $\frac{4^n}{n^{3/2}}$ that gives the final complexity $\mathcal{O}(\frac{4^n}{n^{1/2}})$. Seems to be large but let's not forget that here we're asked to generate $G_n \sim \frac{4^n}{n^{3/2}}$ tree objects as output.
-   Space complexity : n G_n as we keep G_n trees with `n` elements each, that results in $\mathcal{O}(\frac{4^n}{n^{1/2}}) $ complexity.
