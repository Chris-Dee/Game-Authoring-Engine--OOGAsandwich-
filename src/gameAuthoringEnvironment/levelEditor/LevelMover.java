package gameAuthoringEnvironment.levelEditor;

//TODO make this work...
import jgame.JGObject;
import jgame.platform.JGEngine;

//The invisible little object that moves around in the levels
public class LevelMover extends JGObject {
	private static final double INITIAL_X_Y_SPEED = 2.0;
	private static final int BALL_COL_ID = 4;
	private static final int INITIAL_X_AND_Y = 20;
	JGEngine myEngine;
	LevelEditor myLevelEditor;
	private final static String RESOURCE_PATH = "/gameAuthoringEnvironment/levelEditor/Resources/";

	/**
	 * The object that moves around in the levels so user can edit them
	 * 
	 * @param levelEditor
	 * 				LevelEditor that this object exists in
	 */
	public LevelMover(LevelEditor levelEditor) {

		super("srball", true, INITIAL_X_AND_Y, INITIAL_X_AND_Y, BALL_COL_ID,
				"srball", 0, 0, INITIAL_X_Y_SPEED, INITIAL_X_Y_SPEED, -1);
		myEngine = levelEditor;
		myLevelEditor = levelEditor;
		x = INITIAL_X_AND_Y;
		y = INITIAL_X_AND_Y;
	}

	public LevelMover(LevelEditor level, int xPos, int yPos) {
		super("srball",true, xPos, yPos,BALL_COL_ID,"srball",0,0,INITIAL_X_Y_SPEED,INITIAL_X_Y_SPEED,-1);
		myEngine = level;
		myLevelEditor = level;
		x = xPos;
		y = yPos;
	}
	
	public Integer xPos() {

		return (int) x;
	}

	public Integer yPos() {
		return (int) y;
	}

	private boolean checkKey(int key) {
		Boolean b = myEngine.getKey(key);
		myEngine.clearLastKey();

		return b;
	}

	public void move() {
		xdir = 0;
		ydir = 0;
		if (checkKey(myEngine.KeyLeft))
			xdir = -1;
		if (checkKey(myEngine.KeyRight))
			xdir = 1;
		if (checkKey(myEngine.KeyUp))
			ydir = -1;
		if (checkKey(myEngine.KeyDown))
			ydir = 1;
	}

	
	public void changeImage(String imageName) {
		myLevelEditor.defineImage("srball","n",0,RESOURCE_PATH + imageName,"-");
		//myLevelEditor.myMover.destroy();
		//myLevelEditor.myMover = new LevelMover(myLevelEditor, (int) x, (int) y);
	}
}

