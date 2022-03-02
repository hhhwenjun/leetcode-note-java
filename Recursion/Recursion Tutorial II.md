# Recursion Tutorial II

## Divide and Conquer

*   A divide-and-conquer algorithm works by ***recursively*** breaking the problem down into ***two\*** or more subproblems of the same or related type until these subproblems become simple enough to be solved directly. Then one combines the results of subproblems to form the final solution.
*   **Divide-and-conquer manner**
    *   **Divide.** Divide the problem $S$ into a set of subproblems: $\{S_1, S_2, ... S_n\}$ where ${n \geq 2}$, *i.e.* there is usually more than one subproblem.
    *   **Conquer.** Solve each subproblem recursively. 
    *   **Combine.** Combine the results of each subproblem.

<img src="https://assets.leetcode.com/uploads/2019/04/24/d_c.png" alt="img" style="zoom:50%;" />

## Merge Sort

*   One of the classic examples of the divide-and-conquer algorithm is the merge sort algorithm.
*   There are two approaches to implement the merge sort algorithm: **top-down** or **bottom-up**. 

### Top-Down

<img src="https://assets.leetcode.com/uploads/2019/04/15/topdown_mergesort.png" alt="img" style="zoom:50%;" />

*   **Process**:
    *   In the first step, we divide the list into two sublists. (**Divide**)
    *   Then in the next step, we ***recursively*** sort the sublists in the previous step. (**Conquer**) 
    *   Finally, we merge the sorted sublists in the above step repeatedly to obtain the final list of sorted elements. (**Combine**)
*   The recursion in step (2) would reach the base case where the input list is either empty or contains a single element (see the nodes in blue from the above figure).
*   Merging two sorted lists can be done in linear time complexity ${O(N)}$, where ${N}$ is the total lengths of the two lists to merge.

<img src="https://assets.leetcode.com/uploads/2019/04/06/merge_sort_merge.gif" alt="img" style="zoom: 50%;" />

```java
import java.util.Arrays;
public class Solution {
    
    public int [] merge_sort(int [] input) {
      if (input.length <= 1) {
        return input;
      }
      int pivot = input.length / 2;
      int [] left_list = merge_sort(Arrays.copyOfRange(input, 0, pivot));
      int [] right_list = merge_sort(Arrays.copyOfRange(input, pivot, input.length));
      return merge(left_list, right_list);
    }
    
    public int [] merge(int [] left_list, int [] right_list) {
      int [] ret = new int[left_list.length + right_list.length];
      int left_cursor = 0, right_cursor = 0, ret_cursor = 0;

      while (left_cursor < left_list.length && 
             right_cursor < right_list.length) {
        if (left_list[left_cursor] < right_list[right_cursor]) {
          ret[ret_cursor++] = left_list[left_cursor++];
        } else {
          ret[ret_cursor++] = right_list[right_cursor++];
        }
      }
      // append what is remain the above lists
      while (left_cursor < left_list.length) {
        ret[ret_cursor++] = left_list[left_cursor++];
      }
      while (right_cursor < right_list.length) {
        ret[ret_cursor++] = right_list[right_cursor++];
      }  
      return ret;
    }
}
```

### Bottom-Up

*   In the **bottom-up** approach, we divide the list into sublists of a single element at the beginning. Each of the sublists is then sorted already. Then from this point on, we merge the sublists two at a time until a single list remains.

<img src="https://assets.leetcode.com/uploads/2019/04/06/mergesort.png" alt="img" style="zoom: 50%;" />

### Complexity

*   The overall **time complexity** of the merge sort algorithm is ${O(N \log{N})}$, where ${N}$ is the length of the input list. 
*   We recursively divide the input list into two sublists, until a sublist with single element remains. This dividing step computes the midpoint of each of the sublists, which takes ${O(1)}$ time. This step is repeated N times until a single element remains, therefore the total time complexity is $O(N)$.
*   As shown in the recursion tree, there are a total of N*N* elements on each level. Therefore, it takes $O({N})$ time for the merging process to complete on each level. And since there are a total of $\log{N}$ levels, the overall complexity of the merge process is $O({N \log{N}})$.
*   The ***space** **complexity*** of the merge sort algorithm is $O(N)$, where ${N}$ is the length of the input list, since we need to keep the sublists as well as the buffer to hold the merge results at each round of merge process.

## Sort an Array(Medium #912)

**Question**: Given an array of integers `nums`, sort the array in ascending order.

**Example 1:**

```
Input: nums = [5,2,3,1]
Output: [1,2,3,5]
```

**Example 2:**

```
Input: nums = [5,1,1,2,0,0]
Output: [0,0,1,1,2,5]
```

