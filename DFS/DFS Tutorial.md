# DFS Tutorial

### Graph

*   Traverse all vertices in a “graph”; Go deeper and deeper until cannot move on.
*   Traverse all paths between any two vertices in a “graph”.
*   Traverse all vertices
    *   Time Complexity: $O(V + E)$. Here, V represents the number of vertices, and E represents the number of edges. We need to check every vertex and traverse through every edge in the graph.
    *   Space Complexity: $O(V)$. Either the manually created stack or the recursive call stack can store up to V vertices.
*   Traverse all paths between two vertices
    *   Time Complexity: $O((V - 1)!)$ The above example is for an undirected graph. The worst-case scenario, when trying to find all paths, is a complete graph. A complete graph is a graph where every vertex is connected to every other vertex.
    *   Space Complexity: $O(V^3)$ 

### Stack

*   A stack helps with DFS.
*   Overall, we `only` trace-back and try another path after we reach the `deepest` node.

#### Template-Recursion

```java
/*
 * Return true if there is a path from cur to target.
 */
boolean DFS(Node cur, Node target, Set<Node> visited) {
    return true if cur is target;
    for (next : each neighbor of cur) {
        if (next is not in visited) {
            add next to visted;
            return true if DFS(next, target, visited) == true;
        }
    }
    return false;
}
```

#### Template-While

```java
/*
 * Return true if there is a path from cur to target.
 */
boolean DFS(int root, int target) {
    Set<Node> visited;
    Stack<Node> stack;
    add root to stack;
    while (stack is not empty) {
        Node cur = the top element in stack;
        remove the cur from the stack;
        return true if cur is target;
        for (Node next : the neighbors of cur) {
            if (next is not in visited) {
                add next to visited;
                add next to stack;
            }
        }
    }
    return false;
}
```



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

*   Another solution using BFS
    *   Time complexity is the same
    *   Space complexity is $O(2^V * V)$

```java
public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
    List<List<Integer>> paths = new ArrayList<>();
    if (graph == null || graph.length == 0) {
        return paths;
    }
    Queue<List<Integer>> queue = new LinkedList<>();
    List<Integer> path = new ArrayList<>();
    path.add(0);
    queue.add(path);
    while (!queue.isEmpty()) {
        List<Integer> currentPath = queue.poll();
        int node = currentPath.get(currentPath.size() - 1);
        for (int nextNode: graph[node]) {
            List<Integer> tmpPath = new ArrayList<>(currentPath);
            tmpPath.add(nextNode);
            if (nextNode == graph.length - 1) {
                paths.add(new ArrayList<>(tmpPath));
            } else {
                queue.add(new ArrayList<>(tmpPath));
            } 
        }
    }
    return paths;
}
```

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

## Clone Graph (Medium #133)

