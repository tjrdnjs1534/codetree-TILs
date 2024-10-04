// public class Main {
//     static public class Box{
//         Box front;
//         Box back;
//         int id;
//         int weight;

//         Box(int id,int weight){
//             this.id = id;
//             this.weight= weight;
//             this.front = null;
//             this.back = null;
//         }
        
//     }
//     static Box[] belts;
//     static Box[] beltsBack;
//     static boolean[] isBroken;
//     static HashSet<Integer> idSet = new HashSet<>();
//     static int q, n, m, len;
//     public static void main(String[] args) throws IOException{
//         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//         StringTokenizer st = new StringTokenizer(br.readLine());
//         q = Integer.parseInt(st.nextToken());
//         st = new StringTokenizer(br.readLine());
//         int num100 = Integer.parseInt(st.nextToken());
//         n = Integer.parseInt(st.nextToken());
//         m =Integer.parseInt(st.nextToken());
//         len = n/m;
//         belts = new Belt[m];
//         beltsBack= new Belt[m];
//         isBroken = new boolean[m];
//         int[] ids = new int[n];
//         int[] weights = new int[n];
//         for(int i=0; i<m; i++){
//             belts[i] = new Box(0,0);
//         }
//         for(int i=0; i<n; i++){
//             ids[i] = Integer.parseInt(st.nextToken());
//         }
//         for(int i=0; i<n; i++){
//             weights[i] = Integer.parseInt(st.nextToken());
//         }
//         int idx = 0;
//         while(idx<m){
//             Box beltFront = belts[idx];
//             for(int i=len*idx; i<len*(idx+1); i++){
//                 Box newBox = new Box(ids[i], weights[i]);
//                 set.add(ids[i]);
//                 beltFront.back = newBox;
//                 newBox.front = beltFront;
//                 beltFront = newBox;
//             }
//             beltsBack[idx] = beltFront;
//             idx++;
//         }
//         for(int i=1; i<q; i++){
//             st = new StringTokenizer(br.readLine());
//             int op = Integer.parseInt(st.nextToken());
//             if(op==200){
//                 int max = Integer.parseInt(st.nextToken());
//                 int sum=0;
//                 for(int i=0; i<m; i++){
//                     if(isBroken[i]) continue;
//                     Box beltFront = belts[i];
//                     Box last = beltBack[i];
//                     Box first = belts[i].back;
//                     if(first==null)continue;
//                     if(first.weight <=max){
//                         set.remove(first.id);
//                         beltFront.back = first.back;
//                         sum+=first.weight;
//                         if(beltsBack == first){
//                             beltBack[i] = null;
//                         }
//                     }
//                     else {
//                         beltFront.back = first.back;
//                         first.front = last;
//                         first.back = null;
//                         last.back = first;
//                         beltBack[i] = first;
//                     }
//                 }
//                 System.out.println(sum);
//             }
//             else if(op==300){
//                 int rid = Integer.parseInt(st.nextToken());
//                 if(set.contain(rid))
//             }
//             else if(op==400){

//             }
//             else if(op==500){

//             }
//         }

