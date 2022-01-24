# Array and String Tutorial 

Materials refer to: https://leetcode.com/explore/learn/card/array-and-string/201/introduction-to-array/1143/

# Introduction to Array

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

## Plus One(Easy #66)

**Question**: You are given a **large integer** represented as an integer array `digits`, where each `digits[i]` is the `ith` digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading `0`'s.

Increment the large integer by one and return *the resulting array of digits*.

 **Example 1:**

```
Input: digits = [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Incrementing by one gives 123 + 1 = 124.
Thus, the result should be [1,2,4].
```

**Example 2:**

```
Input: digits = [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
Incrementing by one gives 4321 + 1 = 4322.
Thus, the result should be [4,3,2,2].
```

**Example 3:**

```
Input: digits = [9]
Output: [1,0]
Explanation: The array represents the integer 9.
Incrementing by one gives 9 + 1 = 10.
Thus, the result should be [1,0].
```

 **Constraints:**

-   `1 <= digits.length <= 100`
-   `0 <= digits[i] <= 9`
-   `digits` does not contain any leading `0`'s.

### My Solution

```java
public int[] plusOne(int[] digits){
    // Use a linkedlist to store the digits from least important ones
    List<Integer> digitList = new ArrayList<>();
    
    int len = digits.length;
    int carry = 1;
    
    // Loop throught the array and store the value to arraylist with carry
    for (int i = len - 1; i >= 0; i--){
        int currentSum = digits[i] + carry;
        carry = currentSum / 10;
        int currentDigit = currentSum % 10;
        digitList.add(currentDigit);
    }
    digitList.add(carry);
    Collections.reverse(digitList);
    return digitList.stream().mapToInt(Integer::intValue).toArray();
    //convenient but rarely use in leetcode
}
```

### Standard Solution

*   A straightforward idea to convert everything into integers and then apply the addition could be risky, especially for the implementation in Java, due to the potential integer overflow issue.

#### Solution #1 Schoolbook Addition with Carrt

*   **Algorithm**:
    *   Move along the input array starting from the end of array.
    *   Set all the nines at the end of array to zero.
    *   If we meet a not-nine digit, we would increase it by one. The job is done - return `digits`.
    *   We're here because ***all\*** the digits were equal to nine. Now they have all been set to zero. We then append the digit `1` in front of the other digits and return the result.

```java
public int[] plusOne(int[] digits){
    int n = digits.length;
    
    // move along the input array starting from the end
    for (int idx = n - 1; idx >= 0; idx--){
        // set all the nines at the end of array to zeros
        if (digits[idx] == 9){
            digits[idx] = 0;
        }
        // here we have the rightmost not-nine
        else {
            //increase this rightmost not-nine by 1 and just return the digits
            digits[idx]++;
            return digits;
        }
    }
    // we're here because all the digits are nines
    digits = new int[n + 1];
    digits[0] = 1;
    return digits;
}
```

```java
public int[] plusOne(int[] digits) {
    int i = digits.length - 1;

    while(i >= 0 && digits[i] == 9) digits[i--] = 0;

    if (i < 0){
        digits = new int[digits.length + 1];
        i = 0;
    }
    digits[i]++;
    return digits;
}
```

*   Time complexity: $O(N)$
*   Space complexity: $O(N)$
    *   Although we perform the operation **in-place** (*i.e.* on the input list itself), in the worst scenario, we would need to allocate an intermediate space to hold the result, which contains the N+1 elements. Hence the overall space complexity of the algorithm is $\mathcal{O}(N)$.

# Introduction to 2D Array

*   Similar to a one-dimensional array, a `two-dimensional array` also consists of a sequence of elements. But the elements can be laid out in a `rectangular grid` rather than a line.
*   Similar to the one-dimensional dynamic array, we can also define a dynamic two-dimensional array. Actually, it can be just `a nested dynamic array`. 

