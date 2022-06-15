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

