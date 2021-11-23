package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    public int fillCount() {
        return this.fillCount;
    }

    public int capacity() {
        return this.capacity;
    }
}
