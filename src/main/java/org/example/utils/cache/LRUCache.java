package org.example.utils.cache;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class LRUCache<T> {
    private static final int MAX_ELEMENTS = 5;

    private T[] elements;

    public LRUCache() {
        this.elements = (T[]) new Object[MAX_ELEMENTS];
    }

    public void add(T element) {
        if (element == null) {
            throw new InvalidParameterException("The parameter 'element' must not be null");
        }

        // Move each elements to the right
        for (int i = MAX_ELEMENTS - 1; i > 0; i--) {
            if (elements[i] == null && elements[i - 1] == null) continue;

            // Move the element previous element
            elements[i] = elements[i - 1];
        }
        elements[0] = element;
    }

    public List<T> getAll() {
        List<T> items = new ArrayList<>();

        for (T item: elements) {
            if (item != null) items.add(item);
        }

        return items;
    }

    public int size() {
        int size = 0;

        for (T item: elements) {
            if (item != null) size++;
        }

        return size;
    }
}
