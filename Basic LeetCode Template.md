# LeetCode Template 一页通

## Binary Search

**Complexity**

*   Time complexity: $O(\log N)$ - 因为每次都从中间开始把数据split成一半一半，log的底数为2
*   Space complexity: $O(1)$ if not adding other structure
    *   通常使用constant space来存储mid以及需要return的结果


#### **Template 1**

```java
int binarySearch(int[] nums, int target){
  if(nums == null || nums.length == 0)
    return -1;

  int left = 0, right = nums.length - 1;
  while(left <= right){
    // Prevent (left + right) overflow
    int mid = left + (right - left) / 2;
    if(nums[mid] == target){ return mid; }
    else if(nums[mid] < target) { left = mid + 1; }
    else { right = mid - 1; }
  }

  // End Condition: left > right
  return -1;
}
```

#### **Template 2**

```java
int binarySearch(int[] nums, int target){
  if(nums == null || nums.length == 0)
    return -1;

  int left = 0, right = nums.length;
  while(left < right){
    // Prevent (left + right) overflow
    int mid = left + (right - left) / 2;
    if(nums[mid] == target){ return mid; }
    else if(nums[mid] < target) { left = mid + 1; }
    else { right = mid; }
  }

  // Post-processing:
  // End Condition: left == right
  if(left != nums.length && nums[left] == target) return left;
  return -1;
}
```

#### **Template 3**

```java
int binarySearch(int[] nums, int target) {
    if (nums == null || nums.length == 0)
        return -1;

    int left = 0, right = nums.length - 1;
    while (left + 1 < right){
        // Prevent (left + right) overflow
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            left = mid;
        } else {
            right = mid;
        }
    }

    // Post-processing:
    // End Condition: left + 1 == right
    if(nums[left] == target) return left;
    if(nums[right] == target) return right;
    return -1;
}
```

## Dynamic Programming

*   **Top-down**: Recursion + Memoization(usually hashmap)
*   **Bottom-up**: Create an array to cumulatively store values or flags

*   通常提问方式:
    *   Maximum/minimum problems
    *   How many ways
    *   The result depends on optimal sub-problems solutions
*   **Process**:
    *   分析State variables的数量决定了array的dimension: what decisions are there to make, what information is needed
    *   如何进行递归: The recurrence relation to transition between states
    *   Base cases

### Memoization(Top-down) + recursion

*   使用array来记录每个sub-problem的cumulative的结果，储存的array最好是given条件的array
    *   在given array上进行修改，可以保持$O(1)$的space complexity
*   该方法绑定recursion，且通常使用一个helper method来完成recursion
    *   Base case
    *   Recurrence relationship
*   Time complexity: $O(n)$ 
    *   因为我们使用了hashmap进行临时结果的存储，因此每次recursion的时候，对于已存储的结果是$O(1)$
    *   不过因为可能在决定action的时候，会从不同的可能性出发，因此这个$n$也可能是$2n$, $3n$，但总体converge to $n$
    *   值得注意的是，由于需要进行大量的recursion，尽管已经有hashmap来大大缩短时间，但与其他的dp模板相比较，总体运行时间并不会很快
*   Space complexity: $O(n)$
    *   存储hashmap所需要的空间

#### **Template 1 - HashMap**

```java
// step 1: 创建一个hashmap，用以记录对应的结果
private HashMap<String, Integer> memo = new HashMap<>();
// 公开given variables，因为helper method也需要用

public int mainFunct(int[][] costs){
    // 链接真正用来dp的helper method
    return Something;
}

// step2: 考虑具体有多少变量：
//      1. state variables - 有多少个状态(日期, holding, color..)，就有多少个dimension需要考虑
//      2. actions - 可能产生的动作，以股票题为例(买入，售出等), 以及该动作应何时产生

// step3: 创建一个dp的method，输入两个state用以进行recurrsion
public int dp(state1, state2){
    // sub-step 1: 当变量触底，base cases
    if (day == array.length){
        return 0;
    }
    // sub-step 2: 检查是否已经存储了对应的数据
    if (memo.containsKey(state1)){
        return memo.get(state1));
    }
    // sub-step 3: recurrence relationship 链接下一个index，以及找到sub-problem的最优
    currentstate = dp(state1 + 1, state2);
    // sub-step 4: 记录进入memo hashmap，返回该数值
    memo.put(getKey(state1), totalCost);
    return totalCost;
}
```

