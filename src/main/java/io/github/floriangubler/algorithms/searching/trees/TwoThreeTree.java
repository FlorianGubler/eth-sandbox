package io.github.floriangubler.algorithms.searching.trees;

/**
 * A simple implementation of a two-three tree for a generic comparable object.
 * Each node in the tree contains either a value or references to its 2 or 3 child nodes with separators.
 * The two-three tree condition is that each node can have either two or three children
 *
 * @author Florian Gubler
 */
public class TwoThreeTree<T extends Comparable<T>> {

    /**
     * Represents any node in the two-three tree
     */
    public static class Node<T extends Comparable<T>> {

        SeparatorNode<T> parent;

        public Node(SeparatorNode<T> parent) {
            this.parent = parent;
        }

        /**
         * Represents a separator node in the two-three tree. A separator node can have either
         * two or three child nodes. For two child nodes, it has one separator, and for three child nodes,
         * it has two separators.
         * Condition for a two-node: The left child node's value is less than the separator value,
         * and the right child node's value is greater than the separator value.
         * Condition for a three-node: The left child node's value is less than the first separator value,
         * the middle child node's value is between the first and second separator values,
         * and the right child node's value is greater than the second separator value.
         */
        public static class SeparatorNode<T extends Comparable<T>> extends Node<T> {
            T firstSeparator;
            T secondSeparator;
            Node<T> left;
            Node<T> middle;
            Node<T> right;

            public SeparatorNode(SeparatorNode<T> parent, T firstSeparator, T secondSeparator, Node<T> left, Node<T> middle, Node<T> right) {
                super(parent);
                this.firstSeparator = firstSeparator;
                this.secondSeparator = secondSeparator;
                this.left = left;
                this.middle = middle;
                this.right = right;
            }

            /**
             * Returns the number of child nodes for this separator node.
             *
             * @return The number of child nodes (2 for a two-node, 3 for a three-node).
             */
            public int getChildCount() {
                if (secondSeparator == null) {
                    return 2; // Two-node
                } else {
                    return 3; // Three-node
                }
            }

            /**
             * Returns the next child node to traverse based on the given value.
             * This method determines which child node to follow based on the value and the separators.
             * The 2-3 tree property defines:
             * - For a two-node: left child if value < firstSeparator, right child if value >= firstSeparator.
             * - For a three-node: left child if value < firstSeparator, middle child if firstSeparator <= value < secondSeparator, right child if value >= secondSeparator.
             */
            public Node<T> getNextNode(T value){
                // If the second separator is null, it means this is a two-node
                if(secondSeparator == null) {
                    if(value.compareTo(firstSeparator) < 0) {
                        return left;
                    } else {
                        return middle;
                    }
                // If the second separator is not null, it means this is a three-node
                } else {
                    if(value.compareTo(firstSeparator) < 0) {
                        return left;
                    } else if(value.compareTo(secondSeparator) < 0) {
                        return middle;
                    } else {
                        return right;
                    }
                }
            }

            /**
             * Returns the sibling node of this separator node.
             * A sibling node is a node that shares the same parent as this node.
             *
             * @return The sibling seperator node or null if this node is the root (otherwise node must have a parent and
             * thus a sibling as every parent must have 2 or 3 children).
             */
            public SeparatorNode<T> getSibling() {
                if(this.parent == null){
                    return null; // Root node has no sibling
                // Return children which is not this node (this.parent.left, this.parent.middle, this.parent.right)
                // Must be a separator as it is on the same tree level as this node which is a separator node
                } else if (this.parent.left == this) {
                    return (Node.SeparatorNode<T>) this.parent.middle;
                } else if (this.parent.middle == this) {
                    return (Node.SeparatorNode<T>) this.parent.left;
                } else {
                    return (Node.SeparatorNode<T>) this.parent.middle;
                }
            }
        }

        /**
         * Represents a leaf node in the two-three tree. A leaf node contains a value and does not have any child nodes.
         * Leaf nodes are the terminal nodes of the tree and do not have any separators or child nodes.
         */
        public static class LeafNode<T extends Comparable<T>> extends Node<T> {
            T value;

            public LeafNode(SeparatorNode<T> parent, T value) {
                super(parent);
                this.value = value;
            }
        }
    }

    Node<T> root;

    public TwoThreeTree() {
        this.root = null;
    }

    @SafeVarargs
    public TwoThreeTree(T... values) {
        for (T value : values) {
            insert(value);
        }
    }

    /**
     * Searches for a value in the two-three tree.
     *
     * @param value The value to be searched for.
     * @return The node leaf containing the value if found, otherwise the
     * separator node where the value would be if it were in the tree.
     */
    public Node<T> search(T value) {
        if (root == null) {
            return null;
        }
        return search(root, value);
    }

