import java.util.*;
import java.io.*;

public class Main {
    static int n,m,k,C;
    static int [][] map;
    static int [][] jecho;
    static int [][] tmp;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static int[] mx = {-1,-1,1,1};
    static int[] my = {-1,1,-1,1};
    static int ans;
    static boolean isOver;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        jecho = new int[n][n];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j] =Integer.parseInt(st.nextToken());
            }
        }
        for(int i=0; i<m; i++){
            grow();reproduce();
            jechoje();
            if(isOver) break;
            decrease();
        }
        System.out.println(ans);
    }
    public static void grow(){
        tmp = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                tmp[i][j] = map[i][j];
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j] != 0 && map[i][j]!=-1 ){
                    int cnt =0;
                    for(int d = 0; d<4; d++){
                        int nx = i+dx[d];
                        int ny = j+dy[d];
                        if(isOut(nx,ny)) continue;
                        if(map[nx][ny] <=0) continue;
                        cnt++;
                    }
                    tmp[i][j] = map[i][j] + cnt;
                }
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                map[i][j] = tmp[i][j];
            }
        }
    }
    public static void reproduce(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j] != 0 && map[i][j]!=-1 ){
                    ArrayList<int[]> next= new ArrayList<>();
                    for(int d = 0; d<4; d++){
                        int nx = i+dx[d];
                        int ny = j+dy[d];
                        if(isOut(nx,ny)) continue;
                        if(map[nx][ny] ==-1 || map[nx][ny] >0) continue;
                        if(jecho[nx][ny]!=0) continue;
                        next.add(new int[]{nx,ny});
                    }
                    if(next.size()==0) continue;
                    int nextTree = map[i][j] / next.size();
                    for(int[] nxy : next){
                        tmp[nxy[0]][nxy[1]] += nextTree;
                    }
                }
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                map[i][j] = tmp[i][j];
            }
        }
    }
    public static void jechoje(){
        int max = -1;
        int r = 21;
        int c = 21;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j]>0){
                    int sum = map[i][j];
                    for(int d=0; d<4; d++){
                        int nx = i;
                        int ny = j;
                        int step=0;
                        while(step<k){
                            nx = nx +mx[d];
                            ny = ny + my[d];
                            if(isOut(nx,ny)) break;
                            if(map[nx][ny] == 0 || map[nx][ny] ==-1) break;
                            sum += map[nx][ny];
                            step++;
                        }
                    }
                    if(sum>max){
                        max = sum;
                        r = i;
                        c = j;
                    }
                }
            }
        }
        if(r==21) {
            isOver = true;
            return;
        }
        ans+= map[r][c];
        map[r][c] =0;
        jecho[r][c] = C+1;
        for(int d=0; d<4; d++){
            int nx = r;
            int ny = c;
            int step=0;
            while(step<k){
                nx = nx +mx[d];
                ny = ny + my[d];
                if(isOut(nx,ny)) break;
                if(map[nx][ny] == 0 || map[nx][ny] ==-1) break;
                ans += map[nx][ny]; 
                map[nx][ny] = 0;
                jecho[nx][ny]= C+1;
                step++;
            }
        }
    }
    public static void decrease(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(jecho[i][j] >0) jecho[i][j]--;
            }
        }
    }

    public static boolean isOut(int x, int y){
        if(x<0 || x>=n || y<0 || y>=n) return true;
        return false;
    }
}