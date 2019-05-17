package LeetCode.Heap;

import Queue.PriorityQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * 在1,000,000个元素中选出前100名
 * 在N个元素中选出前M个元素
 * 如果使用快速排序算法，时间复杂度是O(NlogN)
 * 可以使用优先队列(数据结构为最大堆)来实现，时间复杂度是O(NlogM)
 * 策略是将前面的100个元素放入优先队列中，再向后遍历，如果后面的元素比队列中最小的元素大，就用该元素替换进队列
 * 如果是选最小的前100名，就使用最大堆；选最大的前100名，就使用最小堆；每次都和堆顶元素比较，所以时间复杂度是O(NlogM)
 *
 * LeetCode中类似问题：347题.前K个高频元素
 * 给定一个非空的整数数组，返回其中出现频率前k高的y元素
 * 例如：数组[1,1,1,2,2,3]，输入k=2，输出[1,2]
 */
public class Solution347 {

    private class Freq implements Comparable<Freq>{
        public int e;
        public int freq;//频率

        public Freq(int e, int freq){
            this.e = e;
            this.freq = freq;
        }

        @Override
        public int compareTo(Freq o) {
            if (this.freq < o.freq) return 1;//频次低，优先级高，优先在堆中被替换
            else if (this.freq > o.freq) return -1;
            return 0;
        }
    }

    public List<Integer> topKFrequent(int[] nums, int k){

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i : nums){
            if (map.containsKey(i)){
                map.put(i, map.get(i)+1);
            }else {
                map.put(i, 1);
            }
        }

        PriorityQueue<Freq> queue = new PriorityQueue<Freq>();
        for (int key : map.keySet()){
            if (queue.getSize() < k){
                queue.enqueue(new Freq(key, map.get(key)));
            }else if (map.get(key) > queue.getFront().freq){
                queue.dequeue();
                queue.enqueue(new Freq(key, map.get(key)));
            }
        }

        LinkedList<Integer> list = new LinkedList<>();
        while (!queue.isEmpty()){
            list.add(queue.dequeue().e);
        }

        return list;
    }
}
