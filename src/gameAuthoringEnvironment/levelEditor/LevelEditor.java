package gameAuthoringEnvironment.levelEditor;

import java.util.List;
import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Map;
import java.util.Vector;

import gameEngine.GameObject;
import gameEngine.Level;
import gameEngine.UninstantiatedGameObject;
import gameplayer.MethodData;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.JGRectangle;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
	private List<GameObject> selectedObjects = new ArrayList<GameObject>();
	private static final String default_path = "src/gameAuthoringEnvironment/levelEditor/Resources/initObject";
	private static final int MAX_FRAME_SKIP = 3;
	private static final int FRAMES_PER_SECOND = 250;
	private static final int SCREEN_HEIGHT = 600;
	private static final int SCREEN_WIDTH = 600;
	private LevelMover myMover;
	private int clickCount = 0;
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
	private ResourceBundle myResources;
	private static final String IMAGE_RESOURCES = "imagetoobject";
	private static final String GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE = "gameAuthoringEnvironment.levelEditor.";

	private Map<String, ObjectStats> myImageToObjectStatsMap;
	private int objectID;

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
		dbgShowMessagesInPf(true);
		objectID = 0;

		dbgIsEmbedded(true);
		initEngine((int) SCREEN_WIDTH, SCREEN_HEIGHT);
		// This just hides the null pointer exception error. If it ends up
		// affecting anything, we can change it.
		defineMedia("tempTable.tbl");
		defineImage("firebackground", "m", 0, "firebackground.jpg", "-");
		defineImage("srball", "n", 0, defaultImage, "-");
		System.out.println("before level move");
		myMover = new LevelMover(this);
		System.out.println("after level move");
		myResources = ResourceBundle
				.getBundle(GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE
						+ IMAGE_RESOURCES);
		myImageToObjectStatsMap = new HashMap<String, ObjectStats>();
		fillImageToObjectStatsMap();
		setBGImage(myLevel.getBackground());

		try {
			fillImageMap(new File(default_path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<GameObject> getSelected() {
		return selectedObjects;
	}

	public void setObjectStatsPanel(ObjectStatsPanel panel) {
		myObjectStatsPanel = panel;
	}

	public void setStats(String imageName) {
		myObjectStatsPanel.setStats(myImageToObjectStatsMap.get(imageName));
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

	public LevelMover getMover() {
		return myMover;
	}

	public boolean checkKey(int key) {
		Boolean b = getKey(key);
		clearLastKey();
		return b;
	}

	private void deleteSelectedObject() {
		if (checkKey(KeyBackspace)) {
			for (GameObject s : selectedObjects) {
				myLevel.getObjects().remove(s.toUninstantiated());
				s.remove();
				selectedObjects.remove(s);
			}
		}
	}
	
	private void placeObject() {
		if (checkKey(KeyEnter) && !myMover.getImageName().equals("srball")) {
			// System.out.println("Adding " + getImageName() + " to screen");
			clearKey(KeyEnter);
			addObject(myMover.getImageName(), (int) myMover.x, (int) myMover.y);
		}
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
		if (myObjectStatsPanel.getMovementName().equals("User-Controlled")) {
			Map<Integer, MethodData<String, Integer>> levelInputMap = new HashMap<Integer, MethodData<String, Integer>>();
			levelInputMap.put(39, new MethodData<String, Integer>("moveRight", 4));
			levelInputMap.put(37, new MethodData<String, Integer>("moveLeft", 4));
			levelInputMap.put(38, new MethodData<String, Integer>("moveUp", 6));
			levelInputMap.put(40, new MethodData<String, Integer>("moveDown", 6));

			newObject = new UninstantiatedGameObject("player",
					new JGPoint(x, y), myObjectStatsPanel.getCollisionID(),
					imageName, levelInputMap, false, true, objectID);
		} else {
			// System.out.println(myObjectStatsPanel.getFloating());
			newObject = new UninstantiatedGameObject(
					myObjectStatsPanel.getObjectName(), new JGPoint(x, y),
					myObjectStatsPanel.getCollisionID(), imageName,
					myObjectStatsPanel.getMovementName().toLowerCase(),
					myObjectStatsPanel.getMovementDuration() * 10,
					myObjectStatsPanel.getMovementSpeed(),
					myObjectStatsPanel.getFloating(), objectID);

		}
		newObject.setMovementName(myObjectStatsPanel.getMovementName());
		objectID++;
		myLevel.addObjects(newObject);
		newObject.instantiate();
	}

	public void setGravity(double value) {
		myLevel.setGravityVal(value / 10);
	}

	private void fillImageToObjectStatsMap() {
		for (String str : myResources.keySet()) {
			myImageToObjectStatsMap.put(str, new ObjectStats(myResources
					.getString(str).split(",")));
		}
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

	private void fillImageMap(File file) throws FileNotFoundException {
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
		deleteSelectedObject();
		placeObject();
		checkInBounds();
		moveObjects(null, 0);
		setViewOffset((int) myMover.x, (int) myMover.y, true);
		 //System.out.println("doFrame");
		selectOnClick();
		// setBGImage(myLevel.getBackground());
	}

	public void paintFrame() {
		for (GameObject s : selectedObjects)
			highlight(s, JGColor.red);
		highlight(myMover, JGColor.blue);
	}

	private void selectOnClick() {
		// new JGRectangle()
		if (getMouseButton(1)) {
			if (clickCount != 0)
				clickCount--;
			else {
				clickCount = 30;
				JGRectangle rect = new JGRectangle(getMouseX() + el.xofs,
						getMouseY() + el.yofs, 10, 10);
				Vector<GameObject> v = getObjects("", 0, true, rect);
				if (v.size() > 0) {

					if (!v.get(0).equals(myMover))
						if (!selectedObjects.contains(v.get(0)))
							for (GameObject g : v)
								selectedObjects.add(v.get(0));
						else
							for (GameObject g : v)
								selectedObjects.remove(g);
				}
			}
			// System.out.println(selectedObject.size());
			if (selectedObjects.size() == 1) {
				myObjectStatsPanel.setStats(new ObjectStats(selectedObjects
						.get(0).getAllStats()));
				// TODO set statsPanel to values for recently added object
			}
			if (selectedObjects.size() == 0) {
				myObjectStatsPanel.setStats(myImageToObjectStatsMap.get(myMover
						.getMoverImage()));
				// TODO set sliders and shit to myMover stats
			}
		}

	}

	public void clearGame() {
		selectedObjects.clear();
	}

	private void highlight(JGObject object, JGColor color) {
		setColor(JGColor.red);
		if (object != null)
			drawRect(object.x, object.y, object.getBBox().width,
					object.getBBox().height, false, false, 2, color);
	}
	// TODO add method to check for collisions with screen boundary, and decide
	// what to do when screen
	// shrinks and doesn't include mover.
	// TODO push a button to place selected object?
}
