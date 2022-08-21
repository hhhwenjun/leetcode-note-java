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

```java
// simple recursion
public int maxDepth(TreeNode root) {
    // base case root is null
    if (root == null) return 0;
    return Math.max(1 + maxDepth(root.left), 1 + maxDepth(root.right));
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

-   Time complexity: $\mathcal{O}(N)$.
-   Space complexity: in the worst case, the tree is completely unbalanced, *e.g.,* each node has only a left child node, and the recursion call would occur N times (the height of the tree). Therefore the storage to keep the call stack would be $\mathcal{O}(N)$. But in the average case (the tree is balanced), the height of the tree would be $\log(N)$. Therefore, the space complexity, in this case, would be $\mathcal{O}(\log(N))$.

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

## Count Univalue Subtrees(Medium #250)

**Question**: Given the `root` of a binary tree, return the number of **uni-value** subtrees.

A **uni-value subtree** means all nodes of the subtree have the same value.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/08/21/unival_e1.jpg)

```
Input: root = [5,1,5,5,5,null,5]
Output: 4
```

**Example 2:**

```
Input: root = []
Output: 0
```

**Example 3:**

```
Input: root = [5,5,5,5,5,null,5]
Output: 6
```

**Constraints:**

-   The number of the node in the tree will be in the range `[0, 1000]`.
-   `-1000 <= Node.val <= 1000`

### Standard Solution

#### Solution #1 Depth First Search

*   A univalue subtree
    *   The node has no children(base case)
    *   All of the node's children are univalue subtrees, and the node and its children all have the same value

```java
class Solution {
    int count = 0;
    boolean is_uni(TreeNode node){
        // base case - if the node has no children this is a univalue subtree
        if (node.left == null && node.right == null){
            // found a univalue subtree - increment
            count++;
            return true;
        }
        boolean is_unival = true;
        
        // check if all of the node's children are univalue subtrees and it they have same value
        // also recursively call is_uni for children
        if (node.left != null){
            is_unival = is_uni(node.left) && is_unival && node.left.val == node.val;
        }
        if (node.right != null){
            is_unival = is_uni(node.right) && is_unival && node.right.val == node.val;
        }
        // return if univalue tree exists here and increment if it does
        if (!is_unival) return false;
        count++;
        return true;
    }
    public int countUnivalSubtrees(TreeNode root) {
        if (root == null) return 0;
        is_uni(root);
        return count;
    }
}
```

*   Time complexity : $O(n)$.

    Due to the algorithm's depth-first nature, the `is_uni` status of each node is computed from bottom up. When given the `is_uni` status of its children, computing the `is_uni` status of a node occurs in $O(1)$.

    This gives us $O(1)$ time for each node in the tree with $O(N)$ total nodes for a time complexity of $O(N)$

*   Space complexity : $O(H)$, with `H` being the height of the tree. Each recursive call of `is_uni` requires stack space. Since we fully process `is_uni(node.left)` before calling `is_uni(node.right)`, the recursive stack is bound by the longest path from the root to a leaf - in other words the height of the tree.

#### Solution #2 Depth First Search - Pass Parent Values

*   Instead of checking if a node has no children, we treat `null` values as univalue subtrees that we don't add to the count.
*   In this manner, if a node has a `null` child, that child is automatically considered to a valid subtree, which results in the algorithm only checking if other children are invalid.
*   Finally, the helper function checks if the current node is a valid subtree but returns a boolean indicating if it is a valid component for its parent. This is done by passing in the value of the parent node.

```java
public class Solution{
    int count = 0;
    boolean is_valid_part(TreeNode node, int val){
        // considered a valid subtree
        if (node == null) return true;
        
        // check if node.left and node.right are univalue subtrees of value node.val
        // note that || short circuits but | does not - both sides of the or get evalueated with |
        // so we can explore all possible routes
        if (!is_valid_part(node.left, node.val) | !is_valid_part(node.right, node.val)){
            return false;
        }
        count++;
        // at this point we know that this node is a univalue subtree of value node.val
        // pass a boolean indicating if this is a valid subtree for the parent node
        return node.val == val;
    }
    
