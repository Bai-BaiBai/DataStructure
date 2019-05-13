package Set;

import BinarySearchTree.BST;

/**
 * 使用二分搜索树作为底层数据结构实现Set集合
 * @param <E>
 */
public class BSTSet< E extends Comparable<E> > implements Set<E> {

    private BST<E> bst;

    public BSTSet(){
        this.bst = new BST<>();
    }

    @Override
    public void add(E e) {
        bst.add(e);
    }

    @Override
    public void remove(E e) {
        bst.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return bst.contains(e);
    }

    @Override
    public int getSize() {
        return bst.getSize();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
