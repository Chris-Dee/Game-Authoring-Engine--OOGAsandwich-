package gameEngine;

import java.util.*;

public class Level {
	
	private List<GameObject> levelObjects;
	public static final int defaultHeight =2;
	public static final int defaultWidth =6;
	private List<GameForce> levelForces;
	private List<Integer> levelSize;
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
		levelObjects=new ArrayList<GameObject>();
		levelForces=new ArrayList<GameForce>();
		levelBG=defaultBackground;
	}
	public void initialize(){
		levelSize=new ArrayList<Integer>(){{add(defaultHeight); add(defaultWidth);}};
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
	public List<Integer> getLevelSize(){
		return levelSize;
	}
	public void doFrame(){
		
	}
	
	public List<GameObject> getObjects(){
		return levelObjects;
	}
	
}
