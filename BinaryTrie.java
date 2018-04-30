import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class BinaryTrie implements Serializable {

    private Node first;
    private Map<Character, BitSequence> lookups;

    private static class Node implements Comparable<Node>, Serializable {
        Character thiscar;
        Integer thisint;
        Node left;
        Node right;

        Node(Character c, Integer i, Node tleft, Node tright) {
            thiscar = c;
            thisint = i;
            left = tleft;
            right = tright;
        }

        Node(Node n) {
            thisint = n.thisint;
            thiscar = n.thiscar;
            left = n.left;
            right = n.right;
        }


        Boolean isLeaf() {
            return (left == null) && (right == null);
        }


        public int compareTo(Node a) {
            return thisint - a.thisint;
        }

    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> nodes = new MinPQ<Node>();
        lookups = new HashMap<>();

        for (Character c : frequencyTable.keySet()) {
            nodes.insert(new Node(c, frequencyTable.get(c), null, null));
        }

        while (nodes.size() > 1) {
            Node left  = nodes.delMin();
            Node right = nodes.delMin();
            Node parent = new Node('\0', left.thisint + right.thisint, left, right);
            nodes.insert(parent);
        }

        first = nodes.delMin();

    }

    public Match longestPrefixMatch(BitSequence querySequence) {

        char[] bits =  querySequence.toString().toCharArray();

        int aindex = 0;

        Node current = new Node(first);

        ArrayList<Character> sequence = new ArrayList<Character>();

        while (!current.isLeaf()) {
            if (bits[aindex] == '0') {
                current = current.left;
            }

            if (bits[aindex] == '1') {
                current = current.right;
            }

            sequence.add(bits[aindex]);

            aindex++;

        }


        return new Match(querySequence.firstNBits(aindex), current.thiscar);


    }

    public Map<Character, BitSequence> buildLookupTable() {
        buildCode(first, new BitSequence());

        return lookups;


    }


    private void buildCode(Node x, BitSequence s) {
        if (!x.isLeaf()) {
            buildCode(x.left, s.appended(0));
            buildCode(x.right, s.appended(1));
        } else {
            lookups.put(x.thiscar, s);
        }
    }
}
