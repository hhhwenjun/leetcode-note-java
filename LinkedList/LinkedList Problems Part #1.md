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

### My Solution

```java
// wrong solution
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

```java
// correct solution
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    // each time add up the number
    int carry = 0;
    ListNode dummyHead = new ListNode();
    ListNode curr = dummyHead;

    // take the carry over number to next node
    while(l1 != null || l2 != null){
        int result = 0;
        if (l1 == null) {
            result = l2.val + carry;
        }
        else if (l2 == null){
            result = l1.val + carry;
        }
        else {
            result = l1.val + l2.val + carry;
        }
        int storeValue = result % 10;
        carry = result / 10;

        curr.next = new ListNode(storeValue);
        curr = curr.next;
        l1 = l1 == null ? null : l1.next;
        l2 = l2 == null ? null : l2.next;
    }


    // if at the end of list, carry > 0, add 1 extra node at the end
    if (carry > 0) curr.next = new ListNode(carry);
    return dummyHead.next;
}
```

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

## Design Linked List (Medium #707)

**Question**: Design your implementation of the linked list. You can choose to use a singly or doubly linked list.
A node in a singly linked list should have two attributes: `val` and `next`. `val` is the value of the current node, and `next` is a pointer/reference to the next node.
If you want to use the doubly linked list, you will need one more attribute `prev` to indicate the previous node in the linked list. Assume all nodes in the linked list are **0-indexed**.

Implement the `MyLinkedList` class:

-   `MyLinkedList()` Initializes the `MyLinkedList` object.
-   `int get(int index)` Get the value of the `indexth` node in the linked list. If the index is invalid, return `-1`.
-   `void addAtHead(int val)` Add a node of value `val` before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
-   `void addAtTail(int val)` Append a node of value `val` as the last element of the linked list.
-   `void addAtIndex(int index, int val)` Add a node of value `val` before the `indexth` node in the linked list. If `index` equals the length of the linked list, the node will be appended to the end of the linked list. If `index` is greater than the length, the node **will not be inserted**.
-   `void deleteAtIndex(int index)` Delete the `indexth` node in the linked list, if the index is valid.

**Example 1:**

```
Input
["MyLinkedList", "addAtHead", "addAtTail", "addAtIndex", "get", "deleteAtIndex", "get"]
[[], [1], [3], [1, 2], [1], [1], [1]]
Output
[null, null, null, null, 2, null, 3]

Explanation
MyLinkedList myLinkedList = new MyLinkedList();
myLinkedList.addAtHead(1);
myLinkedList.addAtTail(3);
myLinkedList.addAtIndex(1, 2);    // linked list becomes 1->2->3
myLinkedList.get(1);              // return 2
myLinkedList.deleteAtIndex(1);    // now the linked list is 1->3
myLinkedList.get(1);              // return 3
```

**Constraints:**

-   `0 <= index, val <= 1000`
-   Please do not use the built-in LinkedList library.
-   At most `2000` calls will be made to `get`, `addAtHead`, `addAtTail`, `addAtIndex` and `deleteAtIndex`.

### My Solution

```java
class Node {
    int val;
    Node next;
    Node prev;
    
    public Node(int val){
        this.val = val;
        this.next = null;
        this.prev = null;
    }
    
    public Node(int val, Node next, Node prev){
        this.val = val;
        this.next = next;
        this.prev = prev;
    }
}

class MyLinkedList {
    private Node head;
    private Node curr;
    private Node tail;
    private int size;

    public MyLinkedList() {
        head = new Node(-1);
        curr = head;
        tail = new Node(-1);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
    
    public int get(int index) {
        curr = head;
        int currIdx = -1;
        if (index < 0 || index >= size) return -1;
        while (currIdx != index){
            curr = curr.next;
            currIdx++;
        }
        return curr.val;
    }
    
    public void addAtHead(int val) {
        Node newNode = new Node(val);
        newNode.next = head.next;
        newNode.prev = head;
        newNode.next.prev = newNode;
        head.next = newNode;
        size++;
    }
    
    public void addAtTail(int val) {
        Node newNode = new Node(val);
        newNode.prev = tail.prev;
        newNode.next = tail;
        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }
    
    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) return;
        curr = head;
        int currIdx = -1;
        while (currIdx != index){
            curr = curr.next;
            currIdx++;
        }
        // find the index
        Node newNode = new Node(val);
        newNode.next = curr;
        newNode.prev = curr.prev;
        newNode.next.prev = newNode;
        newNode.prev.next = newNode;
        curr.prev = newNode;
        size++;
    }
    
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;
        curr = head;
        int currIdx = -1;
        while (currIdx != index){
            curr = curr.next;
            currIdx++;
        }
        // find the index
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        size--;
    }
}

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
```

### Standard Solution

*   The doubly linked list is implemented in Java as [LinkedList](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html).

*   You can use either a singly linked list or doubly linked list

#### Solution #1 Singly Linked List

![bla](https://leetcode.com/problems/design-linked-list/Figures/707/singly4.png)

```java
public class ListNode {
  int val;
  ListNode next;
  ListNode(int x) { val = x; }
}

