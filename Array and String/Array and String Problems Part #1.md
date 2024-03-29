# Array and String Problems Part #1

## Palindrome Number(Easy 9)

**Question**: Given an integer `x`, return `true` if `x` is palindrome integer.

An integer is a **palindrome** when it reads the same backward as forward.

-   For example, `121` is a palindrome while `123` is not.

**Example 1:**

```
Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left.
```

**Example 2:**

```
Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
```

**Example 3:**

```
Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
```

**Constraints:**

-   `-231 <= x <= 231 - 1`

### My Solution

```java
public boolean isPalindrome(int x){
    if (x < 0) return false;
    List<Integer> numList = new ArrayList<>();
    
    // convert it to an interger arraylist
    while(x != 0){
        int digit = x % 10;
        numList.add(digit);
        x = x / 10;
    }
    int low = 0, high = numList.size() - 1;
    while(low < high){
        if (numList.get(low) != numList.get(high)) return false;
        low++;
        high--;
    }
    return true;
}
```

### Standard Solution

#### Solution #1 Revert half of the number

*   Revert the number and compare it with the original number
*   For number `1221`, if we do `1221 % 10`, we get the last digit `1`, to get the second to the last digit, we need to remove the last digit from `1221`, we could do so by dividing it by 10, `1221 / 10 = 122`. 
*   Then we can get the last digit again by doing a modulus by 10, `122 % 10 = 2`, and if we multiply the last digit by 10 and add the second last digit, `1 * 10 + 2 = 12`, it gives us the reverted number we want. Continuing this process would give us the reverted number with more digits.
*   Since we divided the number by 10, and multiplied the reversed number by 10, when the original number is less than the reversed number, it means we've processed half of the number digits.

```java
public bool isPalindrome(int x){
    // Special cases:
    // As discussed above, when x < 0, x is not a palindrome
    // Also if the last digit of the number is 0, in order to be a palindrome,
    // the first digit of the number also needs to be 0. Only 0 satisfy the property
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;
    
    int revertedNumber = 0;
    while(x > revertedNumber){
        revertedNumber = revertedNumber * 10 + x % 10;
        x /= 10;
    }
    
    // When the length is an odd number, we can get rid of the middle digit by revertedNumber / 10
    return x == revertedNumber || x == revertedNumber / 10;
}
```

*   Time complexity : $O(\log_{10}(n))$. We divided the input by 10 for every iteration, so the time complexity is $O(\log_{10}(n))$.
*   Space complexity : $O(1)$.

## Spiral Matrix(Medium #54)

**Question**: Given an `m x n` `matrix`, return *all elements of the* `matrix` *in spiral order*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/13/spiral1.jpg)

