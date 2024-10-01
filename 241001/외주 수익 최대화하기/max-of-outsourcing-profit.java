import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] dp = new int[n + 1]; 
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = sc.nextInt(); // 첫 번째 입력: 작업에 소요되는 시간
            arr[i][1] = sc.nextInt(); // 두 번째 입력: 작업 수행 시 얻는 금액
        }

        for (int i = n - 1; i >= 0; i--) {
            if (i + arr[i][0] <= n) {
                dp[i] = Math.max(dp[i + arr[i][0]] + arr[i][1], dp[i + 1]);
            } else {
                dp[i] = dp[i + 1];
            }
        }
        System.out.println(dp[0]);
        
        sc.close();
    }
}