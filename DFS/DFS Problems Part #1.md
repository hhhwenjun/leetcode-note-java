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

### My Solution

```java
int[][] grid;
int[][] directions = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

public int maxAreaOfIsland(int[][] grid) {
    this.grid = grid;

    // dfs to find each island, for each island, record the area, compare to find max
    // 1. for loop to search each cell, to see if it is the start of a new island
    int length = grid.length;
    int width = grid[0].length;
    int max = 0;
    for (int i = 0; i < length; i++){
        for (int j = 0; j < width; j++){
            if (grid[i][j] == 1){
                // dfs here to find island area
                max = Math.max(max, dfs(i, j));
            }
        }
    }
    return max;
}

public int dfs(int i, int j){
    // 2. for each cell we visited, flip it to 0
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0){
        return 0;
    }
    // 3. add 1 to area if the current cell has value 1
    grid[i][j] = 0;
    int area = 1;
    // find neighbor islands in 4 directions
    for (int[] direction : directions){
        area += dfs(i + direction[0], j + direction[1]);
    }
    return area;
}
```

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

#### Solution #2 BFS

```java
class Solution {
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
          grid[r][c] = '0'; // mark as visited
          Queue<Integer> neighbors = new LinkedList<>();
            // encode the row and col info
          neighbors.add(r * nc + c);
          while (!neighbors.isEmpty()) {
            int id = neighbors.remove();
            int row = id / nc;
            int col = id % nc;
            if (row - 1 >= 0 && grid[row-1][col] == '1') {
              neighbors.add((row-1) * nc + col);
              grid[row-1][col] = '0';
            }
            if (row + 1 < nr && grid[row+1][col] == '1') {
              neighbors.add((row+1) * nc + col);
              grid[row+1][col] = '0';
            }
            if (col - 1 >= 0 && grid[row][col-1] == '1') {
              neighbors.add(row * nc + col-1);
              grid[row][col-1] = '0';
            }
            if (col + 1 < nc && grid[row][col+1] == '1') {
              neighbors.add(row * nc + col+1);
              grid[row][col+1] = '0';
            }
          }
        }
      }
    }
    return num_islands;
  }
}
```

-   Time complexity: $O(M \times N)$ where M is the number of rows and N is the number of columns.
-   Space complexity: $O(min(M, N))$ because in worst case where the grid is filled with lands, the size of queue can grow up to $min(M,N)$.

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

## All Paths from Source Lead to Destination (Medium #1059)

**Question**: Given the `edges` of a directed graph where `edges[i] = [ai, bi]` indicates there is an edge between nodes `ai` and `bi`, and two nodes `source` and `destination` of this graph, determine whether or not all paths starting from `source` eventually, end at `destination`, that is:

-   At least one path exists from the `source` node to the `destination` node
-   If a path exists from the `source` node to a node with no outgoing edges, then that node is equal to `destination`.
-   The number of possible paths from `source` to `destination` is a finite number.

Return `true` if and only if all roads from `source` lead to `destination`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2019/03/16/485_example_1.png" alt="img" style="zoom:50%;" />

```
Input: n = 3, edges = [[0,1],[0,2]], source = 0, destination = 2
Output: false
Explanation: It is possible to reach and get stuck on both node 1 and node 2.
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2019/03/16/485_example_2.png" alt="img" style="zoom:50%;" />

```
Input: n = 4, edges = [[0,1],[0,3],[1,2],[2,1]], source = 0, destination = 3
Output: false
Explanation: We have two possibilities: to end at node 3, or to loop over node 1 and node 2 indefinitely.
```

**Example 3:**

<img src="https://assets.leetcode.com/uploads/2019/03/16/485_example_3.png" alt="img" style="zoom:50%;" />

```
Input: n = 4, edges = [[0,1],[0,2],[1,3],[2,3]], source = 0, destination = 3
Output: true
```

**Constraints:**

-   `1 <= n <= 104`
-   `0 <= edges.length <= 104`
-   `edges.length == 2`
-   `0 <= ai, bi <= n - 1`
-   `0 <= source <= n - 1`
-   `0 <= destination <= n - 1`
-   The given graph may have self-loops and parallel edges.

### Standard Solution

#### Solution #1 DFS + Backtracking

*   Build up `adjList` as the neighbor node list, using hashmap structure
*   We need to make sure that:
    *   The destination node does not have outgoing edges, otherwise, it is not the destination
    *   The non-destination node should have outgoing edges, otherwise, we stuck at the node
    *   Cannot be stuck in a cycle (use `seen` array to make sure we are not in the cycle)
*   Use the neighbor list to traverse the outgoing nodes, if seen, it is in the cycle, and track if the forwarding nodes return false(cannot go to the destination)
*   Use backtracking to flip the status if finish traversing the current node's neighbors

```java
// dfs: find the neighbor nodes that the node point to
public boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
    Map<Integer, List<Integer>> direct = new HashMap<>();
    for (int i = 0; i < n; i++){
        direct.put(i, new ArrayList<Integer>());
    }
    for (int[] edge : edges){
        direct.get(edge[0]).add(edge[1]);
    }
    // destination does not have outgoing edge
    if (direct.get(destination).size() != 0) return false;
    return dfs(direct, source, destination, new boolean[n]);
}

