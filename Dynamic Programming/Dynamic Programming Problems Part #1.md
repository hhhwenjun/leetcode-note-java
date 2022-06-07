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

## Maximum Length of Repeated Subarray (Medium #718)

**Question**: Given two integer arrays `nums1` and `nums2`, return *the maximum length of a subarray that appears in **both** arrays*.

**Example 1:**

```
Input: nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
Output: 3
Explanation: The repeated subarray with maximum length is [3,2,1].
```

**Example 2:**

```
Input: nums1 = [0,0,0,0,0], nums2 = [0,0,0,0,0]
Output: 5
```

**Constraints:**

-   `1 <= nums1.length, nums2.length <= 1000`
-   `0 <= nums1[i], nums2[i] <= 100`

### Standard Solution

#### Solution #1 Dynamic Programming

*   Use dimension of the two arrays to create a 2D memo array
*   If number in two arrays equals, add 1 to the responding cell in 2D memo
*   Cumulatively add 1 if subarray is equal (template of DP)
*   The hard part is to transfer the question to the memo array and its dp process

```java
public int findLength(int[] nums1, int[] nums2) {
    int ans = 0;
    int[][] memo = new int[nums1.length + 1][nums2.length + 1];
    for (int i = nums1.length - 1; i >= 0; i--){
        for (int j = nums2.length - 1; j >= 0; j--){
            if (nums1[i] == nums2[j]){
                // if they are same, add to the cell, also cumulate the results
                memo[i][j] = memo[i + 1][j + 1] + 1;
                // compare to keep finding the maximum length
                if (ans < memo[i][j]) ans = memo[i][j];
            }
        }
    }
    return ans;
}
```

-   Time Complexity: $O(M*N)$, where $M$, $N$ are the lengths of `A, B`.
-   Space Complexity: $O(M*N)$, the space used by `dp`.

## Number of Dice Rolls With Target Sum (Medium #1155)

**Question**: You have `n` dice and each die has `k` faces numbered from `1` to `k`.

Given three integers `n`, `k`, and `target`, return *the number of possible ways (out of the* `kn` *total ways)* *to roll the dice so the sum of the face-up numbers equals* `target`. Since the answer may be too large, return it **modulo** `109 + 7`.

**Example 1:**

```
Input: n = 1, k = 6, target = 3
Output: 1
Explanation: You throw one die with 6 faces.
There is only one way to get a sum of 3.
```

**Example 2:**

```
Input: n = 2, k = 6, target = 7
Output: 6
Explanation: You throw two dice, each with 6 faces.
There are 6 ways to get a sum of 7: 1+6, 2+5, 3+4, 4+3, 5+2, 6+1.
```

**Example 3:**

```
Input: n = 30, k = 30, target = 500
Output: 222616187
Explanation: The answer must be returned modulo 109 + 7. 
```

**Constraints:**

-   `1 <= n, k <= 30`
-   `1 <= target <= 1000`

### Standard Solution

#### Solution #1 Memoization

*   Use memoization to record the number of ways at each die.
*   The time complexity should be $O(n * k)$ and space complexity should be $O(n)$ for hashmap.

```java
Map<String, Integer> countMap = new HashMap<>();
public int numRollsToTarget(int n, int k, int target) {
    long MOD = 1000000007;
    // the base cases
    if (target < n || n * k < target) return 0;
    if (n == 1) return (target <= k) ? 1 : 0;

    String key = n + " " + target;
    if (countMap.containsKey(key)){
        return countMap.get(key);
    }
    // if does not contain key
    int sum = 0;
    for (int i = 1; i <= k; i++){
        sum += numRollsToTarget(n - 1, k, target - i);
        sum %= MOD;
    }
    countMap.put(key, sum);
    return countMap.get(key);
}
```

## Domino and Tromino Tiling (Medium #790)

**Question**: You have two types of tiles: a `2 x 1` domino shape and a tromino shape. You may rotate these shapes.

