# Backtracking Problems Part #1

## Permutations(Medium #46)

**Question**: Given an array `nums` of distinct integers, return *all the possible permutations*. You can return the answer in **any order**.

**Example 1:**

```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

**Example 2:**

```
Input: nums = [0,1]
Output: [[0,1],[1,0]]
```

**Example 3:**

```
Input: nums = [1]
Output: [[1]]
```

**Constraints:**

-   `1 <= nums.length <= 6`
-   `-10 <= nums[i] <= 10`
-   All the integers of `nums` are **unique**.

### My Solution

```java
 public List<List<Integer>> permute(int[] nums){
    comb = new ArrayList<>();
    ArrayList<Integer> numsList = new ArrayList<Integer>();
    // change it to arraylist so it can be more convenient
    for (int num : nums){
        numsList.add(num);
    }
    // locate and fix each digit
    // for (int i = 0; i < nums.length; i++){
    //     helperCalculator(i, numsList, comb);
    // }
    helperCalculator(0, numsList, comb);
    return comb;
}

public void helperCalculator(int digit, ArrayList<Integer> numsList, List<List<Integer>> comb){
    // at the end of the list
    if (digit == numsList.size() - 1){
        comb.add(new ArrayList<Integer>(numsList));
    }
    for (int begin = digit; begin < numsList.size(); begin++){
        Collections.swap(numsList, begin, digit);
        helperCalculator(digit + 1, numsList, comb);
        // recover the swap. eg: [1,2,3] swap to [2,1,3] and call recursion for [2,1,3]. 
        // then we need to swap back to [1,2,3] . 
        // next swap will be [3,2,1] then recursion and recover swap.
        Collections.swap(numsList, begin, digit);
    }
}
```

```java
// second attempt
List<List<Integer>> res = new ArrayList<>();
List<Integer> numsArry = new ArrayList<>();
public List<List<Integer>> permute(int[] nums) {
    for (int num : nums){
        numsArry.add(num);
    }
    helper(0);
    return res;
}

public void helper(int loc){
    if (loc == numsArry.size() - 1){
        res.add(new ArrayList<Integer>(numsArry));
        return;
    }
    for(int i = loc; i < numsArry.size(); i++){
        Collections.swap(numsArry, loc, i);
        helper(loc + 1);
        Collections.swap(numsArry, loc, i);
    }
}
```

*   Store the number array to the array list, use the built-in method to swap the digits.
*   When moving to the end digit, add the current array list to the results.
*   Swap -> call next -> swap back

### Standard Solution 

#### Solution #1 Backtracking

*   [Backtracking](https://en.wikipedia.org/wiki/Backtracking) is an algorithm for finding all solutions by exploring all potential candidates. 
*   Often used with recursion.
*   If the solution candidate turns to be *not* a solution (or at least not the *last* one), backtracking algorithm discards it by making some changes on the previous step, *i.e.* *backtracks* and then try again.
*   After each swap modification, after recording the candidates, we swap them back to the original.
*   Roughly same as my solution

```java
class Solution {
  public void backtrack(int n,
                        ArrayList<Integer> nums,
                        List<List<Integer>> output,
                        int first) {
    // if all integers are used up
    if (first == n)
      output.add(new ArrayList<Integer>(nums));
    for (int i = first; i < n; i++) {
      // place i-th integer first 
      // in the current permutation
      Collections.swap(nums, first, i);
      // use next integers to complete the permutations
      backtrack(n, nums, output, first + 1);
      // backtrack
      Collections.swap(nums, first, i);
    }
  }

  public List<List<Integer>> permute(int[] nums) {
    // init output list
    List<List<Integer>> output = new LinkedList();

    // convert nums into list since the output is a list of lists
    ArrayList<Integer> nums_lst = new ArrayList<Integer>();
    for (int num : nums)
      nums_lst.add(num);

    int n = nums.length;
    backtrack(n, nums_lst, output, 0);
    return output;
  }
}
```

*   Time complexity : $\mathcal{O}(\sum_{k = 1}^{N}{P(N, k)})$ = $\frac{N!}{(N - k)!} = N (N - 1) ... (N - k + 1)$ is so-called [*k-permutations_of_n*, or *partial permutation*](https://en.wikipedia.org/wiki/Permutation#k-permutations_of_n).
*   Space complexity : $\mathcal{O}(N!)$ since one has to keep `N!` solutions.

## Letter Combinations of a Phone Number(Medium #17)

**Question**: Given a string containing digits from `2-9` inclusive, return all possible letter combinations that the number could represent. Return the answer in **any order**.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that one does not map to any letters.

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/Telephone-keypad2.svg/200px-Telephone-keypad2.svg.png)

**Example 1:**

```
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**Example 2:**

