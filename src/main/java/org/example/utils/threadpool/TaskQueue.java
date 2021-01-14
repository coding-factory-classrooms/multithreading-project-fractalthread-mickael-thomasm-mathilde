package org.example.utils.threadpool;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskQueue<E> {

    private final AtomicInteger count = new AtomicInteger(0);

    private Node<E> head;
    private Node<E> last;

    public TaskQueue() {
        last = head = new Node<>(null);
    }

    public boolean add(E element) {
        if (element == null)
            throw new NullPointerException();

        enqueue(new Node<>(element));
        count.getAndIncrement();

        return true;
    }

    public E take() {
        E item = dequeue();
        count.getAndDecrement();
        return item;
    }

    private void enqueue(Node<E> node) {
        last = last.next = node;
    }

    private E dequeue() {
        Node<E> first = head.next;
        head = first;
        E item = first.item;
        first.item = null;
        return item;
    }

    public static class Node<E> {
        E item;

        Node<E> next;

        public Node(E e) { this.item = e; }
    }

    public E getFirst() {
        return head.next.item;
    }

    public E getLast() {
        return last.next.item;
    }
}
