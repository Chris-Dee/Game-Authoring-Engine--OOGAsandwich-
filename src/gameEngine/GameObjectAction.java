package gameEngine;

import gameplayer.Tuple;
import jgame.JGTimer;

import java.lang.reflect.*;
import java.util.Map;

public class GameObjectAction {
	
	private boolean isStart = true;
	private String behavior;
	private int behaviorSpeed;
	private int behaviorTime;
	private boolean behaviorFlag = false;
	
	private Map<Integer, Tuple<String,Integer>> characterMap;
	
	public GameObjectAction(){

	}
	
	public GameObjectAction(String behavior, int time){
		this.behavior = behavior;
		behaviorTime = time;
		behaviorFlag = true;
		behaviorSpeed = 0;
	}
	
	public GameObjectAction(String behavior, int time, int speed){
		this.behavior = behavior;
		behaviorSpeed = speed;
		behaviorFlag = true;
		behaviorTime = time;
	}
	
	public GameObjectAction(Map<Integer, Tuple<String,Integer>> inputMap){
		characterMap=inputMap;
	}
	
	private void pace(int time, int speed, final GameObject myObj){
		myObj.xspeed = speed;
		new JGTimer(time,false) {
			public void alarm() {
				myObj.xspeed = -myObj.xspeed;
			}
		};
	}
	
	private void stationary(int time, int speed, final GameObject myObj){
		myObj.xspeed = 0;
		myObj.yspeed = 0;
	}
	
	public void start(GameObject obj){
		isStart = false;
		if(behaviorFlag){
			doReflect(behavior, behaviorTime, behaviorSpeed, obj);
		}
	}
	
	public boolean getIsStart(){
		return isStart;
	}
	
	private void doReflect(String behavior, int time, int speed, GameObject obj){
		Class<?> moveClass = this.getClass();
		
		Object[] args = new Object[3];
		args[0] = time;
		args[1] = speed;
		args[2] = obj;
		
		Method[] allMethods = moveClass.getDeclaredMethods();
		
		for (Method m : allMethods) {
			String mname = m.getName();
				if (mname.startsWith(behavior)){
					try {
						m.invoke(this, args);
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
	
	public Map<Integer, Tuple<String,Integer>> getCharMap(){
		return characterMap;
	}

	public void moveRight(GameObject myObj,int magnitude){
		myObj.xspeed=magnitude;
	}
	
	public void moveLeft(GameObject myObj,int magnitude){
		myObj.xspeed=-magnitude;
	}
	
	public void moveUp(GameObject myObj,int magnitude){
		myObj.yspeed=-magnitude;
	}
	
	public void moveDown(GameObject myObj,int magnitude){
		myObj.yspeed=magnitude;
	}
	public void stopMovement(GameObject myObj){
		myObj.xspeed=0;
	}
}
