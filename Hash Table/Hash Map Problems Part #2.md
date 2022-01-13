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