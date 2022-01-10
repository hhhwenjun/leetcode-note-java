# Hash Table

Materials refer to: https://leetcode.com/explore/learn/card/hash-table/182/practical-applications/1109/

## Basic Concept

* **Hash table**: a data structure which organizes data using hash functions in order to support quick insertion and search. Two different kinds of hash tables: **hash net** and **hash map**

  * **Hash set**: The hash set is one of the implementations of a **set** data structure to store no repeated values.(no duplicate elements)
  * **Hash map**: The hash map is one of the implementations of a **map** data structure to store **(key, value) pairs**. (no duplicate keys, but have duplicate values)
    * Provides a **fast access** to a value that is associated with a given key.

* **Hash function**: any function that can be used to **map** data of arbitrary size to fixed-size values.

  ![img](https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Hash_table_4_1_1_0_0_1_0_LL.svg/240px-Hash_table_4_1_1_0_0_1_0_LL.svg.png)

* Hash table supports quick insertion and search.

## Hash Table

### Hash Table Introduction

* **Key idea**: use a hash function to map keys to buckets. 
  * When we insert a new key, the hash function will decide which bucket the key should be assigned and the key will be stored in the corresponding bucket;
  * When we want to search for a key, the hash table will use the **same hash function** to find the corresponding bucket and search only in the specific bucket.

* Example:

  * `y = x % 5` as our hash function

  <img src="https://s3-lc-upload.s3.amazonaws.com/uploads/2018/02/20/screen-shot-2018-02-19-at-183537.png" alt="img" style="zoom: 33%;" />
  * **Insertion**: Parse the keys through hash function, map them to corresponding bucket
    * Input keys, assign to the bucket; e.g. 1987 to the bucket 2
  * **Search**: parse the keys through the same hash function and search only in the specific bucket
    * If we search for 23, will map 23 to 3 and search in bucket 3. And We find out that 23 is not in bucket 3 which means 23 is not in the hash table.
    * If we search for 1987, we will use the same hash function to map 1987 to 2. So we search in bucket 2 and we successfully find out 1987 in that bucket.

### Design a Hash Table

#### Hash Function

* **Hash function** is the **most important component** of hash table, it maps the key to bucket. 
* Hash function depends on:
  * The range of the key values
  * The number of buckets

![img](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/05/04/screen-shot-2018-05-04-at-145454.png)

* Try to assign the key to the bucket as **uniform** as you can
* A tradeoff between the **amount of buckets** and the **capacity of a bucket**

#### Collision

* A perfect hash function will be one-one mapping
* If more than one element is assigned to the same bucket, it is called **collision**(1987 and 2 in example)
* **Collision resolution** need to consider (capacity of bucket vs. number of keys):
  * How to organize the values in the same bucket?
  * What if too many values are assigned to the same bucket?
  * How to search a target value in a specific bucket?
* **Solution**: Assume the maximum number of keys is **N**
  * **N** is constant and small: use **array** to store keys in the same bucket.
  * **N** is variable or large: use **height-balanced binary search tree** to store keys in the same bucket.
* **Other strategies**:
  * **Separate Chaining**: for values with the same hash key, we keep them in a bucket, and each bucket is independent of each other.
  * **Open Addressing**: whenever there is a collision, we keep on probing on the main space with certain strategy until a free slot is found.
  * **2-Choice Hashing**: we use two hash functions rather than one, and we pick the generated address with fewer collision.

## Hash Set

* Hash set example
  * [Set API](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html)
  * [HashSet API](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html)

```java
import java.util.HashSet;
import java.util.Set;

public class HashSetExample {
	
	public static void main(String[] args) {
		
		// 1. initialize the hash set
		Set<Integer> hashSet = new HashSet<>();
		
		// 2. add a new key
		hashSet.add(3);
		hashSet.add(2);
		hashSet.add(1);
		
		// 3. remove the key
		hashSet.remove(2);
		
		// 4. check if the key is in the hash set
		if (!hashSet.contains(2)) {
			System.out.println("Key 2 is not in the hash set");
		}
		
		// 5. get the size of the hash set
		System.out.println("The size of hash set is :" + hashSet.size());
		
		// 6. iterate the hash set
		for (Integer i : hashSet) {
			System.out.println(i);
		}
		System.out.println("are in the hash set.");
		
		// 7. clear the hash set
		hashSet.clear();
		
		// 8. check if the hash set is empty
		if (hashSet.isEmpty()) {
			System.out.println("hash set is empty now!");
		}
	}
}
```

