package gameAuthoringEnvironment.levelEditor;

//TODO make this work...
import jgame.JGObject;
import jgame.platform.JGEngine;

//The invisible little object that moves around in the levels
public class LevelMover extends JGObject {
	JGEngine myEngine;
	LevelEditor myLevel;
	private Integer changeCounter = 0;
	private final static String RESOURCE_PATH = "/gameAuthoringEnvironment/levelEditor/Resources/";

	public LevelMover(LevelEditor level) {

		super("srball",true, 20, 20,4,"srball",0,0,2.0,2.0,-1);
		myEngine=level;
		myLevel = level;
		x=20;
		y=20;
	}
	
	public LevelMover(LevelEditor level, int xPos, int yPos, Integer iteration) {
		super("newBallImage"+iteration.toString(),true, xPos, yPos,4,"newBallImage"+iteration.toString(),0,0,2.0,2.0,-1);
		myEngine = level;
		myLevel = level;
		x = xPos;
		y = yPos;
	}
	
	public Integer xPos(){
		return (int) x;
	}
	public Integer yPos(){
		return(int) y;
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
		myLevel.myMover.remove();
		myLevel.myMover = new LevelMover(myLevel, (int) x, (int) y, changeCounter);
		
		
	}

}
