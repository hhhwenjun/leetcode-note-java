# Backtracking Tutorials

**Definition**: Backtracking is a general algorithm for finding all (or some) solutions to some computational problems (notably Constraint satisfaction problems or CSPs), which incrementally builds candidates to the solution and abandons a candidate ("backtracks") as soon as it determines that the candidate cannot lead to a valid solution.

<img src="https://assets.leetcode.com/uploads/2019/04/15/backtracking.png" alt="img" style="zoom:33%;" />

*   Conceptually, one can imagine the procedure of backtracking as the ***tree traversal***. Starting from the root node, one sets out to search for solutions that are located at the leaf nodes. 
*   Once we can determine if a certain node cannot possibly lead to a valid solution, we abandon the current node and ***backtrack*** to its parent node to explore other possibilities. 

## N-Queens II (Hard #52)

**Question**: The **n-queens** puzzle is the problem of placing `n` queens on an `n x n` chessboard such that no two queens attack each other.

Given an integer `n`, return *the number of distinct solutions to the **n-queens puzzle***.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/13/queens.jpg)

```
Input: n = 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown.
```

**Example 2:**

```
Input: n = 1
Output: 1 
```

**Constraints:**

-   `1 <= n <= 9`

### Standard Solution

#### Solution #1 Backtracking

