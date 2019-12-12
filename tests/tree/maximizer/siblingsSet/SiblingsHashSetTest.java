package tree.maximizer.siblingsSet;

import org.junit.jupiter.api.Test;
import tree.MutableNode;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class SiblingsHashSetTest {

    @Test
    void add() {
        MutableNode<Integer> root = new MutableNode<>(Operations.getIntegerOperations());
        MutableNode<Integer> subRoot1 = new MutableNode<>(Operations.getIntegerOperations());
        MutableNode<Integer> subRoot2 = new MutableNode<>(Operations.getIntegerOperations());

        subRoot1.setParent(root);
        subRoot2.setParent(root);

        SiblingsHashSet<Integer> siblingsHashSet = new SiblingsHashSet<>();

        assertTrue(siblingsHashSet.add(root));
        assertFalse(siblingsHashSet.add(subRoot1));
        assertFalse(siblingsHashSet.add(subRoot2));
        assertEquals(siblingsHashSet.size(), 1);
        assertTrue(siblingsHashSet.contains(root));

        siblingsHashSet.clear();

        assertEquals(siblingsHashSet.size(), 0);
        assertTrue(siblingsHashSet.add(subRoot1));
        assertTrue(siblingsHashSet.add(subRoot2));
        assertTrue(siblingsHashSet.add(root));
        assertEquals(siblingsHashSet.size(), 1);
        assertTrue(siblingsHashSet.contains(root));
    }
}