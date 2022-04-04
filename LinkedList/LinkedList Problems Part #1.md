# LinkedList Problems Part #1

## Add Two Numbers(Medium #2)

**Question**: You are given two **non-empty** linked lists representing two non-negative integers. The digits are stored in **reverse order**, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

 **Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/10/02/addtwonumber1.jpg" alt="img" style="zoom: 50%;" />

```
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
```

**Example 2:**

```
Input: l1 = [0], l2 = [0]
Output: [0]
```

**Example 3:**

```
Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]
```

 **Constraints:**

- The number of nodes in each linked list is in the range `[1, 100]`.
- `0 <= Node.val <= 9`
- It is guaranteed that the list represents a number that does not have leading zeros.

### My Solution(Wrong solution)

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    long l1Num = transferToInt(l1);
    long l2Num = transferToInt(l2);
    long sum = l1Num + l2Num;
    return transferToNode(sum);
}

public long transferToInt(ListNode node){
    ListNode current = node;
    int exp = 0;
    long number = 0;
    int nodeVal = 0;

    while(current != null){
        nodeVal = current.val;
        number += (long)nodeVal*Math.pow(10, exp);
        current = current.next;
        exp++;
    }
    return number;
}

public ListNode transferToNode(long num){
    long digit = num % 10;
    ListNode head = new ListNode((int)digit);
    ListNode current = head;
    while(num/10 != 0){
        num = num / 10;
        digit = num % 10;
        current.next = new ListNode((int)digit);
        current = current.next;
    }
    return head;
}
```

* Do not transfer to number then transfer to node since **don't know the number size**
* More complicated than other methods
* Can have problems when casting

### Standard Solution

#### Solution #1 LinkedList

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2){
    int carry = 0;
    ListNode emptyNode = new ListNode();
    ListNode currentNode = new ListNode();
    ListNode result = currentNode;
    while(emptyNode != l1 || emptyNode != l2){
        int sum = l1.val + l2.val + carry;
        carry = sum / 10;
        currentNode = (currentNode.next = new ListNode(sum % 10));
        l1 = null != l1.next ? l1.next : emptyNode;
        l2 = null != l2.next ? l2.next : emptyNode;
    }
    if (carry != 0) currentNode.next = new ListNode(carry);
    return result.next;
}
```

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2){
    ListNode dummyHead = new ListNode(0);
    ListNode p = l1, q = l2, curr = dummyHead;
    int carry = 0;
    while (p != null || q != null){
        int x = (p != null) ? p.val : 0;
        int y = (q != null) ? q.val : 0;
        int sum = carry + x + y;
        carry = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
        if (p != null) p = p.next;
        if (q != null) q = q.next;
    }
    if (carry > 0) curr.next = new ListNode(carry);
    return dummyHead.next;
}
```

* Link the nodes together
* Consider the carry number and put it in the next loop calculation
* Ternary operator
* Return `head.next`
* Time complexity : $O(\max(m, n))$. Assume that m and n represent the length of l1 and l2 respectively, the algorithm above iterates at most $\max(m, n)$ times.
* Space complexity : $O(\max(m, n))$. The length of the new list is at most $\max(m,n) + 1$.

## Swapping Nodes in a Linked List(Medium #1721)

**Question**: You are given the `head` of a linked list, and an integer `k`.

Return *the head of the linked list after **swapping** the values of the* `kth` *node from the beginning and the* `kth` *node from the end (the list is **1-indexed**).*

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/09/21/linked1.jpg)

```
Input: head = [1,2,3,4,5], k = 2
Output: [1,4,3,2,5]
```

**Example 2:**

```
Input: head = [7,9,6,6,7,8,3,0,9,5], k = 5
Output: [7,9,6,6,8,7,3,0,9,5]
```

**Constraints:**

-   The number of nodes in the list is `n`.
-   `1 <= k <= n <= 105`
-   `0 <= Node.val <= 100`

### My Solution

*   First, calculate the size of the linked list
*   Find the kth elements and kth elements from the end, then swap the values.

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
class Solution {
    public ListNode swapNodes(ListNode head, int k) {
        ListNode current = head;
        int size = 0;
        while(current.next != null){
            size++;
            current = current.next;
        }
        size++;
        // find the kth node and kth node from the end
        ListNode kth = new ListNode();
        ListNode kthEnd = new ListNode();
        
        current = head;
        int index = 1;
        while(index <= size){
            if (index == k){
                kth = current;
            }
            if (index == size - k + 1){
                kthEnd = current;
            }
            current = current.next;
            index++;
        }
        // swap the value
        int temp = kth.val;
        kth.val = kthEnd.val;
        kthEnd.val = temp;
        return head;
    }
}
```

### Standard Solution

#### Solution #1 Three-Pass Approach / Two-pass Approach

*   Same as my solution

*   Time Complexity: $\mathcal{O}(n)$, where n is the length of the Linked List. We are iterating over the Linked List twice.

    In the first pass, we are finding the length of the Linked List and setting the `frontNode` which would take $\mathcal{O}(n)$ time.

    In the second pass, we are setting the `endNode` by iterating `n - k` times.

    Thus, the total time complexity would be $\mathcal{O}(n) + \mathcal{O}(n - k)$ which is equivalent to $\mathcal{O}(n)$.

*   Space Complexity: $\mathcal{O}(1)$, as we are using constant extra space to maintain list node pointers `frontNode`, `endNode` and `currentNode`.

#### Solution #2 Single-pass Approach

<img src="https://leetcode.com/problems/swapping-nodes-in-a-linked-list/Documents/5652/SinglePassApproach.svg" alt="img" style="zoom: 33%;" />

*   Create an end node and keep the distance between the pointer and end node is k
*   **Algorithm**:
    *   Start iterating from the `head` of the Linked List until the end using a pointer `currentNode`.
    *   Keep track of the number of nodes traversed so far using the variable `listLength`. The `listLength` is incremented by 11 as each node is traversed.
    *   If `listLength` is equal to `k`, we know that `currentNode` is pointing to the k^{th}*k**t**h* node from the beginning. Set `frontNode` to point to the k^{th}*k**t**h* node. Also, at this point, initialize `endNode` to point at the `head` of the linked list. Now we know that `endNode` is `k` nodes behind the `head` node.
    *   If `endNode` is not null, we know that it is positioned `k` nodes behind the `currentNode` and so we increment `endNode` in addition to `currentNode`. When `currentNode` reaches the end of the list, `endNode` would be pointing at a node which is `k` nodes behind the last node.
    *   Swap the values of `frontNode` and `endNode` using temporary variable `temp`.
    *   Return the `head` node.

```java
  public ListNode swapNodes(ListNode head, int k) {
        int listLength = 0;
        ListNode frontNode = null;
        ListNode endNode = null;
        ListNode currentNode = head;
        // set the front node and end node in single pass
        while (currentNode != null) {
            listLength++;
            if (endNode != null)
                endNode = endNode.next;
            // check if we have reached kth node
            if (listLength == k) {
                frontNode = currentNode;
                endNode = head;
            }
            currentNode = currentNode.next;
        }
        // swap the values of front node and end node
        int temp = frontNode.val;
        frontNode.val = endNode.val;
        endNode.val = temp;
        return head;
    }
```

*   Time and space complexity is same as previous problem
