import java.util.*;
import java.io.*;

public class Main {
    static int Q;
    static StringTokenizer st;
    static int start = 0;
    static int n;
    static int m;
    static HashMap<Integer, Product> products = new HashMap<>();
    static ArrayList<ArrayList<Node>> graph = new ArrayList<>();
    static int[] dist;
    static StringBuilder sb = new StringBuilder();

    static class Node{
		int to;
		int distance;
		public Node(int to, int distance) {
			this.to = to;
			this.distance = distance;
		}
	}
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
                for(int v=0;v<n; v++) {
                    graph.add(new ArrayList<>());
                }
                for(int k = 0; k<m; k++){
                    int v = Integer.parseInt(st.nextToken());
                    int u = Integer.parseInt(st.nextToken());
                    int w = Integer.parseInt(st.nextToken());
    			    graph.get(u).add(new Node(v,w));
                    graph.get(v).add(new Node(u,w));
                }
                // ploid();
                dijk();
            }
            else if(op==200) {
                int pid = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                products.put(pid, new Product(r,d));
            }
            else if(op==300){
                int pid = Integer.parseInt(st.nextToken());
                if(!products.containsKey(pid)) continue;
                products.remove(pid);
            }
            else if(op==400) {
                int ans = find();
                // print();
                // System.out.println();
                sb.append(ans);
                sb.append("\n");
            }
            else if(op==500){
                int s =Integer.parseInt(st.nextToken());
                start = s;
                dijk();
            }
        }
        
        System.out.print(sb.toString());
    }
    
    
    public static int find(){
        int ans = -1;
        int mx = -1;
        for(int key: products.keySet()){
            int pid = key;
            Product p = products.get(key);
            int r = p.revenue;
            int d = p.dest;
            int ct= dist[d];
            if(ct==Integer.MAX_VALUE) continue;
            if(r-ct<0) continue;
            if(mx< r-ct) {
                mx = r-ct;
                ans = pid;
            }
            if(mx==r-ct){
                ans = Math.min(ans,pid);
            }
        }
        if(ans!=-1) {
            products.remove(ans);
        }
        return ans;
    }
    public static void dijk(){
        dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start]=0;

    	PriorityQueue<Node> pq = new PriorityQueue<>((a,b)->a.distance-b.distance);
		pq.add(new Node(start,0));

		while(!pq.isEmpty()){
			Node cur = pq.poll();
			for(Node node : graph.get(cur.to)) {
				if(dist[node.to] > dist[cur.to] + node.distance) {
					dist[node.to] = dist[cur.to] + node.distance;
					pq.add(new Node(node.to, dist[node.to]));
				}
			}
		}
    }

}