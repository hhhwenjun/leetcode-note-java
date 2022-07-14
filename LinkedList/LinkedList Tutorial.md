# LinkedList Tutorial

## Two-Pointer in Linked List

*   Check if the linked list has a cycle
    *   Imagine there are two runners with different speed. If they are running on a straight path, the fast runner will first arrive at the destination. However, if they are running on a circular track, the fast runner will catch up with the slow runner if they keep running.
*   What should be the proper speed for the two pointers?
    *   It is a safe choice to move the slow pointer `one step` at a time while moving the fast pointer `two steps` at a time.

## Linked List Cycle (Easy #141)

**Question**: Given `head`, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to. **Note that `pos` is not passed as a parameter**.

Return `true` *if there is a cycle in the linked list*. Otherwise, return `false`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

```
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test2.png)

```
Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.
```

**Example 3:**

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test3.png)

```
Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.
```

**Constraints:**

-   The number of the nodes in the list is in the range `[0, 104]`.
-   `-105 <= Node.val <= 105`
-   `pos` is `-1` or a **valid index** in the linked-list.

### My Solution

```java
public boolean hasCycle(ListNode head) {
    if (head == null) return false;
    ListNode fast = head.next;
    ListNode slow = head;

    while(slow != fast){
        if (fast == null || fast.next == null){
            return false;
        }
        fast = fast.next.next;
        slow = slow.next;
    }
    return true;
}
```

*   Fast and slow runner, if fast can go to the end and does not encounter slow one, it does not have cycle
*   If the fast runner reach the slow runner, it means there is a cycle

*   Time complexity: $O(n)$. 
*   Space complexity: $O(1)$. We only use two nodes (slow and fast) so the space complexity is $O(1)$.
*   It is the **best solution**

### Standard Solution

#### Solution #1 HashSet

*   To detect if a list is cyclic, we can check whether a node had been visited before. A natural way is to use a hash table.

```java
public boolean hasCycle(ListNode head) {
    Set<ListNode> nodesSeen = new HashSet<>();
    while (head != null) {
        if (nodesSeen.contains(head)) {
            return true;
        }
        nodesSeen.add(head);
        head = head.next;
    }
    return false;
}
```

*   Time complexity: $O(n)$. We visit each of the n*n* elements in the list at most once. Adding a node to the hash table costs only $O(1)$ time.
*   Space complexity: $O(n)$. The space depends on the number of elements added to the hash table, which contains at most n*n* elements.

## Linked List Cycle II (Medium #142)

**Question**: Given the `head` of a linked list, return *the node where the cycle begins. If there is no cycle, return* `null`.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to (**0-indexed**). It is `-1` if there is no cycle. **Note that** `pos` **is not passed as a parameter**.

**Do not modify** the linked list.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

```
Input: head = [3,2,0,-4], pos = 1
Output: tail connects to node index 1
Explanation: There is a cycle in the linked list, where tail connects to the second node.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test2.png)

```
Input: head = [1,2], pos = 0
Output: tail connects to node index 0
Explanation: There is a cycle in the linked list, where tail connects to the first node.
```

**Example 3:**

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist_test3.png)

```
Input: head = [1], pos = -1
Output: no cycle
Explanation: There is no cycle in the linked list.
```

**Constraints:**

-   The number of the nodes in the list is in the range `[0, 104]`.
-   `-105 <= Node.val <= 105`
-   `pos` is `-1` or a **valid index** in the linked-list.

**Follow up:** Can you solve it using `O(1)` (i.e. constant) memory?

### My Solution

```java
public ListNode detectCycle(ListNode head) {
    ListNode curr = head;
    if (curr == null){
        return null;
    }
    Set<ListNode> set = new HashSet<>();
    while(curr.next != null){
        if (set.contains(curr)){
            return curr;
        }
        set.add(curr);
        curr = curr.next;
    }
    return null;
}
```

*   One of the standard solution, time and space complexity is $O(n)$

*   Official solution is below:

```java
public ListNode detectCycle(ListNode head) {
    Set<ListNode> visited = new HashSet<ListNode>();

    ListNode node = head;
    while (node != null) {
        if (visited.contains(node)) {
            return node;
        }
        visited.add(node);
        node = node.next;
    }

    return null;
}
```

### Standard Solution

#### Solution #1 Two Pointer

*   What happens when a fast runner (a hare) races a slow runner (a tortoise) on a circular track? At some point, the fast runner will catch up to the slow runner from behind.

<img src="https://leetcode.com/problems/linked-list-cycle-ii/Figures/142/diagram.png" alt="Phase 2 diagram" style="zoom:50%;" />