**Example**

```java
// 以 paint house 题目为例

// step 1: 创建一个hashmap，用以记录对应的结果
private HashMap<String, Integer> memo = new HashMap<>();
// 通常把given array先存起来，因为memoization通常需要另外再造方法
private int[][] costs;

public int mainFunct(int[][] costs){
    this.costs = costs;
    // 链接真正用来dp的helper method
    return Something // Math.min(Math.min(paintCost(0, 0), paintCost(0, 1)), paintCost(0, 2));
}

// step2: 考虑具体有多少变量：
//      1. state variables - 有多少个状态(日期, holding, color..)，就有多少个dimension需要考虑
//      2. actions - 可能产生的动作，以股票题为例(买入，售出等), 以及该动作应何时产生

// step3: 创建一个dp的method，输入两个state用以进行recurrsion
public int paintCost(int day, int color){
    // Memoization template: ***************************************************************
    // sub-step 1: 当变量触底，base cases
    if (day == costs.length){
        return 0;
    }
    // sub-step 2: 检查是否已经存储了对应的数据
    if (memo.containsKey(getKey(day, color))){
        return memo.get(getKey(day, color));
    }
    // sub-step 3: recurrence relationship 链接下一个index，以及找到sub-problem的最优
    // the cost at current day and color
    int totalCost = costs[day][color];
    if (color == 0){
        // red color
        totalCost += Math.min(paintCost(day + 1, 1), paintCost(day + 1, 2));
    }
    else if (color == 1){
        // blue color
        totalCost += Math.min(paintCost(day + 1, 0), paintCost(day + 1, 2));
    }
    else {
        // green color
        totalCost += Math.min(paintCost(day + 1, 0), paintCost(day + 1, 1));
    }
    // sub-step 4: 记录进入memo hashmap，返回该数值
    memo.put(getKey(day, color), totalCost);
    return totalCost;
}
```

#### Template 2 - 多元或单元Array

*   具体的思考过程与hashmap近似，但是使用了array来进行数据的存储
*   适用于需要考虑多个状态变量的同时，某些变量（如日期等）并不需要在每个节点进行单一的动作
    *   以股票为例，动作分别有售出和hold两种动作/购买和hold，但是hashmap通常只有一个动作
    *   当涉及到某个状态的记录而非单纯数值的记录时，array会比hashmap更加通用，不过array的dimension会更高
    *   以股票为例，一个状态变量即为hold/unhold，既此时是否持有股票
*   Time and space complexity : $O(m*n)$, 分别都是多元array的长度

```java
// step 1: 考虑具体有多少变量：
//      1. state variables - 有多少个状态(日期, holding, color..)，就有多少个dimension需要考虑
//      2. actions - 可能产生的动作，以股票题为例(买入，售出等), 以及该动作应何时产生

// step 2: 创建一个array，用以记录对应的结果
private int[][] memo;
// 公开given variables，因为helper method也需要用

public int mainFunct(int[][] costs){
    memo = new int[prices.length + 1][2]; // 创建对应的memo array，通常对于given变量长度 + 1, 存储结果
    // 链接真正用来dp的helper method
    return Something;
}

// step3: 创建一个dp的method，输入两个state用以进行recurrsion
public int dp(state1, state2){
    // sub-step 1: 当变量触底，base cases
    if (day == array.length){
        return 0;
    }
    // sub-step 2: 检查是否已经存储了对应的数据，但是检查的是array的内容
    if (memo[state1][state2] == 0){
    // sub-step 3: 分辨可能出现的动作，recurrence relationship 链接下一个index，以及找到sub-problem的最优
        int doNothing = dp(state1 + 1, state2); // 动作1 - 结果1
        int doSomething; // 动作2 - 结果2

        if (holding == 1){
            // doSomething, action 1
        }
        else {
            // doSomething, action 2
        }
     // sub-step 4: 对比两种动作的结果，记录进入memo array，返回该数值
        memo[state1][state2] = Math.max(doNothing, doSomething);
    }
    return memo[state1][state2];
}
```

