# Union-Find & Graph Tutorial

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

## The Earliest Moment When Everyone Become Friends (Medium #1101)

**Question**: There are n people in a social group labeled from `0` to `n - 1`. You are given an array `logs` where `logs[i] = [timestampi, xi, yi]` indicates that `xi` and `yi` will be friends at the time `timestampi`.

Friendship is **symmetric**. That means if `a` is friends with `b`, then `b` is friends with `a`. Also, person `a` is acquainted with a person `b` if `a` is friends with `b`, or `a` is a friend of someone acquainted with `b`.

Return *the earliest time for which every person became acquainted with every other person*. If there is no such earliest time, return `-1`.

**Example 1:**

```
Input: logs = [[20190101,0,1],[20190104,3,4],[20190107,2,3],[20190211,1,5],[20190224,2,4],[20190301,0,3],[20190312,1,2],[20190322,4,5]], n = 6
Output: 20190301
Explanation: 
The first event occurs at timestamp = 20190101 and after 0 and 1 become friends we have the following friendship groups [0,1], [2], [3], [4], [5].
The second event occurs at timestamp = 20190104 and after 3 and 4 become friends we have the following friendship groups [0,1], [2], [3,4], [5].
The third event occurs at timestamp = 20190107 and after 2 and 3 become friends we have the following friendship groups [0,1], [2,3,4], [5].
The fourth event occurs at timestamp = 20190211 and after 1 and 5 become friends we have the following friendship groups [0,1,5], [2,3,4].
The fifth event occurs at timestamp = 20190224 and as 2 and 4 are already friends anything happens.
The sixth event occurs at timestamp = 20190301 and after 0 and 3 become friends we have that all become friends.
```

**Example 2:**

```
Input: logs = [[0,2,0],[1,0,1],[3,0,3],[4,1,2],[7,3,1]], n = 4
Output: 3
```

**Constraints:**

-   `2 <= n <= 100`
-   `1 <= logs.length <= 104`
-   `logs[i].length == 3`
-   `0 <= timestampi <= 109`
-   `0 <= xi, yi <= n - 1`
-   `xi != yi`
-   All the values `timestampi` are **unique**.
-   All the pairs `(xi, yi)` occur at most one time in the input.

### Standard Solution

#### Solution #1 Union-Find

*   Create a class of `union-find`, then implement the union and find a method
*   Compare the log time and sort the array
*   At the beginning, each person is a group, count if the group number can be reduced to 1

```java
class Solution {
    public int earliestAcq(int[][] logs, int n) {
        // union-find to group them together
        // when we lower the group num to only 1 (whole group), that is the earliest moment
        
        // sort the events in time order
        Arrays.sort(logs, new Comparator<int[]>(){
            @Override
            public int compare(int[] log1, int[] log2){
               Integer time1 = log1[0];
               Integer time2 = log2[0];
                return time1.compareTo(time2);
            }
        });
        // initially we have n groups, each of them is a group
        int groupCount = n;
        UnionFind uf = new UnionFind(n);
        
        for (int[] log : logs){
            int timestamp = log[0], person1 = log[1], person2 = log[2];
            
            // we merge the groups 
            if (uf.union(person1, person2)){
                groupCount--;
            }
            
            if (groupCount == 1){
                return timestamp;
            }
        }
        return -1;
    }
}
class UnionFind {
    private int[] group;
    private int[] rank;
    
    public UnionFind(int size){
        // initialize the union find class
        this.group = new int[size];
        this.rank = new int[size];
        for (int person = 0; person < size; person++){
            // each person is its own root
            this.group[person] = person;
            // each person rank 0
            this.rank[person] = 0;
        }
    }
    
    // return the root of group that the person belongs to
    public int find(int person){
        if (person != group[person]){
            group[person] = find(group[person]);
        }
        return group[person];
    }
    
    // merge the two groups, true if the groups are merged
    public boolean union(int a, int b){
        int groupA = this.find(a);
        int groupB = this.find(b);
        
        if (groupA == groupB){
            // they are already in the same group
            return false;
        }
        
        // merge the two groups
        if (rank[groupA] > rank[groupB]){
            group[groupB] = groupA;
        }
        else if (rank[groupA] < rank[groupB]){
            group[groupA] = group[groupB];
        }
        // they have same rank (discrete group)
        else {
            group[groupA] = group[groupB];
            rank[groupB]++;
        }
        return true;
    }
}
```

