package test;

import heaps.MinHeap;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

import static junit.framework.TestCase.*;

public class HeapTest {
    public <T extends Comparable<T>> void validateHeap(MinHeap<T> heap) {
        validateHeapOrdering(heap.container, 1);
        validateHeapComplete(heap);
    }

    public <T extends Comparable<T>> void validateHeapOrdering(ArrayList<T> heapContainer, int rootIndex) {
        int leftChildIndex = rootIndex * 2;
        int rightChildIndex = rootIndex * 2 + 1;

        if (leftChildIndex < heapContainer.size()) {
            assertTrue(heapContainer.get(rootIndex).compareTo(heapContainer.get(leftChildIndex)) <= 0);
            validateHeapOrdering(heapContainer, leftChildIndex);
        }

        if (rightChildIndex < heapContainer.size()) {
            assertTrue(heapContainer.get(rootIndex).compareTo(heapContainer.get(rightChildIndex)) <= 0);
            validateHeapOrdering(heapContainer, rightChildIndex);
        }
    }

    public <T extends Comparable<T>> void validateHeapComplete(MinHeap<T> heap) {
        assertNull(heap.container.get(0));

        int i;
        for (i = 1; i < heap.container.size(); ++i) {
            assertNotNull(heap.container.get(i));
        }

        assertEquals(i - 1, heap.size());
        assertEquals(heap.size() + 1, heap.container.size());
    }

    @Test
    public void HeapZeroElementTest() {
        MinHeap<Double> heap = new MinHeap<>();
        assertEquals(0, heap.size());

        try {
            heap.peek();
            fail();
        } catch (NoSuchElementException e) {
        }

        try {
            heap.remove();
            fail();
        } catch (NoSuchElementException e) {
        }

        validateHeap(heap);
    }
    @Test
    public void HeapOneElementTest() {
        MinHeap<Integer> heap = new MinHeap<>();
        assertEquals(0, heap.size());

        heap.insert(0);
        assertEquals(1, heap.size());
        validateHeap(heap);

        assertEquals(Integer.valueOf(0), heap.peek());

        MinHeap<String> otherHeap = new MinHeap<>();
        assertEquals(0, otherHeap.size());

        otherHeap.insert("A");
        assertEquals(1, otherHeap.size());
        validateHeap(otherHeap);

        assertEquals("A", otherHeap.peek());
        assertEquals("A", otherHeap.remove());
        assertEquals(0, otherHeap.size());
        validateHeap(otherHeap);
    }

    @Test
    public void HeapTwoElementTest() {
        MinHeap<Integer> heap = new MinHeap<>();
        assertEquals(0, heap.size());

        heap.insert(1);
        heap.insert(0);
        assertEquals(2, heap.size());
        validateHeap(heap);

        assertEquals(Integer.valueOf(0), heap.peek());
        assertEquals(Integer.valueOf(0), heap.remove());

        assertEquals(1, heap.size());
        validateHeap(heap);
        assertEquals(Integer.valueOf(1), heap.peek());
        assertEquals(Integer.valueOf(1), heap.remove());

        assertEquals(0, heap.size());
        validateHeap(heap);
    }

    @Test
    public void HeapThreeElementTest() {
        MinHeap<BigInteger> heap = new MinHeap<>();
        assertEquals(0, heap.size());

        heap.insert(BigInteger.TEN);
        heap.insert(BigInteger.TWO);
        heap.insert(BigInteger.ONE);

        assertEquals(Arrays.asList(BigInteger.ONE, BigInteger.TEN, BigInteger.TWO),
                     heap.container.subList(1, heap.container.size()));
        validateHeap(heap);

        assertEquals(BigInteger.ONE, heap.peek());
        assertEquals(BigInteger.ONE, heap.remove());

        assertEquals(2, heap.size());
        validateHeap(heap);

        assertEquals(BigInteger.TWO, heap.peek());
        assertEquals(BigInteger.TWO, heap.remove());

        assertEquals(1, heap.size());
        validateHeap(heap);
        heap.insert(BigInteger.ONE);
        heap.insert(BigInteger.ONE);

        assertEquals(Arrays.asList(BigInteger.ONE, BigInteger.TEN, BigInteger.ONE),
                heap.container.subList(1, heap.container.size()));
        validateHeap(heap);

        assertEquals(BigInteger.ONE, heap.remove());
        assertEquals(BigInteger.ONE, heap.remove());
        assertEquals(BigInteger.TEN, heap.remove());

        assertEquals(0, heap.size());
        validateHeap(heap);
    }

    @Test
    public void HeapSixElementTest() {
        MinHeap<Integer> heap = new MinHeap<>();
        assertEquals(0, heap.size());

        heap.insert(10);
        heap.insert(8);
        heap.insert(2);

        assertEquals(Arrays.asList(2, 10, 8), heap.container.subList(1, heap.container.size()));

        heap.insert(6);
        heap.insert(12);

        assertEquals(Arrays.asList(2, 6, 8, 10, 12), heap.container.subList(1, heap.container.size()));

        heap.insert(1);

        assertEquals(Arrays.asList(1, 6, 2, 10, 12, 8), heap.container.subList(1, heap.container.size()));
        validateHeap(heap);

        assertEquals(Integer.valueOf(1), heap.remove());
        assertEquals(Arrays.asList(2, 6, 8, 10, 12), heap.container.subList(1, heap.container.size()));
        validateHeap(heap);

        heap.insert(2);
        assertEquals(Arrays.asList(2, 6, 2, 10, 12, 8), heap.container.subList(1, heap.container.size()));
        validateHeap(heap);
    }

    @Test
    public void HeapSuperTest() {
        MinHeap<Integer> heap = new MinHeap<>();
        Random rand = new Random(0);
        for (int i = 0; i < 1000; ++i) {
            heap.insert(rand.nextInt(100));
            validateHeap(heap);
        }

        assertEquals(1000, heap.size());

        for (int i = 0; i < 1000; ++i) {
            Integer minValue = Integer.MAX_VALUE;
            int originalSize = heap.size();

            for (int j = 1; j < heap.container.size(); ++j) {
                if (heap.container.get(j).compareTo(minValue) < 0) {
                    minValue = heap.container.get(j);
                }
            }

            assertEquals(minValue, heap.remove());
            assertEquals(originalSize - 1, heap.size());
            validateHeap(heap);
        }
    }
}
