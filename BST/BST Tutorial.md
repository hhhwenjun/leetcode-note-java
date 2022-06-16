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

## kth Largest Element in a Stream(Easy #703)

**Question**: Design a class to find the `kth` largest element in a stream. Note that it is the `kth` largest element in the sorted order, not the `kth` distinct element.

Implement `KthLargest` class:

-   `KthLargest(int k, int[] nums)` Initializes the object with the integer `k` and the stream of integers `nums`.
-   `int add(int val)` Appends the integer `val` to the stream and returns the element representing the `kth` largest element in the stream. 

**Example 1:**

```
Input
["KthLargest", "add", "add", "add", "add", "add"]
[[3, [4, 5, 8, 2]], [3], [5], [10], [9], [4]]
Output
[null, 4, 5, 5, 8, 8]

Explanation
KthLargest kthLargest = new KthLargest(3, [4, 5, 8, 2]);
kthLargest.add(3);   // return 4
kthLargest.add(5);   // return 5
kthLargest.add(10);  // return 5
kthLargest.add(9);   // return 8
kthLargest.add(4);   // return 8
```

**Constraints:**

-   `1 <= k <= 104`
-   `0 <= nums.length <= 104`
-   `-104 <= nums[i] <= 104`
-   `-104 <= val <= 104`
-   At most `104` calls will be made to `add`.
-   It is guaranteed that there will be at least `k` elements in the array when you search for the `kth` element.

### My Solution

```java
// second attempt: heap, but more efficient than standard solution
private PriorityQueue<Integer> heap;
private int k;
public KthLargest(int k, int[] nums) {
    this.k = k;
    heap = new PriorityQueue<Integer>();
    for(int num : nums){
        heap.add(num);
        if (heap.size() > k){
            heap.poll();
        }
    }
}

public int add(int val) {
    heap.add(val);
    if (heap.size() > k){
        heap.poll();
    }
    return heap.peek();
}
```

### Standard Solution

*   Should use heap.
*   If you use other tree structures, need to define nodes and the structure would be more complicated.

#### Solution #1 Heap

*   Heap is a priority queue, you can just simply use Java collection.
*   A heap is a data structure that is capable of giving you the smallest (or largest) element (by some criteria) in constant time, while also being able to add elements and remove the smallest (or largest) element in only logarithmic time. 
*   In summary, a heap:
    -   Stores elements, and can find the smallest (min-heap) or largest (max-heap) element stored in $O(1)$.
    -   Can add elements and remove the smallest (min-heap) or largest (max-heap) element in $O(\log(n))$.
    -   Can perform insertions and removals while always maintaining the first property.

```java
class KthLargest {
    private static int k;
    private PriorityQueue<Integer> heap;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        heap = new PriorityQueue<>();
        
        for (int num : nums){
            heap.offer(num);
        }
        
        while(heap.size() > k){
            heap.poll();
        }
    }
    
    public int add(int val) {
        heap.offer(val);
        if (heap.size() > k){
            heap.poll();
        }
        return heap.peek();
    }
}
```

-   Given N as the length of `nums` and M as the number of calls to `add()`,

-   Time complexity: $O(N \cdot \log(N) + M \cdot \log(k))$

    The time complexity is split into two parts. First, the constructor needs to turn `nums` into a heap of size `k`. In Python, `heapq.heapify()` can turn `nums` into a heap in $O(N)$ time. Then, we need to remove from the heap until there are only `k` elements in it, which means removing `N - k` elements. Since `k` can be, say 1, in terms of big O this is `N` operations, with each operation costing $\log(N)$. Therefore, the constructor costs $O(N + N \cdot \log(N)) = O(N \cdot \log(N))$.

    Next, every call to `add()` involves adding an element to `heap` and potentially removing an element from `heap`. Since our heap is of size `k`, every call to `add()` at worst costs $O(2 * \log(k)) = O(\log(k))$. That means `M` calls to `add()` costs $O(M \cdot \log(k))$.

-   Space complexity: $O(N)$

    The only extra space we use is the `heap`. While during `add()` calls we limit the size of the heap to `k`, in the constructor, we start by converting `nums` into a heap, which means the heap will initially be of size `N`.

#### Solution #2 BST

