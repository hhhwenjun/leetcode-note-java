# Two Pointers Problems Part #1

## Maximize Distance to Closest Person(Medium #849)

**Question**: You are given an array representing a row of `seats` where `seats[i] = 1` represents a person sitting in the `ith` seat, and `seats[i] = 0` represents that the `ith` seat is empty **(0-indexed)**.

There is at least one empty seat, and at least one person sitting.

Alex wants to sit in the seat such that the distance between him and the closest person to him is maximized. 

Return *that maximum distance to the closest person*.

 **Example 1:**

<img src="https://assets.leetcode.com/uploads/2020/09/10/distance.jpg" alt="img" style="zoom:50%;" />

```
Input: seats = [1,0,0,0,1,0,1]
Output: 2
Explanation: 
If Alex sits in the second open seat (i.e. seats[2]), then the closest person has distance 2.
If Alex sits in any other open seat, the closest person has distance 1.
Thus, the maximum distance to the closest person is 2.
```

**Example 2:**

```
Input: seats = [1,0,0,0]
Output: 3
Explanation: 
If Alex sits in the last seat (i.e. seats[3]), the closest person is 3 seats away.
This is the maximum distance possible, so the answer is 3.
```

**Example 3:**

```
Input: seats = [0,1]
Output: 1
```

 **Constraints:**

-   `2 <= seats.length <= 2 * 104`
-   `seats[i]` is `0` or `1`.
-   At least one seat is **empty**.
-   At least one seat is **occupied**.

### My Solution

```java
public int maxDistToClosest(int[] seats) {
    int maxDist = 0, low, high;

    for (low = 0, high = 0; high < seats.length; ++high){
        if (seats[high] == 1){
            int totalDist = high - low;
            if (seats[low] == 1){
                maxDist = Math.max((int)Math.ceil(totalDist/2), maxDist);
            }
            else {
                maxDist = Math.max(totalDist, maxDist);
            }
            low = high;
        }
    }
    if (low == seats.length - 1) return Math.max(maxDist, high - low);
    return Math.max(maxDist, high - low - 1);
}
```

### Standard Solution

#### Solution #1 Next Array

*   Let `left[i]` be the distance from seat `i` to the closest person sitting to the left of `i`. Similarly, let `right[i]` be the distance to the closest person sitting to the right of `i`. This is motivated by the idea that the closest person in seat `i` sits a distance `min(left[i], right[i])` away.

*   **Algorithm**

    To construct `left[i]`, notice it is either `left[i-1] + 1` if the seat is empty, or `0` if it is full. `right[i]` is constructed in a similar way.

```java
public int maxDistToClosest(int[] seats){
    int N = seats.length;
    int[] left = new int[N], right = new int[N];
    Arrays.fill(left, N);
    Arrays.fill(right, N);
    
    for (int i = 0; i < N; i++){
        if (seats[i] == 1) left[i] = 0; //distance to the cloest one would be 0
        else if (i > 0) left[i] = left[i - 1] + 1; //add 1 distance comparing to the previous one
    }
    for (int i = N - 1; i >= 0; i--){
        if (seats[i] == 1) right[i] = 0; //distance to the cloest one would be 0
        else if (i < N - 1) right[i] = right[i + 1] + 1; //add 1 distance comparing to the previous one
    }
    
    int ans = 0;
    for (int i = 0; i < N; i++){
        if (seats[i] == 0){
            ans = Math.max(ans, Math.min(left[i], right[i]));
        }
    }
    return ans;
}
```

*   Time complexity: $O(N)$ where N is the length of seats
*   Space complexity: $O(N)$ where the space used by left and right

#### Solution #2 Two Pointer

*   **Algorithm**:
    *   Keep track of `prev`, the filled seat at or to the left of `i`, and `future`, the filled seat at or to the right of `i`.
    *   Then at seat `i`, the closest person is `min(i - prev, future - i)`, with one exception. `i - prev` should be considered infinite if there is no person to the left of seat `i`, and similarly `future - i` is infinite if there is no one to the right of seat `i`.

```java
public int maxDistToClosest(int[] seats){
    int N = seats.length;
    int prev = -1, future = 0;
    int ans = 0;
    
    for (int i = 0; i < N; i++){
        if (seats[i] == 1){
            prev = i;
        } else {
            while (future < N && seats[future] == 0 || future < i){
                future++;
            }
            int left = prev == -1 ? N : i - prev;
            int right = future == N ? N : future - i;
            ans = Math.max(ans, Math.min(left, right));
        }
    }
    return ans;
}
```

*   Time Complexity: $O(N)$, where $N$ is the length of `seats`.
*   Space Complexity: $O(1)$

#### Solution #3 Group by Zero

*   My solution is a combination of solution 2 and 3
*   In a group of `K` adjacent empty seats between two people, the answer is `(K+1) / 2`.
*   For groups of empty seats between the edge of the row and one other person, the answer is `K`, and we should take into account those answers too.

```java
public int maxDistToClosest(int[] seats){
    int N = seats.length;
    int K = 0;//current longest group of empty seats
    int ans = 0;
    
    for (int i = 0; i < N; i++){
        if (seats[i] == 1){
            K = 0;
        } else {
            K++;
            ans = Math.max(ans, (K + 1) / 2);
        }
    }
    
    for (int i = 0; i < N; i++){// in case starts from 0
        if (seats[i] == 1){
            ans = Math.max(ans, i);
            break;
        }
    }
    
    for (int i = N - 1; i >= 0; i--){// in case ends with 0
        if (seats[i] == 1){
            ans = Math.max(ans, N - 1 - i);
            break;
        }
    }
    return ans;
}
```

