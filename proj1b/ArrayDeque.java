/**
 * @author QR1126
 */
public class ArrayDeque<T> implements Deque<T> {

    private T[] arr;
    private int head;
    private int tail;
    private int size;
    private int length;
    private static final int DEFAULT_CAPACITY = 8;

    public ArrayDeque() {
        this.arr = (T[]) new Object[DEFAULT_CAPACITY];
        head = tail = size = 0;
        length = DEFAULT_CAPACITY;
    }

    /**Adds an item of type T to the front of the deque.
     * */
    @Override
    public void addFirst(T item) {
        if (size == arr.length) {
            resize(arr.length << 1);
        }
        arr[head = (head - 1) & (arr.length - 1)] = item;
        size++;
    }

    private void resize(int newCapacity) {
        T[] newArr = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[(head + i) % arr.length];
        }
        this.arr = newArr;
        length = newCapacity;
        head = 0;
        tail = size;
    }

    /** Adds an item of type T to the back of the deque.
     * */
    @Override
    public void addLast(T item) {
        if (size == length) {
            resize(length << 1);
        }
        arr[tail] = item;
        tail = (tail + 1) % length;
        size++;
    }

    /**Returns true if deque is empty, false otherwise.
     * */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**Returns the number of items in the deque.
     * */
    @Override
    public int size() {
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space.
     * */
    @Override
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("null");
        } else {
            for (int i = head; i != tail; ) {
                System.out.print(arr[i] + " ");
                i = (i + 1) % length;
            }
        }
    }
    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T val = arr[head];
            arr[head] = null;
            head = (head + 1) & (length - 1);
            size--;
            if (size == length / 4 && size / 2 != 0) {
                resize(length >> 1);
            }
            return val;
        }
    }

    /**Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    @Override
    public T removeLast()  {
        //Making sure removing from empty deque doesn't give negative size.
        if (isEmpty()) {
            return null;
        } else {
            tail = tail == 0 ? length - 1 : tail - 1;
            T val = arr[tail];
            arr[tail] = null;
            size--;
            if (size == length / 4 && size / 2 != 0) {
                resize(length >> 1);
            }
            return val;
        }
    }
    /**Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such item exists, returns null. Must not alter the deque!*/
    @Override
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        } else {
            int cur = head;
            for (int i = 0; i <= index; i++) {
                cur = (head + i) % length;
            }
            return arr[cur];
        }
    }
}
