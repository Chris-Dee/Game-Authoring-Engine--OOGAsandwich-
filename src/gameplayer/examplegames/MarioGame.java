package gameplayer.examplegames;

import gameengine.Game;
import gameengine.Level;
import gameengine.UninstantiatedGameObject;
import gameengine.eventactions.ActivatePowerUpsAction;
import gameengine.eventactions.AddPowerUpAction;
import gameengine.eventactions.ChangeScoreAction;
import gameengine.eventactions.CollideSolidAction;
//import gameengine.eventactions.DieAction;
import gameengine.eventactions.EndLevelAction;
import gameengine.eventactions.InvalidEventActionException;
import gameengine.eventactions.KillPlayerAction;
import gameengine.eventactions.RemoveObjectsByCollisionAction;
import gameengine.gameevents.AdvancedCollisionEvent;
import gameengine.gameevents.BoundaryEvent;
import gameengine.gameevents.CentralTimerEndsEvent;
import gameengine.gameevents.CollisionEvent;
import gameengine.gameevents.InvalidEventException;
import gameengine.gameevents.KeyEvent;
import gameplayer.MovementParameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jgame.JGPoint;
import util.tuples.Tuple;
import data.InvalidDataFileException;

public class MarioGame extends Game {

	public MarioGame() throws IOException, InvalidDataFileException,
			ClassNotFoundException {

		setGameName("Mario Game");
		//mediaTablePath = "gameplayer/mario.tbl";
		// game.screenSize = new JGPoint(640, 480);
		screenSize = new JGPoint(900, 900);

		List<UninstantiatedGameObject> objs = new ArrayList<UninstantiatedGameObject>();
		int objectID = 0;
		
		objs.add(new UninstantiatedGameObject("Player", new JGPoint(50, 500),
				1, "mario", new MovementParameters("none", 0,0), false, true, objectID, 10));
		for (int i = 0; i < 900 / 32; i++) {
			objectID++;
			objs.add(new UninstantiatedGameObject("brick", new JGPoint(i * 32,
					550), 2, "brick", new MovementParameters("stationary", 0,0), true,false, objectID, 0));
		}
		objs.add(new UninstantiatedGameObject("moving platform", new JGPoint(
				200, 400), 2, "mobile", new MovementParameters("pace", 125, 2), true,false, objectID + 1, 0));
		objs.add(new UninstantiatedGameObject("stationary platform",
				new JGPoint(590, 350), 2, "stationary",new MovementParameters("stationary", 0,0), true, false,objectID + 2, 0));
		objs.add(new UninstantiatedGameObject("turtle", new JGPoint(300, 450),
				4, "turtle", new MovementParameters("pace", 55, 2), false,false, objectID + 3, 0));
		objs.add(new UninstantiatedGameObject("luigi", new JGPoint(600, 450),
				4, "luigi", new MovementParameters("stationary", 0,0),false, false, objectID + 4, 0));
		objs.add(new UninstantiatedGameObject("mushroom",
				new JGPoint(800, 400), 8, "mushroom", new MovementParameters("stationary", 0,0),true, false,objectID + 5, 0));
		objs.add(new UninstantiatedGameObject("coinone",
				new JGPoint(100, 200), 98, "lemon", new MovementParameters("stationary", 0,0),true, false,99, 0));
		objs.add(new UninstantiatedGameObject("cointwo",
				new JGPoint(300, 200), 98, "lemon", new MovementParameters("stationary", 0,0),true, false,99, 0));
		objs.add(new UninstantiatedGameObject("gunPowerUp",
				new JGPoint(350, 300), 101, "lime",new MovementParameters("stationary", 0,0), true,false, 99, 0));
		objs.add(new UninstantiatedGameObject("invincibilitypowerup",
				new JGPoint(450, 300), 102, "lime",new MovementParameters("stationary", 0,0), true,false ,99, 0));
		objs.add(new UninstantiatedGameObject("speedpowerup",
				new JGPoint(150, 500), 103, "lime",new MovementParameters("stationary", 0,0), true,false ,99, 0));
		
		
		Level firstLevel = new Level("first level", new JGPoint(4, 4), objs,
				"skyblue", .6,30);// ,new LevelInput(levelInputMap));

		List<UninstantiatedGameObject> objs2 = new ArrayList<UninstantiatedGameObject>();
		// objectID = 0;
		objs2.addAll(objs);
		/*
		 * objs2.add(new UninstantiatedGameObject("player", new JGPoint(50,
		 * 500),1, "mario", levelInputMap, false, true, objectID)); for(int i =
		 * 0; i < 900 /32; i++){ objectID++; objs2.add(new
		 * UninstantiatedGameObject("brick", new JGPoint(i * 32, 750), 2,
		 * "brick", true, objectID)); } objs2.add(new
		 * UninstantiatedGameObject("moving platform", new JGPoint(200, 600), 2,
		 * "mobile", "pace", 125, 2, true, objectID+1)); objs2.add(new
		 * UninstantiatedGameObject("stationary platform", new JGPoint(590,350),
		 * 2, "stationary", true, objectID+2)); objs2.add(new
		 * UninstantiatedGameObject("turtle", new JGPoint(300, 750), 4,"turtle",
		 * "pace", 55, 2, false, objectID+3)); objs2.add(new
		 * UninstantiatedGameObject("luigi", new JGPoint(600, 450), 4,
		 * "luigi",otherInputMap, false, objectID+4)); objs2.add(new
		 * UninstantiatedGameObject("mushroom", new JGPoint(800, 400), 8,
		 * "mushroom", true, objectID+5)); // This code will eventually be used
		 * to parse the data.
		 */
		// objs2.add(new UninstantiatedGameObject("turtle", new JGPoint(500,
		// 750), 4,"turtle", "pace", 55, 2, false, objectID+3,10));
		objs2.add(new UninstantiatedGameObject("moving platform", new JGPoint(
				100, 200), 2, "mobile", new MovementParameters("pace", 125, 2), true, false,objectID + 6, 0));
		objs2.add(new UninstantiatedGameObject("moving platform", new JGPoint(
				400, 200), 2, "mobile", new MovementParameters("pace", 125, 2), true,false, objectID + 6, 0));

		Level secondLevel = new Level("second level ya bish",
				new JGPoint(4, 4), objs2, "spacebackground", .6,30);
		
		KeyEvent qEvent;
		try{
			qEvent = new KeyEvent(Arrays.asList("" + (int) 'K'), "");
			try{
				ActivatePowerUpsAction activatePowerUpsAction = new ActivatePowerUpsAction();
				qEvent.addAction(activatePowerUpsAction);
				firstLevel.addEvent(qEvent);
				secondLevel.addEvent(qEvent);
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*
		TimerEvent event;
		try {
			event = new TimerEvent(Arrays.asList("10"), "");
			try {
				RemoveObjectAction action = new RemoveObjectAction(
                        new ArrayList<String>(Arrays.asList("0")));
				event.addAction(action);
				firstLevel.addEvent(event);
			} catch (InvalidEventActionException e) {
				e.printStackTrace();
			}
		} catch (InvalidEventException e1) {
			e1.printStackTrace();
		}
		*/
		CollisionEvent event1, event2, event4, event98, event101,event102,event103;
		AdvancedCollisionEvent event_1LandsTop4, event_1Touches4NotTop;
		CentralTimerEndsEvent event_timerEnds;
		try {
			event1 = new CollisionEvent(Arrays.asList(new String [] {"1", "2"}), "");
			event2 = new CollisionEvent(Arrays.asList("2", "4"), "");
			event_1LandsTop4 = new AdvancedCollisionEvent(Arrays.asList("1", "4", "true", "false", "false", "false", "false"), "");
			event_1Touches4NotTop = new AdvancedCollisionEvent(Arrays.asList("1", "4", "false", "true", "true", "true", "false"), "");
			event4 = new CollisionEvent(Arrays.asList("1", "8"), "");
			event98 = new CollisionEvent(Arrays.asList("1", "98"), ""); // coin
			event101 = new CollisionEvent(Arrays.asList("1", "101"), ""); //gun powerup
			event102 = new CollisionEvent(Arrays.asList("1", "102"), ""); //invincibility powerup
			event103 = new CollisionEvent(Arrays.asList("1", "103"), ""); //speed powerup
			event_timerEnds = new CentralTimerEndsEvent();
			
			
			try {
				CollideSolidAction action = new CollideSolidAction(
						new Tuple<Integer>(1, 2).toListString());
				CollideSolidAction action2 = new CollideSolidAction(
						new Tuple<Integer>(4, 2).toListString());
				KillPlayerAction action3 = new KillPlayerAction(
						new ArrayList<String>(Arrays.asList("1")));
				
				RemoveObjectsByCollisionAction action10 = new RemoveObjectsByCollisionAction(new ArrayList<String>(Arrays.asList("98", "1")));

				EndLevelAction action4 = new EndLevelAction(
						new ArrayList<String>());
				
				AddPowerUpAction action101 = new AddPowerUpAction(new ArrayList<String>(Arrays.asList("1", "101", "GunPowerUp","10","mario")));
				AddPowerUpAction action102 = new AddPowerUpAction(new ArrayList<String>(Arrays.asList("1", "102","InvincibilityPowerUp","10", "mobile")));
				AddPowerUpAction action103 = new AddPowerUpAction(new ArrayList<String>(Arrays.asList("1", "103","SpeedPowerUp","10", "mobile")));
				
				ChangeScoreAction action98 = new ChangeScoreAction(new ArrayList<String>(Arrays.asList("1")));
				RemoveObjectsByCollisionAction action_1Kills4 = new RemoveObjectsByCollisionAction(new ArrayList<String>(Arrays.asList("4", "1")));

				RemoveObjectsByCollisionAction action_1Kills103 = new RemoveObjectsByCollisionAction(new ArrayList<String>(Arrays.asList("103", "1")));
				
				RemoveObjectsByCollisionAction action_1Kills101 = new RemoveObjectsByCollisionAction(new ArrayList<String>(Arrays.asList("101", "1")));
				RemoveObjectsByCollisionAction action_1Kills102 = new RemoveObjectsByCollisionAction(new ArrayList<String>(Arrays.asList("102", "1")));
				//DieAction dieAction = new DieAction();
				
				event1.addAction(action);
				event2.addAction(action2);
				event_1Touches4NotTop.addAction(action3);
				event_1LandsTop4.addAction(action_1Kills4);
				event4.addAction(action4);
				event98.addAction(action98);
				event98.addAction(action10);
				event101.addAction(action_1Kills101);
				event101.addAction(action101);
				event102.addAction(action102);
				event102.addAction(action_1Kills102);
				event103.addAction(action103);
				event103.addAction(action_1Kills103);
				event_timerEnds.addAction(action3);
			} catch (InvalidEventActionException e) {
				e.printStackTrace();
			}
			firstLevel.addEvent(event1);
			firstLevel.addEvent(event2);
			firstLevel.addEvent(event_1Touches4NotTop);
			//firstLevel.addEvent(event_1Touches4NotTop);
			firstLevel.addEvent(event4);
			firstLevel.addEvent(event98);
			firstLevel.addEvent(event101);
			firstLevel.addEvent(event102);
			firstLevel.addEvent(event103);
			firstLevel.addEvent(event_1LandsTop4);
			firstLevel.addEvent(event_timerEnds);
			secondLevel.addEvent(event1);
			secondLevel.addEvent(event2);
			secondLevel.addEvent(event_1Touches4NotTop);
			secondLevel.addEvent(event4);
			secondLevel.addEvent(event_timerEnds);
		} catch (InvalidEventException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		BoundaryEvent event5, event6, event7;
		try {
			event5 = new BoundaryEvent(Arrays.asList("0", "900", "1"), "");
			event6 = new BoundaryEvent(Arrays.asList("1", "0", "1"), "");
			event7 = new BoundaryEvent(Arrays.asList("1", "700", "1"), "");
			KillPlayerAction action4;
			try {
				action4 = new KillPlayerAction(Arrays.asList("1"));
				event5.addAction(action4);
				event6.addAction(action4);
				event7.addAction(action4);
				firstLevel.addEvent(event5);
				firstLevel.addEvent(event6);
				firstLevel.addEvent(event7);
			} catch (InvalidEventActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InvalidEventException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Level> myLevels = new ArrayList<Level>();
		myLevels.add(0, firstLevel);
		myLevels.add(1, secondLevel);
		addListOfLevels(myLevels);
		setCurrentLevel(0);

	}
}
