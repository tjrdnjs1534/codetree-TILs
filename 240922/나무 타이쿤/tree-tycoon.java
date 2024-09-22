import java.io.*;
import java.util.*;

public class Main {
    static int[] dx = {0,-1,-1,-1,0,1,1,1};
    static int[] dy = {1,1,0,-1,-1,-1,0,1};
    static int n,m;
    static int[][] map;
    static ArrayList<Emple> emples = new ArrayList<>();
    static boolean[][] visited;
    public static class Emple{
        int x;
        int y;
        Emple(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void move(int d, int step){
            int nx = this.x;
            int ny = this.y;

            for(int i=0; i<step; i++){
                nx = nx+dx[d];
                ny = ny+dy[d];
                if(nx>=n) nx = 0;
                else if(nx==-1) nx = n-1;
                if(ny>=n) ny=0;
                else if(ny==-1) ny= n-1;
            }
            this.x = nx;
            this.y = ny;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        visited = new boolean[n][n];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        emples.add(new Emple(n-1,0));
        emples.add(new Emple(n-1,1));
        emples.add(new Emple(n-2,0));
        emples.add(new Emple(n-2,1));

        for(int t=0; t<m; t++){
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken())-1;
            int step = Integer.parseInt(st.nextToken());
            move(d,step);
            emples.clear();
            cut();
            visited = new boolean[n][n];

        }
        printAns();
    }
    public static void move(int d, int step){
        for(Emple e : emples){
            e.move(d,step);
            visited[e.x][e.y] = true;
            map[e.x][e.y] +=1;
            
        }
        for(Emple e : emples){
            int cnt = count(e.x, e.y);
            map[e.x][e.y] += cnt;
        }
    }
    public static int count(int x, int y){
        int cnt = 0;
        for(int i=1; i<8; i+=2){
            int nx = x+dx[i];
            int ny = y+dy[i];
            if(nx<0 || nx>=n || ny <0 || ny>=n) continue;
            if(map[nx][ny]>0) cnt++;
        }
        return cnt;
    }
    public static void cut(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(visited[i][j]) continue;
                if(map[i][j]<2) continue;
                emples.add(new Emple(i,j));
                map[i][j]-=2;
            }
        }
    }
    public static void print(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(map[i][j]+ " ");
            }
            System.out.println();
        }
    }
    public static void printEmples(){
        for(Emple e: emples){
            System.out.println(e.x + " "+ e.y);
        }
        System.out.println();
    }
    public static void printAns(){
        int sum = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                sum+=map[i][j];
            }
            
        }
        System.out.println(sum);

    }
}