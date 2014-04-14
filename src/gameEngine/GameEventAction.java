package gameEngine;

import gameplayer.GameEventManager;
import gameplayer.GamePlayerGUI;

import java.util.List;

public abstract class GameEventAction {
	protected List<Integer> recipientIDs;
	protected List<String> arguments; 	
	
	public GameEventAction(List<Integer> recipientIDs, List<String> arguments) throws InvalidEventActionException{
		this.recipientIDs = recipientIDs;
		this.arguments = arguments;
	}
	
	public abstract void act(GameEventManager manager, GamePlayerGUI gamePlayer);

}
