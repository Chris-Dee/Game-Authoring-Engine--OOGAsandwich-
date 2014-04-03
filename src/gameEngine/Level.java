package gameEngine;

import java.util.*;

public class Level {
	
	private List<GameObject> levelObjects;
	private List<GameForce> levelForces;
	private String levelBG;
	
	public Level(List<GameObject> objects, List<GameForce> forces, String background){
		levelObjects = objects;
		levelForces = forces;
		levelBG = background;
	}
	
	public void doFrame(){
		
	}
	
	public List<GameObject> getObjects(){
		return levelObjects;
	}
	
}
