package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    @Override
    public int fillCount() {
        return this.fillCount;
    }
    @Override
    public int capacity() {
        return this.capacity;
    }
}
