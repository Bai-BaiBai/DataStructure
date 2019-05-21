package LeetCode.Trie;

import java.util.TreeMap;

/**
 * LeetCode677号问题--
 * 实现一个 MapSum 类里的两个方法，insert 和 sum。
 * insert方法，输入一对（字符串，整数）的键值对。字符串表示键，整数表示值。如果键已经存在，那么替代原来的值
 * sum方法，输入一个前缀字符串，返回以该前缀开头的所以字符串的value总和
 */
public class Solution677 {

    private class Node{
        public boolean isWord;
        public int value;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord, int value){
            this.isWord = true;
            this.value = value;
            this.next = new TreeMap<>();
        }
        public Node(){
            this(false, 0);
        }
    }

    private Node root;

    public Solution677(){
        this.root = new Node();
    }

    public void insert(String key, int val){
        Node cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (cur.next.get(c) == null){
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        cur.isWord = true;
        cur.value = val;
    }

    public int sum(String prefix){

        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) return 0;
            cur = cur.next.get(c);
        }
        //此时cur指向prefix的最后一个字符节点
        //遍历该节点下所有的节点，找到isWord = true 的节点
        int sum = 0;
        if (cur.isWord) sum += cur.value;//prefix也算作一个单词
        sum += __sum(cur);
        return sum;
    }

    //这种求和方式需要在调用手动判断prefix的结尾字符是不是一个单词的结尾
    //这个的策略是node节点的value在上一步就已经添加了，然后取递归它的后面节点
    //不知道为什么，提交答案时，这种方案比下面那种方案时间和内存消耗都小一点点
    private int __sum(Node node){
        int res = 0;//该节点的值在上一步已经添加完成了
        for (Node cur : node.next.values()){
            if (cur.isWord){
                res += cur.value;
            }
            res += __sum(cur);
        }
        return res;
    }

    /*
    //这种策略是node节点的值在当前过程添加，然后去递归后面的节点
    //这种求和方式不需要使用node中的isWord标志位，因为value=0就能表明该节点不是字符串结尾
    private int __sum(Node node){
        int res = node.value;
        for (Node cur : node.next.values()){ //如果该节点不是字符串结尾，该节点的value=0，只需要将所有子节点的value求和就可以了
            res += __sum(cur);
        }
        return res;
    }

     */
}