![img](https://assets.leetcode.com/uploads/2021/07/15/lc-domino.jpg)

Given an integer n, return *the number of ways to tile an* `2 x n` *board*. Since the answer may be very large, return it **modulo** `109 + 7`.

In a tiling, every square must be covered by a tile. Two tilings are different if and only if there are two 4-directionally adjacent cells on the board such that exactly one of the tilings has both squares occupied by a tile. 

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/07/15/lc-domino1.jpg" alt="img" style="zoom:50%;" />

```
Input: n = 3
Output: 5
Explanation: The five different ways are show above.
```

**Example 2:**

```
Input: n = 1
Output: 1
```

**Constraints:**

-   `1 <= n <= 1000`

### Standard Solution

*   The hard point is to transfer the information to recurrence relationship

*   **Fully covered board**: All tiles on board are covered by a *domino* or a *tromino*.
*   **Partially covered board**: Same as a **fully covered board**, except leave the tile in the upper-right corner (the top row of the rightmost column) uncovered. Note, a board with only the lower-right corner uncovered is also considered "partially covered." However, as we will discover soon, we do not need to keep track of which corner is uncovered because of symmetry.
*   $f(k)$: The number of ways to **fully cover a board** of width k.
*   $p(k)$: The number of ways to **partially cover a board** of width k.
*   If $k$ is greater than 2, then we will make recursive calls to $f$ and $p$ according to the transition function
    *   $f(k)=f(k−1)+f(k−2)+2∗p(k−1)$
        *   Consider the $f(k−1)$ plus 1 vertical domino
        *   Consider the $f(k−2)$ plus 2 horizontal dominos (not consider 2 vertical dominos because it would duplicate with the $f(k−1)$ Conditions)
        *   Consider the $p(k−1)$ because they are partially covered, need to add 1 tromino. But we can add it in 2 ways, so multiply it by 2.
    *   $p(k) = p(k-1) + f(k-2)$
        *   For the tromino, we consider two cases. The first one is when $p(k−1)$ we need to add 1 tromino. **Does not** multiply by 2 because it would duplicate with the next case.
        *   $f(k-2)$ considers that when we delete two space and make it partially cover, we need to add 1 tromino to reach the case.

### Standard Solution

#### Solution #1 Bottom-up

```java
public int numTilings(int n) {
    int MOD = 1000000007;
    // base cases
    if (n <= 2) return n;
    // # of ways to fully cover a board of width k
    long[] f = new long[n + 1];
    // # of ways to partially cover a board of width k
    long[] p = new long[n + 1];
    // initialize f and p with results for the base case scenarios
    f[1] = 1L;
    f[2] = 2L;
    p[2] = 1L;
    for (int k = 3; k < n + 1; k++){
        f[k] = (f[k - 1] + f[k - 2] + 2 * p[k - 1])%MOD;
        p[k] = (p[k - 1] + f[k - 2])%MOD;
    }
    return (int)f[n];
}
```

*   Time complexity: $O(N)$. Array iteration requires $N-2$ iterations where each iteration takes constant time.
*   Space complexity: $O(N)$. Two arrays of size $N+1$ are used to store the number of ways to fully and partially tile boards of various widths between 1 and N.

*   We can shorten the method and make it $O(1)$ in space complexity.

```java
public int numTilings(int n) {
    int MOD = 1_000_000_007;
    if (n <= 2) {
        return n;
    }
    long fPrevious = 1L;
    long fCurrent = 2L;
    long pCurrent = 1L;
    for (int k = 3; k < n + 1; ++k) {
        long tmp = fCurrent;
        fCurrent = (fCurrent + fPrevious + 2 * pCurrent) % MOD;
        pCurrent = (pCurrent + fPrevious) % MOD;
        fPrevious = tmp;
    }
    return (int) (fCurrent);
}
```

## Minimum Cost For Tickets (Medium #983)

**Question**: You have planned some train traveling one year in advance. The days of the year in which you will travel are given as an integer array `days`. Each day is an integer from `1` to `365`.

Train tickets are sold in **three different ways**:

-   a **1-day** pass is sold for `costs[0]` dollars,
-   a **7-day** pass is sold for `costs[1]` dollars, and
-   a **30-day** pass is sold for `costs[2]` dollars.

The passes allow that many days of consecutive travel.

-   For example, if we get a **7-day** pass on day `2`, then we can travel for `7` days: `2`, `3`, `4`, `5`, `6`, `7`, and `8`.

Return *the minimum number of dollars you need to travel every day in the given list of days*.

**Example 1:**

```
Input: days = [1,4,6,7,8,20], costs = [2,7,15]
Output: 11
Explanation: For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
In total, you spent $11 and covered all the days of your travel.
```

**Example 2:**

```
Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
Output: 17
Explanation: For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 30-day pass for costs[2] = $15 which covered days 1, 2, ..., 30.
On day 31, you bought a 1-day pass for costs[0] = $2 which covered day 31.
In total, you spent $17 and covered all the days of your travel.
```

**Constraints:**

-   `1 <= days.length <= 365`
-   `1 <= days[i] <= 365`
-   `days` is in strictly increasing order.
-   `costs.length == 3`
-   `1 <= costs[i] <= 1000`

### My Solution

*   Use the top-down recursion method to find values
*   The hardest part is to transfer the question and extract information for recurrence relationship

```java
private Set<Integer> daySet;
private int[] costs;
private Integer[] memo;

public int mincostTickets(int[] days, int[] costs) {
    this.costs = costs;
    memo = new Integer[366];
    daySet = new HashSet<>();
    // day number is non-duplicated
    for (int day : days){
        daySet.add(day);
    }
    return dp(1);
}

public int dp(int day){
    // base case of recursion
    if (day > 365) return 0;

    if (memo[day] != null){
        return memo[day];
    }
    int ans = 0;
    if (daySet.contains(day)){
        ans = Math.min(dp(day + 1) + costs[0],
                       Math.min(dp(day + 7) + costs[1],
                               dp(day + 30) + costs[2]));
    }
    else {
        ans = dp(day + 1); // no need to travel today
    }
    memo[day] = ans;
    return ans;
}
```

-   Time Complexity: $O(W)$, where $W = 365$ is the maximum numbered day in your travel plan.
-   Space Complexity: $O(W)$

### Standard Solution

#### Solution #1 Dynamic Programming (Window Variant)

*   Similar idea but using a for loop to loop through the cost array
*   Record what days we can skip and then add the cost for passes

```java
int[] days, costs;
Integer[] memo;
int[] durations = new int[]{1, 7, 30};

public int mincostTickets(int[] days, int[] costs) {
    this.days = days;
    this.costs = costs;
    memo = new Integer[days.length];
    return dp(0);
}

public int dp(int i) {
    if (i >= days.length)
        return 0;
    if (memo[i] != null)
        return memo[i];
    int ans = Integer.MAX_VALUE;
    int j = i;
    for (int k = 0; k < 3; ++k) {
        // record what days we should skip
        while (j < days.length && days[j] < days[i] + durations[k])
            j++;
        // add the cost and compare with min cost
        ans = Math.min(ans, dp(j) + costs[k]);
    }
    memo[i] = ans;
    return ans;
}
```

-   Time Complexity: $O(N)$, where N is the number of unique days in your travel plan.
-   Space Complexity: $O(N)$.

## Interleaving String (Medium #97)

**Question**: Given strings `s1`, `s2`, and `s3`, find whether `s3` is formed by an **interleaving** of `s1` and `s2`.

An **interleaving** of two strings `s` and `t` is a configuration where they are divided into **non-empty** substrings such that:

-   `s = s1 + s2 + ... + sn`
-   `t = t1 + t2 + ... + tm`
-   `|n - m| <= 1`
-   The **interleaving** is `s1 + t1 + s2 + t2 + s3 + t3 + ...` or `t1 + s1 + t2 + s2 + t3 + s3 + ...`

**Note:** `a + b` is the concatenation of strings `a` and `b`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/09/02/interleave.jpg)

