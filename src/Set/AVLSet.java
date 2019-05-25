package Set;

import AVLTree.AVLTree;

/**
 * 基于AVLTree实现的Set
 * 增删改查时间复杂度稳定在O(logn)
 * @param <E>
 */
public class AVLSet< E extends Comparable<E>> implements Set<E>{

    private AVLTree<E, Object> avl;

    public AVLSet(){
        this.avl = new AVLTree<E, Object>();
    }

    @Override
    public void add(E e) {
        avl.add(e, null);
    }

    @Override
    public void remove(E e) {
        avl.remove(e);
    }

    @Override
    public boolean contains(E e) {
        return avl.contains(e);
    }

    @Override
    public int getSize() {
        return avl.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avl.isEmpty();
    }
}
