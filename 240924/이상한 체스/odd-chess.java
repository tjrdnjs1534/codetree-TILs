import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static int m;
    static int[] dx = {0, -1, 0, 1}; 
    static int[] dy = {1, 0, -1, 0};
    static int[][] board;
    static ArrayList<int[]> cctv =new ArrayList<>();
    static int answer = 64;
    
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        n = Integer.parseInt(nm[0]);
        m = Integer.parseInt(nm[1]);
        int[] nums = new int[n];
        board = new int[n][m];
        for(int i=0; i<n; i++){
            String[] rows = br.readLine().split(" ");
            for(int j=0; j<m; j++){
                int num = Integer.parseInt(rows[j]);
                board[i][j] = num;
                if(num>=1 && num<=5) {
                    cctv.add(new int[]{i,j});
                }
            }
        }
        solve(0);
        System.out.println(answer);
    }
    
    public static void solve(int cctvIdx) {
        if(cctv.size()== cctvIdx){
            count();
            return;
        }
        int[] nowCctv = cctv.get(cctvIdx);
        int[][] copy = new int[n][m];
        int x = nowCctv[0];
        int y = nowCctv[1];
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++){
                    copy[i][j] = board[i][j];
            }
        }
        
        for(int d=0; d<4; d++){
            for(int i=0; i<n; i++) {
                for(int j=0; j<m; j++){
                    copy[i][j] = board[i][j];
                }
            }
            
            if (board[x][y] == 1) {
              check(x, y, d);
            } else if (board[x][y] == 2) {
              check(x, y, d);
              check(x, y, d + 2);
            } else if (board[x][y] == 3) {
              check(x, y, d + 1);
              check(x, y, d);
            } else if (board[x][y] == 4) {
              check(x, y, d);
              check(x, y, d + 1);
              check(x, y, d + 2);
            } else if (board[x][y] == 5) {
              check(x, y, d);
              check(x, y, d + 1);
              check(x, y, d + 2);
              check(x, y, d + 3);
            }
            solve(cctvIdx+1);
            for (int i = 0; i < n; i++) {
              for (int j = 0; j < m; j++) {
                board[i][j] = copy[i][j];
              }
            }

        }
        
    }
    public static void check(int x,int y, int dir) {
        int nowDir = dir%4;
        while(true) {
            int nx = x +dx[nowDir];
            int ny = y + dy[nowDir];
            x=nx;
            y =ny;
            if(nx<0 || nx>=n || ny<0 || ny>= m) return;
            if(board[nx][ny] ==6) return;
            if(board[nx][ny] != 0) continue;
            board[nx][ny]= 7;
        }
    }
    
    
    public static void count() {
        int cnt=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(board[i][j]==0) cnt++;
            }
        }
        answer = Math.min(answer, cnt);

    }

    
}