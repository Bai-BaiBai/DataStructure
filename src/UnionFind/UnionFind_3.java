package UnionFind;

/**
 * 并查集的树结构实现--对size优化
 * 在第二版的UnionFind中，两棵树合并后可能形成一个链表，树的高度h太大
 * union时，size小的树合并到size大的树
 */
public class UnionFind_3 implements UnionFind{

    private int[] id;
    private int[] sz;//sz[i]表示以i为根的集合中元素个数

    public UnionFind_3(int size){
        this.id = new int[size];
        this.sz = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    public int find(int p){
        if (p < 0 || p >= id.length) throw new IllegalArgumentException("p is out of bound");

        while (p != id[p]){
            p = id[p];
        }
        return p;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;

        if (sz[pRoot] < sz[qRoot]){
            id[pRoot] = qRoot;//小树向大树并
            sz[qRoot] += sz[pRoot];
        }else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
    }
}
