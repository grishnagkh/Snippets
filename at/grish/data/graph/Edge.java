/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.grish.data.graph;

import at.grish.data.graph.Node;

/**
 *
 * @author stef
 */

public class Edge {

	int weight;
	Node from, to;

	public Edge(Node from, Node to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
}

