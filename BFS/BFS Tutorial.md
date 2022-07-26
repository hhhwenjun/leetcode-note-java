# BFS Tutorial

### Purpose

*   The most advantageous use case of “breadth-first search” is to efficiently find the shortest path between two vertices in a “graph” where **all edges have equal and positive weights**.

*   Time Complexity: $O(V + E)$. Here, V represents the number of vertices, and E represents the number of edges. We need to check every vertex and traverse through every edge in the graph. The time complexity is the same as it was for the DFS approach.
*   Space Complexity: $O(V)$. Generally, we will check if a vertex has been visited before adding it to the queue, so the queue will use at most $O(V)$ space. Keeping track of which vertices have been visited will also require $O(V)$ space.

### Queue Application

*   One common application of Breadth-first Search (BFS) is to find the shortest path from the root node to the target node.
*   Queue structure is widely applied in the process

### BFS Template

#### Template 1

```java
/**
 * Return the length of the shortest path between root and target node.
 */
int BFS(Node root, Node target) {
    Queue<Node> queue;  // store all nodes which are waiting to be processed
    int step = 0;       // number of steps neeeded from root to current node
    // initialize
    add root to queue;
    // BFS
    while (queue is not empty) {
        // iterate the nodes which are already in the queue
        int size = queue.size();
        for (int i = 0; i < size; ++i) {
            Node cur = the first node in queue;
            return step if cur is target;
            for (Node next : the neighbors of cur) {
                add next to queue;
            }
            remove the first node from queue;
        }
        step = step + 1;
    }
    return -1;          // there is no path from root to target
}
```

#### Template 2

*   Sometimes, it is important to make sure that we `never visit a node twice`.

```java
/**
 * Return the length of the shortest path between root and target node.
 */
int BFS(Node root, Node target) {
    Queue<Node> queue;  // store all nodes which are waiting to be processed
    Set<Node> visited;  // store all the nodes that we've visited
    int step = 0;       // number of steps neeeded from root to current node
    // initialize
    add root to queue;
    add root to visited;
    // BFS
    while (queue is not empty) {
        // iterate the nodes which are already in the queue
        int size = queue.size();
        for (int i = 0; i < size; ++i) {
            Node cur = the first node in queue;
            return step if cur is target;
            for (Node next : the neighbors of cur) {
                if (next is not in visited) {
                    add next to queue;
                    add next to visited;
                }
            }
            remove the first node from queue;
        }
        step = step + 1;
    }
    return -1;          // there is no path from root to target
}
```



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

## Walls and Gates (Meidum #286)

**Question**: You are given an `m x n` grid `rooms` initialized with these three possible values.

-   `-1` A wall or an obstacle.
-   `0` A gate.
-   `INF` Infinity means an empty room. We use the value `231 - 1 = 2147483647` to represent `INF` as you may assume that the distance to a gate is less than `2147483647`.

Fill each empty room with the distance to *its nearest gate*. If it is impossible to reach a gate, it should be filled with `INF`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/03/grid.jpg)

```
Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
```

**Example 2:**

```
Input: rooms = [[-1]]
Output: [[-1]]
```

**Constraints:**

-   `m == rooms.length`
-   `n == rooms[i].length`
-   `1 <= m, n <= 250`
-   `rooms[i][j]` is `-1`, `0`, or `231 - 1`.

### Standard Solution

#### Solution #1 BFS

*   It would keep the smallest distance because the queue structure first goes through all the gates, then the nodes near the gates.

```java
// 1. move 4 directions
int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
int EMPTY = Integer.MAX_VALUE;
int GATE = 0;

public void wallsAndGates(int[][] rooms) {

    int row = rooms.length;
    if (row == 0) return;
    int col = rooms[0].length;

    // 2. put gate in a queue,
    Queue<int[]> queue = new LinkedList<>();
    for (int i = 0; i < row; i++){
        for (int j = 0; j < col; j++){
            if (rooms[i][j] == GATE){
                queue.add(new int[]{i, j});
            }
        }
    }

    // 3. dfs to mark other cell value
    while (!queue.isEmpty()){
        int[] curr = queue.poll();
        int currRow = curr[0];
        int currCol = curr[1];
        for (int[] direction : directions){
            int nextRow = currRow + direction[0];
            int nextCol = currCol + direction[1];

            if (nextRow < 0 || nextRow >= row || nextCol < 0 || nextCol >= col
               || rooms[nextRow][nextCol] != EMPTY){
                continue;
            }
            rooms[nextRow][nextCol] = rooms[currRow][currCol] + 1;
            queue.add(new int[]{nextRow, nextCol});
        }
    }
}
```

-   Time complexity: $O(mn)$.

    If you are having difficulty deriving the time complexity, start simple.

    Let us start with the case with only one gate. The breadth-first search takes at most $m \times n$ steps to reach all rooms. Therefore the time complexity is $O(mn)$. But what if you are doing a breadth-first search from k gates?

    Once we set a room's distance, we are basically marking it as visited, which means each room is visited at most once. Therefore, the time complexity does not depend on the number of gates and is $O(mn)$.

