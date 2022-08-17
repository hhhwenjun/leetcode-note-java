# Trie Tutorial

## Definition

A `Trie` is a special form of a `Nary tree`. Typically, a trie is used to `store strings`. Each Trie node represents `a string` (`a prefix`). Each node might have several children nodes while the paths to different children nodes represent different characters. And the strings the child nodes represent will be the `origin string` represented by the node itself plus `the character on the path`.

<img src="https://s3-lc-upload.s3.amazonaws.com/uploads/2018/02/07/screen-shot-2018-01-31-at-163403.png" alt="img" style="zoom:50%;" />

### Array Solution

```java
class TrieNode {
    // change this value to adapt to different cases
    public static final N = 26;
    public TrieNode[] children = new TrieNode[N];
    
    // you might need some extra values according to different cases
};
```

### Map Solution

```java
class TrieNode {
    public Map<Character, TrieNode> children = new HashMap<>();
    
    // you might need some extra values according to different cases
};
```

## Data Structure - Implement Trie(Prefix Tree)(Medium #208)

```java
class Trie {
    
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }
    
    // insert a word into trie
    // Time complexity : O(m), where m is the key length.
    // Space complexity : O(m).
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++){
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)){
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }
    
    
    // search prefix can be applied in search, but need to check isEnd
    // Time complexity : O(m) In each step of the algorithm we search for the next key character.
    // Space complexity : O(1)
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }
    
    private TrieNode searchPrefix(String word){
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++){
            char currLetter = word.charAt(i);
            if (node.containsKey(currLetter)){
                node = node.get(currLetter);
            }
            else {
                return null;
            }
        }
        return node;
    }
    
    // apply the previous method
    // Time complexity : O(m)
    // Space complexity : O(1)
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }
}

class TrieNode {
    // R links to node children
    private TrieNode[] links;
    private final int R = 26;
    private boolean isEnd;
    
    public TrieNode(){
        links = new TrieNode[R];
    }
    
    public boolean containsKey(char ch){
        return links[ch - 'a'] != null;
    }
    
    public TrieNode get(char ch){
        return links[ch - 'a'];
    }
    
    public void put(char ch, TrieNode node){
        links[ch - 'a'] = node;
    }
    
    public void setEnd(){
        isEnd = true;
    }
    
    public boolean isEnd(){
        return isEnd;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
```

```java
// map implementation
class Trie {
    class TrieNode {
        public boolean isWord; 
        public Map<Character, TrieNode> childrenMap = new HashMap<>();
    }
    
    private TrieNode root; 

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = root;
        for(int i = 0; i < word.length(); i++){
            char c = word.charAt(i);
            if(cur.childrenMap.get(c) == null){
                // insert a new node if the path does not exist
                cur.childrenMap.put(c, new TrieNode());
            }
            cur = cur.childrenMap.get(c); 
        }
        cur.isWord = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(cur.childrenMap.get(c) == null) {
                return false;
            }
            cur = cur.childrenMap.get(c);
        }
        return cur.isWord;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for(int i = 0;i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if(cur.childrenMap.get(c) == null) {
                return false;
            }
            cur = cur.childrenMap.get(c);
        }
        return true;
    }
}
```

*   The time complexity to search in Trie is `O(M)`.
*   The space complexity of hash table is `O(M * N)`.

## Map Sum Pairs (Medium #677)

**Question**: Design a map that allows you to do the following:

-   Maps a string key to a given value.
-   Returns the sum of the values that have a key with a prefix equal to a given string.

Implement the `MapSum` class:

-   `MapSum()` Initializes the `MapSum` object.
-   `void insert(String key, int val)` Inserts the `key-val` pair into the map. If the `key` already existed, the original `key-value` pair will be overridden to the new one.
-   `int sum(string prefix)` Returns the sum of all the pairs' value whose `key` starts with the `prefix`.

**Example 1:**

```
Input
["MapSum", "insert", "sum", "insert", "sum"]
[[], ["apple", 3], ["ap"], ["app", 2], ["ap"]]
Output
[null, null, 3, null, 5]

Explanation
MapSum mapSum = new MapSum();
mapSum.insert("apple", 3);  
mapSum.sum("ap");           // return 3 (apple = 3)
mapSum.insert("app", 2);    
mapSum.sum("ap");           // return 5 (apple + app = 3 + 2 = 5)
```

**Constraints:**

-   `1 <= key.length, prefix.length <= 50`
-   `key` and `prefix` consist of only lowercase English letters.
-   `1 <= val <= 1000`
-   At most `50` calls will be made to `insert` and `sum`.

### Standard Solution

#### Solution #1 Trie

