import java.util.*;

public class Main {
    static int N, Q;
    static int[][] board;
    static int[][] newBoard;
    static boolean[][] visited;
    static int[] dx = {0, -1, 0, 1}; // 동, 북, 서, 남
    static int[] dy = {1, 0, -1, 0}; // 동, 북, 서, 남

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // N과 Q 입력받기
        N = sc.nextInt();
        Q = sc.nextInt();

        // 2^N 크기의 board 초기화
        int size = (int) Math.pow(2, N);
        board = new int[size][size];
        visited = new boolean[size][size];

        // board 입력받기
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = sc.nextInt();
            }
        }

        // 명령어 입력받기
        int[] commands = new int[Q];
        for (int i = 0; i < Q; i++) {
            commands[i] = sc.nextInt();
        }

        // 명령어 처리 (시뮬레이션)
        for (int i = 0; i < Q; i++) {
            simulate(commands[i]);
        }

        // 결과 출력
        // 1. 남아있는 얼음 총합
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum += board[i][j];
            }
        }
        System.out.println(sum);

        // 2. 가장 큰 얼음 덩어리 크기
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!visited[i][j] && board[i][j] > 0) {
                    res = Math.max(res, bfs(i, j));
                }
            }
        }
        System.out.println(res);
        sc.close();
    }

    // 시뮬레이션 수행
    static void simulate(int L) {
        if (L != 0) {
            int step = (int) Math.pow(2, L);
            for (int x = 0; x < (int) Math.pow(2, N); x += step) {
                for (int y = 0; y < (int) Math.pow(2, N); y += step) {
                    rotate(x, y, L);
                }
            }
        }
        melt();
    }

    // 회전 처리
    static void rotate(int x, int y, int L) {
        int step = (int) Math.pow(2, L - 1);
        LinkedList<Integer>[] section = new LinkedList[4];

        for (int i = 0; i < 4; i++) {
            section[i] = new LinkedList<>();
        }

        for (int i = 0; i < step; i++) {
            for (int j = 0; j < step; j++) {
                section[0].add(board[x + i][y + j]);
                section[1].add(board[x + i][y + j + step]);
                section[2].add(board[x + i + step][y + j]);
                section[3].add(board[x + i + step][y + j + step]);
            }
        }

        // Rotate sections
        LinkedList<Integer> temp = section[0];
        section[0] = section[2];
        section[2] = section[3];
        section[3] = section[1];
        section[1] = temp;

        // Update board after rotation
        for (int i = 0; i < step; i++) {
            for (int j = 0; j < step; j++) {
                board[x + i][y + j] = section[0].removeFirst();
                board[x + i][y + j + step] = section[1].removeFirst();
                board[x + i + step][y + j] = section[2].removeFirst();
                board[x + i + step][y + j + step] = section[3].removeFirst();
            }
        }
    }

    // 얼음 녹이기
    static void melt() {
        int size = (int) Math.pow(2, N);
        newBoard = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] > 0) {
                    int cnt = 0;
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dx[d];
                        int nj = j + dy[d];
                        if (ni >= 0 && ni < size && nj >= 0 && nj < size && board[ni][nj] > 0) {
                            cnt++;
                        }
                    }
                    if (cnt >= 3) {
                        newBoard[i][j] = board[i][j];
                    } else {
                        newBoard[i][j] = board[i][j] - 1;
                    }
                }
            }
        }

        // Update board after melting
        board = newBoard;
    }

    // BFS로 가장 큰 덩어리 크기 찾기
    static int bfs(int x, int y) {
        int size = (int) Math.pow(2, N);
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cx = current[0], cy = current[1];
            count++;

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx >= 0 && nx < size && ny >= 0 && ny < size && !visited[nx][ny] && board[nx][ny] > 0) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        return count;
    }
}