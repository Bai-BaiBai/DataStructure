package LeetCode.ListNode;

import Array.Array;

public class Solution {

    public ListNode removeElements(ListNode head, int val) {

        //使用虚拟头结点完成
//        ListNode dummyHead = new ListNode(-1);
//        dummyHead.next = head;
//
//        ListNode prev = dummyHead;
//        while (prev.next != null){
//            if (prev.next.val == val){
//                prev.next = prev.next.next;
//            }else {
//                prev = prev.next;
//            }
//        }
//
//        return dummyHead.next;

        while (head != null && head.val == val) {
            ListNode delNode = head;
            head = head.next;
            delNode.next = null;
        }

        if (head == null) return null;

        ListNode prev = head;
        while (prev.next != null) {
            if (prev.next.val == val) {
                ListNode delNode = prev.next;
                prev.next = delNode.next;
                delNode.next = null;
            } else {
                prev = prev.next;
            }
        }

        return head;
    }

    public ListNode removeElements2(ListNode head, int val) {

        if (head == null) return null;

        if (head.val == val){
            return removeElements2(head.next, val);
        }else {
            head.next = removeElements2(head.next, val);
            return head;
        }
    }

    public int sum(int[] arr){
        return sum(arr, 0);
    }

    private int sum(int[] arr, int start){
        return start == arr.length ? 0 : arr[start] + sum(arr, start+1);
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 6, 3, 4, 5, 6};
        ListNode head = new ListNode(nums);
        System.out.println(head);

        new Solution().removeElements2(head, 6);
        System.out.println(head);

        System.out.println(new Solution().sum(nums));
    }
}
