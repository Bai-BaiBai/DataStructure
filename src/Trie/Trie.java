package Trie;

import java.util.TreeMap;//底层用红黑树实现

/**
 * Trie(字典树)是一种专门用于处理字符串的树结构
 * 如果有n个条目，使用普通的BST查询的时间复杂度是O(logn)
 * Trie查询每个条目的时间复杂度与字典中的条目数量无关！与字符串长度w有关，为O(w)
 * 同时也是LeetCode208号问题的答案
 */
public class Trie {

    private class Node{

        //不需要存储当前节点保存的是哪个字符，因为进入该节点之前就已经知道了是哪个字符
        public boolean isWord;//标识当前字符是否是某个字符串的结尾
        public TreeMap<Character, Node> next;//存储子节点，限定存储的字符只能是Character对象

        public Node(boolean isWord){
            this.isWord = isWord;
            this.next = new TreeMap<>();
        }

        public Node(){
            this.isWord = false;
            this.next = new TreeMap<>();
        }

    }

    private Node root;
    private int size;//存储的单词的数量，而不是节点数

    public Trie(){
        this.root = new Node();
        this.size = 0;
    }

    //获得Trie中单词的数量，不是节点数
    public int getSize(){
        return size;
    }

    //向Trie中添加一个新单词word
    //如果使用递归，递归函数的参数为当前节点Node,字符串word,当前字符在word中的index index=word.length终止
    public void add(String word){
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null){//如果这个字符在Trie中不存在,将它添加进去
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);//将cur指向当前字符节点
        }

        if (!cur.isWord) {//此时cur指向word的最后一个字符的节点，如果该节点isWord为true 表明已经存在该单词
            cur.isWord = true;
            size++;
        }
    }

    //查询单词word是否存在
    public boolean contains(String word){
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) return false;
            cur = cur.next.get(c);
        }
        return cur.isWord;//此时cur指向word最后一个字符的节点
    }

    //查询Trie中是否有单词以prefix为前缀
    public boolean isPrefix(String prefix){
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) return false;
            cur = cur.next.get(c);
        }
        return true;
    }
}
