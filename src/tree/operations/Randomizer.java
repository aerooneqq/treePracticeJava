package tree.operations;

public interface Randomizer<T extends Number> {
    T getRandomValue(T leftBorder, T rightBorder);
}