```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/11/13/spiral.jpg)

```
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
```

**Constraints:**

-   `m == matrix.length`
-   `n == matrix[i].length`
-   `1 <= m, n <= 10`
-   `-100 <= matrix[i][j] <= 100`

### Standard Solution 

#### Solution #1 Simulation that set up boundaries

*   Set up 4 for loop to visit all sides of the matrix
*   Each time move the line of sides and reset the boundaries
*   Print the number along the sides
*   Each time + 1 to the side needs to check if exceed side limit

```java
public List<Integer> spiralOrder(int[][] matrix){
    List<Integer> arr = new ArrayList<>();
    int left = 0, right = matrix[0].length - 1;
    int top = 0, down = matrix.length - 1;// use 4 pointers to locate the point
    
    while(true){
        for (int i = left; i <= right; ++i){
            arr.add(matrix[top][i]);
        }
        top++; // reset the boundary of top
        if (top > down) break; // in case overflow
        for (int i = top; i <= down; ++i){
            arr.add(matrix[i][right]);
        }
        right--;
        if (left > right) break;
        for (int i = right; i >= left; --i){
            arr.add(matrix[down][i]);
        }
        down--;
        if (top > down) break;
        for (int i = down; i >= top; --i){
            arr.add(matrix[i][left]);
        }
        left++;
        if (left > right) break;
    }
    return arr;
}
```

-   Time complexity: $O(M \cdot N)$. This is because we visit each element once.
-   Space complexity: $O(1)$. This is because we don't use other data structures. Remember that we don't include the output array in the space complexity.

#### Solution #2 Mark Visited Element

*   If we mark the cells that we have visited, then when we run into a visited cell, we know we need to turn.
*   An integer `changeDirection` is used to track the number of times we changed the direction consecutively.

```java
public List<Integer> spiralOrder(int[][] matrix){
    int VISITED = 101;
    int rows = matrix.length;
    int columns = matrix[0].length;
    // 4 directions that we will move: right, down, left, up
    int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    // initial direction: right
    int currentDirection = 0;
    // The number of times we change the direction.
    int changeDirection = 0;
    // Current place that we are at is (row, col)
    // row is the row index; col is the col index
    int row = 0, col = 0;
    // Store the first element and mark it as visited
    List<Integer> result = new ArrayList<>();
    result.add(matrix[0][0]);
    matrix[0][0] = VISITED;
    while(changeDirection < 2){
        while (row + directions[currentDirection][0] >= 0 &&
              row + directions[currentDirection][0] < rows &&
              col + directions[currentDirection][1] >= 0 &&
              col + directions[currentDirection][1] < columns &&
              matrix[row + directions[currentDirection][0]]
              [col + directions[currentDirection][1]] != VISITED){
            // Reset this to 0 since we did not break and change the direction.
            changeDirection = 0;
            // Calculate the next place that we will move to
            row = row + directions[currentDirection][0];
            col = col + directions[currentDirection][1];
            result.add(matrix[row][col]);
            matrix[row][col] = VISITED;
        }
        // change our direction.
        currentDirection = (currentDirection + 1) % 4;
        // increment change_direction because we changed our direction
        changeDirection++;
    }
    return result;
}
```

-   Time complexity: $O(M \cdot N)$. This is because we visit each element once.
-   Space complexity: $O(1)$. This is because we don't use other data structures. Remember that we don't consider the output array or the input matrix when calculating the space complexity. However, if we were prohibited from mutating the input matrix, then this would be an $O(M \cdot N)$ space solution. This is because we would need to use a boolean matrix to track all of the previously seen cells.

## Pascal's triangle(Easy #118)

**Question**: Given an integer `numRows`, return the first numRows of **Pascal's triangle**.

In **Pascal's triangle**, each number is the sum of the two numbers directly above it as shown:

![img](https://upload.wikimedia.org/wikipedia/commons/0/0d/PascalTriangleAnimated2.gif)

 

**Example 1:**

```
Input: numRows = 5
Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
```

**Example 2:**

```
Input: numRows = 1
Output: [[1]]
```

**Constraints:**

-   `1 <= numRows <= 30`

### My Solution

*   Create 2D arrays with 0
*   Add the first element 1 to the first array
*   Loop through the rest arrays, each time sum up the elements in the last array
*   If not 0, add to the result set

```java
public List<List<Integer>> generate(int numRows){
    List<List<Integer>> results = new ArrayList<>();
    int[][] resultMat = new int[numRows][numRows];
    resultMat[0][0] = 1;
    List<Integer> result = new ArrayList<>();
    // add the first row with single element
    result.add(resultMat[0][0]);
    results.add(result);
    
    // for the rest of the rows
    for (int i = 1; i < numRows; i++){
        result = new ArrayList<>();
        for (int j = 0; j < numRows; j++){
            if (j == 0){
                resultMat[i][j] = 1;
            } else {
                resultMat[i][j] = resultMat[i - 1][j - 1] + resultMat[i - 1][j];
            }
            if (resultMat[i][j] != 0) result.add(resultMat[i][j]);
        }
        results.add(result);
    }
    return results;
}
```

### Standard Solution

#### Solution #1 Dynamic Programming

*   Similar to my solution

```java
public List<List<Integer>> generate(int numRows){
    List<List<Integer>> triangle = new ArrayList<List<Integer>>();
    
    // Base case; first row is always[1].
    triangle.add(new ArrayList<>());
    triangle.get(0).add(1);
    
    for (int rowNum = 1; rowNum < numRows; rowNum++){
        List<Integer> row = new ArrayList<>();
        List<Integer> prevRow = triangle.get(rowNum - 1);
        
        // the first element is always 1
        row.add(1);
        
        // each triangle element (other than the first and last of each row)
        // is equal to the sum of the elements above-and-to-the-left and 
        // above-and-to-the-right.
        for (int j = 1; j < rowNum; j++){
            row.add(prevRow.get(j - 1) + prevRow.get(j));
        }
        
        // the last row element is always 1.
        row.add(1);
        triangle.add(row);
    }
    return triangle;
}
```

*   Time complexity : $O(numRows^2)$

*   Space complexity : $O(numRows^2)$

    Because we need to store each number that we update in `triangle`, the space requirement is the same as the time complexity.

## Gas Station(Medium #134)

**Question**: There are `n` gas stations along a circular route, where the amount of gas at the `ith` station is `gas[i]`.

You have a car with an unlimited gas tank and it costs `cost[i]` of gas to travel from the `ith` station to its next `(i + 1)th` station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays `gas` and `cost`, return *the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return* `-1`. If there exists a solution, it is **guaranteed** to be **unique**

**Example 1:**

```
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
```

**Example 2:**

```
Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.
```

**Constraints:**

-   `gas.length == n`
-   `cost.length == n`
-   `1 <= n <= 105`
-   `0 <= gas[i], cost[i] <= 104`

### My Solution

```java
public int canCompleteCircuit(int[] gas, int[] cost){
    int sumGas = 0;
    for (int i = 0; i < gas.length; i++){
        if (sumGas + gas[i] - cost[i] < 0){
            sumGas = 0;
            continue;
        } else {
            int j = 0;
            while(j < gas.length && sumGas >= 0){
                int idx = (i + j) % gas.length;
                sumGas += gas[idx];
                sumGas -= cost[idx];
                j++;
            }
            return i;
        }
    }
    return -1;
}
```

*   The solution is correct but exceed the time limit due to nested loop
*   But the idea is similar to the standard solution, just need a little improvement

### Standard Solution

#### Solution #1 One Pass

*   The first idea is to check every single station :
    -   Choose the station as starting point.
    -   Perform the road trip and check how much gas we have in tank at each station.
    -   That means $\mathcal{O}(N^2)$ time complexity, and for sure one could do better(**My solution**).
*   The second fact could be generalized. Let's introduce `curr_tank` variable to track the current amount of gas in the tank. If at some station `curr_tank` is less than `0`, that means that one couldn't reach this station.

*   **Algorithm**

    Now the algorithm is straightforward :

    1.  Initiate `total_tank` and `curr_tank` as zero, and choose station `0` as a starting station.
    2.  Iterate over all stations :
        -   Update `total_tank` and `curr_tank` at each step, by adding `gas[i]` and subtracting `cost[i]`.
        -   If `curr_tank < 0` at `i + 1` station, make `i + 1` station a new starting point and reset `curr_tank = 0` to start with an empty tank.
    3.  Return `-1` if `total_tank < 0` and `starting station` otherwise.

```java
class Solution {
  public int canCompleteCircuit(int[] gas, int[] cost) {
    int n = gas.length;

    int total_tank = 0;
    int curr_tank = 0;
    int starting_station = 0;
    for (int i = 0; i < n; ++i) {
      total_tank += gas[i] - cost[i];
      curr_tank += gas[i] - cost[i];
      // If one couldn't get here,
      if (curr_tank < 0) {
        // Pick up the next station as the starting one.
        starting_station = i + 1;
        // Start with an empty tank.
        curr_tank = 0;
      }
    }
    return total_tank >= 0 ? starting_station : -1;
    // go through the loop and total > 0 means if we start at the start point, the gas would be enough         
    // for the whole trip
  }
}
```

```java
// similar idea, but a simpler algorithm design
public int canCompleteCircuit(int[] gas, int[] cost){
    int len = gas.length;
    int spare = 0;
    int minSpare = Integer.MAX_VALUE;
    int minIndex = 0;
    
    for (int i = 0; i < len; i++){
        spare += gas[i] - cost[i];
        if (spare < minSpare){
            minSpare = spare;
            minIndex = i;
        }
    }
    return spare < 0 ? -1 : (minIndex + 1) % len;
    // you need to draw a line plot of gas in the tank, 
    // the lowest point in the lineplot is the start point
}
```

*   Time complexity : $ \mathcal{O}(N)$ since there is only one loop over all stations here.
*   Space complexity : $\mathcal{O}(1)$ since it's a constant space solution.

## Longest Common Prefix(Easy #14)

**Question**: Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

**Example 1:**

```
Input: strs = ["flower","flow","flight"]
Output: "fl"
```

**Example 2:**

```
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

**Constraints:**

-   `1 <= strs.length <= 200`
-   `0 <= strs[i].length <= 200`
-   `strs[i]` consists of only lower-case English letters.

### Standard Solution

#### Solution #1 Vertical Scanning

*   Compare characters from top to bottom on the same column

<img src="https://assets.leetcode-cn.com/solution-static/14/14_fig2.png" alt="fig2" style="zoom:33%;" />

```java
public String longestCommonPrefix(String[] strs){
    if (strs == null || strs.length == 0){
        return "";
    }
    int length = strs[0].length();
    int count = strs.length;
    for (int i = 0; i < length; i++){
        char c = strs[0].charAt(i);
        for (int j = 1; j < count; j++){
            if (i == strs[j].length() || strs[j].charAt(i) != c){
                return strs[0].substring(0, i);
            }
        }
    }
    return strs[0];
}
```

*   Time complexity: $O(mn)$, m is the average length of the strs, and n is the number of string. 
*   Space complexity: $O(1)$, other extra space complexity is constant

#### Solution #2 Horizontal Scan

*   Each time traverse the substring inside the string and update the prefix
*   When the prefix is empty, just return empty string

<img src="https://assets.leetcode-cn.com/solution-static/14/14_fig1.png" alt="fig1" style="zoom:33%;" />

