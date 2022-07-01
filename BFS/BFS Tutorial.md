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

## N-ary Tree Level Order Traversal (Medium #429)

**Question**: Given an n-ary tree, return the *level order* traversal of its nodes' values.

*Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).*

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2018/10/12/narytreeexample.png" alt="img" style="zoom:50%;" />

```
Input: root = [1,null,3,2,4,null,5,6]
Output: [[1],[3,2,4],[5,6]]
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2019/11/08/sample_4_964.png" alt="img" style="zoom:50%;" />

```
Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
Output: [[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
```

**Constraints:**

-   The height of the n-ary tree is less than or equal to `1000`
-   The total number of nodes is between `[0, 104]`

### My Solution

*   Create a pair class, each time store the node with its level number
*   Use BFS to traverse the tree, and store the map of level number with the node list
*   Loop through the children and add them to the queue as pairs
*   Use lots of memory so it is not a best solution

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};
*/
class Pair {
    private Node node;
    private int level;
    
    public Pair(Node node, int level){
        this.node = node;
        this.level = level;
    }
    
    public int getLevel(){
        return level;
    }
    
    public Node getNode(){
        return node;
    }
}
class Solution {
    // bfs: put nodes to a queue
    public List<List<Integer>> levelOrder(Node root) {
        // use hashmap to store level and list of nodes in that level
        Map<Integer, List<Integer>> levelMap = new HashMap<>();
        Queue<Pair> queue = new LinkedList<Pair>();
        List<List<Integer>> res = new ArrayList<>();
        
        // if null cases
        if (root == null){
            return res;
        }
        
        // each time poll a node from queue and store the children, level + 1
        queue.add(new Pair(root, 0));
        
        while (!queue.isEmpty()){
            Pair current = queue.poll();
            int level = current.getLevel();
            Node node = current.getNode();
            
            if (!levelMap.containsKey(level)){
                levelMap.put(level, new ArrayList());
            }
            levelMap.get(level).add(node.val);
            
            for (Node child : node.children){
                // put each node to the queue
                queue.add(new Pair(child, level + 1));
            }
        }
        
        // loop through the hashmap and store to result list
        for (Map.Entry<Integer,List<Integer>> levelNodes : levelMap.entrySet()){
            res.add(levelNodes.getValue());
        }
        return res;
    }
}
```

### Standard Solution

#### Solution #1 BFS with a Queue

*   A neat version of my solution without making new classes
*   Since we only make list of the nodes, actually we do not need to count the level number
*   Each time we put nodes in the queue, they are definitely from the same level

```java
public List<List<Integer>> levelOrder(Node root){
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    while(!queue.isEmpty()){
        List<Integer> level = new ArrayList<>();
        int size = queue.size();
        for (int i = 0; i < size; i++){
            Node node = queue.poll();
            level.add(node.val);
            queue.addAll(node.children);
        }
        result.add(level);
    }
    return result;
}
```

*   Time complexity: $O(n)$, where n is the number of nodes. Each node is getting added to the queue, removed from the queue, and added to the result exactly once.

*   Space complexity: $O(n)$

    We are using a queue to keep track of nodes we still need to visit the children of. At most, the queue will have 2 layers of the tree on it at any given time. In the worst case, this is all of the nodes. In the best case, it is just 1 node (if we have a tree that is equivalent to a linked list). The average case is difficult to calculate without knowing something about the trees we can expect to see, but in balanced trees, half or more of the nodes are often in the lowest 2 layers. So we should go with the worst case of $O(n)$, and know that the average case is probably similar.

## Rotting Oranges (Medium #994)

**Question**: You are given an `m x n` `grid` where each cell can have one of three values:

-   `0` representing an empty cell,
-   `1` representing a fresh orange, or
-   `2` representing a rotten orange.

Every minute, any fresh orange that is **4-directionally adjacent** to a rotten orange becomes rotten.

Return *the minimum number of minutes that must elapse until no cell has a fresh orange*. If *this is impossible, return* `-1`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/02/16/oranges.png)

```
Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
```

**Example 2:**

```
Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
```

**Example 3:**

```
Input: grid = [[0,2]]
Output: 0
Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
```

**Constraints:**

-   `m == grid.length`
-   `n == grid[i].length`
-   `1 <= m, n <= 10`
-   `grid[i][j]` is `0`, `1`, or `2`.

### My Solution

```java
// bfs: each minute we regard it as a level
// 4 directions
int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

public int orangesRotting(int[][] grid) {
    Queue<int[]> level = new LinkedList<>();
    // find all rotten orange in the current grid
    int m = grid.length;
    int n = grid[0].length;

    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if (grid[i][j] == 2){
                level.add(new int[]{0, i, j});
            }
        }
    }
    // use queue to store level-orange, put the neighbors to queue
    int min = 0;
    while (!level.isEmpty()){
        int[] current = level.poll();
        int currentLevel = current[0];
        min = Math.max(min, currentLevel);
        List<int[]> neighbors = findNeighbors(new int[]{current[1], current[2]}, m - 1, n - 1, grid);
        if (neighbors.size() == 0) continue;
        for (int[] neighbor : neighbors){
            level.add(new int[]{currentLevel + 1, neighbor[0], neighbor[1]});
            grid[neighbor[0]][neighbor[1]] = 2;
        }
    }
    // finally check if the grid has 1 or not(if so, return -1)
    for (int i = 0; i < m; i++){
        for (int j = 0; j < n; j++){
            if (grid[i][j] == 1){
                return -1;
            }
        }
    }
    return min;
}

public List<int[]> findNeighbors(int[] current, int maxRow, int maxCol, int[][] grid){
    List<int[]> neighbors = new ArrayList<int[]>();
    int currentRow = current[0];
    int currentCol = current[1];

    for (int[] direction : directions){
        int neighborRow = currentRow + direction[0];
        int neighborCol = currentCol + direction[1];

        if (neighborRow < 0 || neighborRow > maxRow || neighborCol < 0 
            || neighborCol > maxCol || grid[neighborRow][neighborCol] != 1){
            continue;
        }
        neighbors.add(new int[]{neighborRow, neighborCol});
    }
    return neighbors;
}
```

*   Similar to the first standard solution

### Standard Solution

#### Solution #1 BFS

*   We can add a fake orange to the queue to count the elapsed time
*   Each time we meet the fake orange, elapsed time should plus 1. If the queue is not empty, add another fake orange.
*   Count the fresh orange number at the beginning, and subtract 1 each time we store it in the queue
*   At the end, check if the fresh orange number go to 0.

```java
class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<Pair<Integer, Integer>> queue = new ArrayDeque();

        // Step 1). build the initial set of rotten oranges
        int freshOranges = 0;
        int ROWS = grid.length, COLS = grid[0].length;

        for (int r = 0; r < ROWS; ++r)
            for (int c = 0; c < COLS; ++c)
                if (grid[r][c] == 2)
                    queue.offer(new Pair(r, c));
                else if (grid[r][c] == 1)
                    freshOranges++;

        // Mark the round / level, _i.e_ the ticker of timestamp
        queue.offer(new Pair(-1, -1));

        // Step 2). start the rotting process via BFS
        int minutesElapsed = -1;
        int[][] directions = { {-1, 0}, {0, 1}, {1, 0}, {0, -1}};

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> p = queue.poll();
            int row = p.getKey();
            int col = p.getValue();
            if (row == -1) {
                // We finish one round of processing
                minutesElapsed++;
                // to avoid the endless loop
                if (!queue.isEmpty())
                    queue.offer(new Pair(-1, -1));
            } else {
                // this is a rotten orange
                // then it would contaminate its neighbors
                for (int[] d : directions) {
                    int neighborRow = row + d[0];
                    int neighborCol = col + d[1];
                    if (neighborRow >= 0 && neighborRow < ROWS && 
                        neighborCol >= 0 && neighborCol < COLS) {
                        if (grid[neighborRow][neighborCol] == 1) {
                            // this orange would be contaminated
                            grid[neighborRow][neighborCol] = 2;
                            freshOranges--;
                            // this orange would then contaminate other oranges
                            queue.offer(new Pair(neighborRow, neighborCol));
                        }
                    }
                }
            }
        }

        // return elapsed minutes if no fresh orange left
        return freshOranges == 0 ? minutesElapsed : -1;
    }
}
```

-   Time Complexity: $\mathcal{O}(N)$, where N is the size of the grid. First, we scan the grid to find the initial values for the queue, which would take $\mathcal{O}(N)$ time. Then we run the BFS process on the queue, which in the worst case would enumerate all the cells in the grid once and only once. Therefore, it takes $\mathcal{O}(N)$ time. Thus combining the above two steps, the overall time complexity would be $\mathcal{O}(N) + \mathcal{O}(N) = \mathcal{O}(N)$
-   Space Complexity: $\mathcal{O}(N)$, where N is the size of the grid. In the worst case, the grid is filled with rotten oranges. As a result, the queue would be initialized with all the cells in the grid. By the way, normally for BFS, the main space complexity lies in the process rather than the initialization. For instance, for a BFS traversal in a tree, at any given moment, the queue would hold no more than 2 levels of tree nodes. Therefore, the space complexity of BFS traversal in a tree would depend on the ***width*** of the input tree.