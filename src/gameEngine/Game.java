package gameEngine;

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

		objs.add(new UninstantiatedGameObject("player", new JGPoint(10, 10), 1, "hero-r", new Movement("pace",25, 5)));
		objs.add(new UninstantiatedGameObject("test", new JGPoint(100, 100), 1, "hero-r", new Movement("pace",25, 5)));
		objs.add(new UninstantiatedGameObject("test", new JGPoint(20, 105), 1, "hero-r", new Movement(4,1)));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(20, 155), 1, "mytile", new Movement()));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(25, 135), 1, "mytile", new Movement("pace",75, 2)));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(30, 125), 1, "mytile", new Movement()));

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
		
		Map<Character, String[]> levelInputMap= new HashMap<Character, String[]>();
		levelInputMap.put('D', new String[]{"player","moveRight"});
		levelInputMap.put('A', new String[]{"player","moveLeft"});
		levelInputMap.put('W', new String[]{"player","moveUp"});
		levelInputMap.put('S', new String[]{"player","moveDown"});
		
		Level firstLevel = new Level("first level", new JGPoint(640, 480), objs, forces, "metal",new LevelInput(levelInputMap));

		
		
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
