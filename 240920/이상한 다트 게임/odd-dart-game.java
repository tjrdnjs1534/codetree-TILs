import java.util.*;
import java.io.*;


public class Main {
    static int n,m,q;
    static ArrayList<Integer>[] circles;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        circles = new ArrayList[n+1];

        for(int i=1; i<=n; i++){
            circles[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<m; j++){
                int num = Integer.parseInt(st.nextToken());
                circles[i].add(num);
            }
        }
        for(int i=0; i<q; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            // 0 clock, 1 counterClock
            int d = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            rotatesCircles(x,d,k);
            boolean deleted = erase();
            if(!deleted) {
                nomalize();
            }
            // print();
        }
        printAns();
        
    }
    public static void rotatesCircles(int x, int d, int k){
        for(int i=1; i<=n; i++){
            if(i%x==0) rotate(circles[i], d, k);
        }
    }
    public static void rotate(ArrayList<Integer> arr, int d, int k){
        if(d==0){
            for(int i=0; i<k; i++){
                int tmp = arr.get(arr.size()-1);
                arr.remove(arr.size()-1);
                arr.add(0,tmp);
            }
        }
        else {
            for(int i=0; i<k; i++){
                int tmp = arr.get(0);
                arr.remove(0);
                arr.add(tmp);
            }
        }
    }
    public static boolean erase(){
        boolean flag = false;
        ArrayList<Integer>[] tmp = new ArrayList[n+1];
        for(int i=1; i<=n; i++){
            tmp[i]= new ArrayList<>();
            for(int j=0; j<n; j++){
                tmp[i].add(circles[i].get(j));
            }
        }

        for(int i=1; i<=n; i++){
            ArrayList<Integer> now = circles[i];
            for(int j=0; j<m; j++){
                int num = now.get(j);
                int left = j-1 < 0 ?  m-1 : j-1;
                int right = j+1 > m-1 ? 0 : j+1;

                int leftNum = now.get(left);
                int rightNum = now.get(right);
                if(leftNum == num || rightNum == num){
                    tmp[i].set(j, -1);
                    flag = true;
                    continue;
                }

            
                int next = i+1;
                boolean changed= false;
                while(next<n){
                    ArrayList<Integer> nextD = circles[next];
                    if(nextD.get(j) == num){
                        tmp[next].set(j, -1);
                        flag = true;
                        changed = true;
                    }
                    else{
                        break;
                    }
                    next++;
                    
                }
                if(changed){
                    tmp[i].set(j,-1);
                }
            }
        }    
        for(int i=1; i<=n; i++){
            circles[i] = new ArrayList(tmp[i]);
        }
        return flag;
    }

    public static void print(){
        for(int i=1; i<=n; i++){
            ArrayList<Integer> now = circles[i];
            for(int j=0; j<m; j++){
                int num = now.get(j);
                System.out.print(num + " ");
            }
            System.out.println();
        }    
    }
    public static void nomalize(){
        int cnt = 0;
        int sum = 0;
        for(int i=1; i<=n; i++){
            for(int j=0; j<m; j++){
                int num = circles[i].get(j);
                if(num==-1)continue;
                cnt++;
                sum+=num;
            }
        }
        if(cnt==0) return;
        int avg = sum / cnt;

        for(int i=1; i<=n; i++){
            for(int j=0; j<m; j++){
                int num = circles[i].get(j);
                if(num==-1)continue;
                if(num< avg) {
                    circles[i].set(j,num+1);
                }
                else if(num>avg){
                    circles[i].set(j,num-1);
                }
            }
        }
    }
    public static void printAns(){
        int sum = 0;
        for(int i=1; i<=n; i++){
            for(int j=0; j<m; j++){
                int num = circles[i].get(j);
                if(num==-1)continue;
                sum+=num;
            }
        }
        System.out.println(sum);
        
    }
}