package at.grish.div;

import java.io.PrintStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class Util {

    public static final int SORT_DEC = 1;
    public static final int SORT_INC = 2;

    //selection sort
    public static void sort(int[] a, int mode) {
        for (int i = 0; i < a.length; i++) {
            if (mode == SORT_DEC) {
                swap(a, getMaxIndex(a, i, a.length - 1), i);
            }
            if (mode == SORT_INC) {
                swap(a, getMinIndex(a, i, a.length - 1), i);
            }
        }
    }

    public static int getMaxIndex(int[] a, int start, int end) {
        int idx = start;
        for (int i = start + 1; i <= end; i++) {
            if (a[idx] < a[i]) {
                idx = i;
            }
        }
        return idx;
    }

    public static int getMinIndex(int[] a, int start, int end) {
        int idx = start;
        for (int i = start + 1; i <= end; i++) {
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

    public static void printFormatted(String str, Object... args) {
        printFormatted(str, "%o", args);
    }

    public static void printFormatted(String str, String regex, Object... args) {
        printFormatted(System.out, str, regex, args);
    }

    public static void printFormatted(PrintStream out, String str,
            String regex, Object... args) {
        String[] strA = str.split(regex);
        int ctr = 0;
        for (int i = 0; i < strA.length; i++) {
            out.print(strA[i]);
            out.print(args[i]);
            ctr++;
        }
        if (ctr < args.length) {
            out.print(args[ctr++]);
        }
        out.println();
    }

    public static void printFromatted(PrintStream stream, String str,
            Object... args) {
        printFormatted(stream, str, "%o", args);
    }

    public static void saveObject(Object o, String savePath) throws IOException {
        File outFile = new java.io.File(savePath);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
                outFile));

        oos.writeObject(o);

        oos.flush();
        oos.close();
    }

    public static Object loadObject(String savePath) throws IOException,
            ClassNotFoundException {
        File outFile = new File(savePath);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                outFile));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Calculate a 60-bit hash value from a string.
     *
     * @param s
     * @return
     */
    public static long hash(Object o, int keylen) {
        String s = o.toString();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            // I am also sure that the algorithm exists
        }

        byte[] addressHash = md.digest(s.getBytes());

        return hash(addressHash, keylen);
    }

    /**
     * Calculate a 60-bit hash value from a byte array.
     *
     * @param ba Byte array containing at least 8 fields.
     * @return The hash.
     */
    private static long hash(byte[] ba, int keylen) {
        long hash = 0;

        for (int i = 0; i < 8; i++) {
            hash += (ba[i] & 0xffL) << (8 * i);
        }

        // long consists of 64 bits
        hash = hash >>> (64 - keylen);

        return hash;
    }

    public static boolean contains(long[] arr, long l) {
        if (binarySearch(arr, l) > -1) {
            return true;
        }
        return false;
    }

    public static long binarySearch(long[] a, long key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;

    }
}
