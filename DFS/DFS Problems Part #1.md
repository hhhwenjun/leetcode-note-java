# DFS Problems Part #1

## Max Area of Island(Medium #695)

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

## Number of Islands(Medium #200)

**Question**: Given an `m x n` 2D binary grid `grid` which represents a map of `'1'`s (land) and `'0'`s (water), return *the number of islands*.

An **island** is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

**Example 1:**

```
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
```

**Example 2:**

```
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
```

**Constraints:**

-   `m == grid.length`
-   `n == grid[i].length`
-   `1 <= m, n <= 300`
-   `grid[i][j]` is `'0'` or `'1'`.

### My Solution

```java
char[][] grid;
boolean[][] seen;

public int numIslands(char[][] grid) {
    this.grid = grid;
    int islandNum = 0;
    this.seen = new boolean[grid.length][grid[0].length];
    for (int i = 0; i < grid.length; i++){
        for (int j = 0; j < grid[0].length; j++){
            islandNum += countIsland(i, j);
        }
    }
    return islandNum;
}
// count if this is an island
public int countIsland(int i, int j){
    if (i < 0 || i >= grid.length || j >= grid[0].length || j < 0 || seen[i][j] || grid[i][j] == '0'){
        return 0;
    }
    seen[i][j] = true;

    countIsland(i - 1, j);
    countIsland(i + 1, j);
    countIsland(i, j - 1);
    countIsland(i, j + 1);

    return 1;
}
```

### Standard Solution

#### Solution #1 DFS

*   Similar to my solution
*   Change the value to 0 when seen

```java
class Solution {
  void dfs(char[][] grid, int r, int c) {
    int nr = grid.length;
    int nc = grid[0].length;

    if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
      return;
    }

    grid[r][c] = '0';
    dfs(grid, r - 1, c);
    dfs(grid, r + 1, c);
    dfs(grid, r, c - 1);
    dfs(grid, r, c + 1);
  }

  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }

    int nr = grid.length;
    int nc = grid[0].length;
    int num_islands = 0;
    for (int r = 0; r < nr; ++r) {
      for (int c = 0; c < nc; ++c) {
        if (grid[r][c] == '1') {
          ++num_islands;
          dfs(grid, r, c);
        }
      }
    }
    return num_islands;
  }
}
```

-   Time complexity: $O(M \times N)$ where M is the number of rows and N is the number of columns.
-   Space complexity: worst-case $O(M \times N)$ in case that the grid map is filled with lands where DFS goes by $M \times N$ deep.

## Robot Return to Origin (Easy #657)

**Question**: There is a robot starting at the position `(0, 0)`, the origin, on a 2D plane. Given a sequence of its moves, judge if this robot **ends up at** `(0, 0)` after it completes its moves.

You have given a string `moves` that represent the move sequence of the robot where `moves[i]` represent its `ith` move. Valid moves are `'R'` (right), `'L'` (left), `'U'` (up), and `'D'` (down).

Return `true` *if the robot returns to the origin after it finishes all of its moves, or* `false` *otherwise*.

**Note**: The way that the robot is "facing" is irrelevant. `'R'` will always make the robot move to the right once, `'L'` will always make it move left, etc. Also, assume that the magnitude of the robot's movement is the same for each move.

**Example 1:**

```
Input: moves = "UD"
Output: true
Explanation: The robot moves up once, and then down once. All moves have the same magnitude, so it ended up at the origin where it started. Therefore, we return true.
```

**Example 2:**

```
Input: moves = "LL"
Output: false
Explanation: The robot moves left twice. It ends up two "moves" to the left of the origin. We return false because it is not at the origin at the end of its moves.
```

**Constraints:**

-   `1 <= moves.length <= 2 * 104`
-   `moves` only contains the characters `'U'`, `'D'`, `'L'` and `'R'`.

### My Solution

