package util;


public class SortUtil {
    /**
     * 冒泡排序
     *
     * @param array
     * @return
     */
    public static int[] BubbleSort(int[] array) {
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            for (int y = 0; y < array.length - 1 - i; y++) {
                if (array[y] < array[y + 1]) {
                    temp = array[y];
                    array[y] = array[y + 1];
                    array[y + 1] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 快速排序
     *
     * @param array
     * @return
     */
    public static int[] Quicksort(int[] array, int x, int y) {
        int temp = x;
        int tempEnd = y;
        for (; x < y; y--) {
            if (array[y] < array[temp]) {
                for (; x < y; x++) {
                    if (array[x] > array[temp]) {
                        array[y] = array[x];
                        array[x] = array[temp];
                    }
                    if (x == y) {
                        array[temp] = array[temp] + array[y];
                        array[y] = array[temp] - array[y];
                        array[temp] = array[temp] - array[y];
                        //left
                        Quicksort(array, x, y);
                        //right
                        Quicksort(array, y, tempEnd);
                    }
                }
            }
        }

        return array;
    }


    /**
     * 选择排序
     *
     * @param array
     * @return
     */
    public static int[] SelectionSort(int[] array) {
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            for (int y = 0; y < array.length; y++) {
                if (array[i] < array[y]) {
                    temp = array[i];
                    array[i] = array[y];
                    array[y] = temp;
                }
            }
        }
        return array;
    }

    public static void print(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println("");
    }

    public static void main(String[] args) {
        int[] array = new int[10];
//		for (int i = 0 ; i < 5 ; i ++) {
        for (int y = 0; y < array.length; y++) {
            array[y] = (int) (Math.random() * 10);
        }
        //冒泡排序
        print(array);
        BubbleSort(array);
        //快速排序
        Quicksort(array, 0, array.length - 1);
        //选择排序
        print(SelectionSort(array));
//		}
    }


    /**
     * 别人的实现
     * 快速排序
     *
     * @param s
     * @param l
     * @param r
     */
    void quick_sort(int s[], int l, int r) {
        if (l < r) {
            //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
            int i = l, j = r, x = s[l];
            while (i < j) {
                while (i < j && s[j] >= x) // 从右向左找第一个小于x的数
                    j--;
                if (i < j)
                    s[i++] = s[j];

                while (i < j && s[i] < x) // 从左向右找第一个大于等于x的数
                    i++;
                if (i < j)
                    s[j--] = s[i];
            }
            s[i] = x;
            quick_sort(s, l, i - 1); // 递归调用
            quick_sort(s, i + 1, r);
        }
    }
}
