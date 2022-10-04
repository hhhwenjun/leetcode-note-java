# Queue Problems Part #1

## Design Hit Counter(Medium #362)

**Question**: Design a hit counter which counts the number of hits received in the past `5` minutes (i.e., the past `300` seconds).

Your system should accept a `timestamp` parameter (**in seconds** granularity), and you may assume that calls are being made to the system in chronological order (i.e., `timestamp` is monotonically increasing). Several hits may arrive roughly at the same time.

Implement the `HitCounter` class:

- `HitCounter()` Initializes the object of the hit counter system.
- `void hit(int timestamp)` Records a hit that happened at `timestamp` (**in seconds**). Several hits may happen at the same `timestamp`.
- `int getHits(int timestamp)` Returns the number of hits in the past 5 minutes from `timestamp` (i.e., the past `300` seconds).

 **Example 1:**

```
Input
["HitCounter", "hit", "hit", "hit", "getHits", "hit", "getHits", "getHits"]
[[], [1], [2], [3], [4], [300], [300], [301]]
Output
[null, null, null, null, 3, null, 4, 3]

Explanation
HitCounter hitCounter = new HitCounter();
hitCounter.hit(1);       // hit at timestamp 1.
hitCounter.hit(2);       // hit at timestamp 2.
hitCounter.hit(3);       // hit at timestamp 3.
hitCounter.getHits(4);   // get hits at timestamp 4, return 3.
hitCounter.hit(300);     // hit at timestamp 300.
hitCounter.getHits(300); // get hits at timestamp 300, return 4.
hitCounter.getHits(301); // get hits at timestamp 301, return 3.
```

 **Constraints:**

- `1 <= timestamp <= 2 * 109`
- All the calls are being made to the system in chronological order (i.e., `timestamp` is monotonically increasing).
- At most `300` calls will be made to `hit` and `getHits`.

### My Solution

```java
class HitCounter {
    private LinkedList<Integer> hitQueue;   
    
    public HitCounter() {
        hitQueue = new LinkedList<Integer>();
    }   
    public void hit(int timestamp) {
        this.hitQueue.addLast(timestamp);
    }    
     /** return number of hits in the past 5 mins**/
    private int findHitsInPast5Min(int pastTime){
        
        while(this.hitQueue.peekFirst() != null){
            if (this.hitQueue.getFirst() <= pastTime){
                this.hitQueue.removeFirst();
            }
            else break;
        }
        return this.hitQueue.size();
    }   
    public int getHits(int timestamp) {
        int pastTime = timestamp - 300;        
        return findHitsInPast5Min(pastTime);
    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */
```

### Standard Solution

#### Solution #1 Queue

* Similar to my solution: all the timestamps that we will encounter are going to be in increasing order. 
* Should be clean of API doc comments
* This is the case of considering the elements in the FIFO manner (First in first out) which is best solved by using the "queue" data structure.

```java
class HitCounter{
    private Queue<Integer> hits;
    
    /** Initialize your data structure here.**/
    public HitCounter(){
        this.hits = new LinkedList<Integer>();
    }
    
    /**
    * Record a hit
    * @param timestamp
    */
    public void hit(int timestamp){
        this.hits.add(timestamp);
    }
    
    /**
    * Return the number of hits in the past 5 minutes
    * @param timestamp
    */
    public int getHits(int timestamp){
        while (!this.hits.isEmpty()){
            int diff = timestamp - this.hits.peek();
            if (diff >= 300) this.hits.remove();
            else break;
        }
        return this.hits.size();
    }
}
```

* Time complexity: `hit` is $O(1)$, while `getHits` is $O(N)$.
* Space complexity: The queue can have up to N elements, hence overall space complexity of the approach is $O(N)$

#### Solution #2 Using Deque with Pairs

* Similar to the solution 1 but using deque
* Similar to my solution
* Avoid this repetitive removals of the same value
* If we encounter the hit for the same timestamp, instead of appending a new entry in the deque, we simply increment the count of the latest timestamp.