**Question**: Given a reference of a node in a **[connected](https://en.wikipedia.org/wiki/Connectivity_(graph_theory)#Connected_graph)** undirected graph.

Return a [**deep copy**](https://en.wikipedia.org/wiki/Object_copying#Deep_copy) (clone) of the graph.

Each node in the graph contains a value (`int`) and a list (`List[Node]`) of its neighbors.

```
class Node {
    public int val;
    public List<Node> neighbors;
}
```

**Test case format:**

For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with `val == 1`, the second node with `val == 2`, and so on. The graph is represented in the test case using an adjacency list.

**An adjacency list** is a collection of unordered **lists** used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.

The given node will always be the first node with `val = 1`. You must return the **copy of the given node** as a reference to the cloned graph.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2019/11/04/133_clone_graph_question.png" alt="img" style="zoom: 25%;" />

```
Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]
Explanation: There are 4 nodes in the graph.
1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/01/07/graph.png)

```
Input: adjList = [[]]
Output: [[]]
Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.
```

**Example 3:**

```
Input: adjList = []
Output: []
Explanation: This an empty graph, it does not have any nodes. 
```

**Constraints:**

-   The number of nodes in the graph is in the range `[0, 100]`.
-   `1 <= Node.val <= 100`
-   `Node.val` is unique for each node.
-   There are no repeated edges and no self-loops in the graph.
-   The Graph is connected and all nodes can be visited starting from the given node.

### Standard Solution

#### Solution #1 DFS

*   To avoid getting stuck in a loop we would need some way to keep track of the nodes which have already been copied. By doing this we don't end up traversing them again.
*   We would take a hash map to store the reference of the copy of all the nodes that have already been visited and cloned. If we don't use `visited` we will get stuck in a cycle.
*   If we don't find the node in the `visited` hash map, we create a copy of it and put it in the hash map. 

```java
private HashMap<Node, Node> visited = new HashMap<>();
public Node cloneGraph(Node node){
    if (node == null){
        return node;
    }
    
    // if the node was already visited before.
    // return the clone from the visited dictionary
    if (visited.containsKey(node)){
        return visited.get(node);
    }
    
    // create a clone for the given node
    // note that we don't have cloned neighbors as of now
    Node cloneNode = new Node(node.val, new ArrayList());
    // the key is original node and value being the clone node
    visited.put(node, cloneNode);
    
    // iterate through the neighbors to generate the clones
    for (Node neighbor : node.neighbors){
        cloneNode.neighbors.add(cloneGraph(neighbor));
    }
    return cloneNode;
}
```

-   Time Complexity: $O(N + M)$, where N is a number of nodes (vertices) and M is a number of edges.
-   Space Complexity: $O(N)$. This space is occupied by the `visited` hash map and in addition to that, space would also be occupied by the recursion stack since we are adopting a recursive approach here. The space occupied by the recursion stack would be equal to $O(H)$ where H is the height of the graph. Overall, the space complexity would be $O(N)$.

#### Solution #2 BFS

*   Similar to DFS but using stack/queue to keep track of nodes

```java
// use hashmap to track visited
private Map<Node, Node> visited = new HashMap<>();

// use the dfs to traverse the node and neighbor lists + recursion
public Node cloneGraph(Node node) {
    // consider the null case
    if (node == null){
        return node;
    }
    Stack<Node> stack = new Stack<>();
    stack.add(node);
    visited.put(node, new Node(node.val, new ArrayList<Node>()));

    while (!stack.isEmpty()){
        Node current = stack.pop();

        for (Node neighbor : current.neighbors){
            if (!visited.containsKey(neighbor)){
                visited.put(neighbor, new Node(neighbor.val, new ArrayList<Node>()));
                stack.add(neighbor);
            }
            // add the clone of the neighbor clone node
            visited.get(current).neighbors.add(visited.get(neighbor));
        }
    }
    return visited.get(node);
}
```

*   Time Complexity: $O(N + M)$, where N is a number of nodes (vertices) and M is a number of edges.
*   Space Complexity: $O(N)$. This space is occupied by the `visited` dictionary and in addition to that, space would also be occupied by the queue since we are adopting the BFS approach here. The space occupied by the queue would be equal to $O(W)$ where W is the width of the graph. Overall, the space complexity would be $O(N)$.

#### Solution #3 DFS with Array

```java
class Solution {
    Node[] visited;
    public Node cloneGraph(Node node) {
        if(node == null) return null;
        visited = new Node[101];
        return DFS(node);
    }
    public Node DFS(Node node){
        if(visited[node.val] != null) return visited[node.val];
        visited[node.val] = new Node(node.val);
        for(Node n: node.neighbors) visited[node.val].neighbors.add(DFS(n));
        return visited[node.val];
    }
} 
```

## Flood Fill (Easy #733)

**Question**: An image is represented by an `m x n` integer grid `image` where `image[i][j]` represents the pixel value of the image.

You are also given three integers `sr`, `sc`, and `color`. You should perform a **flood fill** on the image starting from the pixel `image[sr][sc]`.

To perform a **flood fill**, consider the starting pixel, plus any pixels connected **4-directionally** to the starting pixel of the same color as the starting pixel, plus any pixels connected **4-directionally** to those pixels (also with the same color), and so on. Replace the color of all of the aforementioned pixels with `color`.

Return *the modified image after performing the flood fill*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/06/01/flood1-grid.jpg)

```
Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]
Explanation: From the center of the image with position (sr, sc) = (1, 1) (i.e., the red pixel), all pixels connected by a path of the same color as the starting pixel (i.e., the blue pixels) are colored with the new color.
Note the bottom corner is not colored 2, because it is not 4-directionally connected to the starting pixel.
```

**Example 2:**

```
Input: image = [[0,0,0],[0,0,0]], sr = 0, sc = 0, color = 0
Output: [[0,0,0],[0,0,0]]
Explanation: The starting pixel is already colored 0, so no changes are made to the image. 
```

**Constraints:**

-   `m == image.length`
-   `n == image[i].length`
-   `1 <= m, n <= 50`
-   `0 <= image[i][j], color < 216`
-   `0 <= sr < m`
-   `0 <= sc < n`

### My Solution

```java
int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
int[][] image;
int color;
int currCol;

public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    // dfs:find all connected neighbors in terms of sr and sc
    this.image = image;
    this.color = color;
    currCol = image[sr][sc];
    dfs(sr, sc);
    return image;
}

