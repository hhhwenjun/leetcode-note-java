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

*   “Dijkstra’s algorithm” solves the “single-source shortest path” problem in a weighted directed graph with **non-negative** weights.
*   Record the shortest distance from the source vertex and the previous vertex

![image-20220705105812724](/Users/wenjunhan/Library/Application Support/typora-user-images/image-20220705105812724.png)

*   V represents the number of vertices, and E represents the number of edges.
    *   Time Complexity: $O(E + V \log V)$ when a Fibonacci heap is used, or $O(V + E \log V)$ for a Binary heap.
        -   If you use a [Fibonacci heap](https://en.wikipedia.org/wiki/Fibonacci_heap) to implement the “min-heap”, extracting the minimum element will take $O(\log V)$ time while the key decreasing operation will take amortized $O(1)$ time, therefore, the total time complexity would be $O(E + V \log V)$.
        -   If you use a [Binary heap](https://en.wikipedia.org/wiki/Binary_heap), then the time complexity would be $O(V + E \log V)$.
    *   Space Complexity: $O(V)$. We need to store V vertices in our data structure.

### Bellman-Ford Algorithm

*   In a “graph with no negative-weight cycles” with N vertices, the shortest path between any two vertices has at most N-1 edges.
*   No solution can be found when we have a “graph with negative weight cycles”.
*   Time Complexity: $O(V \cdot E)$. In the worst-case scenario, when all the vertices are connected with each other, we need to check every path from every vertex; this results in $O(V \cdot E)$ time complexity.
*   Space Complexity: $O(V)$. We use two arrays of length V. One to store the shortest distance from the source vertex using at most `k-1` edges. The other is to store the shortest distance from the source vertex using at most `k` edges.

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

## Cheapest Flights Within K Stops (Medium #787)

**Question**: There are `n` cities connected by some number of flights. You are given an array `flights` where `flights[i] = [fromi, toi, pricei]` indicates that there is a flight from city `fromi` to city `toi` with cost `pricei`.

You are also given three integers `src`, `dst`, and `k`, return ***the cheapest price** from* `src` *to* `dst` *with at most* `k` *stops.* If there is no such route, return `-1`. 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2022/03/18/cheapest-flights-within-k-stops-3drawio.png)

```
Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
Output: 700
Explanation:
The graph is shown above.
The optimal path with at most 1 stop from city 0 to 3 is marked in red and has cost 100 + 600 = 700.
Note that the path through cities [0,1,2,3] is cheaper but is invalid because it uses 2 stops.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2022/03/18/cheapest-flights-within-k-stops-1drawio.png)

```
Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
Output: 200
Explanation:
The graph is shown above.
The optimal path with at most 1 stop from city 0 to 2 is marked in red and has cost 100 + 100 = 200.
```

**Example 3:**

![img](https://assets.leetcode.com/uploads/2022/03/18/cheapest-flights-within-k-stops-2drawio.png)

```
Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
Output: 500
Explanation:
The graph is shown above.
The optimal path with no stops from city 0 to 2 is marked in red and has cost 500.
```

**Constraints:**

-   `1 <= n <= 100`
-   `0 <= flights.length <= (n * (n - 1) / 2)`
-   `flights[i].length == 3`
-   `0 <= fromi, toi < n`
-   `fromi != toi`
-   `1 <= pricei <= 104`
-   There will not be any multiple flights between two cities.
-   `0 <= src, dst, k < n`
-   `src != dst`

### My Solution

*   It does not work out and don't know why, should be almost same as the first solution

```java
// dijkstra's algorithm with priorityqueue(minheap)
// make adjacency list for cities
Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();

public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    for (int[] flight : flights){
        int source = flight[0];
        int dest = flight[1];
        int price = flight[2];

        adj.putIfAbsent(source, new ArrayList<>());
        adj.get(source).add(new Pair(price, dest));
    }
    // create array for flight prices to the location
    int[] cheapPrice = new int[n];
    int[] currentStops = new int[n];
    Arrays.fill(cheapPrice, Integer.MAX_VALUE);
    Arrays.fill(currentStops, Integer.MAX_VALUE);

    dijkstra(cheapPrice, src, dst, k, currentStops);
    return cheapPrice[dst] == Integer.MAX_VALUE ? -1 : cheapPrice[dst];

}

