# Dynamic Programming Tutorials

## Definition

*   **Dynamic Programming** (DP) is a programming paradigm that can systematically and efficiently explore all possible solutions to a problem.
    *   The problem can be broken down into "overlapping subproblems" - smaller versions of the original problem that are re-used multiple times.
    *   The problem has an "optimal substructure" - an optimal solution can be formed from optimal solutions to the overlapping subproblems of the original problem.
*   **Top-down and Bottom-up**
    *   Fibonacci sequence
    *   Memoization(recursion)
*   **When to use DP**
    *   **The first characteristic** that is common in DP problems is that the problem will ask for the optimum value (maximum or minimum) of something, or the number of ways there are to do something. (Sometimes it also should use greedy)
        *   What is the minimum cost of doing...
        *   What is the maximum profit from...
        *   How many ways are there to do it...
        *   What is the longest possible...
        *   Is it possible to reach a certain point...
    *   **The second characteristic** that is common in DP problems is that future "decisions" depends on earlier decisions. This characteristic is what makes a greedy algorithm invalid for a DP problem.

