# Hash Map Problems Part #2

## Longest Substring Without Repeating Characters(Medium #3)

**Question**: Given a string `s`, find the length of the **longest substring** without repeating characters.

 **Example 1:**

```
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
```

**Example 2:**

```
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
```

**Example 3:**

```
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
```

 **Constraints:**

- `0 <= s.length <= 5 * 104`
- `s` consists of English letters, digits, symbols and spaces.

### My Solution

* Should be slicing window, until you find duplicate
* This one cannot work properly. It **skip number while sliding**

```java
public int lengthOfLongestSubstring(String s) {
    public int lengthOfLongestSubstring(String s) {
        int wordLength = 0;  
        Map<Character, Integer> wordMap = new HashMap<>();
        char[] scharArry = s.toCharArray();
        
        for (int low = 0, high = 0; high < s.length(); high++){           
            if (wordMap.containsKey(scharArry[high])){
                low = Math.max(wordMap.get(scharArry[high]) + 1, low);
            }

            wordLength = Math.max(wordLength, high - low + 1);
            wordMap.put(scharArry[high], high);
        }
        return wordLength;
    }
}
```

### Standard Solution

#### Solution #1 Sliding Window

* Using HashSet with sliding window, define start and end index

```java
public int lengthOfLongestSubstring(String s){
    int[] chars = new int[128];
    
    int left = 0;
    int right = 0;
    
    int res = 0;
    while (right < s.length()){
        char r = s.charAt(right);
        chars[r]++;
        
        while (char[r] > 1){
            char l = s.charAt(left);
            chars[l]--;
            left++;
        }
        
        res = Math.max(res, right - left + 1);
        right++;
    }
    return res;
}
```

- Time complexity : $O(2n) = O(n)$. In the worst case each character will be visited twice by i and j.
- Space complexity : $O(min(m, n))$. Same as the previous approach. We need $O(k)$ space for the sliding window, where k is the size of the `Set`. The size of the Set is upper bounded by the size of the string n and the size of the charset/alphabet m.

#### Solution #2 Sliding Window Optimized

```java
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
                // next one after the previous duplicate
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}
```

- Time complexity : $O(n)$. Index j will iterate n times.
- Space complexity (HashMap) : $O(min(m, n))$. Same as the previous approach.
- Space complexity (Table): $O(m)$). m is the size of the charset.

## Group Anagrams(Medium #49)

* `designing a key` is to `build a mapping relationship by yourself` between the original information and the actual key used by hash map.
* All values belong to the same group will be mapped in the same group.
* Values which needed to be separated into different groups will not be mapped into the same group.

**Question**: Given an array of strings `strs`, group **the anagrams** together. You can return the answer in **any order**.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

 **Example 1:**

```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Example 2:**

```
Input: strs = [""]
Output: [[""]]
```

**Example 3:**

```
Input: strs = ["a"]
Output: [["a"]]
```

 **Constraints:**

- `1 <= strs.length <= 104`
- `0 <= strs[i].length <= 100`
- `strs[i]` consists of lowercase English letters.

### My Solution

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<List<Integer>,List<String>> mapAna = new HashMap<>();
    Integer[] count = new Integer[26];
    for(String s : strs){
        Arrays.fill(count, 0);
        for (char c : s.toCharArray()){
            count[c - 'a']++;
        }
        List<Integer> keyList = Arrays.asList(count);
        if (!mapAna.containsKey(keyList)){
            List<String> wordList = new ArrayList<String>();
            wordList.add(s);
            mapAna.put(keyList, wordList);
        }
        else {
            mapAna.get(keyList).add(s);
        }	
    }
    return new ArrayList(mapAna.values());
}
```

* Array to ArrayList: `List<Integer> keyList = Arrays.asList(count);`
* Fill the arrays with 0: `Arrays.fill(count, 0)`
* Map character to number: `count[c - 'a']++`
* List and ArrayList: `List<String> wordList = new ArrayList<String>();`
* List in List: `return new ArrayList(map.values());`

### Standard Solution

#### Solution #1 Categorize by Sorted String

* Two strings are anagrams if and only if their sorted strings are equal.
* Similar to my solution but sort the arrays

```java
public List<List<String>> groupAnagrams(String[] strs){
    if (strs.length == 0) return new ArrayList();
    Map<String, List> ans = new HashMap<String, List>();
    for (String s : strs){
        char[] ca = s.toCharArray();
        Arrays.sort(ca);
        String key = String.valueOf(ca);
        if (!ans.containsKey(key)) ans.put(key, new ArrayList());
        ans.get(key).add(s);
    }
    return new ArrayList(ans.values());
}
```

- Time Complexity: $O(NK \log K)$, where N is the length of `strs`, and K is the maximum length of a string in `strs`. The outer loop has complexity $O(N)$ as we iterate through each string. Then, we sort each string in $O(K \log K)$ time.
- Space Complexity: $O(NK)$, the total information content stored in `ans`.

#### Solution #2 Categorize by Count

* Similar to my solution
* The hashable representation of our count will be a string delimited with '**#**' characters.

```java
public List<List<String>> groupAnagrams(String[] strs){
    if (strs.length == 0) return new ArrayList();
    Map<String, List> ans = new HashMap<String, List>();
    int[] count = new int[26];
    for (String s : strs){
        Arrays.fill(count, 0);
        for (char c : s.toCharArray()) count[c - 'a']++;
        
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 26; i++){
            sb.append('#');
            sb.append(count[i]);
        }
        String key = sb.toString();
        if (!ans.containsKey(key)) ans.put(key, new ArrayList());
        ans.get(key).add(s);
    }
    return new ArrayList(ans.values());
}
```

* Time Complexity: $O(NK)$, where N is the length of `strs`, and K is the maximum length of a string in `strs`. Counting each string is linear in the size of the string, and we count every string.
* Space Complexity: $O(NK)$, the total information content stored in `ans`.

## Group Shifted Strings(Medium #249)

**Question**: We can shift a string by shifting each of its letters to its successive letter.

- For example, `"abc"` can be shifted to be `"bcd"`.

We can keep shifting the string to form a sequence.

- For example, we can keep shifting `"abc"` to form the sequence: `"abc" -> "bcd" -> ... -> "xyz"`.

Given an array of strings `strings`, group all `strings[i]` that belong to the same shifting sequence. You may return the answer in **any order**.

 **Example 1:**

```
Input: strings = ["abc","bcd","acef","xyz","az","ba","a","z"]
Output: [["acef"],["a","z"],["abc","bcd","xyz"],["az","ba"]]
```

**Example 2:**

```
Input: strings = ["a"]
Output: [["a"]]
```

 **Constraints:**

- `1 <= strings.length <= 200`
- `1 <= strings[i].length <= 50`
- `strings[i]` consists of lowercase English letters.

### My Solution

