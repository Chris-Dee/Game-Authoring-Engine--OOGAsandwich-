package gameEngine;

import java.util.List;
import java.util.ArrayList;

import jgame.JGPoint;

public class Game {
	public List<Level> allLevels;
	public Level currentLevel;
	private JGPoint size;
	public String mediaTablePath;
	public Game(){
		allLevels = new ArrayList<Level>();
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
	public static Game getExample(){
		Game game = new Game();
		game.mediaTablePath = "tempTable.tbl";
		
		List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();
		UninstantiatedGameObject myObject = new UninstantiatedGameObject("test", new JGPoint(10, 10), 1, "hero-r");
		objs.add(myObject);
		List<GameForce> forces = new ArrayList<GameForce>();
		GameForce force1 = new GameForce();
		forces.add(force1);
		
		Level firstLevel = new Level("first level", new JGPoint(640, 480), objs, forces, "metal");
		game.addLevel(firstLevel);
		game.currentLevel = firstLevel;
		return game;
	}
	private void addLevel(Level level) {
		allLevels.add(level);
	}
}
