package tree.maximizer;

/**
 * DpCell - the cell in the DP matrix which is used in the maximize with no more than K steps algorithm.
 * DP = Dynamic Programming.
 * @param <T> The type of values in nodes of a tree.
 */
public class DpCell <T extends Number>  {
    /**
     * The value in the DP cell
     */
    private T value;

    /**
     * The value of the parent index. Parent index is the index from the previous row in the DP matrix, from where
     * we reached the maximum profit in the current DP cell.
     */
    private int parentIndex;


    public DpCell(T value, int parentIndex) {
        this.value = value;
        this.parentIndex = parentIndex;
    }

    public DpCell(DpCell<T> otherCell) {
        this.value = otherCell.getValue();
        this.parentIndex = otherCell.getParentIndex();
    }


    public void setValue(T value) {
        this.value = value;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public T getValue() {
        return value;
    }
}
