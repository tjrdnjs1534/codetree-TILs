import java.io.*;
import java.util.*;

public class Main {
	static int[][] green = new int[6][4];
	static int[][] blue= new int[6][4];
	static int n;
	static int ans;
	static int cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			int t= Integer.parseInt(st.nextToken());
			int x= Integer.parseInt(st.nextToken());
			int y= Integer.parseInt(st.nextToken());
			stack(t,x,y,green);
			if(t==2) t=3;
			else if(t==3) t=2;
			stack(t,y,x ,blue);
			pop(green);
			pop(blue);
			eraseRow(green);
			eraseRow(blue);
//			stackGreen(t,x,y);
//			stackBlue(t,x,y);
//			popGreen();
		}
		count(green);
		count(blue);
		System.out.println(ans);
		System.out.println(cnt);

	}

	public static void stack(int type, int x, int y, int[][]map) {
		int nx = 0;
		int ny = y;
		if(type==1) {
			while(nx <6 && map[nx][ny] ==0) {
				nx++;
			}
			nx--;
			map[nx][ny] = 1;
			return;
		}
		else if(type==2) {
			while(nx <6 && map[nx][ny] == 0 && map[nx][ny+1] ==0 ) {
				nx++;
			}
			nx--;
			map[nx][ny] = 1;
			map[nx][ny+1] = 1;
		}
		else if(type==3) {
			while(nx <6 && map[nx][ny] == 0 ) {
				nx++;
			}
			nx--;
			map[nx][ny]=1;
			map[nx-1][ny] =1;
		}
	}
	public static void pop(int[][]map) {
		ArrayList<Integer> popRow= new ArrayList<>();
		for(int i=0; i<6; i++) {
			int numOne=0;
			for(int j=0; j<4; j++) {
				if(map[i][j] ==1) numOne++;
			}
			if(numOne==4) {
				map[i] = new int[] {0,0,0,0};
				popRow.add(i);
				ans++;
			}
		}
		for(int r:popRow) {
			for(int i=r; i>0; i--) {
				int[] tmp = map[i].clone();
				map[i] = map[i-1].clone();
				map[i-1] = tmp;
			}
		}
	}
	public static void eraseRow(int [][]map) {
		int size =0;
		for(int i=0; i<2; i++) {
			boolean hasBlock=false;
			for(int j=0; j<4; j++) {
				if(map[i][j]==1) {
					hasBlock=true;
					break;
				}
			}
			if(hasBlock) size++;
		}
		int row = 6-size;
		ArrayList<Integer> popRow= new ArrayList<>();
		for(int i=0; i<size; i++) {
			map[row]  = new int[] {0,0,0,0};
			popRow.add(row);
			row++;
		}
		for(int r:popRow) {
			for(int i=r; i>0; i--) {
				int[] tmp = map[i].clone();
				map[i] = map[i-1].clone();
				map[i-1] = tmp;
			}
		}
	}
	public static void count(int [][]map) {
		for(int i=0; i<6;i++) {
			for(int j=0; j<4; j++) {
				if(map[i][j]==1) cnt++;
			}
		}
	}
}