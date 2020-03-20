package algorithms;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sorting {
    /**
     * Merge two ArrayLists efficiently, in ascending order. Does not modify either argument.
     * @param a The first list.
     * @param b The second list.
     * @return A new sorted ArrayList containing elements from both lists.
     */
    public static ArrayList<Integer> merge(ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> result = new ArrayList<>();

        /* YOUR CODE HERE */
        int lengthA = a.size();
        int lengthB = b.size();
        int currentA = 0;
        int currentB = 0;
        while (currentA < lengthA && currentB < lengthB) {
            if (a.get(currentA) <= b.get(currentB)) {
                result.add(a.get(currentA));
                currentA++;
            } else {
                result.add(b.get(currentB));
                currentB++;
            }
        }
        while (currentA < lengthA) {
            result.add(a.get(currentA));
            currentA++;
        }
        while (currentB < lengthB) {
            result.add(b.get(currentB));
            currentB++;
        }
        return result;
    }

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> list) {
        /* YOUR CODE HERE */
        if (list == null) {
            return null;
        }
        int length = list.size();
        if (length == 0 || length == 1) {
            return list;
        }
        int mid = length / 2;
        ArrayList<Integer> left = mergeSort(new ArrayList<>(list.subList(0, mid)));
        ArrayList<Integer> right = mergeSort(new ArrayList<>(list.subList(mid, length)));
        return merge(left, right);
    }
}
