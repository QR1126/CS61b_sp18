
/**
 * @author QR1126
 */

public class LinkedListDeque<T> implements Deque<T>{

    private class Node {
        private Node prev;
        private Node next;
        private T val;

        public Node() {
        }

        public Node(T val) {
            this.val = val;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkedListDeque() {
        this.head = new Node();
        this.tail = new Node();
        this.size = 0;
        head.next = tail;
        tail.prev = head;
    }

    /**Adds an item of type T to the front of the deque.
     * */
    @Override
    public void addFirst(T item) {
        Node nxtNode = head.next;
        Node node = new Node(item);
        head.next = node;
        node.prev = head;
        node.next = nxtNode;
        nxtNode.prev = node;
        size++;
    }
    /** Adds an item of type T to the back of the deque.
     * */
    @Override
    public void addLast(T item) {
        Node preNode = tail.prev;
        Node node = new Node(item);
        tail.prev = node;
        node.next = tail;
        node.prev = preNode;
        preNode.next = node;
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
   /** Prints the items in the deque from
    * first to last, separated by a space.
    * */
    @Override
    public void printDeque() {
        Node cur = head.next;
        if (cur == tail) {
            System.out.println("This list is null");
        }
        while (cur != tail) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
    }
   /** Removes and returns the item at the front of the deque.
    * If no such item exists, returns null.
   * */
    @Override
    public T removeFirst()  {
        if (this.isEmpty()) {
            return null;
        } else {
            Node node = head.next;
            T val = node.val;
            Node nxtNode = node.next;
            head.next = nxtNode;
            nxtNode.prev = head;
            //node = null;
            size--;
            return val;
        }
    }
    /**Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.*/
    @Override
    public T removeLast()  {
        if (this.isEmpty()) {
            return null;
        } else {
            Node node = tail.prev;
            T val = node.val;
            Node preNode = node.prev;
            tail.prev = preNode;
            preNode.next = tail;
            size--;
            return val;
        }
    }
    /**Gets the item at the given index, where 0 is the front,
     * 1 is the next item,
     * and so forth. If no such item exists, returns null.
     * Must not alter the deque!*/
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        int cnt = 0;
        Node cur = head.next;
        while (cnt < index) {
            cur = cur.next;
            cnt++;
        }
        return cur.val;
    }
    /**  Same as get, but uses recursion.*/
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int cnt = 0;
        Node node = head.next;
        return getHelper(index, cnt, node);
    }

    private T getHelper(int index, int cnt, Node node) {
        if (index == cnt) {
            return node.val;
        }
        return getHelper(index, cnt + 1, node.next);
    }
}
