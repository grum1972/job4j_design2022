package ru.job4j.collection;


import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;

    private int size;

    private int modCount;

    public SimpleArrayList(int capacity) {
        this.container = (T[]) new Object[capacity];
    }

    public void checkCapacity() {
        if (container.length == 0) {
            container = Arrays.copyOf(container, 1);
        }
        if (container.length == size) {
            container = Arrays.copyOf(container, container.length * 2);
        }
    }

    public void validate(int index) {
        Objects.checkIndex(index, size());
    }

    @Override
    public void add(T value) {
        checkCapacity();
        container[size] = value;
        modCount++;
        size++;
    }

    @Override
    public T set(int index, T newValue) {
        validate(index);
        T oldValue = container[index];
        container[index] = newValue;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validate(index);
        T result = get(index);
        System.arraycopy(container, index + 1, container, index, size - index - 1);
        size--;
        container[size] = null;
        modCount++;
        return result;

    }

    @Override
    public T get(int index) {
        validate(index);
        return container[index];
    }

    @Override
    public int size() {
        return size;

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("The list has been modified");
                }
                return index < size();

            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return container[index++];
            }
        };
    }
}