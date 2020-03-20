package test;

import org.junit.Test;

import java.util.*;

import static algorithms.Sorting.merge;
import static algorithms.Sorting.mergeSort;
import static junit.framework.TestCase.*;

public class SortingTest {
    @Test
    public void MergeSimpleTest() {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        List<Integer> merged = merge(a, b);
        assertEquals(Collections.emptyList(), merged);

        a = new ArrayList<>(Arrays.asList(1));
        merged = merge(a, b);
        assertEquals(a, merged);

        b = new ArrayList<>(Arrays.asList(2));
        merged = merge(a, b);
        assertEquals(Arrays.asList(1, 2), merged);

        a = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9));
        b = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6, 7, 8));
        List<Integer> aCopy = new ArrayList<>(a);
        List<Integer> bCopy = new ArrayList<>(b);

        merged = merge(a, b);
        assertEquals(Arrays.asList(1, 2, 3, 3, 4, 5, 5, 6, 7, 7, 8, 9), merged);

        // merge should not modify original lists
        assertEquals(a, aCopy);
        assertEquals(b, bCopy);
    }

    @Test
    public void MergeComplexTest() {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> c = new ArrayList<>();
        Random rand = new Random(0);

        for (int i = 0; i < 50000; ++i) {
            if (rand.nextInt(2) % 2 == 0) {
                a.add(i);
            } else {
                b.add(i);
            }
            c.add(i);
        }

        assertEquals(c, merge(a, b));
    }

    @Test
    public void MergeSortSimpleTest() {
        ArrayList<Integer> a = new ArrayList<>();
        assertEquals(Collections.emptyList(), mergeSort(a));

        a = new ArrayList<>(Arrays.asList(0));
        assertEquals(Collections.singletonList(0), mergeSort(a));

        a.add(1);
        a.add(2);
        assertEquals(Arrays.asList(0, 1, 2), mergeSort(a));

        a.set(0, 2);
        a.set(1, 1);
        a.set(2, 0);
        assertEquals(Arrays.asList(0, 1, 2), mergeSort(a));

        assertEquals(Arrays.asList(5, 6, 7, 8, 9, 10), mergeSort(new ArrayList<>(Arrays.asList(9, 8, 7, 10, 6, 5))));
    }

    @Test
    public void MergeSortComplexTest() {
        ArrayList<Integer> a = new ArrayList<>();
        Random rand = new Random(0);

        for (int i = 0; i < 10000; ++i) {
            a.add(rand.nextInt(1000));
        }

        ArrayList<Integer> b = mergeSort(a);
        assertEquals(a.size(), b.size());
        for (int i = 1; i < b.size(); ++i) {
            int j = i - 1;
            assertTrue(b.get(j) <= b.get(i));
        }
    }
}
