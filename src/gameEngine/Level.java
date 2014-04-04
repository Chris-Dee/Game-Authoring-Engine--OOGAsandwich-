package gameEngine;

import java.util.*;

import jgame.JGPoint;

public class Level {
	
	private List<GameObject> levelObjects;
	public static final JGPoint defaultSize = new JGPoint(2, 6);
	private List<GameForce> levelForces;
	private JGPoint levelSize;
	private String levelBG;
	private String name;
	public static final String defaultBackground="Resources/blankbackground.jpg";
	
	public Level(String levelName,List<GameObject> objects, List<GameForce> forces, String background){
		initialize();
		levelObjects = objects;
		levelForces = forces;
		levelBG = background;
		//levelSize=size;
	}
	public Level(){
		initialize();
		levelObjects = new ArrayList<GameObject>();
		levelForces = new ArrayList<GameForce>();
		levelBG = defaultBackground;
	}
	public void initialize(){
		levelSize = defaultSize;
	}
	public void changeStartingBackground(String bg){
		levelBG=bg;
	}
	public String getBackground(){
		return levelBG;
	}
	public void changeLevelSize(JGPoint size){
		levelSize = size;
		System.out.println(levelSize);
	}
	public JGPoint getLevelSize(){
		return levelSize;
	}
	public void doFrame(){
		
	}
	
	public List<GameObject> getObjects(){
		return levelObjects;
	}
	
}
