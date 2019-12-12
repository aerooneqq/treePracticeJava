package tree;

import tree.operations.Operations;

import java.util.*;

/**
 * This the class which represents an immutable node. Immutable means that it can not be changed in-place, that is
 * why there are no Setters in this class.
 * @param <T> The type of value of the node.
 */
public class ImmutableNode<T extends Number> implements Node<T> {
    private final T value;
    private final Node<T> parent;
    private final Collection<Node<T>> children;
    private final Operations<T> operations;

    /**
     * The height of the node (the root of the tree has a height of 0).
     */
    private final int height;


    public ImmutableNode(Operations<T> operations) {
        this(operations, operations.getZero());
    }

    public ImmutableNode(Operations<T> operations, T value) {
        this.operations = operations;
        children = new ArrayList<>();
        this.value = value;
        height = 0;
        parent = null;
    }

    public ImmutableNode(Operations<T> operations, T value, Collection<Node<T>> children, ImmutableNode<T> parent) {
        this.operations = operations;
        this.value = value;
        this.children = children;
        this.parent = parent;

        if (parent != null) {
            this.height = parent.getHeight() + 1;
        } else {
            height = 0;
        }
    }

    /**
     * Recursively copies the current node with all it's children without the given extra node.
     * @param extraNode The node which must not be copied.
     * @param parent The parent which is set to copied new node.
     * @return The instance of the new copied node.
     */
    public ImmutableNode<T> copyWithoutDeepChild(ImmutableNode<T> extraNode, ImmutableNode<T> parent) {
        Collection<Node<T>> newChildren = new ArrayList<>();
        ImmutableNode<T> newNode = new ImmutableNode<T>(operations, value, newChildren, parent);

        for (Node<T> child : getChildren()) {
            if (child != extraNode) {
                var newChildNode = ((ImmutableNode<T>)child).copyWithoutDeepChild(extraNode, newNode);
                newChildren.add(newChildNode);
            }
        }

        return newNode;
    }

    /**
     * Recursively copies the current node with all it's children without the given set of nodes.
     * @param extraNodes The set of nodes which must not be copied.
     * @param parent The parent which is set to copied node.
     * @return The instance of the new copied node.
     */
    public ImmutableNode<T> copyWithoutDeepChildren(List<Node<T>> extraNodes, ImmutableNode<T> parent) {
        Collection<Node<T>> newChildren = new ArrayList<>();
        ImmutableNode<T> newNode = new ImmutableNode<T>(operations, value, newChildren, parent);

        for (Node<T> child : getChildren()) {
            if (!extraNodes.contains(child)) {
                ImmutableNode<T> newChildNode = ((ImmutableNode<T>)child).copyWithoutDeepChildren(extraNodes, newNode);
                newChildren.add(newChildNode);
            }
        }

        return newNode;
    }
    
    @Override
    public Node<T> getParent() {
        return parent;
    }

    @Override
    public Collection<Node<T>> getChildren() {
        return children;
    }

    /**
     * @return The set of all parents of this node.
     */
    @Override
    public Set<Node<T>> getAllParents() {
        Set<Node<T>> prevParentsSets = new HashSet<>();
        Node<T> currParent = parent;

        while (currParent != null) {
            prevParentsSets.add(currParent);
            currParent = currParent.getParent();
        }

        return prevParentsSets;
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

    /**
     * Calculating the subtree weight using DFS.
     */
    @Override
    public T getSubtreeWeight() {
        T subtreeWeight = value;

        Queue<Node<T>> pendingNodes = new ArrayDeque<>(children);

        while (pendingNodes.size() > 0) {
            Node<T> currNode = pendingNodes.poll();
            subtreeWeight = operations.add(subtreeWeight, currNode.getValue());

            pendingNodes.addAll(currNode.getChildren());
        }

        return subtreeWeight;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