```java
public List<List<String>> groupStrings(String[] strings) {
    Map<String, List> mapSeq = new HashMap<>();

    for (String s : strings){
        char[] ca = s.toCharArray();
        StringBuilder sb = new StringBuilder("");
        int length = ca.length;
        char start = ca[0];

       for (int i = 1; i < length; i++){
        int charVal = (ca[i] - start + 26) % 26 + 'a';
        sb.append(charVal);
        sb.append('#');
        start = ca[i];
       }
        if (!mapSeq.containsKey(sb.toString())){
            mapSeq.put(sb.toString(), new ArrayList());
        }
        mapSeq.get(sb.toString()).add(s);
    }
    return new ArrayList(mapSeq.values());
}
```

* Circular difference calculation
* Concept of arithmetic progression: `int charVal = (ca[i] - start + 26) % 26 + 'a';`
* Similar concept of last question

### Standard Solution

#### Solution #1 Hashing

* We need to design a hash function that ensures that the hash values for strings in the same shifting sequence will collide and thus the strings will be grouped together.

<img src="https://leetcode.com/problems/group-shifted-strings/Figures/249/249A.png" alt="fig" style="zoom: 50%;" />

* we will first convert the first character of all the strings to any character, let's say `a`. To convert the first character to `a` we would require some number of shifting say `shift` $(shift \geq 0)$

* **Algorithm**

  * Iterate over `strings`, and for each string:

    a. Find its Hash value, that is, the string starts with an `a` after some shifts. The value of `shift` is equal to the first character of the string. Then shift all the characters by the same value `shift`. **Notice that we also have to do a mod of 26 on the resulting character for the circular shift.**

    b. Map the original string to the above Hash value in the map `mapHashToList`. More specifically, add the original string to the list corresponding to its Hash value.

  * Iterate over the `mapHashToList` and store the list for every key in the map in the answer array `groups`.

```java
char shiftLetter(char letter, int shift){
    return (char) ((letter - shift + 26) % 26 + 'a');
}

// Create a hash value
String getHash(String s){
    char[] chars = s.toCharArray();
    
    // Calculate the number of shifts to make the first character to be 'a'
    int shift = chars[0];
    for (int i = 0; i < chars.length; i++){
        chars[i] = shiftLetter(chars[i], shift);
    }
    
    String hashKey = String.valueOf(chars);
    return hashKey;
}

public List<List<String>> groupStrings(String[] strings){
    Map<String, List<String>> mapHashToList = new HashMap<>();
    
    // Create a hash_value (hashKey) for each string and append the string
    // to the list of hash values i.e. mapHashToList["abc"] = ["abc", "bcd"]
    for (String str : strings){
        String hashKey = getHash(str);
        if (mapHashToList.get(hashKey) ==  null){
            mapHashToList.put(hashKey, new ArrayList<>());
        }
        mapHashToList.get(hashKey).add(str);
    }
    
    // Iterate over the map, and add the values to groups
    List<List<String>> groups = new ArrayList<>();
    for (List<String> group : mapHashToList.values()){
        groups.add(group);
    }
    return groups;
}
```

* Another way is similar to my solution, but take care of **circular shift**

```java
// Create a hash value
String getHash(String s) {
    char[] chars = s.toCharArray();
    StringBuilder hashKey = new StringBuilder();

    for (int i = 1; i < chars.length; i++) {
        hashKey.append((char) ((chars[i] - chars[i - 1] + 26) % 26 + 'a'));
    }

    return hashKey.toString();
}

public List<List<String>> groupStrings(String[] strings) {
    Map<String, List<String>> mapHashToList = new HashMap<>();

    // Create a hash_value (hashKey) for each string and append the string
    // to the list of hash values i.e. mapHashToList["cd"] = ["acf", "gil", "xzc"]
    for (String str : strings ) {
        String hashKey = getHash(str);
        if (mapHashToList.get(hashKey) == null) {
            mapHashToList.put(hashKey, new ArrayList<>());
        } 
        mapHashToList.get(hashKey).add(str);
    }

    // Iterate over the map, and add the values to groups
    List<List<String>> groups = new ArrayList<>();
    for (List<String> group : mapHashToList.values()) {
        groups.add(group);
    }

    // Return a list of all of the grouped strings
    return groups;
}

```

Let N be the length of `strings` and K be the maximum length of a string in `strings`.

- Time complexity: $O(N * K)$

  We iterate over all N `strings` and for each string, we iterate over all the characters to generate the Hash value, which takes O(K) time. To sum up, the overall time complexity is $O(N * K)$.

- Space complexity: $O(N * K)$

  We need to store all the strings plus their Hash values in `mapHashToList`. In the worst scenario, when each string in the given list belongs to a different Hash value, the maximum number of strings stored in `mapHashToList` is $2 * N$. Each string takes at most $O(K)$ space. Hence the overall space complexity is $O(N * K)$.

## Valid Anagram(Easy #242)

**Question**: Given two strings `s` and `t`, return `true` *if* `t` *is an anagram of* `s`*, and* `false` *otherwise*.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

 **Example 1:**

```
Input: s = "anagram", t = "nagaram"
Output: true
```

**Example 2:**

```
Input: s = "rat", t = "car"
Output: false
```

 **Constraints:**

- `1 <= s.length, t.length <= 5 * 104`
- `s` and `t` consist of lowercase English letters.

### My Solution

```java
 public boolean isAnagram(String s, String t) {
    Map<Character, Integer> wordMap = new HashMap<>();
    char[] sArray = s.toCharArray();
    char[] tArray = t.toCharArray();
    for (Character ch : sArray){
        wordMap.put(ch, wordMap.getOrDefault(ch, 0) + 1);
    }

    for (Character ca : tArray){
        if (!wordMap.containsKey(ca)) return false;
        wordMap.put(ca, wordMap.get(ca) - 1);
        if (wordMap.get(ca) == 0) wordMap.remove(ca);
    }

    return wordMap.isEmpty();
}
```

### Standard Solution

#### Solution #1 Sorting

* Sorting is an easiest way to solve the problem

```java
public boolean isAnagram(String s, String t){
    if (s.length() != t.length()){
        return false;
    }
    char[] str1 = s.toCharArray();
    char[] str2 = t.toCharArray();
    Arrays.sort(str1);
    Arrays.sort(str2);
    return Arrays.equals(str1, str2);
}
```

* Time complexity : $O(n \log n)$. Assume that n is the length of s, sorting costs $O(n \log n)$ and comparing two strings costs $O(n)$. Sorting time dominates and the overall time complexity is $O(n \log n)$.

#### Solution #2 Hash Table

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    int[] table = new int[26];
    for (int i = 0; i < s.length(); i++) {
        table[s.charAt(i) - 'a']++;
    }
    for (int i = 0; i < t.length(); i++) {
        table[t.charAt(i) - 'a']--;
        if (table[t.charAt(i) - 'a'] < 0) {
            return false;
        }
    }
    return true;
}
```

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    int[] counter = new int[26];
    for (int i = 0; i < s.length(); i++) {
        counter[s.charAt(i) - 'a']++;
        counter[t.charAt(i) - 'a']--;
    }
    for (int count : counter) {
        if (count != 0) {
            return false;
        }
    }
    return true;
}
```

