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
	LevelEditor myLevel;
	private Integer changeCounter = 0;
	private final static String RESOURCE_PATH = "/gameAuthoringEnvironment/levelEditor/Resources/";

	public LevelMover(LevelEditor level) {

		super("srball", true, INITIAL_X_AND_Y, INITIAL_X_AND_Y, BALL_COL_ID,
				"srball", 0, 0, INITIAL_X_Y_SPEED, INITIAL_X_Y_SPEED, -1);
		myEngine = level;
		myLevel = level;
		x = INITIAL_X_AND_Y;
		y = INITIAL_X_AND_Y;
	}

	public LevelMover(LevelEditor level, int xPos, int yPos, Integer iteration) {
		super("newBallImage"+iteration.toString(),true, xPos, yPos,BALL_COL_ID,"newBallImage"+iteration.toString(),0,0,INITIAL_X_Y_SPEED,INITIAL_X_Y_SPEED,-1);
		myEngine = level;
		myLevel = level;
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
		changeCounter++;
		myEngine.defineImage("newBallImage"+changeCounter.toString(),"",0,RESOURCE_PATH + imageName,"-");
		myLevel.myMover.setImage("newBallImage"+changeCounter.toString());
		//myLevel.myMover = new LevelMover(myLevel, (int) x, (int) y, changeCounter);
		
		
	}
}

