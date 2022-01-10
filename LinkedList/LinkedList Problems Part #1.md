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
* Time complexity : $O(\max(m, n))$. Assume that m and n represents the length of l1 and l2 respectively, the algorithm above iterates at most $\max(m, n)$ times.
* Space complexity : $O(\max(m, n))$. The length of the new list is at most $\max(m,n) + 1$.
