import java.io.*;
import java.util.*;

public class Main {
    static int[][] seats;
    static int[][] tmp;
    private static final int LENGTH = 8;
    static int k;
    static boolean visited[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        seats = new int[4][LENGTH];
        tmp =new int[4][LENGTH];
        for(int i=0; i<4; i++){
            String s = br.readLine();
            char[] ch = s.toCharArray();
            for(int j=0; j<LENGTH; j++){
                seats[i][j]= Character.getNumericValue(ch[j]);
                tmp[i][j] = seats[i][j];
            }
        }
        k = Integer.parseInt(br.readLine());
        for(int i=0; i<k; i++){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken());
            visited = new boolean[4];
            rotate(n,d);
            init();
            tmp = new int[4][LENGTH];
        }
        int s1 = seats[0][0];
        int s2 = seats[1][0];
        int s3 = seats[2][0];
        int s4 = seats[3][0];
        System.out.println(s1+2*s2+4*s3+8*s4);

    }
    public static void init(){
        for(int i=0; i<4; i++){
            for(int j=0; j<LENGTH; j++){
                seats[i][j]= tmp[i][j];
            }
        }
    }
    public static void rotate(int n, int d){
        int left = n-1;
        int right = n+1;
        visited[n] = true;
        if(left >=0 && !visited[left]) {
            if(seats[n][6] != seats[left][2]){
                rotate(left, -d);
            }
            
        }
        if(right<4 && !visited[right]){
            if(seats[n][2] != seats[right][6]){
                rotate(right, -d);
            }
        }

        if(d==1) {
            roateClock(tmp[n]);
        }
        else {
            rotateCounterClock(tmp[n]);
        }

    }

    public static void rotateCounterClock(int[]arr){
        int start = arr[0];
        for(int i=1; i<LENGTH; i++){
            arr[i-1] = arr[i];
        }
        arr[LENGTH-1]= start;
    }
    public static void roateClock(int[]arr){
        int last = arr[LENGTH-1];
        for(int i=LENGTH-1; i>0; i--){
            arr[i] = arr[i-1];
        }
        arr[0]= last;
    }
    public static void print(){
        for(int i=0; i<4; i++){
            for(int j=0; j<LENGTH; j++){
                System.out.print(seats[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}