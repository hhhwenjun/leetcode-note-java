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

## Min Cost Climbing Stairs (Easy #746)

**Question**: You are given an integer array `cost` where `cost[i]` is the cost of `ith` step on a staircase. Once you pay the cost, you can either climb one or two steps.

You can either start from the step with index `0`, or the step with index `1`.

Return *the minimum cost to reach the top of the floor*.

**Example 1:**

```
Input: cost = [10,15,20]
Output: 15
Explanation: You will start at index 1.
- Pay 15 and climb two steps to reach the top.
The total cost is 15.
```

**Example 2:**

```
Input: cost = [1,100,1,1,1,100,1,1,100,1]
Output: 6
Explanation: You will start at index 0.
- Pay 1 and climb two steps to reach index 2.
- Pay 1 and climb two steps to reach index 4.
- Pay 1 and climb two steps to reach index 6.
- Pay 1 and climb one step to reach index 7.
- Pay 1 and climb two steps to reach index 9.
- Pay 1 and climb one step to reach the top.
The total cost is 6.
```

**Constraints:**

-   `2 <= cost.length <= 1000`
-   `0 <= cost[i] <= 999`

### Standard Solution

#### Solution #1 Bottom-Up Dynamic Programming

*   Also known as tabulation and done iteratively.
*   Each time calculate the one-step cost and two-step cost that required to reach the current step

```java
public int minCostClimbingStairs(int[] cost) {
    // The array's length should be 1 longer than the length of cost
    // This is because we can treat the "top floor" as a step to reach
    int[] res = new int[cost.length + 1];

    for (int i = 2; i < res.length; i++){
        // the cost to reach current step
        int oneStep = res[i - 1] + cost[i - 1];
        int twoStep = res[i - 2] + cost[i - 2];
        res[i] = Math.min(oneStep, twoStep);
    }
    return res[res.length - 1];
}
```

-   Time complexity: $O(N)$. We iterate `N - 1` time, and at each iteration, we apply an equation that requires $O(1)$ time.
-   Space complexity: $O(N)$. The array `minimumCost` is always 1 element longer than the array `cost`.

#### Solution #2 Top-Down Dynamic Programming (Recursion + Memoization)

*   Same solution as the bottom-up solution but in a top-down way
*   Using hashmap to store the relationship of array length and the cost

```java
private HashMap<Integer, Integer> memo = new HashMap<Integer, Integer>();

public int minCostClimbingStairs(int[] cost){
    return minimumCost(cost.length, cost);
}

private int minimumCost(int i, int[] cost){
    // base case, we are allowed to start at either step 0 or step 1
    if (i <= 1){
        return 0;
    }
    // check if we have already calculated minimumCost(i)
    if (memo.containsKey(i)){
        return memo.get(i);
    }
    // if not, cache the result in our hashmap and return 
    int downOne = cost[i - 1] + minimumCost(i - 1, cost);
    int downTwo = cost[i - 2] + minimumCost(i - 2, cost);
    memo.put(i, Math.min(downOne, downTwo));
    return memo.get(i);
}
```

-   Time complexity: $O(N)$

    `minimumCost` gets called with each index from `0` to `N`. Because of our memoization, each call will only take $O(1)$ time.

-   Space complexity: $O(N)$

    The extra space used by this algorithm is the recursion call stack. For example, `minimumCost(10000)` will call `minimumCost(9999)`, which calls `minimumCost(9998)` etc., all the way down until the base cases at `minimumCost(0)` and `minimumCost(1)`. In addition, our hash map `memo` will be of size `N` at the end, since we populate it with every index from `0` to `N`.

#### Solution #3 Constant Space

*   Same as the first solution, but not using an array to hold the cost
*   Use two constants to hold the one-step cost and the two-step cost

```java
public int minCostClimbingStairs(int[] cost){
    int downOne = 0;
    int downTwo = 0;
    for (int i = 2; i < cost.length + 1; i++){
        int temp = downOne;
        // downTwo will be whatever downOne was prior to the update, 
        // so let's use a temporary variable to help with the update.
        downOne = Math.min(downOne + cost[i - 1], downTwo + cost[i - 2]);
        downTwo = temp;
    }
    return downOne;
}
```

-   Time complexity: $O(N)$, We only iterate `N - 1` time, and at each iteration, we apply an equation that uses $O(1)$ time.
-   Space complexity: $O(1)$, The only extra space we use is 2 variables, which are independent of input size.

## N-th Tribonacci Number (Easy #1137)

**Question**: The Tribonacci sequence Tn is defined as follows: 

T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.

Given `n`, return the value of Tn.

**Example 1:**

```
Input: n = 4
Output: 4
Explanation:
T_3 = 0 + 1 + 1 = 2
T_4 = 1 + 1 + 2 = 4
```

**Example 2:**

```
Input: n = 25
Output: 1389537
```

**Constraints:**

-   `0 <= n <= 37`
-   The answer is guaranteed to fit within a 32-bit integer, ie. `answer <= 2^31 - 1`.

### My Solution

```java
public int tribonacci(int n) {
    // Tn = Tn-1 + Tn-2 + Tn-3
    int[] res = new int[n + 1];
    if (n == 0){
        return 0;
    }
    if (n == 1 || n == 2){
        return 1;
    }
    res[0] = 0;
    res[1] = 1;
    res[2] = 1;
    for (int i = 3; i < res.length; i++){
        res[i] = res[i - 1] + res[i - 2] + res[i - 3];
    }
    return res[res.length - 1];
}
```

### Standard Solution

#### Solution #1 Space Optimization - Dynamic Programming

*   Same idea as my solution, but not using an array

```java
public int tribonacci(int n){
    if (n < 3) return n == 0 ? 0 : 1;
    int tmp, x = 0, y = 1, z = 1; // represent the first three elements
    for (int i = 3; i <= n; i++){
        tmp = x + y + z;
        x = y;
        y = z;
        z = tmp;
    }
    return z;
}
```

-   Time complexity: $\mathcal{O}(N)$
-   Space complexity: $\mathcal{O}(1)$
