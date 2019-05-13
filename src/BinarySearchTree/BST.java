package BinarySearchTree;

import Array.Array;
import Queue.ArrayQueue;
import Stack.ArrayStack;

import java.util.Random;

public class BST< E extends Comparable<E> > {

    private class Node{
        public E e;
        public Node left, right;

        public Node(E e){
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST(){
        this.root = null;
        this.size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(E e){
        root = __add(e, root);
    }

    //返回添加后的树的根节点
    private Node __add(E e, Node node){
        if (node == null){
            node = new Node(e);
            size++;
        }else if (e.compareTo(node.e) < 0){//e小于root.e 就添加到左子树，大于等于添加到右子树
            node.left = __add(e, node.left);
        }else if (e.compareTo(node.e) > 0){//不包含重复元素，相等直接返回
            node.right = __add(e, node.right);
        }

        return node;
    }

    public boolean contains(E e){
        return __contains(e, root);
    }

    private boolean __contains(E e, Node node){
        if (node == null) return false;
        if (e.compareTo(node.e) == 0) {
            return true;
        }else if (e.compareTo(node.e) < 0){
            return __contains(e, node.left);
        }else {
            return __contains(e, node.right);
        }
    }

    //前序遍历
    public void preOrder(){
        __preOrder(root);
        System.out.println();
    }

    private void __preOrder(Node node){
        if (node == null) return;
        System.out.print(node.e + "  ");
        __preOrder(node.left);
        __preOrder(node.right);
    }

    //非递归实现前序遍历
    public void preOrderNR(){
        ArrayStack<Node> stack = new ArrayStack<Node>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.e);

            if (cur.right != null) stack.push(cur.right);
            if (cur.left != null) stack.push(cur.left);
        }
    }

    //非递归实现中序遍历
    public void midOrderNR(){
        ArrayStack<Node> stack = new ArrayStack<Node>();
        Node cur = root;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            if (!stack.isEmpty()) {
                cur = stack.pop();
                System.out.print(cur.e + " ");
                cur = cur.right;
            }
        }
        System.out.println();
    }

    //中序遍历
    public void midOrder(){
        __midOrder(root);
        System.out.println();
    }

    private void __midOrder(Node node){
        if (node == null){
            return;
        }

        __midOrder(node.left);
        System.out.print(node.e + " ");
        __midOrder(node.right);
    }

    //后序遍历
    public void postOrder(){
        __postOrder(root);
        System.out.println();
    }

    private void __postOrder(Node node){
        if (node == null){
            return;
        }
        __postOrder(node.left);
        __postOrder(node.right);
        System.out.print(node.e + " ");
    }

    //层序遍历(广度优先遍历)
    public void levelOrder(){
        ArrayQueue<Node> queue = new ArrayQueue<Node>();
        Node cur = root;
        if (cur != null) queue.enqueue(cur);
        while (!queue.isEmpty()) {
            cur = queue.dequeue();
            System.out.print(cur.e + " ");
            if (cur.left != null) queue.enqueue(cur.left);
            if (cur.right != null) queue.enqueue(cur.right);
        }
        cur = null;
        System.out.println();
    }

    //寻找最小元素
    public E getMinimum(){
        if (isEmpty()) throw new IllegalArgumentException("BST is Empty!");
        return __minimum(root).e;
    }

    //返回以node为根中的最小节点
    private Node __minimum(Node node){
        if (node.left == null){
            return node;
        }else {
            return __minimum(node.left);
        }
    }

    //寻找最大元素
    public E getMaximun(){
        if (isEmpty()) throw new IllegalArgumentException("BST is Empty!");
        return __maximum(root).e;
    }

    //返回以node为根中的最大节点
    private Node __maximum(Node node){
        if (node.right == null) return node;
        return __maximum(node.right);
    }

    public E removeMinimum(){
        E res = getMinimum();
        root = __removeMinimum(root);
        return res;
    }

    //删除以node为根节点的树中的最小元素，并返回删除后的根节点
    private Node __removeMinimum(Node node){
        if (node.left == null){
            Node rightNode = node.right;//保存一下右子树
            node.right = null;//将原来的右子树断开
            size --;
            return rightNode;//返回右子树的根作为新根节点
        }
        node.left = __removeMinimum(node.left);
        return node;
    }

    public E removeMaximum(){
        E res = getMaximun();
        root = __removeMaximum(root);
        return res;
    }

    //删除以node为根节点的树中的最大元素，并返回删除后的根节点
    private Node __removeMaximum(Node node){
        if (node.right == null){
            Node res = node.left;
            node.left = null;
            size --;
            return res;//因为node所指节点即为要删除的最大节点，所以返回它的左子树，让前一节点连接
        }
        node.right = __removeMaximum(node.right);
        return node;
    }

    //被删除的节点如果左右子树均有值，采用寻找比该节点大的最小节点来替代该节点
    public void removeElement(E e){
        if (!contains(e)) return; //不存在该元素直接返回
        root = __removeElement(root, e);
    }

    //删除以node为根的树中值为e的节点
    //返回删除节点后的树的根
    private Node __removeElement(Node node, E e){
        if (e.compareTo(node.e) == 0){
//            size --;   注意size--不能放在这里，因为下方调用__removeMinimum过程中已经进行size--
            if (node.left == null){//待删除节点左子树为空的情况
                Node res = node.right;
                node.right = null;
                size --;
                return res;
            }
            if (node.right == null){//待删除节点右子树为空的情况
                Node res = node.left;
                node.left = null;
                size --;
                return res;
            }

            //待删除节点左右子树均不为空的情况
            Node res = __minimum(node.right);//先拿到右子树中最小的节点(作为新根)
            res.right = __removeMinimum(node.right);//将右子树中的最小节点删除,并将删除了最小节点的右子树的根 作为新根的右子树
            res.left = node.left;
            node.left = node.right = null;//使node节点脱离树，不写也可以
            return res;
        }else if (e.compareTo(node.e) < 0){
            node.left = __removeElement(node.left, e);
            return node;
        }else{
            node.right = __removeElement(node.right, e);
            return node;
        }
    }

    public E getFloor(E e){

        Node node = __getFloor(root, e);
        return node == null ? null : node.e;
    }

    //取的下限元素
    private Node __getFloor(Node node, E e){

        if (node == null) return null;

        Node res = null;
        if (e.compareTo(node.e) < 0 ){
            res = __getFloor(node.left, e);
        }else if (e.compareTo(node.e) == 0) {
            res = node;
        }else {
            res = __getFloor(node.right, e);
        }
        return res == null ? node : res;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    //前序遍历生成树的遍历
    private void generateBSTString(Node node, int depth, StringBuilder res){
        if (node == null){
            res.append(generateDepthString(depth) +  "NULL\n");
            return;
        }
        res.append(generateDepthString(depth) + node.e + "\n");
        generateBSTString(node.left, depth+1, res);
        generateBSTString(node.right, depth+1, res);
    }

    private String generateDepthString(int depth){//根据深度生成--
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();

        int[] nums = {5, 3, 6, 8, 4, 2};
        for(int num : nums){
            bst.add(num);
        }
        /////////////////////
        //        5        //
        //      /  \       //
        //     3    6      //
        //   / \     \     //
        //  2  4     8     //
        /////////////////////
        bst.midOrder();
        bst.midOrderNR();
        bst.removeElement(5);
        bst.levelOrder();
        System.out.println(bst.getFloor(6));
    }

}
