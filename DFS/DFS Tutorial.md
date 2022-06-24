# DFS Tutorial

### Purpose

*   Traverse all vertices in a “graph”; Go deeper and deeper until cannot move on.
*   Traverse all paths between any two vertices in a “graph”.
*   Traverse all vertices
    *   Time Complexity: $O(V + E)$. Here, V represents the number of vertices, and E represents the number of edges. We need to check every vertex and traverse through every edge in the graph.
    *   Space Complexity: $O(V)$. Either the manually created stack or the recursive call stack can store up to V vertices.
*   Traverse all paths between two vertices
    *   Time Complexity: $O((V - 1)!)$ The above example is for an undirected graph. The worst-case scenario, when trying to find all paths, is a complete graph. A complete graph is a graph where every vertex is connected to every other vertex.
    *   Space Complexity: $O(V^3)$ 

## Find if Path Exists in Graph (Easy #1971)

**Question**: There is a **bi-directional** graph with `n` vertices, where each vertex is labeled from `0` to `n - 1` (**inclusive**). The edges in the graph are represented as a 2D integer array `edges`, where each `edges[i] = [ui, vi]` denotes a bi-directional edge between vertex `ui` and vertex `vi`. Every vertex pair is connected by **at most one** edge, and no vertex has an edge to itself.

You want to determine if there is a **valid path** that exists from vertex `source` to vertex `destination`.

Given `edges` and the integers `n`, `source`, and `destination`, return `true` *if there is a **valid path** from* `source` *to* `destination`*, or* `false` *otherwise**.*

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/08/14/validpath-ex1.png)

```
Input: n = 3, edges = [[0,1],[1,2],[2,0]], source = 0, destination = 2
Output: true
Explanation: There are two paths from vertex 0 to vertex 2:
- 0 → 1 → 2
- 0 → 2
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/08/14/validpath-ex2.png)

```
Input: n = 6, edges = [[0,1],[0,2],[3,5],[5,4],[4,3]], source = 0, destination = 5
Output: false
Explanation: There is no path from vertex 0 to vertex 5.
```

**Constraints:**

-   `1 <= n <= 2 * 105`
-   `0 <= edges.length <= 2 * 105`
-   `edges[i].length == 2`
-   `0 <= ui, vi <= n - 1`
-   `ui != vi`
-   `0 <= source, destination <= n - 1`
-   There are no duplicate edges.
-   There are no self edges.

### Standard Solution

#### Solution #1 DFS

*   Step 1: Create a neighbor list corresponding to the node index
*   Step 2: Create a `seen` array to record which nodes are already visited
*   Step 3: Create a stack to store and pop the node to help with the traversal process
    *   Add the source node to the stack
    *   If reach the destination, return true, if we have seen the node before, continue. Mark the current node as seen.
    *   Traverse the corresponding neighbor list and add them to the stack

```java
// abstract: if there is a valid path from source to destination
// DFS: 1. Record each vertex and neighbours in list
// 2. Keep finding the node until traverse all of them
public boolean validPath(int n, int[][] edges, int source, int destination) {
    List<List<Integer>> nodes = new ArrayList<>();
    for (int i = 0; i < n; i++){
        nodes.add(new ArrayList<>());
    }
    // neighbour list
    for (int[] edge : edges){
        nodes.get(edge[0]).add(edge[1]);
        nodes.get(edge[1]).add(edge[0]);
    }
    boolean[] seen = new boolean[n];

    Stack<Integer> stack = new Stack<>();
    stack.add(source);

    while(!stack.isEmpty()){
        int current = stack.pop();

        if (current == destination){
            return true;
        }
        if (seen[current]){
            continue;
        }
        seen[current] = true;

        // add the neighbours of current to the stack
        for (Integer neighbour : nodes.get(current)){
            stack.add(neighbour);
        }
    }
    return false;
}
```

-   Time Complexity: $O(V + E)$. Here, V represents the number of vertices, and E represents the number of edges.
    -   To create the adjacency list, we must iterate over each of the E edges.
    -   In the while loop, at most, we will visit vertex once.
    -   The for loop inside the while loop will have a cumulative sum of at most E iterations since it will iterate over all of the node's neighbors for each node.
-   Space Complexity: $O(V + E)$.
    -   The adjacency list will contain $O(V + E)$ elements.
    -   The stack will also contain $O(E)$ elements. However, this can be reduced to $O(V)$ by checking whether a `neighbor` node has been seen before adding it to the stack.
    -   The `seen` set will use $O(V)$ space to store the visited nodes.

## All Paths From Source to Target (Medium #797)

**Question**: Given a directed acyclic graph (**DAG**) of `n` nodes labeled from `0` to `n - 1`, find all possible paths from node `0` to node `n - 1` and return them in **any order**.

The graph is given as follows: `graph[i]` is a list of all nodes you can visit from node `i` (i.e., there is a directed edge from node `i` to node `graph[i][j]`).

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/09/28/all_1.jpg" alt="img" style="zoom:50%;" />

```
Input: graph = [[1,2],[3],[3],[]]
Output: [[0,1,3],[0,2,3]]
Explanation: There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/09/28/all_2.jpg" alt="img" style="zoom:50%;" />