```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
```

**Example 2:**

```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
```

**Example 3:**

```
Input: s1 = "", s2 = "", s3 = ""
Output: true
```

**Constraints:**

-   `0 <= s1.length, s2.length <= 100`
-   `0 <= s3.length <= 200`
-   `s1`, `s2`, and `s3` consist of lowercase English letters.

### My Solution

```java
// incorrect solution, always check s1 over s2, would be wrong in some cases
public boolean isInterleave(String s1, String s2, String s3) {
    // 1. Interleaving: loop through s3, use two pointers point to s1 and s2
    // 2. If s1/s2 match the current character, move forward the current pointer(find the char in string)
    // 3. At the end, check if we traverse all the char in s1 and s2, if true-> true, if false-> false

    int index1 = 0, index2 = 0, index3 = 0;
    while (index3 < s3.length()){
        char currentChar = s3.charAt(index3);
        if (index1 < s1.length() && s1.charAt(index1) == currentChar){
            index1++;
        }
        else if (index2 < s2.length() && s2.charAt(index2) == currentChar){
            index2++;
        }
        else {
            return false;
        }
        index3++;
    }
    return index1 == s1.length() && index2 == s2.length();
}
```

```java
// second attempt, dynamic programming top-down with recursion
private String s1, s2, s3;
private int[][] memo;

public boolean isInterleave(String s1, String s2, String s3) {
    if (s1.length() + s2.length() != s3.length()) return false;
    // dp: state variables: s1, s2
    // create a 2D memo for recording states: true - 1, false - 0
    this.s1 = s1;
    this.s2 = s2;
    this.s3 = s3;

    memo = new int[s1.length()][s2.length()];
    for (int i = 0; i < s1.length(); i++){
        Arrays.fill(memo[i], -1);
    }
    return isInterleave(0, 0, 0);
}

public boolean isInterleave(int index1, int index2, int index3){
    // base case: reach to the end of either s1 or s2
    if (index1 == s1.length()){
        return s2.substring(index2).equals(s3.substring(index3));
    }
    if (index2 == s2.length()){
        return s1.substring(index1).equals(s3.substring(index3));
    }
    // check if we have memo about the current situation
    if (memo[index1][index2] != -1){
        return memo[index1][index2] == 1 ? true : false;
    }
    boolean res = false;
    // then we need to do something to fill the memo and connect with future string
    // connect with the next char in corresponding string, return boolean to determine results
    if (s3.charAt(index3) == s1.charAt(index1) && isInterleave(index1 + 1, index2, index3 + 1) ||
       s3.charAt(index3) == s2.charAt(index2) && isInterleave(index1, index2 + 1, index3 + 1)){
        memo[index1][index2] = 1;
        res = true;
    }
    else {
        memo[index1][index2] = 0;
    }
    return res;
}
```