*   Use two stacks and calculate the sum to determine the origin
*   Time and space complexity should be both $O(N)$, but since it involves multiple for loops, so the speed is slow

```java
/**
* abstract: check the robot whether return to origin
* 1. use two stacks represent horizontal/vertical directions
* 2. when move, we add +1/-1 elements inside the stack
* 3. determine all elements in the stack add up to 0
*/
public boolean judgeCircle(String moves) {
    Stack<Integer> horizontal = new Stack<>();
    Stack<Integer> vertical = new Stack<>();

    for (char move : moves.toCharArray()){
        if (move == 'U'){
            vertical.add(1);
        }
        else if (move == 'D'){
            vertical.add(-1);
        }
        else if (move == 'L'){
            horizontal.add(1);
        }
        else if (move == 'R'){
            horizontal.add(-1);
        }
    }

    // count the sum of the two stacks, if 0 true, if != 0, false
    int sum = 0;
    for (Integer elem : horizontal){
        sum += elem;
    }
    if (sum != 0){
        return false;
    }
    for (Integer elem : vertical){
        sum += elem;
    }
    return sum == 0;
}
```

### Standard Solution 

#### Solution #1 Simulation

*   Similar to my idea, but actually we can only use two constants but not two stacks

```java
class Solution {
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        for (char move: moves.toCharArray()) {
            if (move == 'U') y--;
            else if (move == 'D') y++;
            else if (move == 'L') x--;
            else if (move == 'R') x++;
        }
        return x == 0 && y == 0;
    }
}
```

-   Time Complexity: $O(N)$, where N is the length of `moves`. We iterate through the string.
-   Space Complexity: $O(1)$. In Java, our character array is $O(N)$.

## Number of Connected Components in an Undirected Graph (Medium #323)

**Question**: You have a graph of `n` nodes. You are given an integer `n` and array `edges` where `edges[i] = [ai, bi]` indicates that there is an edge between `ai` and `bi` in the graph.

Return *the number of connected components in the graph*.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/03/14/conn1-graph.jpg" alt="img" style="zoom:50%;" />

```
Input: n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/03/14/conn2-graph.jpg" alt="img" style="zoom:50%;" />

```
Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
Output: 1
```

**Constraints:**

-   `1 <= n <= 2000`
-   `1 <= edges.length <= 5000`
-   `edges[i].length == 2`
-   `0 <= ai <= bi < n`
-   `ai != bi`
-   There are no repeated edges.

### Standard Solution

#### Solution #1 DFS

*   The problem has given the number of nodes, we can use it to create a 1D `visited` array.
*   Create a list array, and use it to store neighbors by the edges
*   Loop through the nodes and make all the neighbors as `visited`

```java
// dfs
private void dfs(List<Integer>[] adjList, int[] visited, int startNode){
    visited[startNode] = 1;
    for (int i = 0; i < adjList[startNode].size(); i++){
        if (visited[adjList[startNode].get(i)] == 0){
            // find all neighbours' neighbours
            dfs(adjList, visited, adjList[startNode].get(i));
        }
    }
}

