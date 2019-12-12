package tree.operations;

import java.util.Comparator;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * This is the class which represents the arithmetic logic in this project.
 * Operations class is used in all trees' classes, TreeGenerator, nodes' classes
 * @param <T> The type of nodes' values.
 */
public class Operations<T extends Number> {
    private static final Random random = new Random();

    private BinaryOperator<T> adder;
    private BinaryOperator<T> subtractor;
    private Comparator<T> comparator;
    private UnaryOperator<T> negotiator;
    private Randomizer<T> randomizer;
    private T zero;
    private T negativeInfinity;


    public Operations() { }

    public Operations(BinaryOperator<T> adder, BinaryOperator<T> subtractor, Comparator<T> comparator,
                      UnaryOperator<T> negotiator, T zero, Randomizer<T> randomizer, T negativeInfinity) {
        this.adder = adder;
        this.subtractor = subtractor;
        this.comparator = comparator;
        this.negotiator = negotiator;
        this.zero = zero;
        this.randomizer = randomizer;
        this.negativeInfinity = negativeInfinity;
    }


    //We use a built-in builder pattern here in order to construct the Operations object.
    public Operations<T> setRandomizer(Randomizer<T> randomizer) {
        this.randomizer = randomizer;
        return this;
    }

    public Operations<T> setZero(T zero) {
        this.zero = zero;
        return this;
    }

    public Operations<T> setAdder(BinaryOperator<T> adder) {
        this.adder = adder;
        return this;
    }

    public Operations<T> setSubtractor(BinaryOperator<T> subtractor) {
        this.subtractor = subtractor;
        return this;
    }

    public Operations<T> setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        return this;
    }

    public Operations<T> setNeg(UnaryOperator<T> negotiator) {
        this.negotiator = negotiator;
        return this;
    }

    public Operations<T> setNegativeInfinity(T negativeInfinity) {
        this.negativeInfinity = negativeInfinity;
        return this;
    }


    public BinaryOperator<T> getAdder() {
        return adder;
    }

    public BinaryOperator<T> getSubtractor() {
        return subtractor;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public UnaryOperator<T> getNegotiator() {
        return negotiator;
    }

    public Randomizer<T> getRandomizer() {
        return randomizer;
    }

    public T getZero() {
        return zero;
    }

    public T getNegativeInfinity() {
        return negativeInfinity;
    }


    public T add(T firstValue, T secondValue) {
        return adder.apply(firstValue, secondValue);
    }

    public T sub(T firstValue, T secondValue) {
        return subtractor.apply(firstValue, secondValue);
    }

    public int compare(T firstValue, T secondValue) {
        return comparator.compare(firstValue, secondValue);
    }

    public T negotiate(T value) {
        return negotiator.apply(value);
    }

    public T getRandomValue(T leftBorder, T rightBorder) {
        return randomizer.getRandomValue(leftBorder, rightBorder);
    }


    /**
     * Returns default Integer operations
     */
    public static Operations<Integer> getIntegerOperations() {
        BinaryOperator<Integer> adder = (first, second) -> first + second;

        BinaryOperator<Integer> subtractor = (firstValue, secondValue) -> firstValue - secondValue;

        Comparator<Integer> comparator = (firstValue, secondValue) -> {
            if (firstValue.equals(secondValue)) {
                return 0;
            }

            if (firstValue > secondValue) {
                return 1;
            }

            return -1;
        };

        UnaryOperator<Integer> negotiator = (value) -> -value;

        Integer zero = 0;

        Randomizer<Integer> randomizer = (leftBorder, rightBorder) -> {
            return random.nextInt(rightBorder - leftBorder) + leftBorder;
        };


        return new Operations<Integer>().setAdder(adder).setComparator(comparator)
                                        .setZero(0).setRandomizer(randomizer).setNeg(negotiator)
                                        .setNegativeInfinity(-10000000).setSubtractor(subtractor);
    }
}
