package gameengine.eventactions;

import gameengine.GameObject;
import gameengine.gameevents.ParameterDesc;
import gameengine.powerups.PowerUp;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

import java.util.List;

public class ActivatePowerUpsAction extends GameEventAction {

	public ActivatePowerUpsAction() {
		super();
	}

	public ActivatePowerUpsAction(List<String> arguments)
			throws InvalidEventActionException {
		super(arguments);
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		for (GameObject obj : gamePlayer.getCurrentObjects()) {
			for (PowerUp item : obj.myItems){
				item.useItem(obj);
			}
		}
	}

}
