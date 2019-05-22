package UnionFind;

/**
 * 并查集的树结构实现
 * O(h)的 union操作
 * O(h)的 find操作
 */
public class UnionFind_2 implements UnionFind{

    private int[] id;

    public UnionFind_2(int size){
        this.id = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
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
//        return p == id[p] ? p : find(id[p]);
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public void unionElements(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot != qRoot){
            id[qRoot] = pRoot;
        }
    }
}
