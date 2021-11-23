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
    @Override
    public abstract T peek();
    @Override
    public abstract T dequeue();
    @Override
    public abstract void enqueue(T x);
}
