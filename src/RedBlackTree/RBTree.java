package RedBlackTree;

import Set.FileOperation;

import java.util.ArrayList;

/**
 * 红黑树--等价于2-3树
 * 基于BSTMap代码修改
 * 所有节点向左倾斜，根节点一定是黑色的，红节点与它的父节点可以看作是一个3节点
 * 保持"黑平衡"的二叉树，严格意义上不是平衡二叉树
 * 最大高度2logn,增删改查时间复杂度O(logn)
 * 与AVL树对比：
 *      查询频繁时，AVL树的性能高，因为AVL树的最大高度是logn
 *      增加和删除频繁时，红黑树的性能高，因为AVL树有旋转操作的开销
 */
public class RBTree< K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public boolean color;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.color = RED;//默认是红色，添加节点永远是和叶子节点融合形成3节点或临时4节点
        }
    }

    private Node root;
    private int size;

    public RBTree(){
        this.root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //判断node节点的颜色，空节点为黑色
    private boolean isRed(Node node){
        if (node == null) return BLACK;
        return node.color;
    }

    //对node为根的树进行左旋转,返回新的根节点
    //调用场景：x和一个2节点(node)融合时，并且>node 也就是需要作为node的右孩子
    //          此时该节点与叶子节点是3节点(或4)，但是顺序不对，需要将这两个节点调换位置(也就是右旋操作)并交换颜色
    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate(Node node){
        Node x = node.right;//暂存一下右子树
        node.right = x.left; //使x脱离
        x.left = node;//完成旋转

        //这里交换它俩的颜色，x作为新添加进红黑树的节点一定是RED，而node的颜色不一定
        x.color = node.color;
        node.color = RED;
        return x;
    }

    //颜色翻转，node为一个临时4节点的中间元素(黑节点)
    //                             黑(node)
    // 翻转之前该4节点的样子-->   /   \
    //                          红   红(它可以是新添加进来的元素，也可以是右旋转之后的元素)
    //调用场景：x向一个3节点融合，并且是3个元素中最大的那个(也就是作为3节点中黑节点的右孩子)
    // -------->此时需要将临时4节点分解，只需让4节点中两个RED变成BLACK，将BLACK节点变成RED来让它与父节点融合
    private void flipColors(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    //对node为根的树进行右旋转,返回新的根节点
    //调用场景：融合一个新节点到3节点,node为原先3节点的黑节点，
    //          融合后新节点<node，node依然为4节点中最大的，该4节点向左倾斜，需要右旋转来让4节点变成 红-黑-红 的形式
    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node){
        Node x = node.left;

        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;
        return x;
    }

    //向红黑树中添加节点
    public void add(K key, V value) {
        root = __add(root, key, value);
        root.color = BLACK; //红黑树的根节点一定为黑色
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

    public static void main(String[] args) {
        //测试代码，使用BSTMap测试傲慢与偏见中 pride 和 prejudice的出现频率
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            RBTree<String, Integer> map = new RBTree<String, Integer>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}
