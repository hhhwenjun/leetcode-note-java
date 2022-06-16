# Graph Tutorial

### Types of graphs

*   Undirected graphs: The edges between any two vertices in an “undirected graph” do not have a direction, indicating a two-way relationship.
*   Directed graphs: The edges between any two vertices in a “directed graph” graph are directional.
*   Weighted graphs: Each edge in a “weighted graph” has an associated weight. The weight can be of any metric, such as time, distance, size, etc. The most commonly seen “weighted map” in our daily life might be a city map. 

### Terminologies

*   Vertex: nodes such as A, B, and C are called vertices of the graph.

*   Edge: The connection between two vertices are the edges of the graph.

*   Path: the sequence of vertices to go through from one vertex to another. 
    *   **Note**: there can be multiple paths between two vertices.

*   Path Length: the number of edges in a path. 

*   Cycle: a path where the starting point and endpoint are the same vertexes. 

*   Negative Weight Cycle: In a “weighted graph”, if the sum of the weights of all edges of a cycle is a negative value, it is a negative weight cycle. 

*   Connectivity: if there exists at least one path between two vertices, these two vertices are connected. 

*   Degree of a Vertex: the term “degree” applies to unweighted graphs. The degree of a vertex is the number of edges connecting the vertex. 

*   In-Degree: “in-degree” is a concept in directed graphs. 

*   Out-Degree: “out-degree” is a concept in directed graphs. 

<img src="https://leetcode.com/explore/learn/card/Figures/Graph_Explore/Disjoint_Set_1_edited.png" alt="img" style="zoom: 50%;" />

-   Parent node: the direct parent node of a vertex. For example, in Figure 5, the parent node of vertex 3 is 1, the parent node of vertex 2 is 0, and the parent node of vertex 9 is 9.
-   Root node: a node without a parent node; it can be viewed as the parent node of itself. For example, in Figure 5, the root node of vertices 3 and 2 is 0. As for 0, it is its own root node and parent node. Likewise, the root node and parent node of vertex 9 are 9 itself. Sometimes the root node is referred to as the head node.
-   Disjoint set
    -   Use union to find disjoint sets, and check if they are connected
    -   `*` means the head of the set

![image-20220614101837616](/Users/wenjunhan/Library/Application Support/typora-user-images/image-20220614101837616.png)

*   Implement disjoint sets

    *   The `find` function finds the root node of a given vertex. 
    *   The `union` function unions two vertices and makes their root nodes the same. 
    *   Implementation with Quick Find: in this case, the time complexity of the `find` function will be $O(1)$. However, the `union` function will take more time with the time complexity of $O(N)$.
    *   Implementation with Quick Union: compared with the Quick Find implementation, the time complexity of the `union` function is better. Meanwhile, the `find` function will take more time in this case.

    <img src="/Users/wenjunhan/Library/Application Support/typora-user-images/image-20220614112321870.png" alt="image-20220614112321870" style="zoom: 67%;" />

​	

### Quick Find

*   Array value is the root node (also need to consider union when considering find)
*   **Easy to find, but slower union**
*   Constructor $O(n)$, find $O(1)$, union $O(n)$, connected $O(1)$

```java
// UnionFind.class
class UnionFind {
    private int[] root;

    public UnionFind(int size) {
        root = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
        }
    }

    public int find(int x) {
        return root[x];
    }
		
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            for (int i = 0; i < root.length; i++) {
                if (root[i] == rootY) {
                    root[i] = rootX;
                }
            }
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
```

### Quick Union

*   **Easy to the union, but slower to find**
*   Quick union is more efficient than quick find when unioning all elements

*   Array value is the parent node (must traverse until finding the root node)
*   Constructor $O(n)$, find $O(n)$, union $O(n)$, connected $O(n)$
    *   For the `find` operation, in the worst-case scenario, we need to traverse every vertex to find the root for the input vertex. The maximum number of operations to get the root vertex would be no more than the tree's height, so it will take $O(N)$ time.
    *   The `union` operation consists of two `find` operations which (**only in the worst-case**) will take $O(N)$ time, and two constant time operations, including the equality check and updating the array value at a given index. Therefore, the `union` operation also costs $O(N)$ in the worst case.

```java
class UnionFind {
    private int[] root;

    public UnionFind(int size) {
        root = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
        }
    }

    public int find(int x) {
        while (x != root[x]) {
            x = root[x];
        }
        return x;
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            root[rootY] = rootX;
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
```

### Union by Rank

*   Reduce the height of the tree, and make it as balanced as possible
*   Constructor $O(n)$, find $O(\log n)$, union $O(\log n)$, connected $O(\log n)$

```java
// UnionFind.class
class UnionFind {
    private int[] root;
    private int[] rank;

    public UnionFind(int size) {
        root = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
            rank[i] = 1; 
        }
    }

    public int find(int x) {
        while (x != root[x]) {
            x = root[x];
        }
        return x;
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                root[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                root[rootX] = rootY;
            } else {
                root[rootY] = rootX;
                rank[rootX] += 1;
            }
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
```

### Path Compression Optimization - Disjoint Sets

*   Notice that to find the root node, we need to traverse the parent nodes sequentially until we reach the root node. If we search the root node of the same element again, we repeat the same operations. 
*   When we search for the root node of the same element again, we only need to traverse two elements to find its root node, which is highly efficient. 
*   Connect the nodes directly to their root node to optimize the path, not only their parent nodes
*   Constructor $O(n)$, find $O(\log n)$, union $O(\log n)$, connected $O(\log n)$ in the average case, since the worst-case scenario is rare in practice.

