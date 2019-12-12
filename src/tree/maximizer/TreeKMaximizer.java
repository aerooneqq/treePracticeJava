package tree.maximizer;

import tree.AbstractTree;
import tree.Node;
import tree.maximizer.siblingsSet.SiblingsHashSet;
import tree.operations.Operations;

import java.util.*;

/**
 * The maximizer which is used to solve the problem of maximizing the tree no more than in K steps.
 * DP = Dynamic Programming.
 *
 * @param <T> The type of nodes' values.
 */
public class TreeKMaximizer<T extends Number> implements KMaximizer<T> {

    /**
     * The child parent map which stores all parents of every node in the tree.
     */
    private Map<Node<T>, Set<Node<T>>> childParentMap;

    /**
     * The List of all nodes in the tree.
     */
    private List<Node<T>> nodesCollection;

    /**
     * The DP matrix.
     */
    private ArrayList<ArrayList<DpCell<T>>> dp;

    /**
     * The number of nodes in the tree.
     */
    private int nodesCount;

    private Operations<T> operations;


    public TreeKMaximizer(AbstractTree<T> tree) throws IllegalArgumentException {
        if (tree == null) {
            throw new IllegalArgumentException("Tree can not be null");
        }

        childParentMap = tree.getChildParentMap();
        nodesCollection = tree.getNodesCollection();
        dp = new ArrayList<>();
        operations = tree.getOperationsObject();
        nodesCount = nodesCollection.size();
    }


    /**
     * In order to solve the problem of maximization tree in no more than K steps we create a
     * DP matrix [maxOperationsCount + 1, nodesCount]. Each column corresponds to the node from nodeCollection
     * (if we have a column with index J then it corresponds to the nodesCollection.get(J) node).
     * Each row is a i-th iteration.
     * The first row contains only zeroes.
     * The second row contains the subtree weights of every node.
     *
     * The idea of this DP is that every cell in the DP matrix is a maximum profit we can get after deletion of the
     * node which corresponds to this cell on the i-th iteration (i = cell's row index). To calculate this profit we monitor
     * the i - 1 row, and see what happens if we delete the current node, assuming the fact the we deleted the k-th node from the
     * i - 1 row. Each DP cell has a parentIndex field which tells us from where from the previous row we got the maximum profit.
     * So when we monitor k-th cell from the previous row we check, have we already deleted any parent of the current node. If so,
     * we don't look at this case. If there was no deletion of a parent of the current node, then we calculate the profit. We must
     * be very careful here, because in any previous step we could have deleted the child of the current node, so it's profit will not
     * be just the subtree weight of the current node. We have to look at the deletion history, and if we have deleted the
     * child of this node, we must count it. After that, if the profit is greater than
     * the value in this DP cell, we update it, and also update the parent index of the current cell.
     *
     * The time complexity is O(k^2 * n^2)
     *
     * @return The List of nodes which must be deleted in order to maximize the tree.
     */
    @Override
    public List<Node<T>> maximize(int maxOperationsCount) {
        initializeDpMatrix(maxOperationsCount);

        for (int i = 0; i < nodesCount; i++) {
            dp.get(1).set(i, new DpCell<>(operations.negotiate(nodesCollection.get(i).getSubtreeWeight()), -1));
        }

        for (int i = 2; i < maxOperationsCount + 1; i++) {
            for (int j = 0; j < nodesCount; j++) {
                for (int k = 0; k < nodesCount; k++) {
                    if (j != k) {
                        if (checkIfTheNodeAlreadyInPath(j, i - 1, k)) {
                            if (operations.compare(dp.get(i - 1).get(k).getValue(), dp.get(i).get(j).getValue()) == 1) {
                                dp.get(i).set(j, new DpCell<>(dp.get(i - 1).get(k)));
                            }
                        }
                        else if (!checkIfParentWasDeleted(i, j, k)) {
                            T profit = calculateProfit(nodesCollection.get(j), i -1, k);
                            T oldValue = dp.get(i).get(j).getValue();

                            if (operations.compare(profit, oldValue) == 1) {
                                dp.get(i).set(j, new DpCell<>(profit, k));
                            }
                        }
                    }
                }
            }
        }

        printDp();

        return findAnswerFromDP(maxOperationsCount);
    }

