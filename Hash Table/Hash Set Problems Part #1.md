# Hash Set Problems Part #1

## Contains Duplicate(Easy #217)

**Question**: Given an integer array `nums`, return `true` if any value appears **at least twice** in the array, and return `false` if every element is distinct.

**Example 1:**

```
Input: nums = [1,2,3,1]
Output: true
```

**Example 2:**

```
Input: nums = [1,2,3,4]
Output: false
```

**Example 3:**

```
Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true
```

**Constraints:**

- `1 <= nums.length <= 105`
- `-109 <= nums[i] <= 109`

### My Solution

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        
        Set<Integer> hashSet = new HashSet<>();
        for (Integer num : nums){
            if (hashSet.contains(num)){
                return true;
            }
            else {
                hashSet.add(num);
            }
        }
        return false;
    }
```

### Standard Solutions

#### Solution #1 Sorting

* Employs sorting algorithm

```java
public boolean containsDuplicate(int[] nums){
	Arrays.sort(nums);
    for (int i = 0; i < nums.length - 1; i++){
        if (nums[i] == nums[i + 1]){
            return true;
        }
    }
    return false;
}
```

* Time complexity : `O(n log n)`
* Space complexity: `O(1)`

#### Solution #2 Hash Table

* Coding is the same as my solution
* **BST vs. Hash Table**
  * There are many data structures commonly used as dynamic sets such as Binary Search Tree and Hash Table. The operations we need to support here are `search()` and `insert()`. For a self-balancing Binary Search Tree (TreeSet or TreeMap in Java), `search()` and `insert()` are both `O(log n)` time. For a Hash Table (HashSet or HashMap in Java), `search()` and `insert()` are both `O(1)` on average. Therefore, by using hash table, we can achieve linear time complexity for finding the duplicate in an unsorted array.
* Time complexity : `O(n)`
* Space complexity: `O(n)`

```java
public boolean containsDuplicate(int[] nums){
    Set<Integer> set = new HashSet<>(nums.length);
    for (int x : nums){
        if (set.contains(x)) return true;
        set.add(x);
    }
    return false;
}
```

* But a **better version**
  * Remove `contains` method would make the process a lot faster
  * `add` method would return boolean (be familiar with `set` interface)

```java
public boolean containsDuplicate(int[] nums) {        
    Set<Integer> hashSet = new HashSet<>();
    for (Integer num : nums){
        if (!hashSet.add(num)){
            return true;
        }
        else {
            hashSet.add(num);
        }
    }
    return false;
}
```

## Single Number(Easy #136)

**Question**: Given a **non-empty** array of integers `nums`, every element appears *twice* except for one. Find that single one. You must implement a solution with a linear runtime complexity and use only constant extra space.

**Example 1:**

```
Input: nums = [2,2,1]
Output: 1
```

**Example 2:**

```
Input: nums = [4,1,2,1,2]
Output: 4
```

**Example 3:**

```
Input: nums = [1]
Output: 1
```

**Constraints:**

- `1 <= nums.length <= 3 * 104`
- `-3 * 104 <= nums[i] <= 3 * 104`
- Each element in the array appears twice except for one element which appears only once.

### My Solution

```java
public int singleNumber(int[] nums) {
    Set<Integer> hashSet = new HashSet<Integer>(nums.length);

    for (int num : nums){
        if (!hashSet.add(num)){
            hashSet.remove(num);
        }
    }
    return hashSet.hashCode();
}
```

### Standard Solutions

#### Solution #1 List Operation

* Similar logic to my solution but using array list
* If number is new to array, append it
* If number is already in the array, remove it

```java
public int singleNumber(int[] nums){
    List<Integer> no_duplicate_list = new ArrayList<>();
    
    for (int i : nums){
        if (!no_duplicate_list.contains(i)){
            no_duplicate_list.add(i);
        } else {
            no_duplicate_list.remove(new Integer(i));
        }
    }
    return no_duplicate_list.get(0);
}
```

* Time complexity: `O(n**2)` . We iterate through list taking `O(n)` time, and search the whole list to find whether duplicate number, taking `O(n)` time.
* Space complexity: `O(n)`. We need a list of size n to contains elements.

#### Solution #2 Hash Map

* Iterate through all elements in `nums` and set up key/value pair.
* Return the element which appeared only once.

```java
public int singleNumber(int[] nums){
    HashMap<Integer, Integer> hash_table = new HashMap<>();
    
    for (int i : nums){
        hash_table.put(i, hash_table.getOrDefault(i, 0) + 1);
    }
    for (int i : nums){
        if (hash_table.get(i) == 1){
            return i;
        }
    }
    return 0;
}
```

* `getOrDefault`: [example](https://www.geeksforgeeks.org/hashmap-getordefaultkey-defaultvalue-method-in-java-with-examples/)
  * Which is the **default value** that has to be returned, if no value is mapped with the specified key.
* Time complexity : `O(n * 1)` = `O(n)`. Time complexity of `for` loop is `O(n)`. Time complexity of hash table(dictionary in python) operation `pop` is `O(1)`.
* Space complexity : `O(n)`. The space required by hash table is equal to the number of elements in list.

#### Solution #3 Math

* The logic is same as previous solutions but using math subtraction to find the distinct value

```java
public int singleNumber(int[] nums){
    int sumOfSet = 0, sumOfNums = 0;
    Set<Integer> set = new HashSet();
    
    for (int num : nums){
        if (!set.contains(num)){
            set.add(num);
            sumOfSet += num;
        }
        sumOfNums += num;
    }
    return 2*sumOfSet - sumOfNums;
}
```

* Time complexity: `O(n + n)` = `O(n)`
* Space complexity: `O(n + n)` = `O(n)`

#### Solution #4 Bit Manipulation (Best Solution)

- If we take XOR of zero and some bit, it will return that bit
  - $a \oplus 0 = a$
- If we take XOR of two same bits, it will return 0
  - $a \oplus a = 0⊕a=0$
- $a \oplus b \oplus a = (a \oplus a) \oplus b = 0 \oplus b = b$

So we can XOR all bits together to find the unique number.

```java
public int singleNumber(int[] nums){
    int a = 0;
    for (int i : nums){
        a ^= i;
    }
    return a;
}
```

- Time complexity : `O(n)`. We only iterate through $\text{nums}$, so the time complexity is the number of elements in $\text{nums}$.
- Space complexity : `O(1)`.
- Bit manipulation [CN introduction](https://www.runoob.com/java/java-operators.html)

## Single Number II (Medium #137)

**Question**: Given an integer array `nums` where every element appears **three times** except for one, which appears **exactly once**. *Find the single element and return it*.

You must implement a solution with a linear runtime complexity and use only constant extra space.

**Example 1:**

```
Input: nums = [2,2,3,2]
Output: 3
```

**Example 2:**

```
Input: nums = [0,1,0,1,0,1,99]
Output: 99
```

**Constraints:**

- `1 <= nums.length <= 3 * 104`
- `-231 <= nums[i] <= 231 - 1`
- Each element in `nums` appears exactly **three times** except for one element which appears **once**.

### My Solution

```java
 public int singleNumber(int[] nums) {
    HashMap<Integer, Integer> hashMap = new HashMap<>();

    for (int i : nums){           
        hashMap.put(i, hashMap.getOrDefault(i, 0) + 1);
    }
    for (int i : nums){            
        if (hashMap.get(i) == 1) return i;
    }
    return 0;
} //The Hash map solution of #136
```

### Standard Solutions

#### Overview

* The problem seems to be quite simple and one could solve it in $\mathcal{O}(N)$ time and $\mathcal{O}(N)$ space by using an additional data structure like set or hash map.

* The real game starts at the moment when Google interviewer (the problem is quite popular at Google the last six months) asks you to solve the problem in a constant space, testing if you are OK with **bitwise operators**.

<img src="https://leetcode.com/problems/single-number-ii/Figures/137/methods.png" alt="fig" style="zoom: 33%;" />

#### Solution #1 HashSet

* The idea is to convert an input array into hash set and then to compare the tripled sum of the set with the array sum
* $3×(a+b+c)−(a+a+a+b+b+b+c)=2c$

```java
public int singleNumber(int[] nums){
    Set<long> set = new HashSet<>();
    long sumSet = 0, sumArray = 0;
    for (int n : nums){
        sumArray += n;
        set.add((long)n);
    }
    for (long s : set) sumSet += s;
    return (int)((3 * sumSet - sumArray) / 2);
}
```

* Time complexity: $O(N)$ to iterate over the input array
* Space complexity: $O(N)$ to keep the set of $N/3$ elements

#### Solution #2 HashMap

* Same as my solution
* Time complexity : $\mathcal{O}(N)$ to iterate over the input array.
* Space complexity : $\mathcal{O}(N)$ to keep the hash map of N/3 elements.

#### Solution #3 Bit Manipulation

* Bit operators
  * $∼x$ that means bitwise NOT
  * $x \& y$ that means bitwise AND
  * $x \oplus y $ that means bitwise XOR
* One could see the bit in a bitmask only if it appears odd number of times

<img src="https://leetcode.com/problems/single-number-ii/Figures/137/xor.png" alt="fig" style="zoom: 33%;" />

* **AND and NOT**

  To separate number that appears once from a number that appears three times let's use two bitmasks instead of one: `seen_once` and `seen_twice`.

  The idea is to

  - change `seen_once` only if `seen_twice` is unchanged
  - change `seen_twice` only if `seen_once` is unchanged

<img src="https://leetcode.com/problems/single-number-ii/Figures/137/three.png" alt="fig" style="zoom:33%;" />

* Time complexity $O(N)$ to iterate over the input array.
* Space complexity $O(1)$ since no additional data structures are allocated.

```java
public int singleNumber(int[] nums){
    int seenOnce = 0, seenTwice = 0;
    
    for (int num : nums){
        // first appearence: 
        // add num to seen_once 
        // don't add to seen_twice because of presence in seen_once

        // second appearance: 
        // remove num from seen_once 
        // add num to seen_twice

        // third appearance: 
        // don't add to seen_once because of presence in seen_twice
        // remove num from seen_twice
        seenOnce = ~seenTwice & (seenOnce ^ num);
        seenTwice = ~seenOnce & (seenTwice ^ num);
    }
    
    return seenOnce;
}
```

## Intersection of Two Arrays(Easy #349)

**Question**: Given two integer arrays `nums1` and `nums2`, return an array of their intersection. Each element in the result must be **unique** and you may return the result in **any order**.

**Example 1:**

```
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2]
```

**Example 2:**

```
Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [9,4]
Explanation: [4,9] is also accepted.
```

### My Solution

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> hashSet1 = new HashSet<>();
        Set<Integer> hashSet2 = new HashSet<>();
        Set<Integer> uniset = new HashSet<>();        
        for (int num : nums1){
            if (!hashSet1.add(num)){
                continue;
            }
        }       
        for (int num : nums2){
            if (!hashSet2.add(num)){
                continue;
            }
        }       
        for (int num : hashSet2){
            if (!hashSet1.add(num)){
                uniset.add(num);
            }
        }
        Integer[] integerArry = uniset.toArray(new Integer[0]);
        int[] intArry = Arrays.stream(integerArry).mapToInt(Integer::intValue).toArray();
        
        return intArry;
    }
}//Not a good way to solve the problem
```

