package tree.generators;

import org.junit.jupiter.api.Test;
import tree.*;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class TreeCreationResultsTest {
    private static final Operations<Integer> operations = Operations.getIntegerOperations();
    private static final Node<Integer> root = new ImmutableNode<>(operations);
    private static final AbstractTree<Integer> tree = new ImmutableTree<>(operations);


    @Test
    void getSize() {
        var treeCreationsResults = new TreeCreationResults<Integer>(3, 4, tree, root);
        assertEquals(treeCreationsResults.getSize(), 3);
    }

    @Test
    void getTree() {
        var treeCreationsResults = new TreeCreationResults<Integer>(3, 4, tree, root);
        assertEquals(treeCreationsResults.getTree(), tree);
    }

    @Test
    void getSum() {
        var treeCreationsResults = new TreeCreationResults<Integer>(3, 4, tree, root);
        assertEquals(treeCreationsResults.getSum(), 4);
    }

    @Test
    void getRoot() {
        var treeCreationsResults = new TreeCreationResults<Integer>(3, 4, tree, root);
        assertEquals(treeCreationsResults.getRoot(), root);
    }
}