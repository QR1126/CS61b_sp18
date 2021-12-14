import java.util.Arrays;
import java.util.Comparator;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    public static void main(String[] args) {
        String[] asciis = new String[] { "56", "112", "94", "4", "9", "82", "394", "80" };
        String[] res = RadixSort.sort(asciis);
        for (String s : res) {
            System.out.print(s + " ");
        }

        System.out.println();

        String[] asciis2 = new String[] {"  ", "      ", "    ", " "};
        String[] res2 = RadixSort.sort(asciis2);
        for (String s : res2) {
            System.out.print(s + ",");
        }
    }

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] str = Arrays.copyOf(asciis, asciis.length);
        int numDigits = 0;
        for (String s : str) {
            numDigits = Math.max(numDigits, s.length());
        }
        for (int i = numDigits - 1; i >= 0; i--) {
            sortHelperLSD(str, i);
        }
        return str;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int R = 256;
        int pos =0;
        int[] counts = new int[R + 1];
        int[] starts = new int[R + 1];
        String[] sort = new String[asciis.length];
        for (String s : asciis) {
            int i = charAt(s, index);
            counts[i]++;
        }
        for (int i = 0; i < R + 1; i++) {
            starts[i] = pos;
            pos+=counts[i];
        }
        for (int i = 0; i < asciis.length; i++) {
            String item = asciis[i];
            int c = charAt(item,index);
            int place = starts[c];
            sort[place] = item;
            starts[c]++;
        }
        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sort[i];
        }
    }

    private static int charAt(String str, int index) {
        if (index >= 0 && index < str.length()) {
            return str.charAt(index) + 1;
        } else {
            return 0;
        }
    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
