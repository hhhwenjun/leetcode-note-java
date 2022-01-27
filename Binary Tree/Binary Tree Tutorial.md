# Binary Tree Tutorial 

Materials refer to: https://leetcode.com/explore/learn/card/recursion-i/

# Introduction to Binary Tree

*   From graph view, a tree can also be defined as a directed acyclic graph which has `N nodes` and `N-1 edges`.
*   As the name suggests, a binary tree is a tree data structure in which each node has `at most two children`, which are referred to as the left child and the right child.

## Traverse a Tree

*   **Pre-order Traversal**

    *   Pre-order traversal is to visit the root first. Then traverse the left subtree. Finally, traverse the right subtree.

*   **In-order Traversal**

    *   In-order traversal is to traverse the left subtree first. Then visit the root. Finally, traverse the right subtree.
    *   Typically, for `binary search tree`, we can retrieve all the data in sorted order using in-order traversal. 

*   **Post-order Traversal**

    *   Post-order traversal is to traverse the left subtree first. Then traverse the right subtree. Finally, visit the root.
    *   It is worth noting that when you delete nodes in a tree, **deletion process will be in post-order**. That is to say, when you delete a node, you will delete its left child and its right child before you delete the node itself.
    *   Post-order is widely use in mathematical expression. It is easier to write a program to parse a post-order expression. 

    <img src="https://leetcode.com/explore/learn/card/data-structure-tree/134/traverse-a-tree/Figures/binary_tree/mathematical_expression.png" alt="img" style="zoom:33%;" />

    *   If you handle this tree in postorder, you can easily handle the expression using a stack. 
    *   Each time when you meet a operator, you can just **pop 2 elements from the stack**, **calculate the result and push the result back into the stack**.

*   Frequently come with **recursion and/or iteration** for code solution.

## Binary Tree Preorder Traversal(Easy #144)

**Question**: Given the `root` of a binary tree, return *the preorder traversal of its nodes' values*.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/09/15/inorder_1.jpg" alt="img" style="zoom: 50%;" />

```
Input: root = [1,null,2,3]
Output: [1,2,3]
```

**Example 2:**

```
Input: root = []
Output: []
```

**Example 3:**

```
Input: root = [1]
Output: [1]
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 100]`.
-   `-100 <= Node.val <= 100`

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
 public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> nodes = new ArrayList<>();
    preorder(root, nodes);
    return nodes;
}

public void preorder(TreeNode root, List<Integer> nodes){
    if (root == null){
        return;
    } else {
        nodes.add(root.val);
        preorder(root.left, nodes);
        preorder(root.right, nodes);
    }
}
```

*   Recursion method:
    *   Add the root first
    *   Traverse the left tree
    *   Traverse the right tree
*   The traversal method **return void**, separate it from the main method
*   While traverse, bring in the tree with the nodes record
*   Always use `if-else`, `if` for the base case and `return`

### Standard Solution

*   There are two general strategies to traverse a tree:

    -   ***Breadth First Search* (`BFS`)**

        We scan through the tree level by level, following the order of height, from top to bottom. The nodes on higher level would be visited before the ones with lower levels.

    -   ***Depth First Search* (`DFS`)**

        In this strategy, we adopt the `depth` as the priority, so that one would start from a root and reach all the way down to certain leaf, and then back to root to reach another branch.

        The DFS strategy can further be distinguished as `preorder`, `inorder`, and `postorder` depending on the relative order among the root node, left node and right node.

![postorder](https://leetcode.com/problems/binary-tree-preorder-traversal/Figures/145_transverse.png)

#### Solution #1 Recursion

*   Same as my solution
*   Time complexity: $O(N)$. N is the number of nodes, each node is traversed once.
*   Space complexity: $O(N)$. Average case should be $O(\log n)$, the worst case is a linked-list-like tree then it would be a $O(N)$

#### Solution #2 Iteration

*   Use a stack to hold the values, each time we encouter a null, we pop one element from the stack

```java
public List<Integer> preorderTraversal(TreeNode root){
    Stack<TreeNode> stack = new Stack<>();
    LinkedList<Integer> output = new LinkedList<>();
    if (root == null){
        return output;
    }
    
    stack.add(root);
    while(!stack.isEmpty()){
        TreeNode node = stack.pop();
        output.add(node.val);
        if (node.right != null){// since it is a stack, we need to exchange the order here with left
            stack.add(node.right);
        }
        if (node.left != null){
            stack.add(node.left);
        }
    }
}
```

*   Time complexity: $O(N)$. N is the number of nodes, each node is traversed once.
*   Space complexity: $O(N)$. Average case should be $O(\log n)$, the worst case is a linked-list-like tree then it would be a $O(N)$

#### Solution #3 Morris Traversal(not important)

*   The algorithm does not use additional space for the computation, and the memory is only used to keep the output. If one prints the output directly along the computation, the space complexity would be $ \mathcal{O}(1)$.
*   Details refer to [morris traversal leetcode](https://leetcode.com/problems/binary-tree-preorder-traversal/solution/)

```java
public List<Integer> preorderTraversal(TreeNode root){
    LinkedList<Integer> output = new LinkedList<>();
    
    TreeNode node = root;
    while(node != null){
        if(node.left == null){
            output.add(node.val);
            node = node.right;
        }
        else {
            TreeNode predecessor = node.left;
            while((predecessor.right != null) && (predecessor.right != node)){
                predecessor = predecessor.right;
            }
            if (predecessor.right == null){
                output.add(node.val);
                predecessor.right = node;
                node = node.left;
            }
            else {
                predecessor.right = null;
                node = node.right;
            }
        }
    }
    return output;
}
```

-   Time complexity : we visit each predecessor exactly twice descending down from the node, thus the time complexity is $\mathcal{O}(N)$, where N is the number of nodes, *i.e.* the size of tree.
-   Space complexity : we use no additional memory for the computation itself, thus space complexity is $\mathcal{O}(1)$.

## Binary Tree Inorder Traversal(Easy #94)

**Question**: Given the `root` of a binary tree, return *the inorder traversal of its nodes' values*.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/09/15/inorder_1.jpg" alt="img" style="zoom:50%;" />

```
Input: root = [1,null,2,3]
Output: [1,3,2]
```

**Example 2:**

```
Input: root = []
Output: []
```

**Example 3:**

```
Input: root = [1]
Output: [1]
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 100]`.
-   `-100 <= Node.val <= 100`

### My Solution

```java
public List<Integer> inorderTraversal(TreeNode root){
    List<Integer> nodes = new ArrayList<>();
    inorder(root, nodes);
    return nodes;
}