**Example 以股票问题为例**

```java
// step 1: 考虑具体有多少变量：
//      1. state variables - 有多少个状态(日期, holding, color..)，就有多少个dimension需要考虑
//      2. actions - 可能产生的动作，以股票题为例(买入，售出等), 以及该动作应何时产生

// step 2: 创建一个memo array，用以记录对应的结果, dimension = # state variables
private int[] prices;
private int[][] memo;
private int fee;

public int mainFunct(int[][] costs){
    this.prices = prices;
    this.fee = fee;
    // 该题的考量:
    // two state variables: day, holding stock or not
    // two actions: buy, sell
    
    memo = new int[prices.length + 1][2]; // 创建对应的memo array，通常对于given变量长度 + 1
    return dp(0, 0);
}

// step3: 创建一个dp的method，输入两个state用以进行recurrsion
public int dp(int day, int holding){
    // sub-step 1: 当变量触底，base cases
    if (day == prices.length){
        return 0;
    }
    // sub-step 2: 检查是否已经存储了对应的数据，但是检查的是array的内容
    if (memo[day][holding] == 0){
    // sub-step3: 分辨可能出现的动作，recurrence relationship 链接下一个index，以及找到sub-problem的最优
        int doNothing = dp(day + 1, holding); // 动作1 - 结果1
        int doSomething; // 动作2 - 结果2

        // do something we have two actions
        // 1 - holding stock, 0 - does not hold
        if (holding == 1){
            // sell the stock?
            doSomething = prices[day] - fee + dp(day + 1, 0);
        }
        else {
            // buy a stock?
            doSomething = -prices[day] + dp(day + 1, 1);
        }
     // sub-step 4: 记录进入memo array，返回该数值
        memo[day][holding] = Math.max(doNothing, doSomething);
    }
    return memo[day][holding];
}
```

### Bottom-up + No Recursion

*   通常由top-down的方法转化，但是不需要进行recursion因此运行速度上快很多
*   通常需要进行for loop
*   视情况可以控制space complexity到$O(1)$的水平，是dp中较为好的方法，但是可能比较难想到

#### Template 1 - Changing States

*   使用一些state varibles来存储变量，在循环中不断更改value，通常需要两个或以上
*   通常使用`Math.min()`或者`Math.max()`来进行关系上的传递
*   通常时间complexity为$O(n)$, 空间的complexity为$O(1)$
*   **难点**： 如果寻找内部的recurrence relationship，这里可能会很难

```java
public int maxProfit(int[] prices){
    // step 1: set up states and initial values, usually 0, min, max
    int sold = Integer.MIN_VALUE, held = Integer.MIN_VALUE, reset = 0;
    
    // step 2: for loop
    for (int price : prices){
        int preSold = sold;
    // step 3: states recurrence relationship
        sold = held + price;
        held = Math.max(held, reset - price);
        reset = Math.max(reset, preSold);
    }
    // step 4: return and compare max and min
    return Math.max(sold, reset);
}
```

##### T 1.1 Kanede's Algorithm

*   Goal: find maximum subarray - find the contiguous subarray (containing at least one number) which has the largest sum

```java
public int maxSubArray(int[] nums) {
    // kadene's algorithm
    int current = 0, max = Integer.MIN_VALUE;
    for (int num : nums){
        // should we abandon the previous values?
        current = Math.max(num, current + num);
        max = Math.max(max, current);
    }
    return max;
}
```

#### Template 2 - Sum-up Array

*   与memoization的做法相似，但是不需要进行recursion，而是使用for-loop
*   通常时间complexity为$O(n)$, 空间的complexity为$O(1)$
*   通常使用`Math.min()`或者`Math.max()`来进行关系上的传递
*   在array的最后一行或列中寻找最大或最小
*   **难点**： 如果寻找内部的recurrence relationship

