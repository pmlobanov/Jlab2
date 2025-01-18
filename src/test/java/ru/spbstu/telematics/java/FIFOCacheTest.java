package ru.spbstu.telematics.java;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class FIFOCacheTest {

    // тесты базовой функциональности
    @Test
    public void testAddAndPoll() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        assertEquals(queue.poll(), cache.poll());
        assertEquals(queue.poll(), cache.poll());
        assertEquals(queue.poll(), cache.poll());
        assertNull(cache.poll());
        assertNull(queue.poll());
    }

    @Test
    public void testOfferAndPeek() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.offer(1);
        queue.offer(1);
        cache.offer(2);
        queue.offer(2);
        cache.offer(3);
        queue.offer(3);

        assertEquals(queue.peek(), cache.peek());
        cache.poll();
        queue.poll();
        assertEquals(queue.peek(), cache.peek());
        cache.poll();
        queue.poll();
        assertEquals(queue.peek(), cache.peek());
        cache.poll();
        queue.poll();
        assertNull(cache.peek());
        assertNull(queue.peek());
    }

    @Test
    public void testSizeAndIsEmpty() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        assertTrue(cache.isEmpty());
        assertTrue(queue.isEmpty());
        assertEquals(queue.size(), cache.size());

        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        assertFalse(cache.isEmpty());
        assertFalse(queue.isEmpty());
        assertEquals(queue.size(), cache.size());

        cache.poll();
        queue.poll();
        assertEquals(queue.size(), cache.size());
    }

    @Test
    public void testClear() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        cache.clear();
        queue.clear();
        assertTrue(cache.isEmpty());
        assertTrue(queue.isEmpty());
        assertEquals(queue.size(), cache.size());
        assertNull(cache.peek());
        assertNull(queue.peek());
    }

    @Test
    public void testToArray() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        Object[] cacheArray = cache.toArray();
        Object[] queueArray = queue.toArray();
        assertArrayEquals(queueArray, cacheArray);
    }

    @Test
    public void testToArrayWithType() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        Integer[] cacheArray = new Integer[3];
        Integer[] queueArray = new Integer[3];
        cacheArray = cache.toArray(cacheArray);
        queueArray = queue.toArray(queueArray);
        assertArrayEquals(queueArray, cacheArray);
    }

    @Test
    public void testRemove() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        assertTrue(cache.remove(2));
        assertTrue(queue.remove(2));
        assertFalse(cache.contains(2));
        assertFalse(queue.contains(2));
        assertEquals(queue.size(), cache.size());
    }

    @Test
    public void testContains() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        assertTrue(cache.contains(2));
        assertTrue(queue.contains(2));
        assertFalse(cache.contains(4));
        assertFalse(queue.contains(4));
    }

    @Test
    public void testElement() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        assertEquals(queue.element(), cache.element());
        cache.poll();
        queue.poll();
        assertEquals(queue.element(), cache.element());
    }

    @Test
    public void testRemoveThrowsException() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        assertThrows(NoSuchElementException.class, cache::remove);
        assertThrows(NoSuchElementException.class, queue::remove);
    }

    @Test
    public void testElementThrowsException() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        assertThrows(NoSuchElementException.class, cache::element);
        assertThrows(NoSuchElementException.class, queue::element);
    }

    @Test
    public void testAddAll() {
        FIFOCache<Integer> cache = new FIFOCache<>(5);
        Queue<Integer> queue = new LinkedList<>();
        cache.addAll(Arrays.asList(1, 2, 3, 4, 5));
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));

        assertEquals(queue.size(), cache.size());
        assertTrue(cache.containsAll(Arrays.asList(1, 2, 3, 4, 5)));
        assertTrue(queue.containsAll(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void testRemoveAll() {
        FIFOCache<Integer> cache = new FIFOCache<>(5);
        Queue<Integer> queue = new LinkedList<>();
        cache.addAll(Arrays.asList(1, 2, 3, 4, 5));
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        cache.removeAll(Arrays.asList(1, 2, 3));
        queue.removeAll(Arrays.asList(1, 2, 3));

        assertEquals(queue.size(), cache.size());
        assertFalse(cache.contains(1));
        assertFalse(cache.contains(2));
        assertFalse(cache.contains(3));
        assertFalse(queue.contains(1));
        assertFalse(queue.contains(2));
        assertFalse(queue.contains(3));
    }

    @Test
    public void testRetainAll() {
        FIFOCache<Integer> cache = new FIFOCache<>(5);
        Queue<Integer> queue = new LinkedList<>();
        cache.addAll(Arrays.asList(1, 2, 3, 4, 5));
        queue.addAll(Arrays.asList(1, 2, 3, 4, 5));
        cache.retainAll(Arrays.asList(3, 4, 5));
        queue.retainAll(Arrays.asList(3, 4, 5));

        assertEquals(queue.size(), cache.size());
        assertTrue(cache.contains(3));
        assertTrue(cache.contains(4));
        assertTrue(cache.contains(5));
        assertFalse(cache.contains(1));
        assertFalse(cache.contains(2));
        assertTrue(queue.contains(3));
        assertTrue(queue.contains(4));
        assertTrue(queue.contains(5));
        assertFalse(queue.contains(1));
        assertFalse(queue.contains(2));
    }

    @Test
    public void testIterator() {
        FIFOCache<Integer> cache = new FIFOCache<>(3);
        Queue<Integer> queue = new LinkedList<>();
        cache.add(1);
        queue.add(1);
        cache.add(2);
        queue.add(2);
        cache.add(3);
        queue.add(3);

        Iterator<Integer> cacheIterator = cache.iterator();
        Iterator<Integer> queueIterator = queue.iterator();

        while (cacheIterator.hasNext() && queueIterator.hasNext()) {
            assertEquals(queueIterator.next(), cacheIterator.next());
        }
        assertFalse(cacheIterator.hasNext());
        assertFalse(queueIterator.hasNext());
    }
}