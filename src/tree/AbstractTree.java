package tree;

import tree.maximizer.KMaximizer;
import tree.operations.Operations;

import java.util.*;

/**
 * The base class for all trees in the project.
 * Provides three abstract methods (removeSubtree, maximize, maximize(int k)).
 *
 * Also provides common methods for all trees (calculateSizeAndSum, getChildParentMap, getNodesCollection).
 *
 * @param <T> The type which defines the type of the values in nodes.
 */
public abstract class AbstractTree<T extends Number> {

    /**
     * IKMaximizer is a maximizer which is used to perform maximization no more than in K steps.
     */
    KMaximizer<T> kMaximizer;
    Node<T> root;
    int size;

    /**
     * An Operations class object which allows us performing math actions like adding, subtracting and so on
     */
    Operations<T> operations;

    /**
     * Sum of all values of all nodes which are in the tree.
     */
    T sum;


    public AbstractTree(Operations<T> operations) {
        this.operations = operations;
    }


    public int getSize() {
        return size;
    }

    public T getSum() {
        return sum;
    }

    public Node<T> getRoot() {
        return root;
    }

    public Operations<T> getOperationsObject() {
        return operations;
    }


    /**
     * Finds the deepest negative subtree in the current tree. This method is used in the greedy algorithm
     * which is used to solve the maximize without limitations problem.
     * @return The root of the found subtree.
     */
    Node<T> findDeepestNegativeSubtree() {
        Node<T> minSubtreeRoot = root;
        int maxHeight = 0;

        Queue<Node<T>> pendingNodes = new ArrayDeque<>();
        pendingNodes.addAll(root.getChildren());

        while (pendingNodes.size() > 0) {
            Node<T> currNode = pendingNodes.poll();
            T currNodeSubtreeWeight = currNode.getSubtreeWeight();

            if (operations.compare(currNodeSubtreeWeight, operations.getZero()) == -1 && currNode.getHeight() > maxHeight) {
                minSubtreeRoot = currNode;
                maxHeight = currNode.getHeight();
            }

            pendingNodes.addAll(currNode.getChildren());
        }

        return minSubtreeRoot;
    }


    /**
     * Using BFS calculates size and the sum. Returns the AbstractTree in order to create an
     * opportunity to chain methods invocation.
     */
    public AbstractTree<T> calculateSizeAndSum() {
        Queue<Node<T>> pendingNodes = new ArrayDeque<>();
        pendingNodes.add(root);

        T sum = (T)(Number)0;
        int size = 0;

        while (pendingNodes.size() != 0) {
            Node<T> currNode = pendingNodes.poll();

            size++;
            sum = operations.add(sum, currNode.getValue());

            pendingNodes.addAll(currNode.getChildren());
        }

        this.size = size;
        this.sum = sum;

        return this;
    }

    /**
     * Returns the Map, where the keys are the nodes, and the values are the Sets of nodes, which are parents
     * for the key node. Using BFS here to create this map.
     */
    public Map<Node<T>, Set<Node<T>>> getChildParentMap() {
        if (root == null) {
            return new HashMap<>();
        }

        Map<Node<T>, Set<Node<T>>> childParentMap = new HashMap<>();

        Queue<Node<T>> pendingNodes = new ArrayDeque<>();
        pendingNodes.add(root);

        while (pendingNodes.size() > 0) {
            Node<T> currNode = pendingNodes.poll();

            childParentMap.put(currNode, currNode.getAllParents());

            pendingNodes.addAll(currNode.getChildren());
        }

        return childParentMap;
    }

    /**
     * Returns the List of all nodes which are in the tree with the help of BFS.
     * @return
     */
    public List<Node<T>> getNodesCollection() {
        if (root == null) {
            return new ArrayList<>();
        }

        ArrayList<Node<T>> nodesCollection = new ArrayList<>();

        Queue<Node<T>> pendingNodes = new ArrayDeque<>();
        pendingNodes.add(root);

        while (pendingNodes.size() > 0) {
            Node<T> currNode = pendingNodes.poll();
            nodesCollection.add(currNode);

            for (Node<T> child : currNode.getChildren()) {
                pendingNodes.add(child);
            }
        }

        return nodesCollection;
    }

    /**
     * Removes the subtree with the give root from the tree.
     * @param subtreeRoot The root of a subtree which must be deleted.
     * @return The tree instance.
     */
    abstract AbstractTree<T> removeSubtree(Node<T> subtreeRoot);

    /**
     * Performs the maximization with no more than maxOperationsCount steps (iterations)
     * @param maxOperationsCount The maximum number of iterations allowed.
     * @return The instance of the tree.
     */
    abstract AbstractTree<T> maximize(int maxOperationsCount);

    /**
     * Performs the maximization with an infinite number of steps.
     * @return The instance of the tree.
     */
    abstract AbstractTree<T> maximize();
}