```java
public int minCost(int[][] costs) {
    // step 1: Use for loop 传递并递增信息，例如次数，cost，等
    for (int n = costs.length - 2; n >= 0; n--) {
        // step 2: 寻找array之中的递增联系以及关系，一直传递到最后一行
        // Total cost of painting the nth house red.
        costs[n][0] += Math.min(costs[n + 1][1], costs[n + 1][2]);
        // Total cost of painting the nth house green.
        costs[n][1] += Math.min(costs[n + 1][0], costs[n + 1][2]);
        // Total cost of painting the nth house blue.
        costs[n][2] += Math.min(costs[n + 1][0], costs[n + 1][1]);
    }
    if (costs.length == 0) return 0;   
    // step 3: 在最后一行寻找答案
    return Math.min(Math.min(costs[0][0], costs[0][1]), costs[0][2]);
}
```

## Backtracking

*   通常提问方式:

    *   **All** possible combinations/solutions/permutations，要详细的**所有**结果的情况

    *   Return in list，结果为一个list，list中每个结果都是一个list
*   通常需要另一个backtracking的helper method， helper method需要使用recursion

#### Template 1 - 通用模版

*   把所有给的variables public 作全局，包括需要return的结果（很可能是list，先做好return的空list）
*   在主方法中调用backtracking，并在其中创建一个空list作为单个的结果
*   backtracking：
    *   通常下是void method，调用参数index
        *   index在此处用于追踪我们在given array中的位置，记录是否达到了given array 的末端
        *   同时index也是下一个可能结果的先决条件
    *   写好base case for recursion：
        *   到达了target value
        *   到达了given array的末端
        *   注意需要重新建一个arraylist来结果当前结果list
    *   for loop
        *   从loop中进行操作链接到下一个可能的结果
        *   把本次结果加入到结果集中
        *   进入下一个可能的结果
        *   把本次结果从结果集中**删除**：达到backtracking的目的

```java
// step 1: 把所有给的variables public 作全局，创建res的list（通常list中list嵌套）
List<List<Integer>> res;
int[] candidates;

public List<List<Integer>> main(int[] candidates) {
    res = new ArrayList<>();
    this.candidates = candidates;
    // step 2: 思考需要把什么带入backtracking的方法（target，index等），并套入一个空结果list
    backtracking(0, new ArrayList<Integer>());
    // 直接return已经做好的result list
    return res;
}

// step 2: 思考需要把什么带入backtracking的方法（target，*index*等）
//         并套入一个*空结果list（指代当前结果）*
//		   通常是void method
public void backtracking(int start, List<Integer> singleComb){
// step 3: base case 刚好达成结果的条件，在此增加当前结果到结果集
    if (start == candidates.length){
        res.add(new ArrayList(singleComb));
        return;
    }
// step 4: **最重要**，通常需要for-loop来进入下一个可能的结果 *****************
//         从当前index出发，直到given array的末端，代表某个可能的路径
    for (int i = start; i < candidates.length; i++){
        // 加入到当前结果
        singleComb.add(candidates[i]);
        // 进入下一个可能的结果，注意此处 *********
        // * 可重复的结果则i
        // * 不可重复的记过则i + 1
        backtracking(i + 1, singleComb);
        // 移除出当前结果（实现backtracking）
        singleComb.remove(singleComb.size() - 1);
    }
}
```

以combination sum为例

```java
// step 1: 把所有给的variables public 作全局，创建res的list（通常list中list嵌套）
List<List<Integer>> res;
int[] candidates;
int target;

public List<List<Integer>> combinationSum(int[] candidates, int target) {
    res = new ArrayList<>();
    this.candidates = candidates;
    this.target = target;
    // step 2: 思考需要把什么带入backtracking的方法（target，index等），并套入一个空结果list
    backtracking(0, target, new ArrayList<Integer>());
    // 直接return已经做好的result list
    return res;
}

// step 2: 思考需要把什么带入backtracking的方法（target，*index*等）
//         并套入一个*空结果list（指代当前结果）*
//		   通常是void method
public void backtracking(int start, int target, List<Integer> singleComb){
// step 3: base case 刚好达成结果的条件，在此增加当前结果到结果集
    if (target == 0){
        res.add(new ArrayList(singleComb)); // *重要*：建一个new list来接结果
        return;
    }
    // base case: not meet the requirement
    if (target < 0 || start == candidates.length){
        return;
    }
// step 4: **最重要**，通常需要for-loop来进入下一个可能的结果 *****************
//         从当前index出发，直到given array的末端，代表某个可能的路径
    for (int i = start; i < candidates.length; i++){
        // 加入到当前结果
        singleComb.add(candidates[i]);
        // 进入下一个可能的结果
        backtracking(i, target - candidates[i], singleComb);
        // 移除出当前结果（实现backtracking）
        singleComb.remove(singleComb.size() - 1);
    }
}
```

