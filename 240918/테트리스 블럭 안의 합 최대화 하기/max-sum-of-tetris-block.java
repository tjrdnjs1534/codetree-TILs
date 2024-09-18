import java.util.*;
import java.io.*;

public class Main {
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static int max = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for(int i= 0; i<N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited = new boolean[N][M];
        for(int i= 0; i<N; i++) {
            for(int j=0; j < M; j++) {
                // 4칸 뽑는 dfs
                visited[i][j] = true;
                dfs(i, j, 1, map[i][j]);
                visited[i][j] = false;

                comb(0, 0, i, j, map[i][j]); // 인접한 4칸 중 3칸 고르기 (ㅗ, ㅏ, ㅓ, ㅜ)
            }
        }

        System.out.println(max);
    }

    private static void dfs(int r, int c, int depth, int sum) {
        if(depth == 4) {
            max = Math.max(max, sum);
            return;
        }

        for(int d = 0; d < 4; d++) { // 4방향 돌며 탐색
            int nr = r + dx[d];
            int nc = c + dy[d];

            if(!inRange(nr, nc) || visited[nr][nc]) continue;

            visited[nr][nc] = true;
            dfs(nr, nc, depth + 1, sum + map[nr][nc]);
            visited[nr][nc] = false;
        }
    }

    // ㅗ자 모양 처리
    private static void comb(int start, int depth, int r, int c, int sum) {
        if(depth == 3) {
            max = Math.max(max, sum);
            return;
        }

        for(int d = start ; d < 4; d++) {
            int nr = r + dx[d];
            int nc = c + dy[d];

            if(!inRange(nr, nc)) continue;

            comb(d + 1, depth + 1, r, c, sum + map[nr][nc]); // ㅗ자 모양 처리
        }
    }

    private static boolean inRange(int r, int c) {
        return (0 <= r && r < N && 0 <= c && c < M);
    }
}