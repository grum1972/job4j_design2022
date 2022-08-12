package ru.job4j.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ForwardLinked<T> implements Iterable<T> {
    private Node<T> head;

    public void add(T value) {
        Node<T> node = new Node<>(value, null);
        if (head == null) {
            head = node;
            return;
        }
        Node<T> tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = node;
    }

    public void addFirst(T value) {
        head = new Node<>(value, head);
    }

    public T deleteFirst() {
        if (head != null) {
            Node<T> node = head;
            T rsl = head.value;
            head = node.next;
            node.next = null;
            return rsl;
        } else {
            throw new NoSuchElementException();
        }
    }

    public boolean revert() {
        boolean result = false;
        if (head != null && head.next != null) {
            Node<T> prev = null;
            Node<T> current = head;

            while (current != null) {
                Node<T> next = current.next;
                current.next = prev;
                prev = current;
                current = next;

            }
            head = prev;
            result = true;
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            Node<T> node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = node.value;
                node = node.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
