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

