package ru.spbstu.telematics.java;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.Arrays;

public class FIFOCacheTest {

    @Test
    public void testAddAndPoll() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.add(1);
        cache.add(2);
        cache.add(3);

        assertEquals(1, cache.poll());
        assertEquals(2, cache.poll());
        assertEquals(3, cache.poll());
        assertNull(cache.poll());
    }

    @Test
    public void testOfferAndPeek() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.offer(1);
        cache.offer(2);
        cache.offer(3);

        assertEquals(1, cache.peek());
        cache.poll();
        assertEquals(2, cache.peek());
        cache.poll();
        assertEquals(3, cache.peek());
        cache.poll();
        assertNull(cache.peek());
    }

    @Test
    public void testSizeAndIsEmpty() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        assertTrue(cache.isEmpty());
        assertEquals(0, cache.size());

        cache.add(1);
        cache.add(2);
        cache.add(3);

        assertFalse(cache.isEmpty());
        assertEquals(3, cache.size());

        cache.poll();
        assertEquals(2, cache.size());
    }

    @Test
    public void testClear() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.add(1);
        cache.add(2);
        cache.add(3);

        cache.clear();
        assertTrue(cache.isEmpty());
        assertEquals(0, cache.size());
        assertNull(cache.peek());
    }

    @Test
    public void testToArray() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.add(1);
        cache.add(2);
        cache.add(3);

        Object[] array = cache.toArray();
        assertArrayEquals(new Object[]{1, 2, 3}, array);
    }

    @Test
    public void testToArrayWithType() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.add(1);
        cache.add(2);
        cache.add(3);

        Integer[] array = new Integer[3];
        array = cache.toArray(array);
        assertArrayEquals(new Integer[]{1, 2, 3}, array);
    }

    @Test
    public void testRemove() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.add(1);
        cache.add(2);
        cache.add(3);

        assertTrue(cache.remove(2));
        assertFalse(cache.contains(2));
        assertEquals(2, cache.size());
    }

    @Test
    public void testContains() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.add(1);
        cache.add(2);
        cache.add(3);

        assertTrue(cache.contains(2));
        assertFalse(cache.contains(4));
    }

    @Test
    public void testElement() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        cache.add(1);
        cache.add(2);
        cache.add(3);

        assertEquals(1, cache.element());
        cache.poll();
        assertEquals(2, cache.element());
    }

    @Test
    public void testRemoveThrowsException() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        assertThrows(NoSuchElementException.class, cache::remove);
    }

    @Test
    public void testElementThrowsException() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        assertThrows(NoSuchElementException.class, cache::element);
    }

    @Test
    public void testAddAll() {
        FIFOCache<Integer> cache = new FIFOCache<>(5);
        cache.addAll(Arrays.asList(1, 2, 3, 4, 5));

        assertEquals(5, cache.size());
        assertTrue(cache.containsAll(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void testRemoveAll() {
        FIFOCache<Integer> cache = new FIFOCache<>(5);
        cache.addAll(Arrays.asList(1, 2, 3, 4, 5));
        cache.removeAll(Arrays.asList(1, 2, 3));

        assertEquals(2, cache.size());
        assertFalse(cache.contains(1));
        assertFalse(cache.contains(2));
        assertFalse(cache.contains(3));
    }

    @Test
    public void testRetainAll() {
        FIFOCache<Integer> cache = new FIFOCache<>(5);
        cache.addAll(Arrays.asList(1, 2, 3, 4, 5));
        cache.retainAll(Arrays.asList(3, 4, 5));

        assertEquals(3, cache.size());
        assertTrue(cache.contains(3));
        assertTrue(cache.contains(4));
        assertTrue(cache.contains(5));
        assertFalse(cache.contains(1));
        assertFalse(cache.contains(2));
    }
}
