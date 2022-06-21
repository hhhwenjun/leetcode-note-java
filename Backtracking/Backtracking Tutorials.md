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
*   In the backtracking method, modify the character in a 2D array to 'Q.'
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

    Unlike the brute force approach, we will only place queens on squares that aren't under attack. For the first queen, we have N options. For the next queen, we won't attempt to place it in the same column as the first queen, and there must be at least one square attacked diagonally by the first queen as well. Thus, the maximum number of squares we can consider for the second queen is N - 2. For the third queen, we won't attempt to place it in 2 columns already occupied by the first two queens, and there must be at least two squares attacked diagonally from the first two queens. Thus, the maximum number of squares we can consider for the third queen is N - 4. This pattern continues, resulting in an approximate time complexity of $N!$.

    While it costs $O(N^2)$ to build each valid solution, the amount of good solutions S(N) does not grow nearly as fast as N!, so $O(N! + S(N) * N^2) = O(N!)$.

-   Space complexity: $O(N^2)$

    Extra memory used includes the 3 sets used to store board state and the recursion call stack. All of this scales linearly with the number of queens. However, to keep the board state costs. $O(N^2)$, since the board is of size `N * N`. Space used for the output does not count towards space complexity.

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

*   At the first level, the function is implemented as recursion. At each occurrence of recursion, the process is one step further to the final solution. 
*   As the second level, within the recursion, we have an iteration that allows us to explore all the candidates that are of the same progress to the final solution.
*   Unlike brute-force search, in backtracking algorithms, we can often determine if a partial solution candidate is worth exploring further.

### Robot Room Cleaner(Example)

*   Given a room represented as a grid of cells, where each cell contains a value that indicates whether it is an obstacle or not, we are asked to clean the room with a robot cleaner that can turn in four directions and move one step at a time.

.<img src="https://assets.leetcode.com/uploads/2019/04/06/robot_room_cleaner.png" alt="img" style="zoom: 50%;" />

*   Implementation
    *   One can model each robot step as a recursive function (*i.e.,* `backtrack()`).
    *   At each step, technically, the robot would have four candidates of direction to explore, *e.g.,* the robot located at the coordinate of `(0, 0)`. Since not each guide is available, one should check if the cell in the given order is an obstacle or has been cleaned before, *i.e.,* `is_valid(candidate)`. Another benefit of the check is that it would greatly reduce the number of possible paths that one needs to explore.
    *   Once the robot decides to explore the cell in a certain direction, the robot should mark its decision (*i.e.,* `place(candidate)`). More importantly, later, the robot should revert the previous decision (*i.e.,* `remove(candidate)`) by returning to the cell and restoring its original direction.
    *   The robot conducts the cleaning step by step in the recursion of the `backtrack()` function. The backtracking would be triggered whenever the robot reaches a point surrounded by either the obstacles (*e.g.*, cell at row `1` and column `-3`) or the cleaned cell. At the end of the backtracking, the robot would get back to its starting point, and each cell in the grid would be traversed at least once. As a result, the room is cleaned at the end.

### Sudoku Solver(Example)

*   Sudoku is a popular game that many of you are familiar with. The game's main idea is to fill a grid with only the numbers from 1 to 9 while ensuring that each row, column, and sub-grid of 9 elements do not contain duplicate numbers.
*   Implementation
    *   Given a grid with some pre-filled numbers, the task is to fill the empty cells with the numbers that meet the constraint of the Sudoku game. We could model each step to serve an open call open-cell as a recursion function (*i.e.,* our famous `backtrack()` function).
    *   Technically, we have nine candidates at hand to fill the empty cell at each step. Technically, we have nine candidates at hand to fill the open cell at each stage. Yet, we could filter out the candidates by examining if they meet the rules of the Sudoku game (*i.e.* `is_valid(candidate)`).
    *   Then, among all the suitable candidates, we can try out one by one by filling the cell (i.e. `place(candidate)`). Later we can revert our decision (*i.e.,* `remove(candidate)`) so that we could try out the other candidates.
    *   The solver would carry on one step after another, in the form of recursion by the `backtrack` function. The backtracking would be triggered at the points where either the solver cannot find any suitable candidate (as shown in the above figure), or the solver finds a solution to the problem. At the end of the backtracking, we would enumerate all the possible solutions to the Sudoku game. 

