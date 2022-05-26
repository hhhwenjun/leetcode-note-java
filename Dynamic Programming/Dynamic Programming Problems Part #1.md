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

## Maximum Subarray (Easy #53)

**Question**: Given an integer array `nums`, find the contiguous subarray (containing at least one number) which has the largest sum and return *its sum*.

A **subarray** is a **contiguous** part of an array.

**Example 1:**

```
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
```

**Example 2:**

```
Input: nums = [1]
Output: 1
```

**Example 3:**

```
Input: nums = [5,4,-1,7,8]
Output: 23
```

**Constraints:**

-   `1 <= nums.length <= 105`
-   `-104 <= nums[i] <= 104`

### My Solution & Standard Solution

*   Use Kadene's algorithm: 
    *   In the loop, each time we make a decision about that should we carry over the previous value
    *   Compare if the current value is larger than the carry over value + the current value
    *   Each time compare the max value

```java
public int maxSubArray(int[] nums) {
    // kadene's algorithm
    int current = 0, max = Integer.MIN_VALUE;
    for (int num : nums){
        // should we abandon the previous values?
        current = Math.max(num, current + num);
        max = Math.max(max, current);
    }
    return max;
}
```

*   Time complexity: $O(n)$
*   Space complexity: $O(1)$

## Maximum Sum Circular Subarray (Medium #918)

**Question**: Given a **circular integer array** `nums` of length `n`, return *the maximum possible sum of a non-empty **subarray** of* `nums`.

A **circular array** means the end of the array connects to the beginning of the array. Formally, the next element of `nums[i]` is `nums[(i + 1) % n]` and the previous element of `nums[i]` is `nums[(i - 1 + n) % n]`.

A **subarray** may only include each element of the fixed buffer `nums` at most once. Formally, for a subarray `nums[i], nums[i + 1], ..., nums[j]`, there does not exist `i <= k1`, `k2 <= j` with `k1 % n == k2 % n`.

**Example 1:**

```
Input: nums = [1,-2,3,-2]
Output: 3
Explanation: Subarray [3] has maximum sum 3.
```

**Example 2:**

```
Input: nums = [5,-3,5]
Output: 10
Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10.
```

**Example 3:**

```
Input: nums = [-3,-2,-3]
Output: -2
Explanation: Subarray [-2] has maximum sum -2.
```

**Constraints:**

-   `n == nums.length`
-   `1 <= n <= 3 * 104`
-   `-3 * 104 <= nums[i] <= 3 * 104`

### My Solution

*   Use Kadene's algorithm but exceed the time limit since time complexity is $O(n^2)$
*   The space complexity is $O(1)$

```java
public int maxSubarraySumCircular(int[] nums) {
    // each location starts a loop
    int current = 0, best = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++){
        // start a curcular loop
        current = 0;
        for (int j = 0; j < nums.length; j++){
            int index = (i + j) % nums.length;
            current = Math.max(current + nums[index], nums[index]);
            best = Math.max(best, current);
        }
    }
    return best;
}
```

### Standard Solution

#### Solution #1 1-Pass Dynamic Programming

*   There are two cases:
    *   The first is that the subarray takes only a middle part, and we know how to find the max subarray sum.
    *   The second is that the subarray takes a part of the head array and a part of the tail array.

<img src="https://assets.leetcode.com/users/motorix/image_1538888300.png" alt="image" style="zoom: 67%;" />

*   We can transfer this case to the first one. The maximum result equals the total sum minus the minimum subarray sum.

*   Corner case: 
*   If all numbers are negative, `maxSum = max(A)` and `minSum = sum(A)`. In this case, `max(maxSum, total - minSum) = 0`, which means the sum of an empty subarray. According to the description, We need to return the `max(A)`, instead of sum of am empty subarray. So we return the `maxSum` to handle this corner case.

```java
public int maxSubarraySumCircular(int[] nums) {
    // each location starts a loop
    int curMin = 0, curMax = 0, total = 0, sumMax = nums[0], sumMin = nums[0];
    for (int i = 0; i < nums.length; i++){

        curMin = Math.min(curMin + nums[i], nums[i]);
        sumMin = Math.min(curMin, sumMin);

        curMax = Math.max(curMax + nums[i], nums[i]);
        sumMax = Math.max(curMax, sumMax);

        total += nums[i];
    }
    return sumMax > 0 ? Math.max(sumMax, total - sumMin) : sumMax;
}
```

*   One pass, time `O(N)`
*   No extra space, space `O(1)`