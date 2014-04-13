package gameAuthoringEnvironment.levelEditor;

//TODO make this work...
import java.util.HashMap;

import gameEngine.GameObjectAction;
import gameEngine.UninstantiatedGameObject;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.platform.JGEngine;

//The invisible little object that moves around in the levels
public class LevelMover extends JGObject {
	// private Integer changeCounter;
	private static final String MOVER_STARTING_IMAGE = "srball";
	private ObjectStats moverStats;
	private String myMoverImage = MOVER_STARTING_IMAGE;
	private static final double INITIAL_X_Y_SPEED = 2.0;
	private static final int BALL_COL_ID = 4;
	private static final int INITIAL_X_AND_Y = 20;
	private LevelEditor myEngine;
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
				MOVER_STARTING_IMAGE, 0, 0, INITIAL_X_Y_SPEED, INITIAL_X_Y_SPEED, -1);
		myEngine = levelEditor;
		myLevelEditor = levelEditor;
		x = INITIAL_X_AND_Y;
		y = INITIAL_X_AND_Y;
	}
	
	public String getMoverImage() {
		return myMoverImage;
	}

	public void move() {
		xdir = 0;
		ydir = 0;
		if (myEngine.checkKey(myEngine.KeyLeft)) {
			xdir = -16;
			myEngine.clearKey(myEngine.KeyLeft);
		}
		if (myEngine.checkKey(myEngine.KeyRight)) {
			xdir = 16;
			myEngine.clearKey(myEngine.KeyRight);
		}
		if (myEngine.checkKey(myEngine.KeyUp)) {
			ydir = -16;
			myEngine.clearKey(myEngine.KeyUp);
		}
		if (myEngine.checkKey(myEngine.KeyDown)) {
			ydir = 16;
			myEngine.clearKey(myEngine.KeyDown);
		}
		if (myEngine.checkKey(myEngine.KeyEnter)) {
			// System.out.println("Adding " + getImageName() + " to screen");
			myEngine.clearKey(myEngine.KeyEnter);
			myMoverImage = this.getImageName();
			myLevelEditor.addObject(myMoverImage, (int) x, (int) y);
		}
	}

	public void changeImage(String imageName) {
		setImage(imageName);
	}
}