### Standard Solutions

#### Solution #1 Two Sets

* Similar to my solution but a simpler way

```java
public int[] intersection(int[] nums1, int[] nums2){
    HashSet<Integer> set1 = new HashSet<Integer>();
    for (Integer n : nums1) set1.add(n);
    HashSet<Integer> set2 = new HashSet<Integer>();
    for (Integer n : nums2) set2.add(n);
    
    if (set1.size() < set2.size()) return set_intersection(set1, set2);
    else return set_intersection(set2, set1);
}

public int[] set_intersection(HashSet<Integer> set1, HashSet<Integer> set2){
    int[] output = new int[set1.size()];
    int idx = 0;
    for (Integer s : set1){
        if (set2.contains(s)) output[idx++] = s;
    }
    return Arrays.copyOf(output, idx);
}
```

* Time complexity: $O(m+n)$, where `n` and `m` are arrays' lengths. $\mathcal{O}(n)$ time is used to convert `nums1` into set, $\mathcal{O}(m)$ time is used to convert `nums2`, and `contains/in` operations are $\mathcal{O}(1)$ in the average case.
* Space complexity : $\mathcal{O}(m + n)$ in the worst case when all elements in the arrays are different.

#### Solution #2 Built-in Set Intersection

* `retainAll`:  This method takes **collection c** as a parameter containing elements to be retained from this set. Check it as intersection.