```java
public String longestCommonPrefix(String[] strs){
    if (strs == null || strs.length == 0){
        return "";
    }
    String prefix = strs[0];
    int count = strs.length;
    for (int i = 1; i < count; i++){
        prefix = longestCommonPrefix(prefix, strs[i]);
        if (prefix.length() == 0){
            break;
        }
    }
    return prefix;
}
public String longestCommonPrefix(String str1, String str2){
    int length = Math.min(str1.length(), str2.length());
    int index = 0;
    while(index < length && str1.charAt(index) == str2.charAt(index)){
        index++;
    }
    return str1.substring(0, index);
}
```

*   Time complexity: $O(mn)$, m is the average length of the strs, and n is the number of string. 
*   Space complexity: $O(1)$, other extra space complexity is constant

#### Solution #3 Binary Search

*   Find the shortest string in the strs, and use binary search to find the length of the prefix to shorten the range.

<img src="https://assets.leetcode-cn.com/solution-static/14/14_fig4.png" alt="fig4" style="zoom: 33%;" />

```java
public String longestCommonPrefix(String[] strs){
    if (strs == null || strs.length == 0) return "";
    int minLength = Integer.MAX_VALUE;
    for (String str : strs){
        minLength = Math.min(minLength, str.length());
    }
    int low = 0, high = minLength;
    while(low < high){
        int mid = (high - low + 1) / 2 + low;
        if (isCommonPrefix(strs, mid)){
            low = mid;
        } else {
            high = mid - 1;
        }
    }
    return strs[0].substring(0, low);
}

public boolean isCommonPrefix(String[] strs, int length){
    String str0 = strs[0].substring(0, length);
    int count = strs.length;
    for (int i = 1; i < count; i++){
        String str = strs[i];
        for (int j = 0; j < length; j++){
            if (str0.charAt(j) != str.charAt(j)){
                return false;
            }
        }
    }
}
```

*   Time complexity: $O(mn\log m)$. M is the shortest length of string, n is the number of strings. Binary search operation is $O(\log m)$, each time iteration need to compare $mn$ characters.
*   Space complexity: $O(1)$.

## Sequential Digits(Medium #1291)

**Question**: An integer has *sequential digits* if and only if each digit in the number is one more than the previous digit.

Return a **sorted** list of all the integers in the range `[low, high]` inclusive that have sequential digits.

**Example 1:**

```
Input: low = 100, high = 300
Output: [123,234]
```

**Example 2:**

```
Input: low = 1000, high = 13000
Output: [1234,2345,3456,4567,5678,6789,12345]
```

**Constraints:**

-   `10 <= low <= high <= 10^9`

### My Solution

```java
public List<Integer> sequentialDigits(int low, int high) {
    /**The digits can only be 1,2,3,4,5,6,7,8,9, use slicing window of the 9 digits**/
    String range = "123456789";

    // the we need to know the low and high has how many digits
    int lowBound = findDigit(low), upperBound = findDigit(high);

    // the window size is same as lowBound, but could not larger than upperBound
    int winSize = lowBound;
    List<Integer> seq = new ArrayList<Integer>();
    for(int length = lowBound; length < upperBound + 1; length++){
        for(int start = 0; start < 10 - length; start++){
            int val = Integer.valueOf(range.substring(start, start + length));
            if (val > high) break;
            if (val < low) continue;
            seq.add(Integer.valueOf(range.substring(start, start + length)));
        }
    }
    return seq;
}

public int findDigit(int num){
    int digit = 0;
    while(num > 0){
        num /= 10;
        digit++;
    }
    return digit;
}
```

*   Actually a good solution with fast speed and low space complexity

### Standard Solution

#### Solution #1 Sliding Window

