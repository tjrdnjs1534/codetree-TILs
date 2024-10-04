import java.io.*;
import java.util.*;

public class Main {
    static int N, Q;
    static int[] parents;
    static List<Integer>[] children;
    static int[] auth;
    static int[][] surplusAuth;
    static int[] alarms;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        parents = new int[N + 1];
        children = new ArrayList[N + 1];
        auth = new int[N + 1];
        surplusAuth = new int[N + 1][21];
        alarms = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            children[i] = new ArrayList<>();
        }

        Arrays.fill(alarms, 1); // 초기 알람 상태는 모두 켜져있음

        st = new StringTokenizer(br.readLine());
        int num = Integer.parseInt(st.nextToken());
        for (int i = 1; i <= N; i++) {
            parents[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 1; i <= N; i++) {
            auth[i] = Math.min(20, Integer.parseInt(st.nextToken()));
        }
        for (int i = 1; i <= N; i++) {
            children[parents[i]].add(i);
            int power = auth[i];
            int idx = i;
            while (power > -1) {
                surplusAuth[idx][power]++;
                power--;
                if (idx == parents[idx]) break;
                idx = parents[idx];
            }
        }

        // 명령어 처리
        for (int i = 0; i < Q - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int query = Integer.parseInt(st.nextToken());

            if (query == 200) {
                int c = Integer.parseInt(st.nextToken());
                changeAlarm(c);
            } else if (query == 300) {
                int c = Integer.parseInt(st.nextToken());
                int power = Integer.parseInt(st.nextToken());
                changePower(c, power);
            } else if (query == 400) {
                int c1 = Integer.parseInt(st.nextToken());
                int c2 = Integer.parseInt(st.nextToken());
                changeParents(c1, c2);
            } else if (query == 500) {
                int c = Integer.parseInt(st.nextToken());
                check(c);
            }
        }
    }

   
    // 알림망 상태 변경
    static void changeAlarm(int c) {
        alarms[c] = 1 - alarms[c]; // 알림망 상태 반전
        update(parents[c]);
    }

    // 권한 세기 변경
    static void changePower(int c, int power) {
        auth[c] = Math.min(20, power);
        update(c);
    }

    // 부모 교환
    static void changeParents(int c1, int c2) {
        int p1 = parents[c1];
        int p2 = parents[c2];
        if (p1 == p2) return;

        parents[c1] = p2;
        parents[c2] = p1;

        children[p1].remove((Integer) c1);
        children[p1].add(c2);
        children[p2].remove((Integer) c2);
        children[p2].add(c1);

        update(p1);
        update(p2);
    }

    // 알림 수 조회
    static void check(int c) {
        int sum = 0;
        for (int i = 0; i <= 20; i++) {
            sum += surplusAuth[c][i];
        }
        System.out.println(sum - 1); // c번 채팅방을 제외한 알림을 받을 수 있는 채팅방 수 출력
    }

    // 트리 구조 업데이트
    static void update(int idx) {
        surplusAuth[idx] = new int[21];
        surplusAuth[idx][auth[idx]]++;
        for (int child : children[idx]) {
            if (alarms[child] == 0) continue; // 알림망이 꺼져 있으면 전파하지 않음
            for (int i = 0; i < 20; i++) {
                surplusAuth[idx][i] += surplusAuth[child][i + 1];
            }
        }
        if (idx != parents[idx]) {
            update(parents[idx]);
        }
    }
}