#### Template 2 - Permutation(通用模版变形)

*   适用于可能的情况：
    *   permutation
    *   每个return的结果，并不按照原来given array中element的排序，需要乱序处理
*   本质上讲通用模版中的可能结果的元素增减，改变为元素的swap来换位
    *   需要将given array（如果有的话）转换成list，方便使用`Collections.swap()`，否则需要自己建立helper method来实现元素的交换

以permutation为例

```java
// step 1: 把所有给的variables public 作全局，创建res的list（通常list中list嵌套）
List<List<Integer>> res;
List<Integer> nums;

public List<List<Integer>> permute(int[] nums) {
    res = new ArrayList<>();
    this.nums = new ArrayList<>();
// step 2: array 不方便进行swap且不是list，现转换成list就不需要create new list for single result
    for (int num : nums){
        this.nums.add(num);
    }
// step 3: 思考需要把什么带入backtracking的方法（target，index等）
    backtracking(0);
    return res;
}
public void backtracking(int index){
// step 4: base case 刚好达成结果的条件，在此增加当前结果到结果集
    if (index == nums.size()){
        res.add(new ArrayList(nums));
        return;
    }
// step 4: **最重要**，通常需要for-loop来进入下一个可能的结果 *****************
//         从当前index出发，直到given array的末端，代表某个可能的路径，并进行swap
    for (int i = index; i < nums.size(); i++){
        Collections.swap(nums, i, index);
        //*****注意****************
        // 由于是swap并不按顺序排列结果，因此从index进行关联而不是i，保证swap到每个元素以及自己
        backtracking(index + 1);
        Collections.swap(nums, i, index);
    }
}
```

## UNION FIND

*   Union-Find is all about remembering the patterns.
*   每次使用这个class来进行cluster，对于每个cluster只需要找ancestor的数据（e.g rank，find）
*   Create a class called `UnionFind`
    *   Initialize a `group` array and a `rank` array. Sometimes you may also need to create a `size` array
    *   Create `find,` and `union` method
        *   `find`: Find the ancestor(centroid) of the cluster, 用以进行union时确定存在于某个cluster
        *   `union`: Union two groups. If true, we union them; if false, they are already in the same cluster
    *   `rank`: Speeding up the `union` process, basically, calculates the path number of the cluster tree.
        *   Always attach the cluster with a shorter path to the cluster with a longer path.
        *   使用方法为path compression