```
Input: digits = ""
Output: []
```

**Example 3:**

```
Input: digits = "2"
Output: ["a","b","c"]
```

**Constraints:**

-   `0 <= digits.length <= 4`
-   `digits[i]` is a digit in the range `['2', '9']`.

### My Solution

*   The backtracking solution first stores the hashmap of the character and corresponding letters in the string.
*   The base case is a solution that has the same length as the given string length
*   For each loop through the letters in the stored number, then add 1 to the index and go to the next number.
*   Delete the last character in the solution

```java
private List<String> res = new LinkedList<>();
public Map<Character, String> getMap(){
    Map<Character, String> map = Map.of('2', "abc", '3', "def",
                                       '4', "ghi", '5', "jkl", '6', "mno",
                                       '7', "pqrs", '8', "tuv", '9', "wxyz");
    return map;
}
public List<String> letterCombinations(String digits) {
    if (digits.length() == 0) return res;
    Map<Character, String> map = getMap();
    char[] chars = digits.toCharArray();
    combination(map, 0, chars, new StringBuilder());
    return res;
}
public void combination(Map<Character, String> map, int idx, char[] chars, StringBuilder solution){
    // base case
    if (solution.length() == chars.length){
        res.add(solution.toString());
        return;
    }
    String letters = map.get(chars[idx]);
    for (char ch :letters.toCharArray()){
        // add the character to the solution
        solution.append(ch);
        // move to the next digit
        combination(map, idx + 1, chars, solution);
        // backtrack and remove the previous letter
        solution.deleteCharAt(solution.length() - 1);
    }
}
```

```java
// second attempt
Map<Character, String> map = Map.of('2', "abc", '3', "def", '4', "ghi",
                                   '5', "jkl", '6', "mno", '7', "pqrs",
                                   '8', "tuv", '9', "wxyz");
List<String> res = new ArrayList<>();
char[] nums;
public List<String> letterCombinations(String digits) {
    if (digits.length() == 0) return res;
    this.nums = digits.toCharArray();
    findComb(0, new StringBuilder());
    return res;
}

// create a backtracking method to find all the combinations
public void findComb(int start, StringBuilder string){
    // base case : when reach length of combination(e.g. "23", length is 2)
    if (start == nums.length){
        res.add(string.toString());
        return;
    }
    String chars = map.get(nums[start]);
    for (int j = 0; j < chars.length(); j++){
        string.append(chars.charAt(j));
        findComb(start + 1, string);
        string.deleteCharAt(string.length() - 1);
    }
}
```

*   Time complexity: $O(4^N \cdot N)$, where N is the length of `digits`. Note that 4 in this expression is referring to the maximum *value* length in the *hash map*, and ***not*** to the length of the *input*.

    The worst-case is where the input consists of only 7s and 9s. In that case, we have to explore 4 additional paths for every extra digit. Then, for each combination, it costs up to N to build the combination. This problem can be generalized to a scenario where numbers correspond with up to M digits, in which case the time complexity would be $O(M^N \cdot N)$. For the problem constraints, we're given, $M = 4,$ because digits 7 and 9 have 4 letters each.

*   Space complexity: $O(N)$, where N is the length of `digits`.

    Not counting space used for the output, **the extra space we use relative to input size is the space occupied by the recursion call stack**. It will only go as deep as the number of digits in the input since whenever we reach that depth, we backtrack.

    As the hash map does not grow as the inputs grow, it occupies $O(1)$ space.

## Word Search(Medium #79)

**Question**: Given an `m x n` grid of characters `board` and a string `word`, return `true`  *if*  `word` *exists in the grid*.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/04/word2.jpg)

