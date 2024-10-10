import java.util.*;
import java.io.*;

public class Main {
    static int n, m, k;
    static class Atom{
        int weight;
        int speed;
        int dir;
        Atom(int weight,int speed, int dir){
            this.weight = weight;
            this.dir = dir;
            this.speed = speed;
        }
        Atom(){
            this.weight = 0;
            this.speed = 0;
            this.dir =-1;
        }
    }
    static ArrayList<Atom>[][] map; 
    static ArrayList<Atom>[][] tmp; 

    static int[] dx = {-1,-1,0,1,1,1,0,-1};
    static int[] dy = {0, 1, 1,1,0,-1,-1,-1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new ArrayList[n][n];
        for(int i=0; i<n; i++){
            for(int j = 0; j<n; j++){
                map[i][j]= new ArrayList<>();
            }
        }
        for(int i=0; i<m; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            map[x][y].add(new Atom(m,s,d));
        }            
        // print();
        for(int i=0; i<k; i++){
            move();
            // print();
            synthesis();
        }
        printAnswer();
    }
    public static void move(){
        tmp = new ArrayList[n][n];
        for(int i=0; i<n; i++){
            for(int j = 0; j<n; j++){
                tmp[i][j]= new ArrayList<>();
            }
        }
        for(int i=0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(map[i][j].size()==0) continue;
                for(Atom at : map[i][j]){
                    int curX = i;
                    int curY = j;
                    int nx = curX + (at.speed * dx[at.dir]);
                    int ny = curY + (at.speed * dy[at.dir]);

                    // System.out.println(i+" "+ j +" "+ at.speed +" "+ at.dir + "  "+ nx + " " + ny);

                    if(nx>=n) nx = nx%n;
                    else if(nx<0) nx = n + (nx%n);
                    if(ny>=n) ny = ny%n;
                    else if(ny<0) ny= n + (ny%n);
                    tmp[nx][ny].add(at);
                }
            }
        }
        map = tmp;
    }
    public static void synthesis(){
        for(int i=0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(map[i][j].size() <2) continue;
                int cnt = map[i][j].size();
                boolean hasDiagonal= false;
                boolean hasVertical = false;
                int speedSum =0;
                int weightSum =0;

                for(Atom at : map[i][j]){
                    speedSum += at.speed;
                    weightSum += at.weight;
                    if(at.dir == 0 || at.dir ==2 || at.dir == 4 || at.dir == 6){
                        hasVertical = true;
                    }
                    else{
                        hasDiagonal = true;
                    }
                }
                map[i][j].clear();
                int nextWeight = weightSum /5;
                int nextSpeed = speedSum/cnt;
                if(nextWeight <=0) continue;
                if(hasVertical && hasDiagonal){
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 1));
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 3));
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 5));
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 7));
                }
                else {
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 0));
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 2));
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 4));
                    map[i][j].add(new Atom(nextWeight, nextSpeed, 6));
                }
            }
        }
    }

    public static void printAnswer(){
        int answer = 0;
        for(int i=0; i<n; i++){
            for(int j = 0; j<n; j++){
                for(Atom at : map[i][j]){
                    answer+=at.weight;
                }
                
            }
        }
        System.out.println(answer);
    }
    public static void print(){
        for(int i=0; i<n; i++){
            for(int j = 0; j<n; j++){
                System.out.print(map[i][j].size()+ " ");
                
            }
            System.out.println();
        }
        System.out.println();
    }
    


}