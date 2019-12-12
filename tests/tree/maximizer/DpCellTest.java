package tree.maximizer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DpCellTest {
    private int value = 123;
    private int parentIndex = -1;

    private DpCell<Integer> dpCell;

    public DpCellTest() {
        dpCell = new DpCell<>(value, parentIndex);
    }

    @Test
    void setValue() {
        int value = 1;

        dpCell.setValue(value);

        assertEquals(dpCell.getValue(), value);
    }

    @Test
    void setParentIndex() {
        int parentIndex = 1;

        dpCell.setParentIndex(parentIndex);

        assertEquals(dpCell.getParentIndex(), parentIndex);
    }

    @Test
    void getParentIndex() {
        int parentIndex = 1;

        dpCell.setParentIndex(parentIndex);

        assertEquals(dpCell.getParentIndex(), parentIndex);
    }

    @Test
    void getValue() {
        int value = 1;

        dpCell.setValue(value);

        assertEquals(dpCell.getValue(), value);
    }
}