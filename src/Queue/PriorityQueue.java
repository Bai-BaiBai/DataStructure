package Queue;

import Heap.MaxHeap;

/**
 * 优先队列，堆实现
 */
public class PriorityQueue< E extends Comparable<E> > implements Queue<E>{

    private MaxHeap<E> maxHeap;

    public PriorityQueue(){
        this.maxHeap = new MaxHeap<>();
    }

    @Override
    public int getSize() {
        return maxHeap.getSize();
    }

    @Override
    public boolean isEmpty() {
        return maxHeap.isEmpty();
    }

    @Override
    public E getFront() {
        return maxHeap.findMax();//findMax操作中已经有对堆空情况的判断
    }

    @Override
    public void enqueue(E e) {
        maxHeap.add(e);
    }

    @Override
    public E dequeue() {
        return maxHeap.extractMax();
    }
}
