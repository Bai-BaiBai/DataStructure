package Map;

import Set.FileOperation;

import java.util.ArrayList;

/**
 * 使用二分搜索树作为底层数据结构实现的有序映射
 * 增删改查的时间复杂度平均为O(h) = O(logn)
 * 有局限性：二分搜索树可能退化为链表，时间复杂度最差为O(n)
 * @param <K>
 * @param <V>
 */
public class BSTMap< K extends Comparable<K>, V> implements Map<K, V>{

    private class Node{
        public K key;
        public V value;
        public Node left, right;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BSTMap(){
        this.root = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
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

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) throw new IllegalArgumentException(key + " don't exist");

        node.value = newValue;
    }

    @Override
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

            BSTMap<String, Integer> map = new BSTMap<String, Integer>();
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
