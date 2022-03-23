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

#### Solution #1 Binary Search

*   (二分法) Comparing min and max to continueously re-scale the range
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
