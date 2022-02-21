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