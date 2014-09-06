package gameauthoringenvironment.frontend;

import gameauthoringenvironment.levelstatseditor.BasicLevelStats;
import gameengine.Game;
import gameengine.Level;
import gameengine.eventactions.KillPlayerAction;
import gameengine.gameevents.BoundaryEvent;
import gameplayer.GamePlayerFrame;
import gameplayer.GamePlayerEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.GameData;
import data.InvalidDataFileException;
import data.SandwichGameData;

public class VAEview extends JFrame {

	public static final int BLOCK_SIZE_X = 320;
	private static final int LEVEL_LIST_SIZE_X = 600;
	private static final int LEVEL_LIST_SIZE_Y = 600;
	private static final int UP_ARROW_KEY = 38;
	private static final int DOWN_ARROW_KEY = 40;
	private static final int LEVEL_PANEL_SIZE_X = 300;
	private static final String BAD_FILE_ERROR_NAME = "Bad File Error";
	private static final String INVALID_GAME_CHOICE_ERROR_MESSAGE = "Invalid game file, please choose a different one";
	private static final String PLAYER_HITS_BOTTOM_OF_SCREEN_STRING = "playerhitsbottomofscreen";
	private static final String SET_CURRENT_DIRECTORY_CONSTANT = "user.dir";
	private static final String TITLE_TEXT = "OOGASandwich Game Editor";
	private static final String TEMPORARY_GAME_NAME = "Current Game";
	private static final String WHAT_WOULD_YOU_LIKE_TO_CALL_THIS_GAME_QUESTION = "What would you like to call this game?";
	private static final String DOT_JSON_EXTENSION = ".json";
	private static final String LEVEL_PANEL_LEVEL_COMPONENT_LIST_FIELD_NAME = "levelComponentList";
	private static final String LEVEL_PANEL_COMPONENT_LEVEL_FIELD_NAME = "myLevel";

	private LevelPanel myLevelPanel;
	private static final Color BACKGROUND_COLOR = Color.BLACK;

	private GameData myGameData;

	/**
	 * Main unit of game authoring environment. Run this to run the level
	 * editor.
	 * 
	 */
	public VAEview() {
		initialize();
	}

	// public static void main(String[] args) {
	// VAEview v = new VAEview();
	// }

	private void initialize() {
		setTitle(TITLE_TEXT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		setLayout(new BorderLayout());
		myGameData = new SandwichGameData();
		setMainPanel();
	}

	private void setMainPanel() {
		JPanel mainPanel = (JPanel) this.getContentPane();
		mainPanel.setBackground(BACKGROUND_COLOR);
		mainPanel.setLayout(new BorderLayout());
		JPanel editPanel = new JPanel(new BorderLayout());
		BasicLevelStats stats = new BasicLevelStats();
		ScrollPane levelList = createLevelListPane(stats);

		editPanel.add(new GameChangesPanel(myLevelPanel, BACKGROUND_COLOR),
				BorderLayout.EAST);
		editPanel.add(stats, BorderLayout.CENTER);

		levelList.setPreferredSize(new Dimension(LEVEL_PANEL_SIZE_X, HEIGHT));
		mainPanel.add(levelList);
		mainPanel.add(editPanel, BorderLayout.EAST);

		setExtendedState(Frame.MAXIMIZED_BOTH);
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		MyDispatcher dispatch = new MyDispatcher();
		manager.addKeyEventDispatcher(dispatch);
		this.setJMenuBar(new OptionsPanel(this));
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
	 * Plays the current iteration of the game. Saves the current game state
	 * into a temporary file, and then runs that game.
	 * 
	 * @throws Exception
	 */
	public void playGame() throws Exception {
		saveGame(TEMPORARY_GAME_NAME);
		new GamePlayerFrame(new Game(TEMPORARY_GAME_NAME + DOT_JSON_EXTENSION));
	}

	/**
	 * Saves the current game to a .txt file in the JSON format. The user
	 * defines the file name.
	 * 
	 * @throws Exception
	 */
	public void saveToJSONFile() throws Exception {
		String fileName = JOptionPane
				.showInputDialog(WHAT_WOULD_YOU_LIKE_TO_CALL_THIS_GAME_QUESTION);
		saveGame(fileName);
	}

	/**
	 * Saves the current state of the game to a .JSON file
	 * 
	 * @param fileName
	 *            Name of the file
	 * @throws Exception
	 */
	private void saveGame(String fileName) throws Exception {
		Field levelComponentListField = myLevelPanel.getClass()
				.getDeclaredField(LEVEL_PANEL_LEVEL_COMPONENT_LIST_FIELD_NAME);
		levelComponentListField.setAccessible(true);
		List<LevelPanelComponent> myLevelComponentsList = (List<LevelPanelComponent>) levelComponentListField
				.get(myLevelPanel);
		List<String> namesAlreadyUsed = new ArrayList<String>();
		myGameData = new SandwichGameData();
		for (LevelPanelComponent component : myLevelComponentsList) {
			Field levelField = component.getClass().getDeclaredField(
					LEVEL_PANEL_COMPONENT_LEVEL_FIELD_NAME);
			levelField.setAccessible(true);
			Level thisLevel = (Level) levelField.get(component);
			BoundaryEvent event10 = new BoundaryEvent(Arrays.asList("3",
					"0"), PLAYER_HITS_BOTTOM_OF_SCREEN_STRING);
			
			KillPlayerAction action3 = new KillPlayerAction(
					new ArrayList<String>(Arrays.asList("0")));
			event10.addAction(action3);
			thisLevel.addEvent(event10);
			if (!namesAlreadyUsed.contains(thisLevel.getLevelName())
					&& thisLevel.getLevelName() != null) {
				myGameData.addObj(thisLevel);
				namesAlreadyUsed.add(thisLevel.getLevelName());
			}
		}
		myGameData.setFileName(fileName + DOT_JSON_EXTENSION);
		myGameData.write();
	}

	/**
	 * Loads a game from a JSON file. The previous contents of the viewer are
	 * deleted and replaced with the new contents of the loaded file
	 */
	public void loadFromJSONFile() {
		myGameData = new SandwichGameData();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System
				.getProperty(SET_CURRENT_DIRECTORY_CONSTANT)));
		String selectedFile = "";
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile().getName();
		}
		myGameData.setFileName(selectedFile);
		try {
			myGameData.parse();
			List<Object> myLevels = myGameData.getObjects("gameengine.Level");
			myLevelPanel.deleteAllLevels();
			for (Object level : myLevels) {
				myLevelPanel.addLevel((Level) level);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					INVALID_GAME_CHOICE_ERROR_MESSAGE, BAD_FILE_ERROR_NAME,
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}