*   The second attempt is the memoization method with recursion
*   Recursion input is the index of the strings, either way, works return true and store the value in memo
*   Time complexity: $\mathcal{O}(m \cdot n)$, where m is the length of s1 and n is the length of $s2$. That's a consequence of the fact that each `(i, j)` combination is computed only once.
*   Space complexity: $\mathcal{O}(m \cdot n)$ to keep double array `memo`.

## Champagne Tower (Medium #799)

**Question**: We stack glasses in a pyramid, where the **first** row has `1` glass, the **second** row has `2` glasses, and so on until the 100th row. Each glass holds one cup of champagne.

Then, some champagne is poured into the first glass at the top. When the topmost glass is full, any excess liquid poured will fall equally to the glass immediately to the left and right of it. When those glasses become full, any excess champagne will fall equally to the left and right of those glasses, and so on. (A glass at the bottom row has its excess champagne fall on the floor.)

For example, after one cup of champagne is poured, the top most glass is full. After two cups of champagne are poured, the two glasses on the second row are half full. After three cups of champagne are poured, those two cups become full - there are 3 full glasses total now. After four cups of champagne are poured, the third row has the middle glass half full, and the two outside glasses are a quarter full, as pictured below.

<img src="https://s3-lc-upload.s3.amazonaws.com/uploads/2018/03/09/tower.png" alt="img" style="zoom: 33%;" />

Now after pouring some non-negative integer cups of champagne, return how full the `jth` glass in the `ith` row is (both `i` and `j` are 0-indexed.)

**Example 1:**

```
Input: poured = 1, query_row = 1, query_glass = 1
Output: 0.00000
Explanation: We poured 1 cup of champange to the top glass of the tower (which is indexed as (0, 0)). There will be no excess liquid so all the glasses under the top glass will remain empty.
```

**Example 2:**

```
Input: poured = 2, query_row = 1, query_glass = 1
Output: 0.50000
Explanation: We poured 2 cups of champange to the top glass of the tower (which is indexed as (0, 0)). There is one cup of excess liquid. The glass indexed as (1, 0) and the glass indexed as (1, 1) will share the excess liquid equally, and each will get half cup of champange.
```

**Example 3:**

```
Input: poured = 100000009, query_row = 33, query_glass = 17
Output: 1.00000
```

**Constraints:**

-   `0 <= poured <= 109`
-   `0 <= query_glass <= query_row < 100`

### Standard Solution

#### Solution #1 Simulation

*   Keep track of the total amount of champagne that flows through a glass
*   Use a 2D array for memo, bottom-up solution
*   In general, if a glass has flow-through `X`, then `Q = (X - 1.0) / 2.0` quantity of champagne will equally flow left and right.
*   A glass at `(r, c)` will have excess champagne flow towards `(r+1, c)` and `(r+1, c+1)`.