```java
// "static void main" must be defined in a public class.
public class Main {
    private static void printArray(int[][] a) {
        for (int i = 0; i < a.length; ++i) {
            System.out.println(a[i]);
        }
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; a[i] != null && j < a[i].length; ++j) {//pay attention to this condition
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        System.out.println("Example I:");
        int[][] a = new int[2][5];
        printArray(a);
        System.out.println("Example II:");
        int[][] b = new int[2][];
        printArray(b);
        System.out.println("Example III:");
        b[0] = new int[3];
        b[1] = new int[5];
        printArray(b);
    }
}
```

## Diagonal Traverse(Medium #498)

**Question**: Given an `m x n` matrix `mat`, return *an array of all the elements of the array in a diagonal order*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/04/10/diag1-grid.jpg)

```
Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,4,7,5,3,6,8,9]
```

**Example 2:**

```
Input: mat = [[1,2],[3,4]]
Output: [1,2,3,4] 
```

**Constraints:**

-   `m == mat.length`
-   `n == mat[i].length`
-   `1 <= m, n <= 104`
-   `1 <= m * n <= 104`
-   `-105 <= mat[i][j] <= 105`

### My Solution

*   Sum of i and j is the same for the diagonal
*   While i + j is odd, the print order is normal(**yellow**), while i + j is even, the print order is reverse order(**red**)
*   While i + j is odd, the next print order is reverse(**red**), while i + j is even, the next print order is normal order(**yellow**)

```java
public int[] findDiagonalOrder(int[][] mat){
    int rowMax = mat.length;
    int colMax = mat[0].length;
    int row = 0, col = 0, i = 0;
    int[] nextPoint = new int[]{row, col};
    int[] returnArry = new int[rowMax*colMax];
    //returnArry[0] = mat[row][col];

    while(i < rowMax * colMax && row < rowMax && col < colMax){
        returnArry[i] = mat[row][col];
        i++;
        int[] nextIdx = traversePrintNext(new int[]{row, col}); 
        if (nextIdx[0] >= 0 && nextIdx[1] >= 0 && nextIdx[1] < colMax && nextIdx[0] < rowMax){
            nextPoint = nextIdx;
        }
        else {
            nextPoint = findNextStart(new int[]{row, col}, colMax, rowMax);
        }
        row = nextPoint[0];
        col = nextPoint[1];
    }
    return returnArry;
}

public int[] findNextStart(int[] lastEnd, int colMax, int rowMax){
    int sum = lastEnd[0] + lastEnd[1];//0 index for row, 1 index for col
    int nextStartRow = 0, nextStartCol = 0;
    if (sum % 2 == 0){// sum of i + j is even, next one is normal order
        if (lastEnd[1] + 1 >= colMax){//exceed col limit, go to next row
            nextStartCol = lastEnd[1];
            nextStartRow = lastEnd[0] + 1;
        }
        else{
            nextStartRow = lastEnd[0];
            nextStartCol = lastEnd[1] + 1;
        }
    } else {
        if (lastEnd[0] + 1 < rowMax){//if exceed row limit, go to next col
            nextStartCol = lastEnd[1];
            nextStartRow = lastEnd[0] + 1;
        }
        else{
            nextStartRow = lastEnd[0];
            nextStartCol = lastEnd[1] + 1;
        }
    }   
    return new int[]{nextStartRow, nextStartCol};
}

public int[] traversePrintNext(int[] lastNum){
    int sum = lastNum[0] + lastNum[1];
    int nextRow = 0, nextCol = 0;
    if (sum % 2 == 0){
        nextRow = lastNum[0] - 1;
        nextCol = lastNum[1] + 1;
    }
    else{
        nextRow = lastNum[0] + 1;
        nextCol = lastNum[1] - 1;
    }
    int[] nextPoint = new int[]{nextRow, nextCol};
    return nextPoint;
}
```

### Standard Solution

#### Solution #1 Diagonal Reversal

