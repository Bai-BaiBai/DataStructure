package UnionFind;

/**
 * 并查集数组实现
 * O(n)的 union操作
 * O(1)的 find操作
 */
public class UnionFind_1 implements UnionFind {

    private int[] id;

    public UnionFind_1(int size){
        this.id = new int[size];

        for (int i = 0; i < size; i++) { //每个元素独自为一个集合
            id[i] = i;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    @Override
    public void unionElements(int p, int q) {
        if (p < 0 || p >= id.length || q < 0 || q >= id.length) throw new IllegalArgumentException("p is out of bound");

        int pId = find(p);
        int qId = find(q);

        if (pId != qId) {
            for (int i = 0; i < id.length; i++) {
                if (id[i] == pId) {
                    id[i] = qId;
                }
            }
        }
    }

    public int find(int p){
        if (p < 0 || p >= id.length) throw new IllegalArgumentException("p is out of bound");
        return id[p];
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }
}
