package UnionFind;

/**
 * 并查集的树结构实现--基于树的深度优化
 * 在第三版中，union操作是将size小的向size大的树合并，但是有可能size小的树高度更高
 * 这一版中，uniob操作将高度小的树向合并到高度大的树上，以减少find操作的时间消耗
 */
public class UnionFind_4 implements UnionFind{

    private int[] id;
    private int[] rank;

    public UnionFind_4(int size){
        this.id = new int[size];
        this.rank = new int[size];

        for (int i = 0; i < size; i++) {
            id[i] = i;
            rank[i] = 1;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    public int find(int p){
        if (p < 0 || p > id.length) throw new IllegalArgumentException("p is out of bound");
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

        if (pRoot == qRoot){
            return;
        }
        if (rank[pRoot] < rank[qRoot]){//高度低的合并到高度高的树
            id[pRoot] = qRoot;
        }else if (rank[qRoot] < rank[pRoot]){
            id[qRoot] = pRoot;
        }else {
            id[pRoot] = qRoot;
            rank[qRoot] ++;//只有两棵树相同高度合并时，合并后的树的高度才会发生变化
        }
    }
}