```java
public double champagneTower(int poured, int query_row, int query_glass) {
    double[][] A = new double[102][102];
    A[0][0] = (double) poured;
    for (int r = 0; r <= query_row; ++r) {
        for (int c = 0; c <= r; ++c) {
            double q = (A[r][c] - 1.0) / 2.0;
            if (q > 0) {
                A[r+1][c] += q;
                A[r+1][c+1] += q;
            }
        }
    }
    return Math.min(1, A[query_row][query_glass]);
}
```

-   Time Complexity: $O(R^2)$, where R is the number of rows. As this is fixed, we can consider this complexity to be $O(1)$.
-   Space Complexity: $O(R^2)$, or $O(1)$ by the reasoning above.

## Unique Binary Search Trees (Medium #96)

**Question**: Given an integer `n`, return *the number of structurally unique **BST'**s (binary search trees) which has exactly* `n` *nodes of unique values from* `1` *to* `n`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/01/18/uniquebstn3.jpg" alt="img" style="zoom:50%;" />

```
Input: n = 3
Output: 5
```

**Example 2:**

```
Input: n = 1
Output: 1
```

**Constraints:**

-   `1 <= n <= 19`

### Standard Solution

#### Solution #1 Dynamic Programming

*   Define two functions
    *   $G(n)$: the number of unique BST for a sequence of length `n`.
    *   $F(i, n)$: the number of unique BST, where the number `i` is served as the root of BST$ (1 \leq i \leq n$).

<img src="https://leetcode.com/problems/unique-binary-search-trees/Figures/96_BST.png" alt="img" style="zoom:50%;" />

*   For example, $F(3, 7)$, the number of unique BST tree with the number `3` as its root. To construct a unique BST out of the entire sequence `[1, 2, 3, 4, 5, 6, 7]` with `3` as the root, which is to say, we need to construct a subtree out of its left subsequence `[1, 2]` and another subtree out of the right subsequence `[4, 5, 6, 7]`, and then combine them together (*i.e.* cartesian product). Now the tricky part is that we could consider the number of unique BST out of sequence `[1,2]` as $G(2)$, and the number of unique BST out of sequence `[4, 5, 6, 7]` as $G(4)$. For $G(n)$, it does not matter the content of the sequence, but the length of the sequence. Therefore, $F(3,7) = G(2) \cdot G(4)$.

```java
public int numTrees(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = 1;
    for (int i = 2; i <= n; i++){
        for (int j = 1; j <= i; j++){
            dp[i] += dp[j - 1] * dp[i - j];
        }
    }
    return dp[n];
}
```

-   Time complexity: the main computation of the algorithm is done at the statement with `G[i]`. So the time complexity is essentially the number of iterations for the statement, which is $\sum_{i=2}^{n} i = \frac{(2+n)(n-1)}{2}$, to be exact, therefore the time complexity is $O(N^2)$
-   Space complexity: The space complexity of the above algorithm is mainly the storage to keep all the intermediate solutions, therefore $O(N)$.

## Triangle (Medium #120)

**Question**: Given a `triangle` array, return *the minimum path sum from top to bottom*.

For each step, you may move to an adjacent number of the row below. More formally, if you are on index `i` on the current row, you may move to either index `i` or index `i + 1` on the next row.

**Example 1:**

```
Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
Output: 11
Explanation: The triangle looks like:
   2
  3 4
 6 5 7
4 1 8 3
The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).
```

**Example 2:**

```
Input: triangle = [[-10]]
Output: -10
```

**Constraints:**

-   `1 <= triangle.length <= 200`
-   `triangle[0].length == 1`
-   `triangle[i].length == triangle[i - 1].length + 1`
-   `-104 <= triangle[i][j] <= 104`

### My Solution

```java
public int minimumTotal(List<List<Integer>> triangle) {
    int length = triangle.size();
    // use the triangle list itself as the memo
    for (int i = 1; i < length; i++){
        int listSize = triangle.get(i).size();
        for (int j = 0; j < listSize; j++){
            if (j - 1 < 0){
                triangle.get(i).set(j, Integer.valueOf(triangle.get(i).get(0) + 
                                                       triangle.get(i - 1).get(0)));
            }
            else if (j == listSize - 1){
                triangle.get(i).set(j, Integer.valueOf(triangle.get(i).get(j) + 
                                                       triangle.get(i - 1).get(j - 1)));
            }
            else {
                triangle.get(i).set(j, Integer.valueOf(Math.min(triangle.get(i).get(j) + 
                                                                triangle.get(i - 1).get(j),
                                                                triangle.get(i).get(j) + 
                                                                triangle.get(i - 1).get(j - 1))));
            }
        }
    }
    int res = Collections.min(triangle.get(length - 1));
    return res;
}
```