```java
class TrieNode {
    public Map<Character, TrieNode> children;
    public int score;
    
    public TrieNode() {
        children = new HashMap<>();
        score = 0;
    }
}

class MapSum {
    // trie data structure
    public TrieNode root;
    public Map<String, Integer> map;

    public MapSum() {
        root = new TrieNode();
        map = new HashMap<>();
    }
    
    public void insert(String key, int val) {
        int delta = val - map.getOrDefault(key, 0);
        map.put(key, val);
        TrieNode curr = root;
        curr.score += delta;
        for (int i = 0; i < key.length(); i++){
            char currLetter = key.charAt(i);
            curr.children.putIfAbsent(currLetter, new TrieNode());
            curr = curr.children.get(currLetter);
            curr.score += delta;
        }
    }
    
    public int sum(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()){
            curr = curr.children.get(c);
            if (curr == null) return 0;
        }
        return curr.score;
    }
}
```

-   Time Complexity: Every inserts operation is $O(K)$, where K is the length of the key. Every sum operation is $O(K)$
-   Space Complexity: The space used is linear in the size of the total input.

#### Solution #2 Prefix Hashmap

```java
Map<String, Integer> map;
Map<String, Integer> score;
public MapSum() {
    map = new HashMap();
    score = new HashMap();
}
public void insert(String key, int val) {
    int delta = val - map.getOrDefault(key, 0);
    map.put(key, val);
    String prefix = "";
    for (char c: key.toCharArray()) {
        prefix += c;
        score.put(prefix, score.getOrDefault(prefix, 0) + delta);
    }
}
public int sum(String prefix) {
    return score.getOrDefault(prefix, 0);
}
```

-   Time Complexity: Every insert operation is $O(K^2)$, where K is the length of the key, as K strings are made of an average length of K. Every sum operation is $O(1)$
-   Space Complexity: The space used by `map` and `score` is linear in the size of all input `key` and `val` values combined.

## Replace Words (Medium #648)

**Question**: In English, we have a concept called **root**, which can be followed by some other word to form another longer word - let's call this word **successor**. For example, when the **root** `"an"` is followed by the **successor** word `"other"`, we can form a new word `"another"`.

Given a `dictionary` consisting of many **roots** and a `sentence` consisting of words separated by spaces, replace all the **successors** in the sentence with the **root** forming it. If a **successor** can be replaced by more than one **root**, replace it with the **root** that has **the shortest length**.

Return *the `sentence`* after the replacement.

**Example 1:**

```
Input: dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
Output: "the cat was rat by the bat"
```

**Example 2:**

```
Input: dictionary = ["a","b","c"], sentence = "aadsfasf absbs bbab cadsfafs"
Output: "a a b c"
```

**Constraints:**

-   `1 <= dictionary.length <= 1000`
-   `1 <= dictionary[i].length <= 100`
-   `dictionary[i]` consists of only lower-case letters.
-   `1 <= sentence.length <= 106`
-   `sentence` consists of only lower-case letters and spaces.
-   The number of words in `sentence` is in the range `[1, 1000]`
-   The length of each word in `sentence` is in the range `[1, 1000]`
-   Every two consecutive words in `sentence` will be separated by exactly one space.
-   `sentence` does not have leading or trailing spaces.

### Standard Solution

#### Solution #1 Trie

```java
class TrieNode {
    TrieNode[] children;
    String word;
    TrieNode(){
        children = new TrieNode[26];
    }
}
class Solution {
    public String replaceWords(List<String> dictionary, String sentence) {
        // use a trie structure to store prefix to trie
        TrieNode trie = new TrieNode();
        for (String prefix : dictionary){
            TrieNode curr = trie;
            for (char letter : prefix.toCharArray()){
                // create a children trienode
                if (curr.children[letter - 'a'] == null){
                    curr.children[letter - 'a'] = new TrieNode();
                }
                curr = curr.children[letter - 'a'];
            }
            curr.word = prefix;
        }
        
        // construct a new string to return 
        StringBuilder res = new StringBuilder();
        
        for (String word : sentence.split("\\s+")){
            if (res.length() > 0) res.append(" "); 
            
            TrieNode curr = trie;
            for (char letter : word.toCharArray()){
                if (curr.children[letter - 'a'] == null || curr.word != null){
                    break;
                }
                curr = curr.children[letter - 'a'];
            }
            res.append(curr.word != null ? curr.word : word);
        }
        return res.toString();
    }
}
```

-   Time Complexity: $O(N)$ where N is the length of the `sentence`. Every query of a word is in linear time.
-   Space Complexity: $O(N)$ the size of our trie.