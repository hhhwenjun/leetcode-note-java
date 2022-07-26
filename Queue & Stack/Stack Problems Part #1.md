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

## Minimum Remove to Make Valid Parentheses(Medium #1249)

**Question**: Given a string s of `'('` , `')'` and lowercase English characters.

Your task is to remove the minimum number of parentheses ( `'('` or `')'`, in any positions ) so that the resulting *parentheses string* is valid and returns **any** valid string.

Formally, a *parentheses string* is valid if and only if:

-   It is the empty string, that contains only lowercase characters, or
-   It can be written as `AB` (`A` concatenated with `B`), where `A` and `B` are valid strings, or
-   It can be written as `(A)`, where `A` is a valid string.

**Example 1:**

```
Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
```

**Example 2:**

```
Input: s = "a)b(c)d"
Output: "ab(c)d"
```

**Example 3:**

```
Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.
```

**Constraints:**

-   `1 <= s.length <= 105`
-   `s[i]` is either`'('` , `')'`, or lowercase English letter`.`

### My Solution

```java
public String minRemoveToMakeValid(String s) {
    Set<Integer> remove = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    Stack<Integer> stack = new Stack<>();
    for(int i = 0; i < s.length(); i++){
        if (s.charAt(i) == '('){
            stack.push(i);
        }
        else if (s.charAt(i) == ')'){
            if (!stack.isEmpty()){
                stack.pop();
            }
            else {
                remove.add(i);
            }
        }
    }
    while (!stack.isEmpty()){
        remove.add(stack.pop());
    }
    for (int j = 0; j < s.length(); j++){
        if (!remove.contains(j)){
            sb.append(s.charAt(j));
        }
    }
    return sb.toString();
}
```

### Standard Solution

#### Solution #1 Using a Stack and String Builder

*   Same as my solution
*   Use stack to record the index of parentheses, and use hash set to record what should be removed from the string

```java
public String minRemoveToMakeValid(String s) {
    Set<Integer> indexesToRemove = new HashSet<>();
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } if (s.charAt(i) == ')') {
            if (stack.isEmpty()) {
                indexesToRemove.add(i);
            } else {
                stack.pop();
            }
        }
    }
    // Put any indexes remaining on stack into the set.
    while (!stack.isEmpty()) indexesToRemove.add(stack.pop());
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
        if (!indexesToRemove.contains(i)) {
            sb.append(s.charAt(i));
        }
    }
    return sb.toString();
}
```

*   Time complexity: $O(n)$, where n is the length of the input string.

*   Space complexity: $O(n)$, where n is the length of the input string.

    We are using a **stack**, **set**, and **string builder**, each of which could have up to n characters in them, and so require up to $O(n)$ space.

#### Solution #2 Two-Pass String Builder

*   A key observation you might have made from the previous algorithm is that for all invalid `")"`, we know immediately that they are invalid (they are the ones we were putting in the set). It is the `"("` that we don't know about until the end (as they are what was left on the stack at the end).
*   We could be building up a string builder in that first loop that has *all* of the invalid `")"` removed. Then go to the second loop to remove all the invalid `"("`.
    *   Reverse the first-term result and put it back to the algorithm to remove invalid closing

```java
private StringBuilder removeInvalidClosing(charSequence string, char open, char close){
    StringBuilder sb = new StringBuilder();
    int balance = 0;
    for (int i = 0; i < string.length(); i++){
        char c = string.charAt(i);
        if (c == open){
            balance++;
        }
        if (c == close){
            if (balance == 0) continue;
            balance--;
        }
        sb.append(c);
    }
    return sb;
    
    public String minRemoveToMakeValid(String s){
        StringBuilder result = removeInvalidClosing(s, '(', ')');
        result = removeInvalidClosing(result.reverse(), ')', '(');
        return result.reverse().toString();
    }
}
```

*   Time and space complexity is the same.

