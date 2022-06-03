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

## Paint House (Medium #256)

**Question**: There is a row of `n` houses, where each house can be painted one of three colors: red, blue, or green. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by an `n x 3` cost matrix `costs`.

-   For example, `costs[0][0]` is the cost of painting house `0` with the color red; `costs[1][2]` is the cost of painting house 1 with color green, and so on...

Return *the minimum cost to paint all houses*. 

**Example 1:**

```
Input: costs = [[17,2,17],[16,16,5],[14,3,19]]
Output: 10
Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue.
Minimum cost: 2 + 5 + 3 = 10.
```

**Example 2:**

```
Input: costs = [[7,6,2]]
Output: 2
```

**Constraints:**

-   `costs.length == n`
-   `costs[i].length == 3`
-   `1 <= n <= 100`
-   `1 <= costs[i][j] <= 20`

### My Solution

*   Using the template of memoization + dynamic programming
*   Consider the key: how to create and get the key for hashmap
*   It is a simplified version for brute force recursion:
    *   The time complexity: $O(n)$ - basically it is $3 * 2 * n$
    *   The space complexity: $O(n)$ - the hashmap 

```java
// hashmap: house, cost - two state variables
private HashMap<String, Integer> memo = new HashMap<>();
private int[][] costs;

public int minCost(int[][] costs) {
    this.costs = costs;
    return Math.min(Math.min(paintCost(0, 0), paintCost(0, 1)), paintCost(0, 2));
}

// using recursion and dynamic programming to determine the cost
public int paintCost(int day, int color){
    if (day == costs.length){
        return 0;
    }
    if (memo.containsKey(getKey(day, color))){
        return memo.get(getKey(day, color));
    }
    // the cost at current day and color
    int totalCost = costs[day][color];
    if (color == 0){
        // red color
        totalCost += Math.min(paintCost(day + 1, 1), paintCost(day + 1, 2));
    }
    else if (color == 1){
        // blue color
        totalCost += Math.min(paintCost(day + 1, 0), paintCost(day + 1, 2));
    }
    else {
        // green color
        totalCost += Math.min(paintCost(day + 1, 0), paintCost(day + 1, 1));
    }
    memo.put(getKey(day, color), totalCost);
    return totalCost;
}

// helper method to create key for the house
public String getKey(int day, int color){
    return day + " " + color;
}
```

### Standard Solution

#### Solution #1 Memoization

*   Same as my solution

#### Solution #2 Dynamic Programming

*   At each cell we only calculate the minimum cost to this location

```java
public int minCost(int[][] costs){
    for (int n = costs.length - 2; n >= 0; n--){
        // total cost of painting nth house red
        costs[n][0] += Math.min(costs[n + 1][1], costs[n + 1][2]);
        // total cost of painting the nth house green
        costs[n][1] += Math.min(costs[n + 1][0], costs[n + 1][2]);
        // total cost of painting the nth house blue
        costs[n][2] += Math.min(costs[n + 1][0], costs[n + 1][1]);
    }
    if (costs.length == 0) return 0;
    return Math.min(Math.min(costs[0][0], costs[0][1], costs[0][2]));
}
```

*   Time Complexity: $O(n)$. Finding the minimum of two values and adding it to another value is an $O(1)$ operation. We are doing these $O(1)$ operations for $3 \cdot (n - 1)$ cells in the grid.
*   Space Complexity: $O(1)$

## Paint House II (Hard #265)

**Question**: There are a row of `n` houses, each house can be painted with one of the `k` colors. The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.

The cost of painting each house with a certain color is represented by an `n x k` cost matrix costs.

-   For example, `costs[0][0]` is the cost of painting house `0` with color `0`; `costs[1][2]` is the cost of painting house `1` with color `2`, and so on...

Return *the minimum cost to paint all houses*.

**Example 1:**

