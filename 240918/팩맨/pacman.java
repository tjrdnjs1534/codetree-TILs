import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static class Pacman {
		int x, y;

		public Pacman(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Pacman [x=" + x + ", y=" + y + "]";
		}
		
	}	
	
	static ArrayList<Integer>[][] map;	// 몬스터 저장할 배열
	static ArrayList<Integer>[][] copyMap;	// 복제된 몬스터 저장할 배열
	static int[][] isDead;	// 어느 턴에서 죽었는지 저장할 배열
	static Pacman pacman;
	static int max;	// 팩맨 경로 구할 때 사용
	static int[] route;	// 팩맨 경로 저장
	static boolean[][] visited;	// 방문 체크 배열
	
	// 몬스터 이동 방향
	static int[] mx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] my = {0, -1, -1, -1, 0, 1, 1, 1};
	// 팩맨 이동 방향
	static int[] dx = {-1, 0, 1, 0};	// 상, 좌, 하, 우
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 몬스터 마리 수와 턴 수
		int m = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());
		
		// 팩맨 위치 초기화
		st = new StringTokenizer(br.readLine());
		pacman = new Pacman(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
		
		// map, copyMap, isDead 초기화
		map = new ArrayList[4][4];
		copyMap = new ArrayList[4][4];
		isDead = new int[4][4];
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				map[i][j] = new ArrayList<>();
				copyMap[i][j] = new ArrayList<>();
			}
		}
		
		// 몬스터 위치와 방향을 입력받아서 map에 저장
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int dir = Integer.parseInt(st.nextToken())-1;
			
			map[x][y].add(dir);
		}
		
		for(int turn=1; turn<=t; turn++) {
			// 1. 몬스터 복제
			copyMonster();
			
			// 2. 몬스터 이동
			moveMonster();
			
			// 3. 팩맨 이동
			max = Integer.MIN_VALUE;
			route = new int[] {-1, -1, -1};
			visited = new boolean[4][4];
			
			// 팩맨이 움직일 경로 구하기(dfs)
			findPacmanRoute(0, pacman.x, pacman.y, 0, new int[] {-1, -1, -1});
			// 구한 경로대로 움직이면서 몬스터 먹기
			eatMonster(turn);
			
			// 4. 몬스터 시체 소멸
			removeDeadMonster(turn);
			
			// 5. 복제 완성
			completeCopyMonster();
		}
		
		// 남은 몬스터 구하기
		int sum = 0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				sum += map[i][j].size();
			}
		}
		System.out.println(sum);
	}
	
	public static void copyMonster() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				for(int dir : map[i][j]) 
					copyMap[i][j].add(dir);
			}
		}
	}
	
	public static void moveMonster() {
		ArrayList<Integer>[][] newMap = new ArrayList[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				newMap[i][j] = new ArrayList<>();
			}
		}
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				for(Integer dir : map[i][j]) {
					boolean flag = false;
					for(int d=0; d<8; d++) {
						int nx = i + mx[(dir+d)%8];
						int ny = j + my[(dir+d)%8];
						
						// 범위를 벗어날 경우
						if(!isRange(nx, ny)) continue;
						
						// 2. 시체가 있는 경우
						if(isDead[nx][ny] > 0) continue;
						
						// 3. 팩맨이 있는 경우
						if(nx == pacman.x && ny == pacman.y) continue;
						
						flag = true;
						newMap[nx][ny].add((dir+d)%8);
						break;
					}
					
					// 만약 이동하지 못했으면 원래 위치에 그대로 저장
					if(!flag) newMap[i][j].add(dir);
				}
			}
		}
		
		// 이동 후 map을 얕은 복사로 저장
		map = newMap;
	}
	
	public static void findPacmanRoute(int cur, int x, int y, int eat, int[] dir) {
		// 종료조건
		if(cur == 3) {
			if(eat > max) {
				max = eat;
				for(int i=0; i<3; i++) {
					route[i] = dir[i];
				}
			}
			return;
		}
		
		for(int i=0; i<4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			// 범위를 벗어난 곳은 무시
//			if(!isRange(nx, ny) || visited[nx][ny]) continue;
			if(!isRange(nx, ny)) continue;
			
			if(visited[nx][ny]) {	// 이미 방문했던 곳이면 몬스터 먹었기때문에 eat에 따로 더해주지 않는다.
				dir[cur] = i;
				findPacmanRoute(cur+1, nx, ny, eat, dir);
				dir[cur] = -1;
			} else {
				visited[nx][ny] = true;
				dir[cur] = i;
				findPacmanRoute(cur+1, nx, ny, eat+map[nx][ny].size(), dir);
				visited[nx][ny] = false;
				dir[cur] = -1;
			}
			
		}
	}
	
	public static void eatMonster(int turn) {
		for(int i=0; i<3; i++) {
			// 팩맨 한칸 이동
			pacman.x += dx[route[i]];
			pacman.y += dy[route[i]];
			
			// 만약 옮긴 곳에 몬스터가 있다면
			if(!map[pacman.x][pacman.y].isEmpty()) {
				isDead[pacman.x][pacman.y] = turn;
				map[pacman.x][pacman.y].clear();
			}
		}
	}
	
	public static void removeDeadMonster(int turn) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(isDead[i][j] > 0 && isDead[i][j]+2 == turn) isDead[i][j] = 0;
			}
		}
	}
	
	public static void completeCopyMonster() {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				for(int dir : copyMap[i][j]) {
					map[i][j].add(dir);
				}
				copyMap[i][j].clear();
			}
		}
	}
	
	public static boolean isRange(int x, int y) {
		return x>=0 && x<4 && y>=0 && y<4;
	}
	
}