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

## Remove Linked List Elements (Easy #203)

**Question**: Given the `head` of a linked list and an integer `val`, remove all the nodes of the linked list that has `Node.val == val`, and return *the new head*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/03/06/removelinked-list.jpg)

```
Input: head = [1,2,6,3,4,5,6], val = 6
Output: [1,2,3,4,5]
```

**Example 2:**

```
Input: head = [], val = 1
Output: []
```

**Example 3:**

```
Input: head = [7,7,7,7], val = 7
Output: []
```

**Constraints:**

-   The number of nodes in the list is in the range `[0, 104]`.
-   `1 <= Node.val <= 50`
-   `0 <= val <= 50`

### My Solution

```java
public ListNode removeElements(ListNode head, int val) {
    if (head == null || (head.next == null && head.val == val)){
        return null;
    }
    ListNode dummyHead = new ListNode();
    dummyHead.next = head;
    ListNode prev = dummyHead;
    ListNode curr = dummyHead.next;

    while(curr != null){
        if (curr.val == val){
            prev.next = curr.next;
            curr = curr.next;
            continue;
        }
        curr = curr.next;
        prev = prev.next;
    }
    return dummyHead.next;
}
```

### Standard Solution

*   The problem seems to be very easy if one has to delete a node in the middle:
    -   Pick the node-predecessor `prev` of the node to delete.
    -   Set its next pointer to point to the node next to the one to delete.
*   Things are more complicated when the node or nodes to delete are in the head of the linked list.

### Standard Solution 

#### Solution #1 Sentinel Node

*   Similar to my solution but simplified

```java
public ListNode removeElements(ListNode head, int val){
	ListNode sentinel = new ListNode(0);
    sentinel.next = head;
    
    ListNode prev = sentinel, curr = head;
    while(curr != null){
        if (curr.val == val){
            prev.next = curr.next;
        }
        else {
            prev = curr;
        }
        curr = curr.next;
    }
    return sentinel.next;
}
```

-   Time complexity: $\mathcal{O}(N)$, it's one pass solution.
-   Space complexity: $\mathcal{O}(1)$, it's a constant space solution.

## Odd Even Linked List (Medium #328)

**Question**: Given the `head` of a singly linked list, group all the nodes with odd indices together followed by the nodes with even indices, and return *the reordered list*.

The **first** node is considered **odd**, and the **second** node is **even**, and so on.

Note that the relative order inside both the even and odd groups should remain as it was in the input.

You must solve the problem in `O(1)` extra space complexity and `O(n)` time complexity.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/03/10/oddeven-linked-list.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [1,2,3,4,5]
Output: [1,3,5,2,4]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/03/10/oddeven2-linked-list.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [2,1,3,5,6,4,7]
Output: [2,3,6,7,1,5,4]
```

**Constraints:**

-   The number of nodes in the linked list is in the range `[0, 104]`.
-   `-106 <= Node.val <= 106`

### My Solution

*   It looks hard if you only consider modifying the list within the list itself(like using swap). But much easier if you consider constructing two linked lists, one is for even index, and one is for odd index. Then contact the two linked lists. 

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {
        ListNode curr = head;
        
        // initialize two new linked list
        ListNode dummyOdd = new ListNode();
        ListNode dummyEven = new ListNode();
        ListNode currOdd = dummyOdd;
        ListNode currEven = dummyEven;
        
        // categorize the nodes to each linked list
        int i = 1;
        while(curr != null){
            if (i % 2 == 0){
                currEven.next = curr;
                currEven = currEven.next;
            }
            else {
                currOdd.next = curr;
                currOdd = currOdd.next;
            }
            curr = curr.next;
            i++;
        }
        
        // linked two lists
        currOdd.next = dummyEven.next;
        currEven.next = null; // cut the excessive node (may contain more odd node)
        return dummyOdd.next;
    }
}
```

*   It is not fast enough but more readable
*   Time complexity is $O(n)$ and the space complexity is also $O(n)$

