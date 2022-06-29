# BFS Tutorial

### Purpose

*   The most advantageous use case of “breadth-first search” is to efficiently find the shortest path between two vertices in a “graph” where **all edges have equal and positive weights**.

*   Time Complexity: $O(V + E)$. Here, V represents the number of vertices, and E represents the number of edges. We need to check every vertex and traverse through every edge in the graph. The time complexity is the same as it was for the DFS approach.
*   Space Complexity: $O(V)$. Generally, we will check if a vertex has been visited before adding it to the queue, so the queue will use at most $O(V)$ space. Keeping track of which vertices have been visited will also require $O(V)$ space.