*   Given that phase 1 finds an intersection, phase 2 proceeds to find the node that is the entrance to the cycle. To do so, we initialize two more pointers: `ptr1`, which points to the head of the list, and `ptr2`, which points to the intersection. Then, we advance each of them by 1 until they meet; the node where they meet is the entrance to the cycle, so we return it.

```java
private ListNode getIntersect(ListNode head) {
    ListNode tortoise = head;
    ListNode hare = head;

    // A fast pointer will either loop around a cycle and meet the slow
    // pointer or reach the `null` at the end of a non-cyclic list.
    while (hare != null && hare.next != null) {
        tortoise = tortoise.next;
        hare = hare.next.next;
        if (tortoise == hare) {
            return tortoise;
        }
    }
    return null;
}

public ListNode detectCycle(ListNode head) {
    if (head == null) {
        return null;
    }

    // If there is a cycle, the fast/slow pointers will intersect at some
    // node. Otherwise, there is no cycle, so we cannot find an entrance to
    // a cycle.
    ListNode intersect = getIntersect(head);
    if (intersect == null) {
        return null;
    }

    // To find the entrance to the cycle, we have two pointers traverse at
    // the same speed -- one from the front of the list, and the other from
    // the point of intersection.
    ListNode ptr1 = head;
    ListNode ptr2 = intersect;
    while (ptr1 != ptr2) {
        ptr1 = ptr1.next;
        ptr2 = ptr2.next;
    }

    return ptr1;
}
```

*   Time complexity: $O(n)$
*   Space complexity: $O(1)$. Floyd's Tortoise and Hare algorithm allocates only pointers, so it runs with constant overall memory usage.

## Intersection of Two Linked Lists (Easy #160)

**Question**: Given the heads of two singly linked-lists `headA` and `headB`, return *the node at which the two lists intersect*. If the two linked lists have no intersection at all, return `null`.

For example, the following two linked lists begin to intersect at node `c1`:

![img](https://assets.leetcode.com/uploads/2021/03/05/160_statement.png)

The test cases are generated such that there are no cycles anywhere in the entire linked structure.

**Note** that the linked lists must **retain their original structure** after the function returns.

**Custom Judge:**

The inputs to the **judge** are given as follows (your program is **not** given these inputs):

-   `intersectVal` - The value of the node where the intersection occurs. This is `0` if there is no intersected node.
-   `listA` - The first linked list.
-   `listB` - The second linked list.
-   `skipA` - The number of nodes to skip ahead in `listA` (starting from the head) to get to the intersected node.
-   `skipB` - The number of nodes to skip ahead in `listB` (starting from the head) to get to the intersected node.

The judge will then create the linked structure based on these inputs and pass the two heads, `headA` and `headB` to your program. If you correctly return the intersected node, then your solution will be **accepted**.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/03/05/160_example_1_1.png)

```
Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
Output: Intersected at '8'
Explanation: The intersected node's value is 8 (note that this must not be 0 if the two lists intersect).
From the head of A, it reads as [4,1,8,4,5]. From the head of B, it reads as [5,6,1,8,4,5]. There are 2 nodes before the intersected node in A; There are 3 nodes before the intersected node in B.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/03/05/160_example_2.png)

```
Input: intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
Output: Intersected at '2'
Explanation: The intersected node's value is 2 (note that this must not be 0 if the two lists intersect).
From the head of A, it reads as [1,9,1,2,4]. From the head of B, it reads as [3,2,4]. There are 3 nodes before the intersected node in A; There are 1 node before the intersected node in B.
```

**Example 3:**

![img](https://assets.leetcode.com/uploads/2021/03/05/160_example_3.png)

```
Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
Output: No intersection
Explanation: From the head of A, it reads as [2,6,4]. From the head of B, it reads as [1,5]. Since the two lists do not intersect, intersectVal must be 0, while skipA and skipB can be arbitrary values.
Explanation: The two lists do not intersect, so return null.
```

**Constraints:**

-   The number of nodes of `listA` is in the `m`.
-   The number of nodes of `listB` is in the `n`.
-   `1 <= m, n <= 3 * 104`
-   `1 <= Node.val <= 105`
-   `0 <= skipA < m`
-   `0 <= skipB < n`
-   `intersectVal` is `0` if `listA` and `listB` do not intersect.
-   `intersectVal == listA[skipA] == listB[skipB]` if `listA` and `listB` intersect.

### My Solution

*   It is a hashset solution, it is also a standard solution
*   Time complexity: $O(N + M)$.
*   Space complexity: $O(M)$

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    Set<ListNode> set = new HashSet<>();
    if (headA == null || headB == null){
        return null;
    }
    ListNode currA = headA;
    while(currA != null){
        set.add(currA);
        currA = currA.next;
    }
    ListNode currB = headB;
    while(currB != null){
        if (set.contains(currB)){
            return currB;
        }
        currB = currB.next;
    }
    return null;
}
```

