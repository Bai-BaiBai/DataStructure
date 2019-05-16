package Heap;

import Array.Array;

import java.util.Random;

/**
 * 使用二叉树的形式实现最大堆
 * 因为二叉堆是一棵完全二叉树，所以可以使用数组的方式来存储堆元素
 * 复用src下的Array类
 */
public class MaxHeap<E extends Comparable<E> > {

    private Array<E> data;

    public MaxHeap(int capacity){
        this.data = new Array<E>(capacity);
    }

    public MaxHeap(){
        this.data = new Array<E>();
    }

    //返回堆中元素的个数
    public int getSize(){
        return data.getSize();
    }

    //返回堆是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    //根据给定节点下标返回父亲节点的下标
    //因为从0开始存储，所以有1的偏移量
    private int getParent(int index){
        if (index == 0) throw new IllegalArgumentException("Index-0 doesn't have parent");
        return (index-1) / 2;
    }

    //如果孩子的下标大于size-1，说明该节点不存在孩子节点，返回值为 -1
    private int getLeftChild(int index){
        int r = index*2 + 1;
        return r > getSize()-1 ? -1 : r;
    }

    private int getRightChild(int index){
        int r = index*2 + 2;
        return r > getSize()-1 ? -1 : r;
    }

    public void add(E e){
        data.addLast(e);
        __siftUp(e, getSize()-1);
    }

    //上浮操作，自底向上，将在树的末尾的最后放入的元素放到合适的位置，
    //传入参数为最后放入的元素和它的当前位置
    private void __siftUp(E e, int index){
        if (index == 0) return;//如果它已经是根节点了就返回
        int parIndex = getParent(index);
        if (e.compareTo(data.get(parIndex)) > 0){
            data.swap(index, parIndex);
            __siftUp(e, parIndex);
        }

        /* 上浮操作的非递归写法
        int parentIndex = getParent(index);
        while (index > 0 && e.compareTo(data.get(parentIndex)) > 0){
            data.swap(index, parentIndex);
            index = parentIndex;
        }
        */
    }

    //取出堆中的最大元素
    //弹出最大元素时，策略是将末尾的元素替换到树的根，保证完全二叉树的性质，然后对根元素进行下沉操作。
    public E extractMax(){
        if (getSize() == 0) throw new IllegalArgumentException("heap is empty");

        E res = data.get(0);
        data.swap(0, data.getSize()-1);
        data.removeLast();
        __siftDown(0);
        return res;
    }

    //下沉操作的非递归实现,自顶向下将index对应的元素放到合适的位置
    //被extractMax方法调用
    private void __siftDown(int index){

        while (getLeftChild(index) > 0){ //当index对应节点有子节点时进入循环
            E e = data.get(index);
            int leftIndex = getLeftChild(index);
            int rightIndex = getRightChild(index);

            if (rightIndex < 0){//没有右节点的情况下进入
                if (e.compareTo(data.get(leftIndex)) < 0){//比较左节点和父节点的大小
                    data.swap(index, leftIndex);
                    index = leftIndex;
                }else {
                    break;
                }
            }else {//左右节点都有的情况
                E leftValue = data.get(leftIndex);
                E rightValue = data.get(rightIndex);
                if (e.compareTo(leftValue) < 0 || e.compareTo(rightValue) < 0){//左右节点任意一个大于父节点
                    if (leftValue.compareTo(rightValue) > 0){//左节点大于右节点，则用左节点和父节点交换
                        data.swap(index, leftIndex);
                        index = leftIndex;
                    }else {
                        data.swap(index, rightIndex);
                        index = rightIndex;
                    }
                }else {//左右节点均小于父节点
                    break;
                }

                /* 最外层else中的逻辑还可以这么写，只需要调用两次compareTo
                if (rightValue.compareTo(leftValue) > 0){
                    leftIndex = rightIndex;
                    //此时leftIndex存储的是 左右节点中大的节点的下标
                }
                if (e.compareTo(data.get(leftIndex)) > 0){
                    break;
                }else {
                    data.swap(index, leftIndex);
                    index = leftIndex;
                }

                 */

            }
        }
    }

    //下沉操作的递归实现，开销大于非递归的实现
    private void __sifDown(int index){

        int lchild = getLeftChild(index);
        if (lchild > 0) {//如果lchild小于0则说明该节点没有子节点，结束下沉操作

            int rchild = getRightChild(index);
            E cur = data.get(index);
            E leftVal = data.get(lchild);//将左节点的值暂存方便后面使用

            if (rchild == -1) {
                if (cur.compareTo(leftVal) < 0) {
                    data.swap(index, lchild);
                    __sifDown(lchild);
                    return;
                }else {
                    return;
                }
            }
            E rightVal = data.get(rchild);//将右节点的值暂存方便使用

            /*下面有对该if条件判断的优化，使三次compareTo变成两次
            if (cur.compareTo(leftVal) < 0 || cur.compareTo(rightVal) < 0) {//如果左右子节点其中有一个比父节点大则进入分支，反之不做操作

                if (leftVal.compareTo(rightVal) >= 0) {//如果左节点大于等于右节点，就用左节点元素替换父节点元素
                    data.swap(index, lchild);
                    __sifDown(lchild);
                } else {
                    data.swap(index, rchild);
                    __sifDown(rchild);
                }
            }

             */

            //先判断出左右节点中大的那个，再用它和根节点判断要不要交换
            if (leftVal.compareTo(rightVal) >= 0){
                if (cur.compareTo(leftVal) < 0){
                    data.swap(index, lchild);
                    __siftDown(lchild);
                }
            }else {
                if (cur.compareTo(rightVal) < 0){
                    data.swap(index, rchild);
                    __siftDown(rchild);
                }
            }
        }
    }

    public E findMax(){
        if (data.getSize() == 0) throw new IllegalArgumentException("heap is empty");
        return data.get(0);
    }

    //取出堆中最大元素，并放入一个新的元素
    //可以通过先执行extractMax再执行add来实现，这样的话需要两次O(logn)的操作
    // 另一种实现是直接将堆顶元素替换为新元素，再进行SiftDown，只需一次O(logn)操作
    public E replace(E e){
        E max = findMax();
        data.set(0, e);
        __siftDown(0);
        return max;
    }

    public static void main(String[] args) {
        int n = 100;
        MaxHeap<Integer> heap = new MaxHeap<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            heap.add(random.nextInt(1000));
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = heap.extractMax();
        }

        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }

}
