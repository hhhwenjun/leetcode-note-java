# Dynamic Programming Tutorials

## Definition

*   **Dynamic Programming** (DP) is a programming paradigm that can systematically and efficiently explore all possible solutions to a problem.
    *   The problem can be broken down into "overlapping subproblems" - smaller versions of the original problem that are re-used multiple times.
    *   The problem has an "optimal substructure" - an optimal solution can be formed from optimal solutions to the overlapping subproblems of the original problem.
*   **Top-down and Bottom-up**
    *   Fibonacci sequence
    *   Memoization(recursion)
    *   **Memoization** is where we add caching to a function (that has no side effects). In dynamic programming, it is typically used on **recursive** functions for a **top-down** solution that starts with the initial problem and then recursively calls itself to solve smaller problems.
    *   **Tabulation** uses a table to keep track of subproblem results and works in a **bottom-up** manner: solving the smallest subproblems before the large ones, in an **iterative** manner. Often, people use the words "tabulation" and "dynamic programming" interchangeably.
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

### Top-down to Bottom-up

*   A top-down algorithm is easier to implement than bottom-up
*   But bottom-up usually is more efficient than top-down in terms of runtime
*   **Conversion**:
    *   Start with a completed top-down implementation.
    *   Initialize an array $\text{dp}$ that is sized according to your state variables. 
    *   Set your base cases, the same as the ones you are using in your top-down function.
    *   Write a for-loop(s) that iterate over your state variables. If you have multiple state variables, you will need nested for-loops. These loops should **start iterating from the base cases**.
    *   Each iteration of the inner-most loop represents a given state and is equivalent to a function call to the same state in top-down.
    *   $\text{dp}$ is now an array populated with the answer to the original problem for all possible states.

### Time and Space Complexity

*   DP is that we never repeat calculations, whether, by tabulation or memoization, we only compute a state once.
*   The time complexity of a DP algorithm is directly tied to the number of possible states.
*   If computing each state requires F time, and there are n possible states, then the time complexity of a DP algorithm is $O(n \cdot F)$. 

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

## Delete and Earn (Medium #740)

**Question**: You are given an integer array `nums`. You want to maximize the number of points you get by performing the following operation any number of times:

-   Pick any `nums[i]` and delete it to earn `nums[i]` points. Afterwards, you must delete **every** element equal to `nums[i] - 1` and **every** element equal to `nums[i] + 1`.

Return *the **maximum number of points** you can earn by applying the above operation some number of times*.

**Example 1:**

```
Input: nums = [3,4,2]
Output: 6
Explanation: You can perform the following operations:
- Delete 4 to earn 4 points. Consequently, 3 is also deleted. nums = [2].
- Delete 2 to earn 2 points. nums = [].
You earn a total of 6 points.
```

**Example 2:**

```
Input: nums = [2,2,3,3,3,4]
Output: 9
Explanation: You can perform the following operations:
- Delete a 3 to earn 3 points. All 2's and 4's are also deleted. nums = [3,3].
- Delete a 3 again to earn 3 points. nums = [3].
- Delete a 3 once more to earn 3 points. nums = [].
You earn a total of 9 points.
```

**Constraints:**

-   `1 <= nums.length <= 2 * 104`
-   `1 <= nums[i] <= 104`

### My Solution

```java
private Map<Integer, Integer> points;
private Map<Integer, Integer> sum;

public int deleteAndEarn(int[] nums) {
    points = new HashMap<>();
    sum = new HashMap<>();
    Arrays.sort(nums);
    for (int num : nums){
        points.put(num, points.getOrDefault(num, 0) + num);
    }
    return findSum(nums[nums.length - 1]);
}

public int findSum(int num){
    if (num == 0){
        return 0;
    }
    // prevent stack over flow to negative num
    if (num == 1){
        return points.getOrDefault(1, 0);
    }
    if (sum.containsKey(num)){
        return sum.get(num);
    }
    int current = points.getOrDefault(num, 0);
    sum.put(num, Math.max(findSum(num - 1), findSum(num - 2) + current));
    return sum.get(num);
}
```

