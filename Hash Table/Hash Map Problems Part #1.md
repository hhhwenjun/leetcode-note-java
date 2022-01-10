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

## Two Sum II - Input Array Is Sorted(Easy #167)

**Question**: Given a **1-indexed** array of integers `numbers` that is already ***sorted in non-decreasing order\***, find two numbers such that they add up to a specific `target` number. Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return *the indices of the two numbers,* `index1` *and* `index2`*, **added by one** as an integer array* `[index1, index2]` *of length 2.*

The tests are generated such that there is **exactly one solution**. You **may not** use the same element twice.

 **Example 1:**

```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

**Example 2:**

```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
```

**Example 3:**

```
Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
```

### My Solution

```java
public int[] twoSum(int[] numbers, int target){
    int low = numbers[0];
    int len = numbers.length;
    int high = numbers[len - 1];
    int i = 0;
    int j = len - 1;

    while(low + high != target && i != j){

        if (low + high < target) {
            i++;
            low = numbers[i];
        }
        else if(low + high > target) {
            j--;
            high = numbers[j];
        }
    }

    return new int[]{i + 1, j + 1};
}
```

### Standard Solution

#### Solution #1 Two Pointers

* Concept is same as my solution
* It is a better solution when the input array is sorted.

```java
public int[] twoSum(int[] numbers, int target){
    int lo = 0, hi = numbers.length - 1;
    while(lo < hi){
        int curSum = numbers[lo] + numbers[hi];
        if (curSum == target){
            return new int[]{lo + 1, hi + 1};
        }
        else if (curSum > target){
            hi--;
        }
        else {
            lo++;
        }
    }
    return new int[]{-1, -1};//in case there is no solution
}
```

## Isomorphic Strings(Easy #205)

**Question**: Given two strings `s` and `t`, *determine if they are isomorphic*.

Two strings `s` and `t` are isomorphic if the characters in `s` can be replaced to get `t`.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.

 **Example 1:**

```
Input: s = "egg", t = "add"
Output: true
```

**Example 2:**

```
Input: s = "foo", t = "bar"
Output: false
```

**Example 3:**

```
Input: s = "paper", t = "title"
Output: true
```

 **Constraints:**

- `1 <= s.length <= 5 * 104`
- `t.length == s.length`
- `s` and `t` consist of any valid ascii character.

### My Solution

```java
public boolean isIsomorphic(String s, String t) {
    Map<Character, Character> hashMap = new HashMap<>();

     if (s.length() != t.length()) return false;

     for (int i = 0; i < s.length(); i++){
        if (!hashMap.containsKey(s.charAt(i)) && !hashMap.containsValue(t.charAt(i))){
            hashMap.put(s.charAt(i), t.charAt(i));
        } 
        else if(hashMap.containsKey(s.charAt(i)) &&
                hashMap.get(s.charAt(i)) == t.charAt(i)){
            continue;
        }
        else{
            return false;
        }
    }
    return true;
}
```

* Find character in the string: `str.charAt(i)`
* `Character` is the wrapper class of `char`
* Length of string using `str.length()`
* Map character to character

### Standard Solution 

#### Solution #1 Character Mapping with Dictionary

* Similar to my solution

* We can map a character only to itself or to one other character.

* No two character should map to same character.

* Replacing each character in string `s` with the character it is mapped to results in string `t`.

* Three cases:

  * If the characters don't have a mapping, we add one in the dictionary and move on.
  * The characters already have a mapping in the dictionary. If that is the case, then we're good to go.

  <img src="https://leetcode.com/problems/isomorphic-strings/Figures/205/img2.png" alt="Example for when we have a mapping" style="zoom: 33%;" />

  * The final case is when a mapping already exists for one of the characters but it *doesn't map to the other character at hand*. In this case, we can safely conclude that the given strings are not isomorphic and we can return.

```java
public boolean isIsomorphic(String s, String t){
    int[] mappingDictStoT = new int[256];
    Arrays.fill(mappingDictStoT, -1);
    
    int[] mappingDictTtoS = new int[256];
    Arrays.fill(mappingDictTtoS, -1);
    
    for (int i = 0; i < s.length(); ++i){
        char c1 = s.charAt(i);
        char c2 = t.charAt(i);
        
        // Case 1: No mapping exists in either of the dictionaries
        if (mappingDictStoT[c1] == -1 && mappingDictTtoS[c2] == -1){
            mappingDictStoT[c1] = c2;
            mappingDictTtoS[c2] = c1;
        }
        
        // Case 2: Either mapping doesn't exist in one of the dict or mapping
        // exists and it doesn't match in either of the dict or both
        else if (!(mappingDictStoT[c1] == c2 && mappingDictTtoS[c2] == c1)){
            return false;
        }
    }
    return true;
}
```

* Time complexity : $O(N)$
* Space complexity: $O(1)$

#### Solution #2 First Occurrence Transformation

* **Algorithm**:
  * Define a function called `transform` that takes a string as an input and returns a new string with modifications as explained in the intuition section.
    1. We maintain a dictionary to store the character to index mapping for the given string.
    2. For each character, we look up the mapping in the dictionary. If there is a mapping, that means this character already has its first occurrence recorded and we simply use the first occurrence's index in the new string. Otherwise, we use the current index for the first occurrence.
  * We find the transformed strings for both of our input strings. Let's say the transformed strings are `s1` and `s2` respectively.
  * If `s1 == s2`, that implies the two input strings are isomorphic. Otherwise, they're not.

```java
private String transformString(String s){
    Map<Character, Integer> indexMapping = new HashMap<>();
    StringBuilder builder = new StringBuilder();
    
    for (int i = 0; i < s.length(); i++){
        char c1 = s.charAt(i);
        
        if (!indexMapping.containsKey(c1)){
            indexMapping.put(c1, i);
        }
        
        builder.append(Integer.toString(indexMapping.get(c1)));
        builder.append(" ");
    }
    return builder.toString();
}