## Implement Stack Using Queues (Easy #225)

**Question**: Implement a last-in-first-out (LIFO) stack using only two queues. The implemented stack should support all the functions of a normal stack (`push`, `top`, `pop`, and `empty`).

Implement the `MyStack` class:

-   `void push(int x)` Pushes element x to the top of the stack.
-   `int pop()` Removes the element on the top of the stack and returns it.
-   `int top()` Returns the element on the top of the stack.
-   `boolean empty()` Returns `true` if the stack is empty, `false` otherwise.

**Notes:**

-   You must use **only** standard operations of a queue, which means that only `push to back`, `peek/pop from front`, `size` and `is empty` operations are valid.
-   Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque (double-ended queue) as long as you use only a queue's standard operations.

**Example 1:**

```
Input
["MyStack", "push", "push", "top", "pop", "empty"]
[[], [1], [2], [], [], []]
Output
[null, null, null, 2, 2, false]

Explanation
MyStack myStack = new MyStack();
myStack.push(1);
myStack.push(2);
myStack.top(); // return 2
myStack.pop(); // return 2
myStack.empty(); // return False
```

**Constraints:**

-   `1 <= x <= 9`
-   At most `100` calls will be made to `push`, `pop`, `top`, and `empty`.
-   All the calls to `pop` and `top` are valid.

### My Solution

*   Use a pointer point to the top element
*   Each time to pop, dequeue all elements from one queue to another queue

```java
class MyStack {
    Queue<Integer> queue1;
    Queue<Integer> queue2;
    int top;

    public MyStack() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }
    
    public void push(int x) {
        queue1.add(x);
        top = x;
    }
    
    public int pop() {
        while(queue1.size() > 1){
            top = queue1.poll();
            queue2.add(top);
        }
        int res = queue1.poll();
        // swap queue1 and queue2
        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;
        return res;
    }
    
    public int top() {

        return top;
    }
    
    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }
}
```

### Standard Solution

#### Solution #1 Two Queues, push - $O(1)$, pop $O(n)$

*   Same as my solution

#### Solution #2 One Queue, push - $O(n)$, pop $O(1)$

*   Only use 1 queue, each time push a new element, pop all the previous elements and re-add them to the queue

```java
class MyStack {
    Queue<Integer> q1;

    public MyStack() {
        q1 = new LinkedList<>();
    }
    
    public void push(int x) {
        q1.add(x);
        int sz = q1.size();
        while (sz > 1) {
            q1.add(q1.remove()); // add to the back
            sz--;
        }
    }
    
    public int pop() {
        q1.remove();
    }
    
    public int top() {
		return q1.peek();
    }
    
    public boolean empty() {
        return q1.isEmpty();
    }
}
```

## Min Stack (Medium #155)

**Question**: Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Implement the `MinStack` class:

-   `MinStack()` initializes the stack object.
-   `void push(int val)` pushes the element `val` onto the stack.
-   `void pop()` removes the element on the top of the stack.
-   `int top()` gets the top element of the stack.
-   `int getMin()` retrieves the minimum element in the stack.

You must implement a solution with `O(1)` time complexity for each function.

**Example 1:**

```
Input
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]

Output
[null,null,null,null,-3,null,0,-2]

Explanation
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2
```

**Constraints:**

-   `-231 <= val <= 231 - 1`
-   Methods `pop`, `top` and `getMin` operations will always be called on **non-empty** stacks.
-   At most `3 * 104` calls will be made to `push`, `pop`, `top`, and `getMin`.

### Standard Solution

#### Solution #1 Minimum Pairs

*   Use a pair to record current value and the current minimum value
*   Each time push/pop the pair to the list

