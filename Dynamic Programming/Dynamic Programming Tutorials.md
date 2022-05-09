# Dynamic Programming Tutorials

## Definition

*   **Dynamic Programming** (DP) is a programming paradigm that can systematically and efficiently explore all possible solutions to a problem.
    *   The problem can be broken down into "overlapping subproblems" - smaller versions of the original problem that are re-used multiple times.
    *   The problem has an "optimal substructure" - an optimal solution can be formed from optimal solutions to the overlapping subproblems of the original problem.
*   **Top-down and Bottom-up**
    *   Fibonacci sequence
    *   Memoization(recursion)
*   **When to use DP**
    *   **The first characteristic** that is common in DP problems is that the problem will ask for the optimum value (maximum or minimum) of something, or the number of ways there are to do something. (Sometimes it also should use greedy)
        *   What is the minimum cost of doing...
        *   What is the maximum profit from...
        *   How many ways are there to do it...
        *   What is the longest possible...
        *   Is it possible to reach a certain point...
    *   **The second characteristic** that is common in DP problems is that future "decisions" depends on earlier decisions. This characteristic is what makes a greedy algorithm invalid for a DP problem.

*   Climb stairs example

```java
// A function that represents the answer to the problem for a given state
private int dp(int i) {
    if (i <= 2) return i; // Base cases
    return dp(i - 1) + dp(i - 2); // Recurrence relation
}

public int climbStairs(int n) {
    return dp(n);
}
```

```java
private HashMap<Integer, Integer> memo = new HashMap<>();
private int dp(int i) {
    if (i <= 2) return i;
    // Instead of just returning dp(i - 1) + dp(i - 2), calculate it once and then
    // store it inside a hashmap to refer to in the future
    if (!memo.containsKey(i)) {
        memo.put(i, dp(i - 1) + dp(i - 2));
    }

    return memo.get(i);
}

public int climbStairs(int n) {
    return dp(n);
}
```

```java
// bottom-up solution
public int climbStairs(int n) {
    if (n == 1) return 1;
    // An array that represents the answer to the problem for a given state
    int[] dp = new int[n + 1]; 
    dp[1] = 1; // Base cases
    dp[2] = 2; // Base cases
    for (int i = 3; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2]; // Recurrence relation
    }
    return dp[n];
}
```

### House Robber Example

*   **DP problem**: asking for the maximum of something, and our current decisions will affect which options are available for our future decisions.
    *   A function or array that answers the problem for a given state
    *   A recurrence relation to transition between states
    *   Base cases
*   **Top-down implementation**

```java
private HashMap<Integer, Integer> memo = new HashMap<Integer, Integer>();
private int[] nums;

private int dp(int i){
    // base cases
    if (i == 0) return nums[0];
    if (i == 1) return Math.max(nums[0], nums[1]);
    if (!memo.containsKey(i)){
        memo.put(i, Math.max(dp(i - 1), dp(i - 2) + nums[i]));// recurrence relation
    }
    return memo.get(i);
}
public int rob(int[] nums){
    this.nums = nums;
    return dp(nums.length - 1);
}
```

*   **Bottom-up Implementation**

```java
public int rob(int[] nums){
    if (nums.length == 1) return nums[0];
    
    int[] dp = new int[nums.length];
    
    // base case
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    
    for (int i = 2; i < nums.length; i++){
        dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
    }
    return dp[nums.length - 1];
}
```

## House Robber (Medium #198)

**Question**: You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and **it will automatically contact the police if two adjacent houses were broken into on the same night**.

Given an integer array `nums` representing the amount of money of each house, return *the maximum amount of money you can rob tonight **without alerting the police***.

**Example 1:**

```
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.
```

**Example 2:**

```
Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
Total amount you can rob = 2 + 9 + 1 = 12.
```

**Constraints:**

-   `1 <= nums.length <= 100`
-   `0 <= nums[i] <= 400`

### Standard Solution

*   Subproblems: We need to ensure that the optimal solution to these sub-problems can be used to form the solution to the main problem.
*   Define the first two steps solution. Then on each step, we need to consider rob this house or not.
*   Consider: if the last house value over this house plus the house before the last one

#### Solution 1 & 2

*   Same as in the house robber example, both solutions have the same space and time complexity
*   Time Complexity: $O(N)$ since we process at most N recursive calls, thanks to caching, and during each of these calls, we make an $O(1)$ the computation which is simply making two other recursive calls, finding their maximum, and populating the cache based on that.
*   Space Complexity: $O(N)$ which is occupied by the cache and also by the recursion stack.