```java
private int[] root;
public UnionFind(int size) {
    root = new int[size];
    for (int i = 0; i < size; i++) {
        root[i] = i;
    }
}
public int find(int x) {
    if (x == root[x]) {
        return x;
    }
    return root[x] = find(root[x]);
}
public void union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX != rootY) {
        root[rootY] = rootX;
    }
}
public boolean connected(int x, int y) {
    return find(x) == find(y);
}
```

### Summary

-   A basic implementation of the `find` function:

```java
public int find(int x) {
    while (x != root[x]) {
        x = root[x];
    }
    return x;
}
```

*   The `find` function – optimized with path compression:

```java
public int find(int x) {
    if (x == root[x]) {
        return x;
    }
    return root[x] = find(root[x]);
}
```

-   A basic implementation of the `union` function:

```java
public void union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX != rootY) {
        root[rootY] = rootX;
    }
}
```

-   The `union` function – Optimized by union by rank:

```java
public void union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX != rootY) {
        if (rank[rootX] > rank[rootY]) {
            root[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            root[rootX] = rootY;
        } else {
            root[rootY] = rootX;
            rank[rootX] += 1;
        }
    }
}
```

## Number of Provinces (Medium 547)

There are `n` cities. Some of them are connected, while some are not. If city `a` is connected directly with city `b`, and city `b` is connected directly with city `c`, then city `a` is connected indirectly with city `c`.

A **province** is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an `n x n` matrix `isConnected` where `isConnected[i][j] = 1` if the `ith` city and the `jth` city are directly connected, and `isConnected[i][j] = 0` otherwise.

Return *the total number of **provinces***.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/12/24/graph1.jpg)

```
Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
Output: 2
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/12/24/graph2.jpg)

```
Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3
```

**Constraints:**

-   `1 <= n <= 200`
-   `n == isConnected.length`
-   `n == isConnected[i].length`
-   `isConnected[i][j]` is `1` or `0`.
-   `isConnected[i][i] == 1`
-   `isConnected[i][j] == isConnected[j][i]`

### Standard Solution

#### Solution #1 DFS

*   Similar to counting the number of island problems, using DFS, but be careful that row and column all mean the same nodes.
*   When creating `visited` array to record the occurrence, we only need a 1D array. DFS also 1D are enough.
*   Loop through all the nodes. At each node, we find all connected nodes and mark them as `visited`, next time just ignore all the visited nodes.
*   In this way, we can find all the provinces. 

```java
public int findCircleNum(int[][] isConnected) {
    int[] visited = new int[isConnected.length];
    int count = 0;
    // loop through the nodes and check if they are visited
    for (int i = 0; i < isConnected.length; i++){
        if (visited[i] == 0){
            dfs(isConnected, visited, i);
            count++;
        }
    }
    return count;
}

public void dfs(int[][] M, int[] visited, int i){
    // find all the connected node with i
    for (int j = 0; j < M.length; j++){
        if (M[i][j] == 1 && visited[j] == 0){
            visited[j] = 1;
            dfs(M, visited, j);
        }
    }
}
```

-   Time complexity: $O(n^2)$. The complete matrix of size $n^2$ is traversed.
-   Space complexity: $O(n)$. `visited` array of size n is used.

#### Solution #2 BFS

*   Using queue to store nodes and let it poll to find other connected components.
*   Each time poll from the queue, we mark it as `visited`, and try to find all its neighbors.
*   Put the neighbors in the queue, and repeat the process.
*   Count it as a province when the queue is empty, and go to the next node.

```java
public int findCircleNum(int[][] M){
    int[] visited = new int[M.length];
    int count = 0;
    Queue<Integer> queue = new LinkedList<>();
    
    for (int i = 0; i < M.length; i++){
        if (visited[i] == 0){
            queue.add(i);
            while (!queue.isEmpty()){
                int s = queue.remove();
                visited[s] = 1;
                for (int j = 0; j < M.length; j++){
                    if (M[s][j] == 1 && visited[j] == 0){
                        queue.add(j);
                    }
                }
            }
            count++;
        }
    }
    return count;
}
```

-   Time complexity: $O(n^2)$. The complete matrix of size $n^2$ is traversed.
-   Space complexity: $O(n)$. A `queue` and `visited` array of size n is used.

#### Solution #3 Using Union-Find Method

*   Follow the template and realize the union method and find the method.
*   Union find is commonly used to determine the number of connected components in a graph.

```java
public class Solution {
    int find(int parent[], int i) {
        if (parent[i] == -1)
            return i;
        // recursively find the root
        return find(parent, parent[i]);
    }

    void union(int parent[], int x, int y) {
        int xset = find(parent, x);
        int yset = find(parent, y);
        // connect
        if (xset != yset)
            parent[xset] = yset;
    }
    public int findCircleNum(int[][] M) {
        int[] parent = new int[M.length];
        Arrays.fill(parent, -1);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                // union the nodes together, find parent and connect
                if (M[i][j] == 1 && i != j) {
                    union(parent, i, j);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            // find how many roots exist = how many provinces
            if (parent[i] == -1)
                count++;
        }
        return count;
    }
}
```

-   Time complexity: $O(n^3)$. We traverse over the complete matrix once. Union and find operations take $O(n)$ time in the worst case.
-   Space complexity: $O(n)$. `parent` array of size n is used.
