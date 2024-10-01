import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    static int MAX = Integer.MIN_VALUE;
    static int MIN = Integer.MAX_VALUE;
    static int N; // 숫자 개수
    static int[] arr; // 숫자 배열
    static int[] op = new int[4]; // 연산자 배열
    public static void main(String[] args) throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        // 숫자 갯수 입력
        N = Integer.parseInt(st.nextToken());

        // 숫자 배열 입력
        arr = new int[N];
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 연산자 갯수 입력
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < 3; i++) {
            op[i] = Integer.parseInt(st.nextToken());
        }

        backTracking(arr[0], 1);

        //최대값 최소값 출력
        bw.write(MIN + " " + MAX);

        bw.flush();
        bw.close();
        br.close();
    }

    // 로직
    public static void backTracking(int num, int idx) {

        // idx == N 이 되면 종료, arr 끝까지 탐색했다는 의미
        if (idx == N) {
            MAX = Math.max(MAX, num);
            MIN = Math.min(MIN, num);
            return;
        }

        for (int i = 0; i < 3; i++) {
            // 해당 연산자가 존재하면, 해당연산자 사용한 후 개수 하나 제거
            if (op[i] > 0) {
                op[i]--;
                switch (i) {
                    case 0:	backTracking(num + arr[idx], idx + 1);	break;
                    case 1:	backTracking(num - arr[idx], idx + 1);	break;
                    case 2:	backTracking(num * arr[idx], idx + 1);	break;
                }
                // 재귀호출이 종료되면 다시 해당 연산자 개수를 복구한다.
                op[i]++;
            }
        }
    }
}