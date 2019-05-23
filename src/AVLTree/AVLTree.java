package AVLTree;

/**
 * AVL树的实现
 * 一种平衡二叉树
 * 在BSTMap的代码上进行修改：
 * Node节点添加height并维护
 * 添加平衡因子的计算
 * 在add过程中根据平衡因子维护树
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

    //辅助函数--获取node节点的高度值，如果node为null，返回0
    private int getHeight(Node node){
        return node == null ? 0 : node.height;
    }

    //获得节点node的平衡因子--如果 node=null 平衡因子为0
    private int getBalanceFactor(Node node){
        return node == null ? 0 : getHeight(node.left) - getHeight(node.right);//不在这里返回绝对值，因为还要根据它判断是哪边高
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

        //更新height值--添加节点或更新节点后，都重新计算一下高度值，取左右子树的最大高度+1
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        //计算平衡因子
        int balanceFactor = Math.abs(getBalanceFactor(node));
        if (balanceFactor > 1){
            //旋转操作
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

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) throw new IllegalArgumentException(key + " don't exist");

        node.value = newValue;
    }

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

        if (key.compareTo(node.key) < 0){
            node.left = __remove(node.left, key);
            return node;
        }else if (key.compareTo(node.key) > 0){
            node.right = __remove(node.right, key);
            return node;
        }else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;//可以直接返回node.right，手动的置null一下可能利于垃圾回收吧？
            }
            if (node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }
            Node newNode = __getMinimun(node.right);//从右子树中选出一个最小节点，也就是比该节点大的节点，替换它的位置
            newNode.right = __removeMinimum(node.right);
            newNode.left = node.left;//这三步是将右子树中最小的节点作为该树的新根，相当于删除了原本的根节点，也就是删除了key对应的节点
            node.left = node.right = null;
            return newNode;
        }
    }

    //返回以node为根的树中最小元素节点
    private Node __getMinimun(Node node){
        if (node == null) return null;
        if (node.left == null){
            return node;
        }
        return __getMinimun(node.left);
    }

    //删除以node为根的树中的最小元素并返回新的根节点
    private Node __removeMinimum(Node node){
        if (node == null) return null;

        if (node.left != null){
            node.left = __removeMinimum(node.left);
        }else {
            Node rightNode = node.right;
            node.right = null;
            size --;
            node = rightNode;
        }
        return node;
    }
}
