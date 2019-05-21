package LeetCode.Trie;

import java.util.Map;
import java.util.TreeMap;

/**
 * LeetCode211号问题--添加与搜索单词-数据结构设计
 * 设计一个支持addWord(word)和search(word)操作的数据结构
 * 字符串包含.和26个字母，.代表任何一个字符
 */
public class Solution211 {

    private class Node{
        public boolean isWord;
        public Map<Character, Node> next;

        public Node(boolean isWord){
            this.isWord = isWord;
            this.next = new TreeMap<>();
        }
        public Node(){
            this(false);
        }
    }

    private Node root;

    public Solution211(){
        this.root = new Node();
    }

    public  void addWord(String word){
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
        }
    }

    //查找一个单词，支持正则表达式，.代表任意字母
    public boolean search(String word){
        return __search(root, word, 0);
    }

    //在node节点中寻找word[index]是否存在，node节点是word[index-1]所在位置，初始为root
    private boolean __search(Node node, String word, int index){
        if (index == word.length()) return node.isWord; //如果index越界 说明当前node位于word[index-1]，返回最后一个字符是否是一个单词结尾

        char c = word.charAt(index);
        if (c != '.'){
            if (node.next.get(c) == null) {//如果node节点不存在该字符对应的next 返回false
                return false;
            } else {
                return __search(node.next.get(c), word, index+1);
            }
        }else {
            for (Node next : node.next.values()){
                if (__search(next, word, index+1)) return true;
            }
            return false;
        }
    }
}
