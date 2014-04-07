package gameAuthoringEnvironment.levelEditor;

//TODO make this work...
import java.util.HashMap;

import gameEngine.GameObject;
import gameEngine.GameObjectAction;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.platform.JGEngine;

//The invisible little object that moves around in the levels
public class LevelMover extends JGObject {
	//private Integer changeCounter;
	private static String moverImage = "srball";
	private static final double INITIAL_X_Y_SPEED = 2.0;
	private static final int BALL_COL_ID = 4;
	private static final int INITIAL_X_AND_Y = 20;
	private JGEngine myEngine;
	private LevelEditor myLevelEditor;
	private final static String RESOURCE_PATH = "/gameAuthoringEnvironment/levelEditor/Resources/";

	/**
	 * The object that moves around in the levels so user can edit them
	 * 
	 * @param levelEditor
	 *            LevelEditor that this object exists in
	 */
	public LevelMover(LevelEditor levelEditor) {

		super("srball", true, INITIAL_X_AND_Y, INITIAL_X_AND_Y, BALL_COL_ID,
				moverImage, 0, 0, INITIAL_X_Y_SPEED, INITIAL_X_Y_SPEED, -1);
		myEngine = levelEditor;
		myLevelEditor = levelEditor;
		x = INITIAL_X_AND_Y;
		y = INITIAL_X_AND_Y;
		activate();
	}

	private boolean checkKey(int key) {
		Boolean b = myEngine.getKey(key);
		myEngine.clearLastKey();
		return b;
	}

	public void move() {
		xdir = 0;
		ydir = 0;
		if (checkKey(myEngine.KeyLeft)) {
			xdir = -16;
			myEngine.clearKey(myEngine.KeyLeft);
		}
		if (checkKey(myEngine.KeyRight)) {
			xdir = 16;
			myEngine.clearKey(myEngine.KeyRight);
		}
		if (checkKey(myEngine.KeyUp)) {
			ydir = -16;
			myEngine.clearKey(myEngine.KeyUp);
		}
		if (checkKey(myEngine.KeyDown)) {
			ydir = 16;
			myEngine.clearKey(myEngine.KeyDown);
		}
		if (checkKey(myEngine.KeyEnter)) {
			System.out.println("Adding " + getImageName() + " to screen");
			myEngine.clearKey(myEngine.KeyEnter);
			HashMap<Integer, String> levelInputMap = new HashMap<Integer, String>();
			levelInputMap.put(39,  "moveRight");
			levelInputMap.put(37, "moveLeft");
			levelInputMap.put(40, "moveDown");
			levelInputMap.put(38, "moveUp");

			GameObject newObject = new GameObject("player", new JGPoint((int)x, (int)y), 1, moverImage, levelInputMap, false);
			myLevelEditor.getLevel().addObjects(newObject);
			newObject.activate();
		}
	}

	public void changeImage(String imageName) {

		moverImage = imageName;
		myLevelEditor.defineImage(imageName, "n", 0, RESOURCE_PATH + imageName,
				"-");

	}
}
