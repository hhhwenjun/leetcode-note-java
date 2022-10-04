# Math Problems Part #1

## Koko Eating Bananas(Medium 875)

**Question**: Koko loves to eat bananas. There are `n` piles of bananas, the `ith` pile has `piles[i]` bananas. The guards have gone and will come back in `h` hours.

Koko can decide her bananas-per-hour eating speed of `k`. Each hour, she chooses some pile of bananas and eats `k` bananas from that pile. If the pile has less than `k` bananas, she eats all of them instead and will not eat any more bananas during this hour.

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.

Return *the minimum integer* `k` *such that she can eat all the bananas within* `h` *hours*.

**Example 1:**

```
Input: piles = [3,6,7,11], h = 8
Output: 4
```

**Example 2:**

```
Input: piles = [30,11,23,4,20], h = 5
Output: 30
```

**Example 3:**

```
Input: piles = [30,11,23,4,20], h = 6
Output: 23
```

**Constraints:**

-   `1 <= piles.length <= 104`
-   `piles.length <= h <= 109`
-   `1 <= piles[i] <= 109`

### My Solution

```java
public int minEatingSpeed(int[] piles, int h){
    int sum = 0, max = 0;
    for(int i = 0; i < piles.length; i++){
        sum += piles[i];
        if (piles[i] > max){
            max = piles[i];
        }
    }
    int min = sum / h;
    
    while(min < max) {
        int eatHour = 0;
        int target = (min + max) / 2;
        for (int pile : piles){
            eatHour += Math.ceil((double)pile / target);
        }
        if (eatHour > h){
            min = target + 1;
        } else {
            max = target;
        }
    }
    return min;
}
```

### Standard Solution

#### Solution #1 Brute Force

*   Start from 1. Each time add 1 to the speed. Also works quickly.

```java
public int minEatingSpeed(int[] piles, int h) {
    // Start at an eating speed of 1.
    int speed = 1;

    while (true) {
        // hourSpent stands for the total hour Koko spends with 
        // the given eating speed.
        int hourSpent = 0;

        // Iterate over the piles and calculate hourSpent.
        // We increase the hourSpent by ceil(pile / speed)
        for (int pile : piles) {
            hourSpent += Math.ceil((double) pile / speed);
            if (hourSpent > h) {
                break;
            }
        }

        // Check if Koko can finish all the piles within h hours,
        // If so, return speed. Otherwise, let speed increment by
        // 1 and repeat the previous iteration.
        if (hourSpent <= h) {
            return speed;
        } else {
            speed += 1;
        }            
    }
}
```

-   Time complexity: $O(nm)$
    -   For each eating speed, we iterate over piles and calculate the overall time, which takes $O(n)$ time.
    -   Before finding the first workable eating speed, we must try every smaller eating speed. Suppose in the worst-case scenario (when the answer is m), we have to try every eating speed from 1 to m, that is a total of m iterations over the array.
    -   To sum up, the overall time complexity is $O(nm)$
-   Space complexity: $O(1)$
    -   For each eating speed, we iterate over the array and calculate the total hours Koko spends, which costs constant space.
    -   Therefore, the overall space complexity is $O(1)$.

#### Solution #2 Binary Search

*   (二分法) Comparing min and max to continuously re-scale the range
*   set a reasonable upper and lower bound for binary search (to ensure that we do not miss any workable speed).
*   Let the lower bound be 1, the minimum possible eating speed since there is no speed slower than 1. The upper bound will be the maximum eating speed, that is the maximum number of bananas in a pile. 

```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        // Initalize the left and right boundaries 
        int left = 1, right = 1;
        for (int pile : piles) {
            right = Math.max(right, pile);
        }

        while (left < right) {
            // Get the middle index between left and right boundary indexes.
            // hourSpent stands for the total hour Koko spends.
            int middle = (left + right) / 2;
            int hourSpent = 0;

            // Iterate over the piles and calculate hourSpent.
            // We increase the hourSpent by ceil(pile / middle)
            for (int pile : piles) {
                hourSpent += Math.ceil((double) pile / middle);
            }

            // Check if middle is a workable speed, and cut the search space by half.
            if (hourSpent <= h) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }

        // Once the left and right boundaries coincide, we find the target value,
        // that is, the minimum workable eating speed.
        return right;
    }
}
```