public int countComponents(int n, int[][] edges) {
    int components = 0;
    int[] visited = new int[n];

    List<Integer>[] adjList = new ArrayList[n];
    for (int i = 0; i < n; i++){
        adjList[i] = new ArrayList<>();
    }
    // record the edges in the adjacent list
    for (int i = 0; i < edges.length; i++){
        adjList[edges[i][0]].add(edges[i][1]);
        adjList[edges[i][1]].add(edges[i][0]);
    }
    // loop through the nodes and adjacent nodes
    for (int i = 0; i < n; i++){
        if (visited[i] == 0){
            components++;
            dfs(adjList, visited, i);
        }
    }
    return components;
}
```

*   Here $E$ = Number of edges, $V$ = Number of vertices.

*   Time complexity: ${O}(E + V)$

    Building the adjacency list will take ${O}(E)$ operations, as we iterate over the list of edges once, and insert each edge into two lists.

    During the DFS traversal, each vertex will only be visited once. This is because we mark each vertex as visited as soon as we see it, and then we only visit vertices that are not marked as visited. In addition, when we iterate over the edge list of each vertex, we look at each edge once. This has a total cost of ${O}(E + V).$

*   Space complexity: ${O}(E + V)$

    Building the adjacency list will take ${O}(E)$ space. To keep track of visited vertices, an array of size ${O}(V)$ is required. Also, the run-time stack for DFS will use ${O}(V)$ space.

#### Solution #2 Union Find

*   Initialize a variable `count` with the number of vertices in the input.
*   Traverse all of the edges one by one, performing the union-find method `combine` on each edge. If the endpoints are already in the same set, then keep traversing. If they are not, then decrement `count` by 1.
*   After traversing all of the `edges`, the variable `count` will contain the number of components in the graph.

```java
public class Solution {

    private int find(int[] representative, int vertex) {
        if (vertex == representative[vertex]) {
            return vertex;
        }
        return representative[vertex] = find(representative, representative[vertex]);
    }
    
    private int combine(int[] representative, int[] size, int vertex1, int vertex2) {
        vertex1 = find(representative, vertex1);
        vertex2 = find(representative, vertex2);
        
        if (vertex1 == vertex2) {
            return 0;
        } else {
            if (size[vertex1] > size[vertex2]) {
                size[vertex1] += size[vertex2];
                representative[vertex2] = vertex1;
            } else {
                size[vertex2] += size[vertex1];
                representative[vertex1] = vertex2;
            }
            return 1;
        }
    }

    public int countComponents(int n, int[][] edges) {
        int[] representative = new int[n];
        int[] size = new int[n];
        
        for (int i = 0; i < n; i++) {
            representative[i] = i;
            size[i] = 1;
        }
        
        int components = n;
        for (int i = 0; i < edges.length; i++) { 
            components -= combine(representative, size, edges[i][0], edges[i][1]);
        }

        return components;
    }
}
```

-   Time complexity: $O(E\cdotα(n))$.

    Iterating over every edge requires $O(E)$ operations, and for every operation, we are performing the `combine` method which is $O(α(n))$, where $α(n)$ is the inverse Ackermann function.

-   Space complexity: $O(V)$.

    Storing the representative/immediate-parent of each vertex takes $O(V)$ space. Furthermore, storing the size of components also takes $O(V)$ space.

## Graph Valid Tree (Medium #261)

**Question**: You have a graph of `n` nodes labeled from `0` to `n - 1`. You are given an integer n and a list of `edges` where `edges[i] = [ai, bi]` indicates that there is an undirected edge between nodes `ai` and `bi` in the graph.

Return `true` *if the edges of the given graph make up a valid tree, and* `false` *otherwise*.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/03/12/tree1-graph.jpg" alt="img" style="zoom:67%;" />

```
Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
Output: true
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/03/12/tree2-graph.jpg" alt="img" style="zoom:67%;" />

```
Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
Output: false
```

**Constraints:**

-   `1 <= n <= 2000`
-   `0 <= edges.length <= 5000`
-   `edges[i].length == 2`
-   `0 <= ai, bi < n`
-   `ai != bi`
-   There are no self-loops or repeated edges.

### Standard Solution

*   Use DFS
    *   Create an adjacency list, to store the neighbors for the node
    *   Use stack or queue to store the nodes, pop the node and traverse its' neighbors
    *   Skip the nodes if it has been seen(due to previous nodes), return false if it has been marked as a parent(cycle in nodes)

#### Solution #1 Iterative DFS

*   For the graph to be a valid tree, it must have *exactly* `n - 1` edges. Any less, and it can't possibly be fully connected.
*   Any more, and it *has* to contain cycles. 
*   Check if it has `n - 1` edge, if yes, we do not need to consider cycle situations

