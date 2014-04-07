package gameEngine;

import jgame.JGPoint;

public class Goal extends GameObjectAction {
	private boolean isStart = true;
	private int levelEnd;
	private String behavior; 
	private boolean behaviorFlag = false;
	
	public Goal(String behavior, int x){
		levelEnd = x;
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
