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