package util.tuples;

import java.util.ArrayList;
import java.util.List;

/**
 * Tuple<E> contains two objects of type E.
 * @param <E>
 */
public class Tuple<E> {
	public E x, y;
	
	public Tuple(){
		x = null;
		y = null;
	}
	
	/**
	 * Stores two objects obj1 and obj2 in that order. The objects are 
	 * referenced by x and y respectively.
	 * @param obj1
	 * 		1st Object
	 * @param obj2
	 * 		2nd Object of Same Type
	 */
	public Tuple(E obj1, E obj2){
		x = obj1;
		y = obj2;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object other){
		try{
			Tuple<E> castedOther = (Tuple<E>) other;
			return castedOther.x.equals(x) && castedOther.y.equals(y);
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * Reverses the order of the tuple. Transforms Tuple(a, b) to Tuple(b, a). 
	 */
	public void reverse(){
		E temp = x;
		x = y;
		y = temp;
	}

	@Override
	public String toString(){
		return "(" + x.toString() + ", " + y.toString() + ")";
	}
	
	/**
	 * Returns a shallow clone of the tuple. Object references are simply
	 * copied.
	 * @return new Tuple<E>(x, y)
	 */
	public Tuple<E> clone(){
		return new Tuple<E>(x, y);
	}
	
	/**
	 * Returns list containing the toStrings of x and y.
	 * @return List
	 */
	public List<String> toListString(){
		List<String> ret = new ArrayList<String>();
		ret.add(x.toString());
		ret.add(y.toString());
		return ret;
	}
	
	/**
	 * Returns a list containing x and y.
	 * @return List
	 */
	public List<E> toList(){
		List<E> ret = new ArrayList<E>();
		ret.add(x);
		ret.add(y);
		return ret;
	}
	
	@Override
	public int hashCode() {
		return x.toString().hashCode() + y.toString().hashCode();
	}
	
	/**
	 * Sorts (if possible) the tuple.
	 */
	@SuppressWarnings("unchecked")
	public void sort(){
		try{
			if(((Comparable<E>) x).compareTo(y) > 0){
				reverse();
			}
		}catch(Exception e){
			//do nothing
		}
	}
}