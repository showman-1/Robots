package collections;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BoundedCircularList<T> extends AbstractList<T> {
    private final int capacity;
    private final Object[] buffer;
    private int head;
    private int size;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public BoundedCircularList(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.buffer = new Object[capacity];
        this.head = 0;
        this.size = 0;
    }

    public BoundedCircularList() {
        this(5);
    }

    @Override
    public boolean add(T e) {
        lock.writeLock().lock();
        try {
            int tail = (head + size) % capacity;
            buffer[tail] = e;
            if (size == capacity) {
                head = (head + 1) % capacity;
            } else {
                size++;
            }
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public T get(int index) {
        lock.readLock().lock();
        try {
            checkIndex(index);
            return (T) buffer[(head + index) % capacity];
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int size() {
        lock.readLock().lock();
        try {
            return size;
        } finally {
            lock.readLock().unlock();
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        lock.readLock().lock();
        try {
            Object[] snapshot = new Object[size];
            for (int i = 0; i < size; i++) {
                snapshot[i] = buffer[(head + i) % capacity];
            }
            return new SnapshotIterator<>(snapshot);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        lock.readLock().lock();
        try {
            if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
                throw new IndexOutOfBoundsException();
            }
            int len = toIndex - fromIndex;
            Object[] sub = new Object[len];
            for (int i = 0; i < len; i++) {
                sub[i] = buffer[(head + fromIndex + i) % capacity];
            }
            return new AbstractList<T>() {
                @Override public T get(int i) { return (T) sub[i]; }
                @Override public int size() { return len; }
            };
        } finally {
            lock.readLock().unlock();
        }
    }

    private static class SnapshotIterator<T> implements Iterator<T> {
        private final Object[] snapshot;
        private int cursor;

        SnapshotIterator(Object[] snapshot) {
            this.snapshot = snapshot;
            this.cursor = 0;
        }

        @Override public boolean hasNext() { return cursor < snapshot.length; }
        @Override public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (T) snapshot[cursor++];
        }
    }
}