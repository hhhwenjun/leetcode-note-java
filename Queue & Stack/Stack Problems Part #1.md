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

## Daily Temperatures (Medium #739)

**Question**: Given an array of integers `temperatures` represents the daily temperatures, return *an array* `answer` *such that* `answer[i]` *is the number of days you have to wait after the* `ith` *day to get a warmer temperature*. If there is no future day for which this is possible, keep `answer[i] == 0` instead.

**Example 1:**

```
Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
```

**Example 2:**

```
Input: temperatures = [30,40,50,60]
Output: [1,1,1,0]
```

**Example 3:**

```
Input: temperatures = [30,60,90]
Output: [1,1,0]
```

**Constraints:**

-   `1 <= temperatures.length <= 105`
-   `30 <= temperatures[i] <= 100`

### My Solution

*   Two pointer method, but time complexity can go to $O(n^2)$, space complexity is $O(1)$ with constant extra space.

```java
public int[] dailyTemperatures(int[] temperatures) {
    // two pointers: slow pointer -> current temp, fast pointer -> after ith day
    // 1. make fast and slow pointer
    if (temperatures == null) return new int[0];
    int[] res = new int[temperatures.length];
    int slow = 0, fast = 0;

    while (slow < temperatures.length){
        // 2. each time let fast pointer find the day temp > curr temp, slow -> curr
        int currTemp = temperatures[slow];
        fast = slow;
        while(fast < temperatures.length){
            if (temperatures[fast] > temperatures[slow]){
                break;
            }
            fast++;
        }
        // we cannot find any day warmer
        if (fast == temperatures.length){
            res[slow] = 0;
        }
        else {
            res[slow] = fast - slow;
        }
        // 3. then slow++, fast = slow, point to the same day temp, repeat the process
        slow++;
    }
    return res;
}
```

### Standard Solution

#### Solution #1 Monotonic Stack

*   Use a stack to push each day to the stack; if we peek at the previous day and smaller than the current day, pop it out and add to the answer

*   In this way, we only need 1 pass then we can have all the answers

```java
public int[] dailyTemperatures(int[] temperatures){
    int n = temperatures.length;
    int[] answer = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (int currDay = 0; currDay < n; currDay++){
        int currentTemp = temperatures[currDay];
        // pop until the current day's temperature is not warmer than the temp at top of the stack
        while (!stack.isEmpty() && temperatures[stack.peek()] < currentTemp){
            int prevDay = stack.pop();
            answer[prevDay] = currDay - prevDay;
        }
        stack.push(currDay);
    }
    return answer;
}
```

-   Time complexity: $O(N)$ At first glance, it may look like the time complexity of this algorithm should be $O(N^2)$ ,because there is a nested while loop inside the for-loop. However, each element can only be added to the stack once, which means the stack is limited to N pops. Every iteration of the while loop uses 1 pop, which means the while loop will not iterate more than N times in total, across all iterations of the for loop.

    An easier way to think about this is that in the worst case, every element will be pushed and popped once. This gives a time complexity of $O(2 \cdot N) = O(N)$*

-   Space complexity: $O(N)$

    If the input was non-increasing, then no element would ever be popped from the stack, and the stack would grow to a size of `N` elements at the end.

    Note: `answer` does not count towards the space complexity because space used for the output format does not count.

#### Solution #2 Array

*   It is hard to understand. Starts from the end of array to the front.

```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int hottest = 0;
    int answer[] = new int[n];

    for (int currDay = n - 1; currDay >= 0; currDay--) {
        int currentTemp = temperatures[currDay];
        if (currentTemp >= hottest) {
            hottest = currentTemp;
            continue;
        }

        int days = 1;
        while (temperatures[currDay + days] <= currentTemp) {
            // Use information from answer to search for the next warmer day
            days += answer[currDay + days];
        }
        answer[currDay] = days;
    }

    return answer;
}
```

*   Time complexity: $O(N)$

*   Space complexity: $O(1)$

## Implement Queue using Stacks (Easy #232)

**Question**: Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the functions of a normal queue (`push`, `peek`, `pop`, and `empty`).

Implement the `MyQueue` class:

-   `void push(int x)` Pushes element x to the back of the queue.
-   `int pop()` Removes the element from the front of the queue and returns it.
-   `int peek()` Returns the element at the front of the queue.
-   `boolean empty()` Returns `true` if the queue is empty, `false` otherwise.

**Notes:**

-   You must use **only** standard operations of a stack, which means only `push to top`, `peek/pop from top`, `size`, and `is empty` operations are valid.
-   Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque (double-ended queue) as long as you use only a stack's standard operations.

**Example 1:**

```
Input
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]
Output
[null, null, null, 1, 1, false]

Explanation
MyQueue myQueue = new MyQueue();
myQueue.push(1); // queue is: [1]
myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
myQueue.peek(); // return 1
myQueue.pop(); // return 1, queue is [2]
myQueue.empty(); // return false
```

**Constraints:**

-   `1 <= x <= 9`
-   At most `100` calls will be made to `push`, `pop`, `peek`, and `empty`.
-   All the calls to `pop` and `peek` are valid.