```
Input: costs = [[1,5,3],[2,9,4]]
Output: 5
Explanation:
Paint house 0 into color 0, paint house 1 into color 2. Minimum cost: 1 + 4 = 5; 
Or paint house 0 into color 2, paint house 1 into color 0. Minimum cost: 3 + 2 = 5.
```

**Example 2:**

```
Input: costs = [[1,3],[2,4]]
Output: 5
```

**Constraints:**

-   `costs.length == n`
-   `costs[i].length == k`
-   `1 <= n <= 100`
-   `2 <= k <= 20`
-   `1 <= costs[i][j] <= 20`

**Follow up:** Could you solve it in `O(nk)` runtime?

### My Solution

*   At each row start from the second row, we find the minimum value in last row for the current cell
*   Find the minimum cost at the last row of the table
*   Another way: only record the minimum cost and the second minimum cost for each row
*   The time complexity is $O(n * k ^ 2)$ where $n$ is the row number, and $k$ is the column number
*   The space complexity is $O(1)$ since we do not use extra space for storage

```java
public int minCostII(int[][] costs) {
    int n = costs.length;
    int k = costs[0].length;

    // loop through all the houses, calculate min costs sum for each house
    for (int i = 1; i < n; i++){
        for (int j = 0; j < k; j++){
            // find the optimal in the last row
            int min = Integer.MAX_VALUE;
            for (int l = 0; l < k; l++){
                if (j == l) continue;
                if (costs[i - 1][l] < min){
                    min = costs[i - 1][l];
                }
            }
            costs[i][j] += min;
        }
    }
    int minCost = Integer.MAX_VALUE;
    for (int j = 0; j < k; j++){
        if (minCost > costs[n - 1][j]){
            minCost = costs[n - 1][j];
        }
    }
    return minCost;
}
```

### Standard Soltuion

#### Solution #1 Dynamic Programming

*   Same as my solution

<img src="https://leetcode.com/problems/paint-house-ii/Figures/265/dynamic_programming_1.png" alt="Choosing the best path to house 1, red." style="zoom:50%;" />

```java
public int minCostII(int[][] costs) {
    if (costs.length == 0) return 0;
    int k = costs[0].length;
    int n = costs.length;

    for (int house = 1; house < n; house++) {
        for (int color = 0; color < k; color++) {
            int min = Integer.MAX_VALUE;
            for (int previousColor = 0; previousColor < k; previousColor++) {
                if (color == previousColor) continue;
                min = Math.min(min, costs[house - 1][previousColor]);
            }
            costs[house][color] += min;
        }
    }

    // Find the minimum in the last row.
    int min = Integer.MAX_VALUE;
    for (int c : costs[n - 1]) {
        min = Math.min(min, c);
    }
    return min;
}
```

-   Time complexity: $O(n \cdot k ^ 2)$

    We iterate over each of the $n \cdot k$ cells. For each of the cells, we're finding the minimum of the $k$ values in the row above, excluding the one that is in the same column. This operation is $O(k)$. Multiplying this out, we get $O(n \cdot k ^ 2)$.

-   Space complexity: $O(1)$ if done in-place, $O(n \cdot k)$ if input is copied.

#### Solution #2 Dynamic Programming with Optimized Time

*   Each row we record the minimum cost and the second minimum cost

<img src="https://leetcode.com/problems/paint-house-ii/Figures/265/dynamic_programming_2.png" alt="We're only ever adding 2 different numbers." style="zoom: 67%;" />

```java
public int minCostII(int[][] costs){
    if (costs.length == 0) return 0;
    int k = costs[0].length;
    int n = costs.length;
    
    for (int house = 1; house < n; house++){
        // Find the minimum and second minimum color in the previous row
        int minColor = -1; int secondMinColor = -1;
        for (int color = 0; color < k; color++){
            int cost = costs[house - 1][color];
            if (minColor == -1 || cost < costs[house - 1][minColor]){
                secondMinColor = minColor;
                minColor = color;
            }
            else if (secondMinColor == -1 || cost < costs[house - 1][secondMinColor]){
                secondMinColor = color;
            }
        }
        // and now calculate the new costs for the current row.
        for (int color = 0; color < k; color++){
            if (color == minColor){
                costs[house][color] += costs[house - 1][secondMinColor];
            }
            else {
                costs[house][color] += costs[house - 1][minColor];
            }
        }
    }
    // Find the minimum in the last row
    int min = Integer.MAX_VALUE;
    for (int c : costs[n - 1]){
        min = Math.min(min, c);
    }
    return min;
}
```