## Robot Room Cleaner(Hard #489)

**Question**: You are controlling a robot located somewhere in a room. The room is modeled as an `m x n` binary grid where `0` represents a wall, and `1` illustrates an empty slot.

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

**Note** that the initial direction of the robot will be facing up. You can assume all four edges of the grid are surrounded by a wall.

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

*   Time complexity is constant since the board size is fixed and there is no N-parameter to measure. Though let's discuss the number of operations needed : $(9!)^9$. Let's consider one row, i.e., not more than 99 cells to fill. There are not more than nineineineineineineine possibilities for the first number to put, not more than $9 \times 8$ for the second one, not more than $9 \times 8 \times 7$ for the third one, etc, In total , that results in not more than $9!$ possibilities for just one row that means not more than $(9!)^9$ operations in total. Let's compare:
    -   and $(9!)^9 $for the standard backtracking
*   Space complexity: the board size is fixed, and the space is used to store board, rows, columns, and boxes structures, each containing `81` elements.

## Combinations(Medium #77)

**Question**: Given two integers `n` and `k`, return *all possible combinations of* `k` *numbers out of the range* `[1, n]`.

You may return the answer in **any order**.

**Example 1:**

```
Input: n = 4, k = 2
Output:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
```

**Example 2:**

```
Input: n = 1, k = 1
Output: [[1]]
```

**Constraints:**

-   `1 <= n <= 20`
-   `1 <= k <= n`

### My Solution

*   Backtracking: find a solution, remove the key, backtrack

```java
private List<List<Integer>> comb;
private int n;
private int k;

public List<List<Integer>> combine(int n, int k) {
    this.n = n;
    this.k = k;
    comb = new LinkedList<>();
    // put a potential solution into the recursion, empty solution here
    backtrack(1, new LinkedList<Integer>());
    return comb;
}

public void backtrack(int start, LinkedList<Integer> solution){
    // base case
    if (solution.size() == k){
        comb.add(new LinkedList(solution));
    }
    for (int i = start; i < n + 1; i++){
        solution.add(i);
        backtrack(i + 1, solution);
        solution.removeLast();
    }
}
```

### Standard Solution

#### Solution #1 Backtracking

*   Same as my solution
*   Time complexity : $\mathcal{O}(k C_N^k)$, where $C_N^k = \frac{N!}{(N - k)! k!}$ is a number of combinations to build. `append / pop (add / removeLast)` operations are constant-time ones and the only consuming part here is to append the built combination of length k to the output.
*   Space complexity: $\mathcal{O}(C_N^k)$ to keep all the combinations for an output.

## Subsets (Medium #78)

**Question**: Given an integer array `nums` of **unique** elements, return *all possible subsets (the power set)*.

The solution set **must not** contain duplicate subsets. Return the solution in **any order**.

**Example 1:**

```
Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```

**Example 2:**

```
Input: nums = [0]
Output: [[],[0]]
```

**Constraints:**

-   `1 <= nums.length <= 10`
-   `-10 <= nums[i] <= 10`
-   All the numbers of `nums` are **unique**.

### My Solution

```java
// very good backtracking pratice problem
List<List<Integer>> res;
int[] nums;
int k;
public List<List<Integer>> subsets(int[] nums) {
    res = new ArrayList<>();
    this.nums = nums;
    for (k = 0; k < nums.length + 1; k++){
        backtracking(0, new ArrayList<>());
    }
    return res;
}
public void backtracking(int start, List<Integer> subset){
    // base case
    if (subset.size() == k){
        res.add(new ArrayList(subset));
        return;
    }
    for (int i = start; i < nums.length; i++){
        subset.add(nums[i]);
        backtracking(i + 1, subset);
        subset.remove(subset.size() - 1);
    }
}
```

### Standard Solution

#### Solution #1 Backtracking

