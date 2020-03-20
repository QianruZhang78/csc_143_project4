package test;

import hashing.HashMap;
import lib.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.*;

public class HashMapTest {
    HashMap<String, Integer> people;

    @Before
    public void setUp() {
        people = new HashMap<>();
    }

    private <K, V> void assertEntry(HashMap<K, V> map, K key, V value) {
        assertEquals(value, map.get(key));
        assertTableHasPair(map, new Pair<>(key, value));
    }

    private <K, V> void assertTableHasPair(HashMap<K, V> map, Pair<K, V> keyValuePair) {
        assertTrue(map.table[map.getSlot(keyValuePair.left)].contains(keyValuePair));
    }

    private void assertEmpty(HashMap<?, ?> map) {
        assertEquals(0, map.size());
        for (int i = 0; i < map.table.length; ++i) {
            assertTrue(map.table[i] == null || map.table[i].size() == 0);
        }
    }

    private void assertExhausted(Iterator<?> iterator) {
        assertFalse(iterator.hasNext());
        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
        }

        try {
            iterator.remove();
            fail();
        } catch (IllegalStateException e) {
        }

        assertFalse(iterator.hasNext());
        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
        }

        try {
            iterator.remove();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void PutGetTestEmpty() {
        assertNull(people.get("John"));
        assertNull(people.get(null));
        assertEquals(0, people.size());
    }

    @Test
    public void PutGetTestSimple() {
        assertNull(people.put("John", 25));
        assertEntry(people, "John", 25);
        assertEquals(1, people.size());
    }

    @Test
    public void PutGetNullTest() {
        people.put(null, 55);
        assertEntry(people, null, 55);

        // put returns the old value, or null if there is none
        assertEquals(Integer.valueOf(55), people.put(null, 99));
        assertEntry(people, null, 99);
        assertEquals(1, people.size());
    }

    @Test
    public void PutValueNullTest() {
        assertNull(people.put("John", null));
        assertEntry(people, "John", null);

        assertNull(people.put(null, null));
        assertEntry(people, null, null);
        assertEquals(2, people.size());

        assertNull(people.put("John", 100));
        assertEntry(people, "John", 100);
        assertEquals(2, people.size());
    }

    @Test
    public void PutGetReplaceTest() {
        people.put("John", 25);
        people.put("Paul", 44);
        assertEntry(people, "John", 25);
        assertEntry(people, "Paul", 44);
        assertEquals(2, people.size());

        assertEquals(Integer.valueOf(25), people.put("John", 88));
        assertEntry(people, "John", 88);
        assertEntry(people, "Paul", 44);

        if ("John".hashCode() == "Paul".hashCode()) {
            assertEquals(2, people.table[people.getSlot("John")].size());
        } else {
            assertEquals(1, people.table[people.getSlot("John")].size());
            assertEquals(1, people.table[people.getSlot("Paul")].size());
        }
        assertEquals(2, people.size());
    }

    @Test
    public void RemoveSimpleTest() {
        people.put("John", 33);
        assertEquals(1, people.size());
        assertEntry(people, "John", 33);

        assertEquals(Integer.valueOf(33), people.remove("John"));
        assertNull(people.get("John"));
        assertEmpty(people);

        people.put("John", 99);
        assertEquals(1, people.size());
        assertEntry(people, "John", 99);

        people.put("John", 88);
        assertEquals(1, people.size());
        assertEntry(people, "John", 88);

        assertEquals(Integer.valueOf(88), people.remove("John"));
        assertNull(people.get("John"));
        assertEmpty(people);
    }

    @Test
    public void RemoveMultipleTest() {
        people.put("John", 22);
        people.put("Paul", 44);
        people.put("Ringo", 55);
        people.put("George", 66);
        assertEquals(4, people.size());

        assertEntry(people, "John", 22);
        people.put("John", 33);

        assertEntry(people, "Paul", 44);
        assertEntry(people, "Ringo", 55);
        assertEntry(people, "George", 66);

        assertEquals(Integer.valueOf(33), people.remove("John"));
        assertEquals(3, people.size());
        assertNull(people.get("John"));

        assertEntry(people, "Paul", 44);
        assertEntry(people, "Ringo", 55);
        assertEntry(people, "George", 66);

        assertEquals(Integer.valueOf(66), people.put("George", 77));
        assertEquals(3, people.size());
        assertEntry(people, "George", 77);
        assertEquals(Integer.valueOf(77), people.remove("George"));

        assertEquals(2, people.size());
        assertEquals(Integer.valueOf(44), people.remove("Paul"));
        assertEquals(Integer.valueOf(55), people.remove("Ringo"));

        assertEmpty(people);
    }

    @Test
    public void IteratorEmptyTest() {
        Iterator<Pair<String, Integer>> iterator = people.iterator();
        assertExhausted(iterator);
        assertEmpty(people);
    }

    @Test
    public void IteratorSimpleTest() {
        people.put("John", 22);
        assertEquals(1, people.size());

        Iterator<Pair<String, Integer>> iterator = people.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Pair<String, Integer>("John", 22), iterator.next());

        iterator.remove();
        assertNull(people.get("John"));

        assertEmpty(people);
        assertExhausted(iterator);
    }

    @Test
    public void IteratorMultipleTest() {
        people.put("John", 22);
        people.put("Paul", 44);
        people.put("Ringo", 55);
        people.put("George", 66);
        assertEquals(4, people.size());

        HashSet<Pair<String, Integer>> pairSet = new HashSet<>();
        for (Pair<String, Integer> keyPair : people) {
            pairSet.add(keyPair);
        }

        assertEquals(4, pairSet.size());
        assertTrue(pairSet.contains(new Pair<String, Integer>("John", 22)));
        assertTrue(pairSet.contains(new Pair<String, Integer>("Paul", 44)));
        assertTrue(pairSet.contains(new Pair<String, Integer>("Ringo", 55)));
        assertTrue(pairSet.contains(new Pair<String, Integer>("George", 66)));
    }

    @Test
    public void IteratorMultipleRemoveTest() {
        people.put("A", 1);
        people.put("B", 2);
        people.put("C", 3);
        people.put("D", 4);
        people.put("E", 5);
        people.put("F", 6);
        people.put("G", 7);
        people.put("H", 8);
        people.put("I", 9);
        people.put("J", 10);

        assertEquals(10, people.size());

        HashSet<Pair<String, Integer>> pairSet = new HashSet<>();
        for (Pair<String, Integer> keyPair : people) {
            pairSet.add(keyPair);
        }

        assertEquals(10, pairSet.size());

        Iterator<Pair<String, Integer>> iterator = people.iterator();
        Pair<String, Integer> currentPair = iterator.next();

        iterator.remove();
        pairSet.remove(currentPair);

        assertEquals(9, people.size());
        HashSet<Pair<String, Integer>> otherPairSet = new HashSet<>();
        for (Pair<String, Integer> keyPair : people) {
            otherPairSet.add(keyPair);
        }

        assertEquals(pairSet, otherPairSet);

        try {
            iterator.remove();
            fail();
        } catch (IllegalStateException e) {
        }

        currentPair = iterator.next();
        iterator.remove();
        pairSet.remove(currentPair);

        assertEquals(8, people.size());
        otherPairSet = new HashSet<>();
        for (Pair<String, Integer> keyPair : people) {
            otherPairSet.add(keyPair);
        }

        assertEquals(pairSet, otherPairSet);

        try {
            iterator.remove();
            fail();
        } catch (IllegalStateException e) {
        }

        for (int i = 0; i < 8; ++i) {
            iterator.next();
        }
        assertExhausted(iterator);
    }

    @Test
    public void IteratorRemoveAllTest() {
        people.put("A", 1);
        people.put("B", 2);
        people.put("C", 3);
        people.put("D", 4);
        people.put("E", 5);
        people.put("F", 6);
        people.put("G", 7);
        people.put("H", 8);
        people.put("I", 9);
        people.put("J", 10);

        assertEquals(10, people.size());

        HashSet<Pair<String, Integer>> pairSet = new HashSet<>();
        for (Pair<String, Integer> keyPair : people) {
            pairSet.add(keyPair);
        }

        assertEquals(10, pairSet.size());

        Iterator<Pair<String, Integer>> iterator = people.iterator();
        for (int i = 0; i < 10; ++i) {
            assertTrue(iterator.hasNext());
            iterator.next();
            iterator.remove();
        }

        assertExhausted(iterator);
        assertEmpty(people);
    }

    @Test
    public void ExpandSimpleTest() {
        people = new HashMap<>(1);
        assertEquals(1, people.table.length);
        assertEmpty(people);

        people.put("A", 1);
        // Load factor (75%) exceeded, table should double in size

        assertEquals(2, people.table.length);
        assertEntry(people, "A", 1);

        people.put("B", 2);
        // Load factor exceeded again, table should double in size

        assertEquals(4, people.table.length);
        assertEntry(people, "A", 1);
        assertEntry(people, "B", 2);

        people.put("A", 3);
        assertEquals(4, people.table.length);
        assertEquals(2, people.size());
        assertEntry(people, "A", 3);
        assertEntry(people, "B", 2);

        people.put(null, null);
        // Load factor is not exceeded, 3/4 = 75% exactly
        assertEquals(4, people.table.length);
        assertEquals(3, people.size());
        assertEntry(people, "A", 3);
        assertEntry(people, "B", 2);
        assertEntry(people, null, null);
    }

    @Test
    public void ExpandRemoveTest() {
        this.ExpandSimpleTest();

        people.put(null, 0);
        assertEquals(4, people.table.length);
        assertEquals(3, people.size());
        assertEntry(people, "A", 3);
        assertEntry(people, "B", 2);
        assertEntry(people, null, 0);

        people.remove("A");
        assertEquals(4, people.table.length);
        assertEquals(2, people.size());
        assertNull(people.get("A"));

        assertEntry(people, "B", 2);
        assertEntry(people, null, 0);

        people.remove("B");
        assertEquals(4, people.table.length);
        assertEquals(1, people.size());
        assertNull(people.get("B"));
        assertEntry(people, null, 0);

        people.remove(null);
        assertEquals(4, people.table.length);
        assertEquals(0, people.size());
        assertNull(people.get(null));

        assertEmpty(people);
    }

    @Test
    public void HashMapTest() {
        java.util.HashMap<String, Integer> libMap = new java.util.HashMap<>();
        HashMap<String, Integer> yourMap = new HashMap<>();

        Random rand = new Random(0);
        for (int i = 0; i < 1000; ++i) {
            String character = String.valueOf(((char)(rand.nextInt(26) + 'a')));

            Integer value = rand.nextInt(100);
            libMap.put(character, value);
            yourMap.put(character, value);
        }

        assertEquals(libMap.size(), yourMap.size());

        for (String key : libMap.keySet()) {
            assertEquals(libMap.get(key), yourMap.get(key));
        }
    }
}
