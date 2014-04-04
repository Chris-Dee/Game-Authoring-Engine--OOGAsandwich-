package gameplayer;

import gameEngine.*;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.*;

import java.util.*;

public class GamePlayerGUI extends JGEngine{
	
	private Game currentGame;
	private List<GameObject> currentObjects;
	// TODO: Make a collection of levels so we can dynamically get the level's current objects
	private Level currentLevel;
	
	public GamePlayerGUI(){ //TODO: Allow passing in a Level to automatically start playing.
		currentGame = Game.getExample();
		initEngine(currentGame.getSize());
	}

	public GamePlayerGUI(Game loadedGame){
		currentGame = loadedGame;
		initEngine(currentGame.getSize());
	}

	/**
	 * This method runs before the engine initializes. This can be considered a
	 * replacement of the regular constructor. Typically, we only need to call
	 * setCanvasSettings here, and, optionally, setScalingPreferences() if we
	 * want to allow users to have that functionality. To the real display
	 * dimensions, use displayWidth/Height at this point. OOGASandwich treats
	 * each pixel as a tile.
	 */
	@Override
	public void initCanvas() {
		setCanvasSettings(displayWidth(), displayHeight(), 1, 1, null, null, null);
	}

	/**
	 * Define initializations after the engine is initialized. This method is 
	 * called by the game thread after initEngine() was called. Used to set
	 * initial game state and load in media.
	 */
	@Override
	public void initGame() {
		setFrameRate(30, 2);
		defineMedia(currentGame.mediaTablePath);
		setGameState("InGame");
	}
	
	/**
	 * One-time setup of the game.
	 */
	public void startInGame(){
		
		// TODO: make this all dependent on the current level
		
		constructGame(); //sets levels
		setPFSize(currentLevel.getLevelSize());
		setBGImage(currentLevel.getBackground());
		
		//initObjects();
		//myObject = new GameObject("test", 10, 10, 1, "hero-r");
	}

	public void doFrameInGame(){
		doLevel();
		moveObjects(
				null,// object name prefix of objects to move (null means any)
				1    // object collision ID of objects to move (0 means any)
				);
	}
	
	public void paintFrameInGame() {
		
		//TODO: Make this dependent on the current level (if necessary at all)
		
		drawString("Hello, World!",viewWidth()/2,90,0);
	}
	
	public void doLevel(){
		//does the frame of whatever the current level is and then updates the games objects
		
		//TODO: figure out more in-depth about how jgame tracks objects so we make sure updating them works right
		currentLevel.doFrame();
	}

	public void constructGame(){
		// This is where you take output from parser and construct the game and levels
		// This is temporary for testing.
		currentLevel = currentGame.getCurrentLevel();
		// TODO: Make this dependent on input from the data group.
		currentObjects = new ArrayList<GameObject>();
		for(UninstantiatedGameObject i : currentLevel.getObjects()){
			//TODO: Instantiate based on if sprite is on screen
			currentObjects.add(i.instantiate());
		}
	}
	

}
