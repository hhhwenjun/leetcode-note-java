# Hash Map Problems Part #1

## Two Sum(Easy #1)

**Question**: Given an array of integers `nums` and an integer `target`, return *indices of the two numbers such that they add up to `target`*.

You may assume that each input would have exactly one solution, and you may not use the *same* element twice.

You can return the answer in any order.

**Example 1:**

```
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Output: Because nums[0] + nums[1] == 9, we return [0, 1].
```

**Example 2:**

```
Input: nums = [3,2,4], target = 6
Output: [1,2]
```

**Example 3:**

```
Input: nums = [3,3], target = 6
Output: [0,1]
```

 **Constraints:**

- `2 <= nums.length <= 104`
- `-109 <= nums[i] <= 109`
- `-109 <= target <= 109`
- **Only one valid answer exists.**

### My Solution

```java
public int[] twoSum(int[] nums, int target) {

    Map<Integer, Integer> hashMap = new HashMap<>();

    for (int i = 0; i < nums.length; i++){
        hashMap.put(nums[i], i); //Always use input as key, times/index as values
    }
    for (int i = 0; i < nums.length; i++){
        int req = target - nums[i];
        if (hashMap.containsKey(req) && hashMap.get(req) != i){//index cannot be the same
            return new int[] {i, hashMap.get(req)};
        }
    }
    return null;
}
```

### Standard Solution

#### Solution #1 Two-pass Hash Table

* Same as my solution
* Reduce the lookup time from $O(n)$ to $O(1)$ by trading space for speed.
* Use input nums as keys, index as values in a hash map.
* Time complexity: $O(n)$. 
* Space complexity: $O(n)$.

#### Solution #2 One-pass Hash Table

* While we are iterating and inserting elements into the hash table, we also look back to check if current element's complement already exists in the hash table

```java
public int[] twoSum(int[] nums, int target){
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++){
        int complement = target - nums[i];
        if (map.containsKey(complement)){
            return new int[]{i, map.get(complement)};
        }
        map.put(nums[i], i);
    }
    return null;
}
```

* Time and space complexity is same as last question.

