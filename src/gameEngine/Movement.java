package gameEngine;

import jgame.JGTimer;

import java.lang.reflect.*;

public class Movement {
	
	private int xspeed;
	private int yspeed;
	private boolean isStart = true;
	private String behavior;
	private int behaviorSpeed;
	private int behaviorTime;
	private boolean behaviorFlag = false;
	
	public Movement(){
		xspeed = 0;
		yspeed = 0;
	}
	
	public Movement(String behavior, int time){
		this.behavior = behavior;
		behaviorTime = time;
		behaviorFlag = true;
		behaviorSpeed = 0;
	}
	
	public Movement(String behavior, int time, int speed){
		this.behavior = behavior;
		behaviorSpeed = speed;
		behaviorFlag = true;
		behaviorTime = time;
	}
	
	public Movement(int xspeed, int yspeed){
		this.xspeed = xspeed;
		this.yspeed = yspeed;
	}
	
	public int getXSpeed(){
		return xspeed;
	}
	
	public int getYSpeed(){
		return yspeed;
	}
	
	private void pace(int time, int speed){
		xspeed=speed;
		new JGTimer(time,false) {
			public void alarm() {
				xspeed=-xspeed;
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

}
