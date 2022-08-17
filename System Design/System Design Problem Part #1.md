# System Design Problem Part #1

## Design Search Autocomplete System (Hard #642)

**Question**: Design a search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character `'#'`).

You are given a string array `sentences` and an integer array `times` both of length `n` where `sentences[i]` is a previously typed sentence and `times[i]` is the corresponding number of times the sentence was typed. For each input character except `'#'`, return the top `3` historical hot sentences that have the same prefix as the part of the sentence already typed.

Here are the specific rules:

-   The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
-   The returned top `3` hot sentences should be sorted by hot degree (The first is the hottest one). If several sentences have the same hot degree, use ASCII-code order (smaller one appears first).
-   If less than `3` hot sentences exist, return as many as you can.
-   When the input is a special character, it means the sentence ends, and in this case, you need to return an empty list.

Implement the `AutocompleteSystem` class:

-   `AutocompleteSystem(String[] sentences, int[] times)` Initializes the object with the `sentences` and `times` arrays.
-   `List<String> input(char c)` This indicates that the user typed the character `c`
    -   Returns an empty array `[]` if `c == '#'` and stores the inputted sentence in the system.
    -   Returns the top `3` historical hot sentences that have the same prefix as the part of the sentence already typed. If there are fewer than `3` matches, return them all. 

**Example 1:**

```
Input
["AutocompleteSystem", "input", "input", "input", "input"]
[[["i love you", "island", "iroman", "i love leetcode"], [5, 3, 2, 2]], ["i"], [" "], ["a"], ["#"]]
Output
[null, ["i love you", "island", "i love leetcode"], ["i love you", "i love leetcode"], [], []]

Explanation
AutocompleteSystem obj = new AutocompleteSystem(["i love you", "island", "iroman", "i love leetcode"], [5, 3, 2, 2]);
obj.input("i"); // return ["i love you", "island", "i love leetcode"]. There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman". Also we only need to output top 3 hot sentences, so "ironman" will be ignored.
obj.input(" "); // return ["i love you", "i love leetcode"]. There are only two sentences that have prefix "i ".
obj.input("a"); // return []. There are no sentences that have prefix "i a".
obj.input("#"); // return []. The user finished the input, the sentence "i a" should be saved as a historical sentence in system. And the following input will be counted as a new search.
```

**Constraints:**

-   `n == sentences.length`
-   `n == times.length`
-   `1 <= n <= 100`
-   `1 <= sentences[i].length <= 100`
-   `1 <= times[i] <= 50`
-   `c` is a lowercase English letter, a hash `'#'`, or space `' '`.
-   Each tested sentence will be a sequence of characters `c` that end with the character `'#'`.
-   Each tested sentence will have a length in the range `[1, 200]`.
-   The words in each input sentence are separated by single spaces.
-   At most `5000` calls will be made to `input`.

### Standard Solution

```java
class AutocompleteSystem{
    class TrieNode implements Comparable<TrieNode> {
        TrieNode[] children; // children array
        String s; // stored string
        int times; // times of call
        List<TrieNode> hot; // top 3 hot strings
        
        public TrieNode(){
            children = new TrieNode[128];
            s = null;
            times = 0;
            hot = new ArrayList<>();
        }
        
        public int compareTo(TrieNode o){
            if (this.times == o.times){
                // compare the string alphebet
                return this.s.compareTo(o.s);
            }
            return o.times - this.times;
        }
        
        public void update(TrieNode node){
            if (!this.hot.contains(node)){
                this.hot.add(node);
            }
            Collections.sort(hot);
            if (hot.size() > 3){
                hot.remove(hot.size() - 1);
            }
        }
    }
    
    TrieNode root;
    TrieNode cur;
    StringBuilder sb;
    
    public AutocompleteSystem(String[] sentences, int[] times){
        root = new TrieNode();
        cur = root;
        sb = new StringBuilder();
        
        for (int i = 0; i < times.length; i++){
            add(sentences[i], times[i]);
        }
    }
    
    public void add(String sentence, int t){
        TrieNode tmp = root;
        List<TrieNode> visited = new ArrayList<>();
        for (char c : sentence.toCharArray()){
            if (tmp.children[c] == null){
                tmp.children[c] = new TrieNode();
            }
            tmp = tmp.children[c];
            visited.add(tmp);
        }
        
        tmp.s = sentence;
        tmp.times += t;
        
        for (TrieNode node : visited){
            node.update(tmp);
        }
    }
    
    public List<String> input(char c){
        List<String> res = new ArrayList<>();
        if (c == '#'){
            add(sb.toString(), 1);
            sb = new StringBuilder();
            cur = root;
            return res;
        }
        
        sb.append(c);
        if (cur != null){
            cur = cur.children[c];
        }
        
        if (cur == null) return res;
        for (TrieNode node : cur.hot){
            res.add(node.s);
        }
        return res;
    }
}
```

