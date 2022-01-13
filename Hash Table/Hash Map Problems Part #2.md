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
    int low = 0, diff = 0, wordLength = 0;
    Set<Character> charSet = new HashSet<>();
    String longestWord = null;

    if (s.length() == 0) return 0;
    if (s.length() == 1) return 1;


    for (int high = 0; high < s.length(); high++){
        diff = high - low;
        if (!charSet.contains(s.charAt(high))){
            charSet.add(s.charAt(high));
        }
        else {
            if (diff > wordLength){
                longestWord = s.substring(low, high);
                wordLength = longestWord.length();
                low = high;
                charSet.clear();
                charSet.add(s.charAt(high));
            }
        }
    }
    return longestWord != null ? longestWord.length() : diff + 1;
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

* Time complexity : O(n)*O*(*n*). Time complexity is O(n)*O*(*n*) because accessing the counter table is a constant time operation.
* Space complexity : O(1)*O*(1). Although we do use extra space, the space complexity is O(1)*O*(1) because the table's size stays constant no matter how large n*n* is.