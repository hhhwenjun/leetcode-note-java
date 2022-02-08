# Recursion Tutorial

## Introduction

*   Recursion is an approach to solving problems using a function that calls itself a subroutine.

*   The trick is that each time a recursive function calls itself, it reduces the given problem into subproblems. 

*   A recursive function should have the following properties so that it does not result in an infinite loop:

    1.  A simple `base case` (or cases) — a terminating scenario that does not use recursion to produce an answer.
    2.  A set of rules, also known as a `recurrence relation,` reduces all other cases towards the base case.

*   **Example**: Print a string in reverse order.

    ```java
    private static void printReverse(char [] str) {
      helper(0, str);
    }
    
    private static void helper(int index, char [] str) {
      if (str == null || index >= str.length) {
        return;
      }
      helper(index + 1, str);
      System.out.print(str[index]);
    }
    ```

*   **Example**:

    ```
    Given a linked list, swap every two adjacent nodes and return its head.
    
    e.g.  for a list 1-> 2 -> 3 -> 4, one should return the head of list as 2 -> 1 -> 4 -> 3.
    ```

    *   First, we swap the first two nodes in the list, *i.e.* `head` and `head. next`;
    *   Then, we call the function self as `swap(head.next.next)` to swap the rest of the list following the first two nodes.
    *   Finally, we attach the returned head of the sub-list in step (2) with the two nodes swapped in step (1) to form a new linked list.

## Swap Nodes in Pairs(Medium #24)

**Question**: Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/03/swap_ex1.jpg)

```
Input: head = [1,2,3,4]
Output: [2,1,4,3]
```

**Example 2:**

```
Input: head = []
Output: []
```

**Example 3:**

```
Input: head = [1]
Output: [1]
```

**Constraints:**

-   The number of nodes in the list is in the range `[0, 100]`.
-   `0 <= Node.val <= 100`

### My Solution

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }
    ListNode oldHead = head;
    head = head.next;
    oldHead.next = head.next;
    head.next = oldHead;
    oldHead.next = swapPairs(head.next.next);
    return head;
}
```

### Standard Solution

#### Solution #1 Recursive Approach

*   Similar to my solution
*   In every function call we take out two nodes that would be swapped and the remaining nodes are passed to the next recursive call.
*   Each time swap two nodes and similar to sliding window, similar to backtracking
*   Return the head of the two nodes

```java
public ListNode swapPairs(ListNode head) {
    // If the list has no node or has only one node left.
    if ((head == null) || (head.next == null)) {
        return head;
    }
    // Nodes to be swapped
    ListNode firstNode = head;
    ListNode secondNode = head.next;
    // Swapping
    firstNode.next  = swapPairs(secondNode.next);
    secondNode.next = firstNode;
    // Now the head is the second node
    return secondNode;
}
```

*   Time Complexity: $O(N)$ where N is the size of the linked list.
*   Space Complexity: $O(N)$ stack space utilized for recursion.

#### Solution #2 Iterative Approach

*   Swap the nodes on the go

*   After swapping a pair of nodes, say `A` and `B`, we need to link node `B` to the node that was right before `A`. 

    <img src="https://leetcode.com/problems/swap-nodes-in-pairs/Figures/24/24_Swap_Nodes_7.png" alt="img" style="zoom:50%;" />

<img src="https://leetcode.com/problems/swap-nodes-in-pairs/Figures/24/24_Swap_Nodes_8.png" alt="img" style="zoom:50%;" />

```java
public ListNode swapPairs(ListNode head){
    // Dummy node acts as the prevNode for the head node
    // of the list and hence stores pointer to the head node
    ListNode dummy = new ListNode(-1);
    dummy.next = head;
    
    ListNode prevNode = dummy;
    while ((head != null) && (head.next != null)){
        // Nodes to be swapped
        ListNode firstNode = head;
        ListNode secondNode = head.next;
        
        // swapping
        prevNode.next = secondNode;
        firstNode.next = secondNode.next;
        secondNode.next = firstNode;
        
        // Reinitializing the ehad and prevNode for next swap
        prevNode = firstNode;
        head = firstNode.next;//jump
    }
    return dummy.next;
}
```

*   Time Complexity : $O(N)$ where N is the size of the linked list.
*   Space Complexity : $O(1)$.

## Reverse Linked List(Easy #206)

**Question**: Given the `head` of a singly linked list, reverse the list, and return *the reversed list*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/rev1ex1.jpg)

```
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/02/19/rev1ex2.jpg)