## Design Add and Search Words Data Structure (Medium #211)

**Question**: Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the `WordDictionary` class:

-   `WordDictionary()` Initializes the object.
-   `void addWord(word)` Adds `word` to the data structure, it can be matched later.
-   `bool search(word)` Returns `true` if there is any string in the data structure that matches `word` or `false` otherwise. `word` may contain dots `'.'` where dots can be matched with any letter.

**Example:**

```
Input
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
Output
[null,null,null,null,false,true,true,true]

Explanation
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True
```

**Constraints:**

-   `1 <= word.length <= 25`
-   `word` in `addWord` consists of lowercase English letters.
-   `word` in `search` consist of `'.'` or lowercase English letters.
-   There will be at most `3` dots in `word` for `search` queries.
-   At most `104` calls will be made to `addWord` and `search`.

### Standard Solution

```java
class TrieNode {
    Map<Character, TrieNode> children = new HashMap();
    boolean word = false;
    public TrieNode(){}
}

class WordDictionary {
    TrieNode trie;
    
    /** Initialize your data structure here.**/
    public WordDictionary(){
        trie = new TrieNode();
    }
    
    /** Adds a word into the data structure **/
    public void addWord(String word){
        TrieNode node = trie;
        
        for (char ch : word.toCharArray()){
            if (!node.children.containsKey(ch)){
                node.children.put(ch, new TrieNode());
            }
            node = node.children.get(ch);
        }
        node.word = true;
    }
    
    /** returns if the word is in the node.**/
    public boolean searchInNode(String word, TrieNode node){
        for (int i = 0; i < word.length(); i++){
            char ch = word.charAt(i);
            if (!node.children.containsKey(ch)){
                // if the current char is '.'
                if (ch == '.'){
                    for (char x : node.children.keySet()){
                        TrieNode child = node.children.get(x);
                        if (searchInNode(word.substring(i + 1), child)){
                            return true;
                        }
                    }
                }
                // if no nodes lead to answer
                return false;
            } else {
                node = node.children.get(ch);
            }
        }
        return node.word;
    }
    
    public boolean search(String word){
        return searchInNode(word, trie);
    }
}
```

-   Time complexity: $\mathcal{O}(M)$ for the "well-defined" words without dots, where M is the key length, and N is a number of keys, and $\mathcal{O}(N \cdot 26 ^ M)$ for the "undefined" words. That corresponds to the worst-case situation of searching an undefined word $\underbrace{.........}_\text{M times}$ which is one character longer than all inserted keys.
-   Space complexity: $\mathcal{O}(1)$ for the search of "well-defined" words without dots, and up to $\mathcal{O}(M)$ for the "undefined" words, to keep the recursion stack.

```java
// another similar solution but easier to understand
class WordDictionary {
    private WordDictionary[] children;
    boolean isEndOfWord;
    // Initialize your data structure here. 
    public WordDictionary() {
        children = new WordDictionary[26];
        isEndOfWord = false;
    }
    
    // Adds a word into the data structure. 
    public void addWord(String word) {
        WordDictionary curr = this;
        for(char c: word.toCharArray()){
            if(curr.children[c - 'a'] == null)
                curr.children[c - 'a'] = new WordDictionary();
            curr = curr.children[c - 'a'];
        }
        curr.isEndOfWord = true;
    }
    
    // Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. 
    public boolean search(String word) {
        WordDictionary curr = this;
        for(int i = 0; i < word.length(); ++i){
            char c = word.charAt(i);
            if(c == '.'){
                for(WordDictionary ch: curr.children)
                    if(ch != null && ch.search(word.substring(i+1))) return true;
                return false;
            }
            if(curr.children[c - 'a'] == null) return false;
            curr = curr.children[c - 'a'];
        }
        return curr != null && curr.isEndOfWord;
    }
}
```

