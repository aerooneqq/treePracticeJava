package tree;

import org.junit.jupiter.api.Test;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class MutableNodeTest {
    private Operations<Integer> operations = Operations.getIntegerOperations();
    private MutableNode<Integer> mutableNode;

    @Test
    void getOrSetParent() {
        mutableNode = new MutableNode<>(operations, 3);
        MutableNode<Integer> parent = new MutableNode<>(operations, 1);

        mutableNode.setParent(parent);

        assertEquals(mutableNode.getParent().getValue(), 1);
        assertEquals(mutableNode.getParent(), parent);

        MutableNode<Integer> newParent = new MutableNode<>(operations, 10);
        mutableNode.setParent(newParent);

        assertEquals(mutableNode.getParent(), newParent);
        assertEquals(mutableNode.getParent().getValue(), 10);
    }

    @Test
    void getChildren() {
        mutableNode = new MutableNode<>(operations, 3);

        assertNotEquals(mutableNode.getChildren(), null);
        assertEquals(mutableNode.getChildren().toArray().length, 0);

        MutableNode<Integer> newChild = new MutableNode<>(operations, 34);

        mutableNode.getChildren().add(newChild);

        assertEquals(mutableNode.getChildren().toArray().length, 1);
        assertEquals(mutableNode.getChildren().toArray()[0], newChild);
        assertEquals(((MutableNode<Integer>)(mutableNode.getChildren().toArray()[0])).getValue(), 34);
    }

    @Test
    void getValue() {
        mutableNode = new MutableNode<Integer>(operations);

        assertEquals(mutableNode.getValue(), 0);

        mutableNode = new MutableNode<>(operations, 3);
        assertEquals(mutableNode.getValue(), 3);
    }

    @Test
    void modifyAndGetSubtreeWeight() {
        mutableNode = new MutableNode<>(operations, 4);

        assertEquals(mutableNode.getSubtreeWeight(), 0);

        mutableNode.modifySubtreeWeight(2);

        assertEquals(mutableNode.getSubtreeWeight(), 2);

        mutableNode.modifySubtreeWeight(-3);
        assertEquals(mutableNode.getSubtreeWeight(), -1);
    }

    @Test
    void getSetHeight() {
        mutableNode = new MutableNode<>(operations, 4);

        assertEquals(mutableNode.getHeight(), 0);

        mutableNode.setHeight(123);

        assertEquals(mutableNode.getHeight(), 123);

        mutableNode.setHeight(-11);

        assertEquals(mutableNode.getHeight(), -11);
    }

    @Test
    void addChild() {
        mutableNode = new MutableNode<>(operations);

        assertNotEquals(mutableNode.getChildren(), null);
        assertEquals(mutableNode.getChildren().toArray().length, 0);

        MutableNode<Integer> childNode = new MutableNode<>(operations);

        mutableNode.addChild(childNode);

        assertNotEquals(mutableNode.getChildren(), null);
        assertEquals(mutableNode.getChildren().toArray().length, 1);
        assertEquals(mutableNode.getChildren().toArray()[0], childNode);
    }

    @Test
    void removeChild() {
        mutableNode = new MutableNode<>(operations);

        assertNotEquals(mutableNode.getChildren(), null);
        assertEquals(mutableNode.getChildren().toArray().length, 0);

        MutableNode<Integer> childNode = new MutableNode<>(operations);

        mutableNode.addChild(childNode);

        assertNotEquals(mutableNode.getChildren(), null);
        assertEquals(mutableNode.getChildren().toArray().length, 1);
        assertEquals(mutableNode.getChildren().toArray()[0], childNode);

        mutableNode.removeChild(childNode);

        assertNotEquals(mutableNode.getChildren(), null);
        assertEquals(mutableNode.getChildren().toArray().length, 0);
    }

    @Test
    void getSetParent() {
        mutableNode = new MutableNode<>(operations);

        assertEquals(mutableNode.getParent(), null);

        MutableNode<Integer> newParent = new MutableNode<>(operations);

        mutableNode.setParent(newParent);

        assertEquals(mutableNode.getParent(), newParent);
    }
}