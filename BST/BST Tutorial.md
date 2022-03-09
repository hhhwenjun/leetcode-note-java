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

## Binary Search Tree Iterator(Medium #173)

**Question**: Implement the `BSTIterator` class that represents an iterator over the **[in-order traversal](https://en.wikipedia.org/wiki/Tree_traversal#In-order_(LNR))** of a binary search tree (BST):

-   `BSTIterator(TreeNode root)` Initializes an object of the `BSTIterator` class. The `root` of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
-   `boolean hasNext()` Returns `true` if there exists a number in the traversal to the right of the pointer, otherwise returns `false`.
-   `int next()` Moves the pointer to the right, then returns the number at the pointer.

Notice that by initializing the pointer to a non-existent smallest number, the first call to `next()` will return the smallest element in the BST.

You may assume that `next()` calls will always be valid. That is, there will be at least a next number in the in-order traversal when `next()` is called.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/25/bst-tree.png)

```
Input
["BSTIterator", "next", "next", "hasNext", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
[[[7, 3, 15, null, null, 9, 20]], [], [], [], [], [], [], [], [], []]
Output
[null, 3, 7, true, 9, true, 15, true, 20, false]

Explanation
BSTIterator bSTIterator = new BSTIterator([7, 3, 15, null, null, 9, 20]);
bSTIterator.next();    // return 3
bSTIterator.next();    // return 7
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 9
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 15
bSTIterator.hasNext(); // return True
bSTIterator.next();    // return 20
bSTIterator.hasNext(); // return False
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 105]`.
-   `0 <= Node.val <= 106`
-   At most `105` calls will be made to `hasNext`, and `next`.

**Follow up:**

-   Could you implement `next()` and `hasNext()` to run in average `O(1)` time and use `O(h)` memory, where `h` is the height of the tree?

### My Solution

*   Use a stack to store right - middle - left.
*   Then pop the stack would be an inorder traversal process.

```java
// faster than 96%, storage less than 85%, hooray!
class BSTIterator {
    
    private TreeNode root;
    private Stack<TreeNode> stack;

    public BSTIterator(TreeNode root) {
        this.root = root;
        this.stack = new Stack<>();
        traverse(root);
    }
    
    private void traverse(TreeNode root){
        if (root == null){
            return;
        }
        traverse(root.right);
        stack.add(root);
        traverse(root.left);
    }
    
    public int next() {
        TreeNode current = stack.pop();
        return current.val;
    }
    
    public boolean hasNext() {
        return !stack.empty();
    }
}
```

### Standard Solution

#### Solution #1 Flatten the Tree

*   Use an array to store the elements of the tree with inorder traversal 

```java
ArrayList<Integer> nodesSorted;
int index;
public BSTIterator(TreeNode root) {

    // Array containing all the nodes in the sorted order
    this.nodesSorted = new ArrayList<Integer>();

    // Pointer to the next smallest element in the BST
    this.index = -1;

    // Call to flatten the input binary search tree
    this._inorder(root);
}
private void _inorder(TreeNode root) {

    if (root == null) {
        return;
    }

    this._inorder(root.left);
    this.nodesSorted.add(root.val);
    this._inorder(root.right);
}
/**
 * @return the next smallest number
 */
public int next() {
    return this.nodesSorted.get(++this.index);
}

/**
 * @return whether we have a next smallest number
 */
public boolean hasNext() {
    return this.index + 1 < this.nodesSorted.size();
}
```

-   Time complexity: $O(N)$ is the time taken by the constructor for the iterator. The problem statement only asks us to analyze the complexity of the two functions, however, when implementing a class, it's important to also note the time it takes to initialize a new object of the class and in this case, it would be linear in terms of the number of nodes in the BST. In addition to the space occupied by the new array we initialized, the recursion stack for the inorder traversal also occupies space but that is limited to $O(h)$ where h is the height of the tree.
    -   `next()` would take $O(1)$
    -   `hasNext()` would take $O(1)$
-   Space complexity: $O(N)$ since we create a new array to contain all the nodes of the BST. This doesn't comply with the requirement specified in the problem statement that the maximum space complexity of either of the functions should be $O(h)$ where h is the height of the tree and for a well-balanced BST, the height is usually $logN$. So, we get great time complexities but we had to compromise on the space. Note that the new array is used for both the function calls and hence the space complexity for both the calls is $O(N)$.

#### Solution #2 Controlled Recursion

*   Same concept as my solution, but a little more complex.
*   Time complexity is the same as previous, with $O(1)$ for each method.
*   Space complexity is $O(N)$ (*N* is the number of nodes in the tree), which is occupied by our custom stack for simulating the inorder traversal. Again, we satisfy the space requirements as well as specified in the problem statement.

## BST Solution Template

*   Recursive Solution

```java
public TreeNode searchBST(TreeNode root, int target) {
    if (root == null || root.val == target) {
        return root;
    }
    if (target < root.val) {
        return searchBST(root.left, target);
    }
    return searchBST(root.right, target);
}
```

*   Iterative Solution

