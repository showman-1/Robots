package log;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ConcurrentCircularBuffer<T> {

    private final Object[] buffer;

    private final int maxSize;

    private volatile int size = 0;

    private int writeIndex = 0;

    private int startIndex = 0;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public ConcurrentCircularBuffer(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be positive");
        }
        this.maxSize = maxSize;
        this.buffer = new Object[maxSize];
    }
    public void add(T element) {
        writeLock.lock();
        try {
            buffer[writeIndex] = element;
            writeIndex = (writeIndex + 1) % maxSize;

            if (size < maxSize) {
                size++;
            } else {
                startIndex = (startIndex + 1) % maxSize;
            }
        } finally {
            writeLock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        readLock.lock();
        try {
            if (index < 0 || index >= size) {
                return null;
            }
            int realIndex = (startIndex + index) % maxSize;
            return (T) buffer[realIndex];
        } finally {
            readLock.unlock();
        }
    }

    public int size() {
        return size; // volatile read
    }

    public int getMaxSize() {
        return maxSize;
    }
    public List<T> getAll() {
        readLock.lock();
        try {
            List<T> result = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                result.add(get(i));
            }
            return result;
        } finally {
            readLock.unlock();
        }
    }

    public List<T> getRange(int start, int end) {
        readLock.lock();
        try {
            if (start < 0) start = 0;
            if (end > size) end = size;
            if (start >= end || start >= size) {
                return new ArrayList<>();
            }

            List<T> result = new ArrayList<>(end - start);
            for (int i = start; i < end; i++) {
                result.add(get(i));
            }
            return result;
        } finally {
            readLock.unlock();
        }
    }

    public List<T> getLast(int n) {
        readLock.lock();
        try {
            if (n <= 0) return new ArrayList<>();
            if (n >= size) return getAll();
            return getRange(size - n, size);
        } finally {
            readLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            Arrays.fill(buffer, null);
            size = 0;
            writeIndex = 0;
            startIndex = 0;
        } finally {
            writeLock.unlock();
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Iterator<T> snapshotIterator() {
        List<T> snapshot = getAll();
        return snapshot.iterator();
    }

    public Iterable<T> snapshot() {
        return this::snapshotIterator;
    }

    @Override
    public String toString() {
        readLock.lock();
        try {
            return String.format("ConcurrentCircularBuffer{size=%d, maxSize=%d, startIndex=%d, writeIndex=%d}",
                    size, maxSize, startIndex, writeIndex);
        } finally {
            readLock.unlock();
        }
    }
}