### Standard Solution

#### Solution #1 Two Pointers

![Diagram showing that one pointer could go over a + c + b while the other goes over b + c + a, and then both will end up on the intersection node.](https://leetcode.com/problems/intersection-of-two-linked-lists/Figures/160/image4.png)

*   If we say that c is the *shared* part, a*a* is *exclusive part of list A* and b is *exclusive part of list B*, then we can have one pointer that goes over `a + c + b` and the other that goes over `b + c + a`. Have a look at the diagram below, and this should be fairly intuitive.

*   Algorithm
    *   Set pointer `pA` to point at `headA`.
    *   Set pointer `pB` to point at `headB`.
    *   While `pA`  and `pB` are not pointing at the same node:
        -   If `pA` is pointing to a null, set `pA` to point to `headB`.
        -   Else, set `pA` to point at `pA.next`.
        -   If `pB` is pointing to a null, set `pB` to point to `headA`.
        -   Else, set `pB` to point at `pB.next`.
    *   return the value pointed to by `pA` (or by `pB`; they're the same now).

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    ListNode pA = headA;
    ListNode pB = headB;
    while (pA != pB) {
        pA = pA == null ? headB : pA.next;
        pB = pB == null ? headA : pB.next;
    }
    return pA;
    // Note: In the case lists do not intersect, the pointers for A and B
    // will still line up in the 2nd iteration, just that here won't be
    // a common node down the list and both will reach their respective ends
    // at the same time. So pA will be NULL in that case.
}
```

*   Time complexity: $O(N + M)$
*   Space complexity: $O(1)$

*   Another way is to connect the last node to head of one list (such as A). Then make it a problem of Linked List Cycle II(then start from B for hare and tortoise problem).

## Remove Nth Node From End of List (Medium #19)

**Question**: Given the `head` of a linked list, remove the `nth` node from the end of the list and return its head.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/03/remove_ex1.jpg)

```
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]
```

**Example 2:**

```
Input: head = [1], n = 1
Output: []
```

**Example 3:**

```
Input: head = [1,2], n = 1
Output: [1] 
```

**Constraints:**

-   The number of nodes in the list is `sz`.
-   `1 <= sz <= 30`
-   `0 <= Node.val <= 100`
-   `1 <= n <= sz`

**Follow up:** Could you do this in one pass?

### My Solution

*   It is a one-pass solution with $O(n)$ time compelxity and $O(1)$ space complexity

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    // two pointers: fast pointer, slow pointer
    ListNode dummyHead = new ListNode();
    int distance = n;
    dummyHead.next = head;

    ListNode fast = dummyHead;
    for (int i = 0; i < distance; i++){
        fast = fast.next;
    }
    ListNode slow = dummyHead;

    // when fast pointer go to the end of linkedlist, 
    // slow pointer in the node before target
    while(fast.next != null){
        fast = fast.next;
        slow = slow.next;
    }
    // remove the nth node from the end
    slow.next = slow.next.next;
    return dummyHead.next;
}
```

### Standard Solution

*   Use a dummy head since the index does not start from 0(**important**). It would greatly help reduce the chaos in solving the problem.
*   Need to locate the node **before the target** for remove, not only find the target

#### Solution #1 Two-pass Algorithm

*   Loop through the list to count the length and locate the node, then pass it again for removal

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    int length  = 0;
    ListNode first = head;
    while (first != null) {
        length++;
        first = first.next;
    }
    length -= n;
    first = dummy;
    while (length > 0) {
        length--;
        first = first.next;
    }
    first.next = first.next.next;
    return dummy.next;
}
```

-   Time complexity: $O(L)$. The algorithm makes two traversal of the list, first to calculate list length L and second to find the $(L - n)$ th node. There are $2L-n$ operations and time complexity is $O(L)$.
-   Space complexity: $O(1)$. We only used constant extra space.

#### Solution #2 One-pass Algorithm

*   Two pointer, one fast one slow. One pointer is n ahead of the slow pointer.

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode first = dummy;
    ListNode second = dummy;
    // Advances first pointer so that the gap between first and second is n nodes apart
    for (int i = 1; i <= n + 1; i++) {
        first = first.next;
    }
    // Move first to the end, maintaining the gap
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    second.next = second.next.next;
    return dummy.next;
}
```

*   Time and space complexity is same as the last problem, but better