*   The most classic solution for the problem. This problem is a good example of how backtracking work.
*   Since the answers can have different lengths, we need to predefine k as the length. Loop through the length and get the answers. 
*   If the current ArrayList reaches the length of k, then we stop and return.
*   Same as the above my solution
*   Time complexity: $\mathcal{O}(N \times 2^N)$ to generate all subsets and then copy them into the output list.
*   Space complexity: $\mathcal{O}(N)$. We are using $O(N)$ space to maintain `curr`, and are modifying `curr` in-place with backtracking. Note that for space complexity analysis, we do not count space that is *only* used for the purpose of returning output, so the `output` array is ignored.

#### Solution #2 Cascading

*   Let's start from the empty subset in the output list. At each step one takes a new integer into consideration and generates new subsets from the existing ones. (Somehow similar to dynamic programming)

<img src="https://leetcode.com/problems/subsets/Figures/78/recursion.png" alt="diff" style="zoom: 33%;" />

```java
// continuously expand the current subsets with new digit
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> output = new ArrayList();
    output.add(new ArrayList<Integer>());

    for (int num : nums) {
      List<List<Integer>> newSubsets = new ArrayList();
      for (List<Integer> curr : output) {
        newSubsets.add(new ArrayList<Integer>(curr){{add(num);}});
      }
      for (List<Integer> curr : newSubsets) {
        output.add(curr);
      }
    }
    return output;
}
```

-   Time complexity: $\mathcal{O}(N \times 2^N)$ to generate all subsets and then copy them into the output list.
-   Space complexity: $\mathcal{O}(N \times 2^N)$. This is exactly the number of solutions for subsets multiplied by the number N of elements to keep for each subset.
    -   For a given number, it could be present or absent (*i.e.* binary choice) in a subset solution. As a result, for N numbers, we would have in total $2^N$ choices (solutions).

## Subsets II (Medium #90)

**Question**: Given an integer array `nums` that may contain duplicates, return *all possible subsets (the power set)*.

The solution set **must not** contain duplicate subsets. Return the solution in **any order**.

**Example 1:**

```
Input: nums = [1,2,2]
Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
```

**Example 2:**

```
Input: nums = [0]
Output: [[],[0]]
```

**Constraints:**

-   `1 <= nums.length <= 10`
-   `-10 <= nums[i] <= 10`

### My Solution

```java
// incorrect solution
List<List<Integer>> res;
Set<List<Integer>> set;
int[] nums;
int k;

public List<List<Integer>> subsetsWithDup(int[] nums) {
    this.nums = nums;
    res = new ArrayList<>();
    set = new HashSet<>();
    for (k = 0; k < nums.length + 1; k++){
        backtracking(0, new ArrayList<Integer>());
    }
    return res;
}

// backtracking process
public void backtracking(int start, List<Integer> subset){
    // base case for recursion
    if (subset.size() == k){
        if (!set.contains(new ArrayList(subset))){
            res.add(new ArrayList(subset));
            set.add(new ArrayList(subset));
        }
        return;
    }
    // recurrence relationship
    for (int i = start; i < nums.length; i++){
        if (i != start && nums[i] == nums[i - 1]){
            continue;
        }
        subset.add(nums[i]);
        backtracking(start + 1, subset);
        subset.remove(subset.size() - 1);
    }
}
```

### Standard Solution

#### Solution #1 Backtracking

*   As demonstrated in the previous approaches, the key to this problem is figuring out how to avoid duplicate subsets.
*   When designing our recursive function, there are two main points that we need to consider at each function call:
    -   Whether the element under consideration has duplicates or not.
    -   If the element has duplicates, which element among the duplicates should be considered while creating a subset.

