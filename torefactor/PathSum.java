import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author stef
 */
/* pbviously i tried here to make a graph implementation TODO extract it */
public class PathSum {

	public static void test() {
		System.out.println(less(519432, 525806, 632382, 518061));
		int maxCnt = 1;
		int cnt = 1;
		int maxB, maxE;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"src/baseexp")));
			String s = br.readLine();
			maxB = Integer.parseInt(s.split(",")[0]);
			maxE = Integer.parseInt(s.split(",")[1]);
			while ((s = br.readLine()) != null) {
				cnt++;
				if (less(maxB, maxE, Integer.parseInt(s.split(",")[0]),
						Integer.parseInt(s.split(",")[1]))) {
					maxB = Integer.parseInt(s.split(",")[0]);
					maxE = Integer.parseInt(s.split(",")[1]);

					maxCnt = cnt;
				}
			}
			System.out.println(maxCnt);
		} catch (Exception ex) {
			Logger.getLogger(PathSum.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public static boolean less(int b1, int e1, int b2, int e2) {
		if (Math.log(b1) * e1 < Math.log(b2) * e2) {
			return true;
		} else {
			return false;
		}

	}

	public static void main(String[] args) {

		test();
		System.exit(0);

		int[][] array = { { 131, 673, 234, 103, 18 },
				{ 201, 96, 342, 965, 150 }, { 630, 803, 746, 422, 111 },
				{ 537, 699, 497, 121, 956 }, { 805, 732, 524, 37, 331 } };

		Graph g = new Graph();

		g.addNode(0);// initial node

		// add all the intermdeiate nodes
		for (int i = 0; i < array.length * array.length; i++) {
			g.addNode();
		}
		g.addEdge(0, 1, array[0][0]);

		// add the middle edges
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				// System.out.print("i want to make an edge from " + (i *
				// array.length + j));
				// System.out.print(" to " + (i * array.length + j + 1));
				// System.out.println("; with weight " + array[i][j]);

				g.addEdge((i * array.length + j), (i * array.length + j + 1),
						array[i][j]);
				if (((i + 1) * array.length + j) <= g.nNodes()) {
					g.addEdge((i * array.length + j),
							((i + 1) * array.length + j), array[i][j]);
				}

				// System.out.print("furthermore, i want to make an edge from "
				// + (i * array.length + j));
				// System.out.print(" to " + ((i + 1) * array.length + j));
				// System.out.println("; with weight " + array[i][j] + "\n");

			}
		}

		g.findWay(0, 1);

	}
}

class Node {

	Node pred;
	int dist;
	boolean chosen;
	static int currId = 1;
	int id;

	public Node(int i) {
		id = i;
	}

	public Node() {
		id = currId++;
	}
}

// directed graph
class Edge {

	int weight;
	Node from, to;

	public Edge(Node from, Node to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
}

class Graph {

	public int nNodes() {
		return nodes.size();
	}

	/**
	 * janker den dijkstra drüber!!
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public void findWay(int from, int to) {
		findPath(nodes.get(from));
		System.out.println("distance: " + nodes.get(to).dist);
	}

	HashMap<Integer, Node> nodes;
	List<Edge> edges;

	public Graph() {
		nodes = new HashMap<>();
		edges = new ArrayList<>();
	}

	public void addNode(int i) {
		Node n = new Node(i);
		nodes.put(n.id, n);
	}

	public void addNode() {
		Node n = new Node();
		nodes.put(n.id, n);
	}

	/**
	 * Add undirected edge
	 * 
	 * @param from
	 * @param to
	 */
	public void addUEdge(int from, int to) {
		addEdge(from, to, 1);
		addEdge(to, from, 1);
	}

	public void addUEdge(int from, int to, int weight) {
		addEdge(from, to, weight);
		addEdge(to, from, weight);
	}

	/**
	 * Add directed edge
	 * 
	 * @param from
	 * @param to
	 */
	public void addEdge(int from, int to) {
		addEdge(from, to, 1);
	}

	public void addEdge(int from, int to, int weight) {
		edges.add(new Edge(nodes.get(from), nodes.get(to), weight));
	}

	public void findPath(Node s) {
		Collection<Node> tmpNodes = new ArrayList<>();
		for (Node n : nodes.values()) {
			if (!s.equals(n)) {
				tmpNodes.add(n);
			}
		}
		for (Node n : tmpNodes) {
			n.pred = null;
			n.dist = Integer.MAX_VALUE;
			n.chosen = true;
		}

		s.pred = s;
		s.dist = 0;
		s.chosen = true;
		ArrayList<Node> rand = new ArrayList<>();

		ergR(s, rand);
		Node v;
		while (!rand.isEmpty()) {
			v = chooseV(rand);
			rand.remove(v);
			v.chosen = true;
			ergR(v, rand);
		}
	}

	public Node chooseV(ArrayList<Node> list) {
		Node min = list.get(0);
		for (Node n : list) {
			if (n.dist < min.dist) {
				min = n;
			}
		}
		return min;
	}

	public void ergR(Node s, ArrayList<Node> list) {
		for (Edge e : edges) {
			if (!s.equals(e.from)) {
				continue;
			}
			if (!e.to.chosen && e.from.dist + e.weight < e.to.dist) {
				e.to.pred = e.from;
				e.to.dist = e.from.dist + e.weight;
				list.add(e.to);
			}
		}
	}
}