```java
public int[] findDiagonalOrder(int[][] matrix){
    if (matrix == null || matrix.length == 0) return new int[0];
    
    int m = matrix.length;// row length
    int n = matrix[0].length;// col length
    int[] nums = new int[m * n];
    
    boolean bXFlag = true; // use it to determine the direction of reversal
    // true: reverse direction, false: normal direction
    int k = 0;
    for (int i = 0; i < m + n; i++){ // i < sum of row and col idx
        int pm = bXFlag ? m : n; // determine the limit of row and col
        int pn = bXFlag ? n : m;
        
        int x = (i < pm) ? i : pm - 1;
        int y = i - x;
        
        while(x >= 0 && y < pn){ // reverse x and y, let them stand for x-axis and y-axis
            nums[k++] = bXFlag ? matrix[x][y] : matrix[y][x];
            x--;
            y++;
        }
        bXFlag = !bXFlag;
    }
    return nums;
}
```

```java
/** Same idea but easier for understanding**/
class Solution {
    public int[] findDiagonalOrder(int[][] matrix) {
        
        // Check for empty matrices
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }
        
        // Variables to track the size of the matrix
        int N = matrix.length;
        int M = matrix[0].length;
        
        // Incides that will help us progress through 
        // the matrix, one element at a time.
        int row = 0, column = 0;
        
        // As explained in the article, this is the variable
        // that helps us keep track of what direction we are
        // processing the current diaonal
        int direction = 1;
        
         // The final result array
        int[] result = new int[N*M];
        int r = 0;
        
        // The uber while loop which will help us iterate over all
        // the elements in the array.
        while (row < N && column < M) {
            
            // First and foremost, add the current element to 
            // the result matrix. 
            result[r++] = matrix[row][column];
            
            // Move along in the current diagonal depending upon
            // the current direction.[i, j] -> [i - 1, j + 1] if 
            // going up and [i, j] -> [i + 1][j - 1] if going down.
            int new_row = row + (direction == 1 ? -1 : 1);
            int new_column = column + (direction == 1 ? 1 : -1);
            
            // Checking if the next element in the diagonal is within the
            // bounds of the matrix or not. If it's not within the bounds,
            // we have to find the next head. 
            if (new_row < 0 || new_row == N || new_column < 0 || new_column == M) {
                
                // If the current diagonal was going in the upwards
                // direction.
                if (direction == 1) {
                    
                    // For an upwards going diagonal having [i, j] as its tail
                    // If [i, j + 1] is within bounds, then it becomes
                    // the next head. Otherwise, the element directly below
                    // i.e. the element [i + 1, j] becomes the next head
                    row += (column == M - 1 ? 1 : 0) ;
                    column += (column < M - 1 ? 1 : 0);
                        
                } else {
                    
                    // For a downwards going diagonal having [i, j] as its tail
                    // if [i + 1, j] is within bounds, then it becomes
                    // the next head. Otherwise, the element directly below
                    // i.e. the element [i, j + 1] becomes the next head
                    column += (row == N - 1 ? 1 : 0);
                    row += (row < N - 1 ? 1 : 0);
                }
                    
                // Flip the direction
                direction = 1 - direction;        
                        
            } else {
                
                row = new_row;
                column = new_column;
            }
        }
        return result;      
    }
}
```

*   Time Complexity: $O(N \cdot M)$ considering the array has N rows and M columns.
*   Space Complexity: $O(1)$ since we don't make use of any additional data structure. Note that the space occupied by the output array doesn't count towards the space complexity since that is a requirement of the problem itself. Space complexity comprises any `additional` space that we may have used to get to build the final array. 

# Introduction to String

*   A string is actually an array of `unicode characters`. You can perform almost all the operations we used in an array. 
*   Java may not use "==" to compare two strings.

