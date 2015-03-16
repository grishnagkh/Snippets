package at.grish.data;

import java.io.File;

/**
 * Model representing a single Snapshot Holds a reference to a temporary xml
 * file on disk ready for double linked list implementation
 * 
 * @author stef
 * 
 */
public class Snapshot {
	/**
	 * The file which to reference
	 */
	private File file;
	/**
	 * reference to next Snapshot
	 */
	private Snapshot next;
	/**
	 * reference to previous Snapshot
	 */
	private Snapshot prev;

	/**
	 * Constructor
	 * 
	 * @param f
	 *            the file where the XML stands
	 */
	public Snapshot(File f) {
		setFile(f);
		setNext(null);
		setPrev(null);
	}

	/**
	 * @return the next
	 */
	Snapshot getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	void setNext(Snapshot next) {
		this.next = next;
	}

	/**
	 * @return the previous Snapshot
	 */
	Snapshot getPrev() {
		return prev;
	}

	/**
	 * @param prev
	 *            the Snapshot to set
	 */
	void setPrev(Snapshot prev) {
		this.prev = prev;
	}

	/**
	 * @return the file
	 */
	File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		if (next == null)
			return file.getAbsolutePath();
		return file.getAbsolutePath() + "->" + next.toString();
	}
}