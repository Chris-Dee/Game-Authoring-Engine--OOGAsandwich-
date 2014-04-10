package gameAuthoringEnvironment.levelEditor;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import java.util.Vector;

import gameEngine.GameObject;
import gameEngine.Level;
import gameEngine.UninstantiatedGameObject;
import gameplayer.Tuple;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.JGRectangle;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
	private GameObject selectedObject;
	private static final String default_path = "src/gameAuthoringEnvironment/levelEditor/Resources/initObject";
	private static final int MAX_FRAME_SKIP = 3;
	private static final int FRAMES_PER_SECOND = 250;
	private static final int SCREEN_HEIGHT = 600;
	private static final int SCREEN_WIDTH = 600;
	// TODO Why is this public?
	public LevelMover myMover;

	private Map<String, String> imageMap = new HashMap<String, String>();
	private Level myLevel;
	private static final int INITIAL_WIDTH = 600;
	private static final int INITIAL_HEIGHT = 600;
	private static final int BLOCK_SIZE_X = 300;
	private static final int BLOCK_SIZE_Y = 300;

	private static final int BALL_OFF_SCREEN_ADJUSTMENT_X_LEFTSIDE = 5;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_X_RIGHTSIDE = 10;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_Y_TOP = 5;
	private static final int BALL_OFF_SCREEN_ADJUSTMENT_Y_BOTTOM = 10;
	private ObjectStatsPanel myObjectStatsPanel;

	private final String defaultImage = "/gameAuthoringEnvironment/levelEditor/Resources/red.gif";
	private static int COLID_FOR_PLAYER = 1;
	private static int COLID_FOR_ENEMY = 4;
	private static int COLID_FOR_BLOCK = 2;
	private static int COLID_FOR_GOAL = 8;

	/**
	 * JGame class that holds the level editor. This displays what the created
	 * level currently looks like.
	 * 
	 * @param level
	 *            Level being edited
	 */
	public LevelEditor(Level level) {
		super();
		myLevel = level;
		dbgShowMessagesInPf(false);
		dbgIsEmbedded(true);
		initEngine((int) SCREEN_WIDTH, SCREEN_HEIGHT);
		// This just hides the null pointer exception error. If it ends up
		// affecting anything, we can change it.
		defineMedia("tempTable.tbl");
		defineImage("firebackground","m",0,"firebackground.jpg","-");
		defineImage("srball", "n", 0, defaultImage, "-");
		myMover = new LevelMover(this);

		setBGImage(myLevel.getBackground());

		try {
			fillImageMap(new File(default_path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Level getLevel() {
		return myLevel;
	}

	public void setObjectStatsPanel(ObjectStatsPanel panel) {
		myObjectStatsPanel = panel;
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
	public void addObject(String imageName, int x, int y) {
		UninstantiatedGameObject newObject;
		if (myObjectStatsPanel.getObjectName().equals("Player")) {
			Map<Integer, Tuple<String,Integer>> levelInputMap = new HashMap<Integer, Tuple<String,Integer>>();
			levelInputMap.put(39, new Tuple<String, Integer>("moveRight", 4));
			levelInputMap.put(37, new Tuple<String, Integer>("moveLeft",4));
			levelInputMap.put(38, new Tuple<String, Integer>("moveUp",6));
			levelInputMap.put(40, new Tuple<String, Integer>("moveDown",6));

			newObject = new UninstantiatedGameObject("player",
					new JGPoint(x, y), COLID_FOR_PLAYER, imageName, levelInputMap, false, true);
		} else if (myObjectStatsPanel.getObjectName().equals("Block")) {
			newObject = new UninstantiatedGameObject("block",
					new JGPoint(x, y), COLID_FOR_BLOCK, imageName, true);
		} else if (myObjectStatsPanel.getObjectName().equals("Enemy")) {
			newObject = new UninstantiatedGameObject("goomba",
					new JGPoint(x, y), COLID_FOR_ENEMY, imageName, myObjectStatsPanel
							.getMovementName().toLowerCase(),
					myObjectStatsPanel.getMovementSpeed() * 10,
					myObjectStatsPanel.getMovementDuration(), false);
		} else if (myObjectStatsPanel.getObjectName().equals("Moving Platform")) {
			newObject = new UninstantiatedGameObject("moving platform",
					new JGPoint(x, y), COLID_FOR_BLOCK, imageName, myObjectStatsPanel
							.getMovementName().toLowerCase(),
					myObjectStatsPanel.getMovementSpeed() * 10,
					myObjectStatsPanel.getMovementDuration(), true);
		} else if (myObjectStatsPanel.getObjectName().equals("Goal")) {
			newObject = new UninstantiatedGameObject("goal", new JGPoint(x, y),
					COLID_FOR_GOAL, imageName, true);
		} else if (myObjectStatsPanel.getObjectName().equals("Scenery")) {
			newObject = new UninstantiatedGameObject("stationary platform",
					new JGPoint(x, y), COLID_FOR_PLAYER + 4, imageName, true);
		} else {
			newObject = new UninstantiatedGameObject("block",
					new JGPoint(x, y), COLID_FOR_BLOCK, imageName, true);
		}
		myLevel.addObjects(newObject);
		newObject.instantiate();
	}
	
	public void setGravity(double value) {
		myLevel.setGravityVal(value/10);
	}

	private void checkInBounds() {
		if ((Double) myMover.x == null)
			System.out.println((Double) myMover.x == null);
		if (myMover.x >= myMover.pfwidth) {
			// myMover.x=el.xofs;
			myMover.x = myMover.pfwidth
					- BALL_OFF_SCREEN_ADJUSTMENT_X_RIGHTSIDE;
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

	public void fillImageMap(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		int i = 1;
		while (s.hasNext()) {
			System.out.println(i);
			String[] str = s.nextLine().split(" ");
			imageMap.put(str[0], str[1]);
			defineImage(str[0], "a" + i, 0, str[1], "a" + i);
			i++;
		}
		System.out.println("size " + el.images_loaded.size());
	}

	public Map<String, String> getMap() {
		return imageMap;
	}

	public void doFrame() {
		checkInBounds();
		moveObjects(null, 0);
		setViewOffset((int) myMover.x, (int) myMover.y, true);
		// System.out.println(this.el.images_loaded.size());
		selectOnClick();
		//setBGImage(myLevel.getBackground());
	}

	public void paintFrame() {
		highlight(selectedObject, JGColor.red);
		highlight(myMover,JGColor.blue);
	}

	public void selectOnClick() {
		//new JGRectangle()
		if (getMouseButton(1)) {
			JGRectangle rect=new JGRectangle(getMouseX()+el.xofs, getMouseY()+el.yofs, 10, 10);
			Vector<GameObject> v = getObjects("", 0, true, rect);
			System.out.println(rect);
			if (v.size() > 0)
			if( v.get(0) != (JGObject) myMover)
				selectedObject = v.get(0);
			//System.out.println(selectedObject.x+"    "+selectedObject.y);
		}

	}

	public void highlight(JGObject object, JGColor color) {
		setColor(JGColor.red);
		if (object != null)
			drawRect(object.x, object.y,
					object.getBBox().width,
					object.getBBox().height, false, false, 2,
					color);
	}
	// TODO add method to check for collisions with screen boundary, and decide
	// what to do when screen
	// shrinks and doesn't include mover.
	// TODO push a button to place selected object?
}
