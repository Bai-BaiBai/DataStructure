package LeetCode.Heap;

import java.util.*;

/**
 * 使用java.util中的PriorityQueue解决347号问题
 * java.util.PriorityQueue内部是最小堆实现，构造参数可以传入一个Comparator实例
 */
public class Solution347_2 {

    //如果要修改不能定义的类的比较方法，可以使用比较器的方式
    public List<Integer> topKFrequent(int[] nums, int k){

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i : nums){
            if (map.containsKey(i)){
                map.put(i, map.get(i)+1);
            }else {
                map.put(i, 1);
            }
        }

        //可以传入一个Comparator作为比较的逻辑
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(
                (a, b) -> map.get(a) - map.get(b)
        );
        for (int key : map.keySet()){
            if (priorityQueue.size() < k){
                priorityQueue.add(key);
            }else if (map.get(key) > map.get(priorityQueue.peek())){
                priorityQueue.remove();
                priorityQueue.add(key);
            }
        }

        LinkedList<Integer> list = new LinkedList<>();
        while (!priorityQueue.isEmpty()){
            list.add(priorityQueue.remove());
        }

        return list;
    }
}
