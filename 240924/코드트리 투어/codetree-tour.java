import java.util.*;

public class Main {
    
    static int Q, start;
    static final int MAX = Integer.MAX_VALUE;
    static int[] dist = new int[2001];
    static boolean[] checkId = new boolean[30001];

    static class LandInfo {
        int u, w;

        LandInfo(int u, int w) {
            this.u = u;
            this.w = w;
        }
    }

    static class PackageInfo implements Comparable<PackageInfo> {
        int id, revenue, dest, cost;

        PackageInfo(int id, int revenue, int dest, int cost) {
            this.id = id;
            this.revenue = revenue;
            this.dest = dest;
            this.cost = cost;
        }

        @Override
        public int compareTo(PackageInfo other) {
            if (this.cost != other.cost) return Integer.compare(other.cost, this.cost);
            return Integer.compare(this.id, other.id);
        }
    }

    static List<LandInfo>[] lands = new ArrayList[2001];
    static PriorityQueue<PackageInfo> packages = new PriorityQueue<>();

    // 거리 초기화
    public static void init() {
        Arrays.fill(dist, MAX);
    }

    // 다익스트라 알고리즘
    public static void dijkstra() {
        init();
        dist[start] = 0;
        PriorityQueue<LandInfo> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.w, b.w));
        pq.add(new LandInfo(start, 0));

        while (!pq.isEmpty()) {
            LandInfo now = pq.poll();

            for (LandInfo next : lands[now.u]) {
                if (dist[next.u] > dist[now.u] + next.w) {
                    dist[next.u] = dist[now.u] + next.w;
                    pq.add(new LandInfo(next.u, dist[next.u]));
                }
            }
        }
    }

    // 양방향 간선 연결
    public static void func1(Scanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();

        for (int i = 0; i < 2001; i++) {
            lands[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int v = sc.nextInt();
            int u = sc.nextInt();
            int w = sc.nextInt();
            lands[v].add(new LandInfo(u, w));
            lands[u].add(new LandInfo(v, w));
        }
        dijkstra();
    }

    // 패키지 생성
    public static void func2(Scanner sc) {
        int id = sc.nextInt();
        int revenue = sc.nextInt();
        int dest = sc.nextInt();
        int cost = (dist[dest] == MAX) ? -1 : revenue - dist[dest];
        packages.add(new PackageInfo(id, revenue, dest, cost));
        checkId[id] = true;
    }

    // 패키지 취소
    public static void func3(Scanner sc) {
        int id = sc.nextInt();
        checkId[id] = false;
    }

    // 패키지 판매
    public static void func4() {
        if (packages.isEmpty()) {
            System.out.println(-1);
            return;
        }

        while (!packages.isEmpty()) {
            PackageInfo now = packages.peek();
            if (!checkId[now.id]) {
                packages.poll();
            } else {
                break;
            }
        }

        if (packages.isEmpty()) {
            System.out.println(-1);
        } else {
            PackageInfo now = packages.poll();
            if (now.cost < 0) {
                System.out.println(-1);
            } else {
                checkId[now.id] = false;
                System.out.println(now.id);
            }
        }
    }

    // 출발지 변경
    public static void func5(Scanner sc) {
        start = sc.nextInt();
        dijkstra();

        PriorityQueue<PackageInfo> temp = new PriorityQueue<>();
        while (!packages.isEmpty()) {
            PackageInfo now = packages.poll();
            if (!checkId[now.id]) continue;
            temp.add(now);
        }

        while (!temp.isEmpty()) {
            PackageInfo now = temp.poll();
            int dest = now.dest;
            int revenue = now.revenue;
            int cost = (dist[dest] == MAX) ? -1 : revenue - dist[dest];
            now.cost = cost;
            packages.add(now);
        }
    }

    // 명령 입력
    public static void input(Scanner sc) {
        for (int i = 0; i < Q; i++) {
            int num = sc.nextInt();
            switch (num) {
                case 100:
                    func1(sc);
                    break;
                case 200:
                    func2(sc);
                    break;
                case 300:
                    func3(sc);
                    break;
                case 400:
                    func4();
                    break;
                case 500:
                    func5(sc);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Q = sc.nextInt();
        input(sc);
    }
}