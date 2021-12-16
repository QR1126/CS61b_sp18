import java.io.Serializable;
import java.util.*;


import edu.princeton.cs.algs4.MinPQ;
import org.w3c.dom.Node;


public class BinaryTrie implements Serializable {
    private Node root;

    private static class Node implements Comparable<Node> {
        private char ch;
        private int freq;
        private Node left;
        private Node right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        if (frequencyTable.isEmpty()) {
            this.root = null;
        }

        MinPQ<Node> pq = new MinPQ<>();
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.insert(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        // special case in case there is only one character with a nonzero frequency
        if (pq.size() == 1) {
            pq.insert(new Node('\0', 0, null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        root = pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        int length = querySequence.length();
        int longestStep = 0;
        Match res = new Match(null, null);
        Queue<Integer> list = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            list.add(querySequence.bitAt(i));
        }

        while (!list.isEmpty()) {
            Node cur = root;
            int step = 0;
            StringBuilder sb = new StringBuilder();
            while (!cur.isLeaf()) {
                step++;
                Integer bit = list.poll();
                sb.append(bit);
                if (bit == 0) cur = cur.left;
                else cur = cur.right;
            }
            if (step > longestStep) {
                longestStep = step;
                res = new Match(new BitSequence(sb.toString()), cur.ch);
            }
        }
        return res;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        return null;
    }
}
