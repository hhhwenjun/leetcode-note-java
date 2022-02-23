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

## Backtracking Template

```python
def backtrack(candidate):
    if find_solution(candidate):
        output(candidate)
        return
    
    # iterate all possible candidates.
    for next_candidate in list_of_candidates:
        if is_valid(next_candidate):
            # try this partial candidate solution
            place(next_candidate)
            # given the candidate, explore further.
            backtrack(next_candidate)
            # backtrack
            remove(next_candidate)
```

*   At the first level, the function is implemented as recursion. At each occurrence of recursion, the function is one step further to the final solution. 
*   As the second level, within the recursion, we have an iteration that allows us to explore all the candidates that are of the same progress to the final solution.
*   Unlike brute-force search, in backtracking algorithms, we are often able to determine if a partial solution candidate is worth exploring further.

### Robot Room Cleaner(Example)

*   Given a room that is represented as a grid of cells, where each cell contains a value that indicates whether it is an obstacle or not, we are asked to clean the room with a robot cleaner which can turn in four directions and move one step at a time

<img src="https://assets.leetcode.com/uploads/2019/04/06/robot_room_cleaner.png" alt="img" style="zoom: 50%;" />

*   Implementation
    *   One can model each step of the robot as a recursive function (*i.e.* `backtrack()`).
    *   At each step, technically the robot would have four candidates of direction to explore, *e.g.* the robot located at the coordinate of `(0, 0)`. Since not each direction is available though, one should check if the cell in the given direction is an obstacle or it has been cleaned before, *i.e.* `is_valid(candidate)`. Another benefit of the check is that it would greatly reduce the number of possible paths that one needs to explore.
    *   Once the robot decides to explore the cell in a certain direction, the robot should mark its decision (*i.e.* `place(candidate)`). More importantly, later the robot should be able to revert the previous decision (*i.e.* `remove(candidate)`), by going back to the cell and restoring its original direction.
    *   The robot conducts the cleaning step by step, in the form of recursion of the `backtrack()` function. The backtracking would be triggered whenever the robot reaches a point that it is surrounded either by the obstacles (*e.g.* cell at row `1` and column `-3`) or the cleaned cells. At the end of the backtracking, the robot would get back to its starting point, and each cell in the grid would be traversed at least once. As a result, the room is cleaned at the end.

### Sudoku Solver(Example)

*   Sudoku is a popular game that many of you are familiar with. The main idea of the game is to fill a grid with only the numbers from 1 to 9 while ensuring that each row and each column as well as each sub-grid of 9 elements does not contain duplicate numbers.
*   Implementation
    *   Given a grid with some pre-filled numbers, the task is to fill the empty cells with the numbers that meet the constraint of the Sudoku game. We could model each step to fill an empty cell as a recursion function (*i.e.* our famous `backtrack()` function).
    *   At each step, technically we have 9 candidates at hand to fill the empty cell. Yet, we could filter out the candidates by examining if they meet the rules of the Sudoku game, (*i.e.* `is_valid(candidate)`).
    *   Then, among all the suitable candidates, we can try out one by one by filling the cell (i.e. `place(candidate)`). Later we can revert our decision (*i.e.* `remove(candidate)`) so that we could try out the other candidates.
    *   The solver would carry on one step after another, in the form of recursion by the `backtrack` function. The backtracking would be triggered at the points where either the solver cannot find any suitable candidate (as shown in the above figure), or the solver finds a solution to the problem. At the end of the backtracking, we would enumerate all the possible solutions to the Sudoku game. 

## Robot Room Cleaner(Hard #489)

**Question**: You are controlling a robot that is located somewhere in a room. The room is modeled as an `m x n` binary grid where `0` represents a wall and `1` represents an empty slot.

The robot starts at an unknown location in the room that is guaranteed to be empty, and you do not have access to the grid, but you can move the robot using the given API `Robot`.

You are tasked to use the robot to clean the entire room (i.e., clean every empty cell in the room). The robot with the four given APIs can move forward, turn left, or turn right. Each turn is `90` degrees.

When the robot tries to move into a wall cell, its bumper sensor detects the obstacle, and it stays on the current cell.

Design an algorithm to clean the entire room using the following APIs:

```
interface Robot {
  // returns true if next cell is open and robot moves into the cell.
  // returns false if next cell is obstacle and robot stays on the current cell.
  boolean move();

  // Robot will stay on the same cell after calling turnLeft/turnRight.
  // Each turn will be 90 degrees.
  void turnLeft();
  void turnRight();

  // Clean the current cell.
  void clean();
}
```

**Note** that the initial direction of the robot will be facing up. You can assume all four edges of the grid are all surrounded by a wall.

