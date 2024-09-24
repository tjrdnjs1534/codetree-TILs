import java.io.*;
import java.util.*;

public class Main {
	static StringTokenizer st;
	static int n,m;
    static int[][] map;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static ArrayList<Piece> pieces = new ArrayList<>();
    static ArrayList<int[]> another = new ArrayList<>();
    static int ans =Integer.MAX_VALUE;
    public static class Piece{
        int x;
        int y;
        int type;
        int dir;
        Piece(int x, int y, int type){
            this.x =x;
            this.y =y;
            this.type = type;
            this.dir = 0;
        }
        Piece(int x, int y, int type, int dir){
            this.x =x;
            this.y =y;
            this.type= type;
            this.dir = dir;
        }
    }
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
        
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
        map=new int[n][m];
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<m; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] !=0 && map[i][j]!=6){
                    pieces.add(new Piece(i,j,map[i][j]));
                }
                if(map[i][j]==6){
                    another.add(new int[]{i,j});
                }
            }
		}
        solution(new ArrayList<>(),0);
        System.out.println(ans);

	}
    public static void solution(ArrayList<Piece> selects, int start){
        if(selects.size() == pieces.size()){
            calc(selects);
            return; 
        }
        for(int i=start; i<pieces.size(); i++){
            Piece p = pieces.get(i);
            for(int d =0; d<4; d++){
                selects.add(new Piece(p.x, p.y, p.type, d));
                solution(selects, start+1 );
                selects.remove(selects.size()-1);
            }
        }
    }
    public static void calc(ArrayList<Piece> selects){
        boolean[][] canGo = new boolean[n][m];

        for(Piece p : selects){
            int type = p.type;
            int x = p.x;
            int y = p.y;
            int d = p.dir;
            canGo[x][y] = true;
            switch(type){
                case 1:
                    move(x, y, d, canGo);
                    break;
                case 2:
                    move(x,y, (d+1) %4, canGo);
                    move(x,y, (d+3) %4, canGo);
                    break;
                case 3:
                    move(x,y,d, canGo);
                    move(x,y, (d+1) %4, canGo);
                    break;
                case 4:
                    move(x,y,d, canGo);
                    move(x,y, (d+1) %4, canGo);
                    move(x,y, (d+3) %4, canGo);
                    break;
                case 5:
                    move(x,y,d, canGo);
                    move(x,y, (d+1) %4, canGo);
                    move(x,y, (d+2) %4, canGo);
                    move(x,y, (d+3) %4, canGo);
                    break;
            }
        } 
        for(int[] lo : another){
            int l1 = lo[0];
            int l2 = lo[1];
            canGo[l1][l2] = true;
        }
        int cnt = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(canGo[i][j]==false){
                    cnt++;
                }
            }
        } 
        ans = Math.min(ans,cnt);
    }
    public static boolean isOut(int x, int y){
        if(x<0 || x>= n || y<0 || y>=m) return true;
        return false;
    }
    public static void move(int x, int y, int d, boolean[][]canGo){
        while(true){
            int nx = x+dx[d];
            int ny = y+dy[d];
            if(isOut(nx,ny)) break;
            if(map[nx][ny] ==6) {
            canGo[nx][ny]= true;
            break;
            }
            canGo[nx][ny] = true;
            x=nx;
            y=ny;
        }
        
    }
}