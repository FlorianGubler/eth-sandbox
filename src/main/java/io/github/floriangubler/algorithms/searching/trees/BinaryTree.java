package io.github.floriangubler.algorithms.searching.trees;

/**
 * A simple implementation of a binary tree for a generic comparable object.
 * Each node in the tree contains a value and references to its left and right child nodes.
 * The binary tree condition is that the left child node's value is less than the parent
 * node's value, and the right child node's value is greater than to the parent node's value.
 * Note: Duplicate values are not allowed in this implementation.
 *
 * @author Florian Gubler
 */
public class BinaryTree<T extends Comparable<T>> {

    /**
     * Represents a node in the binary tree. Each node contains an integer value and references
     * to its left and right child nodes.
     */
    public static class Node<T extends Comparable<T>> {
        T value;
        Node<T> left;
        Node<T> right;

        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * The root node of the binary tree.
     */
    public Node<T> root;

    /**
     * Constructs an empty binary tree.
     */
    public BinaryTree() {}

    /**
     * Constructs a binary tree from an array of values.
     *
     * @param values The array of values to be inserted into the binary tree.
     */
    @SafeVarargs
    public BinaryTree(T... values){
        for(T value : values) {
            insert(value);
        }
    }

    /**
     * Inserts a value into the binary tree.
     *
     * @param value The value to be inserted.
     * @throws IllegalArgumentException if the value already exists in the tree.
     */
    public void insert(T value) {
        if(root == null) {
            root = new Node<T>(value, null, null);
        } else {
            insert(root, value);
        }
    }

    /**
     * Inserts a value into the binary tree with a given node as the starting point.
     *
     * @param node The start node for the insertion.
     * @param value The value to be inserted.
     * @throws IllegalArgumentException if the value already exists in the tree.
     */
    private void insert(Node<T> node, T value) {
        // Compare the value to be inserted with the current node's value
        if(value.compareTo(node.value) < 0) {
            if(node.left == null) {
                node.left = new Node<T>(value, null, null);
            } else {
                insert(node.left, value);
            }
        } else {
            if(node.value.compareTo(value) == 0){
                throw new IllegalArgumentException("Value " + value + " already exists in the tree.");
            } else if(node.right == null) {
                node.right = new Node<T>(value, null, null);
            } else {
                insert(node.right, value);
            }
        }
    }

    /**
     * Searches for a value in the binary tree.
     *
     * @param value The value to be searched for.
     * @return The value if found, or null if not found.
     */
    public T search(T value) {
        return search(root, value);
    }

    /**
     * Searches for a value in the binary tree.
     *
     * @param value The value to be searched for.
     * @return The value if found, or null if not found.
     */
    private T search(Node<T> node, T value) {
        if(node == null) {
            return null; // Value not found
        }
        if(node.value.compareTo(value) == 0) {
            return node.value; // Value found
        }
        if(value.compareTo(node.value) < 0) {
            return search(node.left, value); // Search in the left subtree
        } else {
            return search(node.right, value); // Search in the right subtree
        }
    }

    /**
     * Deletes a value from the binary tree.
     *
     * @param value The value to be deleted.
     * @throws IllegalArgumentException if the value is not found in the tree.
     */
    public void delete(T value) {
        this.root = delete(root, value);
    }

    /**
     * Deletes a value from the binary tree with a given node as the starting point.
     *
     * @param node The start node for the deletion.
     * @param value The value to be deleted.
     * @return The updated node after deletion.
     * @throws IllegalArgumentException if the value is not found in the tree.
     */
    private Node<T> delete(Node<T> node, T value) {
        if(node == null) {
            throw new IllegalArgumentException("Value " + value + " not found in the tree.");
        }
        if(value.compareTo(node.value) < 0) {
            node.left = delete(node.left, value); // Search in the left subtree
        } else if(value.compareTo(node.value) > 0) {
            node.right = delete(node.right, value); // Search in the right subtree
        } else {
            // Node to be deleted found
            if(node.left == null && node.right == null) {
                return null; // Node has no children
            } else if(node.left == null) {
                return node.right; // Node has only right child
            } else if(node.right == null) {
                return node.left; // Node has only left child
            } else {
                // Node has two children, find the minimum value in the right subtree
                T minValue = findMin(node.right);
                // Replace the deleted node value with the minimum value
                node.value = minValue;
                // Delete the minimum value from the right subtree
                node.right = delete(node.right, minValue); // Delete the minimum value from the right subtree
            }
        }
        return node;
    }

    /**
     * Finds the minimum value in a subtree rooted at the given node.
     *
     * @param right The root node of the subtree.
     * @return The minimum value in the subtree.
     */
    private T findMin(Node<T> right) {
        while(right.left != null) {
            right = right.left; // Traverse to the leftmost node (minimum value)
        }
        return right.value; // Return the minimum value
    }
}
