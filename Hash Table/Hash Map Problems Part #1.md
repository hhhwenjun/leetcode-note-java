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

## Intersection of Two Arrays II(Easy #350)

**Question**: Given two integer arrays `nums1` and `nums2`, return *an array of their intersection*. Each element in the result must appear as many times as it shows in both arrays and you may return the result in **any order**.

 **Example 1:**

```
Input: nums1 = [1,2,2,1], nums2 = [2,2]
Output: [2,2]
```

**Example 2:**

```
Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
Output: [4,9]
Explanation: [9,4] is also accepted.
```

 **Constraints:**

- `1 <= nums1.length, nums2.length <= 1000`
- `0 <= nums1[i], nums2[i] <= 1000`

### My Solution

```java
public int[] intersect(int[] nums1, int[] nums2){
    Map<Integer, Integer> map = new HashMap<>();
    List<Integer> intersectList = new ArrayList<>();
    
    for (int i = 0; i < nums1.length; i++){
        map.put(nums1[i], map.getOrDefault(nums1[i],0) + 1);
    }
    
    for (int j = 0; j < nums2.length; j++){
        if (map.containsKey(nums2[j])){
            intersectList.add(nums2[j]);
            map.put(nums2[j], map.get(nums2[j]) - 1);
            if (map.get(nums2[j]) == 0) map.remove(nums2[j]);
        }
    }
    int[] arr = intersectList.stream().mapToInt(Integer::intValue).toArray();
    return arr;
}
```

### Standard Solution

#### Solution #1 Hash Map

* Same as my solution
* Compare the length of the two arrays
* **Algorithm**:
  * If `nums1` is larger than `nums2`, swap the arrays.
  * For each element in `nums1`:
    - Add it to the hash map `m`.
      - Increment the count if the element is already there.
  * Initialize the insertion pointer (`k`) with zero.
  * Iterate along `nums2`:
    - If the current number is in the hash map and count is positive:
      - Copy the number into `nums1[k]`, and increment `k`.
      - Decrement the count in the hash map.
  * Return first `k` elements of `nums1`.

```java
public int[] intersect(int[] nums1, int[] nums2){
    if (nums1.length > nums2.length){
        return intersect(nums2, nums1);//short comes first
    }
    
    HashMap<Integer, Integer> m = new HashMap<>();
    for (int n : nums1){
        m.put(n, m.getOrDefault(n, 0) + 1);
    }
    int k = 0;
    for (int n : nums2){
        int cnt = m.getOrDefault(n, 0);
        if (cnt > 0){
            nums1[k++] = n;//modify on the first array
            m.put(n, cnt - 1);
        }
    }
    return Arrays.copyOfRange(nums1, 0, k);
}
```

* `copyOfRange(array, from_index, to_index)` to cut the part of the array
* Time Complexity: $\mathcal{O}(n + m)$, where n and m are the lengths of the arrays. We iterate through the first, and then through the second array; insert and lookup operations in the hash map take a constant time.
* Space Complexity: $\mathcal{O}(\min(n, m))$. We use hash map to store numbers (and their counts) from the smaller array.

#### Solution #2 Sort

* Recommend the method when the input is sorted
* **Algorithm**:
  * Sort `nums1` and `nums2`.
  * Initialize `i`, `j` and `k` with zero.
  * Move indices `i` along `nums1`, and `j` through `nums2`:
    - Increment `i` if `nums1[i]` is smaller.
    - Increment `j` if `nums2[j]` is smaller.
    - If numbers are the same, copy the number into `nums1[k]`, and increment `i`, `j` and `k`.
  * Return first `k` elements of `nums1`.

```java
public int[] intersect(int[] nums1, int[] nums2){
    Arrays.sort(nums1);
    Arrays.sort(nums2);
    int i = 0, j = 0, k = 0;
    while(i < nums1.length && j < nums2.length){
        if (nums1[i] < nums2[j]){
            ++i;
        } else if (nums1[i] > nums2[j]){
            ++j;
        } else {
            nums1[k++] = nums1[i++];//previous spot is used to store intersect
            ++j;
        }
    }
    return Arrays.copyOfRange(nums1, 0, k);
}
```

- Time Complexity: $\mathcal{O}(n\log{n} + m\log{m})$, where n and m are the lengths of the arrays. We sort two arrays independently, and then do a linear scan.
- Space Complexity: from $\mathcal{O}(\log{n} + \log{m})$ to $\mathcal{O}(n + m)$, depending on the implementation of the sorting algorithm. For the complexity analysis purposes, we ignore the memory required by inputs and outputs.

## Contains Duplicate II(Easy #219)

