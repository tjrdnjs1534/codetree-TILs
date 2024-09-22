import java.util.*;
import java.io.*;
 
public class Main {
    static int[] dx = {0, 1, 0, -1}; // 회전 방향 : 서남동북
    static int[] dy = {-1, 0, 1, 0};
 
    static int N;
 
    static int x, y;
    static int dir, moveNum; // 이동 방향, 이동 거리
 
    static int ans;
 
    static int[][] grid;
 
    // 먼지 비율 
    static int[][][] dustRatio = new int[][][] {
        {
            {0, 0, 2, 0, 0},
            {0, 10, 7, 1, 0},
            {5, 0, 0, 0, 0},
            {0, 10, 7, 1, 0},
            {0, 0, 2, 0, 0},
        },
        {
            {0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {2, 7, 0, 7, 2},
            {0, 10, 0, 10, 0},
            {0, 0, 5, 0, 0},
        },
        {
            {0, 0, 2, 0, 0},
            {0, 1, 7, 10, 0},
            {0, 0, 0, 0, 5},
            {0, 1, 7, 10, 0},
            {0, 0, 2, 0, 0},
        },
        {
            {0, 0, 5, 0, 0},
            {0, 10, 0, 10, 0},
            {2, 7, 0, 7, 2},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0},
        }        
    };
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
 
        N = Integer.parseInt(br.readLine());
        grid = new int[N][N];
 
        for(int i = 0 ; i < N ; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for(int j = 0 ; j < N ; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
 
        x = N/2;
        y = N/2;
        dir = 0;
        moveNum = 1;   
 
        while(!end()) {
            for(int i = 0 ; i < moveNum ; i++) {
                move();
 
                if(end()) { // 좌측 최상단에 도착하면 종료
                    break;
                }
            }
 
            dir = (dir + 1) % 4;
            if(dir == 0 || dir == 2) {
                moveNum++;
            }            
        }
 
        System.out.println(ans);
    }
 
    private static boolean end() {
        return (x == 0 && y == 0);
    }
 
    private static void move() {
        x += dx[dir];
        y += dy[dir];
 
        int addedDust = 0;
        for(int i = 0 ; i < 5 ; i++) {
            for(int j = 0 ; j < 5 ; j++) {
                int dust = grid[x][y] * dustRatio[dir][i][j] / 100;
                addDust(x + i - 2, y + j - 2, dust);
 
                addedDust += dust;
            }
        }
 
        // a% 자리에 먼지 추가하기
        addDust(x + dx[dir], y + dy[dir], grid[x][y] - addedDust);
    }
 
    private static void addDust(int a, int b, int dust) {
        if(!inRange(a, b))  ans += dust;           
        else                grid[a][b] += dust;     
    }
 
    private static boolean inRange(int x, int y) {
        return (0 <= x && x < N && 0 <= y && y < N );
    }
}