
public class HuffmanDecoder {


    public static void main(String[] args) {


        ObjectReader reader = new ObjectReader(args[0]);

        BinaryTrie mytrie = (BinaryTrie) reader.readObject();
        int numsymbols = (int) reader.readObject();
        BitSequence hugebit = (BitSequence) reader.readObject();

        char[] bits = new char[numsymbols];

        int i = 0;

        while (i < numsymbols) {
            Match currmath = mytrie.longestPrefixMatch(hugebit);

            bits[i] = currmath.getSymbol();

            hugebit = hugebit.allButFirstNBits(currmath.getSequence().length());

            i++;

        }

        FileUtils.writeCharArray(args[1], bits);


    }
}