### Standard Solution

#### Solution #1 Modify in-place

```JAVA
public ListNode oddEvenList(ListNode head) {
    if (head == null) return null;
    ListNode odd = head, even = head.next, evenHead = even;
    while (even != null && even.next != null) {
        odd.next = even.next;
        odd = odd.next;
        even.next = odd.next;
        even = even.next;
    }
    odd.next = evenHead;
    return head;
}
```

-   Time complexity: $O(n)$. There is total of n nodes, and we visit each node once.
-   Space complexity: $O(1)$. All we need is the four-pointers.

## Palindrome Linked List (Easy #234)

**Question**: Given the `head` of a singly linked list, return `true` if it is a palindrome.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/03/03/pal1linked-list.jpg)

```
Input: head = [1,2,2,1]
Output: true
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/03/03/pal2linked-list.jpg)

```
Input: head = [1,2]
Output: false
```

**Constraints:**

-   The number of nodes in the list is in the range `[1, 105]`.
-   `0 <= Node.val <= 9`

**Follow up:** Could you do it in `O(n)` time and `O(1)` space?

### My Solution

```java
public boolean isPalindrome(ListNode head) {
    if (head == null){
        return true;
    }
    // reverse the second half of the list
    ListNode firstHalfEnd = findSecondHalf(head);
    ListNode secondHalfStart = reverseList(firstHalfEnd.next);

    // check if palindrome
    ListNode currFirst = head;
    ListNode currSecond = secondHalfStart;
    boolean isPalindrome = true;

    while(currSecond != null && isPalindrome){
        if (currFirst.val != currSecond.val){
            isPalindrome = false;
            break;
        }
        currFirst = currFirst.next;
        currSecond = currSecond.next;
    }
    return isPalindrome;
}

// reverse the linked list
public ListNode reverseList(ListNode head){
    ListNode prev = null;
    ListNode curr = head;
    while(curr != null){
        ListNode tempNode = curr.next;
        curr.next = prev;
        prev = curr;
        curr = tempNode;
    }
    return prev;
}

public ListNode findSecondHalf(ListNode head){
    ListNode fast = head;
    ListNode slow = head;
    while(fast.next != null && fast.next.next != null){
        fast = fast.next.next;
        slow = slow.next;
    }
    return slow;
}
```

### Standard Solution

#### Solution #1 Copy to List

```java
public boolean isPalindrome(ListNode head) {
    List<Integer> vals = new ArrayList<>();

    // Convert LinkedList into ArrayList.
    ListNode currentNode = head;
    while (currentNode != null) {
        vals.add(currentNode.val);
        currentNode = currentNode.next;
    }

    // Use two-pointer technique to check for palindrome.
    int front = 0;
    int back = vals.size() - 1;
    while (front < back) {
        // Note that we must use ! .equals instead of !=
        // because we are comparing Integer, not int.
        if (!vals.get(front).equals(vals.get(back))) {
            return false;
        }
        front++;
        back--;
    }
    return true;
}
```

*   Time and space complexity is both $O(n)$

#### Solution #2 Recursion

```java
private ListNode frontPointer;

private boolean recursivelyCheck(ListNode currentNode) {
    if (currentNode != null) {
        if (!recursivelyCheck(currentNode.next)) return false;
        if (currentNode.val != frontPointer.val) return false;
        frontPointer = frontPointer.next;
    }
    return true;
}

public boolean isPalindrome(ListNode head) {
    frontPointer = head;
    return recursivelyCheck(head);
}
```

*   Time and space complexity is both $O(n)$

#### Solution #3 Reverse second half in-place

*   Same as my solution

```java
public boolean isPalindrome(ListNode head) {

    if (head == null) return true;

    // Find the end of first half and reverse second half.
    ListNode firstHalfEnd = endOfFirstHalf(head);
    ListNode secondHalfStart = reverseList(firstHalfEnd.next);

    // Check whether or not there is a palindrome.
    ListNode p1 = head;
    ListNode p2 = secondHalfStart;
    boolean result = true;
    while (result && p2 != null) {
        if (p1.val != p2.val) result = false;
        p1 = p1.next;
        p2 = p2.next;
    }        

    // Restore the list and return the result.
    firstHalfEnd.next = reverseList(secondHalfStart);
    return result;
}

