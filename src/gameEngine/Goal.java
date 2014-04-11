package gameEngine;

import java.util.List;

import jgame.JGPoint;

//Goal object: specifies game objectives
//Is a gameObject to allow for flexibility of goals: allows for touching a moving enemy to be the goal for example.

public abstract class Goal extends GameObject{
	private int nextLevel;
	private List<Integer> myTargets;
	//Todo: implement passing in nextLevel;
	public Goal(String name, JGPoint position, int colid, String sprite, String behavior, int time, int speed, boolean floating, int id){
		super(name, position, colid, sprite, behavior, time, speed, floating, id);
	}
	
	public abstract boolean checkGoal(List<GameObject> targetobjects);
	
	public int getNextLevel(){
		return nextLevel;
	}
	
	public List<Integer> getTargets(){
		return myTargets;
	}
	
	public abstract boolean checkGoal();
}
