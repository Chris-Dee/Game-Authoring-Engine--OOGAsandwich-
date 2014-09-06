package xboxkeybinder.binding_backend;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import ch.aplu.xboxcontroller.XboxController;

import jgame.JGObject;
import jgame.platform.*;

public class BindingExecutor {
	
	private Class<?> actionClass;
	private List<Class<?>> actionClasses;
	private Map<Integer, String> myBindings;
	private JGEngine engine;
	private XboxBinderAdapter myAdapter=new XboxBinderAdapter();
	private XboxController xc=new XboxController();
	private Map<String,Integer> paramMap=new HashMap<String,Integer>();
	private List<JGObject> objectList;
	private Object[] objectInputs;
	
	/**
	 * A class which acts as a back-end for the keybinding util package.
	 * Handles reflective retrieving and calling of methods based
	 * on keys pressed and Bindings.txt.
	 * @throws ClassNotFoundException 
	 */
	public BindingExecutor(String methodsClassName, String... otherClassNames){
		engine = null;
		xc.addXboxControllerListener(myAdapter);
		if(!xc.isConnected())
			System.out.println("Controlloer not connected");
		xc.setLeftThumbDeadZone(0.2);
		xc.setRightThumbDeadZone(0.2);
		actionClasses = new ArrayList<Class<?>>();
		try {
			actionClass = Class.forName(methodsClassName);
			fillParamMap(actionClass.getDeclaredMethods());
			for(String s : otherClassNames){
				actionClasses.add(Class.forName(s));
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		myBindings = new HashMap<Integer, String>();
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("src/xboxkeybinder/Bindings.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException");
		}
		
		for(String key : properties.stringPropertyNames()) {
			Integer keyInt = Integer.parseInt(key);
			String value = properties.getProperty(key);
			myBindings.put(keyInt, value);
		}
		
	}
//	public void findAdapter(XboxAdapter adapt){
//		myAdapter=adapt;
//	}
	
	/**
	 * Executes the keybound functions.
	 * Call this function once in the "doFrame" method of the jgame JGEngine.
	 * 
	 * @param engine
	 * 		the main engine for the current instance of jgame
	 * 		usually, the parameter should be "this", as in "executeInput(this);"
	 */
	public void executeInput(JGEngine engine,Object... argObjects){ // this should be called each frame
		int lastButton=myAdapter.getLastButton();
		this.engine = engine;
		objectInputs=argObjects;
		objectList=engine.getObjects("", 0, false, null);
		//System.out.println(objectList);
		//System.out.println(myBindings.size());
		for(int k : myBindings.keySet()){
			//System.out.println(myAdapter.getLastButton()+" "+k+" "+((int)myAdapter.getLastButton()==(int)k));
			boolean b;
			if(k<XboxBinderAdapter.keyOffset)
				//I was getting some really dumb error with the getKey method...spent way too long on that
				b=engine.getKey(k);
				else
			b=lastButton==k;
			if(b){
				String val = myBindings.get(k);
				String[] valArray = val.split(",");
				doReflect(valArray[0], valArray[1]);
			}
			
		}
		myAdapter.clearLastHit();

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
		//System.out.println(action+"  "+actor);
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
		
		//Object[] args = null;
		//System.out.println(paramMap+" "+actor);
		
		List<Method> allMethods = new ArrayList<Method>();
		
		Method[] methodsArray = actionClass.getDeclaredMethods();
		List<Method> tempMethods = Arrays.asList(methodsArray);
		allMethods.addAll(tempMethods);
		
		for(Class<?> c : actionClasses){
			methodsArray = c.getDeclaredMethods();
			tempMethods = Arrays.asList(methodsArray);
			allMethods.addAll(tempMethods);
		}
		
		for (Method m : allMethods) {
			String mname = m.getName();
				if (mname.startsWith(action)){
					try {
						//System.out.println("invoking");
						m.invoke(actionObject, findArgs(m,actor));
						
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						
						for(Object o : otherActionObjects){
							try {
								
								findArgs(m,actor);
								//System.out.println("invoking");
								m.invoke(o, findArgs(m,actor));
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e1) {
								
							}
						}
						try {
							m.invoke(engine, findArgs(m,actor));
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e1) {
							//This is fine
						}
						
						for(Object o : objectInputs){
							try {
								m.invoke(o, findArgs(m,actor));
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
	public ArrayList<String> getUniversalMethods(){
		
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
			 Class<?>[] params=m.getParameterTypes();
			// System.out.println(isUniversal(m.getParameterTypes()));
            if (annos != null && isUniversal(m.getParameterTypes())) {
            	
            	methodNames.add(m.getName());
            }
		}
		
		if(methodNames.isEmpty()){
			for(Method m : allMethods){
				if(isUniversal(m.getParameterTypes())){
					//System.out.println(m.getName());
					methodNames.add(m.getName());
				}
			}
        }
		
		return methodNames;
		
	}
	protected boolean isUniversal(Class<?>[] params){
		for(int i=0;i<params.length;i++){
			//System.out.println(params[i].toString().contains("JGObject"));
			if(params[i].toString().contains("JGObject"))
			return false;
		}
		return true;
	}
	public ArrayList<String> getObjectMethods(){
		
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
            if (annos != null && !isUniversal(m.getParameterTypes())){ //&& m.getParameterTypes()[0]==jgobj.getClass()) {
            	methodNames.add(m.getName());
            }
		}
		
		if(methodNames.isEmpty()){
			for(Method m : allMethods){
				if(!isUniversal(m.getParameterTypes())){ //&& m.getParameterTypes()[0]==jgobj.getClass()){
					methodNames.add(m.getName());
				}
			}
        }
		
		return methodNames;
		
	}
	private void fillParamMap(Method[] methods){
		for(Method m:methods){
			paramMap.put(m.getName(), m.getParameterTypes().length);
			//System.out.println(paramMap+" 1");
		}
	}
	protected static Map<Integer, String> loadMap(){
		
		Map<Integer, String> loaded = new HashMap<Integer, String>();
		
		Properties properties = new Properties();
		try {
		  properties.load(new FileInputStream("src/xboxkeybinder/Bindings.txt"));
		} catch (IOException e) {
			System.out.println("IOException");
		}
		
		for(String key : properties.stringPropertyNames()) {
			Integer keyInt = Integer.parseInt(key);
			String value = properties.getProperty(key);
			loaded.put(keyInt, value);
			//System.out.println(keyInt+"  "+value);
		}
		
		
		return loaded;
	}
	private Object[] findArgs(Method m, String actor){
		Object[] args=new Object[m.getParameterTypes().length];
		Class<?>[] params=m.getParameterTypes();
		
		for(int i=0;i<params.length;i++){
			String name=params[i].getName();
			if(name.equals("int")||name.equals("Integer"))
				args[i]=myAdapter.getLastDir();
			if(name.equals("double")||name.equals("float")||name.equals("Double"))
				args[i]=myAdapter.getLastMag();
			
			if(name.contains("JGObject")){
				for(JGObject j:objectList){
					//System.out.println(actor+"  "+j.getName());
					if(j.getName().contains(actor)){
						//System.out.println(j);
						args[i]=j;
					}
				}
			
			}
			
		}
	return args;
	}
}
