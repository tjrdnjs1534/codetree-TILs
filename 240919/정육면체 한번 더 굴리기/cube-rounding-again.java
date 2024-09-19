import java.io.*;
import java.util.*;

public class Main {
    static int[] dx = {0,1,0,-1};
    static int[] dy = {1,0,-1,0};
    static int n,m;
    static int[][] map;
    static Dice dice;
    static int ans;
    static boolean[][] visited;
    static int[][]scoreBoard;
    public static class Dice{
        int x;
        int y;
        int dir;
        int up, down, front, back, left, right;
        Dice(){
            this.x = 1;
            this.y = 1;
            this.dir = 0;
            this.up = 1;
            this.front = 2;
            this.back = 5;
            this.left = 4;
            this.right=3;
            this.down = 6;
        }
        void rotateClock(){
            this.dir = (this.dir +1)%4;
        }
        void rotateCounterClock(){
            this.dir = this.dir -1 <0 ? 3: this.dir-1;
        }
        void move(){
            int nx = this.x + dx[this.dir];
            int ny = this.y + dy[this.dir];
            if(isOut(nx,ny)) {
                this.dir = (this.dir +2)%4;
                nx = this.x + dx[this.dir];
                ny = this.y + dy[this.dir];
            }
            this.x = nx;
            this.y = ny;
            int tmp;
            switch(this.dir){
                // right
                case 0:
                    tmp = this.right;
                    this.right = this.up;
                    this.up = this.left;
                    this.left = this.down;
                    this.down = tmp;
                    break;
                // down
                case 1: 
                    tmp = this.up;
                    this.up = this.back;
                    this.back = this.down;
                    this.down = this.front;
                    this.front = tmp;
                    break;
                //left
                case 2:
                    tmp = this.left;
                    this.left = this.up;
                    this.up = this.right;
                    this.right= this.down;
                    this.down = tmp;
                    break;
                //up
                case 3: 
                    tmp = this.up;
                    this.up = this.front;
                    this.front = this.down;
                    this.down = this.back;
                    this.back = tmp;
                    break;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n+1][n+1];
        dice = new Dice();
        scoreBoard = new int[n+1][n+1];
        visited = new boolean[n+1][n+1];
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=n; j++){
                int num = Integer.parseInt(st.nextToken());
                map[i][j] = num;
            }
        } 
        for(int step = 0; step<m; step++){
            dice.move();
            // System.out.println(dice.x + " " + dice.y);
            int score =bfs();
            ans+= score;
            changeDir();
        }
        System.out.println(ans);
    }
    public static boolean isOut(int x, int y){
        if(x<=0 || x>n || y<=0 || y>n) return true;
        return false;
    }
    public static int bfs(){
        int startX = dice.x;
        int startY = dice.y;
        int score = map[startX][startY];        
        visited = new boolean[n+1][n+1];
        Queue<int[]> q = new LinkedList<>();
        int cnt = 1;
        q.add(new int[]{startX,startY});
        visited[startX][startY] = true;

        // ArrayList<int[]> routes = new ArrayList<>();
        // routes.add(new int[]{startX,startY});
        while(!q.isEmpty()){
            int[] location = q.poll();
            int curX = location[0];
            int curY = location[1];

            for(int d = 0; d<4; d++){
                int nx = curX + dx[d];
                int ny = curY + dy[d];
                if(isOut(nx,ny))continue;
                if(visited[nx][ny]) continue;
                if(map[nx][ny] != score) continue;
                cnt++;
                q.add(new int[]{nx,ny});
                visited[nx][ny] = true;
                // routes.add(new int[]{nx,ny});
            }
        }
        // int finalScore = cnt*score;
        return cnt*score;
        // for(int[] location : routes){
        //     int x = location[0];
        //     int y = location[1];
        //     scoreBoard[x][y] = finalScore;
        // }

    }
    public static void changeDir() {
        int score = map[dice.x][dice.y];
        int down = dice.down;
        if(down > score){
            dice.rotateClock();
        }
        else if(down<score) {
            dice.rotateCounterClock();
        }

    }
}