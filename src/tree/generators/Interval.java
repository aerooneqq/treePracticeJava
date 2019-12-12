package tree.generators;

import tree.operations.Operations;

public class Interval<T extends Number> {
    protected T leftBorder;
    protected T rightBorder;
    protected Operations<T> operations;


    public Interval(Operations<T> operations) {
        this.operations = operations;
        leftBorder = operations.getZero();
        rightBorder = operations.getZero();
    }

    public Interval(Operations<T> operations, T leftBorder, T rightBorder) throws IllegalArgumentException {
        this.operations = operations;
        if (this.operations.compare(leftBorder, rightBorder) == 1) {
            throw new IllegalArgumentException("Left border can not be greater than right border");
        }
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }


    public void setLeftBorder(T leftBorder) throws IllegalArgumentException {
        if (operations.compare(leftBorder, rightBorder) == 1) {
            throw new IllegalArgumentException("Left border can not be greater than right border");
        }

        this.leftBorder = leftBorder;
    }

    public void setRightBorder(T rightBorder) {
        if (operations.compare(leftBorder, rightBorder) == 1) {
            throw new IllegalArgumentException("Right border can not be less than left border");
        }

        this.rightBorder = rightBorder;
    }

    public T getLeftBorder() {
        return leftBorder;
    }

    public T getRightBorder() {
        return rightBorder;
    }
}
