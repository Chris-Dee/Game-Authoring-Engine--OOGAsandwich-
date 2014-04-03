package gameEngine;

import java.util.*;

public class Level {
	
	private List<GameObject> levelObjects;
	private List<GameForce> levelForces;
	private List<Integer> levelSize;
	private String levelBG;
	public static final String defaultBackground="Resources/blankbackground.jpg";
	
	public Level(List<GameObject> objects, List<GameForce> forces, String background){
		levelObjects = objects;
		levelForces = forces;
		levelBG = background;
		//levelSize=size;
	}
	public Level(){
		levelObjects=new ArrayList<GameObject>();
		levelForces=new ArrayList<GameForce>();
		levelBG=defaultBackground;
	}
	public void changeStartingBackground(String bg){
		levelBG=bg;
	}
	public String getBackground(){
		return levelBG;
	}
	public void changeLevelSize(List<Integer> size){
	levelSize=size;
	System.out.println(levelSize);
	}
	public void doFrame(){
		
	}
	
	public List<GameObject> getObjects(){
		return levelObjects;
	}
	
}