```java
public int[] intersection(int[] nums1, int[] nums2){
    HashSet<Integer> set1 = new HashSet<Integer>();
    for (Integer n : nums1) set1.add(n);
    HashSet<Integer> set2 = new HashSet<Integer>();
    for (Integer n : nums2) set2.add(n);
    
    set1.retainAll(set2);
    
    int[] output = new int[set1.size()];
    int idx = 0;
    for (int s : set1) output[idx++] = s;
    return output;
}
```

* Time complexity : $\mathcal{O}(n + m)$ in the average case and $\mathcal{O}(n \times m)$.
* Space complexity : $\mathcal{O}(n + m)$ in the worst case when all elements in the arrays are different.

## Happy Number(#Easy 202)

**Question**: Write an algorithm to determine if a number `n` is happy.

A **happy number** is a number defined by the following process:

- Starting with any positive integer, replace the number by the sum of the squares of its digits.
- Repeat the process until the number equals 1 (where it will stay), or it **loops endlessly in a cycle** which does not include 1.
- Those numbers for which this process **ends in 1** are happy.

Return `true` *if* `n` *is a happy number, and* `false` *if not*.

**Example 1:**

```
Input: n = 19
Output: true
Explanation:
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 02 = 1
```

**Example 2:**

```
Input: n = 2
Output: false
```