```java
class HitCounter{
    private int total;
    private Deque<Pair<Integer, Integer>> hits;
    
    /** Initialize your data structure here.**/
    public HitCounter(){
        // Initialize total to 0
        this.total = 0;
        this.hits = new LinkedList<Pair<Integer, Integer>>();
    }
    
    /**
    * Record a hit
    * @param timestamp
    */
    public void hit(int timestamp){
        if (this.hits.isEmpty() || this.hits.getLast().getKey() != timestamp){
            // Insert the new timestamp with count = 1
            this.hits.add(new Pair<Integer, Integer>(timestamp, 1));
        }
        else {
            // Update the count of latest timestamp by incrementing the count by 1
            // Obtain the current count of the lastest timestamp
            int prevCount = this.hits.getLast().getValue();
            // Remove the last pair of (timestamp, count) from the deque
            this.hits.removeLast();
            // Insert a new pair of (timestamp, updated count) in the deque
            this.hits.add(new Pair<Integer, Integer>(timestamp, prevCount + 1));
        }
        // Increment total
        this.total++;
    }
    
    /**
    * Return the number of hits in the past 5 minutes
    * @param timestamp
    */
    public int getHits(int timestamp){
        while (!this.hits.isEmpty()){
            int diff = timestamp - this.hits.getFirst().getKey();
            if (diff >= 300){
                // Decrement total by the count of the oldest timestamp
                this.total -= this.hits.getFirst().getValue();
                this.hits.removeFirst();
            }
            else break;
        }
        return this.total;
    }
}
```

- Time Complexity:
  - `hit` - $O(1)$.
  - `getHits` - If there are a total of n pairs present in the deque, worst case time complexity can be $O(n)$. However, by clubbing all the timestamps with same value together, for the $i^{th} $ timestamp with k repetitions, the time complexity is $O(1)$ as here, instead of removing all those k repetitions, we only remove a single entry from the deque.
- Space complexity: If there are a total of $N$ elements that we encountered throughout, the space complexity is $O(N)$ (similar to Approach 1). However, in the case of repetitions, the space required for storing those k values $O(1)$.

## Design Circular Queue (Medium #622)

**Question**: Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle, and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer".

One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

Implementation of the `MyCircularQueue` class:

-   `MyCircularQueue(k)` Initializes the object with the size of the queue to be `k`.
-   `int Front()` Gets the front item from the queue. If the queue is empty, return `-1`.
-   `int Rear()` Gets the last item from the queue. If the queue is empty, return `-1`.
-   `boolean enQueue(int value)` Inserts an element into the circular queue. Return `true` if the operation is successful.
-   `boolean deQueue()` Deletes an element from the circular queue. Return `true` if the operation is successful.
-   `boolean isEmpty()` Checks whether the circular queue is empty or not.
-   `boolean isFull()` Checks whether the circular queue is full or not.

You must solve the problem without using the built-in queue data structure in your programming language. 

**Example 1:**

```
Input
["MyCircularQueue", "enQueue", "enQueue", "enQueue", "enQueue", "Rear", "isFull", "deQueue", "enQueue", "Rear"]
[[3], [1], [2], [3], [4], [], [], [], [4], []]
Output
[null, true, true, true, false, 3, true, true, true, 4]

Explanation
MyCircularQueue myCircularQueue = new MyCircularQueue(3);
myCircularQueue.enQueue(1); // return True
myCircularQueue.enQueue(2); // return True
myCircularQueue.enQueue(3); // return True
myCircularQueue.enQueue(4); // return False
myCircularQueue.Rear();     // return 3
myCircularQueue.isFull();   // return True
myCircularQueue.deQueue();  // return True
myCircularQueue.enQueue(4); // return True
myCircularQueue.Rear();     // return 4
```

**Constraints:**

-   `1 <= k <= 1000`
-   `0 <= value <= 1000`
-   At most `3000` calls will be made to `enQueue`, `deQueue`, `Front`, `Rear`, `isEmpty`, and `isFull`.

### My Solution

```java
class MyCircularQueue {
    private int[] queue;
    private int size;
    // two pointers to track the queue
    private int front;
    private int rear;
    private int capacity;

    public MyCircularQueue(int k) {
        queue = new int[k];
        capacity = k;
        size = 0;
        front = 0;
        rear = 0;
    }
    
    public boolean enQueue(int value) {
        if (size >= capacity){
            return false;
        }
        // queue is empty
        if (size == 0){
            queue[rear] = value;
        }
        else {
            // in-case we pass the end or array(circular)
            rear = (rear + 1) % capacity;
            queue[rear] = value;
        }
        size++;
        return true;
    }
    
    public boolean deQueue() {
        if (size <= 0){
            return false;
        }
        // only 1 element left in the queue
        if (rear == front){
            queue[rear] = 0;
        }
        else {
            queue[front] = 0;
            front = (front + 1) % capacity;
        }
        size--;
        return true;
    }
    
    public int Front() {
        if (size <= 0){
            return -1;
        }
        return queue[front];
    }
    public int Rear() {
        if (size <= 0){
            return -1;
        }
        return queue[rear];
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public boolean isFull() {
        return size == capacity;
    }
}
```

