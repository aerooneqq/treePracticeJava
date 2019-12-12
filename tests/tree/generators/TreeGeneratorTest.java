package tree.generators;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import tree.AbstractTree;
import tree.ImmutableTree;
import tree.MutableTree;
import tree.operations.Operations;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TreeGeneratorTest {
    private static final Operations<Integer> operations = Operations.getIntegerOperations();
    private static final Interval<Integer> valuesInterval = new Interval<>(operations, -10, 10);
    private static final TreeGeneratorParams<Integer> params = new TreeGeneratorParams<>(3, 3, valuesInterval);

    private TreeGenerator<Integer> generator;

    @BeforeAll
    public void setUp() {
        generator = new TreeGenerator<>(operations, params);
    }

    @Test
    void generateRandomImmutableTree() {
        TreeCreationResults<Integer> results = generator.generateRandomImmutableTree();
        AbstractTree<Integer> tree = results.getTree();
        tree.calculateSizeAndSum();

        assertEquals(tree.getSum(), results.getSum());
        assertEquals(tree.getSize(), results.getSize());
        assertEquals(tree.getRoot(), results.getRoot());
        assertTrue(tree instanceof ImmutableTree);
    }

    @Test
    void generateRandomMutableTree() {
        TreeCreationResults<Integer> results = generator.generateRandomMutableTree();
        AbstractTree<Integer> tree = results.getTree();
        tree.calculateSizeAndSum();

        assertEquals(tree.getSum(), results.getSum());
        assertEquals(tree.getSize(), results.getSize());
        assertEquals(tree.getRoot(), results.getRoot());
        assertTrue(tree instanceof MutableTree);
    }

    @Test
    void getIntegerMutableTree() {
        AbstractTree<Integer> tree = TreeGenerator.getIntegerMutableTree();
        tree.calculateSizeAndSum();

        assertEquals(tree.getSize(), 12);
        assertEquals(tree.getSum(), -30);
    }

    @Test
    void getIntegerImmutableTree() {
        AbstractTree<Integer> tree = TreeGenerator.getIntegerImmutableTree();
        tree.calculateSizeAndSum();

        assertEquals(tree.getSize(), 12);
        assertEquals(tree.getSum(), -30);
    }
}