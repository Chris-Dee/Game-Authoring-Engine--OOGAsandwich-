package gameplayer;

import gameEngine.Game;
import gameEngine.GameForce;
import gameEngine.GameObject;
import gameEngine.GameObjectAction;
import gameEngine.Goal;
import gameEngine.Level;
import gameEngine.UninstantiatedGameObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jgame.JGPoint;
import data.InvalidDataFileException;

public class GravityTestGame extends Game{
	public GravityTestGame() throws IOException, InvalidDataFileException{
		mediaTablePath = "tempTable.tbl";
		//game.screenSize = new JGPoint(640, 480);
		screenSize = new JGPoint(1440, 900);
		
		Map<Integer, Tuple<String,Integer>> levelInputMap= new HashMap<Integer, Tuple<String,Integer>>();
		levelInputMap.put(39, new Tuple<String, Integer>("moveRight",4));
		levelInputMap.put(37,new Tuple<String, Integer>("moveLeft",4));
		levelInputMap.put(38, new Tuple<String, Integer>("moveUp",6));
		levelInputMap.put(40, new Tuple<String, Integer>("moveDown",6));
		int[][] modMatrix = {{1, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, -1, 0, 1, 0, 0}};
		collisionRules.add(
				new BasicCollision(1, 2,
						new GameObjectModification(modMatrix, 1, 0)));
		
		List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();

		//objs.add(new GameObject("test", new JGPoint(20, 105), 1, "hero-r", new GameObjectAction(4,1)));
		objs.add(new UninstantiatedGameObject("player", new JGPoint(10, 10), 1, "hero-r", levelInputMap, false));
		objs.add(new UninstantiatedGameObject("test", new JGPoint(100, 100), 1, "hero-r", "pace",25, 5, true));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(20, 155), 2, "mytile", levelInputMap, false));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(25, 135), 2, "mytile", "pace",75, 2, true));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(30, 125), 2, "mytile", true));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(50, 125), 2, "mytile", true));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(70, 125), 2, "mytile", true));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(150, 125), 2, "mytile", true));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(220, 125), 2, "mytile", true));
		objs.add(new UninstantiatedGameObject("land", new JGPoint(280, 225), 2, "mytile", true));
//		objs.add(new GameObject("goal", new JGPoint(130, 425), 2, "mytile", new Goal("end", 700), true));
		
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
		
	
		
		Level firstLevel = new Level("first level", new JGPoint(640, 480), objs, forces, "metal", .4);//,new LevelInput(levelInputMap));

		
		
		addLevel(firstLevel);
		setCurrentLevel(firstLevel);
	}
}
