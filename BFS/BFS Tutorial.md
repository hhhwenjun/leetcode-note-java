# BFS Tutorial

### Purpose

*   The most advantageous use case of “breadth-first search” is to efficiently find the shortest path between two vertices in a “graph” where **all edges have equal and positive weights**.

*   Time Complexity: $O(V + E)$. Here, V represents the number of vertices, and E represents the number of edges. We need to check every vertex and traverse through every edge in the graph. The time complexity is the same as it was for the DFS approach.
*   Space Complexity: $O(V)$. Generally, we will check if a vertex has been visited before adding it to the queue, so the queue will use at most $O(V)$ space. Keeping track of which vertices have been visited will also require $O(V)$ space.

## Shortest Path in Binary Matrix (Medium #1091)

**Question**: Given an `n x n` binary matrix `grid`, return *the length of the shortest **clear path** in the matrix*. If there is no clear path, return `-1`.

A **clear path** in a binary matrix is a path from the **top-left** cell (i.e., `(0, 0)`) to the **bottom-right** cell (i.e., `(n - 1, n - 1)`) such that:

-   All the visited cells of the path are `0`.
-   All the adjacent cells of the path are **8-directionally** connected (i.e., they are different and they share an edge or a corner).

The **length of a clear path** is the number of visited cells of this path.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/02/18/example1_1.png" alt="img" style="zoom:33%;" />

```
Input: grid = [[0,1],[1,0]]
Output: 2
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/02/18/example2_1.png" alt="img" style="zoom: 33%;" />

```
Input: grid = [[0,0,0],[1,1,0],[1,1,0]]
Output: 4
```

**Example 3:**

```
Input: grid = [[1,0,0],[1,1,0],[1,1,0]]
Output: -1
```

**Constraints:**

-   `n == grid.length`
-   `n == grid[i].length`
-   `1 <= n <= 100`
-   `grid[i][j] is 0 or 1`

### Standard Solution

#### Solution #1 BFS, Overwriting Input

*   To be noticed, each cell you can go in 8 directions, which means it does not have to always "go down"(dynamic programming solution)
*   In this way, could not use dynamic programming, we need to traverse the neighbors of each possible cell (which makes it BFS).
*   Each time we go to a possible cell (value is 0), we collect the neighbor cells of it and store them into a queue

```java
private static final int[][] directions = new int[][]{{-1, -1},
                                                      {-1, 0}, {-1, 1}, {0, -1}, {0, 1},
                                                      {1, -1}, {1, 0}, {1, 1}};
public int shortestPathBinaryMatrix(int[][] grid) {
    // firstly, we need to check that the start and target cells are open
    if (grid[0][0] != 0 || grid[grid.length - 1][grid.length - 1] != 0) return -1;
    
    // set up the bfs queue
    Queue<int[]> queue = new ArrayQueue<>();
    grid[0][0] = 1; // make distance as 1
    queue.add(new int[]{0, 0}); // add the first cell to the queue
    
    // carry out the BFS
    while (!queue.isEmpty()){
        int[] cell = queue.remove();
        int row = cell[0];
        int col = cell[1];
        int distance = grid[row][col];
        // if reach the target 
        if (row == grid.length - 1 && col == grid[0].length - 1){
            return distance;
        }
        // otherwise, put neighbors to the queue
        for (int[] neighbor : getNeighbors(row, col, grid)){
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            queue.add(new int[]{neighborRow, neighborCol});
            grid[neighborRow][neighborCol] = distance + 1; // add to the distance
        }
    }
    return -1;
}

private List<int[]> getNeighbors(int row, int col, int[][] grid){
    List<int[]> neighbors = new ArrayList<>();
    for (int i = 0; i < directions.length; i++){
        int newRow = row + directions[i][0];
        int newCol = col + directions[i][1];
        if (newRow < 0 || newCol < 0 || newRow >= grid.length || newCol >= grid[0].length
           || grid[newRow][newCol] != 0){
            continue;
        }
        neighbors.add(new int[]{newRow, newCol});
    }
    return neighbors;
}
```

*   Let `N` be the number of cells in the grid. Time complexity: $O(N)$.
*   Space complexity: $O(N)$. The only additional space we used was the queue. We determined above that at most, we enqueued N cells. Therefore, an upper bound on the worst-case space complexity is $O(N)$.

