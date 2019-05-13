package LeetCode.ListNode;

public class ListNode {
    protected int val;
    protected ListNode next;

    public ListNode(int x) {
        this.val = x;
    }

    public ListNode() {
    }

    public ListNode(int[] arr){
        if(arr == null || arr.length == 0) throw new IllegalArgumentException("array can not be empty");

        this.val = arr[0];
        ListNode cur = this;
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ListNode cur = this;
        while (cur != null){
            ret.append(cur.val + "->");
            cur = cur.next;
        }
        ret.append("NULL");
        return ret.toString();
    }
}
