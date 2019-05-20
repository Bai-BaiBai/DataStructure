package SegmentTree;

/**
 * 线段树的数组实现
 * API接口有get、getSize、query(L,R)、set(index, e)
 * 线段树初始化时间复杂度为O(4n)，因为开辟空间为4n，要对这些空间赋值基本等于遍历
 * query和set操作时间复杂度是O(logn)级别
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
        //当区间内只有一个元素，该元素作为叶子节点存入tree[treeIndex]
        if(l == r){
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
        tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
    }

    //返回区间[queryL...queryR]的值
    public E query(int queryL, int queryR){
        if (queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR)
            throw new IllegalArgumentException("index is illegal");
        if (queryL == queryR) return data[queryL];
        return __query(0, 0, data.length-1, queryL, queryR);
    }

    //在以treeIndex为根的线段树中[l...r]的范围内，搜索区间[queryL...queryR]的值
    private E __query(int treeIndex, int l, int r, int queryL, int queryR){
        if (l == queryL && r == queryR) return tree[treeIndex];

        int leftChildIndex = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);
        int mid = l + (r-l)/2;

        if (queryL >= mid+1){ //如果搜索区间在右节点区间内
            return __query(rightChildIndex, mid+1, r, queryL, queryR);
        }else if (queryR <= mid){//如果搜索区间在左节点区间内
            return __query(leftChildIndex, l, mid, queryL, queryR);
        }else {//如果搜索区间与两个子区间都有交集，分别在子区间中搜索值
            return merger.merge(__query(leftChildIndex, l, mid, queryL, mid),
                                __query(rightChildIndex, mid+1, r, mid+1, queryR));
        }
    }

    //将data[index]位置的值更新为e
    public void set(int index, E e){
        if (index < 0 || index >= data.length) throw new IllegalArgumentException("index is illegal");

        data[index] = e;
        __set(0, 0, data.length-1, index, e);
    }

    //在以treeIndex为根的线段树中更新index的值为e，l和r是线段树的区间
    private void __set(int treeIndex, int l, int r, int index, E e){
        if (l == r){ // l = r = index 找到该节点，进行更新
            tree[treeIndex] = e;
            return;
        }

        int mid = l + (r-l)/2;
        int leftChildIndex = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);
        if (index >= mid+1){//如果index在右区间内
            __set(rightChildIndex, mid+1, r, index, e);
        }else {
            __set(leftChildIndex, l, mid, index, e);
        }
        tree[treeIndex] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]); //合并子区间的值到treeIndex
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