class MyLinkedList {
  int size;
  ListNode head;  // sentinel node as pseudo-head
    
  public MyLinkedList() {
    size = 0;
    head = new ListNode(0);
  }

  /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
  public int get(int index) {
    // if index is invalid
    if (index < 0 || index >= size) return -1;

    ListNode curr = head;
    // index steps needed 
    // to move from sentinel node to wanted index
    for(int i = 0; i < index + 1; ++i) curr = curr.next;
    return curr.val;
  }

  /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
  public void addAtHead(int val) {
    addAtIndex(0, val);
  }

  /** Append a node of value val to the last element of the linked list. */
  public void addAtTail(int val) {
    addAtIndex(size, val);
  }

  /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
  public void addAtIndex(int index, int val) {
    // If index is greater than the length, 
    // the node will not be inserted.
    if (index > size) return;

    // [so weird] If index is negative, 
    // the node will be inserted at the head of the list.
    if (index < 0) index = 0;

    ++size;
    // find predecessor of the node to be added
    ListNode pred = head;
    for(int i = 0; i < index; ++i) pred = pred.next;

    // node to be added
    ListNode toAdd = new ListNode(val);
    // insertion itself
    toAdd.next = pred.next;
    pred.next = toAdd;
  }

  /** Delete the index-th node in the linked list, if the index is valid. */
  public void deleteAtIndex(int index) {
    // if the index is invalid, do nothing
    if (index < 0 || index >= size) return;

    size--;
    // find predecessor of the node to be deleted
    ListNode pred = head;
    for(int i = 0; i < index; ++i) pred = pred.next;

    // delete pred.next 
    pred.next = pred.next.next;
  }
}
```

-   Time complexity: $\mathcal{O}(1)$ for addAtHead. $\mathcal{O}(k)$ for get, addAtIndex, and deleteAtIndex, where k is an index of the element to get, add or delete. $\mathcal{O}(N)$for addAtTail.
-   Space complexity: $\mathcal{O}(1)$ for all operations.

#### Solution #2 Doubly Linked List

![bla](https://leetcode.com/problems/design-linked-list/Figures/707/dll.png)

*   Same as my solution

```java
public class ListNode {
  int val;
  ListNode next;
  ListNode prev;
  ListNode(int x) { val = x; }
}

class MyLinkedList {
  int size;
  // sentinel nodes as pseudo-head and pseudo-tail
  ListNode head, tail;
    
  public MyLinkedList() {
    size = 0;
    head = new ListNode(0);
    tail = new ListNode(0);
    head.next = tail;
    tail.prev = head;
  }

  /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
  public int get(int index) {
    // if index is invalid
    if (index < 0 || index >= size) return -1;

    // choose the fastest way: to move from the head
    // or to move from the tail
    ListNode curr = head;
    if (index + 1 < size - index)
      for(int i = 0; i < index + 1; ++i) curr = curr.next;
    else {
      curr = tail;
      for(int i = 0; i < size - index; ++i) curr = curr.prev;
    }

    return curr.val;
  }

  /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
  public void addAtHead(int val) {
    ListNode pred = head, succ = head.next;

    ++size;
    ListNode toAdd = new ListNode(val);
    toAdd.prev = pred;
    toAdd.next = succ;
    pred.next = toAdd;
    succ.prev = toAdd;
  }

  /** Append a node of value val to the last element of the linked list. */
  public void addAtTail(int val) {
    ListNode succ = tail, pred = tail.prev;

    ++size;
    ListNode toAdd = new ListNode(val);
    toAdd.prev = pred;
    toAdd.next = succ;
    pred.next = toAdd;
    succ.prev = toAdd;
  }

