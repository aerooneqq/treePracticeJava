package tree;

import tree.operations.Operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Class which represents the Mutable node. All changes to the node can be done in-place.
 */
public class MutableNode<T extends Number> implements Node<T> {
    private T value;
    private Node<T> parent;
    private Collection<Node<T>> children;
    private T subtreeWeight;
    private int height;
    private Operations<T> operations;


    public MutableNode(Operations<T> operations) {
        this.operations = operations;
        children = new ArrayList<>();
        subtreeWeight = this.operations.getZero();
        value = this.operations .getZero();
    }

    public MutableNode(Operations<T> operations, T value) {
        this(operations);
        this.value = value;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    void addChild(MutableNode<T> child) throws IllegalArgumentException {
        if (child == null) {
            throw new IllegalArgumentException("Child can not be null");
        }

        child.setHeight(this.height + 1);
        children.add(child);
    }

    void removeChild(MutableNode<T> child) throws IllegalArgumentException {
        if (child == null) {
            throw new IllegalArgumentException("Child can not be null");
        }

        children.remove(child);
    }

    @Override
    public Node<T> getParent() {
        return parent;
    }

    public void setParent(MutableNode<T> parent) {
        this.parent = parent;
        this.height = parent.getHeight() + 1;
    }

    @Override
    public Collection<Node<T>> getChildren() {
        return children;
    }

    @Override
    public Set<Node<T>> getAllParents() {
        Node<T> currParent = parent;
        Set<Node<T>> parentsSet = new HashSet<>();

        while (currParent != null) {
            parentsSet.add(currParent);
            currParent = currParent.getParent();
        }

        return parentsSet;
    }

    @Override
    public void print(int indents) {
        for (int i = 0; i < indents; i++) {
            System.out.print(' ');
        }

        System.out.print(value);
    }

    @Override
    public T getValue() {
        return value;
    }

    public void modifySubtreeWeight(T delta) {
        subtreeWeight = operations.add(subtreeWeight, delta);
    }

    public T getSubtreeWeight() {
        return subtreeWeight;
    }
}
