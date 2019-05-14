package LeetCode.ListNode.LeetCode.Set;

import java.util.TreeSet;

/**
 * LeetCode 804问题
 */
public class Solution {

    //求有多少不同的摩斯码
    public static int uniqueMorseRepresentations(String[] words){
        //26个字母对应的密码
        String[] codes = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};

        TreeSet<String> set = new TreeSet<>();
        for (String word : words){

            StringBuilder res = new StringBuilder();
            for (int i = 0; i < word.length(); i++){ //在res中存储word这个单词对应的摩斯码
                res.append(codes[word.charAt(i) - 'a']);
            }
            set.add(res.toString());//将每个单词的摩斯码放入set中筛选掉重复的
        }

        return set.size();
    }
}
