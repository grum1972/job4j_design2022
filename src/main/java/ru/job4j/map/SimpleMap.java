package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleMap<K, V> implements Map<K, V> {
    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if (count >= capacity * LOAD_FACTOR) {
            expand();
        }
        boolean rsl = true;
        int index = getIndex(key);
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
        } else {
            rsl = false;
        }
        return rsl;

    }

    private int getIndex(K key) {
        int hCode = key == null ? 0 : key.hashCode();
        return indexFor(hash(hCode));
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {

        return hash & (table.length - 1);
    }

    private void expand() {
        MapEntry<K, V>[] oldTable = table;
        capacity *= 2;
        table = new MapEntry[capacity];
        for (MapEntry<K, V> elem : oldTable) {
            if (elem != null) {
                int index = getIndex(elem.key);
                table[index] = elem;
            }
        }
    }

    @Override
    public V get(K key) {
        int i = getIndex(key);
        V value = null;
        if (table[i] != null) {
            value = table[i].key == null
                    || hash(key.hashCode()) == hash(table[i].key.hashCode()) && table[i].key.equals(key)
                    ? table[i].value : null;

        }
        return value;
    }

    @Override
    public boolean remove(K key) {
        boolean rsl = false;
        int i = getIndex(key);
        if (table[i] != null) {
            if (table[i].key == null
                    || hash(key.hashCode()) == hash(table[i].key.hashCode()) && table[i].key.equals(key)) {
                table[i] = null;
                count--;
                modCount++;
                rsl = true;
            }
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private int index;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("The map has been modified");
                }
                while (index < table.length && table[index] == null) {
                    index++;
                }
                return index < table.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };

    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

    }
}
