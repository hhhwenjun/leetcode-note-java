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