package util.tuples;

public class CastableObject {
	public Class<?> myClass;
	public Object myObject;
	
	/**
	 * To use on CastableObject i, i.myClass.cast(i.myObject) 
	 * @param myClass
	 * @param myObject
	 */
	public CastableObject(Class<?> myClass,	Object myObject){
		this.myClass = myClass;
		this.myObject = myObject;
	}
}
