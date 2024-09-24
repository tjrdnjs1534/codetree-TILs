import java.io.*;
import java.util.*;

public class Main {
    static Map<Integer, Node> nodes = new HashMap<>();
    static int Q;
    static int currentTime = 0;

    static class Node {
        int id;
        Node parent;
        List<Node> children = new ArrayList<>();
        int color;
        int maxDepth;
        int depth;
        int inTime, outTime;
        Set<Integer> colorsInSubtree = new HashSet<>();

        Node(int id, int color, int maxDepth, int depth) {
            this.id = id;
            this.color = color;
            this.maxDepth = maxDepth;
            this.depth = depth;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        List<String> commands = new ArrayList<>();
        for (int i = 0; i < Q; i++) {
            commands.add(br.readLine());
        }

        for (String commandLine : commands) {
            String[] parts = commandLine.split(" ");
            int cmd = Integer.parseInt(parts[0]);

            if (cmd == 100) { // Add Node
                int mid = Integer.parseInt(parts[1]);
                int pid = Integer.parseInt(parts[2]);
                int color = Integer.parseInt(parts[3]);
                int maxDepth = Integer.parseInt(parts[4]);
                addNode(mid, pid, color, maxDepth);
            } else if (cmd == 200) { // Color Change
                int mid = Integer.parseInt(parts[1]);
                int color = Integer.parseInt(parts[2]);
                colorChange(nodes.get(mid), color);
            } else if (cmd == 300) { // Color Query
                int mid = Integer.parseInt(parts[1]);
                sb.append(nodes.get(mid).color).append("\n");
            } else if (cmd == 400) { // Score Calculation
                // Reset colorsInSubtree for all nodes
                for (Node node : nodes.values()) {
                    node.colorsInSubtree.clear();
                }
                // Compute colorsInSubtree
                for (Node node : nodes.values()) {
                    if (node.parent == null) { // Root nodes
                        computeColorsInSubtree(node);
                    }
                }
                long totalScore = 0;
                for (Node node : nodes.values()) {
                    int value = node.colorsInSubtree.size();
                    totalScore += (long) value * value;
                }
                sb.append(totalScore).append("\n");
            }
        }
        System.out.print(sb.toString());
    }

    static void addNode(int mid, int pid, int color, int maxDepth) {
        if (nodes.containsKey(mid)) {
            // Node ID must be unique
            return;
        }
        if (pid == -1) { // New root node
            Node newNode = new Node(mid, color, maxDepth, 1);
            nodes.put(mid, newNode);
        } else {
            Node parent = nodes.get(pid);
            if (parent == null) {
                // Parent node must exist
                return;
            }
            int depth = parent.depth + 1;
            if (!checkMaxDepth(parent, depth)) {
                // Cannot add node due to maxDepth constraint
                return;
            }
            Node newNode = new Node(mid, color, maxDepth, depth);
            newNode.parent = parent;
            parent.children.add(newNode);
            nodes.put(mid, newNode);
        }
    }

    static boolean checkMaxDepth(Node node, int depth) {
        int depthFromAncestor = depth - node.depth + 1;
        if (depthFromAncestor > node.maxDepth) {
            return false;
        }
        if (node.parent != null) {
            return checkMaxDepth(node.parent, depth);
        }
        return true;
    }

    static void colorChange(Node node, int color) {
        node.color = color;
        for (Node child : node.children) {
            colorChange(child, color);
        }
    }

    static void computeColorsInSubtree(Node node) {
        node.colorsInSubtree.add(node.color);
        for (Node child : node.children) {
            computeColorsInSubtree(child);
            node.colorsInSubtree.addAll(child.colorsInSubtree);
        }
    }
}