### My Solution

```java
Stack<Integer> stack1;
Stack<Integer> stack2;
public MyQueue() {
    stack1 = new Stack<>();
    stack2 = new Stack<>();
}
public void push(int x) {
    stack1.add(x);
}
public int pop() {
    if (stack2.isEmpty()){
        while(!stack1.isEmpty()) stack2.add(stack1.pop());
    }
    return stack2.pop();
}
public int peek() {
    if (stack2.isEmpty()){
        while(!stack1.isEmpty()) stack2.add(stack1.pop());
    }
    return stack2.peek();
}
public boolean empty() {
    return stack1.isEmpty() && stack2.isEmpty();
}
```

*   Standard solution is same as my solution

## Decode String (Medium #394)

**Question**: Given an encoded string, return its decoded string.

The encoding rule is: `k[encoded_string]`, where the `encoded_string` inside the square brackets is being repeated exactly `k` times. Note that `k` is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, `k`. For example, there will not be input like `3a` or `2[4]`.

The test cases are generated so that the length of the output will never exceed `105`.

**Example 1:**

```
Input: s = "3[a]2[bc]"
Output: "aaabcbc"
```

**Example 2:**

```
Input: s = "3[a2[c]]"
Output: "accaccacc"
```

**Example 3:**

```
Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"
```

**Constraints:**

-   `1 <= s.length <= 30`
-   `s` consists of lowercase English letters, digits, and square brackets `'[]'`.
-   `s` is guaranteed to be **a valid** input.
-   All the integers in `s` are in the range `[1, 300]`.

### My Solution

```java
public String decodeString(String s) {
    // stack: find ']' then start to pop elements from stack
    // 1. create a stack, store char, put all elements to stack
    Stack<Character> stack = new Stack<>();
    char[] sArray = s.toCharArray();
    for (char ch : sArray){
        if (ch != ']'){
            stack.add(ch);
            continue;
        }
        // 2. when put element, if find ']', start to pop
        StringBuilder currString = new StringBuilder();
        while(!stack.isEmpty()){
            char curr = stack.pop();
            // 3. until we pop '[' and k, repeat k times of curr string
            if (curr == '['){
                break;
            }
            currString.append(curr);
        }

        currString = currString.reverse();
        String pattern = currString.toString();
        StringBuilder times = new StringBuilder();
        while (!stack.isEmpty() && stack.peek() - '0' < 10){
            times.append(stack.pop());
        }
        int time = Integer.parseInt(times.reverse().toString());
        for (int i = 1; i < time; i++){
            currString.append(pattern);
        }
        String repeat = currString.toString();
        // 4. put the string(chars) back to stack
        for (char newch : repeat.toCharArray()){
            stack.add(newch);
        }
    }

    StringBuilder res = new StringBuilder();
    while(!stack.isEmpty()){
        res.append(stack.pop());
    }
    return res.reverse().toString();
}
```



### Standard Solution

#### Solution #1 Using Stack

*   Same idea with my solution but not the same implementation

```java
public String decodeString(String s) {
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == ']') {
            List<Character> decodedString = new ArrayList<>();
            // get the encoded string
            while (stack.peek() != '[') {
                decodedString.add(stack.pop());
            }
            // pop [ from the stack
            stack.pop();
            int base = 1;
            int k = 0;
            // get the number k
            while (!stack.isEmpty() && Character.isDigit(stack.peek())) {
                k = k + (stack.pop() - '0') * base;
                base *= 10;
            }
            // decode k[decodedString], by pushing decodedString k times into stack
            while (k != 0) {
                for (int j = decodedString.size() - 1; j >= 0; j--) {
                    stack.push(decodedString.get(j));
                }
                k--;
            }
        }
        // push the current character to stack
        else {
            stack.push(s.charAt(i));
        }
    }      
    // get the result from stack
    char[] result = new char[stack.size()];
    for (int i = result.length - 1; i >= 0; i--) {
        result[i] = stack.pop();
    }
    return new String(result);
}
```

-   Time Complexity: $\mathcal{O}(\text{maxK} ^ {\text{countK}}\cdot n)$, where $\text{maxK}$ is the maximum value of k, $\text{countK}$ is the count of nested k values and n is the maximum length of encoded string. Example, for s = `20[a10[bc]]`, $\text{maxK}$ is 2020, $\text{countK}$ is 2 as there are 2 nested k values (`20` and `10`) . Also, there are 2 encoded strings `a` and `bc` with maximum length of encoded string ,n as 2
-   Space Complexity: $\mathcal{O}(\text{sum}(\text{maxK} ^ {\text{countK}}\cdot n))$, where $\text{maxK}$ is the maximum value of k, $\text{countK}$ is the count of nested k values and n is the maximum length of encoded string.

#### Solution 2 Using 2 Stacks

*   One is `countStack` to store all the interger k
*   One is `stringStack` to store all the decoded string

