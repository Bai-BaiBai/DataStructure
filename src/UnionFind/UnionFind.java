package UnionFind;

/**
 * 并查集接口
 * p和q代表元素对应的代号
 */
public interface UnionFind {

    int getSize();
    boolean isConnected(int p, int q);
    void unionElements(int p, int q);

}