**Question**: Given an integer array `nums` and an integer `k`, return `true` if there are two **distinct indices** `i` and `j` in the array such that `nums[i] == nums[j]` and `abs(i - j) <= k`.

 **Example 1:**

```
Input: nums = [1,2,3,1], k = 3
Output: true
```

**Example 2:**

```
Input: nums = [1,0,1,1], k = 1
Output: true
```

**Example 3:**

```
Input: nums = [1,2,3,1,2,3], k = 2
Output: false
```

 **Constraints:**

- `1 <= nums.length <= 105`
- `-109 <= nums[i] <= 109`
- `0 <= k <= 105`

### My Solution

```java
public boolean containsNearbyDuplicate(int[] nums, int k){
    Map<Integer, Integer> map = new HashMap<>();
    
    for (int i = 0; i < nums.length; i++){
        if (map.containsKey(nums[i])){
            int diff = Math.abs(map.get(nums[i]) - i);
            if (diff <= k) return true;
        }
        map.put(nums[i], i);
    }
    return false;
}
```

### Standard Solution

![img](https://media.geeksforgeeks.org/wp-content/cdn-uploads/20210315172345/Java-Collections-Framework-Hierarchy.png)

#### Solution #1 TreeSet

* A BST supports `search`, `delete` and `insert` operations all in $O(\log k)$ time, where k*k* is the number of elements in the BST.

* **Algorithm**:
  * Loop through the array, for each element do
    - Search current element in the BST, return `true` if found
    - Put current element in the BST
    - If the size of the BST is larger than k*k*, remove the oldest item.
* **Exceed time limit**(not recommended in the question), just provide some thoughts

```java
public boolean containsNearbyDuplicatee(int[] nums, int k){
    Set<Integer> set = new TreeSet<>();
    for (int i = 0; i < nums.length; ++i){
        if (set.contains(nums[i])) return true;
        set.add(nums[i]);
        if (set.size() > k){
            set.remove(nums[i - k]);
        }
    }
    return false;
}
```

* Time complexity : $O(n \log (\min(k,n)))$.
* Space complexity : $O(\min(n,k))$.

#### Solution #2 HashSet

* Keep a sliding window

```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < nums.length; ++i){
        if (set.contains(nums[i])) return true;
        set.add(nums[i]);
        if (set.size() > k){
            set.remove(nums[i - k]);
        }
    }
    return false;
}
```

- Time complexity : $O(n)$). We do n operations of `search`, `delete` and `insert`, each with constant time complexity.
- Space complexity : $O(\min(n,k))$. The extra space required depends on the number of items stored in the hash table, which is the size of the sliding window, $\min(n,k)$.

## Contains Duplicate III(Medium #220)

**Question**: Given an integer array `nums` and two integers `k` and `t`, return `true` if there are **two distinct indices** `i` and `j` in the array such that `abs(nums[i] - nums[j]) <= t` and `abs(i - j) <= k`.

 **Example 1:**

```
Input: nums = [1,2,3,1], k = 3, t = 0
Output: true
```

**Example 2:**

```
Input: nums = [1,0,1,1], k = 1, t = 2
Output: true
```

**Example 3:**

```
Input: nums = [1,5,9,1,5,9], k = 2, t = 3
Output: false
```

 **Constraints:**

- `1 <= nums.length <= 2 * 104`
- `-231 <= nums[i] <= 231 - 1`
- `0 <= k <= 104`
- `0 <= t <= 231 - 1`

### My Solution

* Sliding window for the loop
* Nested loop(not efficient)
* Naïve search method, exceed time limit, not compliant with constraints.

```java
public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t){
    int low, high;
    
    for (int high = 0; high < nums.length; high++){
        for (int low = high - k; low < high && low >= 0; low++){
            int diff = Math.abs((long)nums[high] - nums[low]);
            if (diff <= t) return true;
        }
    }
    return false;
}
```

### Standard Solution

#### Solution #1 Tree Set

* By utilizing self-balancing Binary Search Tree, one can keep the window ordered at all times with logarithmic time `insert` and `delete`.
* We need a *dynamic* data structure that supports faster `insert`, `search` and `delete`. Self-balancing Binary Search Tree (BST) is the right data structure. 

* Why does self-balancing matter? That is because most operations on a BST take time directly proportional to the height of the tree. 
* **Algorithm**:
  * Initialize an empty BST `set`
  * Loop through the array, for each element x
    - Find the *smallest* element s in `set` that is *greater* than or equal to x, return true if $s - x \leq t$
    - Find the *greatest* element g in `set` that is *smaller* than or equal to x, return true if $x - g \leq t$
    - Put x in `set`
    - If the size of the set is larger than k, remove the oldest item.
  * Return false

