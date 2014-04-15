package gameEngine.eventactions;

import java.util.List;

import gameEngine.GameEventAction;
import gameEngine.InvalidEventActionException;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerGame;

public class RemoveAction extends GameEventAction{

	public RemoveAction(List<Integer> recipientIDs, List<String> arguments) throws InvalidEventActionException{
		super(recipientIDs, arguments);
		if(recipientIDs.isEmpty()){
			throw new InvalidEventActionException("Need a recipient");
		}
	}

	@Override
	public void act(GameEventManager manager, GamePlayerGame gamePlayer) {
		manager.getObjectById(recipientIDs.get(0)).remove();
	}
}