*   Time complexity: time complexity for find/union operation is $\alpha({N})$ (Here, $\alpha({N})$ is the inverse Ackermann function that grows so slowly. Suppose we have $N$ elements to go through, then the time compleXIty would be $O(N \alpha({N}))$
*   Space complexity: just using array so it would be $O(N)$

### Template 1 - 通用模版

```java
class UnionFind {
    private int[] group;
    private int[] rank;
    
    public UnionFind(int n){
        group = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++){
            group[i] = i; // initialization: each node is its own group
            rank[i] = 1; // each node has rank 1(path length: 1)
        }
    }
    
    public int find(int node){
        if (node != group[node]){
            group[node] = find(group[node]); // recursion find the ancestor
        }
        return group[node];
    }

    public boolean union(int i, int j){
        // find the ancestor
        int root1 = find(i);
        int root2 = find(j);
        
        if (root1 == root2){
            // already in same group
            return false;
        }
        // if they are not in the same group
        if (rank[root1] > rank[root2]){
            group[root2] = group[root1]; // attach shorter path cluster to the longer one
        }
        else if (rank[root2] > rank[root1]){
            group[root1] = group[root2];
        }
        else {
            group[root1] = group[root2];
            rank[root2]++; // same length, one of the cluster have to be ancestor, rank + 1
        }
        return true;
    }
}
```

*   **模版的使用**
    *   通常在主method中进行class的实例化与使用，例子如下

```java
// map to store the number and index, union-find to group the numbers
Map<Integer, Integer> numMap = new HashMap<>();
UnionFind uf = new UnionFind(nums.length);
for (int i = 0; i < nums.length; i++){
    int curr = nums[i];
    // in case duplicate
    if (numMap.containsKey(curr)) continue;
    // if map has consecutive elements for curr element
    if (numMap.containsKey(curr - 1)){
        uf.union(i, numMap.get(curr - 1));
    }
    if (numMap.containsKey(curr + 1)){
        uf.union(i, numMap.get(curr + 1));
    }
    numMap.put(curr, i);
}
```

*   `rank`不是必须的，可以分情况使用，如果不需要优化time complexity也可以不用(最好用上)
*   如果需要知道每个cluster的size，也可以使用size来替代rank(同样也是path compression)

### Template 2 - With Size

*   Path compression with size

```java
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

## DFS

### DFS - Line Graph

*   需要创建`adjList`，可以是list或者map，用以存储相关联的nodes的信息（**important**）

## BFS

*   Traverse a tree/graph：通常要使用queue来进行
    *   Traverse by layer
    *   while循环，queue不为空
    *   事先存储一个node（通常为root），然后在循环中pop（poll）
    *   进行操作，接着把node的左子树和右子树存储在queue或者stack中，循环往复直到所有node都traverse完成
*   **DFS-stack, BFS-queue**

*   Time Complexity: $O(V + E)$. Here, V represents the number of vertices, and E represents the number of edges. We need to check every vertex and traverse through every edge in the graph. The time complexity is the same as it was for the DFS approach.
*   Space Complexity: $O(V)$. Generally, we will check if a vertex has been visited before adding it to the queue, so the queue will use most $O(V)$ space. Keeping track of which vertices have been visited will also require $O(V)$ space.

## Two Pointer

### Linked List

*   Usually **1 fast pointer, 1 slow pointer**, and different speeds of progress(hare and tortoise)
    *   A fast pointer can move two steps each time, and a slow pointer move 1 step each time
    *   **Always examine if the node is null before you call the next field.**
        *   Getting the next node of a null node will cause the null-pointer error. For example, before we run `fast = fast.next.next`, we need to examine both `fast` and `fast.next` is not null.
    *   **Carefully define the end conditions of your loop.**

```java
// Initialize slow & fast pointers
ListNode slow = head;
ListNode fast = head;
/**
 * Change this condition to fit the specific problem.
 * Attention: remember to avoid null-pointer error
 **/
while (slow != null && fast != null && fast.next != null) {
    slow = slow.next;           // move slow pointer one step each time
    fast = fast.next.next;      // move fast pointer two steps each time
    if (slow == fast) {         // change this condition to fit specific problem
        return true;
    }
}
return false;   // change return value to fit specific problem
```

*   Or two pointers have the same speed but start from a different node (remove the nth node from the end)
*   **Complexity Analysis**
    *   If you only use pointers without any other extra space, the space complexity will be `O(1)`.
    *   However, it is more difficult to analyze the time complexity. In order to get the answer, we need to analyze `how many times we will run our loop` . In our previous finding cycle example, let's assume that we move the faster pointer 2 steps each time and move the slower pointer 1 step each time.
        *   If there is no cycle, the fast pointer takes `N/2 times` to reach the end of the linked list, where N is the length of the linked list.
        *   If there is a cycle, the fast pointer needs `M times` to catch up the slower pointer, where M is the length of the cycle in the list.
        *   Obviously, M <= N. So we will run the loop `up to N times`. And for each loop, we only need constant time. So, the time complexity of this algorithm is `O(N)` in total.

