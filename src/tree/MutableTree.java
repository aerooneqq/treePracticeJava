package tree;

import tree.maximizer.TreeKMaximizer;
import tree.operations.Operations;

import java.util.*;

public class MutableTree<T extends Number> extends AbstractTree<T> {

    public MutableTree(Operations<T> operations) {
        super(operations);
        kMaximizer = new TreeKMaximizer<>(this);
    }

    public MutableTree(Operations<T> operations, MutableNode<T> root) {
        super(operations);
        this.root = root;
        kMaximizer = new TreeKMaximizer<>(this);
    }


    /**
     * The recursive algorithm which calculates subtrees' weights.
     */
    private void calculateSubtreesWightsRecursive(MutableNode<T> node) {
        if (node.getChildren() == null || node.getChildren().size() == 0) {
            node.modifySubtreeWeight(node.getValue());
            return;
        }

        for (Node<T> child : node.getChildren()) {
            calculateSubtreesWightsRecursive((MutableNode<T>)child);
        }

        T sum = operations.getZero();
        for (Node<T> child : node.getChildren()) {
            sum = operations.add(sum, ((MutableNode<T>)child).getSubtreeWeight());
        }

        sum = operations.add(sum, node.getValue());

        node.modifySubtreeWeight(sum);
    }

    /**
     * Calculates the subtrees' weights starting form the root.
     */
    private void calculateSubtreesWeights() {
        calculateSubtreesWightsRecursive((MutableNode<T>)root);
    }

    /**
     * Recalculates the parents subtrees' weights before the removal of a node.
     * @param deletedNode Node which will be deleted.
     */
    private void recalculateParentsWeights(MutableNode<T> deletedNode) {
        MutableNode<T> parent = (MutableNode<T>)deletedNode.getParent();

        while (parent != null) {
            parent.modifySubtreeWeight(operations.negotiate(deletedNode.getSubtreeWeight()));
            parent = (MutableNode<T>)parent.getParent();
        }
    }

    /**
     * Removes the subtree from the current tree and recalculates the subtree weights of all parents of the
     * deleted node.
     * @param subtreeRoot The root of a subtree which must be deleted.
     */
    @Override
    AbstractTree<T> removeSubtree(Node<T> subtreeRoot) {
        Node<T> parent = subtreeRoot.getParent();

        //If parent is null then it is the root
        if (parent == null) {
            root = null;
            size = 0;
            sum = operations.getZero();

            return this;
        }

        recalculateParentsWeights((MutableNode<T>)subtreeRoot);
        subtreeRoot.getParent().getChildren().remove(subtreeRoot);

        return this;
    }

    /**
     * Maximizes the current tree using the kMaximizer.
     * @param maxOperationsCount The maximum number of iterations allowed.
     */
    @Override
    public AbstractTree<T> maximize(int maxOperationsCount) {
        calculateSubtreesWeights();
        List<Node<T>> nodesToRemoveList = kMaximizer.maximize(maxOperationsCount);

        for (Node<T> nodeToDelete : nodesToRemoveList) {
            removeSubtree(nodeToDelete);
        }

        calculateSizeAndSum();
        return this;
    }

    /**
     * Maximizes the current tree with unlimited number of iterations.
     */
    @Override
    AbstractTree<T> maximize() {
        calculateSubtreesWeights();
        Node<T> minSubtreeRoot = findDeepestNegativeSubtree();

        while (minSubtreeRoot != null && minSubtreeRoot.getSubtreeWeight().doubleValue() < 0) {
            removeSubtree(minSubtreeRoot);

            minSubtreeRoot = findDeepestNegativeSubtree();
        }

        return this;
    }
}
