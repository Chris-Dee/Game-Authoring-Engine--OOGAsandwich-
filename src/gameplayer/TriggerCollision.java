package gameplayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import gameEngine.GameObject;

public class TriggerCollision extends Collision{
	GameObject object;
	public String behavior;
	public TriggerCollision(String behavior, int colid1, int colid2) {
		super(colid1, colid2);
		this.behavior = behavior;
	}

	public TriggerCollision(String behavior, int colid1, int colid2, GameObject obj) {
		super(colid1, colid2);
		object = obj;
		this.behavior = behavior;
	}

	private void reset(){
		object.reset();
	}

	private void doReflect(String behavior){
		Class<?> moveClass = this.getClass();

		Method[] allMethods = moveClass.getDeclaredMethods();

		for (Method m : allMethods) {
			String mname = m.getName();
			if (mname.startsWith(behavior)){
				try {
					m.invoke(this);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
