import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class Main {
    static int[] dx = {0,-1,0,1};
    static int[] dy = {1,0,-1,0};
    private static final int LENGTH = 101;
    private static boolean[][] map = new boolean[LENGTH][LENGTH];
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 커브의 개수
 
        while (N-- > 0) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int d = sc.nextInt(); // 시작 방향
            int g = sc.nextInt(); // 세대
 
            draw(x, y, getDirections(d, g));
            // print();

        }
        System.out.println(getNumberOfSquares());
    }
 
    public static List<Integer> getDirections(int d, int g) {
        List<Integer> directions = new ArrayList<>();
        directions.add(d);
 
        while (g-- > 0) {
            for (int i = directions.size() - 1; i >= 0; i--) {
                int direction = (directions.get(i) + 1) % 4;
                directions.add(direction);
            }
        }
        return directions;
    }
 
    public static void draw(int x, int y, List<Integer> directions) {
        map[x][y] = true;
 
        for (int direction : directions) {
            int nx = x + dx[direction];
            int ny = y + dy[direction];
            map[nx][ny] = true;
            x = nx;
            y = ny;
        }
    }
 
    private static int getNumberOfSquares() {
        int count = 0;
 
        for (int x = 0; x < LENGTH-1; x++) {
            for (int y = 0; y < LENGTH-1; y++) {
                if (map[x][y] && map[x + 1][y] && map[x][y + 1] && map[x + 1][y + 1])
                    count++;
            }
        }
 
        return count;
    }
    public static void print(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                int num =map[i][j] ? 1:0;
                System.out.print( num+ " ");
            }
            System.out.println();
        }            System.out.println();

    }
}