**Custom testing:**

The input is only given to initialize the room and the robot's position internally. You must solve this problem "blindfolded". In other words, you must control the robot using only the four mentioned APIs without knowing the room layout and the initial robot's position.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/07/17/lc-grid.jpg)

```
Input: room = [[1,1,1,1,1,0,1,1],[1,1,1,1,1,0,1,1],[1,0,1,1,1,1,1,1],[0,0,0,1,0,0,0,0],[1,1,1,1,1,1,1,1]], row = 1, col = 3
Output: Robot cleaned all rooms.
Explanation: All grids in the room are marked by either 0 or 1.
0 means the cell is blocked, while 1 means the cell is accessible.
The robot initially starts at the position of row=1, col=3.
From the top left corner, its position is one row below and three columns right.
```

**Example 2:**

```
Input: room = [[1]], row = 0, col = 0
Output: Robot cleaned all rooms.
```

**Constraints:**

-   `m == room.length`
-   `n == room[i].length`
-   `1 <= m <= 100`
-   `1 <= n <= 200`
-   `room[i][j]` is either `0` or `1`.
-   `0 <= row < m`
-   `0 <= col < n`
-   `room[row][col] == 1`
-   All the empty cells can be visited from the starting position.

### Standard Solution

#### Solution #1 Spiral Backtracking

*   Go forward, cleaning and marking all the cells on the way as visited. 
*   At the obstacle *turn right*, again go forward, *etc*. Always *turn right* at the obstacles and then go forward. Consider already visited cells as virtual obstacles.
*   **Algorithm**: Time to write down the algorithm for the backtrack function `backtrack(cell = (0, 0), direction = 0)`.
    *   Mark the cell as visited and clean it up.
    *   Explore `4` directions: `up`, `right`, `down`, and `left` (the order is important since the idea is always to turn right) :
        -   Check the next cell in the chosen direction:
            -   If it's not visited yet and there are no obstacles:
                -   Move forward.
                -   Explore next cells `backtrack(new_cell, new_direction)`.
                -   Backtrack, *i.e.* go back to the previous cell.
            -   Turn right because now there is an obstacle (or a virtual obstacle) just in front.

