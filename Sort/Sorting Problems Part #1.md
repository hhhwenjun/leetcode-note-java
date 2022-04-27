# Sorting Problems Part #1

## Sort List (Medium #148)

**Question**: Given the `head` of a linked list, return *the list after sorting it in **ascending order***.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/09/14/sort_list_1.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [4,2,1,3]
Output: [1,2,3,4]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/09/14/sort_list_2.jpg" alt="img" style="zoom:50%;" />

```
Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]
```

**Example 3:**

```
Input: head = []
Output: []
```

**Constraints:**

-   The number of nodes in the list is in the range `[0, 5 * 104]`.
-   `-105 <= Node.val <= 105`

### My Solution

*   Use an array list to record all the numbers, sort them using Collections
*   Then reassign them to the nodes

```java
public ListNode sortList(ListNode head) {
    if (head == null){
        return null;
    }
    List<Integer> nums = new ArrayList<>();
    ListNode current = head;
    while (current != null){
        nums.add(current.val);
        current = current.next;
    }
    Collections.sort(nums);
    current = head;
    while(nums.size() > 0){
        current.val = nums.remove(0);
        current = current.next;
    }
    return head;
}
```

### Standard Solution

#### Solution #1 Top-down Merge Sort

*   Recursively split the list into two halves, until only one node in the list (find mid)
*   Merge two lists also in recursion

```java
public ListNode sortList(ListNode head){
    if (head == null || head.next == null){
        return head;
    }
    ListNode mid = getMid(head);
    ListNode left = sortList(head);
    ListNode right = sortList(mid);
    return merge(left, right);
}

public ListNode merge(ListNode list1, ListNode list2){
    ListNode dummyHead = new ListNode();
    ListNode tail = dummyHead;
    while (list1 != null && list2 != null){
        if {
           tail.next = list1;
        	list1 = list1.next;
        	tail = tail.next; 
        } else {
        	tail.next = list2;
        	list2 = list2.next;
        	tail = tail.next;
        }
    }
    tail.next = (list1 != null) ? list1 : list2;
    return dummyHead.next;
}
public ListNode getMid(ListNode head){
    ListNode midPrev = null;
    while(head != null && head.next != null){
        midPrev = (midPrev == null) ? head : midPrev.next;
        head = head.next.next;
    }
    ListNode mid = midPrev.next;
    midPrev.next = null;
    return mid;
}
```

-   Time Complexity: $\mathcal{O}(n \log n)$, where n is the number of nodes in the linked list. The algorithm can be split into 2 phases, Split and Merge.
-   Space Complexity: $\mathcal{O}(\log n)$, where n is the number of nodes in the linked list. Since the problem is recursive, we need additional space to store the recursive call stack. The maximum depth of the recursion tree is $\log n$