```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/11/04/word-1.jpg)

```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true
```

**Example 3:**

![img](https://assets.leetcode.com/uploads/2020/10/15/word3.jpg)

```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false
```

**Constraints:**

-   `m == board.length`
-   `n = board[i].length`
-   `1 <= m, n <= 6`
-   `1 <= word.length <= 15`
-   `board` and `word` consists of only lowercase and uppercase English letters.

**Follow up:** Could you use search pruning to make your solution faster with a larger `board`?

### My Solution & Standard Solution

#### Solution #1 Backtracking

*   2D grid traversal problem, similar to Robot Room Cleaner problem
*   **Algorithm**: The skeleton of the algorithm is a loop that iterates through each cell in the grid. For each cell, we invoke the *backtracking* function (*i.e.* `backtrack()`) to check if we would obtain a solution, starting from this very cell.
    *   Step 1). In the beginning, first, we check if we reach the bottom case of the recursion, where the word to be matched is empty, *i.e.* we have already found the match for each prefix of the word.
    *   Step 2). We then check if the current state is invalid, either the position of the cell is out of the boundary of the board or the letter in the current cell does not match with the first letter of the word.
    *   Step 3). If the current step is valid, we then start the exploration of backtracking with the strategy of DFS. First, we mark the current cell as *visited*, *e.g.* any non-alphabetic letter will do. Then we iterate through the four possible directions, namely *up*, *right*, *down,* and *left*. The order of the directions can be altered, to one's preference.
    *   Step 4). At the end of the exploration, we revert the cell back to its original state. Finally, we return the result of the exploration.

```java
char[][] board;
String word;
int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
int maxRow = 0;
int maxCol = 0;
public boolean exist(char[][] board, String word) {
    this.board = board;
    this.word = word;
    this.maxRow = board.length;
    this.maxCol = board[0].length;
    for (int i = 0; i < maxRow; i++){
        for (int j = 0; j < maxCol; j++){
            if (backtrack(i, j, 0)){
                return true;
            }
        }
    }
    return false;
}

