package SegmentTree;

/**
 * SegmentTree中抽象出来的接口，作为SegmentTree构造器参数
 * merge方法用于实现SegmentTree中对两个子区间的合并逻辑
 * 例如：根节点存储的区间内元素的和，merge逻辑就是将左右孩子(a,b)的值相加
 * @param <E>
 */
public interface Merger<E> {

    public E merge(E a, E b);
}
