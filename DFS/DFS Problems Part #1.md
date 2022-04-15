# DFS Problems Part #1

**Question**: You are given an `m x n` binary matrix `grid`. An island is a group of `1`'s (representing land) connected **4-directionally** (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

The **area** of an island is the number of cells with a value `1` in the island.

Return *the maximum **area** of an island in* `grid`. If there is no island, return `0`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/05/01/maxarea1-grid.jpg" alt="img" style="zoom:50%;" />

```
Input: grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
Output: 6
Explanation: The answer is not 11, because the island must be connected 4-directionally.
```

**Example 2:**

```
Input: grid = [[0,0,0,0,0,0,0,0]]
Output: 0
```

**Constraints:**

-   `m == grid.length`
-   `n == grid[i].length`
-   `1 <= m, n <= 50`
-   `grid[i][j]` is either `0` or `1`.

### Solution

#### Standard Solution #1 DFS with Recursion

*   Search each grid, with directions up, down, left, and right recursion search.
*   Count the number of 1

```java
int[][] grid;
boolean[][] seen;

public int area(int r, int c){
    if (r < 0 || c < 0 || c >= grid[0].length || r >= grid.length|| 
        seen[r][c] == true || grid[r][c] == 0){
        return 0;
    }
    seen[r][c] = true;
    return 1 + area(r + 1, c) + area(r - 1, c) + area(r, c + 1)
        + area(r, c - 1);
}

public int maxAreaOfIsland(int[][] grid) {
    this.grid = grid;
    seen = new boolean[grid.length][grid[0].length];
    int ans = 0;
    for (int r = 0; r < grid.length; r++){
        for (int c = 0; c < grid[0].length; c++){
            ans = Math.max(ans, area(r, c));
        }
    }
    return ans;
}
```

-   Time Complexity: $O(R*C)$ where R is the number of rows in the given `grid`, and C is the number of columns. We visit every square once.
-   Space complexity: $O(R*C)$, the space used by `seen` to keep track of visited squares, and the space used by the call stack during our recursion.