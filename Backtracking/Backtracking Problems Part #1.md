# Backtracking Problems Part #1

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

*   Time complexity: $O(4^N \cdot N)$, where N is the length of `digits`. Note that 4 in this expression is referring to the maximum *value* length in the *hash map*, and ***not*** to the length of the *input*.

    The worst-case is where the input consists of only 7s and 9s. In that case, we have to explore 4 additional paths for every extra digit. Then, for each combination, it costs up to N to build the combination. This problem can be generalized to a scenario where numbers correspond with up to M digits, in which case the time complexity would be $O(M^N \cdot N)$. For the problem constraints, we're given, $M = 4,$ because digits 7 and 9 have 4 letters each.

*   Space complexity: $O(N)$, where N is the length of `digits`.

    Not counting space used for the output, **the extra space we use relative to input size is the space occupied by the recursion call stack**. It will only go as deep as the number of digits in the input since whenever we reach that depth, we backtrack.

    As the hash map does not grow as the inputs grow, it occupies $O(1)$ space.