### Standard Solution

#### Solution #1 Detect Cycles with a HashSet

* Start with examples to test the loop, here are some examples

<img src="https://leetcode.com/problems/happy-number/Figures/202/image1.png" alt="The chain of numbers starting with 7. It has the numbers 7, 49, 97, 130, 10 and 1." style="zoom:50%;" />

<img src="https://leetcode.com/problems/happy-number/Figures/202/image2.png" alt="The chain of numbers starting with 116. It has the numbers 116, 38, 73, 58, and then goes in a circle to 89, 145, 42, 20, 4, 16, 37, and back to 58." style="zoom: 50%;" />

* For some numbers, the square process would repeat to a loop, we can use a hash set to record the loop
* 2 parts the algorithm we'll need to design and code.
  * Given a number n, what is its *next* number?
  * Follow a chain of numbers and detect if we've entered a cycle.

```java
private int getNext(int n){
    int totalSum = 0;
    while(n > 0){
        int d = n % 10; // remember this part of taking digits
        n = n / 10;
        totalSum += d * d;
    }
    return totalSum;
}

public boolean isHappy(int n){
    Set<Integer> seen = new HashSet<>();
    while(n != 1 && !seen.contains(n)){
        seen.add(n);
        n = getNext(n);
    }
    return n == 1;
}
```

* Time complexity: $O(243⋅3+\log n+\log\log n+\log\log\log n)... = O(\log n)$.

* Space complexity: $O(\log n)$ Closely related to the time complexity, and is a measure of what numbers we're putting in the HashSet, and how big they are. For a large enough, the most space will be taken by n itself.

  We can optimize to $O(243 \cdot 3) = O(1)O(243⋅3)=O(1)$ easily by only saving numbers in the set that are less than 243, as we have already shown that for numbers that are higher, it's impossible to get back to them anyway.

#### Solution #2 Floyd's Cycle-Finding Algorithm

* Last solution `getNext(n)` is using an implicit linkedlist.
* Use linkedlist structure to track the runners around a circular race track: 
  * Tortoise and hare.
  * Slow runner and fast runner.
  * **Tortoise at location head, hare at location `head.next` at the beginning**
  * **Each time tortoise moves one step, hare moves two steps.**

* If n *is* a happy number, i.e. there is no cycle, then the fast runner will eventually get to 1 before the slow runner.
* If n *is not* a happy number, then eventually the fast runner and the slow runner will be on the same number.

```java
public int getNext(int n){
    int totalSum = 0;
    while(n > 0){
        int d = n % 10;
        n = n / 10;
        totalSum += d * d;
    }
    return totalSum;
}

public boolean isHappy(int n){
    int slowRunner = n;
    int fastRunner = getNext(n);
    
    while(fastRunner != 1 && slowRunner != fastRunner){
        slowRunner = getNext(slowRunner);
        fastRunner = getNext(getNext(fastRunner));
    }
    
    return fastRunner == 1;
}
```

* Time complexity: $O(\log n)$

## LinkedList Cycle(Easy #141)

**Question**: Given `head`, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to. **Note that `pos` is not passed as a parameter**.

