package gameEngine;

import jgame.JGPoint;

public abstract class Goal {
	private boolean isStart = true;
	private int levelEnd;
	private String behavior; 
	private boolean behaviorFlag = false;
	
	public Goal(){
		this.behavior = behavior;
		behaviorFlag = true;
	}
	
	public void start(){
		isStart = false;
	}
	
	public boolean checkGoal(double x){
		return (x>=levelEnd);
	}
}
