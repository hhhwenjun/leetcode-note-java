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

## Largest Number (Medium #179)

**Question**: Given a list of non-negative integers `nums`, arrange them such that they form the largest number and return it.

Since the result may be very large, so you need to return a string instead of an integer.

**Example 1:**

```
Input: nums = [10,2]
Output: "210"
```

**Example 2:**

```
Input: nums = [3,30,34,5,9]
Output: "9534330"
```

**Constraints:**

-   `1 <= nums.length <= 100`
-   `0 <= nums[i] <= 109`

### Standard Solution

#### Solution #1 Sorting via Custom Comparator

*   Create a custom comparator, and compare the adding order (solve the problem: single digit vs. multiple digits)
*   Sort the string array
*   Add element to the string

```java
private class LargeNumberComparator implements Comparator<String>{
    @Override
    public int compare(String a, String b){
        String order1 = a + b;
        String order2 = b + a;
        return order2.compareTo(order1);
    }
}

public String largestNumber(int[] nums) {
    // get input integers as strings
    String[] strs = new String[nums.length];
    for (int i = 0; i < nums.length; i++){
        strs[i] = String.valueOf(nums[i]);
    }

    // sort strings according to custom comparator
    Arrays.sort(strs, new LargeNumberComparator());

    // if, after being sorted, the largest number is 0,
    // the entire number is 0.
    if (strs[0].equals("0")) return "0";

    // build largest number from sorted array
    String largestNumberStr = new String();
    for (String numStr : strs){
        largestNumberStr += numStr;
    }
    return largestNumberStr;
}
```

```java
// almost same but much faster solution
public String largestNumber(int[] nums) {
    if (nums == null || nums.length == 0) return "";
    String[] strs = new String[nums.length];
    for (int i = 0; i < nums.length; i++) {
        strs[i] = nums[i]+"";
    }
    Arrays.sort(strs, new Comparator<String>() {
        @Override
        public int compare(String i, String j) {
            String s1 = i+j;
            String s2 = j+i;
            return s1.compareTo(s2);
        }
    });
    if (strs[strs.length-1].charAt(0) == '0') return "0";
    String res = new String();
    for (int i = 0; i < strs.length; i++) {
        res = strs[i]+res;
    }
    return res;
}
```

-   Time complexity: $\mathcal{O}(nlgn)$

    Although we are doing extra work in our comparator, it is only by a constant factor. Therefore, the overall runtime is dominated by the complexity of `sort`, which is $\mathcal{O}(nlgn)$ in Python and Java.

-   Space complexity: $\mathcal{O}(n)$

    Here, we allocate $\mathcal{O}(n)$ additional space to store the copy of `nums`. Although we could do that work in place (if we decide that it is okay to modify `nums`), we must allocate $\mathcal{O}(n)$ space for the final return string. Therefore, the overall memory footprint is linear in length of `nums`.