*   Since we do it in a nested for loop, so the time complexity is $O(n^2)$
*   But we do not need extra space for storage, so the space complexity is $O(1)$

### Standard Solution

#### Solution #1 DP in-place

*   Same idea as my solution

<img src="https://leetcode.com/problems/triangle/Figures/120/triangle_coordinates.png" alt="img" style="zoom:50%;" />

*   Three situations:
    *   If `row == 0`: This is the top of the triangle: it stays the same.
    *   If `col == 0`: There is only one cell above, located at `(row - 1, col)`.
    *   If `col == row`: There is only one cell above, located at `(row - 1, col - 1)`.
    *   In all other cases: There are two cells above, located at `(row - 1, col - 1`) and `(row - 1, col)` .

```java
public int minimumTotal(List<List<Integer>> triangle) {
    for (int row = 1; row < triangle.size(); row++) {
        for (int col = 0; col <= row; col++) {
            int smallestAbove = Integer.MAX_VALUE;           
            if (col > 0) {
                smallestAbove = triangle.get(row - 1).get(col - 1);
            } 
            if (col < row) {
                smallestAbove = Math.min(smallestAbove, triangle.get(row - 1).get(col));
            }
            int path = smallestAbove + triangle.get(row).get(col);
            triangle.get(row).set(col, path);
        }
    }
    return Collections.min(triangle.get(triangle.size() - 1));
}
```

-   Time Complexity: $O(n^2)$.

    A triangle with $n$ rows and $n$ columns contain $\dfrac{n (n + 1)}{2} = \dfrac{n^2 + n}{2}$ cells. Recall that in big O notation, we ignore the less significant terms. This gives us $O\bigg(\dfrac{n^2 + n}{2}\bigg) = O(n^2)$ cells. For each cell, we are performing a constant number of operations, therefore giving us a total time complexity of $O(n^2)$.

-   Space Complexity: $O(1)$.

    As we're overwriting the input, we don't need any collections to store our calculations.

#### Solution #2 DP Bottom-up, Flip Triangle Upside Down

<img src="https://leetcode.com/problems/triangle/Figures/120/upside_down_triangle_coordinates.png" alt="img" style="zoom:50%;" />

*   It turns the code a lot simpler
*    Where `(row, col)` is the current cell, the cells below are located at `(row + 1, col)` and `(row + 1, col + 1)`. At the end, the answer will be in `triangle[0][0]`.

```java
public int minimumTotal(List<List<Integer>> triangle) {        
    for (int row = triangle.size() - 2; row >= 0; row--) {
        for (int col = 0; col <= row; col++) {
            int bestBelow = Math.min(
                triangle.get(row + 1).get(col), 
                triangle.get(row + 1).get(col + 1));
            triangle.get(row).set(col, bestBelow + triangle.get(row).get(col));
        }
    }
    return triangle.get(0).get(0);
}
```

*   The in-place implementation has the same complexity analysis as Approach 1

#### Solution #3 Memoization

```java
private Map<String, Integer> memoTable;
private List<List<Integer>> triangle;

private int minPath(int row, int col) {
    String params = row + ":" + col;
    if (memoTable.containsKey(params)) {
        return memoTable.get(params);
    } 
    int path = triangle.get(row).get(col);
    if (row < triangle.size() - 1) {
        path += Math.min(minPath(row + 1, col), minPath(row + 1, col + 1));
    }
    memoTable.put(params, path);
    return path;
}

public int minimumTotal(List<List<Integer>> triangle) {
    this.triangle = triangle;
    memoTable = new HashMap<>();
    return minPath(0, 0);
}
```

*   Both time and space complexity is $O(n^2)$​. Each time a base case cell is reached, there will be a path of n cells on the run-time stack, going from the triangle tip, down to that base case cell. This means that there is $O(n)$ space on the run-time stack. Each time a subproblem is solved (a call to `minPath`), its result is stored in a memoization table. We determined above that there are $O(n^2)$ such subproblems, giving a total space complexity of $O(n^2)$ for the memoization table.

## Palindrome Partitioning (Medium #131)

**Question**: Given a string `s`, partition `s` such that every substring of the partition is a **palindrome**. Return all possible palindrome partitioning of `s`.

A **palindrome** string is a string that reads the same backward as forward.

