package gameEngine;

import gameplayer.BasicCollision;
import gameplayer.GameObjectModification;
import gameplayer.TriggerCollision;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import data.GameData;
import data.InvalidDataFileException;
import jgame.JGPoint;

public class Game {
	private List<Level> allLevels;
	private int currentLevel;
	protected JGPoint screenSize;
	public String mediaTablePath;
	private GameData myGameData;
	public ArrayList<BasicCollision> collisionRules;
	public ArrayList<TriggerCollision> collisionTriggers;
	
	public Game(){
		allLevels = new ArrayList<Level>();
		collisionRules = new ArrayList<BasicCollision>();
		try {
			myGameData = new GameData("");
		} catch (InvalidDataFileException e) {
			e.printStackTrace();
		}
		collisionTriggers = new ArrayList<TriggerCollision>();
	}
	
	public Game(String dirPath) throws ClassNotFoundException{
		allLevels = new ArrayList<Level>();
		collisionRules = new ArrayList<BasicCollision>();
		mediaTablePath = "mario.tbl";
		screenSize = new JGPoint(900, 900);
		try {
			myGameData = new GameData("");
			myGameData.setFileName(dirPath);
			myGameData.parse();
			List<Object> myLevelObjects = myGameData.getObjects("gameEngine.Level");
			List<Level> myLevels = new ArrayList<Level>();
			for (Object obj : myLevelObjects) {
				myLevels.add((Level) obj);
			}
			addListOfLevels(myLevels);
			setCurrentLevel(0);
		} catch (InvalidDataFileException e) {
			e.printStackTrace();
		}
		collisionTriggers = new ArrayList<TriggerCollision>();
		int[][] modMatrix = { { 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, -1, 0, 1, 0, 0 } };
		collisionRules.add(new BasicCollision(1, 2, new GameObjectModification(
				modMatrix, 1, 0)));
		collisionRules.add(new BasicCollision(4, 2, new GameObjectModification(
				modMatrix, 1, 0)));
		collisionTriggers.add(new TriggerCollision("endlevel", 8, 1));
		collisionTriggers.add(new TriggerCollision("reset", 1, 4));
	}
	
	/*
	public Game(String dirPath){
		allLevels = new ArrayList<Level>();
		size = new JGPoint(640, 480);
		loadLevels(dirPath);
		mediaTablePath = dirPath + "/media.tbl";
	}
	public void loadLevels(String filePath){
		
	}
	public void initialize() {
		currentLevel = allLevels.get(0);
	}
	public JGPoint getSize() {
		return size;
	}
	*/
	public Game getExample() throws IOException, InvalidDataFileException{
		
		Game game = new Game();
		return game;
	}
	protected void addLevel(Level level) {
		allLevels.add(level);
	}
	protected void addListOfLevels(List<Level> levels) {
		allLevels.addAll(levels);
	}
	public Level getCurrentLevel() {
		return allLevels.get(currentLevel);
	}
	public void setCurrentLevel(int levelIndex) {
		this.currentLevel = levelIndex;
	}
	public JGPoint getSize() {
		return screenSize;
	}
	public int getNextLevelIndex() {
		return currentLevel+1;
	}
}
