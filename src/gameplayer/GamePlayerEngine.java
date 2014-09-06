package gameplayer;

import gameengine.Game;
import gameengine.GameObject;
import gameengine.UninstantiatedGameObject;
import gameengine.powerups.PowerUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import jgame.JGTimer;
import jgame.platform.JGEngine;
import statistics.GameStats;
import statistics.StatsController;
import util.tuples.Tuple;
//import xboxkeybinder.binding_backend.Bind;
//import xboxkeybinder.binding_backend.BindingExecutor;
import util.keybinder.*;
import collisions.CollisionManager;

public class GamePlayerEngine extends JGEngine {

	private static final int TIMER_CONSTANT = 30;
	private static final int TIME_LEFT_AMOUNT = 100;
	private static final String TIME_LEFT_STRING = "Time Left";
	private static final String IN_GAME_STATE_STRING = "InGame";
	private static final int GAME_MAX_FRAMES_SKIP = 2;
	private static final int WIDTH_AND_HEIGHT_DIVISION_CONSTANT = 100;
	private static final int NUMBER_OF_TILES = 80;
	private static final int GAME_FPS = 30;
	private Game currentGame;
	private static List<GameObject> currentObjects;
	private GameEventManager eventManager;
	private CollisionManager collisionManager;
	private Map<Tuple<Integer>, ArrayList<Tuple<GameObject>>> currentCollisions;
	private static final String MEDIA_TABLE_PATH = "projectResources/tempTable.tbl";
	private int counterInitial = 10;
	private int invincibilityCounter = counterInitial;
	// private BindingExecutor xboxBinder;
	private BindingExecutor binder;
	private boolean gameOver;
	private GamePlayerHUD hud;
	private boolean canDie = false;
	private StatsController statController;

	public GamePlayerEngine(Game loadedGame) {

		setCurrentGame(loadedGame);
		initEngine(getGame().getSize());
		statController = new StatsController(this, loadedGame.getGameName());
		// binder = new BindingExecutor(currentObjects);
		// xboxBinder = new
		// xboxkeybinder.BindingExecutor("xboxkeybinder.BoundFunctions");
		binder = new BindingExecutor("util.keybinder.BoundFunctions");
	}

	/**
	 * This method runs before the engine initializes. This can be considered a
	 * replacement of the regular constructor. We only need to call
	 * setCanvasSettings here.
	 */
	@Override
	public void initCanvas() {
		setCanvasSettings(NUMBER_OF_TILES, NUMBER_OF_TILES, displayWidth()
				/ WIDTH_AND_HEIGHT_DIVISION_CONSTANT, displayHeight()
				/ WIDTH_AND_HEIGHT_DIVISION_CONSTANT, null, null, null);
		initEngineApplet();
	}

	/**
	 * Define initializations after the engine is initialized. This method is
	 * called by the game thread after initEngine() was called. Used to set
	 * initial game state and load in media.
	 */
	@Override
	public void initGame() {
		setFrameRate(GAME_FPS, GAME_MAX_FRAMES_SKIP);

		// If images have already been defined by editor DO NO redefine them
		// if (el.images == null || el.images.size()<10)
		// EngineLogic.images.clear();
		defineMedia(MEDIA_TABLE_PATH);
		setGameState(IN_GAME_STATE_STRING);
	}

	/**
	 * One-time setup of the game.
	 */
	public void startInGame() {
		GameStats.set(TIME_LEFT_STRING, TIME_LEFT_AMOUNT);
		final int timer = TIMER_CONSTANT;
		JGTimer topTimer = new JGTimer(timer, false) {
			public void alarm() {
				GameStats.update(TIME_LEFT_STRING, -1);
			}
		};
		getGame().getCurrentLevel().startGameTime();
		constructLevel(); // sets levels
	}

	private boolean setDeathBoolean() {
		if (invincibilityCounter > 0) {
			invincibilityCounter--;
			return false;
		} else
			return true;
	}

	/**
	 * Run every frame while game state is InGame.
	 */
	public void doFrameInGame() {

		canDie=setDeathBoolean();
		binder.executeInput(this);
		moveObjects(null,// object name prefix of objects to move (null means
				// any)
				0 // object collision ID of objects to move (0 means any)
		);
		doLevel();
		setViewOffset(avgScreenPosition(currentObjects), true);
	}

	public void paintFrameInGame() {
		hud.paintHUD();
		drawLine(0, 640, 300, 640);
	}

	public void startGameOver() {
		gameOverScreen();
	}

	public void paintFrameGameOver() {
		drawString("Game Over!", viewWidth() / 2, viewHeight() / 8, 0);
	}

	public void startGameWon() {
		gameOverScreen();
	}

	public void gameOverScreen() {
		gameOver = true;
		removeObjects(null, 0);
		removeAllTimers();
		int timer = GAME_FPS * 3;
		new JGTimer(timer, true) {
			public void alarm() {
				setGameState("DisplayStats");
			}
		};
	}

