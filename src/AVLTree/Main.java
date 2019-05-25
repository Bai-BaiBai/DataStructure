package AVLTree;

import Set.FileOperation;

import java.util.ArrayList;

/**
 * AVLTree测试用例：傲慢与偏见词频统计
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<String, Integer>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println("is BST : " + map.isBST());
            System.out.println("is Balanced : " + map.isBalance());

            for (String word : words) {
                if (map.contains(word)) {
                    map.remove(word);
                    if (!map.isBST() || !map.isBalance())
                        throw new RuntimeException("Error");
                }
            }

            System.out.println(map.getSize());
        }

        System.out.println();
        System.out.println("OK");
    }
}
