# Array and String Problems Part #1

## Palindrome Number(Easy 9)

**Question**: Given an integer `x`, return `true` if `x` is palindrome integer.

An integer is a **palindrome** when it reads the same backward as forward.

-   For example, `121` is a palindrome while `123` is not.

**Example 1:**

```
Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left.
```

**Example 2:**

```
Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
```

**Example 3:**

```
Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
```

**Constraints:**

-   `-231 <= x <= 231 - 1`

### My Solution

```java
public boolean isPalindrome(int x){
    if (x < 0) return false;
    List<Integer> numList = new ArrayList<>();
    
    // convert it to an interger arraylist
    while(x != 0){
        int digit = x % 10;
        numList.add(digit);
        x = x / 10;
    }
    int low = 0, high = numList.size() - 1;
    while(low < high){
        if (numList.get(low) != numList.get(high)) return false;
        low++;
        high--;
    }
    return true;
}
```

### Standard Solution

#### Solution #1 Revert half of the number

*   Revert the number and compare it with the original number
*   For number `1221`, if we do `1221 % 10`, we get the last digit `1`, to get the second to the last digit, we need to remove the last digit from `1221`, we could do so by dividing it by 10, `1221 / 10 = 122`. 
*   Then we can get the last digit again by doing a modulus by 10, `122 % 10 = 2`, and if we multiply the last digit by 10 and add the second last digit, `1 * 10 + 2 = 12`, it gives us the reverted number we want. Continuing this process would give us the reverted number with more digits.
*   Since we divided the number by 10, and multiplied the reversed number by 10, when the original number is less than the reversed number, it means we've processed half of the number digits.

```java
public bool isPalindrome(int x){
    // Special cases:
    // As discussed above, when x < 0, x is not a palindrome
    // Also if the last digit of the number is 0, in order to be a palindrome,
    // the first digit of the number also needs to be 0. Only 0 satisfy the property
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;
    
    int revertedNumber = 0;
    while(x > revertedNumber){
        revertedNumber = revertedNumber * 10 + x % 10;
        x /= 10;
    }
    
    // When the length is an odd number, we can get rid of the middle digit by revertedNumber / 10
    return x == revertedNumber || x == revertedNumber / 10;
}
```

*   Time complexity : $O(\log_{10}(n))$. We divided the input by 10 for every iteration, so the time complexity is $O(\log_{10}(n))$.
*   Space complexity : $O(1)$.