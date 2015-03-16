package at.grish.parse.xml;

import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public class DOMNode {

	private String name;
	private String text;

	private Map<String, String> attributes;
	private Vector<DOMNode> children;

	public DOMNode() {
		attributes = new HashMap<>();
		children = new Vector<>();
	}

	public DOMNode(String name) {
		this();
		this.name = name;
	}

	public DOMNode(String name, String text) {
		this(name);
		this.text = text;
	}

	public String getAttributeByName(String name) {
		return attributes.get(name);
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public boolean addChild(DOMNode child) {
		return children.add(child);
	}

	public void addAttribute(String name, String val) {
		attributes.put(name, val);
	}

	public String getText() {
		return text;
	}

	public String getName() {
		return name;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		String s = getName() + ":" + getText() + attributes.toString() + " ( ";
		for (DOMNode node : children) {
			s += " , ";
			s += node.toString();
		}

		s += " ) ";
		return s;
	}
}
