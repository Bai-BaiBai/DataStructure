package Set;

import BinarySearchTree.BST;

/**
 * 使用二分搜索树作为底层数据结构实现Set集合，具有搜索树的特点：有序性
 * 时间复杂度分析：
 * 二分搜索树每次判断都会筛选掉近乎一半的元素，add、remove、contains操作最差的情况是查找到最长的那一条路径的叶子节点，
 * 也就是说，它的时间复杂度取决于树的高度，add、remove、contains的时间复杂度均为O(h)，h为树的高度
 * 如果二分搜索树是一颗完全平衡二叉树，也就是第层有2^h个元素，那么h层共有2^(h-1)个元素，此时 h = log2^(n-1)，平均时间复杂度O(log2^n) = O(logn)
 * 二分搜索树可能退化成链表，最差的时间复杂度为O(n)
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