public boolean isIsomorphic(String s, String t){
    return transformString(s).equals(transformString(t));
}
```

* Time Complexity: $O(N)$. 
* Space Complexity: $O(N)$. 

## 3Sum(Medium #15)

**Question**: Given an integer array nums, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must not contain duplicate triplets.

**Example 1:**

```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
```

**Example 2:**

```
Input: nums = []
Output: []
```

**Example 3:**

```
Input: nums = [0]
Output: []
```

 **Constraints:**

- `0 <= nums.length <= 3000`
- `-105 <= nums[i] <= 105`

### My Solution

```java
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> triplets = new ArrayList<List<Integer>>();
    Set<ArrayList<Integer>> numSet = new HashSet<>();
    Map<Integer, Integer> hashMap = new HashMap<>();

    for(int i = 0; i < nums.length; i++){
        int req = 0 - nums[i];                       
        for(int j = i + 1; j < nums.length; j++){
            int reqPart = req - nums[j];
            if (hashMap.containsKey(reqPart)){
                ArrayList<Integer> numSum = 
                    new ArrayList<>(Arrays.asList(nums[i], nums[j], reqPart));
                Collections.sort(numSum);
                int k = hashMap.get(reqPart);
                if (i != j && j != k && i != k && numSet.add(numSum)){
                    triplets.add(numSum);
                }     
            }
        }
        hashMap.put(nums[i], i);
    }        
    return triplets;
}
```

* ArrayList use `Collections.sort(ArrayList)`
* Initialize ArrayList: `ArrayList<Integer> num = new ArrayList<>(Arrays.asList(1,2,3));`
* Important to select the location of add/put within a loop

### Standard Solution

#### Solution #1 Two Pointers

* First sort the array, using two pointers for representing
* As a quick refresher, the pointers are initially set to the first and the last element respectively. We compare the sum of these two elements to the target. If it is smaller, we increment the lower pointer `lo`. Otherwise, we decrement the higher pointer `hi`. 
* **Algorithm**
  * For the main function:
    - Sort the input array `nums`.
    - Iterate through the array:
      - If the current value is greater than zero, break from the loop. Remaining values cannot sum to zero.
      - If the current value is the same as the one before, skip it.
      - Otherwise, call `twoSumII` for the current position `i`.
  * For `twoSumII` function:
    - Set the low pointer `lo` to `i + 1`, and high pointer `hi` to the last index.
    - While low pointer is smaller than high:
      - If `sum` of `nums[i] + nums[lo] + nums[hi]` is less than zero, increment `lo`.
      - If `sum` is greater than zero, decrement `hi`.
      - Otherwise, we found a triplet:
        - Add it to the result `res`.
        - Decrement `hi` and increment `lo`.
        - Increment `lo` while the next value is the same as before to avoid duplicates in the result.

```java
public List<List<Integer>> threeSum(int[] nums){
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < nums.length && nums[i] <= 0; i++){
        if(i == 0 || nums[i-1] != nums[i]){
            twoSumII(nums, i, res);
        }
    }
    return res;
}