Return `true` *if there is a cycle in the linked list*. Otherwise, return `false`.

 **Example 1:**

<img src="https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png" alt="img" style="zoom:50%;" />

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

- The number of the nodes in the list is in the range `[0, 104]`.
- `-105 <= Node.val <= 105`
- `pos` is `-1` or a **valid index** in the linked-list.

**Follow up:** Can you solve it using `O(1)` (i.e. constant) memory?

### My Solution

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
 
  public boolean hasCycle(ListNode head) {
        
        ListNode tor = head;
        ListNode hare = head;
        
        if (tor == null || tor.next == null){
            return false;
        }     
        hare = tor.next;
        
        while (tor != hare){
            if (hare == null || hare.next == null){
                return false;
            }
            tor = tor.next;
            hare = hare.next.next;
        }      
        return hare == tor;
   }// Floyd's Cycle Finding algorithm
```

### Standard Solution

#### Solution #1 Hash Table

* Check whether a node had been visited before
* Go through the node one by one：
  * `null`: reach the end of the list
  * Current node is in the hash table indicates a cycle

```java
public boolean hasCycle(ListNode head){
	Set<ListNode> nodesSeen = new HashSet<>();
    while (head != null){
        if (nodesSeen.contains(head)){
            return ture;
        }
        nodesSeen.add(head);
        head = head.next;
    }
    return false;
}
```

* Time complexity : $O(n)$. We visit each of the n elements in the list at most once. Adding a node to the hash table costs only $O(1)$ time.
* Space complexity: $O(n)$. The space depends on the number of elements added to the hash table, which contains at most n elements.

#### Solution #2 Floyd's Cycle Finding Algorithm

* Similar to my solution
* Consider two pointers at different speed - a slow pointer and a fast pointer

```java
public boolean hasCycle(ListNode head){
    if (head == null){
        return false;
    }
    
    ListNode slow = head;
    ListNode fast = head.next;
    
    while (slow != fast){
        if (fast == null || fast.next == null){
            return false;
        }
        slow = slow.next;
        fast = fast.next.next;
    }
    return true;
}
```

* Time complexity : $O(n)$
* Space complexity: $O(1)$

## LinkedList Cycle II(Medium #142)

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

- The number of the nodes in the list is in the range `[0, 104]`.
- `-105 <= Node.val <= 105`
- `pos` is `-1` or a **valid index** in the linked-list.

### My Solution

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        
        Set<ListNode> hashSetNode = new HashSet<ListNode>();   
        ListNode current = head;      
        while (current != null){
            
            if (!hashSetNode.add(current)){
                return current;
            }
            current = current.next;
        }
        return null;
    }
}
```

### Standard Solution

#### Solution #1 Hash Table

* Same as my solution
* Allocate a set to store `ListNode` references.

* Time complexity : $O(n)$
* Space complexity : $O(n)$

#### Solution #2 Floyd's Tortoise and Hare

* There are two phases in the algorithm.
* **Phase 1**
  * Initialize hare and tortoise. Increment tortoise once and hare twice.
  * If hare and tortoise point to the same node, we return it. Otherwise, continue.
  * If while loop terminates without returning a node, return null to indicate `null`
  * Eventually they would go to the same node if there is a cycle

<img src="https://leetcode.com/problems/linked-list-cycle-ii/Figures/142/Slide1.PNG" alt="Diagram of cyclic list" style="zoom:33%;" />

* **Phase 2**

  * Given that phase 1 finds an intersection, phase 2 find the node that is the entrance to the cycle.

  * Initialize two more pointers: `ptr1` and `ptr2`

  * `ptr1` points to the head of the list, `ptr2` points to the intersection.

  * Advance each of them by 1 until they meet, **the node they meet is the entrance of the cycle.**

  * Suppose $F$ is the number of nodes outside of the cycle, $C$ is the length of the cycle

  * To compute the intersection point, let's note that the hare has traversed twice as many nodes as the tortoise, *i.e.* $2d(\text{tortoise}) = d(\text{hare})$, that means

    <img src="https://leetcode.com/problems/linked-list-cycle-ii/Figures/142/diagram.png" alt="Phase 2 diagram" style="zoom:33%;" />

    $2(F + a) = F + nC + a$, where $n$ is some integer. 

  * Hence the coordinate of the intersection point is $F + a = nC$.

  ```java
  private ListNode getIntersect(ListNode head){
      ListNode tortoise = head;
      ListNode hare = head;
      
      while(hare != null && hare.next != null){
          tortoise = tortoise.next;
          hare = hare.next.next;
          if (tortoise == hare){
              return tortoise;
          }
      }
      return null;
  }
  
  public ListNode detectCycle(ListNode head){
      if (head == null){
          return null;
      }
      
      ListNode intersect = getIntersect(head);
      if (intersect == null){
          return null;
      }
      
      ListNode ptr1 = head;
      ListNode ptr2 = intersect;
      while(ptr1 != ptr2){
          ptr1 = ptr1.next;
          ptr2 = ptr2.next;
      }
      
      return ptr1;
  }
  ```

