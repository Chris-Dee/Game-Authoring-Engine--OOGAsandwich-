package gameauthoringenvironment.leveleditor;

import java.util.List;
import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import gameengine.GameObject;
import gameengine.Level;
import gameengine.UninstantiatedGameObject;
import gameengine.eventactions.AddPowerUpAction;
import gameengine.eventactions.ChangeScoreAction;
import gameengine.eventactions.CollideSolidAction;
import gameengine.eventactions.DecreaseObjectHitPointsByCollisionAction;
import gameengine.eventactions.EndLevelAction;
import gameengine.eventactions.InvalidEventActionException;
import gameengine.eventactions.KillPlayerAction;
import gameengine.eventactions.RemoveObjectAction;
import gameengine.eventactions.RemoveObjectsByCollisionAction;
import gameengine.eventactions.ResetObjectAction;
import gameengine.gameevents.AdvancedCollisionEvent;
import gameengine.gameevents.BoundaryEvent;
import gameengine.gameevents.CollisionEvent;
import gameengine.gameevents.InvalidEventException;
import gameengine.gameevents.TimerEvent;
import gameplayer.MovementParameters;

import javax.swing.JPanel;

import util.tuples.Tuple;
import jgame.JGColor;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.JGRectangle;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
	private static final String PLAYER_HITS_BOTTOM_OF_SCREEN_STRING = "playerhitsbottomofscreen";
	private static final String PLAYER_HITS_SIZE_POWER_UP_STRING = "playerhitssizepowerup";
	private static final String SIZE_POWER_UP_STRING = "SizePowerUp";
	private static final String PLAYER_HITS_SPEED_POWERUP_STRING = "playerhitsspeedpowerup";
	private static final String SPEED_POWER_UP_STRING = "SpeedPowerUp";
	private static final String SRBALL_STRING = "srball";
	private static final int GRAVITY_OFFSET_VALUE = 10;
	private static final int MOVEMENT_DURATION_SCALING_CONSTANT = 10;
	private static final String INVINCIBILITY_POWERUP_STRING = "InvincibilityPowerUp";
	private static final String GUN_POWERUP_STRING = "GunPowerUp";
	private static final String PLAYER_HITS_INVINCIBILITY_POWERUP_STRING = "playerhitsinvincibilitypowerup";
	private static final String PLAYER_HITS_GUN_POWERUP_STRING = "playerhitsgunpowerup";
	private static final String BULLET_HITTING_ENEMY_STRING = "bullethittingenemy";
	private static final String PLAYER_COLLECTING_COIN_STRING = "playercollectingcoin";
	private static final String PLAYER_HITTING_GOAL_STRING = "playerhittinggoal";
	private static final String ENEMY_KILLING_PLAYER_STRING = "enemykillingplayer";
	private static final String PLAYER_JUMPING_ON_ENEMY_STRING = "playerjumpingonenemy";
	private static final String ENEMY_ON_BLOCK_STRING = "enemyonblock";
	private static final String PLAYER_LANDING_ON_BLOCK_STRING = "playerlandingonblock";
	private static final int THICKNESS_DEFAULT_FOR_RECT = 2;
	private static final int HEIGHT_FOR_JGRECTANGLE = 10;
	private static final int WIDTH_FOR_JGRECTANGLE = 10;
	private static final int DEFAULT_CLICK_COUNT_VALUE = 30;
	private static final int LEFT_CLICK_VALUE = 1;
	private static final int MAX_FRAME_SKIP = 3;
	private static final int FRAMES_PER_SECOND = 250;
	public static final int SCREEN_HEIGHT = 700;
	public static final int SCREEN_WIDTH = 700;
	public static final int BLOCK_SIZE_X = 320;
	public static final int BLOCK_SIZE_Y = 320;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_X_LEFTSIDE = 0;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_X_RIGHTSIDE = 32;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_Y_TOP = 0;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_Y_BOTTOM = 31;
	private static final String defaultImage = "/projectResources/red.gif";
	private static final String IMAGE_TO_OBJECT_RESOURCES = "imagetoobject";
	private static final String GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE = "gameAuthoringEnvironment.levelEditor.";
	public static int NRTILESX = 2;
	public static int NRTILESY = 2;
	private int clickCount = 0;
	private int objectID;
	private LevelMover myMover;
	private Level myLevel;
	private ObjectStatsPanel myObjectStatsPanel;
	private LevelStatsBar myLevelObjectBar;
	private ResourceBundle myResourcesForImageToObject;
	private List<GameObject> selectedObjects = new CopyOnWriteArrayList<GameObject>();
	private Map<String, ObjectStats> myImageToObjectStatsMap;

	/**
	 * JGame class that holds the level editor. This displays what the created
	 * level currently looks like.
	 * 
	 * @param level
	 *            Level being edited.
	 */
	public LevelEditor(Level level) {
		super();
		myLevel = level;
		// hide null pointer exceptions if needed
		dbgShowMessagesInPf(false);
		objectID = 0;
		dbgIsEmbedded(true);
		initEngine((int) SCREEN_WIDTH, SCREEN_HEIGHT);
		
		defineMedia("projectresources/tempTable.tbl");
		defineImage(SRBALL_STRING, "n", 0, defaultImage, "-");
		myMover = new LevelMover(this);
		myResourcesForImageToObject = ResourceBundle
				.getBundle(GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE
						+ IMAGE_TO_OBJECT_RESOURCES);
		myImageToObjectStatsMap = new HashMap<String, ObjectStats>();
		fillImageToObjectStatsMap();
		setBGImage(myLevel.getBackground());
	}

	/**
	 * Returns the level being edited in this LevelEditor.
	 * 
	 * @return The Level being edited.
	 */
	public Level getLevel() {
		return myLevel;
	}

	/**
	 * Creates events that correspond to the events found in a Mario game.
	 */
	public void setMarioEvents() {
		CollisionEvent event1, event2, event4, event98, event6, event7, event8, event9;
		AdvancedCollisionEvent event_1LandsTop4, event_1Touches4NotTop, event5;
		try {
			// player with block
			event1 = new CollisionEvent(
					Arrays.asList(new String[] { "0", "1" }),
					PLAYER_LANDING_ON_BLOCK_STRING);
			// enemy with block
			event2 = new CollisionEvent(Arrays.asList("1", "2"),
					ENEMY_ON_BLOCK_STRING);
			// player killing enemy
			event_1LandsTop4 = new AdvancedCollisionEvent(Arrays.asList("0",
					"2", "true", "false", "false", "false", "false"),
					PLAYER_JUMPING_ON_ENEMY_STRING);
			// enemy kills player
			event_1Touches4NotTop = new AdvancedCollisionEvent(Arrays.asList(
					"0", "2", "false", "true", "true", "true", "false"),
					ENEMY_KILLING_PLAYER_STRING);
			// player reaches goal
			event4 = new CollisionEvent(Arrays.asList("0", "3"),
					PLAYER_HITTING_GOAL_STRING);
			// player collects coin
			event98 = new CollisionEvent(Arrays.asList("0", "5"),
					PLAYER_COLLECTING_COIN_STRING);
			// bullet hits enemy
			event5 = new AdvancedCollisionEvent(Arrays.asList("2", "150",
					"true", "true", "true", "true", "true"),
					BULLET_HITTING_ENEMY_STRING);
			// player gets gun power up
			event6 = new CollisionEvent(Arrays.asList("0", "6"),
					PLAYER_HITS_GUN_POWERUP_STRING);
			// player gets invincibility power up
			event7 = new CollisionEvent(Arrays.asList("0", "7"),
					PLAYER_HITS_INVINCIBILITY_POWERUP_STRING);
			// player gets size power up
			event8 = new CollisionEvent(Arrays.asList("0", "8"),
					PLAYER_HITS_SIZE_POWER_UP_STRING);
			// player gets speed power up
			event9 = new CollisionEvent(Arrays.asList("0", "9"),
					PLAYER_HITS_SPEED_POWERUP_STRING);
			AddPowerUpAction action13 = new AddPowerUpAction(
					new ArrayList<String>(Arrays.asList("0", "8",
							SIZE_POWER_UP_STRING, "10", "mario")));
			AddPowerUpAction action12 = new AddPowerUpAction(
					new ArrayList<String>(Arrays.asList("0", "9",
							SPEED_POWER_UP_STRING, "10", "bullet")));

			DecreaseObjectHitPointsByCollisionAction action14 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("8", "0")));
			DecreaseObjectHitPointsByCollisionAction action15 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("9", "0")));
			DecreaseObjectHitPointsByCollisionAction action9 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("6", "0")));
			DecreaseObjectHitPointsByCollisionAction action11 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("7", "0")));
			AddPowerUpAction action7 = new AddPowerUpAction(
					new ArrayList<String>(Arrays.asList("0", "6",
							GUN_POWERUP_STRING, "10", "mario")));
			AddPowerUpAction action8 = new AddPowerUpAction(
					new ArrayList<String>(Arrays.asList("0", "7",
							INVINCIBILITY_POWERUP_STRING, "10", "mobile")));
			CollideSolidAction action = new CollideSolidAction(
					new Tuple<Integer>(0, 1).toListString());
			CollideSolidAction action2 = new CollideSolidAction(
					new Tuple<Integer>(2, 1).toListString());
			KillPlayerAction action3 = new KillPlayerAction(
					new ArrayList<String>(Arrays.asList("0")));

			DecreaseObjectHitPointsByCollisionAction action10 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("5", "0")));
			DecreaseObjectHitPointsByCollisionAction action5 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("150", "2")));
			DecreaseObjectHitPointsByCollisionAction action6 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("2", "150")));
			EndLevelAction action4 = new EndLevelAction(new ArrayList<String>());

			ChangeScoreAction action98 = new ChangeScoreAction(
					new ArrayList<String>(Arrays.asList("5")));
			DecreaseObjectHitPointsByCollisionAction action_1Kills4 = new DecreaseObjectHitPointsByCollisionAction(
					new ArrayList<String>(Arrays.asList("2", "0")));

			event1.addAction(action);
			event2.addAction(action2);
			event_1Touches4NotTop.addAction(action3);
			event_1LandsTop4.addAction(action_1Kills4);
			event4.addAction(action4);
			event98.addAction(action98);
			event98.addAction(action10);
			event5.addAction(action5, action6);
			event6.addAction(action7, action9);
			event7.addAction(action8, action11);
			event9.addAction(action12, action15);
			event8.addAction(action13, action14);
			myLevel.addEvent(event1, event2, event_1Touches4NotTop, event4,
					event98, event_1LandsTop4, event5, event6, event7, event8,
					event9);
			myLevelObjectBar.initializeEventsMap(myLevel.getEvents());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the list of selected objects in the level editor.
	 * 
	 * @return List of selected objects.
	 */
	public List<GameObject> getSelected() {
		return selectedObjects;
	}

	/**
	 * Sets the ObjectStatsPanel for the LevelEditor.
	 * 
	 * @param panel
	 *            ObjectStatsPanel to be set.
	 */

	public void setObjectStatsPanel(ObjectStatsPanel panel) {
		myObjectStatsPanel = panel;
	}

	/**
	 * Sets the LevelObjectBar corresponding to this LevelEditor.
	 * 
	 * @param bar
	 *            The LevelObjectBar to set.
	 */
	public void setLevelObjectBar(LevelStatsBar bar) {
		myLevelObjectBar = bar;
	}

	/**
	 * Sets the stats of the ObjectStatsPanel to be the default stats of the
	 * imageName specified.
	 * 
	 * @param imageName
	 *            Name of image to be accessed.
	 */
	public void setStats(String imageName) {
		myObjectStatsPanel.setStats(myImageToObjectStatsMap.get(imageName));
	}

	/**
	 * Returns the ObjectStats for the imageName specified.
	 * 
	 * @param imageName
	 *            Name of the image to be accessed.
	 * @return ObjectStats with the stats for the given imageName.
	 */
	public ObjectStats getStatsForImage(String imageName) {
		return myImageToObjectStatsMap.get(imageName);
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(NRTILESX, NRTILESY, BLOCK_SIZE_X, BLOCK_SIZE_Y, null,
				JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(FRAMES_PER_SECOND, MAX_FRAME_SKIP);
		// width in spot 0, height in spot 1
		setPFSize(myLevel.getLevelSize().y, myLevel.getLevelSize().x);
	}

	/**
	 * Gets the LevelMover from this LevelEditor.
	 * 
	 * @return The LevelMover stored in the LevelEditor.
	 */
	public LevelMover getMover() {
		return myMover;
	}

	/**
	 * Returns the key status of the key and gets the key using the JGame getKey
	 * method.
	 * 
	 * @param key
	 *            Int defining the key that is pressed.
	 * @return Returns true if key status exists.
	 */
	public boolean checkKey(int key) {
		Boolean b = getKey(key);
		clearLastKey();
		return b;
	}

	/**
	 * Sets the selected object name to the specified text.
	 * 
	 * @param text
	 *            The text for the object name to be set to.
	 */
	public void setObjectName(String text) {
		if (selectedObjects.size() > 0)
			selectedObjects.get(0).changeName(text);

	}

	/**
	 * Deletes the GameObject that is specified.
	 * 
	 * @param g
	 *            GameObject to be deleted.
	 */
	public void deleteObject(GameObject g) {
		myLevel.getObjects().remove(g.toUninstantiated());
		g.remove();
		selectedObjects.remove(g);
	}

	/**
	 * Adds the default stats of a user-created image to the
	 * imageToObjectStatsMap.
	 * 
	 * @param imgName
	 *            Name of the image to be added.
	 */
	public void setDefaults(String imgName) {
		myImageToObjectStatsMap.put(imgName, myObjectStatsPanel.exportStats());
	}

	/**
	 * Adds an object to the level and instantiates it
	 * 
	 * @param imageName
	 *            name of image
	 * @param x
	 *            x position of image
	 * @param y
	 *            y position of image
	 */
	public GameObject addObject(String imageName, int x, int y, boolean updateID) {
		MovementParameters myMove = new MovementParameters(
				myObjectStatsPanel.getMovementName(),
				myObjectStatsPanel.getMovementDuration()
						* MOVEMENT_DURATION_SCALING_CONSTANT,
				myObjectStatsPanel.getMovementSpeed());
		UninstantiatedGameObject newObject = new UninstantiatedGameObject(
				myObjectStatsPanel.getObjectName(), new JGPoint(x, y),
				myObjectStatsPanel.getCollisionID(), imageName, myMove,
				myObjectStatsPanel.getFloating(),
				myObjectStatsPanel.getScreenFollow(), objectID,
				myObjectStatsPanel.getHitPoints());
		newObject.setMovementName(myObjectStatsPanel.getMovementName());
		objectID++;
		addObjectsToLevel(newObject);
		return newObject.instantiate(updateID);
	}

	/**
	 * Sets the gravity of the level to the specifed value.
	 * 
	 * @param value
	 *            Value to be used for gravity
	 */
	public void setGravity(double value) {
		myLevel.setGravityVal(value / GRAVITY_OFFSET_VALUE);
	}

	/**
	 * Adds a GameObject to the list of selectedObjects.
	 * 
	 * @param g
	 *            GameObject to be added.
	 */
	public void addSelectedObject(GameObject g) {
		selectedObjects.add(g);
	}

	/**
	 * Clears the list of the selectedObjects
	 */
	public void clearGame() {
		selectedObjects.clear();
	}

	public void doFrame() {
		if (myObjectStatsPanel != null) {
			myObjectStatsPanel.setIDAndNameTextBoxes(selectedObjectObjectID(),
					selectedObjectColID(), selectedObjectName());
		}
		deleteSelectedObject();
		placeObject();
		if (myMover != null)
			checkInBounds();
		moveObjects(null, 0);
		if (myMover != null)
			setViewOffset((int) myMover.x, (int) myMover.y, true);
		selectOnClick();
	}

	public void paintFrame() {
		for (GameObject s : selectedObjects) {
			highlight(s, JGColor.red);
		}
		highlight(myMover, JGColor.blue);
	}

	private String selectedObjectObjectID() {
		if (selectedObjects.isEmpty()) {
			return "";
		}
		return Integer.toString(selectedObjects.get(0).getID());
	}

	private String selectedObjectColID() {
		if (selectedObjects.isEmpty()) {
			return "";
		}
		return Integer.toString(selectedObjects.get(0).getColID());
	}

	private String selectedObjectName() {
		if (selectedObjects.isEmpty()) {
			return "";
		}
		return selectedObjects.get(0).getName();
	}

	private void placeObject() {
		if (checkKey(KeyEnter) && !myMover.getImageName().equals(SRBALL_STRING)) {
			clearKey(KeyEnter);
			addObject(myMover.getImageName(), (int) myMover.x, (int) myMover.y,
					true);
		}
	}

	private void fillImageToObjectStatsMap() {
		for (String str : myResourcesForImageToObject.keySet()) {
			myImageToObjectStatsMap.put(str, new ObjectStats(
					myResourcesForImageToObject.getString(str).split(",")));
		}
	}

	private void deleteSelectedObject() {
		if (checkKey(KeyBackspace)) {
			for (GameObject s : selectedObjects) {
				deleteObject(s);
			}
		}
	}

	private void checkInBounds() {
		if (myMover.getBBox() != null) {
			if (myMover.x >= myMover.pfwidth) {
				myMover.x = myMover.x - myMover.getBBox().width;
			}
			if (myMover.y >= myMover.pfheight) {
				myMover.y = myMover.y - myMover.getBBox().height;
			}
			if (myMover.x <= 0) {
				myMover.x = myMover.x + myMover.getBBox().width;
			}
			if (myMover.y <= 0) {
				myMover.y = myMover.y + myMover.getBBox().height;
			}
		}
	}

	private void selectOnClick() {
		if (getMouseButton(LEFT_CLICK_VALUE)) {
			if (clickCount != 0)
				clickCount--;
			else {
				clickCount = DEFAULT_CLICK_COUNT_VALUE;
				JGRectangle rect = new JGRectangle(getMouseX() + el.xofs,
						getMouseY() + el.yofs, WIDTH_FOR_JGRECTANGLE,
						HEIGHT_FOR_JGRECTANGLE);
				Vector<GameObject> v = getObjects("", 0, true, rect);
				if (v.size() > 0) {
					if (!v.get(0).equals(myMover))
						if (!selectedObjects.contains(v.get(0)))
							for (GameObject g : v)
								addSelectedObject(v.get(0));
						else
							for (GameObject g : v)
								selectedObjects.remove(g);
				} else {
					selectedObjects.clear();
				}
			}
			if (selectedObjects.size() == 1) {
				myObjectStatsPanel.setStats(new ObjectStats(selectedObjects
						.get(0).getAllStats()));
			}
			if (selectedObjects.size() == 0) {
				myObjectStatsPanel.setStats(myImageToObjectStatsMap.get(myMover
						.getStats()));
			}
		}
	}

	private void addObjectsToLevel(UninstantiatedGameObject object) {
		myLevel.addObjects(object);
	}

	private void highlight(JGObject object, JGColor color) {
		setColor(JGColor.red);
		if (object != null)
			drawRect(object.x, object.y, object.getBBox().width,
					object.getBBox().height, false, false,
					THICKNESS_DEFAULT_FOR_RECT, color);
	}

}