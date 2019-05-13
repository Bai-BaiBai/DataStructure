package LinkedList;

import java.util.HashMap;

/*
add、get、removeElement、contains提供了递归的实现，使用递归实现时不需要有虚拟头节点的辅助
 */
public class LinkedList<E> {

    private class Node {
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
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node head;
    private int size;

    public LinkedList() {
        this.head = new Node(null, null); //添加一个虚拟头节点 dummyHead
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(int index, E e) {//向索引为index的位置插入元素
        if (index > size || index < 0) throw new IllegalArgumentException("非法索引");


        Node prev = head;
        for (int i = 0; i < index; i++) { //插入到index位置，所以要找到index位置的前一个节点
            prev = prev.next;
        }
//        Node node = new Node(e);
//        node.next = prev.next;
//        prev.next = node;
        prev.next = new Node(e, prev.next);
        size++;


/*递归实现
        int count = 0;
        head.next = __add(index, count, head.next, e);//因为head是虚拟头节点，第一个节点是head.next
        size++;
 */
    }

    //使用递归完成添加操作
    private Node __add(int index, int count, Node head, E e){
        //其实用不着count，只需要每次index-1就行了，当index==0时进行操作
        if (index == count){
            Node newNode = new Node(e);
            newNode.next = head;
            return newNode;
        } else{
            head.next = __add(index, ++count, head.next, e);
            return head;
        }
    }

    public void addFirst(E e) {
        add(0, e);
    }
    public void addLast(E e) {
        add(size, e);
    }

    public E get(int index){ //取index位置的节点数据
        if (index >= size || index < 0) throw new IllegalArgumentException("非法索引");


        Node cur = head;
        for (int i = 0; i <= index; i++) {
            cur = cur.next;
        }
        return cur.e;

/*
        return __get(index, head.next);
 */
    }

    //递归实现get方法
    private E __get(int index, Node head){
        if (index == 0){
            return head.e;
        }else {
            return __get(--index, head.next);
        }
    }

    public E getFirst(){
        return get(0);
    }
    public E getLast(){
        return get(size - 1);
    }

    public void set(int index, E e){ //更新index位置的节点元素
        if (index >= size || index < 0) throw new IllegalArgumentException("非法索引");

        Node cur = head;
        for (int i = 0; i <= index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    public boolean contains(E e){
        Node cur = head;
        while (cur.next != null){
            cur = cur.next;
            if (cur.e.equals(e)) return true;
        }
        return false;
    }

    //递归实现contains方法，注意传入的head必须是头节点，而不能是虚拟头节点
    private boolean __contains(E e, Node head){
        if (head == null) return false;
        if (head.e.equals(e)) return true;
        return __contains(e, head.next);
    }

    public E remove(int index){
        if (index >= size || index < 0) throw new IllegalArgumentException("非法索引");

        Node prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node delNode = prev.next;
        prev.next = delNode.next;
        delNode.next = null;
        size --;
        return delNode.e;
    }

    public E removeFirst(){
        return remove(0);
    }
    public E removeLast(){
        return remove(size - 1);
    }

    public void removeElement(E e){
        head.next = __removeElement(e, head.next);
    }

    private Node __removeElement(E e,Node head){
        if (head == null){
            return null;
        }
        if (head.e.equals(e)){
            size --;
            return __removeElement(e, head.next);
        }else{
            head.next = __removeElement(e, head.next);
            return head;
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node cur = head.next;
        while (cur != null){
            str.append(cur + "->");
            cur = cur.next;
        }
//        for (Node node = head.next; node != null; node = node.next){ str.append(node + "->");}
        str.append("NULL");
        return str.toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        for (int i = 0; i < 5; i++){
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);
        linkedList.addLast(888);
        System.out.println(linkedList);
        linkedList.removeElement(999);
        System.out.println(linkedList);
    }
}
