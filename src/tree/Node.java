package tree;

import java.util.Collection;
import java.util.Set;

/**
 * The base interfaces for all nodes in the project.
 * Supports many GET methods and print method.
 * @param <T> The type of node's value.
 */
public interface Node<T extends Number> extends Wrapper<T> {
    void print(int indents);

    Collection<Node<T>> getChildren();
    Set<Node<T>> getAllParents();
    Node<T> getParent();
    T getSubtreeWeight();
    int getHeight();
}
