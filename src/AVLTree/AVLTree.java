package AVLTree;

import Set.FileOperation;

import java.util.ArrayList;

/**
 * AVL树的实现
 * 一种平衡二叉树：保证不会退化成链表，查询时间复杂度为O(logn)
 * 在BSTMap的代码上进行修改：
 *      增加属性：Node节点添加height属性
 *      增加方法：getBalanceFactor 平衡因子的计算
 *      增加方法：判断该树是否是一棵平衡二叉树：isBST
 *      增加方法：判断该树是否是平衡二叉树：isBalance
 *      增加内容：
 *          在add、remove过程中根据平衡因子维护树：针对每个节点递归回来时，height=左右子树高度最大值+1
 *          每次递归add、remove的过程，回溯的时候判断每个节点的平衡因子的值，不平衡又分为LL、RR、LR、RL四种情况，
 *          LL进行右旋转；RR左旋转；LR先左旋变LL再右旋；RL先右旋变RR再左旋
 *
 *          remove时，不调用之前的__removeMinimum方法删除右节点中最小的元素，而是暂存最小节点并复用__remove(node.right, minKey)去删除该节点
 *          这是因为在__removeMinimum中维护高度可能会出BUG，
 */
public class AVLTree< K extends Comparable<K>, V > {

    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 1;//新添加的节点高度值为1
        }
    }

    private Node root;
    private int size;

    public AVLTree(){
        this.root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //判断该二叉树是否是一棵二分搜索树
    public boolean isBST(){
        //利用二分搜索树的性质，中序遍历出来的节点的key是升序排列的
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i-1).compareTo(keys.get(i)) > 0){
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node node, ArrayList<K> keys){
        if (node == null) return;
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    //判断该二叉树是不是平衡的
    public boolean isBalance(){
        return isBalance(root);
    }

    public boolean isBalance(Node node){
        if (node == null) return true;
        if (Math.abs(getBalanceFactor(node)) > 1) return false;
        return isBalance(node.left) && isBalance(node.right);//左右两棵子树均平衡
    }

    //辅助函数--获取node节点的高度值，如果node为null，返回0
    private int getHeight(Node node){
        return node == null ? 0 : node.height;
    }

    //获得节点node的平衡因子--如果 node=null 平衡因子为0
    private int getBalanceFactor(Node node){
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);//不在这里返回绝对值，因为还要根据它判断是哪边高
    }

    // LL情况下，直接右旋转就好
    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    private Node rightRotate(Node y){
        Node x = y.left;
        Node T3 = x.right;

        x.right = y;
        y.left = T3;

        //更新高度值
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    // RR情况下，直接左旋转
    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    private Node leftRotate(Node y){
        Node x = y.right;
        Node T2 = x.left;

        x.left = y;
        y.right = T2;

        //更新高度值
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    public void add(K key, V value) {
        root = __add(root, key, value);
    }

    //递归添加键值对，如果key相等进行value的更新
    private Node __add(Node node, K key, V value){
        if (node == null){
            size ++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0){
            node.left = __add(node.left, key, value);
        }else if (key.compareTo(node.key) > 0){
            node.right = __add(node.right, key, value);
        }else { //key.compareTo(node.key) = 0
            node.value = value;
        }

        //1.更新height值--添加节点或更新节点后，都重新计算一下高度值，取左右子树的最大高度+1
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        //2.计算平衡因子, >0代表左高，<0代表右高
        int balanceFactor = getBalanceFactor(node);

        //3.维护平衡性
        //LL表示：新插入的节点是在不平衡节点的左孩子的左孩子中插入的，RR、LR、RL同理
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0){//LL整棵树完全左倾，右旋转
            return rightRotate(node);//此时return的是原先node.left
        }else if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0){//RR整棵树完全右倾斜，左旋转
            return leftRotate(node);
        }else if (balanceFactor > 1 && getBalanceFactor(node.left) < 0){// LR
            node.left = leftRotate(node.left);//将左孩子进行左旋转，LR转变成LL
            return rightRotate(node);
        }else if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {// RL
            node.right = rightRotate(node.right);//将右孩子进行右旋转，RL转变成RR
            return leftRotate(node);
        }

        return node;
    }

    //辅助函数，返回key所对应的节点
    private Node getNode(Node node, K key){
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0){
            return getNode(node.left, key);
        }else if (key.compareTo(node.key) > 0){
            return getNode(node.right, key);
        }else {
            return node;
        }
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    //取出key所对应的值
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    //更新操作
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) throw new IllegalArgumentException(key + " don't exist");

        node.value = newValue;
    }

    //从BST中删除某一结点
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node == null) throw new IllegalArgumentException(key + " don't exist");

        root = __remove(root, key);
        return node.value;
    }

    //删除以node为根中key所对应的节点，并返回新的根节点
    //策略是以比待删除节点大的节点替换该节点的位置，如果没有则用左子树替代
    private Node __remove(Node node, K key){
        if (node == null) return null;//此条件在该应用下无法触发，因为调用前已经判断了是否存在key的节点，但是为了规范还是应该写明

        Node retNode;
        if (key.compareTo(node.key) < 0){
            node.left = __remove(node.left, key);
            retNode = node;
        }else if (key.compareTo(node.key) > 0){
            node.right = __remove(node.right, key);
            retNode =  node;
        }else {
            if (node.left == null) {//待删除节点左子树为空的情况
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNode = rightNode;
            }else if (node.right == null){//待删除节点右子树为空的情况
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNode = leftNode;
            }else {//待删除节点左右子树均不为空的情况
                Node newNode = __getMinimun(node.right);//从右子树中选出一个最小节点，也就是比该节点大的节点，替换它的位置
                newNode.right = __remove(node.right, newNode.key);//删除右子树中最小节点，这里复用remove函数，来维护height值
                newNode.left = node.left;//这三步是将右子树中最小的节点作为该树的新根，相当于删除了原本的根节点，也就是删除了key对应的节点
                node.left = node.right = null;
                retNode = newNode;
            }
        }

        if (retNode == null) return null; //维护平衡性之前判断返回节点是否为null

        {//更新返回节点的height值并且维护平衡性

            //1.更新height值--添加节点或更新节点后，都重新计算一下高度值，取左右子树的最大高度+1
            retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

            //2.计算平衡因子, >0代表左高，<0代表右高
            int balanceFactor = getBalanceFactor(retNode);

            //3.维护平衡性
            //LL表示：新插入的节点是在不平衡节点的左孩子的左孩子中插入的，RR、LR、RL同理
            if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {//LL整棵树完全左倾，右旋转
                return rightRotate(retNode);//此时return的是原先node.left
            } else if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {//RR整棵树完全右倾斜，左旋转
                return leftRotate(retNode);
            } else if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {// LR
                retNode.left = leftRotate(retNode.left);//将左孩子进行左旋转，LR转变成LL
                return rightRotate(retNode);
            } else if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {// RL
                retNode.right = rightRotate(retNode.right);//将右孩子进行右旋转，RL转变成RR
                return leftRotate(retNode);
            }

        }

        return retNode;
    }

    //返回以node为根的树中最小元素节点
    private Node __getMinimun(Node node){
        if (node == null) return null;
        if (node.left == null){
            return node;
        }
        return __getMinimun(node.left);
    }
}