    /**
     * Checks if any parent of the current node already in the path.
     * @param nodeIndex The index of the current node.
     * @param row The row of the previous node from path.
     * @param col The column of the previous node from path.
     * @return True if any parent is in the path.
     */
    private boolean checkIfTheNodeAlreadyInPath(int nodeIndex, int row, int col) {
        int currRow = row;
        int currCol = col;

        while (currRow > 0 && currCol > -1) {
            if (nodeIndex == currCol) {
                return true;
            }

            currCol = dp.get(currRow).get(currCol).getParentIndex();
            currRow--;
        }

        return false;
    }

    /**
     * Creates a list of nodes which must be deleted from the DP matrix.
     * Firstly we find a cell with maximum profit value and then, using the parentIndex, recreate the answer.
     * If the profit is less than zero then we return an empty List.
     * @return The list of nodes which must be deleted.
     */
    private List<Node<T>> findAnswerFromDP(int maxOperationsCount) {
        int maxI = 1;
        int maxJ = 0;

        for (int i = 1; i < maxOperationsCount + 1; i++) {
            for (int j = 0; j < nodesCount; j++) {
                T prevMax = dp.get(maxI).get(maxJ).getValue();

                if (operations.compare(dp.get(i).get(j).getValue(), prevMax) == 1) {
                    maxI = i;
                    maxJ = j;
                }
            }
        }

        if (operations.compare(dp.get(maxI).get(maxJ).getValue(), operations.getZero()) == -1) {
            return new ArrayList<>();
        }

        List<Node<T>> answer = new ArrayList<>();

        int currRow = maxI;
        int currCol = maxJ;

        while (currRow > 0 && currCol > -1) {
            answer.add(nodesCollection.get(currCol));

            currCol = dp.get(currRow).get(currCol).getParentIndex();
            currRow--;
        }

        return answer;
    }


    /**
     * Initializes the DP matrix with zeroes.
     */
    private void initializeDpMatrix(int maxOperationsCount) {
        for (int i = 0; i < maxOperationsCount + 1; i++) {
            dp.add(new ArrayList<>());

            for (int j = 0; j < nodesCount; j++) {
                dp.get(i).add(new DpCell<>(operations.getNegativeInfinity(), -1));
            }
        }
    }

    /**
     * For a given node checks if the parent was deleted.
     * @param row The row of a current node.
     * @param currNodeJ The column of the current node.
     * @param prevNodeJ The column of the previous node.
     * @return True if parent was deleted, false otherwise.
     */
    private boolean checkIfParentWasDeleted(int row, int currNodeJ, int prevNodeJ) {
        Node<T> currNode = nodesCollection.get(currNodeJ);

        int currDpParentRow = row - 1;
        int currDpParentCol = prevNodeJ;

        while (currDpParentRow > 0 && currDpParentCol > -1) {
            Node<T> currParent = nodesCollection.get(currDpParentCol);

            if (childParentMap.get(currNode).contains(currParent)) {
                return true;
            }

            currDpParentRow--;
            currDpParentCol = dp.get(currDpParentRow + 1).get(currDpParentCol).getParentIndex();
        }

        return false;                   
    }

    /**
     * If the parent of the current node haven't been deleted, we calculate the profit.
     * The profit is the profit we gain from deleting selected nodes from the tree.
     * @param startNode The current node.
     * @param startRow The start row from where we start calculating delta.
     * @param startCol The start column from where we start calculating delta
     */
    private T calculateProfit(Node<T> startNode, int startRow, int startCol) {
        T profit = operations.getZero();

        int currRow = startRow;
        int currCol = startCol;

        Set<Node<T>> nodes = new SiblingsHashSet<>();
        nodes.add(startNode);

        while (currRow > 0 && currCol > -1) {
            Node<T> currNode = nodesCollection.get(currCol);

            nodes.add(currNode);

            currRow--;
            currCol = dp.get(currRow + 1).get(currCol).getParentIndex();
        }

        for (Node<T> node : nodes) {
            profit = operations.add(profit, node.getSubtreeWeight());
        }

        return operations.negotiate(profit);
    }


    /**
     * Prints the DP matrix in DEBUG purposes.
     */
    private void printDp() {
        for (ArrayList<DpCell<T>> arrayList : dp) {
            for (DpCell<T> dpCell : arrayList) {
                System.out.print(dpCell.getValue() + " ");
            }

            System.out.println();
        }
    }
}