    /**
     * Searches for a value in the two-three tree starting from a given node.
     *
     * @param node The node to start the search from.
     * @param value The value to be searched for.
     * @return The leaf node containing the value if found, otherwise the separator node
     * where the value would be if it were in the tree.
     */
    private Node<T> search(Node<T> node, T value) {
        if (node instanceof Node.LeafNode<T> leafNode) {
            if (leafNode.value.compareTo(value) == 0) {
                return leafNode;
            } else {
                return leafNode.parent;
            }
        } else {
            return search(((Node.SeparatorNode<T>) node)
                    .getNextNode(value), value);
        }
    }

    /**
     * Inserts a value into the two-three tree.
     *
     * @param value The value to be inserted.
     * @throws IllegalArgumentException if the value already exists in the tree.
     */
    public void insert(T value) {
        // Tree is empty, create a new leaf node as the root
        if (root == null) {
            root = new Node.LeafNode<>(null, value);
        // Tree has only one leaf node, create a new separator node as the root
        } else if (root instanceof Node.LeafNode<T> leafNode) {
            // Create the new root and leaf nodes based on the comparison of the existing leaf
            // node's value and the new value to be inserted.
            Node.LeafNode<T> newLeaf = new Node.LeafNode<>(null, value);
            if (leafNode.value.compareTo(value) < 0) {
                root = new Node.SeparatorNode<>(null, value, null, leafNode, newLeaf, null);
            } else if (leafNode.value.compareTo(value) > 0) {
                root = new Node.SeparatorNode<>(null, leafNode.value, null, newLeaf, leafNode, null);
            } else {
                throw new IllegalArgumentException("Value already exists in the tree: " + leafNode.value);
            }
            ((Node.SeparatorNode<T>) root).left.parent = (Node.SeparatorNode<T>) root;
            ((Node.SeparatorNode<T>) root).middle.parent = (Node.SeparatorNode<T>) root;
        // Tree has more than one node, search for the appropriate position to insert the new value
        } else {
            Node<T> searchResult = search(root, value);
            // If the search result is a leaf node, it means the value already exists in the tree, so we throw an exception.
            if(searchResult instanceof Node.LeafNode<T> leafNode) {
                throw new IllegalArgumentException("Value already exists in the tree: " + leafNode.value);
            // If the search result is a separator node, we insert the new value below it.
            } else if(searchResult instanceof Node.SeparatorNode<T> separatorNode) {
                // If the node has two children, we can add the new value directly.
                if(separatorNode.getChildCount() == 2) {
                    addToTwoNode(separatorNode, value);
                } else {
                    splitAndPropagate(separatorNode, value);
                }
            }
            // Else should never happen as the search result
            // should always be a leaf or separator node.
        }
    }

    /**
     * Adds a leaf to a separator node that currently has two children.
     *
     * @param node The two-node receiving the new leaf.
     * @param value The value to be inserted.
     */
    private void addToTwoNode(Node.SeparatorNode<T> node, T value) {
        // A value smaller than the left child becomes the new left child.
        if (value.compareTo(minimumValue(node.left)) < 0) {
            node.right = node.middle;
            node.middle = node.left;
            node.left = new Node.LeafNode<>(node, value);
            node.secondSeparator = node.firstSeparator;
            node.firstSeparator = value;
            return;
        }

        // A value between the left and middle children becomes the new middle
        // child. The old middle child moves to the right.
        if (value.compareTo(node.firstSeparator) < 0) {
            node.right = node.middle;
            node.middle = new Node.LeafNode<>(node, value);
            node.secondSeparator = node.firstSeparator;
            node.firstSeparator = value;
            return;
        }

        // Otherwise the new value becomes the right child.
        node.right = new Node.LeafNode<>(node, value);
        node.secondSeparator = value;
    }

    /**
     * @param node The full separator node receiving the new value.
     * @param value The value to be inserted.
     */
    private void splitAndPropagate(Node.SeparatorNode<T> node, T value) {
        // A full node has three children. Inserting one more leaf temporarily
        // gives four ordered children. Split them into two two-nodes without
        // sorting: compare the value with the node's two separators.
        Node.LeafNode<T> newLeaf = new Node.LeafNode<>(null, value);

        Node.SeparatorNode<T> leftChild;
        Node.SeparatorNode<T> rightChild;

        if (value.compareTo(node.firstSeparator) < 0) {
            // The order is: new value, old left, old middle, old right.
            // The first two children form the left split node.
            leftChild = makeSeparator(null, newLeaf, node.left);
            rightChild = makeSeparator(null, node.middle, node.right);
        } else if (value.compareTo(node.secondSeparator) < 0) {
            // The order is: old left, new value, old middle, old right.
            leftChild = makeSeparator(null, node.left, newLeaf);
            rightChild = makeSeparator(null, node.middle, node.right);
        } else {
            // The order is: old left, old middle, old right, new value.
            // The last two children form the right split node.
            leftChild = makeSeparator(null, node.left, node.middle);
            rightChild = makeSeparator(null, node.right, newLeaf);
        }

        // Replace the full node in its parent with the two split nodes. If the
        // parent is full too, replaceAndPropagate continues at the next level.
        replaceAndPropagate(node.parent, node, leftChild, rightChild);
    }

