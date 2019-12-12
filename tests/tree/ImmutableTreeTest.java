package tree;

import org.junit.jupiter.api.Test;
import tree.generators.*;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class ImmutableTreeTest {
    private TreeGenerator<Integer> treeGenerator;

    public ImmutableTreeTest() {
        treeGenerator = new TreeGenerator<>(Operations.getIntegerOperations(),
                new TreeGeneratorParams(6, 5,
                new Interval<Integer>(Operations.getIntegerOperations(), -10, 10)));
    }


    @Test
    void removeSubtree() {
        ImmutableTree<Integer> tree = TreeGenerator.getIntegerImmutableTree();

        var newTree = tree.removeSubtree(tree.getRoot());

        assertNull(newTree);

        newTree = tree.removeSubtree((ImmutableNode<Integer>)tree.getRoot().getChildren().toArray()[1]);
        assertEquals(newTree.getRoot().getChildren().size(), 1);
        assertEquals(((ImmutableNode<Integer>)newTree.getRoot().getChildren().toArray()[0]).getValue(), 2);

        var deletedNode = ((ImmutableNode<Integer>)newTree.getRoot().getChildren().toArray()[0]);
        deletedNode = ((ImmutableNode<Integer>)deletedNode.getChildren().toArray()[0]);

        newTree = newTree.removeSubtree(deletedNode);

        var testNode = (ImmutableNode<Integer>)newTree.getRoot().getChildren().toArray()[0];

        assertEquals(testNode.getChildren().size(), 2);
        assertEquals(((ImmutableNode<Integer>)testNode.getChildren().toArray()[1]).getValue(), -5);
        assertEquals(((ImmutableNode<Integer>)testNode.getChildren().toArray()[0]).getValue(), 1);
    }

    @Test
    void maximize() {
        ImmutableTree<Integer> tree = TreeGenerator.getIntegerImmutableTree();

        tree = (ImmutableTree<Integer>)tree.maximize();
        tree.calculateSizeAndSum();

        assertEquals(tree.getSum(), 10);
    }

    @Test
    void testMaximize() {
        AbstractTree<Integer> tree = TreeGenerator.getIntegerImmutableTree();

        tree.calculateSizeAndSum();
        tree = tree.maximize(1);
        tree.calculateSizeAndSum();

        assertEquals(tree.getSum(), 5);
    }

    @Test
    void findDeepestNegativeSubtree() {
        ImmutableTree<Integer> tree = TreeGenerator.getIntegerImmutableTree();
        Node<Integer> minSubtreeRoot = tree.findDeepestNegativeSubtree();

        assertEquals(minSubtreeRoot.getValue(), -3);
    }
}