```
Input: graph = [[4,3,1],[3,2,4],[3],[4],[]]
Output: [[0,4],[0,3,4],[0,1,3,4],[0,1,2,3,4],[0,1,4]]
```

**Constraints:**

-   `n == graph.length`
-   `2 <= n <= 15`
-   `0 <= graph[i][j] < n`
-   `graph[i][j] != i` (i.e., there will be no self-loops).
-   All the elements of `graph[i]` are **unique**.
-   The input graph is **guaranteed** to be a **DAG**.

### My Solution

*   Backtracking + DFS solution
*   The problem asks for all possible paths, therefore, backtracking is suitable for the problem

```java
private List<List<Integer>> res;
private int[][] graph;

// abstract: return all possible paths and return them in any order
public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
    this.graph = graph;

    // dfs: the neighbor list is already given
    // length of given array = number of nodes in graph

    res = new ArrayList<>();
    int target = graph.length - 1;
    List<Integer> path = new ArrayList<>();
    path.add(0);
    backtracking(0, path, target);
    return res;
}

public void backtracking(int current, List<Integer> path, int target){

    if (current == target){
        res.add(new ArrayList(path));
        return;
    }
    for (int neighbor : graph[current]){
        path.add(neighbor);
        backtracking(neighbor, path, target);
        path.remove(path.size() - 1);
    }
}
```

-   Time Complexity: $O(2^V \cdot V)$. Here, V represents the number of vertices.
    -   For a directed acyclic graph (DAG) with V vertices, there could be at most $2^{V - 1} - 1$ possible paths to go from the starting vertex to the target vertex. We need $O(V)$ time to build each such path.
    -   Therefore, a loose upper bound on the time complexity would be $(2^{V - 1} - 1) \cdot O(V) = O(2^V \cdot V)$
    -   Since we have overlap between the paths, the actual time spent on the traversal will be lower to some extent.
-   Space Complexity: $O(V)$ The recursion depth can be no more than V, and we need $O(V)$ space to store all the previously visited vertices while recursively traversing deeper with the current path. Please note that we don't count the space usage for the output, i.e., to store all the paths we obtained.

## Surrounded Regions (Medium #130)

**Question**: Given an `m x n` matrix `board` containing `'X'` and `'O'`, *capture all regions that are 4-directionally surrounded by* `'X'`.

A region is **captured** by flipping all `'O'`s into `'X'`s in that surrounded region.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/02/19/xogrid.jpg" alt="img" style="zoom:50%;" />

```
Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
Explanation: Surrounded regions should not be on the border, which means that any 'O' on the border of the board are not flipped to 'X'. Any 'O' that is not on the border and it is not connected to an 'O' on the border will be flipped to 'X'. Two cells are connected if they are adjacent cells connected horizontally or vertically.
```

**Example 2:**

```
Input: board = [["X"]]
Output: [["X"]]
```

