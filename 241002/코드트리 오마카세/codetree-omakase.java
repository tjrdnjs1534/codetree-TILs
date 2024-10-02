import java.util.*;
import java.io.*;

public class Main {
    static int L,Q;
    static ArrayList<HashMap<String,Integer>> rail = new ArrayList<>();
    static int now = 0;
    static Person[] seats;
    static StringBuilder sb = new StringBuilder();
    public static class Person{
        String name;
        int remain;

        Person(String name, int remain){
            this.name = name;
            this.remain = remain;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        for(int i=0; i<L; i++){
            rail.add(new HashMap<>());
        }
        seats = new Person[L];

        for(int step = 0; step<Q; step++){
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            if(op == 100){
                int t = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());
                String name = st.nextToken();
                rotateRail(t);
                HashMap<String,Integer> cell = rail.get(x);
                cell.put(name, cell.getOrDefault(name, 0)+1);
                eat(x);
            }
            else if(op ==200){
                int t = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());
                String name = st.nextToken();
                int n = Integer.parseInt(st.nextToken());
                rotateRail(t);
                seats[x] = new Person(name, n);
                eat(x);
                // System.out.println(now+"초");
                // print();
            }
            else if(op==300){
                int t = Integer.parseInt(st.nextToken());
                rotateRail(t);
                photo();
            }
        }
        System.out.println(sb.toString());

    }
    public static void rotate(){
        HashMap<String,Integer> last = rail.get(L-1);
        rail.remove(L-1);
        rail.add(0,last);
    }
    public static void print(){
        for(int i=0; i<L; i++){
            System.out.println(i+"에 있는 스시");
            HashMap<String,Integer> cell = rail.get(i);
            cell.forEach((k,v)->{
                System.out.println(k+" " + v);
            });
        }
    }
    public static void eat(int i){
        Person p = seats[i];
            if(seats[i] != null){
                int remain = p.remain;
                int railSushi = rail.get(i).getOrDefault(p.name, 0);
                if(railSushi ==0) return;
                if(railSushi - remain > 0){
                    p.remain = 0;
                    rail.get(i).put(p.name,railSushi - remain);
                    seats[i] = null;
                }
                else if(railSushi == remain){
                    p.remain = 0;
                    rail.get(i).remove(p.name);
                    seats[i] = null;
                }
                else{
                    p.remain -=railSushi;
                    rail.get(i).remove(p.name);
                }
            }
    }
    public static void eatEvery(){
        for(int i=0; i<L; i++){
            eat(i);
        }
    }
    public static void rotateRail(int t){
        for(; now<t; now++){
            rotate();
            eatEvery();
        }
    }
    public static void photo(){
        int sushi = 0;
        int people = 0;
        for(int i=0; i<L; i++){
            if(seats[i]!=null) {
                people++;
                // System.out.println(seats[i].name);
            }
            HashMap<String,Integer> cell = rail.get(i);
            for(int v : cell.values()){
                sushi+=v;
            }
        }
        sb.append(people).append(" ").append(sushi).append("\n");

    }
}