```java
public List<List<Integer>> subsetsWithDup(int[] nums){
    // sort the array - important
    Arrays.sort(nums);
    List<List<Integer>> subsets = new ArrayList<>();
    List<Integer> currentSubset = new ArrayList<>();
    
    subsetsWithDupHelper(subsets, currentSubset, nums, 0);
    return subsets;
}
private void subsetsWithDupHelper(List<List<Integer>> subsets, 
                                  List<Integer> currentSubset, int[] nums, int index){
    // add the subset formed so far to the subses list
    subsets.add(new ArrayList<>(currentSubset));
    
    for (int i = index; i < nums.length; i++){
        // if the current element is a duplicate, ignore
        if (i != index && nums[i] == nums[i - 1]){
            continue;
        }
        currentSubset.add(nums[i]);
        subsetsWithDupHelper(subsets, currentSubset, nums, i + 1);
        currentSubset.remove(currentSubset.size() - 1);
    }
}
```

*   Time complexity: $O(n \cdot 2 ^ n)$
    *   As we can see in the diagram above, this approach does not generate any duplicate subsets. Thus, in the worst case (array consists of n distinct elements), the total number of recursive function calls will be $2 ^ n$
*   Space complexity: $O(n)$
    *   The space complexity of the sorting algorithm depends on the implementation of each programming language. For instance, in Java, the Arrays.sort() for primitives is implemented as a variant of the quicksort algorithm whose space complexity is $O(\log n)$

#### Solution #2 Cascading

*   Time complexity is the same
*   Space complexity: $O(\log n)$. The space complexity of the sorting algorithm depends on the implementation of each programming language.

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> subsets = new ArrayList<>();
    subsets.add(new ArrayList<Integer>());

    int subsetSize = 0;

    for (int i = 0; i < nums.length; i++) {
        int startingIndex = (i >= 1 && nums[i] == nums[i - 1]) ? subsetSize : 0;
        // subsetSize refers to the size of the subset in the previous step. This value also indicates the starting index of the subsets generated in this step.
        subsetSize = subsets.size();
        for (int j = startingIndex; j < subsetSize; j++) {
            List<Integer> currentSubset = new ArrayList<>(subsets.get(j));
            currentSubset.add(nums[i]);
            subsets.add(currentSubset);
        }
    }
    return subsets;
}
```

## Target Sum (Medium #494)

**Question**: You are given an integer array `nums` and an integer `target`.

You want to build an **expression** out of nums by adding one of the symbols `'+'` and `'-'` before each integer in nums and then concatenate all the integers.

-   For example, if `nums = [2, 1]`, you can add a `'+'` before `2` and a `'-'` before `1` and concatenate them to build the expression `"+2-1"`.

Return the number of different **expressions** that you can build, which evaluates to `target` 

**Example 1:**

```
Input: nums = [1,1,1,1,1], target = 3
Output: 5
Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
-1 + 1 + 1 + 1 + 1 = 3
+1 - 1 + 1 + 1 + 1 = 3
+1 + 1 - 1 + 1 + 1 = 3
+1 + 1 + 1 - 1 + 1 = 3
+1 + 1 + 1 + 1 - 1 = 3
```

**Example 2:**

```
Input: nums = [1], target = 1
Output: 1
```

**Constraints:**

-   `1 <= nums.length <= 20`
-   `0 <= nums[i] <= 1000`
-   `0 <= sum(nums[i]) <= 1000`
-   `-1000 <= target <= 1000`

### My Solution

*   Every given number has to be used in the expression, and no order requirement(such as permutations), so no need to use for loop for the question.
*   But since it is a recursion with two directions, speed would be super slow, estimated $O(2^n)$. The space complexity can be $O(n)$
*   Basically, the backtracking method is the brute force method for this problem. 

```java
/*
* abstract: build expression, return all number of expressions
* Use all num in nums, reach the target sum
* backtracking(recursion)
*/
int res;
int[] nums;

public int findTargetSumWays(int[] nums, int target) {
    res = 0;
    this.nums = nums;
    backtracking(0, target);
    return res;
}

