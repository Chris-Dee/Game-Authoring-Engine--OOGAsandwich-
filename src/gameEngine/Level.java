package gameengine;

import gameengine.gameevents.GameEvent;

import java.util.*;

import jgame.JGPoint;
import jgame.JGTimer;

public class Level {

	private static final int TIMER_DEFAULT = 30;
	private List<UninstantiatedGameObject> levelObjects;
	public static final JGPoint DEFAULT_SIZE = new JGPoint(2, 6);
	/**
	 * Total playfield size, which can larger than screensize.
	 */
	private JGPoint levelSize;
	private String levelBG;
	private String levelName;
	private double gravityVal;
	private static final String DEFAULT_BACKGROUND = "blankbackground";
	private List<GameEvent> events;
	private int timeLeft;
	private int startingTime;
	private boolean hasTimer;

	public Level(String lvlName, JGPoint size,
			List<UninstantiatedGameObject> objects, String background,
			double gravityMagnitude, int levelTime) {
		if (levelTime <= 0) {
			hasTimer = false;
		} else {
			hasTimer = true;
			timeLeft = levelTime;
		}
		levelName = lvlName;
		levelSize = size;
		levelObjects = objects;
		levelBG = background;
		gravityVal = gravityMagnitude;
		events = new ArrayList<GameEvent>();
	}

	public Level(String levelName) {
		levelSize = DEFAULT_SIZE;
		this.levelName = levelName;
		levelObjects = new ArrayList<UninstantiatedGameObject>();
		levelBG = DEFAULT_BACKGROUND;
		events = new ArrayList<GameEvent>();
		timeLeft = 0;
		gravityVal = 0;
	}

	public boolean hasTimer() {
		return hasTimer;
	}

	public int getCurrentTime() {
		return timeLeft;
	}
	public void resetTime(){
		setCurrentTime(startingTime);
	}

	public void setCurrentTime(int time) {
		if (time <= 0) {
			hasTimer = false;
		} else {
			hasTimer = true;
			timeLeft = time;
		}
	}

	public void startGameTime() {
		int timer = TIMER_DEFAULT;
		new JGTimer(timer, false) {
			public void alarm() {
				timeLeft--;
			}
		};
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

	public List<GameEvent> getEvents() {
		return events;
	}

	public void addEvent(GameEvent... events) {
		this.events.addAll(Arrays.asList(events));
	}

	public void removeEvent(GameEvent event) {
		events.remove(event);
	}

	public String getLevelName() {
		return levelName;
	}

	public int getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(int time) {
		startingTime = time;
	}
}