* Time complexity : $O(n)$. Time complexity is $O(n)$ because accessing the counter table is a constant time operation.
* Space complexity : $O(1)$. Although we do use extra space, the space complexity is $O(1)$ because the table's size stays constant no matter how large n is.

## Valid Sudoku(Medium #36)

**Question**: Determine if a `9 x 9` Sudoku board is valid. Only the filled cells need to be validated **according to the following rules**:

1. Each row must contain the digits `1-9` without repetition.
2. Each column must contain the digits `1-9` without repetition.
3. Each of the nine `3 x 3` sub-boxes of the grid must contain the digits `1-9` without repetition.

**Note:**

- A Sudoku board (partially filled) could be valid but is not necessarily solvable.
- Only the filled cells need to be validated according to the mentioned rules.

 **Example 1:**

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)

```
Input: board = 
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: true
```

**Example 2:**

```
Input: board = 
[["8","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: false
Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
```

 **Constraints:**

- `board.length == 9`
- `board[i].length == 9`
- `board[i][j]` is a digit `1-9` or `'.'`.

### My Solution

```java
public boolean isValidSudoku(char[][] board){
    
    int len = board.length;
    Set<Character>[] digitRowSet = new HashSet[len];
    Set<Character>[] digitColSet = new HashSet[len];
    Set<Character>[] digitBlockSet = new HashSet[len];
    
    for (int i = 0; i < len; i++){
        digitRowSet[i] = new HashSet<Character>();
        digitColSet[i] = new HashSet<Character>();
        digitBlockSet[i] = new HashSet<Character>();
    }
    
    for (int i = 0; i < board.length; i++){
        for (int j = 0; j < board.length; j++){
            if (board[i][j] == '.'){
                continue;
            }

            if (digitRowSet[i].contains(board[i][j])) return false;
            digitRowSet[i].add(board[i][j]);
            
            if (digitColSet[j].contains(board[i][j])) return false;
            digitColSet[j].add(board[i][j]);
            
            int blockIndex = (i / 3) * 3 + j / 3;
            if (digitBlockSet[blockIndex].contains(board[i][j])) return false;
            digitBlockSet[blockIndex].add(board[i][j]);

        }
    }
    return true;
}
```

### Standard Solution

* Need to find a way to label the block sub sudoku. It is `(r/3) * 3 + (c/3)`

#### Solution #1 Hash Set

* Same as my solution
* Each row, column, and each box is a hashset. Create hashset arrays.
* Initialize hashset in each hashset arrays.

* Time complexity: $O(N^2)$ because we need to traverse every position in the board, and each of the four check steps is an $O(1)$ operation.
* Space complexity: $O(N^2)$ because in the worst-case scenario, if the board is full, we need a hash set each with size `N` to store all seen numbers for each of the `N` rows, `N` columns, and `N` boxes, respectively.

#### Solution #2 Array of Fixed Length

* Similar idea of the solution 1 but using 0 arrays to store the number of times and change it to 1 if presented
* Using 2D integer arrays

```java
public boolean isValidSudoku(char[][] board){
    int N = 9;
    
    // Use the array to record the status
    int[][] rows = new int[N][N];
    int[][] cols = new int[N][N];
    int[][] boxes = new int[N][N];
    
    for (int r = 0; r < N; r++){
        for (int c = 0; c < N; c++){
            // Check if the position is filled with number
            if (board[r][c] == '.'){
                continue;
            }
            int pos = board[r][c] - '1';
            
            // Check the row
            if (rows[r][pos] == 1){
                return false;
            }
            rows[r][pos] = 1;
            
            // Check the column
            if (cols[c][pos] == 1){
                return false;
            }
            cols[c][pos] = 1;
            
            // Check the box
            int idx = (r / 3) * 3 + c / 3;
            if (boxes[idx][pos] == 1){
                return false;
            }
            boxes[idx][pos] = 1;
        }
    }
    return true;
}
```

* Time complexity: $O(N^2)$ 
* Space complexity: $O(N^2)$

## Find Duplicate Subtrees(Medium #652)

**Question**: Given the `root` of a binary tree, return all **duplicate subtrees**.

For each kind of duplicate subtrees, you only need to return the root node of any **one** of them.

Two trees are **duplicate** if they have the **same structure** with the **same node values**.

 **Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/08/16/e1.jpg" alt="img" style="zoom:33%;" />

```
Input: root = [1,2,3,4,null,2,4,null,null,4]
Output: [[2,4],[4]]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/08/16/e2.jpg" alt="img" style="zoom:33%;" />

```
Input: root = [2,1,1]
Output: [[1]]
```

**Example 3:**

<img src="https://assets.leetcode.com/uploads/2020/08/16/e33.jpg" alt="img" style="zoom:33%;" />

```
Input: root = [2,2,2,3,null,3,null]
Output: [[2,3],[3]]
```

 **Constraints:**

- The number of the nodes in the tree will be in the range `[1, 10^4]`
- `-200 <= Node.val <= 200`

### Solution

```java
public List<TreeNode> findDuplicateSubtrees(TreeNode root){
    List<TreeNode> res = new LinkedList<>();
    postorder(root, new HashMap<>(), res);
    return res;
}

public String postorder(TreeNode cur, Map<String, Integer> map, List<TreeNode> res){
    if (cur == null) return "#";
    String serial = cur.val + ','
        + postorder(cur.left, map, res) + ',' + postorder(cur.right, map, res);
    // the way construct the string is preorder
    map.put(serial, map.getOrDefault(serial, 0) + 1);
    if (map.get(serial) == 2) res.add(cur);
    return serial;
}
```

* Time complexity is $O(n^2)$

## Jewels and Stones(Easy #771)

**Question**: You're given strings `jewels` representing the types of stones that are jewels, and `stones` representing the stones you have. Each character in `stones` is a type of stone you have. You want to know how many of the stones you have are also jewels.

Letters are case sensitive, so `"a"` is considered a different type of stone from `"A"`.

 **Example 1:**

```
Input: jewels = "aA", stones = "aAAbbbb"
Output: 3
```

**Example 2:**

```
Input: jewels = "z", stones = "ZZ"
Output: 0
```

 **Constraints:**

- `1 <= jewels.length, stones.length <= 50`
- `jewels` and `stones` consist of only English letters.
- All the characters of `jewels` are **unique**.

### My Solution

```java
public int numJewelsInStones(String jewels, String stones){
    Set<Character> jewelSet = new HashSet<>();
    char[] jewelsChArry = jewels.toCharArray();
    char[] stonesChArry = stones.toCharArray();
    int num = 0;
    
    for (Character c : jewelsChArry){
        jewelSet.add(c);
    }
    
    for (Character s : stonesChArry){
        if (jewelSet.contains(s)){
            num++;
        }
    }
    return num;
}
```

### Standard Solution

* Same as my solution
* Time Complexity: $O(jewels\text{.length} + stones\text{.length})$. 
* Space Complexity: $O(jewels\text{.length})$.

## Two Sum III - Data Structure Design(Easy #170)

**Question**: Design a data structure that accepts a stream of integers and checks if it has a pair of integers that sum up to a particular value.

Implement the `TwoSum` class:

-   `TwoSum()` Initializes the `TwoSum` object, with an empty array initially.
-   `void add(int number)` Adds `number` to the data structure.
-   `boolean find(int value)` Returns `true` if there exists any pair of numbers whose sum is equal to `value`, otherwise, it returns `false`.

 **Example 1:**

```
Input
["TwoSum", "add", "add", "add", "find", "find"]
[[], [1], [3], [5], [4], [7]]
Output
[null, null, null, null, true, false]

