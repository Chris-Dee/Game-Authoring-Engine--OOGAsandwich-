package gameengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jgame.JGPoint;
import data.GameData;
import data.InvalidDataFileException;
import data.SandwichGameData;

public class Game {
	private List<Level> allLevels;
	private int currentLevel;
	protected JGPoint screenSize;
	private GameData myGameData;
	private static int lives;
	private int startingLives;
	private int totalScore = 0;
	private String myGameName;

	/**
	 * Initializes a Game
	 */
	public Game() {
		allLevels = new ArrayList<Level>();
		myGameData = new SandwichGameData();
		lives = 3;
		startingLives=lives;
	}

	/**
	 * Initializes a saved Game from a file
	 */
	public Game(String dirPath) throws ClassNotFoundException {
		allLevels = new ArrayList<Level>();
		screenSize = new JGPoint(900, 900);
		try {
			myGameData = new SandwichGameData();
			myGameData.setFileName(dirPath);
			myGameData.parse();
			List<Object> myLevelObjects = myGameData
					.getObjects("gameengine.Level");
			List<Level> myLevels = new ArrayList<Level>();
			for (Object obj : myLevelObjects) {
				myLevels.add((Level) obj);
			}
			addListOfLevels(myLevels);
			setCurrentLevel(0);
		} catch (InvalidDataFileException e) {
			e.printStackTrace();
		}
		if (lives <= 0) {
			lives = 3;
		}
		startingLives = lives;
		myGameName = dirPath.substring(0, dirPath.length() - 5);
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

	public boolean setCurrentLevel(int levelIndex) {
		if (levelIndex >= allLevels.size()) {
			return false;
		}
		this.currentLevel = levelIndex;
		return true;
	}

	public JGPoint getSize() {
		return screenSize;
	}

	public int getNextLevelIndex() {
		return currentLevel + 1;
	}

	public static void setLives(int l) {
		lives = l;
	}

	public int getLives() {
		return lives;
	}

	public void setTotalScore(int score) {
		totalScore = score;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void reset() {
		setLives(startingLives);
		setTotalScore(0);
		setCurrentLevel(0);
	}

	public String getGameName() {
		return myGameName;
	}

	public void setGameName(String name) {
		myGameName = name;
	}
}