**Constraints:**

-   `1 <= nums.length <= 5 * 104`
-   `-5 * 104 <= nums[i] <= 5 * 104`

### My Solution

*   Merge sort with top-down method
*   No standard solution for this problem

```java
public int[] sortArray(int[] nums) {
    // if too short
	if (nums.length <= 1){
        return nums;
    }
    // divide and conquer, divide into two parts
    int pivot = nums.length / 2;
    int[] leftArry = sortArray(Arrays.copyOfRange(nums, 0, pivot));
    int[] rightArry = sortArry(Arrays.copyOfRange(nums, pivot, nums.length));
    return mergeSort(leftArry, rightArry);
}

public int[] mergeSort(int[] leftArry, int[] rightArry){
    int leftPos = 0;
    int rightPos = 0;
    int resPos = 0;
    int[] res = new int[leftArry.length + rightArry.length];
    while(leftPos < leftArry.length && rightPos < rightArry.length){
        if (leftArry[leftPos] < rightArry[rightPos]){
            res[resPos++] = leftArry[leftPos++];
        }
        else {
            res[resPos++] = rightArry[rightPos++];
        }
    }
    // paste all the rest digit to the res array
    while (leftPos < leftArry.length){
        res[resPos++] = leftArry[leftPos++];
    }
    while (rightPos < rightArry.length){
        res[resPos++] = rightArry[rightPos++];
    }
    return res;
}
```

## Validate Binary Search Tree(Medium #98)

**Question**: Given the `root` of a binary tree, *determine if it is a valid binary search tree (BST)*.

A **valid BST** is defined as follows:

-   The left subtree of a node contains only nodes with keys **less than** the node's key.
-   The right subtree of a node contains only nodes with keys **greater than** the node's key.
-   Both the left and right subtrees must also be binary search trees.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/12/01/tree1.jpg)

```
Input: root = [2,1,3]
Output: true
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/12/01/tree2.jpg)

```
Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 104]`.
-   `-231 <= Node.val <= 231 - 1`

### My Solution

```java
// not working for all cases
public boolean isValidBST(TreeNode root){
    if (root == null){
        return true;
    }
    return helperValid(root, root.left, 0) && helperValid(root, root.right, 1) &&
        isValidBST(root.left) && isValidBST(root.right);
}

public boolean helperValid(TreeNode parent, TreeNode root, int direction){
    // direction 0 - left, 1 - right
    if (direction == 0){
        if (root.val < parent.val){
            return true && helperValid(root.right, root.left, 0);
        }
        else {
            return false;
        }
    }
    else {
        if (root.val > parent.val){
            return true && helperValid(root.left, root.right, 1);
        }
        else {
            return false;
        }
    }
}
```

*   Cannot only compare the parent and child, also need to compare to other branches

*   This is not working for all cases because:

    ![compute](https://leetcode.com/problems/validate-binary-search-tree/Figures/98/98_not_bst_3.png)

### Standard Solution

#### Solution #1 Recursion

*   Need to define the low and high value of the cases, current node value should be between low and high

```java
public boolean validate(TreeNode root, Integer low, Integer high){
    // empty trees are valid BSTs
    if (root == null){
        return true;
    }
    // The current node's value must be between low and high.
    if ((low != null && root.val <= low) || (high != null && root.val >= high)){
        return false;
    }
    // The left and right subtree must also be valid.
    return validate(root.right, root.val, high) && validate(root.left, low, root.val);
}
public boolean isValidBST(TreeNode root){
    return validate(root, null, null);
}
```

-   Time complexity: $\mathcal{O}(N)$ since we visit each node exactly once.
-   Space complexity: $\mathcal{O}(N)$ since we keep up to the entire tree.

#### Solution #2 Iterative Traversal with Valid Range

*   Same solution as the first one, but with the help of an explicit stack. DFS would be better than BFS since it works faster here.

```java
private Deque<TreeNode> stack = new LinkedList();
private Deque<Integer> upperLimits = new LinkedList();
private Deque<Integer> lowerLimits = new LinkedList();

public void update(TreeNode root, Integer low, Integer high){
    stack.add(root);
    lowerLimits.add(low);
    upperLimits.add(high);
}

public boolean isValidBST(TreeNode root){
    Integer low = null, high = null, val;
    update(root, low, high);
    
    while(!stack.isEmpty()){
        root = stack.poll();
        low = lowerLimits.poll();
        high = upperLimits.poll();
        
        if (root == null) continue;
        val = root.val;
        if (low != null && val <= low){
            return false;
        }
        if (high != null && val >= high){
            return false;
        }
        update(root.right, val, high);
        update(root.left, low, val);
    }
    return true;
}
```

*   Time complexity: $\mathcal{O}(N)$ since we visit each node exactly once.
*   Space complexity: $\mathcal{O}(N)$ since we keep up to the entire tree.

#### Solution #3 Inorder Traversal

```java
class Solution {
    // We use Integer instead of int as it supports a null value.
    private Integer prev;