*   Let N be the number of people and M be the number of logs.
*   Time Complexity: $O(N + M \log M + M \alpha (N))$
    *   First of all, we sort the logs in the order of timestamp. The time complexity of (quick) sorting is $O(M \log M)$.
    *   Then we created a Union-Find data structure, which takes $O(N)$ time to initialize the array of group IDs.
    *   We then iterate through the sorted logs. At each iteration, we invoke the `union(a, b)` function. According to the statement we made above, the amortized time complexity of the entire process is $O(M \alpha (N))$.
    *   To sum up, the overall time complexity of our algorithm is $O(N + M \log M + M \alpha (N))$.

*   Space Complexity: $O(N + M)$ or $O(N + \log M)$
    *   The space complexity of our Union-Find data structure is $O(N)$, because we keep track of the group ID for each individual.
    *   The space complexity of the sorting algorithm depends on the implementation of each programming language.
    *   For instance, the `list.sort()` function in Python is implemented with the [Timsort](https://en.wikipedia.org/wiki/Timsort) algorithm whose space complexity is $O(M)$. While in Java, the [Arrays.sort()](https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#sort-byte:A-) is implemented as a variant of the quicksort algorithm whose space complexity is $O(\log{M})$.
    *   To sum up, the overall space complexity of the algorithm is $O(N + M)$ for Python and $O(N + \log M)$ for Java.

*   Another simplified version

```java
public int earliestAcq(int[][] logs, int n) {
    Arrays.sort(logs, (a, b) -> Integer.compare(a[0], b[0]));
    int count = n;
    int[] parent = new int[n];
    Arrays.fill(parent, -1);

    for (int[] log : logs) {
        if (find(parent, log[1]) != find(parent, log[2])) {
            parent[find(parent, log[1])] = log[2];
            count--;
        }
        if (count == 1) return log[0];
    }
    return -1;
}

private int find(int[] p, int f) {
    if (p[f] == -1) return f;
    return find(p, p[f]);
}
```

## Smallest String with Swaps (Medium #1202)

**Question**: You are given a string `s`, and an array of pairs of indices in the string `pairs` where `pairs[i] = [a, b]` indicates 2 indices(0-indexed) of the string.

You can swap the characters at any pair of indices in the given `pairs` **any number of times**.

Return the lexicographically smallest string that `s` can be changed to after using the swaps. 

**Example 1:**

```
Input: s = "dcab", pairs = [[0,3],[1,2]]
Output: "bacd"
Explaination: 
Swap s[0] and s[3], s = "bcad"
Swap s[1] and s[2], s = "bacd"
```

**Example 2:**

```
Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
Output: "abcd"
Explaination: 
Swap s[0] and s[3], s = "bcad"
Swap s[0] and s[2], s = "acbd"
Swap s[1] and s[2], s = "abcd"
```

**Example 3:**

```
Input: s = "cba", pairs = [[0,1],[1,2]]
Output: "abc"
Explaination: 
Swap s[0] and s[1], s = "bca"
Swap s[1] and s[2], s = "bac"
Swap s[0] and s[1], s = "abc"
```

**Constraints:**

-   `1 <= s.length <= 10^5`
-   `0 <= pairs.length <= 10^5`
-   `0 <= pairs[i][0], pairs[i][1] < s.length`
-   `s` only contains lower case English letters.

### Standard Solution

#### Solution #1 Union-Find

*   Get the index of the characters and union them into groups, save the group indices into hash map
*   Characters in the same group are interchangeable, so we can sort the characters in the same group and put them back into the string index

```java
class Solution {
    // swap the number and group them into the same group
    // within the group, construct a new string that meet the requirement
    // union-find (check if the same gorup, how many groups we have)
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        UnionFind uf = new UnionFind(s.length());
        // record the pairs and union them into groups
        for (List<Integer> pair : pairs){
            int edge1 = pair.get(0);
            int edge2 = pair.get(1);
            uf.union(edge1, edge2);
        }
        Map<Integer, List<Integer>> groupMap = new HashMap<>();
        
        // group the index of the string in the same group
        for (int index = 0; index < s.length(); index++){
            int root = uf.find(index);
            groupMap.putIfAbsent(root, new ArrayList<>());
            groupMap.get(root).add(index);
        }
        
        // sort the list based on the characters
        char[] resString = new char[s.length()];
        // iterate the elements in each group
        for (List<Integer> group : groupMap.values()){
            List<Character> substringCh = new ArrayList<>();
            for (int index : group){
                substringCh.add(s.charAt(index));
            }
            Collections.sort(substringCh);
            // put character based on the index, follow the index order
            for (int i = 0; i < substringCh.size(); i++){
                resString[group.get(i)] = substringCh.get(i);
            }
        }
        return new String(resString);
    }
}
class UnionFind {
    private int[] group;
    private int[] rank;
    
    public UnionFind(int size) {
        this.group = new int[size];
        this.rank = new int[size];
        for (int i = 0; i < size; i++){
            // each person is its own root
            group[i] = i;
            rank[i] = 0;
        }
    }
    public int find(int i){
        if (i != group[i]){
            group[i] = find(group[i]);
        }
        return group[i];
    }
    // merge two groups
    public boolean union(int ch1, int ch2){
        int root1 = find(ch1);
        int root2 = find(ch2);
        // check if they already in the same group
        if (root1 == root2){
            return false;
        }
        // merge two groups by rank
        if (rank[root1] > rank[root2]){
            group[root2] = group[root1];
        }
        else if (rank[root1] < rank[root2]){
            group[root1] = group[root2];
        }
        else {
            group[root1] = group[root2];
            rank[root2]++;
        }
        return true;
    }
}
```

*   Here, V represents the number of vertices (the length of the given string) and E represents the number of edges (the number of pairs).
*   Time complexity: $O((E + V) \cdot \alpha(V) + V \log V)$
*   Space complexity: $O(V)$

## Optimize Water Distribution in a Village (Hard #1168)

**Question**: There are `n` houses in a village. We want to supply water for all the houses by building wells and laying pipes.

For each house `i`, we can either build a well inside it directly with cost `wells[i - 1]` (note the `-1` due to **0-indexing**), or pipe in water from another well to it. The costs to lay pipes between houses are given by the array `pipes` where each `pipes[j] = [house1j, house2j, costj]` represents the cost to connect `house1j` and `house2j` together using a pipe. Connections are bidirectional, and there could be multiple valid connections between the same two houses with different costs.

Return *the minimum total cost to supply water to all houses*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/05/22/1359_ex1.png)

