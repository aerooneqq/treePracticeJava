package tree;

import org.junit.jupiter.api.Test;
import tree.generators.*;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTreeTest {
    private TreeGenerator<Integer> treeGenerator;


    public AbstractTreeTest() {
        treeGenerator = new TreeGenerator<Integer>(Operations.getIntegerOperations(),
                new TreeGeneratorParams(5, 5,
                    new Interval<Integer>(Operations.getIntegerOperations(), -5, 5)));
    }


    @Test
    void getRoot() {
        TreeCreationResults<Integer> results = treeGenerator.generateRandomMutableTree();

        assertEquals(results.getTree().getRoot(), results.getRoot());
        assertEquals(results.getTree().getRoot().getValue(), results.getRoot().getValue());
    }


    @Test
    void getSize() {
        TreeCreationResults<Integer> results = treeGenerator.generateRandomMutableTree();

        assertEquals(results.getTree().getSize(), 0);

        ((MutableTree<Integer>)results.getTree()).calculateSizeAndSum();

        assertEquals(results.getTree().getSize(), results.getSize());
    }

    @Test
    void getSum() {
        TreeCreationResults<Integer> results = treeGenerator.generateRandomMutableTree();

        assertEquals(null, results.getTree().getSum());

        ((MutableTree<Integer>)results.getTree()).calculateSizeAndSum();

        assertEquals(results.getTree().getSum(), results.getSum());
    }
}