*   Time complexity: $O(n \cdot k)$.
*   Space complexity: $O(1)$

## Paint House III (Hard #1473)

**Question**: There is a row of `m` houses in a small city, each house must be painted with one of the `n` colors (labeled from `1` to `n`), some houses that have been painted last summer should not be painted again.

A neighborhood is a maximal group of continuous houses that are painted with the same color.

-   For example: `houses = [1,2,2,3,3,2,1,1]` contains `5` neighborhoods `[{1}, {2,2}, {3,3}, {2}, {1,1}]`.

Given an array `houses`, an `m x n` matrix `cost` and an integer `target` where:

-   `houses[i]`: is the color of the house `i`, and `0` if the house is not painted yet.
-   `cost[i][j]`: is the cost of paint the house `i` with the color `j + 1`.

Return *the minimum cost of painting all the remaining houses in such a way that there are exactly* `target` *neighborhoods*. If it is not possible, return `-1`.

**Example 1:**

```
Input: houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
Output: 9
Explanation: Paint houses of this way [1,2,2,1,1]
This array contains target = 3 neighborhoods, [{1}, {2,2}, {1,1}].
Cost of paint all houses (1 + 1 + 1 + 1 + 5) = 9.
```

**Example 2:**

```
Input: houses = [0,2,1,2,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
Output: 11
Explanation: Some houses are already painted, Paint the houses of this way [2,2,1,2,2]
This array contains target = 3 neighborhoods, [{2,2}, {1}, {2,2}]. 
Cost of paint the first and last house (10 + 1) = 11.
```

**Example 3:**

```
Input: houses = [3,1,2,3], cost = [[1,1,1],[1,1,1],[1,1,1],[1,1,1]], m = 4, n = 3, target = 3
Output: -1
Explanation: Houses are already painted with a total of 4 neighborhoods [{3},{1},{2},{3}] different of target = 3. 
```

**Constraints:**

-   `m == houses.length == cost.length`
-   `n == cost[i].length`
-   `1 <= m <= 100`
-   `1 <= n <= 20`
-   `1 <= target <= m`
-   `0 <= houses[i] <= n`
-   `1 <= cost[i][j] <= 104`

### Standard Solution

#### Solution #1 Top-down Dynamic Programming - Memoization

*   Use 3D array, state variables: house, neighborhood, color
*   Each time count the neighborhood number 

```java
// assgin the size as per maximum value for different params
Integer[][][] memo = new Integer[100][100][21];
// maximum cost possible plus 1
final int MAX_COST = 1000001;

public int findMinCost(int[] houses, int[][] cost, int targetCount, int currIndex, int neighborhoodCount, int prevHouseColor){
    if (currIndex == houses.length){
        // if all houses are traversed, check if the neighbor count is as expected or not
        return neighborhoodCount == targetCount ? 0 : MAX_COST;
    }
    if (neighborhoodCount > targetCount){
        // if the neighborhoods as more than the threshold, we can't have target neighborhoods
        return MAX_COST;
    }
    // we have already calcualted the answer so no need to go into recursion
    if (memo[currIndex][neighborhoodCount][prevHouseColor] != null){
        return memo[currIndex][neighborhoodCount][prevHouseColor];
    }
    int minCost = MAX_COST;
    // if the house is already painted, update the values accordingly
    if (houses[currIndex] != 0){
        int newNeighborhoodCount = neighborhoodCount + (houses[currIndex] != prevHouseColor ? 1 : 0);
        minCost = findMinCost(houses, cost, targetCount, currIndex + 1, newNeighborhoodCount, houses[currIndex]);
    }
    else {
        int totalColors = cost[0].length;

        // if the house is not painted, try every possible color and store the minimum cost
        for (int color = 1; color <= totalColors; color++){
            int newNeighborhoodCount = neighborhoodCount + (color != prevHouseColor ? 1 : 0);
            int currCost = cost[currIndex][color - 1] + findMinCost(houses, cost, targetCount, currIndex + 1, 
                                                                   newNeighborhoodCount, color);
            minCost = Math.min(minCost, currCost);
        }
    }
    // return the minimum cost and also storing it for future reference (memoization)
    return memo[currIndex][neighborhoodCount][prevHouseColor] = minCost;
}
public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
    int answer = findMinCost(houses, cost, target, 0, 0, 0);
    // return -1 if the answer is MAX_COST as it implies no answer possible
    return answer == MAX_COST ? -1 : answer;
}
```

