package gameEngine;

import gameplayer.Collision;

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
	private Level currentLevel;
	protected JGPoint screenSize;
	public String mediaTablePath;
	private GameData myGameData;
	public ArrayList<Collision> collisionRules;
	public Game(){
		allLevels = new ArrayList<Level>();
		collisionRules = new ArrayList<Collision>();
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
		//game.screenSize = new JGPoint(640, 480);
		game.screenSize = new JGPoint(1440, 900);
		
		Map<Integer, String> levelInputMap= new HashMap<Integer, String>();
		levelInputMap.put(39, "moveRight");
		levelInputMap.put(37,"moveLeft");
		levelInputMap.put(38, "moveUp");
		levelInputMap.put(40, "moveDown");
		
		List<GameObject> objs = new ArrayList<GameObject>();

		objs.add(new GameObject("player", new JGPoint(10, 10), 1, "hero-r", new GameObjectAction(levelInputMap), false));
		objs.add(new GameObject("test", new JGPoint(100, 100), 1, "hero-r", new GameObjectAction("pace",25, 5), false));
		objs.add(new GameObject("test", new JGPoint(20, 105), 1, "hero-r", new GameObjectAction(4,1), false));
		objs.add(new GameObject("land", new JGPoint(20, 155), 2, "mytile", new GameObjectAction(levelInputMap), true));
		objs.add(new GameObject("land", new JGPoint(25, 135), 2, "mytile", new GameObjectAction("pace", 75, 2), true));
		objs.add(new GameObject("land", new JGPoint(30, 125), 2, "mytile", new GameObjectAction(), true));
		objs.add(new GameObject("goal", new JGPoint(30, 125), 1, "mytile", new Goal("end", 700), false));

		// This code will eventually be used to parse the data.
		
//		myGameData.setFileName("uninstantiatedgameobjectsfile");
//		Map<String, List<Object>> myObjectMap = myGameData.parse();
//		for (String str : myObjectMap.keySet()) {
//			for (Object obj : myObjectMap.get(str)) {
//				objs.add((UninstantiatedGameObject) obj);
//			}
//		}
		
		List<GameForce> forces = new ArrayList<GameForce>();
		GameForce force1 = new GameForce();
		forces.add(force1);
		
	
		
		Level firstLevel = new Level("first level", new JGPoint(640, 480), objs, forces, "metal", .2);//,new LevelInput(levelInputMap));

		
		
		game.addLevel(firstLevel);
		game.setCurrentLevel(firstLevel);
		return game;
	}
	protected void addLevel(Level level) {
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
