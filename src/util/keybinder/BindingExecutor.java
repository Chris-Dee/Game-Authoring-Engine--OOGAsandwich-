package util.keybinder;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import jgame.JGObject;
import jgame.platform.*;

public class BindingExecutor {
	
	private Class<?> actionClass;
	private List<Class<?>> actionClasses;
	private Map<Integer, String> myBindings;
	private JGEngine engine;
	private Object[] objectInputs;
	
	
	/**
	 * A class which acts as a back-end for the keybinding util package.
	 * Handles reflective retrieving and calling of methods based
	 * on keys pressed and Bindings.txt.
	 * @throws ClassNotFoundException 
	 */
	public BindingExecutor(String methodsClassName, String... otherClassNames){
		engine = null;
		actionClasses = new ArrayList<Class<?>>();
		try {
			actionClass = Class.forName(methodsClassName);
			for(String s : otherClassNames){
				actionClasses.add(Class.forName(s));
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		myBindings = new HashMap<Integer, String>();
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("src/util/keybinder/Bindings.txt"));
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		for(String key : properties.stringPropertyNames()) {
			Integer keyInt = Integer.parseInt(key);
			String value = properties.getProperty(key);
			myBindings.put(keyInt, value);
		}
		
	}
	
	/**
	 * Executes the keybound functions.
	 * Call this function once in the "doFrame" method of the jgame JGEngine.
	 * 
	 * @param engine
	 * 		the main engine for the current instance of jgame
	 * 		usually, the parameter should be "this", as in "executeInput(this);"
	 */
	public void executeInput(JGEngine engine, Object... argObjects){ // this should be called each frame
		
		this.engine = engine;
		objectInputs = argObjects;
		for(Integer k : myBindings.keySet()){
			if(engine.getKey(k)){
				String val = myBindings.get(k);
				String[] valArray = val.split(",");
				doReflect(valArray[0], valArray[1]);
			}
			
		}

	}
	
	/**
	 * This method is used in executeInput().
	 * It reflectively invokes the method in the actionClass
	 * class with name matching String action.
	 * 
	 * @param action
	 * 		the name of the function to be invoked
	 */
	private void doReflect(String action, String actor){
		
		Constructor<?> ctor;
		Object actionObject = null;
		List<Object> otherActionObjects = new ArrayList<Object>();
		try {
			ctor = actionClass.getConstructor();
			actionObject = ctor.newInstance();
			
			for(Class<?> oc : actionClasses){
				ctor = oc.getConstructor();
				otherActionObjects.add(ctor.newInstance());
			}
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		actionObject = actionClass.cast(actionObject);
		for(int i=0; i<actionClasses.size(); i++){
			otherActionObjects.set(i,actionClasses.get(i).cast(otherActionObjects.get(i)));
		}
		
		Object[] args = null;
		if(actor.equals("Universal")){
			args = new Object[0];
		}
		else{
			args = new Object[1];
			args[0] = engine.getObjects(actor, 0, false, null).get(0);
		}
		
		List<Method> allMethods = new ArrayList<Method>();
		
		Method[] methodsArray = actionClass.getDeclaredMethods();
		List<Method> tempMethods = Arrays.asList(methodsArray);
		allMethods.addAll(tempMethods);
		
		for(Class<?> c : actionClasses){
			methodsArray = c.getDeclaredMethods();
			tempMethods = Arrays.asList(methodsArray);
			allMethods.addAll(tempMethods);
		}
		
		Class<?> engClass = engine.getClass();
		methodsArray = engClass.getDeclaredMethods();
		tempMethods = Arrays.asList(methodsArray);
		allMethods.addAll(tempMethods);
		
		for (Method m : allMethods) {
			String mname = m.getName();
				if (mname.startsWith(action)){
					try {
						m.invoke(actionObject, args);
						
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						//This is fine
						for(Object o : otherActionObjects){
							try {
								m.invoke(o, args);
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e1) {
								//This is fine
							}
						}
						
						try {
							m.invoke(engine, args);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e1) {
							//This is fine
						}
						
						for(Object o : objectInputs){
							try {
								m.invoke(o, args);
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e1) {
								//This is fine
							}
						}
						
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
		 }
		
	}
	
	/**
	 * Reflectively retrieves the names of the methods in
	 * the actionClass class and returns them as
	 * an ArrayList of Strings.
	 * 
	 * @return
	 */
	protected ArrayList<String> getUniversalMethods(){
		
//		Method[] allMethods = actionClass.getDeclaredMethods();
		
		List<Method> allMethods = new ArrayList<Method>();
		
		Method[] methodsArray = actionClass.getDeclaredMethods();
		List<Method> tempMethods = Arrays.asList(methodsArray);
		allMethods.addAll(tempMethods);
		
		for(Class<?> c : actionClasses){
			methodsArray = c.getDeclaredMethods();
			tempMethods = Arrays.asList(methodsArray);
			allMethods.addAll(tempMethods);
		}
		
		ArrayList<String> methodNames = new ArrayList<String>();
		
		for(Method m : allMethods){
			Bind annos = m.getAnnotation(Bind.class);
            if (annos != null && m.getParameterTypes().length==0) {
            	methodNames.add(m.getName());
            }
		}
		
		if(methodNames.isEmpty()){
			for(Method m : allMethods){
				if(m.getParameterTypes().length==0){
					methodNames.add(m.getName());
				}
			}
        }
		
		return methodNames;
		
	}
	
	protected ArrayList<String> getObjectMethods(){
		
//		Method[] allMethods = actionClass.getDeclaredMethods();
		
		List<Method> allMethods = new ArrayList<Method>();
		
		Method[] methodsArray = actionClass.getDeclaredMethods();
		List<Method> tempMethods = Arrays.asList(methodsArray);
		allMethods.addAll(tempMethods);
		
		for(Class<?> c : actionClasses){
			methodsArray = c.getDeclaredMethods();
			tempMethods = Arrays.asList(methodsArray);
			allMethods.addAll(tempMethods);
		}
		
		ArrayList<String> methodNames = new ArrayList<String>();
//		JGObject jgobj = new JGObject(null, false, 0, 0, 0, null);
		
		for(Method m : allMethods){
			Bind annos = m.getAnnotation(Bind.class);
            if (annos != null && m.getParameterTypes().length==1){ //&& m.getParameterTypes()[0]==jgobj.getClass()) {
            	methodNames.add(m.getName());
            }
		}
		
		if(methodNames.isEmpty()){
			for(Method m : allMethods){
				if(m.getParameterTypes().length==1){ //&& m.getParameterTypes()[0]==jgobj.getClass()){
					methodNames.add(m.getName());
				}
			}
        }
		
		return methodNames;
		
	}
	
	protected static Map<Integer, String> loadMap(){
		
		Map<Integer, String> loaded = new HashMap<Integer, String>();
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("src/util/keybinder/Bindings.txt"));
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		for(String key : properties.stringPropertyNames()) {
			Integer keyInt = Integer.parseInt(key);
			String value = properties.getProperty(key);
			loaded.put(keyInt, value);
		}
		
		
		return loaded;
	}

}