  /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
  public void addAtIndex(int index, int val) {
    // If index is greater than the length, 
    // the node will not be inserted.
    if (index > size) return;

    // [so weird] If index is negative, 
    // the node will be inserted at the head of the list.
    if (index < 0) index = 0;

    // find predecessor and successor of the node to be added
    ListNode pred, succ;
    if (index < size - index) {
      pred = head;
      for(int i = 0; i < index; ++i) pred = pred.next;
      succ = pred.next;
    }
    else {
      succ = tail;
      for (int i = 0; i < size - index; ++i) succ = succ.prev;
      pred = succ.prev;
    }

    // insertion itself
    ++size;
    ListNode toAdd = new ListNode(val);
    toAdd.prev = pred;
    toAdd.next = succ;
    pred.next = toAdd;
    succ.prev = toAdd;
  }

  /** Delete the index-th node in the linked list, if the index is valid. */
  public void deleteAtIndex(int index) {
    // if the index is invalid, do nothing
    if (index < 0 || index >= size) return;

    // find predecessor and successor of the node to be deleted
    ListNode pred, succ;
    if (index < size - index) {
      pred = head;
      for(int i = 0; i < index; ++i) pred = pred.next;
      succ = pred.next.next;
    }
    else {
      succ = tail;
      for (int i = 0; i < size - index - 1; ++i) succ = succ.prev;
      pred = succ.prev.prev;
    }

    // delete pred.next 
    --size;
    pred.next = succ;
    succ.prev = pred;
  }
}
```

-   Time complexity: $\mathcal{O}(1)$ for addAtHead and addAtTail. $\mathcal{O}(\min(k, N - k))$ for `get`, `addAtIndex`, and `deleteAtIndex`, where k*k* is an index of the element to get, add or delete.
-   Space complexity: $\mathcal{O}(1)$ for all operations.

## Insert into a Sorted Circular Linked List (Medium #708)

**Question**: Given a Circular Linked List node, which is sorted in ascending order, write a function to insert a value `insertVal` into the list such that it remains a sorted circular list. The given node can be a reference to any single node in the list and may not necessarily be the smallest value in the circular list.

If there are multiple suitable places for insertion, you may choose any place to insert the new value. After the insertion, the circular list should remain sorted.

If the list is empty (i.e., the given node is `null`), you should create a new single circular list and return the reference to that single node. Otherwise, you should return the originally given node.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/01/19/example_1_before_65p.jpg)


```
Input: head = [3,4,1], insertVal = 2
Output: [3,4,1,2]
Explanation: In the figure above, there is a sorted circular list of three elements. You are given a reference to the node with value 3, and we need to insert 2 into the list. The new node should be inserted between node 1 and node 3. After the insertion, the list should look like this, and we should still return node 3.
```

**Example 2:**

```
Input: head = [], insertVal = 1
Output: [1]
Explanation: The list is empty (given head is null). We create a new single circular list and return the reference to that single node.
```

**Example 3:**

```
Input: head = [1], insertVal = 0
Output: [1,0]
```

**Constraints:**

-   The number of nodes in the list is in the range `[0, 5 * 104]`.
-   `-106 <= Node.val, insertVal <= 106`

### My Solution

```java
// find the smallest node, then use prev and curr to go through the list
// each time compare with the curr and prev value, insert to list
public Node insert(Node head, int insertVal) {

    // edge case
    if (head == null){
        Node newHead = new Node(insertVal);
        newHead.next = newHead;
        return newHead;
    }
    // find smallest
    Node currSmall = head.next;
    Node smallest = currSmall; 
    Node prevSmall = head;
    Node prevSmallest = prevSmall;
    int smallestVal = currSmall.val;
    boolean indicator = true;
    while(indicator || prevSmall != head){
        indicator = false;
        if (currSmall.val <= smallest.val && prevSmall.val != currSmall.val){
            smallest = currSmall;
            smallestVal = smallest.val;
            prevSmallest = prevSmall;
        }
        currSmall = currSmall.next;
        prevSmall = prevSmall.next;
    }

    // start from smallest node and find the location
    Node curr = smallest;
    Node prev = prevSmallest;

    if (insertVal < curr.val){
        Node newNode = new Node(insertVal);
        newNode.next = curr;
        prev.next = newNode;
        return head;
    }
    // if insertVal should be after the smallest node
    prev = curr;
    curr = curr.next;
    while(curr != smallest){
        if (insertVal <= curr.val && insertVal > prev.val){
            Node newNode = new Node(insertVal);
            newNode.next = curr;
            prev.next = newNode;
            return head;
        }
        curr = curr.next;
        prev = prev.next;
    }
    // otherwise it should be the largest node
    // before the smallest node
    Node newNode = new Node(insertVal);
    newNode.next = smallest;
    prevSmallest.next = newNode;
    return head;
}
```

### Standard Solution

#### Solution #1 Two-Pointers

*   As simple as the problem might seem to be, it is actually not trivial to write a solution that covers all cases.
*   The idea is similar but much neater
*   For this problem, we iterate through the cyclic list using two pointers, namely `prev` and `curr`. When we find a suitable place to insert the new value, we insert it between the `prev` and `curr` nodes.

![pic](https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/Figures/708/708_two_pointers.png)

*   Though not explicitly stated in the problem description, our sorted list can contain some duplicate values. And in the extreme case, the entire list has only one single unique value.

![pic](https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/Figures/708/708_case_3.png)

```java
public Node insert(Node head, int insertVal){
    if (head == null){
        Node newNode = new Node(insertVal, null);
        newNode.next = newNode;
        return newNode;
    }
    Node prev = head;
    Node curr = head.next;
    boolean toInsert = false;
    
    do {
        if (prev.val <= inserVal && insertVal <= curr.val){
            // case 1
            toInsert = true;
        } else if (prev.val > curr.val){
            // case 2
            if (insertVal >= prev.val || insertVal <= curr.val){
                toInsert = true;
            }
        }
        if (toInsert){
            prev.next = new Node(isnertVal, curr);
            return head;
        }
        prev = curr;
        curr = curr.next;
    } while (prev != head); // since prev starts at head, use do-while loop
    
    // case 3
    prev.next = new node(insertVal, curr);
    return head;
}
```

-   Time Complexity: $\mathcal{O}(N)$ where N is the size of the list. In the worst case, we would iterate through the entire list.
-   Space Complexity: $\mathcal{O}(1)$. It is a constant space solution.

## Copy List with Random Pointer (Medium #138)

**Question**: A linked list of length `n` is given such that each node contains an additional random pointer, which could point to any node in the list, or `null`.

Construct a [**deep copy**](https://en.wikipedia.org/wiki/Object_copying#Deep_copy) of the list. The deep copy should consist of exactly `n` **brand new** nodes, where each new node has its value set to the value of its corresponding original node. Both the `next` and `random` pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. **None of the pointers in the new list should point to nodes in the original list**.

For example, if there are two nodes `X` and `Y` in the original list, where `X.random --> Y`, then for the corresponding two nodes `x` and `y` in the copied list, `x.random --> y`.

Return *the head of the copied linked list*.

The linked list is represented in the input/output as a list of `n` nodes. Each node is represented as a pair of `[val, random_index]` where:

-   `val`: an integer representing `Node.val`
-   `random_index`: the index of the node (range from `0` to `n-1`) that the `random` pointer points to, or `null` if it does not point to any node.

Your code will **only** be given the `head` of the original linked list.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/12/18/e1.png)

```
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2019/12/18/e2.png)

