package Algorithm.Sort;

public class SelectionSort {

    public static void swap(int[] arr, int a, int b){
        int swap = arr[a];
        arr[a] = arr[b];
        arr[b] = swap;
    }

    public static void sort(int[] a, int n){
        for (int i = 0; i < n; i++) {
            int minIndex = i;
            for (int j = i; j < n; j++) {
                if (a[j] < a[minIndex]){
                    minIndex = j;
                }
            }
            swap(a, minIndex, i);
        }
    }

    public static void main(String[] args) {
        int[] a = {5,3,4,2,1};

        sort(a, a.length);
        System.out.println(a[0]);

        //C++ template<typename T>
    }
}