```
Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
Output: 3
Explanation: The image shows the costs of connecting houses using pipes.
The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
```

**Example 2:**

```
Input: n = 2, wells = [1,1], pipes = [[1,2,1],[1,2,2]]
Output: 2
Explanation: We can supply water with cost two using one of the three options:
Option 1:
  - Build a well inside house 1 with cost 1.
  - Build a well inside house 2 with cost 1.
The total cost will be 2.
Option 2:
  - Build a well inside house 1 with cost 1.
  - Connect house 2 with house 1 with cost 1.
The total cost will be 2.
Option 3:
  - Build a well inside house 2 with cost 1.
  - Connect house 1 with house 2 with cost 1.
The total cost will be 2.
Note that we can connect houses 1 and 2 with cost 1 or with cost 2 but we will always choose the cheapest option. 
```

**Constraints:**

-   `2 <= n <= 104`
-   `wells.length == n`
-   `0 <= wells[i] <= 105`
-   `1 <= pipes.length <= 104`
-   `pipes[j].length == 3`
-   `1 <= house1j, house2j <= n`
-   `0 <= costj <= 105`
-   `house1j != house2j`

### Standard Solution

#### Solution #1 Union-Find

*   The trick is to add **one virtual vertex** to the existing graph. Along with the addition of vertex, we also add edges between the virtual vertex and the rest of the vertices. Finally, we reassign the cost of each vertex to the corresponding newly-added edge.