```java
public TreeNode searchBST(TreeNode root, int target) {
    TreeNode cur = root;
    while (cur != null && cur.val != target) {
        if (target < cur.val) {
            cur = cur.left;
        } else {
            cur = cur.right;
        }
    }
    return cur;
}
```

## Insert into a Binary Search Tree(Medium #701)

**Question**: You are given the `root` node of a binary search tree (BST) and a `value` to insert into the tree. Return *the root node of the BST after the insertion*. It is **guaranteed** that the new value does not exist in the original BST.

**Notice** that there may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return **any of them**.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/05/insertbst.jpg)

```
Input: root = [4,2,7,1,3], val = 5
Output: [4,2,7,1,3,5]
Explanation: Another accepted tree is:
```

**Example 2:**

```
Input: root = [40,20,60,10,30,50,70], val = 25
Output: [40,20,60,10,30,50,70,null,null,25]
```

**Example 3:**

```
Input: root = [4,2,7,1,3,null,null,null,null,null,null], val = 5
Output: [4,2,7,1,3,5]
```

**Constraints:**

-   The number of nodes in the tree will be in the range `[0, 104]`.
-   `-108 <= Node.val <= 108`
-   All the values `Node.val` are **unique**.
-   `-108 <= val <= 108`
-   It's **guaranteed** that `val` does not exist in the original BST.

### My Solution

```java
public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null){
        return new TreeNode(val);
    }
    else if (root.val < val){
        root.right = insertIntoBST(root.right, val); 
    }
    else {
        root.left = insertIntoBST(root.left, val); 
    }
    return root;
}
```

### Standard Solution

#### Solution #1 Recursion

*   Same as my solution
*   Tim complexity and space complexity are both $O(H)$

#### Solution #2 Iterative Solution

```java
public TreeNode insertIntoBST(TreeNode root, int val) {
    TreeNode node = root;
    while (node != null) {
      // insert into the right subtree
      if (val > node.val) {
        // insert right now
        if (node.right == null) {
          node.right = new TreeNode(val);
          return root;
        }
        else node = node.right;
      }
      // insert into the left subtree
      else {
        // insert right now
        if (node.left == null) {
          node.left = new TreeNode(val);
          return root;
        }
        else node = node.left;
      }
    }
    return new TreeNode(val);
}
```

*   Time complexity : $O(H)$. Worst case is $O(n)$
*   Space complexity: $\mathcal{O}(1)$ since it's a constant space solution.

## Delete Node in a BST(Medium #450)

**Question**: Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:

1.  Search for a node to remove.
2.  If the node is found, delete the node.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/09/04/del_node_1.jpg)

```
Input: root = [5,3,6,2,4,null,7], key = 3
Output: [5,4,6,2,null,null,7]
Explanation: Given key to delete is 3. So we find the node with value 3 and delete it.
One valid answer is [5,4,6,2,null,null,7], shown in the above BST.
Please notice that another valid answer is [5,2,6,null,4,null,7] and it's also accepted.
```

**Example 2:**

```
Input: root = [5,3,6,2,4,null,7], key = 0
Output: [5,3,6,2,4,null,7]
Explanation: The tree does not contain a node with value = 0.
```

**Example 3:**

```
Input: root = [], key = 0
Output: []
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 104]`.
-   `-105 <= Node.val <= 105`
-   Each node has a **unique** value.
-   `root` is a valid binary search tree.
-   `-105 <= key <= 105`

### My Solution

```java
public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null){
        return root;
    }
    if (key < root.val){
        root.left = deleteNode(root.left, key);
    }
    else if (key > root.val){
        root.right = deleteNode(root.right, key);
    }
    // we find the node
    else {
        // if there are two children
        if (root.left != null && root.right != null){
            int successorData = findMin(root.right);
            root.val = successorData;
            root.right = deleteNode(root.right, successorData);
        }
        // no right child
        else if (root.left != null){
            root = root.left;
        }
        // no left child
        else {
            root = root.right;
        }
    }
    return root;
}

public int findMin(TreeNode root){
    while(root.left != null){
        root = root.left;
    }
    return root.val;
}
```

*   Use the recursion method and the properties of the binary search tree
*   In two children's cases, need to find the leftmost node of the right children. 

### Standard Solution

#### Solution #1 Recursion

*   Almost the same as my solution
*   Time complexity : $\mathcal{O}(\log N)$. During the algorithm execution, we go down the tree all the time - on the left or on the right, first to search the node to delete $\mathcal{O}(H_1)$ time complexity as already [discussed](https://leetcode.com/articles/insert-into-a-bst/)) and then to actually delete it. $H_1$ is a tree height from the root to the node to delete. Delete process takes $\mathcal{O}(H_2)$ time, where $H_2$ is a tree height from the root to delete the leaves. That in total results in $\mathcal{O}(H_1 + H_2) = \mathcal{O}(H)$ time complexity, where $H$ is a tree height, equal to $\log N$ in the case of the balanced tree.
*   Space complexity: $\mathcal{O}(H)$ to keep the recursion stack, where $H$ is a tree height. $H = \log N$ for the balanced tree.