    public int countUnivalSubtrees(TreeNode root){
        is_valid_part(root, 0);
        return count;
    }
}
```

-   Time complexity : $O(N)$. Same as the previous approach.
-   Space complexity : $O(H)$, with `H` being the height of the tree. Same as the previous approach.

## Construct Binary Tree from Inorder and Postorder Traversal(Medium #106)

**Question**: Given two integer arrays `inorder` and `postorder` where `inorder` is the inorder traversal of a binary tree and `postorder` is the postorder traversal of the same tree, construct and return *the binary tree*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/tree.jpg)

```
Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
Output: [3,9,20,null,null,15,7]
```

**Example 2:**

```
Input: inorder = [-1], postorder = [-1]
Output: [-1]
```

**Constraints:**

-   `1 <= inorder.length <= 3000`
-   `postorder.length == inorder.length`
-   `-3000 <= inorder[i], postorder[i] <= 3000`
-   `inorder` and `postorder` consist of **unique** values.
-   Each value of `postorder` also appears in `inorder`.
-   `inorder` is **guaranteed** to be the inorder traversal of the tree.
-   `postorder` is **guaranteed** to be the postorder traversal of the tree.

### Standard Solution

#### Solution #1 Iteration (hard to understand)

*   Reverse the inorder traversal, we visit the right -> root -> left
*   Reverse the postorder traversal, we visit the root -> right -> left
*   In postorder reverse situation, neighbor two nodes u and v only has the relationship
    *   u is v 's right child
    *   v does not have right child, and u is v's ancestor's left child. We go upward to traverse the ancestor until one of the ancestor root has left child.
*   Use a stack to maintain the ancestor nodes that we haven't considered the left child
*   Use a pointer point to the last node in the Inorder reverse

```java
public TreeNode buildTree(int[] inorder, int[] postorder){
    if (postorder == null || postorder.length == 0){
        return null;
    }
    TreeNode root = new TreeNode(postorder[postorder.length - 1]); //last node in postorder
    Deque<TreeNode> stack = new LinkedList<TreeNode>();
    stack.push(root);
    int inorderIndex = inorder.length - 1;
    for (int i = postorder.length - 2; i >= 0; i--){
        int postorderVal = postorder[i];
        TreeNode node = stack.peek();
        if (node.val != inorder[inorderIndex]){ //it has right child
            node.right = new TreeNode(postorderVal);
            stack.push(node.right);
        } else {
            // it is a left child of ancestor
            while(!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]){
                node = stack.pop();
                inorderIndex--;
            }
            node.left = new TreeNode(postorderVal);
            stack.push(node.left);
        }
    }
    return root;
}
```

*   Time complexity: $O(N)$, N is the number of tree node
*   Space complexity: $O(N)$, we need $O(H)$ to store the stack. H is the height of the tree. The worst case would be $O(N)$.

#### Solution #2 Recursion

![bla](https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/Figures/106/recursion.png)

*   Use a hash map to store the inorder traversal, buildup a (element, index) hash map
*   Define a recursion method `helper(in_left, in_right)` to present the left and right bound of the inorder traversal
    *   If `in_left > in_right`, the subtree is empty, return null
    *   Use hash map to search the current index in the inorder traversal, the left subtree is `in_left` to `index - 1`. The right subtree is from `index + 1` to `in_right`
    *   First create right subtree with `helper(index + 1, in_right)` and left subtree `helper(in_left, index - 1)`
    *   Return root

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
class Solution {
    int post_idx;
    int[] postorder;
    int[] inorder;
    Map<Integer, Integer> idx_map = new HashMap<Integer, Integer>();
    
    public TreeNode helper(int in_left, int in_right){
        // if no node to create binary tree, then it is ended
        if (in_left > in_right){
            return null;
        }
        // choose post_idx location's element as the current tree node
        int root_val = postorder[post_idx];
        TreeNode root = new TreeNode(root_val);
        
        // get the location of the root
        int index = idx_map.get(root_val);
        
        post_idx--;
        // construct the right subtree
        root.right = helper(index + 1, in_right);
        // construct the left subtree
        root.left = helper(in_left, index - 1);
        return root;
    }
    public TreeNode buildTree(int[] inorder, int[] postorder){
    	this.postorder = postorder;
        this.inorder = inorder;
        // start from the last element of postorder
        post_idx = postorder.length - 1;
        
        // build hashmap
        int idx = 0;
        for (Integer val : inorder){
            idx_map.put(val, idx++);
        }
        return helper(0, inorder.length - 1);
	}
}

```

*   Time complexity: $O(N)$, N is the number of nodes in tree
*   Space complexity: $O(N)$, we need $O(N)$ space for storing hashmap. And $O(H)$ for space of stack. But H < N. So the space complexity is $O(N)$.