*   Time complexity: $O(n \cdot \log m)$
*   Space complexity: $O(1)$

## Reverse Integer(Medium #7)

**Question**: Given a signed 32-bit integer `x`, return `x` *with its digits reversed*. If reversing `x` causes the value to go outside the signed 32-bit integer range `[-2^31, 2^31 - 1]`, then return `0`.

**Assume the environment does not allow you to store 64-bit integers (signed or unsigned).**

**Example 1:**

```
Input: x = 123
Output: 321
```

**Example 2:**

```
Input: x = -123
Output: -321
```

**Example 3:**

```
Input: x = 120
Output: 21
```

**Constraints:**

-   `-231 <= x <= 231 - 1`

#### My Solution

*   Similar to pop and push to reverse the order

```java
public int reverse(int x){
    if (x < 0){
        char sign = '-';
    }
    int val = Math.abs(x);
    int reVal = 0;
    while(val != 0){
        int digit = val % 10;
        val /= 10;
        reVal = reVal * 10 + digit;
    }
    if (sign){
        reVal *= -1;
    }
    return reVal;
}
```

*   Could not cover all the cases

#### Standard Solution

*   It is very important to learn the limit of the primitive data type
*   Always use MAX value devide 10, not the smaller one * 10 just in case overflow
*   Consider if add the digit would it be overflow

#### Solution #1 Pop and Push & Check before Overflow

```java
public int reverse(int x){
    int rev = 0;
    while(x != 0){
        int pop = x % 10;
        x /= 10;
        if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
        if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
        rev = rev * 10 + pop;
    }
    return rev;
}
```

*   Time Complexity: $O(\log(x))$. There are roughly $\log_{10}(x)$ digits in x*x*.
*   Space Complexity: $O(1)$.

#### Solution #2 Built-in Method

```java
public int reverse(int x) {
    String reversed = new StringBuilder().append(Math.abs(x)).reverse().toString();
    try {
        return (x < 0) ? Integer.parseInt(reversed) * -1 : Integer.parseInt(reversed);
    } catch (NumberFormatException e) {
        return 0;
    }
}
```

## Container With Most Water(Medium #11)

**Question**: You are given an integer array `height` of length `n`. There are `n` vertical lines drawn such that the two endpoints of the `ith` line are `(i, 0)` and `(i, height[i])`.

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return *the maximum amount of water a container can store*.

**Notice** that you may not slant the container.

**Example 1:**

<img src="https://s3-lc-upload.s3.amazonaws.com/uploads/2018/07/17/question_11.jpg" alt="img" style="zoom:50%;" />

```
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
```

**Example 2:**

```
Input: height = [1,1]
Output: 1
```

**Constraints:**

-   `n == height.length`
-   `2 <= n <= 105`
-   `0 <= height[i] <= 104`

### My Solution

```java
// a brute-force method, but exceed limited time
public int maxArea(int[] height) {
    int max = 0;
    int area = 0;
    for (int i = 0; i < height.length; i++){
        for (int j = i; j < height.length; j++){
            area = (j - i) * Math.min(height[i], height[j]);
            max = Math.max(max, area);
        }
    }
    return max;
}
```

-   Time complexity : $O(n^2)$. Calculating area for all $\dfrac{n(n-1)}{2}$ height pairs.
-   Space complexity : $O(1)$. Constant extra space is used.

### Standard Solution

#### Solution #1 Two Pointer Approach

*   We take two pointers, one at the beginning and one at the end of the array constituting the length of the lines.
*   Further, we maintain a variable $\text{maxarea}$ to store the maximum area obtained till now.
*   At every step, we find out the area formed between them, update $\text{maxarea}$ and move the pointer pointing to the shorter line towards the other end by one step.

```java
public int maxArea(int[] height) {
    int max = 0;
    int area = 0;
    int low = 0, high = height.length - 1;
    while (low < high){
        area = (high - low) * Math.min(height[low], height[high]);
        max = Math.max(max, area);
        if (height[low] < height[high]){
            low++;
        } else {
            high--;
        }
    }
    return max;
}
```