void twoSumII(int[] nums, int i, List<List<Integer>> res){
    int lo = i + 1, hi = nums.length - 1;
    while(lo < hi){
        int sum = nums[i] + nums[lo] + nums[hi];
        if (sum < 0){
            lo++;
        } else if (sum > 0){
            hi--;
        } else {
            res.add(Arrays.asList(nums[i], nums[lo++], nums[hi--]));
            while (lo < hi && nums[lo] == nums[lo - 1]) lo++;
        }
    }
}
```

* Time complexity: : $\mathcal{O}(n^2)$. `twoSumII` is $\mathcal{O}(n)$, and we call it n times. Sorting the array takes $\mathcal{O}(n\log{n})$, so overall complexity is $\mathcal{O}(n\log{n} + n^2)$. This is asymptotically equivalent to $\mathcal{O}(n^2)$.
* Space Complexity: from $\mathcal{O}(\log{n})$ to $\mathcal{O}(n)$, depending on the implementation of the sorting algorithm. For the purpose of complexity analysis, we ignore the memory required for the output.

#### Solution #2 HashSet

* **Algorithm**:
  * For the main function:
    - Sort the input array `nums`.
    - Iterate through the array:
      - If the current value is greater than zero, break from the loop. Remaining values cannot sum to zero.
      - If the current value is the same as the one before, skip it.
      - Otherwise, call `twoSum` for the current position `i`.
  * For `twoSum` function:
    - For each index `j > i` in `A`
      - Compute `complement` value as `-nums[i] - nums[j]`.
      - If `complement` exists in hash set `seen`
        - We found a triplet - add it to the result `res`.
        - Increment `j` while the next value is the same as before to avoid duplicates in the result.
      - Add `nums[j]` to hash set `seen`

```java
public List<List<Integer>> threeSum(int[] nums){
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < nums.length && nums[i] <= 0; i++){
        if (i == 0 || nums[i - 1] != nums[i]){
            twoSum(nums, i, res);
        }
    }
}

