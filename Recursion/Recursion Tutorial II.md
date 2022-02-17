# Recursion Tutorial II

## Divide and Conquer

*   A divide-and-conquer algorithm works by ***recursively*** breaking the problem down into ***two\*** or more subproblems of the same or related type until these subproblems become simple enough to be solved directly. Then one combines the results of subproblems to form the final solution.
*   **Divide-and-conquer manner**
    *   **Divide.** Divide the problem $S$ into a set of subproblems: $\{S_1, S_2, ... S_n\}$ where ${n \geq 2}$, *i.e.* there is usually more than one subproblem.
    *   **Conquer.** Solve each subproblem recursively. 
    *   **Combine.** Combine the results of each subproblem.

<img src="https://assets.leetcode.com/uploads/2019/04/24/d_c.png" alt="img" style="zoom:50%;" />

## Merge Sort

*   One of the classic examples of the divide-and-conquer algorithm is the merge sort algorithm.
*   There are two approaches to implement the merge sort algorithm: **top-down** or **bottom-up**. 

### Top-Down

<img src="https://assets.leetcode.com/uploads/2019/04/15/topdown_mergesort.png" alt="img" style="zoom:50%;" />

*   **Process**:
    *   In the first step, we divide the list into two sublists. (**Divide**)
    *   Then in the next step, we ***recursively*** sort the sublists in the previous step. (**Conquer**) 
    *   Finally, we merge the sorted sublists in the above step repeatedly to obtain the final list of sorted elements. (**Combine**)
*   The recursion in step (2) would reach the base case where the input list is either empty or contains a single element (see the nodes in blue from the above figure).
*   Merging two sorted lists can be done in linear time complexity ${O(N)}$, where ${N}$ is the total lengths of the two lists to merge.

<img src="https://assets.leetcode.com/uploads/2019/04/06/merge_sort_merge.gif" alt="img" style="zoom: 50%;" />

```java
import java.util.Arrays;
public class Solution {
    
    public int [] merge_sort(int [] input) {
      if (input.length <= 1) {
        return input;
      }
      int pivot = input.length / 2;
      int [] left_list = merge_sort(Arrays.copyOfRange(input, 0, pivot));
      int [] right_list = merge_sort(Arrays.copyOfRange(input, pivot, input.length));
      return merge(left_list, right_list);
    }
    
    public int [] merge(int [] left_list, int [] right_list) {
      int [] ret = new int[left_list.length + right_list.length];
      int left_cursor = 0, right_cursor = 0, ret_cursor = 0;

      while (left_cursor < left_list.length && 
             right_cursor < right_list.length) {
        if (left_list[left_cursor] < right_list[right_cursor]) {
          ret[ret_cursor++] = left_list[left_cursor++];
        } else {
          ret[ret_cursor++] = right_list[right_cursor++];
        }
      }
      // append what is remain the above lists
      while (left_cursor < left_list.length) {
        ret[ret_cursor++] = left_list[left_cursor++];
      }
      while (right_cursor < right_list.length) {
        ret[ret_cursor++] = right_list[right_cursor++];
      }  
      return ret;
    }
}
```

### Bottom-Up

*   In the **bottom-up** approach, we divide the list into sublists of a single element at the beginning. Each of the sublists is then sorted already. Then from this point on, we merge the sublists two at a time until a single list remains.

<img src="https://assets.leetcode.com/uploads/2019/04/06/mergesort.png" alt="img" style="zoom: 50%;" />

### Complexity

*   The overall **time complexity** of the merge sort algorithm is ${O(N \log{N})}$, where ${N}$ is the length of the input list. 
*   We recursively divide the input list into two sublists, until a sublist with single element remains. This dividing step computes the midpoint of each of the sublists, which takes ${O(1)}$ time. This step is repeated N times until a single element remains, therefore the total time complexity is $O(N)$.
*   As shown in the recursion tree, there are a total of N*N* elements on each level. Therefore, it takes $O({N})$ time for the merging process to complete on each level. And since there are a total of $\log{N}$ levels, the overall complexity of the merge process is $O({N \log{N}})$.
*   The ***space** **complexity*** of the merge sort algorithm is $O(N)$, where ${N}$ is the length of the input list, since we need to keep the sublists as well as the buffer to hold the merge results at each round of merge process.

## Sort an Array(Medium #912)

**Question**: Given an array of integers `nums`, sort the array in ascending order.

**Example 1:**

```
Input: nums = [5,2,3,1]
Output: [1,2,3,5]
```

**Example 2:**

```
Input: nums = [5,1,1,2,0,0]
Output: [0,0,1,1,2,5]
```

**Constraints:**

-   `1 <= nums.length <= 5 * 104`
-   `-5 * 104 <= nums[i] <= 5 * 104`

### My Solution

*   Merge sort with top-down method
*   No standard solution for this problem

```java
public int[] sortArray(int[] nums) {
    // if too short
	if (nums.length <= 1){
        return nums;
    }
    // divide and conquer, divide into two parts
    int pivot = nums.length / 2;
    int[] leftArry = sortArray(Arrays.copyOfRange(nums, 0, pivot));
    int[] rightArry = sortArry(Arrays.copyOfRange(nums, pivot, nums.length));
    return mergeSort(leftArry, rightArry);
}

public int[] mergeSort(int[] leftArry, int[] rightArry){
    int leftPos = 0;
    int rightPos = 0;
    int resPos = 0;
    int[] res = new int[leftArry.length + rightArry.length];
    while(leftPos < leftArry.length && rightPos < rightArry.length){
        if (leftArry[leftPos] < rightArry[rightPos]){
            res[resPos++] = leftArry[leftPos++];
        }
        else {
            res[resPos++] = rightArry[rightPos++];
        }
    }
    // paste all the rest digit to the res array
    while (leftPos < leftArry.length){
        res[resPos++] = leftArry[leftPos++];
    }
    while (rightPos < rightArry.length){
        res[resPos++] = rightArry[rightPos++];
    }
    return res;
}
```

