# Online Assessment

## Square Data Engineering Intern

**Source**: [Credit to this link and author](https://leetcode.com/discuss/interview-question/1014660/samsara-oa-codesignal-summer-internship)

#### Problem

```
You have a passage of text that needs to be typed out, but some of the letter keys on your keyboard are broken! You are given an array letters representing the working letter keys, as well as a string text, and your task is to determine how many of the words from text can be typed using the broken keyboard. It is guaranteed that all of the non-letter keys are working (including all punctuation and special characters).
A word is defined as a sequence of consecutive characters which does not contain any spaces.
The given text is a string consisting of words, each separated by exactly one space.
It is guaranteed that text does not contain any leading or trailing spaces.
Note that the characters in letters are all lowercase, but since the shift key is working,
it's possible to type the uppercase versions also.
```

```
Example:
1. For text = "Hello, this is CodeSignal!" and letters = ['e', 'i', 'h', 'l', 'o', 's'],
the output should be brokenKeyboard(text, letters) = 2.
    - "Hello," can be typed since the characters 'h', 'e', 'l' and 'o' are in letters.
    - Note that the symbol ',' also belongs to the current word and can by typed.
    - "this" cannot be typed because the character 't' is not in letters.
    - "is" can be typed (both 'i' and 's' appear in letters).
    - "CodeSignal!" cannot be typed because the character 'c' is not in letters (as well as 'd', 'g', 'n', and 'a').
2. For text = "Hi, this is Chris!" and letters = ['r', 's', 't', 'c', 'h'], the output should be
brokenKeyboard(text, letters) = 0.
    - Each word contains the character 'i' which does not appear in letters and thus cannot be typed on the keyboard.
3. For text = "3 + 2 = 5" and letters = [], the output should be brokenKeyboard(text, letters) = 5.
    - There are no working letters on the keyboard, but since each of these words consists of numbers and
      special characters, they can all be typed, and there are 5 of them.
```

**Solution**:

```java
public static int checkBrokenLetter(String text, char[] letters) {
    // Build hashSet
    Set<Character> set = new HashSet<>();
    for (char c : letters) {
      set.add(c);
    }
    String[] words= text.split(" ");
    int count = 0;
    for (String word: words) {
        // important to learn how to clean the string, regular expression
      String cleanText = word.replaceAll("[^[a-zA-Z]]", "").toLowerCase();
      System.out.println(cleanText);
      boolean valid = true;
      for (int i = 0; i < cleanText.length(); i++) {
        if (! set.contains(cleanText.charAt(i))) {
          valid = false;
          break;
        }
      }
      if (valid) {
        count++;
      }
    }
    return count;
 }
```

