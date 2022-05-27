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

## Minimum Path Sum (Medium #64)

**Question**: Given a `m x n` `grid` filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

**Note:** You can only move either down or right at any point in time.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/11/05/minpath.jpg" alt="img" style="zoom:67%;" />

```
Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
```

**Example 2:**

```
Input: grid = [[1,2,3],[4,5,6]]
Output: 12
```

**Constraints:**

-   `m == grid.length`
-   `n == grid[i].length`
-   `1 <= m, n <= 200`
-   `0 <= grid[i][j] <= 100`

### My Solution

*   Since it is going to find the minimum path sum, at each cell position, we record the min path sum for the cell.
*   We fill the first row and first column with a fixed path sum
*   Then starting with the second row and column, we calculate the minimum sum path from the upper and left directions.

```java
public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    // we fill the first column with its path sum, it is fixed, determined by previous i - 1
    for (int i = 1; i < m; i++){
        grid[i][0] = grid[i][0] + grid[i - 1][0];
    }
    // similarly, the first row has fixed path sum
    for (int i = 1; i < n; i++){
        grid[0][i] = grid[0][i] + grid[0][i - 1];
    }
    // start with [1][1] position, each time we find Math.min(upper, left)
    for (int i = 1; i < m; i++){
        for (int j = 1; j < n; j++){
            grid[i][j] = Math.min(grid[i - 1][j] + grid[i][j], grid[i][j - 1] + grid[i][j]);
        }
    }
    return grid[m - 1][n - 1];
}
```

*   The time complexity is $O(m * n)$, since we traverse over the whole table
*   The space complexity is $O(1)$, it does not require any extra space for data structure, only for constants.

### Standard Solution

#### Solution #1 Dynamic Programming

*   A combination version of my solution
*   Combine the column and row fill with the nested for loop

```java
public class Solution {
    public int minPathSum(int[][] grid) {
        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = grid[0].length - 1; j >= 0; j--) {
                if(i == grid.length - 1 && j != grid[0].length - 1)
                    grid[i][j] = grid[i][j] +  grid[i][j + 1];
                else if(j == grid[0].length - 1 && i != grid.length - 1)
                    grid[i][j] = grid[i][j] + grid[i + 1][j];
                else if(j != grid[0].length - 1 && i != grid.length - 1)
                    grid[i][j] = grid[i][j] + Math.min(grid[i + 1][j],grid[i][j + 1]);
            }
        }
        return grid[0][0];
    }
}
```

-   Time complexity: $O(mn)$. We traverse the entire matrix once.
-   Space complexity: $O(1)$. No extra space is used.

## Minimum Falling Path Sum (Medium #931)

**Question**: Given an `n x n` array of integers `matrix`, return *the **minimum sum** of any **falling path** through* `matrix`.

A **falling path** starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left/right. Specifically, the next element from position `(row, col)` will be `(row + 1, col - 1)`, `(row + 1, col)`, or `(row + 1, col + 1)`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/11/03/failing1-grid.jpg" alt="img" style="zoom:67%;" />

```
Input: matrix = [[2,1,3],[6,5,4],[7,8,9]]
Output: 13
Explanation: There are two falling paths with a minimum sum as shown.
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/11/03/failing2-grid.jpg" alt="img" style="zoom:67%;" />

```
Input: matrix = [[-19,57],[-40,-5]]
Output: -59
Explanation: The falling path with a minimum sum is shown.
```

**Constraints:**

-   `n == matrix.length == matrix[i].length`
-   `1 <= n <= 100`
-   `-100 <= matrix[i][j] <= 100`

### My Solution

*   Loop through all the col first values in the first row
*   Use recursion to find the minimum next step in three ways
*   The time complexity should be $O(N * 3^{M - 1})$, where N is the row number, and N is the column number
*   The space complexity is $O(M)$, it represents the stack, the maximum stack length should be the row number

```java
int minSum;
int[][] matrix;
public int minFallingPathSum(int[][] matrix) {
    this.minSum = Integer.MAX_VALUE;
    this.matrix = matrix;
    int sum = 0;
    int m = matrix[0].length;
    // loop through all the first col number in the first row
    for (int i = 0; i < m; i++){
        sum = findPathSum(0, i);
        minSum = Math.min(minSum, sum);
    }
    return minSum;
}
// do the dp for each column in recursion
public int findPathSum(int row, int col){
    // if reach the col edge, return maximum 100 value to stop
    if (col < 0 || col >= matrix[0].length){
        return 100;
    }
    // if reach the bottom, base case
    if (row >= matrix.length){
        return 0;
    }
    int current = matrix[row][col];
    // each time we compare all the possibilities and find minimum in three ways
    return Math.min(Math.min(current + findPathSum(row + 1, col)
                    , current + findPathSum(row + 1, col - 1))
                    ,current + findPathSum(row + 1, col + 1));
}
```

```java
// second solution
public int minFallingPathSum(int[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;

    // use dp: for each cell, we record its minimum falling path sum in loop
    for (int i = 1; i < m; i++){
        for (int j = 0; j < n; j++){
            if (j == 0){
                matrix[i][j] = Math.min(matrix[i][j] + matrix[i - 1][j], 
                                        matrix[i][j] + matrix[i - 1][j + 1]);
            }
            else if (j == n - 1){
                matrix[i][j] = Math.min(matrix[i][j] + matrix[i - 1][j], 
                                        matrix[i][j] + matrix[i - 1][j - 1]);
            }
            else {
                matrix[i][j] = Math.min(Math.min(matrix[i][j] + matrix[i - 1][j], 
                                           matrix[i][j] + matrix[i - 1][j - 1]), 
                                            matrix[i][j] + matrix[i - 1][j + 1]);
            }
        }
    }
    int minSum = Integer.MAX_VALUE;
    // find the min sum at the bottom of the table
    for (int j = 0; j < n; j++){
        minSum = Math.min(minSum, matrix[m - 1][j]);
    }
    return minSum;
}
```

*   We can have a second solution which is a bottom-up DP solution
*   At each cell of the table, we compare and find the minimum path sum to this point
*   If we are on the edge columns, we only consider two paths, if not on edge columns, we consider three paths
*   In the end, we compare the last row value and find the minimum one

*   Time complexity is $O(N * M)$ and the space complexity should be $O(1)$

### Standard Solution

#### Solution #1 Dynamic Programming

*   Same as my solution but in an efficient way
*   When encountering the edge column, compare the column number with 0

```java
public int minFallingPathSum(int[][] matrix){
    for (int i = 1; i < matrix.length; i++){
        for (int j = 0; j < matrix.length; j++){
            matrix[i][j] += Math.min(matrix[i - 1][j], 
                                     Math.min(matrix[i - 1][Math.max(0, j - 1)],
                                             matrix[i - 1][Math.min(matrix.length - 1, j + 1)]));
        }
    }
    return Arrays.stream(matrix[matrix.length - 1]).min().getAsInt();
}
```

*   Time and space complexity is same as my solution

