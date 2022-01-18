# Array and String

Materials refer to: https://leetcode.com/explore/learn/card/array-and-string/201/introduction-to-array/1143/

## Introduction to Array

*   An `array` is a basic data structure to `store a collection of elements sequentially`. 
*   An array can have one or more dimensions. Here we start with the `one-dimensional array`, which is also called the linear array.

<img src="https://s3-lc-upload.s3.amazonaws.com/uploads/2018/03/20/screen-shot-2018-03-20-at-191856.png" alt="img" style="zoom:50%;" />

*   Operations in Array

```java
// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        // 1. Initialize
        int[] a0 = new int[5];
        int[] a1 = {1, 2, 3};
        // 2. Get Length
        System.out.println("The size of a1 is: " + a1.length);
        // 3. Access Element
        System.out.println("The first element is: " + a1[0]);
        // 4. Iterate all Elements
        System.out.print("[Version 1] The contents of a1 are:");
        for (int i = 0; i < a1.length; ++i) {
            System.out.print(" " + a1[i]);
        }
        System.out.println();
        System.out.print("[Version 2] The contents of a1 are:");
        for (int item: a1) {
            System.out.print(" " + item);
        }
        System.out.println();
        // 5. Modify Element
        a1[0] = 4;
        // 6. Sort
        Arrays.sort(a1);
    }
}
```

### Dynamic Array

*   As we mentioned in the previous article, an array has `a fixed capacity` and we need to specify the size of the array when we initialize it. Sometimes this will be somewhat inconvenient and wasteful.
*   Therefore, most programming languages offer built-in `dynamic array` which is still a random access list data structure but with `variable size`. For example, we have `vector` in C++ and `ArrayList` in Java.

```java
// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        // 1. initialize
        List<Integer> v0 = new ArrayList<>();
        List<Integer> v1;                           // v1 == null
        // 2. cast an array to a vector
        Integer[] a = {0, 1, 2, 3, 4};
        v1 = new ArrayList<>(Arrays.asList(a));
        // 3. make a copy
        List<Integer> v2 = v1;                      // another reference to v1
        List<Integer> v3 = new ArrayList<>(v1);     // make an actual copy of v1
        // 3. get length
        System.out.println("The size of v1 is: " + v1.size());
        // 4. access element
        System.out.println("The first element in v1 is: " + v1.get(0));
        // 5. iterate the vector
        System.out.print("[Version 1] The contents of v1 are:");
        for (int i = 0; i < v1.size(); ++i) {
            System.out.print(" " + v1.get(i));
        }
        System.out.println();
        System.out.print("[Version 2] The contents of v1 are:");
        for (int item : v1) {
            System.out.print(" " + item);
        }
        System.out.println();
        // 6. modify element
        v2.set(0, 5);       // modify v2 will actually modify v1
        System.out.println("The first element in v1 is: " + v1.get(0));
        v3.set(0, -1);
        System.out.println("The first element in v1 is: " + v1.get(0));
        // 7. sort
        Collections.sort(v1);
        // 8. add new element at the end of the vector
        v1.add(-1);
        v1.add(1, 6);
        // 9. delete the last element
        v1.remove(v1.size() - 1);
    }
}
```

## Find Pivot Index(Easy #724)

**Question**: Given an array of integers `nums`, calculate the **pivot index** of this array.

The **pivot index** is the index where the sum of all the numbers **strictly** to the left of the index is equal to the sum of all the numbers **strictly** to the index's right.

If the index is on the left edge of the array, then the left sum is `0` because there are no elements to the left. This also applies to the right edge of the array.

Return *the **leftmost pivot index***. If no such index exists, return -1.

**Example 1:**

```
Input: nums = [1,7,3,6,5,6]
Output: 3
Explanation:
The pivot index is 3.
Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
Right sum = nums[4] + nums[5] = 5 + 6 = 11
```

**Example 2:**

```
Input: nums = [1,2,3]
Output: -1
Explanation:
There is no index that satisfies the conditions in the problem statement.
```

**Example 3:**

```
Input: nums = [2,1,-1]
Output: 0
Explanation:
The pivot index is 0.
Left sum = 0 (no elements to the left of index 0)
Right sum = nums[1] + nums[2] = 1 + -1 = 0
```

 **Constraints:**

-   `1 <= nums.length <= 104`
-   `-1000 <= nums[i] <= 1000`

### My Solution

```java
public int pivotIndex(int[] nums){
    int sum = 0, left = 0;
    for (int num : nums){
        sum += num;
    }
    
    for (int i = 0; i < nums.length; i++){
        if (left == sum - left - nums[i]) return i;
    }
    return -1;
}
```

### Standard Solution

*   Same as my solution
*   Let's say we knew `S` as the sum of the numbers, and we are at index `i`. If we knew the sum of numbers `leftsum` that are to the left of index `i`, then the other sum to the right of the index would just be `S - nums[i] - leftsum`.
*   Time Complexity: $O(N)$, where N is the length of `nums`.
*   Space Complexity: $O(1)$, the space used by `leftsum` and `S`.

## Largest Number At Least Twice of Others(Easy #747)

**Question**: You are given an integer array `nums` where the largest integer is **unique**.

Determine whether the largest element in the array is **at least twice** as much as every other number in the array. If it is, return *the **index** of the largest element, or return* `-1` *otherwise*.

 **Example 1:**

```
Input: nums = [3,6,1,0]
Output: 1
Explanation: 6 is the largest integer.
For every other number in the array x, 6 is at least twice as big as x.
The index of value 6 is 1, so we return 1.
```

**Example 2:**

