import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int[][] p; // 상성 리스트
    static int n; // n 값
    static int ans = Integer.MAX_VALUE; // 최소값을 저장할 변수
    static ArrayList<Integer> tmp = new ArrayList<>(); // 팀 A를 저장할 리스트

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // n 값 입력
        n = sc.nextInt();

        // 상성 리스트 입력
        p = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                p[i][j] = sc.nextInt();
            }
        }

        // 0번 사람을 먼저 선택하고 DFS 시작
        tmp.add(0);
        dfs(0, 1);
        System.out.println(ans);
    }

    // 강도를 구하는 함수
    public static int intensity(ArrayList<Integer> c) {
        int res = 0;
        for (int i = 0; i < c.size(); i++) {
            for (int j = i + 1; j < c.size(); j++) {
                res += p[c.get(i)][c.get(j)];
                res += p[c.get(j)][c.get(i)];
            }
        }
        return res;
    }

    // DFS 함수로 두 팀의 최소 차이를 구함
    public static void dfs(int idx, int depth) {
        if (depth == n / 2) {
            // 팀 B를 tmp에 없는 사람들로 구성
            ArrayList<Integer> tmp2 = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!tmp.contains(i)) {
                    tmp2.add(i);
                }
            }

            // 두 팀의 강도 차이를 구하고 최소값 갱신
            ans = Math.min(ans, Math.abs(intensity(tmp) - intensity(tmp2)));
            return;
        }

        // DFS 재귀 호출로 모든 경우의 팀 구성을 탐색
        for (int i = idx + 1; i < n; i++) {
            tmp.add(i);
            dfs(i, depth + 1);
            tmp.remove(tmp.size() - 1); // 백트래킹
        }
    }
}