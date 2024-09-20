import java.util.*;
import java.io.*;

public class Main {

    static int N, M, H, K, score;
    static int[] dy = {-1, 0, 1, 0}; // 상 우 하 좌
    static int[] dx = {0, 1, 0, -1};
    static Boss boss;
    static int[][] treeMap;
    static ArrayList<Integer>[][] runnerMap;
    static int cnt, line;
    static boolean inToOut;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        visited = new boolean[N][N];
        boss = new Boss(N / 2, N / 2, 0);
        treeMap = new int[N][N];
        runnerMap = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                runnerMap[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());
            runnerMap[y][x].add(dir);
        }

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            treeMap[y][x] = 1;
        }

        inToOut = true;
        cnt = 1;
        line = 1;
        for (int i = 1; i <= K; i++) {
            step1_moveRunner();
            step2_moveBoss();
            step3_catchRunner(i);
        }
        System.out.println(score);
    }

    static void step1_moveRunner() {
        ArrayList<Integer>[][] tmp = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[i][j] = new ArrayList<>();
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (runnerMap[i][j].size() > 0 && checkDistance(new Pair(i, j))) {
                    for (Integer curDir : runnerMap[i][j]) {
                        int nextY = i + dy[curDir];
                        int nextX = j + dx[curDir];
                        if (nextY < 0 || nextX < 0 || nextY >= N || nextX >= N) {
                            curDir = (curDir + 2) % 4;
                            nextY = i + dy[curDir];
                            nextX = j + dx[curDir];
                        }
                        if (nextY == boss.y && nextX == boss.x) {
                            tmp[i][j].add(curDir);
                        } else {
                            tmp[nextY][nextX].add(curDir);
                        }
                    }
                } else {
                    for (Integer curDir : runnerMap[i][j]) {
                        tmp[i][j].add(curDir);
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                runnerMap[i][j].clear();
                for (Integer dir : tmp[i][j]) {
                    runnerMap[i][j].add(dir);
                }
            }
        }
    }

    static boolean checkDistance(Pair pair) {
        int dy = Math.abs(pair.y - boss.y);
        int dx = Math.abs(pair.x - boss.x);
        if (dy + dx > 3) {
            return false;
        } else {
            return true;
        }
    }

    static void step2_moveBoss() {
        if (inToOut) {
            step2_1_inToOut();
        } else {
            step2_2_outToIn();
        }
    }

    static void step2_1_inToOut() {
        int nextY = boss.y + dy[boss.dir];
        int nextX = boss.x + dx[boss.dir];

        boss.y = nextY;
        boss.x = nextX;
        cnt--;

        if (boss.y == 0 && boss.x == 0) {
            visited = new boolean[N][N];
            visited[0][0] = true;
            inToOut = false;
            boss.dir = (boss.dir + 2) % 4;
            return;
        }

        if (cnt == 0) {
            line++;
            boss.dir = (boss.dir + 1) % 4;
            cnt = (line / 2) + (line % 2);
        }
    }

    static void step2_2_outToIn() {
        int nextY = boss.y + dy[boss.dir];
        int nextX = boss.x + dx[boss.dir];

        boss.y = nextY;
        boss.x = nextX;
        visited[boss.y][boss.x] = true;

        if (boss.y == N / 2 && boss.x == N / 2) {
            inToOut = true;
            cnt = 1;
            line = 1;
            boss.dir = (boss.dir + 2) % 4;
            return;
        }

        nextY += dy[boss.dir];
        nextX += dx[boss.dir];

        if (nextY < 0 || nextX < 0 || nextY >= N || nextX >= N || visited[nextY][nextX]) {
            boss.dir = (boss.dir + 3) % 4;
        }
    }

    static void step3_catchRunner(int t) {
        for (int i = 0; i < 3; i++) {
            int targetY = boss.y + i * dy[boss.dir];
            int targetX = boss.x + i * dx[boss.dir];

            if (targetY < 0 || targetX < 0 || targetY >= N || targetX >= N) return;

            if (treeMap[targetY][targetX] == 0 && runnerMap[targetY][targetX].size() > 0) {
                score += t * runnerMap[targetY][targetX].size();
                runnerMap[targetY][targetX].clear();
            }
        }
    }

    static class Boss {
        int y;
        int x;
        int dir;

        public Boss(int y, int x, int dir) {
            this.y = y;
            this.x = x;
            this.dir = dir;
        }
    }

    static class Pair {
        int y;
        int x;

        public Pair(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}