void twoSum(int[] nums, int i, List<List<Integer>> res){
    var seen = new HashSet<Integer>();//build a new set every time
    for (int j = i + 1; j < nums.length; j++){
        int complement = - nums[i] - nums[j];
        if (seen.contains(complement)){
            res.add(Arrays.asList(nums[i], nums[j], complement));
            while (j + 1 < nums.length && nums[j] == nums[j + 1]) j++;
        }
    }
    seen.add(nums[j]);
}
```

* Time Complexity: $\mathcal{O}(n^2)$
* Space Complexity: $\mathcal{O}(n)$ for the hash set.

#### Solution #3 No-sort

* Similar to my solution
* **Algorithm**: The algorithm is similar to the hash Set approach above. We just need to add few optimizations so that it works efficiently for repeated values:
  * Use another hashset `dups` to skip duplicates in the outer loop.
  * Instead of re-populating a hashset every time in the inner loop, we can use a hashmap and populate it once. Values in the hashmap will indicate whether we have encountered that element in the current iteration. When we process `nums[j]` in the inner loop, we set its hashmap value to `i`. This indicates that we can now use `nums[j]` as a complement for `nums[i]`.
    - This is more like a trick to compensate for container overheads. The effect varies by language, e.g. for C++ it cuts the runtime in half. Without this trick the submission may time out.

```java
public List<List<Integer>> threeSum(int[] nums){
    Set<List<Integer>> res = new HashSet<>();
    Set<Integer> dups = new HashSet<>();
    Map<Integer, Integer> seen = new HashMap<>();
    
    for (int i = 0; i < nums.length; ++i){
        if (dups.add(nums[i])){//ignore the duplicate number
            for (int j = i + 1; j < nums.length; j++){
                int complement = - nums[i] - nums[j];
                if (seen.containsKey(complement) && seen.get(complement) == i){
                    List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                    Collections.sort(triplet);
                    res.add(triplet);
                }
                seen.put(nums[j], i);
            }
        }
    }
    return new ArrayList(res);
}
```

* Time Complexity: $\mathcal{O}(n^2)$. We have outer and inner loops, each going through n elements.
* Space Complexity: $\mathcal{O}(n)$ for the hashset/hashmap.

## Minimum Index Sum of Two Lists(Easy #599)

**Question**: Suppose Andy and Doris want to choose a restaurant for dinner, and they both have a list of favorite restaurants represented by strings.

You need to help them find out their **common interest** with the **least list index sum**. If there is a choice tie between answers, output all of them with no order requirement. You could assume there always exists an answer.

**Example 1:**

```
Input: list1 = ["Shogun","Tapioca Express","Burger King","KFC"], list2 = ["Piatti","The Grill at Torrey Pines","Hungry Hunter Steakhouse","Shogun"]
Output: ["Shogun"]
Explanation: The only restaurant they both like is "Shogun".
```

**Example 2:**

```
Input: list1 = ["Shogun","Tapioca Express","Burger King","KFC"], list2 = ["KFC","Shogun","Burger King"]
Output: ["Shogun"]
Explanation: The restaurant they both like and have the least index sum is "Shogun" with index sum 1 (0+1).
```

 **Constraints:**

- `1 <= list1.length, list2.length <= 1000`
- `1 <= list1[i].length, list2[i].length <= 30`
- `list1[i]` and `list2[i]` consist of spaces `' '` and English letters.
- All the stings of `list1` are **unique**.
- All the stings of `list2` are **unique**.

### My Solution

```java
public String[] findRestaurant(String[] list1, String[] list2){
    Map<String, Integer> hashMap1 = new HashMap<>();
    List<String> intersectList = new ArrayList<>();
    int len1 = list1.length;
    int len2 = list2.length;
    int minSum = Integer.MAX_VALUE;
    
    for(int i = 0; i < len1; i++){
        hashMap1.put(list1[i], i);
    }
    
    for (int j = 0; j < len2; j++){
        if (hashMap1.containsKey(list2[j])){
            if (j + hashMap1.get(list2[j]) < minSum){
                intersectList.clear();
                intersectList.add(list2[j]);
                minSum = j + hashMap1.get(list2[j]);
            }
            else if(j + hashMap1.get(list2[j]) == minSum){
                intersectList.add(list2[j]);
            }
        }
    }
    
    return intersectList.toArray(new String[intersectList.size()]);
}
```

* Use `clear` method since we only need the one with minimum sum
* Find the max value by `Integer.MAX_VALUE`
* Use array list then covert it to array `toArray` method,  check the size of the list
* Sum the values and compare the sum

### Standard Solution

#### Solution #1 Using HashMap(not recommended)

* Nested loop, sum $i + j$ to find the value and make entry to the map.

```java
public String[] findRestaurant(String[] list1, String[] list2){
    HashMap<Integer, List<String>> map = new HashMap<>();
    for (int i = 0; i < list1.length; i++){
        for (int j = 0; j < list2.length; j++){
            if (list1[i].equals(list2[j])){
                if (!map.containsKey(i + j)){
                    map.put(i + j, new ArrayList<String>());
                }
                map.get(i + j).add(list1[i]);
            }
        }
    }
//    int min_index_sum = Integer.MAX_VALUE;
//    for (int key : map.keySet()){
//        min_index_sum = Math.min(min_index_sum, key);
//    }
//    String[] res = new String[map.get(min_index_sum).size()];
//    return map.get(min_index_sum).toArray(res);
    
    int min_index_sum = Collections.min(map.keySet());
    return map.get(min_index_sum).toArray(new String[map.get(min_index_sum).size()]);
}
```

* Slower with the commented codes
* Map integer with lists, add duplicate restaurant to the **corresponding list**
* Use `Collections.min(map.keySet())` to find the minimum value
  * `map.values()` and `map.keySet()` can apply `Collections.min()`
* Compare one by one then use `Math.min(value1, value2)`
* For loop in map keys: 

```java
for (int key : map.keySet()){
    minSum = Math.min(value1, value2);
}
```

* Time complexity: $O(l1∗l2∗x)$
* Space complexity: $O(l1∗l2∗x)$

#### Solution #2 Using HashMap(linear, recommended)

* Same as my solution but a cleaner version

```java
public String[] findRestaurant(String[] list1, String[] list2){
    HashMap<String, Integer> map = new HashMap<>();
    for (int i = 0; i < list1.length; i++){
        map.put(list1[i],i);
    }
    List<String> res = new ArrayList<>();
    int min_sum = Integer.MAX_VALUE, sum;
    for (int j = 0; j < list2.length && j <= min_sum; j++){
        if (map.containsKey(list2[j])){
            sum = j + map.get(list2[j]);
            if (sum < min_sum){
                res.clear();
                res.add(list2[j]);
                min_sum = sum;
            }
            else if (sum == min_sum){
                res.add(list2[j]);
            }
        }
    }
    return res.toArray(new String[res.size()]);
}
```

* Time complexity : $O(l_1+l_2)$.
* Space complexity : $O(l_1*x)$

## First Unique Character in a String(Easy #387)

**Question**: Given a string `s`, *find the first non-repeating character in it and return its index*. If it does not exist, return `-1`.

 **Example 1:**

```
Input: s = "leetcode"
Output: 0
```

**Example 2:**

```
Input: s = "loveleetcode"
Output: 2
```

**Example 3:**

```
Input: s = "aabb"
Output: -1
```

 **Constraints:**

- `1 <= s.length <= 105`
- `s` consists of only lowercase English letters.

### My Solution

```java
public int firstUniqChar(String s){
	Map<Character, Integer> charMap = new HashMap<>();
    
    for (Character chr : s){
        charMap.put(s, charMap.getOrDefault(s,0) + 1);
    }
    
    for (int i = 0; i < s.length; i++){
        if (charMap.get(s.charAt(i)) == 1) return i;
    }
	return -1;
}
```

### Standard Solution

* Same as my solution
* Use hashmap to store characters and corresponding number of times
* Then loop to find the first one which number of times is 1
* String need to use `.length()`, array can use `.length`
  * [`length()` vs `length`](https://www.geeksforgeeks.org/length-vs-length-java/)

* Time complexity : $\mathcal{O}(N)$ since we go through the string of length `N` two times.
* Space complexity : $\mathcal{O}(1)$ because English alphabet contains 26 letters.
