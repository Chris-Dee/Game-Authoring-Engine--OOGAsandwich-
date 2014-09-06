package util.keybinder;

import gameengine.GameObject;

import java.util.List;

import statistics.GameStats;
import jgame.JGObject;

/**
 * @author samginsburg and dennislynch
 * Class which holds methods to be bound to key presses.
 * Write methods here which either take no parameters or 
 * take a single JGObject as a parameter.
 * This class is an option, but does not have to be used.
 */
public class BoundFunctions {
	
	public BoundFunctions(){
		
	}
	
	// Sample functions:
	
	@Bind
	public void escape(){
		System.out.println("escaping");
		//Code to go to main menu
	}
	
	@Bind
	public void options(){
		System.out.println("bringing up options menu");
		//Code to bring up options menu
	}
	
	@Bind
	public void moveRight(JGObject obj){
		int moveSpeed = ((GameObject)obj).getMoveSpeed();
		obj.x += moveSpeed;
		obj.xspeed = .01;
		GameStats.update("Distance Travelled", moveSpeed);
	}
	
	@Bind
	public void moveLeft(JGObject obj){
		int moveSpeed = ((GameObject)obj).getMoveSpeed();
		obj.x += -moveSpeed;
		obj.xspeed = -.01;
		GameStats.update("Distance Travelled", moveSpeed);
	}
	
	@Bind
	public void moveUp(JGObject obj){
		obj.y += -5;
	}
	
	@Bind
	public void moveDown(JGObject obj){
		obj.y += 5;
	}
	
	@Bind
	public void jump(JGObject obj){
		GameObject go = ((GameObject)obj);
		int jumpSpeed = go.getJumpSpeed();
		if(go.getCanJump()){
			obj.yspeed = -jumpSpeed;
			GameStats.update("Jumps", 1);
		}
	}
	
}