```java
public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t){
    TreeSet<Integer> set = new TreeSet<>();
    for (int i = 0; i < nums.length; i++){
        //Find the successor of current element
        Integer s = set.ceiling(nums[i]);
        if (s != null && (long) s <= nums[i] + t) return true;
        
        //Find the predecessor of current element
        Integer g = set.floor(nums[i]);
        if (g != null && nums[i] <= (long) g + t) return true;
        
        set.add(nums[i]);
        if (set.size() > k){
            set.remove(nums[i - k]);
        }
    }
    return false;
}
```

* The **ceiling()** method of **java.util.TreeSet** class is used to return the least element in this set greater than or equal to the given element, or null if there is no such element.
* Time complexity: $O(n \log (\min(n,k)))$. We iterate through the array of size n. For each iteration, it costs $O(\log \min(k, n))$ time (`search`, `insert` or `delete`) in the BST, since the size of the BST is upper bounded by both k and n.
* Space complexity: $O(\min(n,k))$. Space is dominated by the size of the BST, which is upper bounded by both k*k* and n.

#### Solution #2 Bucket Sort

* Inspired by `bucket sort`, we can achieve linear time complexity in our problem using *buckets* as window.
* **Bucket Sort**: Bucket sort is a sorting algorithm that works by distributing the elements of an array into a number of buckets. Each bucket is then sorted individually, using a different sorting algorithm.
  *  Each of the eight elements is in a particular bucket. For element with value x, its bucket label is $x / w$ and here we have $w = 10$. 
  * We apply the above bucketing principle and design buckets covering the ranges of $..., [0,t], [t+1, 2t+1]$

<img src="https://leetcode.com/problems/contains-duplicate-iii/Figures/220/220_Buckets.png" alt="Illustration of buckets" style="zoom: 25%;" />

* Each of our buckets contains **at most one element** at any time, because two elements in a bucket means "almost duplicate" and we can return early from the function.
* A HashMap with an element associated with a bucket label is enough for our purpose.

```java
//Get the ID of the bucket from element value x and bucket width w
//In java, `-3/5 = 0` but we need `-3/5=-1`
private long getID(long x, long w){
    return x < 0 ? (x + 1) / w - 1 : x / w;
}

public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t){
    if (t < 0) return false;
    Map<long, long> d = new HashMap<>();
    long w = (long)t + 1;
    for (int i = 0; i < nums.length; ++i){
        long m = getID(nums[i], w);
        // check if bucket m is empty, each bucket may contain at most one element
        if (d.containsKey(m)) return true;
        // check the neighbor buckets for almost duplicate
        if (d.containsKey(m - 1) && Math.abs(nums[i] - d.get(m - 1)) < w); return true;
        if (d.containsKey(m + 1) && Math.abs(nums[i] - d.get(m + 1)) < w); return true;
        // now bucket m is empty and no almost duplicate in neighbor buckets
        d.put(m, (long)nums[i]);
        if (i >= k) d.remove(getID(nums[i - k], w));
    }
    return false;
}
```

* Time complexity: $O(n)$. For each of the n elements, we do at most three searches, one insert, and one delete on the HashMap, which costs constant time on average. Thus, the entire algorithm costs $O(n)$ time.
* Space complexity: $O(\min(n,k))$. Space is dominated by the HashMap, which is linear to the size of its elements. The size of the HashMap is upper bounded by both n and k. Thus the space complexity is $O(\min(n, k))$.

## Logger Rate Limiter(Easy #359)

**Question**: Design a logger system that receives a stream of messages along with their timestamps. Each **unique** message should only be printed **at most every 10 seconds** (i.e. a message printed at timestamp `t` will prevent other identical messages from being printed until timestamp `t + 10`).

All messages will come in chronological order. Several messages may arrive at the same timestamp.

Implement the `Logger` class:

- `Logger()` Initializes the `logger` object.
- `bool shouldPrintMessage(int timestamp, string message)` Returns `true` if the `message` should be printed in the given `timestamp`, otherwise returns `false`.

 **Example 1:**

