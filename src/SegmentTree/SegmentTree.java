package SegmentTree;

/**
 * 线段树的数组实现
 * @param <E>
 */
public class SegmentTree<E> {

    private E[] tree;//线段树形式的数组
    private E[] data;//暂存需要转化成线段树的元素
    private Merger<E> merger;

    //对数组中元素生成线段树
    public SegmentTree(E[] arr, Merger<E> merger){

        this.data = (E[]) new Object[arr.length];
        System.arraycopy(arr,0, data,0, arr.length);
        this.tree = (E[]) new Object[4*arr.length];
        this.merger = merger;

        buildSegmentTree(0, 0, data.length-1);
    }

    //从data数组中取元素
    public E get(int index){
        if (index < 0 || index >= data.length) throw new IllegalArgumentException("index is illegal");
        return data[index];
    }

    //获取元素的个数，而不是线段树所有节点的个数
    public int getSize(){
        return data.length;
    }

    //返回完全二叉树的数组表示中，一个索引的左右孩子的数组下标
    //！这里不进行该返回下标的合法性判断！
    private int leftChild(int index){
        return index*2 + 1;
    }
    private int rightChild(int index){
        return index*2 + 2;
    }
    //由于在线段树中只涉及到元素的修改，不需要找父亲节点，所以不需要parent函数

    //创建线段树，传入 当前需要创建的节点对应的下标 和 该节点存储的区间边界(在data数组中的边界)
    //在treeIndex位置创建表示区间[l ... r]的线段树
    //初始传入tree[0], 对应的区间是 data[0...n]
    private void buildSegmentTree(int treeIndex, int l, int r){
        if (l == r){ //当区间内只有一个元素，该元素作为叶子节点存入tree[treeIndex]
            tree[treeIndex] = data[l];
            return;
        }

        int leftChildIndex = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);
        int mid = l + (r-l)/2; //为了防止 l+r 溢出，使用偏移量的方式求mid

        buildSegmentTree(leftChildIndex, l, mid); //对左孩子创建线段树表示区间[l...mid]
        buildSegmentTree(rightChildIndex, mid+1, r);//如果元素个数是单数，左孩子比右孩子元素多一个

        //之后的逻辑取决于具体需求，可以将需求写入到Merger的实现中
        //如果是对区间内元素求和可以存储左右子树元素相加结果，如果取最大值可以存储两个孩子的最大元素
        tree[treeIndex] = merger.merge(data[leftChildIndex], data[rightChildIndex]);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null){
                res.append(tree[i]);
            }else {
                res.append("null");
            }
            if (i != tree.length-1){
                res.append(", ");
            }
        }
        res.append(']');
        return res.toString();
    }
}