**Constraints:**

-   `m == board.length`
-   `n == board[i].length`
-   `1 <= m, n <= 200`
-   `board[i][j]` is `'X'` or `'O'`.

### Standard Solution

#### Solution #1 DFS

*   Concerning the traversal of the 2D grid, the goal of the problem is to mark those captured cells
*   We select all the cells that are located on the borders of the board.
*   Starting from each of the above-selected cell, we then perform the *DFS* traversal.
    *   If a cell on the border happens to be `O`, then we know that this cell is *alive*, together with the other `O` cells that are *connected* to this border cell, based on the description of the problem. Two cells are *connected*, if there exists a path consisting of only `O` letter that bridges between the two cells.
    *   Based on the above conclusion, the goal of our DFS traversal would be to *mark* out all those ***connected*** `O` cells that is originated from the border, with any distinguished letter such as `E`.
*   Once we iterate through all border cells, we would then obtain three types of cells:
    *   The one with the `X` letter: the cell that we could consider as the wall.
    *   The one with the `O` letter: the cells that are spared in our *DFS* traversal, *i.e.* these cells has no connection to the border, therefore they are ***captured***. We then should replace these cell with `X` letter.
    *   The one with the `E` letter: these are the cells that are marked during our DFS traversal, *i.e.* these are the cells that has at least one connection to the borders, therefore they are not *captured*. As a result, we would revert the cell to its original letter `O`.

```java
public class Solution {
  protected Integer ROWS = 0;
  protected Integer COLS = 0;

  public void solve(char[][] board) {
    if (board == null || board.length == 0) {
      return;
    }
    this.ROWS = board.length;
    this.COLS = board[0].length;

    List<Pair<Integer, Integer>> borders = new LinkedList<Pair<Integer, Integer>>();
    // Step 1). construct the list of border cells
    for (int r = 0; r < this.ROWS; ++r) {
      borders.add(new Pair(r, 0));
      borders.add(new Pair(r, this.COLS - 1));
    }
    for (int c = 0; c < this.COLS; ++c) {
      borders.add(new Pair(0, c));
      borders.add(new Pair(this.ROWS - 1, c));
    }

    // Step 2). mark the escaped cells
    for (Pair<Integer, Integer> pair : borders) {
      this.DFS(board, pair.first, pair.second);
    }

    // Step 3). flip the cells to their correct final states
    for (int r = 0; r < this.ROWS; ++r) {
      for (int c = 0; c < this.COLS; ++c) {
        if (board[r][c] == 'O')
          board[r][c] = 'X';
        if (board[r][c] == 'E')
          board[r][c] = 'O';
      }
    }
  }

  protected void DFS(char[][] board, int row, int col) {
    if (board[row][col] != 'O')
      return;

    board[row][col] = 'E';
    if (col < this.COLS - 1)
      this.DFS(board, row, col + 1);
    if (row < this.ROWS - 1)
      this.DFS(board, row + 1, col);
    if (col > 0)
      this.DFS(board, row, col - 1);
    if (row > 0)
      this.DFS(board, row - 1, col);
  }
}


class Pair<U, V> {
  public U first;
  public V second;

  public Pair(U first, V second) {
    this.first = first;
    this.second = second;
  }
}

```

-   Time Complexity: $\mathcal{O}(N)$ where N is the number of cells in the board. In the worst case where it contains only the `O` cells on the board, we would traverse each cell twice: once during the DFS traversal and the other time during the cell reversion in the last step.
-   Space Complexity: $\mathcal{O}(N)$ where N is the number of cells in the board. There are mainly two places that we consume some additional memory.
    -   We keep a list of border cells as starting points for our traversal. We could consider the number of border cells is proportional to the total number (N) of cells.
    -   During the recursive calls of `DFS()` function, we would consume some space in the function call stack, *i.e.* the call stack will pile up along with the depth of recursive calls. And the maximum depth of recursive calls would be N as in the worst scenario mentioned in the time complexity.
    -   As a result, the overall space complexity of the algorithm is $\mathcal{O}(N)$