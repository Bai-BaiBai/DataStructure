package LeetCode.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * LeetCode 350号问题可以使用映射解决
 */
public class Solution350 {

    public int[] intersection(int[] nums1, int[] nums2){
        Map<Integer, Integer> map1 = new TreeMap<>();
        map1 = getMap(nums1, map1);

        ArrayList<Integer> list = new ArrayList<>();
        for (int num : nums2){
            if (map1.containsKey(num)){
                int count = map1.get(num);
                list.add(num);
                if (count-1 == 0){
                    map1.remove(num);
                }else {
                    map1.replace(num, count-1);
                }
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public Map<Integer, Integer> getMap(int[] nums, Map<Integer, Integer> map){
        for (int num : nums){
            if (!map.containsKey(num)){
                map.put(num, 1);
            }else {
                map.replace(num, map.get(num) + 1);
            }
        }
        return map;
    }
}
