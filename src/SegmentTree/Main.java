package SegmentTree;

public class Main {

    public static void main(String[] args) {
        Integer[] nums = {0, 1, 2, 3, 4, 5};

        SegmentTree<Integer> tree = new SegmentTree<Integer>(nums, (a,b) -> a+b );

        System.out.println(tree.query(1,1));
    }
}