    public boolean isValidBST(TreeNode root) {
        prev = null;
        return inorder(root);
    }

    private boolean inorder(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!inorder(root.left)) {
            return false;
        }
        if (prev != null && root.val <= prev) {
            return false;
        }
        prev = root.val;
        return inorder(root.right);
    }
}
```

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Integer prev = null;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // If next element in inorder traversal
            // is smaller than the previous one
            // that's not BST.
            if (prev != null && root.val <= prev) {
                return false;
            }
            prev = root.val;
            root = root.right;
        }
        return true;
    }
}
```

-   Time complexity: $\mathcal{O}(N)$ in the worst case when the tree is BST or the "bad" element is the rightmost leaf.
-   Space complexity: $\mathcal{O}(N)$ to keep `stack`.

## Search a 2D Matrix II(Medium #240)

**Question**: Write an efficient algorithm that searches for a value `target` in an `m x n` integer matrix `matrix`. This matrix has the following properties:

-   Integers in each row are sorted in ascending from left to right.
-   Integers in each column are sorted in ascending from top to bottom.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/11/24/searchgrid2.jpg" alt="img" style="zoom:50%;" />

```
Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
Output: true
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/11/24/searchgrid.jpg" alt="img" style="zoom:50%;" />

```
Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
Output: false
```

**Constraints:**

-   `m == matrix.length`
-   `n == matrix[i].length`
-   `1 <= n, m <= 300`
-   `-109 <= matrix[i][j] <= 109`
-   All the integers in each row are **sorted** in ascending order.
-   All the integers in each column are **sorted** in ascending order.
-   `-109 <= target <= 109`

### My Solution

```java
// does not work for all the situations
public boolean searchMatrix(int[][] matrix, int target){
    int m = matrix.length;
    int n = matrix[0].length;
    int largest = matrix[m - 1][n - 1];
    while(target < largest && m >= 0 && n >= 0){
        largest = matrix[m - 1][n - 1];
        m -= 1;
        n -= 1;
    }
    return helper(matrix, target, m, n, 0) || helper(matrix, target, m, n, 1);
}