public boolean dfs(Map<Integer, List<Integer>> direct, int source, int destination, boolean[] seen){
    if (source == destination){
        return true;
    }
    // now we have seen the node
    seen[source] = true;
    // non-destination should have outgoing edges otherwise we stuck at here
    if (direct.get(source).size() == 0) return false;

    // traverse the neighbor destination nodes
    for (int neighbor : direct.get(source)){
        if (seen[neighbor] || !dfs(direct, neighbor, destination, seen)){
            return false;
        }
    }
    // backtracking to the before status
    seen[source] = false;
    return true;
}
```

*   Time complexity: Typically for an entire DFS over an input graph, it takes $\mathcal{O}(V + E)$ where V represents the number of vertices in the graph and likewise, E represents the number of edges in the graph. In the worst-case E can be $\mathcal{O}(V^2)$ in case each vertex is connected to every other vertex in the graph. However, even in the worst case, we will end up discovering a cycle very early on and prune the recursion tree. If we were to traverse the entire graph, then the complexity would be $\mathcal{O}(V^2)$ as the $\mathcal{O}(E)$ the part would dominate. However, due to pruning and backtracking in case of cycle detection, we end up with overall time complexity of $\mathcal{O}(V)$.
*   Space Complexity: $\mathcal{O}(V + E)$ where $\mathcal{O}(E)$ is occupied by the adjacency list and $\mathcal{O}(V)$ is occupied by the recursion stack and the color states.

## Reconstruct Itinerary (Hard #332)

**Question**: You are given a list of airline `tickets` where `tickets[i] = [fromi, toi]` represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.

All of the tickets belong to a man who departs from `"JFK"`, thus, the itinerary must begin with `"JFK"`. If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.

-   For example, the itinerary `["JFK", "LGA"]` has a smaller lexical order than `["JFK", "LGB"]`.

You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/03/14/itinerary1-graph.jpg)

```
Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
Output: ["JFK","MUC","LHR","SFO","SJC"]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/03/14/itinerary2-graph.jpg)

```
Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
```

**Constraints:**

-   `1 <= tickets.length <= 300`
-   `tickets[i].length == 2`
-   `fromi.length == 3`
-   `toi.length == 3`
-   `fromi` and `toi` consist of uppercase English letters.
-   `fromi != toi`

### My Solution

```java
// use hashmap to store the adjacency list and remove node if traversed (dfs)
// sort the adjacency list based on the spell
private List<String> res = new ArrayList<String>();

public List<String> findItinerary(List<List<String>> tickets) {
    // use hashmap to store the adjacency list
    Map<String, List<String>> flights = new HashMap<>();
    for (List<String> ticket : tickets){
        String key = ticket.get(0);
        String dest = ticket.get(1);
        if (!flights.containsKey(key)){
            flights.put(key, new ArrayList<String>());
        }
        flights.get(key).add(dest);
    }

    // sort the adjacency list
    for (List<String> flight : flights.values()){
        Collections.sort(flight);
    }
    dfs(flights, "JFK");
    return res;
}

public void dfs(Map<String, List<String>> flights, String start){
    if (flights.containsKey(start)){
        // take the adjacency list
        List<String> adjList = flights.get(start);

        while (!adjList.isEmpty()){
            String dest = adjList.get(0);
            adjList.remove(0);
            dfs(flights, dest);
        }
    }
    // have traverse this node, add to the front
    res.add(0, start);
}
```

