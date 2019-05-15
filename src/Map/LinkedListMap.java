package Map;

/**链表作为底层数据结构实现Map
 * 通常无序映射使用Hash表实现，链表效率太低了
 * 增、删、改、查操作时间复杂度都是O(n)
 * @param <K>
 * @param <V>
 */
public class LinkedListMap<K, V> implements Map<K, V> {

    private class Node{
        public K key;
        public V value;
        public Node next;

        public Node(K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key) {
            this(key, null, null);
        }

        public Node(){
            this(null, null, null);
        }

        @Override
        public String toString() {
            return key.toString() + " : " + value.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedListMap(){
        this.dummyHead = new Node();
        this.size = 0;
    }

    //根据key返回该节点，辅助函数
    private Node getNode(K key){
        Node cur = dummyHead.next;
        while (cur != null){
            if (cur.key.equals(key)){
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }

    //增加键值对，如果存在则更新value
    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if (node == null){
            dummyHead.next = new Node(key, value, dummyHead.next);
            size++;
        }else {
            node.value = value;
        }
    }

    @Override
    public V remove(K key) {
        Node prev = dummyHead;
        while (prev.next != null){
            if (prev.next.key.equals(key)){
                Node delNode = prev.next;
                prev.next = delNode.next;
                delNode.next = null;//将要删除的节点从链表中断开
                size --;
                return delNode.value;
            }
            prev = prev.next;
        }

        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    //修改key所对应的value，如果不存在则抛出异常
    @Override
    public void set(K key, V newValue) {
        Node node = getNode(key);
        if (node == null) throw new IllegalArgumentException(key + " don't exist!");
        node.value = newValue;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        LinkedListMap<String, String> map = new LinkedListMap<String, String>();
        map.add("1", "a");
        map.add("1", "a");
        map.add("1", "a");
        System.out.println(map.getSize());
        map.set("1", "b");
        map.add("2", "b");
        System.out.println(map.get("1"));
        map.remove("2");
        System.out.println(map.getSize());
        System.out.println(map.contains("2"));

    }

}