Explanation
TwoSum twoSum = new TwoSum();
twoSum.add(1);   // [] --> [1]
twoSum.add(3);   // [1] --> [1,3]
twoSum.add(5);   // [1,3] --> [1,3,5]
twoSum.find(4);  // 1 + 3 = 4, return true
twoSum.find(7);  // No two integers sum up to 7, return false
```

 **Constraints:**

-   `-105 <= number <= 105`
-   `-231 <= value <= 231 - 1`
-   At most `104` calls will be made to `add` and `find`.

### My Solution

```java
class TwoSum {
    private Map<Integer, Integer> sumMap; 
    public TwoSum(){
        this.sumMap = new HashMap<Integer, Integer>();
    }
    public void add(int number){
        this.sumMap.put(number, this.sumMap.getOrDefault(number, 0) + 1);
    }
    public boolean find(int value){      
        for (Integer key : this.sumMap.keySet()){
            int diff = value - key;
            if (diff != key){
                if (this.sumMap.containsKey(diff)){
                  return true;
               }
            } else {
               if (sumMap.get(key) > 1) return true;
            }            
        }
      return false;
    }
}
```

### Standard Solution

#### Solution #1 Sorted list

*   Sort the list and provide two pointers for low and high index
*   **Algorithm**:
    *   We initialize **two pointers** `low` and `high` which point to the head and the tail elements of the list respectively.
    *   With the two pointers, we start a **loop** to iterate the list. The loop would terminate either we find the two-sum solution or the two pointers meet each other.
    *   Within the loop, at each step, we would move either of the pointers, according to different conditions:
        -   If the sum of the elements pointed by the current pointers is ***less than*** the desired value, then we should try to increase the sum to meet the desired value, *i.e.* we should move the `low` pointer forwards to have a larger value.
        -   Similarly if the sum of the elements pointed by the current pointers is ***greater than*** the desired value, we then should try to reduce the sum by moving the `high` pointer towards the `low` pointer.
        -   If the sum happen to the desired value, then we could simply do an **early return** of the function.
    *   If the loop is terminated at the case where the two pointers meet each other, then we can be sure that there is no solution to the desired value.

```java
class TwoSum{
    private ArrayList<Integer> nums;
    private boolean is_sorted;
    
    /** Initialize your data structure here.**/
    public TwoSum(){
        this.nums = new ArrayList<Integer>();
        this.is_sorted = false;
    }
    
    /** Add the number to an internal data structure.**/
    public void add(int number){
        this.nums.add(number);
        this.is_sorted = false;
    }
    
    /** Find if there exists any pair of numbers which sum is equal to the value.**/
    public boolean find(int value){
        if (!this.is_sorted){
            Collections.sort(this.nums);
            this.is_sorted = true;
        }
        int low = 0, high = this.nums.size() - 1;
        while(low < high){
            int twosum = this.nums.get(low) + this.nums.get(high);
            if (twosum < value){
                low += 1;
            }
            else if (twosum > value){
                high -= 1;
            }
            else {
                return true;
            }
        }
        return false;
    }
}
```

-   Time Complexity:
    -   For the `add(number)` function: $\mathcal{O}(1)$, since we simply append the element into the list.
    -   For the `find(value)` function: $\mathcal{O}(N \cdot \log(N))$. In the worst case, we would need to sort the list first, which is of $\mathcal{O}(N \cdot \log(N))$ time complexity normally. And later, again in the worst case we need to iterate through the entire list, which is of $\mathcal{O}(N)$ time complexity. As a result, the overall time complexity of the function lies on $\mathcal{O}(N \cdot \log(N))$ of the sorting operation, which dominates over the later iteration part.
-   Space Complexity: the overall space complexity of the data structure is $\mathcal{O}(N)$ where N is the total number of *numbers* that have been added.

#### Solution #2 Hash Table

*   Similar to my solution
*   The number can be repeated, so need to clarify if `containsKey` is finding the key we locate to avoid duplicate

```java
import java.util.HashMap;

class TwoSum {
  private HashMap<Integer, Integer> num_counts;

  /** Initialize your data structure here. */
  public TwoSum() {
    this.num_counts = new HashMap<Integer, Integer>();
  }

  /** Add the number to an internal data structure.. */
  public void add(int number) {
    if (this.num_counts.containsKey(number))
      this.num_counts.replace(number, this.num_counts.get(number) + 1);
    else
      this.num_counts.put(number, 1);
  }

