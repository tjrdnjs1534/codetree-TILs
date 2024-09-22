import java.util.*;
import java.io.*;

public class Main {
    static int n,m;
    static Car c;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,1,0,-1};
    static int[][] map;
    static boolean[][] visited;
    static int ans;
    public static class Car{
        int x;
        int y;
        int d;

        Car(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
    
        c = new Car(x,y,d);
        map = new int[n][m];
        visited = new boolean[n][m];
        visited[x][y] = true;
        ans++;
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<m; j++){
                map[i][j]=  Integer.parseInt(st.nextToken());
            }
        }
        move();

    }
    public static void move(){
        int cnt = 0;
        while(true){
            if(cnt ==4){
                int nx = c.x + dx[(c.d+2) %4];
                int ny = c.y + dy[(c.d+2) %4];

                if(map[nx][ny]!= 1){
                    c.x = nx;
                    c.y = ny;
                    cnt = 0;
                    continue;
                }
                else {
                    System.out.println(ans);
                    return;
                }
            }
            int nextD = c.d -1 == -1 ? 3: c.d -1;
            int nx = c.x + dx[nextD];
            int ny = c.y + dy[nextD];

            if(map[nx][ny] != 1 && visited[nx][ny] != true) {
                c.d = nextD;
                c.x = nx;
                c.y = ny;
                cnt = 0;
                ans++;
                visited[nx][ny]=true;
            }
            else {
                cnt++;
                c.d = nextD;
            }
        }
        
    }

    public static boolean isOut(int x,int y){
        if(x<0 || x>=n || y<0 || y>=m)return true;
        return false;
    }
}