```
Input: nums = [1,2,3,4]
Output: -1
Explanation: 4 is less than twice the value of 3, so we return -1.
```

**Example 3:**

```
Input: nums = [1]
Output: 0
Explanation: 1 is trivially at least twice the value as any other number because there are no other numbers.
```

 **Constraints:**

-   `1 <= nums.length <= 50`
-   `0 <= nums[i] <= 100`
-   The largest element in `nums` is unique.

### My Solution

```java
public int dominantIndex(int[] nums){

    if (nums.length == 1) return 0;
    int[] numsCopy = nums.clone();
    Arrays.sort(numsCopy);
    
    if (numsCopy[nums.length - 1] > 2*numsCopy[nums.length - 2]){
        for (int i = 0; i < nums.length; i++){
            if(nums[i] == numsCopy[nums.length - 1]) return i;
        }
    }
    return -1;
}
```

### Standard Solution

*   Keep two ints to store the max and the second max, and store the max index
*   In one loop, we store the max and the second max and finally compare their value

```java
 /** Algorithm:
    1. Keep two ints or int[2] to store the secondMax, max out of all the nums[]
    2. Traverse nums and if a new max is encountered, secondMax = max and max = thatInt
       Also, save the index of the new max. (maxIndex)
    3. If secondMax * 2 <= max return maxIndex, -1 otherwise.
*/
public int dominantIndex(int[] nums){
    int maxIndex = -1;
    if (nums.length == 1){
        return 0;
    }
    int[] maxes = new int[2]; // default would be 0
    for (int i = 0; i < nums.length; i++){
        if (nums[i] > maxes[1]){// larger than the current max
            maxes[0] = maxes[1]; // move the current max to second max
            maxes[1] = nums[i];
            maxIndex = i;
        } else if (nums[i] > maxes[0]){
            maxes[0] = nums[i];
        }
    }
    return maxes[0] * 2 <= maxes[1] ? maxIndex : -1;
}
```

## Next Permutation(Medium #31)

**Question**: Implement **next permutation**, which rearranges numbers into the lexicographically next greater permutation of numbers.

If such an arrangement is impossible, it must rearrange it to the lowest possible order (i.e., sorted in ascending order).

The replacement must be **in place** and use only constant extra memory.

 **Example 1:**

```
Input: nums = [1,2,3]
Output: [1,3,2]
// 132 is greater than 123 and it is the next greater number
```

**Example 2:**

```
Input: nums = [3,2,1]
Output: [1,2,3]
// 321 is the largest so return the samllest possible order which is 123
```

**Example 3:**

```
Input: nums = [1,1,5]
Output: [1,5,1]
```

 **Constraints:**

-   `1 <= nums.length <= 100`
-   `0 <= nums[i] <= 100`

### My Solution

*   Starts from the last few digits, switch them and find the maximum
*   The expand the algorithm to the hundred, thousand, or etc. digits. Switch the hundred with the number greater than it and sort the last few digits.

```java
public void nextPermutation(int[] nums){
    int len = nums.length;
    for (int i = len - 1; i > 0; i--){
        if (nums[i] > nums[i - 1]){
            Arrays.sort(nums, i, len);
            for (int j = i; j < len; j++){
                if (nums[j] > nums[i - 1]){
                    int temp = nums[j];
                    nums[j] = nums[i - 1];
                    nums[i - 1] = temp;
                    return;
                }
            }
        }
    }
    Arrays.sort(nums);
    return;
}
```

### Standard Solution 

#### Solution #1 Single Pass Approach

*   For any given sequence that is in descending order, no next larger permutation is possible
*   We need to find the first pair of two successive numbers $a[i]$ and $a[i-1]$, from the right, which satisfy $a[i] > a[i-1]$.
*   No arrangement to the right can create a larger permutation since the subarray in descending order
*   Then need to rearrange the numbers to the right of $a[i-1]$ including itself
*   Therefore, we need to replace the number $a[i-1]$ with the number which is just larger than itself among the numbers lying to its right section, say $a[j]$.

```java
public void nextPermutation(int[] nums){
    int i = nums.length - 2;
    while(i >= 0 && nums[i + 1] <= nums[i]){
        i--;
    }
    if (i >= 0){
        int j = nums.length - 1;
        while(nums[j] <= nums[i]){
            j--;
        }
        swap(nums, i, j);
    }
    reverse(nums, i + 1);
}

private void reverse(int[] nums, int start){
    int i = start, j = nums.length - 1;
    while(i < j){
        swap(nums, i, j);
        i++;
        j--;
    }
}

private void swap(int[] nums, int i, int j){
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

```java
/** An easier understanding version **/
public void nextPermutation(int[] nums){
    int len = nums.length;
    int idx = -1;
    for (int i = len - 1; i > 0; i--){
        if(nums[i] > nums[i - 1]){
            idx = i - 1;
            break;
        }
    }
    if (idx == -1){
        reverse(nums, 0, len - 1);
        return;
    }
    int swapIdx = -1;
    for (int i = len - 1; i > idx; i--){
        if (nums[i] > nums[idx]){
            swapIdx = i;
            break;
        }
    }
    swap(nums, idx, swapIdx); //swap the indices and reverse the rest of the array
    reverse(nums, idx + 1, len - 1);
}
private void reverse(int[] array, int a, int b){
    for(int i = a, j = b; i < j; i++, j--){
        swap(array, i, j);
    }
}
private void swap(int[] array, int a, int b){
    int temp = array[a];
    array[a] = array[b];
    array[b] = temp;
}
```

*   Time complexity: $O(n)$. In worst case, only two scans of the whole array are needed.
*   Space complexity: $O(1)$. No extra space is used. In place replacements are done.