*   Time Complexity: $O(N)$, where N is the length of `seats`.
*   Space Complexity: $O(1)$.

## 3Sum (Medium #15)

**Question**: Given an integer array nums, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must not contain duplicate triplets.

**Example 1:**

```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation: 
nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
The distinct triplets are [-1,0,1] and [-1,-1,2].
Notice that the order of the output and the order of the triplets does not matter.
```

**Example 2:**

```
Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet does not sum up to 0.
```

**Example 3:**

```
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only possible triplet sums up to 0.
```

**Constraints:**

-   `3 <= nums.length <= 3000`
-   `-105 <= nums[i] <= 105`

### My Solution

```java
// two pointers
// 1. sum to 0: location < 0
// 2. low and high pointer
List<List<Integer>> res;
int[] nums;

public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    res = new ArrayList<>();
    this.nums = nums;
    for (int i = 0; i < nums.length && nums[i] <= 0; i++){
        // in case duplicate situations
        if (i == 0 || nums[i - 1] != nums[i]){
            findSolution(i);
        }
    }
    return res;
}

public void findSolution(int loc){

    int low = loc + 1, high = nums.length - 1;
    while(low < high){
        int sum = nums[low] + nums[high] + nums[loc];
        if (sum == 0){
            res.add(Arrays.asList(nums[loc], nums[low++], nums[high--]));
            while(low < high && nums[low] == nums[low - 1]){
                low++;
            }
        }
        else if (sum < 0){
            low++;
        }
        else {
            high--;
        }
    }
}
```

### Standard Solution

#### Solution #1 Two pointer

*   Same as my solution

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < nums.length && nums[i] <= 0; ++i)
        // skip the duplicate number
        if (i == 0 || nums[i - 1] != nums[i]) {
            twoSumII(nums, i, res);
        }
    return res;
}
void twoSumII(int[] nums, int i, List<List<Integer>> res) {
    int lo = i + 1, hi = nums.length - 1;
    while (lo < hi) {
        int sum = nums[i] + nums[lo] + nums[hi];
        if (sum < 0) {
            ++lo;
        } else if (sum > 0) {
            --hi;
        } else {
            // continue to move low and high pointer to next possible solution
            res.add(Arrays.asList(nums[i], nums[lo++], nums[hi--]));
            // skip the duplicate number
            while (lo < hi && nums[lo] == nums[lo - 1])
                ++lo;
        }
    }
}
```

-   Time Complexity: $\mathcal{O}(n^2)$. `twoSumII` is $\mathcal{O}(n)$, and we call it n times.

    Sorting the array takes $\mathcal{O}(n\log{n})$, so overall complexity is $\mathcal{O}(n\log{n} + n^2)$. This is asymptotically equivalent to $\mathcal{O}(n^2)$

-   Space Complexity: from $\mathcal{O}(\log{n})$ to $\mathcal{O}(n)$, depending on the implementation of the sorting algorithm. For the purpose of complexity analysis, we ignore the memory required for the output.

#### Solution #2 HashSet

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < nums.length && nums[i] <= 0; ++i)
        if (i == 0 || nums[i - 1] != nums[i]) {
            twoSum(nums, i, res);
        }
    return res;
}
void twoSum(int[] nums, int i, List<List<Integer>> res) {
    var seen = new HashSet<Integer>();
    for (int j = i + 1; j < nums.length; ++j) {
        int complement = -nums[i] - nums[j];
        if (seen.contains(complement)) {
            res.add(Arrays.asList(nums[i], nums[j], complement));
            while (j + 1 < nums.length && nums[j] == nums[j + 1])
                ++j;
        }
        seen.add(nums[j]);
    }
}
```

-   Time Complexity: $\mathcal{O}(n^2)$. `twoSum` is $\mathcal{O}(n)$, and we call it n times.

    Sorting the array takes $\mathcal{O}(n\log{n})$, so overall complexity is $\mathcal{O}(n\log{n} + n^2)$. This is asymptotically equivalent to $\mathcal{O}(n^2)$

-   Space Complexity: $\mathcal{O}(n)$ for the hashset.

## Trapping Rain Water (Hard #42)

**Question**: Given `n` non-negative integers representing an elevation map where the width of each bar is `1`, compute how much water it can trap after raining.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/10/22/rainwatertrap.png)

```
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
```

**Example 2:**

```
Input: height = [4,2,0,3,2,5]
Output: 9
```

**Constraints:**

-   `n == height.length`
-   `1 <= n <= 2 * 104`
-   `0 <= height[i] <= 105`

### Standard Solution

#### Solution #1 Two Pointers

*   Use two pointers: left pointer and the right pointer.
*   Track the left maximum and right maximum.
*   Always keep the larger pointer, and move the lower pointer, in this way, we can calcuate the trapping rain water by `max - curr`

```java
public int trap(int[] height) {
    // two pointers： low, high
    int left = 0, right = height.length - 1;
    int maxLeft = 0, maxRight = 0;
    int water = 0;

    while(left < right){
        // current right pointer is max right
        // 1. compare two pointer, always keep the high one
        if (height[left] < height[right]){
            if (height[left] >= maxLeft){
                maxLeft = height[left];
            } 
            else water += maxLeft - height[left];
            // 2. move the lower pointer, record left and right max
            left++;
        }
        else {
             // 3. when move the lower one, record max - low value
            if (height[right] >= maxRight){
                maxRight = height[right];
            }
            else water += maxRight - height[right];
            right--;
        }
    }
    return water;
}
```

-   Time complexity: $O(n)$. Single iteration of $O(n)$.
-   Space complexity: $O(1)$ extra space. Only constant space required for $\text{left}$, $\text{right}$, $\text{left\_max}$ and $\text{right\_max}$.