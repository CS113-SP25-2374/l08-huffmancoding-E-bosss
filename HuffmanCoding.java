import java.util.*;

public class HuffmanCoding  implements HuffmanInterface{

    private class HuffmanNode implements Comparable<HuffmanNode>{
        int count;
        char value;
        HuffmanNode left;
        HuffmanNode right;

        public HuffmanNode(int count, char value) {
            this.count = count;
            this.value = value;
            left = right = null;
        }

        public HuffmanNode(HuffmanNode left, HuffmanNode right) {
            this.left = left;
            this.right = right;
            this.count = left.count + right.count;
            this.value = 0;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(HuffmanNode o) {
            return this.count - o.count;
        }
    }

    class HuffmanCode {
        String code;
        char value;
        HuffmanCode(char value, String code) {
            this.code = code;
            this.value = value;
        }
    }

    PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
    HuffmanNode root;
    List<HuffmanCode> codes = new ArrayList<>();

    @Override
    public String decode(String codedMessage) {
        if (codedMessage == null || codedMessage.length() == 0 || root == null) {
            return "";
        }

        String decodedString = "";
        HuffmanNode node = root;
        for (char c : codedMessage.toCharArray()) {
            if (c == '0') {
                node = node.left;
            }
            if (c == '1') {
                node = node.right;
            }
            if (node.isLeaf()) {
                decodedString += node.value;
                node = root;
            }
        }

        return decodedString;
    }

    @Override
    public String encode(String message) {
        int[] counts = new int[256];

        for (char c : message.toCharArray()) {
            counts[c]++;
        }

        for (char c = 0; c < counts.length; c++) {
            if (counts[c] > 0) {
                HuffmanNode huffmanNode = new HuffmanNode(counts[c], c);
                priorityQueue.add(huffmanNode);
            }
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode composite = new HuffmanNode(left, right);
            priorityQueue.add(composite);
        }

        root = priorityQueue.poll();

        generateCodes(root, "");

        String encodedMessage = "";
        for (char c : message.toCharArray()) {
            encodedMessage += findCodes(c);
        }

        return encodedMessage;
    }

    String findCodes(char c) {
        for (HuffmanCode code : codes) {
            if (code.value == c) {
                return code.code;
            }
        }
        return "";
    }

    void generateCodes(HuffmanNode node, String code) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            codes.add(new HuffmanCode(node.value, code));
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }
}

// Ethan Senger
// v c codes

// e 3 00
// t 1 1110
// h 1 1001
// a 1 1000
// n 2 1111
// _ 1 0111
// s 1 0110
// g 1 0101
// r 1 0100
//
//                12
//         7              5
//     e     4          2  3
//         2    2     2    3
//       r  g  s  _  a  h  t  n