```java
public String decodeString(String s){
	Stack<Integer> countStack = new Stack<>();
    Stack<StringBuilder> stringStack = new Stack<>();
    StringBuilder currentString = new StringBuilder();
    int k = 0;
    for (char ch : s.toCharArray()){
        if (Character.isDigit(ch)){
            k = k * 10 + ch - '0';
        } else if (ch == '['){
            // push the number k to countStack
            countStack.push(k);
            // push the current String to string stack
            stringStack.push(currentString);
            // reset currentString and k
            currentString = new StringBuilder();
            k = 0;
        } else if (ch == ']'){
            StringBuilder decodedString = stringStack.pop();
            // decode currentK[currentString] by appending currentString k times
            for (int currentK = countStack.pop(); currentK > 0; currentK--){
                decodedString.append(currentString);
            }
            currentString = decodedString;
        } else {
            currentString.append(ch);
        }
    }
    return currentString.toString();
}
```

-   Time Complexity: $\mathcal{O}(\text{maxK} \cdot n)$, where $\text{maxK}$ is the maximum value of k, and n is the length of a given string s. We traverse a string of size n and iterate k times to decode each pattern of form $\text{k[string]}$. This gives us worst-case time complexity as $\mathcal{O}(\text{maxK} \cdot n)$.
-   Space Complexity: $\mathcal{O}(m+n)$, where m is the number of `letters(a-z)` and n is the number of `digits(0-9)` in string s. In the worst case, the maximum size of $\text{stringStack}$ and $\text{countStack}$ could be m and n respectively.

## Car Fleet (Medium #853)

**Question**: There are `n` cars going to the same destination along a one-lane road. The destination is `target` miles away.

You are given two integer array `position` and `speed`, both of length `n`, where `position[i]` is the position of the `ith` car and `speed[i]` is the speed of the `ith` car (in miles per hour).

A car can never pass another car ahead of it, but it can catch up to it and drive bumper to bumper **at the same speed**. The faster car will **slow down** to match the slower car's speed. The distance between these two cars is ignored (i.e., they are assumed to have the same position).

A **car fleet** is some non-empty set of cars driving at the same position and same speed. Note that a single car is also a car fleet.

If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.

Return *the **number of car fleets** that will arrive at the destination*. 

**Example 1:**

```
Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
Output: 3
Explanation:
The cars starting at 10 (speed 2) and 8 (speed 4) become a fleet, meeting each other at 12.
The car starting at 0 does not catch up to any other car, so it is a fleet by itself.
The cars starting at 5 (speed 1) and 3 (speed 3) become a fleet, meeting each other at 6. The fleet moves at speed 1 until it reaches target.
Note that no other cars meet these fleets before the destination, so the answer is 3.
```

**Example 2:**

```
Input: target = 10, position = [3], speed = [3]
Output: 1
Explanation: There is only one car, hence there is only one fleet.
```

**Example 3:**

```
Input: target = 100, position = [0,2,4], speed = [4,2,1]
Output: 1
Explanation:
The cars starting at 0 (speed 4) and 2 (speed 2) become a fleet, meeting each other at 4. The fleet moves at speed 2.
Then, the fleet (speed 2) and the car starting at 4 (speed 1) become one fleet, meeting each other at 6. The fleet moves at speed 1 until it reaches target.
```

**Constraints:**

-   `n == position.length == speed.length`
-   `1 <= n <= 105`
-   `0 < target <= 106`
-   `0 <= position[i] < target`
-   All the values of `position` are **unique**.
-   `0 < speed[i] <= 106`

### Standard Solution

#### Solution #1 Arrays

```java
public int carFleet(int target, int[] position, int[] speed) {
    int n = speed.length;
    int[][] cars = new int[n][2];
    for (int i = 0; i < n; i++){
        cars[i][0] = position[i];
        cars[i][1] = speed[i];
    }
    // sort base on the distance to the target, slow car catch up
    Arrays.sort(cars, (a, b) -> a[0] - b[0]);

    int fleet = 0;
    double prevTime = -1.0;
    for (int i = n - 1; i >= 0; i--){
        // the curr needed time to the target 
        double curTime = (target - cars[i][0]) * 1.0 / cars[i][1];
        // if curr time less than prev car, we can catch up
        if (curTime > prevTime){
            fleet++;
            prevTime = curTime;
        }
    }
    return fleet;
}
```

#### Solution #2 Stack

*   Same solution as the last one, but using the stack to keep track of the time to the target
*   Stack size indicates the number of fleet
*   Both solution use `sorts`, so the time complexity is `O(nlogn)`

```java
public int carFleet(int target, int[] positions, int[] speeds) {

    int position = 0, speed = 1;
    int len = positions.length;
    int cars[][] = new int[len][2];
    
    for(int i = 0; i < len; i++)
    {
        cars[i][position] = positions[i];
        cars[i][speed] = speeds[i];
    }
    Arrays.sort(cars, (a,b) -> Integer.compare(b[position], a[position]));
    
    Stack<Double> stack = new Stack<>();
    
    for(int car[] : cars)
    {
        stack.push((double)(target - car[position]) / car[speed]);
        if(stack.size() >= 2 && stack.peek() <= stack.get(stack.size() - 2))
            stack.pop();
    }
    return stack.size();
}
```