```
Input
["Logger", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage", "shouldPrintMessage"]
[[], [1, "foo"], [2, "bar"], [3, "foo"], [8, "bar"], [10, "foo"], [11, "foo"]]
Output
[null, true, true, false, false, false, true]

Explanation
Logger logger = new Logger();
logger.shouldPrintMessage(1, "foo");  // return true, next allowed timestamp for "foo" is 1 + 10 = 11
logger.shouldPrintMessage(2, "bar");  // return true, next allowed timestamp for "bar" is 2 + 10 = 12
logger.shouldPrintMessage(3, "foo");  // 3 < 11, return false
logger.shouldPrintMessage(8, "bar");  // 8 < 12, return false
logger.shouldPrintMessage(10, "foo"); // 10 < 11, return false
logger.shouldPrintMessage(11, "foo"); // 11 >= 11, return true, next allowed timestamp for "foo" is 11 + 10 = 21
```

 **Constraints:**

- `0 <= timestamp <= 109`
- Every `timestamp` will be passed in non-decreasing order (chronological order).
- `1 <= message.length <= 30`
- At most `104` calls will be made to `shouldPrintMessage`.

### My Solution

```java
class Logger {
    
    private HashMap<String, Integer> messageMap;

    public Logger() {
        messageMap = new HashMap<>();
    }
    
    public boolean largerThanTimestamp(int timestamp, String message){
        if (messageMap.get(message) > timestamp){
                return false;
        }
        else {
            messageMap.put(message, timestamp + 10);
            return true;
        }
    }
    
    public boolean shouldPrintMessage(int timestamp, String message) {
        
        if (!messageMap.containsKey(message)) {
            messageMap.put(message, timestamp + 10);
            return true;
        }
        else return largerThanTimestamp(timestamp, message);
    }
}

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */
```

* Claim private field correctly, claim the data type, initialize it in the constructor
* Avoid nasty loops

### Standard Solution

#### Solution #1 Hash Table

* Similar to my solution
* The hash table keeps all the unique messages along with the latest timestamp that the message was printed.

```java
class Logger {
    private HashMap<String, Integer> msgDict;
    
    /** Initialize your data structure here**/
    public Logger(){
        msgDict = new HashMap<String, Integer>();
    }
    
    /** Returns true if the msg should be printed in the given timestamp**/
    public booolean shouldPrintMessage(int timestamp, String message){
        if (!this.msgDict.containsKey(message)){
            this.msgDict.put(message, timestamp);
            return true;
        }
        
        Integer oldTimestamp = this.msgDict.get(message);
        if (timestamp - oldTimestamp >= 10){
            this.msgDict.put(message, timestamp);
            return true;
        }
        else return false;
    }
}
```

- Time Complexity: $\mathcal{O}(1)$. The lookup and update of the hashtable takes a constant time.
- Space Complexity: $\mathcal{O}(M)$ where M is the size of all incoming messages. Over the time, the hashtable would have an entry for each unique message that has appeared.

#### Solution #2 Queue + Set

![pic](https://leetcode.com/problems/logger-rate-limiter/Figures/359/359_deque.png)

* **Algorithm**:
  * First of all, we use a queue as a sort of sliding window to keep all the printable messages in certain time frame (10 seconds).
  * At the arrival of each incoming message, it comes with a `timestamp`. This timestamp implies the evolution of the sliding windows. Therefore, we should first invalidate those *expired* messages in our queue.
  * Since the `queue` and `set` data structures should be in sync with each other, we would also remove those expired messages from our message set.
  * After the updates of our message queue and set, we then simply check if there is any duplicate for the new incoming message. If not, we add the message to the queue as well as the set.

```java
class Pair<U, V>{
    public U first;
    public V second;
    
    public Pair(U first, V second){
        this.first = first;
        this.second = second;
    }
}

class Logger{
    private LinkedList<Pair<String, Integer>> msgQueue;
    private HashSet<String> msgSet;
    
    /** Initialize your data structure here**/
    public Logger(){
        msgQueue = new LinkedList<Pair<String, Integer>>();
        msgSet = new HashSet<String>();
    }
    
    public boolean shouldPrintMessage(int timestamp, String message){
        //clean up
        while(msgQueue.size() > 0){
            Pair<String, Integer> head = msgQueue.getFirst();
            if (timestamp - head.second >= 10){//outdated msg
                msgQueue.removeFirst();
                msgSet.remove(head.first);
            } else
              break;
        }
        if (!msgSet.contains(message)){
            Pair<String, Integer> newEntry = 
                new Pair<String, Integer>(message, timestamp);
            msgQueue.addLast(newEntry);
            msgSet.add(message);
            return true;
        } else return false;
    }
}
```

* Be familiar with queue structure and methods
* Add to queue and set, remove from queue and set is a little redundant
* Time Complexity: $\mathcal{O}(N)$ where N is the size of the queue. 
* Space Complexity: $\mathcal{O}(N)$ where N is the size of the queue. We keep the incoming messages in both the queue and set. The upper bound of the required space would be 2N, if we have no duplicate at all.