-   Time complexity : $O(n)$. Single-pass.
-   Space complexity : $O(1)$. Constant space is used.

## Broken Calculator(Medium #991)

**Question**: There is a broken calculator that has the integer `startValue` on its display initially. In one operation, you can:

-   multiply the number on display by `2`, or
-   subtract `1` from the number on display.

Given two integers `startValue` and `target`, return *the minimum number of operations needed to display* `target` *on the calculator*.

**Example 1:**

```
Input: startValue = 2, target = 3
Output: 2
Explanation: Use double operation and then decrement operation {2 -> 4 -> 3}.
```

**Example 2:**

```
Input: startValue = 5, target = 8
Output: 2
Explanation: Use decrement and then double {5 -> 4 -> 8}.
```

**Example 3:**

```
Input: startValue = 3, target = 10
Output: 3
Explanation: Use double, decrement and double {3 -> 6 -> 5 -> 10}.
```

**Constraints:**

-   `1 <= x, y <= 109`

### My Solution

```java
public int brokenCalc(int startValue, int target){
    if (target <= startValue){
        return startValue - target;
    }
    int operation = 0;
    int intermediate = 0;
    while(startValue < target){
        if(target % 2 == 1){
            intermediate = target + 1;
            target = intermediate / 2;
        }
        else {
            target = target / 2;
        }
        operation++;
    }
    if (startValue != target){
        operation += brokenCalc(startValue, target);
    }
    return operation;
}
```

*   Faster than 100%
*   But with stack, the space complexity would be larger than others

### Standard Solution

#### Solution #1 Work Backwards

*   Instead of multiplying by 2 or subtracting 1 from `startValue`, we could divide by 2 (when `target` is even) or add 1 to `target`.

*   The algorithm is similar but shorten

```java
public int brokenCalc(int startValue, int target) {
    int ans = 0;
    while (target > startValue) {
        ans++;
        if (target % 2 == 1)
            target++;
        else
            target /= 2;
    }
    return ans + startValue - target;
}
```

-   Time Complexity: $O(\log target)$
-   Space Complexity: $O(1)$

## Boats to Save People(Medium #881)

**Question**: You are given an array `people` where `people[i]` is the weight of the `ith` person, and an **infinite number of boats** where each boat can carry a maximum weight of `limit`. Each boat carries at most two people at the same time, provided the sum of the weight of those people is at most `limit`.

Return *the minimum number of boats to carry every given person*.

**Example 1:**

```
Input: people = [1,2], limit = 3
Output: 1
Explanation: 1 boat (1, 2)
```

**Example 2:**

```
Input: people = [3,2,2,1], limit = 3
Output: 3
Explanation: 3 boats (1, 2), (2) and (3)
```

**Example 3:**

```
Input: people = [3,5,3,4], limit = 5
Output: 4
Explanation: 4 boats (3), (3), (4), (5)
```

**Constraints:**

-   `1 <= people.length <= 5 * 104`
-   `1 <= people[i] <= limit <= 3 * 104`

### My Solution

*   This solution works but way too slow

```java
// hash table solution
public int numRescueBoats(int[] people, int limit) {
    Map<Integer, Integer> peopleMap = new HashMap<>();
    for(int num : people){
        peopleMap.put(num, peopleMap.getOrDefault(num, 0) + 1);
    }
    int boat = 0;
    int rest = 0;
    int counter = 0;
    for(int weight : people){
        if (!peopleMap.containsKey(weight)){
            continue;
        }
        else {
            peopleMap.put(weight, peopleMap.get(weight) - 1);
            if (peopleMap.get(weight) == 0){
                peopleMap.remove(weight);
            }
        }
        rest = limit - weight;
        while(rest > 0){
            if (peopleMap.containsKey(rest)){
                peopleMap.put(rest, peopleMap.get(rest) - 1);
                if (peopleMap.get(rest) == 0){
                    peopleMap.remove(rest);
                }
                break;
            }
            else {
                rest--;
            }
        }
        boat++;
    }
    return boat; 
}
```

