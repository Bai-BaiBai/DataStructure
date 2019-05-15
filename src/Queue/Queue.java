package Queue;

/**
 * 优先队列的实现与普通队列的差异在于enqueue方法和dequeue方法
 * 如果使用普通线性结构来实现优先队列，那么入队的时间复杂度是O(1)，出队的时间复杂度是O(n)，出队要查找当前优先级最高的那个元素
 * 如果使用顺序线性结构来实现，入队是O(n)，出队是O(1)，每次入队要根据优先级决定放置位置
 * 最理想的底层数据结构是堆，入队和出队都是最差为O(logn)
 * @param <E>
 */
public interface Queue<E> {

    void enqueue(E e);
    E dequeue();
    E getFront();
    int getSize();
    boolean isEmpty();
}
