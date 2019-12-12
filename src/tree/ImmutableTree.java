package tree;

import tree.maximizer.TreeKMaximizer;
import tree.operations.Operations;

import java.util.List;

/**
 * The class which represents the Immutable tree (Immutable tree is made from Immutable nodes).
 * @param <T> The type of nodes' values.
 */
public class ImmutableTree<T extends Number> extends AbstractTree<T> {

    public ImmutableTree(Operations<T> operations) {
        super(operations);
        kMaximizer = new TreeKMaximizer<>(this);
    }

    public ImmutableTree(Operations<T> operations, ImmutableNode<T> root) {
        super(operations);
        this.root = root;
        kMaximizer = new TreeKMaximizer<>(this);
    }

    public ImmutableTree(Operations<T> operations, ImmutableTree<T> otherTree) {
        super(operations);
        root = otherTree.root;
        size = otherTree.getSize();
        sum = otherTree.getSum();
        kMaximizer = new TreeKMaximizer<>(this);
    }


    /**
     * Copies the current tree without given node with the help of ImmutableNode's copyWithoutDeepChild method.
     * @param deletedNode The node which must not be copied.
     * @return The instance of the new copied node.
     */
    private ImmutableTree<T> copyTreeWithout(ImmutableNode<T> deletedNode) {
        if (deletedNode == root) {
            return new ImmutableTree<>(operations);
        }

        return new ImmutableTree<T>(operations, ((ImmutableNode<T>) root).copyWithoutDeepChild(deletedNode, null));
    }

    @Override
    AbstractTree<T> removeSubtree(Node<T> subtreeRoot) {
        if (subtreeRoot == root) {
            return null;
        }

        return copyTreeWithout((ImmutableNode<T>)subtreeRoot);
    }

    /**
     * Maximizes the tree with maximum of maxOperationsCount iterations.
     * @param maxOperationsCount The maximum number of iterations allowed.
     * @return
     */
    @Override
    AbstractTree<T> maximize(int maxOperationsCount) {
        List<Node<T>> nodesToBeDeleted = kMaximizer.maximize(maxOperationsCount);

        return new ImmutableTree<T>(operations, ((ImmutableNode<T>)root).copyWithoutDeepChildren(nodesToBeDeleted, null));
    }

    /**
     * Maximizes the tree with no limit on how many removals we can do. Uses a greedy algorithm: on each iteration
     * we delete the furthest node (from the root) with a negative subtree weight. We repeat this procedure while the
     * weight of the furthest node subtree is negative.
     * @return The new maximized tree object.
     */
    @Override
    AbstractTree<T> maximize() {
        ImmutableTree<T> newTree = this;
        ImmutableNode<T> deepestNegativeSubtreeRoot = (ImmutableNode<T>) newTree.findDeepestNegativeSubtree();

        while (deepestNegativeSubtreeRoot != null && deepestNegativeSubtreeRoot.getValue().doubleValue() < 0) {
            System.out.print(deepestNegativeSubtreeRoot.getValue().doubleValue() + " ");
            newTree = ((ImmutableTree<T>)(newTree).removeSubtree(deepestNegativeSubtreeRoot));
            deepestNegativeSubtreeRoot = (ImmutableNode<T>) newTree.findDeepestNegativeSubtree();
        }

        return newTree;
    }
}