## Construct Binary Tree from Preorder and Inorder Traversal(Medium #105)

**Question**: Given two integer arrays `preorder` and `inorder` where `preorder` is the preorder traversal of a binary tree and `inorder` is the inorder traversal of the same tree, construct and return *the binary tree*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/tree.jpg)

```
Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]
```

**Example 2:**

```
Input: preorder = [-1], inorder = [-1]
Output: [-1]
```

**Constraints:**

-   `1 <= preorder.length <= 3000`
-   `inorder.length == preorder.length`
-   `-3000 <= preorder[i], inorder[i] <= 3000`
-   `preorder` and `inorder` consist of **unique** values.
-   Each value of `inorder` also appears in `preorder`.
-   `preorder` is **guaranteed** to be the preorder traversal of the tree.
-   `inorder` is **guaranteed** to be the inorder traversal of the tree.

### My Solution

```java
class Solution {
    int[] preorder;
    int[] inorder;
    int preorderIdx;
    Map<Integer, Integer> treeMap;
    public TreeNode helper(int left, int right){
        if (left > right){
            return null;
        }
        int value = preorder[preorderIdx++];
        TreeNode root = new TreeNode(value);
        int nodeIdx = (int)treeMap.get(value);
        root.left = helper(left, nodeIdx - 1);
        root.right = helper(nodeIdx + 1, right);
        return root;
    }
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;
        treeMap = new HashMap<Integer, Integer>();
        preorderIdx = 0;
        int inorderIdx = 0;
        for (Integer digit : inorder){
            treeMap.put(digit, inorderIdx);
            inorderIdx++;
        }
        return helper(0, preorder.length - 1 - preorderIdx);
    }
}
```

*   Similar to last problem, but need to modify the traverse order
*   Similar to the official answer
*   Pur inorder traversal to a hashmap, use preorder to determine the root. Use the hashmap to determine left and right subtree since they are on the two sides of the root.

### Standard Solution

#### Solution #1 Recursion

*   It will set the first element of `preorder` as the root, and then construct the entire tree. 
*   To find the left and right subtrees, it will look for the root in `inorder`, so that everything on the left should be the left subtree, and everything on the right should be the right subtree. 
*   Both subtrees can be constructed by making another recursion call.
*   It is worth noting that, while we recursively construct the subtrees, we should choose the next element in `preorder` to initialize as the new roots. This is because the current one has already been initialized to a parent node for the subtrees.

<img src="https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/Figures/105/105-Page-2.png" alt="Always use the next element in  to initialize a root." style="zoom: 50%;" />

```java
class Solution {
    int preorderIndex;
    Map<Integer, Integer> inorderIndexMap;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        // build a hashmap to store value -> its index relations
        inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }

        return arrayToTree(preorder, 0, preorder.length - 1);
    }

    private TreeNode arrayToTree(int[] preorder, int left, int right) {
        // if there are no elements to construct the tree
        if (left > right) return null;

        // select the preorder_index element as the root and increment it
        int rootValue = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootValue);

        // build left and right subtree
        // excluding inorderIndexMap[rootValue] element because it's the root
        root.left = arrayToTree(preorder, left, inorderIndexMap.get(rootValue) - 1);
        root.right = arrayToTree(preorder, inorderIndexMap.get(rootValue) + 1, right);
        return root;
    }
}
```

*   Time complexity : $O(N)$.

    Building the hashmap takes $O(N)$ time, as there are N nodes to add, and adding items to a hashmap has a cost of $O(1)$, so we get $N \cdot O(1) = O(N)$.

    Building the tree also takes $O(N)$ time. The recursive helper method has a cost of $O(1)$ for each call (it has no loops), and it is called *once* for each of the N nodes, giving a total of $O(N)$.

    Taking both into consideration, the time complexity is $O(N)$.

*   Space complexity : $O(N)$.

    Building the hashmap and storing the entire tree each requires $O(N)$ memory. The size of the implicit system stack used by recursion calls depends on the height of the tree, which is $O(N)$ in the worst case and $O(\log N)$ on average. Taking both into consideration, the space complexity is $O(N)$.

## Populating Next Right Pointers in Each Node(Medium #116)

**Question**: You are given a **perfect binary tree** where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:

```
struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
```

Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to `NULL`.

Initially, all next pointers are set to `NULL`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2019/02/14/116_sample.png" alt="img" style="zoom:50%;" />

