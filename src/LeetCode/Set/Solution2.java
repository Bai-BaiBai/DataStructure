package LeetCode.Set;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * LeetCode 349问题,使用Set解决
 */
public class Solution2 {

    public int[] intersection(int[] nums1, int[] nums2){
        TreeSet<Integer> set = new TreeSet<>();
        for (int num : nums1) {
            set.add(num);
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int num : nums2){
            if (set.contains(num)){
                res.add(num);
                set.remove(num);
            }
        }
        int[] ret = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ret[i] = res.get(i);
        }
        return ret;
    }
}
