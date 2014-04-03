package gamePlayer;

import gameEngine.*;
import jgame.JGPoint;
import jgame.platform.JGEngine;
import jgame.*;
import resources.*;

import java.util.*;

public class GamePlayerGUI extends JGEngine{
	
	private static List<Level> levels;
	private List<GameObject> currentObjects;
	private int currentLevel;
	public GameObject myObject;
	
	public static void main(String[]args) {
		new GamePlayerGUI(new JGPoint(640,480));
	}
	
	public GamePlayerGUI(JGPoint size){
		levels = new ArrayList<Level>();
		currentObjects = new ArrayList<GameObject>();
		currentLevel = 0;
		initEngine(size.x, size.y);
	}

	@Override
	public void initCanvas() {
		
		//TODO: maybe dependent on the level?
		
		//setCanvasSettings(getX(), getY(), 1, 1, null, null, null);
		setCanvasSettings(20,15,32,32,null,null,null);
	}

	@Override
	public void initGame() {
		setFrameRate(30, 2);
		defineMedia("tempTable.tbl");
		setGameState("InGame");
		//class creation through data (getMapOfObjects)
	}
	
	public void startInGame(){
		
		// TODO: make this all dependent on the current level
		
		constructGame(); //sets levels
		
		System.out.println("here");
		setPFSize(80,16);
		setBGImage("metal");
		
		//initObjects();
		//myObject = new GameObject("test", 10, 10, 1, "hero-r");
	}

	public void doFrameInGame(){
		doLevel(currentLevel);
		moveObjects(
				null,// object name prefix of objects to move (null means any)
				1    // object collision ID of objects to move (0 means any)
				);
	}
	
	public void paintFrameInGame() {
		
		//TODO: Make this dependent on the current level (if necessary at all)
		
		drawString("Hello, World!",viewWidth()/2,90,0);
	}
	
	public void doLevel(int levelnum){
		//does the frame of whatever the current level is and then updates the games objects
		
		//TODO: figure out more in-depth about how jgame tracks objects so we make sure updating them works right
		
		levels.get(levelnum).doFrame();
		currentObjects = levels.get(levelnum).getObjects();
	}
	
	public void constructGame(){
		// This is where you take output from parser and construct the game and levels
		// This is temporary for testing.
		
		// TODO: Make this dependent on input from the data group.
		
		List<GameObject> objs = new ArrayList<GameObject>();
		//GameObject obj1 = new GameObject("test", 10, 10, 1, "hero-r");
		myObject = new GameObject("test", 10, 10, 1, "hero-r");
		objs.add(myObject);
		List<GameForce> forces = new ArrayList<GameForce>();
		GameForce force1 = new GameForce();
		forces.add(force1);
		Level firstlevel = new Level(objs, forces, "metal");
		levels.add(firstlevel);
		
	}
	

}
