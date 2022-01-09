# Dynamic Programming Problems Part #1

## Longest Palindromic Substring(Medium #5)

**Question**: Given a string `s`, return *the longest palindromic substring* in `s`.

 **Example 1:**

```
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.
```

**Example 2:**

```
Input: s = "cbbd"
Output: "bb"
```

 **Constraints:**

- `1 <= s.length <= 1000`
- `s` consist of only digits and English letters.

### My Solution

```java
public String longestPalindrome(String s) {
    
    boolean found = false;
        
    if(s.length() == 1) return s;
        
    while(!found){
        String newString = findLongest(s);
        if (checkPalindrome(newString)){
            found = true;
            return newString;
            }
        s = newString;
        }
    return null;
    }
    
private boolean checkPalindrome(String m){

    for (int k = 0; k < (m.length() + 1 )/ 2; k++){
        if (m.charAt(k) != m.charAt(m.length() - 1 - k)){
            return false;
        }
    }
    return true;
}

private String findLongest(String t){      
    for (int i = 0; i < t.length(); i++){        
        for (int j = t.length() - 1; j > i; j--){               
            if (t.charAt(i) == t.charAt(j)){                  
                return t.substring(i, j + 1);
            }
        }
    }
    return null;
}
```

* Brute-force method, not recommend, has bug

### Standard Solution

#### Solution #1 Dynamic Programming

* We can avoid unnecessary re-computation while validating palindromes.
* Time complexity : $O(n^2)$. This gives us a runtime complexity of $O(n^2)$.
* Space complexity : $O(n^2)$. It uses $O(n^2)$ space to store the table.
* $P(i,j)=(P(i+1,j−1) and S_i==S_j)$
* $P(i,i)=true$ and $P(i, i+1) = ( S_i == S_{i+1} )$

```java
public String longestPalindrome(String s){
    
    if (s == null || "".equals(s)){
        return s;
    }  
    int len = s.length(); 
    String ans = "";
    int max = 0;
    
    boolean[][] dp = new boolean[len][len];
    
    for(int j = 0; j < len; j++){
        for(int i = 0; i <= j; i++){
            boolean judge = s.charAt(i) == s.charAt(j);
            dp[i][j] = j - i > 2 ? dp[i + 1][j - 1] && judge : judge;
            if (dp[i][j] && j - i + 1 > max){//longer than current max
                max = j - i + 1;
                ans = s.substring(i, j + 1);
            }
        }
    }
    return ans;
}
```

#### Solution #2 Expand Around Center

* A palindrome can be expanded from its center
* There are only $2n - 1$ such centers

```java
public String longestPalindrome(String s){
    if (s == null || s.length() < 1) return "";
    int start = 0, end = 0;
    for (int i = 0; i < s.length(); i++){
        int len1 = expandAroundCenter(s, i, i);//for odd number
        int len2 = expandAroundCenter(s, i, i + 1);//for even number
        int len = Math.max(len1, len2);
        if (len > end - start){
            start = i - (len - 1) / 2;
            end = i + len / 2;
        }
    }
    return s.substring(start, end + 1);
}

private int expandAroundCenter(String s, int left, int right){
    int L = left, R = right;
    while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)){
        L--;
        R++;
    }
    return R - L - 1;
}
```

* Time complexity: $O(n^2)$
* Space complexity: $O(1)$