import java.util.*;

public class Main {
    static int n, L, R;
    static int[][] eggs;
    static boolean[][] visited;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 입력 받기
        n = sc.nextInt();
        L = sc.nextInt();
        R = sc.nextInt();
        eggs = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                eggs[i][j] = sc.nextInt();
            }
        }

        int moves = 0;
        while (true) {
            visited = new boolean[n][n];
            boolean moved = false;
            
            // 모든 칸을 탐색하여 그룹화 및 계란 이동
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (!visited[i][j]) {
                        List<int[]> group = bfs(i, j);
                        if (group.size() > 1) {
                            moveEggs(group);
                            moved = true;
                        }
                    }
                }
            }
            
            // 더 이상 이동이 없으면 종료
            if (!moved) break;
            moves++;
        }

        // 결과 출력
        System.out.println(moves);
    }

    // BFS로 그룹을 탐색하는 함수
    static List<int[]> bfs(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> group = new ArrayList<>();
        queue.add(new int[] {x, y});
        group.add(new int[] {x, y});
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0];
            int cy = cur[1];

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx >= 0 && ny >= 0 && nx < n && ny < n && !visited[nx][ny]) {
                    int diff = Math.abs(eggs[cx][cy] - eggs[nx][ny]);
                    if (diff >= L && diff <= R) {
                        queue.add(new int[] {nx, ny});
                        group.add(new int[] {nx, ny});
                        visited[nx][ny] = true;
                    }
                }
            }
        }

        return group;
    }

    // 그룹 내 계란을 이동시키는 함수
    static void moveEggs(List<int[]> group) {
        int totalEggs = 0;
        for (int[] cell : group) {
            totalEggs += eggs[cell[0]][cell[1]];
        }
        int avgEggs = totalEggs / group.size();

        for (int[] cell : group) {
            eggs[cell[0]][cell[1]] = avgEggs;
        }
    }
}