```
Input: root = [1,2,3,4,5,6,7]
Output: [1,#,2,3,#,4,5,6,7,#]
Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
```

**Example 2:**

```
Input: root = []
Output: []
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 212 - 1]`.
-   `-1000 <= Node.val <= 1000`

**Follow-up:**

-   You may only use constant extra space.
-   The recursive approach is fine. You may assume implicit stack space does not count as extra space for this problem.

### My Solution

*   When encounter null, move to the next right node
*   Otherwise, go to the parent node and to the right
*   If already right node, go to the ancestor node
*   The solution is a recursion version of the solution 1

```java
public Node connect(Node root){
    // base case
    if (root == null){
        return root;
    }
    // not in the root
    else {
        helper(root);
        connect(root.left);// go to the next level
    }
   return root;
}
public Node helper(Node head){
    if (head == null) return head;
    if (head != null && head.left != null){ // connect the childs of the next level
        head.left.next = head.right;  
        if (head.next != null){
            head.right.next = head.next.left;
        }
    }
    return helper(head.next);
}
```

### Standard Solution

#### Solution #1 Using previously established next pointers

<img src="https://leetcode.com/problems/populating-next-right-pointers-in-each-node/Figures/116/img7.png" alt="img" style="zoom:24%;" />

*   Use a head pointer and a leftmost pointer: leftmost pointer is the beginning of a row, head pointer point to each node
*   If a row is already traverse, we move to the next row, leftmost would be leftmost.left.

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/
public Node connect(Node root){
    // base case
    if (root == null){
        return root;
    }
    // Start with the root node. There are no next pointers on first level
    Node leftmost = root;
    while (leftmost.left != null){// while not on the bottom level
        // at the begining of the level
        Node head = leftmost;
        // while not at the end of the level
        while (head != null){
            // connection 1
            head.left.next = head.right;
            // connection 2
            if (head.next != null){
                head.right.next = head.next.left;
            }
            // progress along the level
            head = head.next;
        }
        leftmost = leftmost.left;
    }
    return root;
}
```

*   Time Complexity: $O(N)$ since we process each node exactly once.
*   Space Complexity: $O(1)$ since we don't make use of any additional data structure for traversing nodes on a particular level like the previous approach does.

#### Solution #2 Level Order Traversal(BFS)

*   A brute force method and easy to understand 
*   Use a queue to store each level of the nodes, peek the top node to connect

```java
class Solution {
    public Node connect(Node root) {   
        if (root == null) {
            return root;
        }
        // Initialize a queue data structure which contains
        // just the root of the tree
        Queue<Node> Q = new LinkedList<Node>(); 
        Q.add(root);
        // Outer while loop which iterates over 
        // each level
        while (Q.size() > 0) { 
            // Note the size of the queue
            int size = Q.size();
            // Iterate over all the nodes on the current level
            for(int i = 0; i < size; i++) {
                
                // Pop a node from the front of the queue
                Node node = Q.poll();
                // This check is important. We don't want to
                // establish any wrong connections. The queue will
                // contain nodes from 2 levels at most at any
                // point in time. This check ensures we only 
                // don't establish next pointers beyond the end
                // of a level
                if (i < size - 1) {
                    node.next = Q.peek();
                }
                // Add the children, if any, to the back of
                // the queue
                if (node.left != null) {
                    Q.add(node.left);
                }
                if (node.right != null) {
                    Q.add(node.right);
                }
            }
        }
        // Since the tree has now been modified, return the root node
        return root;
    }
}
```

*   Time Complexity: $O(N)$since we process each node exactly once. Note that processing a node in this context means popping the node from the queue and then establishing the next pointers.
*   Space Complexity: $O(N)$. This is a perfect binary tree which means the last level contains $N/2$ nodes. The space complexity for breadth first traversal is the space occupied by the queue which is dependent upon the maximum number of nodes in particular level. So, in this case, the space complexity would be $O(N)$.

## Populating Next Right Pointers in Each Node(Medium #117)

**Question**: Given a binary tree

```
struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
```

Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to `NULL`.

Initially, all next pointers are set to `NULL`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2019/02/15/117_sample.png" alt="img" style="zoom:50%;" />

```
Input: root = [1,2,3,4,5,null,7]
Output: [1,#,2,3,#,4,5,7,#]
Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
```

**Example 2:**

```
Input: root = []
Output: []
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 6000]`.
-   `-100 <= Node.val <= 100`

### My Solution

```java
public Node connect(Node root){
    // base case
    if (root == null){
        return root;
    }
    else {
        connect(findNext(root)); // find the next node to connect
    }
    return root;
}
// connect the node in each level
public Node findNext(Node root){
    // base case
    if (root == null){
        return root;
    }
    if (root.left != null){
        if (root.right != null){
            root.left.next = root.right;
            root.right.next = findNext(root.next);
            // if not the same parent node, we find next one as the next
        }
        else { // right child is empty
            root.left.next = findNext(root.next);
        }
    }
    else if (root.right != null){
        root.right.next = findNext(root.next);
    }
    else {// both left and right child is empty
        return findNext(root.next);
    }
    return root.left != null ? root.left : root.right;// return the closet next node
}
```

### Standard Solution

#### Solution #1 Level Order Traversal

*   Use a queue to record the node and pop nodes out to connect
*   This solution is the same as the last problem

```java
public Node connect(Node root){
    if (root == null){
        return root;
    }
    
    // Initialize a queue data structure contains root 
    Queue<Node> Q = new LinkedList<Node>();
    Q.add(root);
    
    // Outer while loop which iterates over each level
    while (Q.size() > 0){
        int size = Q.size();
        
        // iterate over all the nodes on the current level
        for (int i = 0; i < size; i++){
            // pop a node from the front of the queue
            Node node = Q.poll();
            
            // ensure we don't establish next pointers beyond the end of a level
            if (i < size - 1){
                node.next = Q.peek();
            }
            
            // add the children, if any, to the back of queue
            if (node.left != null){
                Q.add(node.left);
            }
            if (node.right != null){
                Q.add(node.right);
            }
        }
    }
    // since the tree has now been modified, return the root node
    return root;
}
```

*   Time complexity: $O(N)$ since we process each node once.
*   Space complexity: $O(N)$. Depends on the queue.

#### Solution #2 Using previously established next pointers

```java
class Solution { 
    Node prev, leftmost;
    public void processChild(Node childNode) {  
        if (childNode != null) {
            // If the "prev" pointer is alread set i.e. if we
            // already found atleast one node on the next level,
            // setup its next pointer
            if (this.prev != null) {
                this.prev.next = childNode;
            } else {
                // Else it means this child node is the first node
                // we have encountered on the next level, so, we
                // set the leftmost pointer
                this.leftmost = childNode;
            }    
            this.prev = childNode; 
        }
    }
    public Node connect(Node root) {
        
        if (root == null) {
            return root;
        }
        // The root node is the only node on the first level
        // and hence its the leftmost node for that level
        this.leftmost = root;
        // Variable to keep track of leading node on the "current" level
        Node curr = leftmost;
        // We have no idea about the structure of the tree,
        // so, we keep going until we do find the last level.
        // the nodes on the last level won't have any children
        while (this.leftmost != null) {
            // "prev" tracks the latest node on the "next" level
            // while "curr" tracks the latest node on the current
            // level.
            this.prev = null;
            curr = this.leftmost;
            // We reset this so that we can re-assign it to the leftmost
            // node of the next level. Also, if there isn't one, this
            // would help break us out of the outermost loop.
            this.leftmost = null;
            // Iterate on the nodes in the current level using
            // the next pointers already established.
            while (curr != null) {
                // Process both the children and update the prev
                // and leftmost pointers as necessary.
                this.processChild(curr.left);
                this.processChild(curr.right);
                // Move onto the next node.
                curr = curr.next;
            }
        }
        return root ;
    }
}
```

-   Time Complexity: $O(N)$ since we process each node exactly once.
-   Space Complexity: $O(1)$ since we don't make use of any additional data structure for traversing nodes on a particular level like the previous approach does.

## Lowest Common Ancestor of a Binary Tree(Medium #236)

**Question**: Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the [definition of LCA on Wikipedia](https://en.wikipedia.org/wiki/Lowest_common_ancestor): “The lowest common ancestor is defined between two nodes `p` and `q` as the lowest node in `T` that has both `p` and `q` as descendants (where we allow **a node to be a descendant of itself**).”

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/14/binarytree.png)

```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2018/12/14/binarytree.png)

