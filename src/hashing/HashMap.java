package hashing;

import lib.Pair;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * HashMap implementation using hashing w/ linked list buckets.
 * Please refer to the official HashMap Java 11 documentation
 * for an explanation on the behavior of each of the methods below.
 * <p>
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/HashMap.html
 *
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class HashMap<K, V> implements Iterable<Pair<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    // Normally this would be private, but we'll make it public
    // for testing purposes
    public LinkedList<Pair<K, V>>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        /* DO NOT MODIFY */
        table = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, DEFAULT_CAPACITY);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        /* DO NOT MODIFY */
        table = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, initialCapacity);
        size = 0;
    }

    public int getSlot(K key) {
        /* DO NOT MODIFY */
        return key == null ? 0 : (key.hashCode() % table.length);
        /*
            Explanation: null always has a hashCode of zero and will always be in index 0;
                         all non-null keys will have a table entry corresponding to their hashCode
                         that wraps around using the modulo (%) operator to prevent overflow
                         (but not collisions).

                         For example, given a table size of 10,
                         a hashCode of 6 results in a slot of 6, and
                         a hashCode of 16 also results in a slot of 6.
         */
    }

    public V put(K key, V value) {
        if (!contains(key) && (size + 1) / (table.length * 1.0) > LOAD_FACTOR) {
            expand();
        }
        int slot = getSlot(key);
        if (table[slot] == null) {
            table[slot] = new LinkedList<>();
        }

        /*
            Use the .set(value) method on the ListIterator to do
            an O(1) replacement of a value.
         */
        ListIterator<Pair<K, V>> i = table[slot].listIterator();


        /* YOUR CODE HERE */
        while (i.hasNext()) {
            Pair<K, V> current = i.next();
            if (current.left == null || current.left.equals(key)) {
                V result = current.right;
                i.set(new Pair<>(key, value));
                return result;
            }
        }
        i.add(new Pair<>(key, value));
        size++;
        return null;
    }


    public V get(K key) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }

        /* YOUR CODE HERE */
        ListIterator<Pair<K, V>> i = table[slot].listIterator();


        /* YOUR CODE HERE */
        while (i.hasNext()) {
            Pair<K, V> current = i.next();
            if (current.left == null || current.left.equals(key)) {
                return current.right;
            }
        }
        return null;
    }
    
    private boolean contains(K key) {
        int slot = getSlot(key);
        if (table[slot] == null || table[slot].isEmpty()) {
            return false;
        }

        ListIterator<Pair<K, V>> i = table[slot].listIterator();
        while (i.hasNext()) {
            Pair<K, V> current = i.next();
            if (current.left == null && key == null) {
                return true;
            }
            if (current.left != null && current.left.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public V remove(K key) {
        int slot = getSlot(key);
        if (table[slot] == null) {
            return null;
        }

        /*
            Use the remove() method supplied by ListIterator
            to do an O(1) remove on the list bucket.
         */
        ListIterator<Pair<K, V>> i = table[slot].listIterator();


        /* YOUR CODE HERE */
        while (i.hasNext()) {
            Pair<K, V> current = i.next();
            if (current.left == null || current.left.equals(key)) {
                V result = current.right;
                i.remove();
                size--;
                return result;
            }
        }
        return null;
    }

    public int size() {
        /* DO NOT MODIFY */
        return size;
    }


    @Override
    public Iterator<Pair<K, V>> iterator() {
        /* DO NOT MODIFY */
        return new HashMapIterator(this);
    }

    private class HashMapIterator implements Iterator<Pair<K, V>> {
        HashMap<K, V> hashMap;

        /* YOUR CODE HERE */
        int currentSlot;
        ListIterator<Pair<K, V>> it;


        HashMapIterator(HashMap<K, V> hashMap) {
            this.hashMap = hashMap;

            /* YOUR CODE HERE */
            currentSlot = -1;
            setSlotIndex(0);
        }

        private void setSlotIndex(int slotIndex) {
            for (int i = slotIndex; i < table.length; i++) {
                if (table[i] != null && table[i].size() != 0) {
                    it = table[i].listIterator();
                    currentSlot = i;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            /*
                hasNext should be worst case O(n), not O(n^2)
                Hint: Use an Iterator to retrieve individual bucket values
                instead of .get(index), which is O(n) on its own
             */

            /* YOUR CODE HERE */
            if (currentSlot < 0) {
                return false;
            }
            if (it.hasNext()) {
                return true;
            }
            int tmp = currentSlot;
            currentSlot = -1;
            it = null;
            setSlotIndex(tmp + 1);

            if (currentSlot < 0) {
                return false;
            }
            return it.hasNext();
        }

        @Override
        public Pair<K, V> next() {
            /* YOUR CODE HERE */
            if (it == null) {
                throw new NoSuchElementException();
            }
            if (hasNext()) {
                return it.next();
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            /* YOUR CODE HERE */
            if (it == null) {
                throw new IllegalStateException();
            }

            it.remove();
            size--;
        }
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        /* DO NOT MODIFY */
        LinkedList<Pair<K, V>>[] newTable = (LinkedList<Pair<K, V>>[]) Array.newInstance(LinkedList.class, table.length * 2);

        /* YOUR CODE HERE */
        LinkedList<Pair<K, V>>[] originalTable = table;
        this.table = newTable;
        size = 0;
        for (int i = 0; i < originalTable.length; i++) {
            if (originalTable[i] == null) {
                continue;
            }
            ListIterator<Pair<K, V>> it = originalTable[i].listIterator();
            /* YOUR CODE HERE */
            while (it.hasNext()) {
                Pair<K, V> current = it.next();
                put(current.left, current.right);
            }
        }
    }

}
