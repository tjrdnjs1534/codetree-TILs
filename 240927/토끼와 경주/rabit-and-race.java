import java.util.*;
import java.io.*;

public class Main {
    static Map<Integer, int[]> rabbit = new HashMap<>();
    static PriorityQueue<int[]> prior_global = new PriorityQueue<>(new Comparator<int[]>() {
        public int compare(int[] a, int[] b) {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
            if (a[2] != b[2]) return Integer.compare(a[2], b[2]);
            if (a[3] != b[3]) return Integer.compare(a[3], b[3]);
            if (a[4] != b[4]) return Integer.compare(a[4], b[4]);
            return 0;
        }
    });

    static int N = -1, M = -1;

    public static void start_200(int K, int S) {
        PriorityQueue<int[]> prior_local = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                // Since we're using negative values for max-heap behavior, we compare normally
                if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
                if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
                if (a[2] != b[2]) return Integer.compare(a[2], b[2]);
                if (a[3] != b[3]) return Integer.compare(a[3], b[3]);
                return 0;
            }
        });

        for (int k = 0; k < K; k++) {
            int[] cur = prior_global.poll();
            int tjcnt = cur[0];
            int x = cur[2];
            int y = cur[3];
            int pid = cur[4];
            int d = rabbit.get(pid)[0];
            List<int[]> tmp = new ArrayList<>();

            // Up
            int nx = (x + d) % (2 * (N - 1));
            if (nx >= N) nx = 2 * (N - 1) - nx;
            tmp.add(new int[]{-(nx + y), -nx, -y});

            // Down
            nx = (x - d) % (2 * (N - 1));
            if (nx < 0) nx += 2 * (N - 1);
            if (nx >= N) nx = 2 * (N - 1) - nx;
            tmp.add(new int[]{-(nx + y), -nx, -y});

            // Left
            int ny = (y + d) % (2 * (M - 1));
            if (ny >= M) ny = 2 * (M - 1) - ny;
            tmp.add(new int[]{-(x + ny), -x, -ny});

            // Right
            ny = (y - d) % (2 * (M - 1));
            if (ny < 0) ny += 2 * (M - 1);
            if (ny >= M) ny = 2 * (M - 1) - ny;
            tmp.add(new int[]{-(x + ny), -x, -ny});

            // Select the best move
            int[] selected = Collections.min(tmp, new Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
                    if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
                    if (a[2] != b[2]) return Integer.compare(a[2], b[2]);
                    return 0;
                }
            });

            int nx_selected = -selected[1];
            int ny_selected = -selected[2];

            // Update other rabbits' scores
            for (Map.Entry<Integer, int[]> entry : rabbit.entrySet()) {
                int key = entry.getKey();
                if (key != pid) {
                    int[] value = entry.getValue();
                    value[1] += nx_selected + ny_selected + 2;
                }
            }

            // Add back to global priority queue
            prior_global.add(new int[]{tjcnt + 1, nx_selected + ny_selected, nx_selected, ny_selected, pid});
            // Add to local priority queue for final selection
            prior_local.add(new int[]{-(nx_selected + ny_selected), -nx_selected, -ny_selected, -pid});
        }

        // Give additional score to the best rabbit
        int[] final_rabbit = prior_local.poll();
        int pid = -final_rabbit[3];
        int[] rabbitData = rabbit.get(pid);
        rabbitData[1] += S;
    }

    public static void chageDist_300(int pid, int L) {
        int[] data = rabbit.get(pid);
        if (data != null) {
            data[0] *= L;
        }
    }

    public static void best_400() {
        int max_s = -1;
        for (int[] value : rabbit.values()) {
            int s = value[1];
            if (s > max_s) {
                max_s = s;
            }
        }
        System.out.println(max_s);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Q = Integer.parseInt(br.readLine().trim());
        for (int q = 0; q < Q; q++) {
            String[] cmd = br.readLine().trim().split("\\s+");
            if (cmd[0].equals("100")) { // 경주 시작 준비
                N = Integer.parseInt(cmd[1]);
                M = Integer.parseInt(cmd[2]);
                int P = Integer.parseInt(cmd[3]);
                rabbit.clear();
                prior_global.clear();
                for (int i = 4; i < cmd.length; i += 2) {
                    int pid = Integer.parseInt(cmd[i]);
                    int d = Integer.parseInt(cmd[i + 1]);
                    rabbit.put(pid, new int[]{d, 0});
                    prior_global.add(new int[]{0, 0, 0, 0, pid});
                }
            } else if (cmd[0].equals("200")) { // 경주 진행
                int K = Integer.parseInt(cmd[1]);
                int S = Integer.parseInt(cmd[2]);
                start_200(K, S);
            } else if (cmd[0].equals("300")) { // 이동거리 변경
                int pid = Integer.parseInt(cmd[1]);
                int L = Integer.parseInt(cmd[2]);
                chageDist_300(pid, L);
            } else if (cmd[0].equals("400")) { // 최고의 토끼 선정
                best_400();
            }
        }
    }
}