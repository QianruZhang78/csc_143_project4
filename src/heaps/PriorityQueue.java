package heaps;

import lib.Pair;

import java.util.Queue;

/**
 * A Priority Queue is a kind of queue where every element has a priority,
 * and the "highest priority" (lower values are higher priority) elements
 * are always removed first. We will be using Integers as priorities.
 *
 * In this Priority Queue implementation, the internal ordering of elements
 * of equal priority is arbitrary. In other words, elements inserted with
 * equal priority are not necessarily removed in FIFO order. We only need
 * to guarantee that the highest priority elements are removed before lower
 * priority elements.
 *
 * @param <T> The type of the elements stored in the queue.
 *
 * Note: PriorityQueue<T> should contain, not extend, Heap.
 *       (What's the difference?)
 */
public class PriorityQueue<T> {

    /*
        A Heap compares the raw values it stores with the Comparable<T> interface,
        but a Priority Queue orders values *by priority* in FIFO order,
        regardless of how the values themselves compare with one another.

        We need a way to associate priority with value: the Pair<L, R>
        class is an excellent starting point. Furthermore, we need to
        have the Heap compare each entry by their priority.
     */
    private static class ComparablePair<L extends Comparable<L>, R>
            extends Pair<L, R>
            implements Comparable<ComparablePair<L, R>> {

        public ComparablePair(L left, R right) {
            super(left, right);
        }

        @Override
        public int compareTo(ComparablePair<L, R> o) {
            /* YOUR CODE HERE */
            return -1;
        }
    }

    MinHeap<ComparablePair<Integer, T>> heap = new MinHeap<>();

    /*
        The reference solution to each of the following methods is one-line each.

        Do NOT add properties or methods to MinHeap to specifically accommodate PriorityQueue;
        that would be a violation of the Open-Closed principle (why?).
     */

    public void insert(Integer priority, T value) {
        /* YOUR CODE HERE */
    }

    public T peek() {
        /* YOUR CODE HERE */
        return null;
    }

    public T remove() {
        /* YOUR CODE HERE */
        return null;
    }

    public int size() {
        /* YOUR CODE HERE */
        return 0;
    }
}
