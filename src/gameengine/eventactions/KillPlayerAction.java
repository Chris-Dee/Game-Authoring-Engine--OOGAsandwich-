package gameengine.eventactions;

import java.util.List;

import statistics.GameStats;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class KillPlayerAction extends GameEventAction { //extends ResetObjectAction {
	public KillPlayerAction() {
		super();
	}
	
	public KillPlayerAction(List<String> arguments) throws InvalidEventActionException{
		super(arguments);
	}
	
	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		//super.act(manager, gamePlayer);
		gamePlayer.die();
		GameStats.update("Deaths", 1);
	}
}
