# BST Tutorial

## Definition of BST

A `binary search tree` (BST), a special form of a binary tree, satisfies the binary search property:

1.  The value in each node must be `greater than` (or equal to) any values stored in its left subtree.
2.  The value in each node must be `less than` (or equal to) any values stored in its right subtree.

*   The inorder traversal is the most frequently used traversal method of a BST.

## Inorder Successor in BST(Medium #285)

**Question**: Given the `root` of a binary search tree and a node `p` in it, return *the in-order successor of that node in the BST*. If the given node has no in-order successor in the tree, return `null`.

The successor of a node `p` is the node with the smallest key greater than `p.val`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/01/23/285_example_1.PNG)

```
Input: root = [2,1,3], p = 1
Output: 2
Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2019/01/23/285_example_2.PNG)

```
Input: root = [5,3,6,2,4,null,null,1], p = 6
Output: null
Explanation: There is no in-order successor of the current node, so the answer is null.
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 104]`.
-   `-105 <= Node.val <= 105`
-   All Nodes will have unique values.

### My Solution

```java
// not a work solution
private TreeNode target;
private TreeNode successor = null;
private TreeNode prev;
public TreeNode inorderSuccessor(TreeNode root, TreeNode p){
    if (root == null || p == null){
        return null;
    }
    this.target = p;
    inorder(root, null);
    return successor;
}

public void inorder(TreeNode root, TreeNode prev){
    if (root == null){
        return;
    }
    inorder(root.left);
    if (prev != null && prev.val == target.val){
        successor = root;
    }
    prev = root;
    inorder(root.right);
}
```

### Standard Solution

#### Solution #1 Using BST properties

*   Utilizing the BST properties of the tree

    <img src="https://leetcode.com/problems/inorder-successor-in-bst/Figures/285/img8.png" alt="BST property depiction" style="zoom:50%;" />

```java
public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    TreeNode successor = null;
    while(root != null){
        if (p.val >= root.val){
            root = root.right;
        }
        else {
            successor = root;
            root = root.left;
        }
    }
    return successor;
}
```

*   Time Complexity: $O(N)$ since we might end up encountering a skewed tree and in that case, we will just be discarding one node at a time. For a balanced binary search tree, however, the time complexity will be $O(\text{log}N)$ which is what we usually find in practice.
*   Space Complexity: $O(1)$ since we don't use recursion or any other data structures for getting our successor.

