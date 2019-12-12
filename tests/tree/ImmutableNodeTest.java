package tree;

import com.sun.java.accessibility.util.AccessibilityListenerList;
import org.junit.jupiter.api.Test;
import tree.operations.Operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ImmutableNodeTest {

    private ImmutableNode<Integer> immutableNode;
    private Operations<Integer> operations = Operations.getIntegerOperations();


    @Test
    void getChildren() {
        immutableNode = new ImmutableNode<>(operations, 3);

        assertNotEquals(immutableNode.getChildren(), null);
        assertEquals(immutableNode.getChildren().toArray().length, 0);

        ImmutableNode<Integer> newChild = new ImmutableNode<>(operations, 34);

        immutableNode.getChildren().add(newChild);

        assertEquals(immutableNode.getChildren().toArray().length, 1);
        assertEquals(immutableNode.getChildren().toArray()[0], newChild);
        assertEquals(((ImmutableNode<Integer>)(immutableNode.getChildren().toArray()[0])).getValue(), 34);
    }

    @Test
    void getValue() {
        immutableNode = new ImmutableNode<Integer>(operations);

        assertEquals(immutableNode.getValue(), 0);

        immutableNode = new ImmutableNode<>(operations, 3);
        assertEquals(immutableNode.getValue(), 3);
    }

    @Test
    void getParent() {
        ImmutableNode<Integer> node = new ImmutableNode<Integer>(operations, 10);
        ImmutableNode<Integer> testNode = new ImmutableNode<Integer>(operations, 10, null, node);

        assertEquals(testNode.getParent(), node);
        assertEquals(node.getParent(), null);
    }

    @Test
    void copyWithoutDeepChild() {
        Collection<Node<Integer>> children = new ArrayList<>();
        ImmutableNode<Integer> node = new ImmutableNode<>(operations, 3, children, null);

        Node<Integer> child1 = new ImmutableNode<Integer>(operations, 1, new ArrayList<>() , node);
        Node<Integer> child2 = new ImmutableNode<Integer>(operations, 2, new ArrayList<>(), node);

        children.add(child1);
        children.add(child2);

        var newNode = node.copyWithoutDeepChild((ImmutableNode<Integer>) child1, null);

        assertEquals(newNode.getChildren().size(),1);
        assertNotEquals(newNode, node);
    }

    @Test
    void copyWithoutDeepChildren() {
        Collection<Node<Integer>> children = new ArrayList<>();
        ImmutableNode<Integer> node = new ImmutableNode<>(operations);

        Node<Integer> child1 = new ImmutableNode<Integer>(operations, 1, null , node);
        Node<Integer> child2 = new ImmutableNode<Integer>(operations, 2, null , node);

        children.add(child1);
        children.add(child2);

        Set<Node<Integer>> nodesToBeDeleted = new HashSet<>();
        nodesToBeDeleted.add(child1);
        nodesToBeDeleted.add(child2);

        var newNode = node.copyWithoutDeepChild((ImmutableNode<Integer>) child1, null);

        assertEquals(newNode.getChildren().size(),0);
        assertNotEquals(newNode, node);
    }

    @Test
    void getAllParents() {
        ImmutableNode<Integer> node = new ImmutableNode<Integer>(operations, 1, null , null);
        ImmutableNode<Integer> child1 = new ImmutableNode<Integer>(operations, 1, null , node);
        ImmutableNode<Integer> child2 = new ImmutableNode<Integer>(operations, 2, null , child1);

        Set<Node<Integer>> allParents = child2.getAllParents();

        assertEquals(allParents.contains(child1), true);
        assertEquals(allParents.contains(node), true);
        assertEquals(allParents.size(), 2);
        assertEquals(allParents.contains(child2), false);
    }


    @Test
    void getSubtreeWeight() {
        Collection<Node<Integer>> children = new ArrayList<>();
        ImmutableNode<Integer> node = new ImmutableNode<Integer>(operations, 1, children , null);

        ImmutableNode<Integer> child1 = new ImmutableNode<Integer>(operations, 1, new ArrayList<>() , node);
        ImmutableNode<Integer> child2 = new ImmutableNode<Integer>(operations, 2, new ArrayList<>() , child1);

        children.add(child1);
        children.add(child2);

        assertEquals(node.getSubtreeWeight(), 4);
        assertEquals(child1.getSubtreeWeight(), 1);
        assertEquals(child2.getSubtreeWeight(), 2);
    }

    @Test
    void getHeight() {
        ImmutableNode<Integer> node = new ImmutableNode<Integer>(operations, 1, null , null);
        ImmutableNode<Integer> child1 = new ImmutableNode<Integer>(operations, 1, null , node);
        ImmutableNode<Integer> child2 = new ImmutableNode<Integer>(operations, 2, null , child1);

        assertEquals(node.getHeight(), 0);
        assertEquals(child1.getHeight(), 1);
        assertEquals(child2.getHeight(), 2);
    }
}