* Hash set does not have duplicates

  * **Apply hast set to find duplicates**

  ```java
  /** Template for using hast set to find duplicates.*/
  boolean findDuplicates(List<Type> keys){
      // Replace type with actual type of your key
      Set<Type> hashSet = new HashSet<>();
      for (Type key : keys){
          if (hashSet.contains(key)){
              return true;
          }
          hashSet.add(key);
      }
      return false;
  }
  ```

* Separate chaining:
  
  * Essentially, the primary storage underneath a Hash Set is a continuous memory as Array. Each element in this array corresponds to a bucket that stores the actual values.
  * Given a value, first we generate a key for the value via the *hash function*. The generated key serves as the index to locate the bucket.
  * Once the bucket is located, we then perform the desired operations on the bucket, such as add, remove and contains.

### Design HashSet (Easy #705)

**Leetcode problem**: Design a Hash Set without using any built-in hash table libraries.

Implement `MyHashSet` class:

- `void add(key)` Inserts the value `key` into the Hash Set.
- `bool contains(key)` Returns whether the value `key` exists in the Hash Set or not.
- `void remove(key)` Removes the value `key` in the Hash Set. If `key` does not exist in the Hash Set, do nothing.

```java
Input
["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
[[], [1], [2], [1], [3], [2], [2], [2], [2]]
Output
[null, null, null, true, false, null, true, null, false]

Explanation
MyHashSet myHashSet = new MyHashSet();
myHashSet.add(1);      // set = [1]
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(1); // return True
myHashSet.contains(3); // return False, (not found)
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(2); // return True
myHashSet.remove(2);   // set = [1]java
myHashSet.contains(2); // return False, (already removed)
```

#### Solution 1: Linked list as bucket

<img src="https://leetcode.com/problems/design-hashset/Figures/705/705_linked_list.png" alt="pic" style="zoom:50%;" />

* **Steps**
  * Determine hash key number, it is generally advisable to use a prime number as the base of modulo
  * Create bucket array based on number of hash key
  * Create hash function (build up connection between hash key and buckets)
  * Create bucket class, each bucket is a linked list
  * Implement the functions such as add, remove, contains
* **Design of buckets**
  * Array data structure take `O(N)` time complexity to remove or insert an element, so array is not suitable for bucket
  * Desired `O(1)` complexity, linked list has constant time complexity for insertion and deletion. 
* **Solution**

```java
class MyHashSet {
    private Bucket[] bucketArray;
    private int keyRange;
    
    /** Initialize your data structure here.*/
    public MyHashSet(){
        this.keyRange = 769;
        this.bucketArray = new Bucket[this.keyRange];
        for (int i = 0; i < this.keyRange; i++){
            this.bucketArray[i] = new Bucket();
        }
    }
    
    protected int _hash(int key){
        return (key % this.keyRange);
    }
    
    public void add(int key){
        int bucketIndex = this._hash(key);
        this.bucketArray[bucketIndex].insert(key);
    }
    
    public void remove(int key){
        int bucketIndex = this._hash(key);
        this.bucketArray[bucketIndex].delete(key);
    }
    
    /**Returns true if the set contains the specific element*/
    public boolean contains(int key){
        int bucketIndex = this._hash(key);
        return this.bucketArray[bucketIndex].exists(key);
    }
}

class Bucket{
    private LinkedList<Integer> container;
    
    public Bucket(){
        container = new LinkedList<Integer>();
    }
    
    public void insert(Integer key){
        int index = this.container.indexOf(key);
        if (index == -1){
            this.container.addFirst(key);//to the head of bucket
        }
    }
    
    public void delete(Integer key){
        this.container.remove(key);
    }
    
    public boolean exists(Integer key){
        int index = this.container.indexOf(key);
        return (index != -1);
    }
}
```

* **Complexity Analysis**:
  * Time complexity: `O(N/K)` where N is the number of all possible values and K is the number of predefined buckets
    * Assume the values are evenly distributed, thus we could consider that the average size of bucket is N/K
    * Since for each operation, in the worst case, we would need to scan the entire bucket, hence the time complexity is `O(N/K)`
  * Space complexity: `O(K + M)` where K is the number of predefined buckets, and M is the number of unique values that have been inserted into the Hash set.

#### Solution 2: Binary Search Tree(BST) as bucket

* The above approach need to scan the entire linkedlist to verify if a value already exists in the bucket
* Could maintain a sorted list as the bucket to faster the process, time complexity would become `O(log N)` for the lookup(BST)
* Applying **Façade design pattern**
  * A façade is an object that serves as a front-facing interface masking more complex underlying or structural code
  * This pattern hides the complexities of the larger system and provides a simpler interface to the client. 
  * Client could not access the subsystem classes directly.
* Others are the same except the bucket class