```java
private Stack<int[]> stack = new Stack<>();
public MinStack() { }

public void push(int x) {

    /* If the stack is empty, then the min value
     * must just be the first value we add. */
    if (stack.isEmpty()) {
        stack.push(new int[]{x, x});
        return;
    }
    int currentMin = stack.peek()[1];
    stack.push(new int[]{x, Math.min(x, currentMin)});
}
public void pop() {
    stack.pop();
}
public int top() {
    return stack.peek()[0];
}
public int getMin() {
    return stack.peek()[1];
}
```

*   Time Complexity: $O(1)$ for all operations.

*   Space Complexity: $O(n)$

#### Solution #2 Use two stacks

*   We have two stacks, one is the normal stack and another one is only store minimum values
*   Each time we check minimum values in min stack, and the normal stack

```java
private Stack<Integer> stack = new Stack<>();
private Stack<Integer> minStack = new Stack<>();
public MinStack() { }

public void push(int x) {
    stack.push(x);
    if (minStack.isEmpty() || x <= minStack.peek()) {
        minStack.push(x);
    }
}
public void pop() {
    if (stack.peek().equals(minStack.peek())) {
        minStack.pop();
    }
    stack.pop();
}
public int top() {
    return stack.peek();
}
public int getMin() {
    return minStack.peek();
}
```

-   Time Complexity: $O(1)$ for all operations. Same as above. All our modifications are still $O(1)$
-   Space Complexity: $O(n)$

## Valid Parentheses (Easy #20)

**Question**: Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:

1.  Open brackets must be closed by the same type of brackets.
2.  Open brackets must be closed in the correct order.

**Example 1:**

```
Input: s = "()"
Output: true
```

**Example 2:**

```
Input: s = "()[]{}"
Output: true
```

**Example 3:**

```
Input: s = "(]"
Output: false
```

**Constraints:**

-   `1 <= s.length <= 104`
-   `s` consists of parentheses only `'()[]{}'`.

### My Solution

```java
private Map<Character, Character> parenMap = new HashMap<>();
public boolean isValid(String s) {
    // 1. create a stack to store char in string
    parenMap.put(')', '(');
    parenMap.put('}', '{');
    parenMap.put(']', '[');
    Stack<Character> stack = new Stack<>();

    // 2. each time we find ) we pop the element in stack
    for (char ch : s.toCharArray()){
        if (!parenMap.containsKey(ch)){
            stack.add(ch);
        }
        else {
            if (stack.isEmpty()) return false;
            char curr = stack.pop();
            char pair = parenMap.get(ch);
            if (curr != pair) return false;
        }
    }
    // 3. if the pop element is not the supposed half bracket, return false
    return stack.isEmpty();
}
```

### Standard Solution

#### Solution #1 HashMap

*   Similar solution to my solution

```java
  // Hash table that takes care of the mappings.
private HashMap<Character, Character> mappings;

// Initialize hash map with mappings. This simply makes the code easier to read.
public Solution() {
    this.mappings = new HashMap<Character, Character>();
    this.mappings.put(')', '(');
    this.mappings.put('}', '{');
    this.mappings.put(']', '[');
}

public boolean isValid(String s) {

// Initialize a stack to be used in the algorithm.
Stack<Character> stack = new Stack<Character>();

    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);

        // If the current character is a closing bracket.
        if (this.mappings.containsKey(c)) {

        // Get the top element of the stack. If the stack is empty, set a dummy value of '#'
        char topElement = stack.empty() ? '#' : stack.pop();

        // If the mapping for this bracket doesn't match the stack's top element, return false.
        if (topElement != this.mappings.get(c)) {
            return false;
        }
        else {
            // If it was an opening bracket, push to the stack.
            stack.push(c);
        }
    }

	// If the stack still contains elements, then it is an invalid expression.
	return stack.isEmpty();
}
```

-   Time complexity: $O(n)$ because we simply traverse the given string one character at a time and push and pop operations on a stack take $O(1)$ time.
-   Space complexity: $O(n)$ as we push all opening brackets onto the stack and in the worst case, we will end up pushing all the brackets onto the stack. e.g. `((((((((((`.
