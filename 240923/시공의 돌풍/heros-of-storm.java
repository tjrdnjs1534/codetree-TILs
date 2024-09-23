import java.io.*;
import java.util.*;

public class Main {
    static int n, m, t;
    static int[][] map;
    static int upX;
    static int upY = 0;
    static int downX;
    static int downY = 0;

    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        map = new int[n][m];

        boolean findFlag = false;
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<m; j++){
                int num = Integer.parseInt(st.nextToken());
                if(num==-1){
                    map[i][j]=0;
                    if(findFlag) continue;
                    upX = i;
                    downX = i+1;
                    findFlag = true;
                    continue;
                }
                map[i][j] = num;
            }
        }
        for(int i=0; i<t; i++){
            step1();
            upRotate();
            downRotate();
            // print();
        }
        System.out.println(ans());

    }
    public static void step1(){
        int[][] tmp = new int[n][m];

        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                int cnt = 0;
                int dust = map[i][j];
                int moveDust = dust/5;
                for(int d=0; d<4; d++){
                    int nx = i+dx[d];
                    int ny = j+dy[d];
                    if(nx<0 || nx>=n || ny<0 || ny>=m) continue;
                    if(nx==upX && ny==0) continue;
                    if(nx==downX && ny==0) continue;
                    cnt++;
                    tmp[nx][ny] += moveDust;
                }
                tmp[i][j] += dust-moveDust*cnt;
            }
        }
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                map[i][j] = tmp[i][j];
            }
        }
        map[upX][0] = 0;
        map[downX][0] =0;
    }
    public static void upRotate(){
        int topLeft = map[0][0];
        int topRight = map[0][m-1];
        int bottomRight = map[upX][m-1];
        for(int i=1; i<m; i++){
            map[0][i-1] = map[0][i];
        }
        for(int i=upX; i>=2; i--){
            map[i][0] = map[i-1][0];
        }
        map[1][0] = topLeft;
        map[upX][0] =0;
        for(int i=m-1; i>0; i--){
            map[upX][i] = map[upX][i-1];
        }
        for(int i=0; i<upX-1; i++){
            map[i][m-1] = map[i+1][m-1];
        }
        map[upX-1][m-1] = bottomRight;

    }

    public static void downRotate(){
        int bottomLeft = map[n-1][0];
        int topRight = map[downX][m-1];
        int bottomRight = map[n-1][m-1];

        for(int i=m-1; i>0; i--){
            map[downX][i] = map[downX][i-1];
        }
        for(int i=downX+1; i<n-1; i++){
            map[i+1][m-1] = map[i][m-1];
        }
        map[downX+1][m-1] = topRight;
        for(int i=1; i<m-1; i++){
            map[n-1][i-1] = map[n-1][i];
        }
        map[n-1][m-2] = bottomRight;
        for(int i=0; i>0; i--){
            map[i-1][0] = map[i][0];
        }
        map[n-2][0] = bottomLeft;
        map[downX][0] = 0;

    }
    public static int ans(){
        int sum=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                sum+=map[i][j];
            }
            
        }
        return sum;
    }

    public static void print(){
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
    }
}