public void dijkstra(int[] cheapPrice, int src, int dst, int k, int[] currentStops){
    // poll from heap and calculate cheapest price from heap
    PriorityQueue<Pair<Integer, int[]>> heap = new PriorityQueue<Pair<Integer, int[]>>(
        Comparator.comparing(Pair::getKey)
    );
    heap.add(new Pair(0, new int[]{src, k}));
    while(!heap.isEmpty()){
        Pair<Integer, int[]> topElem = heap.poll();
        int currLoc = topElem.getValue()[0];
        int currStopNum = topElem.getValue()[1];
        int currPrice = topElem.getKey();

        if (!adj.containsKey(currLoc)){
            continue;
        }
        if (currStopNum < 0){
            continue;
        }

        // traverse the adjlist
        for (Pair<Integer, Integer> edge : adj.get(currLoc)){
            int nextPrice = edge.getKey();
            int nextLoc = edge.getValue();
            if (cheapPrice[nextLoc] > currPrice + nextPrice){
                cheapPrice[nextLoc] = currPrice + nextPrice;
                currentStops[nextLoc] = currStopNum;
                heap.add(new Pair(cheapPrice[nextLoc], new int[]{nextLoc, currStopNum - 1}));
            }
            else if (currStopNum < currentStops[nextLoc]){
                heap.add(new Pair(cheapPrice[nextLoc], new int[]{nextLoc, currStopNum - 1}));
            }
        }
    }
}
```

### Standard Solution

#### Solution #1 Dijkstra's Algorithm

*   Use a min-heap to store the price and other information
*   Create arrays to record the best stop number and the cheapest price
*   If the price is lower, record it to array; If the stop number is lower, record it to array

```java
class Solution {
    
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
     
        // Build the adjacency matrix
        int adjMatrix[][] = new int[n][n];
        for (int[] flight: flights) {
            adjMatrix[flight[0]][flight[1]] = flight[2];
        }
        
        // Shortest distances array
        int[] distances = new int[n];
        
        // Shortest steps array
        int[] currentStops = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(currentStops, Integer.MAX_VALUE);
        distances[src] = 0;
        currentStops[src] = 0;
        
        // The priority queue would contain (node, cost, stops)
        PriorityQueue<int[]> minHeap = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
        minHeap.offer(new int[]{src, 0, 0});
        
         while (!minHeap.isEmpty()) {
             
            int[] info = minHeap.poll();
            int node = info[0], stops = info[2], cost = info[1];
             
             // If destination is reached, return the cost to get here
            if (node == dst) {
                return cost;
            }
             
            // If there are no more steps left, continue 
            if (stops == K + 1) {
                continue;
            }
             
            // Examine and relax all neighboring edges if possible 
            for (int nei = 0; nei < n; nei++) {
                if (adjMatrix[node][nei] > 0) {
                    int dU = cost, dV = distances[nei], wUV = adjMatrix[node][nei];
                    
                    // Better cost?
                    if (dU + wUV < dV) {
                        minHeap.offer(new int[]{nei, dU + wUV, stops + 1});
                        distances[nei] = dU + wUV;
                        currentStops[nei] = stops;
                    }
                    else if (stops < currentStops[nei]) {
                        // Better steps?
                        minHeap.offer(new int[]{nei, dU + wUV, stops + 1});
                    }
                }
            }
         }
        