### Standard Solution

*   A slight modification of house robber problem

#### Solution #1 Top-down Dynamic Programming

*   Similar to my solution
*   Start with the largest number, each time compare the previous ones and add the current points
*   Use a hashmap to record the total points you can get from each number
*   Use recursion to continuously record the maximum points you can get if you choose to drop the current number
*   Fit dynamic programming because of the sub-optimal solutions for each step

```java
private HashMap<Integer, Integer> points = new HashMap<>();
private HashMap<Integer, Integer> cache = new HashMap<>();

private int maxPoints(int num) {
    // Check for base cases
    if (num == 0) {
        return 0;
    }

    if (num == 1) {
        return points.getOrDefault(1, 0);
    }

    if (cache.containsKey(num)) {
        return cache.get(num);
    }

    // Apply recurrence relation
    int gain = points.getOrDefault(num, 0);
    cache.put(num, Math.max(maxPoints(num - 1), maxPoints(num - 2) + gain));
    return cache.get(num);
}

public int deleteAndEarn(int[] nums) {
    int maxNumber = 0;

    // Precompute how many points we gain from taking an element
    for (int num : nums) {
        points.put(num, points.getOrDefault(num, 0) + num);
        maxNumber = Math.max(maxNumber, num);
    }

    return maxPoints(maxNumber);
}
```

-   Time complexity: $O(N + k)$

    To populate `points`, we need to iterate through `nums` once, which costs $O(N)$ time. Then, we call `maxPoints(maxNumber)`. This call will repeatedly call `maxPoints` until we get down to our base cases. Because of `cache`, already solved sub-problems will only cost $O(1)$ time. Since `maxNumber = k`, we will solve `k` unique sub-problems so, this recursion will cost $O(k)$ time. Our final time complexity is $O(N + k)$

-   Space complexity: $O(N + k)$

    The extra space we use is the hash table `points`, the recursion call stack needed to find `maxPoints(maxNumber)`, and the hash table `cache`.

    The size of `points` is equal to the number of unique elements in `nums`. In the worst case, where every element in `nums` is unique, this will take $O(N)$ space. The recursion call stack will also grow up to size `k`, since we start our recursion at `maxNumber`, and we don't start returning values until our base cases at `0` and `1`. Lastly, `cache` will store the answer for all states, from `2` to `maxNumber`, which means it also grows up to `k` size. Our final space complexity is $O(N + 2 \cdot k) = O(N + k)$

#### Solution #2 Bottom-up Dynamic Programming

*   A similar solution to the above problem
*   Use another array iteratively to update the max points and take the place of recursion

```java
public int deleteAndEarn(int[] nums){
	HashMap<Integer, Integer> points = new HashMap<>();
    int maxNumber = 0;
    
    // precompute how many points we gain from taking an element
    for (int num : nums){
        points.put(num, points.getOrDefault(num, 0) + num);
        maxNumber = Math.max(maxNumber, num);
    }
    // declare our array along with base cases
    int[] maxPoints = new int[maxNumber + 1];
    maxPoints[1] = points.getOrDefault(1, 0);
    // from lowest number to the highest number
    for (int num = 2; num < maxPoints.length; num++){
        // apply recurrence relation
        int gain = points.getOrDefault(num, 0);
        maxPoints[num] = Math.max(maxPoints[num - 1], maxPoints[num - 2] + gain);
    }
    return maxPoints[maxNumber];
}
```

*   Time complexity: $O(N + k)$
*   Space complexity: $O(N + k)$

## Maximum Score from Performing Multiplication Operations (Medium 1770)

**Question**: You are given two integer arrays `nums` and `multipliers` of size `n` and `m` respectively, where `n >= m`. The arrays are **1-indexed**.

You begin with a score of `0`. You want to perform **exactly** `m` operations. On the `ith` operation **(1-indexed)**, you will:

