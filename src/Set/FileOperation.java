package Set;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

//读取文件并将文件中的单词放入到words中
public class FileOperation {

    //读取文件名为filename的文件
    public static boolean readFile(String filename, ArrayList<String> words){
        if (filename == null || words == null) return false;

        Scanner scanner;

        try{
            File file = new File(filename);
            if (file.exists()){
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                scanner.useLocale(Locale.ENGLISH);//本地的文件是英文所以设置为英文
            }else {
                return false;
            }
        } catch (IOException e){
            System.out.println("can't open " + filename);
            return false;
        }

        //简单分词，只要是拼写不同就算作不同单词，不考虑时态问题
        if (scanner.hasNextLine()){
            String content = scanner.useDelimiter("\\A").next();

            int start = firstCharacterIndex(content, 0);
            for (int i = start + 1; i <= content.length();){
                if (i == content.length() || !Character.isLetter(content.charAt(i))){
                    String word = content.substring(start, i).toLowerCase();
                    words.add(word);
                    start = firstCharacterIndex(content, i);
                    i = start + 1;
                }else {
                    i++;
                }
            }
        }

        return true;
    }

    //寻找字符串s中，从start的位置开始的第一个字符的位置
    private static int firstCharacterIndex(String s, int start){
        for (int i = start; i < s.length(); i++){
            if (Character.isLetter(s.charAt(i))){
                return i;
            }
        }
        return s.length();
    }
}