**Example 1:**

```
Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]
```

**Example 2:**

```
Input: s = "a"
Output: [["a"]]
```

**Constraints:**

-   `1 <= s.length <= 16`
-   `s` contains only lowercase English letters.

### My Solution

*   A very good example of backtracking 
*   Need to understand more about backtracking and complexity analysis

```java
List<List<String>> res;

public List<List<String>> partition(String s) {
    // boolean memoization
    res = new ArrayList<List<String>>();
    partition(s, 0, new ArrayList<String>());
    return res;
}

// helper method to partition the words
public void partition(String s, int start, List<String> currentList){
    if (start >= s.length()){
        res.add(new ArrayList<String>(currentList));
    }
    for (int end = start; end < s.length(); end++){
        if (isPalindrome(s.substring(start, end + 1))){
            currentList.add(s.substring(start, end + 1));
            partition(s, end + 1, currentList);
            // backtrack and remove the current result
            currentList.remove(currentList.size() - 1);
        }
    }
}

// helper method, check if the word is palindrome
public boolean isPalindrome(String s){
    // two pointers
    int low = 0, high = s.length() - 1;
    while (low < high){
        if (s.charAt(low) != s.charAt(high)){
            return false;
        }
        low++;
        high--;
    }
    return true;
}
```

### Standard Solution

#### Solution #1 Backtracking

*   Backtracking is also a kind of DFS. In-Depth First Search, we recursively expand potential candidates until the defined goal is achieved.
*   **Backtracking steps**: 
    *   *Choose*: Choose the potential candidate. Here, our potential candidates are all substrings that could be generated from the given string.
    *   *Constraint*: Define a constraint that must be satisfied by the chosen candidate. In this case, the constraint is that the string must be a *palindrome*.
    *   *Goal*: We must define the goal that determines if have found the required solution and we must backtrack. Here, our goal is achieved if we have reached the end of the string

```java
public List<List<String>> partition(String s) {
    List<List<String>> result = new ArrayList<List<String>>();
    dfs(0, result, new ArrayList<String>(), s);
    return result;
}

void dfs(int start, List<List<String>> result, List<String> currentList, String s) {
    if (start >= s.length()) result.add(new ArrayList<String>(currentList));
    for (int end = start; end < s.length(); end++) {
        if (isPalindrome(s, start, end)) {
            // add current substring in the currentList
            currentList.add(s.substring(start, end + 1));
            dfs(end + 1, result, currentList, s);
            // backtrack and remove the current substring from currentList
            currentList.remove(currentList.size() - 1);
        }
    }
}

boolean isPalindrome(String s, int low, int high) {
    while (low < high) {
        if (s.charAt(low++) != s.charAt(high--)) return false;
    }
    return true;
}
```

-   Time Complexity: $\mathcal{O}(N \cdot 2^{N})$ where $N$ is the length of string $s$. This is the worst-case time complexity when all the possible substrings are palindrome.

-   Example, if = s = `aaa`, the recursive tree can be illustrated as follows:

    <img src="https://leetcode.com/problems/palindrome-partitioning/Figures/131/time_complexity.png" alt="img" style="zoom:50%;" />

    Hence, there could be $2^{N}$ possible substrings in the worst case. For each substring, it takes $\mathcal{O}(N)$ time to generate substring and determine if it is a palindrome or not. This gives us time complexity as $\mathcal{O}(N \cdot 2^{N})$

*   Space Complexity: $\mathcal{O}(N)$, where N is the length of the string $s$. This space will be used to store the recursion stack. For s = `aaa`, the maximum depth of the recursive call stack is 3 which is equivalent to $N$.

#### Solution #2 Backtracking with Dynamic Programming

*   Use a boolean 2D array to keep track of palindrome

<img src="https://leetcode.com/problems/palindrome-partitioning/Figures/131/palindrome_dp.png" alt="img" style="zoom:50%;" />

*   Let N*N* be the length of the string. To determine if a substring starting at the index $\text{start}$ and ending at the index $\text{end}$ is a palindrome or not, we use a 2 Dimensional array $\text{dp}$ of size $N \cdot N$ 
*   `dp[start][end]=true` , if the substring beginning at index $\text{start}$ and ending at index $\text{end}$ is a palindrome.