public boolean helper(int[][] matrix, int target, int m, int n, int direction){
    // horizonal
    if (direction == 0){
        while(n >= 0){
            if (target == matrix[m][n]){
                return true;
            }
            n-= 1;
        }
        return false;
    }
    // direction = 1, vertical
    else {
        while(m >= 0){
            if (target == matrix[m][n]){
                return true;
            }
            m-= 1;
        }
        return false; 
    }
}
```

### Standard Solution

*   You can also use brute force to find the solution but not recommended

#### Solution #1 Search Space Reduction

*   More like a dynamic programming solution, start from the bottom left, then go up

```java
public boolean searchMatrix(int[][] matrix, int target){
    // start our pointer in the bottom-left
    int row = matrix.length - 1;
    int col = 0;
    
    while (row >= 0 && col < matrix[0].length){
        if (matrix[row][col] > target){
            row--;
        } else if (matrix[row][col] < target){
            col++;
        } else {
            return true;
        }
    }
    return false;
}
```

*   Time complexity: $O(n+m)$
    *   The key to the time complexity analysis is noticing that, on every iteration (during which we do not return `true`) either `row` or `col` is decremented/incremented exactly once. Because `row` can only be decremented m*m* times and `col` can only be incremented n times before causing the `while` loop to terminate, the loop cannot run for more than n+m iterations. Because all other work is constant, the overall time complexity is linear in the sum of the dimensions of the matrix.
*   Space complexity: $O(1)$
    *   Because this approach only manipulates a few pointers, its memory footprint is constant

#### Solution #2 Binary Search

*   Loop through the shortest dimension(col or row) and apply binary search on row or column

```java
private boolean binarySearch(int[][] matrix, int target, int start, boolean vertical) {
    int lo = start;
    int hi = vertical ? matrix[0].length-1 : matrix.length-1;

    while (hi >= lo) {
        int mid = (lo + hi)/2;
        if (vertical) { // searching a column
            if (matrix[start][mid] < target) {
                lo = mid + 1;
            } else if (matrix[start][mid] > target) {
                hi = mid - 1;
            } else {
                return true;
            }
        } else { // searching a row
            if (matrix[mid][start] < target) {
                lo = mid + 1;
            } else if (matrix[mid][start] > target) {
                hi = mid - 1;
            } else {
                return true;
            }
        }
    }

    return false;
}
public boolean searchMatrix(int[][] matrix, int target) {
    // an empty matrix obviously does not contain `target`
    if (matrix == null || matrix.length == 0) {
        return false;
    }

    // iterate over matrix diagonals
    int shorterDim = Math.min(matrix.length, matrix[0].length);
    for (int i = 0; i < shorterDim; i++) {
        boolean verticalFound = binarySearch(matrix, target, i, true);
        boolean horizontalFound = binarySearch(matrix, target, i, false);
        if (verticalFound || horizontalFound) {
            return true;
        }
    }

    return false; 
}
```

*   Time complexity : $\mathcal{O}(\log(n!))$

*   Space complexity : $\mathcal{O}(1)$

    Because our binary search implementation does not literally slice out copies of rows and columns from `matrix`, we can avoid allocating greater-than-constant memory.

## Quick Sort

Following the pseudocode template of the divide-and-conquer algorithm, as we presented before, the quick sort algorithm can be implemented in three steps, namely dividing the problem, solving the subproblems, and combining the results of subproblems.

*   First, it selects a value from the list, which serves as a ***pivot*** value to divide the list into two sublists. One sublist contains all the values that are less than the pivot value, while the other sublist contains the values that are greater than or equal to the pivot value. This process is also called ***partitioning\***. The strategy of choosing a pivot value can vary. Typically, one can choose the first element in the list as the pivot, or randomly pick an element from the list.

*   After the partitioning process, the original list is then reduced into two smaller sublists. We then ***recursively*** sort the two sublists.

    *   Either when the input list is empty or the empty list contains only a single element. In either case, the input list can be considered as sorted already.

*   After the partitioning process, we are sure that all elements in one sublist are less or equal than any element in another sublist. Therefore, we can simply ***concatenate*** the two sorted sublists that we obtain in step [2] to obtain the final sorted list. 

*   **Sample Implementation**

    ```java
    public class Solution {
    
      public void quickSort(int [] lst) {
       /* Sorts an array in the ascending order in O(n log n) time */
        int n = lst.length;
        qSort(lst, 0, n - 1);
      }
    
      private void qSort(int [] lst, int lo, int hi) {
        if (lo < hi) {
          int p = partition(lst, lo, hi);
          qSort(lst, lo, p - 1);
          qSort(lst, p + 1, hi);
        }
      }
    
      private int partition(int [] lst, int lo, int hi) {
        /*
          Picks the last element hi as a pivot
          and returns the index of pivot value in the sorted array */
        int pivot = lst[hi];
        int i = lo;
        for (int j = lo; j < hi; ++j) {
          if (lst[j] < pivot) {
            int tmp = lst[i];
            lst[i] = lst[j];
            lst[j] = tmp;
            i++;
          }
        }
        int tmp = lst[i];
        lst[i] = lst[hi];
        lst[hi] = tmp;
        return i;
      }
    
    }
    ```

    *   Depending on the pivot values, the time complexity of the quick sort algorithm can vary from $O\big(N \log_2{N}\big)$ in the best case and $O(N^2)$ in the worst case, with N as the length of the list.

## Unfold Recursion

*   We illustrate how to convert a recursion algorithm to a non-recursion one, *i.e.* ***unfold*** the recursion.

*   The recursion often incurs additional memory consumption on the system stack, which is a limited resource for each program. If not used properly, the recursion algorithm could lead to StackOverflow.

*   Along with the additional memory consumption, the recursion could impose at least the additional cost of function calls, and in a worse case duplicate calculation.

*   **Example**: 

    ```
    Given two binary trees, write a function to check if they are the same or not.
    
    Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
    ```

    ```java
    // recursive solution
    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
    class Solution {
      public boolean isSameTree(TreeNode p, TreeNode q) {
        // p and q are both null
        if (p == null && q == null) return true;
        // one of p and q is null
        if (q == null || p == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.right, q.right) &&
                isSameTree(p.left, q.left);
      }
    }
    ```

    ```java
    class Solution {
      public boolean check(TreeNode p, TreeNode q) {
        // p and q are null
        if (p == null && q == null) return true;
        // one of p and q is null
        if (q == null || p == null) return false;
        if (p.val != q.val) return false;
        return true;
      }
    
      public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (!check(p, q)) return false;
        // init deques
        ArrayDeque<TreeNode> deqP = new ArrayDeque<TreeNode>();
        ArrayDeque<TreeNode> deqQ = new ArrayDeque<TreeNode>();
        deqP.addLast(p);
        deqQ.addLast(q);
    
        while (!deqP.isEmpty()) {
          p = deqP.removeFirst();
          q = deqQ.removeFirst();
    
          if (!check(p, q)) return false;
          if (p != null) {
            // in Java nulls are not allowed in Deque
            if (!check(p.left, q.left)) return false;
            if (p.left != null) {
              deqP.addLast(p.left);
              deqQ.addLast(q.left);
            }
            if (!check(p.right, q.right)) return false;
            if (p.right != null) {
              deqP.addLast(p.right);
              deqQ.addLast(q.right);
            }
          }
        }
        return true;
      }
    }
    ```

    *   To convert a recursion approach to an iteration one, we could perform the following two steps:
        *   We use a stack or queue data structure within the function, to replace the role of the system call stack. At each occurrence of recursion, we simply push the parameters as a new element into the data structure that we created, instead of invoking a recursion.
        *   In addition, we create a loop over the data structure that we created before. The chain invocation of recursion would then be replaced with the iteration within the loop.

## Same Tree(Easy #100)

**Question**: Given the roots of two binary trees `p` and `q`, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/12/20/ex1.jpg)

```
Input: p = [1,2,3], q = [1,2,3]
Output: true
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/12/20/ex2.jpg)

