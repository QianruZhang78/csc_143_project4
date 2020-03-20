package test;

import heaps.PriorityQueue;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;

import static junit.framework.TestCase.*;

public class PriorityQueueTest {
    @Test
    public void PriorityQueueThrowsWhenEmptyTest() {
        PriorityQueue<String> queue = new PriorityQueue<>();

        try {
            queue.peek();
            fail();
        } catch (NoSuchElementException e) { };

        try {
            queue.remove();
            fail();
        } catch (NoSuchElementException e) { }

        queue.insert(0, "A");
        assertEquals(1, queue.size());
        assertEquals("A", queue.peek());
        assertEquals("A", queue.remove());

        try {
            queue.peek();
            fail();
        } catch (NoSuchElementException e) { };

        try {
            queue.remove();
            fail();
        } catch (NoSuchElementException e) { }
    }

    @Test
    public void PriorityQueueSimpleTest() {
        PriorityQueue<String> queue = new PriorityQueue<>();

        queue.insert(1,"A");
        queue.insert(2, "B");
        queue.insert(0, "C");

        assertEquals(3, queue.size());
        assertEquals("C", queue.peek());
        assertEquals("C", queue.remove());
        assertEquals("A", queue.peek());
        assertEquals("A", queue.remove());
        assertEquals("B", queue.peek());
        assertEquals("B", queue.remove());

        assertEquals(0, queue.size());
    }

    @Test
    public void PriorityQueueComplexTest() {
        PriorityQueue<String> queue = new PriorityQueue<>();

        queue.insert(1, "A");
        queue.insert(2, "B");
        queue.insert(1, "C");
        queue.insert(3, "D");
        queue.insert(2, "E");
        queue.insert(0, "F");
        queue.insert(2, "G");

        assertEquals(7, queue.size());

        assertEquals("F", queue.peek());
        assertEquals("F", queue.remove());
        assertEquals(6, queue.size());

        HashSet<String> set = new HashSet<>();
        set.add(queue.remove());
        set.add(queue.remove());

        assertEquals(new HashSet<String>(Arrays.asList("A", "C")), set);
        assertEquals(4, queue.size());

        set.clear();
        set.add(queue.remove());
        set.add(queue.remove());
        set.add(queue.remove());

        assertEquals(new HashSet<String>(Arrays.asList("B", "E", "G")), set);
        assertEquals(1, queue.size());

        assertEquals("D", queue.peek());
        assertEquals("D", queue.remove());
        assertEquals(0, queue.size());

        queue.insert(1,"A");
        queue.insert(2, "B");
        queue.insert(0, "C");

        assertEquals(3, queue.size());
        assertEquals("C", queue.peek());
        assertEquals("C", queue.remove());
        assertEquals("A", queue.peek());
        assertEquals("A", queue.remove());
        assertEquals("B", queue.peek());
        assertEquals("B", queue.remove());

        assertEquals(0, queue.size());
    }

    @Test
    public void PriorityQueueSuperTest() {
        PriorityQueue<String> queue = new PriorityQueue<>();

        int counter = 0;
        java.util.HashMap<Integer, HashSet<String>> prioritySets = new java.util.HashMap<>();
        for (int i = 0; i <= 100; ++i) {
            prioritySets.put(i, new HashSet<>());
        }

        Random rand = new Random(0);
        for (int i = 0; i < 5000; ++i) {
            Integer action = rand.nextInt(8);
            Integer priority = rand.nextInt(100);

            if (action == 0 || action == 1) {
                if (queue.size() == 0) {
                    for (Integer prioritySetPriority : prioritySets.keySet()) {
                        assertEquals(0, prioritySets.get(prioritySetPriority).size());
                    }

                    continue;
                }

                HashSet<String> minPrioritySet = new HashSet<>();
                for (int j = 0; j <= 100; ++j) {
                    if (prioritySets.get(j).size() != 0) {
                        minPrioritySet = prioritySets.get(j);
                        break;
                    }
                }

                String value = queue.peek();
                assertTrue(minPrioritySet.contains(value));
                assertEquals(value, queue.remove());
                minPrioritySet.remove(value);
                continue;

            }

            String newValue = Integer.valueOf(counter++).toString();
            queue.insert(priority, newValue);
            prioritySets.get(priority).add(newValue);
        }

        int totalEntries = 0;
        for (Integer prioritySetPriority : prioritySets.keySet()) {
            totalEntries += prioritySets.get(prioritySetPriority).size();
        }

        assertEquals(totalEntries, queue.size());
        for (int i = 0; i < totalEntries; ++i) {

            HashSet<String> minPrioritySet = new HashSet<>();
            for (int j = 0; j <= 100; ++j) {
                if (prioritySets.get(j).size() != 0) {
                    minPrioritySet = prioritySets.get(j);
                    break;
                }
            }

            String value = queue.peek();
            assertTrue(minPrioritySet.contains(value));
            assertEquals(value, queue.remove());
            minPrioritySet.remove(value);
        }

        assertEquals(0, queue.size());

        totalEntries = 0;
        for (Integer prioritySetPriority : prioritySets.keySet()) {
            totalEntries += prioritySets.get(prioritySetPriority).size();
        }
        assertEquals(0, totalEntries);
        assertEquals(totalEntries, queue.size());
    }
}
