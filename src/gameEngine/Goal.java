package gameEngine;

import jgame.JGPoint;

//Goal object: specifies game objectives
//Is a gameObject to allow for flexibility of goals: allows for touching a moving enemy to be the goal for example.

public abstract class Goal extends GameObject{
	private int nextLevel;
	public Goal(String name, JGPoint position, int colid, String sprite, String behavior, int time, int speed, boolean floating, int id){
		super(name, position, colid, sprite, behavior, time, speed, floating, id);
	}
	
	public abstract boolean checkGoal();
	
	public int getNextlevel(){
		return nextLevel;
	}
	
	public abstract boolean checkGoal(int targetObject);
}
