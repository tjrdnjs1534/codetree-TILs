import java.io.*;
import java.util.*;

public class Main {
    static int n,m,k;
    static int[] dx = {0,-1,1,0,0};
    static int[] dy = {0,0,0,-1,1};
    static Person[] persons;
    static Map[][] map;
    static class Person{
        int x;
        int y;
        int dir;
        int idx;
        boolean isDead;
        int[][] priorities;
        Person(int x, int y, int dir, int idx){
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.idx = idx;
            this.isDead = false;
            priorities = new int[4][4];
        }
    }

    static class Map{
        int idx;
        int remainTurn;

        Map(){
            this.idx= 0;
            this.remainTurn=0;
        }
        Map(int idx, int remainTurn){
            this.idx = idx;
            this.remainTurn = remainTurn;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        persons = new Person[m+1];
        persons[0] = new Person(0,0,0,0);
        persons[0].isDead = true;
        map = new Map[n][n];
        for(int i=0;i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                int num = Integer.parseInt(st.nextToken());
                if(num==0){
                    map[i][j] = new Map();
                }
                else{
                    map[i][j] = new Map(num, k);
                    persons[num] = new Person(i,j,0, num);
                }
            }
        }
        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=m; i++){
            persons[i].dir = Integer.parseInt(st.nextToken());
        }
        for(int i=1; i<=m; i++){
            for(int j=0; j<4; j++){
                st = new StringTokenizer(br.readLine());
                for(int t=0; t<4; t++){
                    persons[i].priorities[j][t] = Integer.parseInt(st.nextToken());
                }
            }
        }   
        int step = 1;
        while(true){
            move();
            reduce();
            removeSame();
            mapupdate();
            // printP();
            int cnt = check();
            if(cnt==1) break;
            step++;
            if(step>=1000) {
                System.out.println(-1);
                return;
            }
        }
        System.out.println(step);

    }
    public static void move(){
        for(Person p : persons){
            if(p.isDead) continue;
            int x = p.x;
            int y = p.y;
            int dir = p.dir;
            int[] priority = p.priorities[dir-1];
            boolean empty = false;
            for(int i=0; i<4; i++){
                int nd = priority[i];
                int nx = x+ dx[nd];
                int ny = y + dy[nd];
                if(nx<0 || nx>=n || ny<0 || ny>=n) continue;
                if(map[nx][ny].idx ==0){
                    empty = true;
                    p.x = nx;
                    p.y = ny;
                    p.dir = nd;
                    break;
                }
            }
            if(empty) continue;
            for(int i=0; i<4; i++){
                int nd = priority[i];
                int nx = x+ dx[nd];
                int ny = y + dy[nd];
                if(nx<0 || nx>=n || ny<0 || ny>=n) continue;
                if(map[nx][ny].idx == p.idx){
                    p.x = nx;
                    p.y = ny;
                    p.dir = nd;
                    break;
                }
            }
        }
    }
    public static void reduce(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j].idx!=0){
                    map[i][j].remainTurn-=1;
                    if(map[i][j].remainTurn==0){
                        map[i][j].idx=0;
                    }
                }
            }
        }
    }
    public static void removeSame(){
        for(int i=1; i<m; i++){
            if(persons[i].isDead) continue;
            Person p = persons[i];
            for(int j=i+1; j<=m; j++){
                Person np = persons[j];
                if(np.isDead) continue;
                if(p.x == np.x &&  p.y == np.y){
                    np.isDead = true;
                }
            }
        }
    }
    public static void mapupdate(){
        for(int i=1; i<=m; i++){
            if(persons[i].isDead) continue;
            Person p = persons[i];
            int x = p.x;
            int y = p.y;
            
            map[x][y].idx = p.idx;
            map[x][y].remainTurn = k;
        }
    }
    public static int check(){
        int cnt =0;
        for(int i=1; i<=m; i++){
            if(persons[i].isDead) continue;
            cnt++;
        }
        return cnt;
    }
    public static void print(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(map[i][j].remainTurn+" ");
            }
            System.out.println();
        }
    }
    public static void printP(){
        for(int i=1; i<=m; i++){
            System.out.println(persons[i].idx + " "+ persons[i].x + " " + persons[i].y + " " + persons[i].isDead);
        }
    }
}