![diff](https://leetcode.com/problems/sequential-digits/Figures/1291/sliding.png)

*   Same idea as my solution

*   **Algorithm**: 
    *   Initialize sample string "123456789". This string contains all integers that have sequential digits as substrings. Let's implement sliding window algorithm to generate them.
    *   Iterate over all possible string lengths: from the length of `low` to the length of `high`.
        *   For each length iterate over all possible start indexes: from `0` to `10 - length`.
            *   Construct the number from digits inside the sliding window of current length.
            *   Add this number in the output list `nums`, if it's greater than `low` and less than `high`.
    *   Return `nums`.

```java
public List<Integer> sequentialDigits(int low, int high){
    String sample = "123456789";
    int n = 10;
    List<Integer> nums = new ArrayList();
    
    int lowLen = String.valueOf(low).length();
    int highLen = String.valueOf(high).length();
    for (int length = lowLen; length < highLen + 1; length++){
        for (int start = 0; start < n - length; start++){
            int num = Integer.parseInt(sample.substring(start, start + length));
            if (num >= low && num <= high) nums.add(num);
        }
    }
    return nums;
}
```

-   Time complexity: $\mathcal{O}(1)$. Length of sample string is 9, and lengths of low and high are between 2 and 9. Hence the nested loops are executed no more than $8 \times 8 = 64$ times.
-   Space complexity: $\mathcal{O}(1)$ to keep not more than 36 integers with sequential digits.

## Two Sum II - Input Array is Sorted(Easy 167)

**Question**: Given a **1-indexed** array of integers `numbers` that is already ***sorted in non-decreasing order\***, find two numbers such that they add up to a specific `target` number. Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return *the indices of the two numbers,* `index1` *and* `index2`*, **added by one** as an integer array* `[index1, index2]` *of length 2.*

The tests are generated such that there is **exactly one solution**. You **may not** use the same element twice.

**Example 1:**

```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

**Example 2:**

```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
```

**Example 3:**

```
Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
```

**Constraints:**

-   `2 <= numbers.length <= 3 * 104`
-   `-1000 <= numbers[i] <= 1000`
-   `numbers` is sorted in **non-decreasing order**.
-   `-1000 <= target <= 1000`
-   The tests are generated such that there is **exactly one solution**.

### My Solution

*   Two pointer, low pointer at the beginning, high pointer at the end
*   While smaller than target, low pointer + 1, if larger than target high pointer -1

```java
public int[] twoSum(int[] numbers, int target){
    int low = 0, high = numbers.length - 1;
    int[] res = new int[2];
    while(low < high){
        int sum = numbers[low] + numbers[high];
        if (sum > target){
            high--;
        }
        else if (sum < target){
            low++;
        }
        else {
            res[0] = low;
            res[1] = high;
            break;
        }
    }
    return res;
}
```

*   Time complexity: $O(n)$. The input array is traversed at most once. Thus the time complexity is O(n)*O*(*n*).
*   Space complexity: $O(1)$. We only use additional space to store two indices and the sum, so the space complexity is $O(1)$.
*   Same as standard solution

## Max Consecutive Ones(Easy #485)

**Question**: Given a binary array `nums`, return *the maximum number of consecutive* `1`*'s in the array*.

**Example 1:**

```
Input: nums = [1,1,0,1,1,1]
Output: 3
Explanation: The first two digits or the last three digits are consecutive 1s. The maximum number of consecutive 1s is 3.
```

**Example 2:**

```
Input: nums = [1,0,1,1,0,1]
Output: 2
```

**Constraints:**

-   `1 <= nums.length <= 105`
-   `nums[i]` is either `0` or `1`.

### My Solution

*   Loop through the array and record 1 number
*   If 0, we reset the calculation and record the max number of consecutive 1

```java
public int findMaxConsecutiveOnes(int[] nums){
    int count = 0, max = 0, i = 0;
    while(i < nums.length){
        if (nums[i] == 1){
            count++;
            max = Math.max(max, count);
        }
        else {
            count = 0;
        }
        i++;
    }
    return max;
}
```

*   Same as the standard solution
*   Time Complexity: $O(N)$, where N is the number of elements in the array.
*   Space Complexity: $O(1)$. We do not use any extra space.

## Minimum Size Subarray Sum(Medium #209)

**Question**: Given an array of positive integers `nums` and a positive integer `target`, return the minimal length of a **contiguous subarray** `[numsl, numsl+1, ..., numsr-1, numsr]` of which the sum is greater than or equal to `target`. If there is no such subarray, return `0` instead.

**Example 1:**

```
Input: target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: The subarray [4,3] has the minimal length under the problem constraint.
```

**Example 2:**

```
Input: target = 4, nums = [1,4,4]
Output: 1
```

**Example 3:**

```
Input: target = 11, nums = [1,1,1,1,1,1,1,1]
Output: 0 
```

**Constraints:**

-   `1 <= target <= 109`
-   `1 <= nums.length <= 105`
-   `1 <= nums[i] <= 105`

### My Solution

*   Two pointer, 1 start, 1 end, use helper function to sum up
*   Compare the sum, if exceed the target, shorten the range of the two pointer, use Math.min to compare the range

```java
public int minSubArrayLen(int target, int[] nums){
    int range = nums.length;
    int min = Integer.MAX_VALUE;
    boolean pass = true;
    while(pass){
        pass = findEqualOrLargerTarget(nums, range, target);
        if (!pass) break;
        min = Math.min(min, range);
        range--;
    }
   return min != Integer.MAX_VALUE ? min : 0;
}
public boolean findEqualOrLargerTarget(int[] nums, int range, int target){
    int low = 0, high = low + range;
    while (low <= nums.length - range){
        int findSum = findSum(Arrays.copyOfRange(nums, low, high));
        if (findSum >= target){
            return true;
        }
        low++;
        high++;
    }
    return false;
}
public int findSum(int[] nums){
    int sum = 0;
    int i = 0;
    while(i < nums.length){
        sum += nums[i];
        i++;
    }
    return sum;
}
```

*   It can solve the problem, but it is a brute force method so it exceed the time limit

### Standard Solution

*   For subarray, usually we have the following methods
    *   Sliding window ($O(N)$) - similar to queue
    *   Sum of prefix (need extra $O(N)$ for space)
    *   Binary search(usually $O(N \log N)$)

#### Solution #1 Two Pointer + Sliding Window

*   Keep two pointers left and right to maintain the range of the subarray, sum to the right pointer
*   Start from the maximum length of range
*   When sum is larger than the target:
    *   Move to right and remove the left number when adding to the sum
    *   Update the minimum 
    *   Until cannot delete any left number, we then continue to add the right number

```java
public int MinSubArrayLen(int s, int[] nums) {
	int minLen = nums.length +1;
	int sum = 0;
	int left = 0;
	for (int right = 0; right < nums.length; right++){
		sum += nums[right];
		while (sum >= s && left <= right){
			//decrease till get smallest subarray possible in current window
			minLen = Math.min(minLen, right -left + 1);
			sum -= nums[left++];
    	}
	}
    return minLen == nums.length + 1 ? 0 : minLen;    
}
```

*   Time complexity: $O(n)$. Single iteration of $O(n)$.
    -   Each element can be visited atmost twice, once by the right pointer(i) and (atmost)once by the $\text{left}$ pointer.
*   Space complexity: $O(1)$ extra space. Only constant space required for $\text{left}$, $\text{sum}$, $\text{ans}$ and i.

#### Solution #2 Binary Search + Prefix Sum

*   Prefix sum: sum[i] = sum from nums[0] to nums[i-1]
*   Each element need to be **positive**
*   Use binary search to find the upper bound so that sums[bound] - sums[i - 1] >= target
*   We can use `Arrays.binarySearch` to help us to find the bound
    *   Need to be applied on a sorted array, the sums array is cumulative so it is ok

```java
class Solution {
    public int minSubArrayLen(int s, int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        int[] sums = new int[n + 1]; 
        // 为了方便计算，令 size = n + 1 
        // sums[0] = 0 意味着前 0 个元素的前缀和为 0
        // sums[1] = A[0] 前 1 个元素的前缀和为 A[0]
        // 以此类推
        for (int i = 1; i <= n; i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }
        for (int i = 1; i <= n; i++) {
            int target = s + sums[i - 1];
            int bound = Arrays.binarySearch(sums, target);
            if (bound < 0) {//if not found, make it to 0
                bound = -bound - 1;
            }
            if (bound <= n) {
                ans = Math.min(ans, bound - (i - 1));
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }
}

作者：LeetCode-Solution
链接：https://leetcode-cn.com/problems/minimum-size-subarray-sum/solution/chang-du-zui-xiao-de-zi-shu-zu-by-leetcode-solutio/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

*   Time complexity: $O(N \log N)$
*   Space complexity: $O(N)$

## Rotate Array(Medium #189)

**Question**: Given an array, rotate the array to the right by `k` steps, where `k` is non-negative.

**Example 1:**

```
Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
```

**Example 2:**

```
Input: nums = [-1,-100,3,99], k = 2
Output: [3,99,-1,-100]
Explanation: 
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
```

**Constraints:**

-   `1 <= nums.length <= 105`
-   `-231 <= nums[i] <= 231 - 1`
-   `0 <= k <= 105`

### My Solution

```java
public void rotate(int[] nums, int k){
    k = k % nums.length; // in case the k is longer than the length of nums
    int len = nums.length;
    // add another array to store the value
    int[] al = new int[nums.length];
    for (int i = len - k, j = 0; i < len; i++, j++){
        al[j] = nums[i];
    }
    for (int i = 0, j = k; i < len - k; i++, j++){
        al[j] = nums[i];
    }
    for (int i = 0; i < len; i++){
        nums[i] = al[i];
    }
}
```

*   Time complexity: $O(N)$. One pass is used to put the numbers in the new array.
*   Space complexity: $O(N)$. Another array of the same size is used.

### Standard Solution

#### Solution #1 Using Extra Array

*   Similar to my solution but a better way
*   We can see that each element move k steps, the number at index i*i* in the original array is placed at the index $(i + k) \% \text{ length of array}$

```java
public void rotate(int[] nums, int k){
    int[] a = new int[nums.length];
    for (int i = 0; i < nums.length; i++){
        a[(i + k) % nums.length] = nums[i];
    }
    for (int i = 0; i < nums.length; i++){
        nums[i] = a[i];
    }
}
```

#### Solution #2 Using Reverse

*   Reverse the elements, and reverse the range of $[0,k\% (n−1)]$ and $[k\% n, n-1]$ elements

```
Original List                   : 1 2 3 4 5 6 7
After reversing all numbers     : 7 6 5 4 3 2 1
After reversing first k numbers : 5 6 7 4 3 2 1
After revering last n-k numbers : 5 6 7 1 2 3 4 --> Result
```

```java
public void rotate(int[] nums, int k){
    k %= nums.length;
    reverse(nums, 0, nums.length - 1);
    reverse(nums, 0, k - 1);
    reverse(nums, k, nums.length - 1);
}

public void reverse(int[] nums, int start, int end){
    while(start < end){
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start += 1;
        end -= 1;
    }
}
```

-   Time complexity: $\mathcal{O}(n)$. n elements are reversed a total of three times.
-   Space complexity: $\mathcal{O}(1)$. No extra space is used.

## Pascal's Triangle II(Easy #119)

**Question**: Given an integer `rowIndex`, return the `rowIndexth` (**0-indexed**) row of the **Pascal's triangle**.

In **Pascal's triangle**, each number is the sum of the two numbers directly above it as shown:

![img](https://upload.wikimedia.org/wikipedia/commons/0/0d/PascalTriangleAnimated2.gif)

 

**Example 1:**

```
Input: rowIndex = 3
Output: [1,3,3,1]
```

**Example 2:**

```
Input: rowIndex = 0
Output: [1]
```

**Example 3:**

```
Input: rowIndex = 1
Output: [1,1]
```

**Constraints:**

-   `0 <= rowIndex <= 33`

### My Solution

```java
public List<Integer> getRow(int rowIndex){
    int[][] triangle = new int[rowIndex + 1][rowIndex + 1];
    triangle[0][0] = 1;
    List<Integer> res = new ArrayList<>();
    
    for (int i = 1; i < rowIndex + 1; i++){
        triangle[i][0] = 1;
        for (int j = 1; j < rowIndex + 1; j++){
            triangle[i][j] = triangle[i - 1][j] + triangle[i - 1][j - 1];
        }
    }
    for (int k = 0; k < rowIndex + 1; k++){
        res.add(triangle[rowIndex][k]);
    }
    return res;
}
```

*   Use 2D array to store the value
*   Time complexity: $O(k^2)$
*   Space complexity: $O(k^2)$

### Standard Solution

#### Solution #1 Math Sum

*   We can regard it as a permutation and combination, the $m^{th}$ number in the $n^{th}$ row is $C(n, m)$: choose m from n
*   Every number is the sum of last row corresponding numbers, as in combination: $C_n^i=C_{n−1}^i+C_{n−1}^{i−1}$
*   The implementation is similar to my solution but a better way

```java
class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> pre = new ArrayList<Integer>();
        for (int i = 0; i <= rowIndex; ++i) {
            List<Integer> cur = new ArrayList<Integer>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    cur.add(1);
                } else {
                    cur.add(pre.get(j - 1) + pre.get(j));
                }
            }
            pre = cur;
        }
        return pre;
    }
}

作者：LeetCode-Solution
链接：https://leetcode-cn.com/problems/pascals-triangle-ii/solution/yang-hui-san-jiao-ii-by-leetcode-solutio-shuk/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

*   We can improve the code to only use 1 arraylist, make changes to the current array list

```java
public List<Integer> getRow(int rowIndex){
    List<Integer> row = new ArrayList<Integer>();
    row.add(1);
    for (int i = 1; i <= rowIndex; i++){
        row.add(0);
        for (int j = i; j > 0; --j){
            row.set(j, row.get(j) + row.get(j - 1));
        }
    }
    return row;
}
```

*   Time complexity: $O(k^2)$
*   Space complexity: $O(1)$, we don't consider the space occupy by the return

#### Solution #2 Linear Inference

*   Apply math we can learn the relationship between values on the same row
*   Since $C_n^m=\frac{m!(n−m)!}{n!}$, we can have $C_n^m=C_n^{m−1}×\frac{m}{n−m+1}$
*   We can even improve the codes of the last solution, use linear time to find combination on the $n^{th}$ row

```java
public List<Integer> getRow(int rowIndex){
    List<Integer> row = new ArrayList<Integer>();
    row.add(1);
    for (int i = 1; i <= rowIndex; i++){
        row.add((int)((long) row.get(i - 1) * (rowIndex - i + 1) / i));
    }
    return row;
}
```

*   Time complexity: $O(k)$
*   Space complexity: $O(1)$

## Reverse Words in a String(Medium #151)

**Question**: Given an input string `s`, reverse the order of the **words**.

A **word** is defined as a sequence of non-space characters. The **words** in `s` will be separated by at least one space.

Return *a string of the words in reverse order concatenated by a single space.*

**Note** that `s` may contain leading or trailing spaces or multiple spaces between two words. The returned string should only have a single space separating the words. Do not include any extra spaces.

**Example 1:**

```
Input: s = "the sky is blue"
Output: "blue is sky the"
```

**Example 2:**

```
Input: s = "  hello world  "
Output: "world hello"
Explanation: Your reversed string should not contain leading or trailing spaces.
```

**Example 3:**

```
Input: s = "a good   example"
Output: "example good a"
Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
```

**Constraints:**

-   `1 <= s.length <= 104`
-   `s` contains English letters (upper-case and lower-case), digits, and spaces `' '`.
-   There is **at least one** word in `s`.

### My Solution

```java
public String reverseWords(String s){
    public String reverseWords(String s) {
        s = s.trim();//remove space at the beginning and end of the string
        String[] words = s.split(" ");
        int num = words.length;
        StringBuilder reverse = new StringBuilder();
        for (int i = num - 1; i >= 0; i--){
            if (words[i].equals("")) {
                continue;
            }
            else {
                reverse.append(words[i]);
                if (i != 0) reverse.append(" ");
            }
        }
        return reverse.toString();
    }
}
```

### Standard Solution

#### Solution #1 Built-in Function

*   Same idea as my solution but using built-in function

```java
public String reverseWords(String s){
    // remove space at the start and end
    s = s.trim();
    // use empty char as the split
    List<String> wordList = Arrays.asList(s.split("\\s+"));
    Collections.reverse(wordList);
    return String.join(" ", wordList);
}
```

*   Time complexity: $O(N)$, n is the length of the string
*   Space complexity: $O(N)$

#### Solution #2 Deque

*   Deque sipport that insert from head and tail

<img src="https://pic.leetcode-cn.com/Figures/151/deque2.png" alt="fig" style="zoom: 33%;" />

```java
public String reverseWords(String s) {
    int left = 0, right = s.length() - 1;
    // 去掉字符串开头的空白字符
    while (left <= right && s.charAt(left) == ' ') {
        ++left;
    }

    // 去掉字符串末尾的空白字符
    while (left <= right && s.charAt(right) == ' ') {
        --right;
    }

    Deque<String> d = new ArrayDeque<String>();
    StringBuilder word = new StringBuilder();

    while (left <= right) {
        char c = s.charAt(left);
        if ((word.length() != 0) && (c == ' ')) {
            // 将单词 push 到队列的头部
            d.offerFirst(word.toString());
            word.setLength(0);
        } else if (c != ' ') {
            word.append(c);
        }
        ++left;
    }
    d.offerFirst(word.toString());

    return String.join(" ", d);
}

作者：LeetCode-Solution
链接：https://leetcode-cn.com/problems/reverse-words-in-a-string/solution/fan-zhuan-zi-fu-chuan-li-de-dan-ci-by-leetcode-sol/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```

*   Time complexity: $O(N)$, n is the length of the string
*   Space complexity: $O(N)$

## Reverse Words in a String III(Easy #557)

**Question**: Given a string `s`, reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

**Example 1:**

```
Input: s = "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"
```

**Example 2:**

```
Input: s = "God Ding"
Output: "doG gniD"
```

**Constraints:**

-   `1 <= s.length <= 5 * 104`
-   `s` contains printable **ASCII** characters.
-   `s` does not contain any leading or trailing spaces.
-   There is **at least one** word in `s`.
-   All the words in `s` are separated by a single space.

### My Solution

```java
public String reverseWords(String s){
    List<String> wordList = Arrays.asList(s.split("\\s+"));
    Collections.reverse(wordList);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < wordList.size(); i++){
        sb.append(wordList.get(i));
        if (i < wordList.size() - 1) sb.append(" ");
    }
    return sb.reverse().toString();
}
```

### Standard Solution

#### Solution #1 Simple solution

*   Similar to my solution

```java
public String reverseWords(String s){
    String words[] = s.split(" ");
    StringBuilder res = new StringBuilder();
    for (String word : words){
        res.append(new StringBuffer(word).reverse().toString() + " ");
    }
    return res.toString().trim();
}
```

*   Time complexity : $O(n)$. where n is the length of the string.
*   Space complexity : $O(n)$. res of size n is used.

## Remove Duplicates from Sorted Array(Easy #26)

**Question**: Given an integer array `nums` sorted in **non-decreasing order**, remove the duplicates **in-place** such that each unique element appears only **once**. The **relative order** of the elements should be kept the **same**.

Since it is impossible to change the length of the array in some languages, you must instead have the result be placed in the **first part** of the array `nums`. More formally, if there are `k` elements after removing the duplicates, then the first `k` elements of `nums` should hold the final result. It does not matter what you leave beyond the first `k` elements.

Return `k` *after placing the final result in the first* `k` *slots of* `nums`.

Do **not** allocate extra space for another array. You must do this by **modifying the input array in-place** with O(1) extra memory.

**Custom Judge:**

The judge will test your solution with the following code:

```
int[] nums = [...]; // Input array
int[] expectedNums = [...]; // The expected answer with correct length

int k = removeDuplicates(nums); // Calls your implementation

assert k == expectedNums.length;
for (int i = 0; i < k; i++) {
    assert nums[i] == expectedNums[i];
}
```

If all assertions pass, then your solution will be **accepted**.

**Example 1:**

```
Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Example 2:**

```
Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
```

**Constraints:**

-   `0 <= nums.length <= 3 * 104`
-   `-100 <= nums[i] <= 100`
-   `nums` is sorted in **non-decreasing** order.

### My Solution

```java
public int removeDuplicates(int[] nums){
    int slow = 0, fast; // we use two pointers to locate new number
    if (nums.length == 0) return 0;
    int prev = nums[slow];
    for (fast = slow; fast < nums.length; fast++){
        int current = nums[fast];
        if (current != prev){
            slow++;
            nums[slow] = current;
        }
        prev = current;
    }
    return slow + 1;
}
```

*   A pretty fast solution

### Standard Solution

#### Solution #1 Two Pointers

*   Same as my solution but look cleaner

```java
public int removeDuplicates(int[] nums){
    if (nums.length == 0) return 0;
    int i = 0;
    for (int j = 1; j < nums.length; j++){
        if (nums[j] != nums[i]){
            i++;
            nums[i] = nums[j];
        }
    }
    return i + 1;
}
```

-   Time complextiy : $O(n)$. Assume that n is the length of array. Each of i and j traverses at most n*n* steps.
-   Space complexity : $O(1)$.

## Move Zeros(Easy #283)

**Question**: Given an integer array `nums`, move all `0`'s to the end of it while maintaining the relative order of the non-zero elements.

**Note** that you must do this in-place without making a copy of the array.

**Example 1:**

```
Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
```

**Example 2:**

```
Input: nums = [0]
Output: [0]
```

**Constraints:**

-   `1 <= nums.length <= 104`
-   `-231 <= nums[i] <= 231 - 1`

### My Solution

```java
public void moveZeroes(int[] nums) {
    // set up two slow and fast runners as two pointers
    int slow = Integer.MAX_VALUE, fast;
    for (fast = 0; fast < nums.length; fast++){
        if (nums[fast] != 0 && slow != Integer.MAX_VALUE){
            swap(nums, slow, fast);
            slow++;
        } else if (nums[fast] == 0 && slow == Integer.MAX_VALUE) slow = fast;
    }
}
// swap the two number of the two pointers
public void swap(int[] nums, int i, int j){
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

### Standard Solution

#### Solution #1 Replace Fast Runner Number with 0

*   Similar to my solution but without swap, a faster one

```java
public void moveZeros(int[] nums){
    int n = nums.length;
    int j = -1;//slow runner
    int i = 0;//fast runner
    
    while (i < n){
        // first occurence of 0
        if (nums[i] == 0 && j == -1) j = i;
        // has zero in the array before a valid digit, hence swap
        else if (nums[i] != 0 && j != -1){
            nums[j] = nums[i];//move element to front
            nums[i] = 0;
            j++;
        }
        i++;
    }
}
```

#### Solution #2 Two Pointers

*   Left of the left pointers are non-zeros
*   Between left pointer and right pointer are all zeros

```java
public void moveZeros(int[] nums){
    int n = nums.length, left = 0, right = 0;
    while(right < n){
        if (nums[right] != 0){
            swap(nums, left, right);
            left++;
        }
        right++;
    }
}
public void swap(int[] nums, int left, int right){
    int temp = nums[left];
    nums[left] = nums[right];
    nums[right] = temp;
}
```

*   Time complexity: $O(N)$
*   Space complexity: $O(1)$                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

## Valid Anagram (Easy #242)

**Question**: Given two strings `s` and `t`, return `true` *if* `t` *is an anagram of* `s`*, and* `false` *otherwise*.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

**Example 1:**

```
Input: s = "anagram", t = "nagaram"
Output: true
```

**Example 2:**

```
Input: s = "rat", t = "car"
Output: false
```

**Constraints:**

-   `1 <= s.length, t.length <= 5 * 104`
-   `s` and `t` consist of lowercase English letters.

**Follow up:** What if the inputs contain Unicode characters? How would you adapt your solution to such a case?

### My Solution

```java
// sum: return if is anagram
public boolean isAnagram(String s, String t) {
    // 1. compare length of the string, if not same length, false
    if (s.length() != t.length()) return false;

    // 2. use an array to store the occurence of letter in 1 string
    int[] occurence = new int[26]; // lower cases
    char[] sArray = s.toCharArray();
    char[] tArray = t.toCharArray();

    for (char ch : sArray){
        int loc = ch - 'a';
        occurence[loc]++;
    }

    // 3. subtract the occrence in the array base on 2 string, if every letter go back to 0, true
    for (char ch : tArray){
        int loc = ch - 'a';
        occurence[loc]--;
    }
    for (int count : occurence){
        if (count != 0){
            return false;
        }
    }
    return true;
}
```

*   The result is pretty good with time complexity since it should be $O(n)$, but the space complexity is $O(1)$ in a larger case since it can be further simplified.

### Standard Solution

#### Solution #1 Sorting

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
        return false;
    }
    char[] str1 = s.toCharArray();
    char[] str2 = t.toCharArray();
    Arrays.sort(str1);
    Arrays.sort(str2);
    return Arrays.equals(str1, str2);
}
```

-   Time complexity: $O(n \log n)$. Assume that n is the length of s, sorting costs $O(n \log n)$ and comparing two strings costs $O(n)$. Sorting time dominates, and the overall time complexity is $O(n \log n)$.
-   Space complexity: $O(1)$. Space depends on the sorting implementation which, usually, costs $O(1)$ auxiliary space if `heapsort` is used. Note that in Java, `toCharArray()` makes a copy of the string so it costs $O(n)$ extra space, but we ignore this for complexity analysis because:
    -   It is a language-dependent detail.
    -   It depends on how the function is designed. For example, the function parameter types can be changed to `char[]`.

#### Solution #2 Frequency Counter

*   Same as my solution but simplifier

```java
public boolean isAnagram(String s, String t){
    if (s.length() != t.length()){
        return false;
    }
    int[] counter = new int[26];
    for (int i = 0; i < s.length(); i++){
        counter[s.charAt(i) - 'a']++;
        counter[t.charAt(i) - 'a']--;
    }
    for (int count : counter){
        if (count != 0){
            return false;
        }
    }
    return true;
}
```

-   Time complexity: $O(n)$. Time complexity is $O(n)$ because accessing the counter table is a constant time operation.
-   Space complexity: $O(1)$. Although we do use extra space, the space complexity is $O(1)$ because the table's size stays constant no matter how large n is.

#### Solution #3 Hashmap (Follow-up)

```java
public boolean isAnagram(String s, String t) {
    HashMap<Character,Integer> smap=new HashMap<>();
    int sl=s.length();
    int tl=t.length();
    if(sl!=tl){return false;}
    for(int i=0;i<sl;i++){
        smap.put(s.charAt(i),smap.getOrDefault(s.charAt(i),0)+1);
        smap.put(t.charAt(i),smap.getOrDefault(t.charAt(i),0)-1);
    }
    for(char c:smap.keySet()){
        if(smap.get(c)!=0){return false;}
    }
    return true;
}
```

*   The hashtable should handle all Unicode characters. The time complexity is still $O(n)$ while the space complexity becomes $O(n)$ because of the hashtable.

## Group Anagrams (Medium #49)

**Question**: Given an array of strings `strs`, group **the anagrams** together. You can return the answer in **any order**.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

**Example 1:**

```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Example 2:**

```
Input: strs = [""]
Output: [[""]]
```

**Example 3:**

```
Input: strs = ["a"]
Output: [["a"]]
```

**Constraints:**

-   `1 <= strs.length <= 104`
-   `0 <= strs[i].length <= 100`
-   `strs[i]` consists of lowercase English letters.

### My Solution

*   Tricks in the problem
    *   Cannot use `int[]` as key of the hashmap, it seems like it only compare with the address of array
    *   Cannot use `int[]` converted string as the key of the hashmap. It may confuse some of the cases. To distinguish the keys, add `#` between each number of the key

```java
public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> res = new ArrayList<>();
    // pattern (int[26]), list of strings, to count the occurence of letters

    Map<String, List<String>> pattern = new HashMap<>();
    for (String str : strs){
        int[] countMap = new int[26];
        for (char ch : str.toCharArray()){
            countMap[ch - 'a']++;
        }
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 26; i++){
            key.append("#");
            key.append(countMap[i]);
        }
        String keyString = key.toString();
        if (!pattern.containsKey(keyString)){
            pattern.put(keyString, new ArrayList<>());
        }
        // meet the pattern, they should be grouped and put in result list
        pattern.get(keyString).add(str);
    }

    // loop the map and store the string list to result list
    for (Map.Entry<String, List<String>> entry : pattern.entrySet()){
        res.add(entry.getValue());
    }
    return res;
}
```

### Standard Solution

#### Solution #1 Categorize by Count

*   Idea is similar to my method but simpler

```java
public List<List<String>> groupAnagrams(String[] strs) {
    if (strs.length == 0) return new ArrayList();
    Map<String, List> ans = new HashMap<String, List>();
    int[] count = new int[26];
    for (String s : strs) {
        Arrays.fill(count, 0);
        for (char c : s.toCharArray()) count[c - 'a']++;

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 26; i++) {
            sb.append('#');
            sb.append(count[i]);
        }
        String key = sb.toString();
        if (!ans.containsKey(key)) ans.put(key, new ArrayList());
        ans.get(key).add(s);
    }
    return new ArrayList(ans.values());
}
```

-   Time Complexity: $O(NK)$, where N is the length of `strs`, and K is the maximum length of a string in `strs`. Counting each string is linear in the size of the string, and we count every string.
-   Space Complexity: $O(NK)$, the total information content stored in `ans`.

#### Solution #2 Sort the List

```java
public List<List<String>> groupAnagrams(String[] strs) {
    if (strs.length == 0) return new ArrayList();
    Map<String, List> ans = new HashMap<String, List>();
    for (String s : strs) {
        char[] ca = s.toCharArray();
        Arrays.sort(ca);
        String key = String.valueOf(ca);
        if (!ans.containsKey(key)) ans.put(key, new ArrayList());
        ans.get(key).add(s);
    }
    return new ArrayList(ans.values());
}
```

-   Time Complexity: $O(NK \log K)$, where N is the length of `strs`, and K is the maximum length of a string in `strs`. The outer loop has complexity $O(N)$ as we iterate through each string. Then, we sort each string in $O(K \log K)$ time.
-   Space Complexity: $O(NK)$, the total information content stored in `ans`.

## Contains Duplicate (Easy #217)

**Question**: Given an integer array `nums`, return `true` if any value appears **at least twice** in the array, and return `false` if every element is distinct.

**Example 1:**

```
Input: nums = [1,2,3,1]
Output: true
```

**Example 2:**

```
Input: nums = [1,2,3,4]
Output: false
```

**Example 3:**

```
Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true
```

**Constraints:**

-   `1 <= nums.length <= 105`
-   `-109 <= nums[i] <= 109`

### My Solution

```java
public boolean containsDuplicate(int[] nums) {
    // set: cannot duplicate
    Set<Integer> numSet = new HashSet<Integer>();
    for (int num : nums){
        if (numSet.contains(num)){
            return true;
        }
        numSet.add(num);
    }
    return false;
}
```

*   Time and space complexity should be both $O(n)$

### Standard Solution

#### Solution #1 Sorting

```java
public boolean containsDuplicate(int[] nums) {
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 1; ++i) {
        if (nums[i] == nums[i + 1]) return true;
    }
    return false;
}
```

-   Time complexity: $O(n \log n)$. Sorting is $O(n \log n)$ and the sweeping is $O(n)$. The entire algorithm is dominated by the sorting step, which is $O(n \log n)$.
-   Space complexity: $O(1)$. Space depends on the sorting implementation which, usually, costs $O(1)$ auxiliary space if `heapsort` is used.

#### Solution #2 HashSet

*   Same as my solution.

## Two Sum (Easy # 1)

**Question**: Given an array of integers `nums` and an integer `target`, return *indices of the two numbers such that they add up to `target`*.

You may assume that each input would have ***exactly\* one solution**, and you may not use the *same* element twice.

You can return the answer in any order.

**Example 1:**

```
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
```

**Example 2:**

```
Input: nums = [3,2,4], target = 6
Output: [1,2]
```

**Example 3:**

```
Input: nums = [3,3], target = 6
Output: [0,1]
```

**Constraints:**

-   `2 <= nums.length <= 104`
-   `-109 <= nums[i] <= 109`
-   `-109 <= target <= 109`
-   **Only one valid answer exists.**

### My Solution

```java
public int[] twoSum(int[] nums, int target) {
    // hashmap to store the value + index
    Map<Integer, Integer> numMap = new HashMap<>();
    for (int i = 0; i < nums.length; i++){
        if (numMap.containsKey(target - nums[i])){
            return new int[]{i, numMap.get(target - nums[i])};
        }
        if (!numMap.containsKey(nums[i])){
            numMap.put(nums[i], i);
        }
    }
    return new int[]{0, 0};
}
```

*   Time and space complexity should be both $O(n)$

### Standard Solution

#### Solution #1 Two-passes HashMap

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        map.put(nums[i], i);
    }
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement) && map.get(complement) != i) {
            return new int[] { i, map.get(complement) };
        }
    }
    // In case there is no solution, we'll just return null
    return null;
}
```

-   Time complexity: $O(n)$. We traverse the list containing n elements exactly twice. Since the hash table reduces the lookup time to $O(1)$, the overall time complexity is $O(n)$.
-   Space complexity: $O(n)$. The extra space required depends on the number of items stored in the hash table, which stores exactly n elements

#### Solution #2 One-pass Hash Table

```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    // In case there is no solution, we'll just return null
    return null;
}
```

-   Time complexity: $O(n)$ We traverse the list containing n elements only once. Each lookup in the table costs only $O(1)$ time.
-   Space complexity: $O(n)$. The extra space required depends on the number of items stored in the hash table, which stores at most n elements.

## Minimum Sum of Squared Difference(Medium #2333)

**Question**: You are given two positive **0-indexed** integer arrays `nums1` and `nums2`, both of length `n`.

The **sum of squared difference** of arrays `nums1` and `nums2` is defined as the **sum** of `(nums1[i] - nums2[i])2` for each `0 <= i < n`.

You are also given two positive integers `k1` and `k2`. You can modify any of the elements of `nums1` by `+1` or `-1` at most `k1` times. Similarly, you can modify any of the elements of `nums2` by `+1` or `-1` at most `k2` times.

Return *the minimum **sum of squared difference** after modifying array* `nums1` *at most* `k1` *times and modifying array* `nums2` *at most* `k2` *times*.

**Note**: You are allowed to modify the array elements to become **negative** integers.

**Example 1:**

```
Input: nums1 = [1,2,3,4], nums2 = [2,10,20,19], k1 = 0, k2 = 0
Output: 579
Explanation: The elements in nums1 and nums2 cannot be modified because k1 = 0 and k2 = 0. 
The sum of square difference will be: (1 - 2)2 + (2 - 10)2 + (3 - 20)2 + (4 - 19)2 = 579.
```

**Example 2:**

```
Input: nums1 = [1,4,10,12], nums2 = [5,8,6,9], k1 = 1, k2 = 1
Output: 43
Explanation: One way to obtain the minimum sum of square difference is: 
- Increase nums1[0] once.
- Increase nums2[2] once.
The minimum of the sum of square difference will be: 
(2 - 5)2 + (4 - 8)2 + (10 - 7)2 + (12 - 9)2 = 43.
Note that, there are other ways to obtain the minimum of the sum of square difference, but there is no way to obtain a sum smaller than 43.
```

**Constraints:**

-   `n == nums1.length == nums2.length`
-   `1 <= n <= 105`
-   `0 <= nums1[i], nums2[i] <= 105`
-   `0 <= k1, k2 <= 109`

### My Solution

*   It is time limit exceeded, but the process is correct, need to make it more time-efficient

```java
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        PriorityQueue<Integer> diffQueue = new PriorityQueue<>((a, b) -> b - a);
        int[] diff = new int[n];
        for (int i = 0; i < n; i++){
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            diffQueue.add(diff[i]);
        }
        
        int times = k1 + k2;
        while (times > 0){
            int currentDiff = diffQueue.poll();
            if (currentDiff == 0){
                break;
            }
            currentDiff--;
            diffQueue.add(currentDiff);
            times--;
        }
        Long minSum = 0L;
        while(!diffQueue.isEmpty()){
            int elem = diffQueue.poll();
            minSum += elem * elem;
        }
        return minSum;
    }
}
```

### Standard Solution

#### Solution #1 Priority Queue

*   The `k1` and `k2` has a very large number, if just `k1--` it would spend a lot of time and exceed time limit
*   Need to use a hashmap to count the `diff` and each time deduct the counted number of times

```java
class Solution {
    class PQcomparator implements Comparator<int[]> {
        @Override
        public int compare(int[] a, int[] b) {
            return Integer.compare(b[0], a[0]);
        }
    }

    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diffs = new int[n];
        for(int i = 0; i < n; i++) {
            diffs[i] = Math.abs(nums1[i] - nums2[i]);
        }
        