*   M is the number of houses, N is the number of colors and T is the number of target neighborhoods.

-   Time complexity: $O(M \cdot T \cdot N^2)$

    Each state is defined by the values `currIndex`, `neighborhoodCount`, and `prevHouseColor`. Hence, there will be $M \cdot T \cdot N$ possible states, and in the worst-case scenario, we must visit most of the states to solve the original problem. Each recursive call requires $O(N)$ time as we might need to iterate over all the colors. Thus, the total time complexity is equal to $O(M \cdot T \cdot N^2)$.

-   Space complexity: $O(M \cdot T \cdot N)$

    The memoization results are stored in the table `memo` with size $M \cdot T \cdot N$. Also, stack space in the recursion is equal to the maximum number of active functions. The maximum number of active functions will be at most $M$ i.e., one function call for every house. Hence, the space complexity is $O(M \cdot T \cdot N)$.

## Best Time to Buy and Sell Stock II (Medium #122)

**Question**: You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `ith` day.

On each day, you may decide to buy and/or sell the stock. You can only hold **at most one** share of the stock at any time. However, you can buy it then immediately sell it on the **same day**.

Find and return *the **maximum** profit you can achieve*. 

**Example 1:**

```
Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit is 4 + 3 = 7.
```

**Example 2:**

```
Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Total profit is 4.
```

**Example 3:**

```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.
```

**Constraints:**

-   `1 <= prices.length <= 3 * 104`
-   `0 <= prices[i] <= 104`

### My Solution

```java
int[] prices;
int[][] memo;
public int maxProfit(int[] prices) {
    // dp: consider the state variables and actions
    // state: day, hold/unhold
    // actions: buy/sell
    this.prices = prices;
    memo = new int[prices.length + 1][2];
    return dp(0, 0);
}
// using recursion to find the best profit
public int dp(int day, int hold){
    // hold: 0 is unhold, 1 is holding a stock
    // base case: reach the end of days
    if (day == prices.length){
        return 0;
    }

    // check if the memo already has record
    if (memo[day][hold] == 0){
        int doNothing = dp(day + 1, hold);
        int doSomething;

        // actions: buy/sell, but we can do it in the same day
        if (hold == 0){
            // buy a stock
            doSomething = -prices[day] + dp(day + 1, 1);
        }
        else {
            // sell a stock
            doSomething = prices[day] + dp(day + 1, 0);
        }
        memo[day][hold] = Math.max(doSomething, doNothing);
    }
    return memo[day][hold];
}
```

```java
// second attempt, use state variables to store the value
public int maxProfit(int[] prices) {
    int cash = 0, hold = -prices[0]; // if we buy the first stock
    for (int i = 0; i < prices.length; i++){
        cash = Math.max(cash, hold + prices[i]); // make decision if we sell
        hold = Math.max(hold, cash - prices[i]); // make decision if we buy
    }
    return cash;
} 
```

### Standard Solution

#### Solution #1 Simple One Pass

*   Keep on adding the profit obtained from every consecutive transaction
*   Each time we find the profit is larger than the previous one, add on the profit

