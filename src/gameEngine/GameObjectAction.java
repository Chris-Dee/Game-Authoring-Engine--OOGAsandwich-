package gameEngine;

import gameplayer.Tuple;
import jgame.JGPoint;
import jgame.JGTimer;

import java.lang.reflect.*;
import java.util.Map;

public class GameObjectAction {
	
	private boolean isStart = true;
	private String behavior;
	private int behaviorSpeed;
	private int behaviorTime;
	private boolean behaviorFlag = false;
	private JGPoint initialPosition;
	private JGTimer currentTimer;
	
	private Map<Integer, Tuple<String,Integer>> characterMap;
	
	public GameObjectAction(){

	}
	
	public GameObjectAction(String behavior, int time){
		this.behavior = behavior;
		behaviorTime = time;
		behaviorFlag = true;
		behaviorSpeed = 0;
	}
	public void setInitialPosition(JGPoint pos){
		initialPosition=pos;
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
	
	private void pace(int time, int speed, final GameObject myObj) throws InterruptedException{
		myObj.xspeed = speed;
		final int timer=time;
		currentTimer=new JGTimer(behaviorTime,false) {
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
			doReflect(behavior.toLowerCase(), behaviorTime, behaviorSpeed, obj);
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
	public void setSpeed(int speed, GameObject myObj){
		System.out.println("Setting speed to: "+speed);
		behaviorSpeed=speed;
		myObj.xspeed=speed;
		//isStart=true;
		myObj.x=initialPosition.x;
		myObj.y=initialPosition.y;
	}
	public void setDuration(int duration, GameObject myObj) throws InterruptedException{
		behaviorTime=duration*10;
		System.out.println("Setting duration to: "+duration);
		//isStart=true;
		myObj.x=initialPosition.x;
		myObj.y=initialPosition.y;
		currentTimer.set(duration*10,false);
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
