BST Problems Part #1

## Two Sum IV(Easy #653)

**Question**: Given the `root` of a Binary Search Tree and a target number `k`, return *`true` if there exist two elements in the BST such that their sum is equal to the given target*.

 **Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/09/21/sum_tree_1.jpg" alt="img" style="zoom:33%;" />

```
Input: root = [5,3,6,2,4,null,7], k = 9
Output: true
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/09/21/sum_tree_2.jpg" alt="img" style="zoom:33%;" />

```
Input: root = [5,3,6,2,4,null,7], k = 28
Output: false
```

 **Constraints:**

- The number of nodes in the tree is in the range `[1, 104]`.
- `-104 <= Node.val <= 104`
- `root` is guaranteed to be a **valid** binary search tree.
- `-105 <= k <= 105`

### Standard Solution

#### Solution #1 Using HashSet

* Simplest solution is to traverse over the whole tree and consider every pair
* For every node, check if the complement is in the set. Then put it to the set.

```java
public boolean findTarget(TreeNode root, int k){
    Set<Integer> set = new HashSet();
    return find(root, k, set);
}

public boolean find(TreeNode root, int k, Set<Integer> set){
    if (root == null) return false;
    if (set.contains(k - root.val)) return true;
    set.add(root.val);
    return find(root.left, k, set) || find(root.right, k, set);//find left or right
}
```

* Time complexity : $O(n)$. The entire tree is traversed only once in the worst case. Here, n refers to the number of nodes in the given tree.
* Space complexity : $O(n)$. The size of the set can grow up to n in the worst case.

#### Solution #2 BFS and HashSet

* BFS: **Breadth First Search** (traversal)
* **Algorithm**:
  * Remove an element, $p$, from the front of the **queue**.
  * Check if the element $k-p$ already exists in the set. If so, return True.
  * Otherwise, add this element, $p$ to the set. Further, add the right and the left child nodes of the current node to the back of the **queue**.
  * Continue steps 1. to 3. till the **queue** becomes empty.
  * Return false if the **queue** becomes empty.

```java
public boolean findTarget(TreeNode root, int k){
    Set<Integer> set = new HashSet();
    Queue<TreeNode> queue = new LinkedList();//only add, linkedlist is faster
    queue.add(root);
    while(!queue.isEmpty()){
        if (queue.peek() != null){
            TreeNode node = queue.remove();
            if (set.contains(k - node.val)) return true;
            set.add(node.val);
            queue.add(node.right);//put right to the last part of the queue
            queue.add(node.left);//put left to the front of the queue
        } else queue.remove();
    }
    return false;
}
```

* Time complexity : $O(n)$. We need to traverse over the whole tree once in the worst case. Here, n refers to the number of nodes in the given tree.
* Space complexity : $O(n)$. The size of the set can grow at most up to n.

#### Solution #3 Using BST + Two Pointers

* Using BST, in order BST print as a sorted array
* Get the sorted array, apply two pointers method to find the target

```java
public boolean findTarget(TreeNode root, int k){
    List<Integer> list = new ArrayList();//create arraylist to contain the sorted array
    inorder(root, list);
    int l = 0, r = list.size() - 1;
    while(l < r){
        int sum = list.get(l) + list.get(r);
        if (sum == k)return true;
		else if (sum < k) l++;
        else r--;
    }
    return false;
}

public void inorder(TreeNode root, List<Integer> list){
    if (root == null) return;
    inorder(root.left, list);//go left
    list.add(root.val);//operation
    inorder(root.right, list);//then go right
}
```

* Time complexity : $O(n)$. We need to traverse over the whole tree once to do them in-order traversal. Here, n refers to the number of nodes in the given tree.
* Space complexity : $O(n)$. The sorted list will contain n elements.

## Kth Smallest Element in a BST (Medium #230) 

**Question**: Given the `root` of a binary search tree, and an integer `k`, return *the* `kth` *smallest value (**1-indexed**) of all the values of the nodes in the tree*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/28/kthtree1.jpg)

```
Input: root = [3,1,4,null,2], k = 1
Output: 1
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/01/28/kthtree2.jpg)

```
Input: root = [5,3,6,2,4,null,null,1], k = 3
Output: 3
```

**Constraints:**

-   The number of nodes in the tree is `n`.
-   `1 <= k <= n <= 104`
-   `0 <= Node.val <= 104`

**Follow up:** If the BST is modified often (i.e., we can do insert and delete operations) and you need to find the kth smallest frequently, how would you optimize?

### My Solution

```java
public int kthSmallest(TreeNode root, int k) {
    // 1. traverse the tree and put values in a list
    List<Integer> treeList = new ArrayList<>();

    inorder(root, treeList);
    // 2. get the corresponding value in kth location
    return treeList.get(k - 1);

}

public void inorder(TreeNode root, List<Integer> treeList){
    if (root == null) return;
    inorder(root.left, treeList);
    treeList.add(root.val);
    inorder(root.right, treeList);
}
```

### Standard Solution

#### Solution #1 Recursive Inorder Traversal

*   Same as my solution

-   Time complexity: $O(N)$ to build a traversal.
-   Space complexity: $O(N)$ to keep an inorder traversal.

#### Solution #2 Iterative Inorder Traversal

*   With the help of a stack to do the iterative traversal, speed up the solution.
*   One could stop after the kth element.

```java
public int kthSmallest(TreeNode root, int k){
    LinkedList<TreeNode> stack = new LinkedList<>();
    
    while(true){
        while(root != null){
            stack.push(root);
            root = root.left;
        }
        root = stack.pop();
        if (--k == 0) return root.val;
        root = root.right;
    }
}
```

-   Time complexity: $O(H + k)$, where H is a tree height. This complexity is defined by the stack, which contains at least. $H + k$ elements since before starting to pop out, one has to go down to a leaf. This results in $O(\log N + k)$ for the balanced tree and $O(N + k)$ for the completely unbalanced tree with all the nodes in the left subtree.
-   Space complexity: $O(H)$ to keep the stack, where H is a tree height. That makes $O(N)$ in the worst case of the skewed tree and $O(\log N)$ in the average case of the balanced tree.