*   It is also called the eulerian trail, it visits every edge exactly once.
*   Find the adjacency list and sort the list, each time remove the first element in the list
*   If the list is empty, it means we finish the traversal process. 
*   Time Complexity: $\mathcal{O}(|E| \log{\frac{|E|}{|V|}})$ where $|E|$ is the number of edges (flights) in the input.
*   Space Complexity: $\mathcal{O}(|V| + |E|)$ where $|V|$ is the number of airports and $|E|$ is the number of flights.

## Number of Distinct Islands (Medium #694)

**Question**: You are given an `m x n` binary matrix `grid`. An island is a group of `1`'s (representing land) connected **4-directionally** (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

An island is considered to be the same as another if and only if one island can be translated (and not rotated or reflected) to equal the other.

Return *the number of **distinct** islands*. 

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/05/01/distinctisland1-1-grid.jpg" alt="img" style="zoom:50%;" />

```
Input: grid = [[1,1,0,0,0],[1,1,0,0,0],[0,0,0,1,1],[0,0,0,1,1]]
Output: 1
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/05/01/distinctisland1-2-grid.jpg" alt="img" style="zoom:50%;" />

```
Input: grid = [[1,1,0,1,1],[1,0,0,0,0],[0,0,0,0,1],[1,1,0,1,1]]
Output: 3
```

**Constraints:**

-   `m == grid.length`
-   `n == grid[i].length`
-   `1 <= m, n <= 50`
-   `grid[i][j]` is either `0` or `1`.

### My Solution

*   Find islands and check if they are distinct by subtracting the difference. Since we use a certain direction for traversal, the order of traversal is the same.

```java
private int[][] grid;
private boolean[][] seen;
private List<List<int[]>> islandInfo;
private int[][] directions = new int[][]{{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

public int numDistinctIslands(int[][] grid) {
    this.grid = grid;
    seen = new boolean[grid.length][grid[0].length];
    islandInfo = new ArrayList<>();
    int count = 0;
    // dfs: find the islands, store islands in lists
    for (int i = 0; i < grid.length; i++){
        for (int j = 0; j < grid[0].length; j++){
            if (seen[i][j]){
                continue;
            }
            if (grid[i][j] == 1){
                // find a new island
                List<int[]> island = new ArrayList<>();
                dfs(i, j, island);
                if (checkDistinct(island)){
                    count++;
                    islandInfo.add(island);
                }
            }
        }
    }
    return count;
}

public void dfs(int i, int j, List<int[]> island){
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == 0 || seen[i][j]){
        return;
    }
    seen[i][j] = true;
    island.add(new int[]{i, j});
    for (int[] direction : directions){
        dfs(i + direction[0], j + direction[1], island);
    }
}

// check islands if they are distinct
public boolean checkDistinct(List<int[]> island){
    if (islandInfo.size() == 0) return true;

    for (int i = 0; i < islandInfo.size(); i++){
        List<int[]> islandCompare = islandInfo.get(i);
        if (islandCompare.size() != island.size()){
            continue;
        }
        else {
            boolean same = true;
            int diffX = islandCompare.get(0)[0] - island.get(0)[0];
            int diffY = islandCompare.get(0)[1] - island.get(0)[1];
            for (int j = 0; j < island.size(); j++){
                int currdiffX = islandCompare.get(j)[0] - island.get(j)[0];
                int currdiffY = islandCompare.get(j)[1] - island.get(j)[1];
                if (diffX != currdiffX || diffY != currdiffY){
                    same = false;
                }
            }
            if (same == true){
                return false;
            }
        }
    }
    return true;
}
```

```java
// a standard method similar to my method
class Solution {

    private List<List<int[]>> uniqueIslands = new ArrayList<>(); // All known unique islands.
    private List<int[]> currentIsland = new ArrayList<>(); // Current Island
    private int[][] grid; // Input grid
    private boolean[][] seen; // Cells that have been explored. 
     
    public int numDistinctIslands(int[][] grid) {   
        this.grid = grid;
        this.seen = new boolean[grid.length][grid[0].length];   
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                dfs(row, col);
                if (currentIsland.isEmpty()) {
                    continue;
                }
                // Translate the island we just found to the top left.
                int minCol = grid[0].length - 1;
                for (int i = 0; i < currentIsland.size(); i++) {
                    minCol = Math.min(minCol, currentIsland.get(i)[1]);
                }
                for (int[] cell : currentIsland) {
                    cell[0] -= row;
                    cell[1] -= minCol;
                }
                // If this island is unique, add it to the list.
                if (currentIslandUnique()) {
                    uniqueIslands.add(currentIsland);
                }
                currentIsland = new ArrayList<>();
            }
        }
        return uniqueIslands.size();
    }
    
    private void dfs(int row, int col) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length) return;
        if (seen[row][col] || grid[row][col] == 0) return;
        seen[row][col] = true;
        currentIsland.add(new int[]{row, col});
        dfs(row + 1, col);
        dfs(row - 1, col);
        dfs(row, col + 1);
        dfs(row, col - 1);
    }
    
    private boolean currentIslandUnique() {
        for (List<int[]> otherIsland : uniqueIslands) {
            if (currentIsland.size() != otherIsland.size()) continue;
            if (equalIslands(currentIsland, otherIsland)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean equalIslands(List<int[]> island1, List<int[]> island2) {
        for (int i = 0; i < island1.size(); i++) {
            if (island1.get(i)[0] != island2.get(i)[0] || island1.get(i)[1] != island2.get(i)[1]) {
                return false;
            }
        }
        return true;
    }
}
```

*   Basically, my method is brute force with dfs. Time Complexity: $O(M^2 \cdot N^2)$. In the worst case, we would have a large grid, with many unique islands all of the same size, and the islands packed as closely together as possible. This would mean that for each island we discover, we'd be looping over the cells of all the other islands we've discovered so far. For example, here's a grid with M = 10, N = 10, and islands all of size 5.

*   Space complexity: $O(N \cdot M)$

    The `seen` set requires $O(N \cdot M)$ memory. Additionally, each cell with land requires $O(1)$ space in the `islands` array.

### Standard Solution

#### Solution #1 HashSet + DFS

*   Match the top left cell to the top left corner and store in set

```java
class Solution {
    
    private int[][] grid;
    private boolean[][] seen;
    private Set<Pair<Integer, Integer>> currentIsland;
    private int currRowOrigin;
    private int currColOrigin;
    
    private void dfs(int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) return;
        if (grid[row][col] == 0 || seen[row][col]) return;
        seen[row][col] = true;
        currentIsland.add(new Pair<>(row - currRowOrigin, col - currColOrigin));
        dfs(row + 1, col);
        dfs(row - 1, col);
        dfs(row, col + 1);
        dfs(row, col - 1);    
    }
    
    public int numDistinctIslands(int[][] grid) {
        this.grid = grid;
        this.seen = new boolean[grid.length][grid[0].length];   
        Set<Set<Pair<Integer, Integer>>> islands = new HashSet<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                this.currentIsland = new HashSet<>();
                this.currRowOrigin = row;
                this.currColOrigin = col;
                dfs(row, col);
                if (!currentIsland.isEmpty()) islands.add(currentIsland);
            }
        }         
        return islands.size();
    }
}
```

*   Time Complexity: $O(M \cdot N)$.
*   Space complexity: $O(M \cdot N)$. The `seen` set is the biggest use of additional memory.

## Open the Lock (Medium #752)

**Question**: You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: `'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'`. The wheels can rotate freely and wrap around: for example we can turn `'9'` to be `'0'`, or `'0'` to be `'9'`. Each move consists of turning one wheel one slot.

The lock initially starts at `'0000'`, a string representing the state of the 4 wheels.

You are given a list of `deadends` dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.

Given a `target` representing the value of the wheels that will unlock the lock, return the minimum total number of turns required to open the lock, or -1 if it is impossible.

**Example 1:**

```
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation: 
A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
because the wheels of the lock become stuck after the display becomes the dead end "0102".
```

**Example 2:**

```
Input: deadends = ["8888"], target = "0009"
Output: 1
Explanation: We can turn the last wheel in reverse to move from "0000" -> "0009".
```

**Example 3:**

```
Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
Output: -1
Explanation: We cannot reach the target without getting stuck.
```

**Constraints:**

-   `1 <= deadends.length <= 500`
-   `deadends[i].length == 4`
-   `target.length == 4`
-   target **will not be** in the list `deadends`.
-   `target` and `deadends[i]` consist of digits only.

### My Solution

*   Faster than the given standard bfs solution

```java
class Pair {
    String display;
    int step;
    
