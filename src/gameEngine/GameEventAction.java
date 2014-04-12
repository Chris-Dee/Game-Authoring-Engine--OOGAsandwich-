package gameEngine;

public abstract class GameEventAction {
	int[] recipientIDs;
	
	public GameEventAction(int ... recipientIDs){
		this.recipientIDs = recipientIDs;
	}
	
	public abstract void act();	

}