// Taken from https://leetcode.com/problems/reverse-linked-list/solution/
private ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
        ListNode nextTemp = curr.next;
        curr.next = prev;
        prev = curr;
        curr = nextTemp;
    }
    return prev;
}

private ListNode endOfFirstHalf(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (fast.next != null && fast.next.next != null) {
        fast = fast.next.next;
        slow = slow.next;
    }
    return slow;
}
```

*   Time complexity: $O(n)$, where n is the number of nodes in the Linked List.
*   Space complexity: $O(1)$. We are changing the next pointers for half of the nodes. This was all memory that had already been allocated, so we are not using any extra memory, and therefore it is $O(1)$.

## Flatten a Multilevel Doubly Linked List (Medium #430)

**Question**: You are given a doubly linked list, which contains nodes that have a next pointer, a previous pointer, and an additional **child pointer**. This child pointer may or may not point to a separate doubly linked list, also containing these special nodes. These child lists may have one or more children of their own, and so on, to produce a **multilevel data structure** as shown in the example below.

Given the `head` of the first level of the list, **flatten** the list so that all the nodes appear in a single-level, doubly linked list. Let `curr` be a node with a child list. The nodes in the child list should appear **after** `curr` and **before** `curr.next` in the flattened list.

Return *the* `head` *of the flattened list. The nodes in the list must have **all** of their child pointers set to* `null`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/11/09/flatten11.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
Output: [1,2,3,7,8,11,12,9,10,4,5,6]
Explanation: The multilevel linked list in the input is shown.
After flattening the multilevel linked list it becomes:
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/11/09/flatten2.1jpg" alt="img" style="zoom:50%;" />

```
Input: head = [1,2,null,3]
Output: [1,3,2]
Explanation: The multilevel linked list in the input is shown.
After flattening the multilevel linked list it becomes:
```

**Example 3:**

```
Input: head = []
Output: []
Explanation: There could be empty list in the input.
```

**Constraints:**

-   The number of Nodes will not exceed `1000`.
-   `1 <= Node.val <= 105`

### Standard Solution

#### Solution #1 DFS + Recursion

*   Use a helper method to flatten the list
*   Consider we transfer the child node to the next node; in the helper method, we would need two inputs(curr and prev)
    *   Prev is the prev node, and curr node is the prev node's child node (or normal next node, but in that case would not make much difference)
*   The helper method uses recursion
*   Use a dummy head

```java
public Node flatten(Node head) {
    if(head == null) return head;

    Node dummyHead = new Node(0, null, head, null);
    flatten(dummyHead, head);
    dummyHead.next.prev = null;
    return dummyHead.next;
}