        return distances[dst] == Integer.MAX_VALUE? -1 : distances[dst];
    }
}
```

*   Time Complexity: Let E represent the number of flights and V represent the number of cities. The time complexity is mainly governed by the number of times we pop and push into the heap. We will process each node (city) at least once, and for each city popped from the queue, we iterate over its adjacency matrix and can potentially add all its neighbors to the heap. Thus, the time is taken for extract min, and then addition to the heap (or simply, heap replace) would be $\text{O}(\text{V}^2 \cdot \text{log V})$
*   Space Complexity: $\text{O}(\text{V}^2)$ is the overall space complexity. $\text{O}(\text{V})$ is occupied by the two dictionaries and also by the heap and $\text{V}^2$ by the adjacency matrix structure. As mentioned above, there might be duplicate cities in the heap with different distances and number of stops due to our implementation. But we are not taking that into consideration here. This is the space complexity of the traditional Dijkstra's, and it doesn't change with the algorithm modifications (not the implementation modifications) we've done here.

#### Solution #2 Bellman-Ford Algorithm

*   Create a previous and a current array to store the cost, and keep swapping them so that each time we calculate the current price based on the previous prices. 

```java
// Bellman Ford Algorithm
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    if (src == dst) {
        return 0;
    }

    int[] previous = new int[n];
    int[] current = new int[n];
    for (int i = 0; i < n; i++) {
        previous[i] = Integer.MAX_VALUE;
        current[i] = Integer.MAX_VALUE;
    }
    previous[src] = 0;
    for (int i = 1; i < k + 2; i++) {
        current[src] = 0;
        for (int[] flight : flights) {
            int previous_flight = flight[0];
            int current_flight = flight[1];
            int cost = flight[2];

            if (previous[previous_flight] < Integer.MAX_VALUE) {
                current[current_flight] = Math.min(current[current_flight],
                                                   previous[previous_flight] + cost);
            }
        }
        previous = current.clone();
    }
    return current[dst] == Integer.MAX_VALUE ? -1 : current[dst];
}
```

## Path With Minimum Effort (Medium #1631)

**Question**: You are a hiker preparing for an upcoming hike. You are given `heights`, a 2D array of size `rows x columns`, where `heights[row][col]` represents the height of cell `(row, col)`. You are situated in the top-left cell, `(0, 0)`, and you hope to travel to the bottom-right cell, `(rows-1, columns-1)` (i.e., **0-indexed**). You can move **up**, **down**, **left**, or **right**, and you wish to find a route that requires the minimum **effort**.

A route's **effort** is the **maximum absolute difference** in heights between two consecutive cells of the route.

Return *the minimum **effort** required to travel from the top-left cell to the bottom-right cell.*

**Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/10/04/ex1.png" alt="img" style="zoom:50%;" />

```
Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
Output: 2
Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
```

**Example 2:**

<img src="https://assets.leetcode.com/uploads/2020/10/04/ex2.png" alt="img" style="zoom:50%;" />

```
Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
Output: 1
Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is better than route [1,3,5,3,5].
```

**Example 3:**

<img src="https://assets.leetcode.com/uploads/2020/10/04/ex3.png" alt="img" style="zoom:50%;" />

```
Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
Output: 0
Explanation: This route does not require any effort.
```

**Constraints:**

-   `rows == heights.length`
-   `columns == heights[i].length`
-   `1 <= rows, columns <= 100`
-   `1 <= heights[i][j] <= 106`

### Standard Solution

#### Solution #1 Binary Search

```java
class Solution {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public int minimumEffortPath(int[][] heights) {
        // binary search
        int left = 0;
        int right = 1000000;
        int result = right;
        while (left <= right){
            int mid = (left + right) / 2;
            // mid is the highest affordable difference
            if (canReachDestination(heights, mid)){
                result = Math.min(result, mid);
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }
        return result;
    }
    
    // use bfs to check if we can reach destination with max absolute difference k
    boolean canReachDestination(int[][] heights, int k){
        int row = heights.length;
        int col = heights[0].length;
        Deque<Cell> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[heights.length][heights[0].length];
        queue.addLast(new Cell(0, 0));
        visited[0][0] = true;
        while (!queue.isEmpty()){
            Cell curr = queue.removeFirst();
            // if reach the destination
            if (curr.x == row - 1 && curr.y == col - 1){
                return true;
            }
            for (int[] direction : directions){
                int adjacentX = curr.x + direction[0];
                int adjacentY = curr.y + direction[1];
                if (isValidCell(adjacentX, adjacentY, row, col) &&
                   !visited[adjacentX][adjacentY]){
                    int currentDifference = Math.abs(heights[adjacentX][adjacentY] - heights[curr.x][curr.y]);
                    if (currentDifference <= k){
                        visited[adjacentX][adjacentY] = true;
                        queue.addLast(new Cell(adjacentX, adjacentY));
                    }
                }
            }
        }
        return false;
    }
    
    boolean isValidCell(int x, int y, int row, int col){
        return x >= 0 && x <= row - 1 && y >= 0 && y <= col - 1; 
    }
}

class Cell {
    int x;
    int y;
    
