package tree.generators;

public class TreeGeneratorParams<T extends Number> {
    private final int height;
    private final int maxChildrenCount;
    private final Interval<T> valuesInterval;


    public TreeGeneratorParams(int height, int maxChildrenCount, Interval<T> valuesInterval) {
        this.height = height;
        this.maxChildrenCount = maxChildrenCount;
        this.valuesInterval = valuesInterval;
    }


    public int getHeight() {
        return height;
    }

    public int getMaxChildrenCount() {
        return maxChildrenCount;
    }

    public Interval<T> getValuesInterval() {
        return valuesInterval;
    }
}
