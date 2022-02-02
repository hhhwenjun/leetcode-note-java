# Stack Problems Part #1

## Evaluate Reverse Polish Notation(Medium #150)

**Question**: Evaluate the value of an arithmetic expression in [Reverse Polish Notation](http://en.wikipedia.org/wiki/Reverse_Polish_notation).

Valid operators are `+`, `-`, `*`, and `/`. Each operand may be an integer or another expression.

**Note** that division between two integers should truncate toward zero.

It is guaranteed that the given RPN expression is always valid. That means the expression would always evaluate to a result, and there will not be any division by zero operation.

**Example 1:**

```
Input: tokens = ["2","1","+","3","*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
```

**Example 2:**

```
Input: tokens = ["4","13","5","/","+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6
```

**Example 3:**

```
Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
Output: 22
Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 17 + 5
= 22
```

**Constraints:**

-   `1 <= tokens.length <= 104`
-   `tokens[i]` is either an operator: `"+"`, `"-"`, `"*"`, or `"/"`, or an integer in the range `[-200, 200]`.

### My Solution

```java
public int evalRPN(String[] tokens){
    public int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        String num1, num2;
        int result;
        for (String token : tokens){
            switch (token){
                case "+":
                    num1 = stack.pop();
                    num2 = stack.pop();
                    result = Integer.valueOf(num1) + Integer.valueOf(num2);
                    stack.push(String.valueOf(result));
                    break;
                case "-":
                    num1 = stack.pop();
                    num2 = stack.pop();
                    result = Integer.valueOf(num2) - Integer.valueOf(num1);
                    stack.push(String.valueOf(result));
                    break;
                case "*":
                    num1 = stack.pop();
                    num2 = stack.pop();
                    result = Integer.valueOf(num2) * Integer.valueOf(num1);
                    stack.push(String.valueOf(result));
                    break;
                case "/":
                    num1 = stack.pop();
                    num2 = stack.pop();
                    result = Integer.valueOf(num2) / Integer.valueOf(num1);
                    stack.push(String.valueOf(result));
                    break;
                default:
                    stack.push(token);
            }
        }
        String eval = stack.pop();
        return Integer.valueOf(eval);
    }
}
```

### Standard Solution

#### Solution #1 Evaluate with Stack

*   Similar to my solution
*   Using lambda functions to elegantly handle the 4 operations

```java
class Solution {
    private static final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = new HashMap<>();
    // Ensure this only gets done once for ALL test cases.
    static {
        OPERATIONS.put("+", (a, b) -> a + b);
        OPERATIONS.put("-", (a, b) -> a - b);
        OPERATIONS.put("*", (a, b) -> a * b);
        OPERATIONS.put("/", (a, b) -> a / b);
    }
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {          
            if (!OPERATIONS.containsKey(token)) {
                stack.push(Integer.valueOf(token));
                continue;
            }         
            int number2 = stack.pop();
            int number1 = stack.pop();
            BiFunction<Integer, Integer, Integer> operation;
            operation = OPERATIONS.get(token);
            int result = operation.apply(number1, number2);
            stack.push(result);
        }
        return stack.pop();
    }
}
```

*   Time Complexity : $O(n)$.

    We do a linear search to put all numbers on the stack, and process all operators. Processing an operator requires removing 2 numbers off the stack and replacing them with a single number, which is an $O(1)$ operation. Therefore, the total cost is proportional to the length of the input array. Unlike before, we're no longer doing expensive deletes from the middle of an Array or List.

*   Space Complexity : $O(n)$.

    In the worst case, the stack will have all the numbers on it at the same time. This is never more than half the length of the input array.