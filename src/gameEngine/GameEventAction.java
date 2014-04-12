package gameEngine;

import java.util.List;

public abstract class GameEventAction {
	List<Integer> recipientIDs;
	
	public GameEventAction(List<Integer> recipientIDs){
		this.recipientIDs = recipientIDs;
	}
	
	public abstract void act();	

}