	public void paintFrameGameWon() {
		drawString("You Win!", viewWidth() / 2, viewHeight() / 8, 0);
	}

	public void startDisplayStats() {
		statController.saveGameStats();
	}

	public void paintFrameDisplayStats() {
		statController.displayStats();
	}

	private void doLevel() {
		// Runs level logic for the current level and updates.
		doGravity(getGame().getCurrentLevel().getGravityVal());
		setCurrentCollisions(collisionManager.getCollisions());
		checkLevelTimer();
		eventManager.check();
	}

	public void checkLevelTimer() {
		if (getGame().getCurrentLevel().hasTimer()
				&& getGame().getCurrentLevel().getCurrentTime() <= 0) {
			getGame().getCurrentLevel().setCurrentTime(
					getGame().getCurrentLevel().getStartingTime());
			die();
		}
	}

	@Bind
	public void usePowerUps(GameObject obj) {
		for (PowerUp item : obj.myItems) {
			item.useItem(obj);
			GameStats.update("Power-Ups Used", 1);
		}
	}

	public void setCurrentCollisions(
			Map<Tuple<Integer>, ArrayList<Tuple<GameObject>>> collisionsThisFrame) {
		this.currentCollisions = collisionsThisFrame;
	}

	public List<GameObject> getCurrentObjects() {
		return currentObjects;
	}

	public void endLevel(int nextLevelIndex) {
		for (GameObject i : currentObjects) {
			i.remove();
		}
		// currentGame.setTotalScore(currentGame.getTotalScore()+
		// currentGame.getCurrentLevel().getCurrentScore());
		if (!getGame().setCurrentLevel(nextLevelIndex)) {
			setGameState("GameWon");
		} else {
			constructLevel();
		}

	}

	public void clearCurrentObjects() {
		for (GameObject i : currentObjects) {
			i.remove();
		}
	}
	public static void addObjects(GameObject... objs) {
		currentObjects.addAll(Arrays.asList(objs));
	}

	public void constructLevel() {
		currentObjects = new ArrayList<GameObject>();
		for (UninstantiatedGameObject i : getGame().getCurrentLevel()
				.getObjects()) {
			// TODO: Instantiate based on if sprite is on screen
			currentObjects.add(i.instantiate(true));
		}
		setCollisionManager(new CollisionManager(this));
		eventManager = new GameEventManager(currentObjects, getGame()
				.getCurrentLevel().getEvents(), this);
		setPFSize(getGame().getCurrentLevel().getLevelSize().x * 80, getGame()
				.getCurrentLevel().getLevelSize().y * 80);
		setBGImage(getGame().getCurrentLevel().getBackground());
		hud = new GamePlayerHUD(this);
	}

	public void setCollisionManager(CollisionManager collisionManager) {
		this.collisionManager = collisionManager;
	}

	public void doGravity(double mag) {
		for (GameObject obj : currentObjects) {
			checkCanJump(obj, mag);
			if (!obj.getIsFloating()) {
				obj.yspeed += mag;
			}
		}
	}
	
	private void checkCanJump(GameObject go, double gravVal){
		if(go.yspeed != 0 && go.yspeed != gravVal && go.yspeed != gravVal*2){
			go.setCanJump(false);
		}
		else {
			go.incrementJumpCounter();
			if(go.getJumpCounter() == 4){
				go.setCanJump(true);
				go.resetJumpCounter();
			}
		}
	}

	@Bind
	public void die() {
		if(canDie){
			currentGame.getCurrentLevel().resetTime();
			canDie=false;

			invincibilityCounter = counterInitial;
			if (getGame().getLives() == 0) {
				setGameState("GameOver");
			} else {
				getGame().setLives(getGame().getLives() - 1);
				endLevel(getGame().getNextLevelIndex() - 1);
			}
		}
	}

	@Bind
	public void youLose() {
		this.setGameState("GameOver");
	}

	@Bind
	public void speedUp() {
		this.setGameSpeed(2);
	}

	public JGPoint avgScreenPosition(List<GameObject> objs) {
		double xtot = 0;
		double ytot = 0;
		int count = 0;
		for (GameObject g : objs) {
			if (g.getIsScreenFollowing()) {
				xtot += g.x;
				ytot += g.y;
				count++;
			}
		}
		return new JGPoint((int) (xtot / count), (int) (ytot / count) - 100);
	}

	public Map<Tuple<Integer>, ArrayList<Tuple<GameObject>>> getCurrentCollisions() {
		return currentCollisions;
	}

	@Bind
	public void endLevel() {
		endLevel(getGame().getNextLevelIndex());
	}

	public List<GameObject> getObjectsByColid(int id) {
		ArrayList<GameObject> objects = new ArrayList<GameObject>();
		for (GameObject i : currentObjects) {
			if (i.colid == id)
				objects.add(i);
		}
		return objects;
	}

	public int getScore() {
		return getGame().getTotalScore();
	}

	public Game getGame() {
		return currentGame;
	}

	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}
}
