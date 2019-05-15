package Array;

public class Array<E> {

    private E[] data;
    private int size;

    public Array(int capacity) {//初始化数组，传入数组容量capacity
        data = (E[]) new Object[capacity];
    }

    public Array() {//默认初始化容量为10
        this(10);
    }

    public int getSize() {//返回数组元素个数
        return size;
    }

    public int getCapcity(){//返回数组容量
        return data.length;
    }

    public boolean isEmpty(){//返回数组是否为空
        return size == 0;
    }

    public void add(int index, E e){//向数组中的指定位置添加一个元素
        if(index < 0 || index > size){//不能隔空添加
            throw new IllegalArgumentException("插入索引不合法");
        }
        if(size == data.length){//如果满了就扩容
            resize(data.length * 2);
        }
        for (int i = size-1; i >= index; i--) {
            data[i+1] = data[i];
        }
        data[index] = e;
        size++;
    }

    public void addLast(E e){//向数组末端添加元E素
//        if(size == data.length){
//            throw new IllegalArgumentException("满了");
//        }
//        data[size] = e;
//        size ++;
        add(size, e);//相当于向size下标的位置添加元素
    }

    public void addFirst(E e){
        add(0,e);//相当于向0位置添加元素
    }

    public E get(int index){//按索引取元素
        if(index < 0 || index >= size){ //保证数据安全，没有元素的位置不能访问
            throw new IllegalArgumentException("索引不合法");
        }
        return data[index];
    }

    public E getLast(){
        return get(size -1);
    }

    public E getFirst(){
        return  get(0);
    }

    public void set(int index, E e){//按索引设置元素
        if(index < 0 || index >= size){ //保证数据安全，没有元素的位置不能访问
            throw new IllegalArgumentException("索引不合法");
        }
        data[index] = e;
    }

    public boolean contains(E e){//查找元素中是否有e
        for (int i = 0; i < size; i++) {
            if(data[i].equals(e)){
                return true;
            }
        }
        return false;
    }

    public int find(E e){//查找e元素的索引，如果没有该元素返回-1
        for (int i = 0; i < size; i++) {
            if(data[i].equals(e)){
                return i;
            }
        }
        return -1;
    }

    public E remove(int index){//删除并返回该索引的元素
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("索引不合法");
        }
        E ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i-1] = data[i];
        }
        size --;
        data[size] = null;//回收size指向的垃圾对象
        if(size == data.length / 4 && data.length > 10){ //lazy缩容，防止复杂度震荡并将最小容量控制在5
            resize(data.length / 2);
        }
        return ret;
    }

    public E removeFirst(){//删除第一个元素
        return remove(0);
    }

    public E removeLast(){//删除最后一个元素
        return remove(size-1);
    }

    public boolean removeElement(E e){//删除元素e
        int index = find(e);
        if(index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    public int removeAllElement(E e){//删除所有的e元素，返回删除的e的个数
        int count = 0;
        while (find(e) >= 0){
            removeElement(e);
            count ++;
        }
        return count;
    }

    private void resize(int newCapacity){
        E[] newData = (E[])new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    //交换这两个索引所对应的值
    public void swap(int i, int j){
        if (i < 0 || i >= size || j < 0 || j >= size ) throw new IllegalArgumentException("over index");
        E e = data[i];
        data[i] = data[j];
        data[j] = e;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d, capacity = %d \n",size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if(i != size-1){
                res.append(", ");
            }
        }
        res.append(']');
        return res.toString();
    }

}
