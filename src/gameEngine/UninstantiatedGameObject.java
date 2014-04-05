package gameEngine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UninstantiatedGameObject {
	Object[] objArgs;
	Constructor<?> constructor;

	public UninstantiatedGameObject(String className, Object... args)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException {
		objArgs = args;
		Class<?> objKlass = Class.forName("gameEngine." + className);

		Constructor<?>[] objConstructors = objKlass.getConstructors();
		for (Constructor<?> c : objConstructors) {
			if (constructorFits(c, objArgs)) {
				constructor = c;
			}
		}
		if (constructor == null) {
			// Thrown if objects in args don't match constructor in class
			// corresponding to className
			throw new NoSuchMethodException();
		}
	}

	public GameObject instantiate() {
		try {
			return (GameObject) constructor.newInstance(objArgs);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean constructorFits(Constructor<?> constructor, Object[] args) {
		Class<?>[] paramTypes = constructor.getParameterTypes();
		if (args.length != paramTypes.length) {
			return false;
		}
		for (int i = 0; i < paramTypes.length; i++) {
			if (!paramTypes[i].isAssignableFrom(args[i].getClass())) {
				return false;
			}
		}
		return true;
	}
}
