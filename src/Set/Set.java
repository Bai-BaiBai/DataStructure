package Set;

/**
 * Set的特点是无重复元素，可以应用于书籍词汇量的简单统计
 * @param <E>
 */
public interface Set<E> {

    void add(E e);
    void remove(E e);
    boolean contains(E e);
    int getSize();
    boolean isEmpty();
}
