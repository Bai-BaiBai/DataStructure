package Array;

public class Main {

    public static void main(String[] args) {
        Array<Integer> array = new Array<Integer>(10);
        for (int i = 1; i < 10; i++) {
            array.addLast(i);
        }
        array.add(1,100);
        System.out.println(array);
        System.out.println(array.remove(0));
        System.out.println(array.removeAllElement(1));
        System.out.println(array);
        array.add(1,100);
        array.add(1,100);
        System.out.println(array);
        array.remove(1);
        array.remove(1);
        array.remove(1);
        System.out.println(array);
    }
}
