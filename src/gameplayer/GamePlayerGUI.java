package gameplayer;

import gameEngine.*;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import data.InvalidDataFileException;

public class GamePlayerGUI extends JGEngine{

	private Game currentGame = new Game();
	private List<GameObject> currentObjects;
	// TODO: Make a collection of levels so we can dynamically get the level's current objects
	private Level currentLevel;
	private boolean levelOver = false;

	public GamePlayerGUI(Game loadedGame){
		currentGame = loadedGame;
		initEngine(currentGame.getSize());
	}

	/**
	 * This method runs before the engine initializes. This can be considered a
	 * replacement of the regular constructor. We only need to call
	 * setCanvasSettings here.
	 */
	@Override
	public void initCanvas() {
		setCanvasSettings(100,100,displayWidth()/100,displayHeight()/100,null,null,null);
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
		constructGame(); //sets levels
		setPFSize(100, 100); // What does PFSize actually do?
		setBGImage(currentLevel.getBackground());
	}

	/**
	 * Run every frame while game state is InGame.
	 */
	public void doFrameInGame(){
		doLevel();
		moveObjects(
				null,// object name prefix of objects to move (null means any)
				0    // object collision ID of objects to move (0 means any)
				);
		checkCollisions();
	}

	public void paintFrameInGame() {
		// TODO: Add display of stats, status, points, etc.
		//TODO: Make this dependent on the current level (if necessary at all)
		String levelText = "Test Level";
		if(levelOver)
			levelText = "Level Complete";
		drawString(levelText,viewWidth()/2,90,0);
	}

	public void doLevel(){
		//does the frame of whatever the current level is and then updates the games objects

		//TODO: figure out more in-depth about how jgame tracks objects so we make sure updating them works right
		// currentLevel.doFrame();
		doInputs();
		doGravity(currentLevel.getGravityVal());
		
	}

	public void checkCollisions(){
		for(BasicCollision i: currentGame.collisionRules){
			ArrayList<Tuple<GameObject>> temp = getCollisions(i.colid1, i.colid2);
			for(Tuple<GameObject> j: temp){
				i.mod1.apply(j.x, j.y);
				//i.mod2.apply(j.y, j.x);
			}
		}
		for(TriggerCollision i: currentGame.collisionTriggers){
			ArrayList<Tuple<GameObject>> temp = getCollisions(i.colid1, i.colid2);
			for(Tuple<GameObject> j: temp){
				if(i.behavior == "endlevel"){
					endLevel();
				}else if(i.behavior == "reset"){
					j.x.reset();
				}
			}
		}
	}
	public ArrayList<Tuple<GameObject>> getCollisions(int colid1, int colid2){
		ArrayList<Tuple<GameObject>> collisions = new ArrayList<Tuple<GameObject>>();
		for(GameObject i: currentObjects){
			for(GameObject j: currentObjects){
				if(i.colid == colid1 && j.colid == colid2){
					if(i.getBBox().intersects(j.getBBox())){
						collisions.add(new Tuple<GameObject>(i, j));
					}
				}
			}
		}
		return collisions;
	}
	
	private void endLevel(){
		//TODO: wrap up the current level and go to the next one
		for(GameObject i: currentObjects){
			i.remove();
		}
		levelOver = true;
		//TODO: Call something to construct the new level and switch to that new level
	}

	public void constructGame(){
		// This is where you take output from parser and construct the game and levels
		// This is temporary for testing.
		currentLevel = currentGame.getCurrentLevel();
		//currentLevelInput = currentLevel.getLevelInput();
		// TODO: Make this dependent on input from the data group.
		currentObjects = new ArrayList<GameObject>();
		for(GameObject i : currentLevel.getObjects()){
			//TODO: Instantiate based on if sprite is on screen
			currentObjects.add((GameObject)i.activate());
		}
		levelOver = false;
	}
	
	public void doGravity(double mag){
		for(GameObject obj : currentObjects){
			if(!obj.getIsFloating()){
				obj.yspeed += mag;
			}
		}
	}

	public void doInputs(){
		//System.out.println(getLastKey());
		for(GameObject obj: currentObjects){
			GameObjectAction move= obj.getMovement();
			Map<Integer, String> characterMap =obj.getCharMap();
			if(characterMap!=null){
				boolean keyPressed=false;
				for(Integer c : characterMap.keySet()){
					if(getKey(c)){
						keyPressed=true;
						java.lang.reflect.Method method = null;
						try {
							//System.out.println(characterMap.get(c).get(obj.getFuckingName()));
							method = move.getClass().getMethod(characterMap.get(c), GameObject.class);
						} catch (SecurityException e) {
						} catch (NoSuchMethodException e) {}	
						try {
							method.invoke(move, obj);
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {}
					}
				}
				if (!keyPressed){
						java.lang.reflect.Method method = null;
						try {
							//System.out.println(characterMap.get(c).get(obj.getFuckingName()));
							method = move.getClass().getMethod("stopMovement", GameObject.class);
						} catch (SecurityException e) {
							System.out.println("ex1");
						} catch (NoSuchMethodException e) {System.out.println("ex2");}	
						try {
							method.invoke(move, obj);
						} catch (IllegalArgumentException e) {
							System.out.println("ex3");
						} catch (IllegalAccessException e) {
							System.out.println("ex4");
						} catch (InvocationTargetException e) {System.out.println("ex5");}
				}
			}

		}

	}

}