-   Choose one integer `x` from **either the start or the end** of the array `nums`.
-   Add `multipliers[i] * x` to your score.
-   Remove `x` from the array `nums`.

Return *the **maximum** score after performing* `m` *operations.*

**Example 1:**

```
Input: nums = [1,2,3], multipliers = [3,2,1]
Output: 14
Explanation: An optimal solution is as follows:
- Choose from the end, [1,2,3], adding 3 * 3 = 9 to the score.
- Choose from the end, [1,2], adding 2 * 2 = 4 to the score.
- Choose from the end, [1], adding 1 * 1 = 1 to the score.
The total score is 9 + 4 + 1 = 14.
```

**Example 2:**

```
Input: nums = [-5,-3,-3,-2,7,1], multipliers = [-10,-5,3,4,6]
Output: 102
Explanation: An optimal solution is as follows:
- Choose from the start, [-5,-3,-3,-2,7,1], adding -5 * -10 = 50 to the score.
- Choose from the start, [-3,-3,-2,7,1], adding -3 * -5 = 15 to the score.
- Choose from the start, [-3,-2,7,1], adding -3 * 3 = -9 to the score.
- Choose from the end, [-2,7,1], adding 1 * 4 = 4 to the score.
- Choose from the end, [-2,7], adding 7 * 6 = 42 to the score. 
The total score is 50 + 15 - 9 + 4 + 42 = 102.
```

**Constraints:**

-   `n == nums.length`
-   `m == multipliers.length`
-   `1 <= m <= 103`
-   `m <= n <= 105`
-   `-1000 <= nums[i], multipliers[i] <= 1000`

### Standard Solution

*   Need to break it down into sub-problems, each time we compare the current left value and right value, and the corresponding next steps of the choices.
*   We can create a 2D array to record the cumulative sum of the values
*   We can find that though we need to compare the left and right multiplication values, we only need to consider one order of the multipliers
*   Recursion: top-down / Iteration: bottom-up

#### Solution #1 Top-down DP

*   Create a 2D array to record the cumulative sum of values
*   Use left and right to indicate the location or array, use $i$ to point to the multipliers

```java
private int n, m;
private int[] nums, multipliers;
private int[][] memo;

public int dp(int i, int left){
    // need to have left, right pointer indicate left/right end
    if (i == m){
        // reach the end of multipliers
        return 0;
    }
    int mult = multipliers[i];
    int right = n - 1 - (i - left);
    // not complete the calculation on this position
    if (memo[i][left] == 0){
        memo[i][left] = Math.max(mult * nums[left] + dp(i + 1, left + 1),
                                mult * nums[right] + dp(i + 1, left));
    }
    return memo[i][left];
}

public int maximumScore(int[] nums, int[] multipliers) {
    n = nums.length;
    m = multipliers.length;
    this.nums = nums;
    this.multipliers = multipliers;
    this.memo = new int[n][m];
    // start from [0][0] in the matrix
    return dp(0, 0);
}
```

*   Time and space complexity can be $O(m^2)$
*   Can be quite slow

#### Solution #2 Bottom-up Solution

*   Similar idea with solution 1 but shorter in length

```java
public int maximumScore(int[] nums, int[] multipliers){
    int n = nums.length;
    int m = multipliers.length;
    int[][] dp = new int[m + 1][m + 1];
    
    for (int i = m - 1; i >= 0; i--){
        for (int left = i; left >= 0; left--){
            int mult = multipliers[i];
            int right = n - 1 - (i - left);
            dp[i][left] = Math.max(mult * nums[left] + dp[i + 1][left + 1],
                                  mult * nums[right] + dp[i + 1][left]);
        }
    }
    return dp[0][0];
}
```

*   Time and space complexity can be $O(m^2)$

## Word Break (Medium #139)

**Question**: Given a string `s` and a dictionary of strings `wordDict`, return `true` if `s` can be segmented into a space-separated sequence of one or more dictionary words.

