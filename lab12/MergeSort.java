import edu.princeton.cs.algs4.Queue;

import java.util.Iterator;

public class MergeSort {

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        Queue<String> res = mergeSort(students);
        System.out.println(res);
    }

    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
                    makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> res = new Queue<>();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            Queue<Item> q = new Queue<>();
            q.enqueue(item);
            res.enqueue(q);
        }
        return res;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> res = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            res.enqueue(getMin(q1, q2));
        }
        return res;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest.
        Top-Down merge sorting N items:
        1.Split items into 2 roughly even pieces.
        2.Mergesort each half (steps not shown, this is a recursive algorithm!)
        3.Merge the two sorted halves to form the final result.
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> queues = makeSingleItemQueues(items);
        while (queues.size() > 1) {
            Queue<Item> q1 = queues.dequeue();
            Queue<Item> q2 = queues.dequeue();
            Queue<Item> queue = mergeSortedQueues(q1, q2);
            queues.enqueue(queue);
        }
        return queues.dequeue();
    }
}
