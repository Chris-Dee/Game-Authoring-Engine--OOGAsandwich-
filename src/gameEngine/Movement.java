package gameEngine;

import jgame.JGTimer;

public class Movement {
	
	private int xspeed;
	private boolean isStart = true;
	
	public Movement(){
		xspeed = 0;
	}
	
	public Movement(int xspeed){
		this.xspeed = xspeed;
	}
	
	public int getXSpeed(){
		return xspeed;
	}
	
	public void pace(int time){
		xspeed=2;
		new JGTimer(time,false) {
			public void alarm() {
				xspeed=-xspeed;
			}
		};
	}
	
	public void start(){
		pace(50);
		isStart = false;
	}
	
	public boolean getIsStart(){
		return isStart;
	}

}
