import java.util.*;

public class Main {
    static int N, M, K;
    static int[][] arr;
    static int[][] v;
    static Map<Integer, Deque<int[]>> teams = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        
        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        v = new int[N][N];
        int team_n = 5;

        // BFS로 팀을 찾고 팀별로 구분하여 저장
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (v[i][j] == 0 && arr[i][j] == 1) {
                    bfs(i, j, team_n);
                    team_n++;
                }
            }
        }

        int[] di = {0, -1, 0, 1};
        int[] dj = {1, 0, -1, 0};
        int ans = 0;

        // K 라운드 진행
        for (int k = 0; k < K; k++) {
            // [1] 팀별로 머리 이동
            for (Deque<int[]> team : teams.values()) {
                int[] tail = team.pollLast(); // 꼬리 좌표 삭제
                arr[tail[0]][tail[1]] = 4;   // 이동선으로 복원
                int[] head = team.peekFirst(); // 머리 좌표
                // 네 방향 탐색
                for (int d = 0; d < 4; d++) {
                    int ni = head[0] + di[d];
                    int nj = head[1] + dj[d];
                    if (0 <= ni && ni < N && 0 <= nj && nj < N && arr[ni][nj] == 4) {
                        team.addFirst(new int[]{ni, nj});
                        arr[ni][nj] = arr[head[0]][head[1]]; // 새 머리 좌표에 팀 번호 표시
                        break;
                    }
                }
            }

            // [2] 라운드 번호에 맞는 방향과 시작 위치 계산
            int dr = (k / N) % 4;
            int offset = k % N;
            int curX, curY;
            if (dr == 0) {       
                curX = offset;
                curY = 0;
            } else if (dr == 1) { 
                curX = N - 1;
                curY = offset;
            } else if (dr == 2) { 
                curX = N - 1 - offset;
                curY = N - 1;
            } else {            
                curX = 0;
                curY = N - 1 - offset;
            }

            // [3] 공을 받은 사람 점수 추가 및 방향 반전
            for (int step = 0; step < N; step++) {
                if (0 <= curX && curX < N && 0 <= curY && curY < N && arr[curX][curY] > 4) {
                    team_n = arr[curX][curY];
                    int idx = 0;
                    for(int[] ls :teams.get(team_n)) {
                    	idx++;
                    	if(ls[0] == curX && curY==ls[0]) {
                    		break;
                    	}
                    }
                    ans += Math.pow(idx, 2);
                    // 팀 방향 반전
                    reverseDeque(teams.get(team_n));
                    break;
                }
                curX += di[dr];
                curY += dj[dr];
            }
        }

        System.out.println(ans);
        sc.close();
    }

    // BFS 함수
    static void bfs(int si, int sj, int team_n) {
        Queue<int[]> q = new LinkedList<>();
        Deque<int[]> team = new LinkedList<>();
        
        q.add(new int[]{si, sj});
        v[si][sj] = 1;
        team.add(new int[]{si, sj});
        arr[si][sj] = team_n;
        
        int[] di = {-1, 1, 0, 0};
        int[] dj = {0, 0, -1, 1};
        
        while (!q.isEmpty()) {
            int[] current = q.poll();
            int curX = current[0];
            int curY = current[1];
            
            for (int d = 0; d < 4; d++) {
                int ni = curX + di[d];
                int nj = curY + dj[d];
                if (0 <= ni && ni < N && 0 <= nj && nj < N && v[ni][nj] == 0) {
                    if (arr[ni][nj] == 2 || (curX != si || curY != sj) && arr[ni][nj] == 3) {
                        q.add(new int[]{ni, nj});
                        v[ni][nj] = 1;
                        team.add(new int[]{ni, nj});
                        arr[ni][nj] = team_n;
                    }
                }
            }
        }
        teams.put(team_n, team);
    }

    // Deque 반전 함수
    static void reverseDeque(Deque<int[]> deque) {
        List<int[]> list = new ArrayList<>(deque);
        Collections.reverse(list);
        deque.clear();
        deque.addAll(list);
    }
}