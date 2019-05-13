package Set;

import LinkedList.LinkedList;

/**
 * 通过链表作为底层数据结构来实现Set
 * @param <E>
 */
public class LinkedListSet<E> implements Set<E> {

    private LinkedList<E> linkedList;

    public LinkedListSet(){
        this.linkedList = new LinkedList<E>();
    }

    @Override
    public void add(E e) {
        if (!linkedList.contains(e)){
            linkedList.addFirst(e); //该链表没有尾指针，头插法的时间复杂度是O(1)
        }
    }

    @Override
    public void remove(E e) {
        linkedList.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return linkedList.contains(e);
    }

    @Override
    public int getSize() {
        return linkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }
}