**Note** that the same word in the dictionary may be reused multiple times in the segmentation. 

**Example 1:**

```
Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
```

**Example 2:**

```
Input: s = "applepenapple", wordDict = ["apple","pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
Note that you are allowed to reuse a dictionary word.
```

**Example 3:**

```
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false
```

**Constraints:**

-   `1 <= s.length <= 300`
-   `1 <= wordDict.length <= 1000`
-   `1 <= wordDict[i].length <= 20`
-   `s` and `wordDict[i]` consist of only lowercase English letters.
-   All the strings of `wordDict` are **unique**.

### Standard Solution

#### Solution #1 Brute Force

*   Use recursion and backtracking, at each position of the string to decide whether to break the word or not

```java
public boolean wordBreak(String s, List<String> wordDict){
    return wordBreakRecur(s, new HashSet<>(wordDict), 0);
}
private boolean wordBreakRecur(String s, Set<String> wordDict, int start){
    if (start == s.length()){
        return start;
    }
    for (int end = start + 1; end <= s.length(); end++){
        if (wordDict.contains(s.substring(start, end)) && wordBreakRecur(s, wordDict, end)){
            return true;
        }
    }
    return false;
}
```

-   Time complexity: $O(2^n)$. Given a string of length $n$, there are $n + 1$ ways to split it into two parts. At each step, we have a choice: to split or not to split. In the worse case, when all choices are to be checked, that results in $O(2^n)$.
-   Space complexity: $O(n)$. The depth of the recursion tree can go to $n$.

#### Solution #2 Recursion with Memoization

*   **Memoization**: use an array `memo` to store the result of the subproblems

```java
public boolean wordBreak(String s, List<String> wordDict){
    // boolean memoization array
    return wordBreakMemo(s, new HashSet<>(wordDict), 0, new Boolean[s.length()]);
}
private boolean wordBreakMemo(String s, Set<String> wordDict, int start, Boolean[] memo){
    if (start == s.length()){
        return true;
    }
    if (memo[start] != null){
        return memo[start];
    }
    for (int end = start + 1; end <= s.length(); end++){
        if (wordDict.contains(s.substring(start, end)) && wordBreakMemo(s, wordDict, end, memo)){
            return memo[start] = true;
        }
    }
    return memo[start] = false;
}
```

-   Time complexity: $O(n^3)$. The size of the recursion tree can go up to $n^2$.
-   Space complexity: $O(n)$. The depth of the recursion tree can go up to n.

#### Solution #3 Dynamic Programming

*   The bottom-up method with memoization

```java
public boolean wordBreak(String s, List<String> wordDict) {
    Set<String> wordDictSet = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;
    for (int i = 1; i <= s.length(); i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                dp[i] = true;
                break;
            }
        }
    }
    return dp[s.length()];
}
```

-   Time complexity: $O(n^3)$. There are two nested loops and substring computation at each iteration. Overall that results in $O(n^3)$ time complexity.
-   Space complexity: $O(n)$. The length of p array is $n+1$

## Longest Common Subsequence (Medium #1143)

**Question**: Given two strings `text1` and `text2`, return *the length of their longest **common subsequence**.* If there is no **common subsequence**, return `0`.

A **subsequence** of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.

-   For example, `"ace"` is a subsequence of `"abcde"`.

A **common subsequence** of two strings is a subsequence that is common to both strings.

**Example 1:**

```
Input: text1 = "abcde", text2 = "ace" 
Output: 3  
Explanation: The longest common subsequence is "ace" and its length is 3.
```

**Example 2:**

```
Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.
```

**Example 3:**

```
Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.
```

**Constraints:**

-   `1 <= text1.length, text2.length <= 1000`
-   `text1` and `text2` consist of only lowercase English characters.

### Standard Solution

#### Solution #1 Memoization

*   Use a 2D memoization array to store the maximal length of a substring from each location
*   Assume text2 is the shorter string