    /**
     * @param parent The parent of the node being replaced, or {@code null} when
     * the split node is the root.
     * @param oldChild The child being replaced.
     * @param leftChild The left node produced by the split.
     * @param rightChild The right node produced by the split.
     */
    private void replaceAndPropagate(Node.SeparatorNode<T> parent,
                                     Node.SeparatorNode<T> oldChild,
                                     Node.SeparatorNode<T> leftChild,
                                     Node.SeparatorNode<T> rightChild) {
        while (true) {
            // Case 1: parent == null means the split reached the root. Since
            // the root has no parent, create a new root for the two split
            // nodes. The new root separator is the minimum of rightChild.
            if (parent == null) {
                root = new Node.SeparatorNode<>(
                        null,
                        minimumValue(rightChild),
                        null,
                        leftChild,
                        rightChild,
                        null);

                leftChild.parent = (Node.SeparatorNode<T>) root;
                rightChild.parent = (Node.SeparatorNode<T>) root;
                return;
            }

            // Case 2: a two-node has room for one more child. Replacing
            // oldChild with leftChild and rightChild creates a valid three-node.
            if (parent.getChildCount() == 2) {
                if (parent.left == oldChild) {
                    // oldChild was left, so the resulting order is:
                    // leftChild, rightChild, old middle.
                    Node<T> oldMiddle = parent.middle;
                    parent.left = leftChild;
                    parent.middle = rightChild;
                    parent.right = oldMiddle;
                } else {
                    // oldChild was middle, so the resulting order is:
                    // old left, leftChild, rightChild.
                    parent.middle = leftChild;
                    parent.right = rightChild;
                }

                // Separators describe the minimum values of the middle and
                // right children. Update them after changing the children.
                parent.firstSeparator = minimumValue(parent.middle);
                parent.secondSeparator = minimumValue(parent.right);

                // All three children now belong directly to this parent.
                parent.left.parent = parent;
                parent.middle.parent = parent;
                parent.right.parent = parent;
                return;
            }

            // Case 3: a three-node cannot accept another child. Its three
            // existing children plus the two replacement nodes form four
            // ordered children. Split those four children into two two-nodes.
            Node.SeparatorNode<T> newLeft;
            Node.SeparatorNode<T> newRight;

            if (parent.left == oldChild) {
                // Ordered children: leftChild, rightChild, old middle, old right.
                newLeft = makeSeparator(null, leftChild, rightChild);
                newRight = makeSeparator(null, parent.middle, parent.right);
            } else if (parent.middle == oldChild) {
                // Ordered children: old left, leftChild, rightChild, old right.
                newLeft = makeSeparator(null, parent.left, leftChild);
                newRight = makeSeparator(null, rightChild, parent.right);
            } else {
                // Ordered children: old left, old middle, leftChild, rightChild.
                newLeft = makeSeparator(null, parent.left, parent.middle);
                newRight = makeSeparator(null, leftChild, rightChild);
            }

            // The parent has now been split. Continue one level upward:
            // oldChild becomes the full parent that was just split, parent
            // becomes its parent, and the two new split nodes are propagated.
            oldChild = parent;
            parent = parent.parent;
            leftChild = newLeft;
            rightChild = newRight;
        }
    }

    /**
     * Creates a new separator node with the given left and middle children.
     *
     * @param parent The parent of the new separator node.
     * @param left The left child of the new separator node.
     * @param middle The middle child of the new separator node.
     * @return The newly created separator node.
     */
    private Node.SeparatorNode<T> makeSeparator(Node.SeparatorNode<T> parent,
                                                 Node<T> left,
                                                 Node<T> middle) {
        Node.SeparatorNode<T> separator = new Node.SeparatorNode<>(
                parent, minimumValue(middle), null, left, middle, null);
        left.parent = separator;
        middle.parent = separator;
        return separator;
    }

    /**
     * Returns the minimum value in the subtree rooted at the given node.
     *
     * @param node The root of the subtree.
     * @return The minimum value in the subtree.
     */
    private T minimumValue(Node<T> node) {
        if (node instanceof Node.LeafNode<T> leaf) {
            return leaf.value;
        }
        return minimumValue(((Node.SeparatorNode<T>) node).left);
    }
}