        int op = k1 + k2;

        Map<Integer, Integer> freqs = new HashMap<>();
        for(int num : diffs) {
            freqs.put(num, freqs.getOrDefault(num, 0) + 1);
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(new PQcomparator());
        for(int key : freqs.keySet()) {
            pq.offer(new int[]{key, freqs.get(key)});
        }
        
        while(!pq.isEmpty()) {
            if (op == 0) {
                break;
            }
            
            int[] top = pq.poll();
            
            int key = top[0];
            int count = top[1];
            
            if (key == 0) {
                break;
            }

            int nextkey = 0;
            if (!pq.isEmpty()) {
                nextkey = pq.peek()[0];
            } else {
                pq.offer(new int[]{0, 0});
                nextkey = 0;
            }
            
            int diff = key - nextkey;
            if (count * diff <= op) {
                pq.peek()[1] += count;
                op -= count * diff;
            } else {
                int equalDistribute = op / count;
                int remaining = op - equalDistribute * count;
                
                int lowerKey = key - equalDistribute;
                if (lowerKey == 0) {
                    break;
                }
                
                pq.offer(new int[]{lowerKey, count - remaining});
                pq.offer(new int[]{lowerKey - 1, remaining});
                
                op = 0;
            }
        }
        
        long ans = 0;
        while(!pq.isEmpty()) {
            int[] top = pq.poll();
            int key = top[0];
            int count = top[1];
            ans += 1l * key * key * count;
        }
        
        return ans;
    }
}
```

