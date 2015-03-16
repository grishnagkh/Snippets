

import java.io.File;

/**
 *  undo/redo list
 * TODO: look if reusable or make it so
 * @author stef
 * 
 */
public class SnapshotList {

	/**
	 * flag for options
	 */
	public static final int UNDO = 1;
	/**
	 * flag for options
	 */
	public static final int REDO = 2;

	Snapshot first, act;
	private int maxSize;
	private int actSize = 0;

	public SnapshotList(int size) {
		setMaxSize(size);
		first = null;
		actSize = 0;
	}

	/**
	 * add a snapshot to the list
	 * 
	 * @param f
	 */
	public void addSnapshot(File f) {
		Snapshot s = new Snapshot(f);
		if (first == null) {
			first = s;
			act = first;
			actSize++;
			return;
		}
		// keep the temp dir clean
		if (act.getNext() != null)
			cleanUp(act.getNext());

		act.setNext(s);
		s.setPrev(act);
		act = act.getNext();

		actSize++;
		if (actSize > maxSize) {
			actSize--;
			first = first.getNext();
			first.getPrev().getFile().delete();
			first.setPrev(null);
			System.gc();
		}
	}

	/**
	 * 
	 * @return true if undo is possible
	 */
	public boolean isUndoPossible() {
		return null != act.getPrev();
	}

	/**
	 * true if redo is possible
	 * 
	 * @return
	 */
	public boolean isRedoPossible() {
		return null != act.getNext();
	}

	/**
	 * get the last / next snapshot
	 * 
	 * @param direction
	 *            UNDO OR REDO
	 */
	public File getSnapshot(int direction) {
		if (direction == UNDO && act.getPrev() != null)
			act = act.getPrev();
		else if (direction == REDO && act.getNext() != null)
			act = act.getNext();
		else
			;

		return act.getFile();
	}

	/**
	 * @return the maximum size
	 */
	int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param size
	 *            the maximum size to set
	 */
	void setMaxSize(int size) {
		this.maxSize = size;
	}

	/**
	 * removes the temporary files after shutdownpackage main.java.core.io;

	 * 
	 * @param s
	 */
	private void cleanUp(Snapshot s) {
		do {
			s.getFile().delete();
		} while ((s = s.getNext()) != null);
		System.gc();
	}

	/**
	 * cleanup of temporary files
	 */
	public void shutDown() {
		cleanUp(first);
	}

	/**
	 * 
	 * @return actual snapshot
	 */
	public Snapshot getAct() {
		return act;
	}
}