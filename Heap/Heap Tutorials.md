# Heap Tutorials

## Priority Queue

*   A priority queue is an abstract data type similar to a regular queue or stack data structure in which each element additionally has a "priority" associated with it. 
*   In a priority queue, an element with high priority is served before an element with low priority.
*   **Heap vs. PQ**
    *   A priority queue is an abstract data type, while a Heap is a data structure. 
    *   Therefore, a Heap is not a Priority Queue, but a way to implement a Priority Queue.

*   **Implementation**:
    *   There are multiple ways to implement a Priority Queue, such as array and linked list. However, these implementations only guarantee $O(1)$ the time complexity for either insertion or deletion, while the other operation will have a time complexity of $O(N)$. 
    *   On the other hand, implementing the priority queue with Heap will allow both insertion and deletion to have a time complexity of $O(\log N)$. 

## Heap

*   A **Heap** is a special type of binary tree. A heap is a binary tree that meets the following criteria:
    *   Is a **complete binary tree**;
    *   The value of each node must be **no greater than (or no less than)** the value of its child nodes.

*   A Heap has the following properties:

    -   Insertion of an element into the Heap has a time complexity of $O(\log N)$;

    -   Deletion of an element from the Heap has a time complexity of $O(\log N)$;

    -   The maximum/minimum value in the Heap can be obtained with $O(1)$ time complexity.

### Heap Classification

There are two kinds of heaps: **Max Heap** and **Min Heap**.

-   Max Heap: Each node in the Heap has a value **no less than** its child nodes. Therefore, the top element (root node) has the **largest** value in the Heap.
-   Min Heap: Each node in the Heap has a value **no larger than** its child nodes. Therefore, the top element (root node) has the **smallest** value in the Heap.

<img src="https://leetcode.com/explore/learn/card/Figures/heap_explore/1_1_min_max_heap_diagram_new.png" alt="img" style="zoom:50%;" />