*   A queen can be attacked if another queen is in any of the 4 following positions: on the same row, on the same column, on the same diagonal, or on the same anti-diagonal.
*   Recall that to implement backtracking, we implement a `backtrack` function that makes some changes to the state, calls itself again, and then when that call returns it undoes those changes (this last part is why it's called "backtracking").
*   To make sure that we only place 1 queen per **row**, we will pass an integer argument `row` into `backtrack`, and will only place one queen during each call. Whenever we place a queen, we'll move onto the next row by calling `backtrack` again with the parameter value `row + 1`.
*   To make sure we only place 1 queen per **column**, we will use a set. Whenever we place a queen, we can add the column index to this set.
*   For each square on a given **diagonal**, the difference between the row and column indexes `(row - col)` will be constant. Think about the diagonal that starts from `(0, 0)` - the $i^{th}$ square has coordinates `(i, i)`, so the difference is always 0.

<img src="https://leetcode.com/problems/n-queens-ii/Figures/52/diagonals.png" alt="img" style="zoom:50%;" />

*   For each square on a given **anti-diagonal**, the sum of the row and column indexes `(row + col)` will be constant. If you were to start at the highest square in an anti-diagonal and move downwards the row index increments by 1 `(row + 1)`, and the column index decrements by 1 `(col - 1)`. These cancel each other out.

<img src="https://leetcode.com/problems/n-queens-ii/Figures/52/antidiagonals.png" alt="img" style="zoom:50%;" />

*   **Algorithm**: We'll create a recursive function `backtrack` that takes 4 arguments to maintain the board state. The first parameter is the row we're going to place a queen on next, and the other 3 are sets that track which columns, diagonals, and anti-diagonals have already had queens placed on them. The function will work as follows:
    *   If the current row we are considering is greater than `n`, then we have a solution. Return `1`.
    *   Initiate a local variable `solutions = 0` that represents all the possible solutions that can be obtained from the current board state.
    *   Iterate through the columns of the current row. At each column, we will attempt to place a queen at the square `(row, col)` - remember we are considering the current row through the function arguments.
        -   Calculate the diagonal and anti-diagonal that the square belongs to. If there has been no queen placed yet in the column, diagonal, or anti-diagonal, then we can place a queen in this column, in the current row.
        -   If we can't place the queen, skip this column (move on to try with the next column).
    *   If we were able to place a queen, then update our 3 sets (`cols`, `diagonals`, and `antiDiagonals`), and call the function again, but with `row + 1`.
    *   The function call made in step 4 explores all valid board states with the queen we placed in step 3. Since we're done exploring that path, backtrack by removing the queen from the square - this just means removing the values we added to our sets.

```java
private int size;
public int totalNQueens(int n){
    size = n;
    return backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>());
}
private int backtrack(int row, Set<Integer> diagonals, Set<Integer> antiDiagonals, Set<Integer> cols){
    // base case - N queens have been placed
    if (row == size){
        return 1;
    }
    int solutions = 0;
    for(int col = 0; col < size; col++){
        int currDiagonal = row - col;
        int currAntiDiagonal = row + col;
        // If the queen is not placeable
        if (cols.contains(col) || diagonals.contains(currDiagonal) ||
           antiDiagonals.contains(currAntiDiagonal)){
            continue;
        }
        // Add the queen to the board
        cols.add(col);
        diagonals.add(currDiagonal);
        antiDiagonals.add(currAntiDiagonal);
        
        // Move on to the next row with the updated board state
        solutions += backtrack(row + 1, diagonals, antiDiagonals, cols);
        
        // Remove the queen from the board since we have already
        // explored all valid paths using the above function call
        cols.remove(col);
        diagonals.remove(currDiagonal);
        antiDiagonals.remove(currAntiDiagonal);
    }
    return solutions;
}
```

*   Time complexity: $O(N!)$, where $N$ is the number of queens (which is the same as the width and height of the board).

    Unlike the brute force approach, we place a queen only on squares that aren't attacked. For the first queen, we have N options. For the next queen, we won't attempt to place it in the same column as the first queen, and there must be at least one square attacked diagonally by the first queen as well. Thus, the maximum number of squares we can consider for the second queen is $N - 2$. For the third queen, we won't attempt to place it in 2 columns already occupied by the first 2 queens, and there must be at least two squares attacked diagonally from the first 2 queens. Thus, the maximum number of squares we can consider for the third queen is $N - 4$. This pattern continues, giving an approximate time complexity of $N!$ at the end.

*   Space complexity: $O(N)$, where N is the number of queens (which is the same as the width and height of the board).

    Extra memory used includes the 3 sets used to store board state, as well as the recursion call stack. All of this scales linearly with the number of queens.

## N-Queens (Hard #51)

**Question**: The **n-queens** puzzle is the problem of placing `n` queens on an `n x n` chessboard such that no two queens attack each other.

Given an integer `n`, return *all distinct solutions to the **n-queens puzzle***. You may return the answer in **any order**.

Each solution contains a distinct board configuration of the queen's placement, where `'Q'` and `'.'` both indicate a queen and an empty space, respectively 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/13/queens.jpg)

```
Input: n = 4
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
```

**Example 2:**

```
Input: n = 1
Output: [["Q"]]
```

**Constraints:**

-   `1 <= n <= 9`

### Standard Solution

#### Solution #1 Backtracking

*   Same as the last problem, but using a 2D array to make up the solution
*   Add one more helper method to transfer the 2D array to a list
*   In the backtracking method, modify the character in a 2D array to 'Q'
    *   When do the backtrack, change 'Q' back to the dot

```java
private int size;
private List<List<String>> solutions = new ArrayList<List<String>>();
public List<List<String>> solveNQueens(int n) {
    size = n;
    char emptyBoard[][] = new char[size][size];
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            emptyBoard[i][j] = '.';
        }
    }
    backtrack(0, new HashSet<>(), new HashSet<>(), new HashSet<>(), emptyBoard);
    return solutions;  
}
// making use of a helper function to get the solutions in the corrent output format
private List<String> createBoard(char[][] state){
    List<String> board = new ArrayList<String>();
    for (int row = 0; row < size; row++){
        String current_row = new String(state[row]);
        board.add(current_row);
    }
    return board;
}

public void backtrack(int row, Set<Integer> diagonals, Set<Integer> antiDiagonals,
                                   Set<Integer> cols, char[][] state){
    if (row == size){
        solutions.add(createBoard(state));
        return;
    }
    for(int col = 0; col < size; col++){
        int currDiagonal = row - col;
        int currAntiDiagonal = row + col;
        // if the queen is not placeable
        if (cols.contains(col) || diagonals.contains(currDiagonal) ||
           antiDiagonals.contains(currAntiDiagonal)){
            continue;
        }
        // add the queen to the board
        cols.add(col);
        diagonals.add(currDiagonal);
        antiDiagonals.add(currAntiDiagonal);
        state[row][col] = 'Q';
        // move on to the next row with the updated board state
        backtrack(row + 1, diagonals, antiDiagonals, cols, state);

        // remove the queen from the board since we have already explored
        // all valid paths using the above function call
        cols.remove(col);
        diagonals.remove(currDiagonal);
        antiDiagonals.remove(currAntiDiagonal);
        state[row][col] = '.';
    }
}
```

-   Time complexity: $O(N!)$

    Unlike the brute force approach, we will only place queens on squares that aren't under attack. For the first queen, we have N options. For the next queen, we won't attempt to place it in the same column as the first queen, and there must be at least one square attacked diagonally by the first queen as well. Thus, the maximum number of squares we can consider for the second queen is N - 2. For the third queen, we won't attempt to place it in 2 columns already occupied by the first 2 queens, and there must be at least two squares attacked diagonally from the first 2 queens. Thus, the maximum number of squares we can consider for the third queen is N - 4. This pattern continues, resulting in an approximate time complexity of $N!$.

    While it costs $O(N^2)$ to build each valid solution, the amount of valid solutions S(N) does not grow nearly as fast as N!, so $O(N! + S(N) * N^2) = O(N!)$.

-   Space complexity: $O(N^2)$

    Extra memory used includes the 3 sets used to store board state, as well as the recursion call stack. All of this scales linearly with the number of queens. However, to keep the board state costs $O(N^2)$, since the board is of size `N * N`. Space used for the output does not count towards space complexity.