*   Add all the cost-related edges to the same list, and sort the list
*   Union all the nodes, if the node has been unioned, then do not count the cost

```java
class Solution {
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        // the list for all prices related array
        List<int[]> orderedEdges = new ArrayList<>();
        
        // the cost if we have wells (index 0 is virtual helper) at nodes
        for (int i = 0; i < wells.length; i++){
            orderedEdges.add(new int[]{0, i + 1, wells[i]});
        }
        
        // the cost if we use pipes to connect
        for (int i = 0; i < pipes.length; i++){
            int[] edge = pipes[i];
            orderedEdges.add(edge);
        }
        
        // sort the price list 
        Collections.sort(orderedEdges, (a, b) -> a[2] - b[2]);
        
        // union the house together until they are in the same group(all have water)
        UnionFind uf = new UnionFind(n);
        int cost = 0;
        for (int[] edge : orderedEdges){
            int house1 = edge[0];
            int house2 = edge[1];
            int singleCost = edge[2];
            
            if (uf.union(house1, house2)){
                cost += singleCost;
            }
        }
        return cost;
    }
}

class UnionFind {
    private int[] group;
    private int[] rank;
    
    public UnionFind(int size){
        // container to hold the group id for each member
        // index of member starts from 1, add 1 more to container as node 0
        group = new int[size + 1];
        rank = new int[size + 1];
        for (int i = 0; i < size + 1; i++){
            group[i] = i;
            rank[i] = 0;
        }
    }
    
    public int find(int house){
        if (group[house] != house){
            group[house] = find(group[house]);
        }
        return group[house];
    }
    
    public boolean union(int house1, int house2){
        int root1 = find(house1);
        int root2 = find(house2);
        // already in the same group
        if (root1 == root2){
            return false;
        }
        
        // attach the group of lower rank to the one with higher rank
        if (rank[root1] > rank[root2]){
            group[root2] = group[root1];
        }
        else if (rank[root2] > rank[root1]){
            group[root1] = group[root2];
        }
        else {
            // at the same rank
            group[root1] = group[root2];
            rank[root2]++;
        }
        return true;
    }
}
```

*   Let N be the number of houses, and M be the number of pipes from the input.
*   Time Complexity: $O\big((N+M) \cdot \log(N+M) \big)$
    *   First, we build a list of edges, which takes $O(N + M)$ time.
    *   We then sort the list of edges, which takes $O\big((N+M) \cdot \log(N+M) \big)$ time.
    *   In the end, we iterate through the sorted edges. For each iteration, we invoke a Union-Find operation. Hence, the time complexity for iteration is $O\big( (N+M) * \log^{*}(N) \big)$.
    *   To sum up, the overall time complexity of the algorithm is $O\big((N+M) \cdot \log(N+M) \big)$ which is dominated by the sorting step.

*   Space Complexity: $O(N+M)$
    *   The space complexity of our Union-Find data structure is $O(N)$.
    *   The space required by the list of edges is $O(N+M)$.
    *   To sum up, the overall space complexity of the algorithm is $O(N+M)$ which is dominated by the list of edges.

## Longest Consecutive Sequence (Medium #128)

**Question**: Given an unsorted array of integers `nums`, return *the length of the longest consecutive elements sequence.*

You must write an algorithm that runs in `O(n)` time.

**Example 1:**

