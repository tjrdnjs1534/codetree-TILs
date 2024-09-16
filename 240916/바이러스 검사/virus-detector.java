import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력 처리
		int n = Integer.parseInt(br.readLine());
		int[] customer = new int[n];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			customer[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		int ldr = Integer.parseInt(st.nextToken());
		int mbr = Integer.parseInt(st.nextToken());

		long minCount = 0L;
		for (int i = 0; i < n; i++) {
			minCount++;
			if (customer[i] - ldr > 0) {
				minCount += (customer[i] - ldr) % mbr == 0 ? (customer[i] - ldr) / mbr : (customer[i] - ldr) / mbr + 1;
			}
		}

		// 결과 출력
		System.out.println(minCount);
	}
}