public boolean backtrack(int row, int col, int idx){
    if (idx >= word.length()){
        // find the word
        return true;
    }
    if (row < 0 || row == maxRow || col == maxCol || col < 0){
        // out of the boundary
        return false;
    }
    if (board[row][col] != word.charAt(idx)){
        // do not find the char at this location
        return false;
    }
    boolean track = false;
    // mark as visited before going to the next one
    this.board[row][col] = '#';
    for (int i = 0; i < 4; i++){
        track = backtrack(row + directions[i][0], col + directions[i][1], idx + 1);
        if (track){
            break;
        }
    }
    // put the char back to the visited cell
    this.board[row][col] = word.charAt(idx);
    return track;
}
```

-   Time Complexity: $\mathcal{O}(N \cdot 3 ^ L)$ where $N$ is the number of cells on the board and $L$ is the length of the word to be matched.
    -   For the backtracking function, initially, we could have at most 4 directions to explore, but further, the choices are reduced into 3 (since we won't go back to where we come from). As a result, the execution trace after the first step could be visualized as a 3-ary tree, each of the branches represents a potential exploration in the corresponding direction. Therefore, in the worst case, the total number of invocations would be the number of nodes in a full 3-nary tree, which is about $3^L$.
    -   We iterate through the board for backtracking, *i.e.* there could be N times invocation for the backtracking function in the worst case.
    -   As a result, overall the time complexity of the algorithm would be $\mathcal{O}(N \cdot 3 ^ L)$.
-   Space Complexity: $\mathcal{O}(L)$ where L is the length of the word to be matched.
    -   The main consumption of the memory lies in the recursion call of the backtracking function. The maximum length of the call stack would be the length of the word. Therefore, the space complexity of the algorithm is $\mathcal{O}(L)$.

## Combination Sum(Medium #39)

**Question**: Given an array of **distinct** integers `candidates` and a target integer `target`, return *a list of all **unique combinations** of* `candidates` *where the chosen numbers sum to* `target`*.* You may return the combinations in **any order**.

The **same** number may be chosen from `candidates` an **unlimited number of times**. Two combinations are unique if the frequency of at least one of the chosen numbers is different.

It is **guaranteed** that the number of unique combinations that sum up to `target` is less than `150` combinations for the given input.

**Example 1:**

```
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.
```

**Example 2:**

```
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
```

**Example 3:**

```
Input: candidates = [2], target = 1
Output: []
```

**Constraints:**

-   `1 <= candidates.length <= 30`
-   `1 <= candidates[i] <= 200`
-   All elements of `candidates` are **distinct**.
-   `1 <= target <= 500`

### My Solution

```java
int[] candidates;
List<List<Integer>> res = new ArrayList<>();
// main method
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    this.candidates = candidates;
    backtracking(0, target, new ArrayList<Integer>());
    return res;
}
// call backtracking to add new solution
public void backtracking(int start, int target, List<Integer> single){
    // base case
    if (target == 0){
        res.add(new ArrayList<Integer>(single));
        return;
    }
    else if (target < 0 || start == candidates.length){
        return;
    }
    // from different starting point
    for (int i = start; i < candidates.length; i++){
        single.add(candidates[i]);
        // each time start will add 1
        backtracking(i, target - candidates[i], single);
        single.remove(single.size() - 1);
    }
}
// pretty great solution, faster than 92%, less than 76%
```

### Standard Solution

#### Solution #1 Backtracking

![exploration tree](https://leetcode.com/problems/combination-sum/Figures/39/39_exploration_tree.png)

*   Same as my solution: backtracking in a for loop
    *   For the first base case of the recursive function, if the `remain==0`, *i.e.* we fulfill the desired target sum, therefore we can add the current combination to the final list.
    *   As another base case, if `remain < 0`, *i.e.* we exceed the target value, we will cease the exploration here.
    *   Other than the above two base cases, we would then continue to explore the sublist of candidates as `[start ... n]`. For each of the candidates, we invoke the recursive function itself with updated parameters.
        -   Specifically, we add the current candidate into the combination.
        -   With the added candidate, we now have less sum to fulfill, *i.e.* `remain - candidate`.
        -   For the next exploration, still we start from the current cursor `start`.
        -   At the end of each exploration, we ***backtrack\*** by popping out the candidate out of the combination.

*   Let N be the number of candidates, T be the target value, and M is the minimum value among the candidates.

*   Time Complexity: $\mathcal{O}(N^{\frac{T}{M}+1})$
    -   As we illustrated before, the execution of the backtracking is unfolded as a DFS traversal in an n-ary tree. The total number of steps during the backtracking would be the number of nodes in the tree.
    -   At each node, it takes a constant time to process, except the leaf nodes which could take a linear time to make a copy of the combination. So we can say that the time complexity is linear to the number of nodes of the execution tree.
    -   Here we provide a *loose* upper bound on the number of nodes.
        -   First of all, the fan-out of each node would be bounded to N, *i.e.* the total number of candidates.
        -   The maximal depth of the tree would be $\frac{T}{M}$, where we keep on adding the smallest element to the combination.
        -   As we know, the maximal number of nodes in the N-ary tree of $\frac{T}{M}$ height would be $N^{\frac{T}{M}+1}$.
    -   **Note that**, the actual number of nodes in the execution tree would be much smaller than the upper bound since the fan-out of the nodes is decreasing level by level.
*   Space Complexity: $\mathcal{O}(\frac{T}{M})$
    -   We implement the algorithm in recursion, which consumes some additional memory in the function call stack.
    -   The number of recursive calls can pile up to $\frac{T}{M}$, where we keep on adding the smallest element to the combination. As a result, the space overhead of the recursion is $\mathcal{O}(\frac{T}{M})$.
    -   In addition, we keep a combination of numbers during the execution, which requires at most $\mathcal{O}(\frac{T}{M})$ space as well.
    -   To sum up, the total space complexity of the algorithm would be $\mathcal{O}(\frac{T}{M})$.
    -   Note that, we did not take into account the space used to hold the final results for the space complexity.

## Combination Sum II(Medium #40)

**Question**: Given a collection of candidate numbers (`candidates`) and a target number (`target`), find all unique combinations in `candidates` where the candidate numbers sum to `target`.

Each number in `candidates` may only be used **once** in the combination.

**Note:** The solution set must not contain duplicate combinations.

**Example 1:**

```
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: 
[
[1,1,6],
[1,2,5],
[1,7],
[2,6]
]
```

**Example 2:**

```
Input: candidates = [2,5,2,1,2], target = 5
Output: 
[
[1,2,2],
[5]
]
```

**Constraints:**

-   `1 <= candidates.length <= 100`
-   `1 <= candidates[i] <= 50`
-   `1 <= target <= 30`

### My Solution

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target){
    List<List<Integer>> res = new ArrayList<>();
    int[] candidates;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        this.candidates = candidates;
        Arrays.sort(candidates);
        backtracking(0, target, new ArrayList<Integer>());
        return res;
    }
    // create backtracking method to find all combinations
    public void backtracking(int index, int target, List<Integer> combination){
        // base case
        if (target == 0){
            res.add(new ArrayList<Integer>(combination));
            return;
        }
        // invalid combinations or we traverse the last digit in the candidates
        else if (target < 0){
            return;
        }
        for (int i = index; i < candidates.length; i++){
            // skip the positions
            if (i > index && candidates[i] == candidates[i - 1]){
                continue;
            }
            if (target - candidates[i] < 0){
                break;
            }
            combination.add(candidates[i]);
            backtracking(i + 1, target - candidates[i], combination);
            combination.remove(combination.size() - 1);
        }
    }
}
```

