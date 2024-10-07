import java.util.*;
import java.io.*;

public class Main {

    static int n, m, k;
    static Moll[][] map;
    static Moll[][] temp;
    static int[] dx = {0,-1,1,0,0};
    static int[] dy = {0,0,0,1,-1};
    static int ans;
    public static class Moll{
        int s;
        int d;
        int b;

        Moll(int s, int d, int b){
            this.s=s;
            this.d = d;
            this.b = b;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new Moll[n][m];
        for(int i=0; i<k; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            map[x-1][y-1] = new Moll(s,d,b);
        }
        // print();
        for(int i=0; i<m; i++){
            searchMoll(i);
            moveMolls();
        }
        System.out.println(ans);

    }
    public static void searchMoll(int col){
        for(int i=0; i<n;i++){
            if(map[i][col]!=null){
                Moll m = map[i][col];
                ans+=m.b;
                map[i][col] = null;
                return;
            }
        }
    }
    public static void moveMolls(){
        temp = new Moll[n][m];
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(map[i][j]!=null){
                    moveMoll(i,j,map[i][j]);
                }
            }
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                map[i][j]= temp[i][j];
            }
        }
        // print();
        // System.out.println();
    }
    public static void moveMoll(int x, int y, Moll moll){
        int s = moll.s;
        int d = moll.d;
        for (int i = 0; i < s; i++) {
            switch (d) {
                case 1: // 위쪽(↑)
                    x--;
                    if (x < 0) { // 위쪽 벽에 부딪힘
                        x = 1; // 벽을 넘지 않도록 조정
                        d = 2; // 반대 방향(아래쪽)으로 변경
                    }
                    break;
                
                case 2: // 아래쪽(↓)
                    x++;
                    if (x >= n) { // 아래쪽 벽에 부딪힘
                        x = n - 2; // 벽을 넘지 않도록 조정
                        d = 1; // 반대 방향(위쪽)으로 변경
                    }
                    break;
                case 3: // 오른쪽(→)
                    y++;
                    if (y >= m) {
                        y = m - 2; 
                        d = 4; 
                    }
                    break;
                case 4: // 왼쪽(←)
                    y--;
                    if (y < 0) { // 왼쪽 벽에 부딪힘
                        y = 1; // 벽을 넘지 않도록 조정
                        d = 3; // 반대 방향(오른쪽)으로 변경
                    }
                    break;
            }
        }
        int nx = x;
        int ny =y;
        moll.d = d;
        if(temp[nx][ny]==null){
            temp[nx][ny] = moll;
        }
        else {
            Moll tmp =temp[nx][ny];
            if(tmp.b <moll.b){
                temp[nx][ny] = moll;
            }
        }
    }
    public static boolean isOut(int x, int y){
        if(x<0 || x>=n || y<0 || y>=m) return true;
        return false;
    }
    public static void print(){
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(map[i][j]!=null){
                    System.out.print(map[i][j].b+" ");
                }
                else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
}