```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
```

**Example 3:**

```
Input: root = [1,2], p = 1, q = 2
Output: 1
```

**Constraints:**

-   The number of nodes in the tree is in the range `[2, 105]`.
-   `-109 <= Node.val <= 109`
-   All `Node.val` are **unique**.
-   `p != q`
-   `p` and `q` will exist in the tree.

### Standard Solution 

#### Solution #1 Recursion

*   The moment you encounter either of the nodes `p` or `q`, return some boolean flag. 
*   Use integer to count how many true you have already met
*    The least common ancestor would then be the node for which both the subtree recursions return a `True` flag.
*   If the current node itself is one of `p` or `q`, we would mark a variable `mid` as `True` and continue the search for the other node in the left and right branches.

```java
TreeNode target;
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
    if (root == null){
        return root;
    }
    else {
        findChild(root, p, q);
    }
    return target;
}
// at least two of the branch we find then it is the lowest common
public boolean findChild(TreeNode root, TreeNode p, TreeNode q){
    int sum = 0;
    if (root == null){
        return false;
    }
    else {
        int left = findChild(root.left, p, q) ? 1 : 0;
        int right = findChild(root.right, p, q) ? 1 : 0;
        // If the current node is one of p or q
        int mid = (root == p || root == q) ? 1 : 0;
        sum = mid + right + left;
        if (sum >= 2) target = root; // two of the branches should include the nodes
    }
	return (sum > 0); // return if we find at least one of the node
}
```

