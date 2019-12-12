package tree.generators;

import tree.*;
import tree.operations.Operations;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class TreeGenerator<T extends Number> {

    private static Random random = new Random();

    private TreeGeneratorParams<T> params;
    private Operations<T> operations;


    public TreeGenerator(Operations<T> operations, TreeGeneratorParams<T> params) {
        this.operations = operations;
        this.params = params;
    }


    private T getRandomValue() {
        return operations.getRandomValue(params.getValuesInterval().getLeftBorder(),
                params.getValuesInterval().getRightBorder());
    }


    private int getNumberOfChildren() {
        return random.nextInt(params.getMaxChildrenCount());
    }


    public TreeCreationResults<T> generateRandomImmutableTree() {
        T value = getRandomValue();
        T sum = value;

        ImmutableNode<T> root = new ImmutableNode<>(operations, value);
        ImmutableTree<T> immutableTree = new ImmutableTree<>(operations, root);

        int currHeight = 1;

        Queue<ImmutableNode<T>> pendingNodes = new ArrayDeque<>();
        pendingNodes.add(root);
        int size = 1;

        while (currHeight < params.getHeight()) {
            int queueSize = pendingNodes.size();

            while (queueSize > 0) {
                ImmutableNode<T> currNode = pendingNodes.poll();

                for (int i = 0; i < getNumberOfChildren(); i++) {
                    T childValue = getRandomValue();
                    sum = operations.add(sum, childValue);

                    ImmutableNode<T> childNode = new ImmutableNode<>(operations, childValue, new ArrayList<>(), currNode);
                    currNode.getChildren().add(childNode);

                    pendingNodes.add(childNode);
                    size++;
                }

                queueSize--;
            }

            currHeight++;
        }

        return new TreeCreationResults<>(size, sum, immutableTree, root);
    }

    public TreeCreationResults<T> generateRandomMutableTree() {
        T value = getRandomValue();
        T sum = value;

        MutableTree<T> mutableTree = new MutableTree<>(operations, new MutableNode<>(operations, value));
        MutableNode<T> root = (MutableNode<T>) mutableTree.getRoot();

        Queue<MutableNode<T>> pendingNodes = new ArrayDeque<>();
        pendingNodes.add(root);
        int currHeight = 1;
        int size = 1;

        while (currHeight < params.getHeight()) {
            int currQueueSize = pendingNodes.size();

            while (currQueueSize > 0) {
                MutableNode<T> currNode = pendingNodes.poll();

                for (int i = 0; i < getNumberOfChildren(); i++) {
                    value = getRandomValue();
                    sum = operations.add(sum, value);

                    MutableNode<T> childNode = new MutableNode<>(operations, value);
                    childNode.setParent(currNode);

                    currNode.getChildren().add(childNode);
                    pendingNodes.add(childNode);
                    size++;
                }

                currQueueSize--;
            }

            currHeight++;
        }

        return new TreeCreationResults<>(size, sum, mutableTree, root);
    }

    /*
    * Returns the following mutable tree:
    *                 1
    *               /   \
    *              2     -4
    *           /  | \    | \
    *          2   1  -5 -10  -20
    *         / \         | \
    *        3  1       -3   2
    */
    public static MutableTree<Integer> getIntegerMutableTree() {
        MutableNode<Integer> root = new MutableNode<>(Operations.getIntegerOperations(), 1);

        MutableNode<Integer> node1 = new MutableNode<>(Operations.getIntegerOperations(),2);
        node1.setParent(root);

        MutableNode<Integer> node2 =  new MutableNode<>(Operations.getIntegerOperations(),-4);
        node2.setParent(root);

        root.getChildren().add(node1);
        root.getChildren().add(node2);

        MutableNode<Integer> node3 = new MutableNode<>(Operations.getIntegerOperations(),2);
        MutableNode<Integer> node4 = new MutableNode<>(Operations.getIntegerOperations(),1);
        MutableNode<Integer> node5 = new MutableNode<>(Operations.getIntegerOperations(),-5);
        node3.setParent(node1);
        node4.setParent(node1);
        node5.setParent(node1);

        node1.getChildren().add(node3);
        node1.getChildren().add(node4);
        node1.getChildren().add(node5);

        MutableNode<Integer> node6 = new MutableNode<>(Operations.getIntegerOperations(),3);
        MutableNode<Integer> node7 = new MutableNode<>(Operations.getIntegerOperations(),1);
        node6.setParent(node3);
        node7.setParent(node3);

        node3.getChildren().add(node6);
        node3.getChildren().add(node7);

        MutableNode<Integer> node8 = new MutableNode<>(Operations.getIntegerOperations(),-10);
        MutableNode<Integer> node9 = new MutableNode<>(Operations.getIntegerOperations(),-20);
        node8.setParent(node2);
        node9.setParent(node2);

        node2.getChildren().add(node8);
        node2.getChildren().add(node9);

        MutableNode<Integer> node10 = new MutableNode<>(Operations.getIntegerOperations(),-3);
        MutableNode<Integer> node11 = new MutableNode<>(Operations.getIntegerOperations(),2);
        node10.setParent(node8);
        node11.setParent(node8);
        node8.getChildren().add(node10);
        node8.getChildren().add(node11);

        MutableTree<Integer> tree = new MutableTree<Integer>(Operations.getIntegerOperations(), root);

        return tree;
    }

    /*
     * Returns the following immutable tree:
     *                 1
     *               /   \
     *              2     -4
     *           /  | \    | \
     *          2   1  -5 -10  -20
     *         / \         | \
     *        3  1       -3   2
     */
    public static ImmutableTree<Integer> getIntegerImmutableTree() {
        ImmutableNode<Integer> root = new ImmutableNode<>(Operations.getIntegerOperations(),1);

        ImmutableNode<Integer> node1 = new ImmutableNode<>(Operations.getIntegerOperations(),2, new ArrayList<>(), root);
        ImmutableNode<Integer> node2 =  new ImmutableNode<>(Operations.getIntegerOperations(),-4, new ArrayList<>(), root);

        root.getChildren().add(node1);
        root.getChildren().add(node2);

        ImmutableNode<Integer> node3 = new ImmutableNode<>(Operations.getIntegerOperations(),2, new ArrayList<>(), node1);
        ImmutableNode<Integer> node4 = new ImmutableNode<>(Operations.getIntegerOperations(),1, new ArrayList<>(), node1);
        ImmutableNode<Integer> node5 = new ImmutableNode<>(Operations.getIntegerOperations(),-5, new ArrayList<>(), node1);

        node1.getChildren().add(node3);
        node1.getChildren().add(node4);
        node1.getChildren().add(node5);

        ImmutableNode<Integer> node6 = new ImmutableNode<>(Operations.getIntegerOperations(),3, new ArrayList<>(), node3);
        ImmutableNode<Integer> node7 = new ImmutableNode<>(Operations.getIntegerOperations(),1, new ArrayList<>(), node3);

        node3.getChildren().add(node6);
        node3.getChildren().add(node7);

        ImmutableNode<Integer> node8 = new ImmutableNode<>(Operations.getIntegerOperations(),-10, new ArrayList<>(), node2);
        ImmutableNode<Integer> node9 = new ImmutableNode<>(Operations.getIntegerOperations(),-20, new ArrayList<>(), node2);

        node2.getChildren().add(node8);
        node2.getChildren().add(node9);

        ImmutableNode<Integer> node10 = new ImmutableNode<>(Operations.getIntegerOperations(),-3, new ArrayList<>(), node8);
        ImmutableNode<Integer> node11 = new ImmutableNode<>(Operations.getIntegerOperations(),2, new ArrayList<>(), node8);

        node8.getChildren().add(node10);
        node8.getChildren().add(node11);

        ImmutableTree<Integer> tree = new ImmutableTree<>(Operations.getIntegerOperations(), root);

        return tree;
    }
}
