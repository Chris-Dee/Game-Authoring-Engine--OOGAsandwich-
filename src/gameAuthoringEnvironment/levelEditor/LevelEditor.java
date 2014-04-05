package gameAuthoringEnvironment.levelEditor;

import gameEngine.Level;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.platform.JGEngine;

/**
 * JGame class that holds the level editor. This displays what the created level
 * currently looks like.
 * 
 * 
 */
public class LevelEditor extends JGEngine {
	public LevelMover myMover;
	Level level;

	private static final int INITIAL_WIDTH = 600;
	private static final int INITIAL_HEIGHT = 600;
	private static final int BLOCK_SIZE_X = 300;
	private static final int BLOCK_SIZE_Y = 300;

	private static final int BALL_OFF_SCREEN_ADJUSTMENT_X = 20;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_Y = 5;

	private final String defaultImage = "/gameAuthoringEnvironment/levelEditor/Resources/red.gif";

	public LevelEditor(Level l) {
		super();

		level=l;
		dbgShowMessagesInPf(false);
		dbgIsEmbedded(true);
		initEngine((int) 600, 600);
		//This just hides the null pointer exception error. If it ends up affecting anything, we can change it.
		
		defineImage("srball","",0,defaultImage,"-");
		myMover=new LevelMover(this);
		defineImage("background1","",0,level.getBackground(),"-");
		setBGImage("background1");
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(1, 1, BLOCK_SIZE_X, BLOCK_SIZE_Y, null,
				JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(250, 3);
		// width in spot 0, height in spot 1
		setPFSize(level.getLevelSize().x, level.getLevelSize().y);
	}

	private void checkInBounds(){
		if((Double)myMover.x==null)
		System.out.println((Double)myMover.x==null);
		if(myMover.x>=myMover.pfwidth){
			//myMover.x=el.xofs;
			myMover.x=myMover.pfwidth-BALL_OFF_SCREEN_ADJUSTMENT_X;
		}
		if (myMover.y >= myMover.pfheight) {
			myMover.y = myMover.pfheight - BALL_OFF_SCREEN_ADJUSTMENT_Y;
		}
		if (myMover.x <= 0) {
			myMover.x = BALL_OFF_SCREEN_ADJUSTMENT_X;
		}
		if (myMover.y <= 0) {
			myMover.y = BALL_OFF_SCREEN_ADJUSTMENT_Y;
		}
		// System.out.println(el.xofs+" "+el.yofs+" "+myMover.x+" "+myMover.y+" "+myMover.pfwidth);
	}

	public void doFrame() {
		checkInBounds();
		moveObjects(null, 0);
		setViewOffset((int) myMover.x, (int) myMover.y, true);
	}
	// TODO add method to check for collisions with screen boundary, and decide
	// what to do when screen
	// shrinks and doesn't include mover.
	// TODO push a button to place selected object?
}