### Standard Solution

#### Solution #1 Backtracking

*   Same as my solution
*   We skip the position if the following two conditions are met:
    -   1). `next_curr > curr`: we will pick the number at the current `curr` position into the combination, regardless of the other conditions. *This is important, since the iteration should allow us to select multiple instances of a unique number into the combination.*
    -   2). `candidates[next_curr] == candidates[next_curr-1]`: we will skip the occurrences of all repetitive numbers **in-between**, *e.g.* we skip the second and third occurrence of number `2` in this round of backtracking.

![index demo](https://leetcode.com/problems/combination-sum-ii/Figures/40/40_index_demo.png)

Let N*N* be the size of the input array.

-   Time Complexity: $\mathcal{O}(2^N)$
    -   In the worst case, our algorithm will *exhaust* all possible combinations from the input array, which in total amounts to $2^N$ as we discussed before.
    -   The sorting will take $\mathcal{O}(N \log N)$
    -   To sum up, the overall time complexity of the algorithm is dominated by the backtracking process, which is $\mathcal{O}(2^N)$
-   Space Complexity: $\mathcal{O}(N)$
    -   We use the variable `comb` to keep track of the current combination we build, which requires $\mathcal{O}(N)$ space.
    -   In addition, we apply recursion in the algorithm, which will incur additional memory consumption in the function call stack. In the worst case, the stack will pile up to $\mathcal{O}(N)$ space.
    -   To sum up, the overall space complexity of the algorithm is $\mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)$.
    -   ***Note:\*** we did not take into account the space needed to hold the final results of the combination in the above analysis.

## Permutation II(Medium #47)

**Question**: Given a collection of numbers, `nums`, that might contain duplicates, return *all possible unique permutations **in any order**.*

**Example 1:**

```
Input: nums = [1,1,2]
Output:
[[1,1,2],
 [1,2,1],
 [2,1,1]]
```

**Example 2:**

