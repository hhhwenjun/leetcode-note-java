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