* Time complexity : $O(n)$
* Space complexity : $O(1)$

## Find the Duplicate Number(Medium #287)

**Question**: Given an array of integers `nums` containing `n + 1` integers where each integer is in the range `[1, n]` inclusive.

There is only **one repeated number** in `nums`, return *this repeated number*.

You must solve the problem **without** modifying the array `nums` and uses only constant extra space.

**Example 1:**

```
Input: nums = [1,3,4,2,2]
Output: 2
```

**Example 2:**

```
Input: nums = [3,1,3,4,2]
Output: 3
```

 **Constraints:**

- `1 <= n <= 105`
- `nums.length == n + 1`
- `1 <= nums[i] <= n`
- All the integers in `nums` appear only **once** except for **precisely one integer** which appears **two or more** times.

### My Solution

```java
public int findDuplicate(int[] nums) {

    Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
    for (int num : nums){
        hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
        if (hashMap.get(num) == 2){
            return num;
        }
    }
    return 0;
}
```

### Standard Solution

This question has many solutions. The note would only take some of them which are well-written.

#### Solution #1 Sort(Easiest)

* Sort the array and find the duplicate one with for loop

```java
public int findDuplicate(int[] nums){
    Arrays.sort(nums);
    for (int i = 1; i < nums.length; i++){
        if (nums[i] == nums[i-1]){
            return nums[i];
        }
    }
    return -1;
}
```

* Time complexity: $O(n\log n)$

  Sorting takes $O(n \log n)$ time. This is followed by a linear scan, resulting in a total of $O(n \log n)$+ $O(n)$ = $O(n \log n)$ time.

* Space complexity: $O(\log n)$ in Java.

#### Solution #2 HashSet

```java
public int findDuplicate(int[] nums){
	Set<Integer> seen = new HashSet<Integer>();
    for (int num : nums){
        if (seen.contains(num)){
            return num;
        }
        seen.add(num);
    }
    return -1;
}
```

* Time complexity: $O(n)$
* Space complexity: $O(n)$

#### Solution #3 Negative Marking(need to learn)

