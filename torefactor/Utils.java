/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author stef
 */
class Utils {

    public static final int SORT_DEC = 1;
    public static final int SORT_INC = 2;

    public static void main(String[] args) {

        int[] a = {2, 3, 1};

        for (int i : a) {
            System.out.print(i + ",");
        }
        System.out.println("\b");
        sort(a, SORT_DEC);
        for (int i : a) {
            System.out.print(i + ",");
        }
        System.out.println("\b");
        sort(a, SORT_INC);
        for (int i : a) {
            System.out.print(i + ",");
        }
        System.out.println("\b");
    }
    //selection sort

    public static void sort(int[] a, int mode) {
        for (int i = 0; i < a.length; i++) {
            if (mode == SORT_DEC) {
                swap(a, getMaxIndex(a, i, a.length-1), i);
            }
            if (mode == SORT_INC) {
                swap(a, getMinIndex(a, i, a.length-1), i);
            }
        }
    }

    public static int getMaxIndex(int[] a, int start, int end) {
        int idx = start;
        for (int i = start+1; i <= end; i++) {
            if (a[idx] < a[i]) {
                idx = i;
            }
        }
        return idx;
    }

    public static int getMinIndex(int[] a, int start, int end) {
        int idx = start;
         for (int i = start+1; i <= end; i++) {
            if (a[idx] > a[i]) {
                idx = i;
            }
        }
        return idx;
    }

    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
