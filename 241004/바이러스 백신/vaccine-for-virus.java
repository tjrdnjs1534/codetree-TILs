import java.util.*;

public class Main {
    static int N, M;
    static int[][] maps;
    static List<List<int[]>> hospitalComb = new ArrayList<>();
    static int answer = Integer.MAX_VALUE;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        maps = new int[N][N];

        List<int[]> hospital = new ArrayList<>();
        
        // 입력받기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                maps[i][j] = sc.nextInt();
                if (maps[i][j] == 2) {
                    hospital.add(new int[]{i, j}); // 병원 위치 저장
                    maps[i][j] = -2; // 병원 표시
                }
                if (maps[i][j] == 1) {
                    maps[i][j] = -1; // 벽은 -1로 표시
                }
            }
        }

        // DFS로 병원 조합 구하기
        dfs(hospital, new ArrayList<>(), 0);

        // BFS로 최소 시간 계산
        for (List<int[]> comb : hospitalComb) {
            bfs(comb);
        }

        // 출력
        if (answer == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    // DFS로 병원 조합 구하는 함수
    static void dfs(List<int[]> hospitalList, List<int[]> pickList, int idx) {
        if (idx == hospitalList.size()) {
            if (pickList.size() == M) {
                hospitalComb.add(new ArrayList<>(pickList)); // 병원 조합 추가
            }
            return;
        }

        // 병원 선택
        pickList.add(hospitalList.get(idx));
        dfs(hospitalList, pickList, idx + 1);
        // 병원 선택하지 않음
        pickList.remove(pickList.size() - 1);
        dfs(hospitalList, pickList, idx + 1);
    }

    // BFS로 최소 시간을 구하는 함수
    static void bfs(List<int[]> hospitalList) {
        boolean[][] visited = new boolean[N][N];
        int[][] timeMaps = new int[N][N];
        Queue<int[]> q = new LinkedList<>();

        // 병원 위치를 큐에 추가하고 방문 표시
        for (int[] h : hospitalList) {
            q.add(new int[]{h[0], h[1], 0}); // x, y, 시간
            visited[h[0]][h[1]] = true;
        }

        // BFS 탐색
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int cnt = cur[2];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                // 유효한 위치인지 확인
                if (nx >= 0 && ny >= 0 && nx < N && ny < N && !visited[nx][ny]) {
                    if (maps[nx][ny] == 0 || maps[nx][ny] == -2) { // 빈칸 또는 병원
                        q.add(new int[]{nx, ny, cnt + 1});
                        visited[nx][ny] = true;
                        timeMaps[nx][ny] = cnt + 1;
                    }
                }
            }
        }

        // 최소 시간 계산
        int time = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (maps[i][j] == 0 && timeMaps[i][j] == 0) { // 빈칸인데 도달 못한 곳
                    return;
                }
                if (maps[i][j] == 0) {
                    time = Math.max(time, timeMaps[i][j]);
                }
            }
        }
        answer = Math.min(answer, time);
    }
}