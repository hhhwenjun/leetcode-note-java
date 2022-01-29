# Binary Tree Problems Part #1

## Maximum Depth of Binary Tree(Easy #104)

**Question**: Given the `root` of a binary tree, return *its maximum depth*.

A binary tree's **maximum depth** is the number of nodes along the longest path from the root node down to the farthest leaf node.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/26/tmp-tree.jpg)

```
Input: root = [3,9,20,null,null,15,7]
Output: 3
```

**Example 2:**

```
Input: root = [1,null,2]
Output: 2
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 104]`.
-   `-100 <= Node.val <= 100`

### My Solution

```java
public int maxDepth(TreeNode root) {
    if (root == null){
        return 0;
    }
    int leftDepth = maxDepth(root.left) + 1;
    int rightDepth = maxDepth(root.right) + 1;
    return Math.max(leftDepth, rightDepth);
}
```

### Standard Solution

#### Solution #1 Recursion

*   Handle the null situation
*   Pre-order traversal, each time for the depth + 1
*   Compare the left depth and the right depth
*   Same as my solution

```java
public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    } else {
      int left_height = maxDepth(root.left);
      int right_height = maxDepth(root.right);
      return java.lang.Math.max(left_height, right_height) + 1;
    }
}
```

*   Time complexity : we visit each node exactly once, thus the time complexity is $\mathcal{O}(N)$, where N is the number of nodes.
*   Space: each storage unit for a return call for understanding it
*   Space complexity : in the worst case, the tree is completely unbalanced, *e.g.* each node has only left child node, the recursion call would occur N*N* times (the height of the tree), therefore the storage to keep the call stack would be $\mathcal{O}(N)$. But in the best case (the tree is completely balanced), the height of the tree would be $\log(N)$. Therefore, the space complexity in this case would be $\mathcal{O}(\log(N))$.

#### Solution #2 Iteration 

*   Using stack structure to mimic the tree behavior for traversal
*   Time and space complexity is same as the recursion solution

```java
public int maxDepth(TreeNode root) {
    LinkedList<TreeNode> stack = new LinkedList<>();
    LinkedList<Integer> depths = new LinkedList<>();
    if (root == null) return 0;
    stack.add(root);
    depths.add(1);
    int depth = 0, current_depth = 0;
    while(!stack.isEmpty()) {
      root = stack.pollLast();
      current_depth = depths.pollLast();
      if (root != null) {
        depth = Math.max(depth, current_depth);
        stack.add(root.left);
        stack.add(root.right);
        depths.add(current_depth + 1);
        depths.add(current_depth + 1);
      }
    }
    return depth;
}
```

## Symmetric Tree(Easy 101)

**Question**: Given the `root` of a binary tree, *check whether it is a mirror of itself* (i.e., symmetric around its center).

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/symtree1.jpg)

```
Input: root = [1,2,2,3,4,4,3]
Output: true
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/02/19/symtree2.jpg)

```
Input: root = [1,2,2,null,3,null,3]
Output: false
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 1000]`.
-   `-100 <= Node.val <= 100`

### My Solution

```java
public boolean isSymmetric(TreeNode root) {
    return isSymmetric(root, root);
}

