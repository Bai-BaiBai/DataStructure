package UnionFind;

/**
 * 并查集的树结构实现--路径压缩
 * 使用递归可以方便的完成路径完全压缩，即每个树的高度不超过2
 * 理论上这种完全压缩是快于第5版中的部分压缩的，但是由于递归的开销，实际的性能可能比第5版差
 * 并查集的时间复杂度：严格意义上是O(log*n)，比O(1)慢一点点，比O(logn)快
 */
public class UnionFind_6 implements UnionFind{

    private int[] id;
    private int[] rank;

    public UnionFind_6(int size){
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
        if (p != id[p]){
            id[p] = find(id[p]); //寻找根节点的同时进行路径压缩
        }
        return id[p]; //p == id[p]时返回
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