public void inorder(TreeNode root, List<Integer> nodes){
    if (root == null) return;
    else {
        inorder(root.left, nodes);
        nodes.add(root.val);
        inorder(root.right, nodes);
    }
}
```

### Standard Solution

#### Solution #1 Recursion

*   Same as my solution
*   Time complexity: $O(N)$ because the recursive function is $T(n) = 2 \cdot T(n/2)+1$.
*   Space complexity: $O(N)$. The worst case space is $O(N)$, and in average case it is $O(\log n)$

#### Solution #2 Interation using Stack

```java
public List<Integer> inorderTraversal(TreeNode root){
    List<Integer> res = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()){
        while (curr != null){
            stack.push(curr);
            curr = curr.left;
        }
        curr = stack.pop();
        res.add(curr.val);
        curr = curr.right;
    }
}
```

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null){
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur =root;
        while (cur!=null || !stack.isEmpty()) {
            if (cur!=null) {
                stack.push(cur);
                cur = cur.left;
            }else {
                cur = stack.pop();
                result.add(cur.val);
                cur = cur.right;
            }
        }
        return result;
    }
}

作者：mulberry_qs
链接：https://leetcode-cn.com/problems/binary-tree-postorder-traversal/solution/duo-chong-bian-li-tou-da-by-mulberry_qs-02c9/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

*   Time complexity: $O(N)$ because the recursive function is $T(n) = 2 \cdot T(n/2)+1$.
*   Space complexity: $O(N)$. The worst case space is $O(N)$, and in average case it is $O(\log n)$

#### Solution #3 Morris Traversal

```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode curr = root;
        TreeNode pre;
        while (curr != null) {
            if (curr.left == null) {
                res.add(curr.val);
                curr = curr.right; // move to next right node
            } else { // has a left subtree
                pre = curr.left;
                while (pre.right != null) { // find rightmost
                    pre = pre.right;
                }
                pre.right = curr; // put cur after the pre node
                TreeNode temp = curr; // store cur node
                curr = curr.left; // move cur to the top of the new tree
                temp.left = null; // original cur left be null, avoid infinite loops
            }
        }
        return res;
    }
}
```

*   Time complexity: $O(N)$
*   Space complexity: $O(1)$

## Binary Tree Postorder Traversal(Easy #145)

**Question**: Given the `root` of a binary tree, return *the postorder traversal of its nodes' values*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/08/28/pre1.jpg)

```
Input: root = [1,null,2,3]
Output: [3,2,1]
```

**Example 2:**

```
Input: root = []
Output: []
```

**Example 3:**

```
Input: root = [1]
Output: [1]
```

**Constraints:**

-   The number of the nodes in the tree is in the range `[0, 100]`.
-   `-100 <= Node.val <= 100`

### My Solution

```java
public List<Integer> postorderTraversal(TreeNode root){
    List<Integer> nodes = new ArrayList<>();
    postorder(root, nodes);
    return nodes;
}

public void postorder(TreeNode root, List<Integer> nodes){
    if (root == null){
        return;
    }
    else {
        postorder(root.left, nodes);
        postorder(root.right, nodes);
        nodes.add(root.val);
    }
}
```

```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Stack<TreeNode> stack = new Stack<>(); // 入栈为中左右 出栈为中右左
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();
            result.add(treeNode.val);
            if (treeNode.left != null) {
                stack.push(treeNode.left);
            }
            if (treeNode.right != null) {
                stack.push(treeNode.right);
            }
        }
        Collections.reverse(result); // 直接翻转中右左为左右中
        return result;
    }
}

作者：mulberry_qs
链接：https://leetcode-cn.com/problems/binary-tree-postorder-traversal/solution/duo-chong-bian-li-tou-da-by-mulberry_qs-02c9/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

*   Time complexity and space complexity should be the same as previous problems 
*   No given standard solution in leetcode but should be similar to previous problems