```java
public List<List<String>> partition(String s) {
    int len = s.length();
    boolean[][] dp = new boolean[len][len];
    List<List<String>> result = new ArrayList<>();
    dfs(result, s, 0, new ArrayList<>(), dp);
    return result;
}

void dfs(List<List<String>> result, String s, int start, List<String> currentList, boolean[][] dp) {
    if (start >= s.length()) result.add(new ArrayList<>(currentList));
    for (int end = start; end < s.length(); end++) {
        if (s.charAt(start) == s.charAt(end) && (end - start <= 2 || dp[start + 1][end - 1])) {
            dp[start][end] = true;
            currentList.add(s.substring(start, end + 1));
            dfs(result, s, end + 1, currentList, dp);
            currentList.remove(currentList.size() - 1);
        }
    }
}
```

-   Time Complexity: $\mathcal{O}(N \cdot 2^{N})$, where N is the length of string s. In the worst case, there could be $2^{N}$ possible substrings and it will take $\mathcal{O}(N)$ to generate each substring using `substr` as in *Approach 1*. However, we are eliminating one additional iteration to check if substring is a palindrome or not.
-   Space Complexity: $\mathcal{O}(N \cdot N)$, where N is the length of the string s. The recursive call stack would require N space as in *Approach 1*. Additionally, we also use a 2-dimensional array $\text{dp}$ of size $N \cdot N$ .This gives us total space complexity as $\mathcal{O}(N \cdot N) + \mathcal{O}(N) = \mathcal{O}(N \cdot N)$

## Maximum Product Subarray (Medium #152)

**Question**: Given an integer array `nums`, find a contiguous non-empty subarray within the array that has the largest product, and return *the product*.

The test cases are generated so that the answer will fit in a **32-bit** integer.

A **subarray** is a contiguous subsequence of the array.

**Example 1:**

```
Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.
```

**Example 2:**

```
Input: nums = [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
```

**Constraints:**

-   `1 <= nums.length <= 2 * 104`
-   `-10 <= nums[i] <= 10`
-   The product of any prefix or suffix of `nums` is **guaranteed** to fit in a **32-bit** integer.

### My Solution

```java
// Incorrect solution, can only solve "add" but not "product" situation. Basically Kadene's algorithm
// for example [2,3,-2,4,5,6,7,1,-1,0,3,2] can only find [4,5,6,7] as the best
// but it should be [2,3,-2,4,5,6,7,1,-1]
public int maxProduct(int[] nums) {
    int sum = 1, max = Integer.MIN_VALUE;
    for (int num : nums){
        sum = Math.max(sum * num, num);
        max = Math.max(max, sum);
    }
    return max;
}
```

```java
// second attempt, a brute force method
public int maxProduct(int[] nums) {
    int res = nums[0];
    for (int i = 0; i < nums.length; i++){
        // since it is product, start with 1
        int accu = 1;
        for (int j = i; j < nums.length; j++){
            // each time product the number, and compare with the max
            accu *= nums[j];
            res = Math.max(accu, res);
        }
    }
    return res;
}
```

*   With the brute force method, the time complexity is $O(N^2)$ and the space complexity is $O(1)$

### Standard Solution

#### Solution #1 Dynamic Programming

*   **Zeros** will reset your combo chain. A high score that you have achieved will be recorded in the placeholder `result`.
*   **Negative numbers** are a little bit tricky. A single negative number can flip the largest combo chain to a very small number.
*   While going through numbers in `nums`, we will have to keep track of the maximum product up to that number (we will call `max_so_far`) and minimum product up to that number (we will call `min_so_far`). The reason behind keeping track of `max_so_far` is to keep track of the accumulated product of positive numbers. The reason behind keeping track of `min_so_far` is to properly handle negative numbers.

```java
public int maxProduct(int[] nums) {
    if (nums.length == 0) return 0;

    int max_so_far = nums[0];
    int min_so_far = nums[0];
    int result = max_so_far;

    for (int i = 1; i < nums.length; i++) {
        int curr = nums[i];
        int temp_max = Math.max(curr, Math.max(max_so_far * curr, min_so_far * curr));
        min_so_far = Math.min(curr, Math.min(max_so_far * curr, min_so_far * curr));

        max_so_far = temp_max;

        result = Math.max(max_so_far, result);
    }
    return result;
}
```

-   Time complexity: $O(N)$ where N is the size of `nums`. The algorithm achieves linear runtime since we are going through `nums` only once.
-   Space complexity: $O(1)$ since no additional space is consumed rather than variables that keep track of the maximum product so far, the minimum product so far, the current variable, temp variable, and placeholder variable for the result.