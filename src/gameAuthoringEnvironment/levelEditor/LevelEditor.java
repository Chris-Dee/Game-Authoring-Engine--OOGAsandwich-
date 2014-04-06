package gameAuthoringEnvironment.levelEditor;

import gameEngine.Level;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {

	private static final int MAX_FRAME_SKIP = 3;
	private static final int FRAMES_PER_SECOND = 250;
	private static final int SCREEN_HEIGHT = 600;
	private static final int SCREEN_WIDTH = 600;
	public LevelMover myMover;

	Level myLevel;

	private static final int INITIAL_WIDTH = 600;
	private static final int INITIAL_HEIGHT = 600;
	private static final int BLOCK_SIZE_X = 300;
	private static final int BLOCK_SIZE_Y = 300;

	private static final int BALL_OFF_SCREEN_ADJUSTMENT_X_LEFTSIDE = 5;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_X_RIGHTSIDE = 10;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_Y_TOP = 5;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_Y_BOTTOM = 10;

	private final String defaultImage = "/gameAuthoringEnvironment/levelEditor/Resources/red.gif";

	/**
	 * JGame class that holds the level editor. This displays what the created level
	 * currently looks like.
	 * 
	 * @param level
	 * 			Level being edited
	 */
	public LevelEditor(Level level) {
		super();

		myLevel = level;
		dbgShowMessagesInPf(false);
		dbgIsEmbedded(true);
		initEngine((int) SCREEN_WIDTH, SCREEN_HEIGHT);
		// This just hides the null pointer exception error. If it ends up
		// affecting anything, we can change it.

		defineImage("srball", "", 0, defaultImage, "-");
		myMover = new LevelMover(this);
		defineImage("background1", "", 0, myLevel.getBackground(), "-");
		setBGImage("background1");
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(1, 1, BLOCK_SIZE_X, BLOCK_SIZE_Y, null,
				JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(FRAMES_PER_SECOND, MAX_FRAME_SKIP);
		// width in spot 0, height in spot 1
		setPFSize(myLevel.getLevelSize().x, myLevel.getLevelSize().y);
	}

	private void checkInBounds() {
		if ((Double) myMover.x == null)
			System.out.println((Double) myMover.x == null);
		if (myMover.x >= myMover.pfwidth) {
			// myMover.x=el.xofs;
			myMover.x = myMover.pfwidth - BALL_OFF_SCREEN_ADJUSTMENT_X_RIGHTSIDE;
		}
		if (myMover.y >= myMover.pfheight) {
			myMover.y = myMover.pfheight - BALL_OFF_SCREEN_ADJUSTMENT_Y_BOTTOM;
		}
		if (myMover.x <= 0) {
			myMover.x = BALL_OFF_SCREEN_ADJUSTMENT_X_LEFTSIDE;
		}
		if (myMover.y <= 0) {
			myMover.y = BALL_OFF_SCREEN_ADJUSTMENT_Y_TOP;
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
