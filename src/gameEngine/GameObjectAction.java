package gameEngine;

import jgame.JGTimer;

import java.lang.reflect.*;
import java.util.Map;

public class GameObjectAction {
	
	private int xspeed;
	private int yspeed;
	private boolean isStart = true;
	private String behavior;
	private int behaviorSpeed;
	private int behaviorTime;
	private boolean behaviorFlag = false;
	GameObject myObj;
	
	private Map<Integer, String> characterMap;
	
	public GameObjectAction(){
		xspeed = 0;
		yspeed = 0;
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
	
	public GameObjectAction(int xspeed, int yspeed){
		this.xspeed = xspeed;
		this.yspeed = yspeed;
	}
	public GameObjectAction(Map<Integer, String> inputMap){
		characterMap=inputMap;
		xspeed = 0;
		yspeed = 0;
	}
	
	public void addObject(GameObject theObj){
		myObj = theObj;
		myObj.xspeed = xspeed;
		myObj.yspeed = yspeed;
	}
	
	
	public int getXSpeed(){
		return xspeed;
	}
	
	public int getYSpeed(){
		return yspeed;
	}
	
	private void pace(int time, int speed){
//		xspeed=speed;
		myObj.xspeed = speed;
		new JGTimer(time,false) {
			public void alarm() {
//				xspeed=-xspeed;
				myObj.xspeed = -myObj.xspeed;
			}
		};
	}
	
	public void start(){
		isStart = false;
		if(behaviorFlag){
			doReflect(behavior, behaviorTime, behaviorSpeed);
		}
	}
	
	public boolean getIsStart(){
		return isStart;
	}
	
	private void doReflect(String behavior, int time, int speed){
		Class<?> moveClass = this.getClass();
		
		Object[] args = new Object[2];
		args[0] = time;
		args[1] = speed;
		
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
	
	public Map<Integer, String> getCharMap(){
		return characterMap;
	}

	public void moveRight(){
		myObj.xspeed=4;
	}
	
	public void moveLeft(){
		myObj.xspeed=-4;
	}
	
	public void moveUp(){
		myObj.yspeed=-6;
	}
	
	public void moveDown(){
		myObj.yspeed=6;
	}
	public void stopMovement(){
		myObj.xspeed=0;
	}
}
