package gameEngine;

import java.util.List;

import jgame.JGPoint;

public class StageEndGoal extends Goal {
	private int levelEndpoint;
	private boolean isHorizontal;
	public StageEndGoal(String name, JGPoint position, int colid, String sprite, String behavior, int time, int speed, boolean floating, int id, int endpoint, boolean horizontal){
		super(name, position, colid, sprite, behavior, time, speed, floating, id);
		levelEndpoint = endpoint;
		isHorizontal = horizontal;
	}

	@Override
	public boolean checkGoal() {
		return false;
	}

	@Override
	public boolean checkGoal(List<GameObject> targetObjects) {
		if(targetObjects.size() == 0)
			return false;
		if(isHorizontal)
			return (targetObjects.get(0).x >= levelEndpoint);
		else
			return (targetObjects.get(0).y >= levelEndpoint);
	}

}