```java
// find number of right subtree

class KthLargest {
    // insert a node into the BST
    private Node insertNode(Node root, int num) {
        if (root == null) {
            return new Node(num, 1);
        }
        if (root.val < num) {
            root.right = insertNode(root.right, num);
        } else {
            root.left = insertNode(root.left, num);
        }
        root.cnt++;
        return root;
    }

    private int searchKth(Node root, int k) {
        // m = the size of right subtree
        int m = root.right != null ? root.right.cnt : 0;
        // root is the m+1 largest node in the BST
        if (k == m + 1) {
            return root.val;
        }
        if (k <= m) {
            // find kth largest in the right subtree
            return searchKth(root.right, k);
        } else {
            // find (k-m-1)th largest in the left subtree
            return searchKth(root.left, k - m - 1);
        }
    } 
    
    private Node root;
    private int m_k;

    public KthLargest(int k, int[] nums) {
        root = null;
        for (int i = 0; i < nums.length; ++i) {
            root = insertNode(root, nums[i]);
        }
        m_k = k;
    }
    
    public int add(int val) {
        root = insertNode(root, val);
        return searchKth(root, m_k);
    }
}

class Node {    // the structure for the tree node
    Node left;
    Node right;
    int val;
    int cnt;    // the size of the subtree rooted at the node
    public Node (int v, int c) {
        left = null;
        right = null;
        val = v;
        cnt = c;
    }
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */
```

*   By using a BST, the time complexity for insertion and search are both `O(h)`. The time complexity of performing all the operations will be `O(N*h)`. That is, `O(N^2)` in the worst case and `O(NlogN)` ideally. 

## Lowest Common Ancestor of a Binary Search Tree(Easy #235)

**Question**: Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the [definition of LCA on Wikipedia](https://en.wikipedia.org/wiki/Lowest_common_ancestor): “The lowest common ancestor is defined between two nodes `p` and `q` as the lowest node in `T` that has both `p` and `q` as descendants (where we allow **a node to be a descendant of itself**).”

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/14/binarysearchtree_improved.png)

```
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2018/12/14/binarysearchtree_improved.png)

```
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
```

**Example 3:**

```
Input: root = [2,1], p = 2, q = 1
Output: 2
```

**Constraints:**

-   The number of nodes in the tree is in the range `[2, 105]`.
-   `-109 <= Node.val <= 109`
-   All `Node.val` are **unique**.
-   `p != q`
-   `p` and `q` will exist in the BST.

### My Solution

*   Try to find the first node that has value in-between.
*   Fully use the properties of BST.

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
    if (p.val > root.val && q.val > root.val){
            root = lowestCommonAncestor(root.right, p, q);
    }
    else if (p.val < root.val && q.val < root.val){
        root = lowestCommonAncestor(root.left, p, q);
    }
    else {
        return root;
    }
    return root;
}
```

### Standard Solution

#### Solution #1 Recursion

*   Same as my solution
*   Time Complexity: $O(N)$, where N is the number of nodes in the BST. In the worst case, we might be visiting all the nodes of the BST.
*   Space Complexity: $O(N)$. This is because the maximum amount of space utilized by the recursion stack would be N since the height of a skewed BST could be N.

#### Solution #2 Iteration

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // Value of p
        int pVal = p.val;

        // Value of q;
        int qVal = q.val;

        // Start from the root node of the tree
        TreeNode node = root;

        // Traverse the tree
        while (node != null) {

            // Value of ancestor/parent node.
            int parentVal = node.val;

            if (pVal > parentVal && qVal > parentVal) {
                // If both p and q are greater than parent
                node = node.right;
            } else if (pVal < parentVal && qVal < parentVal) {
                // If both p and q are lesser than parent
                node = node.left;
            } else {
                // We have found the split point, i.e. the LCA node.
                return node;
            }
        }
        return null;
    }
}
```

-   Time Complexity: $O(N)$, where N is the number of nodes in the BST. In the worst case, we might be visiting all the nodes of the BST.
-   Space Complexity: $O(1)$.

## Height-Balanced BST

*   The terminology used in trees:

    -   Depth of node - the number of edges from the tree's root node to the node

    -   Height of node - the number of edges on the longest path between that node and a leaf

    -   Height of Tree - the height of its root node

*   A `height-balanced` (or `self-balancing`) binary search tree is a binary search tree that automatically keeps its height small in the face of arbitrary item insertions and deletions. That is, the height of a balanced BST with `N` nodes is always `logN`. Also, the height of the two subtrees of every node never differs by more than 1.
*   The height of a balanced BST with `N` nodes is always `logN`.
*   The` tree set`, `TreeSet` in Java or `set` in C++, is implemented by the height-balanced BST. Therefore, the time complexity of search, insertion, and deletion are all `O(logN)`.

## Balanced Binary Tree(Easy #110)

**Question**: Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as:

>   a binary tree in which the left and right subtrees of *every* node differ in height by no more than 1.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/06/balance_1.jpg)

```
Input: root = [3,9,20,null,null,15,7]
Output: true
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/10/06/balance_2.jpg)

