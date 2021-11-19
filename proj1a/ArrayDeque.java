/**
 * @author QR1126
 */
public class ArrayDeque<T> {

    private T[] arr;
    private int head;
    private int tail;
    private int size;
    private int length;
    private final static int DEFAULT_CAPACITY = 8;

    public ArrayDeque() {
        this.arr = (T[]) new Object[DEFAULT_CAPACITY];
        head = tail = size = 0;
        length = DEFAULT_CAPACITY;
    }

    public ArrayDeque(int capacity) {
        this.arr = (T[]) new Object[capacity];
        length = capacity;
    }

    /**Adds an item of type T to the front of the deque.
     * */
    public void addFirst(T item) {
        if (size == arr.length) {
            resize();
        }
        arr[head = (head - 1) & (arr.length - 1)] = item;
        size ++;
    }

    private void resize() {
        T[] newArr = (T[]) new Object[arr.length << 1];
        for(int i = 0;i < arr.length;i ++) {
            newArr[i] = arr[(head + i) % arr.length];
        }
        this.arr = newArr;
        head = 0;
        tail = size;
    }

    /** Adds an item of type T to the back of the deque.
     * */
    public void addLast(T item) {
        if (size == length) {
            resize();
        }
        arr[tail] = item;
        tail = (tail + 1) % length;
        size ++;
    }

    /**Returns true if deque is empty, false otherwise.
     * */
    public boolean isEmpty() {
        return size == 0;
    }

    /**Returns the number of items in the deque.
     * */
    public int size() {
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space.
     * */
    public void printDeque() throws Exception {
        if (isEmpty()) {
            throw new Exception("empty");
        } else {
            for (int i = head;i != tail; ) {
                System.out.print(arr[i] + " ");
                i = (i + 1) % length;
            }
        }
    }
    /** Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * */
    public T removeFirst() {
        T val = arr[head];
        arr[head] = null;
        head = (head + 1) & (length - 1);
        size --;
        return val;
    }

    /**Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast()  {
        T val = arr[tail];
        arr[tail] = null;
        tail = tail == 0 ? length - 1 : tail --;
        size --;
        return val;
    }
    /**Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        } else {
            int cur = head;
            for(int i = 0;i < index;i ++) {
                cur = (head + i) % length;
            }
            return arr[cur];
        }
    }
}