```java
// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        // initialize
        String s1 = "Hello World";
        System.out.println("s1 is \"" + s1 + "\"");
        String s2 = s1;
        System.out.println("s2 is another reference to s1.");
        String s3 = new String(s1);
        System.out.println("s3 is a copy of s1.");
        // compare using '=='
        System.out.println("Compared by '==':");
        // true since string is immutable and s1 is binded to "Hello World"
        System.out.println("s1 and \"Hello World\": " + (s1 == "Hello World"));
        // true since s1 and s2 is the reference of the same object
        System.out.println("s1 and s2: " + (s1 == s2));
        // false since s3 is refered to another new object
        System.out.println("s1 and s3: " + (s1 == s3));
        // compare using 'equals'
        System.out.println("Compared by 'equals':");
        System.out.println("s1 and \"Hello World\": " + s1.equals("Hello World"));
        System.out.println("s1 and s2: " + s1.equals(s2));
        System.out.println("s1 and s3: " + s1.equals(s3));
        // compare using 'compareTo'
        System.out.println("Compared by 'compareTo':");
        System.out.println("s1 and \"Hello World\": " + (s1.compareTo("Hello World") == 0));
        System.out.println("s1 and s2: " + (s1.compareTo(s2) == 0));
        System.out.println("s1 and s3: " + (s1.compareTo(s3) == 0));
    }
}
```

*   `Immutable` means that you can't change the content of the string once it's initialized.
*   **Operations**

```java
// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        String s1 = "Hello World";
        // 1. concatenate
        s1 += "!";
        System.out.println(s1);
        // 2. find
        System.out.println("The position of first 'o' is: " + s1.indexOf('o'));
        System.out.println("The position of last 'o' is: " + s1.lastIndexOf('o'));
        // 3. get substring
        System.out.println(s1.substring(6, 11));
    }
}
```

*   You should be aware of `the time complexity` of these built-in operations

*   Also, in languages which the string is immutable, you should be careful with the **concatenation** operation

    *   In Java, since the string is `immutable`, concatenation works by first allocating enough space for the new string, copy the contents from the old string and append to the new string which is $O(n^2)$.

*   Obviously, an immutable string cannot be modified. If you want to modify just one of the characters, you have to create a new string.

*   **Solutions**

    *   If you did want your string to be mutable, you can convert it to a **char array**.

    ```java
    // "static void main" must be defined in a public class.
    public class Main {
        public static void main(String[] args) {
            String s = "Hello World";
            char[] str = s.toCharArray();
            str[5] = ',';
            System.out.println(str);
        }
    }
    ```

    *   If you have to concatenate strings often, it will be better to use some other data structures like `StringBuilder`. The below code runs in **`O(n)`** complexity.

    ```java
    // "static void main" must be defined in a public class.
    public class Main {
        public static void main(String[] args) {
            int n = 10000;
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < n; i++) {
                str.append("hello");
            }
            String s = str.toString();
        }
    }
    ```

## Add Binary(Easy #67)

**Question**: Given two binary strings `a` and `b`, return *their sum as a binary string*.

**Example 1:**

```
Input: a = "11", b = "1"
Output: "100"
```

**Example 2:**

```
Input: a = "1010", b = "1011"
Output: "10101"
```

**Constraints:**

-   `1 <= a.length, b.length <= 104`
-   `a` and `b` consist only of `'0'` or `'1'` characters.
-   Each string does not contain leading zeros except for the zero itself.

### My Solution

```java
public String addBinary(String a, String b) {
    int minLength = Math.min(a.length(), b.length());
    int maxLength = Math.max(a.length(), b.length());
    char[] longString, shortString;
    if (a.length() == maxLength) {
        longString = a.toCharArray();
        shortString = b.toCharArray();
    } else {
        longString = b.toCharArray();
        shortString = a.toCharArray();
    }

    int carry = 0;
    StringBuilder sb = new StringBuilder();
    // use the shorter string to loop from last char to the front
    for (int i = minLength - 1, j = maxLength - 1; i >= 0; i--, j--){
        int sumDigit = Character.getNumericValue(shortString[i]) + 
            Character.getNumericValue(longString[j]) + carry;
        carry = sumDigit / 2;// take the carry to the next calculation
        int digit = sumDigit % 2;
        sb.insert(0, digit);
    }
    while(minLength < maxLength){
        int location = maxLength - minLength - 1;
        int sumDigit = 0;
        // add the rest digits of the longer string
        sumDigit = Character.getNumericValue(longString[location]) + carry;

        carry = sumDigit / 2;
        int digit = sumDigit % 2;
        sb.insert(0, digit);
        minLength++;
    }
    if (carry == 1) sb.insert(0, carry);// if we have a carry then need to add a digit
    return sb.toString();
}
```

