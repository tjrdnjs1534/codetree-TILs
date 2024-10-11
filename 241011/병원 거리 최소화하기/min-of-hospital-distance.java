import java.util.*;

public class Main {
    static int n, m;
    static List<int[]> hospitals = new ArrayList<>();
    static List<int[]> people = new ArrayList<>();
    static int minDistanceSum = Integer.MAX_VALUE;

    // 병원 조합을 만들기 위한 재귀 함수
    public static void selectHospitals(int start, List<int[]> selectedHospitals) {
        if (selectedHospitals.size() == m) {
            // 선택된 병원들에 대해 거리 계산
            calculateMinDistance(selectedHospitals);
            return;
        }

        for (int i = start; i < hospitals.size(); i++) {
            selectedHospitals.add(hospitals.get(i));
            selectHospitals(i + 1, selectedHospitals);
            selectedHospitals.remove(selectedHospitals.size() - 1);
        }
    }

    // 선택된 병원에 대해 사람들의 병원 거리 총합 계산
    public static void calculateMinDistance(List<int[]> selectedHospitals) {
        int totalDistance = 0;

        for (int[] person : people) {
            int minDistance = Integer.MAX_VALUE;

            for (int[] hospital : selectedHospitals) {
                int distance = Math.abs(person[0] - hospital[0]) + Math.abs(person[1] - hospital[1]);
                minDistance = Math.min(minDistance, distance);
            }

            totalDistance += minDistance;
        }

        minDistanceSum = Math.min(minDistanceSum, totalDistance);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        // 도시 정보를 입력 받기
        int[][] city = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                city[i][j] = sc.nextInt();
                if (city[i][j] == 1) {
                    people.add(new int[]{i, j});  // 사람 위치 저장
                } else if (city[i][j] == 2) {
                    hospitals.add(new int[]{i, j});  // 병원 위치 저장
                }
            }
        }

        // 병원 선택을 위한 조합 생성
        selectHospitals(0, new ArrayList<>());
        // 최소 거리의 합 출력
        System.out.println(minDistanceSum);
    }
}