*   [Heap Insertion](https://leetcode.com/explore/learn/card/heap/643/heap/4019/)
*   [Heap Deletion](https://leetcode.com/explore/learn/card/heap/643/heap/4020/)

### Implementation of a Heap

*   Use an array to implement a heap

*   Min Heap

    ```java
    // Implementing "Min Heap"
    public class MinHeap {
        // Create a complete binary tree using an array
        // Then use the binary tree to construct a Heap
        int[] minHeap;
        // the number of elements is needed when instantiating an array
        // heapSize records the size of the array
        int heapSize;
        // realSize records the number of elements in the Heap
        int realSize = 0;
    
        public MinHeap(int heapSize) {
            this.heapSize = heapSize;
            minHeap = new int[heapSize + 1];
            // To better track the indices of the binary tree, 
            // we will not use the 0-th element in the array
            // You can fill it with any value
            minHeap[0] = 0;
        }
    
        // Function to add an element
        public void add(int element) {
            realSize++;
            // If the number of elements in the Heap exceeds the preset heapSize
            // print "Added too many elements" and return
            if (realSize > heapSize) {
                System.out.println("Added too many elements!");
                realSize--;
                return;
            }
            // Add the element into the array
            minHeap[realSize] = element;
            // Index of the newly added element
            int index = realSize;
            // Parent node of the newly added element
            // Note if we use an array to represent the complete binary tree
            // and store the root node at index 1
            // index of the parent node of any node is [index of the node / 2]
            // index of the left child node is [index of the node * 2]
            // index of the right child node is [index of the node * 2 + 1]
            int parent = index / 2;
            // If the newly added element is smaller than its parent node,
            // its value will be exchanged with that of the parent node 
            while ( minHeap[index] < minHeap[parent] && index > 1 ) {
                int temp = minHeap[index];
                minHeap[index] = minHeap[parent];
                minHeap[parent] = temp;
                index = parent;
                parent = index / 2;
            }
        }
    
        // Get the top element of the Heap
        public int peek() {
            return minHeap[1];
        }
    
        // Delete the top element of the Heap
        public int pop() {
            // If the number of elements in the current Heap is 0,
            // print "Don't have any elements" and return a default value
            if (realSize < 1) {
                System.out.println("Don't have any element!");
                return Integer.MAX_VALUE;
            } else {
                // When there are still elements in the Heap
                // realSize >= 1
                int removeElement = minHeap[1];
                // Put the last element in the Heap to the top of Heap
                minHeap[1] = minHeap[realSize];
                realSize--;
                int index = 1;
                // When the deleted element is not a leaf node
                while (index <= realSize / 2) {
                    // the left child of the deleted element
                    int left = index * 2;
                    // the right child of the deleted element
                    int right = (index * 2) + 1;
                    // If the deleted element is larger than the left or right child
                    // its value needs to be exchanged with the smaller value
                    // of the left and right child
                    if (minHeap[index] > minHeap[left] || minHeap[index] > minHeap[right]) {
                        if (minHeap[left] < minHeap[right]) {
                            int temp = minHeap[left];
                            minHeap[left] = minHeap[index];
                            minHeap[index] = temp;
                            index = left;
                        } else {
                            // maxHeap[left] >= maxHeap[right]
                            int temp = minHeap[right];
                            minHeap[right] = minHeap[index];
                            minHeap[index] = temp;
                            index = right;
                        }
                    } else {
                        break;
                    }
                }
                return removeElement;
            } 
        }
    
        // return the number of elements in the Heap
        public int size() {
            return realSize;
        }
    
        public String toString() {
            if (realSize == 0) {
                return "No element!";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                for (int i = 1; i <= realSize; i++) {
                    sb.append(minHeap[i]);
                    sb.append(',');
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(']');
                return sb.toString();
            }
        }
    
        public static void main(String[] args) {
            // Test case
            MinHeap minHeap = new MinHeap(3);
            minHeap.add(3);
            minHeap.add(1);
            minHeap.add(2);
            // [1,3,2]
            System.out.println(minHeap.toString());
            // 1
            System.out.println(minHeap.peek());
            // 1
            System.out.println(minHeap.pop());
            // [2, 3]
            System.out.println(minHeap.toString());
            minHeap.add(4);
            // Add too many elements
            minHeap.add(5);
            // [2,3,4]
            System.out.println(minHeap.toString());
        }
    }
    
    ```

*   Max Heap

    ```java
    // Implementing "Max Heap"
    public class MaxHeap {
        // Create a complete binary tree using an array
        // Then use the binary tree to construct a Heap
        int[] maxHeap;
        // the number of elements is needed when instantiating an array
        // heapSize records the size of the array
        int heapSize;
        // realSize records the number of elements in the Heap
        int realSize = 0;
    
        public MaxHeap(int heapSize) {
            this.heapSize = heapSize;
            maxHeap = new int[heapSize + 1];
            // To better track the indices of the binary tree, 
            // we will not use the 0-th element in the array
            // You can fill it with any value
            maxHeap[0] = 0;
        }
    
        // Function to add an element
        public void add(int element) {
            realSize++;
            // If the number of elements in the Heap exceeds the preset heapSize
            // print "Added too many elements" and return
            if (realSize > heapSize) {
                System.out.println("Added too many elements!");
                realSize--;
                return;
            }
            // Add the element into the array
            maxHeap[realSize] = element;
            // Index of the newly added element
            int index = realSize;
            // Parent node of the newly added element
            // Note if we use an array to represent the complete binary tree
            // and store the root node at index 1
            // index of the parent node of any node is [index of the node / 2]
            // index of the left child node is [index of the node * 2]
            // index of the right child node is [index of the node * 2 + 1]
            
            int parent = index / 2;
            // If the newly added element is larger than its parent node,
            // its value will be exchanged with that of the parent node 
            while ( maxHeap[index] > maxHeap[parent] && index > 1 ) {
                int temp = maxHeap[index];
                maxHeap[index] = maxHeap[parent];
                maxHeap[parent] = temp;
                index = parent;
                parent = index / 2;
            }
        }
    
        // Get the top element of the Heap
        public int peek() {
            return maxHeap[1];
        }
    
        // Delete the top element of the Heap
        public int pop() {
            // If the number of elements in the current Heap is 0,
            // print "Don't have any elements" and return a default value
            if (realSize < 1) {
                System.out.println("Don't have any element!");
                return Integer.MIN_VALUE;
            } else {
                // When there are still elements in the Heap
                // realSize >= 1
                int removeElement = maxHeap[1];
                // Put the last element in the Heap to the top of Heap
                maxHeap[1] = maxHeap[realSize];
                realSize--;
                int index = 1;
                // When the deleted element is not a leaf node
                while (index <= realSize / 2) {
                    // the left child of the deleted element
                    int left = index * 2;
                    // the right child of the deleted element
                    int right = (index * 2) + 1;
                    // If the deleted element is smaller than the left or right child
                    // its value needs to be exchanged with the larger value
                    // of the left and right child
                    if (maxHeap[index] < maxHeap[left] || maxHeap[index] < maxHeap[right]) {
                        if (maxHeap[left] > maxHeap[right]) {
                            int temp = maxHeap[left];
                            maxHeap[left] = maxHeap[index];
                            maxHeap[index] = temp;
                            index = left;
                        } else {
                            // maxHeap[left] <= maxHeap[right]
                            int temp = maxHeap[right];
                            maxHeap[right] = maxHeap[index];
                            maxHeap[index] = temp;
                            index = right;
                        }
                    } else {
                        break;
                    }
                }
                return removeElement;
            } 
        }
    
        // return the number of elements in the Heap
        public int size() {
            return realSize;
        }
    
        public String toString() {
            if (realSize == 0) {
                return "No element!";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append('[');
                for (int i = 1; i <= realSize; i++) {
                    sb.append(maxHeap[i]);
                    sb.append(',');
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(']');
                return sb.toString();
            }
        }
    
        public static void main(String[] args) {
            // Test case
            MaxHeap maxheap = new MaxHeap(5);
            maxheap.add(1);
            maxheap.add(2);
            maxheap.add(3);
            // [3,1,2]
            System.out.println(maxheap.toString());
            // 3
            System.out.println(maxheap.peek());
            // 3
            System.out.println(maxheap.pop());
            System.out.println(maxheap.pop());
            System.out.println(maxheap.pop());
            // No element
            System.out.println(maxheap.toString());
            maxheap.add(4);
            // Add too many elements
            maxheap.add(5);
            // [4,1,2]
            System.out.println(maxheap.toString());
        }
    }
    
    ```

## Heap Construction

*   When creating a Heap, we can simultaneously perform the **heapify** operation. Heapify means converting a group of data into a Heap.
*   Time complexity: $O(N)$.
*   Space complexity: $O(N)$.

```java
// In Java, a Heap is represented by a Priority Queue
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Arrays;

// Construct an empty Min Heap
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Construct an empty Max Heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Construct a Heap with initial elements. 
// This process is named "Heapify".
// The Heap is a Min Heap
PriorityQueue<Integer> heapWithValues= new PriorityQueue<>(Arrays.asList(3, 1, 2));
```

*   Inserting an element
    *   Time complexity: $O(\log N)$
    *   Space complexity: $O(1)$

```java
// Insert an element to the Min Heap
minHeap.add(5);

// Insert an element to the Max Heap
maxHeap.add(5);
```

*   Getting the top element of the heap
    *   Time complexity: $O(1)$
    *   Space complexity: $O(1)$

```java
// Get top element from the Min Heap
// i.e. the smallest element
minHeap.peek();
// Get top element from the Max Heap
// i.e. the largest element
maxHeap.peek();
```

*   Deleting the top element
    *   Time complexity: $O(\log N)$
    *   Space complexity: $O(1)$

```java
// Delete top element from the Min Heap
minHeap.poll();

// Delete top element from the Max Heap
maxheap.poll();
```

*   Getting the length of a heap
    *   Time complexity: $O(1)$
    *   Space complexity: $O(1)$

```java
// Length of the Min Heap
minHeap.size();

// Length of the Max Heap
maxHeap.size();

// Note, in Java, apart from checking if the length of the Heap is 0, we can also use isEmpty()
// If there are no elements in the Heap, isEmpty() will return true;
// If there are still elements in the Heap, isEmpty() will return false;
```

*   Time and Space Complexity:

    | Heap method            | Time complexity | Space complexity |
    | ---------------------- | --------------- | ---------------- |
    | Construct a Heap       | $O(N)$          | $O(N)$           |
    | Insert an element      | $O(\log N)$     | $O(1)$           |
    | Get the top element    | $O(1)$          | $O(1)$           |
    | Delete the top element | $O(\log N)$     | $O(1)$           |
    | Get the size of a Heap | $O(1)$          | $O(1)$           |

## Common Methods of Heap

**Min Heap**

```java
// Code for Min Heap
import java.util.PriorityQueue;

public class App {
    public static void main(String[] args) {
        // Construct an instance of Min Heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Add 3，1，2 respectively to the Min Heap
        minHeap.add(3);
        minHeap.add(1);
        minHeap.add(2);
        
        // Check all elements in the Min Heap, the result is [1, 3, 2]
        System.out.println("minHeap: " + minHeap.toString());
        
        // Get the top element of the Min Heap
        int peekNum = minHeap.peek();
        
        // The result is 1
        System.out.println("peek number: " + peekNum);
        
        // Delete the top element in the Min Heap
        int pollNum = minHeap.poll();
        
        // The reult is 1
        System.out.println("poll number: " + pollNum);
        
        // Check the top element after deleting 1, the result is 2
        System.out.println("peek number: " + minHeap.peek());
        
        // Check all elements in the Min Heap, the result is [2,3]
        System.out.println("minHeap: " + minHeap.toString());
        
        // Check the number of elements in the Min Heap
        // Which is also the length of the Min Heap
        int heapSize = minHeap.size();
        
        // The result is 2
        System.out.println("minHeap size: " + heapSize);
        
        // Check if the Min Heap is empty
        boolean isEmpty = minHeap.isEmpty();
        
        // The result is false
        System.out.println("isEmpty: " + isEmpty);
    }
}
```

**Max Heap**

```java
// Code for Max Heap
import java.util.Collections;
import java.util.PriorityQueue;

public class App {
    public static void main(String[] args) {
        // Construct an instance of Max Heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        // Add 1，3，2 respectively to the Max Heap
        maxHeap.add(1);
        maxHeap.add(3);
        maxHeap.add(2);
        
        // Check all elements in the Max Heap, the result is [3, 1, 2]
        System.out.println("maxHeap: " + maxHeap.toString());
        
        // Get the top element of the Max Heap
        int peekNum = maxHeap.peek();
        
        // The result is 3
        System.out.println("peek number: " + peekNum);
        
        // Delete the top element in the Max Heap
        int pollNum = maxHeap.poll();
        
        // The reult is 3
        System.out.println("poll number: " + pollNum);
        
        // Check the top element after deleting 3, the result is 2
        System.out.println("peek number: " + maxHeap.peek());
        
        // Check all elements in the Max Heap, the result is [2,1]
        System.out.println("maxHeap: " + maxHeap.toString());
        
        // Check the number of elements in the Max Heap
        // Which is also the length of the Max Heap
        int heapSize = maxHeap.size();
        
        // The result is 2
        System.out.println("maxHeap size: " + heapSize);
        
        // Check if the Max Heap is empty
        boolean isEmpty = maxHeap.isEmpty();
        
        // The result is false
        System.out.println("isEmpty: " + isEmpty);
    }
}
```