```
Input: head = [1,2]
Output: [2,1]
```

**Example 3:**

```
Input: head = []
Output: []
```

**Constraints:**

-   The number of nodes in the list is the range `[0, 5000]`.
-   `-5000 <= Node.val <= 5000`

### Standard Solution

#### Solution #1 Recursion

*   The key of recursion is to work backwards
*   Assume the rest of the list had already been reversed, how to reverse the front part
*   Avoid linking the list as a cycle.

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public ListNode reverseList(ListNode head) {
    // base case
    if (head == null || head.next == null){
        return head;
    }
    ListNode prev = reverseList(head.next); // go to the end
    // reconstruct the head and next relationship
    head.next.next = head;
    head.next = null;
    return prev;
}
```

*   Time complexity : $O(n)$. Assume that n*n* is the list's length, the time complexity is $O(n)$.
*   Space complexity : $O(n)$. The extra space comes from implicit stack space due to recursion. The recursion could go up to n levels deep.

#### Solution #2 Iteration

*   Store its previous element beforehand
*   Need another pointer to store the next node before changing the reference

```java
public ListNode reverseList(ListNode head){
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null){
        ListNode nextTemp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = nextTemp;
    }
    return prev;
}
```

*   Time complexity : $O(n)$. Assume that n is the list's length, the time complexity is $O(n)$.
*   Space complexity : $O(1)$.

## Search in a Binary Search Tree(Easy #700)

**Question**: You are given the `root` of a binary search tree (BST) and an integer `val`.

Find the node in the BST that the node's value equals `val` and return the subtree rooted with that node. If such a node does not exist, return `null`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/12/tree1.jpg)

```
Input: root = [4,2,7,1,3], val = 2
Output: [2,1,3]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/01/12/tree2.jpg)

```
Input: root = [4,2,7,1,3], val = 5
Output: []
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 5000]`.
-   `1 <= Node.val <= 107`
-   `root` is a binary search tree.
-   `1 <= val <= 107`

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
public TreeNode searchBST(TreeNode root, int val){
    if (root == null) return null;
    if (root.val == val) {
        return root;
    }
    return root.val < val ? searchBST(root.right, val) : searchBST(root.left, val);
}
```

### Standard Solution

*   A binary Search Tree is a binary tree where the key in each node
    -   is greater than any key stored in the left sub-tree,
    -   and less than any key stored in the right sub-tree.

#### Solution #1 Recursion

*   If the tree is empty `root == null` or the value to find is here `val == root.val` - return root.
*   If `val < root.val` - go to search into the left subtree `searchBST(root.left, val)`.
*   If `val > root.val` - go to search into the right subtree `searchBST(root.right, val)`.
*   Return `root`.

```java
public TreeNode searchBST(TreeNode root, int val) {
    if (root == null || val == root.val) return root;
    return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
  }
```

*   Time complexity : $\mathcal{O}(H)$, where H*H* is a tree height. That results in $\mathcal{O}(\log N)$ in the average case, and $\mathcal{O}(N)$ in the worst case.
*   Space complexity : $\mathcal{O}(H)$ to keep the recursion stack, i.e. $\mathcal{O}(\log N)$ in the average case, and $\mathcal{O}(N)$ in the worst case.

#### Solution #2 Iteration

*   The concept is the same as the recursion method

```java
public TreeNode searchBST(TreeNode root, int val){
    while(root != null && val != root.val){
        root = val < root.val ? root.left : root.right;
    }
    return root;
}
```

*   Time complexity is the same as recursion, but the space complexity is $O(1)$

