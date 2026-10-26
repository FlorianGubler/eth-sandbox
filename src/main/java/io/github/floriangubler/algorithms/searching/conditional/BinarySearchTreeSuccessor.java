package io.github.floriangubler.algorithms.searching.conditional;

public class BinarySearchTreeSuccessor {

    public static void main(String[] args) {
        // Create a binary search tree
        Node root = new Node(20);
        root.left = new Node(10);
        root.right = new Node(30);
        root.left.parent = root;
        root.right.parent = root;
        root.left.left = new Node(5);
        root.left.right = new Node(15);
        root.left.left.parent = root.left;
        root.left.right.parent = root.left;
        root.right.right = new Node(40);
        root.right.right.parent = root.right;

        // Test the successor function
        System.out.println(successor(root.left)); // Should print 15
        System.out.println(successor(root)); // Should print 30
        System.out.println(successor(root.right)); // Should print 40
        System.out.println(successor(root.right.right)); // Should print -1 (no successor)
    }

    /**
     * Returns the key of the successor of v, or -1 if v has the largest key.
     * This runs in O(h) time, where h is the height of the tree (O(log n) for balanced trees).
     *
     * @param v The node to find the successor of.
     * @return The key of the successor of v, or -1 if v has the largest key.
     */
    public static int successor(BinarySearchTreeSuccessor.Node v) {
        // Find the root element
        Node root = v;
        while(root.parent != null){
            root = root.parent;
        }
        // Start with the current element as root
        Node current = root;
        // Set result to an absurd hight value
        int result = Integer.MAX_VALUE;
        // Loop until we reach end of branch to search
        while(current != null){
            // If the current key is greater than v's key, it could be a successor
            if (current.key > v.key) {
                // Update result to the smaller of the current key and the previous result
                // (as we search for the smallest y greater than v's key)
                result = Math.min(result, current.key);
                // Move to the left child to find a potentially smaller successor
                current = current.left;
            } else {
                // Move to the right child to find a larger key
                current = current.right;
            }
        }
        // If result is still Integer.MAX_VALUE, it means there was no successor found
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    // A node of a binary search tree.
    public static class Node {
        int key;
        Node left, right, parent;

        Node(int key) { this.key = key; }
    }
}
