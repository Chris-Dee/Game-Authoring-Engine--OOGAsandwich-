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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.GameData;
import data.InvalidDataFileException;

public class VAEview extends JFrame {
	private static final String WHAT_WOULD_YOU_LIKE_TO_CALL_THIS_GAME_QUESTION = "What would you like to call this game?";
	private static final String DOT_JSON_EXTENSION = ".json";
	private static final String LEVEL_PANEL_LEVEL_COMPONENT_LIST_FIELD_NAME = "levelComponentList";
	private static final String LEVEL_PANEL_COMPONENT_LEVEL_FIELD_NAME = "myLevel";
	OptionsPanel myOptionsPanel;
	private LevelPanel myLevelPanel;
	private static final String DEFAULT_RESOURCE_FILE_NAME = "resources.GameAuthoringEnvironment";
	private ResourceBundle resources;
	public static final Color backgroundColor = Color.BLACK;
	private static final int LEVEL_LIST_SIZE_X = 600;
	private static final int LEVEL_LIST_SIZE_Y = 600;
	private static final int UP_ARROW_KEY = 38;
	private static final int DOWN_ARROW_KEY = 40;
	private static final int LEVEL_PANEL_SIZE_X = 300;
	private GameData myGameData;

	/**
	 * Main unit of game authoring environment. Run this to run the level
	 * editor.
	 * 
	 */
	public VAEview() {
		initialize();
	}

	public static void main(String[] args) {
		VAEview v = new VAEview();
		v.setMainPanel();
	}

	private void initialize() {
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

	private void setMainPanel() {
		JPanel mainPanel = (JPanel) this.getContentPane();
		mainPanel.setBackground(backgroundColor);
		mainPanel.setLayout(new BorderLayout());

		JPanel editPanel = new JPanel(new BorderLayout());
		BasicLevelStats stats = new BasicLevelStats();
		ScrollPane levelList = createLevelListPane(stats);

		editPanel.add(new ObjectPanel(myLevelPanel), BorderLayout.EAST);
		editPanel.add(stats, BorderLayout.CENTER);

		myOptionsPanel = new OptionsPanel(this);

		levelList.setPreferredSize(new Dimension(LEVEL_PANEL_SIZE_X, HEIGHT));
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

	/**
	 * Saves the current game to a .txt file in the JSON format. The user
	 * defines the file name.
	 */
	public void saveToTextFile() {
		String fileName = JOptionPane
				.showInputDialog(WHAT_WOULD_YOU_LIKE_TO_CALL_THIS_GAME_QUESTION);
		try {
			Field levelComponentListField = myLevelPanel.getClass()
					.getDeclaredField(
							LEVEL_PANEL_LEVEL_COMPONENT_LIST_FIELD_NAME);
			levelComponentListField.setAccessible(true);
			List<LevelPanelComponent> myLevelComponentsList = (List<LevelPanelComponent>) levelComponentListField
					.get(myLevelPanel);

			for (LevelPanelComponent component : myLevelComponentsList) {
				Field levelField = component.getClass().getDeclaredField(
						LEVEL_PANEL_COMPONENT_LEVEL_FIELD_NAME);
				levelField.setAccessible(true);
				Level thisLevel = (Level) levelField.get(component);
				myGameData.addObj(thisLevel);
			}
			myGameData.setFileName(fileName + DOT_JSON_EXTENSION);
			myGameData.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromTextFile(){
		JFileChooser fileChooser = new JFileChooser();
		String selectedFile = "";
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION){
			selectedFile = fileChooser.getSelectedFile().getName();
		}
		myGameData.setFileName(selectedFile);
		try {
			myGameData.parse();
			List<Object> myLevels = myGameData.getObjects("gameEngine.Level");
			
			myLevelPanel.deleteAllLevels();
			for (Object level:myLevels){
				myLevelPanel.addLevel((Level)level);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}