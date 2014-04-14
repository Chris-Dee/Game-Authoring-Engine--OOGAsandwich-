package gameplayer;

import gameEngine.*;
import jgame.JGColor;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import data.InvalidDataFileException;

public class GamePlayerGUI extends JGEngine{

	private Game currentGame;
	private List<GameObject> currentObjects;
	// TODO: Make a collection of levels so we can dynamically get the level's current objects
	//private Level currentLevel;
	private GameEventManager eventManager;


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
		constructLevel(); //sets levels
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
		setViewOffset((int)avgScreenX(currentObjects),(int)avgScreenY(currentObjects),true);
	}

	public void paintFrameInGame() {
		// TODO: Add display of stats, status, points, etc.
		//TODO: Make this dependent on the current level (if necessary at all)
		String levelText = currentGame.getCurrentLevel().getLevelName();
		drawString(levelText,viewWidth()/2,90,0);
		paintScore();
	}

	public void paintScore() {
		//setColor(JGColor.red);
		String score="Score: "+ currentGame.getCurrentLevel().getCurrentScore();
		String hitPoints="Hit Points: "+ currentObjects.get(0).getHitPoints();
		drawString(score,3*viewWidth()/4,24,0);
		drawString(hitPoints,1*viewWidth()/4,24,0);
	}

	public void doLevel(){
		//does the frame of whatever the current level is and then updates the games objects

		//TODO: figure out more in-depth about how jgame tracks objects so we make sure updating them works right
		// currentLevel.doFrame();
		doInputs();
		doGravity(currentGame.getCurrentLevel().getGravityVal());
		//		setViewOffset((int)avgScreenX(currentObjects),(int)avgScreenY(currentObjects),true);
		eventManager.check();
	}

	private List<GameObject> findTargets(List<Integer> targetIDs){
		List<GameObject> targetObjs = new ArrayList<GameObject>();
		for(Integer i : targetIDs){
			for(GameObject obj : currentObjects){
				if(obj.getID() == i){
					targetObjs.add(obj);
				}
			}
		}
		return targetObjs;
	}

	/*
	public void checkGoals(){
		for(Goal i: levelGoals){
			List<GameObject> goalObjs = findTargets(i.getTargets());
			if(i.checkGoal(goalObjs)){
				endLevel(i.getNextLevel());
			}
		}
	}
	*/
	public void checkCollisions(){
		for(BasicCollision i: currentGame.collisionRules){
			ArrayList<Tuple<GameObject,GameObject>> temp = getCollisions(i.colid1, i.colid2);
			for(Tuple<GameObject,GameObject> j: temp){
				i.mod1.apply(j.x, j.y);
				//i.mod2.apply(j.y, j.x);
			}
		}
		for(TriggerCollision i: currentGame.collisionTriggers){
			ArrayList<Tuple<GameObject,GameObject>> temp = getCollisions(i.colid1, i.colid2);
			for(Tuple<GameObject,GameObject> j: temp){
				if(i.behavior == "endlevel"){
					endLevel(currentGame.getNextLevelIndex());
				}else if(i.behavior == "reset"){
					j.x.reset();
					j.x.changeHitPoints(-1);
					currentGame.getCurrentLevel().changeScore(-1);
				}
			}
		}
	}
	public ArrayList<Tuple<GameObject,GameObject>> getCollisions(int colid1, int colid2){
		ArrayList<Tuple<GameObject,GameObject>> collisions = new ArrayList<Tuple<GameObject,GameObject>>();
		for(GameObject i: currentObjects){
			for(GameObject j: currentObjects){
				if(i.colid == colid1 && j.colid == colid2){
					if(i.getBBox().intersects(j.getBBox())){
						collisions.add(new Tuple<GameObject,GameObject>(i, j));
					}
				}
			}
		}
		return collisions;
	}

	public HashMap<Tuple<Integer, Integer>, ArrayList<Tuple<GameObject, GameObject>>> getCollisions(ArrayList<Tuple<Integer, Integer>> collisions){
		HashMap<Tuple<Integer, Integer>, ArrayList<Tuple<GameObject, GameObject>>> collisionsByCollisionIDs = new HashMap<Tuple<Integer, Integer>, ArrayList<Tuple<GameObject,GameObject>>>();
		for(Tuple<Integer, Integer> collisionIDs: collisions){
			collisionsByCollisionIDs.put(collisionIDs, new ArrayList<Tuple<GameObject,GameObject>>());
		}
		for(GameObject i: currentObjects){
			for(GameObject j: currentObjects){
				if(i != j){
					Tuple<Integer, Integer> collisionRelation = new Tuple<Integer, Integer>(i.colid, j.colid);
					if(collisionsByCollisionIDs.containsKey(collisionRelation)){
						collisionsByCollisionIDs.get(collisionRelation).add(new Tuple<GameObject,GameObject>(i, j));
					}
				}
			}
		}
		return collisionsByCollisionIDs;
	}
	
	private void endLevel(int nextLevelIndex){
		//TODO: wrap up the current level and go to the next one
		for(GameObject i: currentObjects){
			i.remove();
		}
		currentGame.setCurrentLevel(nextLevelIndex);
		constructLevel();
	}

	public void constructLevel(){
		// This is where you take output from parser and construct the game and levels
		// This is temporary for testing.
		//currentLevel = currentGame.getCurrentLevel();
		//currentLevelInput = currentLevel.getLevelInput();
		// TODO: Make this dependent on input from the data group.
		currentObjects = new ArrayList<GameObject>();;
		for(UninstantiatedGameObject i : currentGame.getCurrentLevel().getObjects()){
			//TODO: Instantiate based on if sprite is on screen

			currentObjects.add(i.instantiate());
			//			if(myObject.getFuckingName().equals("goal")){
			//				levelGoals.add((Goal) myObject);
			//			}
			//			else
			//				currentObjects.add(myObject);
		}
		eventManager = new GameEventManager(currentObjects, currentGame.getCurrentLevel().getEvents(), this);
		setPFSize(currentGame.getCurrentLevel().getLevelSize().x*100, currentGame.getCurrentLevel().getLevelSize().y*100);
		//setPFSize(1000, 1000); // What does PFSize actually do? // Sam - I need the correct size for scrolling to work
		setBGImage(currentGame.getCurrentLevel().getBackground());
	}

	public void doGravity(double mag){
		for(GameObject obj : currentObjects){
			if(!obj.getIsFloating()){
				obj.yspeed += mag;
			}
		}
	}

	public double avgScreenX(List<GameObject> objs){
		double xtot = 0;
		int cnt = 0;
		for(GameObject g : objs){
			if(g.getIsScreenFollowing()){
				xtot += g.x;
				cnt++;
			}
		}
		return xtot / cnt;
	}

	public double avgScreenY(List<GameObject> objs){
		double ytot = 0;
		int cnt = 0;
		for(GameObject g : objs){
			if(g.getIsScreenFollowing()){
				ytot += g.y;
				cnt++;
			}
		}
		return ytot / cnt;
	}

	public void doInputs(){
		//System.out.println(getLastKey());
		for(GameObject obj: currentObjects){
			GameObjectAction move= obj.getMovement();
			Map<Integer, MethodData<String,Integer>> characterMap =obj.getCharMap();
			if(characterMap!=null){
				boolean keyPressed=false;
				for(Integer c : characterMap.keySet()){
					if(getKey(c)){

						keyPressed=true;
						java.lang.reflect.Method method = null;
						try {
							//System.out.println(characterMap.get(c).get(obj.getFuckingName()));
							method = move.getClass().getMethod(characterMap.get(c).x, GameObject.class, int[].class);
						} catch (SecurityException e) {
						} catch (NoSuchMethodException e) {
							System.out.println("wtf1");
						}	
						try {
							method.invoke(move, obj, (Object) characterMap.get(c).y);
						} catch (IllegalArgumentException e) {
							System.out.println("wtf2");
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {}
					}
				}
				if (!keyPressed){
					obj.xspeed=0;
				}
			}

		}
		clearKey('Z');
		clearKey('X');
		if (getKey('O')){
			endLevel(currentGame.getNextLevelIndex());
			clearKey('O');
		}

	}

}