![Image showing subproblem LCS("ctgattag", "tcg")](https://leetcode.com/problems/longest-common-subsequence/Figures/1143/subproblem_1.png)

```java
class Solution {
    
  private int[][] memo;
  private String text1;
  private String text2;
    
  public int longestCommonSubsequence(String text1, String text2) {
    // Make the memo big enough to hold the cases where the pointers
    // go over the edges of the strings.
    this.memo = new int[text1.length() + 1][text2.length() + 1];
    // We need to initialise the memo array to -1's so that we know
    // whether or not a value has been filled in. Keep the base cases
    // as 0's to simplify the later code a bit.
    for (int i = 0; i < text1.length(); i++) {
      for (int j = 0; j < text2.length(); j++) {
        this.memo[i][j] = -1;
      }
    }
    this.text1 = text1;
    this.text2 = text2;
    return memoSolve(0, 0);
  }

  private int memoSolve(int p1, int p2) {        
    // Check whether or not we've already solved this subproblem.
    // This also covers the base cases where p1 == text1.length
    // or p2 == text2.length.
    if (memo[p1][p2] != -1) {
      return memo[p1][p2];
    }

    // Option 1: we don't include text1[p1] in the solution.
    int option1 = memoSolve(p1 + 1, p2);

    // Option 2: We include text1[p1] in the solution, as long as
    // a match for it in text2 at or after p2 exists.
    int firstOccurence = text2.indexOf(text1.charAt(p1), p2);
    int option2 = 0;
    if (firstOccurence != -1) {
        // next one start from the common index + 1
      option2 = 1 + memoSolve(p1 + 1, firstOccurence + 1);
    }

    // Add the best answer to the memo before returning it.
    memo[p1][p2] = Math.max(option1, option2);
    return memo[p1][p2];
  }
}
```

-   Time complexity: $O(M \cdot N^2)$.

    We analyze a memoized-recursive function by looking at how many unique subproblems it will solve, and then what the cost of solving each subproblem is.

    The input parameters to the recursive function are a pair of integers; representing a position in each string. There are M possible positions for the first string and N for the second string. Therefore, this gives us $M \cdot N$ possible pairs of integers, and is the number of subproblems to be solved.

    Solving each subproblem requires, in the worst case, an $O(N)$ operation; searching for a character in a string of length N. This gives us a total of $(M \cdot N^2)$.

-   Space complexity: $O(M \cdot N)$.

    We need to store the answer for each of the $M \cdot N$ subproblems. Each subproblem takes $O(1)$ space to store. This gives us a total of $O(M \cdot N)$.

#### Solution #2 Improved Memoization

*   An improved version of the solution #1

```java
class Solution {
    
  private int[][] memo;
  private String text1;
  private String text2;
    
  public int longestCommonSubsequence(String text1, String text2) {
    // Make the memo big enough to hold the cases where the pointers
    // go over the edges of the strings.
    this.memo = new int[text1.length() + 1][text2.length() + 1];
    // We need to initialise the memo array to -1's so that we know
    // whether or not a value has been filled in. Keep the base cases
    // as 0's to simplify the later code a bit.
    for (int i = 0; i < text1.length(); i++) {
      for (int j = 0; j < text2.length(); j++) {
        this.memo[i][j] = -1;
      }
    }
    this.text1 = text1;
    this.text2 = text2;
    return memoSolve(0, 0);
  }

  private int memoSolve(int p1, int p2) {        
    // Check whether or not we've already solved this subproblem.
    // This also covers the base cases where p1 == text1.length
    // or p2 == text2.length.
    if (memo[p1][p2] != -1) {
      return memo[p1][p2];
    }

    // Recursive cases.
    int answer = 0;
    if (text1.charAt(p1) == text2.charAt(p2)) {
      answer = 1 + memoSolve(p1 + 1, p2 + 1);
    } else {
      answer = Math.max(memoSolve(p1, p2 + 1), memoSolve(p1 + 1, p2));
    }
    
    // Add the best answer to the memo before returning it.
    memo[p1][p2] = answer;
    return memo[p1][p2];
  }
}
```

-   Time complexity: $O(M \cdot N)$.

    This time, solving each subproblem has a cost of $O(1)$. Again, there are $M \cdot N$ subproblems, and so we get a total time complexity of $O(M \cdot N)$.

-   Space complexity: $O(M \cdot N)$.

    We need to store the answer for each of the $M \cdot N$ subproblems.

## Maximal Square (Medium #221)

**Question**: Given an `m x n` binary `matrix` filled with `0`'s and `1`'s, *find the largest square containing only* `1`'s *and return its area*.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/11/26/max1grid.jpg" alt="img" style="zoom:50%;" />

```
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/11/26/max2grid.jpg" alt="img" style="zoom:50%;" />

```
Input: matrix = [["0","1"],["1","0"]]
Output: 1
```

**Example 3:**

```
Input: matrix = [["0"]]
Output: 0
```

**Constraints:**

-   `m == matrix.length`
-   `n == matrix[i].length`
-   `1 <= m, n <= 300`
-   `matrix[i][j]` is `'0'` or `'1'`.

### My Solution

*   Works but is way too complicated, exceed the limited time

```java
public int maximalSquare(char[][] matrix) {
    // sliding window in square
    int m = matrix.length;
    int n = matrix[0].length;
    int length = m > n ? n : m;

    int res = 0;
    for (int i = length; i >= 1; i--){
        for (int row = 0; row + i <= m; row++){
            for (int col = 0; col + i <= n; col++){
                int indicator = 0;
                for (int rowIndex = row; rowIndex < row + i; rowIndex++){
                    for (int colIndex = col; colIndex < col + i; colIndex++){
                        if (matrix[rowIndex][colIndex] == '0'){
                            indicator = 0;
                            break;
                        }
                        indicator++;
                    }
                }
                if (indicator == i * i){
                res = indicator;
                break;
                }
            }

        }
        if (res != 0){
            break;
        }
    }
    return res;
}
```

### Standard Solution

#### Solution #1 Dynamic Programming

*   Create a 2D array table, store the maximal square found so far at this position
*   Add the minimal previous value to get the current maximal square data 

<img src="https://leetcode.com/media/original_images/221_Maximal_Square.PNG?raw=true" alt="Max Square" style="zoom: 67%;" />

```java
public int maximalSquare(char[][] matrix) {
    int rows = matrix.length, cols = matrix[0].length;
    int[][] dp = new int[rows + 1][cols + 1];
    int maxsqlen = 0;
    for (int i = 1; i <= rows; i++){
        for (int j = 1; j <= cols; j++){
            if (matrix[i - 1][j - 1] == '1'){
                dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                maxsqlen = Math.max(maxsqlen, dp[i][j]);
            }
        }
    }
    return maxsqlen * maxsqlen;
}
```

-   Time complexity: $O(mn)$. Single-pass.
-   Space complexity: $O(mn)$. Another matrix of the same size is used for dp.

## Unique Paths (Medium #62)

**Question**: There is a robot on an `m x n` grid. The robot is initially located at the **top-left corner** (i.e., `grid[0][0]`). The robot tries to move to the **bottom-right corner** (i.e., `grid[m - 1][n - 1]`). The robot can only move either down or right at any point in time.

Given the two integers `m` and `n`, return *the number of possible unique paths that the robot can take to reach the bottom-right corner*.

The test cases are generated so that the answer will be less than or equal to `2 * 109`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/10/22/robot_maze.png)

```
Input: m = 3, n = 7
Output: 28
```

**Example 2:**

```
Input: m = 3, n = 2
Output: 3
Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Down -> Down
2. Down -> Down -> Right
3. Down -> Right -> Down
```

**Constraints:**

-   `1 <= m, n <= 100`

### My Solution

```java
public int uniquePaths(int m, int n) {
    // we can create a 2D array to record the path number to current point
    int[][] path = new int[m + 1][n + 1];
    if (m == 1 || n == 1){
        return 1;
    }
    // base cases
    path[2][1] = 1;
    path[1][2] = 1;
    // other cases are sum of other locations to current one
    for (int i = 1; i <= m; i++){
        for (int j = 1; j <= n; j++){
            if (i + j <= 3){
                continue;
            }
            path[i][j] = path[i][j - 1] + path[i - 1][j];
        }
    }
    return path[m][n];
}
```

### Standard Solution

#### Solution #1 Dynamic Programming

*   Similar to my solution

```java
public int uniquePaths(int m, int n){
	int[][] d = new int[m][n];
    
    for (int[] arr : d){ // loop through the 2d array
        Arrays.fill(arr, 1);
    }
    for (int col = 1; col < m; col++){
        for (int row = 1; row < n; row++){
            d[col][row] = d[col - 1][row] + d[col][row - 1];
        }
    }
    return d[m - 1][n - 1];
}
```

-   Time complexity: $\mathcal{O}(N \times M)$
-   Space complexity: $\mathcal{O}(N \times M)$

## Coin Change (Medium #322)

**Question**: You are given an integer array `coins` representing coins of different denominations and an integer `amount` representing a total amount of money.

Return *the fewest number of coins that you need to make up that amount*. If that amount of money cannot be made up by any combination of the coins, return `-1`.

You may assume that you have an infinite number of each kind of coin.

**Example 1:**

```
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1
```

**Example 2:**

```
Input: coins = [2], amount = 3
Output: -1
```

**Example 3:**

```
Input: coins = [1], amount = 0
Output: 0
```

**Constraints:**

-   `1 <= coins.length <= 12`
-   `1 <= coins[i] <= 231 - 1`
-   `0 <= amount <= 104`

### Standard Solution

#### Solution #1 Dynamic Programming - Top-down

*   Use an array to find at each number, how many coins we need to have - memoization
*   Loop through the coins and use recursion to find the minimum number of coins that are needed
*   Each time add 1 to the count

```java
  public int coinChange(int[] coins, int amount) {
    if (amount < 1) return 0;
    return coinChange(coins, amount, new int[amount]);
  }

  private int coinChange(int[] coins, int rem, int[] count) {
    if (rem < 0) return -1;
    if (rem == 0) return 0;
    if (count[rem - 1] != 0) return count[rem - 1];
    int min = Integer.MAX_VALUE;
    for (int coin : coins) {
      int res = coinChange(coins, rem - coin, count);
      if (res >= 0 && res < min)
        min = 1 + res;
    }
    count[rem - 1] = (min == Integer.MAX_VALUE) ? -1 : min;
    return count[rem - 1];
  }
```

-   Time complexity: $O(S*n)$. where S is the amount, and n is the denomination count. In the worst case, the recursive tree of the algorithm has a height of S and the algorithm solves only S subproblems because it caches precalculated solutions in a table. Each subproblem is computed with n iterations, one by coin denomination. Therefore there is $O(S*n)$ time complexity.
-   Space complexity: $O(S)$, where S is the amount to change. We use extra space for the memoization table.

#### Solution #2 Dynamic Programming - Bottom up

*   Use an array with 1 more length of the coins array
*   Keep finding the minimum of the coin count

<img src="https://leetcode.com/media/original_images/322_coin_change_table.png" alt="Bottom-up approach using a table to build up the solution to F6." style="zoom:33%;" />

```java
public int coinChnage(int[] coins, int amount){
    int max = amount + 1;
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, max); // fill with the possible max counts
    dp[0] = 0;
    for (int i = 1; i <= amount; i++){
        for (int j = 0; j < coins.length; j++){
            if (coins[j] <= i){
                dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
            }
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

-   Time complexity: $O(S*n)$. On each step, the algorithm finds the next $*F(i)$ in n iterations, where $1\leq i \leq S$. Therefore in total, the iterations are $S*n$.
-   Space complexity: $O(S)$. We use extra space for the memoization table.
