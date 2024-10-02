import java.io.*;
import java.util.*;

public class Main {
    static int L, Q;
    static List<Command> commands = new ArrayList<>();
    static Map<String, Integer> sittingPosition = new HashMap<>(); // 손님이 앉은 자리 매핑
    static Set<String> visitingCustomerNames = new HashSet<>(); // 방문한 손님 목록 저장
    static Map<String, Integer> visitCustomerTime = new HashMap<>(); // 손님이 방문한 시간대
    static Map<String, Integer> eatCount = new HashMap<>(); // 손님이 먹어야할 초밥 갯수 매핑
    static List<Command> sushiDisappearCommands = new ArrayList<>(); // 초밥이 사라지는 시점의 명령 저장
    static int sushiCount = 0; // 남은 초밥 갯수
    static int customerCount = 0; // 남은 손님 명수

    static class Command {
        int cmd, x, t, n;
        String name;

        Command(int cmd, int x, int t, int n, String name) {
            this.cmd = cmd;
            this.x = x;
            this.t = t;
            this.n = n;
            this.name = name;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            int t = -1, x = -1, n = -1;
            String name = "";

            if (cmd == 100) {
                t = Integer.parseInt(st.nextToken());
                x = Integer.parseInt(st.nextToken());
                name = st.nextToken();
            } else if (cmd == 200) {
                t = Integer.parseInt(st.nextToken());
                x = Integer.parseInt(st.nextToken());
                name = st.nextToken();
                n = Integer.parseInt(st.nextToken());

                visitingCustomerNames.add(name);
                sittingPosition.put(name, x); // 손님의 앉은 위치 저장
                visitCustomerTime.put(name, t); // 손님 방문 시간 저장
                eatCount.put(name, n); // 먹어야 할 초밥 갯수 저장
            } else if (cmd == 300) {
                t = Integer.parseInt(st.nextToken());
            }

            Command command = new Command(cmd, x, t, n, name);
            commands.add(command);
        }

        solve();
    }

    static void solve() {
        // 초밥 명령 처리 (생성 명령을 기반으로 초밥 사라짐 명령 추가)
        for (Command command : commands) {
            if (command.cmd != 100) continue; // 초밥 생성 명령이 아니면 패스

            int meetSushiCustomerTime = 0; // 손님이 초밥을 먹기 위해 기다리는 시간
            int eatSushiTime = 0; // 초밥을 먹는 시간

            if (command.t < visitCustomerTime.get(command.name)) {
                // 손님 입장 전 초밥이 존재하는 경우
                int nowSushiPosition = command.x;
                int diffT = visitCustomerTime.get(command.name) - command.t;
                nowSushiPosition = (nowSushiPosition + diffT) % L;

                if (sittingPosition.get(command.name) >= nowSushiPosition) {
                    meetSushiCustomerTime = sittingPosition.get(command.name) - nowSushiPosition;
                } else {
                    meetSushiCustomerTime = (sittingPosition.get(command.name) + L) - nowSushiPosition;
                }

                eatSushiTime = visitCustomerTime.get(command.name) + meetSushiCustomerTime;
            } else {
                // 손님이 입장 후 초밥이 들어오는 경우
                int nowSushiPosition = command.x;

                if (sittingPosition.get(command.name) >= nowSushiPosition) {
                    meetSushiCustomerTime = sittingPosition.get(command.name) - nowSushiPosition;
                } else {
                    meetSushiCustomerTime = (sittingPosition.get(command.name) + L) - nowSushiPosition;
                }

                eatSushiTime = command.t + meetSushiCustomerTime;
            }

            // 초밥이 사라지는 시점에 대한 명령 추가
            sushiDisappearCommands.add(new Command(101, -1, eatSushiTime, -1, command.name));
        }

        // 전체 명령 리스트에 초밥 사라짐 명령 추가
        commands.addAll(sushiDisappearCommands);

        // 명령 정렬 (시간 순서대로, 시간이 같으면 명령어 순으로)
        commands.sort((a, b) -> {
            if (a.t != b.t) return Integer.compare(a.t, b.t);
            return Integer.compare(a.cmd, b.cmd);
        });

        // 정렬된 명령 처리
        for (Command command : commands) {
            if (command.cmd == 100) {
                sushiCount++; // 초밥 생성
            } else if (command.cmd == 101) {
                sushiCount--; // 초밥 소멸
                eatCount.put(command.name, eatCount.get(command.name) - 1);
                if (eatCount.get(command.name) <= 0) customerCount--; // 손님이 다 먹으면 줄어듦
            } else if (command.cmd == 200) {
                customerCount++; // 손님 방문
            } else if (command.cmd == 300) {
                // 촬영 명령, 남은 손님 수와 초밥 수 출력
                System.out.println(customerCount + " " + sushiCount);
            }
        }
    }
}