public void dfs(int sr, int sc){
    // if curr cell is out of range or is not connected (0), or we have already seen
    if (sr < 0 || sr >= image.length || sc < 0 || 
        sc >= image[0].length || image[sr][sc] == color || image[sr][sc] != currCol){
        return;
    }
    image[sr][sc] = color;
    for (int[] direction : directions){
        // find all available neighbors for the curr cell
        int row = sr + direction[0];
        int col = sc + direction[1];
        dfs(row, col);
    }
}
```

### Standard Solution

#### Solution #1 DFS

*   Same as my solution

```java
public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    int color = image[sr][sc];
    if (color != newColor) dfs(image, sr, sc, color, newColor);
    return image;
}
public void dfs(int[][] image, int r, int c, int color, int newColor) {
    if (image[r][c] == color) {
        image[r][c] = newColor;
        if (r >= 1) dfs(image, r-1, c, color, newColor);
        if (c >= 1) dfs(image, r, c-1, color, newColor);
        if (r+1 < image.length) dfs(image, r+1, c, color, newColor);
        if (c+1 < image[0].length) dfs(image, r, c+1, color, newColor);
    }
}
```

-   Time Complexity: $O(N)$, where N is the number of pixels in the image. We might process every pixel.
-   Space Complexity: $O(N)$, the size of the implicit call stack when calling `dfs`.

## 01 Matrix (Medium #542)

**Question**: Given an `m x n` binary matrix `mat`, return *the distance of the nearest* `0` *for each cell*.

The distance between two adjacent cells is `1`.

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2021/04/24/01-1-grid.jpg" alt="img" style="zoom:50%;" />

```
Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
Output: [[0,0,0],[0,1,0],[0,0,0]]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2021/04/24/01-2-grid.jpg" alt="img" style="zoom:50%;" />

```
Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
Output: [[0,0,0],[0,1,0],[1,2,1]]
```

**Constraints:**

-   `m == mat.length`
-   `n == mat[i].length`
-   `1 <= m, n <= 104`
-   `1 <= m * n <= 104`
-   `mat[i][j]` is either `0` or `1`.
-   There is at least one `0` in `mat`.

### My Solution

```java
public int[][] updateMatrix(int[][] mat) {
    // get the dimension info of the matrix
    int length = mat.length;
    int width = mat[0].length;
    boolean[][] visited = new boolean[length][width];
    Queue<int[]> queue = new LinkedList<>();

    // bfs: put cell to a queue
    // 1. put all the 0 cells into a queue, mark visited array as true
    for (int i = 0; i < length; i++){
        for (int j = 0; j < width; j++){
            if (mat[i][j] == 0){
                // dimension: i, j || distance
                queue.add(new int[]{i, j, 0});
                visited[i][j] = true;
            }
        }
    }
    int[][] directions = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    // 2. poll each cell out, traverse 4 directions unvisited array, + 1
    while(!queue.isEmpty()){
        int[] curr = queue.poll();
        int currX = curr[0];
        int currY = curr[1];
        int currDis = curr[2];
        mat[currX][currY] = currDis;

        for (int[] direction : directions){
            int nextX = currX + direction[0];
            int nextY = currY + direction[1];
            if (nextX < 0 || nextX >= length || nextY < 0 || nextY >= width || visited[nextX][nextY]){
                continue;
            }
            // 3. put them back to queue, repeat the process
            queue.add(new int[]{nextX, nextY, currDis + 1});
            visited[nextX][nextY] = true;
        }
    }
    return mat;
}
```



### Standard Solution

#### Solution #1 DFS

*   Record the number of one
*   Each time when encounter a 0, check if it surrounds by -1, and reduce the number of 1
*   Then check each 1 if surrounds by -1 and repeat the process

```java
public int[][] updateMatrix(int[][] matrix) {
    int n = matrix.length;
    int m = matrix[0].length;
    int ones = 0;
    for(int i = 0; i < n; i++)
        for(int j = 0; j < m; j++)
            if(matrix[i][j] == 1){
                matrix[i][j] = -1;
                ones++;
            }
    int x = 0;
    while(ones > 0){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(matrix[i][j] == x){
                     if(isValid(matrix,i+1,j) && matrix[i+1][j] == -1){
                         matrix[i+1][j] = x + 1;
                         ones--;
                     }
                    if(isValid(matrix,i-1,j) && matrix[i-1][j] == -1){
                         matrix[i-1][j] = x + 1;
                         ones--;
                     }
                    if(isValid(matrix,i,j+1) && matrix[i][j+1] == -1){
                         matrix[i][j+1] = x + 1;
                         ones--;
                     }if(isValid(matrix,i,j-1) && matrix[i][j-1] == -1){
                         matrix[i][j-1] = x + 1;
                         ones--;
                     }
                }
            }
        }
        x++;
    }
    return matrix;
}
private boolean isValid(int[][] mat, int i, int j){
    return (i >= 0 && i < mat.length && j >= 0 && j < mat[0].length);
}
```