public boolean isSymmetric(TreeNode node1, TreeNode node2){
    if (node1 == null && node2 == null) return true;
    if (node1 == null || node2 == null) return false;
    return (node1.val == node2.val)
        && (isSymmetric(node1.left, node2.right))
        && (isSymmetric(node1.right, node2.left));
}
```

### Standard Solution

#### Solution #1 Recursion

*   Same as my solution
*   Need to handle the edge case first, then use `&&` to handle the recursion.

*   Time complexity : $O(n)$. Because we traverse the entire input tree once, the total run time is $O(n)$, where n is the total number of nodes in the tree.
*   Space complexity : The number of recursive calls is bound by the height of the tree. In the worst case, the tree is linear and the height is in $O(n)$. Therefore, space complexity due to recursive calls on the stack is $O(n)$ in the worst case.

#### Solution #2 Iteration

*   Use iteration with the aid for queue, similar to BFS since we try to search mirror

```java
public boolean isSymmetric(TreeNode root) {
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    q.add(root);
    while (!q.isEmpty()) {
        TreeNode t1 = q.poll();
        TreeNode t2 = q.poll();
        if (t1 == null && t2 == null) continue;
        if (t1 == null || t2 == null) return false;
        if (t1.val != t2.val) return false;
        q.add(t1.left);
        q.add(t2.right);
        q.add(t1.right);
        q.add(t2.left);
    }
    return true;
}
```

*   Time and space complexity is the same as last solution

## Path Sum(Easy #112)

**Question**: Given the `root` of a binary tree and an integer `targetSum`, return `true` if the tree has a **root-to-leaf** path such that adding up all the values along the path equals `targetSum`.

A **leaf** is a node with no children. 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/18/pathsum1.jpg)

```
Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
Output: true
Explanation: The root-to-leaf path with the target sum is shown.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/01/18/pathsum2.jpg)

```
Input: root = [1,2,3], targetSum = 5
Output: false
Explanation: There two root-to-leaf paths in the tree:
(1 --> 2): The sum is 3.
(1 --> 3): The sum is 4.
There is no root-to-leaf path with sum = 5.
```

**Example 3:**

```
Input: root = [], targetSum = 0
Output: false
Explanation: Since the tree is empty, there are no root-to-leaf paths.
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 5000]`.
-   `-1000 <= Node.val <= 1000`
-   `-1000 <= targetSum <= 1000`

### My Solution

```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    return findPathSum(root, targetSum);
}
public boolean findPathSum(TreeNode root, int targetSum){
    if (root == null) return false;
    targetSum -= root.val;
    if (root.left == null && root.right == null){
        return (targetSum == 0);
    }

    return findPathSum(root.left, targetSum)||
        findPathSum(root.right, targetSum);
}
```

### Standard Solution

#### Solution #1 Recursion

*   The most intuitive way, same as my solution.
*   If node *is not* a leaf, one calls recursively `hasPathSum` method for its children with a sum decreased by the current node value. 
*   If node *is* a leaf, one checks if the the current sum is zero, *i.e* if the initial sum was discovered.

```java
public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null)
      return false;

    sum -= root.val;
    if ((root.left == null) && (root.right == null))
      return (sum == 0);
    return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
}
```

*   Time complexity : we visit each node exactly once, thus the time complexity is $\mathcal{O}(N)$, where N is the number of nodes.
*   Space complexity : in the worst case, the tree is completely unbalanced, *e.g.* each node has only one child node, the recursion call would occur N times (the height of the tree), therefore the storage to keep the call stack would be $\mathcal{O}(N)$. But in the best case (the tree is completely balanced), the height of the tree would be $\log(N)$. Therefore, the space complexity in this case would be $\mathcal{O}(\log(N))$.

#### Solution #2 Iteration

*   Convert the above solution into iteration using stack
*   So we start from a stack which contains the root node and the corresponding remaining sum which is `sum - root.val`. 
*   Then we proceed to the iterations: pop the current node out of the stack and return `True` if the remaining sum is `0` and we're on the leaf node.
*   If the remaining sum is not zero or we're not on the leaf yet then we push the child nodes and corresponding remaining sums into stack.

```java
public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null)
      return false;

    LinkedList<TreeNode> node_stack = new LinkedList();
    LinkedList<Integer> sum_stack = new LinkedList();
    node_stack.add(root);
    sum_stack.add(sum - root.val);

    TreeNode node;
    int curr_sum;
    while ( !node_stack.isEmpty() ) {
      node = node_stack.pollLast();
      curr_sum = sum_stack.pollLast();
      if ((node.right == null) && (node.left == null) && (curr_sum == 0))
        return true;

      if (node.right != null) {
        node_stack.add(node.right);
        sum_stack.add(curr_sum - node.right.val);
      }
      if (node.left != null) {
        node_stack.add(node.left);
        sum_stack.add(curr_sum - node.left.val);
      }
    }
    return false;
}
```

*   Time and space complexity is the same as above solution