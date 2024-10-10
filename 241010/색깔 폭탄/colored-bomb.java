// public class Main {
//     static int n, m;
//     static int[][] map;
//     public static void main(String[] args) {
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         StringTokenizer st = new StringTokenizer(br.readLine());
//         n = Integer.parseInt(br.readLine());
//         m = Integer.parseInt(br.readLine());
//         map = new int[n][n];
//         for(int i=0; i<n; i++){
//             st = new StringTokenizer(br.readLine());
//             for(int j =0; j<n; j++){
//                 map[i][j] = Integer.parseInt(br.readLine());
//             }
//         }

//     }
//     public static void findLargestBombGroup(){
//         for(int )
//     }
//     public static void eraseBomb()
//     public static void gravity(){

//     }
    
//     public static void rotate(int[][] matrix, int r1, int c1, int r2, int c2) {
//         int n = r2 - r1 + 1;
//         for (int i = 0; i < n / 2; i++) {
//             for (int j = 0; j < (n + 1) / 2; j++) {
//                 int temp = matrix[r1 + i][c1 + j];
//                 matrix[r1 + i][c1 + j] = matrix[r1 + j][c2 - i];
//                 matrix[r1 + j][c2 - i] = matrix[r2 - i][c2 - j];
//                 matrix[r2 - i][c2 - j] = matrix[r2 - j][c1 + i];
//                 matrix[r2 - j][c1 + i] = temp;
//             }
//         }
//     }

    
    
// }

import java.util.*;

class Main {
    static int n, m;
    static int[][] grid;
    static boolean[][] visited;
    static int totalScore = 0;
    static int[] dx = {-1, 1, 0, 0}; // 상하좌우
    static int[] dy = {0, 0, -1, 1}; // 상하좌우

    static class Group implements Comparable<Group> {
        int size, redCount, row, col;
        List<int[]> cells;

        Group(int size, int redCount, int row, int col, List<int[]> cells) {
            this.size = size;
            this.redCount = redCount;
            this.row = row;
            this.col = col;
            this.cells = cells;
        }

        @Override
        public int compareTo(Group other) {
            if (this.size != other.size) return Integer.compare(other.size, this.size); // 큰 폭탄 묶음 우선
            if (this.redCount != other.redCount) return Integer.compare(this.redCount, other.redCount); // 빨간색 적은 묶음 우선
            if (this.row != other.row) return Integer.compare(other.row, this.row); // 행이 큰 묶음 우선
            return Integer.compare(this.col, other.col); // 열이 작은 묶음 우선
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        while (true) {
            Group bestGroup = findBestGroup();
            if (bestGroup == null) break;

            // 점수 계산
            totalScore += bestGroup.size * bestGroup.size;

            // 폭탄 묶음 제거
            for (int[] cell : bestGroup.cells) {
                grid[cell[0]][cell[1]] = -2; // 제거된 칸 표시
            }

            // 중력 작용
            applyGravity();

            // 90도 반시계 회전
            rotate90();

            // 다시 중력 작용
            applyGravity();
        }

        System.out.println(totalScore);
    }

    // BFS로 폭탄 묶음을 찾음
    static Group findBestGroup() {
        visited = new boolean[n][n];
        Group bestGroup = null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0 && !visited[i][j]) { // 빨간색이 아닌 폭탄을 기준으로
                    Group group = bfs(i, j);
                    if (group != null && (bestGroup == null || group.compareTo(bestGroup) < 0)) {
                        bestGroup = group;
                    }
                }
            }
        }

        return bestGroup;
    }

    static Group bfs(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        List<int[]> cells = new ArrayList<>();
        boolean[][] tempVisited = new boolean[n][n];
        int redCount = 0;
        int baseColor = grid[x][y];
        int row = x, col = y;

        queue.add(new int[]{x, y});
        cells.add(new int[]{x, y});
        visited[x][y] = true;
        tempVisited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (nx < 0 || ny < 0 || nx >= n || ny >= n || tempVisited[nx][ny]) continue;
                if (grid[nx][ny] == baseColor || grid[nx][ny] == 0) {
                    if (grid[nx][ny] == 0) redCount++;
                    queue.add(new int[]{nx, ny});
                    cells.add(new int[]{nx, ny});
                    tempVisited[nx][ny] = true;
                    visited[nx][ny] = true;
                }
            }
        }

        if (cells.size() < 2) return null; // 폭탄 묶음이 2개 미만이면 무시
        return new Group(cells.size(), redCount, row, col, cells);
    }

    // 중력 작용 함수
    static void applyGravity() {
        for (int j = 0; j < n; j++) {
            int emptyRow = n - 1;
            for (int i = n - 1; i >= 0; i--) {
                if (grid[i][j] == -2) continue; // 제거된 칸은 무시
                if (grid[i][j] == -1) { // 돌은 떨어지지 않음
                    emptyRow = i - 1;
                } else {
                    if (i != emptyRow) {
                        grid[emptyRow][j] = grid[i][j];
                        grid[i][j] = -2;
                    }
                    emptyRow--;
                }
            }
        }
    }

    // 반시계 방향 90도 회전 함수
    static void rotate90() {
        int[][] newGrid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newGrid[n - 1 - j][i] = grid[i][j];
            }
        }
        grid = newGrid;
    }
}