### Standard Solution

#### Solution #1 Greedy (Two Pointer)

*   Sort the array and start from start and end

```java
public int numRescueBoats(int[] people, int limit){
    Arrays.sort(people);
    int i = 0, j = people.length - 1;
    int ans = 0;
    // each loop we have a boat
    while(i <= j){
        ans++;
        if (people[i] + people[j] <= limit){
            i++;
        }
        j--;
    }
    return ans;
}
```

-   Time Complexity: $O(N \log N)$, where N is the length of `people`.
-   Space Complexity: $O(N)$.

## Power of Two (Easy #231)

**Question**: Given an integer `n`, return *`true` if it is a power of two. Otherwise, return `false`*.

An integer `n` is a power of two, if there exists an integer `x` such that `n == 2x`.

**Example 1:**

```
Input: n = 1
Output: true
Explanation: 20 = 1
```

**Example 2:**

```
Input: n = 16
Output: true
Explanation: 24 = 16
```

**Example 3:**

```
Input: n = 3
Output: false
```

**Constraints:**

-   `-231 <= n <= 231 - 1`

### My Solution

```java
public boolean isPowerOfTwo(int n) {
    if (n == 1){
        return true;
    }
    if (n <= 0){
        return false;
    }
    return n % 2 == 0 && isPowerOfTwo(n/2);
}
```

### Standard Solution

#### Solution #1 Iteration

*   Same idea as my solution, but in an iterative way

```java
public boolean isPowerOfTwo(int n){
    if (n == 0) return false;
    while (n % 2 == 0) n /= 2;
    return n == 1;
}
```

*   Complexity in $O(\log N)$

#### Solution #2 Bit-wise Solution

```java
  public boolean isPowerOfTwo(int n) {
    if (n == 0) return false;
    long x = (long) n;
    return (x & (x - 1)) == 0;
  }
```

-   Time complexity: $\mathcal{O}(1)$
-   Space complexity : $\mathcal{O}(1)$

## Number of Days Between Two Dates (Easy #1360)

**Question**: Write a program to count the number of days between two dates.

The two dates are given as strings, their format is `YYYY-MM-DD` as shown in the examples.

**Example 1:**

```
Input: date1 = "2019-06-29", date2 = "2019-06-30"
Output: 1
```

**Example 2:**

```
Input: date1 = "2020-01-15", date2 = "2019-12-31"
Output: 15
```

**Constraints:**

-   The given dates are valid dates between the years `1971` and `2100`.

### Standard Solution

#### Solution #1 Subtract days

*   Count the number of days starting from 1971, subtract the days between two dates (set 1971 as the baseline for comparison)
*   Summarize the days difference for month, and calculate the leap year for Feb.

```java
// count the day starting from 1971, then substract the days
private int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

public int daysBetweenDates(String date1, String date2) {
    return Math.abs(countDays(date1) - countDays(date2));      
}

private int countDays(String date){
    String[] dateStr = date.split("-");
    int year = Integer.parseInt(dateStr[0]), month = Integer.parseInt(dateStr[1]);
    int day = Integer.parseInt(dateStr[2]);
    int count = day;
    for (int i = 1971; i < year; i++) count += (isLeapYear(i)) ? 366 : 365;
    for (int i = 0; i < month - 1; i++) count += months[i];
    if (month > 2 && isLeapYear(year)) count += 1;
    return count;
}

private boolean isLeapYear(Integer year) {
    return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
}
```

*   Time and space complexity should be both $O(1)$

## Plus One (Easy #66)

**Question**: You are given a **large integer** represented as an integer array `digits`, where each `digits[i]` is the `ith` digit of the integer. The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading `0`'s.

Increment the large integer by one and return *the resulting array of digits*.

**Example 1:**

```
Input: digits = [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Incrementing by one gives 123 + 1 = 124.
Thus, the result should be [1,2,4].
```

**Example 2:**

```
Input: digits = [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
Incrementing by one gives 4321 + 1 = 4322.
Thus, the result should be [4,3,2,2].
```

**Example 3:**

