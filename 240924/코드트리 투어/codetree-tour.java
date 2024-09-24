import java.util.*;
import java.io.*;

public class Main {
    static int Q;
    static StringTokenizer st;
    static int start = 0;
    static int n;
    static int m;
    static HashMap<Integer, Product> products = new HashMap<>();
    static int[][] cost;
    static int[][] nodes;
    static StringBuilder sb = new StringBuilder();
    public static class Product{
        int revenue;
        int dest;
        Product(int r, int d){
            this.revenue = r;
            this.dest= d;
        }
    }

    

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());

        for(int i=0; i<Q; i++){
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            if(op ==100){
                n = Integer.parseInt(st.nextToken());
                m = Integer.parseInt(st.nextToken());
                nodes = new int[n][n];
                cost = new int[n][n];
                for(int r=0; r<n; r++){
                    for(int c=0; c<n; c++){
                        if(r==c)continue;
                        cost[r][c] = 100000;
                    }
                }

                for(int k = 0; k<m; k++){
                    int v = Integer.parseInt(st.nextToken());
                    int u = Integer.parseInt(st.nextToken());
                    int w = Integer.parseInt(st.nextToken());
                    if(nodes[v][u] == 0){
                        nodes[v][u] = w;
                        nodes[u][v] = w;
                        cost[v][u] = w;
                        cost[u][v] = w;
                    }
                    else {
                        nodes[v][u] = Math.min(nodes[v][u],w);
                        nodes[u][v] = Math.min(nodes[u][v],w);
                        cost[v][u] = nodes[v][u];
                        cost[u][v] = nodes[u][v];
                    }
                }
                ploid();
            }
            else if(op==200) {
                int pid = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                products.put(pid, new Product(r,d));
            }
            else if(op==300){
                int pid = Integer.parseInt(st.nextToken());
                products.remove(pid);
            }
            else if(op==400) {
                int ans = find();
                sb.append(ans);
                sb.append("\n");
            }
            else if(op==500){
                int s =Integer.parseInt(st.nextToken());
                start = s;
            }
        }
        
        System.out.print(sb.toString());
    }
    public static void ploid() {
        for(int k =0; k<n; k++){
            for(int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    cost[i][j] = Math.min(cost[i][j], cost[i][k] + cost[k][j]);
                }
            }
        }
    }
    public static void print(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                System.out.printf("%5d ", cost[i][j]);
            }
            System.out.println();
        }
    }
    public static int find(){
        int ans = -1;
        int mx = -1;
        for(int key: products.keySet()){
            int pid = key;
            Product p = products.get(key);
            int r = p.revenue;
            int d = p.dest;
            int ct= cost[start][d];
            if(ct==100000) continue;
            if(r-ct<0) continue;

            if(mx< r-ct) {
                mx = r-ct;
                ans = pid;
            }
        }
        if(ans!=-1) {
            products.remove(ans);
        }
        return ans;
    }

}