package gameEngine;

import java.util.List;
import java.util.ArrayList;

import jgame.JGPoint;

public class Game {
	private List<Level> allLevels;
	private Level currentLevel;
	private JGPoint screenSize;
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
		game.screenSize = new JGPoint(640, 480);
		
		List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();
		objs.add(new UninstantiatedGameObject("test", new JGPoint(10, 10), 1, "hero-r"));
		objs.add(new UninstantiatedGameObject("test", new JGPoint(100, 100), 1, "hero-r"));
		objs.add(new UninstantiatedGameObject("test", new JGPoint(20, 105), 1, "hero-r"));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(20, 155), 1, "mytile"));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(25, 135), 1, "mytile"));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(30, 125), 1, "mytile"));
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