  /** Find if there exists any pair of numbers which sum is equal to the value. */
  public boolean find(int value) {
    for (Map.Entry<Integer, Integer> entry : this.num_counts.entrySet()) {
      int complement = value - entry.getKey();
      if (complement != entry.getKey()) {
        if (this.num_counts.containsKey(complement))
          return true;
      } else {
        if (entry.getValue() > 1)
          return true;
      }
    }
    return false;
  }
}
```

*   Time Complexity:
    -   For the `add(number)` function: $\mathcal{O}(1)$, since it takes a constant time to update an entry in hashtable.
    -   For the `find(value)` function: $\mathcal{O}(N)$, where N is the total number of **unique** *numbers*. In the worst case, we would iterate through the entire table.
*   Space Complexity: $\mathcal{O}(N)$, where N is the total number of **unique** *numbers* that we will see during the usage of the data structure.

## 4Sum II（Medium #454)

**Question**: Given four integer arrays `nums1`, `nums2`, `nums3`, and `nums4` all of length `n`, return the number of tuples `(i, j, k, l)` such that:

-   `0 <= i, j, k, l < n`
-   `nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0`

 **Example 1:**

```
Input: nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
Output: 2
Explanation:
The two tuples are:
1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
```

**Example 2:**

```
Input: nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
Output: 1
```

 **Constraints:**

-   `n == nums1.length`
-   `n == nums2.length`
-   `n == nums3.length`
-   `n == nums4.length`
-   `1 <= n <= 200`
-   `-228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228`

### My Solution

```java
public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4){
    Map<Integer, Integer> countMap = new HashMap<>();
    int count = 0;
    // a : nums1, b : nums2, c : nums3, d : nums4
    for (int a : nums1){
        for (int b : nums2){
            countMap.put(a + b, countMap.getOrDefault(0) + 1);
        }
    }  
    for (int c : nums3){
        for (int d : nums4){
            int find = -(c + d);
            if (countMap.containsKey(find)){
                count += countMap.get(find);
            }
        }
    }
    return count;
}
```

### Standard Solution

#### Solution #1 Hash Map

*   Same as my solution 
*   First, we will count sums of elements `a + b` from the first two arrays using a hashmap. Then, we will enumerate elements from the third and fourth arrays, and search for a complementary sum `a + b == -(c + d)` in the hashmap.
*   A cleaner solution version

```java
public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    int cnt = 0;
    Map<Integer, Integer> m = new HashMap<>();
    for (int a : A)
        for (int b : B)
            m.put(a + b, m.getOrDefault(a + b, 0) + 1);
    for (int c : C)
        for (int d : D)
            cnt += m.getOrDefault(-(c + d), 0);
    return cnt;
}
```

*   Time Complexity: $\mathcal{O}(n^2)$. We have 2 nested loops to count sums, and another 2 nested loops to find complements.
*   Space Complexity: $\mathcal{O}(n^2)$ for the hashmap. There could be up to $\mathcal{O}(n^2)$ distinct `a + b` keys.

#### Solution #2 kSum II (Important)

*   For the first group, we will have $\frac{k}{2}$ nested loops to count sums. Another $\frac{k}{2}$ nested loops will enumerate arrays in the second group and search for complements.

```java
public int fourSumCount(int[] A, int[] B, int[] C, int[] D){
    return kSumCount(new int[][]{A, B, C, D});
}
public int kSumCount(int[][] lists){
    Map<Integer, Integer> m = new HashMap<>();
    addToHash(lists, m, 0, 0);
    return countComplements(lists, m, lists.length / 2, 0);
}
void addToHash(int[][] lists, Map<Integer, Integer> m, int i, int sum){
    if (i == lists.length / 2){
        m.put(sum, m.getOrDefault(sum, 0) + 1);
    }
    else {
        for (int a : lists[i]){
            addToHash(lists, m, i + 1, sum + a);
        }
    }
}
int countComplements(int[][] lists, Map<Integer, Integer> m, int i, int complement){
    if (i == lists.length){
        return m.getOrDefault(complement, 0);
    }
    int cnt = 0;
    for (int a : lists[i]){
        cnt += countComplements(lists, m, i + 1, complement - a);
    }
    return cnt;
}
```

*   Time Complexity: $\mathcal{O}(n^{\frac{k}{2}})$, or $\mathcal{O}(n^2)$ for 4Sum II. We have $\frac{k}{2}$ nested loops to count sums, and another $\frac{k}{2}$ nested loops to find complements.

    If the number of arrays is odd, the time complexity will be $\mathcal{O}(n^{\frac{k+1}{2}})$. We will pass $\frac{k}{2}$  arrays to `addToHash`, and $\frac{k+1}{2}$ arrays to `kSumCount` to keep the space complexity $\mathcal{O}(n ^ {\frac{k}{2}})$.

*   Space Complexity: $\mathcal{O}(n^{\frac{k}{2}})$ for the hashmap. The space needed for the recursion will not exceed $\frac{k}{2}$

## Valid Parentheses(Easy #20)

**Question**: Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:

1.  Open brackets must be closed by the same type of brackets.
2.  Open brackets must be closed in the correct order.

 **Example 1:**

```
Input: s = "()"
Output: true
```

**Example 2:**

```
Input: s = "()[]{}"
Output: true
```

**Example 3:**

```
Input: s = "(]"
Output: false
```

 **Constraints:**

-   `1 <= s.length <= 104`
-   `s` consists of parentheses only `'()[]{}'`.

### Standard Solution

*   The solution is not a complete one since there are a few valid cases

```
(((((()))))) -- VALID

()()()()     -- VALID

