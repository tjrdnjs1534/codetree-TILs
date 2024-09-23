import java.util.*;
import java.io.*;

public class Main {
    static int n, m, k;
    static PriorityQueue<Virus> viruses;
    static int[][] map;
    static int[][] plusMap;
    static int[] dx = {-1,1,0,0,-1,-1,1,1};
    static int[] dy = {0,0,-1,1,-1,1,-1,1};

    static class Virus implements Comparable<Virus>{
        int x;
        int y;
        int age;
        int isDead;
        Virus(int x, int y, int age){
            this.x=x;
            this.y=y;
            this.age =age;
            this.isDead = 0;
        }
        @Override
        public int compareTo(Virus o){
            return this.age - o.age;
        }
    }
  

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new int[n+1][n+1];
        plusMap=new int[n+1][n+1];
        viruses = new PriorityQueue<>();
        for(int i=1; i<=n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=n; j++){
                map[i][j]= 5;
                plusMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for(int j=0;j<m; j++){
            st = new StringTokenizer(br.readLine());  
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int age =Integer.parseInt(st.nextToken());
            Virus v = new Virus(x,y,age);
            viruses.add(v);
        }

        for(int t=1; t<=k; t++){
            eat(t);
            dead(t);
            replica();
            
            plus();

        }
        System.out.println(check());

    }
    public static void eat(int t){
        PriorityQueue<Virus> tmp = new PriorityQueue<>();
        while(!viruses.isEmpty()){
            Virus v = viruses.poll();
            if(v.isDead !=0) continue;
            if(map[v.x][v.y] < v.age) {
                v.isDead = t;
            }
            else {
                map[v.x][v.y]-= v.age;
                v.age+=1;
            }
            tmp.add(v);
        }
        viruses = tmp;
    }
    public static void dead(int t){
        PriorityQueue<Virus> tmp = new PriorityQueue<>();
        while(!viruses.isEmpty()){
            Virus v = viruses.poll();
            if(v.isDead ==0) {
                tmp.add(v);
                continue;
            }
            if(v.isDead!=t) {
                continue;
            }
            map[v.x][v.y] += v.age/2;
            tmp.add(v);
        }
        viruses= tmp;
        
    }
    public static void replica(){
        PriorityQueue<Virus> tmp = new PriorityQueue<>();
        while(!viruses.isEmpty()){
            Virus v = viruses.poll();
            if(v.age%5 !=0) {
                tmp.add(v);
                continue;
            }
            if(v.isDead !=0) continue;
            int x = v.x;
            int y = v.y;
            for(int d=0; d<8; d++){
                int nx = x+dx[d];
                int ny = y+dy[d];
                if(nx<=0 || nx>n || ny<=0 || ny>n) continue;
                tmp.add(new Virus(nx,ny,1));
            }
            tmp.add(v);
        }
        viruses = tmp;
        
    }
    public static void plus(){
        for(int i=1; i<=n; i++){
            for(int j=1; j<=n; j++){
                map[i][j]+=plusMap[i][j];
            }
        }
    }
    public static int check(){
        int cnt = 0;
        for(Virus v : viruses){
            if(v.isDead !=0) continue;
            cnt++;
        }
        return cnt;
    }
    public static void print(){
        for(int i=1; i<=n; i++){
            for(int j=1; j<=n; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    // public static void printV(){
    //     for(int i=0; i<viruses.size(); i++){
    //         Virus v = viruses.get(i);
    //         // if(v.isDead !=0) continue;
    //         System.out.println(v.x + " " + v.y + " " + v.age + " " + v.isDead);
    //     }
    //     System.out.println();
    // }
}