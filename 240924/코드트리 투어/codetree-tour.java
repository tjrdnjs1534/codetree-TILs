import java.util.*;

public class Main {
    static final int INF = Integer.MAX_VALUE;
    static int n, m;
    static List<List<Edge>> adj = new ArrayList<>();
    static int[] cost;
    static Map<Integer, Integer> itemDict = new HashMap<>();

    static class Edge {
        int dest, weight;

        Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    static class Item implements Comparable<Item> {
        int itemId, revenue, dest, profit;

        Item(int itemId, int revenue, int dest) {
            this.itemId = itemId;
            this.revenue = revenue;
            this.dest = dest;
            updateProfit();
        }

        @Override
        public int compareTo(Item other) {
            if (this.profit == other.profit) {
                return Integer.compare(this.itemId, other.itemId);
            }
            return Integer.compare(other.profit, this.profit); // Descending by profit
        }

        void updateProfit() {
            if (cost[this.dest] == INF) {
                this.profit = -1;
            } else {
                this.profit = this.revenue - cost[this.dest];
            }
        }
    }

    public static void dijkstra(int start) {
        cost = new int[n];
        Arrays.fill(cost, INF);
        cost[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.offer(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int cur = current.dest;
            int curCost = current.weight;

            if (curCost > cost[cur]) {
                continue;
            }

            for (Edge edge : adj.get(cur)) {
                int next = edge.dest;
                int nextCost = curCost + edge.weight;
                if (nextCost < cost[next]) {
                    cost[next] = nextCost;
                    pq.offer(new Edge(next, nextCost));
                }
            }
        }
    }

    public static int sell(PriorityQueue<Item> pq) {
        while (!pq.isEmpty()) {
            Item item = pq.peek();
            if (item.profit < 0) {
                break;
            }
            pq.poll();
            if (itemDict.get(item.itemId) == 1) {
                return item.itemId;
            }
        }
        return -1;
    }

    public static void updateItem(PriorityQueue<Item> pq) {
        List<Item> tempList = new ArrayList<>(pq);
        for (Item item : tempList) {
            item.updateProfit();
        }
        pq.clear();
        pq.addAll(tempList);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Q = sc.nextInt();
        int dumy = sc.nextInt();
        n = sc.nextInt();
        m = sc.nextInt();

        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int v = sc.nextInt();
            int u = sc.nextInt();
            int w = sc.nextInt();
            adj.get(v).add(new Edge(u, w));
            adj.get(u).add(new Edge(v, w));
        }

        dijkstra(0);

        PriorityQueue<Item> pqItem = new PriorityQueue<>();
        itemDict = new HashMap<>();

        for (int i = 1; i < Q; i++) {
            int cmd = sc.nextInt();
            // System.out.println(cmd);
            if (cmd == 200) {
                int itemId = sc.nextInt();
                int revenue = sc.nextInt();
                int dest = sc.nextInt();
                Item item = new Item(itemId, revenue, dest);
                pqItem.offer(item);
                itemDict.put(itemId, 1);
            } else if (cmd == 300) {
                int itemId = sc.nextInt();
                itemDict.put(itemId, 0); // Mark as inactive
            } else if (cmd == 400) {
                int result = sell(pqItem);
                System.out.println(result);
            } else if (cmd == 500) {
                int s = sc.nextInt();
                dijkstra(s);
                updateItem(pqItem);
            }
        }
        sc.close();
    }
}