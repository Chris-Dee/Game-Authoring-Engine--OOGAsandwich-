package gameEngine;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import data.GameData;
import data.InvalidDataFileException;
import jgame.JGPoint;

public class Game {
	private List<Level> allLevels;
	private Level currentLevel;
	private JGPoint screenSize;
	public String mediaTablePath;
	private GameData myGameData;
	public Game(){
		allLevels = new ArrayList<Level>();
		try {
			myGameData = new GameData("");
		} catch (InvalidDataFileException e) {
			e.printStackTrace();
		}
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
		game.mediaTablePath = "tempTable.tbl";
		game.screenSize = new JGPoint(640, 480);
		
		List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();
		myGameData.setFileName("uninstantiatedgameobjectsfile");
		Map<String, List<Object>> myObjectMap = myGameData.parse();
		for (String str : myObjectMap.keySet()) {
			for (Object obj : myObjectMap.get(str)) {
				objs.add((UninstantiatedGameObject) obj);
			}
		}
		List<GameForce> forces = new ArrayList<GameForce>();
		GameForce force1 = new GameForce();
		forces.add(force1);
		
		Level firstLevel = new Level("first level", new JGPoint(640, 480), objs, forces, "metal");
		game.addLevel(firstLevel);
		game.setCurrentLevel(firstLevel);
		return game;
	}
	private void addLevel(Level level) {
		allLevels.add(level);
	}
	public Level getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}
	public JGPoint getSize() {
		return screenSize;
	}
}