*   Time Complexity: $O(N)$, where N is the number of nodes in the binary tree. In the worst case we might be visiting all the nodes of the binary tree.
*   Space Complexity: $O(N)$. This is because the maximum amount of space utilized by the recursion stack would be N since the height of a skewed binary tree could be N.

#### Solution #2 Iteration

*   Start from the root node and traverse the tree.
*   Until we find `p` and `q` both, keep storing the parent pointers in a dictionary.
*   Once we have found both `p` and `q`, we get all the ancestors for `p` using the parent dictionary and add to a set called `ancestors`.
*   Similarly, we traverse through ancestors for node `q`. If the ancestor is present in the ancestors set for `p`, this means this is the first ancestor common between `p` and `q` (while traversing upwards) and hence this is the LCA node.

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
    // stack for tree traversal
    Deque<TreeNode> stack = new ArrayDeque<>();
    
    // hashmap for parent pointers
    Map<TreeNode, TreeNode> parent = new HashMap<>();
    parent.put(root, null);
    stack.push(root);
    
    // Iterate untill we find both the bodes p and q
    while(!parent.containsKey(p) || !parent.containsKey(q)){
        TreeNode node = stack.pop();
        
        // while traversing the tree, keep saving the parent pointers
        if (node.left != null){
            parent.put(node.left, node);
            stack.push(node.left);
        }
        if (node.right != null){
            parent.put(node.right, node);
            stack.push(node.right);
        }
    }
    // Ancestors set() for node p.
    Set<TreeNode> ancestors = new HashSet<>();
    // Process all ancestors for node p using parent pointers.
    while (p != null){
        ancestors.add(p);
        p = parent.get(p);
    }
    // The first ancestor of q which appears in p's ancestor set() is their lowest common ancestor
    while (!ancestors.contains(q)){
        q = parent.get(q);
    }
    return q;
}
```

*   Time Complexity: $O(N)$, where N is the number of nodes in the binary tree. In the worst case we might be visiting all the nodes of the binary tree.
*   Space Complexity: $O(N)$. This is because the maximum amount of space utilized by the recursion stack would be N since the height of a skewed binary tree could be N.

## Serialize and Deserialize Binary Tree(Hard #297)

**Question**: Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

**Clarification:** The input/output format is the same as [how LeetCode serializes a binary tree](https://leetcode.com/faq/#binary-tree). You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/09/15/serdeser.jpg" alt="img" style="zoom:50%;" />

```
Input: root = [1,2,3,null,null,4,5]
Output: [1,2,3,null,null,4,5]
```

**Example 2:**

```
Input: root = []
Output: []
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 104]`.
-   `-1000 <= Node.val <= 1000`

### My Solution

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    	String nullString = "null";
    
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        String result = "";
        return helperSerialize(root, result);
    }
    
    public String helperSerialize(TreeNode root, String result){
        if (root == null){
            result += nullString;
            result += ",";
        } else {
            String value = String.valueOf(root.val);
            result += value;
            result += ",";
            result = helperSerialize(root.left, result);
            result = helperSerialize(root.right, result);
        }
        return result;
    }

    public TreeNode helperDeserialize(List<String> data){
        if (data.get(0).equals("null")){
            data.remove(0);
            return null;
        } else {
            TreeNode node = new TreeNode(Integer.parseInt(data.get(0)));
            data.remove(0);
            node.left = helperDeserialize(data);
            node.right = helperDeserialize(data);
            return node;
        }
        
    }
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] dataArry = data.split(",");
        List<String> dataQueue = new LinkedList<String>(Arrays.asList(dataArry));
        return helperDeserialize(dataQueue);
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
```