//     }
// }

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
	static class Node {
		int id;
		int weight;
		int next;
		int prev;

		Node(int a, int b) {
			id = a;
			weight = b;
			next = -1;
			prev = -1;
		}
	}

	static class Line {
		int head;
		int tail;
		int size;
		boolean isMalfunction;

		Line() {
			head = -1;
			tail = -1;
			size = 0;
			isMalfunction = false;
		}

		public void addFirst(int key) {
			if (size == 0) {
				head = key;
				tail = key;
				size++;
				return;
			}

			nodes[tail].prev = key;
			nodes[key].next = head;
			head = key;
			size++;
		}

		public void addLast(int key) {
			if (size == 0) {
				addFirst(key);
				return;
			}

			nodes[tail].next = key;
			nodes[key].prev = tail;
			tail = key;
			size++;
		}

		public int popFront() {
			if (size == 0)
				return -1;

			int index = head;
			if (size == 1) {
				head = -1;
				tail = -1;
			} else {
				int next = nodes[head].next;
				nodes[head].next = -1;
				nodes[next].prev = -1;
				head = next;
			}
			size--;
			idBeltMap.put(nodes[index].id, -1);
			idIndexMap.put(nodes[index].id, -1);
			return index;
		}

		public void remove(int pos) {
			if (head == pos) {
				popFront();
			} else if (tail == pos) {
				int prev = nodes[tail].prev;
				nodes[tail].prev = -1;
				nodes[prev].next = -1;
				tail = prev;
				size--;
			} else {
				int prev = nodes[pos].prev;
				int next = nodes[pos].next;
				nodes[prev].next = next;
				nodes[next].prev = prev;
				size--;
			}

			idBeltMap.put(nodes[pos].id, -1);
			idIndexMap.put(nodes[pos].id, -1);
		}

		public void reArrange(int pos) {
			if (head == pos) {
				// nothing
			} else if (tail == pos) {
				int prev = nodes[tail].prev;
				nodes[tail].prev = -1;
				nodes[prev].next = -1;
				tail = prev;

				nodes[pos].next = head;
				nodes[head].prev = pos;
				head = pos;
			} else {
				int prev = nodes[pos].prev;
				int last = tail;
				int first = head;

				nodes[last].next = first;
				nodes[first].prev = last;
				nodes[prev].next = -1;
				tail = prev;
				head = pos;
			}
		}
	}

	static Map<Integer, Integer> idIndexMap = new HashMap<>();
	static Map<Integer, Integer> idBeltMap = new HashMap<>();
	static Node[] nodes;
	static Line[] lines;
	static int N, M;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = stoi(in.readLine());
		for (int tc = 0; tc < T; ++tc) {
			String[] inputs = in.readLine().split(" ");
			switch (stoi(inputs[0])) {
				case 100:
					makeFactory(inputs);
					break;
				case 200:
					unloadProduct(inputs);
					break;
				case 300:
					removeProduct(inputs);
					break;
				case 400:
					checkProduct(inputs);
					break;
				case 500:
					malfunction(inputs);
					break;
				default:
					break;
			}
		}
		System.out.println(sb);

	}

	private static void makeFactory(String[] inputs) {
		N = stoi(inputs[1]);
		M = stoi(inputs[2]);
		nodes = new Node[N];
		lines = new Line[M];
		for (int i = 0; i < M; ++i)
			lines[i] = new Line();

		for (int i = 0; i < N; ++i)
			// i번째 node는 id, weight로 구성됨.
			nodes[i] = new Node(stoi(inputs[3 + i]), stoi(inputs[3 + i + N]));

		for (int i = 0; i < N; ++i) {
			int lineNum = i / (N / M);
			lines[lineNum].addLast(i);
			idIndexMap.put(nodes[i].id, i);
			idBeltMap.put(nodes[i].id, lineNum);
		}

	}

	private static void unloadProduct(String[] inputs) {
		int limit = stoi(inputs[1]);

		int sum = 0;
		for (int i = 0; i < M; ++i) {
			if (lines[i].isMalfunction)
				continue;
			if (lines[i].size == 0)
				continue;

			int idx = lines[i].popFront();
			// 물건 하차
			if (nodes[idx].weight <= limit) {
				sum += nodes[idx].weight;
				continue;
			}

			// 재적재
			lines[i].addLast(idx);
			idIndexMap.put(nodes[idx].id, idx);
			idBeltMap.put(nodes[idx].id, i);
		}

		sb.append(sum).append("\n");
	}

	private static void removeProduct(String[] inputs) {
		int key = stoi(inputs[1]);
		int pos = idIndexMap.getOrDefault(key, -1);

		if (pos == -1) {
			sb.append(-1).append("\n");
			return;
		}

		int belt = idBeltMap.get(key);
		lines[belt].remove(pos);
		sb.append(key).append("\n");
	}

	private static void checkProduct(String[] inputs) {
		int key = stoi(inputs[1]);
		int pos = idIndexMap.getOrDefault(key, -1);

		if (pos == -1) {
			sb.append(-1).append("\n");
			return;
		}

		int belt = idBeltMap.get(key);
		lines[belt].reArrange(pos);
		sb.append(belt + 1).append("\n");
	}

	private static void malfunction(String[] inputs) {
		int n = stoi(inputs[1]);

		if (lines[n - 1].isMalfunction) {
			sb.append("-1").append("\n");
			return;
		}
		lines[n - 1].isMalfunction = true;

		int next = -1;
		for (int i = 1; i < M; ++i) {
			next = (n - 1 + i) % M;
			if (!lines[next].isMalfunction)
				break;
		}

		while (lines[n - 1].size > 0) {
			int index = lines[n - 1].popFront();
			lines[next].addLast(index);
			idIndexMap.put(nodes[index].id, index);
			idBeltMap.put(nodes[index].id, next);
		}

		sb.append(n).append("\n");
	}

	public static int stoi(String s) {
		return Integer.parseInt(s);
	}
}