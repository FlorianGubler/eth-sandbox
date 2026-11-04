package io.github.floriangubler.algorithms.searching.trees;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TwoThreeTreeTest {

    @Test
    void emptyConstructorCreatesEmptyTree() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>();

        assertNull(tree.root);
        assertNull(tree.search(1));
    }

    @Test
    void valuesConstructorInsertsAllValues() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>(5, 3, 7, 1, 4, 6, 8);

        assertContainsAll(tree, 1, 3, 4, 5, 6, 7, 8);
        assertTreeInvariants(tree.root);
    }

    @Test
    void insertCreatesRootLeafAndThenTwoNode() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>();

        tree.insert(5);
        assertEquals(5, assertInstanceOf(TwoThreeTree.Node.LeafNode.class, tree.root).value);

        tree.insert(3);
        TwoThreeTree.Node.SeparatorNode<Integer> root =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, tree.root);
        assertEquals(2, root.getChildCount());
        assertEquals(5, root.firstSeparator);
        assertEquals(5, leafValue(root.middle));
        assertTreeInvariants(tree.root);
    }

    @Test
    void insertAddsValuesToBothSidesOfTwoNode() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>(5, 3);

        tree.insert(1);
        tree.insert(7);

        TwoThreeTree.Node.SeparatorNode<Integer> root =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, tree.root);
        assertEquals(2, root.getChildCount());
        TwoThreeTree.Node.SeparatorNode<Integer> left =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, root.left);
        TwoThreeTree.Node.SeparatorNode<Integer> right =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, root.middle);
        assertEquals(1, leafValue(left.left));
        assertEquals(3, leafValue(left.middle));
        assertEquals(5, leafValue(right.left));
        assertEquals(7, leafValue(right.middle));
        assertContainsAll(tree, 1, 3, 5, 7);
        assertTreeInvariants(tree.root);
    }

    @Test
    void insertRejectsDuplicateValues() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>(5);

        assertThrows(IllegalArgumentException.class, () -> tree.insert(5));
    }

    @Test
    void searchReturnsLeafForPresentValueAndSeparatorForMissingValue() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>(5, 3, 7);

        assertEquals(3, leafValue(tree.search(3)));
        assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, tree.search(4));
        assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, tree.search(8));
    }

    @Test
    void separatorNodeReportsChildrenAndRoutesValues() {
        TwoThreeTree.Node.LeafNode<Integer> left = new TwoThreeTree.Node.LeafNode<>(null, 1);
        TwoThreeTree.Node.LeafNode<Integer> middle = new TwoThreeTree.Node.LeafNode<>(null, 3);
        TwoThreeTree.Node.LeafNode<Integer> right = new TwoThreeTree.Node.LeafNode<>(null, 5);
        TwoThreeTree.Node.SeparatorNode<Integer> node =
                new TwoThreeTree.Node.SeparatorNode<>(null, 3, 5, left, middle, right);

        assertEquals(3, node.getChildCount());
        assertSame(left, node.getNextNode(0));
        assertSame(middle, node.getNextNode(3));
        assertSame(right, node.getNextNode(5));
    }

    @Test
    void siblingReturnsTheOtherChildAtEveryPosition() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>(1, 2, 3, 4, 5, 6, 7);
        TwoThreeTree.Node.SeparatorNode<Integer> root =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, tree.root);
        TwoThreeTree.Node.SeparatorNode<Integer> left =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, root.left);
        TwoThreeTree.Node.SeparatorNode<Integer> middle =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, root.middle);
        TwoThreeTree.Node.SeparatorNode<Integer> right =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, root.right);

        assertNull(root.getSibling());
        assertSame(middle, left.getSibling());
        assertSame(left, middle.getSibling());
        assertSame(middle, right.getSibling());
    }

    @Test
    void splitPromotesNodesAndPreservesAllValuesAcrossLevels() {
        TwoThreeTree<Integer> tree = new TwoThreeTree<>();

        for (int value = 1; value <= 40; value++) {
            tree.insert(value);
        }

        assertContainsAll(tree, range(1, 40));
        assertTreeInvariants(tree.root);
    }

    private static void assertContainsAll(TwoThreeTree<Integer> tree, int... values) {
        for (int value : values) {
            TwoThreeTree.Node<Integer> result = tree.search(value);
            assertNotNull(result, "search returned null for " + value);
            assertEquals(value, leafValue(result));
        }
    }

    private static int[] range(int first, int last) {
        int[] values = new int[last - first + 1];
        for (int i = 0; i < values.length; i++) {
            values[i] = first + i;
        }
        return values;
    }

    private static int leafValue(TwoThreeTree.Node<Integer> node) {
        TwoThreeTree.Node.LeafNode<Integer> leaf =
                assertInstanceOf(TwoThreeTree.Node.LeafNode.class, node);
        return leaf.value;
    }

    private static void assertTreeInvariants(TwoThreeTree.Node<Integer> node) {
        if (node instanceof TwoThreeTree.Node.LeafNode<Integer>) {
            return;
        }

        TwoThreeTree.Node.SeparatorNode<Integer> separator =
                assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, node);
        assertEquals(separator.getChildCount() == 3 ? 3 : 2,
                separator.secondSeparator == null ? 2 : 3);
        assertSame(separator, separator.left.parent);
        assertSame(separator, separator.middle.parent);
        assertOrdered(separator.left, separator.middle);

        if (separator.getChildCount() == 3) {
            assertSame(separator, separator.right.parent);
            assertOrdered(separator.middle, separator.right);
        } else {
            assertNull(separator.right);
        }

        assertTreeInvariants(separator.left);
        assertTreeInvariants(separator.middle);
        if (separator.right != null) {
            assertTreeInvariants(separator.right);
        }
    }

    private static void assertOrdered(TwoThreeTree.Node<Integer> left,
                                      TwoThreeTree.Node<Integer> right) {
        assertTrue(minimum(left) < minimum(right));
    }

    private static int minimum(TwoThreeTree.Node<Integer> node) {
        if (node instanceof TwoThreeTree.Node.LeafNode<Integer> leaf) {
            return leaf.value;
        }
        return minimum(assertInstanceOf(TwoThreeTree.Node.SeparatorNode.class, node).left);
    }
}