* Since the array only contains positive integers, we can track each number (num) that has been seen before by flipping the sign of the number located at index num
* **Algorithm**
  1. Iterate over the array, evaluating each element (let's call the current element cur.
  2. Since we use negative marking, we must ensure that the current element (cur) is positive (i.e. if cur is negative, then use its absolute value).
  3. Check if nums[cur] is negative.
     - If it is, then we have already performed this operation for the same number, and hence cur is the duplicate number. Store cur as the duplicate and exit the loop.
     - Otherwise, flip the sign of nums[cur] (i.e. make it negative). Move to the next element and repeat step 3.
  4. Once we've identified the duplicate, we could just return the duplicate number. However, even though we were not able to meet the problem constraints, we can show that we are mindful of the constraints by restoring the array. This is done by changing all negative numbers to positive.

```java
public int findDuplicate(int[] nums) {
    int duplicate = -1;
    for (int i = 0; i < nums.length; i++) {
        int cur = Math.abs(nums[i]);
        if (nums[cur] < 0) {
            duplicate = cur;
            break;
        }
        nums[cur] *= -1;
    }

    // Restore numbers
    for (int i = 0; i < nums.length; i++)
        nums[i] = Math.abs(nums[i]);

    return duplicate;
}

```

* Time Complexity: $O(n)$
* Space Complexity: $O(1)$

#### Solution #4 Array as HashMap (Recursion)

* Use the Array as a HashMap -- map each number to its equivalent index in the array.
* The array starts from [1, n]
* Starts with index 0(since it must be out of place), if nums[first] == first, then we find a duplicate
* Otherwise, swap the numbers located at index 0 and at index first
* **Algorithm**:
  * Start with the first index (index 0) of the array. Call store(nums, 0) to store the number 0 at index 0.
    - Note that because all input numbers are in the range [1, n] no number will ever be mapped to index 00. Hence, index 00 can hold any dummy value, including 0.
  * store(nums, cur) uses the current number, cur, as the target index. It first backs up the number that's stored at the equivalent position (next = nums[cur]), and then overwrites that index with cur.
  * Now we need to deal with the number that was backed up (i.e. next). Recursively call store with this number as input (i.e. store(nums, next)) so it too can be placed at its equivalent index.
  * Repeat steps 2 and 3. At some point, nums[cur] will already contain the number cur, in which case we have found the duplicate number.
* **Example**:
  * To illustrate the algorithm, let's consider an example [3,3,5,4,1,3]:
    - store(nums, 0)
      - Here cur = 0 and nums[cur]=3
      - Back up the number 3 (that's at index 0)
      - Store 0 at index 0. The array is now: [$\underline0$,3,5,4,1,3]
      - Recursively call store(nums, 3)
    - store(nums, 3)
      - Back up the number 4 (at index 3)
      - Store 3 at index 3. The array is now: $[0,3,5,\underline3,1,3]$
      - Now call store(nums, 4)
    - store(nums, 4)
      - Back up the number 1 (at index 4)
      - Store 4 at index 4. The array is now $[0,3,5,3,\underline4,3]$
      - Now call store(nums, 1)
    - store(nums, 1)
      - Back up the number 3 (at index 1)
      - Store 1 at index 1. The array is now $[0,\underline1,5,3,4,3]$
      - Now call store(nums, 3)
    - store(nums, 3)
      - Since 3 already exists at index 3 in the array $[0,1,5,\underline3,4,3]$, and we have another instance of 3 that we are trying to store there, clearly that's the duplicate number. Return 3 as the duplicate, and stop execution.

```java
public int store(int[] nums, int cur){
    if (cur == nums[cur]){
        return cur;
    }
    int nxt = nums[cur];
    nums[cur] = cur;
    return store(nums, nxt);
}

public int findDuplicate(int[] nums){
    return store(nums, 0);
}
```

* Time Complexity: $O(n)$
* Space Complexity: $O(n)$

#### Solution #4.2 Array as HashMap(Iterative, Recommend)

* Similar to last solution, but using index 0 to hold the temp value.
* Swap the value if the nums[0] does not equal to nums[nums[0]], index does not equal to its value
* If equals, it means that is the duplicate one

* **Algorithm**:
  * At every iteration, compare the number at index 0 (i.e. nums[0]) to the number at index nums[0] (i.e. nums[nums[0]]).
  * Use index 0 as the source of all swapping because we know the number 0 is not in the array.
  * Take each number at index 0 (let's call it first) and swap it with the number at its equivalent index in the array (i.e. the number at nums[first]).
  * Repeat step 3 until first is the same as nums[first] (e.g. if the duplicate number is 3, then keep swapping nums[0] and nums[nums[0]] until both nums[3] == 3 and nums[0] == 3).

* **Example**:
  * $[3,3,5,4,1,3]$ // Compare nums[0] to nums[nums[0]] (i.e. nums[0] to nums[3]). 3 != 4. Swap them. Now the first 33 will be swapped into its correct position, and position 0 has 44.
  * [$\underline4,3,5,3,\underline1,3]$ // Compare nums[0] to nums[4]. 4 != 1. Not equal, so swap again. Now 4 is in its correct position.
  * $[\underline1,\underline3,5,3,4,3] $// Compare nums[0] with nums[1]. Not equal, swap.
  * $[\underline3,1,5,\underline3,4,3] $// Now nums[0] == nums[3] (both are 3). That's it! 3 is in both positions 0 and position 3, so it's the duplicate.

```java
public int findDuplicate(int[] nums){
    while (nums[0] != nums[nums[0]]){
        int nxt = nums[nums[0]];
        nums[nums[0]] = nums[0];
        nums[0] = nxt;
    }
    return nums[0];
}
```

* Time Complexity: $O(n)$
* Space Complexity: $O(1)$

