import java.util.Scanner;

public class Main {

    static final int MAX = 50 + 5;
    static int T;
    static int N, M, Q;

    static int[][] circle = new int[MAX][MAX];
    static int[] X = new int[MAX];
    static int[] D = new int[MAX];
    static int[] K = new int[MAX];

    static class Queue {
        int r, c;

        Queue(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static Queue[] queue = new Queue[MAX * MAX];
    static int wp, rp;

    /* 순서대로 왼쪽, 위, 오른쪽, 아래 */
    static int[] dr = {0, -1, 0, 1};
    static int[] dc = {-1, 0, 1, 0};

    public static void input(Scanner sc) {
        N = sc.nextInt();
        M = sc.nextInt();
        Q = sc.nextInt();

        for (int r = 0; r < N; r++)
            for (int c = 0; c < M; c++)
                circle[r][c] = sc.nextInt();

        for (int i = 0; i < Q; i++) {
            X[i] = sc.nextInt();
            D[i] = sc.nextInt();
            K[i] = sc.nextInt();
        }
    }

    public static void output() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++)
                System.out.print(circle[r][c] + " ");
            System.out.println();
        }
    }

    public static void rotate(int x, int d, int k) {
        int[] tmp = new int[MAX];

        if (d == 1) k = M - k; /* 반시계 방향 k칸은 시계 방향으로 M - k 칸 */

        for (int r = x - 1; r < N; r += x) {
            for (int c = 0; c < M - k; c++) tmp[c + k] = circle[r][c];
            for (int c = M - k; c < M; c++) tmp[c - (M - k)] = circle[r][c];
            for (int c = 0; c < M; c++) circle[r][c] = tmp[c];
        }
    }

    public static int BFS(int r, int c, int[][] visit) {
        int flag = 0;

        wp = rp = 0;

        queue[wp++] = new Queue(r, c);
        visit[r][c] = 1;

        while (wp > rp) {
            Queue out = queue[rp++];

            if (out.c == 0) {
                if (circle[out.r][M - 1] == circle[r][c] && visit[out.r][M - 1] == 0) {
                    queue[wp++] = new Queue(out.r, M - 1);
                    visit[out.r][M - 1] = 1;
                    flag = 1;
                }
            } else if (out.c == M - 1) {
                if (circle[out.r][0] == circle[r][c] && visit[out.r][0] == 0) {
                    queue[wp++] = new Queue(out.r, 0);
                    visit[out.r][0] = 1;
                    flag = 1;
                }
            }

            for (int k = 0; k < 4; k++) {
                int nr = out.r + dr[k];
                int nc = out.c + dc[k];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;

                if (circle[nr][nc] == circle[r][c] && visit[nr][nc] == 0) {
                    queue[wp++] = new Queue(nr, nc);
                    visit[nr][nc] = 1;
                    flag = 1;
                }
            }
        }

        return flag;
    }

    public static void deleteCircle(int[][] visit) {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (visit[r][c] == 1) circle[r][c] = 0;
            }
        }
    }

    public static void averageCircle() {
        int sum = 0, cnt = 0;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (circle[r][c] != 0) {
                    sum += circle[r][c];
                    cnt++;
                }
            }
        }

        if (cnt == 0) return;

        int avg = sum / cnt;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (circle[r][c] == 0) continue;

                if (circle[r][c] < avg) circle[r][c]++;
                else if (circle[r][c] > avg) circle[r][c]--;
            }
        }
    }

    public static void allBFS() {
        int[][] visit = new int[MAX][MAX];
        boolean deleteflag = false;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (circle[r][c] != 0 && visit[r][c] == 0) {
                    int tmp = BFS(r, c, visit);
                    if (tmp == 1) {
                        deleteCircle(visit);
                        deleteflag = true;
                    } else {
                        visit[r][c] = 0;
                    }
                }
            }
        }

        if (!deleteflag) averageCircle();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // T = sc.nextInt(); // 여러 테스트 케이스가 있을 경우 사용
        T = 1;
        for (int tc = 0; tc < T; tc++) {
            input(sc);

            for (int q = 0; q < Q; q++) {
                rotate(X[q], D[q], K[q]);
                allBFS();
            }

            int sum = 0;
            for (int i = 0; i < N; i++) {
                for (int c = 0; c < M; c++) {
                    sum += circle[i][c];
                }
            }

            System.out.println(sum);
        }

        sc.close();
    }
}