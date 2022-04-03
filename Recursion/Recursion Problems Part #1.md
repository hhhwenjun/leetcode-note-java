# Recursion Problems Part #1

## Count Complete Tree Nodes(Medium #222)

**Question**: Given the `root` of a **complete** binary tree, return the number of the nodes in the tree.

According to **[Wikipedia](http://en.wikipedia.org/wiki/Binary_tree#Types_of_binary_trees)**, every level, except possibly the last, is completely filled in a complete binary tree, and all nodes in the last level are as far left as possible. It can have between `1` and `2h` nodes inclusive at the last level `h`.

Design an algorithm that runs in less than `O(n)` time complexity.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/14/complete.jpg)

```
Input: root = [1,2,3,4,5,6]
Output: 6
```

**Example 2:**

```
Input: root = []
Output: 0
```

**Example 3:**

```
Input: root = [1]
Output: 1
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 5 * 104]`.
-   `0 <= Node.val <= 5 * 104`
-   The tree is guaranteed to be **complete**.

### My Solution

*   Recursion method to do the counting

```java
public int countNodes(TreeNode root) {
    return count(root);
}
public int count(TreeNode root){
    if (root == null){
        return 0;
    }
    return 1 + count(root.left) + count(root.right);
}
```

### Standard Solution

#### Solution #1 Linear Time

*   Same as my solution, but a more simplified version.

```java
public int countNodes(TreeNode root){
    return root != null ? 1 + countNodes(root.right) + countNodes(root.left) : 0;
}
```

-   Time complexity: $\mathcal{O}(N)$.
-   Space complexity: $\mathcal{O}(d) = \mathcal{O}(\log N)$ to keep the recursion stack, where d is a tree depth.