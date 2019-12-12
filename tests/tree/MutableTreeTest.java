package tree;

import org.junit.jupiter.api.Test;

import tree.generators.*;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;


class MutableTreeTest {
    private TreeGenerator<Integer> treeGenerator;
    private Operations<Integer> operations = Operations.getIntegerOperations();

    public MutableTreeTest() {
        treeGenerator = new TreeGenerator<>(Operations.getIntegerOperations(),
                new TreeGeneratorParams(6, 5,
                        new Interval<Integer>(Operations.getIntegerOperations(), -10, 10)));
    }


    @org.junit.jupiter.api.Test
    void removeSubtree() {
        MutableTree<Integer> tree = TreeGenerator.getIntegerMutableTree();

        tree.removeSubtree(tree.getRoot());

        assertEquals(tree.getRoot(), null);

        tree = TreeGenerator.getIntegerMutableTree();

        tree.removeSubtree((MutableNode<Integer>)tree.getRoot().getChildren().toArray()[1]);

        assertEquals(tree.getRoot().getChildren().size(), 1);
        assertEquals(((MutableNode<Integer>)tree.getRoot().getChildren().toArray()[0]).getValue(), 2);

        tree = TreeGenerator.getIntegerMutableTree();

        var deletedNode = ((MutableNode<Integer>)tree.getRoot().getChildren().toArray()[0]);
        deletedNode = ((MutableNode<Integer>)deletedNode.getChildren().toArray()[0]);

        tree.removeSubtree(deletedNode);

        var testNode = (MutableNode<Integer>)tree.getRoot().getChildren().toArray()[0];

        assertEquals(testNode.getChildren().size(), 2);
        assertEquals(((MutableNode<Integer>)testNode.getChildren().toArray()[1]).getValue(), -5);
        assertEquals(((MutableNode<Integer>)testNode.getChildren().toArray()[0]).getValue(), 1);
    }

    @org.junit.jupiter.api.Test
    void maximize() {
        MutableTree<Integer> tree = TreeGenerator.getIntegerMutableTree();

        tree.maximize();
        tree.calculateSizeAndSum();

        assertEquals(tree.getSum(), 10);
    }

    @Test
    void MishasTest() {
        MutableNode<Integer> root = new MutableNode<>(operations, 1);

        MutableNode<Integer> subRoot = new MutableNode<>(operations, 1);
        subRoot.setParent(root);

        MutableNode<Integer> subRoot1 = new MutableNode<>(operations, -5);
        subRoot1.setParent(subRoot);

        MutableNode<Integer> subRoot2 = new MutableNode<>(operations, -5);
        subRoot2.setParent(subRoot);

        root.addChild(subRoot);

        subRoot.addChild(subRoot1);
        subRoot.addChild(subRoot2);

        var testTree = new MutableTree<>(operations, root);
        testTree.calculateSizeAndSum();

        testTree.maximize(2);

        assertEquals(testTree.getSize(), 2);
        assertEquals(testTree.getSum(), 2);
    }

    @Test
    void DimaAndZhenyaTest() {
        MutableNode<Integer> root = new MutableNode<>(operations, 1);

        MutableNode<Integer> subRoot = new MutableNode<>(operations, -2);
        subRoot.setParent(root);

        MutableNode<Integer> subRoot1 = new MutableNode<>(operations, -5);
        subRoot1.setParent(subRoot);

        MutableNode<Integer> subRoot2 = new MutableNode<>(operations, -5);
        subRoot2.setParent(subRoot);

        MutableNode<Integer> subRoot3 = new MutableNode<>(operations, 3);
        subRoot3.setParent(subRoot);

        root.addChild(subRoot);

        subRoot.addChild(subRoot1);
        subRoot.addChild(subRoot2);
        subRoot.addChild(subRoot3);

        var testTree = new MutableTree<>(operations, root);
        testTree.calculateSizeAndSum();

        testTree.maximize(10);

        assertEquals(testTree.getSize(), 3);
        assertEquals(testTree.getSum(), 2);
    }

    @org.junit.jupiter.api.Test
    void testMaximize() {
        MutableTree<Integer> tree = TreeGenerator.getIntegerMutableTree();

        tree.calculateSizeAndSum();
        tree.maximize(2);
        tree.calculateSizeAndSum();

        assertEquals(tree.getSum(), 10);
    }
}