```
Input: p = [1,2], q = [1,null,2]
Output: false
```

**Example 3:**

![img](https://assets.leetcode.com/uploads/2020/12/20/ex3.jpg)

```
Input: p = [1,2,1], q = [1,1,2]
Output: false
```

**Constraints:**

-   The number of nodes in both trees is in the range `[0, 100]`.
-   `-104 <= Node.val <= 104`

### My Solution

```java
public boolean isSameTree(TreeNode p, TreeNode q){
    if (p == null && q == null){
        return true;
    }
    if (p == null || q == null){
        return false;
    }
    return q.val == p.val && 
        isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
```

*   Same as the above unfold recursion content.

### Standard Solution

#### Solution #1 Recursion

*   Same as my solution
*   Time complexity: $\mathcal{O}(N)$, where N is the number of nodes in the tree since one visits each node exactly once.
*   Space complexity: $\mathcal{O}(\log(N))$ in the best case of the completely balanced tree and $\mathcal{O}(N)$ in the worst case of the completely unbalanced tree, to keep a recursion stack.

#### Solution #2 Iteration

*   Same as above content
*   Time and space complexity same as the above solution.

## Generate Parentheses(Medium #22)

**Question**: Given `n` pairs of parentheses, write a function to *generate all combinations of well-formed parentheses*.

**Example 1:**

```
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
```

**Example 2:**

```
Input: n = 1
Output: ["()"]
```

**Constraints:**

-   `1 <= n <= 8`

### My Solution

*   Backtracking is suitable for the case

```java
private List<String> res;
private int n;
public List<String> generateParenthesis(int n){
    this.n = n;
    res = new ArrayList<String>();
    backtrack(new StringBuilder(), 0, 0);
    return res;
}
public void backtrack(StringBuilder string, int open, int close){
    if (start == n * 2){// reach the certain number
        res.add(string.toString());
    }
    if (open < n){
        string.append("(");
        backtrack(string, open+1, close);
        string.deleteCharAt(string.length() - 1);
    }
    if (close < open){
        string.append(")");
        backtrack(string, open, close+1);
        string.deleteCharAt(string.length() - 1);
    }
}
```

### Standard Solution

#### Solution #1 Backtracking

*   Same as my solution
*   Time Complexity : $O(\dfrac{4^n}{\sqrt{n}})$. Each valid sequence has at most `n` steps during the backtracking procedure.
*   Space Complexity : $O(\dfrac{4^n}{\sqrt{n}})$ as described above, and using $O(n)$ space to store the sequence.

#### Solution #2 Closure Number

*   For each closure number `c`, we know the starting and ending brackets must be at index `0` and `2*c + 1`. Then, the `2*c` elements between must be a valid sequence, plus the rest of the elements must be a valid sequence.

```java
public List<String> generateParenthesis(int n) {
    List<String> ans = new ArrayList();
    if (n == 0) {
        ans.add("");
    } else {
        for (int c = 0; c < n; ++c)
            for (String left: generateParenthesis(c))
                for (String right: generateParenthesis(n-1-c))
                    ans.add("(" + left + ")" + right);
    }
    return ans;
}
```

*   Time and space complexity is the same as the last solution.

## Beyond Recursion

*   A divide-and-conquer algorithm works by recursively breaking the problem down into two or more subproblems of the same or related type until these subproblems become simple enough to be solved directly. Then one combines the results of subproblems to form the final solution.

    <img src="https://assets.leetcode.com/uploads/2019/04/15/divide_and_conquer.png" alt="img" style="zoom:33%;" />

*   Backtracking is a general algorithm for finding all (or some) solutions to some computational problems (notably constraint satisfaction problems), which incrementally builds candidates to the solution and abandons a candidate ("backtracks") as soon as it determines that the candidate cannot leads to a valid solution.

    <img src="https://assets.leetcode.com/uploads/2019/04/15/backtracking.png" alt="img" style="zoom:33%;" />

*   Divide and Conquer vs. Backtracking
    *   Often the case, the divide-and-conquer problem has a ***sole*** solution, while the backtracking problem has unknown number of solutions. 
    *   Each step in the divide-and-conquer problem is ***indispensable*** to build the final solution, while many steps in backtracking problem might not be useful to build the solution, but serve as ***atttempts*** to search for the potential solutions.
    *   When building the solution in the divide-and-conquer algorithm, we have a ***clear and predefined*** path, though there might be several different manners to build the path. While in the backtracking problems, one does not know in advance the ***exact path*** to the solution.

## Convert Binary Search Tree to Sorted Doubly Linked List(Medium #426)

**Question**: Convert a **Binary Search Tree** to a sorted **Circular Doubly-Linked List** in place.

You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.

We want to do the transformation **in place**. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2018/10/12/bstdlloriginalbst.png" alt="img" style="zoom: 50%;" />

<img src="https://assets.leetcode.com/uploads/2018/10/12/bstdllreturndll.png" alt="img" style="zoom:50%;" />

```
Input: root = [4,2,5,1,3]
Output: [1,2,3,4,5]

Explanation: The figure below shows the transformed BST. The solid line indicates the successor relationship, while the dashed line means the predecessor relationship.
```

**Example 2:**

```
Input: root = [2,1,3]
Output: [1,2,3]
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 2000]`.
-   `-1000 <= Node.val <= 1000`
-   All the values of the tree are **unique**.

### Standard Solution

#### Solution #1 Recursion

*   Makes it an inorder traversal from BFS 

*   Use recursion for linking the nodes

*   Link the previous node with the current one, track the last node, and track the first node

    <img src="https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/Figures/426/process.png" alt="postorder" style="zoom:50%;" />

*   **Algorithm**: 

    *   Initiate the `first` and the `last` nodes as nulls.
    *   Call the standard inorder recursion `helper(root)` :
        -   If node is not null :
            -   Call the recursion for the left subtree `helper(node.left)`.
            -   If the `last` node is not null, link the `last` and the current `node` nodes.
            -   Else initiate the `first` node.
            -   Mark the current node as the last one : `last = node`.
            -   Call the recursion for the right subtree `helper(node.right)`.
    *   Link the first and the last nodes to close DLL ring and then return the `first` node.

```java
class Solution {
  // the smallest (first) and the largest (last) nodes
  Node first = null;
  Node last = null;

  public void helper(Node node) {
    if (node != null) {
      // left
      helper(node.left);
      // node 
      if (last != null) {
        // link the previous node (last)
        // with the current one (node)
        last.right = node;
        node.left = last;
      }
      else {
        // keep the smallest node
        // to close DLL later on
        first = node;
      }
      last = node;
      // right
      helper(node.right);
    }
  }
  public Node treeToDoublyList(Node root) {
    if (root == null) return null;
    helper(root);
    // close DLL
    last.right = first;
    first.left = last;
    return first;
  }
}
```

*   Time complexity : $\mathcal{O}(N)$ since each node is processed exactly once.
*   Space complexity : $\mathcal{O}(N)$. We have to keep a recursion stack of the size of the tree height, which is $\mathcal{O}(\log N)$ for the best case of the completely balanced tree and $\mathcal{O}(N)$ for the worst case of a completely unbalanced tree.

## Largest Rectangle in Histogram(Hard #84)

**Question**: Given an array of integers `heights` representing the histogram's bar height where the width of each bar is `1`, return *the area of the largest rectangle in the histogram*.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/01/04/histogram.jpg" alt="img" style="zoom:50%;" />

```
Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/01/04/histogram-1.jpg" alt="img" style="zoom:50%;" />

```
Input: heights = [2,4]
Output: 4
```

**Constraints:**

-   `1 <= heights.length <= 105`
-   `0 <= heights[i] <= 104`

### My Solution

*   Fail for some of the test cases

```java
// fail some test cases
public int largestRectangleArea(int[] heights) {
    int max = 0;
    int area = 0;
    int slow = 0, fast = 0;
    int minLength = Integer.MAX_VALUE;
    int tempMin = Integer.MAX_VALUE;
    while(slow <= fast && fast <= heights.length - 1){
        tempMin = Math.min(heights[slow], heights[fast]);
        minLength = Math.min(tempMin, minLength);
        area = ((fast - slow) + 1) * minLength;
        max = Math.max(area, max);
        if (heights[slow] < heights[fast]){

            if (minLength == heights[slow]){
                minLength = Integer.MAX_VALUE;
            }
            slow++;
        } else if (heights[slow] >= heights[fast] || slow == fast){
            fast++;
        }
    }
    return max;
}
```

### Standard Solution

#### Solution #1 Brute Force(Exceed Limited Time)

*   Brute force method with nested loops

```java
public int largestRectangleArea(int[] heights){
    int maxArea = 0;
    int length = heights.length;
    for (int i = 0; i < length; i++){
        int minHeight = Integer.MAX_VALUE;
        for (int j = i; j < length; j++){
            minHeight = Math.min(minHeight, heights[j]);
            maxArea = Math.max(maxArea, minHeight * (j - 1 + 1));
        }
    }
    return maxArea;
}
```

*   Time complexity: $O(n^2)$. Every possible pair is considered
*   Space complexity: $O(1)$. No extra space is used.

#### Solution #2 Divide and Conquer Approach(Exceed Limited Time)

*   Exceed the limited time but recommended to learn

    <img src="https://leetcode.com/media/original_images/84_Largest_Rectangle2.PNG" alt="Divide and Conquer" style="zoom: 67%;" />

*   **Algorithm**:

    *   The widest possible rectangle with a height equal to the height of the shortest bar.
    *   The largest rectangle is confined to the left of the shortest bar(subproblem).
    *   The largest rectangle is confined to the right of the shortest bar(subproblem).

```java
public int calculateArea(int[] heights, int start, int end){
    if (start > end){
        return 0;
    }
    int minindex = start;
    for (int i = start; i <= end; i++){
        if (heights[minindex] > heights[i]){
            minindex = i;
        }
    }
    return Math.max(heights[minindex] * (end - start + 1),
                   Math.max(calculateArea(heights, start, minindex - 1),
                           calculateArea(heights, minindex + 1, end)));
}
public int largestRectangleArea(int[] heights){
    return calculateArea(heights, 0, heights.length - 1);
}
```

-   Time complexity:

    Average Case: $O\big(n \log n\big)$.

    Worst Case: $O(n^2)$. If the numbers in the array are sorted, we don't gain the advantage of divide and conquer.

-   Space complexity: $O(n)$. Recursion with worst-case depth n.

#### Solution #3 Stack

*   Use a stack to store the width index information
*   Each time we encounter a height of $i + 1$ smaller than $i$, it helps us determine the max area before the $i + 1$.

```java
public int largestRectangleArea(int[] heights){
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1);
    int length = heights.length;
    int maxArea = 0;
    for (int i = 0; i < length; i++){
        while((stack.peek() != -1)
             && (heights[stack.peek()] >= heights[i])){
            int currentHeight = heights[stack.pop()];
            int currentWidth = i - stack.peek() - 1;
            maxArea = Math.max(maxArea, currentHeight * currentWidth);
        }
        stack.push(i);
    }
    while (stack.peek() != -1){
        int currentHeight = heights[stack.pop()];
        int currentWidth = length - stack.peek() - 1;
        maxArea = Math.max(maxArea, currentHeight * currentWidth);
    }
    return maxArea;
}
```

-   Time complexity: $O(n)$. n numbers are pushed and popped.
-   Space complexity: $O(n)$. Stack is used.

## The Skyline Problem(Hard #218)

**Question**: A city's **skyline** is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the locations and heights of all the buildings, return *the **skyline** formed by these buildings collectively*.

The geometric information of each building is given in the array `buildings` where `buildings[i] = [lefti, righti, heighti]`:

-   `lefti` is the x coordinate of the left edge of the `ith` building.
-   `righti` is the x coordinate of the right edge of the `ith` building.
-   `heighti` is the height of the `ith` building.

You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height `0`.

The **skyline** should be represented as a list of "key points" **sorted by their x-coordinate** in the form `[[x1,y1],[x2,y2],...]`. Each key point is the left endpoint of some horizontal segment in the skyline except the last point in the list, which always has a y-coordinate `0` and is used to mark the skyline's termination where the rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of the skyline's contour.

**Note:** There must be no consecutive horizontal lines of equal height in the output skyline. For instance, `[...,[2 3],[4 5],[7 5],[11 5],[12 7],...]` is not acceptable; the three lines of height 5 should be merged into one in the final output as such: `[...,[2 3],[4 5],[12 7],...]`

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/12/01/merged.jpg" alt="img" style="zoom: 33%;" />

```
Input: buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
Output: [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
Explanation:
Figure A shows the buildings of the input.
Figure B shows the skyline formed by those buildings. The red points in figure B represent the key points in the output list.
```

**Example 2:**

```
Input: buildings = [[0,2,3],[2,5,3]]
Output: [[0,3],[5,0]]
```

**Constraints:**

-   `1 <= buildings.length <= 104`
-   `0 <= lefti < righti <= 231 - 1`
-   `1 <= heighti <= 231 - 1`
-   `buildings` is sorted by `lefti` in non-decreasing order.

### Standard Solution

#### Solution #1 Divide and conquer

*   Divide the problem to only two triangles overlaps, only record top-left and bottom-right points
*   Then merge the solution together
*   Another method is to use heap
*   (Not quite understand this problem, need to have second attempt)

```java
/**
Divide and conquer algorithm to solve skyline problem,
similar with the merge sort algorithm
**/
public List<List<Integer>> getSkyline(int[][] buildings){
    int n = buildings.length;
    List<List<Integer>> output = new ArrayList<>();
    
    // the base cases
    if (n == 0) return output;
    if (n == 1) {
        int xStart = buildings[0][0];
        int xEnd = buildings[0][1];
        int h = buildings[0][2];
        
        output.add(new ArrayList<Integer>(){{add(xStart); add(y);}});
        output.add(new ArrayList<Integer>(){{add(xEnd); add(0);}});
        return output;
    }
    
    // if there is more than one building, recursively
    // divide the input into two subproblems
    List<List<Integer>> leftSkyline, rightSkyline;
    leftSkyline = getSkyline(Arrays.copyOfRange(buildings, 0, n / 2));
    rightSkyline = getSkyline(Arrays.copyOfRange(buildings, n / 2, n));
    
    // merge the results of subproblem together
    return mergeSkylines(leftSkyline, rightSkyline);
}

/** Merge two skylines together**/
public List<List<Integer>> mergeSkylines(List<List<Integer>> left, List<List<Integer>> right){
    int nL = left.size(), nR = right.size();
    int pL = 0, pR = 0; 
    int currY = 0, leftY = 0, rightY = 0;
    int x, maxY;
    List<List<Integer>> output = new ArrayList<List<Integer>>();
    
    // while we are in the region where both skylines are present
    while((pL < nL) && (pR < nR)){
        List<Integer> pointL = left.get(pL);
        List<Integer> pointR = right.get(pR);
        // pick up the smallest x
        if (pointL.get(0) < pointR.get(0)){
            x = pointL.get(0);
            leftY = pointL.get(1);
            pL++;
        }
        else {
            x = pointR.get(0);
            rightY = pointR.get(1);
            pR++;
        }
        // max height (i.e. y) between both skylines
        maxY = Math.max(leftY, rightY);
        // update output if there is a skyline change
        if (currY != maxY){
            updateOutput(output, x, maxY);
            currY = maxY;
        }
    }
    // there is only left skyline
    appendSkyline(output, left, pL, nL, currY);
    // there is only right skyline
    appendSkyline(output, right, pR, nR, currY);
    return output;
}

/** update the final output with new element**/
public void updateOutput(List<List<Integer>> output, int x, int y){
    // if skyline change is not vertical - add the new point
    if (output.isEmpty() || output.get(output.size() - 1).get(0) != x){
        output.add(new ArrayList<Integer>(){{add(x); add(y);}});
        // if skyline change is vertical - update the last point
    }
    else {
        output.get(output.size() - 1).set(1, y);
    }
}

/** 
* append the rest of the skyline elements with indice (p, n) 
* to the final output
**/
public void appendSkyline(List<List<Integer>> output, List<List<Integer>> skyline,
                         int p, int n, int currY){
    while (p < n){
        List<Integer> point = skyline.get(p);
        int x = point.get(0);
        int y = point.get(1);
        p++;
        
        // update output
        // if there is a skyline change
        if (currY != y){
            updateOutput(output, x, y);
            currY = y;
        }
    }
}
```

-   Time complexity : $\mathcal{O}(N \log N)$, where N is number of buildings. The problem is an example of [Master Theorem case II](https://en.wikipedia.org/wiki/Master_theorem_(analysis_of_algorithms)#Case_2_example) : $T(N) = 2 T(\frac{N}{2}) + 2N$ that results in $\mathcal{O}(N \log N)$ time complexity.
-   Space complexity : $\mathcal{O}(N)$.
    -   We use the `output` variable to keep track of the results.