### Solution

*   A built-in solution: but low efficiency due to $O(M + N)$ complexity
    *   Convert a and b into integers.
    *   Compute the sum.
    *   Convert the sum back into binary form

```java
public String addBinary(String a, String b){
    return Integer.toBinaryString(Integer.parseInt(a, 2) + Integer.parseInt(b, 2));
}
```

#### Solution #1 Bit-by-Bit Computation

*   Same logics as my solution but a cleaner way
*   Start from carry = 0

```java
public String addBinary(String a, String b){
    int n = a.length(), m = b.length();
    if (n < m) return addBinary(b, a);//if not, recursion to switch
    int L = Math.max(n, m);
    
    StringBuilder sb = new StringBuilder();
    int carry = 0, j = m - 1;
    for(int i = L - 1; i > -1; --i){
        if (a.charAt(i) == '1') ++carry;
        if (j > -1 && b.charAt(j--) == '1') ++carry;
        
        if (carry % 2 == 1) sb.append('1');
        else sb.append('0');
        
        carry /= 2;
    }
    if (carry == 1) sb.append('1');
    sb.reverse(); //string builder has a reverse function **
    return sb.toString();
}
```

*   Time complexity: $\mathcal{O}(\max(N, M))$, where N and M are lengths of the input strings a and b.
*   Space complexity: $\mathcal{O}(\max(N, M))$ to keep the answer.

## Implement strStr()(Easy #28)

