package gameAuthoringEnvironment.frontEnd;

import gameAuthoringEnvironment.levelStatsEditor.BasicLevelStats;
import gameEngine.Level;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.GameData;
import data.InvalidDataFileException;

/**
 * Main unit of game authoring environment. Run this to run the level editor.
 * 
 * 
 */
public class VAEview extends JFrame {
	OptionsPanel myOptionsPanel;
	private LevelPanel myLevelPanel;
	private static final String DEFAULT_RESOURCE_FILE_NAME = "resources.GameAuthoringEnvironment";
	private ResourceBundle resources;
	public static final Color backgroundColor = Color.BLACK;
	private static final int LEVEL_LIST_SIZE_X = 600;
	private static final int LEVEL_LIST_SIZE_Y = 600;
	private static final int UP_ARROW_KEY = 38;
	private static final int DOWN_ARROW_KEY = 40;
	private GameData myGameData;

	public VAEview() {
		initialize();
	}

	public static void main(String[] args) {
		VAEview v = new VAEview();
		v.setMainPanel();
	}

	public void initialize() {
		setTitle("OOGASandwich Game Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		setLayout(new BorderLayout());
		resources = ResourceBundle.getBundle(DEFAULT_RESOURCE_FILE_NAME);
		try {
			myGameData = new GameData("");
		} catch (InvalidDataFileException e) {
			e.printStackTrace();
		}
	}

	public void setMainPanel() {
		JPanel mainPanel = (JPanel) this.getContentPane();
		mainPanel.setBackground(backgroundColor);
		mainPanel.setLayout(new BorderLayout());

		JPanel editPanel = new JPanel(new BorderLayout());
		BasicLevelStats stats = new BasicLevelStats();
		ScrollPane levelList = createLevelListPane(stats);

		editPanel.add(new ObjectPanel(myLevelPanel), BorderLayout.EAST);
		editPanel.add(stats, BorderLayout.CENTER);

		// mainPanel.add(new OptionsPanel(this),BorderLayout.NORTH);
		myOptionsPanel = new OptionsPanel(this);

		levelList.setPreferredSize(new Dimension(300, HEIGHT));
		mainPanel.add(levelList);
		mainPanel.add(editPanel, BorderLayout.EAST);

		setExtendedState(Frame.MAXIMIZED_BOTH);
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		MyDispatcher dispatch = new MyDispatcher();
		manager.addKeyEventDispatcher(dispatch);
		pack();
	}

	private ScrollPane createLevelListPane(BasicLevelStats stats) {
		ScrollPane scroller = new ScrollPane();

		scroller.setSize(LEVEL_LIST_SIZE_X, LEVEL_LIST_SIZE_Y);
		LevelPanel level = new LevelPanel(stats);
		myLevelPanel = level;
		scroller.add(level);

		return scroller;
	}

	private class MyDispatcher implements KeyEventDispatcher {
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {

			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				if (e.getKeyCode() == UP_ARROW_KEY)
					myLevelPanel.switchLevels(myLevelPanel.findActivePanel(),
							-1);
				if (e.getKeyCode() == DOWN_ARROW_KEY) {
					myLevelPanel
							.switchLevels(myLevelPanel.findActivePanel(), 1);
				}
			} else if (e.getID() == KeyEvent.KEY_TYPED) {
			}
			return false;
		}
	}

	public void save() {
		String fileName = JOptionPane.
				showInputDialog("What would you like to call this game?");
		try {
			Field levelComponentListField = myLevelPanel.getClass()
					.getDeclaredField("levelComponentList");
			levelComponentListField.setAccessible(true);
			List<LevelPanelComponent> myLevelComponentsList = (List<LevelPanelComponent>) levelComponentListField
					.get(myLevelPanel);

			for (LevelPanelComponent component : myLevelComponentsList) {
				Field levelField = component.getClass().getDeclaredField(
						"currLevel");
				levelField.setAccessible(true);
				Level thisLevel = (Level) levelField.get(component);
				myGameData.addObj(thisLevel);
			}
			myGameData.setFileName(fileName + ".txt");
			myGameData.write();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}