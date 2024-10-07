import java.util.*;
import java.io.*;

public class Main {
    static int n;
    static int[][]map;
    static int[] dx = {0,0,-1,1};
    static int[] dy= {1,-1,0,0};
    static int robotLevel = 2;
    static int robotCnt = 0;
    static int robotX;
    static int robotY;
    static int[][] dist;
    static int time;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        for(int i =0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                int num = Integer.parseInt(st.nextToken());
                if(num==9){
                    map[i][j] = 0;
                    robotX = i;
                    robotY = j;
                    continue;
                }
                map[i][j]= num;
            }
        }
        while(true){
            bfs(robotX, robotY);
            int[] next = findNextMonster();
            if(next[0]==Integer.MAX_VALUE) break;
            int x = next[1];
            int y = next[2];
            time += dist[x][y];
            robotCnt++;
            robotX =x;
            robotY =y;
            map[x][y] = 0;
            if(robotCnt == robotLevel){
                robotCnt = 0;
                robotLevel ++;
            }
        }
        System.out.println(time);
    }
    public static void bfs(int startX , int startY){
        Queue<int[]> q = new LinkedList<>();
        dist = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j =0;j<n; j++){
                dist[i][j]=-1;
            }
        }
        q.offer(new int[]{startX, startY});
        dist[startX][startY] = 0;
        while(!q.isEmpty()){
            int[] location = q.poll();
            int curX = location[0];
            int curY = location[1];

            for(int d=0; d<4; d++){
                int nx = curX + dx[d];
                int ny= curY +dy[d];
                if(nx<0 || nx>=n || ny<0|| ny>=n) continue;
                if(dist[nx][ny]>0) continue;
                if(map[nx][ny] > robotLevel) continue;
                q.offer(new int[]{nx,ny});
                dist[nx][ny] = dist[curX][curY] + 1;
            }
        }
    }
    public static int[] findNextMonster(){
        int min = Integer.MAX_VALUE; 
        int x = Integer.MAX_VALUE; 
        int y = Integer.MAX_VALUE; 
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j] !=0 && map[i][j] < robotLevel){
                    if(dist[i][j]==-1)continue;
                    if(dist[i][j] < min){
                        min = dist[i][j];
                        x = i;
                        y = j;
                    }
                }
            }
        }
        return new int[]{min,x,y};


    }
}