```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

**Constraints:**

-   `1 <= nums.length <= 8`
-   `-10 <= nums[i] <= 10`

### My Solution

```java
List<Integer> numsList = new ArrayList<>();
List<List<Integer>> res = new ArrayList<>();
Set<List<Integer>> set = new HashSet<>();
public List<List<Integer>> permuteUnique(int[] nums) {
    for (int num : nums){
        numsList.add(num);
    }
    backtracking(0);
    return res;
}
public void backtracking(int index){
    if (index == numsList.size() - 1){
        if (!set.contains(new ArrayList<Integer>(numsList))){
            set.add(new ArrayList<Integer>(numsList));
            res.add(new ArrayList<Integer>(numsList));
        }
        return;
    }
    for(int i = index; i < numsList.size(); i++){
        Collections.swap(numsList, i, index);
        backtracking(index + 1);
        Collections.swap(numsList, i, index);
    }
}
```

*   Similar to the permutation I solution, use a HashSet to record the permutations.

### Standard Solution

#### Solution #1 Backtracking with Groups of Numbers

*   Build a hash table with a unique number as key and occurrence as the value.
*   To implement the algorithm, first, we define a function called `backtrack(comb, counter)` which generates all permutations, starting from the current combination (`comb`) and the remaining numbers (`counter`).

```java
class Solution {

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        // count the occurrence of each number
        HashMap<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            if (!counter.containsKey(num))
                counter.put(num, 0);
            counter.put(num, counter.get(num) + 1);
        }

        LinkedList<Integer> comb = new LinkedList<>();
        this.backtrack(comb, nums.length, counter, results);
        return results;
    }

    protected void backtrack(
            LinkedList<Integer> comb,
            Integer N,
            HashMap<Integer, Integer> counter,
            List<List<Integer>> results) {

        if (comb.size() == N) {
            // make a deep copy of the resulting permutation,
            // since the permutation would be backtracked later.
            results.add(new ArrayList<Integer>(comb));
            return;
        }

        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            Integer num = entry.getKey();
            Integer count = entry.getValue();
            if (count == 0)
                continue;
            // add this number into the current combination
            comb.addLast(num);
            counter.put(num, count - 1);

            // continue the exploration
            backtrack(comb, N, counter, results);

            // revert the choice for the next exploration
            comb.removeLast();
            counter.put(num, count);
        }
    }
}
```

**Complexity Analysis**

Let N be the length of the input array. Hence, the number of permutations would be at maximum $N!$, *i.e.* $N \cdot (N-1) \cdot (N-2) ... 1$, when each number in the array is unique.

-   Time Complexity: $\mathcal{O}\big(\sum_{k = 1}^{N}{P(N, k)}\big)$
-   Space Complexity: $\mathcal{O}(N)$
    -   First of all, we build a hash table out of the input numbers. In the worst case where each number is unique, we would need $\mathcal{O}(N)$ space for the table.
    -   Since we applied recursion in the algorithm which consumes some extra space in the function call stack, we would need another $\mathcal{O}(N)$ space for the recursion.
    -   During the exploration, we keep a candidate of permutation along the way, which takes yet another $\mathcal{O}(N)$.
    -   To sum up, the total space complexity would be $\mathcal{O}(N) + \mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)$

## Restore IP Address (Medium #93)

**Question**: A **valid IP address** consists of exactly four integers separated by single dots. Each integer is between `0` and `255` (**inclusive**) and cannot have leading zeros.

-   For example, `"0.1.2.201"` and `"192.168.1.1"` are **valid** IP addresses, but `"0.011.255.245"`, `"192.168.1.312"` and `"192.168@1.1"` are **invalid** IP addresses.

Given a string `s` containing only digits, return *all possible valid IP addresses that can be formed by inserting dots into* `s`. You are **not** allowed to reorder or remove any digits in `s`. You may return the valid IP addresses in **any** order.

**Example 1:**

```
Input: s = "25525511135"
Output: ["255.255.11.135","255.255.111.35"]
```

**Example 2:**

```
Input: s = "0000"
Output: ["0.0.0.0"]
```

**Example 3:**

```
Input: s = "101023"
Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
```

**Constraints:**

-   `1 <= s.length <= 20`
-   `s` consists of digits only.

### Standard Solution

#### Solution #1 Backtracking

*   Since we need to insert dots, we can set them as an input to the backtracking method
*   Time complexity is constant, no more than 27 combinations to check
    *   If one already put a dot that leaves only `3` possibilities for the next dot to be placed: after one digit, after two digits, or after three digits. The first dot has only `3` available slots as well.
*   Space complexity is also constant, no more than 19 valid IP addresses

```java
List<String> res;
String s;
public List<String> restoreIpAddresses(String s) {
    res = new ArrayList<String>();
    this.s = s;
    // not valid address
    if (s.length() < 4 || s.length() > 12) return res;
    backtrack(0, new StringBuilder(), 4);
    return res;
}
// use backtracking to traverse all possible ip address
public void backtrack(int start, StringBuilder ip, int step){
    // base case: reach the end of string
    if (start == s.length() && step == 0){
        // since we always add dot after num, need to remove the dot
        res.add(ip.toString().substring(0, ip.length() - 1));
        return;
    }
    for (int i = start; i <= start + 2 && i < s.length(); i++){
        String num = s.substring(start, i + 1);
        if (!isvalid(num)) continue;
        // add a dot
        ip.append(num);
        ip.append(".");
        // backtracking
        backtrack(i + 1, ip, step - 1);
        ip.setLength(ip.length() - (i + 1 - start + 1));
    }
}
private boolean isvalid(String num){
    if (num.length() > 1 && num.startsWith("0") || Integer.parseInt(num) > 255){
        return false;
    }
    return true;
}
```

## Path Sum II (Medium #113)

**Question**: Given the `root` of a binary tree and an integer `targetSum`, return *all **root-to-leaf** paths where the sum of the node values in the path equals* `targetSum`*. Each path should be returned as a list of the node **values**, not node references*.

A **root-to-leaf** path is a path starting from the root and ending at any leaf node. A **leaf** is a node with no children.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/01/18/pathsumii1.jpg" alt="img" style="zoom:50%;" />

```
Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: [[5,4,11,2],[5,8,4,5]]
Explanation: There are two paths whose sum equals targetSum:
5 + 4 + 11 + 2 = 22
5 + 8 + 4 + 5 = 22
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/01/18/pathsum2.jpg)

