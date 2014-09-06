package util.tuples;

import java.util.ArrayList;
import java.util.List;

public class PythonTuple {
	private List<CastableObject> objects;
	
	public PythonTuple(Object... args){
		objects = new ArrayList<CastableObject>();
		for(int i = 0; i < args.length; i++){
			objects.add(new CastableObject(args[i].getClass(), args[i]));
		}
	}
	
	public CastableObject get(int index){
		return objects.get(index);
	}
	
	public void append(Object... args){
		for(int i = 0; i < args.length; i++){
			objects.add(new CastableObject(args[i].getClass(), args[i]));
		}
	}
	
	public void append(PythonTuple arg){
		objects.addAll(arg.objects);
	}
	
	public void remove(int index){
		objects.remove(index);
	}
}