// backtracking process
public void backtracking(int index, int target){
    // base case: reach the end of nums
    if (index == nums.length && target == 0){
        res++;
        return;
    }
    if (index == nums.length){
        return;
    }
    // expressions building, can be +/-
    backtracking(index + 1, target + nums[index]);
    backtracking(index + 1, target - nums[index]); 
}
```

### Standard Solution

#### Solution #1 2D DP

*   Traverse all the possibilities

<img src="/Users/wenjunhan/Library/Application Support/typora-user-images/image-20220613153621191.png" alt="image-20220613153621191" style="zoom:50%;" />

```java
public int findTargetSumWays(int[] nums, int S) {
    int total = Arrays.stream(nums).sum();
    int[][] dp = new int[nums.length][2 * total + 1];
    dp[0][nums[0] + total] = 1;
    dp[0][-nums[0] + total] += 1;

    for (int i = 1; i < nums.length; i++) {
        for (int sum = -total; sum <= total; sum++) {
            if (dp[i - 1][sum + total] > 0) {
                dp[i][sum + nums[i] + total] += dp[i - 1][sum + total];
                dp[i][sum - nums[i] + total] += dp[i - 1][sum + total];
            }
        }
    }
    return Math.abs(S) > total ? 0 : dp[nums.length - 1][S + total];
}
```

-   Time complexity: $O(t \cdot n)$ The `dp` array of size $O(t \cdot n)$ has been filled just once. Here, t refers to the sum of the $nums$ array and n refers to the length of the $nums$ array.
-   Space complexity: $O(t \cdot n)$. `dp` array of size $t \cdot n$ is used.

## Palindrome Permutation II (Medium #267)

**Question**: Given a string s, return *all the palindromic permutations (without duplicates) of it*.

You may return the answer in **any order**. If `s` has no palindromic permutation, return an empty list.

**Example 1:**

```
Input: s = "aabb"
Output: ["abba","baab"]
```

**Example 2:**

```
Input: s = "abc"
Output: [] 
```

**Constraints:**

-   `1 <= s.length <= 16`
-   `s` consists of only lowercase English letters.

### My Solution

```java
String s;
Set<String> set;

public List<String> generatePalindromes(String s) {
    this.s = s;
    set = new HashSet<>();

    // backtracking process
    backtracking(0);
    return new ArrayList(set);
}

public void swap(int i, int j){
    // swap char at index i and j
    char[] strArray = s.toCharArray();
    char temp = strArray[i];
    strArray[i] = strArray[j];
    strArray[j] = temp;
    s = String.valueOf(strArray);
    return;
}

public boolean isPalindrome(String str){
    int high = str.length() - 1, low = 0;
    while (low < high){
        if (str.charAt(high) != str.charAt(low)){
            return false;
        }
    }
    return true;
}

