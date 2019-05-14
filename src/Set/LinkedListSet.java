package Set;

import LinkedList.LinkedList;

/**
 * 通过链表作为底层数据结构来实现Set，链表实现和哈希表实现的Set都是无序的
 * 时间复杂度：
 * add：因为Set中不能有重复元素，所以add操作之前还要进行contains查询，contains的时间复杂度是O(n)，头插法的时间复杂度是O(1)，结合起来add操作是O(n)
 * remove:要通过遍历查询到该元素的位置，时间复杂度为O(n)
 * contains：遍历操作，时间复杂度为O(n)
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
