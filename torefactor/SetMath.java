import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * Class for basic math operations
 * 
 * @author stef
 * 
 */
public class SetMath {

	/**
	 * returns true, iff x is a subset from y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static <T> boolean isSubset(Collection<T> x, Collection<T> y) {
		for (T elem : x)
			if (!y.contains(elem))
				return false;
		return true;
	}

	/**
	 * union implementation on Lists
	 * 
	 * @param list
	 * @param x
	 * @return
	 */
	public static <T> List<T> union(List<T> list, List<T> x) {
		List<T> out = clone(list);
		for (T o : x)
			if (!out.contains(o))
				out.add(o);
		return out;
	}

	/**
	 * union implementation on an arbitrary number of lists
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> union(List<T>... list) {
		if (list.length == 0)
			return null;
		if (list.length > 1) {
			List<T> curr = list[0];
			for (int i = 1; i < list.length; i++) {
				curr = union(curr, list[i]);
			}
			return curr;
		}
		return list[0];

	}

	/**
	 * intersection implementation on Lists
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> intersect(List<T> list, List<T> x) {
		List<T> temp = clone(x);
		for (T a : x)
			if (!list.contains(a))
				temp.remove(a);
		return temp;
	}

	/**
	 * Minus implementation on lists
	 * 
	 * @param dependencies
	 * @param dList
	 * @return
	 */
	public static <T> List<T> minus(List<T> dependencies, List<T> dList) {

		List<T> list = clone(dependencies);
		for (T o : dependencies)
			if (dList.contains(o))
				list.remove(o);

		return list;
	}

	/**
	 * union implementation on sets
	 * 
	 * @param list
	 * @param x
	 * @return
	 */
	public static <T> Set<T> union(Set<T> list, Set<T> x) {
		Set<T> out = new HashSet<T>();
		out.addAll(list);
		out.addAll(x);
		return out;

	}

	/**
	 * minus implementation on Sets
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static <T> Set<T> minus(Set<T> x, Set<T> y) {
		Set<T> list = clone(x);
		for (T o : x)
			if (y.contains(o))
				list.remove(o);

		return list;
	}

	public static <T> List<T> clone(List<T> toClone) {
		List<T> list = new ArrayList<T>();
		for (T elem : toClone) {
			list.add(elem);
		}
		return list;
	}

	public static <T> Set<T> clone(Set<T> toClone) {
		Set<T> list = new HashSet<T>();
		for (T elem : toClone) {
			list.add(elem);
		}
		return list;
	}
}