```java
public int maxProfit(int[] prices){
    int maxprofit = 0;
    for (int i = 1; i < prices.length; i++){
        if (prices[i] > prices[i - 1]){
            maxprofit += prices[i] - prices[i - 1];
        }
    }
    return maxprofit;
}
```

-   Time complexity: $O(n)$. Single pass.
-   Space complexit: $O(1)$. Constant space needed.

## Count Vowels Permutation (Hard #1220)

**Question**: Given an integer `n`, your task is to count how many strings of length `n` can be formed under the following rules:

-   Each character is a lower case vowel (`'a'`, `'e'`, `'i'`, `'o'`, `'u'`)
-   Each vowel `'a'` may only be followed by an `'e'`.
-   Each vowel `'e'` may only be followed by an `'a'` or an `'i'`.
-   Each vowel `'i'` **may not** be followed by another `'i'`.
-   Each vowel `'o'` may only be followed by an `'i'` or a `'u'`.
-   Each vowel `'u'` may only be followed by an `'a'.`

Since the answer may be too large, return it modulo `10^9 + 7.`

**Example 1:**

```
Input: n = 1
Output: 5
Explanation: All possible strings are: "a", "e", "i" , "o" and "u".
```

**Example 2:**

```
Input: n = 2
Output: 10
Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".
```

**Example 3:** 

```
Input: n = 5
Output: 68
```

**Constraints:**

-   `1 <= n <= 2 * 10^4`

### My Solution

```java
public int countVowelPermutation(int n) {
    if (n <= 0) return 0;
    // initialize the base case, when n = 1
    long aVowel = 1, eVowel = 1, iVowel = 1, oVowel = 1, uVowel = 1;
    long MOD = 1000000007;
    // given integer n, the length of vowel is n
    for (int i = 1; i < n; i++){
        // each time set the previous permutation number, add to the current number
        long aPreVowel = aVowel;
        long ePreVowel = eVowel;
        long iPreVowel = iVowel;
        long oPreVowel = oVowel;
        long uPreVowel = uVowel;

        // Sum up the vowel number to the current 
        aVowel = ePreVowel%MOD + uPreVowel%MOD + iPreVowel%MOD;
        eVowel = aPreVowel%MOD + iPreVowel%MOD;
        iVowel = ePreVowel%MOD + oPreVowel%MOD;
        oVowel = iPreVowel%MOD;
        uVowel = oPreVowel%MOD + iPreVowel%MOD;
    }
    long res = (aVowel%MOD + eVowel%MOD + iVowel%MOD + uVowel%MOD + oVowel%MOD)%MOD;
    return (int)res;
}
```

*   Time complexity should be $O(n)$ and the space complexity should be $O(1)$

### Standard Solution

#### Solution #1 Bottom-up Solution with Optimized Space

*   Similar to my solution
*   Use constant variables to cumulatively store values

```java
public int countVowelPermutation(int n) {
    long aCount = 1, eCount = 1, iCount = 1, oCount = 1, uCount = 1;
    int MOD = 1000000007;

    for (int i = 1; i < n; i++) {
        long aCountNew = (eCount + iCount + uCount) % MOD;
        long eCountNew = (aCount + iCount) % MOD;
        long iCountNew = (eCount + oCount) % MOD;
        long oCountNew = (iCount) % MOD;
        long uCountNew = (iCount + oCount) % MOD;
        aCount = aCountNew;
        eCount = eCountNew;
        iCount = iCountNew;
        oCount = oCountNew;
        uCount = uCountNew;
    }
    long result = (aCount + eCount + iCount + oCount + uCount)  % MOD;
    return (int)result;
}
```

-   Time complexity: $O(N)$ (N equals the input length `n`). This is because iterating from `1` to `n` will take $O(N)$ time. The initializations take constant time. Putting them together gives us $O(N)$ time.
-   Space complexity: $O(1)$. This is because we don't use any additional data structures to store data.