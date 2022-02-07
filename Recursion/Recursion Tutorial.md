# Recursion Tutorial

## Introduction

*   Recursion is an approach to solving problems using a function that calls itself a subroutine.

*   The trick is that each time a recursive function calls itself, it reduces the given problem into subproblems. 

*   A recursive function should have the following properties so that it does not result in an infinite loop:

    1.  A simple `base case` (or cases) — a terminating scenario that does not use recursion to produce an answer.
    2.  A set of rules, also known as `recurrence relation` reduces all other cases towards the base case.

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

    *   First, we swap the first two nodes in the list, *i.e.* `head` and `head.next`;
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