    public Pair(String display, int step){
        this.display = display;
        this.step = step;
    }
}

class Solution {
    Set<String> deadendsSet;
    String target;
    
    public int openLock(String[] deadends, String target) {
        deadendsSet = new HashSet<>();
        for (String deadend : deadends){
            deadendsSet.add(deadend);
        }
        this.target = target;
        
        // 1. starts from target, find its neighbors, bfs
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(target, 0));
        int output = -1;
        String newtarget = "0000";
        if (deadendsSet.contains(newtarget)) return output;
        // 2. each time we have neighbors 2 + 2 + 2 + 2
        while(!queue.isEmpty()){
            Pair currPair = queue.poll();
            String curr = currPair.display;
            
            // skip to enhance some efficiency
            if (deadendsSet.contains(curr)){
                continue;
            }
            deadendsSet.add(curr);
            int val = currPair.step;
            if (curr.equals(newtarget)){
                output = val;
                break;
            }
            // each time we change 1 digit value -> 2 possible change method +/-1
            for (int i = 0; i < curr.length(); i++){
                char digit = curr.charAt(i);
                int digitVal = digit - '0';
                int digitPossible1 = (digitVal + 1 + 10) % 10;
                int digitPossible2 = (digitVal - 1 + 10) % 10;
                String next1 = curr.substring(0, i) + digitPossible1 + curr.substring(i + 1);
                String next2 = curr.substring(0, i) + digitPossible2 + curr.substring(i + 1);
                
                if (!deadendsSet.contains(next1)){
                    queue.add(new Pair(next1, val + 1));
                }
                if (!deadendsSet.contains(next2)){
                    queue.add(new Pair(next2, val + 1));
                }
            }
        }
        // 3. until we find "0000", reach it and return output
        return output;
    }
}
```

### Standard Solution

#### Solution #1 BFS

```java
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet();
        for (String d: deadends) dead.add(d);

        Queue<String> queue = new LinkedList();
        queue.offer("0000");
        queue.offer(null);

        Set<String> seen = new HashSet();
        seen.add("0000");

        int depth = 0;
        while (!queue.isEmpty()) {
            String node = queue.poll();
            if (node == null) {
                depth++;
                if (queue.peek() != null)
                    queue.offer(null);
            } else if (node.equals(target)) {
                return depth;
            } else if (!dead.contains(node)) {
                for (int i = 0; i < 4; ++i) {
                    for (int d = -1; d <= 1; d += 2) {
                        int y = ((node.charAt(i) - '0') + d + 10) % 10;
                        String nei = node.substring(0, i) + ("" + y) + node.substring(i+1);
                        if (!seen.contains(nei)) {
                            seen.add(nei);
                            queue.offer(nei);
                        }
                    }
                }
            }
        }
        return -1;
    }
}
```

-   Time Complexity: $O(N^2 * \mathcal{A}^N + D)$ where $\mathcal{A}$ is the number of digits in our alphabet, N is the number of digits in the lock, and D is the size of `deadends`. We might visit every lock combination, plus we need to instantiate our set `dead`. When we visit every lock combination, we spend $O(N^2)$ time enumerating through and constructing each node.

```
Complexity: O(N^2 * A^N + D)
where, N is Number of dials (4 in our case)
A is number of alphabets (10 in our case -> 0 to 9)
D is the size of deadends.

There are 10 x 10 x 10 x 10 possible combinations => 10^4 => A^N
For each combination, we are looping 4 times (which is N) and in each iteration, there are substring operations ( which is O(N) * constant) => O(4N*constant) => O(4N) => O(NN) => O(N^2)
Total complexity => A^N * N^2, plus D to create the hashset => N^2 * A^N + D
```

-   Space Complexity: $O(\mathcal{A}^N + D)$, for the `queue` and the set `dead`.
