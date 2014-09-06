package gameengine.gameevents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameengine.GameObject;
import gameplayer.GamePlayerEngine;

public class BoundaryEvent extends GameEvent {
	@ParameterDesc(name = "Axis", description = "1-Top, 2-Left, 3-Bottom, 4-Right", type = Integer.class)
	private boolean isXAxis;
	private boolean isOrigin;
	@ParameterDesc(name = "Collision ID", description = "Object Type / Collision ID", type = Integer.class)
	private int colid;
	double boundary;
	private List<String> param;

	public BoundaryEvent() throws InvalidEventException {
		super();
	}

	public BoundaryEvent(List<String> parameters, String name)
			throws InvalidEventException {
		super(parameters, name);
		if (parameters.isEmpty()) {
			throw new InvalidEventException();
		}
		param=parameters;
		this.colid = Integer.parseInt(parameters.get(1));
	}

	@Override
	protected boolean eventHappened(GamePlayerEngine gamePlayer) {
		for (GameObject i : gamePlayer.getObjectsByColid(colid)) {
			//System.out.println("param "+param.get(0));
			switch (Integer.parseInt(param.get(0))){
			case 1:boundary=0; 
			isXAxis=true;
			isOrigin=true;
				break;
			case 2: boundary=0;
			isOrigin=true;
				break;
			case 3: boundary=gamePlayer.getGame().getCurrentLevel().getLevelSize().y;
			isXAxis=true;	
			break;
			case 4: boundary=gamePlayer.getGame().getCurrentLevel().getLevelSize().x;
				break;
			}
			if(isXAxis)
				return passedBounds(isOrigin,i.y);
			else
				return passedBounds(isOrigin,i.x);
		}
		
		return false;
	}

	private boolean passedBounds(boolean isTop,double current) {
	//	System.out.println("bound "+(boundary*320-10)+"  "+current+" "+isTop);
		if(isTop)
			return (current <= boundary*320-10);
		else
			return(current>=boundary*320-10);
	}

}