// recursion method
public Node flatten(Node prev, Node curr){
    // actually prev is prev, curr is prev's child
    // connect child and prev as prev's next, prev's true next is way behind
    if (curr == null) return prev;
    
    // for prev and its child, or child list to normal next node
    // if both are normal nodes, nothing is changed
    prev.next = curr;
    curr.prev = prev;

    // check if it has child, if so, continue to connect child as next
    Node tempNext = curr.next;
    Node temp = flatten(curr, curr.child);
    curr.child = null;

    return flatten(temp, tempNext);
}
```

#### Solution #2 DFS by Iteration

*   It is easier to understand normal dfs method
*   Deque's push is added to the front

```java
public Node flatten(Node head){
    if (head == null) return head;
    
    Node pseudoHead = new Node(0, null, head, null);
    Node curr, prev = pseudoHead;
    
    Deque<Node> stack = new ArrayQeque<>();
    stack.push(head);
    
    while(!stack.isEmpty()){
        curr = stack.pop();
        prev.next = curr;
        curr.prev = prev;
        
        if (curr.next != null) stack.push(curr.next);
        if (curr.child != null){
            stack.push(curr.child);
            // remove all child pointers
            curr.child = null;
        }
        prev = curr;
    }
    // detach the pseudo node from result
    pseudoHead.next.prev = null;
    return pseudoHead.next;
}
```

-   Time Complexity: $\mathcal{O}(N)$. The iterative solution has the same time complexity as the recursive.
-   Space Complexity: $\mathcal{O}(N)$. Again, the iterative solution has the same space complexity as the recursive one.

## Reorder List(Medium #143)

**Question**: You are given the head of a singly linked-list. The list can be represented as:

```
L0 → L1 → … → Ln - 1 → Ln
```

*Reorder the list to be on the following form:*

```
L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
```

You may not modify the values in the list's nodes. Only nodes themselves may be changed.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/03/04/reorder1linked-list.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [1,2,3,4]
Output: [1,4,2,3]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/03/09/reorder2-linked-list.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [1,2,3,4,5]
Output: [1,5,2,4,3]
```

**Constraints:**

-   The number of nodes in the list is in the range `[1, 5 * 104]`.
-   `1 <= Node.val <= 1000`

### My Solution

```java
public void reorderList(ListNode head) {
    ListNode secondStart = findSecondHalf(head);
    ListNode firstStart = head;

    // reverse the second half
    secondStart = reverseList(secondStart);

    // each time take one node from 1st half list, and 2nd half list
    ListNode temp;
    while(secondStart.next != null){
        temp = firstStart.next;
        firstStart.next = secondStart;
        firstStart = temp;

        temp = secondStart.next;
        secondStart.next = firstStart;
        secondStart = temp;
    }
}

public ListNode findSecondHalf(ListNode head){
    ListNode fast = head;
    ListNode slow = head;
    while(fast != null && fast.next != null){
        fast = fast.next.next;
        slow = slow.next;
    }
    // slow in the middle, fast in the end
    return slow;
}

public ListNode reverseList(ListNode head){
    ListNode prev = null;
    ListNode curr = head;
    while(curr != null){
        ListNode tempNode = curr.next;
        curr.next = prev;
        prev = curr;
        curr = tempNode;
    }
    return prev;
}
```

### Standard Solution

*   This problem is a combination of these three easy problems:
    -   [Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list).
    -   [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list).
    -   [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists).

*   Almost same as my solution
*   Draw a graph if feeling confused

```java
class Solution {
  public void reorderList(ListNode head) {
    if (head == null) return;

    // find the middle of linked list [Problem 876]
    // in 1->2->3->4->5->6 find 4 
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    // reverse the second part of the list [Problem 206]
    // convert 1->2->3->4->5->6 into 1->2->3->4 and 6->5->4
    // reverse the second half in-place
    ListNode prev = null, curr = slow, tmp;
    while (curr != null) {
      tmp = curr.next;

      curr.next = prev;
      prev = curr;
      curr = tmp;
    }

    // merge two sorted linked lists [Problem 21]
    // merge 1->2->3->4 and 6->5->4 into 1->6->2->5->3->4
    ListNode first = head, second = prev;
    while (second.next != null) {
      tmp = first.next;
      first.next = second;
      first = tmp;

      tmp = second.next;
      second.next = first;
      second = tmp;
    }
  }
}
```

-   Time complexity: $\mathcal{O}(N)$. There are three steps here. Identifying the middle node takes $\mathcal{O}(N)$ time. To reverse the second part of the list, one needs $N/2$ operations. The final step, to merge two lists, requires $N/2$ operations as well. In total, that results in $\mathcal{O}(N)$ time complexity.
-   Space complexity: $\mathcal{O}(1)$, since we do not allocate any additional data structures.