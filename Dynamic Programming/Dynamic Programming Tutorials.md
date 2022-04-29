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