*   The solution works well; the space complexity is $O(N)$ and the time complexity is $O(1)$
*   Two pointers solution is easy to find the front and rear

### Standard Solution

#### Solution #1 Array

*   Similar idea but only using 1 pointer

*   A simplified code, don't need to clear the cell to 0, only left the new element cover the old element.

```java
class MyCircularQueue {

  private int[] queue;
  private int headIndex;
  private int count;
  private int capacity;

  /** Initialize your data structure here. Set the size of the queue to be k. */
  public MyCircularQueue(int k) {
    this.capacity = k;
    this.queue = new int[k];
    this.headIndex = 0;
    this.count = 0;
  }

  /** Insert an element into the circular queue. Return true if the operation is successful. */
  public boolean enQueue(int value) {
    if (this.count == this.capacity)
      return false;
    this.queue[(this.headIndex + this.count) % this.capacity] = value;
    this.count += 1;
    return true;
  }

  /** Delete an element from the circular queue. Return true if the operation is successful. */
  public boolean deQueue() {
    if (this.count == 0)
      return false;
    this.headIndex = (this.headIndex + 1) % this.capacity;
    this.count -= 1;
    return true;
  }

  /** Get the front item from the queue. */
  public int Front() {
    if (this.count == 0)
      return -1;
    return this.queue[this.headIndex];
  }

  /** Get the last item from the queue. */
  public int Rear() {
    if (this.count == 0)
      return -1;
    int tailIndex = (this.headIndex + this.count - 1) % this.capacity;
    return this.queue[tailIndex];
  }

  /** Checks whether the circular queue is empty or not. */
  public boolean isEmpty() {
    return (this.count == 0);
  }

  /** Checks whether the circular queue is full or not. */
  public boolean isFull() {
    return (this.count == this.capacity);
  }
}
```

-   Time complexity: $\mathcal{O}(1)$. All of the methods in our circular data structure are of constant time complexity.
-   Space Complexity: $\mathcal{O}(N)$. The overall space complexity of the data structure is linear, where N is the pre-assigned capacity of the queue. *However, it is worth mentioning that the memory consumption of the data structure remains at its pre-assigned capacity during its entire life cycle.*

## Moving Average from Data Stream (Easy #346)

**Question**: Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

Implement the `MovingAverage` class:

-   `MovingAverage(int size)` Initializes the object with the size of the window `size`.
-   `double next(int val)` Returns the moving average of the last `size` values of the stream.

**Example 1:**

```
Input
["MovingAverage", "next", "next", "next", "next"]
[[3], [1], [10], [3], [5]]
Output
[null, 1.0, 5.5, 4.66667, 6.0]

Explanation
MovingAverage movingAverage = new MovingAverage(3);
movingAverage.next(1); // return 1.0 = 1 / 1
movingAverage.next(10); // return 5.5 = (1 + 10) / 2
movingAverage.next(3); // return 4.66667 = (1 + 10 + 3) / 3
movingAverage.next(5); // return 6.0 = (10 + 3 + 5) / 3
```

**Constraints:**

-   `1 <= size <= 1000`
-   `-105 <= val <= 105`
-   At most `104` calls will be made to `next`.

### My Solution

*   Each time only keep the  same size of elements in the list

```java
class MovingAverage {
    
    private int size;
    private List<Integer> list;

    public MovingAverage(int size) {
        this.size = size;
        list = new ArrayList<>();
    }
    
    public double next(int val) {
        list.add(val);
        if (list.size() > size){
            list.remove(0);
        }
        int denominator = list.size();
        int sum = 0;
        for (int i = 0; i < denominator; i++){
            int curr = list.get(i);
            sum += curr;
        }
        return (double)sum / (double)denominator;
    }
}
```

### Standard Solution

#### Solution #1 Array or List

```java
class MovingAverage {
  int size;
  List queue = new ArrayList<Integer>();
  public MovingAverage(int size) {
    this.size = size;
  }

  public double next(int val) {
    queue.add(val);
    // calculate the sum of the moving window
    int windowSum = 0;
    for(int i = Math.max(0, queue.size() - size); i < queue.size(); ++i)
      windowSum += (int)queue.get(i);

    return windowSum * 1.0 / Math.min(queue.size(), size);
  }
}
```

