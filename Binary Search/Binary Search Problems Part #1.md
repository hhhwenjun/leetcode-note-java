# Binary Search Problems Part #1

## Closest Binary Search Tree Value(Easy #270)

**Question**: Given the `root` of a binary search tree and a `target` value, return *the value in the BST that is closest to the* `target` 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/03/12/closest1-1-tree.jpg)

```
Input: root = [4,2,5,1,3], target = 3.714286
Output: 4
```

**Example 2:**

```
Input: root = [1], target = 4.428571
Output: 1
```

**Constraints:**

-   The number of nodes in the tree is in the range `[1, 104]`.
-   `0 <= Node.val <= 109`
-   `-109 <= target <= 109`

### My Solution

*   In order recursively find the distance between the value and the target
*   Compare the distance and record the root value

```java
class Solution {
    int value = 0;
    public int closestValue(TreeNode root, double target) {
        double distance = Double.POSITIVE_INFINITY;
        findValue(root, target, distance);
        return value;
    }
    // recursively find the value near target
    public double findValue(TreeNode root, double target, double distance){
        if (root == null){
            return distance;
        }
        distance = findValue(root.left, target, distance);
        if ((double)Math.abs(target - root.val) < distance){
            distance = (double)Math.abs(target - root.val);
            value = root.val;
        }
        distance = findValue(root.right, target, distance);
        return distance;
    }
}
```

### Standard Solution

#### Solution #1 Recursive Inorder + Linear Search

*   Recursively use the list to record all the value
*   Create a customized comparator for comparison

```java
public void inorder(TreeNode root, List<Integer> nums){
    if (root == null) return;
    inorder(root.left, nums);
    nums.add(root.val);
    inorder(root.right, nums);
}
public int closestValue(TreeNode root, double target){
    List<Integer> nums = new ArrayList();
    inorder(root, nums); // put all values to the list
    return Collections.min(nums, new Comparator<Integer>(){
        @Override
        public int compare(Integer o1, Integer o2){
            return Math.abs(o1 - target) < Math.abs(o2 - target) ? -1 : 1;
        }
    });
}
```

-   Time complexity: $\mathcal{O}(N)$ because to build in order traversal and then to perform linear search takes linear time.
-   Space complexity: $\mathcal{O}(N)$ to keep in order traversal.

#### Solution #2 Binary Search, O(H) time

*   Use the properties of the binary search tree

<img src="https://leetcode.com/problems/closest-binary-search-tree-value/Figures/270/binary.png" alt="pic" style="zoom:50%;" />

```java
public int closestValue(TreeNode root, double target){
    int val, closest = root.val;
    while (root != null){
        val = root.val;
        closest = Math.abs(val - target) < Math.abs(closest - target) ? val : closest;
        root = target < root.val ? root.left : root.right;
    }
    return closest;
}
```

-   Time complexity: $\mathcal{O}(H)$ since here one goes from root down to a leaf.
-   Space complexity: $\mathcal{O}(1)$.