package HashTable;

import java.util.TreeMap;

/**
 * 哈希表
 * 哈希冲突时使用java.util包的TreeMap(红黑树)解决
 * 这个版本 K不能使用无法compare的对象
 */
public class HashTable<K, V> {

    private static final int upperTol = 10;
    private static final int lowerTol = 2;
    private static final int initCapacity = 7;

    private TreeMap<K, V>[] hashtable;
    private int M;//哈希表的长度
    private int size;

    public HashTable(int M){
        this.M = M;
        this.size = 0;
        this.hashtable = new TreeMap[M];
        for (int i = 0; i < M; i++) {
            hashtable[i] = new TreeMap<>();
        }
    }
    public HashTable(){
        this(initCapacity);
    }

    //根据hash值定位
    private int hash(K key){
        return (key.hashCode() & 0x7fffffff ) % M;//先消除符号位(不等于取绝对值)
    }

    public int getSize(){
        return size;
    }

    public void add(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if (map.containsKey(key)){
            map.put(key, value);
        }else {
            map.put(key, value);
            size++;

            if (size >= upperTol * M){
                resize(M*2);
            }
        }
    }

    public V remove(K key){
        TreeMap<K, V> map = hashtable[hash(key)];
        V ret = null;
        if (map.containsKey(key)){
            ret = map.remove(key);
            size --;

            if (size < lowerTol * M && M >= 14){
                resize(M/2);
            }
        }
        return ret;
    }

    public void set(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if (!map.containsKey(key)) throw new IllegalArgumentException(key + " does not exist");

        map.put(key, value);
    }

    public boolean contains(K key){
        return hashtable[hash(key)].containsKey(key);
    }


    public V get(K key){
        return hashtable[hash(key)].get(key);
    }

    private void resize(int newM){
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for (int i = 0; i < newM; i++) {
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;//hash()计算的时候需要用新的M值
        for (int i = 0; i < oldM; i++) {
            TreeMap<K, V> map = hashtable[i];
            for (K key : map.keySet()){
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }
        this.hashtable = newHashTable;
    }

}
