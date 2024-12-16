package ru.spbstu.telematics.java;


import java.util.*;

class cell<T>
{
    //значение, хранимое в ячейке.
    T value;
    //ссылка на следующий элемент.
    cell<T> next;
    public cell(T val)
    {
        //ячейка создается в пустоте. Мы не знаем, к чему ее привязывать, поэтому просто пишем значение.
        value = val;
        next = null;
    }

}

public class FIFOCache<T> implements Queue<T>, Iterable<T>
{
    //ссылка на первый элемент
    cell<T> m_head;
    //ссылка на второй элемент
    cell<T> m_tail;
    //текущая заполненость стека
    int m_size;
    //общий размер стека. После создания не меняется
    int m_capacity;

    @Override
    public void clear() {
        m_head = null;
        m_tail = null;
        m_size = 0;
    }

    @Override
    public boolean retainAll(java.util.Collection<?> c) {
        if (c.size() > this.m_size) return false;
        cell<T> i = this.m_head;
        while(i != null)
        {
            if (!c.contains(i.value)) this.remove(i.value);
            i = i.next;
        }
        return false;
    }

    @Override
    public boolean removeAll(java.util.Collection<?> c) {
        if (c.size() > this.m_size) return false;
        for (var elem : c) {
            if (!this.contains(elem)) {
                return false;
            }
            this.remove(elem);
        }
        return true;
    }

    @Override
    public boolean addAll(java.util.Collection<? extends T> c) {
        if (c.size() > this.m_capacity) return false;
        for (var elem : c) {
            this.add(elem);
        }
        return true;
    }

    @Override
    public boolean containsAll(java.util.Collection<?> c) {
        cell<T> cur = m_head;
        for (var elem : c) {
            if (!this.contains(elem)) {
                return false;
            }
        }
        return true;
    }

    private cell<T> find(Object data) {
        cell<T> cur = m_head;
        while (cur != null) {
            if (cur.value == data) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    private class MyIterator implements Iterator<T>
    {
        cell<T> curr = m_head;
        @Override
        public boolean hasNext() {
            return curr != null;
        }
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            var val = curr.value;
            curr = curr.next;
            return val;
        }
    }

    @Override
    public boolean remove(Object o) {
        if (m_head == null) {
            return false;
        }
        // Если элемент для удаления находится в голове списка
        if (o.equals(m_head.value)) {
            m_head = m_head.next;
            m_size--;
            if (m_head == null) {
                m_tail = null;
            }
            return true;
        }

        cell<T> current = m_head;
        while (current.next != null) {
            if (o.equals(current.next.value)) {
                current.next = current.next.next;
                m_size--;
                if (current.next == null) {
                    m_tail = current;
                }
                return true;
            }
            current = current.next;
        }
        return false;

    }

    @Override
    public MyIterator iterator() {
        return new MyIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[m_size];
        int index = 0;
        for (T key : this) {
            array[index++] = key;
        }
        return array;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < this.size()) {
            a = (T1[]) Arrays.copyOf(a, this.size());
        }
        int i = 0;
        cell<T> current = m_head;
        while (current != null) {
            a[i++] = (T1) current.value;
            current = current.next;
        }
        if (a.length > this.size()) {
            a[this.size()] = null;
        }
        return a;
    }

    @Override
    public boolean contains(Object o)
    {
        if (o == null)
            return false;
        cell<T> i = this.m_head;
        while(i != null)
        {
            if (o.equals(i.value)) return true;
            i = i.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return (m_size == 0);
    }
    @Override
    public int size()
    {
        return this.m_size;
    }

    FIFOCache(int cap)
    {
        this.m_head = null;
        this.m_tail = null;
        this.m_size = 0;
        this.m_capacity = cap;
    }
    @Override
    public boolean add(T e)
    {
        return this.offer(e);
    }

    @Override
    public boolean offer(T e)
    {
        //если в стеке нет места. Вымещаем самый старый.
        if(m_size == m_capacity)
        {
            this.poll();
        }
        //теперь, 100% место есть. Вставляем
        //новый элемент
        cell<T> newCell = new cell<>(e);
        //если пуст
        if(m_head == null)
        {
            this.m_head = newCell;
            this.m_tail = newCell;
        }
        else
        {
            // перепавляем ссылку на последний элемент
            this.m_tail.next = newCell;
            this.m_tail = newCell;
        }
        m_size++;
        return true;
    }

    //сделан
    @Override
    public T remove()
    {
        //если стек пустой, то null
        if (this.poll() == null)
        {
            throw new NoSuchElementException("Cache is empty");
        }
        return this.poll();
    }
    //сделан
    //удаляем первый элемент
    @Override
    public T poll()
    {
        //если стек пустой, то null
        if(m_head == null)
        {
            return null;
        }
        cell<T> temp = this.m_head;
        this.m_head = temp.next;
        m_size--;
        //если выкинули последний, то стек пустой.
        if (m_head == null) {
            m_tail = null;
        }
        return temp.value;
    }
    @Override
    public T peek()
    {
        //если стек пустой, то null
        if(m_head == null)
        {
            return null;
        }
        return this.m_head.value;
    }

    @Override
    public T element()
    {
        //если стек пустой, то null
        if (this.peek() == null)
        {
            throw new NoSuchElementException("Cache is empty");
        }
        return this.peek();
    }


}