*   Need to carefully observe the features of the input, remember to add "," to separate the nodes.
*   When leetcode run the test, it first serialize then deserialize, so you need to debug the two methods.
*   Middle - Left - right, recursion to continuously add to the string
*   Use a list to store the string array and delete the top one (pop element from a queue)

### Standard Solution 

*   Same as my solution
*   Time complexity : in both serialization and deserialization functions, we visit each node exactly once, thus the time complexity is $O(N)$, where N is the number of nodes, *i.e.* the size of tree.
*   Space complexity : in both serialization and deserialization functions, we keep the entire tree, either at the beginning or at the end, therefore, the space complexity is $O(N)$.

## Binary Tree Vertical Order Traversal (Medium #314)

**Question**: Given the `root` of a binary tree, return ***the vertical order traversal** of its nodes' values*. (i.e., from top to bottom, column by column).

If two nodes are in the same row and column, the order should be from **left to right**.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/01/28/vtree1.jpg" alt="img" style="zoom:67%;" />

```
Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/01/28/vtree2-1.jpg" alt="img" style="zoom:67%;" />

```
Input: root = [3,9,8,4,0,1,7]
Output: [[4],[9],[3,0,1],[8],[7]]
```

**Example 3:**

<img src="https://assets.leetcode.com/uploads/2021/01/28/vtree2.jpg" alt="img" style="zoom:67%;" />

```
Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
Output: [[4],[9,5],[3,0,1],[8,2],[7]]
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 100]`.
-   `-100 <= Node.val <= 100`

### My Solution

*   Based on one of the solutions, basically correct but the order within ArrayList is not correct
*   That is because we use stack here and it should be a queue, it influences the order

```java
 /**
* abstract; traverse the tree return vertical order
* in-order traversal? (similiar pattern)
* leftchild - right = rightchild - left
*/
List<List<Integer>> res;

public List<List<Integer>> verticalOrder(TreeNode root) {
    res = new ArrayList<>();
    if (root == null){
        return res;
    }
    // vertical column map and array list
    Map<Integer, ArrayList> columnTable = new HashMap<>();
    // record the treenode and its column number
    Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
    int column = 0;
    stack.add(new Pair(root, column));
    int minColumn = 0, maxColumn = 0;

    // traverse all the nodes
    while (!stack.isEmpty()){
        Pair<TreeNode, Integer> pair = stack.pop();
        root = pair.getKey();
        column = pair.getValue();

        if (root != null){
            if (!columnTable.containsKey(column)){
                columnTable.put(column, new ArrayList<Integer>());
            }
            columnTable.get(column).add(root.val);
            minColumn = Math.min(minColumn, column);
            maxColumn = Math.max(maxColumn, column);

            stack.add(new Pair(root.left, column - 1));
            stack.add(new Pair(root.right, column + 1));
        }
    }
    for (int i = minColumn; i <= maxColumn; i++){
        res.add(columnTable.get(i));
    }
    return res;
}
```

### Standard Solution

![tree in 2D coordinates](https://leetcode.com/problems/binary-tree-vertical-order-traversal/Figures/314/314_coordinates.png)

#### Solution #1 BFS

*   The idea is that we keep a hash table (let's denote it as `columnTable<key, value>`), where we keep the node values grouped by the `column` index.

```java
  public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> output = new ArrayList();
    if (root == null) {
      return output;
    }

    Map<Integer, ArrayList> columnTable = new HashMap();
    Queue<Pair<TreeNode, Integer>> queue = new ArrayDeque();
    int column = 0;
    queue.offer(new Pair(root, column));

    while (!queue.isEmpty()) {
      Pair<TreeNode, Integer> p = queue.poll();
      root = p.getKey();
      column = p.getValue();

      if (root != null) {
        if (!columnTable.containsKey(column)) {
          columnTable.put(column, new ArrayList<Integer>());
        }
        columnTable.get(column).add(root.val);

        queue.offer(new Pair(root.left, column - 1));
        queue.offer(new Pair(root.right, column + 1));
      }
    }

    List<Integer> sortedKeys = new ArrayList<Integer>(columnTable.keySet());
    Collections.sort(sortedKeys);
    for(int k : sortedKeys) {
      output.add(columnTable.get(k));
    }

    return output;
  }
