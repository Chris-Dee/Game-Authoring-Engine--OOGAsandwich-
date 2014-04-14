package gameplayer;

public class Tuple<E,B> {
	E x;
	B y;
	public Tuple (E obj1, B obj2){
		x = obj1;
		y = obj2;
	}
	public boolean equals(Object other){
		@SuppressWarnings("unchecked")
		Tuple<E,B> castedOther = (Tuple<E,B>) other;
		return castedOther.x.equals(x) && castedOther.y.equals(y);
	}
}
