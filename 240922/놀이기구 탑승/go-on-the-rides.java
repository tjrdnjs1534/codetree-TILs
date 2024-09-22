import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static HashMap<Integer,HashSet<Integer>> loves = new HashMap<>();
    static int[][]map;
    static int[] nums;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0, 0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        map= new int[n][n];
        nums = new int[n*n];
        for(int i=0; i<n*n; i++){
            st = new StringTokenizer(br.readLine());
            int n0 =Integer.parseInt(st.nextToken());
            int n1= Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int n3 = Integer.parseInt(st.nextToken());
            int n4 = Integer.parseInt(st.nextToken());
            loves.put(n0, new HashSet<>());
            loves.get(n0).add(n1);
            loves.get(n0).add(n2);
            loves.get(n0).add(n3);
            loves.get(n0).add(n4);
            nums[i]= n0;
        }
        for(int i=0; i<n*n; i++){
            int num = nums[i];
            int[] xy = seat(num);
            int x = xy[0];
            int y = xy[1];
            map[x][y] = num;
        }
        printAns();

    }
    public static int[] seat(int num){
        int likeFriends = -1;
        int emptySeats = -1;
        int x = n;
        int y = n;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(map[i][j]!=0) continue;
                int[] info = infos(i,j,num);
                int l = info[0];
                int e = info[1];
                if(l > likeFriends) {
                    likeFriends = l;
                    emptySeats = e;
                    x = i;
                    y = j;
                }
                else if(l==likeFriends){
                    if(e> emptySeats){
                        emptySeats= e;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        return new int[] {x,y};
    }
    public static int[] infos(int x, int y, int num){
        int emptySeats = 0;
        int likeFriends =0;
        for(int d=0; d<4; d++){
            int nx = x+dx[d];
            int ny = y+dy[d];
            if(nx<0 || nx>=n || ny<0 || ny>=n) continue;
            if(map[nx][ny] ==0 ) {
                emptySeats++;
                continue;
            }
            if(loves.get(num).contains(map[nx][ny])){
                likeFriends++;
            }
        }
        return new int[]{likeFriends, emptySeats};
    }
    public static void print(){
        
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.print(map[i][j]+ " ");
            }
            System.out.println();
        }
    }
    public static void printAns(){
        int ans =0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                ans+=getLikes(i,j,map[i][j]);
            }
        }
        System.out.println(ans);
    }
    public static int getLikes(int x, int y, int num){
        int likeFriends =0;
        for(int d=0; d<4; d++){
            int nx = x+dx[d];
            int ny = y+dy[d];
            if(nx<0 || nx>=n || ny<0 || ny>=n) continue;
            if(loves.get(num).contains(map[nx][ny])){
                likeFriends++;
            }
        }
        int point;
        switch(likeFriends){
            case 0:
            return 0;
            case 1: 
            return 1;
            case 2:
            return 10;
            case 3:
            return 100;
            case 4:
            return 1000;
        }
        return 0;
    }
}