```
Input: root = [1,2,2,3,3,null,null,4,4]
Output: false
```

**Example 3:**

```
Input: root = []
Output: true 
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 5000]`.
-   `-104 <= Node.val <= 104`

### My Solution

```java
 public boolean isBalanced(TreeNode root) {
    if (root == null){
        return true;
    }
    return Math.abs(balanceHelper(root.left) - balanceHelper(root.right)) <= 1
        && isBalanced(root.left)
        && isBalanced(root.right);
}
public int balanceHelper(TreeNode root){
    if (root == null){
        return -1;
    }
    return 1 + Math.max(balanceHelper(root.left), balanceHelper(root.right));
}
```

### Standard Solution

#### Solution #1 Recursion

*   Same as my solution
*   Find the height of the subtrees and compare the value, use && to connect them

*   Time complexity: $O(n \log n)$
*   Space complexity: $\mathcal{O}(n)$. The recursion stack may contain all nodes if the tree is skewed.

## Convert Sorted Array to Binary Search Tree(Easy #108)

**Question**: Given an integer array `nums` where the elements are sorted in **ascending order**, convert *it to a **height-balanced** binary search tree*.

A **height-balanced** binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/18/btree1.jpg)

```
Input: nums = [-10,-3,0,5,9]
Output: [0,-3,9,-10,null,5]
Explanation: [0,-10,5,null,-3,null,9] is also accepted:
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/02/18/btree.jpg)

```
Input: nums = [1,3]
Output: [3,1]
Explanation: [1,null,3] and [3,1] are both height-balanced BSTs.
```

**Constraints:**

-   `1 <= nums.length <= 104`
-   `-104 <= nums[i] <= 104`
-   `nums` is sorted in a **strictly increasing** order.

### My Solution

```java
public TreeNode sortedArrayToBST(int[] nums) {
    // find the root
    int length = nums.length;
    TreeNode root = new TreeNode(nums[length / 2]);
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();
    int index = 0;
    while(index < length/2){
        left.add(nums[index]);
        index++;
    }
    // reverse the left one
    Collections.reverse(left);
    index++;
    while(index < length){
        right.add(nums[index]);
        index++;
    }
    // add to the tree
    for(Integer num : left){
        addToTree(num, root);
    }
    for(Integer num : right){
        addToTree(num, root);
    }
    return root;
}
public TreeNode addToTree(int num, TreeNode root){
    if (root == null){
        return new TreeNode(num);
    }
    if (num > root.val){
        root.right = addToTree(num, root.right);
    }
    else {
        root.left = addToTree(num, root.left);
    }
    return root;
}
```

*   It is a BST, but not with a balanced height
*   Not a passed solution

### Standard Solution

*   It is also about the traversal method
*   Basically, the height-balanced restriction means that at **each step one has to pick up the number in the middle as a root**. That works fine with arrays containing an odd number of elements but there is no predefined choice for arrays with an even number of elements.

#### Solution #1 Preorder Traversal: Always Choose Left Middle Node as a Root

-   Implement helper function `helper(left, right)`, which constructs BST from nums elements between indexes `left` and `right`:
    -   If left > right, then there are no elements available for that subtree. Return None.
    -   Pick left middle element: `p = (left + right) // 2`.
    -   Initiate the root: `root = TreeNode(nums[p])`.
    -   Compute recursively left and right subtrees: `root.left = helper(left, p - 1)`, `root.right = helper(p + 1, right)`.

```java
int[] nums;

public TreeNode helper(int left, int right){
    if (left > right) return null;
    
    // always choose left middle node as a root
    int p = (left + right) / 2;
    
    // preorder traversal: node -> left -> right
    TreeNode root = new TreeNode(nums[p]);
    root.left = helper(left, p - 1);
    root.right = helper(p + 1, right);
    return root;
}
public TreeNode sortedArrayToBST(int[] nums){
    this.nums = nums;
    return helper(0, nums.length - 1);
}
```

-   Time complexity: $O(N)$ since we visit each node exactly once.

-   Space complexity: $O(\log N)$.

    The recursion stack requires $O(\log N)$ space because the tree is height-balanced. Note that the $O(N)$ space used to store the output does not count as auxiliary space, so it is not included in the space complexity.