(((((((()    -- INVALID

((()(())))   -- VALID
```

#### Solution #1 Using stack and hash map

*   An interesting property about a valid parenthesis expression is that a sub-expression of a valid expression should also be a valid expression.

<img src="https://leetcode.com/problems/valid-parentheses/Figures/20/20-Valid-Parentheses-Recursive-Property.png" alt="img" style="zoom: 33%;" />

*   The stack data structure can come in handy here in representing this recursive structure of the problem.
*   **Algorithm**
    *   Initialize a stack S.
    *   Process each bracket of the expression one at a time.
    *   If we encounter an opening bracket, we simply push it onto the stack. This means we will process it later, let us simply move onto the **sub-expression** ahead.
    *   If we encounter a closing bracket, then we check the element on top of the stack. If the element at the top of the stack is an opening bracket `of the same type`, then we pop it off the stack and continue processing. Else, this implies an invalid expression.
    *   In the end, if we are left with a stack still having elements, then this implies an invalid expression.

```java
class Solution{
    // Hash table to take care of the mappings
    private HashMap<Character, Character> mappings;

    // Initialize hash map with mappings, this simply akes the code easier to read
    public Solution(){
        this.mappings = new HashMap<Character, Character>();
        this.mappings.put('}','{');
        this.mappings.put(')','(');
        this.mappings.put(']','[');
    }
    
    public boolean isValid(String s){
        // Initialize a stack to be used in the algorithm.
        Stack<Character> stack = new Stack<Character>();
        
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            
            // If the current character is a closing bracket.
            if (this.mappings.containsKey(c)){
                
                // Get the top element of the stack. If the stack is empty, set a dummy value of '#'
                char topElement = stack.empty() ? '#' : stack.pop();
                
                // If the mapping for the bracket doesn't match the stack's top element, return false
                if (topElement != this.mappings.get(c)){
                    return false;
                }
                else {
                    // If it was an opening bracket, push to the stack.
                    stack.push(c);
                }
            }
        }
        // If the stack still contains elements, then it is an invalid expressions.
        return stack.isEmpty();
    }
}   

```

```java
public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('{','}');
        map.put('(',')');
        map.put('[',']');
        
        Stack<Character> parenthesis = new Stack<>();
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if (map.containsKey(c)){
                parenthesis.push(map.get(c));
            }
            else if (map.containsValue(c)){
                if (parenthesis.isEmpty() || parenthesis.pop() != c){
                    return false;
                }
            }
        }
        return parenthesis.isEmpty();
    }
```

*   Time complexity : $O(n)$ because we simply traverse the given string one character at a time and push and pop operations on a stack take $O(1)$ time.
*   Space complexity : $O(n)$ as we push all opening brackets onto the stack and in the worst case, we will end up pushing all the brackets onto the stack. e.g. `((((((((((`

## Top K Frequent Elements(Medium #347)

**Question**: Given an integer array `nums` and an integer `k`, return *the* `k` *most frequent elements*. You may return the answer in **any order**.

 **Example 1:**

```
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
```

**Example 2:**

```
Input: nums = [1], k = 1
Output: [1]
```

 **Constraints:**

-   `1 <= nums.length <= 105`
-   `k` is in the range `[1, the number of unique elements in the array]`.
-   It is **guaranteed** that the answer is **unique**.

### My Solution

```java
 public List<Map.Entry<Integer, Integer>> sortByValue(HashMap<Integer, Integer> hm){
    List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer,Integer>>(hm.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>(){
        public int compare(Map.Entry<Integer,Integer> o1,
                          Map.Entry<Integer,Integer> o2){
            return (o1.getValue()).compareTo(o2.getValue());
        }
    });
    return list;
}

public int[] topKFrequent(int[] nums, int k) {
    HashMap<Integer, Integer> countMap = new HashMap<>();
    for(int num : nums){
        countMap.put(num, countMap.getOrDefault(num, 0) + 1);
    }
    List<Map.Entry<Integer, Integer>> sortedMap = sortByValue(countMap);
    Collections.reverse(sortedMap);
    int[] intArry = new int[k];
    int i = 0;
    for (Map.Entry<Integer, Integer> countPair : sortedMap){
        Integer countVal = countPair.getKey();
        intArry[i] = Integer.valueOf(countVal);
        i++;
        if (i == k) break;
    }
    return intArry;
}
```

*   The solution contains a sorting algorithm by comparator
*   After sorting it, need to reverse the sorted list
*   Cannot just sort the map values or key set, need to store the map into a list for sorting

### Standard Solution

#### Solution #1 Heap

*   [Heap introduction](https://en.wikipedia.org/wiki/Heap_(data_structure))
*   The heap is one maximally efficient implementation of an abstract data type called a priority queue, and in fact, priority queues are often referred to as "heaps", regardless of how they may be implemented.
*   In a max heap, for any given node C, if P is a parent node of C, then the key (the value) of P is greater than or equal to the key of C. In a min heap, the key of P is less than or equal to the key of C. The node at the "top" of the heap (with no parents) is called the root node.
*   In a heap, the highest (or lowest) priority element is always stored at the root. 
*   A heap is a useful data structure when it is necessary to repeatedly remove the object with the highest (or lowest) priority, or when insertions need to be interspersed with removals of the root node.

![diff](https://leetcode.com/problems/top-k-frequent-elements/Figures/347_rewrite/summary.png)

*   **Algorithm**:
    *   The first step is to build a hash map element -> its frequency. In Java, we use the data structure HashMap. Python provides dictionary subclass Counter to initialize the hash map we need directly from the input array.
        This step takes $\mathcal{O}(N)$ time where N is a number of elements in the list.
    *   The second step is to build a heap of size k using N elements. To add the first k elements takes a linear time $\mathcal{O}(k)$ in the average case, and $\mathcal{O}(\log 1 + \log 2 + ... + \log k) = \mathcal{O}(\log k!) = \mathcal{O}(k \log k)$ in the worst case. It's equivalent to heapify implementation in Python. After the first k elements we start to push and pop at each step, N - k steps in total. The time complexity of heap push/pop is $\mathcal{O}(\log k)$ and we do it N - k times that means $\mathcal{O}((N - k)\log k)$time complexity. Adding both parts up, we get $\mathcal{O}(N \log k)$ time complexity for the second step.
    *   The third and the last step is to convert the heap into an output array. That could be done in $\mathcal{O}(k \log k)$ time.

```java
public int[] topKFrequent(int[] nums, int k){
    // O(1) time
    if (k == nums.length){
        return nums;
    }
    
    // 1. build hash map O(N) time
    Map<Integer, Integer> count = new HashMap();
    for (int n : nums){
        count.put(n, count.getOrDefault(n, 0) + 1);
    }
    
    // init heap 'the less frequent element first'
    Queue<Integer> heap = new PriorityQueue<>((n1, n2) -> count.get(n1) - count.get(n2));
    
    // 2. keep k top frequent elements in the heap
    // O(N log k) < O(N log N) time
    for (int n : count.keySet()){
        heap.add(n);
        if (heap.size() > k) heap.poll();
    }
    
    // 3. build an output array
    // O(k log k) time
    int[] top = new int[k];
    for(int i = k - 1; i >= 0; --i){
        top[i] = heap.poll();
    }
    return top;
}
```

*   Time complexity : $\mathcal{O}(N \log k)$ if $k < N$and $\mathcal{O}(N)$ in the particular case of $N = k$. That ensures time complexity to be better than $\mathcal{O}(N \log N)$.
*   Space complexity : $\mathcal{O}(N + k)$ to store the hash map with not more $N$ elements and a heap with $k$ elements.

## Unique Word Abbreviation(Medium #288)

**Question**: The **abbreviation** of a word is a concatenation of its first letter, the number of characters between the first and last letter, and its last letter. If a word has only two characters, then it is an **abbreviation** of itself.

For example:

-   `dog --> d1g` because there is one letter between the first letter `'d'` and the last letter `'g'`.
-   `internationalization --> i18n` because there are 18 letters between the first letter `'i'` and the last letter `'n'`.
-   `it --> it` because any word with only two characters is an **abbreviation** of itself.

Implement the `ValidWordAbbr` class:

-   `ValidWordAbbr(String[] dictionary)` Initializes the object with a `dictionary` of words.
-   `boolean isUnique(string word)` Returns `true` if either of the following conditions are met (otherwise returns `false`):
    -   There is no word in `dictionary` whose **abbreviation** is equal to `word`'s **abbreviation**.
    -   For any word in `dictionary` whose **abbreviation** is equal to `word`'s **abbreviation**, that word and `word` are **the same**.

 **Example 1:**

```
Input
["ValidWordAbbr", "isUnique", "isUnique", "isUnique", "isUnique", "isUnique"]
[[["deer", "door", "cake", "card"]], ["dear"], ["cart"], ["cane"], ["make"], ["cake"]]
Output
[null, false, true, false, true, true]

Explanation
ValidWordAbbr validWordAbbr = new ValidWordAbbr(["deer", "door", "cake", "card"]);
validWordAbbr.isUnique("dear"); // return false, dictionary word "deer" and word "dear" have the same abbreviation "d2r" but are not the same.
validWordAbbr.isUnique("cart"); // return true, no words in the dictionary have the abbreviation "c2t".
validWordAbbr.isUnique("cane"); // return false, dictionary word "cake" and word "cane" have the same abbreviation  "c2e" but are not the same.
validWordAbbr.isUnique("make"); // return true, no words in the dictionary have the abbreviation "m2e".
validWordAbbr.isUnique("cake"); // return true, because "cake" is already in the dictionary and no other word in the dictionary has "c2e" abbreviation.
```

 **Constraints:**

-   `1 <= dictionary.length <= 3 * 104`
-   `1 <= dictionary[i].length <= 20`
-   `dictionary[i]` consists of lowercase English letters.
-   `1 <= word.length <= 20`
-   `word` consists of lowercase English letters.
-   At most `5000` calls will be made to `isUnique`.

### My Solution

```java
class ValidWordAbbr {
    private Map<String, Set<String>> wordMap;
    private Map<String, Integer> numberMap;
    
    public String wordToAbbr(String word){
        if (word.length() <= 2){
            return word;
        }
        char[] wordArry = word.toCharArray();
        StringBuilder abr = new StringBuilder();
        char start = wordArry[0];
        char end = wordArry[wordArry.length - 1];
        int number = wordArry.length - 2;
        abr.append(start);
        abr.append(number);
        abr.append(end);
        return abr.toString();
    }
    public ValidWordAbbr(String[] dictionary){   
        wordMap = new HashMap<>();
        for (String word : dictionary){
            String abr = wordToAbbr(word);
            wordMap.putIfAbsent(abr, new HashSet<String>());
            wordMap.get(abr).add(word);
        }
    }
    
    public boolean isUnique(String word){
        String wordAbr = wordToAbbr(word);
        if (wordMap.containsKey(wordAbr)){
            if (wordMap.get(wordAbr).size() < 2){
                return wordMap.get(wordAbr).contains(word);
            }
            else return false;
        }
        return true;
    }
}
```

### Standard Solution

```java
class ValidWordAbbr {
    private Map<String, String> wordMap;
    private Map<String, Integer> numberMap;
    
    public String wordToAbbr(String word){
        if (word.length() <= 2){
            return word;
        }
        char[] wordArry = word.toCharArray();
        StringBuilder abr = new StringBuilder();
        char start = wordArry[0];
        char end = wordArry[wordArry.length - 1];
        int number = wordArry.length - 2;
        abr.append(start);
        abr.append(number);
        abr.append(end);
        return abr.toString();
    }
    public ValidWordAbbr(String[] dictionary){   
        wordMap = new HashMap<>();
        numberMap = new HashMap<>();
        for (String word : dictionary){
            if (wordMap.containsKey(word)) continue; // Important, avoid duplicate number in number map
            String abr = wordToAbbr(word);
            wordMap.put(word, abr);
            numberMap.put(abr, numberMap.getOrDefault(abr, 0) + 1);
        }
    }
    
    public boolean isUnique(String word){
        String wordAbr = wordToAbbr(word);
        if(numberMap.containsKey(wordAbr)){ 
            if(numberMap.get(wordAbr)==1 && wordMap.containsKey(word)) return true ; 
            else return false ;  
        }
        return true;
    }
}
```

## Insert Delete GetRandom O(1)(Medium #380)

**Question**: Implement the `RandomizedSet` class:

-   `RandomizedSet()` Initializes the `RandomizedSet` object.
-   `bool insert(int val)` Inserts an item `val` into the set if not present. Returns `true` if the item was not present, `false` otherwise.
-   `bool remove(int val)` Removes an item `val` from the set if present. Returns `true` if the item was present, `false` otherwise.
-   `int getRandom()` Returns a random element from the current set of elements (it's guaranteed that at least one element exists when this method is called). Each element must have the **same probability** of being returned.

You must implement the functions of the class such that each function works in **average** `O(1)` time complexity.

 **Example 1:**

```
Input
["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
[[], [1], [2], [2], [], [1], [2], []]
Output
[null, true, false, true, 2, true, false, 2]

Explanation
RandomizedSet randomizedSet = new RandomizedSet();
randomizedSet.insert(1); // Inserts 1 to the set. Returns true as 1 was inserted successfully.
randomizedSet.remove(2); // Returns false as 2 does not exist in the set.
randomizedSet.insert(2); // Inserts 2 to the set, returns true. Set now contains [1,2].
randomizedSet.getRandom(); // getRandom() should return either 1 or 2 randomly.
randomizedSet.remove(1); // Removes 1 from the set, returns true. Set now contains [2].
randomizedSet.insert(2); // 2 was already in the set, so return false.
randomizedSet.getRandom(); // Since 2 is the only number in the set, getRandom() will always return 2.
```

 **Constraints:**

-   `-231 <= val <= 231 - 1`
-   At most `2 * 105` calls will be made to `insert`, `remove`, and `getRandom`.
-   There will be **at least one** element in the data structure when `getRandom` is called.

### My Solution

```java
class RandomizedSet {
	Set<Integer> numSet;
    
    public RandomizedSet() {
        this.numSet = new HashSet<>();
    }
    
    public boolean insert(int val) {
        if (numSet.add(val)){
            return true;
        }
        return false;
    }
    
    public boolean remove(int val) {
        if (numSet.remove(val)){
            return true;
        }
        return false;
    }
    
    public int getRandom() {
        Integer[] numArry = this.numSet.toArray(new Integer[this.numSet.size()]);
        Random rndm = new Random();
        int rndmNum = rndm.nextInt(numArry.length);
        return numArry[rndmNum];
    }
}
```

*   It pass the test but would be very slow
*   How to make the process faster?

### Standard Solution(HashMap + ArrayList)

*   A combination of ArrayList and HashMap
*   Let's figure out how to implement such a structure. Starting from the Insert, we immediately have two good candidates with $\mathcal{O}(1)$ average insert time: 
    *   Hashmap (or Hashset, the implementation is very similar): Java HashMap / Python dictionary
        *   Hashmap provides Insert and Delete in average constant time, although has problems with GetRandom. 
    *   Array List: Java ArrayList / Python list
        *   Array List has indexes and could provide Insert and GetRandom in average constant time, though has problems with Delete.

**Insert**

*   Add value for the hash map, average $O(1)$ time
*   Append value to array list, average $O(1)$ as well
*   Use hash map to store the location of the array list to avoid looping

<img src="https://leetcode.com/problems/insert-delete-getrandom-o1/Figures/380/isert.png" alt="fig" style="zoom:33%;" />

```java
public boolean insert(int val){
    if (dict.containsKey(val)) return false;
    dict.put(val, list.size());//put the val and its location on the list
    list.add(list.size(), val);//put the val to the end of the list
}
```

**Delete**

*   Retrieve an index of element to delete from the hash map.
*   Move the last element to the place of the element to delete, $O(1)$ time
*   Pop the last element out, $O(1)$ time

```java
/** Removes a value from the set. Returns true if the set contained the specified element.**/
public boolean remove(int val){
    if (!dict.containsKey(val)) return false;
    
    // move the last element to the place idx of the element to delete
    int lastElement = list.get(list.size() - 1);
    int idx = dict.get(val);
    list.set(idx, lastElement);
    dict.put(lastElement, idx); // update the element location
    
    // delete the last element
    list.remove(list.size() - 1);
    dict.remove(val);
    return true;
}
```

**Get Random**

*   Get random could be implemented in $O(1)$ time with `Random` object

```java
/** Get a random element from the set.**/
public int getRandom(){
    return list.get(rand.nextInt(list.size()));
}
```

*   Time complexity. GetRandom is always $\mathcal{O}(1)$. Insert and Delete both have $\mathcal{O}(1)$ average time complexity, and $\mathcal{O}(N)$ in the worst-case scenario when the operation exceeds the capacity of currently allocated array/hashmap and invokes space reallocation.
*   Space complexity: $\mathcal{O}(N)$, to store N elements.

## Word Pattern(Easy #290)

**Question**: Given a `pattern` and a string `s`, find if `s` follows the same pattern.

Here **follow** means a full match, such that there is a bijection between a letter in `pattern` and a **non-empty** word in `s`.

 **Example 1:**

```
Input: pattern = "abba", s = "dog cat cat dog"
Output: true
```

**Example 2:**

```
Input: pattern = "abba", s = "dog cat cat fish"
Output: false
```

**Example 3:**

```
Input: pattern = "aaaa", s = "dog cat cat dog"
Output: false
```

 **Constraints:**

-   `1 <= pattern.length <= 300`
-   `pattern` contains only lower-case English letters.
-   `1 <= s.length <= 3000`
-   `s` contains only lowercase English letters and spaces `' '`.
-   `s` **does not contain** any leading or trailing spaces.
-   All the words in `s` are separated by a **single space**.

### My Solution

```java
public String[] parseString(String s){
    List<String> stringArry = new ArrayList<String>();
    int low = 0, high = 0;
    while(high < s.length()){
        if (s.charAt(high) == ' '){
            String word = s.substring(low, high);
            stringArry.add(word);
            low = high + 1;
        }
        high++;
    }
    String lastWord = s.substring(low); // add the last word
    stringArry.add(lastWord);
    return stringArry.toArray(new String[stringArry.size()]);
}

public boolean wordPattern(String pattern, String s) {
    Map<Character, String> stringMap = new HashMap<>();
    char[] patternArry = pattern.toCharArray();
    String [] stringArry = parseString(s);
    if (patternArry.length != stringArry.length) return false;
    for(int i = 0; i < patternArry.length; i++){
        if (!stringMap.containsKey(patternArry[i]) && !stringMap.containsValue(stringArry[i])){
            stringMap.put(patternArry[i], stringArry[i]);
        } else {
            if (!stringMap.containsKey(patternArry[i])) return false;
            if (!stringArry[i].equals(stringMap.get(patternArry[i]))) return false;
        }
    }
    return true;
}
```

*   First consider how to parse the strings to words
*   Then compare the patterns with hash map, but need to pay attention to avoid the situation that **different keys with same value**

### Standard Solution

#### Solution #1 Two hash maps

*   Have two hash maps, one for mapping characters to words and the other for mapping words to characters. While scanning each character-word pair,
    -   If the character is NOT in the character to word mapping, you additionally check whether that word is also in the word to character mapping.
        -   If that word is already in the word to character mapping, then you can return `False` immediately since it has been mapped with some other character before.
        -   Else, update both mappings.
    -   If the character **IS IN** in the character to word mapping, you just need to check whether the current word matches with the word which the character maps to in the character to word mapping. If not, you can return `False` immediately.

```java
public boolean wordPattern(String pattern, String s){
    HashMap<Character, String> map_char = new HashMap();
    HashMap<String, Character> map_word = new HashMap();
    String[] words = s.split(" "); // the fastest way to split the words
    
    if (words.length != pattern.length()){
        return false;
    }
    for (int i = 0; i < words.length; i++){
        char c = pattern.charAt(i);
        String w = words[i];
        if (!map_char.containsKey(c)){
            if (map_word.containsKey(w)){
                return false;
            } else {
                map_char.put(c, w);
                map_word.put(w, c);
            }
        } else {
            String mapped_word = map_char.get(c);
            if (!mapped_word.equals(w)) return false;
        }
    }
    return true;
}
```

*   Time complexity : $O(N)$ where N represents the number of words in `s` or the number of characters in `pattern`.
*   Space complexity : $O(M)$ where M represents the number of unique words in `s`. Even though we have two hash maps, the character to word hash map has space complexity of $O(1)$ since there can at most be 26 keys.

#### Solution #2 Single Index Hash Map

*   Rather than having two hash maps, we can have a single index hash map which keeps track of the first occurrences of each character in `pattern` and each word in `s`. As we go through each character-word pair, we insert unseen characters from `pattern` and unseen words from `s`.
*   Example: 
*   'a' and 'dog' -> map_index = `{'a': 0, 'dog': 0}`
    -   Index of 'a' and index of 'dog' are the same.
*   'b' and 'cat' -> map_index = `{'a': 0, 'dog': 0, 'b': 1, 'cat': 1}`
    -   Index of 'b' and index of 'cat' are the same.
*   'b' and 'fish' -> map_index = `{'a': 0, 'dog': 0, 'b': 1, 'cat': 1, 'fish': 2}`
    -   'b' is already in the mapping, no need to update.
    -   Index of 'b' and index of 'fish' are NOT the same. Returns `False`.

```java
public boolean wordPattern(String pattern, String s){
    HashMap map_index = new HashMap();
    String[] words = s.split(" ");
    
    if (words.length != pattern.length()) return false;
    
    for (Integer i = 0; i < words.length; i++){
        char c = pattern.charAt(i);
        String w = words[i];
        
        if (!map_index.containsKey(c)) map_index.put(c, i);
        if (!map_index.containsKey(w)) map_index.put(w, i);
        if (map_index.get(c) != map_index.get(w)) return false;
    }
    return true;
}
```

*   Time complexity : $O(N)$ where N represents the number of words in the `s` or the number of characters in the `pattern`.
*   Space complexity : $O(M)$ where M is the number of unique characters in `pattern` and words in `s`.

## Reformat Date(Easy #1507)

**Question**: Given a `date` string in the form `Day Month Year`, where:

-   `Day` is in the set `{"1st", "2nd", "3rd", "4th", ..., "30th", "31st"}`.
-   `Month` is in the set `{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}`.
-   `Year` is in the range `[1900, 2100]`.

Convert the date string to the format `YYYY-MM-DD`, where:

-   `YYYY` denotes the 4 digit year.
-   `MM` denotes the 2 digit month.
-   `DD` denotes the 2 digit day.

**Example 1:**

```
Input: date = "20th Oct 2052"
Output: "2052-10-20"
```

**Example 2:**

```
Input: date = "6th Jun 1933"
Output: "1933-06-06"
```

**Example 3:**

```
Input: date = "26th May 1960"
Output: "1960-05-26"
```

### My Solution

```java
class Solution {
    public String reformatDate(String date) {
        Map<String, String> month = getMonth();
        String[] dates = date.split(" ");
        // we have 3 elements
        StringBuilder formatDate = new StringBuilder();
        formatDate.append(dates[2]);
        formatDate.append("-");
        formatDate.append(month.get(dates[1]));
        formatDate.append("-");
        if (dates[0].length() > 3){
            formatDate.append(dates[0].substring(0, 2));
        }
        else {
            formatDate.append(0);
            formatDate.append(dates[0].substring(0, 1));
        }
        return formatDate.toString();
    }
    // create a dict for the months
    public Map<String, String> getMonth(){
        Map<String, String> months = new HashMap<>();
        months.put("Jan", "01");
        months.put("Feb", "02");
        months.put("Mar", "03");
        months.put("Apr", "04");
        months.put("May", "05");
        months.put("Jun", "06");
        months.put("Jul", "07");
        months.put("Aug", "08");
        months.put("Sep", "09");
        months.put("Oct", "10");
        months.put("Nov", "11");
        months.put("Dec", "12");
        return months;
    }
}
```

*   This problem does not have a certain solution, but my solution works
*   Time complexity and space complexity should be 1 since the operation number is fixed.