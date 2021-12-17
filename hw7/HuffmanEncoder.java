import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : inputSymbols) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        return map;
    }

    /**1: Read the file as 8 bit symbols.
       2: Build frequency table.
       3: Use frequency table to construct a binary decoding trie.
       4: Write the binary decoding trie to the .huf file.
       5: (optional: write the number of symbols to the .huf file)
       6: Use binary trie to create lookup table for encoding.
       7: Create a list of bitsequences.
       8: For each 8 bit symbol:
          Lookup that symbol in the lookup table.
          Add the appropriate bit sequence to the list of bitsequences.
       9: Assemble all bit sequences into one huge bit sequence.
       10: Write the huge bit sequence to the .huf file.

       1: char[] FileUtils.readFile(String filename)
       4/5/10: ObjectWriter's writeObject method.
       9: BitSequence BitSequence.assemble(List<BitSequence>)
     * **/
    public static void main(String[] args) {
//        char[] readFile = FileUtils.readFile("watermelonsugar.txt");
        char[] readFile = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(readFile);
        BinaryTrie binaryTrie = new BinaryTrie(frequencyTable);

        ObjectWriter ow = new ObjectWriter( args[0] + ".huf");
        ow.writeObject(binaryTrie);

        Map<Character, BitSequence> lookupTable = binaryTrie.buildLookupTable();
        List<BitSequence> list = new ArrayList<>();
        for (char ch : readFile) {
            if (lookupTable.containsKey(ch)) {
                list.add(lookupTable.get(ch));
            }
        }
        BitSequence bitSequence = BitSequence.assemble(list);
        ow.writeObject(bitSequence);
    }
}
