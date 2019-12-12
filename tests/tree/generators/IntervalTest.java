package tree.generators;

import org.junit.jupiter.api.Test;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

    @Test
    void setLeftBorder() {
        var interval = new Interval<Integer>(Operations.getIntegerOperations());
        interval.setLeftBorder(-34);

        assertEquals(interval.leftBorder, -34);
    }

    @Test
    void setRightBorder() {
        var interval = new Interval<Integer>(Operations.getIntegerOperations());
        interval.setRightBorder(34);

        assertEquals(interval.rightBorder, 34);
    }

    @Test
    void getLeftBorder() {
        var interval = new Interval<Integer>(Operations.getIntegerOperations());
        interval.setLeftBorder(-34);

        assertEquals(interval.leftBorder, -34);
        assertEquals(interval.getLeftBorder(), interval.leftBorder);
    }

    @Test
    void getRightBorder() {
        var interval = new Interval<Integer>(Operations.getIntegerOperations());
        interval.setRightBorder(34);

        assertEquals(interval.rightBorder, 34);
        assertEquals(interval.getRightBorder(), interval.rightBorder);
    }
}