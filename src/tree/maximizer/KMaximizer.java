package tree.maximizer;

import tree.Node;

import java.util.List;

/**
 * This is the interface which defines method to solve the K-step maximization problem.
 * The algorithm is not so easy to implement and it takes ~260 lines of code,
 * so I decided to separate it from the Tree classes.
 * @param <T> The type of nodes' values.
 */
public interface KMaximizer<T extends Number> {

    /**
     * Solves the problem of K tree maximization.
     * @param maxOperationsCount The maximum number of operations allowed.
     * @return The list of nodes which must be deleted in order to maximize the tree.
     */
    List<Node<T>> maximize(int maxOperationsCount);
}