```
Input: digits = [9]
Output: [1,0]
Explanation: The array represents the integer 9.
Incrementing by one gives 9 + 1 = 10.
Thus, the result should be [1,0]. 
```

**Constraints:**

-   `1 <= digits.length <= 100`
-   `0 <= digits[i] <= 9`
-   `digits` does not contain any leading `0`'s.

### My Solution

```java
public int[] plusOne(int[] digits) {
    // all you need to check is the last digit see if it is 9
    int length = digits.length;
    if (digits[length - 1] != 9){
        digits[length - 1]++;
        return digits;
    }

    // if is 9, need to go through other digits
    // +1 then carry 1 over to previous digits
    int loc = length - 1;
    while(loc >= 0 && digits[loc] == 9){
        digits[loc] = 0;
        loc--;
    }
    if (loc >= 0) {
        digits[loc]++;
        return digits;
    }

    // create a new array
    int[] res = new int[length + 1];
    res[0] = 1;
    return res;
}
```

### Standard Solution

#### Solution #1 Schoolbook Addition with Carry

```java
public int[] plusOne(int[] digits) {
    int n = digits.length;

    // move along the input array starting from the end
    for (int idx = n - 1; idx >= 0; --idx) {
      // set all the nines at the end of array to zeros
      if (digits[idx] == 9) {
        digits[idx] = 0;
      }
      // here we have the rightmost not-nine
      else {
        // increase this rightmost not-nine by 1
        digits[idx]++;
        // and the job is done
        return digits;
      }
    }
    // we're here because all the digits are nines
    digits = new int[n + 1];
    digits[0] = 1;
    return digits;
}
```

*   Simplified version

```java
public int[] plusOne(int[] digits) {
    int i = digits.length - 1;

    while(i >= 0 && digits[i] == 9) digits[i--] = 0;
    if (i < 0){
        digits = new int[digits.length + 1];
        i = 0;
    }
    digits[i]++;
    return digits;
}
```

-   Time complexity: $\mathcal{O}(N)$ since it's not more than one pass along the input list.
-   Space complexity: $\mathcal{O}(N)$
    -   Although we perform the operation **in-place** (*i.e.* on the input list itself), in the worst scenario, we would need to allocate an intermediate space to hold the result, which contains the N+1 elements. Hence the overall space complexity of the algorithm is $\mathcal{O}(N)$

## Plus One Linked List(Medium #369)

**Question**: Given a non-negative integer represented as a linked list of digits, *plus one to the integer*.

The digits are stored such that the most significant digit is at the `head` of the list.

**Example 1:**

```
Input: head = [1,2,3]
Output: [1,2,4]
```

**Example 2:**

```
Input: head = [0]
Output: [1]
```

**Constraints:**

-   The number of nodes in the linked list is in the range `[1, 100]`.
-   `0 <= Node.val <= 9`
-   The number represented by the linked list does not contain leading zeros except for the zero itself. 

### My Solution

```java
// 1. reverse the list 
// 2. add 1 and bring over carry
// 3. reverse the lsit
public ListNode plusOne(ListNode head) {
    head = reverse(head);
    ListNode curr = head;
    int carry = 1;
    if (curr.val + carry >= 10){
        while(curr != null && curr.val + carry >= 10){
            int value = curr.val + carry;
            curr.val = 0;
            curr = curr.next;
        }
        if (curr == null) {
            curr = head;
            while(curr.next != null) curr = curr.next;
            ListNode last = new ListNode(1);
            curr.next = last;
        }
        else {
            curr.val += 1;
        }
    }
    else {
        curr.val += 1;
    }
    head = reverse(head);
    return head;
}

public ListNode reverse(ListNode head){
    if (head == null || head.next == null) return head;
    ListNode prev = reverse(head.next);
    head.next.next = head;
    head.next = null;
    return prev;
}
```

### Standard Solution

#### Solution #1 Sentinel Head + Textbook Addition

-   Initialize sentinel node as `ListNode(0)` and set it to be the new head: `sentinel.next = head`.
-   Find the rightmost digit not equal to nine.
-   Increase that digit by one.
-   Set all the following nines to zero.
-   Return sentinel node if it was set to 1, and head `sentinel.next` otherwise.