```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

**Example 2:**

```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
```

**Constraints:**

-   `0 <= nums.length <= 105`
-   `-109 <= nums[i] <= 109`

### My Solution

```java
class Solution {
    // abstract: return longest consecutive sequence in o(n)
    // union-find to group the number that are consecutive |a - b| = 1
    public int longestConsecutive(int[] nums) {
        int length = nums.length;
        UnionFind uf = new UnionFind(length);
        // key: number in nums, value: index of number in nums
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < length; i++){
            if (map.containsKey(nums[i])){
                continue;
            }
            if (map.containsKey(nums[i] - 1)){
                uf.union(i, map.get(nums[i] - 1));
            }
            if (map.containsKey(nums[i] + 1)){
                uf.union(i, map.get(nums[i] + 1));
            }
            map.put(nums[i], i);
        }
        return uf.getMaxSeqSize();
    }
}

// create union-find class
class UnionFind {
    private int[] group;
    private int[] rank;
    
    public UnionFind(int size){
        group = new int[size];
        rank = new int[size];
        
        for (int i = 0; i < size; i++){
            group[i] = i;
            rank[i] = 1;
        }
    }
    
    public int find(int index){
        if (group[index] != index){
            group[index] = find(group[index]);
        }
        return group[index];
    }
    
    public void union(int index1, int index2){
        int root1 = find(index1);
        int root2 = find(index2);
        
        if (root1 != root2){
            group[root1] = root2;
            rank[root2] += rank[root1];
        }
    }
    
    public int getMaxSeqSize(){
        int maxSize = 0;
        for (int singleRank : rank){
            maxSize = Math.max(maxSize, singleRank);
        }
        return maxSize;
    }
}
```

*   The complexity is $O(a(n))$ which is the inverse Ackerman function, but it is slow-growing that it's considered constant time as $O(n)$. It also can be $O(n \log n)$

### Standard Solution

#### Solution #1 HashSet and Intelligent Sequence Building

*   Optimize the brute force method, user HashSet to store the values in case duplicate

```java
public int longestConsecutive(int[] nums) {
    Set<Integer> num_set = new HashSet<Integer>();
    for (int num : nums) {
        num_set.add(num);
    }

    int longestStreak = 0;

    for (int num : num_set) {
        // in case already count it
        if (!num_set.contains(num-1)) {
            int currentNum = num;
            int currentStreak = 1;

            while (num_set.contains(currentNum+1)) {
                currentNum += 1;
                currentStreak += 1;
            }

            longestStreak = Math.max(longestStreak, currentStreak);
        }
    }

    return longestStreak;
}
```

*   Time complexity: $O(n)$.
*   Space complexity: $O(n)$.

#### Solution #2 Sorting

```java
public int longestConsecutive(int[] nums) {
    if (nums.length == 0) {
        return 0;
    }

    Arrays.sort(nums);

    int longestStreak = 1;
    int currentStreak = 1;

    for (int i = 1; i < nums.length; i++) {
        if (nums[i] != nums[i-1]) {
            if (nums[i] == nums[i-1]+1) {
                currentStreak += 1;
            }
            else {
                longestStreak = Math.max(longestStreak, currentStreak);
                currentStreak = 1;
            }
        }
    }

    return Math.max(longestStreak, currentStreak);
}
```

*   Time complexity: $O(n \log n)$. The main `for` loop does constant work n*n* times, so the algorithm's time complexity is dominated by the invocation of `sort`, which will run in $O(n \log n)$ time for any sensible implementation.

*   Space complexity: $O(1)$ (or $O(n)$). For the implementations provided here, the space complexity is constant because we sort the input array in place. If we are not allowed to modify the input array, we must spend linear space to store a sorted copy.

## Accounts Merge (Medium #721)

**Question**: Given a list of `accounts` where each element `accounts[i]` is a list of strings, where the first element `accounts[i][0]` is a name, and the rest of the elements are **emails** representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails **in sorted order**. The accounts themselves can be returned in **any order**.

**Example 1:**

```
Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
Explanation:
The first and second John's are the same person as they have the common email "johnsmith@mail.com".
The third John and Mary are different people as none of their email addresses are used by other accounts.
We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
```

**Example 2:**

```
Input: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]
Output: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]
```

**Constraints:**

-   `1 <= accounts.length <= 1000`
-   `2 <= accounts[i].length <= 10`
-   `1 <= accounts[i][j].length <= 30`
-   `accounts[i][0]` consists of English letters.
-   `accounts[i][j] (for j > 0)` is a valid email.

### Standard Solution

#### Solution #1 Union-Find

*   Group email with its person's index as a hashmap
*   If we have already seen this email(exists in the hashmap), it means there is a duplicate person, we can merge the person's index using the union-find method
*   Then we create another hashmap, store the person's index and corresponding emails using find
*   Finally sort the email list, use the index to find person's name

```java
class Solution {
    // abstract: merge accounts, return emails in sorted order
    // 1. create a union-find class to group the emails
    // 2. sort the emails in a list
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int accountNum = accounts.size();
        UnionFind uf = new UnionFind(accountNum);
        
