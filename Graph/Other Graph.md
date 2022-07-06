# Graph Tutorial

## Minimum Spanning Tree

*   A **spanning tree** is a connected subgraph in an undirected graph where **all vertices** are connected with the **minimum number** of edges.
*   All pink edges `[(A, B), (A, C), (A, D), (A, E)]` form a tree, which is a spanning tree of this undirected graph. 

![img](https://leetcode.com/explore/learn/card/Figures/Graph_Explore/Spanning_Tree.png)

*   A **minimum spanning tree** is a spanning tree with the minimum possible total edge weight in a “weighted undirected graph”.

![img](https://leetcode.com/explore/learn/card/Figures/Graph_Explore/Minimum_Spanning_Tree.png)

*   A spanning tree formed by green edges `[(A, E), (A, B), (B, C), (C, D)]` is one of the minimum spanning trees in this weighted undirected graph. Actually, `[(A, E), (E, D), (A, B), (B, C)]` forms another minimum spanning tree of the weighted undirected graph. 

*   Cut concept

    *   First, in Graph theory, a “cut” is a partition of vertices in a “graph” into two disjoint subsets. Figure illustrates a “cut”, where `(B, A, E)` forms one subset, and `(C, D)` forms the other subset.
    *   A crossing edge is an edge that connects a vertex in one set with a vertex in the other set. In Figure 11, `(B, C)`, `(A, C)`, `(A, D)`, `(E, D)` are all “crossing edges”.

    ![img](https://leetcode.com/explore/learn/card/Figures/Graph_Explore/Cut_Property.png)

### Kruskal's Algorithm - produces Minimum Spanning Tree

*   **Process**
    *   Ascending sort all edges by their weight
    *   Add edges in that order into the minimum spanning tree. Skip the edges that would produce cycles in the minimum spanning tree.
    *   Repeat step 2 unitl N - 1 edges are added
*   Basically it is a greedy algorithm
*   Time complexity: $O(E \cdot \log E)$. Here, E represents the number of edges.
    *   At first, we need to sort all the edges of the graph in ascending order. Sorting will take $O(E \log E)$ time.
    *   Next, we can start building our minimum spanning tree by selecting which edges should be included. For each edge, we will look at whether both of the vertices of the edge belong to the same connected component; which is an $O(\alpha(V))$ operation, where $\alpha$ refers to the Inverse Ackermann function. In the worst case, the tree will not be complete until we reach the very last edge (the edge with the largest weight), so this process will take $O(E \alpha(V))$ time.
    *   Therefore, in total, the time complexity is $O(E \log E + E \alpha(V)) = O(E \log E)$.

*   Space Complexity: $O(V)$. V represents the number of vertices. Keeping track of the root of every vertex in the union-find data structure requires $O(V)$ space. However, depending on the sorting algorithm used, different amounts of auxiliary space will be required to sort the list of edges in place. For instance, Timsort (used by default in python) requires $O(E)$ space in the worst-case scenario, while Java uses a variant of quicksort whose space complexity is $O(\log E)$.

## Min Cost to Connect All Points (Medium #1584)

**Question**: You are given an array `points` representing integer coordinates of some points on a 2D-plane, where `points[i] = [xi, yi]`.

The cost of connecting two points `[xi, yi]` and `[xj, yj]` is the **manhattan distance** between them: `|xi - xj| + |yi - yj|`, where `|val|` denotes the absolute value of `val`.

Return *the minimum cost to make all points connected.* All points are connected if there is **exactly one** simple path between any two points.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/08/26/d.png)

```
Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
Output: 20
Explanation: 

We can connect the points as shown above to get the minimum cost of 20.
Notice that there is a unique path between every pair of points.
```

**Example 2:**

```
Input: points = [[3,12],[-2,5],[-4,1]]
Output: 18
```

**Constraints:**

-   `1 <= points.length <= 1000`
-   `-106 <= xi, yi <= 106`
-   All pairs `(xi, yi)` are distinct.

### My Solution & Kruskal's algorithm

```java
class Solution {
    // minimum spanning tree: kruskal's algorithm
    public int minCostConnectPoints(int[][] points) {
        // 1. calculate the edges, sort the edges
        int length = points.length;
        // point, two connected point
        List<int[]> edgeMap = new ArrayList<>();
        for (int i = 0; i < length; i++){
            for (int j = i + 1; j < length; j++){
                int mandis = Math.abs(points[j][0] - points[i][0]) 
                    + Math.abs(points[j][1] - points[i][1]);
                edgeMap.add(new int[]{mandis, i, j});
            }
        }
        Collections.sort(edgeMap, (a, b) -> Integer.compare(a[0], b[0]));
        // 2. connect the point with edge based on sorting, if create cycle, skip the edge
        // 3. unitl we have n - 1 edges in the graph, we have exactly 1 simple path
        int edgeNum = 0;
        int edgeSum = 0;
        UnionFind uf = new UnionFind(length);
        for (int[] edge : edgeMap){
            int point1 = edge[1];
            int point2 = edge[2];
            if (uf.union(point1, point2)){
                edgeSum += edge[0];
                edgeNum++;
            }
            if (edgeNum == length - 1) break;
        }
        return edgeSum;
    }
}
class UnionFind {
    private int[] group;
    private int[] rank;
    public UnionFind(int n){
        group = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++){
            group[i] = i;
        }
    }
    public int find(int i){
        if (i != group[i]){
            group[i] = find(group[i]);
        }
        return group[i];
    }
    public boolean union(int i, int j){
        int root1 = find(i);
        int root2 = find(j);
        
        if (root1 == root2){
            return false;
        }
        
        if (rank[root1] > rank[root2]){
            group[root2] = group[root1];
        }
        else if (rank[root2] > rank[root2]){
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

*   Use kruskal's algorithm to sort edges and combine them to a graph
*   Use Union-Find method to determine if form a cycle
*   Or we can use min heap so that we do not need to sort them
*   Same as the kruskal's solution
*   Time complexity: $O(N^2 \cdot \log(N))$
    -   First, we store $N \cdot (N-1) / 2 \approx N^2$ edges of our complete graph which will take $O(N^2)$ time, and sorting this array will take $O(N^2 \cdot \log(N^2))$ time.
    -   Then, we iterate over the array, and for each element, we perform a union-find operation. The amortized time complexity for union-find by rank and path compression is $O(\alpha(N))$, where $\alpha(N)$ is [Inverse Ackermann Function](https://en.wikipedia.org/wiki/Ackermann_function#:~:text=Inverse[edit],is primitive recursive.), which is nearly constant, even for large values of N.
    -   Thus, the overall time complexity is $O(N^2 + N^2 \cdot \log(N^2) + N^2 \cdot \alpha(N)) \approx O(N^2 \cdot \log(N^2) \approx O(N^2 \cdot \log(N))$.
*   Space complexity: $O(N^2)$
    -   We use an array to store all $N \cdot (N-1) / 2 \approx N^2$ edges of our graph.
    -   UnionFind object `uf` uses two arrays each of size `N` to store the group id and rank of all the nodes.
    -   Thus, the overall space complexity is $O(N^2 + N) \approx O(N^2)$.

### Standard Solution

#### Solution #1 Prim's Algorithm

*   A more efficient way to track which edges are available and which of these edges has the lowest weight is to use a min-heap.

```java
public int minCostConnectPoints(int[][] points){
    int n = points.length;
    
    // min-heap to store minimum weight edge at top
    PriorityQueue<Pair<Integer, Integer>> heap = new PriorityQueue<>(
        (a, b) -> (a.getKey()) - b.getKey()));
    // track nodes which are included in mst.
    boolean[] inMST = new boolean[n];
    
    heap.add(new Pair(0, 0));
    int mstCost = 0;
    int edgesUsed = 0;
    
    while (edgesUsed < n){
        Pair<Integer, Integer> topElement = heap.poll();
        int weight = topElement.getKey();
        int currNode = topElement.getValue();
        
        // if node was already included in MST we will discard this edge
        if (inMST[currNode]){
            continue;
        }
        intMST[currNode] = true;
        mstCost += weight;
        edgesUsed++;
        
        for (int nextNode = 0; nextNode < n; nextNode++){
            // if next node is not in mst, then edge from curr node
            // to next node can be pushed in the priority queue.
            if (!inMST(nextNode)){
                int nextWeight = Math.abs(points[currNode][0] - points[nextNode][0] +
                                         Math.abs(points[currNode][1] - points[nextNode][1]));
                heap.add(new Pair(nextWeight, nextNode));
            }
        }
    }
    return mstCost;
}
```

-   Time complexity: $O(N^2 \cdot \log(N))$.
    -   In the worst-case, we push/pop $N \cdot (N-1) / 2 \approx N^2$  edges of our graph in the heap. Each push/pop operation takes $O(\log(N^2)) \approx \log(N)$ time.
    -   Thus, the overall time complexity is $O(N^2 \cdot \log(N))$.
-   Space complexity: $O(N^2)$.
    -   In the worst-case, we push $N \cdot (N-1) / 2 \approx N^2$ edges into the heap.
    -   We use an array `inMSTinMST` of size `NN` to mark which nodes are included in MST.
    -   Thus, the overall space complexity is $O(N^2 + N) \approx O(N^2)$.

## Prim's Algorithm

*   Mark visited nodes and non-visited node sets. Each time we choose the minimum weighted edge to connect. Then move the connected node to the visited set. Repeat until the non-visited set is empty.
*   “Kruskal’s algorithm” expands the “minimum spanning tree” by adding edges. Whereas “Prim’s algorithm” expands the “minimum spanning tree” by adding vertices.
*   Use greedy strategy
*   Time Complexity: $O(E \cdot \log V)$ for Binary heap, and $O(E+V \cdot \log V)$ for Fibonacci heap.
    -   For a Binary heap:
        -   We need $O(V + E)$ time to traverse all the vertices of the graph, and we store in the heap all the vertices that are not yet included in our minimum spanning tree.
        -   Extracting minimum elements and key decreasing operations cost $O(\log V)$ time.
        -   Therefore, the overall time complexity is $O(V + E) \cdot O(\log V) = O(E \cdot \log V)$.
    -   For a Fibonacci heap:
        -   Extracting minimum elements will take $O(\log V)$ time while key decreasing operation will take amortized $O(1)$ time, therefore, the total time complexity would be $O(E + V \cdot \log V)$.
*   Space Complexity: $O(V)$. We need to store V vertices in our data structure.

## Single-Source Shortest Path

*   However, the “breadth-first search” algorithm can only solve the “shortest path” problem in “unweighted graphs”. But in real life, we often need to find the “shortest path” in a “weighted graph”.
*   Two single source shortest path algorithm
    *   Dijkstra’s algorithm
    *   Bellman-Ford algorithm

### Dijkstra's Algorithm

*   “Dijkstra’s algorithm” solves the “single-source shortest path” problem in a weighted directed graph with non-negative weights.
*   Record the shortest distance from the source vertex and the previous vertex

![image-20220705105812724](/Users/wenjunhan/Library/Application Support/typora-user-images/image-20220705105812724.png)

*   V represents the number of vertices, and E represents the number of edges.
    *   Time Complexity: $O(E + V \log V)$ when a Fibonacci heap is used, or $O(V + E \log V)$ for a Binary heap.
        -   If you use a [Fibonacci heap](https://en.wikipedia.org/wiki/Fibonacci_heap) to implement the “min-heap”, extracting the minimum element will take $O(\log V)$ time while the key decreasing operation will take amortized $O(1)$ time, therefore, the total time complexity would be $O(E + V \log V)$.
        -   If you use a [Binary heap](https://en.wikipedia.org/wiki/Binary_heap), then the time complexity would be $O(V + E \log V)$.
    *   Space Complexity: $O(V)$. We need to store V vertices in our data structure.

## Network Delay Time (Medium #743)

**Question**: You are given a network of `n` nodes, labeled from `1` to `n`. You are also given `times`, a list of travel times as directed edges `times[i] = (ui, vi, wi)`, where `ui` is the source node, `vi` is the target node, and `wi` is the time it takes for a signal to travel from source to target.

We will send a signal from a given node `k`. Return *the **minimum** time it takes for all the* `n` *nodes to receive the signal*. If it is impossible for all the `n` nodes to receive the signal, return `-1`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/05/23/931_example_1.png)

```
Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
Output: 2
```

**Example 2:**

```
Input: times = [[1,2,1]], n = 2, k = 1
Output: 1
```

**Example 3:**

```
Input: times = [[1,2,1]], n = 2, k = 2
Output: -1
```

**Constraints:**

-   `1 <= k <= n <= 100`
-   `1 <= times.length <= 6000`
-   `times[i].length == 3`
-   `1 <= ui, vi <= n`
-   `ui != vi`
-   `0 <= wi <= 100`
-   All the pairs `(ui, vi)` are **unique**. (i.e., no multiple edges.)

### My Solution

*   DFS solution

```java
Map<Integer, List<int[]>> adjList = new HashMap<>();// key: node, value: pair(nextnode, weighted edge)

public int networkDelayTime(int[][] times, int n, int k) {
    // dfs: directed edges, use it to build adjacency list using map
    for (int[] time : times){
        if (!adjList.containsKey(time[0])){
            adjList.put(time[0], new ArrayList<>());
        }
        adjList.get(time[0]).add(new int[]{time[1], time[2]});
    }

    // sort the edges connecting to every node, sort can be faster(no sort is ok)
    for (int node : adjList.keySet()){
        Collections.sort(adjList.get(node), (a, b) -> a[1] - b[1]);
    }

    // start from the source node, traverse the adjacency list and + 1 for time in each traversal
    int[] signalReceivedTime = new int[n + 1];
    Arrays.fill(signalReceivedTime, Integer.MAX_VALUE);
    dfs(k, 0, signalReceivedTime);

    int answer = Integer.MIN_VALUE;

    for (int i = 1; i <= n; i++){
        answer = Math.max(answer, signalReceivedTime[i]);
    }
    return answer == Integer.MAX_VALUE ? -1 : answer;
}

public void dfs(int source, int currTime, int[] seen){
    if (currTime >= seen[source]){
        return;
    }
    seen[source] = currTime;

    if (!adjList.containsKey(source)){
        // no next node
        return;
    }

    for (int[] nextNode : adjList.get(source)){
        dfs(nextNode[0], currTime + nextNode[1], seen);
    }
}
```

*   Time complexity: $O((N - 1)! + E \log E)$

    In a complete graph with N nodes and $N(N - 1)$ directed edges, we can end up traversing all the paths of all the possible lengths. The total number of paths can be represented as $\sum_{len=1}^{N} {{N} \choose {len}} * len!$, where `len` is the length of the path, which can be 1 to N. This number can be represented as $e.N!$, it's essentially equal to the [number of arrangements](https://oeis.org/wiki/Number_of_arrangements) for N elements. In our case, the first element will always be K, hence the number of arrangements is e.$(N - 1)!$.

    Also, we sort the edges corresponding to each node; this can be expressed as $E \log E$ because sorting each small bucket of outgoing edges is bounded by sorting all of them, using the inequality $x \log x + y \log y \leq (x+y) \log (x+y)$. 

*   Space complexity: $O(N + E)$

    Building the adjacency list will take $O(E)$ space and the run-time stack for DFS can have at most N*N* active functions calls hence, $O(N)$ space.

### Standard Solution

#### Solution #1 BFS

*   The concept is the same as DFS but the traversal based on a queue

```java
class Solution {
    // Adjacency list
    Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();

    private void BFS(int[] signalReceivedAt, int sourceNode) {
        Queue<Integer> q = new LinkedList<>();
        q.add(sourceNode);
        
        // Time for starting node is 0
        signalReceivedAt[sourceNode] = 0;
        
        while (!q.isEmpty()) {
            int currNode = q.remove();
            
            if (!adj.containsKey(currNode)) {
                continue;
            }
            
            // Broadcast the signal to adjacent nodes
            for (Pair<Integer, Integer> edge : adj.get(currNode)) {
                int time = edge.getKey();
                int neighborNode = edge.getValue();
                
                // Fastest signal time for neighborNode so far
                // signalReceivedAt[currNode] + time : 
                // time when signal reaches neighborNode
                int arrivalTime = signalReceivedAt[currNode] + time;
                if (signalReceivedAt[neighborNode] > arrivalTime) {
                    signalReceivedAt[neighborNode] = arrivalTime;
                    q.add(neighborNode);
                }
            }
        }
    }
    
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build the adjacency list
        for (int[] time : times) {
            int source = time[0];
            int dest = time[1];
            int travelTime = time[2];
            
            adj.putIfAbsent(source, new ArrayList<>());
            adj.get(source).add(new Pair(travelTime, dest));
        }
        
        int[] signalReceivedAt = new int[n + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);
        
        BFS(signalReceivedAt, k);
        
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            answer = Math.max(answer, signalReceivedAt[i]);
        }
        
        // INT_MAX signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}
```

*   Time complexity: $O(N \cdot E)$

    Each of the N nodes can be added to the queue for all the edges connected to it; hence in a complete graph, the total number of operations would be $O(NE)$. Also, finding the minimum time required in `signalReceivedAt` takes $O(N)$.

*   Space complexity: $O(N \cdot E)$

    Building the adjacency list will take $O(E)$ space and the queue for BFS will use $O(N \cdot E)$ space as there can be a much number of nodes in the queue.

#### Solution #2 Dijkstra's Algorithm

```java
class Solution {
    // Adjacency list
    Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();
    
    private void dijkstra(int[] signalReceivedAt, int source, int n) {
        Queue<Pair<Integer, Integer>> pq = new PriorityQueue<Pair<Integer,Integer>>
            (Comparator.comparing(Pair::getKey));
        pq.add(new Pair(0, source));
        
        // Time for starting node is 0
        signalReceivedAt[source] = 0;
        
        while (!pq.isEmpty()) {
            Pair<Integer, Integer> topPair = pq.remove();
            
            int currNode = topPair.getValue();
            int currNodeTime = topPair.getKey();
            
            if (currNodeTime > signalReceivedAt[currNode]) {
                continue;
            }
            
            if (!adj.containsKey(currNode)) {
                continue;
            }
            
            // Broadcast the signal to adjacent nodes
            for (Pair<Integer, Integer> edge : adj.get(currNode)) {
                int time = edge.getKey();
                int neighborNode = edge.getValue();
                
                // Fastest signal time for neighborNode so far
                // signalReceivedAt[currNode] + time : 
                // time when signal reaches neighborNode
                if (signalReceivedAt[neighborNode] > currNodeTime + time) {
                    signalReceivedAt[neighborNode] = currNodeTime + time;
                    pq.add(new Pair(signalReceivedAt[neighborNode], neighborNode));
                }
            }
        }
    }
    
    public int networkDelayTime(int[][] times, int n, int k) {
        // Build the adjacency list
        for (int[] time : times) {
            int source = time[0];
            int dest = time[1];
            int travelTime = time[2];
            
            adj.putIfAbsent(source, new ArrayList<>());
            adj.get(source).add(new Pair(travelTime, dest));
        }
        
        int[] signalReceivedAt = new int[n + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);
        
        dijkstra(signalReceivedAt, k, n);
        
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            answer = Math.max(answer, signalReceivedAt[i]);
        }
        
        // INT_MAX signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}
```

-   Time complexity: $O(N + E \log N)$. Dijkstra's Algorithm takes $O(E \log N)$. Finding the minimum time required in `signalReceivedAt` takes $O(N)$.
    -   The maximum number of vertices that could be added to the priority queue is E. Thus, push and pop operations on the priority queue take $O(\log E)$ time. The value of E can be at most $N \cdot (N - 1)$. Therefore, $O(\log E)$ is equivalent to $O(\log N^2)$ which in turn equivalent to $O(2 \cdot \log N)$. Hence, the time complexity for priority queue operations equals $O(\log N)$.
    -   Although the number of vertices in the priority queue could be equal to E, we will only visit each vertex only once. If we encounter a vertex for the second time, then `currNodeTime` will be greater than `signalReceivedAt[currNode]`, and we can continue to the next vertex in the priority queue. Hence, in total E edges will be traversed, and for each edge, there could be one priority queue insertion operation.
    -   Hence, the time complexity is equal to $O(N + E \log N)$.
-   Space complexity: $O(N + E)$
    -   Building the adjacency list will take $O(E)$ space. Dijkstra's algorithm takes $O(E)$ space for priority queue because each vertex could be added to the priority queue $N - 1$ time which makes it $N * (N - 1)$ and $O(N^2)$ is equivalent to $O(E)$. `signalReceivedAt` takes $O(N)$ space.
