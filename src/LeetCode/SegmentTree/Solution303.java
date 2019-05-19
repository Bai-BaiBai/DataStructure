package LeetCode.SegmentTree;

import SegmentTree.SegmentTree;

/**
 * LeetCode303号问题--区域和检索(数组不可变)
 * 给定一个数组，求出数组从索引i到j范围内元素的总和，包含i,j两点
 *
 */
public class Solution303 {

    //使用线段树的方式
    private class NumArray{

        private SegmentTree<Integer> segmentTree;

        public NumArray(int[] nums){
            if (nums != null && nums.length > 0){
                Integer[] data = new Integer[nums.length];
                for (int i = 0; i < nums.length; i++) {
                    data[i] = nums[i];
                }
                this.segmentTree = new SegmentTree<Integer>(data, ((a, b) -> a+b));
            }
        }

        public int sumRange(int i, int j){
            return segmentTree.query(i, j);
        }
    }

    //不使用线段树的方式，开辟一个数组存储前i个元素的和
    private class NumArray2{

        private int[] sum; //sum[i]存储前i个元素的和, sum[0] = 0  存储nums[0....i-1]的和

        public NumArray2(int[] nums){
            this.sum = new int[nums.length + 1];
            sum[0] = 0;
            for (int i = 1; i <= nums.length; i++) {
                sum[i] = sum[i-1] + nums[i-1];
            }
        }

        public int sumRange(int i, int j){
            return sum[j+1] - sum[i] ;//sum[j+1]中存储的是nums[0...j]的和，返回结果应包括 nums[i]
        }

    }
}
