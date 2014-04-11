package gameEngine;

import java.util.*;

import jgame.JGPoint;

public class Level {

	// private LevelInput levelInput;
	private List<UninstantiatedGameObject> levelObjects;
	public static final JGPoint defaultSize = new JGPoint(2, 6);
	/**
	 * Total playfield size, which can larger than screensize.
	 */
	private JGPoint levelSize;
	private String levelBG;
	private String name;
	private double gravityVal;
	public static final String defaultBackground="blankbackground";
	private int GAME_SCORE=100;

	public Level(String levelName, JGPoint size,
			List<UninstantiatedGameObject> objects,
			String background, double gravityMagnitude) {
		initialize();
		levelSize = size;
		levelObjects = objects;
		levelBG = background;
		// levelSize=size;
		// levelInput=lvlInput;
		gravityVal = gravityMagnitude;
	}

	public Level(String levelName) {
		initialize();
		name = levelName;
		levelObjects = new ArrayList<UninstantiatedGameObject>();
		levelBG = defaultBackground;
	}

	public void initialize() {
		levelSize = defaultSize;
	}

	public String getName() {
		return name;
	}

	public void changeStartingBackground(String bg) {
		levelBG = bg;
	}

	public String getBackground() {
		return levelBG;
	}

	public void changeLevelSize(JGPoint size) {
		levelSize = size;
	}

	public JGPoint getLevelSize() {
		return levelSize;
	}

	public void doFrame() {

	}

	public List<UninstantiatedGameObject> getObjects() {
		return levelObjects;
	}

	public void addObjects(UninstantiatedGameObject object) {
		levelObjects.add(object);
	}

	public double getGravityVal() {
		return gravityVal;
	}

	public void setGravityVal(double value) {
		gravityVal = value;
	}
	
	public void changeScore(int magnitude){
		GAME_SCORE+=magnitude;
	}
	public int getCurrentScore(){
		return GAME_SCORE;
	}
	// public LevelInput getLevelInput(){
	// return levelInput;
	// }
}