    Cell(int x, int y){
        this.x = x;
        this.y = y;
    }
}
```

*   Let m be the number of rows and n be the number of columns for the matrix $\text{height}$.

*   Time Complexity: $\mathcal{O}(m \cdot n)$. We do a binary search to calculate the `mid` values and then do Breadth First Search on the matrix for each of those values.

*   Space Complexity: $\mathcal{O}(m \cdot n)$ as we use a queue and visited array of size m \cdot n*m*⋅*n*

## Kahn's Algorithm for Topological Sorting

*   Topological sorting provides a linear sorting based on the required ordering between vertices in directed acyclic graphs.
    *   In what order to finish the tasks
    *   If directed graph forms a cycle, the algorithm cannot work
    *   There must be at least one vertex in the “graph” with an “in-degree” of 0.
*   **Concepts**
    *   In-degree: number of arrows point to the node
    *   Out-degree: number of arrows point from the node out to somewhere
*   **Steps**
    *   Transfer the orders to a directed graph
    *   Calculate the in-degrees and out-degrees of the node, and put the node with no in-degree to a queue as the order.
    *   Each time pull out the node, we delete 1 in-degree to other related nodes. 
*   **Complexity Analysis**
    *   Time Complexity: $O(V + E)$
        *   First, we will build an adjacency list. This allows us to efficiently check which courses depend on each prerequisite course. Building the adjacency list will take $O(E)$ time, as we must iterate over all edges.
        *   Next, we will repeatedly visit each course (vertex) with an in-degree of zero and decrement the in-degree of all courses that have this course as a prerequisite (outgoing edges). In the worst-case scenario, we will visit every vertex and decrement every outgoing edge once. Thus, this part will take $O(V + E)$ time.
        *   Therefore, the total time complexity is $O(E) + O(V + E) = O(V + E)$
    *   Space Complexity: $O(V+E)$
        *   The adjacency list uses $O(E)$ space.
        *   Storing the in-degree for each vertex requires $O(V)$ space.
        *   The queue can contain at most V nodes, so the queue also requires $O(V)$ space.

## Course Schedule II (Medium #210)

**Question**: There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you **must** take course `bi` first if you want to take course `ai`.

-   For example, the pair `[0, 1]`, indicates that to take course `0` you have to first take course `1`.

Return *the ordering of courses you should take to finish all courses*. If there are many valid answers, return **any** of them. If it is impossible to finish all courses, return **an empty array**.

**Example 1:**

```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
```

**Example 2:**

```
Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0.
So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3].
```

**Example 3:**

```
Input: numCourses = 1, prerequisites = []
Output: [0]
```

**Constraints:**

-   `1 <= numCourses <= 2000`
-   `0 <= prerequisites.length <= numCourses * (numCourses - 1)`
-   `prerequisites[i].length == 2`
-   `0 <= ai, bi < numCourses`
-   `ai != bi`
-   All the pairs `[ai, bi]` are **distinct**.

### My Solution

```java
// topological sorting: kahn's algorithm
// 1. adjacency list, find out the neighbors(dfs)
// 2. count the in-degree of each node, put node into a queue
// 3. if the in-degree is reduced to 0, put it into the result
int numCourses;
List<List<Integer>> adjList;

public int[] findOrder(int numCourses, int[][] prerequisites) {
    // initialization
    this.numCourses = numCourses;
    int[] indegree = new int[numCourses];
    adjList = new ArrayList<>();
    for (int i = 0; i < numCourses; i++){
        adjList.add(new ArrayList());
    }

    // build adjacency list and count the in-degree of node
    for (int[] pre : prerequisites){
        indegree[pre[0]]++;
        // directed graph
        adjList.get(pre[1]).add(pre[0]);
    }
    // kahn's algorithm to reduce the in-degree to 0 for finding order
    boolean[] seen = new boolean[numCourses]; // incase we are in a cycle
    List<Integer> res = new ArrayList<>();
    kahnAlgorithm(res, seen, indegree);
    if (res.size() != numCourses) return new int[0];
    int[] resArray = new int[numCourses];
    for (int i = 0; i < numCourses; i++){
        resArray[i] = res.get(i);
    }
    return resArray;
}

public void kahnAlgorithm(List<Integer> res, boolean[] seen, int[] indegree){
    for (int i = 0; i < numCourses; i++){
        if (seen[i]){
            continue;
        }
        // the course we can learn right now
        if (indegree[i] == 0){
            res.add(i);
            seen[i] = true;
            List<Integer> neighbors = adjList.get(i);
            for (int neighbor : neighbors){
                indegree[neighbor]--;
                kahnAlgorithm(res, seen, indegree);
            }
        }
    }
}
```

*   Use topological sorting concept, but very slow in time complexity probably because of recursion. 

### Standard Solution

#### Solution #1 DFS

*   Use integer to mark the status of the course
*   Just simply use dfs to traverse all the nodes by neighbor nodes, assume 0 is the start point

```java
class Solution {
  static int WHITE = 1;
  static int GRAY = 2;
  static int BLACK = 3;

  boolean isPossible;
  Map<Integer, Integer> color;
  Map<Integer, List<Integer>> adjList;
  List<Integer> topologicalOrder;

  private void init(int numCourses) {
    this.isPossible = true;
    this.color = new HashMap<Integer, Integer>();
    this.adjList = new HashMap<Integer, List<Integer>>();
    this.topologicalOrder = new ArrayList<Integer>();

    // By default all vertces are WHITE
    for (int i = 0; i < numCourses; i++) {
      this.color.put(i, WHITE);
    }
  }