public void backtracking(int index){
    if (index == s.length() && isPalindrome(s)){
        set.add(s);
    }
    for (int i = index; i < s.length(); i++){
        swap(index, i);
        backtracking(index + 1);
        swap(index, i);
    }
}
```

### Standard Solution

#### Solution #1 Backtracking Brute Force (TLE)

*   Same as my solution, but also exceed the time limit for this problem
*   Time complexity: $O((n+1)!)$. A total of $n!$ permutations are possible. For every permutation generated, we need to check if it is a palindrome, each of which requires $O(n)$ time.
*   Space complexity: $O(n)$. The depth of the recursion tree can go upto n.

#### Solution #2 Backtracking and Split the String into Two

*   Since palindrome would use most of the character twice, we can split the string and reverse later half the string to save complexity.
*   Use a helper method to count the number of characters in the string, determine if it can be a palindrome, if not, just return null list
*   Use an array to save half of the string characters, when using backtracking, each time we reverse the string and concat it with the first half of the string and make if palindrome

```java
public class Solution {
    Set < String > set = new HashSet < > ();
    public List < String > generatePalindromes(String s) {
        int[] map = new int[128];
        char[] st = new char[s.length() / 2];
        if (!canPermutePalindrome(s, map))
            return new ArrayList < > ();
        char ch = 0;
        int k = 0;
        for (int i = 0; i < map.length; i++) {
            if (map[i] % 2 == 1)
                ch = (char) i;
            for (int j = 0; j < map[i] / 2; j++) {
                st[k++] = (char) i;
            }
        }
        permute(st, 0, ch);
        return new ArrayList < String > (set);
    }
    public boolean canPermutePalindrome(String s, int[] map) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            map[s.charAt(i)]++;
            if (map[s.charAt(i)] % 2 == 0)
                count--;
            else
                count++;
        }
        return count <= 1;
    }
    public void swap(char[] s, int i, int j) {
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
    void permute(char[] s, int l, char ch) {
        if (l == s.length) {
            set.add(new String(s) + (ch == 0 ? "" : ch) + new StringBuffer(new String(s)).reverse());
        } else {
            for (int i = l; i < s.length; i++) {
                if (s[l] != s[i] || l == i) {
                    swap(s, l, i);
                    permute(s, l + 1, ch);
                    swap(s, l, i);
                }
            }
        }
    }
}
```

-   Time complexity: $O\big((\frac{n}{2}+1)!\big)$. At most $\frac{n}{2}!$ permutations need to be generated in the worst case. Further, for each permutation generated, `string.reverse()` function will take $n/4$ time.
-   Space complexity: $O(n)$. The depth of recursion tree can go upto $n/2$ in the worst case.

## Beautiful Arrangement (Medium #526)

**Question**: Suppose you have `n` integers labeled `1` through `n`. A permutation of those `n` integers `perm` (**1-indexed**) is considered a **beautiful arrangement** if for every `i` (`1 <= i <= n`), **either** of the following is true:

-   `perm[i]` is divisible by `i`.
-   `i` is divisible by `perm[i]`.

Given an integer `n`, return *the **number** of the **beautiful arrangements** that you can construct*.

**Example 1:**

```
Input: n = 2
Output: 2
Explanation: 
The first beautiful arrangement is [1,2]:
    - perm[1] = 1 is divisible by i = 1
    - perm[2] = 2 is divisible by i = 2
The second beautiful arrangement is [2,1]:
    - perm[1] = 2 is divisible by i = 1
    - i = 2 is divisible by perm[2] = 1
```

**Example 2:**

```
Input: n = 1
Output: 1
```

**Constraints:**

-   `1 <= n <= 15`

### My Solution

```java
// abstract: return number of arragements meet requirements
// 1. Using backtracking, swap elements for permutations
// 2. Determine if the permutation meets the requirements
int count;
List<Integer> nums;

public int countArrangement(int n) {
    nums = new ArrayList<>();
    // put it inside an array
    for (int i = 1; i <= n; i++){
        nums.add(i);
    }
    count = 0;
    backtracking(0);
    return count;
}

private boolean isBeautiful(int i, int j){
    return i % j == 0 || j % i == 0;
}

private void backtracking(int index){
    if (index == nums.size()){
        count++;
        return;
    }
    for (int i = index; i < nums.size(); i++){
        Collections.swap(nums, i, index);
        if (isBeautiful(nums.get(index), index + 1)){
            backtracking(index + 1);
        }
        Collections.swap(nums, i, index);
    }
}
```

*   Basically, it is a backtracking method with brute force, but slower since we use `Collections.swap()`, if we only use array swap could be faster.
*   It is also a standard solution.
*   Time complexity: $O(k)$. k refers to the number of valid permutations.
*   Space complexity: $O(n)$. The depth of the recursion tree can go up to n. Further, $nums$ an array of size n is used, where, n is the given number.

#### Solution #2 Better Backtracking

*   Use a `visited` array to record if we have visited the digit, no need to swap and save more time

```java
int count = 0;
public int countArrangement(int N){
    boolean[] visited = new boolean[N + 1];
    calculate(N, 1, visited);
    return count;
}
public void calculate(int N, int pos, boolean[] visited){
    if (pos > N){
       count++;
    }
    for (int i = 1; i <= N; i++){
        if (!visited[i] && (pos % i == 0 || i % pos == 0)){
            visited[i] = true;
            calculate(N, pos + 1, visited);
            visited[i] = false;
        }
    }
}
```

-   Time complexity: $O(k)$. k refers to the number of valid permutations.
-   Space complexity: $O(n)$.` visited` array of size n is used. The depth of the recursion tree will also go up to n. Here, n refers to the given integer n.
