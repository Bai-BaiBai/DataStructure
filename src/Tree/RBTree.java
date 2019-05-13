package Tree;

public class RBTree<K extends Comparable<K>, V> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public boolean color;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            color = RED; //添加节点的颜色默认是红色的，因为不能向空节点添加，只能融合
        }
    }

    private Node root;
    private int size;

    public RBTree() {
        this.root = null;
        this.size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    private boolean isRed(Node node){//判断节点的颜色
        if (node == null) return BLACK;
        return  node.color;
    }
}
