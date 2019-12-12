package tree.maximizer.siblingsSet;

import tree.Node;

import java.util.HashSet;
import java.util.Set;


/**
 * The special type of Set which is created for the k-maximization algorithm.
 * The only method which is changed from the original hash set is an ADD method.
 * If there is the child of the node in the set, then we delete this child, because the weight of
 * the child is already counted in this node subtree weight.
 * If the parent of this node in the set, then we do NOT add this node to the set.
 * @param <T> The type of nodes' values.
 */
public class SiblingsHashSet<T extends Number> extends HashSet<Node<T>> {

    @Override
    public boolean add(Node<T> node) {
        Set<Node<T>> nodesToDelete = new HashSet<>();

        for (Node<T> el : this) {
            if (el.getAllParents().contains(node)) {
                nodesToDelete.add(el);
            }

            if (node.getAllParents().contains(el)) {
                return false;
            }
        }

        super.removeAll(nodesToDelete);
        super.add(node);

        return true;
    }
}
