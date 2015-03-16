package at.grish.parse.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* TODO erweitern :) */

public class DomParser {

	String xml;

	public DomParser(String xml) {
		this.xml = xml;
	}

	public DomParser(File xml) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(xml));
		StringBuffer sb = new StringBuffer();
		String tmp;
		while ((tmp = br.readLine()) != null) {
			sb.append(tmp);
		}
		br.close();
		this.xml = sb.toString();
	}

	public DOMNode parse() {
		if ("".equals(xml)) {
			System.out.println("already successfullyParsed");
			return null;
		}
		return parse(xml, null);
	}

	DOMNode root = null;

	public DOMNode parse(String xml, DOMNode parent) {
		consumeSpaces();
		xmlHeader();
		consumeSpaces();
		return tag(null);
	}

	public void xmlHeader() {
		// <?xml version="x.x" encoding="enc" ?>

		consume("<?xml");
		consumeSpaces();
		consume("version=\"");
		String version = version();
		consume("\"");
		consumeSpaces();
		consume("encoding=\"");
		String encoding = enc();
		consume("\"");
		consumeSpaces();
		consume("?>");
		consumeSpaces();
	}

	public DOMNode tag(DOMNode parent) {

		if (!consume("<")) {
			consumeSpaces();
			String text = consumeText();
			consumeSpaces();
			parent.setText(text);
			return parent;
		}
		consumeSpaces();
		String name = nextString();
		DOMNode t = new DOMNode(name);

		consumeSpaces();
		nodeAttributes(t);
		consumeSpaces();
		consume(">");
		consumeSpaces();

		while (!consume("</")) {
			tag(t);
		}
		consumeSpaces();
		consume(name);
		consumeSpaces();
		consume(">");
		consumeSpaces();

		if (parent != null) {
			parent.addChild(t);
		}
		return t;
	}

	public String nextString() {
		// stop at those chars:
		int nextSpace = xml.indexOf(' ');
		int tagEnd = xml.indexOf('>');
		int equal = xml.indexOf('=');
		int quote = xml.indexOf('"');

		int endIndex;
		// RMF calculations, we want the nearest appearance of one of the stop
		// chars
		endIndex = tagEnd > nextSpace && nextSpace > -1 ? nextSpace : tagEnd;
		endIndex = endIndex > equal && equal > -1 ? equal : endIndex;
		endIndex = endIndex > quote && quote > -1 ? quote : endIndex;

		String ret = xml.substring(0, endIndex);
		xml = xml.substring(endIndex);
		return ret;
	}

	public void nodeAttributes(DOMNode node) {

		consumeSpaces();
		while (!consume(">")) {
			consumeSpaces();
			String aName = nextString();
			consume("=");
			consume("\"");
			String aVal = nextString();
			consume("\"");
			node.addAttribute(aName, aVal);
			consumeSpaces();

		}

	}

	public String consumeText() {
		int pos = xml.indexOf("<");
		String ret = xml.substring(0, pos);
		xml = xml.substring(pos);
		return ret;
	}

	// returns true if String is consumable and consumes it
	public boolean consume(String str) {

		boolean ret = xml.startsWith(str);
		if (ret) {
			xml = xml.substring(str.length());
		}
		return ret;
	}

	public void consumeSpaces() {
		xml = xml.trim(); 
	}
	
	// only 1.0 is accepted
	public String version() {
		consume("1.0");
		return "1.0";
	}

	public String enc() {
		consume("UTF-8");
		return "UTF-8";
	}
}
