import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HuffmanEncoder {



    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {

        HashMap<Character, Integer> freqs = new HashMap<>();

        for (char c : inputSymbols) {
            if (freqs.containsKey(c)) {
                freqs.put(c, freqs.get(c) + 1);

            } else {
                freqs.put(c, 1);
            }

        }

        return freqs;
    }


    public static void main(String[] args) {

        char[] readin =  FileUtils.readFile(args[0]);

        Map<Character, Integer> freqtable = buildFrequencyTable(readin); // Steps 1-2

        BinaryTrie mytrie = new BinaryTrie(freqtable);  // Step 3

        ObjectWriter hufwriter = new ObjectWriter(args[0] + ".huf"); // Step 4
        hufwriter.writeObject(mytrie);

        hufwriter.writeObject(readin.length); // Step 5

        Map<Character, BitSequence> lookuptable = mytrie.buildLookupTable(); // Step 6

        ArrayList<BitSequence> bitseqs = new ArrayList<>(); // Step 7

        for (Character c : readin) {
            bitseqs.add(lookuptable.get(c));
        } // Step 8

        BitSequence hugesequence = BitSequence.assemble(bitseqs); // Step 9

        hufwriter.writeObject(hugesequence); // Step 10





    }



}