```
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]
```

**Example 3:**

**![img](https://assets.leetcode.com/uploads/2019/12/18/e3.png)**

```
Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]
```

**Constraints:**

-   `0 <= n <= 1000`
-   `-104 <= Node.val <= 104`
-   `Node.random` is `null` or is pointing to some node in the linked list.

### My Solution

```java
public Node copyRandomList(Node head) {
    if (head == null) return head;
    // store original random index - new random node
    Map<Node, Node> randomMap = new HashMap<>();

    // 1. copy the linked list without random index
    // 2. loop through the list, check random index and get it from random map
    Node dummyHead = new Node(0);
    Node newCurr = dummyHead;
    Node curr = head;
    while(curr != null){
        newCurr.next = new Node(curr.val);
        newCurr = newCurr.next;
        randomMap.put(curr, newCurr);
        curr = curr.next;
    }
    curr = head;
    newCurr = dummyHead.next;

    // get it from random map and connect random node
    while(curr != null){
        if (curr.random != null) {
            newCurr.random = randomMap.get(curr.random);
        }
        newCurr = newCurr.next;
        curr = curr.next;
    }

    return dummyHead.next;
}
```

### Standard Solution

#### Solution #1 Iterative with $O(n)$ Space

*   Similar idea to my solution

```java
HashMap<Node, Node> visited = new HashMap<>();

public Node getClonedNode(Node node){
    // if the node exists then
    if (node != null){
        // check if the node is in the visited dictionary
        if (this.visited.containsKey(node)){
            // if its in the visited dictionary then return the new node reference from dict
            return this.visited.get(node);
        }
        else {
            this.visited.put(node, new Node(node.val, null, null));
            return this.visited.get(node);
        }
    }
    return null;
}

public Node copyRandomList(Node head){
    if (head == null) return null;
    Node oldNode = head;
    Node newNode = new Node(oldNode.val);
    this.visited.put(oldNode, newNode);
    
    // iterate on the linked list until all nodes are cloned
    while (oldNode != null){
        // get the clones of the nodes referenced by random and next pointers
        newNode.random = this.getClonedNode(oldNode.random);
        newNode.next = this.getClonedNode(oldNode.next);
        
        // move one step ahead in the linked list
        oldNode = oldNode.next;
        newNode = newNode.next;
    }
    return this.visited.get(head);
}
```

-   Time Complexity: $O(N)$ because we make one pass over the original linked list.
-   Space Complexity: $O(N)$ as we have a dictionary containing a mapping from old list nodes to new list nodes. Since there are N nodes, we have $O(N)$ space complexity.

#### Solution #2 Recursion

*   Same time and space complexity as the last method

```java
HashMap<Node, Node> visitedHash = new HashMap<Node, Node>();

public Node copyRandomList(Node head) {

    if (head == null) {
      return null;
    }

    if (this.visitedHash.containsKey(head)) {
      return this.visitedHash.get(head);
    }

    Node node = new Node(head.val, null, null);
    this.visitedHash.put(head, node);

    // Recursively copy the remaining linked list starting once from the next pointer and then from
    // the random pointer.
    // Thus we have two independent recursive calls.
    // Finally we update the next and random pointers for the new node created.
    node.next = this.copyRandomList(head.next);
    node.random = this.copyRandomList(head.random);
	return node;
}
```

## Rotate List (Medium #61)

**Question**: Given the `head` of a linked list, rotate the list to the right by `k` places.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/11/13/rotate1.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/11/13/roate2.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [0,1,2], k = 4
Output: [2,0,1]
```

**Constraints:**

-   The number of nodes in the list is in the range `[0, 500]`.
-   `-100 <= Node.val <= 100`
-   `0 <= k <= 2 * 109`

### My Solution

*   My solution is the almost standard solution for this problem
*   All solutions have time complexity as $O(N)$ and space complexity as $O(1)$

#### Solution #1 Two Pointers

```java
public ListNode rotateRight(ListNode head, int k) {
    // 1. find true k : k % length of list, a loop to find length of list
    if (head == null || head.next == null || k == 0) return head;

    ListNode curr = head;
    int length = 0;
    while (curr != null){
        length++;
        curr = curr.next;
    }
    int trueK = k % length;
	// equals to no rotation
    if (trueK == 0) return head;

    // 2. two pointers: slow, fast, fast go to the end of list, slow k steps away from fast
    ListNode slow = head;
    ListNode fast = head;
    int loc = 0;
    while(loc != trueK){
        loc++;
        fast = fast.next;
    }

    // find the break point
    while(fast != null && fast.next != null){
        fast = fast.next;
        slow = slow.next; // stop at 3
    }

    // 3. slow pointer 1, 2, 3 || 4, 5, then move the back of list to the front, make a new head
    ListNode newHead = slow.next;
    fast.next = head;
    slow.next = null;

    // 4. return the new head, such as 4 in last cases
    return newHead;
}
```

#### Solution #2 Cycle Linked List

*   Link the linked list into a circle, use a new head, break the link

```java
public ListNode rotateRight(ListNode head, int k) {
    // handle the null cases
    if (head == null || head.next == null){
        return head;
    }
    ListNode dummyHead = new ListNode(0);
    ListNode dummyEnd = new ListNode(0);
    ListNode curr = head;
    int length = 1;
    while(curr.next != null){
        curr = curr.next;
        length++;
    }
    // connect the nodes as a cycle
    curr.next = head;

    // put the dummy head and dummy end to the right locations
    int loc = length - k % length;
    int index = 1;
    curr = head;
    while(curr.next != null && index < loc){
        index++;
        curr = curr.next;
    }
    dummyEnd = curr;
    dummyHead = curr.next;
    curr.next = null;
    return dummyHead;
}
```

## Merge k Sorted Lists (Heard #23)

**Question**: You are given an array of `k` linked-lists `lists`, each linked-list is sorted in ascending order.

*Merge all the linked-lists into one sorted linked-list and return it.*

**Example 1:**

```
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted list:
1->1->2->3->4->4->5->6
```

**Example 2:**

```
Input: lists = []
Output: []
```

**Example 3:**

```
Input: lists = [[]]
Output: []
```

**Constraints:**

-   `k == lists.length`
-   `0 <= k <= 104`
-   `0 <= lists[i].length <= 500`
-   `-104 <= lists[i][j] <= 104`
-   `lists[i]` is sorted in **ascending order**.
-   The sum of `lists[i].length` will not exceed `104`.

### My Solution

```java
public ListNode mergeKLists(ListNode[] lists) {
    // each time compare the listnode pointer
    // find the max length of the new list
    int length = 0;
    for (ListNode list : lists){
        ListNode curr = list;
        while(curr != null){
            curr = curr.next;
            length++;
        }
    }
    int currListLength = 0;
    ListNode dummyHead = new ListNode();
    ListNode curr = dummyHead;
    // if not reach the number of nodes
    while(currListLength < length){
        int min = Integer.MAX_VALUE;
        ListNode minNode = new ListNode();
        int minIndex = 0;
        for (int i = 0; i < lists.length; i++){
            if (lists[i] != null && lists[i].val < min){
                min = lists[i].val;
                minNode = lists[i];
                minIndex = i;
            }
        }
        curr.next = minNode;
        curr = curr.next;
        currListLength++;
        if (lists[minIndex] != null) lists[minIndex] = lists[minIndex].next;
    }
    return dummyHead.next;
}
```

*   The method of compare them one by one, slow in time complexity but fast in space complexity.
*   Time complexity: $O(kN)$ where $\text{k}$ is the number of linked lists.
    -   Almost every selection of node in the final linked costs $O(k)$ ($\text{k-1}$ times comparison).
    -   There are N nodes in the final linked list.
*   Space complexity:
    -   $O(1)$ It's not hard to apply the in-place method - connect selected nodes instead of creating new nodes to fill the new linked list.

### Standard Solution

#### Solution #1 Compare them 1 by 1

*   Same as my solution concept, low space complexity but slow

#### Solution #2 Collect all the values of list and sort

*   Use a list to include all the value of list node, then sort the list
*   Create a new LinkedList with new nodes of the values

```java
public ListNode mergeKLists(ListNode[] lists){
    List<Integer> list = new ArrayList<Integer>();
    for (ListNode node : lists){
        while(node != null){
            list.add(node.val);
            node = node.next;
        }
    }
    Collections.sort(list);
    ListNode head = new ListNode(0);
    ListNode curr = head;
    for (int listVal : list){
        ListNode newNode = new ListNode(listVal);
        curr.next = newNode;
        curr = curr.next;
    }
    curr.next = null;
    return head.next;
}
```

-   Time complexity: $O(N\log N)$ where N is the total number of nodes.
    -   Collecting all the values costs $O(N)$ time.
    -   A stable sorting algorithm costs $O(N\log N)$ time.
    -   Iterating for creating the linked list costs $O(N)$ time.
-   Space complexity: $O(N)$
    -   Sorting cost $O(N)$ space (depends on the algorithm you choose).
    -   Creating a new linked list costs $O(N)$ space.

#### Solution #3 Optimize solution by Priority Queue

*   Put all the nodes in the priority queue and poll them out
*   Put the head of the lists, then poll min out, then put the next node into the queue

```java
public ListNode mergeKLists(ListNode[] lists) { 
    Comparator<ListNode> cmp;
    cmp = new Comparator<ListNode>() {  
    @Override
    public int compare(ListNode o1, ListNode o2) {
        // TODO Auto-generated method stub
        return o1.val-o2.val;
    }
    };

    Queue<ListNode> q = new PriorityQueue<ListNode>(cmp);
    for(ListNode l : lists){
        if(l!=null){
            q.add(l);
        }        
    }
    ListNode head = new ListNode(0);
    ListNode point = head;
    while(!q.isEmpty()){ 
        point.next = q.poll();
        point = point.next; 
        ListNode next = point.next;
        if(next!=null){
            q.add(next);
        }
    }
    return head.next;
}
```

-   Time complexity: $O(N\log k)$ where $\text{k}$ is the number of linked lists.
    -   The comparison cost will be reduced to $O(\log k)$ for every pop and insertion to the priority queue. But finding the node with the smallest value just costs $O(1)$ time.
    -   There are N nodes in the final linked list.
-   Space complexity :
    -   $O(k)$ The above code applies the in-place method, which costs $O(1)$ space. And the priority queue (often implemented with heaps) costs $O(k)$ space (it's far less than N in most situations).
