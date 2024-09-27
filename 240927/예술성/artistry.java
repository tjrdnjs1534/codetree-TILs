import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, half;
    static int[][] board;
    static int[][] rotate_board;
    static int[] dr = { -1, 1, 0, 0 }; // dr, dc(상하좌우 좌표)
    static int[] dc = { 0, 0, -1, 1 };
    static int answer;

    /*
     * Group(그룹의 정보를 담은 클래스)
     */
    static class Group {
        int num; // num(그룹을 이루고 있는 숫자 값)
        int count; // count(그룹에 속한 칸의 수)

        public Group(int num) {
            this.num = num;
        }
    }

    /*
     * 예술성 준비하기
     */
    static void init(StringTokenizer st) throws IOException {
        N = Integer.parseInt(st.nextToken()); // N(격자의 크기)
        half = N / 2; // half(격자를 십자 모양으로 나눌 때의 중간 위치)

        board = new int[N][N]; // board(그림 정보 배열)
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken()); // 그림 정보 저장
            }
        }
    }

    /*
     * 예술 점수 구하기
     */
    static void point() {
        answer = 0; // answer(총 예술 점수)
        for (int i = 0; i < 4; i++) {
            Group[][] groups = findGroups();
            calculateGroups(groups);
            rotate();
        }
    }

    /*
     * 그룹 찾기
     */
    static Group[][] findGroups() {
        Group[][] groups = new Group[N][N]; // groups(좌표에 따른 그룹을 담는 배열)
        boolean[][] visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    int num = board[i][j];
                    Group group = new Group(num);
                    group.count++;
                    groups[i][j] = group;

                    Queue<int[]> queue = new LinkedList<>(); // BFS 탐색으로 상하좌우 이동하여 그룹을 구함
                    queue.add(new int[] { i, j });
                    visited[i][j] = true;
                    while (!queue.isEmpty()) {
                        int[] now = queue.poll();
                        for (int d = 0; d < 4; d++) {
                            int nr = now[0] + dr[d];
                            int nc = now[1] + dc[d];
                            if (nr < 0 || nr >= N || nc < 0 || nc >= N || board[nr][nc] != num || visited[nr][nc]) {
                                continue;
                            }
                            visited[nr][nc] = true;
                            group.count++;
                            groups[nr][nc] = group;
                            queue.add(new int[] { nr, nc });
                        }
                    }
                }
            }
        }
        return groups;
    }

    /*
     * 그룹에 따른 그룹 쌍의 조화로움의 합 찾기
     */
    static void calculateGroups(Group[][] groups) {
        int point = 0; // point(조화로움의 합)
        for (int r = 0; r < groups.length; r++) {
            for (int c = 0; c < groups.length; c++) {
                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                        if (groups[r][c] != groups[nr][nc]) { // 서로 다른 그룹이 서로 맞닿을 때마다 조화로움의 합을 구하도록 함
                            point += (groups[r][c].count + groups[nr][nc].count)
                                    * groups[r][c].num * groups[nr][nc].num;
                        }
                    }
                }
            }
        }
        point = point / 2; // 양쪽 그룹에서 맞닿으므로 2로 나누어줌
        answer += point; // answer에 합산
    }

    /*
     * 그림 회전하기
     */
    static void rotate() {
        rotate_board = new int[N][N]; // rotate_board(회전한 그림 정보 배열)
        rotate_plus(); // 십자 모양 회전
        rotate_square(0, 0); // 왼쪽 위 정사각형 회전
        rotate_square(0, half + 1); // 오른쪽 위 정사각형 회전
        rotate_square(half + 1, 0); // 왼쪽 아래 정사각형 회전
        rotate_square(half + 1, half + 1); // 오른쪽 아래 정사각형 회전
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = rotate_board[i][j]; // 회전한 모양을 원래 모양에 적용
            }
        }
    }

    /*
     * 십자 모양 반 시계 방향 90도 회전
     */
    static void rotate_plus() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == half) {
                    rotate_board[i][j] = board[j][i]; // 십자 모양의 상, 우 부분 이동
                }
                if (j == half) {
                    rotate_board[i][j] = board[N - j - 1][N - i - 1]; // 십자 모양의 좌, 우 부분 이동
                }
            }
        }
    }

    /*
     * 십자 모양 제외 4개의 정사각형 시계 방향 90도 회전
     */
    static void rotate_square(int startR, int startC) {
        for (int r = startR; r < startR + half; r++) {
            for (int c = startC; c < startC + half; c++) {
                int or = r - startR; // or, oc(0, 0으로 변환한 상대적인 위치)
                int oc = c - startC;
                int nr = oc; // nr, nc(회전한 결과의 상대적인 위치)
                int nc = half - or - 1;
                rotate_board[nr + startR][nc + startC] = board[r][c]; // 회전한 결과를 절대적인 위치에 저장
            }
        }
    }

    /*
     * 예술 점수 출력하기
     */
    static void print() {
        System.out.println(answer);
    }

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        init(st);
        point();
        print();
    }
}