        // map email to the account index
        Map<String, Integer> emails = new HashMap<>();
        
        for (int i = 0; i < accountNum; i++){
            int accountLength = accounts.get(i).size();
            
            for (int j = 1; j < accountLength; j++){
                // take the email from each account
                String email = accounts.get(i).get(j);
                String accountName = accounts.get(i).get(0);
                
                // assign the email to the account index
                if (!emails.containsKey(email)){
                    emails.put(email, i);
                }
                else {
                    // we have already seen this email before, duplicate email means merge person
                    uf.union(i, emails.get(email));
                }
            }
        }
        // store emails corresponding to persons' index
        Map<Integer, List<String>> indexEmails = new HashMap<Integer, List<String>>();
        for (String email : emails.keySet()){
            // the given host id of the email before merge
            int groupid = emails.get(email);
            // check the true group is who (some groups are already merged in uf class)
            int groupTrueid = uf.find(groupid);
            
            if (!indexEmails.containsKey(groupTrueid)){
                indexEmails.put(groupTrueid, new ArrayList<String>());
            }
            indexEmails.get(groupTrueid).add(email);
        }
        // sort the emails and add the account name
        List<List<String>> res = new ArrayList<>();
        for (int group : indexEmails.keySet()){
            List<String> indexEmail = indexEmails.get(group);
            Collections.sort(indexEmail);
            // put the name at the beginning of the list
            indexEmail.add(0, accounts.get(group).get(0));
            res.add(indexEmail);
        }
        return res;
    }
}

class UnionFind {
    int representative[];
    int size[];
    
    public UnionFind(int size){
        representative = new int[size];
        this.size = new int[size];
        
        for (int i = 0; i < size; i++){
            representative[i] = i;
            this.size[i] = 1;
        }
    }
    
    public int find(int x){
        if (x != representative[x]){
            representative[x] = find(representative[x]);
        }
        return representative[x];
    }
    
    public void union(int a, int b){
        int root1 = find(a);
        int root2 = find(b);
        
        if (root1 == root2) return;
        if (size[root1] > size[root2]){
            size[root1] += size[root2];
            representative[root2] = representative[root1];
        }
        else {
            size[root2] += size[root1];
            representative[root1] = representative[root2];
        }
    }
}
```

*   Here N is the number of accounts and K is the maximum length of an account.
*   Time complexity: $O(NK \log {NK})$
    *   We find the representative of all the emails, hence it will take $O(NK\alpha({N}))$ time. We are also sorting the components and the worst case will be when all emails end up belonging to the same component this will cost $O(NK(\log {NK}))$.
    *   Hence the total time complexity is $O(NK \cdot \log {NK} + NK \cdot \alpha({N}))$.

*   Space complexity: $O(NK)$
    *   List `representative`, `size` store information corresponding to each group so will take $O(N)$ space. All emails get stored in `emailGroup` and `component` hence space used is $O(NK)$.
