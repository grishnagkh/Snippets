/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.grish.data.graph;

/**
 *
 * @author stef
 */
public  class Node {

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