```

*   Time Complexity: $\mathcal{O}(N \log N)$ where N is the number of nodes in the tree.
    *   In the first part of the algorithm, we do the BFS traversal, whose time complexity is $\mathcal{O}(N)$ since we traversed each node once and only once.
    *   In the second part, in order to return the ordered results, we then sort the obtained hash table by its keys, which could result in the $\mathcal{O}(N \log N)$ time complexity in the worst-case scenario where the binary tree is extremely imbalanced (for instance, each node has only a left child node.)
    *   As a result, the overall time complexity of the algorithm would be $\mathcal{O}(N \log N)$.

*   Space Complexity: $\mathcal{O}(N)$ where N is the number of nodes in the tree.

#### Solution #2 BFS without Sorting

*   The key insight is that we only need to know the **range** of the column index (*i.e.* `[min_column, max_column]`). Then we can simply ***iterate*** through this range to generate the outputs without the need for sorting.

```java
class Solution {
  public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> output = new ArrayList();
    if (root == null) {
      return output;
    }

    Map<Integer, ArrayList> columnTable = new HashMap();
    // Pair of node and its column offset
    Queue<Pair<TreeNode, Integer>> queue = new ArrayDeque();
    int column = 0;
    queue.offer(new Pair(root, column));

    int minColumn = 0, maxColumn = 0;

    while (!queue.isEmpty()) {
      Pair<TreeNode, Integer> p = queue.poll();
      root = p.getKey();
      column = p.getValue();

      if (root != null) {
        if (!columnTable.containsKey(column)) {
          columnTable.put(column, new ArrayList<Integer>());
        }
        columnTable.get(column).add(root.val);
        minColumn = Math.min(minColumn, column);
        maxColumn = Math.max(maxColumn, column);

        queue.offer(new Pair(root.left, column - 1));
        queue.offer(new Pair(root.right, column + 1));
      }
    }

    for(int i = minColumn; i < maxColumn + 1; ++i) {
      output.add(columnTable.get(i));
    }

    return output;
  }
}
```

*   Time Complexity: $\mathcal{O}(N)$ where N is the number of nodes in the tree.
    Following the same analysis in the previous BFS approach, the only difference is that this time we don't need the costly sorting operation (*i.e.* $\mathcal{O}(N \log N)$).
*   Space Complexity: $\mathcal{O}(N)$ where N is the number of nodes in the tree. The analysis follows the same logic as in the previous BFS approach.

## Subtree of Another Tree (Easy #572)

**Question**: Given the roots of two binary trees `root` and `subRoot`, return `true` if there is a subtree of `root` with the same structure and node values of` subRoot` and `false` otherwise.

A subtree of a binary tree `tree` is a tree that consists of a node in `tree` and all of this node's descendants. The tree `tree` could also be considered as a subtree of itself.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/04/28/subtree1-tree.jpg" alt="img" style="zoom:50%;" />

```
Input: root = [3,4,5,1,2], subRoot = [4,1,2]
Output: true
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/04/28/subtree2-tree.jpg" alt="img" style="zoom:50%;" />

```
Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
Output: false
```

**Constraints:**

-   The number of nodes in the `root` tree is in the range `[1, 2000]`.
-   The number of nodes in the `subRoot` tree is in the range `[1, 1000]`.
-   `-104 <= root.val <= 104`
-   `-104 <= subRoot.val <= 104`

### My Solution

```java
public boolean isSubtree(TreeNode root, TreeNode subRoot) {
    // base case
    if (subRoot == null) return true;
    if (root == null) return false;
    return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
}
public boolean isSameTree(TreeNode root1, TreeNode root2) {
    if (root1 == null && root2 == null) return true;
    if (root1 == null || root2 == null) return false;
    return root1.val == root2.val && isSameTree(root1.left, root2.left) 
        && isSameTree(root1.right, root2.right);
}
```

-   Time: `O(M * N)`, where `M` is the number of nodes in binary tree `root`, `N` is the number of nodes in binary tree `subRoot`
-   Space: `O(H)`, where `H` is the height of binary tree `root`
