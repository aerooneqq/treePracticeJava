package tree.generators;

import org.junit.jupiter.api.Test;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class TreeGeneratorParamsTest {
    private static final Operations<Integer> operations = Operations.getIntegerOperations();
    private static final Interval<Integer> interval = new Interval<Integer>(operations);
    private static final int maxChildrenCount = 3;
    private static final int height = 12;


    @Test
    void getHeight() {
        TreeGeneratorParams<Integer> params = new TreeGeneratorParams<>(height, maxChildrenCount, interval);
        assertEquals(params.getHeight(), height);
    }

    @Test
    void getMaxChildrenCount() {
        TreeGeneratorParams<Integer> params = new TreeGeneratorParams<>(height, maxChildrenCount, interval);
        assertEquals(params.getMaxChildrenCount(), maxChildrenCount);
    }

    @Test
    void getValuesInterval() {
        TreeGeneratorParams<Integer> params = new TreeGeneratorParams<>(height, maxChildrenCount, interval);
        assertEquals(params.getValuesInterval(), interval);
    }
}