#### Solution #2 BFS

```java
public int[][] updateMatrix(int[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (matrix[i][j] == 0) {
                queue.offer(new int[] {i, j});
            }
            else {
                matrix[i][j] = Integer.MAX_VALUE;
            }
        }
    }
    // DO BFS IN ALL NEIGHBOURS SO THAT WE GET FINAL RESULT 
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : dirs) {
            int r = cell[0] + d[0];
            int c = cell[1] + d[1];
             // if new cell is out of bounds or is already closer to another 0, 
             // stop further bfs in this cell 
            if (r < 0 || r >= m || c < 0 || c >= n || 
                matrix[r][c] <= matrix[cell[0]][cell[1]] + 1) continue;
            queue.add(new int[] {r, c});
            matrix[r][c] = matrix[cell[0]][cell[1]] + 1;
        }
    }

    return matrix;
}
```

-   Time complexity: $O(r \cdot c)$. Since, the new cells are added to the queue only if their current distance is greater than the calculated distance, cells are not likely to be added multiple times.
-   Space complexity: $O(r \cdot c)$. An additional $O(r \cdot c)$ space is required to maintain the queue.

## Keys and Rooms (Medium #841)

**Question**: There are `n` rooms labeled from `0` to `n - 1` and all the rooms are locked except for room `0`. Your goal is to visit all the rooms. However, you cannot enter a locked room without having its key.

When you visit a room, you may find a set of **distinct keys** in it. Each key has a number on it, denoting which room it unlocks, and you can take all of them with you to unlock the other rooms.

Given an array `rooms` where `rooms[i]` is the set of keys that you can obtain if you visited room `i`, return `true` *if you can visit **all** the rooms, or* `false` *otherwise*.

**Example 1:**

```
Input: rooms = [[1],[2],[3],[]]
Output: true
Explanation: 
We visit room 0 and pick up key 1.
We then visit room 1 and pick up key 2.
We then visit room 2 and pick up key 3.
We then visit room 3.
Since we were able to visit every room, we return true.
```

**Example 2:**

```
Input: rooms = [[1,3],[3,0,1],[2],[0]]
Output: false
Explanation: We can not enter room number 2 since the only key that unlocks it is in that room.
```

**Constraints:**

-   `n == rooms.length`
-   `2 <= n <= 1000`
-   `0 <= rooms[i].length <= 1000`
-   `1 <= sum(rooms[i].length) <= 3000`
-   `0 <= rooms[i][j] < n`
-   All the values of `rooms[i]` are **unique**.

### My Solution

```java
// bfs solution
public boolean canVisitAllRooms(List<List<Integer>> rooms) {
    // bfs: visited the adjacent list
    int num = rooms.size();
    int[] visited = new int[num];
    Queue<Integer> queue = new LinkedList<>();

    // 1. go to the first room, put it into queue, visited array
    queue.add(0);
    visited[0] = 1;

    // 2. add the adjacent rooms to the queue
    while(!queue.isEmpty()){
        int currRoom = queue.poll();
        // visit the rooms with keys (adjacency list)
        for (int room : rooms.get(currRoom)){
            if (visited[room] == 1) continue;
            visited[room] = 1;
            queue.add(room);
        }
    }
    // 3. if the queue is empty, but visited is not all true
    int sum = 0;
    for (int digit : visited) sum += digit;
    return sum == num;
}
```

*   The time and space complexity is $O(N)$

### Standard Solution

#### Solution #1 DFS

```java
public boolean canVisitAllRooms(List<List<Integer>> rooms) {
    boolean[] seen = new boolean[rooms.size()];
    seen[0] = true;
    Stack<Integer> stack = new Stack();
    stack.push(0);

    //At the beginning, we have a to-do list "stack" of keys to use.
    //'seen' represents at some point we have entered this room.
    while (!stack.isEmpty()) { // While we have keys...
        int node = stack.pop(); // Get the next key 'node'
        for (int nei: rooms.get(node)) // For every key in room # 'node'...
            if (!seen[nei]) { // ...that hasn't been used yet
                seen[nei] = true; // mark that we've entered the room
                stack.push(nei); // add the key to the todo list
            }
    }

    for (boolean v: seen)  // if any room hasn't been visited, return false
        if (!v) return false;
    return true;
}
```

-   Time Complexity: $O(N + E)$, where N is the number of rooms, and E is the total number of keys.
-   Space Complexity: $O(N)$ in additional space complexity, to store `stack` and `seen`.
