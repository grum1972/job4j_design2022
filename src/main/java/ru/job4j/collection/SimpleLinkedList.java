package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SimpleLinkedList<E> implements LinkedList<E> {

    private int size = 0;
    private int modCount = 0;
    private Node<E> firstNode;
    private Node<E> lastNode;

    public SimpleLinkedList() {

    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public int size() {
        return size;
    }

    public void validate(int index) {
        Objects.checkIndex(index, size());
    }

    @Override
    public void add(E value) {

        if (size == 0) {
            firstNode = new Node<E>(null, value, null);
            lastNode = firstNode;
        } else {
            Node<E> tempNode = new Node<E>(lastNode, value, null);
            lastNode.next = tempNode;
            lastNode = tempNode;
        }
        modCount++;
        size++;
    }

    @Override
    public E get(int index) {
        validate(index);
        Node<E> cursor = firstNode;
        for (int i = 0; i < index; i++) {
            cursor = cursor.next;
        }
        return cursor.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = modCount;
            private Node<E> cursor = firstNode;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("The list has been modified");
                }
                return cursor != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E item = cursor.item;
                cursor = cursor.next;
                return item;
            }
        };
    }
}