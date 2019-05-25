package Map;

import AVLTree.AVLTree;

/**
 * 基于AVLTree实现的Map
 * 增删改查时间复杂度稳定在O(logn)
 * @param <K>
 * @param <V>
 */
public class AVLMap< K extends Comparable<K>, V> implements  Map<K ,V>{

    private AVLTree<K, V> avl;

    public AVLMap(){
        this.avl = new AVLTree<K, V>();
    }

    @Override
    public void add(K key, V value) {
        avl.add(key, value);
    }

    @Override
    public V remove(K key) {
        return avl.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return avl.contains(key);
    }

    @Override
    public V get(K key) {
        return avl.get(key);
    }

    @Override
    public void set(K key, V newValue) {
        avl.set(key, newValue);
    }

    @Override
    public int getSize() {
        return avl.getSize();
    }

    @Override
    public boolean isEmpty() {
        return avl.isEmpty();
    }
}