-   Space complexity: $O(mn)$. The space complexity depends on the queue's size. We insert at most $m \times n$ points into the queue.

*   Queue: implement with linked list

#### Solution #2 DFS

*   BFS cannot be implements through recursion

```java
private void dfs(int[][] rooms, int i, int j, int distance) {
    if (i < 0 || i >= rooms.length || j < 0 ||  j >= rooms[0].length) {
        return ;
    }
    if (rooms[i][j] < distance) {
        return ;
    } else {
        rooms[i][j] = distance;
        dfs(rooms, i + 1, j, distance + 1);
        dfs(rooms, i - 1, j, distance + 1);
        dfs(rooms, i, j + 1, distance + 1);
        dfs(rooms, i, j - 1, distance + 1);
    }
}
public void wallsAndGates(int[][] rooms) {
    if (rooms == null || rooms.length == 0 || rooms[0].length == 0) {
        return ;
    }
    for (int i = 0; i < rooms.length; ++i) {
        for (int j = 0; j < rooms[0].length; ++j) {
            if (rooms[i][j] == 0) {
                dfs(rooms, i, j, 0);
            }
        }
    }
}
```

## Perfect Squares (Medium #279)

**Question**: Given an integer `n`, return *the least number of perfect square numbers that sum to* `n`.

A **perfect square** is an integer that is the square of an integer; in other words, it is the product of some integer with itself. For example, `1`, `4`, `9`, and `16` are perfect squares while `3` and `11` are not 

**Example 1:**

```
Input: n = 12
Output: 3
Explanation: 12 = 4 + 4 + 4.
```

**Example 2:**

```
Input: n = 13
Output: 2
Explanation: 13 = 4 + 9.
```

**Constraints:**

-   `1 <= n <= 104`

### Standard Solution

#### Solution #1 Dynamic Programming

<img src="https://leetcode.com/problems/perfect-squares/Figures/279/279_dp.png" alt="pic" style="zoom:50%;" />

*   1D array to record the DP results
*   Each time loop the square array, and cumulatively plus the previous result, until sqaure array value larger than target

```java
public int numSquares(int n){
    int dp[] = new int[n + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    // bottom case
    dp[0] = 0;
    
    // pre-calculate the sqaure numbers.
    int max_square_index = (int) Math.sqrt(n) + 1;
    int square_nums[] = new int[max_square_index];
    for (int i = 1; i < max_square_index; i++){
        square_nums[i] = i * i;
    }
    for (int i = 1; i <= n; i++){
        for (int s = 1; s < max_square_index; s++){
            if (i < square_nums[s]) break;
            dp[i] = Math.min(dp[i], dp[i - square_nums[s]] + 1);
        }
    }
    return dp[n];
}
```

-   Time complexity: $\mathcal{O}(n\cdot\sqrt{n})$. In main step, we have a nested loop, where the outer loop is of n iterations and in the inner loop it takes at maximum $\sqrt{n}$ iterations.
-   Space Complexity: $\mathcal{O}(n)$. We keep all the intermediate sub-solutions in the array `dp[]`.

#### Solution #2 Greedy + BFS

*   The idea is similar to the last method, but using BFS
*   Each time use a new queue. For each element in the queue, there is a possible way to reach the number of squares
*   Each time loop through the possible square number, add the remainders to the queue

```java
public int numsSquares(int n){
    ArrayList<Integer> square_nums = new ArrayList<Integer>();
    for (int i = 1; i * i <= n; i++){
        square_nums.add(i * i);
    }
    Set<Integer> queue = new HashSet<Integer>();
    queue.add(n);
    
    int level = 0;
    while(queue.size() > 0){
        level += 1;
        Set<Integer> next_queue = new HashSet<Integer>();
        
        for (Integer remainder : queue){
            for (Integer square : square_nums){
                if (remainder.equals(square)){
                    return level;
                } else if (remainder < square){
                    break;
                } else {
                    next_queue.add(remainder - square);
                }
            }
        }
        queue = next_queue;
    }
    return level;
}
```

-   Time complexity: $\mathcal{O}( \frac{\sqrt{n}^{h+1} - 1}{\sqrt{n} - 1} ) = \mathcal{O}(n^{\frac{h}{2}})$ where `h` is the height of the N-ary tree. 
-   Space complexity: $\mathcal{O}\Big((\sqrt{n})^h\Big)$, which is also the maximal number of nodes that can appear at the level `h`. As one can see, though we keep a list of `square_nums`, the main consumption of the space is the `queue` variable, which keeps track of the remainders to visit for a given level of the N-ary tree.

## Open the Lock(Medium #752)

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
-   Space Complexity: $O(\mathcal{A}^N + D)$, for the `queue` and the set `dead`.
