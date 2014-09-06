package gameauthoringenvironment.leveleditor;

import jgame.JGObject;


public class LevelMover extends JGObject {
	
	private static final int BALL_COL_ID = 4;
	private static final int INITIAL_X_AND_Y = 0;
	private static final double INITIAL_X_Y_SPEED = 2.0;
	private static final String MOVER_STARTING_IMAGE = "srball";
	
	private LevelEditor myLevelEditor;
	private ObjectStats myStats;

	/**
	 * The object that moves around in the levels so user can edit them
	 * 
	 * @param levelEditor
	 *            LevelEditor that this object exists in
	 */
	public LevelMover(LevelEditor levelEditor) {

		super("srball", true, INITIAL_X_AND_Y, INITIAL_X_AND_Y, BALL_COL_ID,
				"srball", 0, 0, INITIAL_X_Y_SPEED, INITIAL_X_Y_SPEED, -1);
		myLevelEditor = levelEditor;
		myStats = new ObjectStats();
		myStats.myImageName = MOVER_STARTING_IMAGE;
		x = INITIAL_X_AND_Y;
		y = INITIAL_X_AND_Y;
	}

	/**
	 * Gets the ObjectStats of the mover, which is the object to be placed.
	 * 
	 * @return Stats of the mover.
	 */

	public ObjectStats getStats() {
		return myStats;
	}

	/**
	 * Method to be called to move the LevelMover.
	 */

	public void move() {
		xdir = 0;
		ydir = 0;
		if (myLevelEditor.checkKey(myLevelEditor.KeyLeft)) {
			xdir = -getBBox().width/4;
			myLevelEditor.clearKey(myLevelEditor.KeyLeft);
		}
		if (myLevelEditor.checkKey(myLevelEditor.KeyRight)) {
			xdir = getBBox().width/4;
			myLevelEditor.clearKey(myLevelEditor.KeyRight);
		}
		if (myLevelEditor.checkKey(myLevelEditor.KeyUp)) {
			ydir = -getBBox().height/4;
			myLevelEditor.clearKey(myLevelEditor.KeyUp);
		}
		if (myLevelEditor.checkKey(myLevelEditor.KeyDown)) {
			ydir = getBBox().height/4;
			myLevelEditor.clearKey(myLevelEditor.KeyDown);
		}
	}
	private void setPosition(){ 
		System.out.println(pfwidth);
		x=x-x%getBBox().width;
		y=y-y%getBBox().height;
		System.out.println(getBBox().width);
	}
	/**
	 * Changes the image of the mover to the given image. The image is given as
	 * a string that is the name of the file containing the image.
	 * 
	 * @param imageName
	 *            New image of the LevelMover
	 */

	public void changeImage(String imageName) {
		setImage(imageName);
		myStats = myLevelEditor.getStatsForImage(imageName);
		setPosition();
	}
}