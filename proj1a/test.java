import org.junit.Test;

import java.util.ArrayList;

public class test {
    @Test
    public void test() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);
        arrayDeque.addLast(4);
        int val1 = arrayDeque.removeFirst();     //==> 1
        int val2 = arrayDeque.removeLast();     //==> 4
        int a = arrayDeque.get(0);      //==> 0
        int b = arrayDeque.get(0);      //==> 0
        arrayDeque.addLast(9);
        int c = arrayDeque.get(1);      //==> 2
        arrayDeque.removeFirst();     //==> 0
        arrayDeque.removeFirst();
    }
}
