
import java.io.Serializable;
import java.util.*;
import edu.princeton.cs.algs4.MinPQ;
import org.w3c.dom.Node;


public class BinaryTrie implements Serializable {
    private Node root;
    private Map<Character, BitSequence> map = new HashMap<>();
    private final static int LEFT = 0;
    private final static int RIGHT = 1;

    private static class Node implements Comparable<Node> , Serializable{
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
        Node cur = root;
        StringBuilder path = new StringBuilder();
        int index = 0;
        while (!cur.isLeaf()) {
            int x = querySequence.bitAt(index++);
            if (x == LEFT) {
                cur = cur.left;
                path.append(LEFT);
            } else {
                cur = cur.right;
                path.append(RIGHT);
            }
        }
        Match res = new Match(new BitSequence(path.toString()), cur.ch);
        return res;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        dfs(root, new StringBuilder());
        return map;
    }

    private void dfs(Node node, StringBuilder path) {
        if (node.isLeaf()) {
            char ch = node.ch;
            if (map.containsKey(ch)) return;
            map.put(ch, new BitSequence(path.toString()));
        }
        if (node.left != null){
            path.append(LEFT);
            dfs(node.left, path);
            path.deleteCharAt(path.length() - 1);
        }
        if (node.right != null) {
            path.append(RIGHT);
            dfs(node.right, path);
            path.deleteCharAt(path.length() - 1);
        }
    }

}
