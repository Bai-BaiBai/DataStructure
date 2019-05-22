package UnionFind;

/**
 * 并查集的树结构实现--路径压缩
 * 在find过程，寻找一个节点的根节点时，顺便对这个树的深度进行压缩
 * 但是并没有完全压缩，只进行了部分压缩，此时rank[i]并不代表该树的深度，但是依然能表明深度的大小关系，所以不进行维护
 */
public class UnionFind_5 implements UnionFind{

    private int[] id;
    private int[] rank;

    public UnionFind_5(int size){
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
            id[p] = id[id[p]];//将p的父节点更新为父节点的父节点，即完成了压缩
            p = id[p];//然后对它的父节点进行相同的操作
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