-   Time Complexity: $\mathcal{O}(N)$ where N is the size of the moving window since we need to retrieve N elements from the queue at each invocation of `next(val)` function.
-   Space Complexity: $\mathcal{O}(M)$, where M is the length of the queue which would grow at each invocation of the `next(val)` function.

#### Solution #2 Deque

*   Each time add value to the sum, delete the value in the tail if exceed the specified size

```java
class MovingAverage {
  int size, windowSum = 0, count = 0;
  Deque queue = new ArrayDeque<Integer>();

  public MovingAverage(int size) {
    this.size = size;
  }

  public double next(int val) {
    ++count;
    // calculate the new sum by shifting the window
    queue.add(val);
    int tail = count > size ? (int)queue.poll() : 0;

    windowSum = windowSum - tail + val;

    return windowSum * 1.0 / Math.min(size, count);
  }
}
```

-   Time Complexity: $\mathcal{O}(1)$, as we explained in intuition.
-   Space Complexity: $\mathcal{O}(N)$, where N is the size of the moving window.

## Flatten Nested List Iterator (Medium #341)

**Question**: You are given a nested list of integers `nestedList`. Each element is either an integer or a list whose elements may also be integers or other lists. Implement an iterator to flatten it.

Implement the `NestedIterator` class:

-   `NestedIterator(List<NestedInteger> nestedList)` Initializes the iterator with the nested list `nestedList`.
-   `int next()` Returns the next integer in the nested list.
-   `boolean hasNext()` Returns `true` if there are still some integers in the nested list and `false` otherwise.

Your code will be tested with the following pseudocode:

```
initialize iterator with nestedList
res = []
while iterator.hasNext()
    append iterator.next() to the end of res
return res
```

If `res` matches the expected flattened list, then your code will be judged as correct.

**Example 1:**

```
Input: nestedList = [[1,1],2,[1,1]]
Output: [1,1,2,1,1]
Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].
```

**Example 2:**

```
Input: nestedList = [1,[4,[6]]]
Output: [1,4,6]
Explanation: By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].
```

**Constraints:**

-   `1 <= nestedList.length <= 500`
-   The values of the integers in the nested list is in the range `[-106, 106]`.

### Standard Solution

*   Need to fully understand the `iterator`. `next()` would return the element
*   Flatten the elements into a list or other structure, then use `iterator` to iterate the structure.

#### Solution #1 Recursion + List

```java
import java.util.NoSuchElementException;
public class NestedIterator implements Iterator<Integer> {
    
    int curr;
    List<Integer> list;

    public NestedIterator(List<NestedInteger> nestedList) {
        curr = 0;
        list = new ArrayList<>();
        flattenList(nestedList);
    }
    
    // recursively unpacks a nest list in dfs
    // flatten all the elements to a list before the iterator begins
    private void flattenList(List<NestedInteger> nestedList){
        for (NestedInteger nestedInteger : nestedList){
            if (nestedInteger.isInteger()){
                list.add(nestedInteger.getInteger());
            } else {
                flattenList(nestedInteger.getList());
            }
        }
    }

    @Override
    public Integer next() {
        if (!hasNext()) throw new NoSuchElementException();
        return list.get(curr++);
    }

    @Override
    public boolean hasNext() {
        return curr < list.size();
    }
}
```

-   Let N be the total number of *integers* within the nested list, L be the total number of *lists* within the nested list, and D be the maximum nesting depth (maximum number of lists inside each other).

-   Time complexity:

    We'll analyze each of the methods separately.

    -   **Constructor:** $O(N + L)$

        The constructor is where all the time-consuming work is done.

        For each list within the nested list, there will be one call to `flattenList(...)`. The loop within `flattenList(...)` will then iterate n times, where n is the number of integers within that list. Across all calls to `flattenList(...)`, there will be a total of N loop iterations. Therefore, the time complexity is the number of lists plus the number of integers, giving us $O(N + L)$

        Notice that the maximum depth of the nesting does not impact the time complexity.

    -   **next():** $O(1)$

        Getting the next element requires incrementing `position` by 1 and accessing an element at a particular index of the `integers` list. Both of these are $O(1)$ operations.

    -   **hasNext():** $O(1)$.

        Checking whether or not there is a next element requires comparing the length of the `integers` list to the `position` variable. This is an $O(1)$ operation.

