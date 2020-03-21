package heaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Min-heap implementation where the smallest element is the root of the heap.
 * The heap should be stored in an ArrayList<T> container, with a null element
 * at index 0, and the root element starting at index 1. Use the indexing
 * formulas discussed in class to retrieve and assign child nodes.
 *
 * @param <T> The generic type stored in the heap. Must be a Comparable type.
 *            For more information on the Comparable interface, please read:
 *            https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Comparable.html
 */
public class MinHeap<T extends Comparable<T>> {
    public ArrayList<T> container = new ArrayList<>();
    /* YOUR CODE HERE */

    public MinHeap() {
        container.add(0, null);
    }

    public MinHeap(Collection<T> collection) {
        container.add(0, null);
    }

    private int getParentIndex(int childIndex) {
        return childIndex / 2;
    }

    private int getLeftChildIndex(int parentIndex) {
        return parentIndex * 2;
    }

    private int getRightChildIndex(int parentIndex) {
        return getLeftChildIndex(parentIndex) + 1;
    }

    private void swap(int left, int right) {
        T leftValue = container.get(left);
        container.set(left, container.get(right));
        container.set(right, leftValue);
    }

    /**
     * Inserts a value into the heap, "bubbling up" to the correct position.
     *
     * @param value The value to be inserted.
     */
    public void insert(T value) {
        /* YOUR CODE HERE */
        container.add(value);
        int index = container.size() - 1;
        while (getParentIndex(index) > 0 && container.get(getParentIndex(index)).compareTo(value) > 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }

    }

    public T peek() {
        /* YOUR CODE HERE */
        if (container.size() == 1) {
            throw new NoSuchElementException("no elements in the heap");
        }
        return container.get(1);
    }

    public T remove() {
        /* YOUR CODE HERE */
        if (container.size() == 1) {
            throw new NoSuchElementException("no elements in the heap");
        }
        T result = container.get(1);
        container.set(1, container.get(container.size() - 1));
        container.remove(container.size() - 1);
        int index = 1;
        int leftIndex = getLeftChildIndex(index);
        int rightIndex = getRightChildIndex(index);
        while (leftIndex < container.size()) {
            T left = container.get(leftIndex);
            T right = null;
            if (rightIndex < container.size()) {
                right = container.get(rightIndex);
            }
            if (isLeftSmallerThanRight(left, right)) {
                if (container.get(index).compareTo(container.get(leftIndex)) > 0) {
                    swap(index, leftIndex);
                    index = leftIndex;

                } else {
                    break;
                }
            } else {
                if (container.get(index).compareTo(container.get(rightIndex)) > 0) {
                    swap(index, rightIndex);
                    index = rightIndex;
                } else {
                    break;
                }
            }
            leftIndex = getLeftChildIndex(index);
            rightIndex = getRightChildIndex(index);

        }
        return result;
    }

    private boolean isLeftSmallerThanRight(T left, T right) {
        if (right == null) {
            return true;
        }
        return left.compareTo(right) <= 0;
    }

    public int size() {
        /* YOUR CODE HERE */
        return container.size() - 1;
    }
}
