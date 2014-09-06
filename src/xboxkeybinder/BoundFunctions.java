package xboxkeybinder;

import java.util.List;

import xboxkeybinder.binding_backend.Bind;
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
	public void rightStick(int dir, double mag){
		System.out.println("RS "+dir+" "+mag);
		//Code to go to main menu
	}
	@Bind
	public void leftStick(int dir, double mag){
		System.out.println("LS "+dir+" "+mag);
		//Code to go to main menu
	}
	@Bind
	public void dPad(int dir){
		System.out.println("D-Pad "+dir);
		//Code to go to main menu
	}
	@Bind
	public void button(){
		System.out.println("You hit a button!");
	}
	
	@Bind
	public void options(){
		System.out.println("bringing up options menu");
		//Code to bring up options menu
	}
	
	@Bind
	public void moveHoriz(JGObject obj,double magnitude,int dir){
		System.out.println(magnitude);
		//obj.x += 5;
		obj.xspeed = magnitude*5;
		obj.xdir=-(dir-180)/Math.abs(dir-180);
	}
	
	@Bind
	public void moveLeft(JGObject obj){
		obj.x += -5;
		obj.xspeed = -.01;
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
	public void jump(JGObject obj,double mag,int dir){
		obj.yspeed = -8;
		obj.xspeed=mag;
		obj.xdir=-(dir-180)/Math.abs(dir-180);
	}
	
}