  private void dfs(int node) {

    // Don't recurse further if we found a cycle already
    if (!this.isPossible) {
      return;
    }

    // Start the recursion
    this.color.put(node, GRAY);

    // Traverse on neighboring vertices
    for (Integer neighbor : this.adjList.getOrDefault(node, new ArrayList<Integer>())) {
      if (this.color.get(neighbor) == WHITE) {
        this.dfs(neighbor);
      } else if (this.color.get(neighbor) == GRAY) {
        // An edge to a GRAY vertex represents a cycle
        this.isPossible = false;
      }
    }

    // Recursion ends. We mark it as black
    this.color.put(node, BLACK);
    this.topologicalOrder.add(node);
  }

  public int[] findOrder(int numCourses, int[][] prerequisites) {

    this.init(numCourses);

    // Create the adjacency list representation of the graph
    for (int i = 0; i < prerequisites.length; i++) {
      int dest = prerequisites[i][0];
      int src = prerequisites[i][1];
      List<Integer> lst = adjList.getOrDefault(src, new ArrayList<Integer>());
      lst.add(dest);
      adjList.put(src, lst);
    }

    // If the node is unprocessed, then call dfs on it.
    for (int i = 0; i < numCourses; i++) {
      if (this.color.get(i) == WHITE) {
        this.dfs(i);
      }
    }

    int[] order;
    if (this.isPossible) {
      order = new int[numCourses];
      for (int i = 0; i < numCourses; i++) {
        order[i] = this.topologicalOrder.get(numCourses - i - 1);
      }
    } else {
      order = new int[0];
    }

    return order;
  }
}
```

-   Time Complexity: $O(V + E)$ where V represents the number of vertices and E represents the number of edges. Essentially we iterate through each node and each vertex in the graph once and only once.
-   Space Complexity: $O(V + E)$.
    -   We use the adjacency list to represent our graph initially. The space occupied is defined by the number of edges because for each node as the key, we have all its adjacent nodes in the form of a list as the value. Hence, $O(E)$
    -   Additionally, we apply recursion in our algorithm, which in worst case will incur $O(E)$ extra space in the function call stack.
    -   To sum up, the overall space complexity is $O(V + E)$.

#### Solution #2 Topological Sorting (In-degree)

*   Same algorithm as my solution, keep track of all nodes in the graph with 0 in-degree.
*   Iterate over all the edges in the input and create an adjacency list and also a map of node v/s in-degree.

```java
public int[] findOrder(int numCourses, int[][] prerequisites){
    int[] result = new int[numCourses];
    // edge cases
    if (numCourses == 0){
        return result;
    }
    if (prerequisites == null || prerequisites.length == 0){
        for (int i = 0; i < numCourses; i++){
            result[i] = i;
        }
        return result;
    }
    int[] indegree = new int[numCourses];
    Queue<Integer> zeroDegree = new LinkedList<>();
    for (int[] pre : prerequisites){
        indegree[pre[0]]++;
    }
    // check if anynode has no prerequisite
    for (int i = 0; i < indegree.length; i++){
        if (indegree[i] == 0){
            zeroDegree.add(i);
        }
    }
    // no course has no prerequisite, has to be a cycle
    if (zeroDegree.isEmpty()){
        return new int[0];
    }
    int index = 0; 
    while (!zeroDegree.isEmpty()){
        int course = zeroDegree.poll();
        result[index] = course;
        index++;
        for (int[] pre : prerequisites){
            if (pre[1] == course){
                indegree[pre[0]]--;
                if (indegree[pre[0]] == 0){
                    zeroDegree.add(pre[0]);
                }
            }
        }
    }
    for (int in : indegree){
        if (in != 0){
            return new int[0];
        }
    }
    return result;
}
```

*   Time Complexity: $O(V + E)$ where V represents the number of vertices and E represents the number of edges. We pop each node exactly once from the zero in-degree queues, and that gives us V. Also, for each vertex, we iterate over its adjacency list, and in totality, we iterate over all the edges in the graph, which gives us E. Hence, $O(V + E)$
*   Space Complexity: $O(V + E)$. We use an intermediate queue data structure to keep all the nodes with 0 in-degree. In the worst case, there won't be any prerequisite relationship, and the queue will contain all the vertices initially since all of them will have 0 in-degree. That gives us $O(V)$. Additionally, we also used the adjacency list to represent our graph initially. The space occupied is defined by the number of edges because for each node as the key, we have all its adjacent nodes in the form of a list as the value. Hence, $O(E)$. So, the overall space complexity is $O(V + E)$
