package gameEngine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
		//game.screenSize = new JGPoint(640, 480);
		game.screenSize = new JGPoint(1440, 900);
		
		List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();

		try {
			objs.add(new UninstantiatedGameObject("GameObject", "test", new JGPoint(10, 10), 1, "hero-r", new Movement("pace",50, 8)));
			objs.add(new UninstantiatedGameObject("GameObject", "test", new JGPoint(100, 100), 1, "hero-r", new Movement("pace",25, 5)));
			objs.add(new UninstantiatedGameObject("GameObject", "test", new JGPoint(20, 105), 1, "hero-r", new Movement(4,1)));
			objs.add(new UninstantiatedGameObject("GameObject", "land", new JGPoint(20, 155), 1, "mytile", new Movement()));
			objs.add(new UninstantiatedGameObject("GameObject", "land", new JGPoint(25, 135), 1, "mytile", new Movement("pace",75, 2)));
			objs.add(new UninstantiatedGameObject("GameObject", "land", new JGPoint(30, 125), 1, "mytile", new Movement()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

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
