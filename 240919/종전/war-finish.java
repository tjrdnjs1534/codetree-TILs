import java.util.Scanner;

public class Main {
    static int n;
    static int[][] maps;
    static int INF = (int) 1e9;
    static int answer = INF;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        maps = new int[n + 1][n + 1];
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                maps[i][j] = sc.nextInt();
            }
        }

        int total = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                total += maps[i][j];
            }
        }

        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                for (int d1 = 1; d1 <= n; d1++) {
                    for (int d2 = 1; d2 <= n; d2++) {
                        if (x + d1 + d2 > n) continue;
                        if (y - d1 < 1) continue;
                        if (y + d2 > n) continue;

                        answer = Math.min(answer, calculateRec(x, y, d1, d2, total));
                    }
                }
            }
        }

        System.out.println(answer);
    }

    static int calculateRec(int x, int y, int d1, int d2, int total) {
        int[][] temp = new int[n + 1][n + 1];

        temp[x][y] = 5;
        for (int i = 1; i <= d1; i++) {
            temp[x + i][y - i] = 5;
        }
        for (int i = 1; i <= d2; i++) {
            temp[x + i][y + i] = 5;
        }
        for (int i = 1; i <= d2; i++) {
            temp[x + d1 + i][y - d1 + i] = 5;
        }
        for (int i = 1; i <= d1; i++) {
            temp[x + d2 + i][y + d2 - i] = 5;
        }

        int[] R = new int[5];

        // 1구역
        for (int r = 1; r < x + d1; r++) {
            for (int c = 1; c <= y; c++) {
                if (temp[r][c] == 5) break;
                R[1] += maps[r][c];
            }
        }

        // 2구역
        for (int r = 1; r <= x + d2; r++) {
            for (int c = n; c > y; c--) {
                if (temp[r][c] == 5) break;
                R[2] += maps[r][c];
            }
        }

        // 3구역
        for (int r = x + d1; r <= n; r++) {
            for (int c = 1; c < y - d1 + d2; c++) {
                if (temp[r][c] == 5) break;
                R[3] += maps[r][c];
            }
        }

        // 4구역
        for (int r = x + d2 + 1; r <= n; r++) {
            for (int c = n; c >= y - d1 + d2; c--) {
                if (temp[r][c] == 5) break;
                R[4] += maps[r][c];
            }
        }

        R[0] = total - (R[1] + R[2] + R[3] + R[4]);

        int max = R[0], min = R[0];
        for (int i = 1; i < 5; i++) {
            max = Math.max(max, R[i]);
            min = Math.min(min, R[i]);
        }

        return max - min;
    }
}