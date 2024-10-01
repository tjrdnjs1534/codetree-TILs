import java.io.*;
import java.util.*;

public class Main {
	static int[] dices = new int[10];
	static int ans = -1;
	static HashMap<Integer,Node> map = new HashMap<>();
	static HashMap<Integer, int[]> special = new HashMap<>();
	static HashMap<Integer, int[]> blue30 = new HashMap<>();
	public static class Node{
		boolean speacial;
		int lo;
		boolean onBlue30;
		Node(boolean special, int lo, boolean onBlue30){
			this.speacial = special;
			this.lo = lo;
			this.onBlue30= onBlue30;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i=0; i<10; i++) {
			dices[i] = Integer.parseInt(st.nextToken());
		}
	
		
		special.put(10, new int[] {0,13,16,19,25,30});
		special.put(13, new int[] {0,16,19,25,30,35});
		special.put(16, new int[] {0,19,25,30,35,40});
		special.put(19, new int[] {0,25,30,35,40,-1});
		
		special.put(20, new int[] {0,22,24,25,30,35});
		special.put(22, new int[] {0,24,25,30,35,40});
		special.put(24, new int[] {0,25,30,35,40,-1});
		special.put(25, new int[] {0,30,35,40,-1,-1});
		
		special.put(26, new int[] {0,25,30,35,40,-1});
		special.put(27, new int[] {0,26,25,30,35,40});
		special.put(28, new int[] {0,27,26,25,30,35});
		
		special.put(35, new int[] {0,40,-1,-1,-1,-1});		
		special.put(30, new int[] {0,35,40,-1,-1,-1});
		
		blue30.put(30, new int[] {0,28,27,26,25,30});
		
		solution(0, new int[10]);
		System.out.print(ans);
	}
	
	
	public static void solution(int depth , int[] plays) {
		if(depth == 10) {
			int score = 0;
			map.put(0, new Node(false,0, false));
			map.put(1, new Node(false,0, false));
			map.put(2, new Node(false,0, false));
			map.put(3, new Node(false,0, false));
			
			for(int i=0; i<10; i++) {
				int d = dices[i];
				int player = plays[i];
				Node nowLocation = map.get(player);
				if(nowLocation.lo == -1) return;
				
				Node nextLocation = move(d, nowLocation);
				
				if(nextLocation.lo ==-1) {
					map.put(player, new Node(false,-1, false));
					continue;
				}
				 
				for(int p = 0; p<4; p++) {
					Node checkNode= map.get(p);
					if(p!=player && nextLocation.lo == checkNode.lo) {
						if(!special.containsKey(nextLocation.lo)) return;
						if(nextLocation.lo == 30) {
							if(checkNode.onBlue30 == nextLocation.onBlue30)return;
						}
						else {
							if(checkNode.speacial == nextLocation.speacial ) return;
						}	
					}
				}
				map.put(player, nextLocation);
				score+= nextLocation.lo;
			}
			
//			System.out.println(score);
			ans = Math.max(ans, score);
			return;
		}
		
		for(int i=0; i<4; i++) {
			plays[depth]  = i;
			solution(depth+1, plays);
		}
	}
	public static Node move(int dice, Node n) {
		int now = n.lo;
		boolean isSpecial = n.speacial;
		boolean onBlue30 = n.onBlue30;
		
		if(onBlue30) {
			return new Node(true, blue30.get(30)[dice], false);
		}
		if(isSpecial && special.containsKey(now)) {
			return new Node(true, special.get(now)[dice],false);
		}
		
		int location =  (now + 2*dice) > 40 ? -1 :  (now + 2*dice);
		if(location ==10 || location ==20) {
			return new Node(true, location, false);
		}
		else if(location == 30) {
			return new Node(false, 30, true);
		}
		return new Node(false,location,false);
	}
	
}
//3 1 1 5 4 5 3 3 2 5