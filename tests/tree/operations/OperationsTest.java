package tree.operations;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {
    private static Operations<Integer> integerOperations = Operations.getIntegerOperations();

    @Test
    void setRandomizer() {
        Randomizer<Integer> randomizer = (leftBorder, rightBorder) -> {
            return 1;
        };

        assertNotEquals(integerOperations.getRandomizer(), randomizer);

        integerOperations.setRandomizer(randomizer);

        assertEquals(integerOperations.getRandomizer(), randomizer);
        assertEquals(integerOperations.getRandomValue(0, 0), 1);
    }

    @Test
    void setZero() {
        integerOperations.setZero(2);

        assertEquals(2, integerOperations.getZero());

        integerOperations.setZero(3);

        assertEquals( 3, integerOperations.getZero());

        integerOperations.setZero(0);

        assertEquals(0, integerOperations.getZero());
    }

    @Test
    void setAdder() {
        BinaryOperator<Integer> adder = (firstValue, secondValue) ->  {
            return 1;
        };

        assertNotEquals(integerOperations.getAdder(), adder);

        integerOperations.setAdder(adder);

        assertEquals(integerOperations.getAdder(), adder);
    }

    @Test
    void setSubtractor() {
        BinaryOperator<Integer> subtractor = (firstValue, secondValue) ->  {
            return 1;
        };

        assertNotEquals(integerOperations.getSubtractor(), subtractor);

        integerOperations.setSubtractor(subtractor);

        assertEquals(integerOperations.getSubtractor(), subtractor);
    }

    @Test
    void setComparator() {
        Comparator<Integer> comparator = (firstValue, secondValue) ->  {
            return 0;
        };

        assertNotEquals(integerOperations.getComparator(), comparator);

        integerOperations.setComparator(comparator);

        assertEquals(integerOperations.getComparator(), comparator);
    }

    @Test
    void setNeg() {
        UnaryOperator<Integer> negotiator = value -> {
              return -value;
        };

        assertNotEquals(integerOperations.getNegotiator(), negotiator);

        integerOperations.setNeg(negotiator);

        assertEquals(integerOperations.getNegotiator(), negotiator);
    }

    @Test
    void add() {
        assertEquals(5, integerOperations.add(2, 3));
        assertNotEquals(0, integerOperations.add(2, 3));
        assertEquals(4, integerOperations.add(2, 2));
        assertEquals(5, integerOperations.add(5, 0));
    }

    @Test
    void sub() {
        assertEquals(integerOperations.sub(5, 4), 1);
        assertEquals(integerOperations.sub(4, 5), -1);
        assertEquals(integerOperations.sub(10, 0), 10);
        assertEquals(integerOperations.sub(0, 10), -10);
    }

    @Test
    void compare() {
        Comparator<Integer> comparator = (firstValue, secondValue) -> {
            if (firstValue == secondValue) {
                return 0;
            }

            if (firstValue > secondValue) {
                return 1;
            }

            return -1;
        };

        integerOperations.setComparator(comparator);

        assertEquals(integerOperations.compare(0, 0), 0);
        assertEquals(integerOperations.compare(1, 0), 1);
        assertEquals(integerOperations.compare(0, 1), -1);
    }

    @Test
    void negotiate() {
        assertEquals(integerOperations.negotiate(5), -5);
        assertEquals(integerOperations.negotiate(-5), 5);
        assertEquals(integerOperations.negotiate(0), 0);
    }

    @Test
    void getZero() {
        assertEquals(integerOperations.getZero(), 0);
    }

    @Test
    void getRandomValue() {
        Randomizer<Integer> randomizer = (leftBorder, rightBorder) -> {
            return 1;
        };

        integerOperations.setRandomizer(randomizer);

        assertEquals(integerOperations.getRandomValue(0, 0), 1);
    }

    @Test
    void getIntegerOperations() {
        assertNotEquals(Operations.getIntegerOperations(), null);
    }
}