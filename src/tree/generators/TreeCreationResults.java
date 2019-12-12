package tree.generators;

import tree.AbstractTree;
import tree.Node;


public class TreeCreationResults<T extends Number> {
    protected int size;
    protected T sum;
    protected AbstractTree<T> tree;
    protected Node<T> root;


    public TreeCreationResults(int size, T sum, AbstractTree<T> tree, Node<T> root) {
        this.size = size;
        this.sum = sum;
        this.tree = tree;
        this.root = root;
    }


    public int getSize() {
        return size;
    }

    public AbstractTree<T> getTree() {
        return tree;
    }

    public T getSum() {
        return sum;
    }

    public Node<T> getRoot() {
        return root;
    }
}
