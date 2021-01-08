package org.example.utils.cache;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class LRUCache<T, U> {
    private static final int MAX_ELEMENTS = 15;

    private T[] keys;
    private U[] elements;

    public LRUCache() {
        this.keys = (T[]) new Object[MAX_ELEMENTS];
        this.elements = (U[]) new Object[MAX_ELEMENTS];
    }

    public void add(T key, U element) {
        if (element == null) {
            throw new InvalidParameterException("The parameter 'element' must not be null");
        }

        // Move each elements to the right
        for (int i = MAX_ELEMENTS - 1; i > 0; i--) {
            if (elements[i] == null && elements[i - 1] == null) continue;

            // Move the element previous element
            keys[i] = keys[i - 1];
            elements[i] = elements[i - 1];
        }

        keys[0] = key;
        elements[0] = element;
    }

    public U get(T key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) break;
            if (keys[i].equals(key)) return elements[i];
        }

        return null;
    }

    public List<U> getAll() {
        List<U> items = new ArrayList<>();

        for (U item: elements) {
            if (item != null) items.add(item);
        }

        return items;
    }

    public boolean contains(T key) {
        for (T innerKey: keys) {
            if (innerKey != null && innerKey.equals(key)) return true;
        }
        return false;
    }

    public void reset() {
        this.keys = (T[]) new Object[MAX_ELEMENTS];
        this.elements = (U[]) new Object[MAX_ELEMENTS];
    }

    public int size() {
        int size = 0;

        for (U item: elements) {
            if (item != null) size++;
        }

        return size;
    }
}