```
Input: root = [1,2,3], targetSum = 5
Output: []
```

**Example 3:**

```
Input: root = [1,2], targetSum = 0
Output: []
```

**Constraints:**

-   The number of nodes in the tree is in the range `[0, 5000]`.
-   `-1000 <= Node.val <= 1000`
-   `-1000 <= targetSum <= 1000`

### My Solution

```java
// incorrect solution but logistically correct, solution return twice: why?
List<List<Integer>> res;      
public List<List<Integer>> pathSum(TreeNode root, int targetSum) {

    res = new ArrayList<>();
    if (root == null){
        return res;
    }
    if (root.val == targetSum && (root.left != null || root.right != null)){
        return res;
    }

    backtracking(root, new ArrayList<>(), targetSum);
    return res;
}
// backtracking to traverse the tree and find all solutions
public void backtracking(TreeNode root, List<Integer> subset, int numLeft){
    /**
    * Because we end at null, it would return same solution twice
    * that is because root.left is null && root.right is null
    */
    if (root == null && numLeft == 0){
        res.add(new ArrayList(subset));
        return;
    }
    if (root == null){
        return;
    }
    // backtracking process
    subset.add(root.val);
    backtracking(root.left, subset, numLeft - root.val);
    backtracking(root.right, subset, numLeft - root.val);
    subset.remove(subset.size() - 1);
    numLeft += root.val;
}
```

### Standard Solution

#### Solution #1 Backtracking

*   Count how much target sum is left in the method

```java
List<List<Integer>> res;  
public List<List<Integer>> pathSum(TreeNode root, int targetSum) {    
    res = new ArrayList<>();
    if (root == null){
        return res;
    }
    backtracking(root, new ArrayList<>(), targetSum);
    return res;
}

// backtracking to traverse the tree and find all solutions
public void backtracking(TreeNode root, List<Integer> subset, int numLeft){
    if (root == null){
        return;
    }
    subset.add(root.val);

    if (root.val == numLeft && root.left == null && root.right == null){
        res.add(new ArrayList(subset));
    }
    else {
        backtracking(root.left, subset, numLeft - root.val);
        backtracking(root.right, subset, numLeft - root.val);
    }
    // backtracking process
    subset.remove(subset.size() - 1);
}
```

-   Time Complexity: $O(N^2)$ where N are the number of nodes in a tree. In the worst case, we could have a complete binary tree and if that is the case, then there would be $N/2$ leafs. For every leaf, we perform a potential $O(N)$ operation of copying over the `pathNodes` nodes to a new list to be added to the final `pathsList`. Hence, the complexity in the worst case could be $O(N^2)$.
-   Space Complexity: $O(N)$. The space complexity, like many other problems is debatable here. I personally choose *not* to consider the space occupied by the `output` in the space complexity. So, all the `new` lists that we create for the paths are actually a part of the output and hence, don't count towards the final space complexity. The only *additional* space that we use is the `pathNodes` list to keep track of nodes along a branch.