**Question**: Implement [strStr()](http://www.cplusplus.com/reference/cstring/strstr/).

Return the index of the first occurrence of needle in haystack, or `-1` if `needle` is not part of `haystack`.

**Clarification:**

What should we return when `needle` is an empty string? This is a great question to ask during an interview.

For the purpose of this problem, we will return 0 when `needle` is an empty string. This is consistent to C's [strstr()](http://www.cplusplus.com/reference/cstring/strstr/) and Java's [indexOf()](https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#indexOf(java.lang.String)).

**Example 1:**

```
Input: haystack = "hello", needle = "ll"
Output: 2
```

**Example 2:**

```
Input: haystack = "aaaaa", needle = "bba"
Output: -1
```

**Example 3:**

```
Input: haystack = "", needle = ""
Output: 0
```

**Constraints:**

-   `0 <= haystack.length, needle.length <= 5 * 104`
-   `haystack` and `needle` consist of only lower-case English characters.

### My Solution

```java
public int strStr(String haystack, String needle) {
    if (needle.equals("") || needle == null) return 0;

    char[] haystackArry = haystack.toCharArray();
    char[] needleArry = needle.toCharArray();

    for (int i = 0; i < haystackArry.length; i++){
        if (haystackArry[i] == needleArry[0]){
            if (i + needleArry.length > haystackArry.length) break;
            String comp = haystack.substring(i, i + needleArry.length);
            if (comp.equals(needle)){
                return i;
            }
        }
    }

    return -1;
}
```

### Standard Solution

#### Solution #1 One-line solution

*   Simple but slow

```java
public int strStr(String haystack, String needle){
    return haystack.indexOf(needle);
}
```

#### Solution #2 Two-pointers

*   Use two pointers to compare the characters
*   No need to transfer string to char array
*   Save the time of comparison
*   A much faster solution

```java
public int strStr(String haystack, String needle){
    int h = haystack.length();
    int n = needle.length();
    if (needle.isEmpty()) return 0;
    if (n > h) return -1;
    
    for (int i = 0; i < h - n + 1; i++){
        int j = 0, k;
        if ((haystack.charAt(i) == needle.charAt(j)) 
            && (haystack.charAt(i + n - 1) == needle.charAt(j + n - 1))){
            // each time compare the first char and the last char to see if the same
            k = i; // locate the idx
            // loop through the needle and haystack and idx + 1
            while(k < h && j < n && haystack.charAt(k) == needle.charAt(j)){
                k++;
                j++;
            }
            if (j == n) return i; // if all char are the same
        }
    }
    return -1;
}
```

#### Solution #3 KMP algorithm

*   [KMP algorithm](https://leetcode-cn.com/leetbook/read/array-and-string/cpoo6/)
*   Create a next integer array to represent encountered times of the characters

```java
public int strStr(String haystack, String needle) {
    if(needle.length()==0) return 0;
    int m = haystack.length(), i = 0;
    int n = needle.length(),   j = 0;

    int[] next = nextBuilder(needle);
    while(i<m && j<n) {
        if(j<0 || haystack.charAt(i) == needle.charAt(j)) {
            i++;j++;
        }else{j = next[j];}
    }
    if(j == n) { return i - j;}
    else return -1;

}
private int[] nextBuilder(String needle) {
    int m = needle.length();
    int[] next = new int[m];
    next[0] = -1;
    int t = -1, j = 0;
    while(j < m-1) {
        if(t < 0 || needle.charAt(t) == needle.charAt(j)) {
            t++;
            j++;
            next[j] = t;
        } else { t = next[t];}
    }
    return next;
}

作者：AguynamedRich
链接：https://leetcode-cn.com/leetbook/read/array-and-string/cm5e2/?discussion=aQc2Qy
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处
```

# Two-pointer Technique

*   Many situations require two-pointer

    *   Reverse the elements in an array
    *   The idea is to swap the first element with the end, advance to the next element and swapping repeatedly until it reaches the middle position. 
    *   And it is worth noting that this technique is often used in a `sorted` array.

    ```java
    public static void reverse(int[] v, int N) {
        int i = 0;
        int j = N - 1;
        while (i < j) {
            swap(v, i, j);  // this is a self-defined function
            i++;
            j--;
        }
    }
    ```

*   **Iterate the array from two ends to the middle.**

*   **One pointer starts from the beginning while the other pointer starts from the end.**

*   Sometimes, we can use `two pointers with different steps` to solve problems.

    *   Given an array and a value, remove all instances of that value in-place and return the new length.
        *   It is easy if we create new array, but that increase space complexity
        *   The best way is to use two pointers, one is used for iteration of the original array and another one points at the last position of the new array
    *   Code reference

    ```java
    public int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }
    ```

    *   We use two pointers, one faster-runner `i` and one slower-runner `k`, in the example above. `i` moves one step each time while `k` moves one step only if a new needed value is added.

*   **Common scenario**

    *   One **slow-runner** and one **fast-runner** at the same time(快慢指针). (**They may in the same direction**)
    *   Determine the movement strategy for both pointers.
    *   Similar to the previous scenario, you might sometimes need to `sort` the array before using the two-pointer technique. And you might need a `greedy` thought to determine your movement strategy.

## Reverse String(Easy #344)

**Question**: Write a function that reverses a string. The input string is given as an array of characters `s`.

You must do this by modifying the input array in-place with `O(1)` extra memory.

**Example 1:**

```
Input: s = ["h","e","l","l","o"]
Output: ["o","l","l","e","h"]
```

**Example 2:**

```
Input: s = ["H","a","n","n","a","h"]
Output: ["h","a","n","n","a","H"]
```

**Constraints:**

-   `1 <= s.length <= 105`
-   `s[i]` is a printable ascii character.

### My Solution

```java
public void reverseString(char[] s){
    int front = 0, end = s.length - 1;
    while(front < end){
        swap(s, front, end);
        front++;
        end--;
    }
}
public void swap(char[] s, int front, int end){
    char temp = s[front];
    s[front] = s[end];
    s[end] = temp;
}
```

-   Time complexity : $\mathcal{O}(N)$ to swap N/2 element.
-   Space complexity : $\mathcal{O}(1)$, it's a constant space solution.
-   Same as the standard solution

## Array Partition I(Easy #561)

**Question**: Given an integer array `nums` of `2n` integers, group these integers into `n` pairs `(a1, b1), (a2, b2), ..., (an, bn)` such that the sum of `min(ai, bi)` for all `i` is **maximized**. Return *the maximized sum*.

**Example 1:**

```
Input: nums = [1,4,3,2]
Output: 4
Explanation: All possible pairings (ignoring the ordering of elements) are:
1. (1, 4), (2, 3) -> min(1, 4) + min(2, 3) = 1 + 2 = 3
2. (1, 3), (2, 4) -> min(1, 3) + min(2, 4) = 1 + 2 = 3
3. (1, 2), (3, 4) -> min(1, 2) + min(3, 4) = 1 + 3 = 4
So the maximum possible sum is 4.
```

**Example 2:**

```
Input: nums = [6,2,6,5,1,2]
Output: 9
Explanation: The optimal pairing is (2, 1), (2, 5), (6, 6). min(2, 1) + min(2, 5) + min(6, 6) = 1 + 2 + 6 = 9.
```

**Constraints:**

-   `1 <= n <= 104`
-   `nums.length == 2 * n`
-   `-104 <= nums[i] <= 104`

### My Solution

```java
public int arrayPairSum(int[] nums){
    /** sort the array and group them by order (small with small, large with large in order)**/
    Arrays.sort(nums);
    int first = 0, second = 1;
    int sum = 0;
    while(first < nums.length){
        int min = Math.min(nums[first], nums[second]);
        sum += min;
        first += 2;
        second += 2;
    }
    return sum;
}
```

### Standard Solution

*   Same as my solution but a simpler one

```java
public int arrayPairSum(int[] nums) {
    Arrays.sort(nums);
    int sum = 0;
    for(int i=0; i<nums.length;i+=2) sum+=nums[i];
    return sum;
}
```

*   Time complexity: $O(n\log n)$
*   Space complexity: $O(1)$

## Remove Element(Easy #27)

**Question**: Given an integer array `nums` and an integer `val`, remove all occurrences of `val` in `nums` [**in-place**](https://en.wikipedia.org/wiki/In-place_algorithm). The relative order of the elements may be changed.

Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the **first part** of the array `nums`. More formally, if there are `k` elements after removing the duplicates, then the first `k` elements of `nums` should hold the final result. It does not matter what you leave beyond the first `k` elements.

Return `k` *after placing the final result in the first* `k` *slots of* `nums`.

Do **not** allocate extra space for another array. You must do this by **modifying the input array [in-place](https://en.wikipedia.org/wiki/In-place_algorithm)** with O(1) extra memory.

**Custom Judge:**

The judge will test your solution with the following code:

```
int[] nums = [...]; // Input array
int val = ...; // Value to remove
int[] expectedNums = [...]; // The expected answer with correct length.
                            // It is sorted with no values equaling val.

int k = removeElement(nums, val); // Calls your implementation

assert k == expectedNums.length;
sort(nums, 0, k); // Sort the first k elements of nums
for (int i = 0; i < actualLength; i++) {
    assert nums[i] == expectedNums[i];
}
```

If all assertions pass, then your solution will be **accepted** 

**Example 1:**

```
Input: nums = [3,2,2,3], val = 3
Output: 2, nums = [2,2,_,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 2.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Example 2:**

```
Input: nums = [0,1,2,2,3,0,4,2], val = 2
Output: 5, nums = [0,1,4,0,3,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums containing 0, 0, 1, 3, and 4.
Note that the five elements can be returned in any order.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Constraints:**

-   `0 <= nums.length <= 100`
-   `0 <= nums[i] <= 50`
-   `0 <= val <= 100`

### My Solution

```java
public int removeElement(int[] nums, int val){
    int i = 0, k = 0;
    while(i < nums.length){
        if (nums[i] != val){
            nums[k] = nums[i];
            k++;
        }
        i++;
    }
    return k;
}
```

*   Time complexity : $O(n)$. Assume the array has a total of n elements.
*   Space complexity : $O(1)$.
*   Same as standard solution
