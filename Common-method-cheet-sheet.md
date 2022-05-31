# Commonly Used Method in LeetCode
Authored by: hhhwenjun

## Integer, String, Character Transformation
#### Char to Int 
1. Subtract with '0' to find the value: `char - '0'`
2. Use built-in character method to get the numeric value: `Character.getNumericValue(char)`
**Tips**: Do not use `Integer.valueOf` or `Integer.parseInt`, they would only show the char value, such as 'a' -> 67
