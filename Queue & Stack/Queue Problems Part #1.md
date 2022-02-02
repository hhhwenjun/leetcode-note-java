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