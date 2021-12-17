import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class HuffmanDecoder {

    /**1: Read the Huffman coding trie.
     2: If applicable, read the number of symbols.
     3: Read the massive bit sequence corresponding to the original txt.
     4: Repeat until there are no more symbols:
     4a: Perform a longest prefix match on the massive sequence.
     4b: Record the symbol in some data structure.
     4c: Create a new bit sequence containing the remaining unmatched bits.
     5: Write the symbols in some data structure to the specified file.

     1/2/3: ObjectReader's readObject method.
     4c: BitSequence has methods that may be useful to you.
     5: FileUtils.writeCharArray(String filename, char[] chars)
     **/
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0] + ".huf");
        Object a = or.readObject();
        Object b = or.readObject();
        BinaryTrie binaryTrie = (BinaryTrie) a;
        BitSequence bitSequence = (BitSequence) b;
        BitSequence seq = bitSequence;
        List<Character> list = new ArrayList<>();
        while (seq.length() > 0) {
            Match match = binaryTrie.longestPrefixMatch(bitSequence);
            Character symbol = match.getSymbol();
            BitSequence sequence = match.getSequence();
            list.add(symbol);
            int length = sequence.length();
            seq = seq.lastNBits(seq.length() - length);
        }
        int size = list.size();
        char[] chars = new char[size];
        for (int i = 0; i < size; i++) {
            chars[i] = list.get(i);
        }
        FileUtils.writeCharArray(args[1], chars);
    }
}