![bla](https://leetcode.com/problems/robot-room-cleaner/Figures/489/489_implementation.png)

```java
// going clockwise : 0: 'up', 1: 'right', 2: 'down', 3: 'left'
  int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
  Set<Pair<Integer, Integer>> visited = new HashSet();
  Robot robot;

  public void goBack() {
    robot.turnRight();
    robot.turnRight();
    robot.move();
    robot.turnRight();
    robot.turnRight();
  }

  public void backtrack(int row, int col, int d) {
    visited.add(new Pair(row, col));
    robot.clean();
    // going clockwise : 0: 'up', 1: 'right', 2: 'down', 3: 'left'
    for (int i = 0; i < 4; ++i) {
      int newD = (d + i) % 4;
      int newRow = row + directions[newD][0];
      int newCol = col + directions[newD][1];

      if (!visited.contains(new Pair(newRow, newCol)) && robot.move()) {
        backtrack(newRow, newCol, newD);
        goBack();
      }
      // turn the robot following chosen direction : clockwise
      robot.turnRight();
    }
  }

  public void cleanRoom(Robot robot) {
    this.robot = robot;
    backtrack(0, 0, 0);
  }
```

-   Time complexity: $\mathcal{O}(N - M)$, where $N$ is a number of cells in the room and $M$ is a number of obstacles.
    -   We visit each non-obstacle cell once and only once.
    -   At each visit, we will check 4 directions around the cell. Therefore, the total number of operations would be $4 \cdot (N-M)$.
-   Space complexity: $\mathcal{O}(N - M)$, where N is the number of cells in the room and M is the number of obstacles.
    -   We employed a hashtable to keep track of whether a non-obstacle cell is visited or not.

## Sudoku Solver(Hard #37)

**Question**: Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy **all of the following rules**:

1.  Each of the digits `1-9` must occur exactly once in each row.
2.  Each of the digits `1-9` must occur exactly once in each column.
3.  Each of the digits `1-9` must occur exactly once in each of the 9 `3x3` sub-boxes of the grid.

The `'.'` character indicates empty cells.

**Example 1:**

![img](https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Sudoku-by-L2G-20050714.svg/250px-Sudoku-by-L2G-20050714.svg.png)

```
Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
Explanation: The input board is shown above and the only valid solution is shown below:
```

**Constraints:**

-   `board.length == 9`
-   `board[i].length == 9`
-   `board[i][j]` is a digit or `'.'`.
-   It is **guaranteed** that the input board has only one solution.

### Standard Solution

#### Solution #1 Backtracking

*   Hard problems usually contain multiple methods to solve one problem
    *   How to analyze the problem and break down the problem into sub-problems(divide and conquer)
    *   How to organize your need of the methods, and arrange their relationship
    *   List multiple points of the problem and separate them into each method
        *   What are the methods, each method for one sub-problem
        *   What fields you may need
        *   What programming concepts and structures are applicable
*   Enumerate sub boxes: `box_index = (row / 3) * 3 + column / 3`

<img src="https://leetcode.com/problems/sudoku-solver/Figures/36/36_boxes_2.png" alt="img" style="zoom: 33%;" />

*   **Algorithm**: Now everything is ready to write down the backtrack function `backtrack(row = 0, col = 0)`.
    *   Start from the upper left cell `row = 0, col = 0`. Proceed till the first free cell.
    *   Iterate over the numbers from `1` to `9` and try to put each number `d` in the `(row, col)` cell.
        -   If number `d` is not yet in the current row, column, and box :
            -   Place the `d` in a `(row, col)` cell.
            -   Write down that `d` is now present in the current row, column and box.
            -   If we're on the last cell `row == 8, col == 8`:
                -   That means that we've solved the sudoku.
            -   Else
                -   Proceed to place further numbers.
            -   Backtrack if the solution is not yet here: remove the last number from the `(row, col)` cell.

```java
// box size
int n = 3;
// row size
int N = n * n;

int[][] rows = new int[N][N + 1];
int[][] columns = new int[N][N + 1];
int[][] boxes = new int[N][N + 1];

char[][] board;
boolean sudokuSolved = false;

public boolean couldPlace(int d, int row, int col){
    // check if one could place a number d in (row, col) cell
    int idx = (row / n) * n + col / n; // box index
    return rows[row][d] + columns[col][d] + boxes[idx][d] == 0; // no other location place this num
}

public void placeNumber(int d, int row, int col){
    // place a number d in (row, col) cell
    int idx = (row / n) * n + col / n;
    
    // place number, change flag to 1
    rows[row][d]++;
    columns[col][d]++;
    boxes[idx][d]++;
    board[row][col] = (char)(d + '0');
}

public void removeNumber(int d, int row, int col){
    // remove a number which did not lead to a solution
    int idx = (row / n) * n + col / n;
    rows[row][d]--;
    columns[col][d]--;
    boxes[idx][d]--;
    board[row][col] = '.';
}

// determine the end of operations, determine where to move next
public void placeNextNumbers(int row, int col){
    /*
    * call backtrack function in recursion to continue to place numbers
    * till the moment we have a solution
    */
    // if we're in the last cell
    // that means we have the solution
    if ((col == N - 1) && (row == N - 1)){
        sudokuSolved = true;
    }
    else {
        // if we are in the end of row, go to next row
        if (col == N - 1) backtrack(row + 1, 0);
        // go to the next column
        else backtrack(row, col + 1);
    }
}

public void backtrack(int row, int col){
    // if the cell is empty
    if (board[row][col] == '.'){
        // iterate over all numbers from 1 to 9
        for (int d = 1; d < 10; d++){
            if (couldPlace(d, row, col)){
                placeNumber(d, row, col);
                placeNextNumbers(row, col);
                // if sodoku is solved, there is no need to backtrack
                // since the single unique solution is promised
                if (!sudokuSolved) removeNumber(d, row, col);
            }
        }
    }
    else placeNextNumbers(row, col);
}

public void solveSudoku(char[][] board){
    this.board = board;
    // init rows, columns and boxes
    for (int i = 0; i < N; i++){
        for (int j = 0; j < N; j++){
            char num = board[i][j];
            if (num != '.'){
                int d = Character.getNumericValue(num);
                // record the existing number in the 2D arrays records
                placeNumber(d, i, j);
            }
        }
    }
    backtrack(0,0);// solve from the first value
}
```

*   Time complexity is constant here since the board size is fixed and there is no N-parameter to measure. Though let's discuss the number of operations needed : $(9!)^9$. Let's consider one row, i.e. not more than 99 cells to fill. There are not more than 9 possibilities for the first number to put, not more than $9 \times 8$ for the second one, not more than $9 \times 8 \times 7$ for the third one, etc. In total that results in not more than $9!$ possibilities for just one row, that means not more than $(9!)^9$ operations in total. Let's compare:
    -   and $(9!)^9 $for the standard backtracking
*   Space complexity: the board size is fixed, and the space is used to store board, rows, columns, and boxes structures, each containing `81` elements.