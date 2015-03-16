/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.grish.data.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author stef
 */
public class Graph {

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
		nodes = new HashMap<Integer, Node>();
		edges = new ArrayList<Edge>();
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
		Collection<Node> tmpNodes = new ArrayList<Node>();
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
		ArrayList<Node> rand = new ArrayList<Node>();

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