```java
public boolean validTree(int n, int[][] edges) {
   if (edges.length != n - 1) return false;

   // make the adjacency list
    List<List<Integer>> adjacencyList = new ArrayList<>();
    for (int i = 0; i < n; i++){
        adjacencyList.add(new ArrayList<>());
    }
    for (int[] edge : edges){
        adjacencyList.get(edge[0]).add(edge[1]);
        adjacencyList.get(edge[1]).add(edge[0]);
    }

    Stack<Integer> stack = new Stack<>();
    Set<Integer> seen = new HashSet<>();
    stack.push(0);
    seen.add(0);

    while(!stack.isEmpty()){
        int node = stack.pop();
        for (int neighbour : adjacencyList.get(node)){
            if (seen.contains(neighbour)) continue;
            seen.add(neighbour);
            stack.push(neighbour);
        }
    }

    return seen.size() == n;
}
```

*   Time complexity and the space complexity are both $O(n)$

*   If we do not check the edges, there is another solution

```java
List<List<Integer>> adjacencyList = new ArrayList<>();
for (int i = 0; i < n; i++) {
    adjacencyList.add(new ArrayList<>());
}
for (int[] edge : edges) {
    adjacencyList.get(edge[0]).add(edge[1]);
    adjacencyList.get(edge[1]).add(edge[0]);
}

Map<Integer, Integer> parent = new HashMap<>();
parent.put(0, -1);
Stack<Integer> stack = new Stack<>();
stack.push(0);

while (!stack.isEmpty()) {
    int node = stack.pop();
    // Check for unseen neighbours of this node:
    for (int neighbour : adjacencyList.get(node)) {
        // Don't look at the trivial cycle.
        if (parent.get(node) == neighbour) {
            continue;
        }
        // Check if we've already seen this node.
        if (parent.containsKey(neighbour)) {
            return false; // There must be a cycle.
        }
        // Otherwise, put this neighbour onto stack
        // and record that it has been seen.
        stack.push(neighbour);
        parent.put(neighbour, node);
    }
}

return parent.size() == n; 
```

#### Solution #2 Union Find

*   Use `union-find` to merge the nodes together, if when merge, the nodes are already in the same set, meaning they construct a loop already.

```java
class UnionFind {
    
    private int[] parent;
    
    // For efficiency, we aren't using makeset, but instead initialising
    // all the sets at the same time in the constructor.
    public UnionFind(int n) {
        parent = new int[n];
        for (int node = 0; node < n; node++) {
            parent[node] = node;
        }
    }
    
    // The find method, without any optimizations. It traces up the parent
    // links until it finds the root node for A, and returns that root.
    public int find(int A) {
        while (parent[A] != A) {
            A = parent[A];
        }
        return A;
    }

    // The union method, without any optimizations. It returns True if a
    // merge happened, False if otherwise.
    public boolean union(int A, int B) {
        // Find the roots for A and B.
        int rootA = find(A);
        int rootB = find(B);
        // Check if A and B are already in the same set.
        if (rootA == rootB) {
            return false;
        }
        // Merge the sets containing A and B.
        parent[rootA] = rootB;
        return true;
    } 
}
class Solution {
    
    public boolean validTree(int n, int[][] edges) {     
        // Condition 1: The graph must contain n - 1 edges.
        if (edges.length != n - 1) return false;
        
        // Condition 2: The graph must contain a single connected component.
        // Create a new UnionFind object with n nodes. 
        UnionFind unionFind = new UnionFind(n);
        // Add each edge. Check if a merge happened, because if it 
        // didn't, there must be a cycle.
        for (int[] edge : edges) {
            int A = edge[0];
            int B = edge[1];
            if (!unionFind.union(A, B)) {
                return false;
            }
        }
        // If we got this far, there's no cycles!
        return true;
    }
}
```

*   Time Complexity: $O(N \cdot α(N))$.
*   Space Complexity: $O(N)$.