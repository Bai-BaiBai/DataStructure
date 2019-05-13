package Set;

import java.util.ArrayList;

public class Main {

    /*
    使用Sey测试了傲慢与偏见和双城记两本书的词汇量
     */
    public static void main(String[] args) {
        System.out.println("Pride and prejudice");

        ArrayList<String> words1 = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words1)){
            System.out.println("Totel words :" + words1.size());

            BSTSet<String> bstSet1 = new BSTSet<String>();
            for(String word : words1){
                bstSet1.add(word);
            }
            System.out.println("Totel different words : " + bstSet1.getSize());
        }

        System.out.println();

        System.out.println("A tale of two cities");
        ArrayList<String> words2 = new ArrayList<>();
        if (FileOperation.readFile("a-tale-of-two-cities.txt", words2)){
            System.out.println("Totel words :" + words2.size());

            BSTSet<String> bstSet2 = new BSTSet<String>();
            for(String word : words2){
                bstSet2.add(word);
            }
            System.out.println("Totel different words : " + bstSet2.getSize());
        }
    }
}