-   Space complexity: $O(N + D)$.

    The most obvious auxiliary space is the `integers` list. The length of this is $O(N)$

    The less obvious auxiliary space is the space used by the `flattenList(...)` function. Recall that recursive functions need to keep track of where they're up to by putting stack frames on the runtime stack. Therefore, we need to determine what the maximum number of stack frames there could be at a time is. Each time we encounter a nested list, we call `flattenList(...)` and a stack frame is added. Each time we finish processing a nested list, `flattenList(...)` returns and a stack frame is removed. Therefore, the maximum number of stack frames on the runtime stack is the maximum nesting depth, D.

    Because these two operations happen one-after-the-other, and either could be the largest, we add their time complexities together giving a final result of $O(N + D)$

#### Solution #2 Stack

```java
import java.util.NoSuchElementException;

public class NestedIterator implements Iterator<Integer> {

    // In Java, the Stack class is considered deprecated. Best practice is to use
    // a Deque instead. We'll use addFirst() for push, and removeFirst() for pop.
    private Deque<NestedInteger> stack;
    
    public NestedIterator(List<NestedInteger> nestedList) {
        // The constructor puts them on in the order we require. No need to reverse.
        stack = new ArrayDeque(nestedList);
    }
        
    
    @Override
    public Integer next() {
        // As per java specs, throw an exception if there's no elements left.
        if (!hasNext()) throw new NoSuchElementException();
        // hasNext ensures the stack top is now an integer. Pop and return
        // this integer.
        return stack.removeFirst().getInteger();
    }

    
    @Override
    public boolean hasNext() {
        // Check if there are integers left by getting one onto the top of stack.
        makeStackTopAnInteger();
        // If there are any integers remaining, one will be on the top of the stack,
        // and therefore the stack can't possibly be empty.
        return !stack.isEmpty();
    }


    private void makeStackTopAnInteger() {
        // While there are items remaining on the stack and the front of 
        // stack is a list (i.e. not integer), keep unpacking.
        while (!stack.isEmpty() && !stack.peekFirst().isInteger()) {
            // Put the NestedIntegers onto the stack in reverse order.
            List<NestedInteger> nestedList = stack.removeFirst().getList();
            for (int i = nestedList.size() - 1; i >= 0; i--) {
                stack.addFirst(nestedList.get(i));
            }
        }
    }
}
```

-   Time complexity.

    -   **Constructor:** $O(N + L)$

        The worst-case occurs when the initial input nestedList consists entirely of integers and empty lists (everything is in the top-level). In this case, every item is reversed and stored, giving a total time complexity of $O(N + L)$

    -   **makeStackTopAnInteger():** $O(\dfrac{L}{N})$or $O(1)$

        If the top of the stack is an integer, then this function does nothing; taking $O(1)$ time.

        Otherwise, it needs to process the stack until an integer is on top. The best way of analyzing the time complexity is to look at the total cost across all calls to `makeStackTopAnInteger()` and then divide by the number of calls made. Once the iterator is exhausted `makeStackTopAnInteger()` must have seen every integer at least once, costing $O(N)$ time. Additionally, it has seen every list (except the first) on the stack at least once also, so this costs $O(L)$ time. Adding these together, we get $O(N + L)$ time.

        The amortized time of a single `makeStackTopAnInteger` is the total cost, $O(N + L)$, divided by the number of times it's called. In order to get all integers, we need to have called it N times. This gives us an amortized time complexity of $\dfrac{O(N + L)}{N} = O(\dfrac{N}{N} + \dfrac{L}{N}) = O(\dfrac{L}{N})$

    -   **next():** $O(\dfrac{L}{N})$ or $O(1)$

        All of this method is $O(1)$, except for possibly the call to `makeStackTopAnInteger()`, giving us a time complexity the same as `makeStackTopAnInteger()`.

    -   **hasNext():** $O(\dfrac{L}{N})$ or $O(1)$

        All of these methods is $O(1)$, except for possibly the call to `makeStackTopAnInteger()`, giving us a time complexity the same as `makeStackTopAnInteger()`.

-   Space complexity: $O(N + L)$

    In the worst case, where the top list contains N integers, or L empty lists, it will cost $O(N + L)$ space. Other expensive cases occur when the nesting is very deep. However, it's useful to remember that $D ≤ L$ (because each layer of nesting requires another list), and so we don't need to take this into account.