```java
class Bucket {
    private BSTree tree;
    
    public Bucket(){
        tree = new BSTree();
    }
    
    public void insert(Integer key){
        this.tree.root = this.tree.insertIntoBST(this.tree.root, key);
    }
    
    public void delete(Integer key){
        this.tree.root = this.tree.deleteNode(this.tree.root, key);
    }
    
    public boolean exists(Integer key){
        TreeNode node = this.tree.searchBST(this.tree.root, key);
        return (node != null);
    }
}

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int x){
        val = x;
    }
}

class BSTree {
    TreeNode root = null;
    
    public TreeNode searchBST(TreeNode root, int val){
        if (root == null || val == root.val){
            return root;
        }
        
        return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);//ternary conditional operator (if-else)
    }
    
    public TreeNode insertIntoBST(TreeNode root, int val){
        if (root == null){
            return new TreeNode(val);
        }
        
        if (val > root.val){
            //insert into the right subtree
            root.right = insertIntoBST(root.right, val);
        }
        else if (val == root.val){
            //skip the insertion
            return root;
        }
        else {
            //insert into the left subtree
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }
    
    /*
    * One step right and then always left
    */
    public int successor(TreeNode root){
        root = root.right;
        while(root.left != null){
            root = root.left;
        }
        return root.val;
    }
    
    /*
    * One step left and then always right
    */
    public int predecessor(TreeNode root){
        root = root.left;
        while(root.right != null){
            root = root.right;
        }
        return root.val;
    }
    
    public TreeNode deleteNode(TreeNode root, int key){
        if (root == null){
            return null;
        }
        
        // delete from the right subtree
        if (key > root.val){
            root.right = deleteNode(root.right, key);
        }
        
        // delete from the left subtree
        else if (key < root.val){
            root.left = deleteNode(root.left, key);
        }
        
        // delete the current node
        else {
            // the node is a leaf
            if (root.left == null && root.right == null){
                root = null;
            }
            // the node is not a leaf and has a right child
            else if (root.right != null){
                root.val = successor(root);
                root.right = deleteNode(root.right, root.val);
            }
            // the node is not a leaf, has no right child, and has a left child
            else {
                root.val = predecessor(root);
                root.left = deleteNode(root.left, root.val);
            }
        }
        return root;
    }
}
```

* **Consideration**:
  * Each bucket is a BST
  * Insert and search in a BST, left, right and current node
  * Delete node in a BST, left, right and current node. Has child and no child situation.
  * When deleting node, use what node to take the place of the root
  * Use recursion to solve the problem.

* **Complexity analysis**:
  * Time complexity : `O(log N/K)`
  * Space Complexity: `O(K + M)`

## Hash Map

* The `hash map` is one of the implementations of a `map` which is used to store `(key, value)` pairs.

```java
public class Main {
    public static void main(String[] args) {
        // 1. initialize a hash map
        Map<Integer, Integer> hashmap = new HashMap<>();
        // 2. insert a new (key, value) pair
        hashmap.putIfAbsent(0, 0);
        hashmap.putIfAbsent(2, 3);
        // 3. insert a new (key, value) pair or update the value of existed key
        hashmap.put(1, 1);
        hashmap.put(1, 2);
        // 4. get the value of specific key
        System.out.println("The value of key 1 is: " + hashmap.get(1));
        // 5. delete a key
        hashmap.remove(2);
        // 6. check if a key is in the hash map
        if (!hashmap.containsKey(2)) {
            System.out.println("Key 2 is not in the hash map.");
        }
        // 7. get the size of the hash map
        System.out.println("The size of hash map is: " + hashmap.size()); 
        // 8. iterate the hash map
        for (Map.Entry<Integer, Integer> entry : hashmap.entrySet()) {
            System.out.print("(" + entry.getKey() + "," + entry.getValue() + ") ");
        }
        System.out.println("are in the hash map.");
        // 9. clear the hash map
        hashmap.clear();
        // 10. check if the hash map is empty
        if (hashmap.isEmpty()) {
            System.out.println("hash map is empty now!");
        }
    }
}
```

### Design HashMap (Easy #706)

**Leetcode problem**: Design a HashMap without using any built-in hash table libraries.

Implement the `MyHashMap` class:

- `MyHashMap()` initializes the object with an empty map.
- `void put(int key, int value)` inserts a `(key, value)` pair into the HashMap. If the `key` already exists in the map, update the corresponding `value`.
- `int get(int key)` returns the `value` to which the specified `key` is mapped, or `-1` if this map contains no mapping for the `key`.
- `void remove(key)` removes the `key` and its corresponding `value` if the map contains the mapping for the `key`.

