package LeetCode.SegmentTree;

import SegmentTree.SegmentTree;

/**
 * 题目要求与303号问题大致相同
 * 给定一个数组(可变)，求出数组从索引i到j范围内元素的总和，包含i,j两点
 * 可以使用update操作更改数组中的值
 * 如果使用额外开辟一个数组存储前i个元素的值的方式，时间复杂度为O(n)，会造成超时
 * 可以使用线段树的方式，时间复杂度为O(logn)
 */
public class Solution307 {

    private class NumArray{

        private SegmentTree<Integer> segmentTree;

        public NumArray(int[] nums){
            if (nums.length > 0){
                Integer[] data = new Integer[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    data[i] = nums[i];
                }
                segmentTree = new SegmentTree<Integer>(data, ((a, b) -> a+b));
            }
        }

        public void update(int i, int val){
            if (segmentTree == null) throw new IllegalArgumentException("");
            segmentTree.set(i, val);
        }

        public int sumRange(int i, int j){
            if (segmentTree == null) throw new IllegalArgumentException("");
            return segmentTree.query(i, j);
        }
    }
}
