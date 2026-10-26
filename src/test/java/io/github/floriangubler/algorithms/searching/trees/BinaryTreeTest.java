package io.github.floriangubler.algorithms.searching.trees;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BinaryTreeTest {

    @Test
    void emptyConstructorCreatesEmptyTree() {
        BinaryTree<Integer> tree = new BinaryTree<>();

        assertNull(tree.root);
    }

    @Test
    void valuesConstructorBuildsBinarySearchTree() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 7, 2, 4, 6, 8);

        assertEquals(5, tree.root.value);
        assertEquals(3, tree.root.left.value);
        assertEquals(7, tree.root.right.value);
        assertEquals(2, tree.root.left.left.value);
        assertEquals(4, tree.root.left.right.value);
        assertEquals(6, tree.root.right.left.value);
        assertEquals(8, tree.root.right.right.value);
    }

    @Test
    void insertAddsValuesToCorrectBranches() {
        BinaryTree<Integer> tree = new BinaryTree<>();

        tree.insert(5);
        tree.insert(3);
        tree.insert(7);

        assertEquals(5, tree.root.value);
        assertEquals(3, tree.root.left.value);
        assertEquals(7, tree.root.right.value);
    }

    @Test
    void insertRejectsDuplicateValues() {
        BinaryTree<Integer> tree = new BinaryTree<>(5);

        assertThrows(IllegalArgumentException.class, () -> tree.insert(5));
    }

    @Test
    void searchReturnsStoredValueWhenPresent() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 7, 2, 4, 6, 8);

        assertEquals(2, tree.search(2));
        assertEquals(5, tree.search(5));
        assertEquals(8, tree.search(8));
    }

    @Test
    void searchReturnsNullWhenValueIsMissing() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 7);

        assertNull(tree.search(10));
    }

    @Test
    void deleteRemovesLeafNode() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 7);

        tree.delete(3);

        assertNull(tree.search(3));
        assertNull(tree.root.left);
    }

    @Test
    void deleteRemovesNodeWithOnlyLeftChild() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 2, 7);

        tree.delete(3);

        assertEquals(2, tree.root.left.value);
        assertNull(tree.search(3));
    }

    @Test
    void deleteRemovesNodeWithOnlyRightChild() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 4, 7);

        tree.delete(3);

        assertEquals(4, tree.root.left.value);
        assertNull(tree.search(3));
    }

    @Test
    void deleteRemovesNodeWithTwoChildren() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 7, 2, 4, 6, 8);

        tree.delete(3);

        assertEquals(4, tree.root.left.value);
        assertNull(tree.search(3));
        assertEquals(2, tree.root.left.left.value);
    }

    @Test
    void deleteCanRemoveRoot() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 7);

        tree.delete(5);

        assertEquals(7, tree.root.value);
        assertEquals(3, tree.root.left.value);
    }

    @Test
    void deleteRejectsMissingValue() {
        BinaryTree<Integer> tree = new BinaryTree<>(5, 3, 7);

        assertThrows(IllegalArgumentException.class, () -> tree.delete(10));
    }

    @Test
    void deleteMakesTreeEmptyWhenDeletingOnlyValue() {
        BinaryTree<Integer> tree = new BinaryTree<>(5);

        tree.delete(5);

        assertNull(tree.root);
    }
}