```java
public ListNode plusOne(ListNode head){
    // sentinel head
    ListNode sentinel = new ListNode(0);
    sentinel.next = head;
    ListNode notNine = sentinel;
    
    // find the rightmost not-nine digit
    while (head != null){
        if (head.val != 9){
            notNine = head;
        }
        head = head.next;
    }
    
    // increase this rightmost not-nine digit by 1
    notNine.val++;
    notNine = notNine.next;
    
    // set all the following nines to zeros
    while (notNine != null){
        notNine.val = 0;
        notNine = notNine.next;
    }
    return sentinel.val != 0 ? sentinel : sentinel.next;
}
```

-   Time complexity: $\mathcal{O}(N)$ since it's not more that two passes along the input list.
-   Space complexity: $\mathcal{O}(1)$

## Spiral Matrix (Medium #54)

**Question**: Given an `m x n` `matrix`, return *all elements of the* `matrix` *in spiral order*.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/13/spiral1.jpg)

```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/11/13/spiral.jpg)

```
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
```

**Constraints:**

-   `m == matrix.length`
-   `n == matrix[i].length`
-   `1 <= m, n <= 10`
-   `-100 <= matrix[i][j] <= 100`

### My Solution

*   Pretty good solution with a fast speed and low space requirement

```java
public List<Integer> spiralOrder(int[][] matrix) {
    // if exceed boundary, change the direction, then modify boundary
    // count the number cells as stopping criteria
    int cell = 0;
    int i = 0, j = 0;
    int upboundary = 0, downboundary = matrix.length - 1;
    int rightboundary = matrix[0].length - 1, leftboundary = 0;
    int maxCell = matrix.length * matrix[0].length;
    List<Integer> res = new ArrayList<>();
    while(cell < maxCell){
        // going right
        while(j <= rightboundary && cell < maxCell){
            res.add(matrix[i][j]);
            j++; cell++;
        }
        j--;
        i++;
        upboundary++;
        // going down
        while(i <= downboundary&& cell < maxCell){
            res.add(matrix[i][j]);
            i++; cell++;
        }
        i--;
        j--;
        rightboundary--;
        // going left
        while(j >= leftboundary&& cell < maxCell){
            res.add(matrix[i][j]);
            j--; cell++;
        }
        j++;
        i--;
        downboundary--;
        // going up
        while(i >= upboundary&& cell < maxCell){
            res.add(matrix[i][j]);
            i--; cell++;
        }
        i++;
        j++;
        leftboundary++;
    }
    return res;
}
```

### Standard Solution

*   Consider 4 directions of the pointer

```
Given that we are at (row, col), where the row is the row index, and col is the column index.

move right: (row, col + 1)
move downwards: (row + 1, col)
move left: (row, col - 1)
move upwards: (row - 1, col)
```

#### Solution #1 Set up boundaries

*   Similar idea but the code is neater

```java
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<>();
    int rows = matrix.length;
    int columns = matrix[0].length;
    int up = 0;
    int left = 0;
    int right = columns - 1;
    int down = rows - 1;

    while (result.size() < rows * columns) {
        // Traverse from left to right.
        for (int col = left; col <= right; col++) {
            result.add(matrix[up][col]);
        }
        // Traverse downwards.
        for (int row = up + 1; row <= down; row++) {
            result.add(matrix[row][right]);
        }
        // Make sure we are now on a different row.
        if (up != down) {
            // Traverse from right to left.
            for (int col = right - 1; col >= left; col--) {
                result.add(matrix[down][col]);
            }
        }
        // Make sure we are now on a different column.
        if (left != right) {
            // Traverse upwards.
            for (int row = down - 1; row > up; row--) {
                result.add(matrix[row][left]);
            }
        }
        left++;
        right--;
        up++;
        down--;
    }

    return result;
}
```

-   Time complexity: $O(M \cdot N)$. This is because we visit each element once.
-   Space complexity: $O(1)$. This is because we don't use other data structures. Remember that we don't include the output array in the space complexity.