#### Solution 1: Modulo + Array

This localization process can be done in two steps:

- For a given key value, first we apply the hash function to generate a **hash key **(bucket index), which corresponds to the address in our main storage. With this hash key, we would find the *bucket* where the value should be stored.
- Now that we found the bucket, we simply iterate through the bucket to check if the desired <key, value> pair does exist.

* Use **Pair<U, V>**, [API doc](https://docs.oracle.com/javase/9/docs/api/javafx/util/Pair.html)
* Use either a **linkedlist** or **array** to implement the bucket data structure

![pic](https://leetcode.com/problems/design-hashmap/Figures/706/706_hashmap.png)

```java
class Pair<U,V> {
    public U first;
    public V second;
    
    public Pair(U first, V second){
        this.first = first;
        this.second = second;
    }
}

class Bucket{
    private List<Pair<Integer, Integer>> bucket;
    
    public Bucket(){
        this.bucket = new LinkedList<Pair<Integer, Integer>>();
    }
    
    public Integer get(Integer key) {
        for (Pair<Integer, Integer> pair : this.bucket){
            if (pair.first.equals(key)){
                return pair.second; //return pair value
            }
        }
        return -1; //not found
    }
    
    /** Hash map should not have duplicate key, check for duplication */
    public void update(Integer key, Integer value){
        boolean found = false;
        for (Pair<Integer, Integer> pair : this.bucket){
            if (pair.first.equals(key)){
                pair.second = value;
                found = true;
            }
        }
        if (!found){
            this.bucket.add(new Pair<Integer, Integer>(key, value));
        }
    }
    
    public void remove(Integer key){
        for (Pair<Integer, Integer> pair : this.bucket){
            if (pair.first.equals(key)){
                this.bucket.remove(pair);
                break;
            }
        }
    }
}

class MyHashMap {
    private int key_space;
    private List<Bucket> hash_table;
    
    /** Initialize data structure here. */
    public MyHashMap(){
        this.key_space = 2069;
        this.hash_table = new ArrayList<Bucket>();
        for (int i = 0; i < this.key_space; i++){
            this.hash_table.add(new Bucket());
        }
    }
    
    /** Value will always be non-negative*/
    public void put(int key, int value){
        int hash_key = key % this.key_space;
        this.hash_table.get(hash_key).update(key, value);
    }
    
    /**
    * Returns the value to which the specified key is mapped, or -1 if the map
    * contains no mapping for the key
    */
    public int get(int key){
        int hash_key = key % this.key_space;
        return this.hash_table.get(hash_key).get(key);
    }
    
    /** 
    * Removes the mapping of the specified value key if the map 
    * contains a mapping for the key
    */
    public void remove(int key){
        int hash_key = key % this.key_space;
        this.hash_table.get(hash_key).remove(key);
    }
}
```

* **Complexity Analysis**
  * Time complexity: `O(N/K)` 
  * Space complexity: `O(K + M)`

## Problem Solving Tips !!

* Be clear about the structure of hash table:

  * Two fields at least: key field and table field.
  * We have multiple keys, we **store** the **keys** in **bucket**
  * Hash set has no duplicated element, hash map can have duplicated values, **no duplicate keys**

* How to implement the structure:

  **Hash map/set -> Bucket -> Data Structure** (use other class)

  **Functions -> Functions -> Functions** (call helper functions in class)

  * **Bucket**: Need to have a **bucket class**
    * Hash set/map uses **bucket array**
    * **Search** the values in the bucket (implementation)
    * Time complexity and space complexity consideration
    * Array(easy to code but high complexity), Linked list, **BST**(recommended), etc.
  * **Hash function**: how should it be mapped
    * Transfer **keys** to **bucket index**
  * Hash set/map functions: add, remove, get, etc.
    * First get the bucket index by keys to locate the bucket
    * Call you helper method to locate the value inside the bucket and do the functions
  * Private helper functions/methods in the class
    * Locate the value in the bucket and do the operations

* **Functions(get, put, remove, etc)**

  * Functions inside the hash set/map(bucket array)
  * Functions inside the bucket class 
  * Functions inside the bucket class structure (Linked list, BST, etc)

* **BST**

  * Bucket class
  * Tree class
    * All functions need to locate the node, left, current, or right (**Recursion**)
    * Remove and add the node (**handle the root with leaf**)
  * Node class
    * Left and right

* Be familiar with common used structures and their built-in functions (check API doc)

  * [Pair API](https://docs.oracle.com/javase/9/docs/api/javafx/util/Pair.html)
  * [LinkedList API](https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html)
  * [ArrayList API](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html)

