package LinkedList;

import Queue.Queue;

public class LinkedListQueue<E> implements Queue<E> {

    private class Node{
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
        public Node(E e) {
            this(e, null);
        }
        public Node() {
            this(null,null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }
    private Node head;
    private Node tail;
    private int size;

    public LinkedListQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void enqueue(E e) {
        if(tail == null){
            tail = new Node(e);
            head = tail;
        }else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size ++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) throw new IllegalArgumentException("队列为空！");
        Node ret = head;
        head = head.next;
        ret.next = null;
        if (head == null){
            tail = null;
        }
        size --;
        return ret.e;
    }

    @Override
    public E getFront() {
        if (isEmpty()) throw new IllegalArgumentException("队列为空！");
        return head.e;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Queue: front ");
        Node cur = head;
        while (cur != null){
            str.append(cur + "->");
            cur = cur.next;
        }
        str.append("NULL  tail");
        return str.toString();
    }
}
