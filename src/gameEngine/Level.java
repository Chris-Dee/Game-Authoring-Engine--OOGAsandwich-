package gameEngine;

import java.util.*;

import jgame.JGPoint;

public class Level {
	
	//private LevelInput levelInput;
	private List<GameObject> levelObjects;
	public static final JGPoint defaultSize = new JGPoint(2, 6);
	private List<GameForce> levelForces;
	/**
	 * Total playfield size, which can larger than screensize.
	 */
	private JGPoint levelSize;
	private String levelBG;
	private String name;
	public static final String defaultBackground="Resources/blankbackground.jpg";
	
	public Level(String levelName, JGPoint size, List<GameObject> objects, List<GameForce> forces, String background){
		initialize();
		levelSize = size;
		levelObjects = objects;
		levelForces = forces;
		levelBG = background;
		//levelSize=size;
		//levelInput=lvlInput;
	}
	public Level(String levelName){
		initialize();
		name=levelName;
		levelObjects = new ArrayList<GameObject>();
		levelForces = new ArrayList<GameForce>();
		levelBG = defaultBackground;
	}
	public void initialize(){
		levelSize = defaultSize;
	}
	public String getName(){
		return name;
	}
	public void changeStartingBackground(String bg){
		levelBG=bg;
	}
	public String getBackground(){
		return levelBG;
	}
	public void changeLevelSize(JGPoint size){
		levelSize = size;
	}
	public JGPoint getLevelSize(){
		return levelSize;
	}
	public void doFrame(){
		
	}
	
	public List<GameObject> getObjects(){
		return levelObjects;
	}
	
	public void addObjects(GameObject object) {
		levelObjects.add(object);
	}
	
	//public LevelInput getLevelInput(){
	//	return levelInput;
	//}
	
}
